package com.ideassoft.util;

import java.io.IOException;


public class PriceUtil {

	/**
	 * 将double截取三位小数然后见毫进分保留两位
	 * 
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static String doubleToPrice(double data){
		//return String.format("%.2f", Math.ceil(Math.floor(data*1000)/1000*100)/100);
		double thousand =  Math.floor(data*1000)/1000;
	
		double hundred =Math.floor(data*100)/100;
		
		if(thousand > hundred){
			double result =Math.ceil(data*100)/100;
			return String.format("%.2f", result);
		}else{
			return String.format("%.2f", hundred);
		}
	}
	/**
	 * 同上，但是返回值是double
	 */
	public static Double doubleToPriceDouble(double data){
		//return Math.ceil(Math.floor(data*1000)/1000*100)/100;
		double thousand =  Math.floor(data*1000)/1000;
		
		double hundred =Math.floor(data*100)/100;
		
		if(thousand > hundred){
			double result =Math.ceil(data*100)/100;
			return result;
		}else{
			return hundred;
		}
	}
}
