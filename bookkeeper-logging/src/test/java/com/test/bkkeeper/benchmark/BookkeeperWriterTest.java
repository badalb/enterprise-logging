package com.test.bkkeeper.benchmark;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
import com.talentica.logging.bookkeeper.LogWritter;
import com.talentica.logging.model.LogDto;


public class BookkeeperWriterTest {
	
	private static LogWritter logwriter;

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	@Threads(1)
	@Warmup(iterations = 2)
	@Measurement(iterations = 2, batchSize = 5)
	public void benchmarkAsyncLog() {

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
			
			getLogWriter().writeEntries(logMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static LogWritter getLogWriter(){
		if(logwriter == null){
			try {
				logwriter = new LogWritter();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return logwriter;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(".*" + BookkeeperWriterTest.class.getSimpleName() + ".*").forks(1).build();
		new Runner(opt).run();
	}
}
