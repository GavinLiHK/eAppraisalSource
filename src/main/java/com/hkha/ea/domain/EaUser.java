package com.hkha.ea.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA_HR_EA_USER")
public class EaUser extends EaBaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userId;
	
	private Long groupId;
	
	private String status;

	@Id
	@Column(name = "USER_ID", length = 10, nullable = false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "GROUP_ID", nullable = false)
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Column(name = "STATUS", length = 1, nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
