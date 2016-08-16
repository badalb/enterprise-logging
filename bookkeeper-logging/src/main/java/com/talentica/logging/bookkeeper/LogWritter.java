package com.talentica.logging.bookkeeper;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.LedgerHandle;
import org.apache.bookkeeper.conf.ClientConfiguration;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import com.google.gson.Gson;
import com.talentica.logging.model.LogDto;

public class LogWritter extends LeaderSelectorListenerAdapter implements Closeable {

	private static final String ZOOKEEPER_SERVER = "127.0.0.1:2181";
	private static final String ELECTION_PATH = "/test";
	private static final byte[] ELECT_PASSWD = "passwd".getBytes();
	private static final String ELECT_LOG = "/test";

	private CuratorFramework curator;
	private LeaderSelector leaderSelector;
	private BookKeeper bookkeeper;

	volatile boolean leader = false;
	private static List<Long> ledgers = new ArrayList<Long>();

	public LogWritter() throws Exception {
		curator = CuratorFrameworkFactory.newClient(ZOOKEEPER_SERVER, 2000, 10000,
				new ExponentialBackoffRetry(1000, 3));
		curator.start();
		curator.blockUntilConnected();

		leaderSelector = new LeaderSelector(curator, ELECTION_PATH, this);
		leaderSelector.autoRequeue();
		leaderSelector.start();

		ClientConfiguration conf = new ClientConfiguration().setZkServers(ZOOKEEPER_SERVER).setZkTimeout(30000);
		bookkeeper = new BookKeeper(conf);
	}

	public void writeEntries(String logMessage) throws Exception {

		//One LH can have multiple entries.
		//Every time createLedgerHandler is called it will create a new Ledger 
		//So reuse LH
		LedgerHandle lhandle = createLedgerHandler();
		
		//open an existing ledger
		//LedgerHandle lhandle = bookkeeper.openLedgerNoRecovery(5, BookKeeper.DigestType.MAC, ELECT_PASSWD);

		Gson gson = new Gson();
		LogDto logData = gson.fromJson(logMessage, LogDto.class);

		String message = "Date:" + logData.getDate() + "Site Id:" + logData.getSiteId() + "Partner Id :"
				+ logData.getPartnerId() + "Source File: " + logData.getSourceFileName() + "Message: "
				+ logData.getMessage();

		long entryId = lhandle.addEntry(message.getBytes());
		System.out.println(" epoch = " + lhandle.getId() + ", leading" + ", entry Id: " + entryId);
		
		
		String message1 = "Date:" + logData.getDate() + "Site Id:" + logData.getSiteId() + "Partner Id :"
				+ logData.getPartnerId() + "Source File: " + logData.getSourceFileName() + "Message: "
				+ logData.getMessage() + "hi there!!!!!";
		
		long entryId1 = lhandle.addEntry(message1.getBytes());
		System.out.println(" epoch = " + lhandle.getId() + ", leading" + ", entry Id: " + entryId1);
		
		lhandle.close();
		
	}

	private LedgerHandle createLedgerHandler() throws Exception {

		Stat stat = new Stat();
		boolean mustCreate = false;

		// get all the ledgers exists from curator
		try {
			byte[] ledgerListBytes = curator.getData().storingStatIn(stat).forPath(ELECT_LOG);
			ledgers = Utils.listFromBytes(ledgerListBytes);
		} catch (KeeperException.NoNodeException nne) {
			// no ledger exists so we have to create one
			mustCreate = true;
		}

		//Create a new ledger
		LedgerHandle lh = bookkeeper.createLedger(3, 3, 2, BookKeeper.DigestType.MAC, ELECT_PASSWD);
				
		ledgers.add(lh.getId());
		byte[] ledgerListBytes = Utils.listToBytes(ledgers);

		if (mustCreate) {
			try {
				curator.create().forPath(ELECT_LOG, ledgerListBytes);
			} catch (KeeperException.NodeExistsException nne) {
				nne.printStackTrace();
			}
		} else {
			try {
				curator.setData().withVersion(stat.getVersion()).forPath(ELECT_LOG, ledgerListBytes);
			} catch (KeeperException.BadVersionException bve) {
				bve.printStackTrace();
			}
		}
		System.out.println("ledger ID:" + lh.getId());
		return lh;
	}

	public void takeLeadership(CuratorFramework client) throws Exception {
		synchronized (this) {
			System.out.println("Becoming leader");
			leader = true;
			try {
				while (true) {
					this.wait();
				}
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
				leader = false;
			}
		}
	}

	public void close() {
		leaderSelector.close();
		curator.close();
	}

}
