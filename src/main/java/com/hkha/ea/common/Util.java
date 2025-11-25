package com.hkha.ea.common;

//import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import java.net.InetAddress;
import java.net.UnknownHostException;

//import com.salmonllc.util.MessageLog;
//import com.salmonllc.html.HtmlDropDownList;
//import com.salmonllc.html.HtmlListBox;
//import com.salmonllc.html.HtmlRadioButtonGroup;
//import com.salmonllc.sql.DBConnection;
//import com.salmonllc.sql.DataStore;
//import com.salmonllc.sql.DataStoreBuffer;
//import com.salmonllc.sql.DataStoreException;
//import com.salmonllc.properties.Props;

public class Util implements Constants {
	public static String appName = null; 
	public static String serverURL = null; 

public static boolean isEmptyString(String str) {
if (str == null) {
  return true;
} else if (str.trim().equals("")) {
  return true;
}

return false;
}

public static String nullToString( Object obj) {
if ( obj == null) {
  return "";
} else {
  return obj.toString();
}
}

public static Date parseDisplayDate(String displayDate) throws ParseException {
Date result = null;
SimpleDateFormat df = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
result = df.parse(displayDate.trim());

return result;
}

public static String date2String(Date iDate) {
return date2String(iDate, DISPLAY_DATE_FORMAT);
}

public static String date2String(Date iDate, String dateFormat) {
SimpleDateFormat simpleDateFormat;
String oDate;

try {
  if (iDate == null)
    return "";

  simpleDateFormat = new SimpleDateFormat(dateFormat);
  return simpleDateFormat.format(iDate);
} catch (Exception e) {
  return "";
}
}

public static Date string2Date(String iDate, String dateFormat) {
SimpleDateFormat simpleDateFormat;
String oDate;

try {
  if (iDate == null)
    return null;

  simpleDateFormat = new SimpleDateFormat(dateFormat);
  return simpleDateFormat.parse(iDate);
} catch (Exception e) {
  return null;
}
}

public static String int2String(int iInt, String format) {
return double2String(iInt, format);
}

public static String double2String(double idouble, String format) {
NumberFormat numberFormat = new DecimalFormat(format);
try {
  return numberFormat.format(idouble);
} catch (Exception e) {
  return null;
}
}

public static Double string2double(String str) {
try {
  double iDouble = Double.parseDouble(str);
  return new Double(iDouble);
} catch (Exception e) {
  return null;
}
}

/**
* concate a list of String or Integer to a comma delimited string   
* @param values list of String or Integer
* @return comma delimited string
*/
public static String concatValues(List values) {
return concatValues(values, "'", ",");
}

/**
* concate a list of String or Integer to a comma delimited string   
* @param values list of String or Integer
* @return comma delimited string
*/
public static String concatValues(List values, String quote) {
	return concatValues(values, quote, ",");
}  

/**
* concate a list of String or Integer 
* @param values list of String or Integer
* @param quote
* @param delimiter
* @return comma delimited string
*/
public static String concatValues(List values, String quote, String delimiter) {
	if (values == null || values.size() <= 0)
	  return "";

	if (quote == null)
		quote = "";
	
	if (delimiter == null)
		delimiter = "";	 

	StringBuffer criteria = new StringBuffer();
	if (values.get(0) instanceof String) {
	  String de = "";
	  for (int i = 0; i < values.size(); i++) {
		criteria.append(de);
		criteria.append(quote + (String) values.get(i) + quote);
		de = delimiter;
	  }
	} else if (values.get(0) instanceof Integer) {
	  String de = "";
	  for (int i = 0; i < values.size(); i++) {
		criteria.append(de);
		criteria.append(((Integer) values.get(i)).intValue());
		de = delimiter;
	  }
	}
	return criteria.toString();
}    

/**
* concate a set of String or Integer 
* @param values list of String or Integer
* @param quote
* @param delimiter
* @return comma delimited string
*/
public static String concatValues(Set values, String quote, String delimiter) {
	if (values == null || values.size() <= 0)
	  return "";
	
	if (quote == null)
		quote = "";
	
	if (delimiter == null)
		delimiter = "";	 
	  
	String de = "";
	StringBuffer criteria = new StringBuffer();
	Iterator itr = values.iterator();
	while (itr.hasNext()) {
	  Object obj = itr.next();
	  if (obj instanceof String) {
		criteria.append(de);
		criteria.append(quote + (String) obj + quote);
		de = delimiter;
	  } else if (obj instanceof Integer) {
		criteria.append(de);
		criteria.append(String.valueOf((Integer) obj));
		de = delimiter;
	  }
	}
	return criteria.toString();
}  

/**
* concate a set of String or Integer to a comma delimited string   
* @param values list of String or Integer
* @return comma delimited string
*/
public static String concatValues(Set values) {
return concatValues(values, "'", ",");
}

/**
* concate a list of String or Integer to a comma delimited string   
* @param list of String or Integer
* @return comma delimited string
*/
//public static String concatValues(Object[] values) {  	
//	return concatValues(values, "'", ",");	
//}    

/**
* concate a list of String or Integer to a comma delimited string   
* @param list of String or Integer
* @return comma delimited string
*/
//public static String concatValues(Object[] values, String quote, String del) {
//	if (values == null || values.length <= 0)
//	  return "";
//
//	StringBuffer criteria = new StringBuffer();
//	if (values instanceof String[]) {
//	  String delimiter = "";
//	  for (int i = 0; i < values.length; i++) {
//		criteria.append(delimiter);
//		criteria.append(quote + values[i] + quote);
//		delimiter = del;
//	  }
//	}else if (values instanceof Integer[]) {
//		String delimiter = "";
//		for (int i = 0; i < values.length; i++) {
//		  criteria.append(delimiter);
//		  criteria.append(((Integer)values[i]).intValue());
//		  delimiter = del;
//		}		
//	}else if (values instanceof java.util.Date[]) {
//		String delimiter = "";
//		for (int i = 0; i < values.length; i++) {
//		  criteria.append(delimiter);
//		  criteria.append(quote + DateUtil.date2DbDateStr((java.util.Date)values[i]) + quote);
//		  delimiter = del;
//		}		
//	}
//	
//	return criteria.toString();
//}  

/**
* convert java.util.Date instance to java.sql.Timestamp instance
* @param date java.util.Date
* @return java.sql.Timestamp
*/
public static java.sql.Timestamp date2Timestamp(java.util.Date date) {
Calendar cal = Calendar.getInstance();
cal.setTime(date);
return new java.sql.Timestamp(cal.getTimeInMillis());
}

//public static int getDataStoreStatusCount(DataStore ds, int status) {
//int statusCount = 0;
//
//if (ds == null)
//  return statusCount;
//
//for (int i = 0; i < ds.getRowCount(); i++) {
//  if (ds.getRowStatus(i) == status) {
//    statusCount++;
//  }
//}

//return statusCount;
//}

static public int dayDiff(Date first, Date second) {

// returns the difference, in days, between the first
// and second Date arguments

long msPerDay = 1000 * 60 * 60 * 24;
long diff = (first.getTime() / msPerDay) - (second.getTime() / msPerDay);
Long convertLong = new Long(diff);
return convertLong.intValue();
} // dayDiff

private static int calendarMonthToInt(int calendarMonth) {

if (calendarMonth == Calendar.JANUARY)
  return 1;
else if (calendarMonth == Calendar.FEBRUARY)
  return 2;
else if (calendarMonth == Calendar.MARCH)
  return 3;
else if (calendarMonth == Calendar.APRIL)
  return 4;
else if (calendarMonth == Calendar.MAY)
  return 5;
else if (calendarMonth == Calendar.JUNE)
  return 6;
else if (calendarMonth == Calendar.JULY)
  return 7;
else if (calendarMonth == Calendar.AUGUST)
  return 8;
else if (calendarMonth == Calendar.SEPTEMBER)
  return 9;
else if (calendarMonth == Calendar.OCTOBER)
  return 10;
else if (calendarMonth == Calendar.NOVEMBER)
  return 11;
else if (calendarMonth == Calendar.DECEMBER)
  return 12;
else
  return 1;

} // calendarMonthToInt

//======================================================================
static public int getYear(Date date) {

Calendar cal = new GregorianCalendar();
cal.setTime(date);
return cal.get(Calendar.YEAR);
} // getYear

//======================================================================
static public int getMonth(Date date) {

Calendar cal = new GregorianCalendar();
cal.setTime(date);
int calendarMonth = cal.get(Calendar.MONTH);
return calendarMonthToInt(calendarMonth);
} // getMonth

//======================================================================
static public int getDay(Date date) {

Calendar cal = new GregorianCalendar();
cal.setTime(date);
return cal.get(Calendar.DAY_OF_MONTH);
} // getDay

public static float calNumMonth(Date st_date, Date end_date) {
Calendar cal_st = Calendar.getInstance();
Calendar cal_end = Calendar.getInstance();
cal_st.setTime(st_date);
cal_end.setTime(end_date);
int st_date_year = cal_st.get(Calendar.YEAR);
int st_date_month = cal_st.get(Calendar.MONTH) + 1;
int st_date_day = cal_st.get(Calendar.DAY_OF_MONTH);
int end_date_year = cal_end.get(Calendar.YEAR);
int end_date_month = cal_end.get(Calendar.MONTH) + 1;
int end_date_day = cal_end.get(Calendar.DAY_OF_MONTH);
int diff_year = 0;
int diff_month = 0;
int diff_day_st = 0;
int diff_day_end = 0;

int month_of_st = 0;
int month_of_end = 0;

float rtn_val = 0;
float test_val = 0;

if (st_date_month == 1 || st_date_month == 3 || st_date_month == 5 || st_date_month == 7 || st_date_month == 8 || st_date_month == 10 || st_date_month == 12) {
  month_of_st = 31;
} else if (st_date_month == 4 || st_date_month == 6 || st_date_month == 9 || st_date_month == 11) {
  month_of_st = 30;
} else if (st_date_year % 4 == 0) {
  month_of_st = 29;
} else {
  month_of_st = 28;
}

if (end_date_month == 1 || end_date_month == 3 || end_date_month == 5 || end_date_month == 7 || end_date_month == 8 || end_date_month == 10 || end_date_month == 12) {
  month_of_end = 31;
} else if (end_date_month == 4 || end_date_month == 6 || end_date_month == 9 || end_date_month == 11) {
  month_of_end = 30;
} else if (end_date_year % 4 == 0) {
  month_of_end = 29;
} else {
  month_of_end = 28;
}

if (end_date_year > st_date_year) {
  diff_year = end_date_year - st_date_year - 1;
  diff_month = (12 - st_date_month) + (end_date_month - 1);
  diff_day_st = month_of_st - st_date_day + 1;
  diff_day_end = end_date_day - 1 + 1;
} else if (end_date_month > st_date_month) {
  diff_month = end_date_month - st_date_month - 1;
  diff_day_st = month_of_st - st_date_day + 1;
  diff_day_end = end_date_day - 1 + 1;
} else { //month equal
  diff_day_st = 0;
  diff_day_end = 0;
}

rtn_val = diff_year * 12 + diff_month;

if (diff_day_st == 0) {
  rtn_val += (1.0 * end_date_day - st_date_day + 1) / month_of_st;
} else if ((diff_day_st != month_of_st) && (diff_day_end != month_of_end)) {
  rtn_val += (1.0 * diff_day_st / month_of_st + diff_day_end / month_of_end) / 2;
} else {
  rtn_val += (diff_day_st == month_of_st ? 1 : 1.0 * diff_day_st / month_of_st) + (diff_day_end == month_of_end ? 1 : 1.0 * diff_day_end / month_of_end);
}

return rtn_val;
}

/**
* Round a float value to a specified number of decimal 
* places.
*
* @param val the value to be rounded.
* @param places the number of decimal places to round to.
* @return val rounded to places decimal places.
*/
public static float round(float val, int places) {
return (float) round((double) val, places);
}

/**
	* Round a double value to a specified number of decimal 
	* places.
	*
	* @param val the value to be rounded.
	* @param places the number of decimal places to round to.
	* @return val rounded to places decimal places.
	*/
public static double round(double val, int places) {
long factor = (long) Math.pow(10, places);

// Shift the decimal the correct number of places
// to the right.
val = val * factor;

// Round to the nearest integer.
long tmp = Math.round(val);

// Shift the decimal the correct number of places
// back to the left.
return (double) tmp / factor;
}

//public static void buildDropDownList(DataStoreBuffer ds, HtmlDropDownList ddl, String valueCol, String descCol) {
//buildDropDownList(ds, ddl, valueCol, descCol, null, 0, null);
//}
//
//public static void buildDropDownList(DataStoreBuffer ds, HtmlDropDownList ddl, String valueCol, String descCol, int maxSize) {
//buildDropDownList(ds, ddl, valueCol, descCol, null, maxSize, null);
//}
//
//public static void buildDropDownList(DataStoreBuffer ds, HtmlDropDownList ddl, String valueCol, String descCol, int maxSize, String firstOption) {
//buildDropDownList(ds, ddl, valueCol, descCol, null, maxSize, firstOption);
//}
//
//public static void buildDropDownList(DataStoreBuffer ds, HtmlDropDownList ddl, String valueCol, String descCol1, String descCol2) {
//buildDropDownList(ds, ddl, valueCol, descCol1, descCol2, 0, null);
//}
//
//public static void buildDropDownList(DataStoreBuffer ds, HtmlDropDownList ddl, String valueCol, String descCol1, String descCol2, int maxSize) {
//buildDropDownList(ds, ddl, valueCol, descCol1, descCol2, maxSize, null);
//}
//
//public static void buildDropDownList(DataStoreBuffer ds, HtmlDropDownList ddl, String valueCol, String descCol1, String descCol2, int maxSize, String firstOption) {
//ddl.resetOptions();
//if (firstOption != null) {
//  String desc = null;
//  if (firstOption.equals("")) {
//    desc = "-- Please Select --";
//  } else {
//    desc = firstOption;
//  }
//  ddl.addOption(null, desc);
//}
//
//for (int i = 0; i < ds.getRowCount(); i++) {
//  try {
//    String value = ds.getAny(i, valueCol) == null ? "" : ds.getAny(i, valueCol).toString().trim();
//    String desc = ds.getAny(i, descCol1) == null ? "" : ds.getAny(i, descCol1).toString().trim();
//    if (descCol2 != null) {
//      desc = desc + " - " + (ds.getAny(i, descCol2) == null ? "" : ds.getAny(i, descCol2).toString().trim());
//    }
//
//    if (maxSize > 3) {
//      if (desc.length() > maxSize) {
//        desc = desc.substring(0, maxSize - 3) + "...";
//      }
//    } else if (maxSize > 0 && desc.length() > maxSize) {
//      desc = desc.substring(0, maxSize);
//    }
//    ddl.addOption(value, desc);
//  } catch (DataStoreException e) {        
//    e.printStackTrace();
//  }
//}
//}
//
//public static void buildListBox(DataStoreBuffer ds, HtmlListBox lb, String valueCol, String descCol) {
//buildListBox(ds, lb, valueCol, descCol, null, 0, null);
//}
//
//public static void buildListBox(DataStoreBuffer ds, HtmlListBox lb, String valueCol, String descCol, int maxSize) {
//buildListBox(ds, lb, valueCol, descCol, null, maxSize, null);
//}
//
//public static void buildListBox(DataStoreBuffer ds, HtmlListBox lb, String valueCol, String descCol, int maxSize, String firstOption) {
//buildListBox(ds, lb, valueCol, descCol, null, maxSize, firstOption);
//}
//
//public static void buildListBox(DataStoreBuffer ds, HtmlListBox lb, String valueCol, String descCol1, String descCol2) {
//buildListBox(ds, lb, valueCol, descCol1, descCol2, 0, null);
//}
//
//public static void buildListBox(DataStoreBuffer ds, HtmlListBox lb, String valueCol, String descCol1, String descCol2, int maxSize) {
//buildListBox(ds, lb, valueCol, descCol1, descCol2, maxSize, null);
//}
//
//public static void buildListBox(DataStoreBuffer ds, HtmlListBox lb, String valueCol, String descCol1, String descCol2, int maxSize, String firstOption) {
//lb.resetOptions();
//if (firstOption != null) {
//  String desc = null;
//  if (firstOption.equals("")) {
//    desc = "-- Please Select --";
//  } else {
//    desc = firstOption;
//  }
//  lb.addOption(null, desc);
//}
//
//for (int i = 0; i < ds.getRowCount(); i++) {
//  try {
//    String value = ds.getAny(i, valueCol).toString().trim();
//    String desc = ds.getAny(i, descCol1).toString().trim();
//    if (descCol2 != null) {
//      desc = desc + " - " + ds.getAny(i, descCol2).toString().trim();
//    }
//
//    if (maxSize > 3) {
//      if (desc.length() > maxSize) {
//        desc = desc.substring(0, maxSize - 3) + "...";
//      }
//    } else if (maxSize > 0 && desc.length() > maxSize) {
//      desc = desc.substring(0, maxSize);
//    }
//    lb.addOption(value, desc);
//  } catch (DataStoreException e) {
//    
//    e.printStackTrace();
//  }
//}
//}
//
//public static void buildRadioGroup(DataStoreBuffer ds, HtmlRadioButtonGroup rbg, String valueCol, String descCol) {
//for (int i = 0; i < ds.getRowCount(); i++) {
//  try {
//    String value = ds.getAny(i, valueCol).toString().trim();
//    String desc = ds.getAny(i, descCol).toString().trim();
//    rbg.addOption(value, desc);
//  } catch (DataStoreException ex) {
//    ex.printStackTrace();
//  }
//}
//}

public static Date getCurDate() {
Date iDate = new Date();
return (iDate);
}

public static void writeTextFile(String filePath, String string, String charset) throws Exception {
File file;
FileOutputStream fileOutputStream = null;
InputStream inputStream;
int data;

try {
  file = new File(filePath);
  fileOutputStream = new FileOutputStream(file);

  inputStream = new ByteArrayInputStream(string.getBytes(charset));

  while ((data = inputStream.read()) != -1) {
    fileOutputStream.write(data);
  }
} catch (Exception e) {
  throw e;
} finally {
  if (fileOutputStream != null) {
    fileOutputStream.close();
  }
}
}

public static void writeTextFileWithByte(String filePath, Vector byteVector) throws Exception {
	File file;
	FileOutputStream fileOutputStream = null;
	InputStream inputStream;
	int data;

	try {
	  file = new File(filePath);
	  fileOutputStream = new FileOutputStream(file);

//	  Byte[] byteObjArray = (Byte[])byteVector.toArray();
	  byte[] byteArray = new byte[byteVector.size()];
	  for (int i=0; i<byteVector.size(); i++){	  
	  	byteArray[i] = ((Byte)byteVector.get(i)).byteValue();
	  }
	  inputStream = new ByteArrayInputStream(byteArray);

	  while ((data = inputStream.read()) != -1) {
		fileOutputStream.write(data);
	  }
	} catch (Exception e) {
	  throw e;
	} finally {
	  if (fileOutputStream != null) {
		fileOutputStream.close();
	  }
	}
}


public static void writeBinaryFile(String filePath, Vector content) throws Exception {
	File file;
	DataOutputStream dout = null;
	InputStream inputStream;
	int data;

	try {
	  file = new File(filePath);
	  dout = new DataOutputStream(new FileOutputStream(filePath));
	  
	  for (int i=0; i<content.size(); i++) {
		dout.writeByte(((Integer)content.get(i)).intValue());		
//		dout.writeByte(0X3A);
	  }
	} catch (Exception e) {
	  throw e;
	} finally {
	  if (dout != null){
		dout.close();
	  }
	}
}

public static Object[] ReadTextFile(String filePath, String charset) throws Exception {
		  File file;
		  FileInputStream fileInputStream = null;
		  InputStreamReader isr = null;		
		  BufferedReader br = null;
		  Vector v = new Vector();
		  String data;		
		  Object[] o = null;

		  try {
			file = new File(filePath);
			fileInputStream = new FileInputStream(file);
			isr = new InputStreamReader(fileInputStream, charset);
			br = new BufferedReader(isr);

			while ((data = br.readLine()) != null) {			
			  v.add(data);
			
			}		  		  
			o=v.toArray();	  
		  		  
		  } catch (Exception e) {
			throw e;
			
		  } finally {
			
			if (fileInputStream != null) {
			  	fileInputStream.close();
			}
			if (br != null) {
				br.close();
			}
		  }
		  return o;		 
		}



public static String getFlagString(boolean b) {
return (b ? "Y" : "N");
}

public static boolean getFlagBoolean(String b) {
return (b.equals("Y"));
}

public static boolean isEqual(String str1, String str2) {
if (str1 == null) {
  if (str2 == null) {
    return true;
  } else {
    return false;
  }
} else {
  return str1.equals(str2);
}
}

//public static int getLastIdentity(DBConnection conn) throws SQLException {
//Statement st = conn.createStatement();
//ResultSet rs = st.executeQuery("select IDENTITY_VAL_LOCAL() from SYSIBM.SYSDUMMY1");
//rs.next();
//return rs.getInt(1);
//}

public static String stringSizeChecking(String inString, int len) {
		  String ret = "";

		  if ((inString == null) || (inString.equals(""))) {
			  for (int i = 0; i < len; i++) {
				  ret += ' ';
			  }
			  return ret;
		  } else if (inString.length() == len)
			  return inString;
		  else if (inString.length() < len) {
			  ret = inString;
			  for (int i = inString.length(); i < len; i++) {
				  ret += ' ';
			  }
		  } else if (inString.length() > len) {
			  ret = inString.substring(0, len);
		  }
		  return ret;
	  }

