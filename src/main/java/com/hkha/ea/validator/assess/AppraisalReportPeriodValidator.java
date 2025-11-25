package com.hkha.ea.validator.assess;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.ReportUserInfoOrPeriodDAO;

@Component( "appraisalReportPeriodValidator" )
public class AppraisalReportPeriodValidator {
	
	@Autowired
	private ReportUserInfoOrPeriodDAO reportUserInfoOrPeriodDAO;
	
	private final static String el="(((0[1-9]|[12][0-9]|3[01])/((0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)/(0[469]|11))|(0[1-9]|[1][0-9]|2[0-8])/(02))/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}))|(29/02/(([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00)))";

	public void checkAppraisalPeriodOverlappedWithExistingReport(String commenceDate,String endDate,String selectedEmployees,Errors errors){
		int count=reportUserInfoOrPeriodDAO.searchAppraisalPeriodOverlappedWithExistingReport(commenceDate,endDate,selectedEmployees);
		if(count>0){
			errors.rejectValue("", "error.er0074");
		}
	}
	
	public void checkAppraisalPeriodMandatory(String commenceDate,String endDate,Errors errors){
		Pattern p = Pattern.compile(el); 
		Date fromDate=null;
		Date toDate=null;
		if(StringUtil.isEmptyString(commenceDate)){
			errors.rejectValue("commenceDate", "error.message.empty.content", new String[]{"Appraisal Period Start"}, "Appraisal Period Start  is required.");
		}else{
			Matcher m = p.matcher(commenceDate);
			if(!m.matches()){
				errors.rejectValue("commenceDate", "error.message.empty.content.date.format", new String[]{"Appraisal Period Start Date "}, "format must be 'dd/MM/yyyy'");
			}else{
				fromDate=Util.string2Date(commenceDate,  Constants.DISPLAY_DATE_FORMAT);
			}
		}
		if(StringUtil.isEmptyString(endDate)){
			errors.rejectValue("commenceDate", "error.message.empty.content", new String[]{"Appraisal Period End"}, "Appraisal Period End  is required.");
		}else{
			Matcher m = p.matcher(endDate);
			if(!m.matches()){
				errors.rejectValue("endDate", "error.message.empty.content.date.format", new String[]{"Appraisal Period End Date "}, "format must be 'dd/MM/yyyy'");
			}else{
				toDate=Util.string2Date(endDate,  Constants.DISPLAY_DATE_FORMAT);
			}
		}
		
		if(null!=fromDate && null!=toDate){
			if(toDate.before(fromDate)){
				errors.rejectValue("endDate", "error.er0063", new String[]{"Appraisal Period End Date "}, "Appraisal Period End must equals or after Appraisal Period Start.");
			}
		}
	}


}
