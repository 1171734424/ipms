package com.ideassoft.util;

import java.util.HashSet;
import java.util.Set;

public class IPv4Matcher {
	private class IPRegExp {
		private String reg;

		private Long address = 0L;

		private Long mask = 0L;

		public IPRegExp() {
		};

		public Long IPStringToInteger(String ip) {
			if (ip == null)
				return null;
			String[] ss = ip.split("[.]");
			if (ss.length != 4)
				return null;
			try {
				Long ret = new Long(Long.parseLong(ss[0])
						* Math.round(Math.pow(2, 24)) + Long.parseLong(ss[1])
						* Math.round(Math.pow(2, 16)) + Long.parseLong(ss[2])
						* Math.round(Math.pow(2, 8)) + Long.parseLong(ss[3]));
				return ret;
			} catch (Exception e) {
				return 0L;
			}
		}

		public IPRegExp parse(String rep) {
			this.reg = rep;
			if (rep == null)
				return null;
			String str = rep.trim();
			if (str.equals(""))
				return null;

			int p = str.indexOf('/');
			if (p > -1) {
				String addr = str.substring(0, p);
				String mask = str.substring(p + 1);
				this.address = IPStringToInteger(addr);
				this.mask = Long.valueOf(mask);

				long lmask = 0;
				for (long i = 31; i > (32 - this.mask - 1); i--) {
					lmask = lmask + Math.round(Math.pow(2, i));
				}

				this.mask = lmask;
			} else {
				this.address = IPStringToInteger(str);
				this.mask = new Long(0xffffffff);
			}
			if (this.address == null || this.mask == null)
				return null;

			this.address = this.address & this.mask;
			return this;
		}

		public boolean match(Long l) {
			if (l == null)
				return false;
			long laddress = l & this.mask;
			return this.address.equals(laddress);
		}

		public int hashCode() {
			return address.hashCode() * 198209 + mask.hashCode();
		}

		public boolean equals(Object o) {
			if (!(o instanceof IPRegExp))
				return false;
			IPRegExp rhs = (IPRegExp) o;
			return address.equals(rhs.address) && mask.equals(rhs.mask);
		}

		public String toString() {
			return new StringBuffer().append("[reg=" + reg).append(",address=" + address)
				.append(",mask=" + mask).append("]").toString();
		}
	}

	private Set<IPRegExp> set = new HashSet<IPRegExp>();

	public void setIpRangeRegexp(String iprange_regexp) {
		if (iprange_regexp == null)
			return;
		String[] ips = iprange_regexp.split(",");
		for (int i = 0; i < ips.length; i++) {
			String reg = ips[i];
			if (reg != null) {
				IPRegExp ipr = new IPRegExp();
				ipr = ipr.parse(reg);
				if (ipr != null)
					set.add(ipr);
			}
		}
	}

	public Boolean MatchIp(String ip) {
		IPRegExp ipr = new IPRegExp();
		Long address = ipr.IPStringToInteger(ip);
		for (IPRegExp o : set) {
			if (o.match(address))
				return true;
		}
		return false;
	}

	public static void main(String args[]) {
		IPv4Matcher ipm = new IPv4Matcher();
		ipm.setIpRangeRegexp("202.96.128.0/25");
		// ipm.setIpRangeRegexp("61.144.56.100");
		// ipm.setIpRangeRegexp("61.144.56.101");
		// ipm.setIpRangeRegexp("192.168.0.0/16");
		// ipm.setIpRangeRegexp("61.144.56.101/30");

		System.out.println(ipm.MatchIp("202.96.128.68"));
		System.out.println(ipm.MatchIp("202.96.127.68"));
		System.out.println(ipm.MatchIp("61.144.56.102"));
		System.out.println(ipm.MatchIp("61.144.56.109"));
		System.out.println(ipm.MatchIp("61.144.56.100"));
		System.out.println(ipm.MatchIp("61.144.56.104"));
		System.out.println(ipm.MatchIp("61.144.56.103"));
		System.out.println(ipm.MatchIp("61.144.56.101"));
		System.out.println(ipm.MatchIp("61.144.56.100"));
		System.out.println(ipm.MatchIp("192.167.254.254"));
		System.out.println(ipm.MatchIp("192.168.254.254"));
		System.out.println(ipm.MatchIp("192.168.0.254"));
		System.out.println(ipm.MatchIp("192.168.555.888x"));
		System.out.println(ipm.MatchIp("0.00.0.00.0.0.0"));
	}

}
