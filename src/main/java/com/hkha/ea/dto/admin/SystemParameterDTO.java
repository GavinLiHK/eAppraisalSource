package com.hkha.ea.dto.admin;



public class SystemParameterDTO {

	private String paramName;
	
	private String paramDesc;
	
	private String paramValue;

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

	
	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Override
	public String toString() {
		return "SystemParameterDTO [paramName=" + paramName + ", paramDesc=" + paramDesc + ", paramValue="
				+ paramValue + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemParameterDTO other = (SystemParameterDTO) obj;
		if (this.getParamName() != null && this.getParamValue() != null && this.getParamDesc() != null) {
			if (this.getParamName().equals(other.getParamName()) && this.getParamValue().equals(other.getParamValue()) && this.getParamDesc().equals(other.getParamDesc()))
				return true;
		}
		return false;
	}
}
