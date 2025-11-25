package com.hkha.ea.dto.assess;

import java.util.Date;

import jakarta.persistence.Column;

import com.hkha.ea.dto.workflow.WorkflowRoleInfoAO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoCD;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoCO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoEO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoIO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoRO;

public class AssignOfficerDto {
	
	private String batchName;
	
	private String oldBatchName;
	
	private String deadline;
	
	private String appraiseeDeadline;
	
	private String employeeNum;
	
	private String employeeName;
	
	private String noftfication;
	
	private String reportTemplate;
	
	private String commenceDate;
	
	private String endDate;
	
	private boolean changeStatus;
	
	private ReportRoleDto reportRoleAO;
	
	private ReportRoleDto reportRoleIO;
	
	private ReportRoleDto reportRoleEO;
	
	private ReportRoleDto reportRoleRO;
	
	private ReportRoleDto reportRoleCO;
	
	private ReportRoleDto reportRoleCD;

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getOldBatchName() {
		return oldBatchName;
	}

	public void setOldBatchName(String oldBatchName) {
		this.oldBatchName = oldBatchName;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
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

	public String getNoftfication() {
		return noftfication;
	}

	public void setNoftfication(String noftfication) {
		this.noftfication = noftfication;
	}

	public String getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(String reportTemplate) {
		this.reportTemplate = reportTemplate;
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

	public ReportRoleDto getReportRoleAO() {
		return reportRoleAO;
	}

	public void setReportRoleAO(ReportRoleDto reportRoleAO) {
		this.reportRoleAO = reportRoleAO;
	}

	public ReportRoleDto getReportRoleIO() {
		return reportRoleIO;
	}

	public void setReportRoleIO(ReportRoleDto reportRoleIO) {
		this.reportRoleIO = reportRoleIO;
	}

	public ReportRoleDto getReportRoleEO() {
		return reportRoleEO;
	}

	public void setReportRoleEO(ReportRoleDto reportRoleEO) {
		this.reportRoleEO = reportRoleEO;
	}

	public ReportRoleDto getReportRoleRO() {
		return reportRoleRO;
	}

	public void setReportRoleRO(ReportRoleDto reportRoleRO) {
		this.reportRoleRO = reportRoleRO;
	}

	public ReportRoleDto getReportRoleCO() {
		return reportRoleCO;
	}

	public void setReportRoleCO(ReportRoleDto reportRoleCO) {
		this.reportRoleCO = reportRoleCO;
	}

	public ReportRoleDto getReportRoleCD() {
		return reportRoleCD;
	}

	public void setReportRoleCD(ReportRoleDto reportRoleCD) {
		this.reportRoleCD = reportRoleCD;
	}

	public String getAppraiseeDeadline() {
		return appraiseeDeadline;
	}

	public void setAppraiseeDeadline(String appraiseeDeadline) {
		this.appraiseeDeadline = appraiseeDeadline;
	}

	public boolean isChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(boolean changeStatus) {
		this.changeStatus = changeStatus;
	}
	
}
