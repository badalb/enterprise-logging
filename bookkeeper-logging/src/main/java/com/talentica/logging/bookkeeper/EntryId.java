package com.talentica.logging.bookkeeper;

public class EntryId {

	final long ledgerId;
	final long entryId;

	public EntryId(long ledgerId, long entryId) {
		this.ledgerId = ledgerId;
		this.entryId = entryId;
	}

	long getLedgerId() {
		return ledgerId;
	}

	long getEntryId() {
		return entryId;
	}

}
