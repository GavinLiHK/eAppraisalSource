package com.hkha.ea.service.assess;

import java.sql.SQLException;
import java.util.List;

import com.hkha.ea.common.EAException;
import com.hkha.ea.dto.assess.AppraiseeEnquiryDto;
import com.hkha.ea.dto.assess.ReportNextRoleDto;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;

public interface AppraiseeReportMaintenanceService {

	public List<AppraiseeEnquiryDto> search(String name, String year, String trackDate, String rank, String postUnit, String employeeNumber,
			String appraisalPeriodStart, String appraisalPeriodEnd, String reportGenerated, boolean generate)throws Exception ;

	public List<ReportNextRoleDto> updateReport(List<Long> reportIds);

	public void deleteReport(List<Long> reportIds, String batchID);

	public List<ReportNextRoleDto> handleByExcel(List<Long> reportIds);

	public List<AppraiseeEnquiryDto> searchGeneratedReport(String batchName, String rank, String postUnit,
			String postTitle, String name, boolean useLike);

	public List<ReportNextRoleDto> updateReportRoleByBatch(List<String> reportIds);

}
