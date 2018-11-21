package com.ideassoft.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CheckCodeUtil {
	
	private static String directory = System.getProperty("user.dir") + File.separator + "src" + File.separator + "com" + File.separator + "ideassoft";
	
	public static void checkFiledAndMethod(String... path) throws Exception {
		String dir = path.length > 0 ? path[0] : directory;
		File[] beans = new File(dir).listFiles();
		if (beans != null) {
			Class<?> cls = null;
			for (File bean : beans) {
				if (RegExUtil.match(".svn", bean.getName())) {
					continue;
				}
				
				if (bean.isDirectory()) {
					checkFiledAndMethod(dir + File.separator + bean.getName());
				} else {
					if (RegExUtil.match("java", bean.getName().split("[.]")[1])) {
						cls = Class.forName(dir.split("src")[1].substring(1).replace(File.separator, ".") + "." + bean.getName().split("[.]")[0]);
						
						for (Field field : cls.getDeclaredFields()) {
							if(field.getName().charAt(0) >= 65 && field.getName().charAt(0) <= 90) {
								System.out.println("incorrect field name : " + bean.getName().split("[.]")[0] + "." + field.getName());
							}
						}

						for (Method method : cls.getDeclaredMethods()) {
							if (!RegExUtil.match("main", method.getName())) {
								if(method.getName().charAt(0) >= 65 && method.getName().charAt(0) <= 90) {
									System.out.println("incorrect method name : " + bean.getName().split("[.]")[0] + "." + method.getName());
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void checkClassName(String... path) throws Exception {
		String dir = path.length > 0 ? path[0] : directory;
		File[] classes = new File(dir).listFiles();
		if (classes != null) {
			for (File clss : classes) {
				if (RegExUtil.match(".svn", clss.getName())) {
					continue;
				}
				
				if (clss.isDirectory()) {
					checkClassName(dir + File.separator + clss.getName());
				} else {
					if(clss.getName().charAt(0) >= 97 && clss.getName().charAt(0) <= 122) {
						System.out.println("incorrect file name : " + clss.getName());
					}
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		//CheckCodeUtil.checkFiledAndMethod();
		CheckCodeUtil.checkClassName();
	}
    
}

