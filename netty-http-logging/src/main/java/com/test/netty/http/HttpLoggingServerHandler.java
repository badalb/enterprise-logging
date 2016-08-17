package com.test.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.google.gson.Gson;

public class HttpLoggingServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LogManager.getLogger(HttpLoggingServerHandler.class.getName());

	private volatile HttpRequest request;
	private volatile StringBuilder buf = new StringBuilder();

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof HttpRequest) {
			HttpRequest req = (HttpRequest) msg;
			request = req;
			Counter.setCounter();
		}

		if (msg instanceof HttpContent) {
			HttpContent httpContent = (HttpContent) msg;
			ByteBuf content = httpContent.content();
			if (content.isReadable()) {
				// buf.append("CONTENT: ");
				buf.append(content.toString(CharsetUtil.UTF_8));
				buf.append("\r\n");
				appendDecoderResult(buf, request);
			}

			if (msg instanceof LastHttpContent) {
				logMessage(buf);
			}

		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	private static void appendDecoderResult(StringBuilder buf, HttpObject o) {
		DecoderResult result = o.decoderResult();
		if (result.isSuccess()) {
			return;
		}
		buf.append(".. WITH DECODER FAILURE: ");
		buf.append(result.cause());
		buf.append("\r\n");
	}

	private void logMessage(StringBuilder buf) {
		Gson converter = new Gson();
		String data = new String(buf);
		LogDto logData = converter.fromJson(data, LogDto.class);
		String fileName = "profilling_" + logData.getSiteId() + "_" + logData.getFileName();
		ThreadContext.put("logFileName", fileName);
		String message = "Date:" + logData.getDate() + "Site Id:" + logData.getSiteId() + "Partner Id :"
				+ logData.getPartnerId() + "Source File: " + logData.getSourceFileName() + "Message: "
				+ logData.getMessage();
		logger.log(getLogLevel(logData.getLoglevel()), message);
		ThreadContext.remove("logFileName");
	}

	private Level getLogLevel(String logLevel) {
		return Level.getLevel(logLevel);
	}

}