package com.hkha.ea.dao.admin.impl;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Struct;

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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.admin.MonitorReportDAO;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.dto.admin.MonitorReportVo;
import com.hkha.ea.dto.assess.PeopleAssignmentDto;

import oracle.jdbc.OracleTypes;
//20231204 The type STRUCT is deprecated
//import oracle.sql.STRUCT;
//End 20231204 The type STRUCT is deprecated

@Repository("monitorReportDAO")
public class MonitorReportDAOImpl implements MonitorReportDAO{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(MonitorReportDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	//20240821 Name Presentation
	private String fullNamePattern = " initcap(f.title) || ' ' || upper(f.last_name) || ' ' || initcap(f.first_name) || decode(f.PRE_NAME_ADJUNCT, null, '', (' ' || initcap(lower(f.PRE_NAME_ADJUNCT)))) || decode(f.suffix, null, '', (', ' || f.suffix)) fullName ,";
	//End 20240821 Name Presentation
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<MonitorReportVo> search(String rank, String employeeNumber, String postUnit, String sortSequence) {
		StringBuffer criteria = new StringBuffer();

		criteria.append(" select distinct r.REPORT_ID reportId, r.EMPLOYEE_NUMBER reportEmployeeNum, r.NAME reportName, r.COMMENCE_DATE commenceDate, r.END_DATE endDate,")
				.append(" r.OVERALL_DEADLINE overallDeadLine, r.STATUS status, r.SUBSTANTIVE_RANK substantiveRank, r.PRESENT_POST persentPost, r.BATCH_ID batchId,")
				.append(" role1.ROLE role1, role1.OFFICER_ID officerId, role1.DEADLINE deadLine, role1.NOTIFICATION notification,")
				//20240821 Name Presentation
				.append(fullNamePattern)
				.append(" f.email_address emailAddress, f.employee_number peopleEmplyNum,")
//				.append(" f.full_name fullName, f.email_address emailAddress, f.employee_number peopleEmplyNum,")
				//End of 20240821 Name Presentation
				.append(" (SELECT COUNT(*) FROM HA_HR_EA_MESSAGE_LOG WHERE REPORT_ID = r.report_id and role = r.status and created_by <> 'INIT_NOTE') as remSent,")
				.append(" ( (select count(*) FROM HA_HR_EA_MESSAGE_LOG WHERE REPORT_ID = r.report_id and created_by = 'INIT_NOTE') ||'  '||  nvl( (")
				.append("  select min(Email_ind) from HA_HR_EA_MESSAGE_LOG where REPORT_ID = r.report_id and created_by = 'INIT_NOTE' and last_update_date = (select max(last_update_date) from HA_HR_EA_MESSAGE_LOG where REPORT_ID = r.report_id and created_by = 'INIT_NOTE')), 'N') ")
				.append("  ) as noteInfo ")
				.append(" from ha_hr_ea_report r,ha_hr_ea_report_role role1, ha_hr_ea_report_role role2,per_all_people_f f ")
				.append(" where ")
		     	.append( " r.REPORT_ID = role1.REPORT_ID ")
				.append( " and r.STATUS = role1.ROLE "); 
		
     	if (!Util.isEmptyString(rank))
     		criteria.append(" and r.substantive_rank like '").append(Util.handleSCInSQL(rank.toUpperCase())).append("%'");
     	if (!Util.isEmptyString(postUnit))
     		criteria.append(" and r.present_post like '").append(Util.handleSCInSQL(postUnit.toUpperCase())).append("%'");
     	
     	criteria.append( " and role1.REPORT_ID = role2.REPORT_ID ");
		
        if ( !Util.isEmptyString(employeeNumber) )
     		criteria.append(" and role2.officer_id = '").append(Util.handleSCInSQL(employeeNumber)).append("'");
        else 
     		criteria.append(" and role2.officer_id is not null ");
        
 		criteria.append(" and r.status != 'CL'");
 		criteria.append(" and role1.officer_id = f.employee_number ");																	
 		criteria.append("   and (" +
				" f.effective_end_date = to_date('31124712', 'ddmmyyyy') " +
				" or " +
 		        " f.effective_start_date >= ( " +
 		        " select max(papfm.effective_start_date) from per_all_people_f papfm where papfm.employee_number = f.employee_number " +
 		        " ) " +
			") "
 		);
 		
 		if(sortSequence.equals("sort1")){
 			criteria.append("order by r.PRESENT_POST ASC,r.NAME ASC, r.COMMENCE_DATE ASC");
		}else if(sortSequence.equals("sort2")){
			criteria.append("order by r.PRESENT_POST ASC,f.full_name ASC, r.NAME ASC");
		}else if(sortSequence.equals("sort3")){
			criteria.append("order by f.full_name ASC,r.PRESENT_POST ASC, r.NAME ASC");
		}else if(sortSequence.equals("sort4")){
			criteria.append("order by role1.ROLE ASC,r.PRESENT_POST ASC, r.NAME ASC");
		}
 		List<MonitorReportVo> list = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<MonitorReportVo>(MonitorReportVo.class));
 		if(list != null && list.size()>0){
 			for ( int i=0; i < list.size(); i++)
 	     	{
 				if(list.get(i) != null){
 					if(list.get(i).getEmailAddress() != null)
 						list.get(i).setEmailAddress(Constants.YES);
 					else
 						list.get(i).setEmailAddress(Constants.NO);
 				}
 	     	}
 		}
     	
		return list;
	}
	
