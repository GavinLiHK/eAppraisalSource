package com.hkha.ea.dao.assess.impl;

import java.util.ArrayList;
import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.assess.FinalReportDAO;
import com.hkha.ea.dao.workflow.WorkflowTemplateMaintenanceDAO;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.dto.assess.FinalReportResultDto;
import com.hkha.ea.dto.assess.SearchFinalReportDto;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;

@Repository("finalReportDAO")
public class FinalReportDAOImpl implements FinalReportDAO {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(FinalReportDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private WorkflowTemplateMaintenanceDAO workflowTemplateMaintenanceDAO;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	public List<FinalReportResultDto> searchReport(String userID, String reportType, String rank, String postUnit,
			String name, String startDate, String endDate) {
		
		//userID = "ACO-G3";
		List<FinalReportResultDto> list = new ArrayList<FinalReportResultDto>();
		StringBuffer criteria = new StringBuffer();
		
		criteria.append(" select r.*")
				.append(" from ha_hr_ea_report r left join ha_hr_ea_workflow w on r.WORKFLOW_TEMPLATE_ID = w.WORKFLOW_TEMPLATE_ID ")
				.append(" left join ha_hr_ea_group_rank rank on r.SUBSTANTIVE_RANK = rank.RANK ")
				.append(" left join ha_hr_ea_user u on u.GROUP_ID = rank.GROUP_ID ")
				.append(" where ")
				.append(" u.USER_ID='" + userID + "' ")
				.append(" and r.STATUS = 'CL' ");

		if (!Util.isEmptyString(startDate))
			criteria.append(" and r.COMMENCE_DATE between to_date('" + startDate + "','DD/MM/YYYY') and to_date('"
					+ endDate + "','DD/MM/YYYY') ");

		if (!Util.isEmptyString(reportType))
			criteria.append(" and upper(w.REPORT_TEMPLATE) = '").append(reportType.toUpperCase()).append("' ");
		else
			criteria.append(" and upper(w.REPORT_TEMPLATE) <> 'M' ");
		if (!Util.isEmptyString(rank))
			criteria.append(" and upper(r.SUBSTANTIVE_RANK) LIKE '%")
					.append(Util.handleSCInSQL(rank.toUpperCase())).append("%' ");
		if (!Util.isEmptyString(postUnit))
			criteria.append(" and upper(r.PRESENT_POST) LIKE '%")
					.append(Util.handleSCInSQL(postUnit.toUpperCase())).append("%' ");
		if (!Util.isEmptyString(name))
			criteria.append(" and upper(r.NAME) LIKE '%").append(Util.handleSCInSQL(name.toUpperCase()))
					.append("%' ");

		List<EaReport> result = jdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReport>(EaReport.class));
		if(result != null && result.size() > 0){
			for(int i=0; i<result.size(); i++){
				EaReport rp = result.get(i);
				if(rp != null){
					FinalReportResultDto dto = new FinalReportResultDto();
					dto.setReportId(String.valueOf(rp.getReportId()));
					dto.setEmployeeNumber(rp.getEmployeeNumber());
					dto.setName(rp.getName());
					dto.setPresentPost(rp.getPresentPost());
					dto.setCommenceDate(DateTimeUtil.date2String(rp.getCommenceDate()));
					dto.setCommenceDateSort(rp.getCommenceDate());
					dto.setSubstantiveRank(rp.getSubstantiveRank());
					if(rp.getWorkflowTemplateId() != 0){
						EaWorkflow w = workflowTemplateMaintenanceDAO.searchWorkflowInfo(rp.getWorkflowTemplateId());
						dto.setReportTemplate(w.getReportTemplate());
					}
						
					list.add(dto);
				}
			}
		}
		return list;
	}

