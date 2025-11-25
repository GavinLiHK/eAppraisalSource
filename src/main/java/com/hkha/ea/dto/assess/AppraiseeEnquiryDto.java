package com.hkha.ea.dto.assess;

public class AppraiseeEnquiryDto {

	private String personId;
	private String employeeNum;
	private String effStartDate;
	private String effEndDate;
	private String fullName;
	private String rank;
	private String postUnit;
	private Long reportId;
	private String batchId;
	private String reportGenerated;
	private String status;
	
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getEmployeeNum() {
		return employeeNum;
	}
	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}
	public String getEffStartDate() {
		return effStartDate;
	}
	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}
	public String getEffEndDate() {
		return effEndDate;
	}
	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getPostUnit() {
		return postUnit;
	}
	public void setPostUnit(String postUnit) {
		this.postUnit = postUnit;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getReportGenerated() {
		return reportGenerated;
	}
	public void setReportGenerated(String reportGenerated) {
		this.reportGenerated = reportGenerated;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
