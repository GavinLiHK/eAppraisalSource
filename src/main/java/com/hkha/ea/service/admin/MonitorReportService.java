package com.hkha.ea.service.admin;

import java.util.List;

import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.dto.admin.MonitorReportDto;
import com.hkha.ea.dto.admin.MonitorReportVo;
import com.hkha.ea.dto.common.MessageLogDto;


public interface MonitorReportService {

	public List<MonitorReportDto> search(String rank, String employeeNumber, String postUnit, String sortSequence);

	public List<MonitorReportDto> search(List<Long> reportIds);

	public List<MessageLogDto> searchLogFromReportIDAndStatus(Long long1, String role);

}
