package com.ideassoft.util;

import java.util.ArrayList;
import java.util.List;


public class NumberUtil {
	
	public static List<Integer> primeNumber(int limit) {
		List<Integer> result = new ArrayList<Integer>();
		boolean continueFlag;
		for (int i = 1; i < limit; i++) {
			continueFlag = false;
			for (int j = 2; j < i; j++)
				if (j != i && i % j == 0) {
					continueFlag = true;
					break;
				}
			
			if (continueFlag) 
				continue;
			else 
				result.add(i);
		}
		return result;
	}
	
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}

		return true;
	}

	public static void main(String[] args) throws Exception {
//		for (Integer i : NumberUtil.primeNumber(180)) {
//			System.out.println(i);
//		}

	}
	
}

