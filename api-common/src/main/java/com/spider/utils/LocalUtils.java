/**
 * 
 */
package com.spider.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 10:24:36 AM Nov 25, 2013
 */
public class LocalUtils {

	private static final Log LOG = LogFactory.getLog(LocalUtils.class);
	public static final String DEFAULT_LOCAL_IP = "127.0.0.1";
	public static final String LOCAL_IP;
	static {
		LOCAL_IP = getLocalAddress();
		// 打印本地IP
		System.out.println("LOCAL_IP： " + LOCAL_IP);
	}

	/**
	 * 获取本地地址
	 * 
	 * @return
	 */
	public static String getLocalAddress() {
		try {
			// 遍历网卡，查找一个非回路ip地址并返回
			Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
			InetAddress inet6Address = null;
			while (enumeration.hasMoreElements()) {
				NetworkInterface networkInterface = enumeration.nextElement();
				Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
						if (inetAddress instanceof Inet6Address) {
							inet6Address = inetAddress;
						}
						else {
							// 优先使用ipv4
							return normalizeHostAddress(inetAddress);
						}
					}
				}
			}
			// 没有ipv4，再使用ipv6
			if (inet6Address != null) {
				return normalizeHostAddress(inet6Address);
			}
			return normalizeHostAddress(InetAddress.getLocalHost());
		}
		catch (Exception e) {
			LOG.warn("Error to get local address\r\n", e);
			return DEFAULT_LOCAL_IP;
		}
	}

	/**
	 * 获取主机地址
	 * 
	 * @param inetAddress
	 * @return
	 */
	public static String normalizeHostAddress(InetAddress inetAddress) {
		if (inetAddress instanceof Inet6Address) {
			return "[" + inetAddress.getHostAddress() + "]";
		}
		else {
			return inetAddress.getHostAddress();
		}
	}
}
