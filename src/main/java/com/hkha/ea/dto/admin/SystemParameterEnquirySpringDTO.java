package com.hkha.ea.dto.admin;



public class SystemParameterEnquirySpringDTO {

	private String paramName;
	
	private String paramDesc;

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	@Override
	public String toString() {
		return "SystemParameterEnquirySpringDTO [paramName=" + paramName + ", paramDesc=" + paramDesc + "]";
	}
}
