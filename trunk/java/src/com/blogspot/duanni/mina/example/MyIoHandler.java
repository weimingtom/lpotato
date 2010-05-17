package com.blogspot.duanni.mina.example;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyIoHandler extends IoHandlerAdapter {

	public final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
		log.info("The message received is [{}]", str);
		if (str.endsWith("quit")) {
			session.close(true);
			return;
		}
	}

}