	public static Date getDateFromReportFormat(String str) throws ParseException {
			
			Date d = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			d = df.parse(str);
			return d;
		}
	
	public static Object[] getFileListFromDir(String fileDir, String prefix, String ext) {
	 		
			File f= new File(fileDir);
	 		
			if(f.isFile()){
				Object[] o = new Object[1];
				o[0] = f.toString();	 		
				return o;
			}	 			
	 					
			File[] fileNames = f.listFiles();
			
			Vector v = new Vector();	
			
	 		if(fileNames != null) {					
	 		
				for(int i=0; i < fileNames.length; i++){
	 			
					String reg = "^.+("+ prefix + ").+" + ext + "$";	 				 			
	 			
					String filename = fileNames[i].toString(); 			
	 			
					if(fileNames[i].isFile() && filename.matches(reg)){	 				 			
						v.add(filename);	 				
						}	 			
				}		
	 		} else return null;
	 		
			Collections.sort(v); 		
			return v.toArray(); 	 	
	 }
	 
	 
	public static Object[] getFileListShortNameFromDir(String fileDir, String prefix, String ext) {
	 		
				File f= new File(fileDir);
	 		
				if(f.isFile()){
					Object[] o = new Object[1];
					o[0] = f.toString();	 		
					return o;
				}	 			
	 					
				File[] fileNames = f.listFiles();
			
				Vector v = new Vector();	
			
				if(fileNames != null) {					
	 		
					for(int i=0; i < fileNames.length; i++){
	 			
						String reg = "^.+("+ prefix + ").+" + ext + "$";	 				 			
	 			
						String filename = fileNames[i].getName(); 			
	 			
						if(fileNames[i].isFile() && filename.matches(reg)){	 				 			
							v.add(filename);	 				
							}	 			
					}		
				} else return null;
	 		
				Collections.sort(v); 		
				return v.toArray(); 	 	
		 }
	 
	 
	//	for generate report name
	 public static String getInterfaceDateTimeFilename() {
		 String ret = "";
		 Date d = new Date();
		 SimpleDateFormat df = new SimpleDateFormat(Constants.INTERFACE_FILENAME_DATETIME_FORMAT);
		 ret = df.format(d);
		 return ret;
	 }
	 
