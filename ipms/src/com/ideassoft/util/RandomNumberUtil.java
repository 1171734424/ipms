package com.ideassoft.util;

import java.util.Random;

public class RandomNumberUtil {

	// 生成短信验证码
	public static String verificationCode(int length) {
		Random random = new Random();
		String verificationCode = "";
		for (int i = 0; i < length; i++) {
			int num;
			if(i==0){
				do{
					num = random.nextInt(10);
				}while(num == 0);
			}else{
				num = random.nextInt(10);
			}
			verificationCode += Integer.toString(num);
		}
		return verificationCode;
	}
}
