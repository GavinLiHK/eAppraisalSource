package com.hkha.ea.dao.workflow;

import java.util.List;

import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.domain.EaWorkflowRole;
import com.hkha.ea.dto.workflow.WorkflowDetailDTO;
import com.hkha.ea.dto.workflow.WorkflowEnquiryDTO;

public interface WorkflowTemplateMaintenanceDAO {
	public List<WorkflowEnquiryDTO> search(WorkflowEnquiryDTO workflowEnquiryDTO);
	public WorkflowDetailDTO searchWorkflowRoleInfoDetail(long workflowTemplateId);
	public void addWorkflowRoleInfoDetail(WorkflowDetailDTO dto) throws Exception;
	public EaWorkflow searchWorkflowInfo(long workflowTemplateId);
	public void modifyWorkflowRoleInfoDetail(WorkflowDetailDTO dto,EaWorkflow ewf)throws Exception;
	//elina for 003
	public List<EaWorkflowRole> searchByWorkflowAndRole(long id,String role);
	//for workflow validator
	public EaWorkflow searchFreshTemplateForWorkflowById(Long wfId);
}
