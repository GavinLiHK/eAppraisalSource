package com.hkha.ea.validator.assess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dto.assess.AssignOfficerDto;
import com.hkha.ea.dto.assess.ReportRoleDto;

@Component( "assignOfficerValidator" )
public class AssignOfficerValidator implements Validator{
	
	private static String BATCH_NAME_FORMAT = "[A-Za-z0-9_\\-()<>\\/\\p{Space}]*";
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AssignOfficerValidator.class.getName());
	//End 20231201 Write log in catalina.out

	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}
public void validateWorkflowDetailMandatory(String mode,String reportTemplate, AssignOfficerDto dto, Errors errors) {
		
		String el="(((0[1-9]|[12][0-9]|3[01])/((0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)/(0[469]|11))|(0[1-9]|[1][0-9]|2[0-8])/(02))/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}))|(29/02/(([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00)))";
		Pattern p = Pattern.compile(el);      
		if(StringUtil.isEmptyString(dto.getBatchName())){
			errors.rejectValue("batchName", "error.message.empty.content", new String[]{"Batch Name"}, "Batch Name is required");
		}else{
			Pattern p1 = Pattern.compile(BATCH_NAME_FORMAT);
			Matcher m = p1.matcher(dto.getBatchName());
			if(!m.matches()){
				errors.rejectValue("batchName", "error."+Constants.ERROR_INPUT_CHARACTERS);
			}
		}
		if(StringUtil.isEmptyString(dto.getDeadline())){
			errors.rejectValue("deadline", "error.message.empty.content", new String[]{"Overall Deadline "}, "Overall Deadline is required");
		}else{
			Matcher m = p.matcher(dto.getDeadline());
			if(!m.matches()){
				errors.rejectValue("deadline", "error.message.empty.content.date.format", new String[]{"Overall Deadline "}, "format must be 'dd/MM/yyyy'");
			}

		}
		if(!StringUtil.isEmptyString(dto.getAppraiseeDeadline())){
			Matcher m = p.matcher(dto.getAppraiseeDeadline());
			if(!m.matches()){
				errors.rejectValue("appraiseeDeadline", "error.message.empty.content.date.format", new String[]{"Appraisee Deadline "}, "format must be 'dd/MM/yyyy'");
			}
		}
		
		if(dto.getReportRoleCD() == null){
			errors.rejectValue("noftfication", "error.message.empty.content", new String[]{"Coordinator Notification "}, "Coordinator Notification is required");
		}else{
			if(StringUtil.isEmptyString(dto.getReportRoleCD().getNotification())){
				errors.rejectValue("noftfication", "error.message.empty.content", new String[]{"Coordinator Notification "}, "Coordinator Notification is required");
			}
			/*if(StringUtil.isEmptyString(dto.getReportRoleCD().getEmployeeNum())){
				errors.rejectValue("reportRoleCD.employeeNum", "error.message.empty.content", new String[]{"Coordinator Officer Employee Number "}, "Coordinator Officer Employee Number is required");
			}
			if(StringUtil.isEmptyString(dto.getReportRoleCD().getEmployeeName())){
				errors.rejectValue("reportRoleCD.employeeName", "error.message.empty.content", new String[]{"Coordinator Officer Employee Name "}, "Coordinator Officer Employee Name is required");
			}*/
		}
		
		if (Constants.PAGE_ASSIGN_REMAINING.equals(mode)){
			if(dto.getReportRoleAO() != null){
				if(Util.isEmptyString(dto.getReportRoleAO().getEmployeeNum())){
					errors.rejectValue("reportRoleAO.employeeName", "error."+Constants.ERROR_OFFICER_ID_FOR_AO);
				}
			}else{
				if(Util.isEmptyString(dto.getReportRoleAO().getEmployeeNum())){
					errors.rejectValue("reportRoleAO.employeeName", "error."+Constants.ERROR_OFFICER_ID_FOR_AO);
				}
			}
		}else{
			if(dto.getReportRoleAO() != null && dto.getReportRoleCD() != null){
				if(Util.isEmptyString(dto.getReportRoleAO().getEmployeeNum()) && Util.isEmptyString(dto.getReportRoleCD().getEmployeeNum())){
					errors.rejectValue("reportRoleCD.employeeNum", "error."+Constants.ERROR_OFFICER_ID_FOR_CD_AO);
				}
			}
		}
		
		if(dto.getReportRoleAO() != null && dto.getReportRoleCO() != null){
			if(!Util.isEmptyString(dto.getReportRoleAO().getEmployeeNum()) && !Util.isEmptyString(dto.getReportRoleCO().getEmployeeNum()))
				if(Util.isEqual(dto.getReportRoleAO().getEmployeeNum(), dto.getReportRoleCO().getEmployeeNum())){
					errors.rejectValue("reportRoleAO.employeeNum", "error."+Constants.ERROR_SAME_OFFICER_FOR_AO_CO);
				}
		}
		if(!Util.isEmptyString(dto.getAppraiseeDeadline())){
			this.checkAPDateDifferent(reportTemplate, dto, errors);
		}
		
		this.checkDateDifferent(reportTemplate,dto,errors);
		
		if(!Util.isEmptyString(dto.getDeadline())){
			if (Util.isEmptyString(dto.getReportRoleAO().getDeadline())){
				errors.rejectValue("deadline", "error.message.empty.content", new String[]{"AO Deadline "}, "AO deadline is required.");
			}else if(Util.isEmptyString(dto.getReportRoleCO().getDeadline())){
				errors.rejectValue("deadline", "error.message.empty.content", new String[]{"CO Deadline "}, "CO deadline is required.");
			}else if(Util.isEmptyString(dto.getReportRoleIO().getDeadline())){
				errors.rejectValue("deadline", "error.message.empty.content", new String[]{"IO Deadline "}, "IO deadline is required.");
			}else{
				this.checkOverallDateDifferent(reportTemplate, dto, errors);
			}
			
		}
		/*
		//20161215 check Overall deadline equal or after reviewing officer deadline
		if(!StringUtil.isEmptyString(dto.getDeadline()) && !StringUtil.isEmptyString(dto.getReportRoleRO().getDeadline())){
			if(DateTimeUtil.compare(dto.getDeadline(), dto.getReportRoleRO().getDeadline())<0){
				errors.rejectValue("deadline", "error.message.date.after", new String[]{"Reviewing Officer Deadline", "Overall Deadline"}, "Overall Deadline must equals or after Reviewing Officer Deadline.");
			}
		}
		*/
		
		/*if(dto.getReportRoleAO() != null && dto.getReportRoleAO().isRoleChecked()){
			this.checkOfficerField(p, dto.getReportRoleAO(), errors ,Constants.TITLE_AO);
		}
		
		if(dto.getReportRoleCO() != null && dto.getReportRoleCO().isRoleChecked()){
			this.checkOfficerField(p, dto.getReportRoleCO(), errors ,Constants.TITLE_CO);
		}
		
		if(dto.getReportRoleIO() != null && dto.getReportRoleIO().isRoleChecked()){
			this.checkOfficerField(p, dto.getReportRoleIO(), errors ,Constants.TITLE_IO);
		}
		
		if(dto.getReportRoleEO() != null && dto.getReportRoleEO().isRoleChecked()){
			this.checkOfficerField(p, dto.getReportRoleEO(), errors ,Constants.TITLE_EO);
		}
		
		if(dto.getReportRoleRO() != null && dto.getReportRoleRO().isRoleChecked()){
			this.checkOfficerField(p, dto.getReportRoleRO(), errors ,Constants.TITLE_RO);
		}*/
		
		/*
		this.checkReminderInterval(dto.getReportRoleAO(), errors, "AO");
		this.checkReminderInterval(dto.getReportRoleCO(), errors, "CO");
		this.checkReminderInterval(dto.getReportRoleIO(), errors, "IO");
		if(dto.getReportRoleRO()!=null){
			this.checkReminderInterval(dto.getReportRoleRO(), errors, "RO");
		}
		*/
		
		if (reportTemplate.equals(Constants.WORKFLOW_REPORT_TEMPLATE_MEMO)) {
		} else {
			if(dto.getReportRoleRO() == null || (dto.getReportRoleRO() != null && Util.isEmptyString(dto.getReportRoleRO().getEmployeeNum()))){
				errors.rejectValue("reportRoleRO.employeeNum","",new String[]{" Reviewing Officer employee number "}, "Reviewing Officer employee number is Mandatory");
			}
			if (reportTemplate.equals(Constants.WORKFLOW_REPORT_TEMPLATE_CONTRACT_STAFF)) {
				this.checkOfficerField(p,dto.getReportRoleRO(), errors ,Constants.TITLE_RO);
			} else if (reportTemplate.equals(Constants.WORKFLOW_REPORT_TEMPLATE_HOUSING_OFFICER)) {
				this.checkOfficerField(p, dto.getReportRoleEO(), errors ,Constants.TITLE_EO);
				this.checkOfficerField(p,dto.getReportRoleRO(), errors ,Constants.TITLE_RO);
			}
		}
		
		this.checkOfficerField(p, dto.getReportRoleAO(), errors ,Constants.TITLE_AO);
		this.checkOfficerField(p,dto.getReportRoleCO(), errors,Constants.TITLE_CO);
		this.checkOfficerField(p,dto.getReportRoleIO(), errors,Constants.TITLE_IO);
	}

