package com.ideassoft.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	protected static MessageDigest messagedigest = null;
	
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将明文md5散列后以Base64方式序列化
	 * 
	 * @param clearText 明文字符串
	 * @return 加密后的字符串
	 */
	public static String getCryptogram(String clearText) {
		String encryptedPassword = null;
		try {
			messagedigest.update(clearText.getBytes());
			encryptedPassword = bufferToHex(messagedigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedPassword;
	}

	/**
	 * 判断明文和密文是否相符
	 * 
	 * @param clearText 明文
	 * @param cryptogram 密文
	 * @return 检查结果
	 */
	public static boolean isSame(String clearText, String cryptogram) {
		boolean flag = false;
		if (clearText == null && cryptogram == null) {
			return true;
		}
		try {
			messagedigest.update(clearText.getBytes());
			flag = bufferToHex(messagedigest.digest()).equals(cryptogram);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static void main(String[] args) throws IOException {
		messagedigest.update("123123".getBytes());
		String md5 = bufferToHex(messagedigest.digest());
		System.out.println("md5:" + md5);
		System.out.println(isSame("123", "202cb962ac59075b964b07152d234b70"));
	}

}
