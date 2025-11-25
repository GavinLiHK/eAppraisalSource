package com.hkha.ea.validator.security;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hkha.ea.common.StringUtil;
import com.hkha.ea.dto.security.UserGroupEnquiryDTO;

@Component( "userGroupValidator" )
public class UserGroupValidator implements Validator{

	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}

	public void validateUserGroup(UserGroupEnquiryDTO dto, Errors errors) {
		
		if(StringUtil.isEmptyString(dto.getUserGroupName())){
			errors.rejectValue("userGroupName", "error.message.empty.content", new String[]{"User Group Name"}, "User Group Name is required");
		} 
	}
}
