package com.test.aeronexample;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.agrona.concurrent.BackoffIdleStrategy;
import org.agrona.concurrent.IdleStrategy;
import org.agrona.concurrent.SigInt;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.google.gson.Gson;
import com.test.dto.LogDto;

import io.aeron.Aeron;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;

public class Subscriber {

	private static final Logger logger = LogManager.getLogger(Subscriber.class.getName());

	public static void main(final String[] args) throws Exception {
		// Maximum number of message fragments to receive during a single 'poll'
		// operation
		final int fragmentLimitCount = 1000000000;

		// The channel (an endpoint identifier) to receive messages from
		final String channel = "udp://localhost:40123";

		// A unique identifier for a stream within a channel. Stream ID 0 is
		// reserved
		// for internal use and should not be used by applications.
		final int streamId = 10;

		System.out.println("Subscribing to " + channel + " on stream Id " + streamId);

		final AtomicBoolean running = new AtomicBoolean(true);
		// Register a SIGINT handler for graceful shutdown.
		SigInt.register(() -> running.set(false));

		// dataHandler method is called for every new datagram received
		final FragmentHandler fragmentHandler = (buffer, offset, length, header) -> {
			final byte[] data = new byte[length];
			buffer.getBytes(offset, data);

			System.out.println(String.format(
					"Received message (%s) to stream %d from session %x term id %x term offset %d (%d@%d)",
					new String(data), streamId, header.sessionId(), header.termId(), header.termOffset(), length,
					offset));
			String jsonData = new String(data);
			Gson gson = new Gson();
			LogDto logDto = gson.fromJson(jsonData, LogDto.class);

			String fileName = "profilling_" + logDto.getSiteId() + "_" + logDto.getFileName();
			ThreadContext.put("logFileName", fileName);
			String message = "Date:" + logDto.getDate() + "Site Id:" + logDto.getSiteId() + "Partner Id :"
					+ logDto.getPartnerId() + logDto.getMessage();
			logger.log(getLogLevel(logDto.getLoglevel()), message);
			ThreadContext.remove("logFileName");  

			running.set(true);
		};

		final Aeron.Context ctx = new Aeron.Context();
		try (final Aeron aeron = Aeron.connect(ctx);
				final Subscription subscription = aeron.addSubscription(channel, streamId)) {
			final IdleStrategy idleStrategy = new BackoffIdleStrategy(1000000, 100000000, TimeUnit.MICROSECONDS.toNanos(1),
					TimeUnit.MICROSECONDS.toNanos(100));
			while (running.get()) {
				final int fragmentsRead = subscription.poll(fragmentHandler, fragmentLimitCount);
				idleStrategy.idle(fragmentsRead);
			}

			System.out.println("Shutting down...");
		}
	}

	private static Level getLogLevel(String logLevel) {
		return Level.getLevel(logLevel);
	}

}
