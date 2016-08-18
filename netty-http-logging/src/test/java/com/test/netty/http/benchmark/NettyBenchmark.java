package com.test.netty.http.benchmark;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.google.gson.Gson;
import com.test.netty.http.LogDto;

public class NettyBenchmark {

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	@Threads(1)
	@Warmup(iterations = 1)
	@Measurement(iterations = 1, batchSize = 1)
	public void benchmarkAsyncLog() {

		LogDto model = new LogDto();
		model.setFileName("test_log_file");
		model.setLoglevel("DEBUG");
		model.setMessage("Test sample log message performance test using a queue message no");
		model.setPartnerId("test-partner-1");
		model.setSiteId("test-site-1");
		model.setSourceFileName("Test.php");
		model.setDate(new Date().toString());

		String content = new Gson().toJson(model);

		final CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		httpclient.start();
		try {
			httpclient.execute(
					HttpAsyncMethods.createPost("http://localhost:8080", content, ContentType.APPLICATION_JSON), null,
					null).get(20000, TimeUnit.MILLISECONDS);
			httpclient.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("Done");

	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(".*" + NettyBenchmark.class.getSimpleName() + ".*").forks(1).build();
		new Runner(opt).run();
	}

}
