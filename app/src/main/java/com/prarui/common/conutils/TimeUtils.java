package com.prarui.common.conutils;

import java.sql.Date;
import java.util.Calendar;


/**
 * 获取时间
 * 
 * @author Michael
 * 
 */
public class TimeUtils {

	/**
	 * 获取今天的时间，"-"分割
	 * 
	 * @return
	 */
	public static String getDate() {
		Calendar c = Calendar.getInstance();
		int year;
		int month;
		int day;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		return year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
	}

	/**
	 * 获取今天的时间，年月日
	 * 
	 * @return
	 */
	public static String getDateChinese() {
		Calendar c = Calendar.getInstance();
		int year;
		int month;
		int day;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		return year + "年" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "月" + (day < 10 ? "0" + day : day)
				+ "日";
	}

	/**
	 * 得到本周周一
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		// 一周第一天是否为星期天
		boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
		// 获取周几
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		// 若一周第一天为星期天，则-1
		if (isFirstSunday) {
			weekDay = weekDay - 1;
			if (weekDay == 0) {
				weekDay = 7;
			}
		}
		int weekend = 1 - weekDay;
		c.add(Calendar.DATE, weekend);
		int year;
		int month;
		int day;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		return year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
	}

	/**
	 * 得到本周周一,年月日
	 * 
	 */
	public static String getMondayOfThisWeekChinese() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		int year;
		int month;
		int day;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		return year + "年" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "月" + (day < 10 ? "0" + day : day)
				+ "日";
	}

	/**
	 * 得到本周周日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		// 一周第一天是否为星期天
		boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
		// 获取周几
		int weekDay = c.get(Calendar.DAY_OF_WEEK);
		// 若一周第一天为星期天，则-1
		if (isFirstSunday) {
			weekDay = weekDay - 1;
			if (weekDay == 0) {
				weekDay = 7;
			}
		}
		int weekend = 7 - weekDay;
		c.add(Calendar.DATE, weekend);
		// 打印周几
		System.out.println(weekDay);
		int year;
		int month;
		int day;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		return year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
	}

	/**
	 * 得到本周周日，年月日
	 * 
	 */
	public static String getSundayOfThisWeekChinese() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		int year;
		int month;
		int day;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		return year + "年" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "月" + (day < 10 ? "0" + day : day)
				+ "日";
	}

	/**
	 * 获取当前一周的开头和结尾
	 * 
	 * @return
	 */
	public static String getThisWeek() {
		return getMondayOfThisWeek() + "-" + getSundayOfThisWeek();
	}

	/**
	 * 获取当前一周的开头和结尾
	 * 
	 * @return
	 */
	public static String getThisWeekChinese() {
		return getMondayOfThisWeekChinese() + "-" + getSundayOfThisWeekChinese();
	}

	/**
	 * 获取当前月
	 * 
	 * @return
	 */
	public static String getThisMonth() {
		Calendar c = Calendar.getInstance();
		int year;
		int month;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		return year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1));
	}

	/**
	 * 获取当前月 年月日
	 * 
	 * @return
	 */
	public static String getThisMonthChinese() {
		Calendar c = Calendar.getInstance();
		int year;
		int month;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		return year + "年" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "月";
	}

	/**
	 * 传入一天的值，获取昨天YYYY-MM-DD
	 * 
	 * @param today
	 * @return
	 */
	public static String getYestoday(String today) {
		long time = DateUtils.getStringToDateSmall(today);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		c.add(Calendar.DAY_OF_YEAR, -1);
		return DateUtils.getDateToStringSmall(c.getTime().getTime());
	}

	/**
	 * 传入一天的值，获取昨天年月日
	 * 
	 * @param today
	 * @return
	 */
	public static String getYestodayChina(String today) {
		long time = DateUtils.getStringToDateSmall(today);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		c.add(Calendar.DAY_OF_YEAR, -1);
		return DateUtils.getDateToStringChina(c.getTime().getTime());
	}

	/**
	 * 传入一天的值，获取明天YYYY-MM-DD
	 * 
	 * @param today
	 * @return
	 */
	public static String getTomorrow(String today) {
		long time = DateUtils.getStringToDateSmall(today);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		c.add(Calendar.DAY_OF_YEAR, 1);
		return DateUtils.getDateToStringSmall(c.getTime().getTime());
	}

	/**
	 * 传入一天的值，获取前一周的任意一天YYYY-MM-DD
	 * 
	 * @param today
	 * @return
	 */
	public static String getLastWeekAnyday(String today) {
		long time = DateUtils.getStringToDateSmall(today);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		c.add(Calendar.WEEK_OF_YEAR, -1);
		return DateUtils.getDateToStringSmall(c.getTime().getTime());
	}

	/**
	 * 传入一天的值，获取前一月的任意一天YYYY-MM-DD
	 * 
	 * @param today
	 * @return
	 */
	public static String getLastMonthAnyday(String today) {
		long time = DateUtils.getStringToDateSmall(today);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		c.add(Calendar.MONTH, -1);
		return DateUtils.getDateToStringSmall(c.getTime().getTime());
	}

	/**
	 * 判断是否时间前后
	 * 
	 * @param big
	 * @param small
	 * @return
	 */
	public static boolean isBigger(String big, String small) {
		long bigTime = DateUtils.getStringToDateSmall(big);
		long smallTime = DateUtils.getStringToDateSmall(small);
		return bigTime > smallTime;
	}

	/**
	 * 判断是否时间前后
	 * 
	 * @param big
	 * @param small
	 * @return
	 */
	public static boolean isBiggerChina(String big, String small) {
		long bigTime = DateUtils.getStringToDateSmallChina(big);
		long smallTime = DateUtils.getStringToDateSmallChina(small);
		return bigTime > smallTime;
	}

	/**
	 * 传入一天的值，90天以前YYYY-MM-DD
	 * 
	 * @param today
	 * @return
	 */
	public static String get90Older(String today) {
		long time = DateUtils.getStringToDateSmall(today);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		c.add(Calendar.DAY_OF_YEAR, -90);
		return DateUtils.getDateToStringSmall(c.getTime().getTime());
	}

	/**
	 * 判断是否在90天以前
	 * 
	 * @return
	 */
	public static boolean is90Older(String selectTime) {
		return isBigger(get90Older(getDate()), selectTime);
	}

	/**
	 * 获得当前的日期：（月日）
	 */
	public static String getDateChineseWeek(int dely) {
		Calendar c = Calendar.getInstance();
		int year;
		int month;
		int day;
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH) + dely;
		return ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
	}

	public static String getDateChineseDayWeek(int dely) {
		Calendar c = Calendar.getInstance();
		int week;
		week = (c.get(Calendar.DAY_OF_WEEK) + 1 + dely) % 7;
		String weekDay = null;
		switch (week) {
		case 0:
			weekDay = "五";
			break;
		case 1:
			weekDay = "六";
			break;
		case 2:
			weekDay = "天";
			break;
		case 3:
			weekDay = "一";

			break;
		case 4:

			weekDay = "二";
			break;

		case 5:
			weekDay = "三";

			break;
		case 6:
			weekDay = "四";
			break;
		default:
			break;
		}
		return "星期" + weekDay;
	}

	/**
	 * 获取当前日期是星期几
	 * @param date yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getWeek(String date) {
		long time = DateUtils.getStringToDateSmall(date);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		int week = c.get(Calendar.DAY_OF_WEEK);
		String weekDay = null;
		switch (week) {
		case 1:
			weekDay = "日";
			break;
		case 2:
			weekDay = "一";
			break;
		case 3:
			weekDay = "二";
			break;
		case 4:
			weekDay = "三";
			break;
		case 5:
			weekDay = "四";
			break;
		case 6:
			weekDay = "五";
			break;
		case 7:
			weekDay = "六";
			break;
		}
		return "星期" + weekDay;
	}

}
