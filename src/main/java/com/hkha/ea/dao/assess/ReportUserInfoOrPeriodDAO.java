package com.hkha.ea.dao.assess;

import java.util.List;

import com.hkha.ea.dto.assess.BatchEnquiryDto;

public interface ReportUserInfoOrPeriodDAO {

	public List<BatchEnquiryDto> searchRpUserInfoOrPeriod(String curUser, String batchName, String appraiseeName, 
			String appraiseeNumber, String rank, String postUnit, String postTitle, long groupid);
	
	public int searchAppraisalPeriodOverlappedWithExistingReport(String commenceDate,String endDate,String employees);
	
	public void saveChangeAppraisalPeriodDate(List<String> rptIds,String commenceDate,String endDate)throws Exception;
}
