/**
 */
package com.blogspot.duanni.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.net.SimpleSocketServer;

/**
 * 
 * @author duanni
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 * @since <t-version>
 */
public class LogsServer {
	/** 监听端口 */
	private int port;
	/** logs server 配置文件 */
	private String config;
	private SimpleSocketServer sss;
	private Logger logger;

	public void init() throws Exception {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		SimpleSocketServer.configureLC(lc, config);
		sss = new SimpleSocketServer(lc, port);
		sss.start();
		logger = LoggerFactory.getLogger(getClass());
		logger.info("日志服务器启动成功.配置文件 => [{}], 监听端口 => [{}]", config, port);
	}

	public void destroy() {
		if (sss != null && !sss.isClosed()) {
			sss.close();
		}
		logger.info("日志服务器关闭.");
	}

	public void setConfig(String config) throws Exception {
		this.config = new ClassPathResource(config).getFile().getPath();
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 用于测试.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("contextServer.xml");
	}
}
