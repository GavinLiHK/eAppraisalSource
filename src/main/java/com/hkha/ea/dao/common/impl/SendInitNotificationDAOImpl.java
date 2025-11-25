package com.hkha.ea.dao.common.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Util;
import com.hkha.ea.dao.common.SendInitNotificationDAO;
import com.hkha.ea.dto.admin.ReminderRecipientVo;
import com.hkha.ea.dto.admin.SendAutoReminderVo;
import com.hkha.ea.dto.assess.PeopleAssignmentDto;

@Repository("sendInitNotificationDAO")
public class SendInitNotificationDAOImpl implements SendInitNotificationDAO{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<SendAutoReminderVo> executeBatch(){
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT reportId,officerId,officerName,emailAddress,OFFICER_RANK_TYPE officerRankType, batchId,MESSAGE message,NAME rpNameAndEmplNum, ")
		.append("firstName,lastName, STATUS, role, substantiveRank,presentPost,commenceDate,endDate  ")
		.append(",MESSAGE_FOOTER messageFooter ")
		.append("FROM ( ")
		.append("SELECT hherr.report_id reportId, hherr.officer_id officerId, hherr.officer_name officerName,papf.email_address emailAddress, ")
		.append("'GG' OFFICER_RANK_TYPE , ")
		.append("hher.batch_id batchId, ")
		.append("( select para_value from ha_hr_ea_sys_para where para_name = 'NOTE_MSG_GG' ) MESSAGE, ")
		.append("( select para_value from ha_hr_ea_sys_para where para_name = 'NOTE_MSG_GG_FOOTER' ) MESSAGE_FOOTER, ")
		.append("hher.NAME || ' (' || hher.EMPLOYEE_NUMBER || ')' NAME, ")
		.append("appraisee.FIRST_NAME firstName, appraisee.LAST_NAME lastName, hherr.role, hher.STATUS, hher.SUBSTANTIVE_RANK substantiveRank, hher.PRESENT_POST presentPost, ")
		.append("hher.COMMENCE_DATE commenceDate, hher.END_DATE endDate ")
		.append("FROM  ")
		.append("ha_hr_ea_report hher, ")
		.append("ha_hr_ea_report_role hherr, ")
		.append("ha_hr_ea_group_rank hhegr, ")
		.append("per_all_people_f papf, ")
		.append("per_all_people_f appraisee ")
		.append("WHERE 1=1 ")
		.append("AND (   (hher.status in ('CD') and hher.status = hherr.role ) ")
		.append("or (hher.status in ('AO', 'AP') AND hherr.role = 'AO' and hherr.officer_id is not null) ")
		.append(") ")
		.append("AND hhegr.GROUP_ID = 4 ")
		.append("AND hher.overall_deadline = ( SELECT to_date(para_value, 'DD-MON-YYYY') FROM ha_hr_ea_sys_para WHERE para_name = 'NOTE_DUE_DATE_GG' ) ")
		.append("AND trunc(sysdate) = ( SELECT to_date(para_value, 'DD-MON-YYYY') FROM ha_hr_ea_sys_para WHERE para_name = 'NOTE_EXEC_DATE_GG' ) ")
		.append("AND hher.SUBSTANTIVE_RANK = hhegr.RANK ")
		.append("AND hher.report_id = hherr.report_id ")
		.append("AND hherr.OFFICER_ID = papf.employee_number ")
		.append("AND hher.EMPLOYEE_NUMBER = appraisee.EMPLOYEE_NUMBER ")
		.append("AND trunc(sysdate) between papf.EFFECTIVE_START_DATE and papf.EFFECTIVE_END_DATE ")
		.append("AND trunc(sysdate) between appraisee.EFFECTIVE_START_DATE and appraisee.EFFECTIVE_END_DATE ")
		
		.append("UNION ALL ")
		
		.append("SELECT hherr.report_id reportId, hherr.officer_id officerId, hherr.officer_name officerName, papf.email_address emailAddress, ")
		.append("'GM' OFFICER_RANK_TYPE, ")
		.append("hher.batch_id batchId, ")
		.append("( select para_value from ha_hr_ea_sys_para where para_name = 'NOTE_MSG_GM' ) MESSAGE, ")
		.append("( select para_value from ha_hr_ea_sys_para where para_name = 'NOTE_MSG_GM_FOOTER' ) MESSAGE_FOOTER, ")
		.append("hher.NAME || ' (' || hher.EMPLOYEE_NUMBER || ')' NAME, ")
		.append("appraisee.FIRST_NAME firstName, appraisee.LAST_NAME lastName, hherr.role, hher.STATUS, hher.SUBSTANTIVE_RANK substantiveRank, ")
		.append("hher.PRESENT_POST presentPost, hher.COMMENCE_DATE commenceDate, hher.END_DATE endDate ")
		.append("FROM  ")
		.append("ha_hr_ea_report hher, ")
		.append("ha_hr_ea_report_role hherr, ")
		.append("ha_hr_ea_group_rank hhegr, ")
		.append("per_all_people_f papf, ")
		.append("per_all_people_f appraisee ")
		.append("WHERE 1=1 ") 
		.append("AND (   (hher.status in ('CD') and hher.status = hherr.role ) ")
		.append("or (hher.status in ('AO', 'AP') AND hherr.role = 'AO' and hherr.officer_id is not null) ")
		.append(") ")
		.append("AND hhegr.GROUP_ID = 5  ")
		.append("AND hher.overall_deadline = ( SELECT to_date(para_value, 'DD-MON-YYYY') FROM ha_hr_ea_sys_para WHERE para_name = 'NOTE_DUE_DATE_GM' ) ")
		.append("AND trunc(sysdate) = ( SELECT to_date(para_value, 'DD-MON-YYYY') FROM ha_hr_ea_sys_para WHERE para_name = 'NOTE_EXEC_DATE_GM' ) ")
		.append("AND hher.SUBSTANTIVE_RANK = hhegr.RANK ")
		.append("AND hher.report_id = hherr.report_id ")
		.append("AND hherr.OFFICER_ID = papf.employee_number ")
		.append("AND hher.EMPLOYEE_NUMBER = appraisee.EMPLOYEE_NUMBER ")
		.append("AND trunc(sysdate) between papf.EFFECTIVE_START_DATE and papf.EFFECTIVE_END_DATE ")
		.append("AND trunc(sysdate) between appraisee.EFFECTIVE_START_DATE and appraisee.EFFECTIVE_END_DATE ) ")
		.append("ORDER BY presentPost, substantiveRank, lastName, firstName  ");
		
		List<SendAutoReminderVo> list = namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<SendAutoReminderVo>(SendAutoReminderVo.class));
		return list;

	}
}