	/**
	 * move file from one location to another
	 * @param sourceFilePath source file path
	 * @param toFilePath
	 * @param overwrite
	 */	 
	 public static void moveFile(String sourceFilePath, String toFilePath, boolean overwrite){
		moveFile(new File(sourceFilePath), new File (toFilePath), overwrite);
	 }
	
	/**
	 * move file from one location to another
	 * @param sourceFile
	 * @param toFile
	 * @param overwrite
	 */	
	 public static void moveFile(File sourceFile, File toFile, boolean overwrite){
	 	if (!sourceFile.isFile() || !toFile.isFile())
	 		return;
	 	if (!sourceFile.exists())
	 		return;
	 	
		File outFile = toFile;		
	 	//not overwrite, append verion no to the end of file name
	 	if (!overwrite && outFile.exists()){
	 		boolean found = false;
	 		int appendix = 1;	 		
			
	 		while (!found){
				outFile = new File(getFilePath(toFile.getPath(), "_"+appendix));
				if (!outFile.exists())
					found = true;
				appendix++;	
	 		}
	 	}
		sourceFile.renameTo(outFile);
		sourceFile.delete();
	 }
	 
	/**
	 * move file from one location to another
	 * @param sourceFile
	 * @param toDir
	 * @param overwrite
	 */	
	 public static void moveFile(File sourceFile, String toDir, boolean overwrite){
		if (!sourceFile.isFile() || !sourceFile.exists() || isEmptyString(toDir))
			return;

	 	File dir = new File(toDir);
	 	if (!dir.exists())
	 		dir.mkdir(); //make destination directory
	 	
		File toFile = new File(toDir + File.separator + sourceFile.getName());		
		File outFile = toFile;
		//not overwrite, append verion no to the end of file name
		if (!overwrite && outFile.exists()){
			boolean found = false;
			int appendix = 1;
			
			while (!found){
				outFile = new File(getFilePath(toFile.getPath(), "_"+appendix));
				if (!outFile.exists())
					found = true;
				appendix++;	
			}
		}
		sourceFile.renameTo(outFile);
		sourceFile.delete();
	 }	 
	 
