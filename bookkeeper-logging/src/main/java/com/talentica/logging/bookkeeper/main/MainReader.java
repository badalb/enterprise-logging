package com.talentica.logging.bookkeeper.main;

import com.talentica.logging.bookkeeper.EntryId;
import com.talentica.logging.bookkeeper.LogReader;

public class MainReader {

	public static void main(String[] args) {
		try {
			LogReader reader = new LogReader();
			EntryId skipPast = new EntryId(-1, -1);
			reader.readEntries(skipPast);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
