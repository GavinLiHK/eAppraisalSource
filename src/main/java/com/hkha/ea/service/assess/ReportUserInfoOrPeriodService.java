package com.hkha.ea.service.assess;

import java.util.List;

import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryVo;

public interface ReportUserInfoOrPeriodService {

	public List<BatchEnquiryDto> searchRpUserInfoOrPeriod(String name, String batchName, String employeeNum,
			String employeeName, String rank, String postUnit, String postTitle, long groupid);

	public List<BatchEnquiryVo> searchAppraiseeBatchList(String batchName);
	
	public void saveChangeAppraisalPeriodDate(List<String> rptIds,String commenceDate,String endDate)throws Exception;

}
