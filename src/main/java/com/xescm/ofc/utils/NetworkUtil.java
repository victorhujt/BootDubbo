package com.xescm.ofc.utils;

import java.net.InetAddress;
import java.net.ServerSocket;

public class NetworkUtil {

	public static String getLocalHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			return "";
		}
	}

	public static int getFreeSocketPort() {
		try {
			ServerSocket ss = new ServerSocket(0);
			int freePort = ss.getLocalPort();
			ss.close();
			return freePort;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String getLocalIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) {
		System.out.println(getLocalIP());
		System.out.println(getFreeSocketPort());
		System.out.println(getLocalHostName());
	}
}
