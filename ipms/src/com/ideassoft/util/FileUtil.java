package com.ideassoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {
	
//	private static Logger logger = Logger.getLogger(FileUtil.class);
	
	/**
	 * 使用文件通道拷贝文件
	 * @param srcFilePath
	 * @param targetFilePath
	 * @return
	 */
	public static void copyFile(String srcFilePath, String targetFilePath) throws IOException {
		File srcFile = new File(srcFilePath);
		if (srcFile.exists()) {
			String fileName = srcFile.getName();
			
			File targetFileDir = new File(targetFilePath);
			//目标路径不存在则创建之
			if (!targetFileDir.exists()) {
				targetFileDir.mkdirs();
			}
			
			String targetFileFullPath = targetFilePath + (targetFilePath.endsWith("/") ? fileName : "/" + fileName);
			File targetFile = new File(targetFileFullPath);
			
			FileInputStream fis = null;
	        FileOutputStream fos = null;
	        FileChannel inc = null;
	        FileChannel outc = null;

	        try {
	            fis = new FileInputStream(srcFile);
	            fos = new FileOutputStream(targetFile);
	            inc = fis.getChannel();
	            outc = fos.getChannel();
	            //打开文件通道传输文件
	            inc.transferTo(0, inc.size(), outc);
	        } finally {
	            fis.close();
	            inc.close();
	            fos.close();
	            outc.close();
	        }
		}
	}
	
	/***
	 * @param filePath 文件路径
	 * @param writeInfo 写入的值
	 * @param concat 是否追加内容
	 */
	public static void writeFile(String filePath, String writeInfo, boolean concat) {
		File ist = new File(filePath);
		FileWriter fw = null;
		try {
			if (!ist.exists()) {
				ist.createNewFile();
			}
			
			fw = new FileWriter(ist, concat);
			fw.write(writeInfo);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.flush();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					fw = null;
				}
			}
		}
	}
	
}
