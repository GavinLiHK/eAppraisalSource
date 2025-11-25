package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA_HR_EA_USER_GROUP")
@SequenceGenerator(name="userGroupSeq",sequenceName="HA_HR_EA_USER_GROUP_ID_SEQ",allocationSize=1,initialValue=1)
public class EaUserGrp extends EaBaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7033761003400230841L;
	private Long groupId;
	private String groupName;
	
	@Id
	@GeneratedValue(generator="userGroupSeq")
	@Column(name = "GROUP_ID", unique = true, nullable = false, length = 22)
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	@Column(name = "GROUP_NAME", nullable = false,length = 30)
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