	 private static String getFilePath(String path, String appendix){
	 	int index = path.lastIndexOf(".");
	 	return path.substring(0, index) + appendix + path.substring(index); 
	 }

	/*
	  public static void buildDropDownList(DataStoreBuffer ds, HtmlDropDownList ddl, String valueCol, String descCol1, String descCol2, int maxSize, String firstOption, boolean scrollToDefault) {
		ddl.resetOptions();
		if (firstOption != null) {
		  String desc = null;
		  if (firstOption.equals("")) {
			desc = "-- Please Select --";
		  } else {
			desc = firstOption;
		  }
		  ddl.addOption(null, desc);
		}

		//String defaultValue = null;
		int defaultValue = -1;
		for (int i = 0; i < ds.getRowCount(); i++) {
		  try {
			String value = ds.getAny(i, valueCol) == null ? "" : ds.getAny(i, valueCol).toString().trim();
			String desc = ds.getAny(i, descCol1) == null ? "" : ds.getAny(i, descCol1).toString().trim();
			String isDefault = ds.getAny(i, "COM_SYS_PARAM.SP_DEFAULT") == null ? "" : ds.getAny(i, "COM_SYS_PARAM.SP_DEFAULT").toString().trim();
			if (descCol2 != null) {
			  desc = desc + " - " + (ds.getAny(i, descCol2) == null ? "" : ds.getAny(i, descCol2).toString().trim());
			}

			if (maxSize > 3) {
			  if (desc.length() > maxSize) {
				desc = desc.substring(0, maxSize - 3) + "...";
			  }
			} else if (maxSize > 0 && desc.length() > maxSize) {
			  desc = desc.substring(0, maxSize);
			}

			ddl.addOption(value, desc);
		
			if (isDefault.equals("Y")){
				defaultValue = ddl.getOptionCount() - 1;
				System.out.println("defaultValue: " + defaultValue);
			}		
		  } catch (DataStoreException e) {        
			e.printStackTrace();
		  }
		}	
		if (scrollToDefault){
			ddl.setSelectedIndex(defaultValue);
		}
	  }
	 */
	
