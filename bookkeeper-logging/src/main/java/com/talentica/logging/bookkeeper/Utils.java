package com.talentica.logging.bookkeeper;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	static byte[] listToBytes(List<Long> ledgerIds) {
		ByteBuffer bb = ByteBuffer.allocate((Long.SIZE * ledgerIds.size()) / 8);
		for (Long l : ledgerIds) {
			bb.putLong(l);
		}
		return bb.array();
	}

	static List<Long> listFromBytes(byte[] bytes) {
		List<Long> ledgerIds = new ArrayList<Long>();
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		while (bb.remaining() > 0) {
			ledgerIds.add(bb.getLong());
		}
		return ledgerIds;
	}
}
