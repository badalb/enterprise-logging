package com.test.aeron;

import io.aeron.Aeron;
import io.aeron.Publication;

import java.nio.ByteBuffer;

import org.agrona.concurrent.UnsafeBuffer;

public class SimplePublisher {
	public static void main(final String[] args) throws Exception {
		// Allocate enough buffer size to hold maximum message length
		// The UnsafeBuffer class is part of the Agrona library and is used for
		// efficient buffer management
		final UnsafeBuffer buffer = new UnsafeBuffer(
				ByteBuffer.allocateDirect(512));

		// The channel (an endpoint identifier) to send the message to
		final String channel = "udp://localhost:40123";

		// A unique identifier for a stream within a channel. Stream ID 0 is
		// reserved
		// for internal use and should not be used by applications.
		final int streamId = 10;

		System.out.println("Publishing to " + channel + " on stream Id "
				+ streamId);

		// Create a context, needed for client connection to media driver
		// A separate media driver process needs to be running prior to starting
		// this application
		final Aeron.Context ctx = new Aeron.Context();

		try (final Aeron aeron = Aeron.connect(ctx);
				final Publication publication = aeron.addPublication(channel,
						streamId)) {
			final String message = "Hello World! ";
			final byte[] messageBytes = message.getBytes();
			buffer.putBytes(0, messageBytes);

			// Try to publish the buffer. 'offer' is a non-blocking call.
			// If it returns less than 0, the message was not sent, and the
			// offer should be retried.
			final long result = publication.offer(buffer, 0,
					messageBytes.length);

			if (result < 0L) {
				if (result == Publication.BACK_PRESSURED) {
					System.out.println(" Offer failed due to back pressure");
				} else if (result == Publication.NOT_CONNECTED) {
					System.out
							.println(" Offer failed because publisher is not yet connected to subscriber");
				} else if (result == Publication.ADMIN_ACTION) {
					System.out
							.println("Offer failed because of an administration action in the system");
				} else if (result == Publication.CLOSED) {
					System.out.println("Offer failed publication is closed");
				} else {
					System.out.println(" Offer failed due to unknown reason");
				}
			} else {
				System.out.println(" yay !!");
			}

			System.out.println("Done sending.");
		}
	}
}
