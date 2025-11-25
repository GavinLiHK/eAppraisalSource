package com.hkha.ea.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.hkha.ea.common.Constants;

public class DeadlineAgent {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public Date[] deadline = {null, null, null, null, null};
	public int[] interval = {0,0,0,0};
	public String[] reminder = {null,null,null,null};
	private int reminderIndex = 0;
	private int lastReminderIndex = 0;
	private boolean isValidTrigger = false;
	//added on 20170203
	private String lastAutoMsgType = "";
	
	public DeadlineAgent(Date due, int inv1, int inv2, int inv3, int inv4, String lastMsgType) throws ParseException {
		deadline[0] = due;
		interval[0] = inv1;
		interval[1] = inv2;
		interval[2] = inv3;
		interval[3] = inv4;
		//calc. 1st/2nd/3rd reminder, by days after due date
		Date lastDead = due;
		for (int i=1; i<4; i++) {
		    if (interval[i-1] > 0)
		        deadline[i] = new Date( due.getTime() + interval[i-1] * 86400000 );
		    else 
		        deadline[i] = (lastDead != null) ? lastDead : due; 
			lastDead = deadline[i];
		}
		//calc. subsequent deadline, to add interval to be first subsequent deadline
		if (inv4 > 0)
		    deadline[4] = new Date( lastDead.getTime() + inv4 * 86400000 );
		else
		    deadline[4] = lastDead;

		int lastRmdIdx = -1; //this default value makes all reminder start from 1st reminder
		if (lastMsgType.indexOf(Constants.MESSAGE_LOG_MESSAGE_TYPE_SUBSEQUENT_REMINDER) == 0) lastRmdIdx = 3;
		else if (lastMsgType.indexOf(Constants.MESSAGE_LOG_MESSAGE_TYPE_3RD_REMINDER) == 0) lastRmdIdx = 2;
		else if (lastMsgType.indexOf(Constants.MESSAGE_LOG_MESSAGE_TYPE_2ND_REMINDER) == 0) lastRmdIdx = 1;
		else if (lastMsgType.indexOf(Constants.MESSAGE_LOG_MESSAGE_TYPE_1ST_REMINDER) == 0) lastRmdIdx = 0;
		
		isValidTrigger = false;
		Calendar cal = Calendar.getInstance();
		String curDay = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
		Date today = format.parse(curDay);
		for (int i=1; i<5; i++) {
			if (deadline[i].compareTo(today) >= 0) break;
			if (interval[i-1] != 0) {
				reminderIndex = i-1;
				isValidTrigger = true;
			}
			if (lastRmdIdx < reminderIndex) break;
		}
	
		int z = 0;
	
		while (today.compareTo(deadline[z])>0){
			z++;
			reminderIndex = z-1;
			if (reminderIndex > 3) reminderIndex = 3;
		
		
			if (z>4) {
				break;
			}
		}
/*
		if (lastRmdIdx == reminderIndex) {
		    if (lastMsgType.indexOf(Constants.MESSAGE_LOG_MESSAGE_TYPE_SUBSEQUENT_REMINDER) == 0) {
			    //checking today if its trigger-day of subsequent reminder
		        java.sql.Date subSequent = deadline[4];
		        if (inv4 == 0) {
		            isValidTrigger = false;
		        } else {
			        isValidTrigger = true;
					//parse last reminder creation date
			        if (lastMsgType.indexOf("@") >= 0) {
					    String lastSentOn = lastMsgType.split("@")[1];
					    if (lastSentOn.matches("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}")) {
							java.sql.Date lastSentDate = new java.sql.Date((Date.valueOf(lastSentOn).getTime()));
					        long diffMs = Math.abs((today.getTime() - 86400000) - lastSentDate.getTime());
							//To prevent the subsequent reminder send twice in same period of interval
							if (diffMs < inv4 * 86400000) {
							    isValidTrigger = false;
							}
					    }
					}
		        }
		    } else isValidTrigger = false; 
		}
*/
	if (lastRmdIdx == reminderIndex) {
	    if (lastMsgType.indexOf(Constants.MESSAGE_LOG_MESSAGE_TYPE_SUBSEQUENT_REMINDER) == 0) {
		    //checking today if its trigger-day of subsequent reminder
	        Date subSequent = deadline[4];
	        if (inv4 == 0) {
	            isValidTrigger = false;
	        } else {
		        isValidTrigger = true;
			//parse last reminder creation date
		        if (lastMsgType.indexOf("@") >= 0) {
				    String lastSentOn = lastMsgType.split("@")[1];
				    if (lastSentOn.matches("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}")) {
						Date lastSentDate = format.parse(lastSentOn);
						/* edited on 20170302 for send auto reminder interval incorrect
				        long diffMs = Math.abs((today.getTime() - 86400000) - lastSentDate.getTime());
				        */
						long diffMs = Math.abs((today.getTime() - 86400000) - lastSentDate.getTime()) + 86400000;
						//To prevent the subsequent reminder send twice in same period of interval
						if (diffMs < inv4 * 86400000) {
						    isValidTrigger = false;
						}
				    }
				}
	        }
	    } else {
	    	isValidTrigger = false;
	    	 
	    }
	}else{
		String lastSentOn;
		try{
			lastSentOn = lastMsgType.split("@")[1];
			lastAutoMsgType = lastMsgType.split("@")[0];
			
		}catch(Exception e){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
			lastSentOn = df.format(due);
		}
		if (lastSentOn.matches("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}")) {
			Date lastSentDate = format.parse(lastSentOn);
			/*edited on 20170203 for send auto reminder interval incorrect 
			long diffMs = Math.abs((today.getTime() - 86400000) - lastSentDate.getTime());
			if (diffMs < inv1 * 86400000) {
				isValidTrigger = false;
			}else{
				isValidTrigger = true;
			}
			*/
			
			long diffMs;
			
			if(!lastAutoMsgType.equalsIgnoreCase("3")){
				diffMs = Math.abs((today.getTime() - 86400000) - due.getTime()) + 86400000;
			}else{
				diffMs = Math.abs((today.getTime() - 86400000) - lastSentDate.getTime()) + 86400000;
			}
			if (diffMs < inv1 * 86400000) {
				isValidTrigger = false;
			}else if (diffMs < inv2 * 86400000 && lastAutoMsgType.equalsIgnoreCase("1")){
				isValidTrigger = false;
			}else if (diffMs < inv3 * 86400000 && lastAutoMsgType.equalsIgnoreCase("2")){
				isValidTrigger = false;
			}else if (diffMs < inv4 * 86400000 && lastAutoMsgType.equalsIgnoreCase("3")){
				isValidTrigger = false;
			}else{
				isValidTrigger = true;
			}
		}
	}
}
	
