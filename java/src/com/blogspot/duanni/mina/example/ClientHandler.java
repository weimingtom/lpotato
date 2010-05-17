package com.blogspot.duanni.mina.example;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends IoHandlerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final String values;

	public ClientHandler(String values) {
		this.values = values;
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		session.write(values);
	}

}
