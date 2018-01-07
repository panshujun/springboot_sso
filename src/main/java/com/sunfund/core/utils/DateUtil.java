package com.sunfund.util;

import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;


/**
 * 时间类型转换
 * 
 * @author：xiaogao 2014年11月7日
 */
public class DateUtil {
	private static final Logger logger = Logger.getLogger(DateUtil.class);
	public static final String CH_DATE_FORMAT = "yyyy年MM月dd日";
	public static final String EN_DATE_FORMAT = "yyyy-MM-dd";
	public static final String SHORT_DATE_FORMAT = "MM-dd";
	public static final String EN_LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将String类型的时间串按照给定的格式解析为Date 
	 * 
	 * @param dateStr
	 *            待解析的时间串
	 * @param format
	 *            时间串解析的格式
	 * @return
	 */
	public static Date stringToDate(String dateStr, String format) {
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 查看当天是当月第几天
	 * @return
	 */
	public static int getDayOfMonth(){
		Calendar c = Calendar.getInstance();
		return c.get(c.DAY_OF_MONTH);
	}
	/**
	 * 获取当月一共有多少天
	 * @return
	 */
	public static int getDaysOfCurrentMonth(){
		Calendar c = Calendar.getInstance();
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 获取指定日期的月一共有多少天
	 * @param date
	 * @return
	 */
	public static int getDaysOfDateMonth(Date date){
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		return now.getActualMaximum(Calendar.DATE);
	}
	

	public static String StringToCHDate(Date dateStr) {
		if (dateStr == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String date = null;
		try {
			date = sdf.format(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}



	/**
	 * 根据时间格式取得当前时间的字符串
	 * 
	 * @param strFormat
	 * @return
	 */
	public static String getTodayByFormat(Date date, String strFormat) {
		String strDate = "";
		if (date != null) {
			if (strFormat == null || strFormat.equals("")) {
				strFormat = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat format = new SimpleDateFormat(strFormat);
			strDate = format.format(date);
		}
		return strDate;

	}

	/**
	 * 返回当前时间距离当天24点的毫秒值
	 * 
	 * @return
	 */
	public static long getLockTime() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis() - new Date().getTime();
	}

	// 获取当前日期的下一个工作日
	public static String nextDate() {
		String str = "yyyy年MM月dd日";
		Date date;
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime();

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 6) {
			calendar.setTime(date);
			calendar.add(calendar.DATE, 2);
			date = calendar.getTime();
		}
		if (dayOfWeek == 0) {
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);
			date = calendar.getTime();
		}
		return getTodayByFormat(date, "");
	}

	// 当前日期是否在该日期内
	public static boolean compareDate(Date startTime, Date endTime) {
		long time = System.currentTimeMillis();
		try {
			if (time > startTime.getTime() && time < endTime.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

	}

	/**
	 * 根据当前日期计算几天后的日期
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
	/**
	 * 获取当前时间偏移月数的时间
	 * @param date
	 * @param offsetMonths
	 * @return
	 */
	public static Date getOffsetMonthDate(Date date,int offsetMonths){
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + offsetMonths);
		return now.getTime();
	}
	/**
	 * 根据当前日期计算几个月前的日期
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(int month) {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.MONTH, now.get(Calendar.MONTH)-month);
		return now.getTime();
	}

	/**
	 * 获取两个日期之间的天数
	 */
	public static int getDaySpace(Date date1) {
		if (date1 == null)
			return 0;
		Date date = new Date();
		int day = 0;
		if (date.getTime() > date1.getTime()) {
			long aa = date.getTime() - date1.getTime();
			System.out.println(aa + "-----------");
			day = (int) (aa / (1000 * 60 * 60 * 24));
			day += 1;
		}
		return day;
	}

	
	// 获取当前日期的第N个工作日
	public static Date nextNWorkDate(int day) {
		Date date;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime();

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 6) {
			calendar.setTime(date);
			calendar.add(calendar.DATE, 2);
			date = calendar.getTime();
		}
		if (dayOfWeek == 0) {
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);
			date = calendar.getTime();
		}
		return date;
	}

	// 获取当前时间的上一小时(YYYY-MM-dd HH:mm:dd)
	public static Date getBeforeHourTimeDate(int h) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)
				- h);

