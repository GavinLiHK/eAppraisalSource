package com.hkha.ea.dto.common;

import java.util.Date;

public abstract class EaBaseEntityDTO {
	
	private String createdBy;
	
	private Date creationDate;
	
	private String lastUpdatedBy;
	
	private Date lastUpdatedDate;
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
}
