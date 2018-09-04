package com.pi.base.util.time;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTools {

	/**
     * 简单日期格式
     */
    public static final String FORMAT_SIMPLEDATE = "yyyyMMdd";
    /**
     * 简单时间格式
     */
    public static final String FORMAT_SIMPLETIME = "HH:mm:ss";
    /**
     * 简单日期时间格式
     */
    public static final String FORMAT_SIMPLEDATETIME = "yyyyMMdd HH:mm:ss";
    /**
     * 完整日期时间格式
     */
    public static final String  FORMAT_FULL_DATE = "yyyy-MM-dd HH:mm:ss";
    
    public static String FORMAT_PUSHTIME = "yyyy/MM/dd HH:mm";//推送用格式
    /**
     * 得到某个微秒数(距离1970.1.1 00:00:00的微秒值)表示日期时间中的时间，时间格式为“HH:mm:ss” ,例如："10:23:01" 。
     * @param dateTime
     * @return
     */
    public final static String getSimpleTime(long dateTime) {
        Date date = new Date(dateTime);
        return formatDate(date, FORMAT_SIMPLETIME);
    }
    
    /**
     * 得到一个日期中简单格式(格式为“yyyyMMdd” ,例如："20040101")的年月日
     * @param date
     * @return
     */
    public final static String getSimpleDate(Date date) {
        return formatDate(date, FORMAT_SIMPLEDATE);
    }
    /**
     * 得到一个日期中简单格式(格式为"yyyy-MM-dd HH:mm:ss" ,例如："20040101")的年月日
     * @param date
     * @return
     */
    public static final String getFullDate(Date date){
      return formatDate(date, FORMAT_FULL_DATE);
    }
    /**
     * 得到简单格式(格式为“yyyyMMdd HH:mm:ss” ,例如："20040101")的年月日 时分秒
     * @param date
     * @return
     */
    public final static String getSimpleDateTime(Date date) {
        return formatDate(date, FORMAT_SIMPLEDATETIME);
    }
    
    /**
     * 得到一个日期中简单格式(格式为“HH:mm:ss” ,例如："10:23:01")的时间
     * @param date
     * @return
     */
    public final static String getSimpleTime(Date date) {
        return formatDate(date, FORMAT_SIMPLETIME);
    }
    
    /**
     * 得到指定格式的当前日期
     * @param format
     * @return
     */
    public final static String getCurDate(String format){
        Calendar cal = Calendar.getInstance();
        return formatDate(cal.getTime(), format);
    }
    /**
     * @description 获取推送的时间格式
     * @param format
     * @return
     */
    public final static String getPushDate() {
      Calendar cal = Calendar.getInstance();
      return formatDate(cal.getTime(), FORMAT_PUSHTIME);
    }
    
    /**
     * 得到当前时间，格式为pattern，例如HH:mm:ss等。
     * @param pattern String
     * @return String
     * @deprecated
     */
    public final static String getDateTime(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
    
    /**
     * 格式                                示例
     "dd/MMM/yyyy"                     06/Mar/1974
     "dd-MM-yyyy"                      06-03-1974
     "dd MMMMMMMMM yyyy"               06 March 1974
     "EEEEEEEEE, MMMMMMMMM dd, yyyy"   Wednesday, March 06, 1974
     有效的SimpleDateFormat显示格式
     表B是SimpleDateFormat参数的缩略语表。
     y   year
     M   month in year
     d   day in month
     D   day in year
     w   week in year
     W   week in month
     E   day in week
     
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    
    /**
     * 把日期String转换为日期对象
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parserDate(String dateStr, String format) throws ParseException {
      return parserDate(dateStr, format, null);
    }
    /**
     * 把日期String转换为中国时间
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parserChinaDate(String dateStr, String format) throws ParseException {
        return parserDate(dateStr, format, TimeZone.getTimeZone("GMT+8:00"));
    }
    /**
     * 把日期String转换为中国时间
     * @param dateStr
     * @param format
     * @param locale 区域
     * @return
     * @throws ParseException
     */
    public static Date parserDate(String dateStr, String format, TimeZone timeZone) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        if(null != timeZone){          
          formatter.setTimeZone(timeZone);
        }
        return formatter.parse(dateStr);
    }
    
    /**
     * 方法说明：时间的加减。
     * 
     * Modification History
     * Date  |  Author  |  Desription
     * --------------------------------
     *
     * @author <a href="mailto:wjingxin.sagittarius@gamil.com">wjingxin</a>
     * @see 
     * @since 
     * @param today 当前目标日期
     * @param format 当前目标日期的格式
     * @param CalendarType 间隔的类型 Calendar.DATE 为天，Calendar.MONTH 	为月
     * @param d 跨度 可以为负
     * @return
     * @throws ParseException 
     *
     */
    public static String getTheSpanDate(String today,String format,int calendarType, int d) {
    	return getTheSpanDate(today,format,calendarType,d,format);
    }
    
    /**
     * 方法说明：时间的加减。
     *
     * Modification History
     * Date  |  Author  |  Desription
     * --------------------------------
     *
     * @author <a href="mailto:wjingxin.sagittarius@gamil.com">wjingxin</a>
     * @see 
     * @since 
     * @param today 当前目标日期
     * @param format 当前目标日期的格式
     * @param CalendarType 间隔的类型 Calendar.DATE 为天，Calendar.MONTH 	为月
     * @param d 跨度 可以为负
     * @param outeFormat 输出日期格式
     * @return 
     *
     */
    public static String getTheSpanDate(String today,String format,int calendarType, int d,String outeFormat){
    	Calendar now = Calendar.getInstance();  
        try {
			now.setTime(parserDate(today,format));
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        now.set(calendarType, now.get(calendarType) + d);  
        return formatDate(now.getTime(),outeFormat); 
    }
    
    /**
     * 方法说明：这个日期的当月的第一天
     *
     * Modification History
     * Date  |  Author  |  Desription
     * --------------------------------
     *
     * @author <a href="mailto:wjingxin.sagittarius@gamil.com">wjingxin</a>
     * @see 
     * @since 
     * @param day
     * @param format
     * @return 
     *
     */
    public static String getTheMonthFirstDate(String day,String format) {
    	Calendar now = Calendar.getInstance();  
        try {
			now.setTime(parserDate(day,format));
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        now.set(Calendar.DATE, 1);  
        return formatDate(now.getTime(),format); 
	}
    
    /**
     * @return
     */
    public static String getLastDayOfMonth(String format) {
      Calendar cDay1 = Calendar.getInstance();
      cDay1.setTime(new Date());
      final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
      cDay1.set(Calendar.DATE, lastDay);
      return formatDate(cDay1.getTime(), format);
    }

    public static String getUpSpanDateByMinute(String date, int minute) {
      return DateTools.getTheSpanDate(date, "yyyy-MM-dd HH:mm:ss", Calendar.MINUTE, minute);
    }

    public static String getDownSpanDateByMinute(String date, int minute) {
      return DateTools.getTheSpanDate(date, "yyyy-MM-dd HH:mm:ss", Calendar.MINUTE, -minute);
    }

    public static boolean isValidDate(String str, String formatStr) {
      boolean convertSuccess = true;
      SimpleDateFormat format = new SimpleDateFormat(formatStr);
      try {
        format.setLenient(false);
        format.parse(str);
      } catch (ParseException e) {
        convertSuccess = false;
      }
      return convertSuccess;
    }
    
    public static void main(String[] args) {
      System.out.println(getLastDayOfMonth(FORMAT_PUSHTIME));
    }
    
}
