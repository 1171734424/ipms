/**
 */
package com.ideassoft.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Vector;

import org.apache.log4j.Logger;

public class RandomGUID {
	public String valueBeforeMD5 = "";

	public String valueAfterMD5 = "";

	private static Random myRand;

	private static SecureRandom mySecureRand;

	private final Logger logger = Logger.getLogger("RandomGUID");

	private static String s_id;

	private static final int PAD_BELOW = 0x10;

	private static final int TWO_BYTES = 0xFF;

	static {
		mySecureRand = new SecureRandom();
		long secureInitializer = mySecureRand.nextLong();
		myRand = new Random(secureInitializer);
		try {
			s_id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	public RandomGUID(boolean secure) {
		getRandomGUID(secure);
	}

	public static void main(String[] args) {
		int i = 0;
		while (i < 100) {
			new Thread(new Runnable() {

				public void run() {
					String guid = RandomGUID.getGUID();

					System.out.println("GUID:\r\n" + guid);

				}

			}).start();
			i++;
		}

	}

	public RandomGUID() {
		this(false);
	}

	public synchronized static String getGUID() {
		return new RandomGUID().toString();
	}

	public String toString() {
		String raw = valueAfterMD5.toUpperCase();
		StringBuffer sb = new StringBuffer(64);
		sb.append(raw.substring(0, 8));
		sb.append("-");
		sb.append(raw.substring(8, 12));
		sb.append("-");
		sb.append(raw.substring(12, 16));
		sb.append("-");
		sb.append(raw.substring(16, 20));
		sb.append("-");
		sb.append(raw.substring(20));

		return sb.toString();
	}

	private void getRandomGUID(boolean secure) {
		MessageDigest md5 = null;
		StringBuffer sbValueBeforeMD5 = new StringBuffer(128);

		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error: " + e);
		}

		try {
			long time = System.currentTimeMillis();
			long rand = 0;

			if (secure) {
				rand = mySecureRand.nextLong();
			} else {
				rand = myRand.nextLong();
			}
			sbValueBeforeMD5.append(s_id);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));

			valueBeforeMD5 = sbValueBeforeMD5.toString();
			md5.update(valueBeforeMD5.getBytes());

			byte[] array = md5.digest();
			StringBuffer sb = new StringBuffer(32);
			for (int j = 0; j < array.length; ++j) {
				int b = array[j] & TWO_BYTES;
				if (b < PAD_BELOW)
					sb.append('0');
				sb.append(Integer.toHexString(b));
			}

			valueAfterMD5 = sb.toString();

		} catch (Exception e) {
			logger.error("Error:" + e);
		}
	}

	static class GuidGenThread extends Thread {
		private transient boolean isStop = false;

		private Vector<String> guidPool = new Vector<String>(10);

		public GuidGenThread() {

		}

		public void run() {
			RandomGUID guid = new RandomGUID();
			try {
				while (!isStop) {
					while (guidPool.size() > 10) {
						synchronized (guidPool) {
							guidPool.wait();
						}
					}

					for (int i = 0; i < 10; i++) {
						guidPool.addElement(guid.toString());
					}
					synchronized (guidPool) {
						guidPool.notifyAll();
					}

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public String getGUID() {

			synchronized (guidPool) {
				String guid = null;
				while (guidPool.isEmpty()) {
					try {
						guidPool.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				guid = (String) guidPool.remove(0);
				if (guidPool.size() < 10) {
					guidPool.notifyAll();
				}
				return guid;

			}
		}
	}

}
