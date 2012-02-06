package com.message.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * 时间的工具类
 * @see org.apache.commons.lang.time.DateUtils
 * @author sunhao(sunhao.java@gmail.com)
 */
public final class DateUtils extends org.apache.commons.lang.time.DateUtils {
	
	/**
     * Default date formate pattern.
     */
    public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * Short pattern of date.
     */
    public static final String shortPattern = "yyyy-MM-dd";
    
    public static final String defaultPatternWithZone = "yyyy-MM-dd HH:mm:ss z";
    
    /**
     * 获取当前系统日历的辅助变量.
     */
    private static Calendar calendar;
    
    static {
    	calendar = Calendar.getInstance();
    }
    
    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * 
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
    	return parse(strDate, defaultPattern);
    }
    
    /**
     * 将短时间格式时间转换为字符串, yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static String dateToStr(java.util.Date date) {
    	return formatDate(date, shortPattern);
    }
    
    /**
     * 将短时间格式字符串转换为时间, yyyy-MM-dd
     *
     * @param strDate Date in string.
     * @return Date.
     */
    public static Date parseShortDate(String strDate) {
        return parse(strDate, shortPattern);
    }
	
	/**
	 * 把字符串转成日期型
	 * 
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date parse(final String dateString, final String pattern){
		if(StringUtils.isEmpty(dateString)){
			return null;
		}
		String pat = pattern;
		if(StringUtils.isEmpty(pat)){
			pat = defaultPattern;
		}
		try {
			return new SimpleDateFormat(pat).parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 将日期型转成字符串型
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if(StringUtils.isEmpty(pattern)){
			pattern = defaultPattern;
		}
		
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 将日期型转成字符串型(按时区转换，默认是GMT)
	 * 
	 * @param date
	 * @param pattern
	 * @param timeZone
	 * @return
	 */
	public static String formatDate(Date date, String pattern, String timeZone) {
		if(StringUtils.isEmpty(pattern)){
			pattern = defaultPatternWithZone;
		}
		
		if(StringUtils.isEmpty(timeZone)){
			timeZone = "GMT";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		
		return sdf.format(date);
	}
	
	/**
	 * 把日期型转换成yyyy-MM-dd HH:mm:ss类型的
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, defaultPattern);
	}
	
	/**
     * Format date.
     * <p/>
     * 如果在一天之内发生，显示xx小时xx分钟xx秒前
     * 其它显示长日期，yyyy-MM-dd hh:mm
     *
     * @param date Date to be format.
     * @return Display string.
     */
    public static String formatResentDate(final Date date) {
        return formatResentDateByLocale(date, null);
    }
    
    /**
     * Format date.
     * <p/>
     * 如果在一天之内发生，显示xx小时xx分钟xx秒前
     * 其它显示长日期，yyyy-MM-dd hh:mm
     *
     * @param date Date to be format.
     * @return Display string.
     */
    public static String formatResentDateByLocale(final Date date, final Locale locale) {
    	//TODO 要写实现
    	return "";
    }
    
    /**
     * Format date.
     * <p/>
     * 如果在一天之内发生，显示xx小时xx分钟xx秒前
     * 其它则按照pattern显示日期，如yyyy-MM-dd
     *
     * @param date Date to be format.
     * @return Display string.
     */
    public static String formatResentDate(final Date date, final String pattern) {
    	return formatResentDateByLocale(date, pattern, null);
    }
    
    /**
     * 
     * @param date
     * @param pattern
     * @param locale
     * @return
     */
    public static String formatResentDateByLocale(final Date date, final String pattern, final Locale locale) {
    	//TODO 写实现
    	return "";
    }
	
	/**
     * 得到两个日期的间隔
     *
     * @param now     现在时间
     * @param endDate 结束时间
     * @return String
     */
	public static String getDifferenceDate(Date now, Date endDate) {
		//TODO 写实现
		return "";
	}
	
	/**
     * 返回两个日期之间相差的月份数.
     *
     * @param startDate 开始日期.
     * @param endDate   结束日期.
     * @return 两个日期之间相差的月份数.
     */
	public static int getMonths(Date startDate, Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int startMonth = startCalendar.get(Calendar.MONTH);
		int endMonth = endCalendar.get(Calendar.MONTH);
		int monthBetween = 0;
		
		if(startDate.before(endDate)){
			monthBetween = endMonth - startMonth;
		} else {
			monthBetween = startMonth - endMonth;
		}
		
		return Math.abs(year * 12) + monthBetween + 1;
	}
	
	/**
     * 计算2天之间的间隔，忽略时间中的比天小的单位，如时分秒等
     * @param begin  开始时间的毫秒数；
     * @param end 结束时间的毫秒数
     * @return  间隔的天数
     */
	public static long getDayBetweenNumInMills(long begin, long end){
		long endDay = end - end % MILLIS_PER_DAY;
		long beginDay = begin - begin % MILLIS_PER_DAY;
		return (endDay - beginDay) / MILLIS_PER_DAY;
	}
	
	/**
     * 两个日期之间相隔天数.
     *
     * @param nowDate
     * @param endDate
     * @return　 天数
     */
	public static long getDaysBetweenTwoDates(String nowDate, String endDate) {
		Date dtFrom;
        Date dtEnd;
        dtFrom = parse(nowDate, shortPattern);
        dtEnd = parse(endDate, shortPattern);
        long begin = dtFrom.getTime();
        long end = dtEnd.getTime();
        long inter = end - begin;
        if(inter < 0){
        	inter *= -1;
        }
        long dateMillSec = 24 * 60 * 60 * 1000;
        long dateCnt = inter / dateMillSec;
        long remainder = inter % dateMillSec;
        
        if(remainder != 0){
        	dateCnt ++;
        } 
        
        return dateCnt;
	}
	
	/**
     * 两个日期之间相隔天数的共通.
     *
     * @param nowDate
     * @param endDate
     * @return
     */
    public static long getDaysBetweenTwoDates(Date nowDate, Date endDate) {
        return getDaysBetweenTwoDates(formatDate(nowDate), formatDate(endDate));
    }
    
    /**
     * 取得两个时间间隔的分钟数.
     *
     * @param beginDate 开始时间
     * @param endDate   截至时间
     * @return 分钟数
     */
    public static long getMinuteBewteenDates(Date beginDate, Date endDate) {
    	long begin = beginDate.getTime();
    	long end = endDate.getTime();
    	long inter = end - begin;
    	
    	if(inter < 0){
    		inter = inter * (-1);
    	}
    	
    	long dateMillSec = 60 * 1000;
    	long dateCnt = inter / dateMillSec;
    	long remainder = inter % dateMillSec;
    	
    	if(remainder != 0){
    		dateCnt ++;
    	}
    	
    	return dateCnt;
    }
    
    /**
     * 见getEndOfDay(Date day, Calendar cal)
     * 
     * @param day
     * @see com.message.utils.DateUtils.getEndOfDay(Date day, Calendar cal)
     * @return
     */
    public static Date getEndOfDay(Date day) {
    	return getEndOfDay(day, Calendar.getInstance());
    }
    
    /**
     * 获取给定时间的那天的最后时刻
     * @param day	给定时间(em.2011-01-25 22:11:00...)
     * @param cal
     * @return		给定时间的那天的最后时刻(em.2011-01-25 23:59:59...)
     */
    public static Date getEndOfDay(Date day, Calendar cal) {
    	if(day == null)
    		day = new Date();
    	cal.setTime(day);
    	cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
    	cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
    	cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
    	cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
    	
    	return cal.getTime();
    }
    
    /**
     * 获取给定时间的那天的开始时刻
     * @param day		给定时间(em.2011-01-25 22:11:00...)
     * @param cal
     * @return			给定时间的那天的最后时刻(em.2011-01-25 00:00:00...)
     */
    public static Date getStartOfDay(Date day, Calendar cal) {
    	if(day == null)
    		day = new Date();
    	cal.setTime(day);
    	cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
    	cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
    	cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
    	cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
    	
    	return cal.getTime();
    }
    
    /**
     * 见getStartOfDay(Date day, Calendar cal)
     * @param day
     * @see com.message.utils.DateUtils.getStartOfDay(Date day, Calendar cal)
     * @return
     */
    public static Date getStartOfDay(Date day) {
        return getStartOfDay(day, Calendar.getInstance());
    }
    
    /**
     * 获取给定时间的那个月的最后时刻
     * @param day	给定时间(em.2011-01-25 22:11:00...)
     * @param cal
     * @return		给定时间的那个月的最后时刻(em.2011-01-31 23:59:59...)
     */
    public static Date getEndOfMonth(Date day, Calendar cal) {
    	if(day == null)
    		day = new Date();
    	cal.setTime(day);

    	cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
    	cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
    	cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
    	cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
    	
        cal.set(Calendar.DAY_OF_MONTH, 1);
        
        cal.add(Calendar.MONTH, 1);
        
        cal.add(Calendar.DAY_OF_MONTH, -1);
        
        return cal.getTime();
    }
    
    /**
     * 见getEndOfMonth(Date day, Calendar cal)
     * @param day
     * @see com.message.utils.DateUtils.getEndOfMonth(Date day, Calendar cal)
     * @return
     */
    public static Date getEndOfMonth(Date day) {
        return getEndOfMonth(day, Calendar.getInstance());
    }
    
    /**
     * 获取给定时间的那个月的开始时刻
     * @param day		给定时间(em.2011-01-25 22:11:00...)
     * @param cal
     * @return			给定时间的那个月的开始时刻(em.2011-01-01 00:00:00...)
     */
    public static Date getStartOfMonth(Date day, Calendar cal) {
    	if(day == null)
    		day = new Date();
    	cal.setTime(day);
    	
    	cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
    	cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
    	cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
    	cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
    	
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	
    	return cal.getTime();
    }
    
    /**
     * 见getStartOfMonth(Date day, Calendar cal)
     * @param day
     * @see com.message.utils.DateUtils.getStartOfMonth(Date day, Calendar cal)
     * @return
     */
    public static Date getStartOfMonth(Date day) {
        return getStartOfMonth(day, Calendar.getInstance());
    }
    
    /**
     * 获取给定时间的那天的正午时刻
     * @param day		给定时间(em.2011-01-25 22:11:00...)
     * @param cal
     * @return			给定时间的那天的最后时刻(em.2011-01-25 12:00:00...)
     */
    public static Date getNoonOfDay(Date day, Calendar cal) {
        if (day == null) 
        	day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }
    
    /**
     * 见getNoonOfDay(Date day, Calendar cal)
     * @param day
     * @see com.message.utils.DateUtils.getNoonOfDay(Date day, Calendar cal)
     * @return
     */
    public static Date getNoonOfDay(Date day){
    	return getNoonOfDay(day, Calendar.getInstance());
    }
    
    /**
     * 把给定的date中的时间全部设置为0
     * @param date		给定时间(em.2011-01-25 22:11:00...)
     * @return			给定的date中的时间全部设置为0(em.2011-01-25 00:00:00...)
     */
    public static Calendar timeSetTo0(Date date) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	
    	return calendar;
    }
    
    /**
     * 获取一个时间范围
     * @param date		给定时间(em.2011-01-25 22:11:00...)
     * @return			范围(2011-01-25 00:00:00 ~ 2011-01-26 00:00:00)
     */
    public static Date[] getDayRegion(Date date) {
    	Calendar c0 = timeSetTo0(date);
    	Calendar c1 = timeSetTo0(date);
    	
    	c1.add(Calendar.DATE, 1);
    	
    	Date[] ds = new Date[2];
    	ds[0] = c0.getTime();
    	ds[1] = c1.getTime();
    	
    	return ds;
    }
    
    /**
     * 获取当前所在星期的星期一和下个星期一,两个日期.
     * 
     * @param date										给定时间(em.2011-01-25 22:11:00...)
     * @return 日期数组{本星期一日期，下一个星期一日期}.	范围(em.2012-01-23 00:00:00 ~ 2012-01-30 00:00:00)
     */
    public static Date[] getWeekRegion(Date date) {
    	Date[] ds = new Date[2];
    	
    	Calendar c0 = timeSetTo0(date);
    	
    	int week = c0.get(Calendar.DAY_OF_WEEK) - 1;	//当前星期几
    	if(week == 0)	//星期日
    		week += 7;	//变为星期一
    	
    	c0.add(Calendar.DATE, (1 - week));
    	ds[0] = c0.getTime();
    	c0.add(Calendar.WEEK_OF_YEAR, 1);
    	ds[1] = c0.getTime();
    	
    	return ds;
    }
    
    /**
     * 获取当前所在星期一到星期日,两个日期.
     *
     * @param date						给定时间(em.2011-01-25 22:11:00...)
     * @return 日期数组{星期一，星期日}.	范围(em.2012-01-23 00:00:00 ~ 2012-01-29 00:00:00)
     */
    public static Date[] getNowWeekRegion(Date date) {
    	Date[] ds = new Date[2];
    	
    	Calendar c0 = timeSetTo0(date);
    	
    	int week = c0.get(Calendar.DAY_OF_WEEK) - 1;	//当前星期几
    	if(week == 0)	//星期日
    		week += 7;	//变为星期一
    	
    	c0.add(Calendar.DATE, (1 - week));
    	ds[0] = c0.getTime();
    	c0.add(Calendar.DATE, 6);
    	ds[1] = c0.getTime();
    	
    	return ds;
    }
    
    /**
     * 获取当前所在月的1号到下个月1号的两个日期.
     *
     * @param date 要获取时间区间的日期	给定时间(em.2011-01-25 22:11:00...)
     * @return 月份区间					范围(em.2012-01-01 00:00:00 ~ 2012-02-01 00:00:00)
     */
    public static Date[] getMonthRegion(Date date) {
    	Date[] ds = new Date[2];

        Calendar c0 = timeSetTo0(date);

        int w = c0.get(Calendar.DAY_OF_MONTH);//当前几号

        c0.add(Calendar.DATE, (1 - w));
        ds[0] = c0.getTime();
        c0.add(Calendar.MONTH, 1);
        ds[1] = c0.getTime();
        return ds;
    }
    
    /**
     * 所在年的1号到下一年1号，两个日期.
     *
     * @param date 要获取时间区间的日期	给定时间(em.2011-01-25 22:11:00...)
     * @return 年份区间					范围(em.2012-01-01 00:00:00 ~ 2013-01-01 00:00:00)
     */
    public static Date[] getYearRegion(Date date) {
    	Date[] ds = new Date[2];

        Calendar c0 = timeSetTo0(date);

        int w = c0.get(Calendar.DAY_OF_YEAR);//当前几号

        c0.add(Calendar.DATE, (1 - w));
        ds[0] = c0.getTime();
        c0.add(Calendar.YEAR, 1);
        ds[1] = c0.getTime();
        return ds;
    }
    
    /**
     * 根据类型获取当前日期所在范围日期.
     *
     * @param date
     * @param field 同java.util.Calendar中的DATE,WEEK_OF_YEAR,MONTH,YEAR
     * @return
     */
    public static Date[] getRegion(Date date, int field) {
    	switch(field){
    		case Calendar.WEEK_OF_YEAR :
    			return getWeekRegion(date);
    		case Calendar.MONTH : 
    			return getMonthRegion(date);
    		case Calendar.YEAR :
    			return getYearRegion(date);
    		default :
    			return getDayRegion(date);
    	}
    }
    
    /**
     * 获取某天开始的毫秒数。
     * @param ms	需要获取的日期的毫秒数表示
     * @return	某天开始的毫秒数
     */
    public static long getStartOfDayMillis(long ms){
    	return ms - ms % MILLIS_PER_DAY;
    }
    
    /**
     * 获取当前时间
     * @return
     */
    public static Date getNow(){
    	return new Date();
    }
    
    /**
     * 获取当前时间
     * @return
     */
    public static Date getToday(){
    	return getNow();
    }
    
    /**
     * 获取给定日期的星期数
     * @param prefix	前缀				ep.'星期'
     * @param date		给定日期			ep.'2012-01-25 23:07:58'
     * @return			前缀+(星期数)	ep.'星期三'
     */
    public static String getDayOfWeek(String prefix, Date date) {
    	final String dayNames[] = {"日", "一", "二", "三", "四", "五", "六"};
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
    	if(dayOfWeek < 0)
    		dayOfWeek = 0;
    	
    	return prefix + dayNames[dayOfWeek];
    }
    
    /**
     * 获取给定日期的星期数(默认前缀:'星期')
     * @param date
     * @return
     */
    public static String getDayOfWeek(Date date) {
    	return getDayOfWeek("星期", date);
    }
    
    /**
     * 获取从开始时间为第一周，到结束时间为最后一周的，周->星期几->日期的一个MAP表,以便方便地从周数和星期几对应到一个具体的日期,适用于校历的查询.
     *
     * @param start
     * @param end
     * @return
     */
    //TODO 需要理解是什么意思
    @SuppressWarnings("unchecked")
	public static Map getWeekMap(Date start, Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek <= 0) {
            dayOfWeek = 7;
        }
        Map weekMap = new HashMap();
        Map weekDayMap = new HashMap();
        weekMap.put("1", weekDayMap);
        Date dateNext = null;
        int weekcount = 1;
        for (int i = dayOfWeek; ; ++i) {
            Date dateCurrent = calendar.getTime();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dateNext = calendar.getTime();
            if (dateNext.getTime() < end.getTime()) {
                if (i > 7) {
                    weekMap.put(String.valueOf(weekcount), weekDayMap);
                    weekDayMap = new HashMap();
                    i = 1;
                    weekcount++;
                }
                weekDayMap.put(String.valueOf(i), dateCurrent);
            } else {
                break;
            }
        }
        return weekMap;
    }
    
    //TODO 需要理解是什么意思
    @SuppressWarnings("unchecked")
	public static Date getDateByWeekcountAndWeekday(Date start, Date end, int weekcount, int weekday) {
        Map weekMap = getWeekMap(start, end);
        Map weekDayMap = (Map) weekMap.get(String.valueOf(weekcount));
        if (weekDayMap == null) {
            return null;
        } else {
            return (Date) weekDayMap.get(String.valueOf(weekday));
        }
    }
    
    /**
     * 获取开始日期当天的开始时刻以及结束日期当天的最大时刻.例如开始日期是2009-05-01,结束日期是2009-05-30，<br/>
     * 那么调用该方法后将返回一个包含两个日期的数组.数组的元素分别是2009-05-01 00:00:00、20009-05-30 23:59:59.<br/>
     * 如果开始日期或者结束日期是<code>Null<code>,那么处理后相应返回的日期也是<code>Null</code>,例如开始日期是<code>Null</code>，<br/>
     * 那么返回的数组的第一个元素也是<code>Null</code>.
     * 
     * @param startTime		开始日期.
     * @param endTime		结束日期.
     * @return			开始日期当天的开始时刻以及结束日期当天的最大时刻,数组的第一个元素是开始日期当天的开始时刻,第二个元素是结束日期的最大时刻.
     */
    public static Date[] getMinMaxDates(Date startTime, Date endTime) {
    	Date[] dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        
        if (startTime != null) {
            calendar.setTime(startTime);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            dates[0] = calendar.getTime();
        }
        
        if (endTime != null) {
            calendar.setTime(endTime);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            dates[1] = calendar.getTime();
        }

        return dates;
    }
    
    /**
     * 将年月转换成日期格式yyyy-mm.在转化的过程中不会对年月的值进行合法性检查.方法的调用示例如下:<br/>
     * getFullYearMonth(2009, 1) 返回2009-01
     * @param year		年份.
     * @param month		月份.
     * @return			年月的日期格式yyyy-mm.
     */
    public static String getFullYearMonthString(Integer year, Integer month) {
    	String format = year + "-";
    	if(month.intValue() < 10)
    		format += "0";
    	
    	return format + month;
    }
    
    /**
     * 获取当前日历所属的年.
     * @return	当前日历所属的年.
     */
    public static int getCurrentYear() {
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	return calendar.get(Calendar.YEAR);
    }
    
    /**
     * 获取当前日历所属的月，月份是从1开始的.
     *
     * @return 当前日历所属的月.
     */
    public static int getCurrentMonth() {
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	return calendar.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 获取当前日期后第kszc周的周一的时间
     *
     * @param kszc 开始周次.		ep.3
     * @param date 起始时间.		ep.2012-01-25 23:46:54
     * @return					ep.2012-02-06 23:45:03
     */
    public static Date getStartDate(int kszc, Date date) {
    	Calendar cal = Calendar.getInstance();
    	if(date != null){
    		cal.setTime(date);
    	}
    	int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
    	int ks = kszc;
    	int ds = (ks - 1) * 7;
    	if (week < 0) {
    		week = 0;
        }
        if (ks > 1) {
            ds = ds + 1 - week;
            cal.add(Calendar.DATE, ds);
        }

        Date sd = cal.getTime();
        return sd;
    }
    //TODO row 806
}
