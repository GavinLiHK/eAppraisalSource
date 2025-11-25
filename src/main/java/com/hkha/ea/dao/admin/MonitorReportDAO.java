package com.hkha.ea.dao.admin;

import java.util.List;

import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.dto.admin.MonitorReportVo;

public interface MonitorReportDAO {

	public List<MonitorReportVo> search(String rank, String employeeNumber, String postUnit, String sortSequence);
	
	public List<MonitorReportVo> searchAP(String rank, String employeeNumber, String postUnit, String sortSequence);

	public List<EaMessageLog> searchLogFromReportIDAndStatus(Long reportId, String status);

	public List<EaMessageLog> searchLogNotification(Long reportID);
	
	public List<EaMessageLog> searchLogNewestNotification(Long reportId);

	public List<MonitorReportVo> search(List<Long> reportIds);

	public List<MonitorReportVo> searchAll(String rank, String employeeNumber, String postUnit, String sortSequence);

}
