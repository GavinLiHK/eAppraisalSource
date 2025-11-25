package com.hkha.ea.dto.assess;

import java.util.Date;

public class ReportNextRoleDto {

	private Long reportId;
	private String employeeNum;
	private Date commenceDate;
	private String status;
	private String assigned;
	private Long roleReportId;
	private Long roleSeq;
	private String role;
	private String lastUpdateby;
	private Date lastUpdateDate;
	private String createBy;
	private Date createDate;
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public String getEmployeeNum() {
		return employeeNum;
	}
	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}
	public Date getCommenceDate() {
		return commenceDate;
	}
	public void setCommenceDate(Date commenceDate) {
		this.commenceDate = commenceDate;
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
	public Long getRoleSeq() {
		return roleSeq;
	}
	public void setRoleSeq(Long roleSeq) {
		this.roleSeq = roleSeq;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLastUpdateby() {
		return lastUpdateby;
	}
	public void setLastUpdateby(String lastUpdateby) {
		this.lastUpdateby = lastUpdateby;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getRoleReportId() {
		return roleReportId;
	}
	public void setRoleReportId(Long roleReportId) {
		this.roleReportId = roleReportId;
	}
	
	
}
