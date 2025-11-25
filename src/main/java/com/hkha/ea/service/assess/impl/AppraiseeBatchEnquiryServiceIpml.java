package com.hkha.ea.service.assess.impl;

import java.util.ArrayList;
import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

import org.apache.commons.beanutils.BeanUtils;
//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.dao.assess.AppraiseeBatchEnquiryDAO;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dto.assess.AppraiseeEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryVo;
import com.hkha.ea.service.assess.AppraiseeBatchEnquiryService;
import com.hkha.ea.service.assess.AppraiseeReportMaintenanceService;

@Service("appraiseeBatchEnquiryService")
public class AppraiseeBatchEnquiryServiceIpml implements AppraiseeBatchEnquiryService{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AppraiseeBatchEnquiryServiceIpml.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private AppraiseeReportMaintenanceService appraiseeReportMaintenanceService;
	
	@Autowired
	private AppraiseeBatchEnquiryDAO appraiseeBatchEnquiryDAO;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;

	public List<BatchEnquiryDto> searchBatch(String batchName, String rank, String postUnit, String postTitle, String name) {
		return appraiseeBatchEnquiryDAO.searchBatch( batchName,  rank,  postUnit,  postTitle,  name);
	}

	public List<AppraiseeEnquiryDto> searchReport(String batchName, String rank, String postUnit, String postTitle,
			String name, boolean useLike) {
		return appraiseeReportMaintenanceService.searchGeneratedReport( batchName,  rank,  postUnit,  postTitle,
				 name,  useLike);
	}

	public List<BatchEnquiryDto> searchAppraiseeBatch(String curUser, String batchName, String employeeNum,
			String employeeName, String rank, String postUnit, String postTitle) {
		return appraiseeBatchEnquiryDAO.searchAppraiseeBatch( curUser,  batchName,  employeeNum,
				 employeeName,  rank,  postUnit,  postTitle);
	}

	public List<BatchEnquiryDto> searchAppraiseeBatchList(String batchName) {
		return appraiseeCommonSearchDAO.searchAppraiseeBatchList(batchName);
	}
	
	public List<BatchEnquiryVo> searchAppraiseeBatchVoList(String batchName) {
		List<BatchEnquiryVo> voList = new ArrayList<BatchEnquiryVo>();
		try {	
			List<BatchEnquiryDto> list =  appraiseeCommonSearchDAO.searchAppraiseeBatchList(batchName);
			if(list != null && list.size()>0){				
				for(int i=0; i<list.size(); i++){
					BatchEnquiryVo vo = new BatchEnquiryVo();
					BeanUtils.copyProperties(vo,list.get(i));				
					vo.setCommenceDate(list.get(i).getCommenceDate());
					vo.setEndDate(list.get(i).getEndDate());
					voList.add(vo);
				}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			log.severe(e.toString());
		}
		
		return voList;
	}


}
