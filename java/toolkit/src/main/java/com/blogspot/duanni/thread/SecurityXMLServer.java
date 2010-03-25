package com.blogspot.duanni.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * flash安全限制配置.监听1843端口.输入编码UTF-8,输出编码UTF-8.
 * 
 * @version $1.0, 2009-8-10 2009-8-10 GMT+08:00
 * @since JDK1.5
 */
public class SecurityXMLServer implements Runnable {
	private ServerSocket server;
	private int port = 1843;
	/**
	 * 注意此处xml文件的内容，为纯字符串，没有xml文档的版本号
	 */
	private static final String CROSS_XML = "<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\"/></cross-domain-policy>\0";

	public SecurityXMLServer() {
		System.out.println(CROSS_XML);
		//启动843端口
		createServerSocket(port);
		new Thread(this).start();
	}

	//启动服务器
	private void createServerSocket(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("服务监听端口：" + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	//启动服务器线程
	public void run() {
		while (true) {
			Socket client = null;
			try {
				//接收客户端的连接
				client = server.accept();
				InputStream input = client.getInputStream();
				OutputStream out = client.getOutputStream();

				//读取客户端发送的数据
				StringBuilder data = new StringBuilder();
				int c = 0;
				while ((c = input.read()) != -1) {
					if (c != '\0')
						data.append((char) c);
					else
						break;
				}
				String info = data.toString();
				System.out.println("输入的请求: " + info);
				if (info.indexOf("<policy-file-request/>") >= 0) {
					//接收到客户端的请求之后，将策略文件发送出去
					out.write(CROSS_XML.getBytes("UTF-8"));
					out.flush();
					System.out.println("将安全策略文件发送至: " + client.getInetAddress());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					client.close();
					System.out.println("xml service close");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//测试主函数
	public static void main(String[] args) {
		new SecurityXMLServer();
	}
}