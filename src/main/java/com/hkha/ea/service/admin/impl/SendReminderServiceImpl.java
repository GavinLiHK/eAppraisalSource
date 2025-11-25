package com.hkha.ea.service.admin.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.Vector;

import jakarta.servlet.http.HttpServletRequest;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Message;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.admin.SendReminderDAO;
import com.hkha.ea.dao.common.NotificationManagerDAO;
import com.hkha.ea.domain.DeadlineAgent;
import com.hkha.ea.domain.DummyEmployee;
import com.hkha.ea.domain.DummyReportInfo;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.dto.admin.ReminderRecipientDto;
import com.hkha.ea.dto.admin.ReminderRecipientVo;
import com.hkha.ea.dto.admin.ReportReminderDto;
import com.hkha.ea.dto.admin.SendAutoReminderVo;
import com.hkha.ea.service.admin.SendReminderService;

@Service("sendReminderService")
public class SendReminderServiceImpl implements SendReminderService{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SendReminderServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out

	@Autowired
	private SendReminderDAO sendReminderDAO;
	
	@Autowired
	private NotificationManagerDAO notificationManagerDAO;
	
	public ReportReminderDto generateList(HttpServletRequest request,List<Long> reportIds) {
		Hashtable hashRecipient = new Hashtable();
		ReportReminderDto dto = new ReportReminderDto();
		List<ReminderRecipientDto> recipientList = new ArrayList<ReminderRecipientDto>();
		
		List<ReminderRecipientVo> result = sendReminderDAO.search(reportIds);
		if(result != null && result.size()>0){
			for(int i=0; i<result.size(); i++){
				ReminderRecipientVo dsRecipient = result.get(i);
				
				if(dsRecipient != null){
					
					String recipientNumber = dsRecipient.getPeopleEmployeeNum();
					String recipientName = dsRecipient.getFullName();
					String recipientEmail = dsRecipient.getEmailAddress();
					if(!hashRecipient.containsKey(recipientNumber)) {
						DummyEmployee em = new DummyEmployee(recipientNumber, recipientName, recipientEmail);
						String hoId = String.valueOf(dsRecipient.getHoId());

						if(!Util.isEmptyString(hoId) && !"NULL".equalsIgnoreCase(hoId)){
							em.setOverridingNumber(hoId);
						}
						
						hashRecipient.put(recipientNumber, em);
					}
					
					DummyEmployee de = (DummyEmployee) hashRecipient.get(recipientNumber);
					
					int reportID = (int)dsRecipient.getReportId();
					String reportEmpName = dsRecipient.getName();
					String reportStatus = dsRecipient.getStatus();
					String reportEmpRank = dsRecipient.getSubstantiveRank();
					Date reportStart = dsRecipient.getCommenceDate();
					Date reportEnd = dsRecipient.getEndDate();
					
					DummyReportInfo ri =new DummyReportInfo(reportID, reportEmpName, reportStatus, reportEmpRank, reportStart, reportEnd);
					de.getReports().add(ri);
				}
				
			}
		}
		
		Iterator itr = hashRecipient.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			DummyEmployee de = (DummyEmployee) hashRecipient.get(key);
			
			ReminderRecipientDto rre = new ReminderRecipientDto();
			rre.setPeopleEmployeeNum(de.getNumber());
			rre.setFullName(de.getName());
			rre.setEmailAddress(de.getEmail());
			rre.setHoId(de.getOverridingNumber());
			
			//borrow a string field to store temprary session name
			rre.setOfficerId(de.toString());
			request.getSession().setAttribute(de.getNumber(), de);
			recipientList.add(rre);
		}
		dto.setRecipientList(recipientList);
		List<ReminderRecipientDto> otherRecipientList = new ArrayList<ReminderRecipientDto>();
		for(int i=0;i<5;i++){
			ReminderRecipientDto other = new ReminderRecipientDto();
			otherRecipientList.add(other);
		}
		dto.setOtherRecipientList(otherRecipientList);
		return dto;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public String sendNotification(HttpServletRequest request, ReportReminderDto reportReminderDto) throws Exception {
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("application-local.properties"));
		
		final String eAppraisalURL = Util.getEAppURL();		// 20231120 Update eApp URL
		final String reminderSender = props.getProperty("email.ReminderSender");

		Vector ccEmployee = new Vector();
		for (int row=0; row < reportReminderDto.getOtherRecipientList().size(); row++) {
			if(reportReminderDto.getOtherRecipientList().get(row) != null){
				String ccNumber = reportReminderDto.getOtherRecipientList().get(row).getPeopleEmployeeNum();
				if (!Util.isEmptyString(ccNumber)) {
					ccEmployee.add(ccNumber);
				}
			}
			
		}
		
