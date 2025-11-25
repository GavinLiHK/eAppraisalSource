package com.hkha.ea.dao.assess.impl;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.domain.EaBatch;
import com.hkha.ea.domain.EaContract;
import com.hkha.ea.domain.EaCoreCompetency;
import com.hkha.ea.domain.EaMemoDetails;
import com.hkha.ea.domain.EaObjectives;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.domain.EaTrainPlan;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.Pem001RDto;
import com.hkha.ea.dto.assess.PeopleAssignmentDto;
import com.hkha.ea.dto.assess.UserTableColumn;

@Repository("appraiseeCommonSearchDAO")
public class AppraiseeCommonSearchDAOImpl implements AppraiseeCommonSearchDAO{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AppraiseeCommonSearchDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//20240821 Name Presentation
	private String fullNamePattern = " initcap(f.title) || ' ' || upper(f.last_name) || ' ' || initcap(f.first_name) || decode(f.PRE_NAME_ADJUNCT, null, '', (' ' || initcap(lower(f.PRE_NAME_ADJUNCT)))) || decode(f.suffix, null, '', (', ' || f.suffix)) engName ,";
	//End 20240821 Name Presentation

	
	public EaReport findEareportById(long id){
		EaReport er=null;
		StringBuilder sql = new StringBuilder();
		sql.append("select * FROM ha_hr_ea_report r "); 
		sql.append("where r.REPORT_ID = :id");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id",id);
		log.info("sql is "+sql.toString());  
		er = namedParameterJdbcTemplate.queryForObject(sql.toString(), paramSource, new BeanPropertyRowMapper<EaReport>(EaReport.class));
		return er;
	}
	
	
	public EaReportRole searchReportRoleByReportIdandRole(long id,String role){
		//EaReportRole reptRole=(EaReportRole)hibernateTemplate.find("from EaReportRole r where r.id.reportId = "+id+" and r.id.role = "+role);
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * from ha_hr_ea_report_role")
       			.append(" where report_id = ").append(id).append(" ")
       			.append(" and role = '").append(role).append("' ");
		List<EaReportRole> r = jdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReportRole>(EaReportRole.class));
		if(r != null && r.size() > 0)
			return r.get(0);
		return null;
	}
	
	public EaContract findEaContractById(long id){
		List<EaContract> eclist=new ArrayList<EaContract>();
		EaContract ec=new EaContract();
		/*
	 	Following part was created to remove HibernateTemplate.find on 12/11/2021
	 	
		Due to the Java version was upgraded from OpenJDK 8 to OpenKDK 11, 
			the Spring framework was required to upgrade from version 4 to version 5.
	 	
	 	The hibernateTemplate.find is deprecated in Hibernate 5.
	 	
	 	Modified on 12/11/2021
		*/
		 //eclist=(List<EaContract>)hibernateTemplate.find("from EaContract c where c.id.reportId = "+id);
		
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("reportId", id);
		
		StringBuffer criteria = new StringBuffer();
		criteria.append("SELECT * FROM HA_HR_EA_CONTRACT ")
				.append("where REPORT_ID =  " ).append(id);
		
		eclist = namedParameterJdbcTemplate.query(criteria.toString(), paramSource, new BeanPropertyRowMapper<EaContract>(EaContract.class));
		
		
		 
		 if(eclist.size()>0){
			 ec=eclist.get(0);
		 }
		
		return ec;
	}
	
	public EaBatch findEaBatchById(String batchID) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM HA_HR_EA_BATCH")
       			.append(" where BATCH_ID = :batchID ");
       	
       	MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("batchID",batchID);
		
		List<EaBatch> batchs = namedParameterJdbcTemplate.query(criteria.toString(), paramSource, new BeanPropertyRowMapper<EaBatch>(EaBatch.class));
		if(batchs != null && batchs.size() >0 )
			return batchs.get(0);
		return null;
	}
	
	public List<EaObjectives> findEaObjectivesByReportIdAndType(long id,String type){
		List<EaObjectives> eolist=new ArrayList<EaObjectives>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("reportId",id);		
		paramSource.addValue("type",type);
		
		/*
	 	Following part was created to remove HibernateTemplate.find on 12/11/2021
	 	
		Due to the Java version was upgraded from OpenJDK 8 to OpenKDK 11, 
			the Spring framework was required to upgrade from version 4 to version 5.
	 	
	 	The hibernateTemplate.find is deprecated in Hibernate 5.
	 	
	 	Modified on 12/11/2021
		*/
		
		// eolist =(List<EaObjectives>)hibernateTemplate.find("from EaObjectives o where o.id.reportId = "+id+" and o.id.type="+type);
		//eolist =(List<EaObjectives>)hibernateTemplate.find("from EaObjectives o where o.reportId = ?0 and o.type = ?1 order by o.seqNo", new Object[]{id, type});
		
		
		
		StringBuffer criteria = new StringBuffer();
		criteria.append("select * FROM HA_HR_EA_OBJECTIVES")
				.append(" where REPORT_ID = :reportId ")
				.append(" and TYPE = :type ")
//				20240214 Add ordering on the sequence to the query
				.append(" order by seq_no ");
//				20240214 End Add ordering on the sequence to the query
		
		eolist = namedParameterJdbcTemplate.query(criteria.toString(), paramSource, new BeanPropertyRowMapper<EaObjectives>(EaObjectives.class));
		
		
		return eolist;
	}
	
	public List<EaCoreCompetency> findEaCoreCompetencyByReportId(long id ){
		List<EaCoreCompetency> ecclist=new ArrayList<EaCoreCompetency>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("reportId",id);	
		
		/*
	 	Following part was created to remove HibernateTemplate.find on 12/11/2021
	 	
		Due to the Java version was upgraded from OpenJDK 8 to OpenKDK 11, 
			the Spring framework was required to upgrade from version 4 to version 5.
	 	
	 	The hibernateTemplate.find is deprecated in Hibernate 5.
	 	
	 	Modified on 12/11/2021
		*/
		//ecclist =(List<EaCoreCompetency>)hibernateTemplate.find("from EaCoreCompetency ecc where ecc.reportId = ?0 ", new Object[]{id});
		
		
		StringBuffer criteria = new StringBuffer();
		criteria.append("select * FROM HA_HR_EA_CORE_COMPETENCY")
				.append(" where REPORT_ID = :reportId ");
		
		ecclist = namedParameterJdbcTemplate.query(criteria.toString(), paramSource, new BeanPropertyRowMapper<EaCoreCompetency>(EaCoreCompetency.class));
		
		
		return ecclist;
	}
	
	public List<EaReportRole> searchReportRoleDetailByReportId(long id){
		/*
	 	Following part was created to remove HibernateTemplate.find on 12/11/2021
	 	
		Due to the Java version was upgraded from OpenJDK 8 to OpenKDK 11, 
			the Spring framework was required to upgrade from version 4 to version 5.
	 	
	 	The hibernateTemplate.find is deprecated in Hibernate 5.
	 	
	 	Modified on 12/11/2021
		*/
		//List<EaReportRole> rolelist=(List<EaReportRole>)hibernateTemplate.find("from EaReportRole r where r.reportId = "+id);
		
		StringBuffer criteria = new StringBuffer();
		criteria.append("select * from HA_HR_EA_REPORT_ROLE")
				.append(" where report_id = ").append(id);
		
		List<EaReportRole> rolelist = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReportRole>(EaReportRole.class));
		
		return rolelist;
	}

	
	public List<EaReport> findEaReportByIds(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_report")
       			.append(" where REPORT_ID in ( ")
       			.append(" ");
		for ( int i=0; i < reportIds.size(); i++)
		{
			if ( i != 0 )
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		List<EaReport> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReport>(EaReport.class));
		return result;
	}
	
	public List<EaTrainPlan> findEaTrainPlanByIds(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_train_plan")
       			.append(" where REPORT_ID in ( ")
       			.append(" ");
		for ( int i=0; i < reportIds.size(); i++)
		{
			if ( i != 0 )
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		List<EaTrainPlan> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaTrainPlan>(EaTrainPlan.class));
		return result;
	}
	
	public List<EaObjectives> findEaObjectivesByIds(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_objectives")
       			.append(" where REPORT_ID in ( ")
       			.append(" ");
		for ( int i=0; i < reportIds.size(); i++)
		{
			if ( i != 0 )
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		List<EaObjectives> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaObjectives>(EaObjectives.class));
		return result;
	}
	
	public List<EaCoreCompetency> findEaCoreCompetencyByIds(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_core_competency")
       			.append(" where REPORT_ID in ( ")
       			.append(" ");
		for ( int i=0; i < reportIds.size(); i++)
		{
			if ( i != 0 )
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		List<EaCoreCompetency> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaCoreCompetency>(EaCoreCompetency.class));
		return result;
	}
	
	public List<EaMemoDetails> findEaMemoDetailsByIds(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_memo_details")
       			.append(" where REPORT_ID in ( ")
       			.append(" ");
		for ( int i=0; i < reportIds.size(); i++)
		{
			if ( i != 0 )
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		List<EaMemoDetails> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaMemoDetails>(EaMemoDetails.class));
		return result;
	}
	
	public List<EaContract> findEaContractByIds(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_contract")
       			.append(" where REPORT_ID in ( ")
       			.append(" ");
		for ( int i=0; i < reportIds.size(); i++)
		{
			if ( i != 0 )
				criteria.append(", ");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		List<EaContract> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaContract>(EaContract.class));
		return result;
	}
	

	public List<EaReportRole> searchReportRoleDetailByReportIds(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * from ha_hr_ea_report_role")
       			.append(" where report_id in ( ")
       			.append(" ");
		for ( int i=0; i < reportIds.size(); i++)
		{
			if ( i != 0 )
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		List<EaReportRole> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReportRole>(EaReportRole.class));
		return result;
	}
	
	public List<EaReportRole> searchAllRolesByReportID(Long reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * from ha_hr_ea_report_role")
       			.append(" where report_id  = ")
       			.append(reportIds);
		
		criteria.append(" order by ROLE_SEQUENCE ASC");
		List<EaReportRole> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReportRole>(EaReportRole.class));
		return result;
	}
	
	public EaReport searchByReportID(String reportID) {
		StringBuffer criteria = new StringBuffer();
		
       	criteria.append("select distinct r.*  ")
       	.append("FROM per_all_people_f f left join ha_hr_ea_report r on f.employee_number = r.employee_number ")
       	.append("left join per_people_extra_info info on f.PERSON_ID = info.PERSON_ID ")
       	.append(" where 1=1 ");
       	
       	if(!Util.isEmptyString(reportID)){
       		Long rpId = Long.valueOf(reportID);
       		criteria.append(" and r.REPORT_ID").append(" = ").append(rpId).append(" ");
       	}
       	
		//maurice
		criteria.append(" and info.INFORMATION_TYPE").append(" = ").append("'HA_CONTRACT_INFO'").append(" ");
		criteria.append(" and r.CURRENT_CONTRACT_END_DATE");
		criteria.append(" between to_date( info.PEI_INFORMATION2").append(", 'yyyy/mm/dd hh24:mi:ss') and to_date( info.PEI_INFORMATION3").append(", 'yyyy/mm/dd hh24:mi:ss') ");
		//maurice end
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		List<EaReport> list = namedParameterJdbcTemplate.query(criteria.toString(), paramSource, new BeanPropertyRowMapper<EaReport>(EaReport.class));
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<EaTrainPlan> findEaTrainPlanById(long id) {
		List<EaTrainPlan> etplist=new ArrayList<EaTrainPlan>();
		
		/*
	 	Following part was created to remove HibernateTemplate.find on 12/11/2021
	 	
		Due to the Java version was upgraded from OpenJDK 8 to OpenKDK 11, 
			the Spring framework was required to upgrade from version 4 to version 5.
	 	
	 	The hibernateTemplate.find is deprecated in Hibernate 5.
	 	
	 	Modified on 12/11/2021
		*/
		//etplist=(List<EaTrainPlan>)hibernateTemplate.find("from EaTrainPlan etp where etp.reportId = "+id);
	
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * from HA_HR_EA_TRAIN_PLAN")
       			.append(" where report_id = ").append(id);
       	
       	etplist = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaTrainPlan>(EaTrainPlan.class));
		
		return etplist;
	}
	
	public EaMemoDetails findEaMemoDetailsById(long id) {
		EaMemoDetails emd =new EaMemoDetails();
		
		/*
	 	Following part was created to remove HibernateTemplate.find on 12/11/2021
	 	
		Due to the Java version was upgraded from OpenJDK 8 to OpenKDK 11, 
			the Spring framework was required to upgrade from version 4 to version 5.
	 	
	 	The hibernateTemplate.find is deprecated in Hibernate 5.
	 	
	 	Modified on 12/11/2021
		*/		
		//List<EaMemoDetails> list=(List<EaMemoDetails>)hibernateTemplate.find("from EaMemoDetails emd where emd.reportId = "+id);
		
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * from HA_HR_EA_MEMO_DETAILS")
       			.append(" where report_id = ").append(id);
       	
       	List<EaMemoDetails> list = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaMemoDetails>(EaMemoDetails.class));
		
		
		
		if(list.size()>0){
			emd=list.get(0);
		}
		
		return emd;
	}

	public int findHaCommonRptPeopleVPersonIdByEmployeeNumber(String employeeNumber){
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select person_id from ha_common_rpt_people_v where employee_number = :employeeNumber");
       			
       	MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("employeeNumber",employeeNumber);
		
		List<Integer> personIds = (List<Integer>)namedParameterJdbcTemplate.queryForList(criteria.toString(), paramSource, java.lang.Integer.class);
		if(0==personIds.size()){
			return 0;
		}else{
			return personIds.get(0);
		}
		
	}
	
	public List<PeopleAssignmentDto> searchPeopleAssignment(List<String> employeeNumbers,Date appraisalPeriodStart){
		StringBuffer criteria = new StringBuffer();
		criteria.append(" select ass.person_id assPersonId, ass.eng_rank engRank, ass.primary_flag primaryFlag, ass.people_start_date peopleStartDateAss, ass.people_end_date peopleEndDateAss, ass.assignment_start_date assignmentStartDate, ass.assignment_end_date assignmentEndDate, ")
				.append(" p.PERSON_ID peoPersonId, p.EMPLOYEE_NUMBER employeeNum, ");
		//20240821 Name Presentation
		criteria.append(fullNamePattern);
		//End 20240821 Name Presentation
//		.append(" p.PERSON_ID peoPersonId, p.EMPLOYEE_NUMBER employeeNum, p.ENG_NAME engName, p.PEOPLE_START_DATE peopleStartDate, p.PEOPLE_END_DATE peopleEndDate, f.attribute17 attribute17");
		criteria.append(" p.PEOPLE_START_DATE peopleStartDate, p.PEOPLE_END_DATE peopleEndDate, f.attribute17 attribute17");
		criteria.append(" from ha_hr_ea_assignment_v ass left join ha_common_people_v p on ass.person_id = p.person_id");
		criteria.append(" left join per_all_people_f f on p.person_id = f.person_id and p.people_start_date = f.EFFECTIVE_START_DATE and p.people_end_date = f.EFFECTIVE_END_DATE");
		criteria.append(" where ");
		criteria.append(" p.employee_number in ( ");
		for (int i=0; i<employeeNumbers.size(); i++)
		{
			if(!Util.isEmptyString(employeeNumbers.get(i))){
				if ( i != 0)
					criteria.append(",");
				criteria.append(" '").append( employeeNumbers.get(i) ).append("'");
			}
			
		}
			
		criteria.append(") ");
		criteria.append(" and p.person_id = ass.person_id ");
		criteria.append(" and ass.primary_flag = 'Y' " );
		criteria.append(" and ass.employ_cat IN ( 'POST' ,'SEC') ");
		//
		// on 2007-May-04
		// Changed to compare people join date (PEOPLE_START_DATE) to specific officer role
		// thus, remove existing date range filter
		// then, apply Rule 1, 2 to following filter
		//
		criteria.append(" and (");
		//
		// Rule 1. Staff joining HA after Appraisal Period Start Date of the appraisee can be
		//         selected for roles including Appraising Officer 
		//         (AO), Countersigning Officer (CO), Endorsing Officer (EO), Interviewing 
		//         Officer (IO), Reviewing Officer (RO)
		//
		criteria.append("  to_date('" + Util.date2String(appraisalPeriodStart) + "','dd/mm/yyyy') <= to_date(f.attribute17,'yyyy/mm/dd HH24:MI:SS') ");
		//
		// Rule 2. Active staff as at Appraisal Period Start Date (i.e. no termination date or the
		//         termination date is after the Appraisal Period Start Date) can be selected for
		//         Appraisee (AP), AO, CO, EO, IO, RO.
		//
		criteria.append(" or (");
		criteria.append("  to_date('" + Util.date2String(appraisalPeriodStart) + "','dd/mm/yyyy') <= p.people_end_date ");
		criteria.append("   ) ");
		criteria.append(" ) ");
		
		criteria.append(" and p.people_end_date between ass.assignment_start_date and ass.assignment_end_date ");

		criteria.append(" order by ass.people_start_date desc, p.people_start_date desc ");
		
		List<PeopleAssignmentDto> list = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<PeopleAssignmentDto>(PeopleAssignmentDto.class));
		return list;
	}

	public EaReportRole searchNextOfficerRoleByIdAndCurrentRptStatus(Long rptId ,String currentRptStatus){
		StringBuffer criteria = new StringBuffer();
		criteria.append("select * from ha_hr_ea_report_role where report_id = :rptId ");
		if("I".equalsIgnoreCase(currentRptStatus)){
			criteria.append(" and ha_hr_ea_report_role.ROLE_SEQUENCE  = 0 ");
		}else if("AP".equalsIgnoreCase(currentRptStatus)){
			criteria.append(" and ha_hr_ea_report_role.ROLE  = 'AO' ");
		}else{
			criteria.append("and ha_hr_ea_report_role.ROLE_SEQUENCE  > (select ha_hr_ea_report_role.ROLE_SEQUENCE  from HA_HR_EA_REPORT_ROLE  where ha_hr_ea_report_role.REPORT_ID  =  :rptId  and  ha_hr_ea_report_role.ROLE  = :currentRptStatus) ");
		}
		criteria.append(" order by ha_hr_ea_report_role.ROLE_SEQUENCE asc");
		
       	MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("rptId",rptId);
		paramSource.addValue("currentRptStatus",currentRptStatus);
		List<EaReportRole> erlist = (List<EaReportRole>)namedParameterJdbcTemplate.query(criteria.toString(), paramSource,  new BeanPropertyRowMapper<EaReportRole>(EaReportRole.class));
		if(erlist.size()>0){
			return erlist.get(0);
		}else{
			return null;
		}
		
	}
	
	public EaReportRole searchNextOfficerByStatus(Long rptId,String currentRptStatus, String rountingTo){
		StringBuffer criteria = new StringBuffer();
		criteria.append("select * from ha_hr_ea_report_role where report_id = :rptId ");
		if("I".equalsIgnoreCase(currentRptStatus) || "AO".equalsIgnoreCase(rountingTo)){
			criteria.append(" and ha_hr_ea_report_role.ROLE_SEQUENCE  = 1 ");
		}else if("CD".equalsIgnoreCase(rountingTo)){
			criteria.append(" and ha_hr_ea_report_role.ROLE_SEQUENCE  = 0 ");
		}else if("CO".equalsIgnoreCase(rountingTo)){
			criteria.append(" and ha_hr_ea_report_role.ROLE_SEQUENCE  = 2 ");
		}else if("IO".equalsIgnoreCase(rountingTo)){
			criteria.append(" and ha_hr_ea_report_role.ROLE_SEQUENCE  = 3 ");
		}else if("RO".equalsIgnoreCase(rountingTo)){
			criteria.append(" and ha_hr_ea_report_role.ROLE_SEQUENCE  = 4 ");
		}
	
		criteria.append(" order by ha_hr_ea_report_role.ROLE_SEQUENCE asc");
		
       	MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("rptId",rptId);
		List<EaReportRole> erlist = (List<EaReportRole>)namedParameterJdbcTemplate.query(criteria.toString(), paramSource,  new BeanPropertyRowMapper<EaReportRole>(EaReportRole.class));
		if(erlist.size()>0){
			return erlist.get(0);
		}else{
			return null;
		}
	}


	public Pem001RDto searchPemByEmployeeNum(String curUser, String employeeNumber) {
		StringBuffer criteria = new StringBuffer();
		criteria.append("select * from HA_HR_PEM001R ")
				.append("where ")
				.append("COMPUTER_NUM").append(" = '")
				.append(curUser).append("'");
		if(!Util.isEmptyString(employeeNumber)){
			criteria.append(" and ").append("EMPLOYEE_NUM").append(" = ");
			criteria.append(Long.valueOf(employeeNumber));
		}

		List<Pem001RDto> list = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<Pem001RDto>(Pem001RDto.class));
		if(list != null && list.size()>0)
			return list.get(0);
		return null;
	}


	public List<EaReportRole> batchSearch(List<Long> appraisees, String roleAbbrCd) {
		if(appraisees.size()<=1)
			return null;
		StringBuffer criteria = new StringBuffer();
		criteria.append("select * from ha_hr_ea_report_role ")
				.append("where ")
				.append("report_id in (");
		
		for ( int i=1; i < appraisees.size(); i++)
		{
			if ( i != 1 )
				criteria.append(",");
			criteria.append(appraisees.get(i));
		}
		criteria.append(") ");
		criteria.append("and role = '");
		criteria.append(roleAbbrCd).append("'");
		return  namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReportRole>(EaReportRole.class));
	}


	public List<EaReport> batchSearchReport(List<Long> reportIds) {
		if(reportIds.size()<=1)
			return null;
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_report")
       			.append(" where REPORT_ID in ( ")
       			.append(" ");
		for ( int i=1; i < reportIds.size(); i++)
		{
			if ( i != 1 )
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		List<EaReport> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReport>(EaReport.class));
		return result;
	}
	
	public EaReportRole searchPreviousOfficerRoleByIdAndCurrentRptStatus(Long rptId ,String currentRptStatus){
		StringBuffer criteria = new StringBuffer();
		criteria.append("select * from ha_hr_ea_report_role where report_id = :rptId ");
		if("I".equalsIgnoreCase(currentRptStatus)){
			criteria.append(" and ha_hr_ea_report_role.ROLE_SEQUENCE  = 0 ");
		}else if("AP".equalsIgnoreCase(currentRptStatus)){
			criteria.append(" and ha_hr_ea_report_role.ROLE  = 'AO' ");
		}else{
			criteria.append("and ha_hr_ea_report_role.ROLE_SEQUENCE  < (select ha_hr_ea_report_role.ROLE_SEQUENCE  from HA_HR_EA_REPORT_ROLE  where ha_hr_ea_report_role.REPORT_ID  =  :rptId  and  ha_hr_ea_report_role.ROLE  = :currentRptStatus) ");
		}
		criteria.append(" order by ha_hr_ea_report_role.ROLE_SEQUENCE desc");
		
       	MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("rptId",rptId);
		paramSource.addValue("currentRptStatus",currentRptStatus);
		List<EaReportRole> erlist = (List<EaReportRole>)namedParameterJdbcTemplate.query(criteria.toString(), paramSource,  new BeanPropertyRowMapper<EaReportRole>(EaReportRole.class));
		if(erlist.size()>0){
			return erlist.get(0);
		}else{
			return null;
		}
		
		
	}
	
	public List<EaObjectives> batchResetObjectivesInfo(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_objectives")
       			.append(" where 1=1 ")
       			.append(" and REPORT_ID in ( ")
       			.append(" ");
		for ( int i=0; i < reportIds.size(); i++)
		{
			if ( i != 0 )
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		criteria.append(" and ");
		criteria.append(" TYPE " );
		criteria.append(" in ('");
		criteria.append( Constants.OBJECTIVE_TYPE_PARTB ).append("')");
		List<EaObjectives> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaObjectives>(EaObjectives.class));
		if(result != null && result.size() > 0){
			for(int i = result.size()-1; i >= 0; i--){
				hibernateTemplate.delete(result.get(i));
			}
		}
		return result;
	}
	
	public List<EaCoreCompetency> batchResetCoreCometencyInfo(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_core_competency")
       			.append(" where 1=1 ")
       			.append(" and REPORT_ID in ( ")
       			.append(" ");
		for ( int i=0; i < reportIds.size(); i++)
		{
			if ( i != 0 )
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		List<EaCoreCompetency> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaCoreCompetency>(EaCoreCompetency.class));
		
		List<EaCoreCompetency> list = new ArrayList<EaCoreCompetency>();
		if(result != null && result.size() > 0){
			for(int i=0; i<result.size(); i++){
				if(result.get(i) != null){
					EaCoreCompetency c = result.get(i);
					c.setRating("");
					list.add(c);
				}
			}
		}
		return list;
	}
	
	public List<EaTrainPlan> batchResetTrainPlanInfo(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_train_plan")
       			.append(" where 1=1  ")
       			.append(" and REPORT_ID in ( ")
       			.append(" ");
		for ( int i=0; i < reportIds.size(); i++)
		{
			if ( i != 0 )
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
		criteria.append(")");
		List<EaTrainPlan> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaTrainPlan>(EaTrainPlan.class));
		if(result != null && result.size() > 0){
			for(int i = 0;i< result.size(); i++){
				hibernateTemplate.delete(result.get(i));
			}
		}
		return result;
	}


	public List<BatchEnquiryDto> searchAppraiseeBatchList(String batchName) {
		List<BatchEnquiryDto> result = new ArrayList<BatchEnquiryDto>();
		StringBuffer criteria = new StringBuffer();
		
		criteria.append(" select distinct r.REPORT_ID reportId, r.EMPLOYEE_NUMBER employeeNum, r.BATCH_ID batchName, r.NAME name, r.SUBSTANTIVE_RANK subRank, r.PRESENT_POST postUnit, r.STATUS status, r.ASSIGNED assigned,  ");
		criteria.append(" to_char(COMMENCE_DATE,'dd/MM/yyyy') as commenceDate, to_char(END_DATE,'dd/MM/yyyy')asendDate ");
		criteria.append(" from ha_hr_ea_report r, ha_hr_ea_report_role rl");
		criteria.append(" where");
     	criteria.append(" r.batch_id = '").append(batchName).append("'");
     	criteria.append(" and rl.role in ('");
     	criteria.append( Constants.ROLE_ABBR_CD ).append("') ");
     	criteria.append(" and rl.REPORT_ID=r.REPORT_ID")
		 		.append(" and r.status not in ('")
				.append(Constants.STATUS_INITIAL).append("','")
				.append(Constants.STATUS_CL).append("', '")
		 		.append(Constants.STATUS_HANDLED_BY_EXCEL).append("') ");  
     	
     	List<BatchEnquiryDto> list = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<BatchEnquiryDto>(BatchEnquiryDto.class));
     	if(list != null && list.size() >0){
     		for(int i=0; i<list.size(); i++){
     			BatchEnquiryDto dto = list.get(i);
     			if(Constants.STATUS_HANDLED_BY_EXCEL.equalsIgnoreCase(dto.getStatus())){
     				list.get(i).setAssigned(Constants.MESSAGE_HANDLED_BY_EXCEL);
     			}
     		}
     	}
		return list;
	}
	
	/*
	 * 
	 * **/
	
	public EaWorkflow searchReportTemplateByReportId(Long rptId){	
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ha_hr_ea_workflow where workflow_template_id=(select workflow_template_id from ha_hr_ea_report where report_id= :rptId)");
		
		 log.info("sql is "+sql.toString());
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("rptId",rptId);
		 EaWorkflow efl= (EaWorkflow)namedParameterJdbcTemplate.queryForObject(sql.toString(), paramSource,  new  BeanPropertyRowMapper(EaWorkflow.class));
		 
		 return efl;
		
	}


	public List<EaReport> searchSpecialReports(String rank, String postUnit) {
		StringBuilder criteria = new StringBuilder();
		criteria.append("select distinct r.*  ")
       			.append("FROM per_all_people_f f left join ha_hr_ea_report r on f.employee_number = r.employee_number ")
       			.append("left join per_people_extra_info info on f.PERSON_ID = info.PERSON_ID ")
       			.append(" where 1=1 ")
       			.append(" and r.STATUS in ('" + Constants.STATUS_GM + "','" + Constants.STATUS_INITIAL + "')");
       	
		if (!Util.isEmptyString(rank))
     		criteria.append("and Upper(r.SUBSTANTIVE_RANK) like '" + rank.toUpperCase() + "%'" );
     	if (!Util.isEmptyString(postUnit))
     		criteria.append("and Upper(r.PRESENT_POST) like '" + postUnit.toUpperCase() + "%'" );
     	
     	criteria.append(" and info.INFORMATION_TYPE = ").append("'HA_CONTRACT_INFO'");
     	criteria.append(" and r.CURRENT_CONTRACT_END_DATE ");
       	criteria.append(" between to_date( info.PEI_INFORMATION2 , 'yyyy/mm/dd hh24:mi:ss') and to_date( info.PEI_INFORMATION3 , 'yyyy/mm/dd hh24:mi:ss') ");     	
       	criteria.append(" and r.CURRENT_CONTRACT_END_DATE ");
       	criteria.append(" between f.EFFECTIVE_START_DATE and f.EFFECTIVE_END_DATE ");
		
       	List<EaReport> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReport>(EaReport.class));
		return result;
	}


	public EaReport searchByEmployeeNumAndCommDate(String empNum, Date commenceDate) {
		StringBuffer criteria = new StringBuffer();
       	criteria.append("select * FROM ha_hr_ea_report ")
       			.append("where EMPLOYEE_NUMBER = '").append(empNum).append("' ")
       			.append("and COMMENCE_DATE = ")
       			.append("to_date('" + Util.date2String(commenceDate) + "','dd/mm/yyyy')");
		List<EaReport> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReport>(EaReport.class));
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	//added on 20170320 size limit for unicode characters
	public List<UserTableColumn> searchColumnSize(String mode){	
		
		StringBuilder criteria = new StringBuilder();
		criteria.append("select table_name, column_name, data_type, data_length ")
				.append("from all_tab_cols ");
		if(mode.equalsIgnoreCase("C")){
			criteria.append("where table_name in ('HA_HR_EA_CONTRACT', 'HA_HR_EA_OBJECTIVES','HA_HR_EA_TRAIN_PLAN') ");
		}else if(mode.equalsIgnoreCase("M")){
			criteria.append("where table_name in ('HA_HR_EA_MEMO_DETAILS') ");
		}
				
		
		//List<UserTableColumn> userTableColumn = namedParameterJdbcTemplate.query(criteria.toString(), paramSource, new BeanPropertyRowMapper<UserTableColumn>(UserTableColumn.class));
		List<UserTableColumn> userTableColumn = namedParameterJdbcTemplate.query(criteria.toString(),new BeanPropertyRowMapper<UserTableColumn>(UserTableColumn.class));
		
		return userTableColumn;
	}
	//end added on 20170320
}
