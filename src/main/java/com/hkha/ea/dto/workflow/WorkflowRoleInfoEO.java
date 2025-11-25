package com.hkha.ea.dto.workflow;

public class WorkflowRoleInfoEO {
	
	private String role;	
	
	private String roleCheck;
	
	private String deadline;
		
	private String notification;
		
	private String firstReminder;
		
	private int firstRemInterval;
		
	private String secondReminder;
		
	private Integer secondRemInterval;
		
	private String thirdReminder;
		
	private Integer thirdRemInterval;

	private String subsReminder;
		
	private Integer subsRemInterval;
	
	//for assign officer by elina
	private String employeeNum;
		
	private String employeeName;
	
	private long roleSequence;
	//

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
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

	public String getRoleCheck() {
		return roleCheck;
	}

	public void setRoleCheck(String roleCheck) {
		this.roleCheck = roleCheck;
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

	public long getRoleSequence() {
		return roleSequence;
	}

	public void setRoleSequence(long roleSequence) {
		this.roleSequence = roleSequence;
	}

	public void setFirstRemInterval(int firstRemInterval) {
		this.firstRemInterval = firstRemInterval;
	}
	
	
}
