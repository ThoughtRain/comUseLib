package com.prarui.common.conutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * @author
 */
public class DateUtils {

	private static SimpleDateFormat sf = null;

	/* 获取系统时间 格式为："yyyy/MM/dd " */
	public static String getCurrentDate() {
		Date d = new Date();
		sf = new SimpleDateFormat("yyyy年MM月dd日");
		return sf.format(d);
	}

	/* 时间戳转换成字符窜 */
	public static String getDateToString(long time) {
		Date d = new Date(time);
		sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(d);
	}

	/* 时间戳转换成字符窜 */
	public static String getDateToStringSmall(long time) {
		Date d = new Date(time);
		sf = new SimpleDateFormat("yyyy/MM/dd");
		return sf.format(d);
	}

	/* 时间戳转换成字符窜 */
	public static String getDateToStringChina(long time) {
		Date d = new Date(time);
		sf = new SimpleDateFormat("yyyy年MM月dd日");
		return sf.format(d);
	}

	/* 将字符串转为时间戳 */
	public static long getStringToDate(String time) {
		sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}

	/* 将字符串转为时间戳 */
	public static long getStringToDateSmall(String time) {
		sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}

	/* 将字符串转为时间戳 */
	public static long getStringToDateSmallChina(String time) {
		sf = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}

	public static String getWeek(String dates) {
		Date date = new Date(dates);
		SimpleDateFormat strdate = new SimpleDateFormat("EEEE");
		String str = strdate.format(date);
		return str;
	}

	public static String getDayDely(String dates, int daly) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dt = null;
		try {
			dt = sdf.parse(dates);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.DAY_OF_YEAR, daly);// 日期加10天
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;

	}
}