	/*
	 * Generate the next segment number from the input segment number
	 * if the <input segment number> is null, the result will be the first number: 'AA'
	 * if the <input segment number> is the end of the possible number: 'ZZ', the result will remain as 'ZZ'
	 */
	public static String getNextSegmentNumber(String segmentNumber) {
		StringBuffer result = new StringBuffer();
		if (segmentNumber == null) {
			result.append("A0");
		} else if (segmentNumber.equals("ZZ")) {
			result.append("ZZ");
		} else {
			if (segmentNumber.charAt(1) == 'Z') {
				char digit1 = segmentNumberDigitIncrement(segmentNumber.charAt(0));
				if (digit1 == '0') digit1 = 'Z';
				result.append(digit1);
			} else {
				result.append(segmentNumber.charAt(0));								
			}
			result.append(segmentNumberDigitIncrement(segmentNumber.charAt(1)));
		}
		return result.toString();
	}
	//loop to next value
	// sequence : 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ
	private static char segmentNumberDigitIncrement(char digit) {
		char result;
		if (digit == '9') result = 'A';
		else if (digit == 'Z') result = '0';
		else result = ++digit;
		return result;
	}
	
	
	public static String[] generateValues(String prefix, int fromNo, int toNo, int minLength){								
		if (fromNo == toNo){
			//from no = to no
			return new String[]{prefix + leftPadString(""+fromNo, '0', minLength - prefix.length())};
			
		}else {
			int len = minLength - prefix.length();
			List pool = new Vector();			
			if (fromNo < toNo){
				//loop from fromNo to toNo
				for (int k=fromNo; k <= toNo; k++){
					pool.add(prefix + leftPadString(""+k, '0', len));				
				}				
			} else {				
				//loop from toNo to fromNo
				for (int k=toNo; k <= fromNo; k++){
					pool.add(prefix + leftPadString(""+k, '0', len));				
				}
			}
			return (String[])pool.toArray(new String[0]);		
		}
	}
	