	public List<MonitorReportVo> searchAP(String rank, String employeeNumber, String postUnit, String sortSequence) {
		StringBuffer criteria = new StringBuffer();

		criteria.append(" select distinct r.REPORT_ID reportId, r.EMPLOYEE_NUMBER reportEmployeeNum, r.NAME reportName, r.COMMENCE_DATE commenceDate, r.END_DATE endDate, ")
				.append(" r.OVERALL_DEADLINE overallDeadLine, r.STATUS status, r.SUBSTANTIVE_RANK substantiveRank, r.PRESENT_POST persentPost, r.BATCH_ID batchId, ")
				.append(" role1.ROLE role1, role1.OFFICER_ID officerId, role1.DEADLINE deadLine, role1.NOTIFICATION notification, ")
				//20240821 Name Presentation
				.append(fullNamePattern)
				.append(" f.email_address emailAddress, f.employee_number peopleEmplyNum,  ")
//				.append(" f.full_name fullName, f.email_address emailAddress, f.employee_number peopleEmplyNum,  ")
				//End of20240821 Name Presentation
				.append(" (SELECT COUNT(*) FROM HA_HR_EA_MESSAGE_LOG WHERE REPORT_ID = r.report_id and role = r.status and created_by <> 'INIT_NOTE') as remSent,")
				.append(" ( (select count(*) FROM HA_HR_EA_MESSAGE_LOG WHERE REPORT_ID = r.report_id and created_by = 'INIT_NOTE') ||'  '||  nvl( (")
				.append("  select min(Email_ind) from HA_HR_EA_MESSAGE_LOG where REPORT_ID = r.report_id and created_by = 'INIT_NOTE' and last_update_date = (select max(last_update_date) from HA_HR_EA_MESSAGE_LOG where REPORT_ID = r.report_id and created_by = 'INIT_NOTE')), 'N') ")
				.append("  ) as noteInfo ")
				.append(" from ha_hr_ea_report r,ha_hr_ea_report_role role1, ha_hr_ea_report_role role2,per_all_people_f f ")
				.append(" where ")
				.append( " r.REPORT_ID = role1.REPORT_ID (+)")
				.append( " and r.STATUS = role1.ROLE (+)")
		     	.append( " and r.REPORT_ID = role2.REPORT_ID (+)")
				.append( " and r.STATUS = role2.ROLE (+)");
		
     	if (!Util.isEmptyString(rank))
     		criteria.append(" and r.SUBSTANTIVE_RANK like '").append(Util.handleSCInSQL(rank.toUpperCase())).append("%'");
     	if (!Util.isEmptyString(postUnit))
     		criteria.append(" and r.PRESENT_POST like '").append(Util.handleSCInSQL(postUnit.toUpperCase())).append("%'");
        if ( !Util.isEmptyString(employeeNumber))
     		criteria.append(" and r.EMPLOYEE_NUMBER = '").append(Util.handleSCInSQL(employeeNumber)).append("'");

 		criteria.append(" and r.STATUS = '" + Constants.STATUS_AP + "' ");
 		criteria.append(" and r.EMPLOYEE_NUMBER = f.employee_number ");
 		criteria.append("   and (" +
				" f.effective_end_date = to_date('31124712', 'ddmmyyyy') " +
				" or " +
 		        " f.effective_start_date >= ( " +
 		        " select max(papfm.effective_start_date) from per_all_people_f papfm where papfm.employee_number = f.employee_number " +
 		        " ) " +
			") "
 		);
 		
 		if(sortSequence.equals("sort1")){
 			criteria.append("order by r.PRESENT_POST ASC,r.NAME ASC, r.COMMENCE_DATE ASC");
		}else if(sortSequence.equals("sort2")){
			criteria.append("order by r.PRESENT_POST ASC,f.full_name ASC, r.NAME ASC");
		}else if(sortSequence.equals("sort3")){
			criteria.append("order by f.full_name ASC,r.PRESENT_POST ASC, r.NAME ASC");
		}else if(sortSequence.equals("sort4")){
			criteria.append("order by role1.ROLE ASC,r.PRESENT_POST ASC, r.NAME ASC");
		}
 		
 		List<MonitorReportVo> list = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<MonitorReportVo>(MonitorReportVo.class));
 		if(list != null && list.size()>0){
 			for ( int i=0; i < list.size(); i++)
 	     	{
 				if(list.get(i) != null){
 					if(list.get(i).getEmailAddress() != null)
 						list.get(i).setEmailAddress(Constants.YES);
 					else
 						list.get(i).setEmailAddress(Constants.NO);
 				}
 	     	}
 		}
     	