		//debug
		log.info("CC: "+ccEmployee.size());
		for(int i=0;i<ccEmployee.size();i++){
			log.info("CC Name:"+ccEmployee.get(i));
		}
		
		Hashtable ccEmail = notificationManagerDAO.searchEmailAddressByEmployeeNumber(ccEmployee);
		boolean atLeastOneFail = false;
		for (int row=0; row < reportReminderDto.getOtherRecipientList().size(); row++) {
			if(reportReminderDto.getOtherRecipientList().get(row) != null){
				String ccNumber = reportReminderDto.getOtherRecipientList().get(row).getPeopleEmployeeNum();
				//debug
				log.info("CC NUmber:"+ccNumber);
				
				if (!Util.isEmptyString(ccNumber) && !ccEmail.containsKey(ccNumber)) {
					atLeastOneFail = true;
				}
			}
			
		}
		if (atLeastOneFail) {
			return "valid";
		}

		
		List<EaMessageLog> msgLogList = new ArrayList<EaMessageLog>();
		
		TreeSet sentSet = new TreeSet();
		TreeSet failSet = new TreeSet();
		
		
		if(reportReminderDto.getRecipientList() != null && reportReminderDto.getRecipientList().size() >0){

			for(int i=0; i<reportReminderDto.getRecipientList().size(); i++){

				ReminderRecipientDto dto = reportReminderDto.getRecipientList().get(i);

				if(dto != null){
					
					String objId = dto.getPeopleEmployeeNum();
					DummyEmployee de = (DummyEmployee) request.getSession().getAttribute(objId);
					
					if(!Util.isEmptyString(de.getOverridingNumber()) && !"NULL".equals(de.getOverridingNumber().toUpperCase())){
						Vector overridingOfficer = new Vector();
						overridingOfficer.add(de.getOverridingNumber());
						Hashtable ovEmail = notificationManagerDAO.searchEmailAddressByEmployeeNumber(overridingOfficer);
						if (!ovEmail.containsKey(de.getOverridingNumber())) {
							continue;
						} else {
							de.setEmail((String)ovEmail.get(de.getOverridingNumber()));
						}

					}
					
					String sent = de.getHasEmail();

					Vector cc = new Vector();
					Iterator itr = ccEmail.keySet().iterator();
					while (itr.hasNext()) {
						String ccEmpNum = (String) itr.next();
						String ccEmpEmail = (String) ccEmail.get(ccEmpNum);
						cc.add(ccEmpEmail);
					}
					
					StringBuffer mailBody = new StringBuffer();
					//Vector mailBody = new Vector();

					
					if (de.hasEmail()) {

						mailBody.append("The appraisal report(s) of the following officer(s) has/have not yet been received:");
						mailBody.append("\n\n");
						
						for (int r=0; r<de.getReports().size(); r++) {
							DummyReportInfo ri = (DummyReportInfo) de.getReports().get(r);
						    mailBody.append(ri.toDisplayString());
                            mailBody.append("\n");
						}

						mailBody.append("\n");
						if (!Util.isEmptyString(reportReminderDto.getMessageBody())) {
							mailBody.append(reportReminderDto.getMessageBody());
							mailBody.append("\n");
						}
                        mailBody.append("\n");
						//mailBody.add("Please complete the report(s) using the link as shown below:");
						mailBody.append(
				        "Please complete the report(s) accordingly by using the following link:" );
						mailBody.append("\n");
						mailBody.append(eAppraisalURL);
						mailBody.append("\n");

						String mailSubject = "Overdue Appraisal Report";
						try {
							notificationManagerDAO.send(reminderSender, de.getEmail(), cc, mailSubject, mailBody.toString());
							sentSet.add(de.getNumber() + ", " + de.getName());
						} catch (Exception e) {
							failSet.add(de.getNumber() + ", " + de.getName());
						}
					}else{

						failSet.add(de.getNumber() + ", " + de.getName());
					}
					
					if (sentSet.contains(de.getNumber() + ", " + de.getName())) {
						for (int r=0; r<de.getReports().size(); r++) {
							DummyReportInfo ri = (DummyReportInfo) de.getReports().get(r);
							EaMessageLog msglog = new EaMessageLog();
							msglog.setReportId((long) ri.getId());
							msglog.setRole(ri.getStatus());
							if (!Util.isEmptyString(de.getOverridingNumber()) && !"NULL".equals(de.getOverridingNumber().toUpperCase())){
								msglog.setRecipient(de.getOverridingNumber());
							} else {
								msglog.setRecipient( de.getNumber());
							}
							msglog.setEmailInd(sent);
							msgLogList.add(msglog);
						}
					}
				}
			}
		}


