package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
@Entity
@Table(name = "HA_HR_EA_WORKFLOW")
@SequenceGenerator(name="workflowTemplateSeq",sequenceName="HA_HR_EA_SEQ_WORKFLOW_TEMPLATE",allocationSize=1,initialValue=1)
public class EaWorkflow extends EaBaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long workflowTemplateId ;
	
	private String  workflowTemplateName;
	
	private String reportTemplate;
	
	private Date overallDeadline;
	
	private Date appraiseeDeadline;
	
	@Id	
	@GeneratedValue(generator="workflowTemplateSeq")
	@Column(name = "WORKFLOW_TEMPLATE_ID", nullable = false,length = 22)
	public long getWorkflowTemplateId() {
		return workflowTemplateId;
	}
	
	
	public void setWorkflowTemplateId(long workflowTemplateId) {
		this.workflowTemplateId = workflowTemplateId;
	}
	
	@Column(name = "WORKFLOW_TEMPLATE_NAME", nullable = false,length = 40)
	
	public String getWorkflowTemplateName() {
		return workflowTemplateName;
	}

	public void setWorkflowTemplateName(String workflowTemplateName) {
		this.workflowTemplateName = workflowTemplateName;
	}
	
	@Column(name = "REPORT_TEMPLATE", nullable = false,length = 1)
	public String getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(String reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
	
	@Column(name = "OVERALL_DEADLINE", nullable = false)
	public Date getOverallDeadline() {
		return overallDeadline;
	}

	public void setOverallDeadline(Date overallDeadline) {
		this.overallDeadline = overallDeadline;
	}


	@Column(name = "APPRAISEE_DEADLINE")
	public Date getAppraiseeDeadline() {
		return appraiseeDeadline;
	}


	public void setAppraiseeDeadline(Date appraiseeDeadline) {
		this.appraiseeDeadline = appraiseeDeadline;
	}

}
