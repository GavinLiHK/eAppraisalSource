package com.hkha.ea.common.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hkha.ea.common.StringUtil;
import com.hkha.ea.dto.admin.SystemParameterDTO;

@Component( "generalValidator" )
public class GeneralValidator implements Validator  {

	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validateSystemParam(SystemParameterDTO dto, Errors errors) {
		if(StringUtil.isEmptyString(dto.getParamName())){
			errors.rejectValue("paramName", "error.message.empty.content", new String[]{"Parameter name"}, "Parameter name is required");
		}
		if(StringUtil.isEmptyString(dto.getParamValue())){
			errors.rejectValue("paramValue", "error.message.empty.content", new String[]{"Parameter value"}, "Parameter value is required");
		}
		if(StringUtil.isEmptyString(dto.getParamDesc())){
			errors.rejectValue("paramDesc", "error.message.empty.content", new String[]{"Parameter description"}, "Parameter description is required");
		} 
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
	}

}