		if(msgLogList != null && msgLogList.size() > 0){
			for (int i=0; i<msgLogList.size(); i++) {
				if(msgLogList.get(i) != null)
					msgLogList.get(i).setMessageType(Constants.MESSAGE_LOG_MESSAGE_TYPE_SUBSEQUENT_REMINDER);
				msgLogList.get(i).setType(Constants.MESSAGE_LOG_TYPE_MANUAL);
				if (ccEmployee.size() >= 1) msgLogList.get(i).setCc1((String)ccEmployee.get(0));
				if (ccEmployee.size() >= 2) msgLogList.get(i).setCc2((String)ccEmployee.get(1));
				if (ccEmployee.size() >= 3) msgLogList.get(i).setCc3((String)ccEmployee.get(2));
				if (ccEmployee.size() >= 4) msgLogList.get(i).setCc4((String)ccEmployee.get(3));
				if (ccEmployee.size() >= 5) msgLogList.get(i).setCc5((String)ccEmployee.get(4));
			}
			notificationManagerDAO.updateMessageLog(msgLogList);
		}
		

		
		if (!sentSet.isEmpty()) {
		    //construct message in javascript alert
		    StringBuffer sbSent = new StringBuffer();
		    sbSent.append("The manual reminder(s) has\\/have been sent to the recipient(s) as shown below:\\n");
		    sbSent.append("\\n");
		    Iterator deSet = sentSet.iterator();
		    while (deSet.hasNext()) {
		        String deSent = (String)deSet.next();
		        sbSent.append("  " + deSent.replaceAll("'", "''") + "\\n");
		    }
		}
		

		if (!failSet.isEmpty()) {
		    //construct message in page top
			StringBuffer failMsg = new StringBuffer();
			failMsg.append("An invalid email account(s) of the following recipient(s) has/have been found:");
			failMsg.append("\\n");
		    Iterator deSet = sentSet.iterator();
		    while (deSet.hasNext()) {
		        String deSent = (String)deSet.next();
		        failMsg.append("  " + deSent);
		    }
		    failMsg.append("Please contact Helpdesk for supports.");
		    return failMsg.toString();
		}

