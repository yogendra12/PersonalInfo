package com.personal.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SampleClass {
	public static void main(String args[]) throws IOException {
		ServerSocket server = new ServerSocket(8800);
		System.out.println("Listening for connection on port 8800 ....");
		while (true) {
			try (Socket socket = server.accept()) {
				Date today = new Date();
				/*String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
				socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));*/
				System.out.println("this is socket connectio,,,,,,"+today);
			}
		}
	}

}
