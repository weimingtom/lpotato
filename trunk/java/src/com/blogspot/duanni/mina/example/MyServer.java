package com.blogspot.duanni.mina.example;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.BasicConfigurator;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MyServer {

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8")
						//, LineDelimiter.WINDOWS.getValue(),LineDelimiter.WINDOWS.getValue()
						)));
		acceptor.setHandler(new MyIoHandler());
		acceptor.bind(new InetSocketAddress(9123));
	}
}
