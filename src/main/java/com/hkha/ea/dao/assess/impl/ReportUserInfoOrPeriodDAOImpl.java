package com.hkha.ea.dao.assess.impl;

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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.assess.ReportUserInfoOrPeriodDAO;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.dto.assess.BatchEnquiryDto;

@Repository("reportUserInfoOrPeriodDAO")
public class ReportUserInfoOrPeriodDAOImpl implements ReportUserInfoOrPeriodDAO{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(ReportUserInfoOrPeriodDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;

	public List<BatchEnquiryDto> searchRpUserInfoOrPeriod(String curUser, String batchName, String appraiseeName, 
			String appraiseeNumber, String rank, String postUnit, String postTitle, long groupid){
		//test
		//curUser = "1668624";
		//
		StringBuffer sql = new StringBuffer("");

		sql
			//edited on 20170220 duplicated entries are retireved
			//.append("select distinct HHER.batch_id as batchName, HHER.ASSIGNED as assigned, HHER.STATUS as status, ")
			.append("select distinct HHER.batch_id as batchName, HHER.ASSIGNED as assigned, ")
			.append("nvl((select count(*) ")
			.append("from ha_hr_ea_report r1 ") 
			.append("where r1.assigned = 'Y' and r1.batch_id = HHER.batch_id ")
     		.append(" and r1.status not in ('")
			.append(Constants.STATUS_INITIAL).append("','")
			.append(Constants.STATUS_CL).append("', '")
     		.append(Constants.STATUS_HANDLED_BY_EXCEL).append("') ")				
			.append("group by batch_id  ), 0) as numAssigned, ")
			.append("nvl(( select count(*)  ")
			.append("from ha_hr_ea_report r2  ")
			.append("where r2.assigned = 'N' and r2.batch_id = HHER.batch_id ")
     		.append(" and r2.status not in ('")
			.append(Constants.STATUS_INITIAL).append("','")
			.append(Constants.STATUS_CL).append("', '")
     		.append(Constants.STATUS_HANDLED_BY_EXCEL).append("') ")				
			.append("group by batch_id), 0) as numUnassigned ")
			.append("from ")
			.append("HA_HR_EA_REPORT HHER,HA_HR_EA_USER HHEU,HA_HR_EA_GROUP_RANK HHEGR,HA_HR_EA_GROUP_FUNCTION HHEGF ")
			.append("where 1=1 " )
			.append("AND HHEU.USER_ID = '")
			.append( curUser )
			.append("' ")
			.append("AND HHEU.STATUS = 'Y' ")
			.append("AND HHER.STATUS <> 'CL' " )
     		.append("AND HHER.SUBSTANTIVE_RANK = HHEGR.RANK " )
     		.append("AND HHEU.GROUP_ID = HHEGR.GROUP_ID ")
			.append("AND HHEGR.GROUP_ID = HHEGF.GROUP_ID ");
		
		
		if(groupid!=4 && groupid!=5){
			sql.append("AND HHEGF.FUNCTION_ID = 50 ");
			sql.append("AND HHEGF.ACCESS_RIGHT = 'Y' ");
		}

		if (!Util.isEmptyString(batchName))
			sql.append(" AND upper(HHER.BATCH_ID) like '").append(batchName.toUpperCase()).append("%' ");
		
		if (!Util.isEmptyString(appraiseeNumber))
			sql.append(" AND HHER.EMPLOYEE_NUMBER = '").append(appraiseeNumber).append("' ");
		
		if (!Util.isEmptyString(appraiseeName))
			sql.append(" AND upper(HHER.NAME) like '").append(appraiseeName.toUpperCase()).append("%' ");
		
		if (!Util.isEmptyString(rank))
			sql.append(" AND upper(HHER.SUBSTANTIVE_RANK) like '").append(rank.toUpperCase()).append("%' ");
		
		if (!Util.isEmptyString(postUnit))
			sql.append(" AND upper(HHER.PRESENT_POST) like '").append(postUnit.toUpperCase()).append("%' ");

		if (!Util.isEmptyString(postTitle)) {
		    sql.append(" AND HHER.EMPLOYEE_NUMBER in ( ");
			sql.append("select distinct hcpv.employee_number from ha_common_people_v hcpv where hcpv.eng_post_title like ");
		    sql.append("'").append(postTitle).append("%'");
		    sql.append(" ) ");
		}

		sql.append("order by batch_id");
		List<BatchEnquiryDto> result = namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<BatchEnquiryDto>(BatchEnquiryDto.class));
		if(result != null && result.size() > 0){
			for(int i=0; i<result.size(); i++){
				BatchEnquiryDto dto = result.get(i);
				int total = dto.getNumAssigned()+dto.getNumUnassigned();
				result.get(i).setNumAppraisee(total);
			}
		}
		return result;
	}
	public int searchAppraisalPeriodOverlappedWithExistingReport(String commenceDate,String endDate,String employees){
		int count=0;
		StringBuffer sql = new StringBuffer("");

		sql.append("select count(*) from ha_hr_ea_report where 1=1 and employee_number in ("+employees+") and COMMENCE_DATE between :commenceDate and :endDate  ");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		//paramSource.addValue("employees", employees);
		paramSource.addValue("commenceDate", Util.string2Date(commenceDate,Constants.DISPLAY_DATE_FORMAT));
		paramSource.addValue("endDate", Util.string2Date(endDate,Constants.DISPLAY_DATE_FORMAT));
	
		
		
		count =(Integer)namedParameterJdbcTemplate.queryForObject(sql.toString(), paramSource,Integer.class);
		return count;
	}
	
	public void saveChangeAppraisalPeriodDate(List<String> rptIds,String commenceDate,String endDate)throws Exception{
		
		for(int i=0;i<rptIds.size();i++){
			EaReport er=appraiseeCommonSearchDAO.findEareportById(Long.parseLong(rptIds.get(i)));
			if(null!=er){
				EaReport ernew=new EaReport();
				BeanUtils.copyProperties(er, ernew);
				ernew.setCommenceDate(Util.string2Date(commenceDate,Constants.DISPLAY_DATE_FORMAT));
				ernew.setEndDate(Util.string2Date(endDate,Constants.DISPLAY_DATE_FORMAT));
				hibernateTemplate.saveOrUpdate(ernew);
			}
			
		}
	}
	
}
