package com.hkha.ea.service.common.impl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.Message;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.common.NotificationManagerDAO;
import com.hkha.ea.dao.common.SendInitNotificationDAO;
import com.hkha.ea.domain.DummyEmployee;
import com.hkha.ea.domain.DummyReportInfo;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.dto.admin.SendAutoReminderVo;
import com.hkha.ea.service.common.SendInitNotificationService;

@Service("sendInitNotificationService")
public class SendInitNotificationServiceImpl implements SendInitNotificationService{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SendInitNotificationServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out

	@Autowired
	private SendInitNotificationDAO sendInitNotificationDAO;
	
	@Autowired
	private NotificationManagerDAO notificationManagerDAO;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(rollbackFor=Exception.class)
	public void executeBatch() {
		List<SendAutoReminderVo> list = sendInitNotificationDAO.executeBatch();
		log.info("Number of notification(s): " + list.size());
		
		Hashtable officerList = null;
		if(list != null && list.size() > 0){
			officerList = new Hashtable();
			//Group the results with OFFICER_ID and OFFICER_RANK_TYPE
			officerList = this.groupByofficerAndRank(list);
			
			log.info("Number of recipent(s): " + officerList.size());
			
			try{
				//If officer has email, send email to the officer
				this.sendEmailAndInsertMessageLog(officerList);
				//this.sendEmailAndInsertMessageLogTesting(officerList);
			}catch (Exception e){
				log.severe(e.toString());
			}
		}
	}
	
	private void sendEmailAndInsertMessageLog(Hashtable officerList) throws Exception {
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("application-local.properties"));
		final String eAppraisalURL = Util.getEAppURL();		// 20231120 Update eApp URL
		final String reminderSender = props.getProperty("email.ReminderSender");
		
		List<EaMessageLog> msgList = new ArrayList<EaMessageLog>();
		
		Iterator itr = officerList.keySet().iterator();
		while (itr.hasNext()) {
			String nextOfficerID = (String) itr.next();
			DummyEmployee officer = (DummyEmployee) officerList.get(nextOfficerID);
			String sent = officer.getHasEmail();
			
			log.info("Sending message to : " + officer.getNumber() + "; has email = " + officer.hasEmail());

			//Vector mailBody = new Vector();
			StringBuffer mailBody = new StringBuffer();
			StringBuffer mailBodyAction = new StringBuffer();
			StringBuffer mailBodyFYI = new StringBuffer();
			if (officer.hasEmail()) {
				mailBody.append(officer.getNotification());
				mailBody.append("\n\n");
				//mailBody.append("The appraisal report(s) of the following officer(s) has/have been sent to you via the web");
				//mailBody.append("\n\n");
				
				for (int r=0; r<officer.getReports().size(); r++) {
					DummyReportInfo ri = (DummyReportInfo) officer.getReports().get(r);
					//mailBody.append( ri.toDisplayString(false) );
					//mailBody.append("\n");
					//log.info(ri.toDisplayString(false));
					if (ri.getStatus().equals("AP")) {
						mailBodyFYI.append( ri.toDisplayString(false) );
						mailBodyFYI.append("\n");
						log.info("for information:" + ri.toDisplayString(false));
					} else {
						mailBodyAction.append( ri.toDisplayString(false) );
						mailBodyAction.append("\n");
						log.info("-for Action:" + ri.toDisplayString(false));
					}
				}
			    //mailBody.append("\n");

				////mailBody.add("Please complete the report(s) and send to the next processing officer using the link as shown below:");
				//mailBody.append(
		        //"Please complete the report(s) accordingly by using the following link:" );
				//mailBody.append("\n");
				//mailBody.append(eAppraisalURL);
				//mailBody.append("\n");

				if (mailBodyAction.length()>0) {
					mailBody.append("The following appraisal report(s) has/have been sent to you before and is/are pending your action (Note):\n");
				    mailBody.append("\n");
				    mailBody.append(mailBodyAction.toString());
					mailBody.append("\n");
					mailBody.append("Please take necessary action on the report(s) via the following link:\n");
					mailBody.append(eAppraisalURL);
					mailBody.append("\n\n");
				}
				if (mailBodyFYI.length()>0) {
					mailBody.append("The following appraisal report(s) has/have been sent to the Appraisee(s) and is/are pending his/her/their action(s) (Note):\n");
				    mailBody.append("\n");
				    mailBody.append(mailBodyFYI.toString());
					mailBody.append("\n\n");
				}				
				mailBody.append(officer.getNotificationFooter()==null? "":officer.getNotificationFooter());

				//replaced PHCM with PHRM on 20211210
				//String db = "PHCM";
				//String db = "PHRM";
				
				//get DB SID
				String db = "";
			    try{
			    	 
			    	Statement stmt = jdbcTemplate.getDataSource().getConnection().createStatement();
			    	String sql = "select SYS_CONTEXT('USERENV', 'CON_NAME') NAME FROM DUAL";
			    	ResultSet rs = stmt.executeQuery(sql);
			    	if (rs.next())
			    		db = rs.getString("NAME");
			    	rs.close();
			    	stmt.close();
			    }catch (Exception e){
			    	e.printStackTrace();
			    	log.severe(e.toString());
			    }

			    // "DHRM: eAppraisal Initial Noteification" for DHRM
			    // "AHRM: eAppraisal Initial Noteification" for AHRM
			    // "eAppraisal Initial Noteification" for PHRM
			    db = (db.equalsIgnoreCase("PHRM"))?"":db+": ";
				String mailSubject = db + "Appraisal Report Notification";

				try {
					notificationManagerDAO.send(reminderSender, officer.getEmail(), null, mailSubject, mailBody.toString());
				
				} catch (Exception ex) {
				    sent = "F";
				}
			}

			log.info("Complete sending message to all recipent(s)");

			// Save message log
			for (int r=0; r<officer.getReports().size(); r++) {
				EaMessageLog msg = new EaMessageLog();
				DummyReportInfo ri = (DummyReportInfo) officer.getReports().get(r);
				msg.setReportId((long)ri.getId());
				msg.setRole(ri.getStatus());
				msg.setRecipient(officer.getNumber());
				msg.setType(Constants.MESSAGE_LOG_TYPE_AUTO);
				msg.setMessageType(Constants.MESSAGE_LOG_MESSAGE_TYPE_NOTIFICATION);
				msg.setEmailInd(sent);
				
				msgList.add(msg);
			}
		}
		log.info("Start Update Message Log.");
		notificationManagerDAO.updateMessageLog(msgList, "INIT_NOTE");
		log.info("Finish Update Message Log.");
	}
	
