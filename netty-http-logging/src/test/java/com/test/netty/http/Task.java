package com.test.netty.http;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.methods.HttpAsyncMethods;

import com.google.gson.Gson;
import com.test.netty.http.LogDto;

public class Task implements Runnable {

	// private static int i =1;

	public void run() {
		LogDto dto = new LogDto();
		dto.setDate(new Date().toString());
		dto.setFileName("test_log_file");
		dto.setLoglevel("DEBUG");
		dto.setMessage("Sample test log message");
		dto.setPartnerId("p1234567890");
		dto.setSiteId("s1234567890");
		dto.setSourceFileName("Test.Java");
		String content = new Gson().toJson(dto);

		final CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		httpclient.start();
		// System.out.println("Sending Request: "+i );
		// i++;
		try {
			httpclient.execute(
					HttpAsyncMethods.createPost("http://localhost:8080", content, ContentType.APPLICATION_JSON), null,
					null).get(5000, TimeUnit.MILLISECONDS);
			httpclient.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("Done");

	}

}
