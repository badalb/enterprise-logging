package com.test.netty.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TuskRunner {

	private static final int NTHREDS = 5;

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		Task worker = new Task();
		// ExecutorService executor = Executors.newCachedThreadPool();
		System.out.println("Started all threads for worm up!!!");
		for (int i = 0; i < 10000; i++) {
			System.out.println("client thread group :" + i);
			executor.submit(worker);
			Thread.sleep(2000);
		}
		executor.shutdown();
		executor.awaitTermination(10000, TimeUnit.SECONDS);
		System.out.println("Finished all threads for worm up!!!");

		System.out.println("Started all threads for testing!!!");
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			System.out.println("client thread group :" + i);
			executor.submit(worker);
			// Thread.sleep(1);
		}
		executor.shutdown();
		executor.awaitTermination(100000, TimeUnit.SECONDS);
		long end = System.currentTimeMillis();
		System.out.println("Total THREADS:" + NTHREDS + " Total MESSAGES:" + NTHREDS * 100000 + "Total TIME:"
				+ (end - start) + "milisecond");
		System.out.println("Finished all threads for worm up!!!");

	}

}
