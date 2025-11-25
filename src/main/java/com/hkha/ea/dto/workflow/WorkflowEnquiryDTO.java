package com.hkha.ea.dto.workflow;

import java.util.Date;

public class WorkflowEnquiryDTO {

	private String workflowTemplateName;
	private String reportTemplate;
	private Long workflowId ;
	private Date overallDeadline;	
	private String appraoseeDeadline;
	
	public String getWorkflowTemplateName() {
		return workflowTemplateName;
	}
	public void setWorkflowTemplateName(String workflowTemplateName) {
		this.workflowTemplateName = workflowTemplateName;
	}
	public String getReportTemplate() {
		return reportTemplate;
	}
	public void setReportTemplate(String reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
	
	public Long getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}
	public Date getOverallDeadline() {
		return overallDeadline;
	}
	public void setOverallDeadline(Date overallDeadline) {
		this.overallDeadline = overallDeadline;
	}
	public String getAppraoseeDeadline() {
		return appraoseeDeadline;
	}
	public void setAppraoseeDeadline(String appraoseeDeadline) {
		this.appraoseeDeadline = appraoseeDeadline;
	}

	
	
}
