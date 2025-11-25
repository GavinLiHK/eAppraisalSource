package com.hkha.ea.helper;

import java.util.Date;

import org.springframework.validation.Errors;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.Util;

public class EaValidatorUtil {
	
	public static Errors checkMandatory(String str, Errors errors, String field, String message){
		
		if(StringUtil.isEmptyString(str)){
			errors.rejectValue(field, "error.message.empty.content", new String[]{message}, message + " is required.");
		}
	
		return errors;
	}
	
	public static Errors checkMustBeLater(Date a, Date b, Errors errors, String field, String message1, String message2){
		if (a != null && b != null) {
			if (a.compareTo(b) > 0) {
				errors.rejectValue(field, "error.message.date.after", new String[]{message1, message2}, message2 + " must equals or after " + message1 +".");
			}
		}
		return errors;
	}
	
	public static Errors checkMustBeValidDate(String str, Errors errors, String field, String message){
		return checkMustBeValidDate(str, Constants.DISPLAY_DATE_FORMAT, errors, field, message);
	}
	
	public static Errors checkMustBeValidDate(String str, String dateFormat, Errors errors, String field, String message){
		if(!StringUtil.isEmptyString(str)){
			if (!DateTimeUtil.isValidDateFormat(str, dateFormat)) {
				errors.rejectValue(field, "error.message.empty.content.date.format", new String[]{message}, message + " format must be 'dd/MM/yyyy'");
			}
		}
		return errors;
	}
	public static Errors checkMustBeSameDate(Date a, Date b, Errors errors, String field, String message1, String message2){
		if (a != null && b != null) {
			if (a.compareTo(b) != 0) {
				errors.rejectValue(field, "error.message.date.same", new String[]{message1, message2}, message1 + " and " + message2 + " should be the same date.");
			}
		}
		return errors;
	}
	
}
