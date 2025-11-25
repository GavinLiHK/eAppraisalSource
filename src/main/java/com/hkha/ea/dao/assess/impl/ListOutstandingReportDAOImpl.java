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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.dao.assess.ListOutstandingReportDAO;
import com.hkha.ea.dto.assess.AppraisalReportRoleDTO;
import com.hkha.ea.dto.assess.AppraiseeBatchDTO;
import com.hkha.ea.dto.assess.ListHistoryDTO;
import com.hkha.ea.helper.EaUtililty;

@Repository("listOutstandingReportDAO")
public class ListOutstandingReportDAOImpl implements ListOutstandingReportDAO {
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(ListOutstandingReportDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<AppraiseeBatchDTO> searchAppraiseeBatch(String currentUser) {
		List<AppraiseeBatchDTO> results = new ArrayList<AppraiseeBatchDTO>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT DISTINCT batch_id batchId, ");
		sql.append("  NVL( (SELECT COUNT(*) FROM ha_hr_ea_report r1 WHERE r1.assigned = 'Y' AND r1.batch_id = r.batch_id AND r1.status NOT IN ('CL', 'EX', 'I') GROUP BY batch_id ), 0) noOfAssigned, ");
		sql.append("  NVL( (SELECT COUNT(*) FROM ha_hr_ea_report r2 WHERE r2.assigned = 'N' AND r2.batch_id = r.batch_id AND r2.status NOT IN ('CL', 'EX', 'I') GROUP BY batch_id ), 0) noOfUnassigned, ");
		sql.append("  NVL( (SELECT COUNT(*) FROM ha_hr_ea_report r2 WHERE r2.assigned in ('Y', 'N') AND r2.batch_id = r.batch_id AND r2.status NOT IN ('CL', 'EX', 'I') GROUP BY batch_id ), 0) noOfAppraisee ");
		sql.append("FROM ha_hr_ea_report r, ");
		sql.append("     ha_hr_ea_report_role rr ");
		sql.append("WHERE 1=1 ");
		sql.append("  AND rr.officer_id = :empNum");
		sql.append("  AND rr.report_id = r.report_id");
		sql.append("  AND rr.role IN ( 'CD' )");
		sql.append("  AND r.status NOT IN ('CL', 'EX', 'I')");
		sql.append("ORDER BY batch_id");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("empNum", currentUser);
		
		results = namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<AppraiseeBatchDTO>(AppraiseeBatchDTO.class));
		
		if ((results != null) && (results.size() > 0)){
			return results;
		}
		
		return null;
	}

	public List<AppraisalReportRoleDTO> searchOutstandingList(String currentUser) {
		List<AppraisalReportRoleDTO> results = new ArrayList<AppraisalReportRoleDTO>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT distinct ");
		sql.append("       HHER.REPORT_ID reportId, ");
		sql.append("       HHER.EMPLOYEE_NUMBER employeeNumber, ");
		sql.append("       HHER.WORKFLOW_TEMPLATE_ID workflowTemplateId, ");
		sql.append("       HHER.COMMENCE_DATE commenceDate, ");
		sql.append("       HHER.END_DATE endDate, ");
		sql.append("       HHER.BATCH_ID batchId, ");
		sql.append("       HHER.NAME name, ");
		sql.append("       HHER.CHINESE_NAME chineseName, ");
		sql.append("       HHER.FIRST_APPOINTMENT_RANK firstAppointmentRank, ");
		sql.append("       HHER.DATE_OF_APP_FIRST_RANK dateOfAppFirstRank, ");
		sql.append("       HHER.SUBSTANTIVE_RANK substantiveRank, ");
		sql.append("       HHER.DATE_OF_APP_SUB_RANK dateOfAppSubRank, ");
		sql.append("       HHER.PRESENT_POST presentPost, ");
		sql.append("       HHER.DIVISION division, ");
		sql.append("       HHER.TELEPHONE telephone, ");
		sql.append("       HHER.OVERALL_DEADLINE overallDeadline, ");
		sql.append("       HHER.STATUS status, ");
		sql.append("       HHER.ROUTING_REASON routingReason, ");
		sql.append("       HHER.HO_ID hoId, ");
		//new column appraisee deadline
		sql.append("       HHER.APPRAISEE_DEADLINE appraiseeDeadline, ");
		
		sql.append("       HHERR.ROLE_SEQUENCE roleSequence, ");
		sql.append("       HHERR.ROLE role, ");
		sql.append("       HHERR.OFFICER_ID officerId, ");
		sql.append("       HHERR.DEADLINE deadline, ");
		sql.append("       HHERR.NOTIFICATION notification, ");
		sql.append("       HHERR.FIRST_REMINDER firstReminder, ");
		sql.append("       HHERR.FIRST_REM_INTERVAL firstRemInterval, ");
		sql.append("       HHERR.SECOND_REMINDER secondReminder, ");
		sql.append("       HHERR.SECOND_REM_INTERVAL secondRemInterval, ");
		sql.append("       HHERR.THIRD_REMINDER thirdReminder, ");
		sql.append("       HHERR.THIRD_REM_INTERVAL thirdRemInterval, ");
		sql.append("       HHERR.SUBS_REMINDER subsReminder, ");
		sql.append("       HHERR.SUBS_REM_INTERVAL subsRemInterval, ");
		sql.append("       HHERR.REMINDER_SENT reminderSent ");
		sql.append("FROM HA_HR_EA_REPORT HHER, ");
		sql.append("     HA_HR_EA_REPORT_ROLE HHERR ");
		sql.append("WHERE 1=1 ");
		sql.append("  AND HHER.REPORT_ID = HHERR.REPORT_ID ");
		sql.append("  AND ( ");
		sql.append("          (    HHER.EMPLOYEE_NUMBER = :empNum ");
		sql.append("           AND HHER.STATUS = 'AP' AND HHERR.ROLE = 'CD' ");
		sql.append("          ) ");
		sql.append("          OR ");
		sql.append("          (    HHERR.OFFICER_ID = :empNum ");
		sql.append("           AND HHER.STATUS NOT IN ('CD', 'RO') ");
		sql.append("           AND HHER.STATUS=HHERR.ROLE ");
		sql.append("          ) ");
		sql.append("          OR  /*RO will be shown when there is no HO*/ ");
		sql.append("          ( ");
		sql.append("               HHERR.OFFICER_ID = :empNum ");
		sql.append("           AND HHER.STATUS = 'RO' ");
		//20170302 RO and HO can enter the report(status:RO)
		//sql.append("           AND HHER.HO_ID IS NULL ");
		sql.append("           AND HHER.STATUS=HHERR.ROLE ");
		sql.append("          ) ");
		sql.append("          OR ");
		sql.append("          ( ");
		sql.append("               HHER.HO_ID = :empNum ");
		sql.append("           AND HHER.STATUS = 'RO' ");
		sql.append("           AND HHER.STATUS=HHERR.ROLE ");
		sql.append("          ) ");
		sql.append("          OR  /*GM*/ ");
		sql.append("          ( ");
		sql.append("               HHER.STATUS = 'GM' AND HHERR.ROLE = 'CD' ");
		sql.append("           AND HHER.SUBSTANTIVE_RANK IN ( ");
		sql.append("                    SELECT HHEGR.RANK ");
		sql.append("                    FROM HA_HR_EA_USER HHEU, ");
		sql.append("                         HA_HR_EA_GROUP_RANK HHEGR, ");
		sql.append("                         HA_HR_EA_GROUP_FUNCTION HHEGF ");
		sql.append("                    WHERE HHEU.USER_ID = :empNum ");
		sql.append("                      AND HHEU.STATUS = 'Y' ");
		sql.append("                      AND HHEU.GROUP_ID = HHEGR.GROUP_ID ");
		sql.append("                      AND HHEGR.GROUP_ID = HHEGF.GROUP_ID ");
		sql.append("                      AND HHEGF.FUNCTION_ID = 50 ");
		sql.append("                      AND HHEGF.ACCESS_RIGHT = 'Y' ");
		sql.append("                ) ");
		sql.append("          ) ");
		sql.append("      ) ");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("empNum", currentUser);
		
		results = namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<AppraisalReportRoleDTO>(AppraisalReportRoleDTO.class));
		