	public List<EaReport> searchReportRSM(String batchName, String rank, String postUnit,String employeeNumber,String employeeName, 
			String startDate, String endDate) {
		
		StringBuffer criteria = new StringBuffer();
		criteria.append("SELECT HHER.* ")
		.append(" FROM HA_HR_EA_REPORT HHER,HA_HR_EA_USER HHEU, HA_HR_EA_GROUP_RANK HHEGR,HA_HR_EA_GROUP_FUNCTION HHEGF")		
		.append(" WHERE 1=1 ")
		.append(" AND HHEU.USER_ID = '"+SecurityContextHolder.getContext().getAuthentication().getName()+"' ")
		.append(" AND HHEU.STATUS = 'Y' ")
		.append(" AND HHER.STATUS = 'CL' ")
		.append(" AND HHER.SUBSTANTIVE_RANK = HHEGR.RANK ")
		.append(" AND HHEU.GROUP_ID = HHEGR.GROUP_ID ")
		.append(" AND HHEGR.GROUP_ID = HHEGF.GROUP_ID ")
		.append(" AND HHEGF.FUNCTION_ID = 50 ")
		.append(" AND HHEGF.ACCESS_RIGHT = 'Y' ")
		.append(" AND HHER.COMMENCE_DATE BETWEEN to_date('" + startDate + "','DD/MM/YYYY') AND to_date('"+ endDate + "','DD/MM/YYYY') ");
		
		
		if (!Util.isEmptyString(batchName)){
			criteria.append(" AND HHER.BATCH_ID LIKE '%").append(Util.handleSCInSQL(batchName)).append("%' ");
		}
			
		if (!Util.isEmptyString(rank)){
			criteria.append(" AND HHER.SUBSTANTIVE_RANK LIKE '%").append(Util.handleSCInSQL(rank)).append("%' ");
		}
			
		if (!Util.isEmptyString(postUnit)){
			criteria.append(" AND HHER.PRESENT_POST LIKE '%").append(Util.handleSCInSQL(postUnit)).append("%' ");
		}
		
		if (!Util.isEmptyString(employeeNumber)){
			criteria.append(" AND HHER.EMPLOYEE_NUMBER = "+employeeNumber );
		}

		if (!Util.isEmptyString(employeeName)){
			criteria.append(" AND HHER.NAME LIKE '%").append(Util.handleSCInSQL(employeeName)).append("%' ");
		}
			
		
		List<EaReport> result = new ArrayList<EaReport>();
		
		result=jdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReport>(EaReport.class));
		
		return result;
	}
	
	public void revertReportStatusForRSM(String[] strsArray)throws Exception{
		for(int i=0;i<strsArray.length;i++){
			StringBuffer sql = new StringBuffer();	
			sql.append("update HA_HR_EA_REPORT set status ='GM' ").append(" where report_id  = ").append(Long.parseLong(strsArray[i]));
	       	jdbcTemplate.update(sql.toString());
			
		}
	}
	
	//added on 20170315 SU retrieve CL report for RSM
	public List<EaReport> searchReportRSMSU(SearchFinalReportDto searchFinalReportDto, List<UserEnquiryModelDTO> gmlist) {
		
		StringBuffer criteria = new StringBuffer();
		criteria.append("SELECT HHER.* ")
		.append(" FROM HA_HR_EA_REPORT HHER,HA_HR_EA_USER HHEU, HA_HR_EA_GROUP_RANK HHEGR,HA_HR_EA_GROUP_FUNCTION HHEGF")		
		.append(" WHERE 1=1 ");
		criteria.append(" AND HHEU.USER_ID in ( ");
		for(int i=0; i<gmlist.size();i++){
			if(i!=0){
				criteria.append(",");
			}
			criteria.append("'"+gmlist.get(i).getUserId()+"'");
		}
		criteria.append(") ")
		.append(" AND HHEU.STATUS = 'Y' ")
		.append(" AND HHER.STATUS = 'CL' ")
		.append(" AND HHER.SUBSTANTIVE_RANK = HHEGR.RANK ")
		.append(" AND HHEU.GROUP_ID = HHEGR.GROUP_ID ")
		.append(" AND HHEGR.GROUP_ID = HHEGF.GROUP_ID ")
		.append(" AND HHEGF.FUNCTION_ID = 50 ")
		.append(" AND HHEGF.ACCESS_RIGHT = 'Y' ")
		.append(" AND HHER.COMMENCE_DATE BETWEEN to_date('" + searchFinalReportDto.getCommenceStartDate() + "','DD/MM/YYYY') AND to_date('"+ searchFinalReportDto.getCommenceEndDate() + "','DD/MM/YYYY') ");
		
		
		if (!Util.isEmptyString(searchFinalReportDto.getBatchName())){
			criteria.append(" AND HHER.BATCH_ID LIKE '%").append(Util.handleSCInSQL(searchFinalReportDto.getBatchName())).append("%' ");
		}
			
		if (!Util.isEmptyString(searchFinalReportDto.getRank())){
			criteria.append(" AND HHER.SUBSTANTIVE_RANK LIKE '%").append(Util.handleSCInSQL(searchFinalReportDto.getRank())).append("%' ");
		}
			
		if (!Util.isEmptyString(searchFinalReportDto.getPostUnit())){
			criteria.append(" AND HHER.PRESENT_POST LIKE '%").append(Util.handleSCInSQL(searchFinalReportDto.getPostUnit())).append("%' ");
		}
		
		if (!Util.isEmptyString(searchFinalReportDto.getEmployeeNumber())){
			criteria.append(" AND HHER.EMPLOYEE_NUMBER = "+searchFinalReportDto.getEmployeeNumber() );
		}

		if (!Util.isEmptyString(searchFinalReportDto.getEmployeeName())){
			criteria.append(" AND HHER.NAME LIKE '%").append(Util.handleSCInSQL(searchFinalReportDto.getEmployeeName())).append("%' ");
		}
			
		
		List<EaReport> result = new ArrayList<EaReport>();
		
		result=jdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReport>(EaReport.class));
		log.info("searchReportRSMSU: "+criteria);
		return result;
	}
	//end added on 20170315
}
