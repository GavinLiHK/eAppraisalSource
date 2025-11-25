package com.hkha.ea.domain;

import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class EaBaseEntity{
	
	private String createdBy;
	
	private Date creationDate;
	
	private String lastUpdatedBy;
	
	private Date lastUpdatedDate;
	
	@Basic
	@Column(name = "CREATED_BY", length = 100)
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Basic
	@Column(name = "CREATION_DATE")
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Basic
	@Column(name = "LAST_UPDATED_BY", length = 100)
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@Basic
	@Column(name = "LAST_UPDATE_DATE")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
}