		if ((results != null) && (results.size() > 0)){
			
			for(AppraisalReportRoleDTO dto:results){
				String status = dto.getStatus();
				dto.setStatus(EaUtililty.getFullStatus(status));
				if (dto.getDeadline() == null){
					if(status.equalsIgnoreCase("AP")){
						dto.setDeadline(dto.getAppraiseeDeadline());
					}else{
						dto.setDeadline(dto.getOverallDeadline());
					}
				}
			}
			
			return results;
		}
		
		return null;
	}

	public List<ListHistoryDTO> searchHistory(String currentUser) {
		List<ListHistoryDTO> results = new ArrayList<ListHistoryDTO>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("select hher.REPORT_ID reportId, ");
		sql.append("       hher.EMPLOYEE_NUMBER employeeNum, ");
		sql.append("       hher.NAME fullName, ");
		sql.append("       hher.STATUS status, ");
		sql.append("       hher.SUBSTANTIVE_RANK rank, ");
		sql.append("       hheml.ROLE role, ");
		sql.append("       hheml.MESSAGE_TYPE messageType, ");
		sql.append("       hheml.RECIPIENT recipient, ");
		sql.append("       hheml.RECIPIENT_ROLE recipientRole, ");
		sql.append("       hheml.LAST_UPDATE_DATE lastUpdateDate, ");
		sql.append("       hherr.OFFICER_ID officerId ");
		sql.append("from ha_hr_ea_report hher, ");
		sql.append("     ha_hr_ea_message_log hheml, ");
		sql.append("     (select report_id, role_sequence, role, officer_id from ha_hr_ea_report_role ");
		sql.append("      union ");
		sql.append("      select report_id, 0, 'AP', employee_number from ha_hr_ea_report where employee_number = :empNum ");
		sql.append("     ) hherr ");
		sql.append("where 1=1 ");
		sql.append("  and hher.report_id = hheml.report_id ");
		sql.append("  and hher.report_id = hherr.report_id ");
		sql.append("  and hheml.role = hherr.role ");
		sql.append("  and hherr.OFFICER_ID = :empNum ");
		sql.append("  and hheml.RECIPIENT_ROLE is not null ");
		sql.append("  and hheml.MESSAGE_TYPE = 'N' ");
		sql.append("  and hher.STATUS not in ('CL', 'EX') ");
		sql.append("order by hheml.CREATION_DATE desc ");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("empNum", currentUser);
		
		results = namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<ListHistoryDTO>(ListHistoryDTO.class));
				
		if ((results != null) && (results.size() > 0)){
			String remark = "";
			for(ListHistoryDTO dto:results){
				remark = String.format("Send to %s on %s", EaUtililty.getFullStatus(dto.getRecipientRole()), DateTimeUtil.date2String(dto.getLastUpdateDate()));
				dto.setRemark(remark);
			}
			
			return results;
		}
		
		return null;
	}

}
