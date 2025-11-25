package com.hkha.ea.validator.assess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;

@Component( "searchAppraiseeValidator" )
public class SearchAppraiseeValidator implements Validator{

	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}

	public void validateSearchAppraisee(SearchAppraiseeDto searchAppraiseeDto, Errors errors) {
		if(StringUtils.isBlank(searchAppraiseeDto.getYear())){
			//throw new EAException(Constants.ERROR_YEAR_REQUIRED);
			errors.rejectValue("year", "error.er0005", new String[]{"Year"}, "User Group Name is required");
		}
		
		if(searchAppraiseeDto.getEmployeeNumber() == null && StringUtils.isBlank(searchAppraiseeDto.getRank())){
			//throw new EAException(Constants.ERROR_EMPLOYEE_NUM_OR_RANK_REQUIRED);
			errors.rejectValue("rank", "error.er0003", new String[]{"Rank"}, "User Group Name is required");
		}
		
		if(StringUtils.isBlank(searchAppraiseeDto.getWfTemplateId())){
			//throw new EAException(Constants.ERROR_WORKFLOW_TEMPLATE_REQUIRED); 
			errors.rejectValue("wfTemplateId", "error.er0002", new String[]{"Workflow Template Name"}, "User Group Name is required");
		}
		
		
		
		if (StringUtils.isBlank(searchAppraiseeDto.getAppraisalPeriodStart()))
		    //throw new EAException(Constants.ERROR_APPRAISAL_PERIOD_START_REQ);
			errors.rejectValue("appraisalPeriodStart", "error.er0061", new String[]{"Appraisal Period Start"}, "User Group Name is required");
		
		if (StringUtils.isBlank(searchAppraiseeDto.getAppraisalPeriodEnd()))
		    //throw new EAException(Constants.ERROR_APPRAISAL_PERIOD_END_REQ);
			errors.rejectValue("appraisalPeriodEnd", "error.er0062", new String[]{"Appraisal Period end"}, "User Group Name is required");

		if (!StringUtils.isBlank(searchAppraiseeDto.getAppraisalPeriodEnd())) {
			Date endDate = DateTimeUtil.string2Date(searchAppraiseeDto.getAppraisalPeriodEnd());
			
			if(!StringUtils.isBlank(searchAppraiseeDto.getAppraisalPeriodStart())){
				Date startDate = DateTimeUtil.string2Date(searchAppraiseeDto.getAppraisalPeriodStart());
				 if (startDate != null && endDate.compareTo(startDate) < 0)
					    //throw new EAException(Constants.ERROR_APPR_END_MUST_EAF_START_D);
				    	errors.rejectValue("appraisalPeriodEnd", "error.er0063", new String[]{"Appraisal Period end"}, "Appraisal Period End must equals or after Appraisal Period Start.");
			}
		    if(!StringUtils.isBlank(searchAppraiseeDto.getTrackDate())){
		    	   Date tDate =  DateTimeUtil.string2Date(searchAppraiseeDto.getTrackDate());
				   
				    if (tDate != null && endDate.compareTo(tDate) < 0)
					    //throw new EAException(Constants.ERROR_APPR_END_MUST_EAF_TRACK_D);
				    	errors.rejectValue("appraisalPeriodEnd", "error.er0064", new String[]{"Appraisal Period end"}, "Appraisal Period End must equals or after Track Date.");
			}
		 }
	}

	public void checkBatchId(String batchid, BindingResult result) {
		if (Util.isEmptyString(batchid)) {
			result.rejectValue("batchId", "error."+Constants.ERROR_SELECT_ONE_APPRAISEE,new String[]{""},"");
		}
		String[] batchIdArr = batchid.split(",");
		List<String> batchIds = convertToListBatchId(batchIdArr);
		if (batchIds == null) {
			result.rejectValue("batchId", Constants.ERROR_SELECT_ONE_APPRAISEE);
		}
		
	}
	
	private List<String> convertToListBatchId(String[] batchIdArr) {
		List<String> list = null;
		if (batchIdArr != null && batchIdArr.length > 0) {
			list = new ArrayList<String>();
			for (int i = 0; i < batchIdArr.length; i++) {
				list.add(batchIdArr[i]);
			}
		}
		return list;
	}
}
