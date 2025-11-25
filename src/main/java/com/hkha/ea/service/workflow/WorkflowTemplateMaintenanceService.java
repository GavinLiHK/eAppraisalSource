package com.hkha.ea.service.workflow;

import java.util.List;

import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.domain.EaWorkflowRole;
import com.hkha.ea.dto.workflow.WorkflowDetailDTO;
import com.hkha.ea.dto.workflow.WorkflowEnquiryDTO;

public interface WorkflowTemplateMaintenanceService {
	public List<WorkflowEnquiryDTO> workflowTemplateSearch(WorkflowEnquiryDTO workflowEnquiryDTO);
	public WorkflowDetailDTO searchWorkflowRoleInfoDetail(String workflowId);
	public String saveWorkflowRoleInfoDetail(WorkflowDetailDTO dto);
	public EaWorkflow searchById(long wfId);
	public List<EaWorkflowRole> searchByWorkflowAndRole(long wfId, String object);
	public EaWorkflow searchFreshTemplateForWorkflowById(Long wfId);
}