private void checkOfficerField(Pattern p, ReportRoleDto reportRole, Errors errors,String title) {
	/*if(StringUtil.isEmptyString(reportRole.getEmployeeNum())){
		errors.rejectValue("reportRoleAO.employeeNum","error.message.empty.content", new String[]{title+" Employee Number "}, title+" Employee Number is required");
	}
	if(StringUtil.isEmptyString(reportRole.getEmployeeName())){
		errors.rejectValue("reportRoleAO.employeeName", "error.message.empty.content", new String[]{title+" Employee Name "}, title+" Employee Name is required");
	}*/
	if(StringUtil.isEmptyString(reportRole.getNotification())){
		errors.rejectValue("reportRoleAO.notification", "error.message.empty.content", new String[]{title+" Notification "}, title+" Notification is required");
	}
	if(StringUtil.isEmptyString(reportRole.getFirstReminder())){
		errors.rejectValue("reportRoleAO.firstReminder", "error.message.empty.content", new String[]{title+" First Reminder "}, title+" First Reminder is required");
	}
	if(StringUtil.isEmptyString(reportRole.getSecondReminder())){
		errors.rejectValue("reportRoleAO.secondReminder","error.message.empty.content", new String[]{title+" Second Reminder "},  title+" Second Reminder is required");
	}
	if(StringUtil.isEmptyString(reportRole.getThirdReminder())){
		errors.rejectValue("reportRoleAO.thirdReminder", "error.message.empty.content", new String[]{title+" Third Reminder "}, title+" Third Reminder is required");

	}
	if(StringUtil.isEmptyString(reportRole.getSubsReminder())){
		errors.rejectValue("reportRoleAO.subsReminder", "error.message.empty.content", new String[]{title+" Subsequent Reminder "}, title+" Subsequent Reminder is required");
	}
	/*if(!StringUtil.isEmptyString(reportRole.getDeadline())){
		Matcher m = p.matcher(reportRole.getDeadline());
		if(!m.matches()){
			errors.rejectValue("overallDeadline", "error.message.empty.content.date.format", new String[]{title+" Deadline "}, "format must be 'dd/MM/yyyy'");
			
		}
	}*/
	if(null!=reportRole.getFirstRemInterval()){
		if(null!=reportRole.getSecondRemInterval()){
			if(reportRole.getFirstRemInterval().compareTo(reportRole.getSecondRemInterval())>0){
				errors.rejectValue("reportRoleAO.secondRemInterval", "error.er0026", new String[]{title+" Second Reminder Interval"}, "Second Reminder Interval should be greater than First Reminder Interval.");				
			}
		}
		if(null!=reportRole.getThirdRemInterval()){
			if(reportRole.getFirstRemInterval().compareTo(reportRole.getThirdRemInterval())>0){
				errors.rejectValue("reportRoleAO.thirdRemInterval", "error.er0027", new String[]{title+" Third Reminder Interval"}, "Third Reminder Interval should be greater than First Reminder Interval.");				
			}
		}
	}
	if(null!=reportRole.getSecondRemInterval()){
		if(null!=reportRole.getThirdRemInterval()){
			if(reportRole.getSecondRemInterval().compareTo(reportRole.getThirdRemInterval())>0){
				errors.rejectValue("reportRoleAO.thirdRemInterval", "error.er0028", new String[]{title+" Third Reminder Interval"}, "Third Reminder Interval should be greater than Second Reminder Interval.");				
			}
		}
	}
	
}



