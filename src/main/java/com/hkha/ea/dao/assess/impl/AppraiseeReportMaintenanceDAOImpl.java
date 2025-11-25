package com.hkha.ea.dao.assess.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

import org.apache.commons.lang3.StringUtils;
//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.assess.AppraiseeReportMaintenanceDAO;
import com.hkha.ea.domain.EaBatch;
import com.hkha.ea.domain.EaContract;
import com.hkha.ea.domain.EaCoreCompetency;
import com.hkha.ea.domain.EaMemoDetails;
import com.hkha.ea.domain.EaObjectives;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.domain.EaTrainPlan;
import com.hkha.ea.dto.assess.ReportNextRoleDto;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;


@Repository("appraiseeReportMaintenanceDAO")
public class AppraiseeReportMaintenanceDAOImpl implements AppraiseeReportMaintenanceDAO{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AppraiseeReportMaintenanceDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void retrieveRecord(String curUser, String year, String trackDate, String rank, String postUnit,
			String employeeNumber) {
		
		if(Util.isEmptyString(employeeNumber) || "null".equals(employeeNumber)){
			employeeNumber = "";
		}
		log.info("curUser:"+curUser+" year:"+year+" trackDate:"+trackDate);
		log.info("rank: "+rank+" postUnit:"+postUnit+" employeeNum:"+employeeNumber);
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			CallableStatement cstmt = null;
			cstmt = conn.prepareCall( "{call HA_HR_PEM001R_PKG.PEM001R(?,?,?,?,?,?,?)}");
			
			java.sql.Date dateCheckDate = Util.string2SqlDate(trackDate);
			cstmt.registerOutParameter(1, Types.VARCHAR);    
			cstmt.registerOutParameter(2, Types.NUMERIC);
			
			if (Util.isEmptyString(rank)){
				cstmt.setString(3, rank);
			} else {
				cstmt.setString(3, rank.toUpperCase());
			}
			
			cstmt.setInt(4, Integer.parseInt(year));
			cstmt.setString(5, employeeNumber);
			//cstmt.setString(6, trackDate);
			cstmt.setDate(6, dateCheckDate);
			cstmt.setString(7, curUser);
			cstmt.execute();
			//String err = cstmt.getString(1);
			//int result =  cstmt.getInt(2);
			log.info("RESULT: "+cstmt.getString(1)+cstmt.getInt(2));
			
			cstmt.close();
			DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
		} catch(Exception e) {
			e.printStackTrace();
			log.severe(e.toString());
		}
	}
	
	public List<SearchAppraiseeDto> search(String curUser, String employeeNumber, String rank, String postUnit, String appraisalPeriodStart,
			String appraisalPeriodEnd, String reportGenerated) throws SQLException {
		//test
		//curUser = "ACO-G3";
		//
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct ha_hr_pem001r.EMPLOYEE_NUM employeeNumber, ha_hr_pem001r.ENG_NAME engName, ha_hr_pem001r.RANK rank, ha_hr_pem001r.DATE_TO_RANK dateToRank, ha_hr_pem001r.POST_UNIT postUnit, ");
		sql.append("ha_hr_ea_report.REPORT_ID reportId, ha_hr_ea_report.BATCH_ID batchId, ha_hr_ea_report.STATUS status ");
		//sql.append("from HA_HR_PEM001R ha_hr_pem001r,HA_HR_EA_REPORT ha_hr_ea_report, HA_HR_EA_USER ha_hr_ea_user, HA_HR_EA_GROUP_RANK ha_hr_ea_group_rank ");
		sql.append("from HA_HR_PEM001R ha_hr_pem001r,HA_HR_EA_REPORT ha_hr_ea_report ");
		sql.append("where ha_hr_pem001r.COMPUTER_NUM").append(" = :curUser ");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		if ( StringUtils.isNotBlank(postUnit)){
			sql.append(" and ").append("ha_hr_pem001r.POST_UNIT")
				.append(" = :postUnit");
			paramSource.addValue("postUnit", postUnit);
		}
		
		sql.append(" and ").append("ha_hr_pem001r.EMPLOYEE_NUM")
			.append(" = ").append("ha_hr_ea_report.EMPLOYEE_NUMBER").append(" (+)");
	
		sql.append(" and ").append("ha_hr_pem001r.COMMENCE_DATE")
			.append(" = ").append("ha_hr_ea_report.COMMENCE_DATE").append(" (+)");
		
		sql.append(" and ").append("ha_hr_pem001r.END_DATE")
		.append(" = ").append("ha_hr_ea_report.END_DATE").append(" (+)");
		
		//20161205
		/*
		sql.append(" and ").append("ha_hr_pem001r.COMMENCE_DATE ")
			.append(" = ").append(":appraisalPeriodStart ");
		paramSource.addValue("appraisalPeriodStart", DateTimeUtil.string2Date(appraisalPeriodStart));
		
		sql.append(" and ").append("ha_hr_pem001r.END_DATE ")
			.append(" = ").append(" :appraisalPeriodEnd ");
		paramSource.addValue("appraisalPeriodEnd", DateTimeUtil.string2Date(appraisalPeriodEnd));
		*/
		
		/*
		sql.append(" and upper(").append("ha_hr_ea_user.USER_ID").append(") = :curUser ");
		sql.append(" and ").append("ha_hr_ea_user.GROUP_ID").append(" = ")
			.append( "ha_hr_ea_group_rank.GROUP_ID" );
		sql.append(" and ").append( "ha_hr_ea_group_rank.RANK").append("=")
			.append("ha_hr_pem001r.RANK" );   
		*/
		
		if(StringUtils.isNotBlank(rank)){
			sql.append(" and ").append( "ha_hr_pem001r.RANK")
				.append(" = UPPER(:rank)");
			paramSource.addValue("rank", rank);
		}
		
		
		if(!Util.isEmptyString(employeeNumber)&&!employeeNumber.equalsIgnoreCase("null")&&!employeeNumber.equalsIgnoreCase("")){
			if(StringUtils.isNotBlank(employeeNumber)){
				sql.append(" and ").append( "ha_hr_pem001r.EMPLOYEE_NUM")
					.append(" = :employeeNumber");
				paramSource.addValue("employeeNumber", Integer.valueOf(employeeNumber));
			}
		}
		

		if (!Util.isEmptyString(reportGenerated)) {
			sql.append(" and ").append( "ha_hr_ea_report.REPORT_ID" )
				.append(" is  ").append(reportGenerated.equals("Y") ? " not " : " ")
				.append("  null ");
		}
		
		sql.append(" order by ha_hr_pem001r.POST_UNIT").append(",");
		sql.append("ha_hr_pem001r.RANK");
		
		paramSource.addValue("curUser", curUser);
		log.info("AppraiseeEnquiryDto search sql param=============="+postUnit+"====curUser=="+curUser);
		log.info("AppraiseeEnquiryDto search sql=============="+sql.toString());
		List<SearchAppraiseeDto> result = namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<SearchAppraiseeDto>(SearchAppraiseeDto.class));
		return result;
	}

	public List<ReportNextRoleDto> getRole(String userId ,List<Long> reportIds) {

		StringBuffer criteria = new StringBuffer();
		criteria.append("select ha_hr_ea_report.report_Id reportId, ha_hr_ea_report_role.report_Id roleReportId, ha_hr_ea_report.status status, ha_hr_ea_report_role.role role, ha_hr_ea_report_role.role_sequence roleSeq ");
		criteria.append(" from ha_hr_ea_report left join ha_hr_ea_report_role on ha_hr_ea_report.report_id = ha_hr_ea_report_role.report_id ");
		criteria.append(" where");
		criteria.append(" ha_hr_ea_report_role.report_id in ( ");
     	
     	for ( int i=0; i<reportIds.size(); i++)
     	{
     		if ( i != 0)
     			criteria.append(",");
     		criteria.append(reportIds.get(i));
     	}
     	criteria.append(" ) ");
     	criteria.append(" and ha_hr_ea_report.status = 'I'");
     	criteria.append(" and ha_hr_ea_report_role.role_sequence = ");
     	criteria.append("(select min(role_sequence) from ha_hr_ea_report_role");
     	criteria.append(" where report_id = ha_hr_ea_report.report_id")
     			.append(" and ha_hr_ea_report_role.officer_id is not null group by report_id)");
     	
     	
     	List<ReportNextRoleDto> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<ReportNextRoleDto>(ReportNextRoleDto.class));
     	if(result != null && result.size() > 0){
     		for(int i=0; i<result.size(); i++){
     			if(result.get(i) != null){
     				EaReport report = convertToEaReport(result.get(i),Constants.STATUS_INITIAL);
     				report.setLastUpdatedBy(userId);
     				report.setLastUpdateDate(new Date());
     				hibernateTemplate.update(report);
     			}
     		}
     	}
     	return result;
	}

	private EaReport convertToEaReport(ReportNextRoleDto reportNextRoleDto, String statusHandledByExcel) {
		EaReport report = new EaReport();
		if(reportNextRoleDto.getReportId() != null){
			report = appraiseeCommonSearchDAO.findEareportById(reportNextRoleDto.getReportId());
			if(Constants.STATUS_HANDLED_BY_EXCEL.equals(statusHandledByExcel)){
				report.setStatus(Constants.STATUS_HANDLED_BY_EXCEL);
			}
			if(Constants.STATUS_INITIAL.equals(statusHandledByExcel)){
				report.setStatus(reportNextRoleDto.getRole());
				if(Constants.STATUS_AO.equals(reportNextRoleDto.getRole())){
					report.setAssigned("Y");
				}
			}
		}
		
		return report;
	}

	public void deleteReport(List<Long> reportIds, String batchID) {
		
		// Delete records in report role table
		this.deleteReportRole(reportIds);
		
		// Delete records in contract table
		this.deleteContract(reportIds);
		
		// Delete records in memo details table
		this.deleteMemoDeatils(reportIds);
					
		// Delete records in core competency table
		this.deleteCoreCompetency(reportIds);
					
		// Delete records in objective table
		this.deleteObjectives(reportIds);
					
		// Delete records in train plan table
		this.deleteEaTrainPlan(reportIds);
					
		// Delete empty batch
		this.deleteEaBatch(reportIds, batchID);
					
		// Delete records in report table
		this.deleteEaReport(reportIds);
		
	}

	private void deleteEaReport(List<Long> reportIds) {
		List<EaReport> list = appraiseeCommonSearchDAO.findEaReportByIds(reportIds);
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				EaReport rp = list.get(i);
				hibernateTemplate.delete(rp);
			}
			
		}
		
	}

	private void deleteEaBatch(List<Long> reportIds, String batchID) {
		if(!checkPartialBatch(reportIds,batchID)){
			EaBatch batch = appraiseeCommonSearchDAO.findEaBatchById(batchID);
			if(batch != null && !Util.isEmptyString(batch.getBatchId())){
				hibernateTemplate.delete(batch);
			}
		}
		
	}

	 public boolean checkPartialBatch( List<Long> reportIDs, String batchID ) 
		{
	    	
	         	StringBuffer criteria = new StringBuffer();
	         	MapSqlParameterSource paramSource = new MapSqlParameterSource();
	         	criteria.append(" select * from ha_hr_ea_report");
	   			criteria.append(" where ha_hr_ea_report.report_id not in (");
	   			for (int i=0; i<reportIDs.size(); i++)
	   			{
	   				
	   				if ( i!=0 )
	   					criteria.append(", ");
	   				criteria.append(reportIDs.get(i));
	   			}
	   			criteria.append(")");
	   			criteria.append(" and ha_hr_ea_report.batch_id = :batchID ");
	   			paramSource.addValue("batchID", batchID);
	   			List<EaReport> result = namedParameterJdbcTemplate.query(criteria.toString(), paramSource, new BeanPropertyRowMapper<EaReport>(EaReport.class));
	   			if(result != null && result.size()>0){
	   				return true;
	   			}
	         	return false;	
			
		}
	 
	private void deleteEaTrainPlan(List<Long> reportIds) {
		List<EaTrainPlan> list = appraiseeCommonSearchDAO.findEaTrainPlanByIds(reportIds);
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				EaTrainPlan role = list.get(i);
				hibernateTemplate.delete(role);
			}
			
		}
	}

	private void deleteObjectives(List<Long> reportIds) {
		List<EaObjectives> list = appraiseeCommonSearchDAO.findEaObjectivesByIds(reportIds);
	if(list != null && list.size() > 0){
		for(int i=0; i<list.size(); i++){
			EaObjectives role = list.get(i);
			hibernateTemplate.delete(role);
		}
		
	}
		
	}

	private void deleteCoreCompetency(List<Long> reportIds) {
		List<EaCoreCompetency> list = appraiseeCommonSearchDAO.findEaCoreCompetencyByIds(reportIds);
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				EaCoreCompetency role = list.get(i);
				hibernateTemplate.delete(role);
			}
			
		}
		
	}

	private void deleteMemoDeatils(List<Long> reportIds) {
		List<EaMemoDetails> list = appraiseeCommonSearchDAO.findEaMemoDetailsByIds(reportIds);
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				EaMemoDetails role = list.get(i);
				hibernateTemplate.delete(role);
			}
			
		}
		
	}

	private void deleteContract(List<Long> reportIds) {
		List<EaContract> list = appraiseeCommonSearchDAO.findEaContractByIds(reportIds);
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				EaContract role = list.get(i);
				hibernateTemplate.delete(role);
			}
			
		}
	}

	private void deleteReportRole(List<Long> reportIds) {
		List<EaReportRole> list= appraiseeCommonSearchDAO.searchReportRoleDetailByReportIds(reportIds);
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				EaReportRole role = list.get(i);
				hibernateTemplate.delete(role);
			}
			
		}
	}

	public List<ReportNextRoleDto> handleByExcel(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();
		
		criteria.append("select ha_hr_ea_report.report_Id reportId, ha_hr_ea_report_role.report_Id roleReportId, ha_hr_ea_report.status status, ha_hr_ea_report_role.role role, ha_hr_ea_report_role.role_sequence roleSeq ");
		criteria.append(" from ha_hr_ea_report left join ha_hr_ea_report_role on ha_hr_ea_report.report_id = ha_hr_ea_report_role.report_id ");
		criteria.append(" where");
		criteria.append(" ha_hr_ea_report_role.report_id in ( ");
     	
     	for ( int i=0; i<reportIds.size(); i++)
     	{
     		if ( i != 0)
     			criteria.append(",");
     		criteria.append(reportIds.get(i));
     	}
     	criteria.append(" ) ");
     	criteria.append(" and ha_hr_ea_report_role.role_sequence = 0 ");
     	
     	List<ReportNextRoleDto> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<ReportNextRoleDto>(ReportNextRoleDto.class));
     	
     	if(result != null && result.size() > 0){
     		for(int i=0; i<result.size(); i++){
     			if(result.get(i) != null){
     				EaReport report = convertToEaReport(result.get(i),Constants.STATUS_HANDLED_BY_EXCEL);
     				hibernateTemplate.update(report);
     			}
     		}
     	}
     	return result;
	}

	public List<EaReport> search(String batchName, String rank, String postUnit, String postTitle, String curUser,
			boolean useLike) {
		//test
		//curUser = "ACO-G3";
		//
		StringBuffer criteria = new StringBuffer("select ha_hr_ea_report.* from ha_hr_ea_report ");
		criteria.append(" where 1=1");
     	
     	if ( !Util.isEmptyString(batchName) )
     	{
     		if (useLike)
     			criteria.append(" and Upper(ha_hr_ea_report.batch_id) like '").append(batchName.toUpperCase()).append("%'");
     		else
     			criteria.append(" and Upper(ha_hr_ea_report.batch_id) = '").append(batchName.toUpperCase()).append("'");
     	}
     	
     	if ( !Util.isEmptyString(rank) )
     	{
     		if (useLike)
     			criteria.append(" and Upper(ha_hr_ea_report.substantive_rank) like '").append(rank.toUpperCase()).append("%'");
     		else
     			criteria.append(" and Upper(ha_hr_ea_report.substantive_rank) = '").append(rank.toUpperCase()).append("'");
     	}
     		
     	if ( !Util.isEmptyString(postUnit) )
     	{
     		if (useLike)
     			criteria.append(" and Upper(ha_hr_ea_report.present_post) like '").append(postUnit.toUpperCase()).append("%'");
     		else
     			criteria.append(" and Upper(ha_hr_ea_report.present_post) = '").append(postUnit.toUpperCase()).append("'");
     	}

     	//added by jeff on 20070215
     	if ( !Util.isEmptyString(postTitle) )
     	{
     	    criteria.append(" and ha_hr_ea_report.employee_number in (");
     	    criteria.append(" select distinct hcpv.employee_number from ha_common_people_v hcpv where hcpv.eng_post_title ");
     		if (useLike)
     		    criteria.append(" like '").append(postTitle.toUpperCase()).append("%'");
     		else
     		    criteria.append(" = '").append(postTitle.toUpperCase()).append("'");
     		criteria.append(") ");
     	}
     	//added by jeff on 20070215

     	criteria.append(" and Upper(ha_hr_ea_report.substantive_rank) in ")
				.append("(select gr.rank from ha_hr_ea_group_rank gr, ha_hr_ea_user u ")
				.append(" where gr.group_id = u.group_id ")
				.append(" and Upper(u.user_id) = '" + curUser.toUpperCase() + "') ");
     	List<EaReport> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaReport>(EaReport.class));
     	return result;
	}

	public List<ReportNextRoleDto> getNextRoleByBatch(String userId ,List<String> batchIDs) {

		StringBuffer criteria = new StringBuffer();

		/*
		criteria.append("select ha_hr_ea_report.report_Id reportId, ha_hr_ea_report_role.report_Id roleReportId, ha_hr_ea_report.status status, ha_hr_ea_report_role.role role, ha_hr_ea_report_role.role_sequence roleSeq ");
		criteria.append(" from ha_hr_ea_report  ha_hr_ea_report_role ");
		criteria.append(" where 1=1");
		criteria.append(" and ha_hr_ea_report.report_id = ha_hr_ea_report_role.report_id ");
		criteria.append(" and ha_hr_ea_report.batch_id in ( ");
     	
     	for ( int i=0; i<batchIDs.size(); i++)
     	{
     		if ( i != 0)
     			criteria.append(",");
     		criteria.append("'");
     		criteria.append(batchIDs.get(i));
     		criteria.append("'");
     	}
     	criteria.append(")");
     	criteria.append(" and ha_hr_ea_report.status = 'I'");
     	criteria.append(" and ha_hr_ea_report_role.role_sequence = ");
     	criteria.append("(select min(role_sequence) from ha_hr_ea_report_role");
     	criteria.append(" where report_id = ha_hr_ea_report.report_id");
		criteria.append(" and ha_hr_ea_report_role.officer_id is not null group by report_id)"); 
		*/
		
		criteria.append("select r.REPORT_ID reportId, rr.REPORT_ID roleReportId, r.STATUS status, rr.ROLE role, rr.ROLE_SEQUENCE roleSeq ");
		criteria.append(" from ha_hr_ea_report r, ha_hr_ea_report_role rr ");
		criteria.append(" where 1=1 ");
		criteria.append(" and r.REPORT_ID = rr.REPORT_ID");
		criteria.append(" and r.BATCH_ID IN ( ");
		
		for ( int i=0; i<batchIDs.size(); i++)
     	{
     		if ( i != 0)
     			criteria.append(",");
     		criteria.append("'");
     		criteria.append(batchIDs.get(i));
     		criteria.append("'");
     	}
		
		criteria.append(") ");
		criteria.append(" and r.STATUS = 'I' ");
		criteria.append(" and rr.ROLE_SEQUENCE = ");
		criteria.append(" ( ");
		criteria.append(" SELECT MIN(hherr.role_sequence) from ha_hr_ea_report_role hherr, ha_hr_ea_report hher ");
		criteria.append(" WHERE hherr.report_id = hher.report_id ");
		criteria.append(" and hherr.officer_id IS NOT NULL ");
		criteria.append(" and  hher.report_id =  r.report_id ");
		//criteria.append(" GROUP BY hherr.report_id");
		criteria.append(" ) ");
     	
     	List<ReportNextRoleDto> result = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<ReportNextRoleDto>(ReportNextRoleDto.class));
     	if(result != null && result.size() > 0){
     		for(int i=0; i<result.size(); i++){
     			if(result.get(i) != null){
     				EaReport report = convertToEaReport(result.get(i),Constants.STATUS_INITIAL);
     				report.setLastUpdatedBy(userId);
     				report.setLastUpdateDate(new Date());
     				hibernateTemplate.update(report);
     			}
     		}
     	}
     	return result;
	}
}
