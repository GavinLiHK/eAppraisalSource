package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA_HR_EA_BATCH")
public class EaBatch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5105726674872225285L;
	@Id
	@Column(name = "BATCH_ID", nullable = false,length = 30)
	private String batchId;
	@Column(name = "LAST_UPDATED_BY", length = 100)
	private String lastUpdatedBy;
	@Column(name = "LAST_UPDATE_DATE", length = 7)
	private Date lastUpdateDate;
	@Column(name = "CREATED_BY", length = 100)
	private String createdBy;
	@Column(name = "CREATION_DATE", length = 7)
	private Date creationDate;
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
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
	
}