/*Testing
	private void sendEmailAndInsertMessageLogTesting(Hashtable officerList) throws Exception {
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("application-local.properties"));
		final String eAppraisalURL = props.getProperty("email.eAppraisalURL");
		final String reminderSender = props.getProperty("email.ReminderSender");
		
		List<EaMessageLog> msgList = new ArrayList<EaMessageLog>();
		
		Iterator itr = officerList.keySet().iterator();
		while (itr.hasNext()) {
			String nextOfficerID = (String) itr.next();
			DummyEmployee officer = (DummyEmployee) officerList.get(nextOfficerID);
			String sent = officer.getHasEmail();
			
			log.info("Sending message to : " + officer.getNumber() + "; has email = " + officer.hasEmail());

			//Vector mailBody = new Vector();
			StringBuffer mailBody = new StringBuffer();
			if (officer.hasEmail()) {
				mailBody.append(officer.getNotification());
				mailBody.append("\n\n");
				mailBody.append("The appraisal report(s) of the following officer(s) has/have been sent to you via the web");
				mailBody.append("\n\n");

				for (int r=0; r<officer.getReports().size(); r++) {
					DummyReportInfo ri = (DummyReportInfo) officer.getReports().get(r);
					mailBody.append( ri.toDisplayString(false) );
					mailBody.append("\n");
					log.info(ri.toDisplayString(false));
				}
			    mailBody.append("\n");

				//mailBody.add("Please complete the report(s) and send to the next processing officer using the link as shown below:");
				mailBody.append(
		        "Please complete the report(s) accordingly by using the following link:" );
				mailBody.append("\n");
				mailBody.append(eAppraisalURL);
				mailBody.append("\n");

				String db = "PHCM";
				//get DB SID
			    /*try{
			    	stmt = conn.createStatement();
			    	String sql = "select * from v$database";
			    	rs = stmt.executeQuery(sql);
			    	if (rs.next())
			    		db = rs.getString("NAME");
			    	rs.close();
			    	stmt.close();
			    }catch (Exception e){
			    	e.printStackTrace();
			    	log.severe(e.toString());
			    }*/
/*
			    // "DHRM: eAppraisal Initial Notification" for DHRM
			    // "AHRM: eAppraisal Initial Notification" for AHRM
			    // "eAppraisal Initial Notification" for PHRM
			    db = (db.equalsIgnoreCase("PHRM"))?"":db+": ";
				String mailSubject = db + "Appraisal Report Notification";

				try {
					Thread.sleep(1800000);
					log.info("Send Email");
				} catch (Exception ex) {
				    sent = "F";
				}
			}

			log.info("Complete sending message to all recipent(s)");
		}
		
	}
*/
	
	private Hashtable groupByofficerAndRank(List<SendAutoReminderVo> list) {
		Hashtable officerList = new Hashtable();
		DummyEmployee officer = null;
		for(int i=0; i<list.size(); i++){
			SendAutoReminderVo vo = list.get(i);
			if(vo != null){
				String officerID = vo.getOfficerId();
				long reportID = vo.getReportId();
				String officerName = vo.getOfficerName();
				String officerEmail = vo.getEmailAddress();
				String officerRankType = vo.getOfficerRankType();
				String batchID = vo.getBatchId();
				String message = vo.getMessage();
				String messageFooter = vo.getMessageFooter();
				
				//String reportName = vo.getReportName();
				String reportName = vo.getRpNameAndEmplNum();
				
				String reportStatus = vo.getStatus();
				String reportRank = vo.getSubstantiveRank(); 
				Date reportCommenceDate = vo.getCommenceDate();
				Date reportEndDate = vo.getEndDate();
				//log.info("reportName: "+reportName+" reportRank: "+reportRank+" rpNameAndEmplNum: "+vo.getRpNameAndEmplNum());
				
				DummyReportInfo report = new DummyReportInfo((int)reportID,reportName,reportStatus,reportRank,reportCommenceDate,reportEndDate);
				
				String hashKey = officerID + officerRankType;
				if (officerList.containsKey(hashKey)){
					officer = (DummyEmployee)officerList.get(hashKey);
					officer.getReports().add(report);
				}else{
					officer = new DummyEmployee(officerID, officerName, officerEmail, 
												batchID, message, messageFooter);
					officer.setReports(new Vector()); 
					officer.getReports().add(report);
					officerList.put(hashKey, officer);
				}
			}
		}
		return officerList;
	}

}
