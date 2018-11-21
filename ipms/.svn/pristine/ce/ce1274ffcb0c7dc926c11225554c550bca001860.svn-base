package com.ideassoft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.ideassoft.core.constants.SystemConstants;

public class RemoteTransUtil {

	private final static Logger logger = Logger.getLogger(RemoteTransUtil.class);

	public static String transData(String remotePath, Object data, Integer dataType, 
			String returnType) throws Exception {
		logger.debug("remote path [" + remotePath + "].");
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
			HttpPost httpPost = new HttpPost(remotePath);
			HttpEntity reqEntity = setEntity(data, dataType);
			httpPost.setEntity(reqEntity);
			
			CloseableHttpResponse response = httpclient.execute(httpPost);
			
			try {
				HttpEntity resEntity = response.getEntity();

				if (resEntity != null) {
					if (SystemConstants.RemoteTransReturnType.STRING == returnType) {
						result = transDataToString(resEntity.getContent());
					} else {
						result = saveTransFile(resEntity.getContent(), data.toString(), returnType);
					}
				}
				
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			httpclient.close();
		}
		
		return result;
	}
	
	private static HttpEntity setEntity(Object data, Integer dataType) throws Exception {
		MultipartEntityBuilder meb = MultipartEntityBuilder.create();
		HttpEntity reqEntity = null;
		
		if (SystemConstants.RemoteTransDataType.FILE == dataType) {
			FileBody fbody = new FileBody(new File(data.toString()));
			reqEntity = meb.addPart("file", fbody).build();
		} else if (SystemConstants.RemoteTransDataType.OBJECT == dataType) {
			data = URLEncoder.encode(JSONUtil.fromObject(data).toString(), "UTF-8");
			StringBody comment = new StringBody(data.toString(), ContentType.TEXT_PLAIN);
			reqEntity = meb.addPart("comment", comment).build();
		} else if (SystemConstants.RemoteTransDataType.STRING == dataType) {
			data = URLEncoder.encode(data.toString(), "UTF-8");
			StringBody comment = new StringBody(data.toString(), ContentType.TEXT_PLAIN);
			reqEntity = meb.addPart("comment", comment).build();
		} else if (SystemConstants.RemoteTransDataType.MIXED == dataType) {
			File file = new File(RequestUtil.getWebPath() + data.toString());
			FileBody fbody = new FileBody(file);
			StringBody path = new StringBody(data.toString(), ContentType.TEXT_PLAIN);
			reqEntity = meb.addPart("file", fbody).addPart("path", path).build();
		}
		
		return reqEntity;
	}
	
	private static String transDataToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} finally {
			is.close();
		}

		return sb.substring(0, sb.length() - 1);
	}
	
	private static String saveTransFile(InputStream is, String fileName, String returnType) throws Exception {
		if (StringUtils.isEmpty(fileName)) {
			fileName = System.currentTimeMillis() + "." + returnType;
		} else {
			fileName += "." + returnType;
		}
		
		FileOutputStream fos = null;
		
		File srcFolder = new File(RequestUtil.getWebPath() + "/download");
		if (!srcFolder.exists()) {
			srcFolder.mkdirs();
		}
		
		try {
			File tarFile = new File(srcFolder.getAbsolutePath() + "/" + fileName);
			tarFile.createNewFile();
			
			fos = new FileOutputStream(tarFile);
			
			int ln = 0;
			byte[] buf = new byte[1024];
			while ((ln = is.read(buf)) > 0) {
				fos.write(buf, 0, ln);
			}
		} finally {
			is.close();
			
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}
		
		return fileName;
	}
	
}

