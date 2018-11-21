package com.ideassoft.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.dao.GenericDAOImpl;

public class HeartBeatClient {
	private static Logger log = Logger.getLogger(HeartBeatClient.class);
	
	private static GenericDAOImpl gdhimpl = (GenericDAOImpl) SpringUtil.getBean("genericDAOImpl");

	public static volatile boolean heartbeat = false;
	
	public static volatile boolean heartToDoorbeat = false;

	private static HeartBeatClient heartBeatClient;

	private HeartBeatClientThread heartBeatClientThreadService;
	
	private HeartBeatClientToDoorThread heartBeatClientToDoorThread;
	
	private static String remotePath;
	
	private static String remotePathToDoor;

	public static interface ObjectAction {
		void doAction(Object obj, HeartBeatClient heartBeatClient);
	}

	public static void setBeatStatus(boolean status) {
		heartbeat = status;
	}
	
	public static void setBeatToDoorStatus(boolean status) {
		heartToDoorbeat = status;
	}

	public HeartBeatClient() {
		
	}
	
	public static HeartBeatClient getInstance() {
		if (heartBeatClient == null) {
			heartBeatClient = new HeartBeatClient();
		}
		return heartBeatClient;
	}

	public void init() {
		/*SysParam sp = (SysParam) gdhimpl.findOneByProperties(SysParam.class, "paramType", "IPMSADDRESS");
		remotePath = sp.getContent();
		if(heartBeatClientThreadService == null || !heartBeatClientThreadService.isAlive()) {
			heartBeatClientThreadService = new HeartBeatClientThread();
			heartBeatClientThreadService.start();
		}*/
		
		/*remotePathToDoor = "https://lock.qixutech.com";
		if(heartBeatClientToDoorThread == null || !heartBeatClientToDoorThread.isAlive()) {
			heartBeatClientToDoorThread = new HeartBeatClientToDoorThread();
			heartBeatClientToDoorThread.start();
		}*/
	}

	class HeartBeatClientThread extends Thread {
		public void run() {
			while(true) {
				try {
						String testUrl = remotePath + "/reciveNet.do";
						URL url = new URL(testUrl);
						HttpURLConnection connect = (HttpURLConnection) url.openConnection();
						//connect.setDoInput(true);
						//connect.setConnectTimeout(30000);
						//connect.setReadTimeout(30000);
						if (connect.getResponseCode() == 200) {
							setBeatStatus(true);
						} else {
							setBeatStatus(false);
						}
						
					} catch (IOException e) {
						log.error("heart visit exception");
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						log.error("heart visit exception");
					}
			}
		}

	}
	
	class HeartBeatClientToDoorThread extends Thread {
		public void run() {
			while(true) {
				try {
						
						X509TrustManager xtm = new X509TrustManager() {
			                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			                public X509Certificate[] getAcceptedIssuers() {
			                    return null;
			                }
			            };
			            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
			                public boolean verify(String arg0, SSLSession arg1) {
			                    return true;
			                }
			            };
			            SSLContext ctx = SSLContext.getInstance("TLS");
			            ctx.init(null, new TrustManager[] { xtm }, null);
			            SSLSocketFactory socketFactory =ctx.getSocketFactory();
					
						String testUrl = "https://" + CommonConstants.ZyDoorIp.IP + ":"+CommonConstants.ZyDoorIp.PORT + "/";
						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("sign", 16);
						jsonObject.addProperty("ownerId", CommonConstants.doorInterfaceParam.OWNERID);
						jsonObject.addProperty("operatorId", CommonConstants.doorInterfaceParam.OPERATORID);
						String data = jsonObject.toString();
						URL url = new URL(testUrl);
						HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
						connect.setSSLSocketFactory(socketFactory);
						connect.setHostnameVerifier(hostnameVerifier);
						connect.setRequestMethod("POST");
						connect.setConnectTimeout(30000);
						connect.setDoOutput(true);
						connect.setDoInput(true);
						connect.setRequestProperty("Content-Type","text/plain");
						
						OutputStream outputStream=connect.getOutputStream();
						DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
				         
				            data=SecurityUtils.encrypt(data);
				            dataOutputStream.write(data.getBytes());
					
						if (connect.getResponseCode() == 200) {
							setBeatToDoorStatus(true);
						} else {
							setBeatToDoorStatus(false);
							//记日志
							SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
							com.ideassoft.bean.WarningLog warningLog = new com.ideassoft.bean.WarningLog();
							warningLog.setLogId(sdf.format(new Date()) + gdhimpl.getSequence("SEQ_WARNINGLOG_ID"));
							warningLog.setRecordTime(new Date());
							warningLog.setRecordUser(SystemConstants.User.ADMIN_ID);
							warningLog.setWarningDate(new Date());
							warningLog.setStatus(CommonConstants.servieLogStatus.EFFECTIVE);
							warningLog.setWarningType(CommonConstants.warningLogStatus.SYSTEM_LOST);
							warningLog.setRemark("与门锁平台失联");
							warningLog.setSerialNo("lockno");
							gdhimpl.save(warningLog);
						}
						
					} catch (IOException e) {
						log.error("heart To Door visit exception");
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (KeyManagementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e1) {
						log.error("heart visit exception");
					}
			}
		}

	}

}
