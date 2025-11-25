package com.hkha.ea.dao.assess.impl;

import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraiseeBatchEnquiryDAO;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.domain.EaBatch;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;

@Repository("appraiseeBatchEnquiryDAO")
public class AppraiseeBatchEnquiryDAOImpl implements AppraiseeBatchEnquiryDAO{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AppraiseeBatchEnquiryDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public List<BatchEnquiryDto> searchBatch(String batchId, String rank, String postUnit, String postTitle, String curUser) {
		StringBuffer sql = new StringBuffer();

		//test
		//curUser = "ACO-G3";
		//
		sql
			.append("select distinct batch_id as id, ")
			.append("LISTAGG(report_id, '-')WITHIN GROUP(order by report_id ) as strReportId, ")
			//.append("report_id as reportId, ")
			.append("nvl((select count(*) ")
			.append("from ha_hr_ea_report r1 ")
			.append("where status is not null and status <> 'I' and r1.batch_id = r.batch_id ")
			.append("group by batch_id  ), 0) as noOfAppDispatched,")
			.append("nvl(( select count(*) ")
			.append("from ha_hr_ea_report r2 ")
			.append("where r2.batch_id = r.batch_id group by batch_id), 0) as totalAppraisees ")
			.append("from ha_hr_ea_report r, ha_hr_ea_user u, ha_hr_ea_group_rank gr ")
			.append("where r.status not in ('" + Constants.STATUS_CL + "') " )
			.append(" and u.USER_ID = '" + curUser + "'")
			.append(" and u.GROUP_ID = gr.GROUP_ID")
			.append(" and gr.RANK = r.SUBSTANTIVE_RANK ");
		
		if (!Util.isEmptyString(batchId))
			sql.append("and Upper(batch_id) like '").append(batchId.toUpperCase()).append("%' ");
		
		if (!Util.isEmptyString(rank))
			sql.append("and Upper(substantive_rank) like '").append(rank.toUpperCase()).append("%' ");
		
		if (!Util.isEmptyString(postUnit))
			sql.append("and Upper(present_post) like '").append(postUnit.toUpperCase()).append("%' ");

		if (!Util.isEmptyString(postTitle)) {
		    sql.append(" and r.employee_number in ( ");
			sql.append("select distinct hcpv.employee_number from ha_common_people_v hcpv where hcpv.eng_post_title like ");
		    sql.append("'").append(postTitle.toUpperCase()).append("%'");
		    sql.append(" ) ");
		}
		
		sql.append("group by batch_id ");
		sql.append("order by UPPER(batch_id)");
		
		List<BatchEnquiryDto> result = namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<BatchEnquiryDto>(BatchEnquiryDto.class));
		return result;
	}

	public List<BatchEnquiryDto> searchAppraiseeBatch(String curUser, String batchName, String appraiseeName, 
			String appraiseeNumber, String rank, String postUnit, String postTitle){
		//test
		//curUser = "1668624";
		//
		StringBuffer sql = new StringBuffer("");

		sql
			.append("select distinct batch_id as batchName, r.ASSIGNED as assigned, r.STATUS as status, ")
			.append("nvl((select count(*) ")
			.append("from ha_hr_ea_report r1 ") 
			.append("where r1.assigned = 'Y' and r1.batch_id = r.batch_id ")
     		.append(" and r1.status not in ('")
			.append(Constants.STATUS_INITIAL).append("','")
			.append(Constants.STATUS_CL).append("', '")
     		.append(Constants.STATUS_HANDLED_BY_EXCEL).append("') ")				
			.append("group by batch_id  ), 0) as numAssigned, ")
			.append("nvl(( select count(*)  ")
			.append("from ha_hr_ea_report r2  ")
			.append("where r2.assigned = 'N' and r2.batch_id = r.batch_id ")
     		.append(" and r2.status not in ('")
			.append(Constants.STATUS_INITIAL).append("','")
			.append(Constants.STATUS_CL).append("', '")
     		.append(Constants.STATUS_HANDLED_BY_EXCEL).append("') ")				
			.append("group by batch_id), 0) as numUnassigned ")
			.append("from ha_hr_ea_report r, ha_hr_ea_report_role rr ")
			.append("where rr.officer_id = '" )
			.append( curUser )
			.append("' AND rr.report_id = r.report_id ")
			.append(" and rr.role in ( '" )
     		.append(Constants.STATUS_CD).append("') " )
     		.append(" and r.status not in ('")
			.append(Constants.STATUS_INITIAL).append("','")
			.append(Constants.STATUS_CL).append("', '")
     		.append(Constants.STATUS_HANDLED_BY_EXCEL).append("') ");

		if (!Util.isEmptyString(batchName))
			sql.append(" and upper(r.batch_id) like '").append(batchName.toUpperCase()).append("%' ");
		
		if (!Util.isEmptyString(appraiseeNumber))
			sql.append(" and r.employee_number = '").append(appraiseeNumber).append("' ");
		
		if (!Util.isEmptyString(appraiseeName))
			sql.append(" and upper(r.name) like '").append(appraiseeName.toUpperCase()).append("%' ");
		
		if (!Util.isEmptyString(rank))
			sql.append(" and upper(r.substantive_rank) like '").append(rank.toUpperCase()).append("%' ");
		
		if (!Util.isEmptyString(postUnit))
			sql.append(" and upper(r.present_post) like '").append(postUnit.toUpperCase()).append("%' ");

		if (!Util.isEmptyString(postTitle)) {
		    sql.append(" and r.employee_number in ( ");
			sql.append("select distinct hcpv.employee_number from ha_common_people_v hcpv where hcpv.eng_post_title like ");
		    sql.append("'").append(postTitle.toUpperCase()).append("%'");
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
}
