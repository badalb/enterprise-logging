package com.test.async_logging_log4j2.benchmark;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Level;
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

import com.test.asynclogging.log4j2.AsyncLogger;
import com.test.asynclogging.log4j2.LogDto;

public class AsyncLoggerBenchmarkTest {

	private static AsyncLogger asyncLogger = new AsyncLogger();

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	@Threads(5)
	@Warmup(iterations = 5)
	@Measurement(iterations = 10, batchSize = 100)
	public void benchmarkAsyncLog() {

		LogDto model = new LogDto();
		model.setFileName("test_log_file");
		model.setLoglevel(Level.DEBUG.name());
		model.setMessage("Test sample log message performance test using a queue message no");
		model.setPartnerId("test-partner-1");
		model.setSiteId("test-site-1");
		model.setSourceFileName("Test.php");
		model.setDate(new Date().toString());

		asyncLogger.log(model);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(".*" + AsyncLoggerBenchmarkTest.class.getSimpleName() + ".*").forks(1).build();
		new Runner(opt).run();
	}
}
