package com.iqmkj.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * logback自定义获取主机名
 * @author whx
 *
 */
public class LogbackCustomName {
 //测试提交
	public String getPropertyValue() {
		//获取主机名 linux多网卡无法根据环境指定具体网卡
		InetAddress netAddress = getInetAddress();
		if (null == netAddress) {
			return null;
		}
		
		return getHostName(netAddress); 
	}

	public static InetAddress getInetAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getHostName(InetAddress netAddress) {
		if (null == netAddress) {
			return null;
		}
		
		return netAddress.getHostName(); 
	}
	
}