		return calendar.getTime();

	}



	// 获取当前日期跟当天21点的毫秒数
	public static int getMinusMillis() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int minute = calendar.get(Calendar.MINUTE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int second = calendar.get(Calendar.SECOND);
		int x = (17 * 60 * 60 - hour * 60 * 60 - minute * 60 - second) * 1000;

		return x;
	}

	
	/**
	 * 将日期类型转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		return str;
	}

	public static long getExpireTime(String timeStr) {
		Date date = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long time = date.getTime() - new Date().getTime();
		return time;
	}

	public static String TimestampToString(Timestamp ts, String format) {
		String tsStr = "";
		if (ts == null) {
			return "";
		}
		DateFormat sdf = new SimpleDateFormat(format);
		try {
			// 方法一
			tsStr = sdf.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}


	/**
	 * String 时间类型截取
	 */
	public static String strTimeSub(String time) {
		String time1 = time;
		if (null != time && time.length() > 10) {
			time1 = time.substring(0, 10);
		}
		return time1;
	}

	// 是否是美股下单时间
	public static boolean isOrderMgTime() {
		boolean flag = false;

		Calendar calendar = Calendar.getInstance();

		if (7 == calendar.get(7)) {
			int hour = calendar.get(11);
			int minute = calendar.get(12);
			if (((hour == 5) && (minute >= 30)) || (hour == 6)
					|| ((hour == 7) && (minute < 30))) {
				flag = true;
			}
		}

		return flag;
	}
	/**
	 * 获取当前指定格式的美东时间
	 * @param timeFormat
	 * @return
	 */
	public static String getFormatUSATime(String timeFormat){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		return sdf.format(date);
	}
	/**
	 * 获取months个月之后的最后一天23:59:59
	 * @param months
	 * @return
	 */
	public static Date getNextXMonthLastDate(int months){
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+months+1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.DATE, calendar.get(calendar.DATE)-1);
		return calendar.getTime();
	}
	/**
	 * 判断当前日期是否是当月最后一天
	 * @return
	 */
	public static boolean isLastDayOfMonth(){
		Calendar calendar = Calendar.getInstance();  
		int date = calendar.get(Calendar.DATE);
		if(date == calendar.getActualMaximum(Calendar.DATE)){
			return true;
		}
		return false;
	}
	/**
	 * 判断当前日期是否是当月第一天
	 * @return
	 */
	public static boolean isFirstDayOfMonth(){
		Calendar calendar = Calendar.getInstance();  
		int date = calendar.get(Calendar.DATE);
		if(date == 1){
			return true;
		}
		return false;
	}
	/**
	 * 获取当前的年月
	 * @return
	 */
	public static String getYearAndMonth() {
	    Date date = new Date();
	    String yearAndMonth=getTodayByFormat(date,"yyyy-MM");
	    System.out.println(yearAndMonth);
	    return yearAndMonth;
	}
	/**
	 * 比较两个日期的大小
	 */
	public static boolean compareDate(String start,String end) {
		boolean flag = true;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(start);
			Date date2 = sdf.parse(end);
			if(date1.after(date2)) {
				flag = false;
			}
		} catch (ParseException e) {
			logger.info("ASSET-日期比较出错："+e.getMessage());
		}
		return flag;
	}
	/**
	 * 获取下一个月
	 * @param dateString
	 * @param month
	 * @return
	 */
	public static String getNextMonthDay(String dateString,int month) {
		String form = "";
		if(dateString.length()<10) {
			form = "yyyy-MM";
		}else {
			form = "yyyy-MM-dd";
		}
		Date date=stringToDate(dateString,form);
        SimpleDateFormat df = new SimpleDateFormat(form);  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.MONTH, month);  
        String dayS=df.format(calendar.getTime());
	    return dayS;
	}
	/**
	 * 获取标准的北京时间，不受服务器时间影响
	 * @return
	 * @throws Exception 
	 */
	public static Date getStandardBJTime() throws Exception{
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8")); // 时区设置    
        URL url=new URL("http://www.baidu.com");//取得资源对象    
        URLConnection uc = url.openConnection();//生成连接对象    
        uc.connect(); //发出连接    
        long ld=uc.getDate(); //取得网站日期时间（时间戳）    
        Date date=new Date(ld); //转换为标准时间对象    
        return date;
	}
	
	/**
	 * 获取指定时间前/后 days天
	 * 
	 * @param days
	 * @param patter
	 * @return
	 */
	public static String getDateByDays(int days, String patter, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(patter);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取两个日期之间的天数
	 */
	public static int getDaySpace(String date1Str,String date2Str) {
		try {
			//时间转换类
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date date1 = sdf.parse(date1Str);
	        Date date2 = sdf.parse(date2Str);
	        //将转换的两个时间对象转换成Calendard对象
	        Calendar can1 = Calendar.getInstance();
	        can1.setTime(date1);
	        Calendar can2 = Calendar.getInstance();
	        can2.setTime(date2);
	        //拿出两个年份
	        int year1 = can1.get(Calendar.YEAR);
	        int year2 = can2.get(Calendar.YEAR);
	        //天数
	        int days = 0;
	        Calendar can = null;
	        //如果can1 < can2
	        //减去小的时间在这一年已经过了的天数
	        //加上大的时间已过的天数
	        if(can1.before(can2)){
	            days -= can1.get(Calendar.DAY_OF_YEAR);
	            days += can2.get(Calendar.DAY_OF_YEAR);
	            can = can1;
	        }else{
	            days -= can2.get(Calendar.DAY_OF_YEAR);
	            days += can1.get(Calendar.DAY_OF_YEAR);
	            can = can2;
	        }
	        for (int i = 0; i < Math.abs(year2-year1); i++) {
	            //获取小的时间当前年的总天数
	            days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
	            //再计算下一年。
	            can.add(Calendar.YEAR, 1);
	        }
//	        System.out.println("天数差："+days);
	        return days;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/** 
	* 获得指定日期的后一天 
	* @param specifiedDay 
	* @return 
	*/ 
	public static String getSpecifiedDayAfter(String specifiedDay,int i){ 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
		} catch (ParseException e) { 
			e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+i); 
	
		String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayAfter; 
	} 
	
}
