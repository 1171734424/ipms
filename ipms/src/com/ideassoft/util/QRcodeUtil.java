package com.ideassoft.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
// java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
 * Zxing
 *
 */
public class QRcodeUtil {
	
	/*private static String basepath = RequestUtil.getWebPath()+"\\images\\qrcode";//D:\apache-tomcat-6.0.39\webapps\ipms\images\qrcode
	private static final List<String> IMAGE_TYPE = new ArrayList<String>();//二维码图片格式
	
	static{
		IMAGE_TYPE.add("png");
		IMAGE_TYPE.add("jpg");
		IMAGE_TYPE.add("gif");
	}
	
	*//**
	 * 生成二维码
	 * @param width 宽
	 * @param height 高
	 * @param content 二维码内容
	 * @param name 图片名
	 *  @param format 格式,入jpg,png,gif
	 * @return boolean 
	 *//*
	public static String createQRCode(int width,int height,String content,String name,String format){
		
		if(format==null){
			format="jpg";
		}
		if(IMAGE_TYPE.contains(format)){
			
			//定义二维码参数
			HashMap<EncodeHintType, Comparable<?>> hints = new HashMap<EncodeHintType, Comparable<?>>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.M);
			hints.put(EncodeHintType.MARGIN,2);	
			String path=basepath+"\\"+name+"."+format;
			try {
				File files = new File(path);
				if(!files.exists()){
					files.mkdirs();
				}
				BitMatrix bm = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);		
				Path file = new File(path).toPath();
					
				MatrixToImageWriter.writeToPath(bm, format, file);
				return name+"."+format;
			} catch (Exception e) {
				
				e.printStackTrace();
				return e.getMessage();
			}	
		}
		return null;
			
	}
	
	public  String createQRCode(int width,int height,String content,String name){
		return createQRCode( width, height, content, name,null);
	}
	
	
	
	*//**
	 * 添加logo图片,路径默认
	 * @param qrPic 原图片路径
	 * @param logoPic logo图片路径
	 * @param name 二维码名称
	 * @param format 二维码格式
	 * @return
	 *//*
	public static boolean addQRCodeLogo(String originalPicName ,File logoPic,String NewPicName,String format){
		
		String orlPicPath = basepath + "\\" + originalPicName;
		String newpicPath = basepath + "\\" + NewPicName + "." +format;
		if(!IMAGE_TYPE.contains(format)){
			return false;
		}
		File qrPic = new File(orlPicPath);
		if(!qrPic.isFile() && !logoPic.isFile()){
			return false;
		}
		
		try {
			
			 * 读取二维码图片,构建绘图对象画笔
			 
			BufferedImage qrimg = ImageIO.read(qrPic);
			Graphics2D g2d = qrimg.createGraphics();
			
			 * 读取logo图片
			 
			BufferedImage logoimg = ImageIO.read(logoPic);
			
			
			 * 设置logo图片大小,最大20%
			 
			int logoPicWidth = logoimg.getWidth(null) > qrimg.getWidth()*2/10 ? (qrimg.getWidth()*2/10) : logoimg.getWidth(null);
			int logoPicHeight = logoimg.getHeight(null) > qrimg.getHeight()*2/10 ? (qrimg.getHeight()*2/10) : logoimg.getHeight(null);
		
			
			 * 计算图片坐标
			 
			int x = (qrimg.getWidth()-logoPicWidth) / 2;
			int y = (qrimg.getHeight()-logoPicHeight) / 2;
			
			 * 绘制
			 
			g2d.drawImage(logoimg, x, y, logoPicWidth, logoPicHeight, null);
			g2d.drawRoundRect(x, y, logoPicWidth, logoPicHeight, 15, 15);
			g2d.setStroke(new BasicStroke(2));//边框宽度
			g2d.setColor(Color.WHITE);//边框颜色
			g2d.drawRect(x, y, logoPicWidth, logoPicHeight);
			g2d.dispose();
			logoimg.flush();
			qrimg.flush();
			File newFile = new File(newpicPath);
//			System.out.println(newFile);
			if(!newFile.exists()){
				newFile.mkdirs();
			}
			ImageIO.write(qrimg, format, newFile);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	*//**
	 * 获取二维码信息
	 * @param path二维码名称
	 * @return
	 *//*
	public static String getQRCode(String codeName,String format){
		
		String path = basepath + "\\" + codeName + "." + format;
		if(format == null){
			format = "jpg";
		}
		if(IMAGE_TYPE.contains(format)){
			try {
				MultiFormatReader mfr = new MultiFormatReader();
				File file = new File(path);
				BufferedImage image = ImageIO.read(file);
				int width = image.getWidth();
				int height = image.getHeight();
				int[] pixels = new int[width*height];
//				RGBLuminanceSource source = new RGBLuminanceSource(width, height,pixels);
				BinaryBitmap binarybitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
				//二维码参数
				HashMap<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
				hints.put(DecodeHintType.CHARACTER_SET, "utf-8");//定义字符集
				hints.put(DecodeHintType.PURE_BARCODE,Boolean.TRUE);//复杂模式,logo
				hints.put(DecodeHintType.TRY_HARDER,Boolean.TRUE);//精度
//				hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.);
				Result result = mfr.decode(binarybitmap,hints);//解析结果
				return result.toString();
			} catch (NotFoundException e) {
				e.printStackTrace();
				return e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				return e.getMessage();
			}
			
		}else{
			return "请选择jpg,png,gif类型图片";
		}
		
		
	}
	
	public static String getQRCode(String codeName){
		return getQRCode(codeName, null);
	}
	
	*//**
	 * @param args
	 *//*
	public static void main(String[] args) {
		
		String name = "二维码";
		int width = 300;//宽
		int height = 300;//高
		String format = "jpg";//图片格式
		String content = "https://m.baidu.com/";//内容
		String codeName = QRcodeUtil.createQRCode(width, height, content, name, format);
		File file = new File("D:/zxing/TIM截图20170929164909.jpg");//logo图片路径
		addQRCodeLogo(codeName, file, "newcode", "png");
		System.out.println(basepath);
	}
*/
}
