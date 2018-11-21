package com.ideassoft.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;
 
/**
 * 简易生成条形码（EN-13码）
 *
 */
 
public class Barcode13Util {
	@SuppressWarnings("unused")
	private final static String imagePath = RequestUtil.getWebPath() + File.separator + "images"+ File.separator +"JBarCode13";
    /**
     * 生成商品条形码
     *
     * @param filePath
     *            商品条形码图片存放路径：
     *
     * @param barCode
     *            商品条形码：13位
     * @param imgFormat
     *            图片格式
     *           
     * @return 图片存放路径+图片名称+图片文件类型
     */
	//单个生成
    public static String createBarCode13( String jbarCode,
            String imgFormat) {
 
        // 校验
//         if(StringUtils.isNotEmpty(savePath)){
//                return null;
//        }
         if(StringUtils.isEmpty(jbarCode)){
                return "编号不能为空";
         }
         if(StringUtils.isEmpty(imgFormat)){
                return "请输入生成条形码的格式";
         }
         if( jbarCode.length()!=13){
                return "编号不是13位";
         }
 
       
        try {
 
            BufferedImage bi = null;
 
            int len = jbarCode.length();
 
            // 实例化JBarcode
            // 这里三个参数，必要填写
            JBarcode jbarcode13 = new JBarcode(EAN13Encoder.getInstance(),
                    WidthCodedPainter.getInstance(),
                    EAN13TextPainter.getInstance());
 
            // 获取到前12位
            String barCode = jbarCode.substring(0, len - 1);
 
            // 获取到校验位
            String code = jbarCode.substring(len - 1, len);
            String checkCode = jbarcode13.calcCheckSum(barCode);
            System.out.println(checkCode);
            if (!code.equals(checkCode)) {
                return "商品   "+ jbarCode +" EN-13 条形码最后一位校验码 不对，应该是： " + checkCode;
            }

            // 尺寸，面积，大小
            jbarcode13.setXDimension(Double.valueOf(0.8).doubleValue());
            // 条形码高度
            jbarcode13.setBarHeight(Double.valueOf(60).doubleValue());
            // 宽度率
            jbarcode13.setWideRatio(Double.valueOf(15).doubleValue());
            // 是否显示最后一位校验码，默认是false
            jbarcode13.setShowCheckDigit(true);
            jbarcode13.setShowText(true);// 显示图片下字符串内容  
            jbarcode13.setCheckDigit(true);// 选择是否生成检查码  
            // 生成条形码 
            bi = jbarcode13.createBarcode(barCode);
 
           String imgPath = saveToFile(bi,imgFormat,jbarCode);
            // 返回路径
            return imgPath;
        } catch (Exception e) {
            e.printStackTrace();
           
        }
        return "条形码未成功保存！";
		
    }
    
  static String saveToFile(BufferedImage paramBufferedImage, String imgFormat, String jbarCode) throws UnsupportedEncodingException {
	  // 定义图片名称
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
      String imgName = sdf.format(new Date()) + "_" + jbarCode;

      // 保存条形码图片

      FileOutputStream fileOutputStream = null;
      //String imgPath = savePath + imgName + "." + imgFormat;
      String imgdir = URLDecoder.decode(imagePath, "utf-8");
      String imgPath = URLDecoder.decode(imagePath + File.separator + imgName + "." + imgFormat, "utf-8");
      try {
          File dirFile = new File(imgdir);
          if (!dirFile.exists()) {
              dirFile.mkdirs();
          }
          File imgFile = new File(imgPath);
          if (!imgFile.exists()) {
          	 fileOutputStream = new FileOutputStream(imgPath);
               ImageUtil.encodeAndWrite(paramBufferedImage, imgFormat, fileOutputStream, 96, 96);
          }else{
          	return "该产品条形码已存在！";
          }
      } catch (Exception e) {
          e.printStackTrace();
      }finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
      return imgPath;
        

}

  //批量生成条形码
  public static String createBarCode13( String[] jbarCodes,
          String imgFormat) {
	  String result = null;
	  for(String jbarCode : jbarCodes){
		  result =  createBarCode13(jbarCode,imgFormat);
		  
	  }
	return result;
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
  }
    /**
     * @param args
     * @throws InvalidAtributeException
     */
    public static void main(String[] args) throws InvalidAtributeException {
 
        String path = Barcode13Util.createBarCode13( "6527334511919",
                ImageUtil.JPEG);
    
        System.out.println();
 
    }
 
}