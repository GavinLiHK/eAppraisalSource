package com.hkha.ea.service.common;

import com.hkha.ea.dto.common.PrintReportPrepareDTO;

public interface PrintReportService {

	public PrintReportPrepareDTO generateReportPrepare(String rptId,String roleCode);
}
