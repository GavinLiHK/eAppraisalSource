package com.hkha.ea.service.assess.impl;

import java.util.ArrayList;
import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.dao.assess.FinalReportDAO;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.dto.assess.FinalReportResultDto;
import com.hkha.ea.dto.assess.SearchFinalReportDto;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;
import com.hkha.ea.service.assess.FinalReportService;

@Service("finalReportService")
public class FinalReportServiceImpl implements FinalReportService{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(FinalReportServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private FinalReportDAO finalReportDAO;

	public List<FinalReportResultDto> searchReport(SearchFinalReportDto searchFinalReportDto) {
		
		return finalReportDAO.searchReport(SecurityContextHolder.getContext().getAuthentication().getName(), searchFinalReportDto.getReportTypeJmesa(), searchFinalReportDto.getRankJmesa(), searchFinalReportDto.getPostUnitJmesa(), searchFinalReportDto.getEmployeeNameJmesa(), searchFinalReportDto.getCommenceStartDateJmesa(), searchFinalReportDto.getCommenceEndDateJmesa());
	}
	
	public List<FinalReportResultDto> searchReportRSM(SearchFinalReportDto searchFinalReportDto) {
		
		List<EaReport> result = finalReportDAO.searchReportRSM(searchFinalReportDto.getBatchName(), searchFinalReportDto.getRank(), searchFinalReportDto.getPostUnit(), searchFinalReportDto.getEmployeeName(),searchFinalReportDto.getEmployeeNumber(), searchFinalReportDto.getCommenceStartDate(), searchFinalReportDto.getCommenceEndDate());
		
		List<FinalReportResultDto> list = new ArrayList<FinalReportResultDto>();
		
		if(result != null && result.size() > 0){
			for(int i=0; i<result.size(); i++){
				EaReport rp = result.get(i);
				if(rp != null){
					FinalReportResultDto dto = new FinalReportResultDto();
					dto.setReportId(String.valueOf(rp.getReportId()));
					dto.setBatchName(rp.getBatchId());
					dto.setSubstantiveRank(rp.getSubstantiveRank());
					dto.setPresentPost(rp.getPresentPost());
					dto.setEmployeeNumber(rp.getEmployeeNumber());
					dto.setName(rp.getName());
					dto.setCommenceDate(DateTimeUtil.date2String(rp.getCommenceDate()));
					dto.setCommenceDateSort(rp.getCommenceDate());
					list.add(dto);
				}
			}
		}
		
		return list;
	}
	@Transactional(rollbackFor=Exception.class)
	public String revertReportStatusForRSM(SearchFinalReportDto searchFinalReportDto){
		String selectedOptions=searchFinalReportDto.getSelectedOptions();
		String strs=selectedOptions.substring(0,selectedOptions.length()-1);
		String[] strsArray=strs.split(",");
		String flag="";
		try {
			finalReportDAO.revertReportStatusForRSM(strsArray);
			flag="success";
		} catch (Exception e) {				
			flag="fail";
			e.printStackTrace();
			log.severe(e.toString());
		}
		return flag;
	}
	
	//added on 20170315 let SU search report RSM
	public List<FinalReportResultDto> searchReportRSMSU(SearchFinalReportDto searchFinalReportDto, List<UserEnquiryModelDTO> gmlist) {
		
		List<EaReport> result = finalReportDAO.searchReportRSMSU(searchFinalReportDto,gmlist);
		log.info("result_frsi: "+result.size());
		List<FinalReportResultDto> list = new ArrayList<FinalReportResultDto>();
		
		if(result != null && result.size() > 0){
			for(int i=0; i<result.size(); i++){
				EaReport rp = result.get(i);
				if(rp != null){
					FinalReportResultDto dto = new FinalReportResultDto();
					dto.setReportId(String.valueOf(rp.getReportId()));
					dto.setBatchName(rp.getBatchId());
					dto.setSubstantiveRank(rp.getSubstantiveRank());
					dto.setPresentPost(rp.getPresentPost());
					dto.setEmployeeNumber(rp.getEmployeeNumber());
					dto.setName(rp.getName());
					dto.setCommenceDate(DateTimeUtil.date2String(rp.getCommenceDate()));
					dto.setCommenceDateSort(rp.getCommenceDate());
					list.add(dto);
				}
			}
		}
		
		return list;
	}
	//end added on 20170315

}