//20161121 added by joanna check first reminder/ second reminder/ third reminder/ sub remider is not null
private void checkReminderInterval(ReportRoleDto reportRole, Errors errors,String tile){
	if(reportRole.getFirstRemInterval()==null){
		errors.rejectValue("reportRoleAO.firstRemInterval", "error.er0082", new String[]{tile," First"}, " - First reminder interval should not be empty.");
	}
	if(reportRole.getSecondRemInterval()==null){
		errors.rejectValue("reportRoleAO.secondRemInterval", "error.er0082", new String[]{tile, " Second"}, " - Second reminder interval should not be empty.");
	}
	if(reportRole.getThirdRemInterval()==null){
		errors.rejectValue("reportRoleAO.thirdRemInterval", "error.er0082", new String[]{tile, " Third"}, " - Third reminder interval should not be empty.");
	}
	if(reportRole.getSubsRemInterval()==null){
		errors.rejectValue("reportRoleAO.subsRemInterval", "error.er0082", new String[]{tile, " Subsequent"}, " - Subs reminder interval should not be empty.");
	}
}

//20170105 added by joanna
private void checkAPDateDifferent(String reportTemplate, AssignOfficerDto dto, Errors errors){
	String[] deadlinearr = new String[5];
	String[] deadlineRole = new String[5];
	deadlinearr[0] = dto.getAppraiseeDeadline();
	deadlinearr[1] = dto.getReportRoleAO().getDeadline();
	deadlinearr[2] = dto.getReportRoleCO().getDeadline();
	deadlinearr[3] = dto.getReportRoleIO().getDeadline();
	//20170105 added by joanna
	deadlineRole[0] = "Appraisee Deadline";
	deadlineRole[1] = "Appraising Officer Deadline";
	deadlineRole[2] = "Countersigning Officer Deadline";
	deadlineRole[3] = "Interviewing Officer Deadline";
	//20170105 ended by joanna
	int noOfTitle = 4;		
	if(Constants.WORKFLOW_REPORT_TEMPLATE_HOUSING_OFFICER.equals(reportTemplate)){
		deadlinearr[4] = dto.getReportRoleEO().getDeadline();
		deadlineRole[4] = "Endorsing Officer Deadline";
		noOfTitle++;
	}else if (Constants.WORKFLOW_REPORT_TEMPLATE_CONTRACT_STAFF.equalsIgnoreCase(reportTemplate)){
		if(!Util.isEmptyString(dto.getReportRoleRO().getDeadline())){
			deadlinearr[4] = dto.getReportRoleRO().getDeadline();
			deadlineRole[4] = "Reviewing Officer Deadline";
			noOfTitle++;
		}
	}
	
		
	for(int i=1;i<noOfTitle;i++){
		Date checkingDate = DateTimeUtil.string2Date(deadlinearr[0]);
		Date currentDate = DateTimeUtil.string2Date(deadlinearr[i]); 
		if(DateTimeUtil.compare(checkingDate, currentDate)>0){
			errors.rejectValue("deadline", "error.message.date.after", new String[]{deadlineRole[0], deadlineRole[i]}, "Overall Deadline must equals or after Reviewing Officer Deadline.");
		}
	}
}
//20170105 ended by joanna

