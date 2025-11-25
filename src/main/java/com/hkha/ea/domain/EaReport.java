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
@Table(name = "HA_HR_EA_REPORT")
@SequenceGenerator(name="reportIdSeq",sequenceName="HA_HR_EA_SEQ_REPORT_ID",allocationSize=1,initialValue=1)
public class EaReport implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7677316037231821395L;

	@Id
	@GeneratedValue(generator="reportIdSeq")
	@Column(name = "REPORT_ID", unique = true,nullable = false,length = 22)
	private long reportId;
	
	@Column(name = "EMPLOYEE_NUMBER", nullable = false,length = 10)
	private String employeeNumber;
	
	@Column(name = "WORKFLOW_TEMPLATE_ID", nullable = false,length = 22)
	private long workflowTemplateId ;
	
	@Column(name="COMMENCE_DATE" ,nullable=false)
	private Date commenceDate;
	
	@Column(name="END_DATE" ,nullable=false)
	private Date endDate;
	
	@Column(name="TRACK_DATE" ,nullable=false)
	private Date trackDate;
	
	@Column(name = "BATCH_ID", nullable = false,length = 30)
	private String batchId;
	
	@Column(name = "NAME", length = 240)
	private String name;
	
	@Column(name = "CHINESE_NAME", length = 240)
	private String chineseName;
	
	@Column(name = "FIRST_APPOINTMENT_RANK", length = 40)
	private String firstAppointmentRank;
	
	@Column(name="DATE_OF_APP_FIRST_RANK" )
	private Date dateOfAppFirstRank;
	
	@Column(name = "SUBSTANTIVE_RANK", length = 40)
	private String substantiveRank;
	
	@Column(name="DATE_OF_APP_SUB_RANK" )
	private Date dateOfAppSubRank;
	
	@Column(name = "PRESENT_POST", length = 40)
	private String presentPost;
	
	@Column(name = "DIVISION", length = 40)
	private String division;
	
	@Column(name = "TELEPHONE", length = 20)
	private String telephone;
	
	@Column(name="OVERALL_DEADLINE" )
	private Date overallDeadline;
	
	@Column(name = "STATUS", length = 2)
	private String status; 
	
	@Column(name = "ASSIGNED", length = 1)
	private String assigned; 
	
	@Column(name = "ROUTING_REASON", length = 300)
	private String routingReason; 
	
	@Column(name = "HO_ID", length = 10)
	private String hoId; 
	
	@Column(name = "HO_NAME", length = 240)
	private String hoName; 
	
	@Column(name = "DATE_OF_POSTING")
	private Date dateOfPosting; 
	
	@Column(name = "CURRENT_CONTRACT_START_DATE")
	private Date currentContractStartDate; 
	
	@Column(name = "CURRENT_CONTRACT_END_DATE")
	private Date currentContractEndDate; 
	
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
	
	@Column(name = "COMPLETION_DATE_COMPARE_1", length = 10)
	private String completionDateCompare1; 
	
	@Column(name = "COMPLETION_DATE_COMPARE_2", length = 10)
	private String completionDateCompare2;
	
	@Column(name = "APPRAISEE_DEADLINE", length = 7)
	private Date appraiseeDeadline;
	
	@Column(name = "LAST_UPDATED_BY", length = 100)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE", length = 7)
	private Date lastUpdateDate;
	
	@Column(name = "CREATED_BY", length = 100)
	private String createdBy;
	
	@Column(name = "CREATION_DATE", length = 7)
	private Date creationDate;

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public long getWorkflowTemplateId() {
		return workflowTemplateId;
	}

	public void setWorkflowTemplateId(long workflowTemplateId) {
		this.workflowTemplateId = workflowTemplateId;
	}

	public Date getCommenceDate() {
		return commenceDate;
	}

	public void setCommenceDate(Date commenceDate) {
		this.commenceDate = commenceDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getTrackDate() {
		return trackDate;
	}

	public void setTrackDate(Date trackDate) {
		this.trackDate = trackDate;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getFirstAppointmentRank() {
		return firstAppointmentRank;
	}

	public void setFirstAppointmentRank(String firstAppontmentRank) {
		this.firstAppointmentRank = firstAppontmentRank;
	}

	public Date getDateOfAppFirstRank() {
		return dateOfAppFirstRank;
	}

	public void setDateOfAppFirstRank(Date dateOfAppFirstRank) {
		this.dateOfAppFirstRank = dateOfAppFirstRank;
	}

	public String getSubstantiveRank() {
		return substantiveRank;
	}

	public void setSubstantiveRank(String substantiveRank) {
		this.substantiveRank = substantiveRank;
	}

	public Date getDateOfAppSubRank() {
		return dateOfAppSubRank;
	}

	public void setDateOfAppSubRank(Date dateOfAppSubRank) {
		this.dateOfAppSubRank = dateOfAppSubRank;
	}

	public String getPresentPost() {
		return presentPost;
	}

	public void setPresentPost(String presentPost) {
		this.presentPost = presentPost;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getOverallDeadline() {
		return overallDeadline;
	}

	public void setOverallDeadline(Date overallDeadline) {
		this.overallDeadline = overallDeadline;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssigned() {
		return assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public String getRoutingReason() {
		return routingReason;
	}

	public void setRoutingReason(String routingReason) {
		this.routingReason = routingReason;
	}

	public String getHoId() {
		return hoId;
	}

	public void setHoId(String hoId) {
		this.hoId = hoId;
	}

	public String getHoName() {
		return hoName;
	}

	public void setHoName(String hoName) {
		this.hoName = hoName;
	}

	public Date getDateOfPosting() {
		return dateOfPosting;
	}

	public void setDateOfPosting(Date dateOfPosting) {
		this.dateOfPosting = dateOfPosting;
	}

	public Date getCurrentContractStartDate() {
		return currentContractStartDate;
	}

	public void setCurrentContractStartDate(Date currentContractStartDate) {
		this.currentContractStartDate = currentContractStartDate;
	}

	public Date getCurrentContractEndDate() {
		return currentContractEndDate;
	}

	public void setCurrentContractEndDate(Date currentContractEndDate) {
		this.currentContractEndDate = currentContractEndDate;
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

	public String getCompletionDateCompare1() {
		return completionDateCompare1;
	}

	public void setCompletionDateCompare1(String completionDateCompare1) {
		this.completionDateCompare1 = completionDateCompare1;
	}

	public String getCompletionDateCompare2() {
		return completionDateCompare2;
	}

	public void setCompletionDateCompare2(String completionDateCompare2) {
		this.completionDateCompare2 = completionDateCompare2;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getAppraiseeDeadline() {
		return appraiseeDeadline;
	}

	public void setAppraiseeDeadline(Date appraiseeDeadline) {
		this.appraiseeDeadline = appraiseeDeadline;
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



