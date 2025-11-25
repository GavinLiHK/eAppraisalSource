package com.hkha.ea.dao.common.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.common.NotificationManagerDAO;
import com.hkha.ea.domain.DummyEmployee;
import com.hkha.ea.domain.DummyReportInfo;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.dto.admin.MonitorReportVo;

@Repository("notificationManagerDAO")
public class NotificationManagerDAOImpl implements NotificationManagerDAO {
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(NotificationManagerDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	//20240821 Name Presentation
	private String fullNamePattern = " initcap(f.title) || ' ' || upper(f.last_name) || ' ' || initcap(f.first_name) || decode(f.PRE_NAME_ADJUNCT, null, '', (' ' || initcap(lower(f.PRE_NAME_ADJUNCT)))) || decode(f.suffix, null, '', (', ' || f.suffix)) fullName ,";
	//End 20240821 Name Presentation

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<MonitorReportVo> generateDispatchNotification(List<Long> reportIds) {

		StringBuffer criteria = new StringBuffer();

		criteria.append(" select r.REPORT_ID reportId, r.EMPLOYEE_NUMBER reportEmployeeNum, r.NAME reportName, r.COMMENCE_DATE commenceDate, r.END_DATE endDate, ");
		criteria.append(" r.OVERALL_DEADLINE overallDeadLine, r.STATUS status, r.SUBSTANTIVE_RANK substantiveRank, r.PRESENT_POST persentPost, r.BATCH_ID batchId, ");
		criteria.append(" role1.ROLE role1, role1.OFFICER_ID officerId, role1.DEADLINE deadLine, role1.NOTIFICATION notification, ");
		//20240821 Name Presentation
		criteria.append(fullNamePattern);
//		criteria.append(" f.full_name fullName, f.email_address emailAddress, f.employee_number peopleEmplyNum  ");
		criteria.append(" f.email_address emailAddress, f.employee_number peopleEmplyNum  ");
		//End 20240821 Name Presentation
		criteria.append(" from ha_hr_ea_report r,ha_hr_ea_report_role role1, ha_hr_ea_report_role role2,per_all_people_f f ");
		criteria.append(" where ");
		criteria.append(" r.REPORT_ID in ( ");

		for (int i = 0; i < reportIds.size(); i++) {
			if (reportIds.get(i) != null) {
				if (i != 0)
					criteria.append(",");
				criteria.append(reportIds.get(i));

			}
		}
		criteria.append(" ) ");
		criteria.append(" and r.REPORT_ID = role1.REPORT_ID ");
		criteria.append(" and r.STATUS = role1.ROLE ");
		criteria.append(" AND role1.role = role2.role ");
		criteria.append(" and role1.REPORT_ID = role2.REPORT_ID ");
		criteria.append(" and role2.officer_id is not null ");
		criteria.append(" and r.status != 'CL'");
		criteria.append(" and role1.officer_id = f.employee_number ");

		criteria.append("   and (" + " f.effective_end_date = to_date('31124712', 'ddmmyyyy') " + " or "
				+ " f.effective_start_date >= ( "
				+ " select max(papfm.effective_start_date) from per_all_people_f papfm where papfm.employee_number = f.employee_number "
				+ " ) " + ") ");

		List<MonitorReportVo> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<MonitorReportVo>(MonitorReportVo.class));
		return result;
	}

	public void updateMessageLog(List<EaMessageLog> messages) {
		updateMessageLog(messages, null);
	}
	
