package com.zyyapp.util.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;

/**
 * 时间运算工具类;
 */
public class TimeUtil {
	private static Logger log =  LoggerFactory.getLogger(TimeUtil.class);
	/** 一秒对应的毫秒值*/
	public static final long ONE_SECONDS_MS = 1000;
	/** 一分钟对应的毫秒值*/
	public static final long ONE_MINUTES_MS = 60 * 1000;
	/** 一小时对应的毫秒值*/
	public static final long ONE_HOUR_MS = 60 * 60 * 1000;
	/** 一天对应的毫秒值*/
	public static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;
	
	/** 一小时对应的秒值*/
	public static final long ONE_HOUR_SECONDS = 60 * 60;
	/** 一天对应的秒值*/
	public static final long ONE_DAY_SECONDS = 24 * 60 * 60;
	/** 一天对应的毫秒值*/ 
	public static final long ONE_DAY_MILLSECONDS = 24 * 60 * 60 * 1000;
	public static final float MILLSECOND_RATIO = 0.001f;

	/**
	 * @Description 获取指定时间增加特定天数的时间
	 * @param d 指定时间
	 * @param day 增加的天数
	 * @return Date
	 * @author jingxiaobo
	 * @date 2015年11月13日 下午3:19:45
	 */
	public static Date getAfterDayTime(Date d, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/***
	 * 获取当前秒
	 * 
	 * @return 格林时间秒
	 */
	public static int getSecond() {
		return (int) (System.currentTimeMillis() * MILLSECOND_RATIO);
	}

	/**
	 * 获取指定时间到现在的时间数（毫秒）
	 * 
	 * @param time
	 * @return
	 */
	public static long getDurationToNow(long time) {
		return System.currentTimeMillis() - time;
	}

	/**
	 * 获取指定时间到现在的时间数（秒）
	 * 
	 * @param time
	 * @return 秒
	 */
	public static int getDurationToNowSec(long time) {
		return (int) (getDurationToNow(time) * MILLSECOND_RATIO);
	}

	/**
	 * 判断两个时间中间所差天数
	 *
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int getBetweenDays(long time1, long time2) {
		Calendar instance1 = Calendar.getInstance();
		instance1.setTimeInMillis(time1);
		instance1.set(Calendar.HOUR_OF_DAY, 0);
		instance1.set(Calendar.MINUTE, 0);
		instance1.set(Calendar.SECOND, 0);
		instance1.set(Calendar.MILLISECOND, 0);
		Calendar instance2 = Calendar.getInstance();
		instance2.setTimeInMillis(time2);
		instance2.set(Calendar.HOUR_OF_DAY, 0);
		instance2.set(Calendar.MINUTE, 0);
		instance2.set(Calendar.SECOND, 0);
		instance2.set(Calendar.MILLISECOND, 0);
		return (int) ((instance1.getTimeInMillis() - instance2.getTimeInMillis()) / ONE_DAY_MS);
	}

	/**
	 * 判断两个时间是否在同一天
	 * 
	 * @param time
	 * @param time2
	 * @return
	 */
	public static boolean isSameDay(long time, long time2) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		int d1 = instance.get(Calendar.DAY_OF_YEAR);
		int y1 = instance.get(Calendar.YEAR);
		instance.setTimeInMillis(time2);
		int d2 = instance.get(Calendar.DAY_OF_YEAR);
		int y2 = instance.get(Calendar.YEAR);
		return d1 == d2 && y1 == y2;
	}

	/**
	 * 判断是否在今天几点之后 true为大于指定时间
	 * 
	 * @param hour
	 * @return
	 */
	public static boolean isAfterHourOfCurrentDay(int hour, long time) {
		long currentTimeMillis = System.currentTimeMillis();
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(currentTimeMillis);
		instance.set(Calendar.HOUR_OF_DAY, hour);
		instance.set(Calendar.MINUTE, 0);
		instance.set(Calendar.SECOND, 0);
		instance.set(Calendar.MILLISECOND, 0);
		long timeInMillis = instance.getTimeInMillis();
		return time - timeInMillis > 0;
	}

	/**
	 * 指定时间的年份
	 * 
	 * @param time
	 * @return
	 */
	public static int getYear(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		return instance.get(Calendar.YEAR);
	}

	/**
	 * 指定时间的月份,0-11
	 * 
	 * @param time
	 * @return
	 */
	public static int getMonth(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		return instance.get(Calendar.MONTH);
	}