//20170105 added by joanna
private void checkOverallDateDifferent(String reportTemplate, AssignOfficerDto dto,Errors errors){
	String[] deadlinearr = new String[5];
	String[] deadlineRole = new String[5];
	deadlinearr[0] = dto.getDeadline();
	deadlinearr[1] = dto.getReportRoleAO().getDeadline();
	deadlinearr[2] = dto.getReportRoleCO().getDeadline();
	deadlinearr[3] = dto.getReportRoleIO().getDeadline();
	//20170105 added by joanna
	deadlineRole[0] = "Overall Deadline";
	deadlineRole[1] = "Appraising Officer Deadline";
	deadlineRole[2] = "Countersigning Officer Deadline";
	deadlineRole[3] = "Interviewing Officer Deadline";
	//20170105 ended by joanna
	int noOfTitle = 4;	
	if(Constants.WORKFLOW_REPORT_TEMPLATE_HOUSING_OFFICER.equals(reportTemplate)){
		deadlinearr[4] = dto.getReportRoleEO().getDeadline();
		deadlineRole[4] = "Endorsing Officer Deadline";
		noOfTitle++;
	}else if (Constants.WORKFLOW_REPORT_TEMPLATE_CONTRACT_STAFF.equalsIgnoreCase(reportTemplate)){
		if(!Util.isEmptyString(dto.getReportRoleRO().getDeadline())){
			deadlinearr[4] = dto.getReportRoleRO().getDeadline();
			deadlineRole[4] = "Reviewing Officer Deadline";
			noOfTitle++;
		}
	}
	
	log.info(reportTemplate);
	log.info(deadlinearr[4]+" "+deadlineRole[4]+" "+noOfTitle);
		
	for(int i=1;i<noOfTitle;i++){
		Date checkingDate = DateTimeUtil.string2Date(deadlinearr[0]);
		Date currentDate = DateTimeUtil.string2Date(deadlinearr[i]); 
		if(DateTimeUtil.compare(checkingDate, currentDate)<0){
			errors.rejectValue("deadline", "error.message.date.after", new String[]{deadlineRole[i], deadlineRole[0]}, "Overall Deadline must equals or after Reviewing Officer Deadline.");
		}
	}
}
//20170105 ended by joanna

