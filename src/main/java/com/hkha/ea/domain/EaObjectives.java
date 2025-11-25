package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(com.hkha.ea.domain.EaObjectivesPK.class)
@Table(name = "HA_HR_EA_OBJECTIVES")
public class EaObjectives  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//@EmbeddedId
	//private EaObjectivesPK id;
	@Id
	private Long reportId;
	@Id
	private String type;
	@Id
	private Long seqNo;
	
	
	@Column(name = "KEY_OBJECTIVE", length = 450)
	private String keyObjective;
	
	@Column(name = "RESULTS_ACHIEVED", length = 1500)
	private String resultsAchieved;
	
	@Column(name = "WEIGHTING", length = 22)
	private Integer weighting;
	
	@Column(name = "RATING", length = 1)
	private String rating;
	
	@Column(name = "AUDIT_ENABLE_GM", length = 1)
	private String auditEnableGM; 
	
	@Column(name = "AUDIT_ENABLE_AP", length = 1)
	private String auditEnableAP; 
	
	@Column(name = "AUDIT_ENABLE_AO", length = 1)
	private String auditEnableAO; 
	
	@Column(name = "AUDIT_ENABLE_CO", length = 1)
	private String auditEnableCO; 
	
	@Column(name = "AUDIT_ENABLE_RO", length = 1)
	private String auditEnableRO; 
	
	@Column(name = "AUDIT_ENABLE_EO", length = 1)
	private String auditEnableEO; 
	
	@Column(name = "AUDIT_ENABLE_IO", length = 1)
	private String auditEnableIO; 
	
	@Column(name = "CREATED_BY", length = 100)
	private String createdBy;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	
	@Column(name = "LAST_UPDATED_BY", length = 100)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

//	public EaObjectivesPK getId() {
//		return id;
//	}
//
//	public void setId(EaObjectivesPK id) {
//		this.id = id;
//	}

	public String getKeyObjective() {
		return keyObjective;
	}

	public void setKeyObjective(String keyObjective) {
		this.keyObjective = keyObjective;
	}

	public String getResultsAchieved() {
		return resultsAchieved;
	}

	public void setResultsAchieved(String resultsAchieved) {
		this.resultsAchieved = resultsAchieved;
	}

	public Integer getWeighting() {
		return weighting;
	}

	public void setWeighting(Integer weighting) {
		this.weighting = weighting;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getAuditEnableGM() {
		return auditEnableGM;
	}

	public void setAuditEnableGM(String auditEnableGM) {
		this.auditEnableGM = auditEnableGM;
	}

	public String getAuditEnableAP() {
		return auditEnableAP;
	}

	public void setAuditEnableAP(String auditEnableAP) {
		this.auditEnableAP = auditEnableAP;
	}

	public String getAuditEnableAO() {
		return auditEnableAO;
	}

	public void setAuditEnableAO(String auditEnableAO) {
		this.auditEnableAO = auditEnableAO;
	}

	public String getAuditEnableCO() {
		return auditEnableCO;
	}

	public void setAuditEnableCO(String auditEnableCO) {
		this.auditEnableCO = auditEnableCO;
	}

	public String getAuditEnableRO() {
		return auditEnableRO;
	}

	public void setAuditEnableRO(String auditEnableRO) {
		this.auditEnableRO = auditEnableRO;
	}

	public String getAuditEnableEO() {
		return auditEnableEO;
	}

	public void setAuditEnableEO(String auditEnableEO) {
		this.auditEnableEO = auditEnableEO;
	}

	public String getAuditEnableIO() {
		return auditEnableIO;
	}

	public void setAuditEnableIO(String auditEnableIO) {
		this.auditEnableIO = auditEnableIO;
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

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}
	
	
}
