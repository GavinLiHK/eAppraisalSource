package com.hkha.ea.dto.assess;

import java.util.Date;

public class AppraisalReportRoleDTO {
	
	private Long reportId;
    
	private String employeeNumber;
    
	private Long workflowTemplateId;
    
	private Date commenceDate;
    
	private Date endDate;
    
	private String batchId;
    
	private String name;
    
	private String chineseName;
    
	private String firstAppointmentRank;
    
	private Date dateOfAppFirstRank;
    
	private String substantiveRank;
    
	private Date dateOfAppSubRank;
    
	private String presentPost;
    
	private String division;
    
	private String telephone;
    
	private Date overallDeadline;
    
	private String status;
    
	private String routingReason;
    
	private String hoId;
    
	private Long roleSequence;
    
	private String role;
    
	private String officerId;
    
	private Date deadline;
    
	private String notification;
    
	private String firstReminder;
    
	private Long firstRemInterval;
    
	private String secondReminder;
    
	private Long secondRemInterval;
    
	private String thirdReminder;
    
	private Long thirdRemInterval;
    
	private String subsReminder;
    
	private Long subsRemInterval;
    
	private String reminderSent;
	
	//new column appraisee deadline
	private Date appraiseeDeadline;

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Long getWorkflowTemplateId() {
		return workflowTemplateId;
	}

	public void setWorkflowTemplateId(Long workflowTemplateId) {
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

	public void setFirstAppointmentRank(String firstAppointmentRank) {
		this.firstAppointmentRank = firstAppointmentRank;
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

	public Long getRoleSequence() {
		return roleSequence;
	}

	public void setRoleSequence(Long roleSequence) {
		this.roleSequence = roleSequence;
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

	public Long getFirstRemInterval() {
		return firstRemInterval;
	}

	public void setFirstRemInterval(Long firstRemInterval) {
		this.firstRemInterval = firstRemInterval;
	}

	public String getSecondReminder() {
		return secondReminder;
	}

	public void setSecondReminder(String secondReminder) {
		this.secondReminder = secondReminder;
	}

	public Long getSecondRemInterval() {
		return secondRemInterval;
	}

	public void setSecondRemInterval(Long secondRemInterval) {
		this.secondRemInterval = secondRemInterval;
	}

	public String getThirdReminder() {
		return thirdReminder;
	}

	public void setThirdReminder(String third_reminder) {
		this.thirdReminder = third_reminder;
	}

	public Long getThirdRemInterval() {
		return thirdRemInterval;
	}

	public void setThirdRemInterval(Long thirdRemInterval) {
		this.thirdRemInterval = thirdRemInterval;
	}

	public String getSubsReminder() {
		return subsReminder;
	}

	public void setSubsReminder(String subsReminder) {
		this.subsReminder = subsReminder;
	}

	public Long getSubsRemInterval() {
		return subsRemInterval;
	}

	public void setSubsRemInterval(Long subsRemInterval) {
		this.subsRemInterval = subsRemInterval;
	}

	public String getReminderSent() {
		return reminderSent;
	}

	public void setReminderSent(String reminderSent) {
		this.reminderSent = reminderSent;
	}
	
	public Date getAppraiseeDeadline(){
		return appraiseeDeadline;
	}
	
	public void setAppraiseeDeadline(Date appraiseeDeadline){
		this.appraiseeDeadline = appraiseeDeadline;
	}
	
}
