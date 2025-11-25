package com.hkha.ea.validator.workflow;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.dto.workflow.WorkflowDetailDTO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoAO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoCO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoEO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoIO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoRO;
import com.hkha.ea.service.workflow.WorkflowTemplateMaintenanceService;


@Component( "workflowValidator" )
public class WorkflowValidator  implements Validator {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(WorkflowValidator.class.getName());
	//End 20231201 Write log in catalina.out
	
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}
	@Autowired
	private WorkflowTemplateMaintenanceService workflowTemplateMaintenanceService;
	
	
	private static  String el="(((0[1-9]|[12][0-9]|3[01])/((0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)/(0[469]|11))|(0[1-9]|[1][0-9]|2[0-8])/(02))/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}))|(29/02/(([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00)))";
	
	public void validateWorkflowDetailMandatory(WorkflowDetailDTO dto, Errors errors) {
		
		Pattern p = Pattern.compile(el);
		Date aoDeadLine=null;
		Date coDeadLine=null;
		Date ioDeadLine=null;
		
		if(StringUtil.isEmptyString(dto.getWorkflowTemplateName())){
			errors.rejectValue("workflowTemplateName", "error.message.empty.content", new String[]{"Workflow Template Name"}, "Workflow Name is required");
		}
		if(StringUtil.isEmptyString(dto.getReportTemplate())){
			errors.rejectValue("reportTemplate", "error.message.empty.content", new String[]{"Report Template Name"}, "Report Template Name value is required");
			
		}else{
			//check report template name can be update
			 Long wfId = dto.getWorkflowTemplateId();
			 if(null!=wfId &&0!=wfId){
				 EaWorkflow ewf=workflowTemplateMaintenanceService.searchFreshTemplateForWorkflowById(wfId);
				 if(null!=ewf){
					 if(!ewf.getReportTemplate().equals(dto.getReportTemplate())){
						 errors.rejectValue("","error.er0072",new String[]{},"");
					 }
				 }
			 }
			 errors=checkOfficerSelection(dto.getReportTemplate(),dto.getWorkflowRoleInfoAO().getRoleCheck(),dto.getWorkflowRoleInfoCO().getRoleCheck(),dto.getWorkflowRoleInfoIO().getRoleCheck(),dto.getWorkflowRoleInfoEO().getRoleCheck(),dto.getWorkflowRoleInfoRO().getRoleCheck(),errors);
		}
		
		if(StringUtil.isEmptyString(dto.getOverallDeadline())){
			errors.rejectValue("overallDeadline", "error.message.empty.content", new String[]{"Overall Deadline "}, "Overall Deadline is required");
		}else{
			Matcher m = p.matcher(dto.getOverallDeadline());
			if(!m.matches()){
				errors.rejectValue("overallDeadline", "error.message.empty.content.date.format", new String[]{"Overall Deadline "}, "format must be 'dd/MM/yyyy'");
			}
			//20161215 check Overall deadline equal or after reviewing officer deadline
			if(!StringUtil.isEmptyString(dto.getWorkflowRoleInfoRO().getDeadline())){
				if(DateTimeUtil.compare(dto.getOverallDeadline(), dto.getWorkflowRoleInfoRO().getDeadline())<0){
					errors.rejectValue("overallDeadline", "error.message.date.after", new String[]{"Reviewing Officer Deadline", "Overall Deadline"}, "Reviewing Officer Deadline must equals or after Overall Deadline.");
				}
			}
		}
		if("Y".equals(dto.getWorkflowRoleInfoAO().getRoleCheck())){
			errors=checkAOInfoMandatory(dto.getWorkflowRoleInfoAO(),p,aoDeadLine,errors);
		}
		if("Y".equals(dto.getWorkflowRoleInfoCO().getRoleCheck())){
			
			errors=checkCOInfoMandatory(dto.getWorkflowRoleInfoCO(),p,coDeadLine,errors);
		}
		if("Y".equals(dto.getWorkflowRoleInfoIO().getRoleCheck())){
			errors=checkIOInfoMandatory(dto.getWorkflowRoleInfoIO(),p,ioDeadLine,errors);			
		}
		if("Y".equals(dto.getWorkflowRoleInfoEO().getRoleCheck())){
			errors=checkEOInfoMandatory(dto.getWorkflowRoleInfoEO(),p,errors);			
		}
		if("Y".equals(dto.getWorkflowRoleInfoRO().getRoleCheck())){			
			errors=checkROInfoMandatory(dto.getWorkflowRoleInfoRO(),p,errors);				
		}
		
		errors=checkDateDifferent(dto,errors);
		
		
				
	}
	
	private Errors checkAOInfoMandatory(WorkflowRoleInfoAO aoInfo,Pattern p,Date aoDeadLine,Errors errors){
		
			if(StringUtil.isEmptyString(aoInfo.getFirstReminder())){
				errors.rejectValue("workflowRoleInfoAO.firstReminder", "error.message.empty.content", new String[]{"Appraising officer First Reminder"}, "Appraising officer First Reminder is required");
			}
			if(StringUtil.isEmptyString(aoInfo.getSecondReminder())){
				errors.rejectValue("workflowRoleInfoAO.secondReminder", "error.message.empty.content", new String[]{"Appraising officer Second Reminder"}, "Appraising officer Second Reminder is required");
			}
			if(StringUtil.isEmptyString(aoInfo.getThirdReminder())){
				errors.rejectValue("workflowRoleInfoAO.thirdReminder", "error.message.empty.content", new String[]{"Appraising officer Third Reminder"}, "Appraising officer Third Reminder is required");

			}
			if(StringUtil.isEmptyString(aoInfo.getSubsReminder())){
				errors.rejectValue("workflowRoleInfoAO.subsReminder", "error.message.empty.content", new String[]{"Appraising officer Subsequent Reminder"}, "Appraising officer Subsequent Reminder is required");
			}
			if(!StringUtil.isEmptyString(aoInfo.getDeadline())){
				Matcher m = p.matcher(aoInfo.getDeadline());
				if(!m.matches()){
					errors.rejectValue("overallDeadline", "error.message.empty.content.date.format", new String[]{"Appraising officer Deadline "}, "format must be 'dd/MM/yyyy'");	
				}
			}
			if(null!=aoInfo.getFirstRemInterval()){
				if(null!=aoInfo.getSecondRemInterval()){
					if(aoInfo.getFirstRemInterval().compareTo(aoInfo.getSecondRemInterval())>0){
						errors.rejectValue("workflowRoleInfoAO.secondRemInterval", "error.er0026", new String[]{"Appraising officer Second Reminder Interval"}, "Second Reminder Interval should be greater than First Reminder Interval.");				
					}
				}
				if(null!=aoInfo.getThirdRemInterval()){
					if(aoInfo.getFirstRemInterval().compareTo(aoInfo.getThirdRemInterval())>0){
						errors.rejectValue("workflowRoleInfoAO.thirdRemInterval", "error.er0027", new String[]{"Appraising officer Third Reminder Interval"}, "Third Reminder Interval should be greater than First Reminder Interval.");				
					}
				}
			}
			if(null!=aoInfo.getSecondRemInterval()){
				if(null!=aoInfo.getThirdRemInterval()){
					if(aoInfo.getSecondRemInterval().compareTo(aoInfo.getThirdRemInterval())>0){
						errors.rejectValue("workflowRoleInfoAO.thirdRemInterval", "error.er0028", new String[]{"Appraising officer Third Reminder Interval"}, "Third Reminder Interval should be greater than Second Reminder Interval.");				
					}
				}
			}
		return errors;
	}
	
	private Errors checkCOInfoMandatory(WorkflowRoleInfoCO coInfo,Pattern p,Date coDeadLine,Errors errors){
			
		if(StringUtil.isEmptyString(coInfo.getFirstReminder())){
			errors.rejectValue("workflowRoleInfoCO.firstReminder", "error.message.empty.content", new String[]{"Countersigning officer First Reminder"}, "Countersigning officer First Reminder is required");
		}
		if(StringUtil.isEmptyString(coInfo.getSecondReminder())){
			errors.rejectValue("workflowRoleInfoCO.secondReminder", "error.message.empty.content", new String[]{"Countersigning officer Second Reminder"}, "Countersigning officer Second Reminder is required");
		}
		if(StringUtil.isEmptyString(coInfo.getThirdReminder())){
			errors.rejectValue("workflowRoleInfoCO.thirdReminder", "error.message.empty.content", new String[]{"Countersigning officer Third Reminder"}, "Countersigning officer Third Reminder is required");

		}
		if(StringUtil.isEmptyString(coInfo.getSubsReminder())){
			errors.rejectValue("workflowRoleInfoCO.subsReminder", "error.message.empty.content", new String[]{"Countersigning officer Subsequent Reminder"}, "Countersigning officer Subsequent Reminder is required");
		}
		if(!StringUtil.isEmptyString(coInfo.getDeadline())){
			Matcher m = p.matcher(coInfo.getDeadline());
			if(!m.matches()){
				errors.rejectValue("overallDeadline", "error.message.empty.content.date.format", new String[]{"Countersigning officer Deadline "}, "format must be 'dd/MM/yyyy'");		
			}
		}
		if(null!=coInfo.getFirstRemInterval()){
			if(null!=coInfo.getSecondRemInterval()){
				if(coInfo.getFirstRemInterval().compareTo(coInfo.getSecondRemInterval())>0){
					errors.rejectValue("workflowRoleInfoCO.secondRemInterval", "error.er0026", new String[]{"Countersigning officer Second Reminder Interval"}, "Second Reminder Interval should be greater than First Reminder Interval.");				
				}else{
					coDeadLine=Util.string2Date(coInfo.getDeadline(), Constants.DISPLAY_DATE_FORMAT);
				}
			}
			if(null!=coInfo.getThirdRemInterval()){
				if(coInfo.getFirstRemInterval().compareTo(coInfo.getThirdRemInterval())>0){
					errors.rejectValue("workflowRoleInfoCO.thirdRemInterval", "error.er0027", new String[]{"Countersigning officer Third Reminder Interval"}, "Third Reminder Interval should be greater than First Reminder Interval.");				
				}
			}
		}
		if(null!=coInfo.getSecondRemInterval()){
			if(null!=coInfo.getThirdRemInterval()){
				if(coInfo.getSecondRemInterval().compareTo(coInfo.getThirdRemInterval())>0){
					errors.rejectValue("workflowRoleInfoCO.thirdRemInterval", "error.er0028", new String[]{"Countersigning officer Third Reminder Interval"}, "Third Reminder Interval should be greater than Second Reminder Interval.");				
				}
			}
		}
		return errors;
	}
	
	private Errors checkIOInfoMandatory(WorkflowRoleInfoIO ioInfo,Pattern p,Date ioDeadLine,Errors errors){
		
		if(StringUtil.isEmptyString(ioInfo.getFirstReminder())){
			errors.rejectValue("workflowRoleInfoIO.firstReminder", "error.message.empty.content", new String[]{"Interviewing officer First Reminder"}, "Interviewing officer First Reminder is required");
		}
		if(StringUtil.isEmptyString(ioInfo.getSecondReminder())){
			errors.rejectValue("workflowRoleInfoIO.secondReminder", "error.message.empty.content", new String[]{"Interviewing officer Second Reminder"}, "Interviewing officer Second Reminder is required");
		}
		if(StringUtil.isEmptyString(ioInfo.getThirdReminder())){
			errors.rejectValue("workflowRoleInfoIO.thirdReminder", "error.message.empty.content", new String[]{"Interviewing officer Third Reminder"}, "Interviewing officer Third Reminder is required");

		}
		if(StringUtil.isEmptyString(ioInfo.getSubsReminder())){
			errors.rejectValue("workflowRoleInfoIO.subsReminder", "error.message.empty.content", new String[]{"Interviewing officer Subsequent Reminder"}, "Interviewing officer Subsequent Reminder is required");
		}
		if(!StringUtil.isEmptyString(ioInfo.getDeadline())){
			Matcher m = p.matcher(ioInfo.getDeadline());
			if(!m.matches()){
				errors.rejectValue("overallDeadline", "error.message.empty.content.date.format", new String[]{"Interviewing officer Deadline "}, "format must be 'dd/MM/yyyy'");	
			}
		}
		if(null!=ioInfo.getFirstRemInterval()){
			if(null!=ioInfo.getSecondRemInterval()){
				if(ioInfo.getFirstRemInterval().compareTo(ioInfo.getSecondRemInterval())>0){
					errors.rejectValue("workflowRoleInfoIO.secondRemInterval", "error.er0026", new String[]{"Interviewing officer Second Reminder Interval"}, "Second Reminder Interval should be greater than First Reminder Interval.");				
				}
			}
			if(null!=ioInfo.getThirdRemInterval()){
				if(ioInfo.getFirstRemInterval().compareTo(ioInfo.getThirdRemInterval())>0){
					errors.rejectValue("workflowRoleInfoIO.thirdRemInterval", "error.er0027", new String[]{"Interviewing officer Third Reminder Interval"}, "Third Reminder Interval should be greater than First Reminder Interval.");				
				}
			}
		}
		if(null!=ioInfo.getSecondRemInterval()){
			if(null!=ioInfo.getThirdRemInterval()){
				if(ioInfo.getSecondRemInterval().compareTo(ioInfo.getThirdRemInterval())>0){
					errors.rejectValue("workflowRoleInfoIO.thirdRemInterval", "error.er0028", new String[]{"Interviewing officer Third Reminder Interval"}, "Third Reminder Interval should be greater than Second Reminder Interval.");				
				}
			}
		}
		return errors;
	}
	
	private Errors checkEOInfoMandatory(WorkflowRoleInfoEO eoInfo,Pattern p,Errors errors){
		if(StringUtil.isEmptyString(eoInfo.getFirstReminder())){
			errors.rejectValue("workflowRoleInfoEO.firstReminder", "error.message.empty.content", new String[]{"Endorsing officer First Reminder"}, "Endorsing officer First Reminder is required");
		}
		if(StringUtil.isEmptyString(eoInfo.getSecondReminder())){
			errors.rejectValue("workflowRoleInfoEO.secondReminder", "error.message.empty.content", new String[]{"Endorsing officer Second Reminder"}, "Endorsing officer Second Reminder is required");
		}
		if(StringUtil.isEmptyString(eoInfo.getThirdReminder())){
			errors.rejectValue("workflowRoleInfoEO.thirdReminder", "error.message.empty.content", new String[]{"Endorsing officer Third Reminder"}, "Endorsing officer Third Reminder is required");

		}
		if(StringUtil.isEmptyString(eoInfo.getSubsReminder())){
			errors.rejectValue("workflowRoleInfoEO.subsReminder", "error.message.empty.content", new String[]{"Endorsing officer Subsequent Reminder"}, "Endorsing officer Subsequent Reminder is required");
		}
		if(!StringUtil.isEmptyString(eoInfo.getDeadline())){
			Matcher m = p.matcher(eoInfo.getDeadline());
			if(!m.matches()){
				errors.rejectValue("overallDeadline", "error.message.empty.content.date.format", new String[]{"Endorsing officer Deadline "}, "format must be 'dd/MM/yyyy'");
				
			}
		}
		if(null!=eoInfo.getFirstRemInterval()){
			if(null!=eoInfo.getSecondRemInterval()){
				if(eoInfo.getFirstRemInterval().compareTo(eoInfo.getSecondRemInterval())>0){
					errors.rejectValue("workflowRoleInfoEO.secondRemInterval", "error.er0026", new String[]{"Endorsing officer Second Reminder Interval"}, "Second Reminder Interval should be greater than First Reminder Interval.");				
				}
			}
			if(null!=eoInfo.getThirdRemInterval()){
				if(eoInfo.getFirstRemInterval().compareTo(eoInfo.getThirdRemInterval())>0){
					errors.rejectValue("workflowRoleInfoEO.thirdRemInterval", "error.er0027", new String[]{"Endorsing officer Third Reminder Interval"}, "Third Reminder Interval should be greater than First Reminder Interval.");				
				}
			}
		}
		if(null!=eoInfo.getSecondRemInterval()){
			if(null!=eoInfo.getThirdRemInterval()){
				if(eoInfo.getSecondRemInterval().compareTo(eoInfo.getThirdRemInterval())>0){
					errors.rejectValue("workflowRoleInfoEO.thirdRemInterval", "error.er0028", new String[]{"Endorsing officer Third Reminder Interval"}, "Third Reminder Interval should be greater than Second Reminder Interval.");				
				}
			}
		}
		return errors;
	}
	
	private Errors checkROInfoMandatory(WorkflowRoleInfoRO roInfo,Pattern p,Errors errors){
		if(StringUtil.isEmptyString(roInfo.getFirstReminder())){
			errors.rejectValue("workflowRoleInfoRO.firstReminder", "error.message.empty.content", new String[]{"Reviewing officer First Reminder"}, "Reviewing officer First Reminder is required");
		}
		if(StringUtil.isEmptyString(roInfo.getSecondReminder())){
			errors.rejectValue("workflowRoleInfoRO.secondReminder", "error.message.empty.content", new String[]{"Reviewing officer Second Reminder"}, "Reviewing officer Second Reminder is required");
		}
		if(StringUtil.isEmptyString(roInfo.getThirdReminder())){
			errors.rejectValue("workflowRoleInfoAO.thirdReminder", "error.message.empty.content", new String[]{"Reviewing officer Third Reminder"}, "Reviewing officer Third Reminder is required");

		}
		if(StringUtil.isEmptyString(roInfo.getSubsReminder())){
			errors.rejectValue("workflowRoleInfoRO.subsReminder", "error.message.empty.content", new String[]{"Reviewing officer Subsequent Reminder"}, "Reviewing officer Subsequent Reminder is required");
		}
		if(!StringUtil.isEmptyString(roInfo.getDeadline())){
			Matcher m = p.matcher(roInfo.getDeadline());
			if(!m.matches()){
				errors.rejectValue("overallDeadline", "error.message.empty.content.date.format", new String[]{"Reviewing officer Deadline "}, "format must be 'dd/MM/yyyy'");					
			}
		}
		if(null!=roInfo.getFirstRemInterval()){
			if(null!=roInfo.getSecondRemInterval()){
				if(roInfo.getFirstRemInterval().compareTo(roInfo.getSecondRemInterval())>0){
					errors.rejectValue("workflowRoleInfoRO.secondRemInterval", "error.er0026", new String[]{"Reviewing officer Second Reminder Interval"}, "Second Reminder Interval should be greater than First Reminder Interval.");				
				}
			}
			if(null!=roInfo.getThirdRemInterval()){
				if(roInfo.getFirstRemInterval().compareTo(roInfo.getThirdRemInterval())>0){
					errors.rejectValue("workflowRoleInfoRO.thirdRemInterval", "error.er0027", new String[]{"Reviewing officer Third Reminder Interval"}, "Third Reminder Interval should be greater than First Reminder Interval.");				
				}
			}
		}
		if(null!=roInfo.getSecondRemInterval()){
			if(null!=roInfo.getThirdRemInterval()){
				if(roInfo.getSecondRemInterval().compareTo(roInfo.getThirdRemInterval())>0){
					errors.rejectValue("workflowRoleInfoRO.thirdRemInterval", "error.er0028", new String[]{"Reviewing officer Third Reminder Interval"}, "Third Reminder Interval should be greater than Second Reminder Interval.");				
				}
			}
		}
		return errors;
	}
	
	private Errors checkOfficerSelection(String reportTemplate, String chkAO, String chkCO, String chkIO, String chkEO, String chkRO,Errors errors){
		if ("H".equals(reportTemplate)) {
			if (null==chkAO || null==chkCO || null==chkIO || null==chkEO|| null==chkRO){			
				errors.rejectValue("", "er0024", new String[]{}, "Appraising Officer, Countersigning Officer, Interviewing Officer, Endorsing Officer and Reviewing Officer should be checked in Contract Housing Officer Template.");
			}
		}else if ("C".equals(reportTemplate)) {
			if (null==chkAO || null==chkCO  || null==chkIO || null==chkRO){// )||("Y".equals(chkEO)) ){
				errors.rejectValue("", "er0023", new String[]{}, "Appraising Officer, Countersigning Officer, Interviewing Officer and Reviewing Officer should be checked in Contract Staff Template. ");
			}
		} else if ("M".equals(reportTemplate)){				
			if(null==chkAO ||null==chkCO || null==chkIO){//|| ("Y".equals(chkEO)) || ("Y".equals(chkRO))) {
				errors.rejectValue("", "er0025", new String[]{}, "Appraising Officer, Countersigning Officer and Interviewing Officer should be checked in Memo Template.");
			}
	   }
		return errors;

	  }
	
	private Errors checkDateDifferent(WorkflowDetailDTO dto,Errors errors){
		String[] tempTitle = new String[5];
		String[] tempTextBox = new String[5];
		int noOfTitle = 3;
		tempTextBox[0] = dto.getWorkflowRoleInfoAO().getDeadline();
		tempTextBox[1] = dto.getWorkflowRoleInfoCO().getDeadline();
		tempTextBox[2] = dto.getWorkflowRoleInfoIO().getDeadline();
		tempTitle[0] = "Appraising Officer";
		tempTitle[1] = "Countersigning Officer";
		tempTitle[2] = "Interviewing Officer";
		
		if ("H".equals(dto.getReportTemplate())) {
			tempTextBox[noOfTitle] = dto.getWorkflowRoleInfoEO().getDeadline();
			tempTitle[(noOfTitle++)] = "Endorsing Officer";
		}
		
		for (int i = 0; i < noOfTitle - 1; i++) {
			if (!StringUtil.isEmptyString(tempTextBox[i])) {
				Date checkingDate = DateTimeUtil.string2Date(tempTextBox[i]);
				for (int j = i + 1; j < noOfTitle; j++) {
					if (!StringUtil.isEmptyString(tempTextBox[j])) {
						Date currentDate = DateTimeUtil.string2Date(tempTextBox[j]);
						if (DateTimeUtil.compare(checkingDate, currentDate) > 0) {
							errors.rejectValue("", "error.er0073",  new String[]{tempTitle[i],tempTitle[j]}, "");
						}
					}
				}
			}
		}
	
		return errors;
	}
}
