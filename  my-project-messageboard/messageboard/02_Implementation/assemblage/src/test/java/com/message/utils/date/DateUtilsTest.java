package com.message.utils.date;

import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

import org.junit.Test;

import com.message.base.utils.DateUtils;

@SuppressWarnings("deprecation")
public class DateUtilsTest {

	@Test
	public void testStrToDateLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testDateToStr() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseShortDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testParse() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatDateDateString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatDateDateStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatDateDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatResentDateDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatResentDateByLocaleDateLocale() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatResentDateDateString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormatResentDateByLocaleDateStringLocale() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDifferenceDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMonths() {
		Date startDate = new Date();
		Date endDate = new Date();
		startDate.setMonth(10);
		endDate.setMonth(2);
		DateUtils.getMonths(startDate, endDate);
		Assert.assertEquals("hahaha", 9, DateUtils.getMonths(startDate, endDate));
	}

	@Test
	public void testGetDayBetweenNumInMills() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDaysBetweenTwoDatesStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDaysBetweenTwoDatesDateDate() {
		String nowDate = "2012-01-25";
		String endDate = "2011-01-25";
		Assert.assertEquals("hahahha", 365, DateUtils.getDaysBetweenTwoDates(nowDate, endDate));
	}

	@Test
	public void testGetMinuteBewteenDates() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEndOfDay() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		Date endOfDay = DateUtils.getEndOfDay(date, calendar);
		System.out.println(endOfDay);
	}
	
	@Test
	public void testGetEndOfMonth(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		Date endOfMonth = DateUtils.getEndOfMonth(date, calendar);
		System.out.println(date);
		System.out.println(endOfMonth);
	}
	
	@Test
	public void testgetStartOfMonth(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		Date endOfMonth = DateUtils.getStartOfMonth(date, calendar);
		System.out.println(date);
		System.out.println(endOfMonth);
	}
	
	@Test
	public void testgetNoonOfDay(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		Date endOfMonth = DateUtils.getNoonOfDay(date, calendar);
		System.out.println(date);
		System.out.println(endOfMonth);
	}
	
	@Test
	public void testtimeSetTo0(){
		Date date = new Date();
		Date date1 = DateUtils.timeSetTo0(date).getTime();
		System.out.println(date);
		System.out.println(date1);
	}
	
	@Test
	public void testgetDayRegion(){
		Date date = new Date();
		Date[] ds = DateUtils.getDayRegion(date);
		for(Date d : ds){
			System.out.println(d);
		}
	}
	
	@Test
	public void testgetWeekRegion(){
		Date date = new Date();
		Date[] ds = DateUtils.getWeekRegion(date);
		System.out.println(DateUtils.formatDate(date));
		for(Date d : ds){
			System.out.println(DateUtils.formatDate(d));
		}
	}
	
	@Test
	public void testgetNowWeekRegion(){
		Date date = new Date();
		Date[] ds = DateUtils.getNowWeekRegion(date);
		System.out.println(DateUtils.formatDate(date));
		for(Date d : ds){
			System.out.println(DateUtils.formatDate(d));
		}
	}
	
	@Test
	public void testgetMonthRegion(){
		Date date = new Date();
		Date[] ds = DateUtils.getMonthRegion(date);
		System.out.println(DateUtils.formatDate(date));
		for(Date d : ds){
			System.out.println(DateUtils.formatDate(d));
		}
	}
	
	@Test
	public void testgetYearRegion(){
		Date date = new Date();
		Date[] ds = DateUtils.getYearRegion(date);
		System.out.println(DateUtils.formatDate(date));
		for(Date d : ds){
			System.out.println(DateUtils.formatDate(d));
		}
	}
	
	@Test
	public void testgetStartOfDayMillis(){
		Date date = new Date();
		long m = DateUtils.getStartOfDayMillis(System.currentTimeMillis());
		System.out.println(DateUtils.formatDate(date));
		System.out.println(m);
		System.out.println(System.currentTimeMillis());
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testgetWeekMap(){
		String nowDate = "2012-01-25";
		String endDate = "2012-01-30";
		Map map = DateUtils.getWeekMap(DateUtils.parseShortDate(nowDate), DateUtils.parseShortDate(endDate));
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			Entry en = (Entry) it.next();
			System.out.println("key: " + en.getKey() + "---" + "value: " + en.getValue());
			/*Map valueMap = (Map) en.getValue();
			Iterator i = valueMap.entrySet().iterator();
			while(i.hasNext()){
				Entry e = (Entry) i.next();
				System.out.println("key: " + e.getKey() + "---" + "value: " + e.getValue());
			}*/
		}
	}
	
	@Test
	public void testgetStartDate(){
		System.out.println(DateUtils.formatDate(new Date()));
		System.out.println(DateUtils.formatDate(DateUtils.getStartDate(3, new Date())));
	}

}
