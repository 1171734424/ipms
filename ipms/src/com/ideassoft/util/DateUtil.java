package com.ideassoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DateUtil {
	
	private static HashMap<String, SimpleDateFormat> dfMap = new HashMap<String, SimpleDateFormat>();

	private static SimpleDateFormat getDf(String pattern) {
		SimpleDateFormat df = dfMap.get(pattern);
		if (df == null) {
			df = new SimpleDateFormat(pattern);
			dfMap.put(pattern, df);
		}

		return df;
	}

	public static String d2s(Date date, String pattern) {
		SimpleDateFormat sdf = getDf(pattern);
		return sdf.format(date);
	}

	public static String d2s(Date date) {
		SimpleDateFormat sdf = getDf("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String t2s(Date date) {
		SimpleDateFormat sdf = getDf("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String utilDate2Str(java.util.Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
		return df.format(date);

	}

	public static String currentDate() {
		return currentDate("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String currentDate(String pattern) {
		return utilDate2Str(new java.util.Date(), pattern);
	}
	
	public static String nextDate(String pattern) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		return utilDate2Str(date, pattern);
	}
	
	public static Date s2d(String strdate, String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
		Date date = null;
		try {
			 date = df.parse(strdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 判断日期 day1 和day2是否是同一天
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static boolean isSameDay(Calendar day1, Calendar day2){
		if(day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR)){
			return day1.get(Calendar.DAY_OF_YEAR) == day2.get(Calendar.DAY_OF_YEAR)?true:false;			
		}else{
			return false;
		}
	}
	
	/**
	 * 比较日期的天数，不考虑小时，只考虑天数
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public static int daysOfTwo(Date sDate, Date eDate){
		Calendar startcalendar = Calendar.getInstance();
		Calendar endcalendar = Calendar.getInstance();
		startcalendar.setTime(sDate);
		endcalendar.setTime(eDate);
		int day1 = startcalendar.get(Calendar.DAY_OF_YEAR);
		int day2 = endcalendar.get(Calendar.DAY_OF_YEAR);
		
		int year1 = startcalendar.get(Calendar.YEAR);
		int year2 = endcalendar.get(Calendar.YEAR);
		if(year1 != year2){//同一年
			
			int timeDistance = 0;
			for(int i = year1; i < year2 ; i++){
				if(i % 4 == 0 && i % 100 != 0 || i % 400 ==0){
					timeDistance += 366;
				}
				else{
					timeDistance += 365;
				}
			}
			
			return timeDistance + (day2 - day1);
		}
		else{
			return day2 - day1;
		}
	}
	
	/**
	 * 
	 * @param date 操作的日期
	 * @param days 增加的天数（可以为负数，-1表示前一天）
	 * @return
	 */
	public static Date addDays(Date date, int days){
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.DATE, days);
		return cl.getTime();
	}
	
	public static long subHour(Calendar hour1, Calendar hour2){
		long subhour = (hour1.getTimeInMillis() - hour2.getTimeInMillis())/(60*60*1000) + 1;
		return Math.abs(subhour);
	}
	
	public static void onlysetMonthYear(Calendar calendar){
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
	}
	
	public static void setEndOfDay(Calendar day) {
		day.set(Calendar.HOUR_OF_DAY, 4);
		day.set(Calendar.MINUTE,0);
		day.set(Calendar.SECOND,0);
		day.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * 判断两个时间断，是否重叠
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static boolean isOverlap(Date[] date1, Date[] date2) {
		if(date1 == null || date2 == null){
			return false;
		}
		if(date1.length != 2 || date2.length != 2){
			try {
				throw new Exception("时间段，只包括开始时间和结束时间!");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		Date leftstartDate = null;
		Date leftendDate = null;
		Date rightstartDate = null;
		Date rightendDate = null;
		if(date1[0].getTime() >= date1[1].getTime()){
			leftstartDate = date1[1];
			leftendDate = date1[0];
		}
		else {
			leftstartDate = date1[0];
			leftendDate = date1[1];
		}
		
		if(date2[0].getTime() >= date2[1].getTime()){
			rightstartDate = date2[1];
			rightendDate = date2[0];
		}
		else {
			rightstartDate = date2[0];
			rightendDate = date2[1];
		}
		
		return (
				/*(leftstartDate.getTime() >= rightstartDate.getTime()
				&& leftstartDate.getTime() < rightendDate.getTime())
			||
			(leftstartDate.getTime() > rightstartDate.getTime()
					&& leftstartDate.getTime() < rightendDate.getTime())
			||
			(rightstartDate.getTime() >= leftstartDate.getTime()
					&& rightstartDate.getTime() < leftendDate.getTime())
			||
			(rightstartDate.getTime() >= leftstartDate.getTime()
					&& rightstartDate.getTime() <= leftendDate.getTime())*/
			!(rightendDate.getTime() <= leftstartDate.getTime()
					||  rightstartDate.getTime() >= leftendDate.getTime())
			);
	}
	
	/**
	 * 根据传入的时间返回当天最后一秒的时间
	 * 
	 * @param nowTime 传入现时间
	 * @param pattern 需要的时间格式
	 * @return
	 */
	public static String getNowEndTime(String nowTime,String pattern){
		//一天的毫秒-1  
		int dayMis=1000*60*60*24;
        long curMillisecond;
        long resultMis = 0;
		try {
			 //当天的毫秒
			curMillisecond = getDf("yyyy-MM-dd").parse(nowTime).getTime();
			//当天最后一秒  
	        resultMis=curMillisecond+(dayMis-1); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 // 获取时间转换格式
		SimpleDateFormat sdf = getDf(pattern);
		return sdf.format(resultMis);
		
	}
	
}
