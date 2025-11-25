package com.hkha.ea.validator.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hkha.ea.common.StringUtil;
import com.hkha.ea.dto.security.UserEnquiryDto;

@Component( "userEnquireValidator" )
public class UserEnquireValidator implements Validator{

	public boolean supports(Class<?> arg0) {
		
		return false;
	}

	public void validate(Object arg0, Errors arg1) {
		
		
	}

	public void validateUserEnquire(UserEnquiryDto dto, Errors errors) {
		if(StringUtil.isEmptyString(dto.getUserId())){
			errors.rejectValue("userId", "error.message.empty.content", new String[]{"User ID"}, "User ID is required");
		}
		if(dto.getGroupId() == null){
			errors.rejectValue("groupName", "error.message.empty.content", new String[]{"Group Name"}, "Group Name is required");
		}
		if(StringUtil.isEmptyString(dto.getEnable())){
			errors.rejectValue("enable", "error.message.empty.content", new String[]{"Enable"}, "Enable is required");
		} 
	}
}
