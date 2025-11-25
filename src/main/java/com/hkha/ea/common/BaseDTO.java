package com.hkha.ea.common;

import java.util.Date;

public class BaseDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String createUserIndicator;

	private String createBy;

	private Date createDate;

	private String lastUpdateUserIndicator;

	private String lastUpdateBy;

	private Date lastUpdateDate;

	private String lastTransactionIndicator;
	
	private Integer optVersion;

	public Integer getOptVersion() {
		return optVersion;
	}

	public void setOptVersion(Integer optVersion) {
		this.optVersion = optVersion;
	}

	public String getCreateUserIndicator() {
		return createUserIndicator;
	}

	public void setCreateUserIndicator(String createUserIndicator) {
		this.createUserIndicator = createUserIndicator;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLastUpdateUserIndicator() {
		return lastUpdateUserIndicator;
	}

	public void setLastUpdateUserIndicator(String lastUpdateUserIndicator) {
		this.lastUpdateUserIndicator = lastUpdateUserIndicator;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}



	public String getLastTransactionIndicator() {
		return lastTransactionIndicator;
	}

	public void setLastTransactionIndicator(String lastTransactionIndicator) {
		this.lastTransactionIndicator = lastTransactionIndicator;
	}
}