package com.hkha.ea.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out

public class DateTimeUtil {
	
	//20231201 Write log in catalina.out
//	private static Log log = LogFactory.getLog(LogFactory.class);
	private static Logger log = Logger.getLogger(DateTimeUtil.class.getName());
	//End 20231201 Write log in catalina.out

	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String DB_DATE_FORMAT = "yyyy-MM-dd";

	public static String date2String(Date iDate) {
		return date2String(iDate, DATE_FORMAT);
	}

	public static String date2String(Date iDate, String dateFormat) {
		
		SimpleDateFormat simpleDateFormat;
		String oDate = null;

		try {
			if (iDate != null) {
				simpleDateFormat = new SimpleDateFormat(dateFormat);
				oDate = simpleDateFormat.format(iDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.severe(e.toString());
		}

		return (oDate);
	}

	public static Date string2Date(String sDate) {
		return string2Date(sDate, DATE_FORMAT);
	}

	public static Date dbDateString2Date(String sDate){
		return string2Date(sDate, DB_DATE_FORMAT);
	}
	
	public static String date2DbDateStr(Date sDate) {
		return date2String(sDate, "yyyy-MM-dd");
	}

	public static Date string2Date(String sDate, String fmt) {
		SimpleDateFormat df = new SimpleDateFormat(fmt);
		Date date1 = null;

		try {
			date1 = df.parse(sDate);
		} catch (ParseException pe) {
			pe.printStackTrace();
			log.severe(pe.toString());
		} catch (Exception ex) {
			ex.printStackTrace(); 
			log.severe(ex.toString());
		}
		return (date1);
	}
  
  public static java.sql.Date addDays(java.sql.Date inDate, long noOfDays) {
    return new java.sql.Date(inDate.getTime() + (noOfDays * 24 * 60 * 60 * 1000));
  }

  public static java.util.Date addDays(java.util.Date inDate, long noOfDays) {
	return new java.util.Date(inDate.getTime() + (noOfDays * 24 * 60 * 60 * 1000));
  }

	public static int dayDiff(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		long ldate1 =
			date1.getTime()
				+ cal1.get(Calendar.ZONE_OFFSET)
				+ cal1.get(Calendar.DST_OFFSET);
		long ldate2 =
			date2.getTime()
				+ cal2.get(Calendar.ZONE_OFFSET)
				+ cal2.get(Calendar.DST_OFFSET);
		int hr1 = (int) (ldate1 / 3600000);
		int hr2 = (int) (ldate2 / 3600000);
		int days1 = (int) hr1 / 24;
		int days2 = (int) hr2 / 24;

		int dayDiff = days2 - days1;
		return (dayDiff);
	}

	public static int weekDiff(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		long ldate1 =
			date1.getTime()
				+ cal1.get(Calendar.ZONE_OFFSET)
				+ cal1.get(Calendar.DST_OFFSET);
		long ldate2 =
			date2.getTime()
				+ cal2.get(Calendar.ZONE_OFFSET)
				+ cal2.get(Calendar.DST_OFFSET);
		int hr1 = (int) (ldate1 / 3600000);
		int hr2 = (int) (ldate2 / 3600000);
		int days1 = (int) hr1 / 24;
		int days2 = (int) hr2 / 24;

		int dayDiff = days2 - days1;
		int weekOffset =
			(cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK))
				< 0	? 1	: 0;
		int weekDiff = dayDiff / 7 + weekOffset;

		return (weekDiff);
	}

	public static int monthDiff(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		int monthDiff =
			yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
		return (monthDiff);
	}

	public static int yearDiff(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		return (yearDiff);
	}

	public static Date getTodayDate() {
		return (new Date());
	}

	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	} // getYear

