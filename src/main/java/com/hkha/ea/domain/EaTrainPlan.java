package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(com.hkha.ea.domain.EaTrainPlanPK.class)
@Table(name = "HA_HR_EA_TRAIN_PLAN")
public class EaTrainPlan implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7562809755295528352L;

	//@EmbeddedId
	//private EaTrainPlanPK id;
	@Id
	private Long reportId;
	@Id
	private Long seqNo;
	
	@Column(name = "DEVELOP_NEED", length = 150, nullable=false )
	private String developNeed;
	
	@Column(name = "ACTION_PLAN", length = 100)
	private String actionPlan;
	
	@Column(name = "REVIEW_DATE", length = 7)
	private Date reviewDate;
	
	@Column(name = "AUDIT_ENABLE_GM", length = 1)
	private String auditEnableGm;
	
	@Column(name = "AUDIT_ENABLE_AP", length = 1)
	private String auditEnableAp;
	
	@Column(name = "AUDIT_ENABLE_AO", length = 1)
	private String auditEnableAo;
	
	@Column(name = "AUDIT_ENABLE_CO", length = 1)
	private String auditEnableCo;
	
	@Column(name = "AUDIT_ENABLE_RO", length = 1)
	private String auditEnableRo;
	
	@Column(name = "AUDIT_ENABLE_EO", length = 1)
	private String auditEnableEo;
	
	@Column(name = "AUDIT_ENABLE_IO", length = 1)
	private String auditEnableIo;
	
	@Column(name = "LAST_UPDATED_BY", length = 100)
	private String lastUpdatedBy;
	
	@Column(name = "CREATED_BY", length = 100)
	private String createdBy;
	
	@Column(name = "LAST_UPDATE_DATE", length = 7)
	private Date lastUpdateDate;
	
	@Column(name = "CREATION_DATE", length = 7)
	private Date creationDate;
	
//	public EaTrainPlanPK getId() {
//		return id;
//	}
//	public void setId(EaTrainPlanPK id) {
//		this.id = id;
//	}
	public String getDevelopNeed() {
		return developNeed;
	}
	public void setDevelopNeed(String developNeed) {
		this.developNeed = developNeed;
	}
	public String getActionPlan() {
		return actionPlan;
	}
	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getAuditEnableGm() {
		return auditEnableGm;
	}
	public void setAuditEnableGm(String auditEnableGm) {
		this.auditEnableGm = auditEnableGm;
	}
	public String getAuditEnableAp() {
		return auditEnableAp;
	}
	public void setAuditEnableAp(String auditEnableAp) {
		this.auditEnableAp = auditEnableAp;
	}
	public String getAuditEnableAo() {
		return auditEnableAo;
	}
	public void setAuditEnableAo(String auditEnableAo) {
		this.auditEnableAo = auditEnableAo;
	}
	public String getAuditEnableCo() {
		return auditEnableCo;
	}
	public void setAuditEnableCo(String auditEnableCo) {
		this.auditEnableCo = auditEnableCo;
	}
	public String getAuditEnableRo() {
		return auditEnableRo;
	}
	public void setAuditEnableRo(String auditEnableRo) {
		this.auditEnableRo = auditEnableRo;
	}
	public String getAuditEnableEo() {
		return auditEnableEo;
	}
	public void setAuditEnableEo(String auditEnableEo) {
		this.auditEnableEo = auditEnableEo;
	}
	public String getAuditEnableIo() {
		return auditEnableIo;
	}
	public void setAuditEnableIo(String auditEnableIo) {
		this.auditEnableIo = auditEnableIo;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public Long getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
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
