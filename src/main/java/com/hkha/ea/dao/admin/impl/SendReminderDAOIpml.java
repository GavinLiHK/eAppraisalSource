package com.hkha.ea.dao.admin.impl;

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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Util;
import com.hkha.ea.dao.admin.SendReminderDAO;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.dto.admin.OfficerEmailDto;
import com.hkha.ea.dto.admin.ReminderRecipientVo;
import com.hkha.ea.dto.admin.SendAutoReminderVo;

@Repository("sendReminderDAO")
public class SendReminderDAOIpml implements SendReminderDAO{

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SendReminderDAOIpml.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	//20240821 Name Presentation
	private String fullNamePattern = " initcap(PAPF.title) || ' ' || upper(PAPF.last_name) || ' ' || initcap(PAPF.first_name) || decode(PAPF.PRE_NAME_ADJUNCT, null, '', (' ' || initcap(lower(PAPF.PRE_NAME_ADJUNCT)))) || decode(PAPF.suffix, null, '', (', ' || PAPF.suffix)) fullName ,";
	//End 20240821 Name Presentation
	
	 /**
     * Search report and current office by report id
    * @param reportIDs List of Report ID in Vector 
    * @throws Exception
    */
   public List<ReminderRecipientVo> search(List<Long> reportIDs) {
    	StringBuffer sql = new StringBuffer();

    	sql.append(" select distinct r.REPORT_ID reportId, r.EMPLOYEE_NUMBER employeeNum, r.NAME name, r.COMMENCE_DATE commenceDate, r.END_DATE endDate, r.STATUS status, r.HO_ID hoId, r.SUBSTANTIVE_RANK substantiveRank, r.PRESENT_POST presentPost,  ")
    		//20240821 Name Presentation
    		.append(" role1.ROLE role, f.employee_number peopleEmployeeNum, initcap(f.title) || ' ' || upper(f.last_name) || ' ' || initcap(f.first_name) || decode(f.PRE_NAME_ADJUNCT, null, '', (' ' || initcap(lower(f.PRE_NAME_ADJUNCT)))) || decode(f.suffix, null, '', (', ' || f.suffix)) fullName, f.email_address emailAddress ")
//    		.append(" role1.ROLE role, f.employee_number peopleEmployeeNum, f.full_name fullName, f.email_address emailAddress ")
    		//End of 20240821 Name Presentation
    		.append(" from ha_hr_ea_report r, ha_hr_ea_report_role role1, per_all_people_f f, HA_COMMON_ASSIGNMENT_V v ")
    		.append(" where ")
    		.append(" r.REPORT_ID in (");
    	for(int i=0; i<reportIDs.size();i++){
    		if(i != 0)
    			sql.append(" ,");
    		sql.append(reportIDs.get(i));
    	}
    	sql.append(") ")
			.append(" and r.REPORT_ID = role1.REPORT_ID ")
			.append(" and r.STATUS = role1.ROLE ")
			.append(" and r.status != 'CL'")
			.append(" and role1.officer_id = f.employee_number ")
			.append(" and f.PERSON_ID = v.PERSON_ID ")
			.append(" and f.effective_start_date >= ( ")
	    	.append("   select max(papfm.effective_start_date) from per_all_people_f papfm ") 
	    	.append("   where papfm.employee_number = f.employee_number ")
	    	.append("  ) ")
	    	.append(" and v.primary_flag = 'Y' ")
	    	.append("   and v.assignment_start_date >= ( ")
	     	.append("   	select max(hcavm.assignment_start_date) from HA_COMMON_ASSIGNMENT_V hcavm ")
	     	.append("   	where hcavm.employee_number = v.employee_number ")
	     	.append("       and hcavm.primary_flag = 'Y' ")
	     	.append("   ) ")
	    	.append(" order by r.report_id ");

    	List<ReminderRecipientVo> list = namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ReminderRecipientVo>(ReminderRecipientVo.class));
    	return list;
    }
   
   //EA015 SendAutoReminder start
   public List<SendAutoReminderVo> searchRpForSendAutoReminder(){
	   StringBuffer sql = new StringBuffer();
	   sql.append("select reportId,reportEmployeeNum,reportName, commenceDate, endDate, status, hoId, substantiveRank, presentPost, batchId,appraiseeDeadline,")
		  .append(" role, officerId, deadline, firstReminder, firstRemInt, secondReminder, secondRemInt, thirdReminder, thirdRemInt, subsReminder, subsRemInt,")
		  .append(" fullName, emailAddress, peopleEmployeeNum,  engRank, primaryFlag, LAST_AUTO_RMD_MESSAGE_TYPE messageType ")
		  .append("from (( ")
		  .append(searchRpStatusExcludeAP())
		  .append(")")
		  .append("union all ")
		  .append("( ")
		  .append(searchRpStatusAP())
		  .append(")) order by officerId, batchId ");
	   List<SendAutoReminderVo> list = namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<SendAutoReminderVo>(SendAutoReminderVo.class));
	   return list;
	   
   }
   public String searchRpStatusExcludeAP(){
	   StringBuffer sql = new StringBuffer();

   	sql.append(" SELECT HHER.REPORT_ID reportId,HHER.EMPLOYEE_NUMBER reportEmployeeNum,HHER.NAME reportName,HHER.COMMENCE_DATE commenceDate,HHER.END_DATE endDate,")
   	.append("HHER.STATUS status,HHER.HO_ID hoId,HHER.SUBSTANTIVE_RANK substantiveRank,HHER.PRESENT_POST presentPost,HHER.BATCH_ID batchId,HHER.APPRAISEE_DEADLINE appraiseeDeadline,")
   	.append("ROLE1.ROLE role,ROLE1.OFFICER_ID officerId,ROLE1.DEADLINE deadline,ROLE1.FIRST_REMINDER firstReminder,NVL(ROLE1.FIRST_REM_INTERVAL,7) firstRemInt,ROLE1.SECOND_REMINDER secondReminder,")
   	.append("NVL(ROLE1.SECOND_REM_INTERVAL,7) secondRemInt,ROLE1.THIRD_REMINDER thirdReminder,NVL(ROLE1.THIRD_REM_INTERVAL,7) thirdRemInt,ROLE1.SUBS_REMINDER subsReminder,NVL(ROLE1.SUBS_REM_INTERVAL,7) subsRemInt,")
   	//20240821 Name Presentation
   	.append(fullNamePattern)
   	//End of 20240821 Name Presentation
//   	.append("PAPF.FULL_NAME fullName,PAPF.EMAIL_ADDRESS emailAddress,PAPF.EMPLOYEE_NUMBER peopleEmployeeNum, HCAV.ENG_RANK engRank,HCAV.PRIMARY_FLAG primaryFlag,")
   	.append("PAPF.EMAIL_ADDRESS emailAddress,PAPF.EMPLOYEE_NUMBER peopleEmployeeNum, HCAV.ENG_RANK engRank,HCAV.PRIMARY_FLAG primaryFlag,")
   	.append("(")
   	.append("select to_char(nvl(max(ha_hr_ea_message_log.message_type),'-1')) || '@' || to_char(nvl(max(to_char(ha_hr_ea_message_log.creation_date,'yyyy-mm-dd')),'')) ")
   	.append("from ha_hr_ea_message_log ")
   	.append("where ha_hr_ea_message_log.type = 'A' ")
   	.append("and ha_hr_ea_message_log.report_id = hher.report_id and ha_hr_ea_message_log.role = hher.status and ha_hr_ea_message_log.message_type <> 'N'")
   	.append(") AS LAST_AUTO_RMD_MESSAGE_TYPE ")
   	.append("FROM HA_HR_EA_REPORT HHER,HA_HR_EA_REPORT_ROLE ROLE1,PER_ALL_PEOPLE_F PAPF, HA_COMMON_ASSIGNMENT_V HCAV ")
   	.append("WHERE 1=1 ")
   	.append("and hher.status not in ('I','CD','CL') ")
   	.append("and hher.REPORT_ID = role1.REPORT_ID ")
   	.append("and hher.STATUS = role1.ROLE ")
   	.append("and role1.deadline < trunc(sysdate) ")
   	//.append("and role1.deadline <= trunc(sysdate) ")
   	.append("and papf.business_group_id = ha_global.get_business_group_id(ha_global.get_housing_authority) ")
   	.append("and role1.officer_id = papf.employee_number ")
   	.append("and papf.PERSON_ID = hcav.PERSON_ID ")
   	.append("and trunc(sysdate) between papf.effective_start_date and papf.effective_end_date ")
   	.append("and hcav.primary_flag = 'Y' ")
   	.append("and trunc(sysdate) between hcav.people_start_date and hcav.people_end_date ")
   	.append("and trunc(sysdate) between hcav.assignment_start_date and hcav.assignment_end_date ");
   	
   	// If the officer is an ex-employee, no need to send reminder to him/her
   	//.append("and hcav.assignment_start_date >= ( select max(hcavm.assignment_start_date) from HA_COMMON_ASSIGNMENT_V hcavm "
    //+ "where hcavm.employee_number = hcav.employee_number and hcavm.assignment_id = hcav.assignment_id and hcavm.primary_flag = 'Y' and HCAVM.person_id = HCAV.person_id) ");

   	return sql.toString();
   }
   
   public String searchRpStatusAP(){
	   StringBuffer sql = new StringBuffer();

   	sql.append(" SELECT HHER.REPORT_ID reportId,HHER.EMPLOYEE_NUMBER reportEmployeeNum,HHER.NAME reportName,HHER.COMMENCE_DATE commenceDate,HHER.END_DATE endDate,")
   	.append("HHER.STATUS status,HHER.HO_ID hoId,HHER.SUBSTANTIVE_RANK substantiveRank,HHER.PRESENT_POST presentPost,HHER.BATCH_ID batchId,HHER.APPRAISEE_DEADLINE appraiseeDeadline,")
   	.append("Decode(HHER.STATUS, 'AP',HHER.STATUS ,ROLE1.ROLE) role, Decode(HHER.STATUS, 'AP',HHER.EMPLOYEE_NUMBER ,ROLE1.OFFICER_ID) officerId,ROLE1.DEADLINE deadline,ROLE1.FIRST_REMINDER firstReminder,NVL(ROLE1.FIRST_REM_INTERVAL,7) firstRemInt,ROLE1.SECOND_REMINDER secondReminder,")
   	.append("NVL(ROLE1.SECOND_REM_INTERVAL,7) secondRemInt,ROLE1.THIRD_REMINDER thirdReminder,NVL(ROLE1.THIRD_REM_INTERVAL,7) thirdRemInt,ROLE1.SUBS_REMINDER subsReminder,NVL(ROLE1.SUBS_REM_INTERVAL,7) subsRemInt,")
   	//20240821 Name Presentation
   	.append(fullNamePattern)
   	//End of 20240821 Name Presentation
//   	.append("PAPF.FULL_NAME fullName,PAPF.EMAIL_ADDRESS emailAddress,PAPF.EMPLOYEE_NUMBER peopleEmployeeNum, HCAV.ENG_RANK engRank,HCAV.PRIMARY_FLAG primaryFlag,")
   	.append("PAPF.EMAIL_ADDRESS emailAddress,PAPF.EMPLOYEE_NUMBER peopleEmployeeNum, HCAV.ENG_RANK engRank,HCAV.PRIMARY_FLAG primaryFlag,")
   	.append("(")
   	.append("select to_char(nvl(max(ha_hr_ea_message_log.message_type),'-1')) || '@' || to_char(nvl(max(to_char(ha_hr_ea_message_log.creation_date,'yyyy-mm-dd')),'')) ")
   	.append("from ha_hr_ea_message_log ")
   	.append("where ha_hr_ea_message_log.type = 'A' ")
   	.append("and ha_hr_ea_message_log.report_id = hher.report_id and ha_hr_ea_message_log.role = hher.status and ha_hr_ea_message_log.message_type <> 'N'")
   	.append(") AS LAST_AUTO_RMD_MESSAGE_TYPE ")
   	.append("FROM HA_HR_EA_REPORT HHER,HA_HR_EA_REPORT_ROLE ROLE1,PER_ALL_PEOPLE_F PAPF, HA_COMMON_ASSIGNMENT_V HCAV ")
   	.append("WHERE 1=1 ")
   	.append("and hher.status = 'AP' ")
   	.append("and hher.REPORT_ID = role1.REPORT_ID ")
   	.append("and role1.role = 'AO' ")
   	.append("and hher.appraisee_deadline < trunc(sysdate) ")
   	//.append("and hher.appraisee_deadline <= trunc(sysdate) ")
   	.append("and papf.business_group_id = ha_global.get_business_group_id(ha_global.get_housing_authority) ")
   	.append("and hher.employee_number = papf.employee_number ")
   	.append("and papf.person_id = hcav.person_id ")
   	.append("and trunc(sysdate) between papf.effective_start_date and papf.effective_end_date ")
   	.append("and hcav.primary_flag = 'Y' ")
   	.append("and trunc(sysdate) between hcav.people_start_date and hcav.people_end_date ")
   	.append("and trunc(sysdate) between hcav.assignment_start_date and hcav.assignment_end_date ");

   	return sql.toString();
   }
   //end

	public String searchEmailAddressByEmployeeNumber(String employeeNumber) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select employee_number, email_address ")
		.append("from per_all_people_f ")
		.append("where business_group_id = ha_global.get_business_group_id(ha_global.get_housing_authority) ")
		.append("and employee_number = :employeeNUmber ")
		.append("and trunc(sysdate) between effective_start_date and effective_end_date ")
		.append("and rownum = 1 ")
		;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("employeeNUmber", employeeNumber);
		
		List<OfficerEmailDto> list = namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<OfficerEmailDto>(OfficerEmailDto.class));
		if ((list != null) && (list.size() > 0)){
			return list.get(0).getEmailAddress().trim();
		}
		return "";
	}
}
