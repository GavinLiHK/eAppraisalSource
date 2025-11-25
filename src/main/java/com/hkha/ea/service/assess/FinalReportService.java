package com.hkha.ea.service.assess;

import java.util.List;

import com.hkha.ea.dto.assess.FinalReportResultDto;
import com.hkha.ea.dto.assess.SearchFinalReportDto;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;

public interface FinalReportService {

	public List<FinalReportResultDto> searchReport(SearchFinalReportDto searchFinalReportDto);
	
	public List<FinalReportResultDto> searchReportRSM(SearchFinalReportDto searchFinalReportDto);

	public String revertReportStatusForRSM(SearchFinalReportDto searchFinalReportDto);
	
	//added on 20170315 For SU search report RSM
	public List<FinalReportResultDto> searchReportRSMSU(SearchFinalReportDto searchFinalReportDto,List<UserEnquiryModelDTO> gmlist);
}
