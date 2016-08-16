package com.talentica.logging.bookkeeper.main;

import java.util.Date;

import com.google.gson.Gson;
import com.talentica.logging.bookkeeper.LogWritter;
import com.talentica.logging.model.LogDto;

public class MainWriter {

	public static void main(String[] args) {

		LogDto model = new LogDto();
		model.setFileName("test_log_file");
		model.setLoglevel("DEBUG");
		model.setMessage("Test sample log message performance test using a queue message no");
		model.setPartnerId("test-partner-1");
		model.setSiteId("test-site-1");
		model.setSourceFileName("Test.php");
		model.setDate(new Date().toString());

		String logMessage = new Gson().toJson(model);

		try {
			LogWritter writer = new LogWritter();
			writer.writeEntries(logMessage);
			writer.close();
			//System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
