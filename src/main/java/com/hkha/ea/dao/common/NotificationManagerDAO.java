package com.hkha.ea.dao.common;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.hkha.ea.common.EAException;
import com.hkha.ea.domain.DummyEmployee;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.dto.admin.MonitorReportVo;

public interface NotificationManagerDAO {

	public List<MonitorReportVo> generateDispatchNotification(List<Long> reportIds);

	public void updateMessageLog(List<EaMessageLog> messages);
	
	public void updateMessageLog(List<EaMessageLog> messages, String userId);
	
	public Hashtable searchEmailAddressByEmployeeNumber(Vector employeeNumber)throws EAException;
	
	public void send(String from, String to, Vector cc, String subject, String lines) throws Exception;
	
	public List<EaMessageLog> sendNotification(DummyEmployee de, Date dueDate, boolean individualReportDeadline, EaMessageLog dsHaHrEaMessageLog) throws EAException;
	
	public List<EaMessageLog> sendNotificationByBatch(DummyEmployee de, Date dueDate, boolean individualReportDeadline, EaMessageLog dsHaHrEaMessageLog
		     ) throws EAException;
	
	
}
