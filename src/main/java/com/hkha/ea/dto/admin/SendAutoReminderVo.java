package com.hkha.ea.dto.admin;

import java.util.Date;

public class SendAutoReminderVo {

	private long reportId;
	private String reportEmployeeNum;
	private String reportName;
	private Date commenceDate;
	private Date endDate;
	private String status;
	private String hoId;
	private String substantiveRank;
	private String presentPost;
	private String batchId;
	private Date appraiseeDeadline;
	private String role;
	private String officerId;
	private Date deadline;
	private String firstReminder;
	private int firstRemInt;
	private String secondReminder;
	private int secondRemInt;
	private String thirdReminder;
	private int thirdRemInt;
	private String subsReminder;
	private int subsRemInt;
	private String fullName;
	private String emailAddress;
	private String peopleEmployeeNum;
	private String engRank;
	private String primaryFlag;
	private String messageType;
	
	//for sendInitNotification
	private String officerRankType;
	private String officerName;
	private String message;
	private String messageFooter;
	private String rpNameAndEmplNum;
	private String firstName;
	private String lastName;
	//
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	public String getReportEmployeeNum() {
		return reportEmployeeNum;
	}
	public void setReportEmployeeNum(String reportEmployeeNum) {
		this.reportEmployeeNum = reportEmployeeNum;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHoId() {
		return hoId;
	}
	public void setHoId(String hoId) {
		this.hoId = hoId;
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
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public Date getAppraiseeDeadline() {
		return appraiseeDeadline;
	}
	public void setAppraiseeDeadline(Date appraiseeDeadline) {
		this.appraiseeDeadline = appraiseeDeadline;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOfficerId() {
		return officerId;
	}
	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getFirstReminder() {
		return firstReminder;
	}
	public void setFirstReminder(String firstReminder) {
		this.firstReminder = firstReminder;
	}
	public int getFirstRemInt() {
		return firstRemInt;
	}
	public void setFirstRemInt(int firstRemInt) {
		this.firstRemInt = firstRemInt;
	}
	public String getSecondReminder() {
		return secondReminder;
	}
	public void setSecondReminder(String secondReminder) {
		this.secondReminder = secondReminder;
	}
	public int getSecondRemInt() {
		return secondRemInt;
	}
	public void setSecondRemInt(int secondRemInt) {
		this.secondRemInt = secondRemInt;
	}
	public String getThirdReminder() {
		return thirdReminder;
	}
	public void setThirdReminder(String thirdReminder) {
		this.thirdReminder = thirdReminder;
	}
	public int getThirdRemInt() {
		return thirdRemInt;
	}
	public void setThirdRemInt(int thirdRemInt) {
		this.thirdRemInt = thirdRemInt;
	}
	public String getSubsReminder() {
		return subsReminder;
	}
	public void setSubsReminder(String subsReminder) {
		this.subsReminder = subsReminder;
	}
	public int getSubsRemInt() {
		return subsRemInt;
	}
	public void setSubsRemInt(int subsRemInt) {
		this.subsRemInt = subsRemInt;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPeopleEmployeeNum() {
		return peopleEmployeeNum;
	}
	public void setPeopleEmployeeNum(String peopleEmployeeNum) {
		this.peopleEmployeeNum = peopleEmployeeNum;
	}
	public String getEngRank() {
		return engRank;
	}
	public void setEngRank(String engRank) {
		this.engRank = engRank;
	}
	public String getPrimaryFlag() {
		return primaryFlag;
	}
	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getOfficerRankType() {
		return officerRankType;
	}
	public void setOfficerRankType(String officerRankType) {
		this.officerRankType = officerRankType;
	}
	public String getOfficerName() {
		return officerName;
	}
	public void setOfficerName(String officerName) {
		this.officerName = officerName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageFooter() {
		return messageFooter;
	}
	public void setMessageFooter(String messageFooter) {
		this.messageFooter = messageFooter;
	}
	public String getRpNameAndEmplNum() {
		return rpNameAndEmplNum;
	}
	public void setRpNameAndEmplNum(String rpNameAndEmplNum) {
		this.rpNameAndEmplNum = rpNameAndEmplNum;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
