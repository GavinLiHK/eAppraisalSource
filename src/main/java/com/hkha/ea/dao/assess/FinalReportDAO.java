package com.hkha.ea.dao.assess;

import java.util.List;

import com.hkha.ea.domain.EaReport;
import com.hkha.ea.dto.assess.FinalReportResultDto;
import com.hkha.ea.dto.assess.SearchFinalReportDto;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;

public interface FinalReportDAO {

	public List<FinalReportResultDto> searchReport(String userID, String reportType, String rank, String postUnit,
			String name, String startDate, String endDate);
	
	public List<EaReport> searchReportRSM(String batchName, String rank, String postUnit,String employeeNumber,String employeeName, String startDate, String endDate);

	public void revertReportStatusForRSM(String[] strsArray)throws Exception;
	
	//added on 20170315 SU search Report RSM
	public List<EaReport> searchReportRSMSU(SearchFinalReportDto searchFinalReportDto, List<UserEnquiryModelDTO> gmlist);
	
}