	public void updateMessageLog(List<EaMessageLog> messages, String userId) {
		if (messages != null && messages.size() > 0) {
			try{
				for (int i = 0; i < messages.size(); i++) {
					EaMessageLog megLog = messages.get(i);
					if (megLog != null) {
						if (Util.isEmptyString(userId)){
							userId = SecurityContextHolder.getContext().getAuthentication().getName();
						}
						log.info("Recipient ID: "+megLog.getRecipient());
						megLog.setCreatedBy(userId);
						megLog.setCreationDate(new Date());
						megLog.setLastUpdatedBy(userId);
						megLog.setLastUpdateDate(new Date());
						hibernateTemplate.save(megLog);
					}
				}
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

	}

	public Hashtable searchEmailAddressByEmployeeNumber(Vector employeeNumber) throws EAException {
		Hashtable email = new Hashtable();

		if ((employeeNumber != null) && (employeeNumber.size() > 0)) {
			StringBuffer sql = new StringBuffer();
			try {
				sql.append("select PER_ALL_PEOPLE_F.employee_number, PER_ALL_PEOPLE_F.email_address ");
				sql.append("from PER_ALL_PEOPLE_F where PER_ALL_PEOPLE_F.employee_number in (");
				sql.append(Util.concatValues(employeeNumber, "'", ","));
				sql.append(") ");
				//sql.append("and (sysdate between effective_start_date and effective_end_date or effective_end_date is null) ");
				sql.append("and sysdate between effective_start_date and effective_end_date ");
				sql.append("and PER_ALL_PEOPLE_F.email_address is not null ");

				Statement st = jdbcTemplate.getDataSource().getConnection().createStatement();
				ResultSet rs = st.executeQuery(sql.toString());
				while (rs.next()) {
					if (!email.containsKey(rs.getString(1))) {
						email.put(rs.getString(1), rs.getString(2));
					}
				}
				rs.close();
				st.close();

			} catch (SQLException e) {
				throw new EAException(e);
			}
		}
		return email;
	}

	public void send(String from, String to, Vector cc, String subject, String lines) throws Exception {
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("application-local.properties"));
		final String user = props.getProperty("mail.username");
		final String password = props.getProperty("mail.password");
		final String emailEnabled = props.getProperty("email.enabled");

		Session session = null;

		if (Util.isEmptyString(user) && Util.isEmptyString(password)) {
			props.put("mail.smtp.auth", "false");
			session = Session.getInstance(props);
		} else {
			props.put("mail.smtp.auth", "true");
			session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
		}

		log.info("email.enabled property: " + emailEnabled );
		if ("Y".equalsIgnoreCase(emailEnabled)) {
			log.info("NotificationManager.send: ready to send email.");

			// new email message
			MimeMessage message = new MimeMessage(session);

			// set email
			message.setFrom(new InternetAddress(from));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(lines.toString());
			message.setSentDate(new Date());
			
			if ((cc != null) && (cc.size() > 0)){
				for (int i=0; i<cc.size(); i++){
					message.addRecipient(RecipientType.CC, new InternetAddress(cc.get(i).toString()));
				}
			}

			try {
				Transport.send(message);
				log.info("NotificationManager.send: send email completed.");
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

	}

	/**
	 * Send notification message
	 * 
	 * @param appName                  Application Name for creating model instance
	 * @param de                       DummyEmployee instance to store email information
	 * @param dueDate                  Due Date for these tasks
	 * @param individualReportDeadline Display individual due date after appraisal period of each report
	 * @param dsHaHrEaMessageLog       MessageLog instance
	 * @throws EAException
	 */
	public List<EaMessageLog> sendNotification(DummyEmployee de, Date dueDate, boolean individualReportDeadline,
			EaMessageLog dsHaHrEaMessageLog) throws EAException {
		try {
			log.info("*** send Notification***");
			String getHasEmail = de.getHasEmail();
			String mailSubject = "Appraisal Report Notification";

			Properties props = new Properties();
			props.load(this.getClass().getClassLoader().getResourceAsStream("application-local.properties"));
			final String user = props.getProperty("mail.username");
			final String password = props.getProperty("mail.password");

			Session session = null;

			if (Util.isEmptyString(user) && Util.isEmptyString(password)) {
				props.put("mail.smtp.auth", "false");
				session = Session.getInstance(props);
			} else {
				props.put("mail.smtp.auth", "true");
				session = Session.getInstance(props, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});
			}

			// new email message
			MimeMessage message = new MimeMessage(session);
			StringBuffer mailBody = new StringBuffer();
			//Vector mailBody = new Vector();
			if (de.hasEmail()) {
				mailBody.append("The appraisal report(s) of the following officer(s) has/have been sent to you via the web");
				mailBody.append("\n\n");
			}

			for (int r = 0; r < de.getReports().size(); r++) {
				DummyReportInfo ri = (DummyReportInfo) de.getReports().get(r);
				if (de.hasEmail())
					mailBody.append(ri.toDisplayString(individualReportDeadline));
					mailBody.append("\n");
			}
				mailBody.append("\n");
			if (de.hasEmail()) {
				log.info("***Ready to send email***");
				// ## BEGIN 2005-12-22 Added by Jeff
				mailBody.append(de.getNotification());
				// ## END 2005-12-22 Added by Jeff
				mailBody.append("\n");
				mailBody.append("Please complete the report(s) accordingly by using the following link:");
				mailBody.append("\n");
				mailBody.append(Util.getEAppURL());		// 20231120 Update eApp URL
				mailBody.append("\n");
				// set email
				message.setFrom(new InternetAddress(props.getProperty("email.ReminderSender")));
				message.setRecipient(RecipientType.TO, new InternetAddress(de.getEmail()));
				message.setSubject(mailSubject);
				message.setText(mailBody.toString());
				message.setSentDate(new Date());
				try {
					// send email
					Transport.send(message);
					log.info("***Send email completed.***");
				} catch (Exception ex) {
					getHasEmail = "F";
					log.severe(ex.toString());
				}
			}
			String username = SecurityContextHolder.getContext().getAuthentication().getName();

			List<EaMessageLog> messages = new ArrayList<EaMessageLog>();
			for (int r = 0; r < de.getReports().size(); r++) {
				DummyReportInfo ri = (DummyReportInfo) de.getReports().get(r);
				dsHaHrEaMessageLog = new EaMessageLog();
				// EaMessageLogPK id = new EaMessageLogPK();
				dsHaHrEaMessageLog.setReportId(Long.valueOf(String.valueOf(ri.getId())));
				// dsHaHrEaMessageLog.setId(id);
				dsHaHrEaMessageLog.setRole(ri.getStatus());
				dsHaHrEaMessageLog.setRecipient(de.getNumber());
				dsHaHrEaMessageLog.setMessageType(Constants.MESSAGE_LOG_MESSAGE_TYPE_NOTIFICATION);
				dsHaHrEaMessageLog.setType(Constants.MESSAGE_LOG_TYPE_MANUAL);
				dsHaHrEaMessageLog.setRecipientRole(ri.getRecipientRole());
				dsHaHrEaMessageLog.setEmailInd(getHasEmail);
				dsHaHrEaMessageLog.setCreatedBy(username);
				dsHaHrEaMessageLog.setCreationDate(new Date());
				dsHaHrEaMessageLog.setLastUpdatedBy(username);
				dsHaHrEaMessageLog.setLastUpdateDate(new Date());
				messages.add(dsHaHrEaMessageLog);
			}
			return messages;

		} catch (Exception e) {
			// e.printStackTrace();
			//log.severe(e.toString());
			throw new EAException(e);
		}

	}

	/**
	 * Send notification message function for dispatching
	 * 
	 * @param appName                  Application Name for creating model instance
	 * @param de                       DummyEmployee instance to store email information
	 * @param dueDate                  Due Date for these tasks
	 * @param individualReportDeadline Display individual due date after appraisal period of each report
	 * @param dsHaHrEaMessageLog       MessageLog instance
	 * @throws EAException
	 */
	public List<EaMessageLog> sendNotificationByBatch(DummyEmployee de, Date dueDate, boolean individualReportDeadline,
			EaMessageLog dsHaHrEaMessageLog) throws EAException {
		try {
			String getHasEmail = de.getHasEmail();
			String mailSubject = "Appraisal Report Notification";
			//Vector mailBody = new Vector();
			StringBuffer mailBody = new StringBuffer();

			Properties props = new Properties();
			props.load(this.getClass().getClassLoader().getResourceAsStream("application-local.properties"));
			final String user = props.getProperty("mail.username");
			final String password = props.getProperty("mail.password");
			
			Session session = null;
			if (Util.isEmptyString(user) && Util.isEmptyString(password)) {
				props.put("mail.smtp.auth", "false");
				session = Session.getInstance(props);
			} else {
				props.put("mail.smtp.auth", "true");
				session = Session.getInstance(props, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});
			}
			// new email message
			MimeMessage message = new MimeMessage(session);

			if (de.hasEmail()) {
				mailBody.append(de.getNotification());
				mailBody.append("\n\n");
				mailBody.append(de.getBatchID());
				mailBody.append("\n\n");
				mailBody.append(Util.getEAppURL());	// 20231120 Update eApp URL
				mailBody.append("\n");

				message.setFrom(new InternetAddress(props.getProperty("email.ReminderSender")));
				message.setRecipient(RecipientType.TO, new InternetAddress(de.getEmail()));
				message.setSubject(mailSubject);
				message.setText(mailBody.toString());
				message.setSentDate(new Date());

				try {
					Transport.send(message);
				} catch (Exception ex) {
					getHasEmail = "F";
				}
			}

			List<EaMessageLog> messages = new ArrayList<EaMessageLog>();
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			
			// int messageId = dsHaHrEaMessageLog.getNextId(Util.getAppName());
			for (int r = 0; r < de.getReports().size(); r++) {
				DummyReportInfo ri = (DummyReportInfo) de.getReports().get(r);
				dsHaHrEaMessageLog = new EaMessageLog();
				// EaMessageLogPK id = new EaMessageLogPK();
				dsHaHrEaMessageLog.setReportId(Long.valueOf(String.valueOf(ri.getId())));
				// dsHaHrEaMessageLog.setId(id);
				dsHaHrEaMessageLog.setRole(ri.getStatus());
				dsHaHrEaMessageLog.setRecipient(de.getNumber());
				dsHaHrEaMessageLog.setMessageType(Constants.MESSAGE_LOG_MESSAGE_TYPE_NOTIFICATION);
				dsHaHrEaMessageLog.setType(Constants.MESSAGE_LOG_TYPE_MANUAL);
				dsHaHrEaMessageLog.setRecipientRole(ri.getRecipientRole());
				dsHaHrEaMessageLog.setEmailInd(getHasEmail);
				dsHaHrEaMessageLog.setCreatedBy(username);
				dsHaHrEaMessageLog.setCreationDate(new Date());
				dsHaHrEaMessageLog.setLastUpdatedBy(username);
				dsHaHrEaMessageLog.setLastUpdateDate(new Date());
				messages.add(dsHaHrEaMessageLog);
			}

			return messages;
		} catch (Exception e) {
			// e.printStackTrace();
			//log.severe(e.toString());
			throw new EAException(e);
		}
	}
	
}
