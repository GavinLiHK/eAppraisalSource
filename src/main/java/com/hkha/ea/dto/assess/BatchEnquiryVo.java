package com.hkha.ea.dto.assess;

import java.util.Date;

public class BatchEnquiryVo {

	private String id;
	private long reportId;
	private String batchName;
	private String name;
	private String subRank;
	private String employeeNum;
	private String employeeName;
	private String rank;
	private String postUnit;
	private String postTitle;
	
	private int totalAppraisees;
	private int noOfAppDispatched;
	
	private int numAssigned;
	private int numUnassigned;
	private int numAppraisee;
	private String assigned;
	private String status;
	
	//check page is EA016 or EA017
	private boolean isEA016;
	private String commenceDate;
	private String endDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public int getTotalAppraisees() {
		return totalAppraisees;
	}
	public void setTotalAppraisees(int totalAppraisees) {
		this.totalAppraisees = totalAppraisees;
	}
	public int getNoOfAppDispatched() {
		return noOfAppDispatched;
	}
	public void setNoOfAppDispatched(int noOfAppDispatched) {
		this.noOfAppDispatched = noOfAppDispatched;
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
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getEmployeeNum() {
		return employeeNum;
	}
	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getNumAssigned() {
		return numAssigned;
	}
	public void setNumAssigned(int numAssigned) {
		this.numAssigned = numAssigned;
	}
	public int getNumUnassigned() {
		return numUnassigned;
	}
	public void setNumUnassigned(int numUnassigned) {
		this.numUnassigned = numUnassigned;
	}
	public String getAssigned() {
		return assigned;
	}
	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getNumAppraisee() {
		return numAppraisee;
	}
	public void setNumAppraisee(int numAppraisee) {
		this.numAppraisee = numAppraisee;
	}
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubRank() {
		return subRank;
	}
	public void setSubRank(String subRank) {
		this.subRank = subRank;
	}
	public boolean isEA016() {
		return isEA016;
	}
	public void setEA016(boolean isEA016) {
		this.isEA016 = isEA016;
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
	
}