	/**
	 * 获取日期(一个月内的第几天)
	 * 
	 * @param time
	 * @return
	 */
	public static int getDayOfMonth(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		return instance.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取小时
	 * 
	 * @param time
	 * @return
	 */
	public static int getDayOfHour(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		return instance.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取分钟
	 * 
	 * @param time
	 * @return
	 */
	public static int getDayOfMin(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		return instance.get(Calendar.MINUTE);
	}

	/**
	 * 获取秒
	 * 
	 * @param time
	 * @return
	 */
	public static int getDayOfSecond(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		return instance.get(Calendar.SECOND);
	}

	/**
	 * 获取指定时间 是一月内的第几周
	 * 
	 * @param time
	 * @return
	 */
	public static int getDayOfWeekInMonth(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		return instance.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}

	/**
	 * 获取星期几
	 * 
	 * @param time
	 * @return
	 */
	public static int getDayOfWeek(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		int i = instance.get(Calendar.DAY_OF_WEEK);
		return i - 1;
	}

	/**
	 * 获取指定日期的24点时间;
	 *
	 * @param date: 指定的日期date数据; 为空则指定为当前时间;
	 * @return long; 24点时刻的unix时间(毫秒)
	 */
	public static long getMillSecondsToNight(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 获取一年内的第几天
	 * 
	 * @param time
	 * @return
	 */
	public static int getDayOfYear(long time) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(time);
		return instance.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取1970至今的天数 （计数会在在每天指定的小时+1，用来判断每天X点清数据之类的）
	 * 
	 * @param hour
	 *            每天第X个小时+1
	 * @return
	 */
	public static int GetCurDay(int hour) {
		TimeZone zone = TimeZone.getDefault(); // 默认时区
		long s = System.currentTimeMillis() / ONE_SECONDS_MS + hour * ONE_HOUR_SECONDS;
		if (zone.getRawOffset() != 0) {
			s = s + zone.getRawOffset() / ONE_SECONDS_MS;
		}
		s = s / ONE_DAY_SECONDS;
		return (int) s;
	}

	/**
	 * 获取1970至今的时间, 1获取秒，2 分钟，3小时，4天数,5周数
	 * 
	 * @param x
	 * @return
	 */
	public static long GetCurTimeInMin(int x, long time) {
		TimeZone zone = TimeZone.getDefault(); // 默认时区
		long s = time / 1000;
		if (zone.getRawOffset() != 0) {
			s = s + zone.getRawOffset() / ONE_SECONDS_MS;
		}
		switch (x) {
			case 1:
			break;
			case 2:
				s = s / 60;
			break;
			case 3:
				s = s / 3600;
			break;
			case 4:
				s = s / ONE_DAY_SECONDS;
			break;
			case 5:
				s = s / ONE_DAY_SECONDS + 3;// 补足天数，星期1到7算一周
				s = s / 7;
			break;
			default:
			break;
		}
		return s;
	}

	public static long GetCurTimeInMin(int x) {
		return GetCurTimeInMin(x, System.currentTimeMillis());
	}

	/**
	 * 获取今天指定时间的UNIX时间
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public static long getTheDayUnixTime(int hour, int minute, int second, int millisecond) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal.getTimeInMillis();
	}

	/**
	 * 获取格式化的时间字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}
	
	/**
	 * @Description 获取特定时间当天0点的时间字符串表示[例如2015-11-13]
	 * @param date 特定时间
	 * @return String
	 * @author jingxiaobo
	 * @date 2015年11月13日 下午3:22:05
	 */
	public static String getDateString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(date);
	}

	/**
	 * 获取格式化的时间字符串
	 *
	 * @return
	 */
	public static long millisecondDate(String timeStr, String formatStr) {
		try {
			return new SimpleDateFormat(formatStr).parse(timeStr).getTime();
		} catch (ParseException e) {
			log.error("{}日期格式有误{}" + timeStr + " 例: yyyy-MM-dd HH:mm:ss");
			return 0;
		}
	}
	
	/**
	 * 获取格式化的时间字符串
	 *
	 * @return
	 */
	public static int daySecond(long t) {
		try {
			return (int)(new SimpleDateFormat("HH:mm:ss").parse(millisecondStr(t, "HH:mm:ss")).getTime() * MILLSECOND_RATIO);
		} catch (ParseException e) {
			return 0;
		}
	}
	
	public static boolean isNumeric(String str){ 
		   java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	/**
	 * @Description: 格林时间转字符
	 * @param t 时间[毫秒值]
	 * @param format 格式化模式
	 * @return
	 */
	public static String millisecondStr(long t,String format)
	{
		return new SimpleDateFormat(format).format(t);
	}
	
	/**
	 * @Description 格林时间转字符
	 * @param t 时间[秒]
	 * @param format 格式化模式
	 * @return 
	 * String
	 * @author jingxiaobo
	 * @date 2015年9月17日 上午10:48:03
	 */
	public static String secondStr(long t,String format)
	{
		return millisecondStr(t * ONE_SECONDS_MS, format);
	}

	/**
	 * 获取当前格式化的时间字符串
	 *
	 * @return
	 */
	public static String getNowStringDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}

	/**
	 * 字符串转日期("yyyy-MM-dd HH:mm:ss");
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateByString(String date) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			log.error("{}日期格式有误{}" + date + "yyyy-MM-dd HH:mm:ss");
			return null;
		}
	}

	/**
	 * 字符串转日期("yyyyMMdd-HHmmss");
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateByString2(String date) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			log.error("{}日期格式有误{}" + date + "yyyyMMdd-HHmmss");
			return null;
		}
	}

	/**
	 * 判定是不是今天
	 * 
	 * @author zhouyu @dateTime 2011-11-14 下午09:38:55
	 * @param time
	 *            时间毫秒串(<strong>注意:精确到毫秒</strong>)
	 * @return
	 */
	public static boolean isToday(final Long time) {
		if (null == time) {
			return false;
		}
		Calendar target = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		target.setTimeInMillis(time);
		if (target.get(Calendar.YEAR) == today.get(Calendar.YEAR) && target.get(Calendar.MONTH) == today.get(Calendar.MONTH)
				&& target.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是不是本周
	 * 
	 * @author zhouyu @dateTime 2011-11-16 下午06:05:27
	 * @param time
	 *            时间毫秒串 <string>注意：精确到毫秒</strong>
	 * @return
	 */
	public static boolean isCurrentWeek(final Long time) {
		if (null == time) {
			return false;
		}
		Calendar target = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		target.setTimeInMillis(time);
		if (target.get(Calendar.YEAR) == today.get(Calendar.YEAR) && target.get(Calendar.WEEK_OF_YEAR) == today.get(Calendar.WEEK_OF_YEAR)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 传入秒 ,获得String时间格式 X天X小时X分钟X秒
	 * 
	 * @param mss
	 * @return
	 */
	/*
	 * public static String GetTransformTime(long mss) { String t = ""; long
	 * days = mss / (60 * 60 * 24); long hours = (mss % (60 * 60 * 24)) / (60 *
	 * 60); long minutes = (mss % (60 * 60)) / 60; long seconds = mss % 60; if
	 * (days > 0) { t = days + ResManager.getInstance().getString("天") + hours +
	 * ResManager.getInstance().getString("小时") + minutes + ("分") + seconds +
	 * ("秒"); } else { t = hours + ResManager.getInstance().getString("小时") +
	 * minutes + ResManager.getInstance().getString("分") + seconds +
	 * ResManager.getInstance().getString("秒"); } return t; }
	 */

	/**
	 * 根据毫秒数获取 天 时 分秒 格式
	 * 
	 * @param millisecond
	 * @return
	 */
	/*
	 * public static String millisecondToStr(long millisecond) { if (millisecond
	 * < 0) { return millisecond + ResManager.getInstance().getString("毫秒"); }
	 * if (millisecond < 1000) { return "1秒"; } long second = millisecond /
	 * 1000; String result = ""; long day = second / (3600 * 24); if (day > 0) {
	 * result += (day + ResManager.getInstance().getString("天")); } second %=
	 * 3600 * 24; long hour = second / 3600; if (hour > 0) { result += (hour +
	 * ResManager.getInstance().getString("小时")); } second %= 3600; long minute
	 * = second / 60; if (minute > 0) { result += (minute +
	 * ResManager.getInstance().getString("分")); } second %= 60; if (second > 0)
	 * { result += (second + ResManager.getInstance().getString("秒")); } return
	 * result; }
	 */

	/**
	 * 获得最近指定星期X 返回毫秒
	 * 
	 * @param week
	 *            0=周日 ，1=周一 。。。 6=周六
	 * @return
	 */
	public static long getSoonWeek(int week) {
		return getSoonWeek(new Date(), week);
	}

	/**
	 * 获得最近指定星期X 返回毫秒
	 * 
	 * @param week
	 *            0=周日 ，1=周一 。。。 6=周六
	 * @return
	 */
	public static long getSoonWeek(long ms, int week) {
		Date date = new Date(ms);
		return getSoonWeek(date, week);
	}

	/**
	 * 获得最近指定星期X 返回毫秒
	 * 
	 * @param week
	 *            0=周日 ，1=周一 。。。 6=周六
	 * @return
	 */
	public static long getSoonWeek(Date date, int week) {
		if (week > 6) {
			week = week % 7;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		// 如果当前日期不是周6则自动往后递增日期
		while (cal.get(Calendar.DAY_OF_WEEK) != week + 1) {
			cal.add(Calendar.DAY_OF_WEEK, 1);
		}
		return cal.getTimeInMillis();
	}

	/**
	 * 得到年月日组合格式int ， 例子20110101 用来精确控制到 某一天 的时间
	 * 
	 * @return
	 */
	public static int GetSeriesDay() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int sday = year * 10000 + month * 100 + day;
		return sday;
	}

	/**
	 * 计算当前时间距截止的时间截止时间还有多少天,不足一天按一天处理
	 * 
	 * @param startTime
	 * @param deadLineDays
	 * @return
	 */

	public static int getLeftDays(long startTime, int deadLineDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startTime);
		calendar.add(Calendar.DAY_OF_MONTH, deadLineDays);
		int lefDays = (int) ((calendar.getTime().getTime() - System.currentTimeMillis()) / ONE_DAY_MS);
		return lefDays <= 0 ? 1 : lefDays;
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String formateDate(long time, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = new Date(time);
		return simpleDateFormat.format(date);
	}

	public static long getUnixTime(int month, int day, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static long getUnixTime(int day, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static long getUnixTime(int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static long getUnixTime(int year, int month, int day, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static long getUnixWeekTime(int week, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, week);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	public static boolean isInTimeAtDay(String start, String end) {
		long now = System.currentTimeMillis();
		String tmp = getYear(now) + "-" + (getMonth(now)+1) + "-" + getDayOfMonth(now);
		long startTime = millisecondDate(tmp + " " + start, "yyyy-MM-dd HH:mm:ss");
		long endTime = millisecondDate(tmp + " " + end, "yyyy-MM-dd HH:mm:ss");
		return startTime <= now && endTime > now;
	}
	
	public static boolean isInTimeAtWeek(String start, String end) {
		long now = System.currentTimeMillis();
		int dayOfWeek = getDayOfWeek(now);
		return dayOfWeek >= Integer.parseInt(start) && dayOfWeek <= Integer.parseInt(end);
	}
	
	/**
	 * @Description 计算当前时间增加的秒值之后的毫秒值
	 * @param seconds 增加的秒值
	 * @return long
	 * @author jingxiaobo
	 * @date 2015年9月17日 上午10:57:23
	 */
	public static long nowAndSeconds(long seconds){
		return System.currentTimeMillis() + seconds * ONE_SECONDS_MS;
	}
	
	/**
	 * @Description 计算当前时间增加的分钟值之后的毫秒值
	 * @param minutes 增加的分钟值
	 * @return long
	 * @author jingxiaobo
	 * @date 2015年12月10日 下午2:07:42
	 */
	public static long nowAndMinutes(long minutes){
		return System.currentTimeMillis() + minutes * ONE_MINUTES_MS;
	}
	
	/**
	 * @Description 计算当前时间增加的天数之后的毫秒值
	 * @param days 增加的天数
	 * @return long
	 * @author jingxiaobo
	 * @date 2015年9月17日 上午11:02:11
	 */
	public static long nowAndDays(long days){
		return System.currentTimeMillis() + days * ONE_DAY_MS;
	}
	
	/**
	 * @Description 计算特定时间减少的天数之后的毫秒值
	 * @param date 特定时间
	 * @param days 减少的天数
	 * @return long
	 * @author jingxiaobo
	 * @date 2015年11月13日 下午3:31:11
	 */
	public static long specialMinusDays(Date date, int days){
		return date.getTime() - days * ONE_DAY_MS;
	}
	
	/**
	 * @Description 获取包含当天在内向前推连续几天的时间字符串表示[例如,2015-11-13, 2015-11-12]
	 * @param days 特定的天数
	 * @return 
	 * List<String>
	 * @author jingxiaobo
	 * @date 2015年11月13日 下午3:25:58
	 */
	public static List<String> getNowSpecialDayBeforeString(int days){
		List<String> list = new ArrayList<String>();
		Date now = new Date();
		for(int i = 1; i <= days; i++){
			long time = TimeUtil.specialMinusDays(now, i - 1);
			String timeStr = TimeUtil.getDateString(new Date(time));
			list.add(timeStr);
		}
		return list;
	}
	
	public static String formatDateByPattern(Date date,String dateFormat){
		return new SimpleDateFormat(dateFormat).format(date);
	}
	
	public static String getCron(long  time){
		Date date = new Date(time);
		String dateFormat = "0 0 0 dd MM ? yyyy";
		return formatDateByPattern(date, dateFormat);
	}
	
	/**
	 * @Description 获取今天0点对应的毫秒值
	 * @param date: 指定的日期date数据; 为空则指定为当前时间;
	 * @return long
	 * @author jingxiaobo
	 * @date 2016年1月5日 下午5:07:56
	 */
	public static long getCurZeroClockMs(Date date){
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
}