	public static String leftPadString(String str, char pad, int length){		
		char [] buf = new char[length];
		for (int j = 0; j < length; j++)   
			buf[j] = pad;		
		return (new String(buf)).substring(0, length - str.length()) + str;
	}
	
//	public static String getReportPath()
//	{
//		Props p = Props.getProps(appName, "");
//		String reportPath = p.getProperty("ReportPath");
//		MessageLog.writeDebugMessage("Get ReportPath="+reportPath, null);
//		return reportPath;
//	}

//	public static String getFullReportPath()
//	{
//		Props p = Props.getProps(appName, "");
//		String reportPath = p.getProperty("ReportPath");
//		if (!reportPath.startsWith("http"))
//			reportPath = serverURL + reportPath; 
//
//		MessageLog.writeDebugMessage("Get FullReportPath="+reportPath, null);
//		return reportPath;
//	}
	
//	public static String getMenuPath()
//	{
//		Props p = Props.getProps(com.cabletv.asaps.com.Util.getAppName(), "");
//		String menuDir = p.getProperty("MenuDir");
//		//if (menuDir.equals(""))
//		//	menuDir = File.separator + "js";
//		// System.out.println("menu path="+menuDir);
//		return menuDir;		
//	}	
	
//	public static boolean isMenuPathExist()
//	{
//		Props p = Props.getProps(com.cabletv.asaps.com.Util.getAppName(), "");
//		String menuDir = p.getProperty("MenuDir");
//		if (menuDir.equals(""))
//			return false;		
//		return true;		
//	}
	
