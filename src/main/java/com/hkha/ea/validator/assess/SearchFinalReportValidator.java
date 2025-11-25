package com.hkha.ea.validator.assess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hkha.ea.dto.assess.SearchFinalReportDto;


@Component( "searchFinalReportValidator" )
public class SearchFinalReportValidator implements Validator{

	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}

	public void validateSearchFinalReport(SearchFinalReportDto searchFinalReportDto, Errors errors) {
		String el="(((0[1-9]|[12][0-9]|3[01])/((0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)/(0[469]|11))|(0[1-9]|[1][0-9]|2[0-8])/(02))/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}))|(29/02/(([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00)))";
		Pattern p = Pattern.compile(el); 
		
		if(StringUtils.isBlank(searchFinalReportDto.getCommenceStartDate())){
			errors.rejectValue("commenceStartDate", "error.message.empty.content",new String[]{"Commence Date Start period"}, "");
		}else{
			Matcher m = p.matcher(searchFinalReportDto.getCommenceStartDate());
			if(!m.matches()){
				errors.rejectValue("commenceStartDate", "error.message.empty.content.date.format", new String[]{"Commence Date Start period"}, "format must be 'dd/MM/yyyy'");
			}
		}
		
		if(StringUtils.isBlank(searchFinalReportDto.getCommenceEndDate())){
			errors.rejectValue("commenceEndDate", "error.message.empty.content",new String[]{"Commence Date End period"}, "");
		}else{
			Matcher m = p.matcher(searchFinalReportDto.getCommenceEndDate());
			if(!m.matches()){
				errors.rejectValue("commenceEndDate", "error.message.empty.content.date.format", new String[]{"Commence Date End period"}, "format must be 'dd/MM/yyyy'");
			}
		}
	}
}
