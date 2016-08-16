package com.talentica.logging.bookkeeper;

import java.io.Closeable;
import java.util.Enumeration;
import java.util.List;

import org.apache.bookkeeper.client.BKException;
import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.LedgerEntry;
import org.apache.bookkeeper.client.LedgerHandle;
import org.apache.bookkeeper.conf.ClientConfiguration;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

public class LogReader extends LeaderSelectorListenerAdapter implements Closeable {

	private static final String ZOOKEEPER_SERVER = "127.0.0.1:2181";
	private static final String ELECTION_PATH = "/test";
	private static final byte[] ELECT_PASSWD = "passwd".getBytes();
	private static final String ELECT_LOG = "/test";

	private CuratorFramework curator;
	private LeaderSelector leaderSelector;
	private BookKeeper bookkeeper;

	volatile boolean leader = false;
	

	public LogReader() throws Exception {
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


	public void readEntries(EntryId skipPast) throws Exception{
		    Stat stat = new Stat();
	        List<Long> ledgers;
	      
	        try {
	            byte[] ledgerListBytes = curator.getData()
	                .storingStatIn(stat).forPath(ELECT_LOG);
	            ledgers = Utils.listFromBytes(ledgerListBytes);
	        } catch (KeeperException.NoNodeException nne) {
	           nne.printStackTrace();
	           return;
	        }

	        List<Long> toRead = ledgers;
	        if (skipPast.getLedgerId() != -1) {
	            toRead = ledgers.subList(ledgers.indexOf(skipPast.getLedgerId()),
	                                     ledgers.size());
	        }

	      //  long nextEntry = skipPast.getEntryId() + 1;
	        for (Long previous : toRead) {
	            LedgerHandle lh;
	            try {
	                lh = bookkeeper.openLedger(previous,
	                        BookKeeper.DigestType.MAC, ELECT_PASSWD);
	            } catch (BKException.BKLedgerRecoveryException e) {
	                e.printStackTrace();
	                return;
	            }

//	            if (nextEntry > lh.getLastAddConfirmed()) {
//	                nextEntry = 0;
//	                continue;
//	            }
	            Enumeration<LedgerEntry> entries
	                = lh.readEntries(0, lh.getLastAddConfirmed());

	            while (entries.hasMoreElements()) {
	                LedgerEntry e = entries.nextElement();
	                byte[] entryData = e.getEntry();
	                System.out.println("Value = " + new String(entryData)
	                                   + ", epoch = " + lh.getId()
	                                   + ", catchup");
	            }
	            lh.close();
	        }
	}
	
	
	// @Override
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

	// @Override
	public void close() {
		leaderSelector.close();
		curator.close();
	}
}