	public static String getAppName() {
//		return (appName == null ? "asaps" : appName);	
		return (appName == null ? "ea" : appName);
	}

	public static java.sql.Date string2SqlDate( String iDate) throws EAException
	{
		try
		{
			return java.sql.Date.valueOf(
					Util.date2String(Util.parseDisplayDate(iDate), Constants.DATE_FORMAT));
		}
		catch(Exception e)
		{
			throw new EAException(e);
		}
	}
	
	public static TreeSet appendToTreeSet(TreeSet toBeAppended, Object[] objs) {
		TreeSet redundence = new TreeSet();
		if (toBeAppended != null) {
			for (int i=0; i<objs.length; i++) {
				if (objs[i] == null) continue;
				if (!toBeAppended.add( objs[i] ))
					redundence.add( objs[i] );
			}
		}
		return redundence;
	}
	
//	public static String getPrintFormat(){
//		Props p = Props.getProps(com.cabletv.asaps.com.Util.getAppName(), "");
//		String format = p.getProperty("lib.receipt.format");
//		if (format == null || format.equals(""))
//			return null;
//		else 
//			return format.trim();	
//	}
	
//	public static String getPrintParameter(){		
//		String fmt = getPrintFormat();
//		if (fmt == null || fmt .equals(""))
//			return "";
//		else
//			return "&outtype=" + fmt;
//	}	
	
