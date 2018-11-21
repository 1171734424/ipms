package com.ideassoft.util;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ideassoft.core.constants.CommonConstants;



/**
 * Created by Administrator on 2016/7/11.
 */
public class HttpUtil {

    public static void printTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        String time=simpleDateFormat.format(new Date());
        System.out.println(time);
    }

    public static String postData(String data,String ip){
        URL url;
        HttpsURLConnection httpsURLConnection=null;
        OutputStream outputStream=null;
        DataOutputStream dataOutputStream=null;
        InputStream inputStream=null;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader=null;
        String result="";
        String line;
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

            url=new URL("https://" + ip + ":" + CommonConstants.ZyDoorIp.PORT + "/");
            httpsURLConnection=(HttpsURLConnection)url.openConnection();

            httpsURLConnection.setSSLSocketFactory(socketFactory);
            httpsURLConnection.setHostnameVerifier(hostnameVerifier);
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setConnectTimeout(30000);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setRequestProperty("Content-Type","text/plain");
            httpsURLConnection.connect();
            outputStream=httpsURLConnection.getOutputStream();
            dataOutputStream=new DataOutputStream(outputStream);
            //printTime();
            data=SecurityUtils.encrypt(data);
            //printTime();
            //System.out.println("加密后"+data);
            dataOutputStream.write(data.getBytes());
            inputStream=httpsURLConnection.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
            bufferedReader=new BufferedReader(inputStreamReader);
            while((line=bufferedReader.readLine())!=null){
                result+=line+"\n";
            }
            //printTime();
            //System.out.println("result"+result);
            result=SecurityUtils.decrypt(result);
            //printTime();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            if(outputStream!=null){
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream!=null){
                try{
                    inputStream.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(dataOutputStream!=null){
                try{
                    dataOutputStream.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(httpsURLConnection!=null){
                httpsURLConnection.disconnect();
            }
        }
        return null;
    }

    public static String requestCardInfo(String data,String ip,int port){
        URL url;
        HttpsURLConnection httpsURLConnection=null;
        OutputStream outputStream=null;
        DataOutputStream dataOutputStream=null;
        InputStream inputStream=null;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader=null;
        String result="";
        String line;
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

            url=new URL("https://" + ip + ":" + port);
            httpsURLConnection=(HttpsURLConnection)url.openConnection();

            httpsURLConnection.setSSLSocketFactory(socketFactory);
            httpsURLConnection.setHostnameVerifier(hostnameVerifier);
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setConnectTimeout(5000);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setRequestProperty("Content-Type","text/plain");
            httpsURLConnection.connect();
            outputStream=httpsURLConnection.getOutputStream();
            dataOutputStream=new DataOutputStream(outputStream);
            dataOutputStream.write(data.getBytes());
            inputStream=httpsURLConnection.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream,"utf-8");
            bufferedReader=new BufferedReader(inputStreamReader);
            while((line=bufferedReader.readLine())!=null){
                result+=line+"\n";
            }
            //System.out.println(result);
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            if(outputStream!=null){
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream!=null){
                try{
                    inputStream.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(dataOutputStream!=null){
                try{
                    dataOutputStream.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(httpsURLConnection!=null){
                httpsURLConnection.disconnect();
            }
        }
        return null;
    }
    
    
    /**
     * 示例
     * @param args
     */
    public static void main(String[] args) {
    	String ip = CommonConstants.ZyDoorIp.IP;
    	//String ip = "112.25.233.122";
    /*	printTime();
    	JsonObject jsonObject=new JsonObject();
    	jsonObject.addProperty("sign", 18);
		jsonObject.addProperty("ownerId","18952016312");
		jsonObject.addProperty("operatorId", "jsdr");
		jsonObject.addProperty("gatewayCode", "LGW0011805000160");
		jsonObject.addProperty("lockCode", "LCN0011805000119");
		jsonObject.addProperty("serviceNumb", "");
		jsonObject.addProperty("name", "蒋顺敏");
		jsonObject.addProperty("cardNumb", "320981199309263224");
		jsonObject.addProperty("dnCode", "");
		jsonObject.addProperty("startTime", "20180922000000");
		jsonObject.addProperty("endTime", "20180922000000");
		jsonObject.addProperty("timetag", "20180922000000");

		for(int i=1; i<=100; i++){
			String  d = postData(jsonObject.toString(), ip);
			System.out.println("添加身份证开锁授权 "+i+"--->"+d);
			printTime();
		}*/
    	//String  d = postData(jsonObject.toString(), ip);
    	
		
    	
    	
    	
    	/*//获取日志，获取开锁记录只有开始时间和结束时间查询
    	String data1 = "{ \"sign\": 26,\"ownerId\" : 18952016312,\"operatorId\" : jsdr, \"startTime\" :20170922000000, \"endTime\" :20180922000000}";
    	String b = postData(data1, ip);
    	System.out.println("获得开锁日志："+b);*/
    	
    	//获得门锁授权开锁的身份证信息
    	//{"operatorId":"mtms","ownerId":"17705155208","sign":16}
  /* 	String data2 = "{ \"sign\": 17,\"ownerId\" : \"18952016312\",\"operatorId\" : \"jsdr\", \"gatewayCode\" :\"LGW0011805000160\", \"lockCode\" :\"LCN0011805000119\"}";
    	
    	JsonObject jsonObject=new JsonObject();

    	jsonObject.addProperty("sign", 17);
		jsonObject.addProperty("ownerId","18952016312");
		jsonObject.addProperty("operatorId", "jsdr");
		jsonObject.addProperty("gatewayCode", "LGW0011805000160");
		jsonObject.addProperty("lockCode", "LCN0011805000119");
		
		//System.out.println(jsonObject.toString());
    	String  c = postData(jsonObject.toString(), ip);
    	System.out.println("获得门锁授权开锁的身份证信息："+c);*/
    	//String s = printHexString(c.getBytes());
    	//System.out.println("获得门锁授权开锁的身份证信息："+s);
    /*	try {
			String path="C:/log.txt";
			File file=new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter fileWriter=new FileWriter(path, true);
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd "+"hh:mm:ss:SSS   ");
			Date date=new Date();
			fileWriter.write(simpleDateFormat.format(date)+s+"\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
  /*  	//添加身份证开锁授权
  	String data3 = "{ \"sign\": 18,\"ownerId\" : '18952016312',\"operatorId\" : 'jsdr', \"gatewayCode\" :'LGW0011805000160', \"lockCode\" :'LCN0011805000119',"
    		+"\"serviceNumb\":,\"name\": '蒋顺敏',\"cardNumb\":'320981199309263224',\"dnCode\": , \"startTime\" :'20180922000000', \"endTime\" :'20190922000000',\"timetag\": '20180622000000' }";
   	
    	JsonObject jsonObject=new JsonObject();
    	jsonObject.addProperty("sign", 18);
		jsonObject.addProperty("ownerId","18952016312");
		jsonObject.addProperty("operatorId", "jsdr");
		jsonObject.addProperty("gatewayCode", "LGW0011805000160");
		jsonObject.addProperty("lockCode", "LCN0011805000119");
		jsonObject.addProperty("serviceNumb", "");
		jsonObject.addProperty("name", "2222");
		jsonObject.addProperty("cardNumb", "320981199309263222");
		jsonObject.addProperty("dnCode", "");
		jsonObject.addProperty("startTime", "20180922000000");
		jsonObject.addProperty("endTime", "20181022000000");
		jsonObject.addProperty("timetag", "");

    	String  d = postData(jsonObject.toString(), ip);

    	System.out.println("添加身份证开锁授权："+d);
   	//取消身份证开锁授权
*/  	/*String data4 = "{ \"sign\": 19,\"ownerId\" : 18952016312,\"operatorId\" : jsdr, \"lockCode\" :LCN0011805000119,"

    	System.out.println("添加身份证开锁授权："+d);
*/    /*	//取消身份证开锁授权
    	String data4 = "{ \"sign\": 19,\"ownerId\" : 18952016312,\"operatorId\" : jsdr, \"lockCode\" :LCN0011805000119,"

    		+"\"serviceNumb\":20180613153033189520163126538604,\"name\": 蒋顺敏,\"cardNumb\":320981199309263224,\"timetag\": }";
    	//String  e = postData(data4, ip);
    	//System.out.println("取消身份证开锁授权："+e);
    
    	JsonObject jsonObjectee=new JsonObject();
    	jsonObjectee.addProperty("sign", 19);
    	jsonObjectee.addProperty("ownerId","18952016312");
    	jsonObjectee.addProperty("operatorId", "jsdr");
    	jsonObjectee.addProperty("lockCode", "LCN0011805000119");
    	jsonObjectee.addProperty("serviceNumb", "");
    	jsonObjectee.addProperty("cardNumb", "320981199309263222");
    	jsonObjectee.addProperty("timetag", "");

    	String  d = postData(jsonObjectee.toString(), ip);
    	System.out.println("取消身份证开锁授权："+d);
    	*/
    	
    	
    	
    	/*//获得智能锁的开锁密码
    	String data5 = "{ \"sign\": 20,\"ownerId\" : 18952016312,\"operatorId\" : jsdr,\"gatewayCode\" :LGW0011805000160, \"lockCode\" :LCN0011805000119}";
    	String  f = postData(data5, ip);
    	System.out.println("获得智能锁的开锁密码："+f);*/
    /*	//添加智能锁的开锁密码
    	JsonObject jsonObjects=new JsonObject();
    	jsonObjects.addProperty("sign", 21);
    	jsonObjects.addProperty("ownerId","18084090001");
    	jsonObjects.addProperty("operatorId", "jsdr");
    	jsonObjects.addProperty("gatewayCode", "LGW0011805000125");
    	jsonObjects.addProperty("lockCode", "LCN0011805000242");
    	jsonObjects.addProperty("serviceNumb", "11111");
    	jsonObjects.addProperty("passWord", "123456");
    	jsonObjects.addProperty("startTime", "201807250000");
    	jsonObjects.addProperty("endTime", "201807252300");
    	jsonObjects.addProperty("timetag", "");
    	
    	
    	String  g = postData(jsonObjects.toString(), ip);
    	System.out.println("添加智能锁的开锁密码："+g);*/
    	//取消智能锁的开锁密码
    /*	String data7 = "{ \"sign\": 22,\"ownerId\" : 18952016312,\"operatorId\" : jsdr, \"gatewayCode\" :LGW0011805000160, \"lockCode\" :LCN0011805000119,\"serviceNumb\":,\"timetag\": 18 }";
    	JsonObject jsonObjectsa=new JsonObject();
    	jsonObjectsa.addProperty("sign", 22);
    	jsonObjectsa.addProperty("ownerId","18952016312");
    	jsonObjectsa.addProperty("operatorId", "jsdr");
    	jsonObjectsa.addProperty("gatewayCode", "LGW0011805000160");
    	jsonObjectsa.addProperty("lockCode", "LCN0011805000119");
    	jsonObjectsa.addProperty("serviceNumb", "20180613153114189520163122735151");
    	jsonObjectsa.addProperty("timetag", "");
    	
    	String  h = postData(jsonObjectsa.toString(), ip);
    	System.out.println("取消取消智能锁的开锁密码："+h);*/
    	//获得门锁动态开锁密码
    	/*String data8 = "{ \"sign\": 110,\"ownerId\" : 18952016312,\"operatorId\" : jsdr, \"gatewayCode\" :LGW0011805000160, \"lockCode\" :LCN0011805000119}";
    	String  j = postData(data8, ip);
    	System.out.println("获得门锁动态开锁密码："+j);*/
    	
    	
    	//批量删除门锁的身份证
    	/*{ 
  "sign": 40
  "operatorId" : String
  "ownerId":    String
“lockList”{
   "lockCode" :   String
   "gatewayCode" : String 
}
    	 * */
    /*	JsonArray jsonArrayList = new JsonArray();
    	JsonObject jsonObjectsasd = new JsonObject();
    	jsonObjectsasd.addProperty("lockCode", "LCN0011805000242");
    	jsonObjectsasd.addProperty("gatewayCode", "LGW0011805000125");
    	jsonArrayList.add(jsonObjectsasd);
    	
    	JsonObject jsonObjectsas=new JsonObject();
    	jsonObjectsas.addProperty("sign", 40);
    	jsonObjectsas.addProperty("ownerId","18084090001");
    	jsonObjectsas.addProperty("operatorId", "jsdr");
    	jsonObjectsas.add("lockList", jsonArrayList);
    	String  h = postData(jsonObjectsas.toString(), ip);
    	System.out.println("ssss："+h);
    	*/
    	
	//JsonArray jsonArrayList = new JsonArray();
    	/*JsonObject lockInfo = new JsonObject();
    	lockInfo.addProperty("lockCode", "LCN0011805000158");
    	lockInfo.addProperty("gatewayCode", "LGW0011805000267");
    	jsonArrayList.add(lockInfo);*/
    	
    /*	JsonObject lockInfoe = new JsonObject();
    	lockInfoe.addProperty("lockCode", "LCN0011805000242");
    	lockInfoe.addProperty("gatewayCode", "LGW0011805000125");
    	jsonArrayList.add(lockInfoe);
    	
    	JsonObject jsonObjects=new JsonObject();
    	jsonObjects.addProperty("sign", 40);
    	jsonObjects.addProperty("ownerId","18084090001");
    	jsonObjects.addProperty("operatorId", "jsdr");
    	jsonObjects.add("lockList", jsonArrayList);
		String receive=HttpUtil.postData(jsonObjects.toString(), ip);
		System.out.println(receive);*/
    	
    	/*//获取设备
    	String data = "{\"operatorId\":\"jsdr\",\"ownerId\":\"18952016312\",\"sign\":16} ";
    	String a = postData(data, ip);
    	System.out.println("获得设备信息："+a);
    	*/
    	
    	
    	
   /*	//添加
    	JsonObject jsonObjectdd=new JsonObject();
    	jsonObjectdd.addProperty("sign", 18);
    	jsonObjectdd.addProperty("ownerId","18084090001");
    	jsonObjectdd.addProperty("operatorId", "jsdr");
    	jsonObjectdd.addProperty("gatewayCode", "LGW0011805000125");
    	jsonObjectdd.addProperty("lockCode", "LCN0011805000242");
    	jsonObjectdd.addProperty("serviceNumb", "111222");
    	jsonObjectdd.addProperty("name", "jsm");
    	jsonObjectdd.addProperty("cardNumb", "320981199309262123");
    	jsonObjectdd.addProperty("dnCode", "");
    	jsonObjectdd.addProperty("startTime", "201807191652");
    	jsonObjectdd.addProperty("endTime", "201807162123");
    	jsonObjectdd.addProperty("timetag", "");

    	String  d = postData(jsonObjectdd.toString(), ip);
    	System.out.println("添加身份证开锁授权："+d);
    	*/
    	
   //取消身份证开锁授权：
  /*  JsonObject jsonObjectee=new JsonObject();
    	jsonObjectee.addProperty("sign", 19);
    	jsonObjectee.addProperty("ownerId","18084090001");
    	jsonObjectee.addProperty("operatorId", "jsdr");
    	jsonObjectee.addProperty("lockCode", "LCN0011805000242");
    	jsonObjectee.addProperty("serviceNumb", "");
    	jsonObjectee.addProperty("cardNumb", "320821199004281518");
    	jsonObjectee.addProperty("timetag", "");

    	String  d = postData(jsonObjectee.toString(), ip);
    	System.out.println("取消身份证开锁授权："+d);
    	
   */
   
		
		//获取获得门锁授权开锁的身份证信息：
    	JsonObject jsonObject=new JsonObject();

    	jsonObject.addProperty("sign", 17);
		jsonObject.addProperty("ownerId","18952016312");
		jsonObject.addProperty("operatorId", "jsdr");
		jsonObject.addProperty("gatewayCode", "LGW0011805000307");
		jsonObject.addProperty("lockCode", "LCN0011805000242");
		
    	String  c = postData(jsonObject.toString(), ip);
    	System.out.println("获得门锁授权开锁的身份证信息："+c);
    
    	
    /*	//将某身份证批量增加到门锁授权
    	JsonArray jsonArrayList = new JsonArray();
    	JsonObject lockInfo = new JsonObject();
    	lockInfo.addProperty("lockCode", "LCN0011805000242");
    	lockInfo.addProperty("gatewayCode", "LGW0011805000125");
    	JsonObject lockInfod = new JsonObject();
    	lockInfod.addProperty("lockCode", "LCN0011805000161");
    	lockInfod.addProperty("gatewayCode", "LGW0011805000319");
    	  
    	jsonArrayList.add(lockInfo);
    	//jsonArrayList.add(lockInfod);
    	JsonObject jsonObjects=new JsonObject();
    	jsonObjects.addProperty("sign", 41);
    	jsonObjects.addProperty("ownerId","18084090001");
    	jsonObjects.addProperty("operatorId", "jsdr");
    	jsonObjects.addProperty("name", "卡卡");
    	jsonObjects.addProperty("cardNumb", "320981199309262122");
    	jsonObjects.addProperty("startTime", "20180718000000");
    	jsonObjects.addProperty("endTime", "20180719000000");
    	jsonObjects.addProperty("timetag", "");
    	jsonObjects.add("lockList", jsonArrayList);
    	System.out.println(jsonObjects.toString());
    	
    	
    	String receive=HttpUtil.postData(jsonObjects.toString(), ip);
		System.out.println("某身份证批量增加到门锁授权:"+receive);
    	
		//批量获取门锁动态开锁密码
		
	/*	JsonArray jsonArrayList = new JsonArray();
    	JsonObject lockInfo = new JsonObject();
    	lockInfo.addProperty("lockCode", "LCN0011805000158");
    	lockInfo.addProperty("gatewayCode", "LGW0011805000267");
    	jsonArrayList.add(lockInfo);
    	JsonObject lockInfod = new JsonObject();
    	lockInfod.addProperty("lockCode", "LCN0011805000161");
    	lockInfod.addProperty("gatewayCode", "LGW0011805000319");
    	jsonArrayList.add(lockInfod);
    	
    	JsonObject jsonObjects=new JsonObject();
    	jsonObjects.addProperty("sign", 42);
    	jsonObjects.addProperty("ownerId","18084090001");
    	jsonObjects.addProperty("operatorId", "jsdr");
    	jsonObjects.add("lockList", jsonArrayList);
		String receive=HttpUtil.postData(jsonObjects.toString(), ip);
		System.out.println("批量获取门锁动态开锁密码:"+receive);*/

    	
    	/*
    	SimpleDateFormat formatter1  = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formatter2  = new SimpleDateFormat("yyyyMMdd");
        String birthdayreal="";
		try {
			birthdayreal = formatter1.format(formatter2.parse("20180909"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("aaaa"+birthdayreal);*/
    	
    	/*Integer remainder = (int) (Math.ceil((double)6/(double)4)%5);
		Integer merchant = (int) (Math.ceil((double)6/(double)4)/5);
		System.out.println("刚开始的值"+Math.ceil((double)6/(double)4)+"商"+merchant+"余数"+remainder);*/
		
	}

    
    public static String printHexString( byte[] b) 
	{  
		String bstr="";
		   for (int i = 0; i < b.length; i++) { 
		     String hex = Integer.toHexString(b[i] & 0xFF); 
		     if (hex.length() == 1) { 
		       hex = '0' + hex; 
		     } 
		     bstr=bstr+hex.toUpperCase();
		   } 
		return bstr;
	}
    
    public static String toBinary(String str){
      
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        return result;
    }
  
}