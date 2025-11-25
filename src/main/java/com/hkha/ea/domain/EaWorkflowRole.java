package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
@Entity
@IdClass(com.hkha.ea.domain.EaWorkflowRolePK.class)
@Table(name = "HA_HR_EA_WORKFLOW_ROLE")
public class EaWorkflowRole implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//@EmbeddedId
	//private EaWorkflowRolePK id;
	@Id
	private Long workflowTemplateId;
	@Id
	private String role;
	
	@Column(name = "ROLE_SEQUENCE", nullable = false,length = 22)
	private long roleSequence;
	
	@Column(name = "DEADLINE")
	private Date deadline;
	
	@Column(name = "NOTIFICATION", length = 1000)
	private String notification;
	
	@Column(name = "FIRST_REMINDER", length = 1000,nullable = false)
	private String firstReminder;
	
	@Column(name = "FIRST_REM_INTERVAL",length = 22)
	private Integer firstRemInterval;
	
	@Column(name = "SECOND_REMINDER", nullable = false,length = 1000)
	private String secondReminder;
	
	@Column(name = "SECOND_REM_INTERVAL", length = 22)
	private Integer secondRemInterval;
	
	@Column(name = "THIRD_REMINDER",length = 1000,nullable = false)
	private String thirdReminder;
	
	@Column(name = "THIRD_REM_INTERVAL",length = 22)
	private Integer thirdRemInterval;

	@Column(name = "SUBS_REMINDER",length = 1000,nullable = false)
	private String subsReminder;
	
	@Column(name = "SUBS_REM_INTERVAL",length = 22)
	private Integer subsRemInterval;
	
	@Column(name = "CREATED_BY", length = 100)
	private String createdBy;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATED_BY", length = 100)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	
//	public EaWorkflowRolePK getId() {
//		return id;
//	}
//
//	public void setId(EaWorkflowRolePK id) {
//		this.id = id;
//	}

	public long getRoleSequence() {
		return roleSequence;
	}

	public void setRoleSequence(long roleSequence) {
		this.roleSequence = roleSequence;
	}
	
	
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	
	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	
	public String getFirstReminder() {
		return firstReminder;
	}

	public void setFirstReminder(String firstReminder) {
		this.firstReminder = firstReminder;
	}
	
	
	public Integer getFirstRemInterval() {
		return firstRemInterval;
	}

	public void setFirstRemInterval(Integer firstRemInterval) {
		this.firstRemInterval = firstRemInterval;
	}

	
	public String getSecondReminder() {
		return secondReminder;
	}

	public void setSecondReminder(String secondReminder) {
		this.secondReminder = secondReminder;
	}

	
	public Integer getSecondRemInterval() {
		return secondRemInterval;
	}

	public void setSecondRemInterval(Integer secondRemInterval) {
		this.secondRemInterval = secondRemInterval;
	}

	
	public String getThirdReminder() {
		return thirdReminder;
	}

	public void setThirdReminder(String thirdReminder) {
		this.thirdReminder = thirdReminder;
	}

	
	public Integer getThirdRemInterval() {
		return thirdRemInterval;
	}

	public void setThirdRemInterval(Integer thirdRemInterval) {
		this.thirdRemInterval = thirdRemInterval;
	}
	
	
	public String getSubsReminder() {
		return subsReminder;
	}

	public void setSubsReminder(String subsReminder) {
		this.subsReminder = subsReminder;
	}
	
	
	public Integer getSubsRemInterval() {
		return subsRemInterval;
	}

	public void setSubsRemInterval(Integer subsRemInterval) {
		this.subsRemInterval = subsRemInterval;
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

	public Long getWorkflowTemplateId() {
		return workflowTemplateId;
	}

	public void setWorkflowTemplateId(Long workflowTemplateId) {
		this.workflowTemplateId = workflowTemplateId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