	// Handling Special Characters in SQL
	public static String handleSCInSQL(String checkField){
		String returnString = new String();
		returnString = checkField.replaceAll("'","''");
		return returnString;
	}
	
	public static boolean isNaN(String val) {
	    try {
	        Double d = Double.valueOf(val);	        
	    } catch (NumberFormatException e) {
	        return true;
	    }
	    return false;
	}
	
	public static boolean isNaInt(String val) {
	    try {
	        Integer i = Integer.valueOf(val);
	    } catch (NumberFormatException e) {
	        return true;
	    }
	    return false;
	}
	
	public static String chopRank(String employeeName) {
	    if (employeeName == null) return null;
	    if (Util.isEmptyString(employeeName)) return "";
	    int lastIdx = employeeName.lastIndexOf("     ");
	    if (lastIdx > 0) return employeeName.substring(0, lastIdx);
	    return employeeName;
	}
	
	// 20231120 Update eApp URL
	public static String getEAppURL() {
		Properties props = new Properties();
		try {
			props.load(Util.class.getClassLoader().getResourceAsStream("application-local.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String hostname;
		String url;
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostname = "";
		}
		
		if (hostname.startsWith("dhrm")) {
			url = props.getProperty("email.eAppraisalURL_dev");
		} else if (hostname.startsWith("shrm")) {
			url = props.getProperty("email.eAppraisalURL_uat");
		} else {
			url = props.getProperty("email.eAppraisalURL");
		}
		return url;
	}
}
