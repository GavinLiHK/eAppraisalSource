package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(com.hkha.ea.domain.EaGroupFunctionPK.class)
@Table(name = "HA_HR_EA_GROUP_FUNCTION")
public class EaGroupFunction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 574645120767375907L;
	
	//@EmbeddedId
	//private EaGroupFunctionPK id;
	@Id
	private Long groupId;
	@Id
	private Long functionId;
	
	@Column(name = "ACCESS_RIGHT", length = 1)
	private String accessRight;
	
	@Column(name = "CREATED_BY", length = 100)
	private String createdBy;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATED_BY", length = 100)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

//	public EaGroupFunctionPK getId() {
//		return id;
//	}
//
//	public void setId(EaGroupFunctionPK id) {
//		this.id = id;
//	}

	public String getAccessRight() {
		return accessRight;
	}

	public void setAccessRight(String accessRight) {
		this.accessRight = accessRight;
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

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	
	
}
