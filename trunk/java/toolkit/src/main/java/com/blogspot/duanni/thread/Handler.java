package com.blogspot.duanni.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Arrays;

public class Handler implements Runnable {
	private Socket socket;

	public Handler(Socket socket) {
		this.socket = socket;
	}

	private OutputStream getWriter(Socket socket) throws IOException {
		return socket.getOutputStream();
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}


	public void run() {
		try {
			System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
			BufferedReader br = getReader(socket);
			OutputStream out = getWriter(socket);
			String msg = null;
			while ((msg = br.readLine()) != null) {
				byte[] bMsg = msg.getBytes();
				System.out.println("length:" + bMsg.length + "\nbyte:\n" + Arrays.toString(bMsg));
				msg = new String(Arrays.copyOfRange(bMsg, 16, bMsg.length));
				System.out.println("content:\n" + msg);

				byte[] hByte = "你好".getBytes();
				byte[] rMsg = new byte[hByte.length + 16];
				System.arraycopy(hByte, 0, rMsg, 16, hByte.length);
				System.out.println("resp length:" + rMsg.length + " resp body:" + Arrays.toString(rMsg));
				System.out.println(Charset.defaultCharset().name());
				out.write(rMsg);
				out.flush();
				if (msg.equals("bye"))
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				System.out.println("socket close.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
