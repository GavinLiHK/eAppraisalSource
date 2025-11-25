package com.hkha.ea.service.assess.impl;


import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraiseeReportMaintenanceDAO;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.dto.assess.AppraiseeEnquiryDto;
import com.hkha.ea.dto.assess.ReportNextRoleDto;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;
import com.hkha.ea.service.assess.AppraiseeReportMaintenanceService;

@Service("appraiseeReportMaintenanceService")
public class AppraiseeReportMaintenanceServiceImpl implements AppraiseeReportMaintenanceService{

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AppraiseeReportMaintenanceServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private AppraiseeReportMaintenanceDAO appraiseeReportMaintenanceDAO;
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public List<AppraiseeEnquiryDto> search(String curUser, String year, String trackDate, String rank, String postUnit, String employeeNumber,
			String appraisalPeriodStart, String appraisalPeriodEnd, String reportGenerated, boolean generate) throws Exception {
		List<AppraiseeEnquiryDto> list = new ArrayList<AppraiseeEnquiryDto>();
		try
		{
			Date commenceDate = DateTimeUtil.string2Date(appraisalPeriodStart);
			Date endDate = DateTimeUtil.string2Date(appraisalPeriodEnd);
			String commenceDateStr = DateTimeUtil.getDay(commenceDate)+"/"+DateTimeUtil.getMonth(commenceDate)+"/"+year;
			String endDateStr = DateTimeUtil.getDay(endDate)+"/"+DateTimeUtil.getMonth(endDate)+"/"+year;
			if (generate)
				appraiseeReportMaintenanceDAO.retrieveRecord(curUser, year, trackDate, rank, postUnit,  
					employeeNumber);
			/*
			List<SearchAppraiseeDto> result = appraiseeReportMaintenanceDAO.search(curUser, employeeNumber, rank, postUnit,  
					appraisalPeriodStart, appraisalPeriodEnd, reportGenerated);
			*/
			List<SearchAppraiseeDto> result = appraiseeReportMaintenanceDAO.search(curUser, employeeNumber, rank, postUnit,  
					commenceDateStr, endDateStr, reportGenerated);
			
			java.sql.Date effStartDate = Util.string2SqlDate(appraisalPeriodStart);
			
			if(result != null && result.size() > 0){
				for(int i=0; i<result.size(); i++){
					
					AppraiseeEnquiryDto asDto =  new AppraiseeEnquiryDto();
					SearchAppraiseeDto dto = result.get(i);
					
					if(dto != null){
							
							if (Constants.STATUS_CL.equalsIgnoreCase(dto.getStatus())) 
						        continue;
							
							if (DateTimeUtil.compare(dto.getDateToRank(), effStartDate)>0){
								appraisalPeriodStart = Util.date2String(dto.getDateToRank());
							}
							
							//log.info("dto employee num===="+dto.getEmployeeNumber());
							if(dto.getEmployeeNumber() != null){
								asDto.setEmployeeNum(String.valueOf(dto.getEmployeeNumber()));
							}
							
							asDto.setEffStartDate(appraisalPeriodStart);
							asDto.setEffEndDate(appraisalPeriodEnd);
							asDto.setFullName(dto.getEngName());
							asDto.setRank(dto.getRank());
							asDto.setPostUnit(dto.getPostUnit());
							asDto.setReportId(dto.getReportId());
							asDto.setBatchId(dto.getBatchId());
							asDto.setStatus(dto.getStatus());
							
							if(dto.getReportId() != null && dto.getReportId() > 0){
								if ( Constants.STATUS_HANDLED_BY_EXCEL.equalsIgnoreCase(dto.getStatus()))
									asDto.setReportGenerated(Constants.MESSAGE_HANDLED_BY_EXCEL);
								
								else if (Constants.STATUS_INITIAL.equalsIgnoreCase(dto.getStatus())) 
									asDto.setReportGenerated(Constants.YES);
								else
									asDto.setReportGenerated(Constants.MESSAGE_DISPATCHED + " (" + dto.getStatus() + ")");
							}
							else{
								asDto.setReportGenerated(Constants.NO);
							}
							list.add(asDto);
						}
					}
				}
			
//			SELECT R.*, ER.REPORT_ID FROM HA_HR_PEM001R R, HA_HR_EA_REPORT ER
//			WHERE R.COMPUTER_NUM = '1313561' AND R.EMPLOYEE_NUM = ER.EMPLOYEE_NUMBER (+)			sql
			
			return list;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.severe(e.toString());
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public List<ReportNextRoleDto> updateReport(List<Long> reportIds) {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return appraiseeReportMaintenanceDAO.getRole(userId,reportIds);
		
	}

	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public void deleteReport(List<Long> reportIds, String batchID) {
		
		appraiseeReportMaintenanceDAO.deleteReport(reportIds,batchID);
	}

	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public List<ReportNextRoleDto> handleByExcel(List<Long> reportIds) {
		
		return appraiseeReportMaintenanceDAO.handleByExcel(reportIds);
	}

	public List<AppraiseeEnquiryDto> searchGeneratedReport(String batchName, String rank, String postUnit,
			String postTitle, String curUser, boolean useLike) {
		List<AppraiseeEnquiryDto> list = new ArrayList<AppraiseeEnquiryDto>();
		List<EaReport> reports = appraiseeReportMaintenanceDAO.search(batchName, rank, postUnit, postTitle, curUser, useLike);
		if(reports != null && reports.size() > 0){
			for(int i=0; i<reports.size(); i++){
				AppraiseeEnquiryDto dto = new AppraiseeEnquiryDto();
				EaReport r = reports.get(i);
				if (Constants.STATUS_CL.equalsIgnoreCase(r.getStatus())) 
			        continue;
				
				dto.setEmployeeNum(r.getEmployeeNumber());
				dto.setEffStartDate(Util.date2String(r.getCommenceDate()));
				dto.setEffEndDate(Util.date2String(r.getEndDate()));
				dto.setFullName(r.getName());
				dto.setRank(r.getSubstantiveRank());
				dto.setPostUnit(r.getPresentPost());
				dto.setReportId(r.getReportId());
				dto.setBatchId(r.getBatchId());
				dto.setStatus(r.getStatus());
				
				if(r.getReportId() >0){
					if ( Constants.STATUS_HANDLED_BY_EXCEL.equalsIgnoreCase(r.getStatus()))
						dto.setReportGenerated(Constants.MESSAGE_HANDLED_BY_EXCEL);
					else if (Constants.STATUS_INITIAL.equalsIgnoreCase(r.getStatus())) 
						dto.setReportGenerated(Constants.YES);
					else
						dto.setReportGenerated(Constants.MESSAGE_DISPATCHED + " (" + r.getStatus() + ")");
				}
				else{
					dto.setReportGenerated(Constants.NO);
				}
				list.add(dto);
			}
		}
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public List<ReportNextRoleDto> updateReportRoleByBatch(List<String> batchIDs) {
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		return appraiseeReportMaintenanceDAO.getNextRoleByBatch(userId, batchIDs);
	}

}
