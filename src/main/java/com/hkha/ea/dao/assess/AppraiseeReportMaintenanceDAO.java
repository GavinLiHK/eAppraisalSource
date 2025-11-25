package com.hkha.ea.dao.assess;

import java.sql.SQLException;
import java.util.List;

import com.hkha.ea.domain.EaReport;
import com.hkha.ea.dto.assess.AppraiseeEnquiryDto;
import com.hkha.ea.dto.assess.ReportNextRoleDto;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;


public interface AppraiseeReportMaintenanceDAO {

	public List<SearchAppraiseeDto> search(String curUser, String employeeNumber, String rank, String postUnit, String appraisalPeriodStart,
			String appraisalPeriodEnd, String reportGenerated) throws SQLException;

	public List<ReportNextRoleDto> getRole(String userId,List<Long> reportIds);

	public void deleteReport(List<Long> reportIds, String batchID);

	public List<ReportNextRoleDto> handleByExcel(List<Long> reportIds);

	public List<EaReport> search(String batchName, String rank, String postUnit, String postTitle, String curUser,
			boolean useLike);
	
	public List<ReportNextRoleDto> getNextRoleByBatch(String userId ,List<String> batchIDs);
	
	public boolean checkPartialBatch(List<Long> reportIDs, String batchID );

	public void retrieveRecord(String curUser, String year, String trackDate, String rank, String postUnit,
			String employeeNumber);

}
