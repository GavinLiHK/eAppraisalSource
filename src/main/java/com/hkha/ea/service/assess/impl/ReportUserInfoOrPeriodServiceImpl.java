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
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.assess.ReportUserInfoOrPeriodDAO;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryVo;
import com.hkha.ea.service.assess.ReportUserInfoOrPeriodService;

@Service("reportUserInfoOrPeriodService")
public class ReportUserInfoOrPeriodServiceImpl implements ReportUserInfoOrPeriodService{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(ReportUserInfoOrPeriodServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private ReportUserInfoOrPeriodDAO reportUserInfoOrPeriodDAO;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;

	public List<BatchEnquiryDto> searchRpUserInfoOrPeriod(String curUser, String batchName, String appraiseeNumber,
			String appraiseeName, String rank, String postUnit, String postTitle, long groupid) {

		return reportUserInfoOrPeriodDAO.searchRpUserInfoOrPeriod(curUser, batchName, appraiseeName, appraiseeNumber, rank, postUnit, postTitle,groupid);
	}

	public List<BatchEnquiryVo> searchAppraiseeBatchList(String batchName){
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
	@Transactional (rollbackFor=Exception.class)
	public void saveChangeAppraisalPeriodDate(List<String> rptIds,String commenceDate,String endDate)throws Exception{
	
		reportUserInfoOrPeriodDAO.saveChangeAppraisalPeriodDate(rptIds, commenceDate, endDate);

	}

}