	//======================================================================
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return (cal.get(Calendar.MONTH) + 1);
	} // getMonth

	//======================================================================
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	} // getDay

	public static int getHour(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR);		
	}
	
	public static int getMinute(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);		
	}	
	
	public static double getSecond(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.SECOND);		
	}	

	public static void main(String[] args) {
		Date d1 = string2Date("12/03/2004");
		Date d2 = string2Date("16/05/2005");

		System.out.println("Day diff:" + dayDiff(d1, d2));
		System.out.println("Week diff:" + weekDiff(d1, d2));
		System.out.println("Month diff:" + monthDiff(d1, d2));
		System.out.println("Year diff:" + yearDiff(d1, d2));
		System.out.println("Get day:" + getDay(d1));
		System.out.println("Get month:" + getMonth(d2));
		System.out.println("Get year:" + getYear(d1));
		System.out.println("Get current date:" + getTodayDate());
		System.out.println("Date2String:" + date2String(d1));
	}
	
	public static Date getEndOfNextMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE,1);
		if (getMonth(cal.getTime())==12){
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.YEAR, getYear(cal.getTime())+1);
		}else
			cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
		cal.set(Calendar.DATE, dayInmonth(cal.get(Calendar.MONTH)+1));
		return cal.getTime();
			
/* -- Modified in 10/5/2005 -- Wrong date would be given out from the below method		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.roll(Calendar.MONTH, 1);
		int daysOfMonth = dayInmonth(cal.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.DATE, daysOfMonth);
		return cal.getTime();
*/
	}
	
	public static Date addToEndOfNextMonth(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			int daysOfMonth = dayInmonth(cal.get(Calendar.DAY_OF_MONTH));
			cal.set(Calendar.DATE, daysOfMonth);
			return cal.getTime();
		}  

	public static Date getFirstOfPerviousMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (getMonth(cal.getTime())==1)
			cal.set(Calendar.MONTH, Calendar.DECEMBER);
		else
			cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)-1);
		cal.set(Calendar.DATE, 1);
		if (getMonth(cal.getTime())==12)
			cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-1);		
		return cal.getTime();
	}
	
	//Checked and Corrected in 24/3/2005
	public static Date getEndOfPerviousMonth(Date date){
		int perviousMonth;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		if (getMonth(cal.getTime())==Calendar.JANUARY)
			perviousMonth = Calendar.DECEMBER;
		else
			perviousMonth = cal.get(Calendar.MONTH)-1;
		cal.set(Calendar.DATE,1);
		cal.set(Calendar.MONTH, perviousMonth);
		cal.set(Calendar.DATE,dayInmonth(perviousMonth+1));					
		if (perviousMonth==Calendar.DECEMBER)
			cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-1);				
		return cal.getTime();		
	}
	
	public static Date getFirstOfThisMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE,1);
		return cal.getTime();				
	}
	
	public static Date getEndOfThisMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE,dayInmonth(getMonth(date)));
		return cal.getTime();		
	}	
	
	public static Date getFirstOfThisYear(int year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH,Calendar.JANUARY);
		cal.set(Calendar.DATE,1);
		return cal.getTime();	
	}

