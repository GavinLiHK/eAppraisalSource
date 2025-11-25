package com.hkha.ea.service.admin;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import jakarta.servlet.http.HttpServletRequest;

import com.hkha.ea.common.EAException;
import com.hkha.ea.dto.admin.ReportReminderDto;

public interface SendReminderService {

	public ReportReminderDto generateList(HttpServletRequest request, List<Long> reportIds);

	public String sendNotification(HttpServletRequest request, ReportReminderDto reportReminderDto)throws Exception;

	public void sendAutoReminder();

}