private void checkDateDifferent(String reportTemplate, AssignOfficerDto dto,Errors errors) {
	String[] deadlinearr = new String[5];
	String[] deadlineRole = new String[5];
	deadlinearr[0] = dto.getReportRoleAO().getDeadline();
	deadlinearr[1] = dto.getReportRoleCO().getDeadline();
	deadlinearr[2] = dto.getReportRoleIO().getDeadline();
	int noOfTitle = 3;

	//20170105 added by joanna
	deadlineRole[0] = "Appraising Officer Deadline";
	deadlineRole[1] = "Countersigning Officer Deadline";
	deadlineRole[2] = "Interviewing Officer Deadline";
	//20170105 ended by joanna
	
	if(Constants.WORKFLOW_REPORT_TEMPLATE_HOUSING_OFFICER.equals(reportTemplate)){
		deadlinearr[3] = dto.getReportRoleEO().getDeadline();
		deadlineRole[3] = "Endorsing Officer Deadline";
		noOfTitle++;
	}else if (Constants.WORKFLOW_REPORT_TEMPLATE_CONTRACT_STAFF.equalsIgnoreCase(reportTemplate)){
		deadlinearr[3] = dto.getReportRoleRO().getDeadline();
		deadlineRole[3] = "Reviewing Officer Deadline";
		noOfTitle++;
	}
	
	for(int i=0; i<noOfTitle-1; i++){
		if(!Util.isEmptyString(deadlinearr[i])){
			Date checkingDate = DateTimeUtil.string2Date(deadlinearr[i]);
			for(int j=i+1; j<noOfTitle; j++){
				if(!Util.isEmptyString(deadlinearr[j])){
					Date currentDate = DateTimeUtil.string2Date(deadlinearr[j]);
					if(DateTimeUtil.compare(checkingDate, currentDate)>0)
						//errors.rejectValue("reportRoleAO.deadline", deadlinearr[i]+" deadline should not exceed "+deadlinearr[j]+" deadline.");
						//errors.rejectValue("", "error.er0073",  new String[]{deadlinearr[i],deadlinearr[j]}, "");
						errors.rejectValue("deadline", "error.message.date.after", new String[]{deadlineRole[i], deadlineRole[j]}, "Appraising Officer Deadline must equals or after Reviewing Officer Deadline.");
				}
			}
		}
	}
}

	public void checkSelectedAssignCaseExist(String selAssign,Errors errors) {
		if(!Util.isEmptyString(selAssign)){
			String[] s= selAssign.split(",");
			if(s!= null && s.length > 0){
				for(int i=0; i<s.length; i++){
					if(!Util.isEmptyString(s[i])){
						if("Y".equalsIgnoreCase(s[i])){
							errors.rejectValue("", "error."+Constants.ERROR_SELECTED_REPORT_ALREADY_ASSIGNED);
							break;
						}
					}
				}
			}
		}
		
	}
	
	public void checkReportStatusForMultipleSelection(String status,Errors errors) {
		if(!Util.isEmptyString(status)){
			String[] s = status.split(",");
			if(s!= null && s.length > 0){
				if(s.length <= 1){
					return;//errors.rejectValue("", "error."+Constants.ERROR_ASSIGN_ONE_RECORD_ONLY);
					
				}else{	
					for(int i=0; i<s.length; i++){
						if(!Util.isEmptyString(s[i])){
							if(!(Util.isEqual(s[i],Constants.STATUS_INITIAL) || Util.isEqual(s[i],Constants.STATUS_CD))){
								errors.rejectValue("", "error."+Constants.ERROR_ASSIGN_ONE_RECORD_ONLY);
							}
						}
					}
				}
			}
		}
		
	}
	
	public void checkSelectedReportID(String selBatch,Errors errors) {
		
		if(!Util.isEmptyString(selBatch)){
			String[] s = selBatch.split(",");
			if(s == null && s.length <= 0){
				errors.rejectValue("", "error."+Constants.ERROR_SELECT_ONE_APPRAISEE);
			}else{
				for(int i=0; i<s.length; i++){
					if(Util.isEmptyString(s[i])){
						errors.rejectValue("", "error."+Constants.ERROR_SELECT_ONE_APPRAISEE);
						break;
					}
				}
			}
		}else{
			errors.rejectValue("", "error."+Constants.ERROR_SELECT_ONE_APPRAISEE);
		}
		
	}

}
