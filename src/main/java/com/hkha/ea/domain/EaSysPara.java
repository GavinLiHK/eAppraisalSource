package com.hkha.ea.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA_HR_EA_SYS_PARA")
public class EaSysPara extends EaBaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	private String paraName;
	
	private String paraValue;
	
	private String paraDesc;
	
	@Id
	@Column(name = "PARA_NAME", nullable = false)
	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	
	@Column(name = "PARA_VALUE", nullable = false)
	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	
	@Column(name = "PARA_DESC",  nullable = false)
	public String getParaDesc() {
		return paraDesc;
	}

	public void setParaDesc(String paraDesc) {
		this.paraDesc = paraDesc;
	}
	
	
}