	public boolean isReadyToSend() {
	    return isValidTrigger;
	}
	
	public boolean isOverDue() {
		java.sql.Date today = new java.sql.Date((new java.util.Date()).getTime());
		return deadline[reminderIndex].compareTo(today) <= 0;
	}
	
	/**
	 * get deadline of last expried reminder
	 * @return
	 */
	public Date getDeadline() {
		return deadline[reminderIndex];
	}
	
	/**
	 * get last expried reminder sequence
	 * @return
	 */
	public int getReminderSeq() {
		return reminderIndex+1;
	}
	
	/**
	 * Get last expired reminder message
	 * @return
	 */
	public String getReminderMessage() {
		return reminder[Math.min(3,reminderIndex)];
	}

	/**
	 * Get last expired reminder type
	 * @return
	 */
	public String getMessageLogType() {
		String msgLogType = Constants.MESSAGE_LOG_MESSAGE_TYPE_1ST_REMINDER;
		if (reminderIndex == 0) msgLogType = Constants.MESSAGE_LOG_MESSAGE_TYPE_1ST_REMINDER;
		else if (reminderIndex == 1) msgLogType = Constants.MESSAGE_LOG_MESSAGE_TYPE_2ND_REMINDER;
		else if (reminderIndex == 2) msgLogType = Constants.MESSAGE_LOG_MESSAGE_TYPE_3RD_REMINDER;
		else if (reminderIndex == 3) msgLogType = Constants.MESSAGE_LOG_MESSAGE_TYPE_SUBSEQUENT_REMINDER;
		else if (reminderIndex == 4) msgLogType = Constants.MESSAGE_LOG_MESSAGE_TYPE_SUBSEQUENT_REMINDER;
		return msgLogType;
	}

	/**
	 * Set reminder message
	 * @param msg
	 */
	public void setReminderMessage(String msg) {
		this.setReminderMessage(getMessageLogType(), msg);
	}

	/**
	 * Set reminder message
	 * @param msgLogType Probably, use Constants.MESSAGE_LOG_MESSAGE_TYPE_** 
	 * @param msg Value of reminder message
	 */
	public void setReminderMessage(String msgLogType, String msg) {
		if (Constants.MESSAGE_LOG_MESSAGE_TYPE_1ST_REMINDER.equals(msgLogType))
			reminder[0] = msg;
		else if (Constants.MESSAGE_LOG_MESSAGE_TYPE_2ND_REMINDER.equals(msgLogType))
			reminder[1] = msg;
		else if (Constants.MESSAGE_LOG_MESSAGE_TYPE_3RD_REMINDER.equals(msgLogType))
			reminder[2] = msg;
		else if (Constants.MESSAGE_LOG_MESSAGE_TYPE_SUBSEQUENT_REMINDER.equals(msgLogType))
			reminder[3] = msg;
	}
}
