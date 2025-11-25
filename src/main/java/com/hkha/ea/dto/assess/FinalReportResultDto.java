package com.hkha.ea.dto.assess;

import java.util.Date;

public class FinalReportResultDto {

	private String reportId;
	private String employeeNumber;
	private String workflowTemplateId;
	private String commenceDate;
	private Date commenceDateSort;
	private String endDate;
	private String name;
	private String substantiveRank;
	private String presentPost;
	private String status;
	private String reportTemplate;
	private String userId;
	private String groupId;
	private String rank;
	
	//EA0018
	private String batchName;
	
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getWorkflowTemplateId() {
		return workflowTemplateId;
	}
	public void setWorkflowTemplateId(String workflowTemplateId) {
		this.workflowTemplateId = workflowTemplateId;
	}
	public String getCommenceDate() {
		return commenceDate;
	}
	public void setCommenceDate(String commenceDate) {
		this.commenceDate = commenceDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubstantiveRank() {
		return substantiveRank;
	}
	public void setSubstantiveRank(String substantiveRank) {
		this.substantiveRank = substantiveRank;
	}
	public String getPresentPost() {
		return presentPost;
	}
	public void setPresentPost(String presentPost) {
		this.presentPost = presentPost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReportTemplate() {
		return reportTemplate;
	}
	public void setReportTemplate(String reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public Date getCommenceDateSort() {
		return commenceDateSort;
	}
	public void setCommenceDateSort(Date commenceDateSort) {
		this.commenceDateSort = commenceDateSort;
	}
	
}