//	-----------------------------------------------------------------------------------------------
//	-----------------------------------------------------------------------------------------------
//	-----------------------------------------------------------------------------------------------

	static final long SECOND = 1000L ;
	static final long MINUTE = (long)60*SECOND ;
	static final long HOUR = (long)60*MINUTE ;
	static final long DAY = (long)24*HOUR ;
	static final long WEEK = (long)7*DAY ;

	
	//======================================================================
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(date) ;
		return cal.get(Calendar.DAY_OF_WEEK) ;
	}

	public static int getDayOfWeekStMon(Date date) {
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(date) ;
		if (cal.get(Calendar.DAY_OF_WEEK) == 2) return 1;
		else if (cal.get(Calendar.DAY_OF_WEEK) == 3) return 2;
		else if (cal.get(Calendar.DAY_OF_WEEK) == 4) return 3;
		else if (cal.get(Calendar.DAY_OF_WEEK) == 5) return 4;
		else if (cal.get(Calendar.DAY_OF_WEEK) == 6) return 5;
		else if (cal.get(Calendar.DAY_OF_WEEK) == 7) return 6;
		else if (cal.get(Calendar.DAY_OF_WEEK) == 1) return 7;
		return 0;
	}
	
	public static int dayInmonth (int monthNum){
		int numDays=0;
		if (monthNum == 1
		  || monthNum == 3
		  || monthNum == 5
		  || monthNum == 7
		  || monthNum == 8
		  || monthNum == 10
		  || monthNum == 12) {
			numDays = 31;
		} else if (monthNum == 4 || monthNum == 6 || monthNum == 9 || monthNum == 11) {
			numDays = 30;
		} else if (monthNum % 4 == 0) {
			numDays = 29;
		} else {
			numDays = 28;
		}

		
		return numDays;
	}

	public static float calNumMonth(Date stDate, Date endDate) {
	  Calendar calSt = Calendar.getInstance();
	  Calendar calEnd = Calendar.getInstance();
	  calSt.setTime(stDate);
	  calEnd.setTime(endDate);
	  int stDateYear = calSt.get(Calendar.YEAR);
	  int stDateMonth = calSt.get(Calendar.MONTH) + 1;
	  int stDateDay = calSt.get(Calendar.DAY_OF_MONTH);
	  int endDateYear = calEnd.get(Calendar.YEAR);
	  int endDateMonth = calEnd.get(Calendar.MONTH) + 1;
	  int endDateDay = calEnd.get(Calendar.DAY_OF_MONTH);
//	  int diffYear = 0;
//	  int diffMonth = 0;
//	  int diffDay = 0;

	  int monthOfSt = 0;
	  int monthOfEnd = 0;

	  float rtnVal = 0;

		
	monthOfSt = dayInmonth(stDateMonth);
	monthOfEnd = dayInmonth(endDateMonth);
	rtnVal += 1.0 * ( ((endDateYear - stDateYear) * 12) + 
					  (endDateMonth - stDateMonth) + 
					  ((1.0 * endDateDay / monthOfEnd) - (1.0 * (stDateDay - 1) / monthOfSt))
					);

	  return rtnVal;
	}
	
	public static int compare(Date d1, Date d2) {
		final int equal = 0 ;
		final int larger = 1 ;
		final int smaller = -1 ;
		if (yearDiff(d2, d1)>0) {
			return larger ;
		}
		else if(yearDiff(d1, d2)>0){
			return smaller ;						
		}
		else {
			if (monthDiff(d2, d1)>0) {
				return larger ;
			}
			else if (monthDiff(d1, d2)>0) {
				return smaller ;
			}
			else {
				if (dayDiff(d2, d1)>0) {
					return larger ;
				}
				else if (dayDiff(d1, d2)>0) {
					return smaller ;
				}
				else {
					return equal ;
				}
			}
		}
	}
	
	public static int compare(String d1, String d2) {
		return compare(string2Date(d1), string2Date(d2)) ;
	}

	public static void addDay(Date d, int amount) {
		long time = d.getTime() ;
		time = time + (long)(DAY*amount) ;
		d.setTime(time) ;
	}

	public static Date addDayReturn(Date d, int amount) {
		Date result = null;
		long time = d.getTime() ;
		time = time + (long)(DAY*amount) ;
		result = new Date();
		result.setTime(time) ;	
		return result;		
	}

	public static Date addMinutes(Date d, int minutes){
		Date result = null;
		long time = d.getTime();
		time += (long)(SECOND*minutes);
		result = new Date();
		result.setTime(time);
		return result;
	}

	public static Timestamp addTimestampReturn(Timestamp d, int amount) {
		Timestamp result = null;
		long time = d.getTime() ;
		time = time + (long)(DAY*amount) ;
		result = new Timestamp(0);
		result.setTime(time) ;	
		return result;		
	}

	public static Timestamp getCurrentTimestamp() {
		Timestamp ts = new Timestamp(new Date().getTime());
		return ts;
	}
	
	public static boolean isValidDateFormat(String d1, String f1) {
		java.util.Date d = DateTimeUtil.string2Date(d1, f1);
		return (d == null) ? false : DateTimeUtil.date2String(d, f1).equals(d1);
	}
	
	public static java.sql.Date toSQLDate(java.util.Date date) {
		java.sql.Date rtnDate = null;

		if (date != null)
			rtnDate = new java.sql.Date(date.getTime());
		return rtnDate;
	}

	public static java.util.Date toUtilDate(java.sql.Date date) {
		java.util.Date rtnDate = null;

		if (date != null)
			rtnDate = new java.util.Date(date.getTime());
		return rtnDate;
	}
}
