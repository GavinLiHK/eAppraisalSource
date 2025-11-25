package com.hkha.ea.service.common.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.common.NotificationManagerDAO;
import com.hkha.ea.domain.DummyEmployee;
import com.hkha.ea.domain.DummyReportInfo;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.dto.admin.MonitorReportVo;
import com.hkha.ea.dto.assess.ReportNextRoleDto;
import com.hkha.ea.service.common.NotificationManagerService;


@Service("notificationManagerService")
public class NotificationManagerServiceImpl implements NotificationManagerService{

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(NotificationManagerServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private NotificationManagerDAO notificationManagerDAO;
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void generateDispatchNotification(List<ReportNextRoleDto> dsNextRole) throws EAException {
		
		List<Long> reportIds = new ArrayList<Long>();
		for(int i=0; i<dsNextRole.size(); i++){
			if(dsNextRole.get(i).getReportId() != null){
				reportIds.add(dsNextRole.get(i).getReportId());
			}
		}
		if(reportIds != null && reportIds.size() > 0){
			List<MonitorReportVo> dsRecipients = notificationManagerDAO.generateDispatchNotification(reportIds);
			Hashtable hashRecipient = new Hashtable();
			
			if(dsRecipients != null && dsRecipients.size() > 0){
				for(int i=0; i<dsRecipients.size();  i++){
					MonitorReportVo  dsRecipient = dsRecipients.get(i);
					if(dsRecipient != null){
						String recipientBatch = dsRecipient.getBatchId();
						String recipientNumber = dsRecipient.getPeopleEmplyNum();
						String recipientName = dsRecipient.getFullName();
						String recipientEmail = dsRecipient.getEmailAddress();
						String reportNotification = dsRecipient.getNotification();
						
						String notificationID = ":" + recipientNumber + "_____:_____" + recipientBatch + ":";
						if (!hashRecipient.containsKey(notificationID)) {
							hashRecipient.put(notificationID, new DummyEmployee(recipientNumber, recipientName, recipientEmail, recipientBatch, reportNotification));
						}
						DummyEmployee de = (DummyEmployee) hashRecipient.get(notificationID);

						int reportID = Integer.valueOf(dsRecipient.getReportId().toString());
						String reportEmpName = dsRecipient.getReportName();
						String reportStatus = dsRecipient.getStatus();
						String reportEmpRank = dsRecipient.getSubstantiveRank();
						java.sql.Date reportStart = DateTimeUtil.toSQLDate(dsRecipient.getCommenceDate());
						java.sql.Date reportEnd = DateTimeUtil.toSQLDate(dsRecipient.getEndDate());
						java.sql.Date dueDate = DateTimeUtil.toSQLDate(dsRecipient.getDeadLine());
						if (dueDate != null) dueDate = DateTimeUtil.toSQLDate(dsRecipient.getOverallDeadLine());
						DummyReportInfo ri = new DummyReportInfo(reportID, reportEmpName, reportStatus, reportEmpRank, reportStart, reportEnd);
						ri.setDueDate(dueDate);
						de.getReports().add(ri);
					}
				}
				EaMessageLog dsHaHrEaMessageLog = new EaMessageLog();
				List<EaMessageLog> messages = new ArrayList<EaMessageLog>();

				//send email & generate message log
				Iterator itr = hashRecipient.keySet().iterator();
				
				while (itr.hasNext()) {
					String key = (String) itr.next();
					DummyEmployee de = (DummyEmployee) hashRecipient.get(key);
					
					// NOTE: All report's role set in same batch must be completely same.
					//       thus, checking the first officer role of first report in 
					//       DummyEmployee instance is equal to checking whole set of reports.
					if (de.getReports().size() > 0) {
					    DummyReportInfo ri = (DummyReportInfo)de.getReports().elementAt(0);
					    log.info("Dispatch to "+ri.getStatus());

					    if (Constants.STATUS_AO.equals(ri.getStatus())){
					    	messages = notificationManagerDAO.sendNotification(de, ri.getDueDate(), false, dsHaHrEaMessageLog);
					    }
					    else
					    	messages = notificationManagerDAO.sendNotificationByBatch( de, ri.getDueDate(), false, dsHaHrEaMessageLog);
					}
				}
				
				notificationManagerDAO.updateMessageLog(messages);
			}
		}
		
	}

}