		return list;
	}

	public List<EaMessageLog> searchLogFromReportIDAndStatus(Long reportId, String status) {
		StringBuffer criteria = new StringBuffer();
     	criteria.append(" select * from ha_hr_ea_message_log ")
     			.append(" where ")
     			.append(" ha_hr_ea_message_log.REPORT_ID = " + reportId + " AND ")
				.append(" ha_hr_ea_message_log.ROLE = '" + status + "' AND ")
				.append(" ha_hr_ea_message_log.CREATED_BY <> 'INIT_NOTE' "); 
     	
     	List<EaMessageLog> list = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaMessageLog>(EaMessageLog.class));
		return list;
	}

	public List<EaMessageLog> searchLogNotification(Long reportId) {
		StringBuffer criteria = new StringBuffer();
     	criteria.append(" select * from ha_hr_ea_message_log ")
     			.append(" where ")
     			.append(" ha_hr_ea_message_log.REPORT_ID = " + reportId + " AND ")
				.append(" ha_hr_ea_message_log.CREATED_BY = 'INIT_NOTE' "); 
     	
     	List<EaMessageLog> list = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaMessageLog>(EaMessageLog.class));
		return list;
	}

	public List<EaMessageLog> searchLogNewestNotification(Long reportId) {
		StringBuffer criteria = new StringBuffer();
     	criteria.append(" select * from ha_hr_ea_message_log ")
     			.append(" where ")
     			.append(" ha_hr_ea_message_log.REPORT_ID = " + reportId + " AND ")
				.append(" ha_hr_ea_message_log.CREATED_BY = 'INIT_NOTE' AND ")
				.append(" ha_hr_ea_message_log.LAST_UPDATE_DATE = (SELECT MAX (ha_hr_ea_message_log.LAST_UPDATE_DATE) FROM ha_hr_ea_message_log WHERE report_id = " + reportId + " AND created_by = 'INIT_NOTE')" );
     	
     	List<EaMessageLog> list = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaMessageLog>(EaMessageLog.class));
		return list;
	}

	public List<MonitorReportVo> search(List<Long> reportIds) {
		StringBuffer criteria = new StringBuffer();

		criteria.append(" select distinct r.REPORT_ID reportId, r.EMPLOYEE_NUMBER reportEmployeeNum, r.NAME reportName, r.COMMENCE_DATE commenceDate, r.END_DATE endDate, ")
				.append(" r.OVERALL_DEADLINE overallDeadLine, r.STATUS status, r.SUBSTANTIVE_RANK substantiveRank, r.PRESENT_POST persentPost, r.BATCH_ID batchId, ")
				.append(" role1.ROLE role1, role1.OFFICER_ID officerId, role1.DEADLINE deadLine, role1.NOTIFICATION notification, ")
				//20240821 Name Presentation
				.append(fullNamePattern)
//				.append(" f.full_name fullName, f.email_address emailAddress, f.employee_number peopleEmplyNum,  ")
				.append(" f.email_address emailAddress, f.employee_number peopleEmplyNum,  ")
				//End of 20240821 Name Presentation
				.append(" (SELECT COUNT(*) FROM HA_HR_EA_MESSAGE_LOG WHERE REPORT_ID = r.report_id and role = r.status and created_by <> 'INIT_NOTE') as remSent,")
				.append(" ( (select count(*) FROM HA_HR_EA_MESSAGE_LOG WHERE REPORT_ID = r.report_id and created_by = 'INIT_NOTE') ||'  '||  nvl( (")
				.append("  select min(Email_ind) from HA_HR_EA_MESSAGE_LOG where REPORT_ID = r.report_id and created_by = 'INIT_NOTE' and last_update_date = (select max(last_update_date) from HA_HR_EA_MESSAGE_LOG where REPORT_ID = r.report_id and created_by = 'INIT_NOTE')), 'N') ")
				.append("  ) as noteInfo ")
				.append(" from ha_hr_ea_report r,ha_hr_ea_report_role role1, ha_hr_ea_report_role role2,per_all_people_f f ")
				.append(" where ");
		
     	criteria.append(" r.REPORT_ID in (");
		for(int i=0;i<reportIds.size();i++){
			if(i != 0)
				criteria.append(",");
			criteria.append(reportIds.get(i));
		}
     	criteria.append(") ")
		     	.append(" and r.REPORT_ID = role1.REPORT_ID ")
				.append(" and ((r.STATUS = role1.ROLE) or (r.STATUS in ('AP', 'GM') and role1.ROLE = 'AO')) ")
		     	.append(" and role1.REPORT_ID = role2.REPORT_ID ")
		   		.append(" and role2.officer_id is not null ")
		 		.append(" and r.status != 'CL'")
		 		.append(" and role1.officer_id = f.employee_number ")
		 		.append("   and (" +
						" f.effective_end_date = to_date('31124712', 'ddmmyyyy') " +
						" or " +
		 		        " f.effective_start_date >= ( " +
		 		        " select max(papfm.effective_start_date) from per_all_people_f papfm where papfm.employee_number = f.employee_number " +
		 		        " ) " +
					") "
		 		);
     	criteria.append(" order by r.REPORT_ID ");
     	List<MonitorReportVo> list = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<MonitorReportVo>(MonitorReportVo.class));
		return list;
	}

	//monitorReport sorting 
	public List<MonitorReportVo> searchAll(String rank, String employeeNumber, String postUnit, String sortSequence) {
		String sortBy = null;
		Connection conn = null;
		CallableStatement proc2 = null;
		 
		 try {
			 conn = jdbcTemplate.getDataSource().getConnection();
			 proc2 = conn.prepareCall("{call HA_HR_EA_MONITOR_REPORT_PKG.search_monitor_report(?,?,?,?,?)}");
			 proc2.setString(1, Util.isEmptyString(rank)? rank:rank.toUpperCase());
			 proc2.setString(2, employeeNumber);
			 proc2.setString(3, Util.isEmptyString(postUnit)? postUnit:postUnit.toUpperCase());
			 if(sortSequence.equals("sort1")){
				 sortBy = "persentPost ASC,reportName ASC, commenceDate ASC";
				}else if(sortSequence.equals("sort2")){
					sortBy = "persentPost ASC,fullName ASC, reportName ASC";
				}else if(sortSequence.equals("sort3")){
					sortBy = "fullName ASC,persentPost ASC, reportName ASC";
				}else if(sortSequence.equals("sort4")){
					sortBy = "role1 ASC,persentPost ASC, reportName ASC";
				}
			 
			 proc2.setString(4, sortBy);
			 //proc2.registerOutParameter(5, OracleTypes.ARRAY,"SEARCH_RP_TAB");
			 proc2.registerOutParameter(5, OracleTypes.ARRAY,"HA_HR_EA_SRCH_RPT_TBL_TYPE");
			 proc2.execute();
			 
			 Array array = proc2.getArray(5);
			 
			 List<MonitorReportVo> list = new ArrayList<MonitorReportVo>();
   		
   			Object[] ay = (Object[])array.getArray();
   			if(ay != null && ay.length > 0){
   				for (int i = 0; i < ay.length; i++) {
   	   				if(ay[i] != null){
   	   				//20231204 The type STRUCT is deprecated	
//   	   				STRUCT st = (STRUCT)ay[i];
   	   				Struct st = (Struct)ay[i];
   	   				//End 20231204 The type STRUCT is deprecated
      				Object[] obj = st.getAttributes();
      				MonitorReportVo vo = new MonitorReportVo();
      				vo.setReportId(obj[0] != null? new BigDecimal(obj[0].toString()).longValue() : null);
      				vo.setReportEmployeeNum(obj[1] != null? obj[1].toString() : null);
      				vo.setReportName(obj[2] != null? obj[2].toString() : null);
      				vo.setCommenceDate(obj[3] != null? Timestamp.valueOf(obj[3].toString()) : null);
      				vo.setEndDate(obj[4] != null? Timestamp.valueOf(obj[4].toString()) : null);
      				vo.setOverallDeadLine(obj[5] != null? Timestamp.valueOf(obj[5].toString()) : null);
      				vo.setStatus(obj[6] != null? obj[6].toString() : null);
      				vo.setSubstantiveRank(obj[7] != null? obj[7].toString() : null);
      				vo.setPersentPost(obj[8] != null? obj[8].toString() : null);
      				vo.setBatchId(obj[9] != null? obj[9].toString() : null);
      				vo.setRole1(obj[10] != null? obj[10].toString() : null);
      				vo.setOfficerId(obj[11] != null? obj[11].toString() : null);
      				vo.setDeadLine(obj[12] != null? Timestamp.valueOf(obj[12].toString()) : null);
      				vo.setNotification(obj[13] != null? obj[13].toString() : null);
      				vo.setFullName(obj[14] != null? obj[14].toString() : null);
      				vo.setEmailAddress(obj[15] != null? obj[15].toString() : null);
      				vo.setPeopleEmplyNum(obj[16] != null? obj[16].toString() : null);
      				vo.setRemSent(obj[17] != null? new BigDecimal(obj[17].toString()).intValue() : null);
      				vo.setNoteInfo(obj[18] != null? obj[18].toString() : null);
      				vo.setSpeReport(obj[19] != null? obj[19].toString() : null);
      				
      				list.add(vo);
   	   				}
   	   			}
   			}
   			
			 proc2.close();
			 DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			 return list;
		 } catch (SQLException e) {	
			e.printStackTrace();
			log.severe(e.toString());
		}
		return null;
		/*StringBuilder sql = new StringBuilder();
		
		String sql1 = this.searchMonitorReport(rank,employeeNumber,postUnit);
		String sql2 = this.searchMonitorReportAP(rank,employeeNumber,postUnit);
		String sql3 = this.searchMonitorReportSpecial(rank,postUnit);
		
		sql.append("select reportId, reportEmployeeNum, reportName, commenceDate, endDate, overallDeadLine, status, ")
			.append("substantiveRank, persentPost, batchId, role1,officerId,deadLine,notification,fullName,emailAddress,")
			.append("peopleEmplyNum,remSent,noteInfo ,speReport")
			.append(" from")
			.append("(");
		sql.append("("+sql1+") ")
			.append("union all")
			.append("("+sql2+") ")
			.append("union all")
			.append("("+sql3+") ")
			.append(") ");
		
		if(sortSequence.equals("sort1")){
			sql.append("order by persentPost ASC,reportName ASC, commenceDate ASC");
		}else if(sortSequence.equals("sort2")){
			sql.append("order by persentPost ASC,fullName ASC, reportName ASC");
		}else if(sortSequence.equals("sort3")){
			sql.append("order by fullName ASC,persentPost ASC, reportName ASC");
		}else if(sortSequence.equals("sort4")){
			sql.append("order by role1 ASC,persentPost ASC, reportName ASC");
		}
		
		List<MonitorReportVo> list = namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<MonitorReportVo>(MonitorReportVo.class));
		return list;*/
	}

	private String searchMonitorReportSpecial(String rank, String postUnit) {
		StringBuilder criteria = new StringBuilder();
		criteria.append("select distinct r.REPORT_ID reportId, r.EMPLOYEE_NUMBER reportEmployeeNum, r.NAME reportName, r.COMMENCE_DATE commenceDate,") 
				.append("r.END_DATE endDate,  r.OVERALL_DEADLINE overallDeadLine, r.STATUS status, ")
				.append("r.SUBSTANTIVE_RANK substantiveRank, r.PRESENT_POST persentPost, ")
				.append("r.BATCH_ID batchId,  r.STATUS role1, ")
				.append("NULL officerId, ")
				.append("NULL deadLine, NULL notification,  ")
				//20240821 Name Presentation
				.append(fullNamePattern)
//				.append("f.full_name fullName,  f.email_address emailAddress, ")
				.append(" f.email_address emailAddress, ")
				//End of 20240821 Name Presentation
				.append("f.employee_number peopleEmplyNum,0  remSent, 'N' noteInfo,'GM' as speReport  ")
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
		return criteria.toString();
	}

	private String searchMonitorReportAP(String rank, String employeeNumber, String postUnit) {
		StringBuffer criteria = new StringBuffer();

		criteria.append(" select distinct r.REPORT_ID reportId, r.EMPLOYEE_NUMBER reportEmployeeNum, r.NAME reportName, r.COMMENCE_DATE commenceDate, r.END_DATE endDate, ")
				.append(" r.OVERALL_DEADLINE overallDeadLine, r.STATUS status, r.SUBSTANTIVE_RANK substantiveRank, r.PRESENT_POST persentPost, r.BATCH_ID batchId, ")
				.append(" r.STATUS role1, role1.OFFICER_ID officerId, role1.DEADLINE deadLine, role1.NOTIFICATION notification, ")
				//20240821 Name Presentation
				.append(fullNamePattern)
//				.append(" f.full_name fullName, case when f.email_address is null then 'N' else 'Y' end emailAddress, f.employee_number peopleEmplyNum,  ")
				.append(" case when f.email_address is null then 'N' else 'Y' end emailAddress, f.employee_number peopleEmplyNum,  ")
				//End of 20240821 Name Presentation
				.append(" (SELECT COUNT(*) FROM HA_HR_EA_MESSAGE_LOG WHERE REPORT_ID = r.report_id and role = r.status and created_by <> 'INIT_NOTE') as remSent,")
				.append(" ( (select count(*) FROM HA_HR_EA_MESSAGE_LOG WHERE REPORT_ID = r.report_id and created_by = 'INIT_NOTE') ||'  '||  nvl( (")
				.append("  select min(Email_ind) from HA_HR_EA_MESSAGE_LOG where REPORT_ID = r.report_id and created_by = 'INIT_NOTE' and last_update_date = (select max(last_update_date) from HA_HR_EA_MESSAGE_LOG where REPORT_ID = r.report_id and created_by = 'INIT_NOTE')), 'N') ")
				.append("  ) as noteInfo, 'AP' as speReport ")
				.append(" from ha_hr_ea_report r,ha_hr_ea_report_role role1, ha_hr_ea_report_role role2,per_all_people_f f ")
				.append(" where ")
				.append( " r.REPORT_ID = role1.REPORT_ID (+)")
				.append( " and r.STATUS = role1.ROLE (+)")
		     	.append( " and r.REPORT_ID = role2.REPORT_ID (+)")
				.append( " and r.STATUS = role2.ROLE (+)");
		
     	if (!Util.isEmptyString(rank))
     		criteria.append(" and r.SUBSTANTIVE_RANK like '").append(Util.handleSCInSQL(rank.toUpperCase())).append("%'");
     	if (!Util.isEmptyString(postUnit))
     		criteria.append(" and r.PRESENT_POST like '").append(Util.handleSCInSQL(postUnit.toUpperCase())).append("%'");
        if ( !Util.isEmptyString(employeeNumber))
     		criteria.append(" and r.EMPLOYEE_NUMBER = '").append(Util.handleSCInSQL(employeeNumber)).append("'");

 		criteria.append(" and r.STATUS = '" + Constants.STATUS_AP + "' ");
 		criteria.append(" and r.EMPLOYEE_NUMBER = f.employee_number ");
 		criteria.append("   and (" +
				" f.effective_end_date = to_date('31124712', 'ddmmyyyy') " +
				" or " +
 		        " f.effective_start_date >= ( " +
 		        " select max(papfm.effective_start_date) from per_all_people_f papfm where papfm.employee_number = f.employee_number " +
 		        " ) " +
			") "
 		);
		return criteria.toString();
	}

	private String searchMonitorReport(String rank, String employeeNumber, String postUnit) {
		StringBuffer criteria = new StringBuffer();

		criteria.append(" select distinct r.REPORT_ID reportId, r.EMPLOYEE_NUMBER reportEmployeeNum, r.NAME reportName, r.COMMENCE_DATE commenceDate, r.END_DATE endDate,")
				.append(" r.OVERALL_DEADLINE overallDeadLine, r.STATUS status, r.SUBSTANTIVE_RANK substantiveRank, r.PRESENT_POST persentPost, r.BATCH_ID batchId,")
				.append(" role1.ROLE role1, role1.OFFICER_ID officerId, role1.DEADLINE deadLine, role1.NOTIFICATION notification,")
				//20240821 Name Presentation
				.append(fullNamePattern)
//				.append(" f.full_name fullName, case when f.email_address is null then 'N' else 'Y' end emailAddress, f.employee_number peopleEmplyNum,")
				.append(" case when f.email_address is null then 'N' else 'Y' end emailAddress, f.employee_number peopleEmplyNum,")
				//End of 20240821 Name Presentation

				.append(" (SELECT COUNT(*) FROM HA_HR_EA_MESSAGE_LOG WHERE REPORT_ID = r.report_id and role = r.status and created_by <> 'INIT_NOTE') as remSent,")
				.append(" ( (select count(*) FROM HA_HR_EA_MESSAGE_LOG WHERE REPORT_ID = r.report_id and created_by = 'INIT_NOTE') ||'  '||  nvl( (")
				.append("  select min(Email_ind) from HA_HR_EA_MESSAGE_LOG where REPORT_ID = r.report_id and created_by = 'INIT_NOTE' and last_update_date = (select max(last_update_date) from HA_HR_EA_MESSAGE_LOG where REPORT_ID = r.report_id and created_by = 'INIT_NOTE')), 'N') ")
				.append("  ) as noteInfo,'N' as speReport ")
				.append(" from ha_hr_ea_report r,ha_hr_ea_report_role role1, ha_hr_ea_report_role role2,per_all_people_f f ")
				.append(" where ")
		     	.append( " r.REPORT_ID = role1.REPORT_ID ")
				.append( " and r.STATUS = role1.ROLE "); 
		
     	if (!Util.isEmptyString(rank))
     		criteria.append(" and r.substantive_rank like '").append(Util.handleSCInSQL(rank.toUpperCase())).append("%'");
     	if (!Util.isEmptyString(postUnit))
     		criteria.append(" and r.present_post like '").append(Util.handleSCInSQL(postUnit.toUpperCase())).append("%'");
     	
     	criteria.append( " and role1.REPORT_ID = role2.REPORT_ID ");
		
        if ( !Util.isEmptyString(employeeNumber) )
     		criteria.append(" and role2.officer_id = '").append(Util.handleSCInSQL(employeeNumber)).append("'");
        else 
     		criteria.append(" and role2.officer_id is not null ");
        
 		criteria.append(" and r.status != 'CL'");
 		criteria.append(" and role1.officer_id = f.employee_number ");																	
 		criteria.append("   and (" +
				" f.effective_end_date = to_date('31124712', 'ddmmyyyy') " +
				" or " +
 		        " f.effective_start_date >= ( " +
 		        " select max(papfm.effective_start_date) from per_all_people_f papfm where papfm.employee_number = f.employee_number " +
 		        " ) " +
			") "
 		);
		return criteria.toString();
	}
}