		return "success";
	}

	@Transactional(rollbackFor=Exception.class)
	public void sendAutoReminder() {
		try{
			Hashtable hashRecipient = new Hashtable();
			List<SendAutoReminderVo> sendAR = sendReminderDAO.searchRpForSendAutoReminder();
			log.info("Number of overdue report(s): " + sendAR.size());
			
			if(sendAR != null && sendAR.size() > 0){
				for(int i=0; i<sendAR.size(); i++){
					SendAutoReminderVo vo = sendAR.get(i);
					if(vo != null){
						if(vo.getDeadline() == null)
							continue;
						//group the overdue report with the same officer.
						hashRecipient = this.getOverdueRpByOfficer(vo, hashRecipient);
						
					}
				}
				log.info("Number of recipent(s): " + hashRecipient.size());
			}
					
			//send email to officer
			List<EaMessageLog> msgLogList = new ArrayList<EaMessageLog>();
			Iterator itr = hashRecipient.keySet().iterator();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				DummyEmployee de = (DummyEmployee) hashRecipient.get(key);
				
				log.info(String.format("Sending message to %s, has email=%s, Report Size: %d", de.getNumber(), de.hasEmail(), de.getReports().size()));
				
				if(de.hasEmail()){
					String sent = "";
					try{
						sent = this.sendEmail(de);
					} catch (Exception e){
						sent = "F";
						log.severe(e.toString());
					}
					
					for (int r=0; r<de.getReports().size(); r++) {
						DummyReportInfo ri = (DummyReportInfo) de.getReports().get(r);
						//get message log information
						EaMessageLog msglog = new EaMessageLog();
						msglog.setReportId((long) ri.getId());
						msglog.setRole(ri.getStatus());
						msglog.setRecipient( de.getNumber());
						msglog.setType(Constants.MESSAGE_LOG_TYPE_AUTO);
						msglog.setMessageType(de.getDeadlineAgent().getMessageLogType());
						msglog.setEmailInd(sent);
						msgLogList.add(msglog);
					}
				}
			}
			log.info("Complete sending message to all recipent(s)");
			
			//insert message log to DB
			notificationManagerDAO.updateMessageLog(msgLogList, "AUTO_RMD");
		}catch(Exception ex){
			ex.printStackTrace();
			log.severe(ex.toString());
		}
		
	}

	private String sendEmail(DummyEmployee de) throws IOException {
		
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("application-local.properties"));
		final String eAppraisalURL = Util.getEAppURL();		// 20231120 Update eApp URL
		final String reminderSender = props.getProperty("email.ReminderSender");
		
		String sent = de.getHasEmail();
		StringBuffer mailBody = new StringBuffer();
		//Vector mailBody = new Vector();
		mailBody.append("The appraisal report(s) of the following officer(s) has/have not been received:");
		mailBody.append("\n\n");
		
		for (int r=0; r<de.getReports().size(); r++) {
			DummyReportInfo ri = (DummyReportInfo) de.getReports().get(r);
		    mailBody.append(ri.toDisplayString());
		    mailBody.append("\n");
		}
		
		mailBody.append("\n");
		mailBody.append("Please complete the report(s) accordingly by using the following link:" );
		mailBody.append("\n");
		mailBody.append(eAppraisalURL);
		mailBody.append("\n");

		
		String mailSubject = "Overdue Appraisal Report";
		try {
			notificationManagerDAO.send(reminderSender, de.getEmail(), null, mailSubject, mailBody.toString());
		} catch (Exception e) {
			log.severe(e.toString());
			sent = "F";
		}
		return sent;
	}

	private Hashtable getOverdueRpByOfficer(SendAutoReminderVo vo, Hashtable hashRecipient) {
		//Hashtable hashRecipient = new Hashtable();
		
		String officer = vo.getOfficerId();
		String batchId = vo.getBatchId();
		//String recipientName = vo.getFullName();
		String recipientEmail = vo.getEmailAddress();
		
		int inv1 = vo.getFirstRemInt();
		int inv2 = vo.getSecondRemInt();
		int inv3 = vo.getThirdRemInt();
		int inv4 = vo.getSubsRemInt();
		
		String lastMsgType = vo.getMessageType();
		if (Util.isEmptyString(lastMsgType)){ 
			lastMsgType = "";
		}
		
		String hoID = vo.getHoId();
		if (!Util.isEmptyString(hoID) && Constants.STATUS_RO.equalsIgnoreCase(vo.getRole())) {
			officer = hoID;
			recipientEmail = sendReminderDAO.searchEmailAddressByEmployeeNumber(hoID);
		}
		
		
		String reminderID = ":" + officer + "_____:_____" + batchId + ":";
		
		//create empty dummy to store first record
		DummyEmployee em = new DummyEmployee("", null, null, null, null);
		
		try {
			//create deadline agent instance and determine which reminder should be sent
			DeadlineAgent dla = new DeadlineAgent(vo.getDeadline(), inv1, inv2, inv3, inv4, lastMsgType);
			if (dla.isReadyToSend()) {
				if(!hashRecipient.containsKey(reminderID)) {
					em = new DummyEmployee(officer, null, recipientEmail,batchId,null);
					//set all reminder content once
					dla.reminder[0] = vo.getFirstReminder();
					dla.reminder[1] = vo.getSecondReminder();
					dla.reminder[2] = vo.getThirdReminder();
					dla.reminder[3] = vo.getSubsReminder();
					//then, attaching deadline agent instance reference to officer
					em.setDeadlineAgent(dla);
					//hashRecipient.put(officer, em);
					hashRecipient.put(reminderID, em);
				}else {
					//em = (DummyEmployee) hashRecipient.get(officer);
					em = (DummyEmployee) hashRecipient.get(reminderID);
				}
				
				//DummyEmployee de = (DummyEmployee) hashRecipient.get(officer);
				
				int reportID = (int)vo.getReportId();
				String reportEmpName = vo.getReportName();
				String reportStatus = vo.getStatus();
				String reportEmpRank = vo.getSubstantiveRank();
				Date reportStart = vo.getCommenceDate();
				Date reportEnd = vo.getEndDate();
				String presentPost = vo.getPresentPost();
				DummyReportInfo ri =new DummyReportInfo(reportID, reportEmpName, reportStatus, reportEmpRank, reportStart, reportEnd, presentPost);
				em.getReports().add(ri);
			}
			
		} catch (ParseException e) {
			
			e.printStackTrace();
			log.severe(e.toString());
		}
		return hashRecipient;
	}

}
