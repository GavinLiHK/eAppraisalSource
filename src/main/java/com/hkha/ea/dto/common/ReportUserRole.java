package com.hkha.ea.dto.common;

public class ReportUserRole {
	
	private String currentReportStatus;
	
	private String currentOfficerId;
	
	private String gmId;
	
	private String currentUserRoleStatus;
	
	private String nextOfficerId;
	
	private String nextOfficerName;

	private String nextOfficerDeadline;
	
	private String nextOfficerRole;
	
	private boolean isGradeManagement = false;
	
	private boolean isSuperUser = false;
	
	public String getCurrentReportStatus() {
		return currentReportStatus;
	}

	public void setCurrentReportStatus(String currentReportStatus) {
		this.currentReportStatus = currentReportStatus;
	}

	public String getCurrentUserRoleStatus() {
		return currentUserRoleStatus;
	}

	public void setCurrentUserRoleStatus(String currentUserRoleStatus) {
		this.currentUserRoleStatus = currentUserRoleStatus;
	}

	public String getCurrentOfficerId() {
		return currentOfficerId;
	}

	public void setCurrentOfficerId(String currentOfficerId) {
		this.currentOfficerId = currentOfficerId;
	}

	public String getGmId() {
		return gmId;
	}

	public void setGmId(String gmId) {
		this.gmId = gmId;
	}

	public String getNextOfficerId() {
		return nextOfficerId;
	}

	public void setNextOfficerId(String nextOfficerId) {
		this.nextOfficerId = nextOfficerId;
	}

	public String getNextOfficerName() {
		return nextOfficerName;
	}

	public void setNextOfficerName(String nextOfficerName) {
		this.nextOfficerName = nextOfficerName;
	}

	public String getNextOfficerDeadline() {
		return nextOfficerDeadline;
	}

	public void setNextOfficerDeadline(String nextOfficerDeadline) {
		this.nextOfficerDeadline = nextOfficerDeadline;
	}

	public String getNextOfficerRole() {
		return nextOfficerRole;
	}

	public void setNextOfficerRole(String nextOfficerRole) {
		this.nextOfficerRole = nextOfficerRole;
	}

	public boolean isGradeManagement() {
		return isGradeManagement;
	}

	public void setGradeManagement(boolean isGradeManagement) {
		this.isGradeManagement = isGradeManagement;
	}

	public boolean isSuperUser() {
		return isSuperUser;
	}

	public void setSuperUser(boolean isSuperUser) {
		this.isSuperUser = isSuperUser;
	}
	

}
