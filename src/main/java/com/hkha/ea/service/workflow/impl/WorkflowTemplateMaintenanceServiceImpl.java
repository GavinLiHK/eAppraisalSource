package com.hkha.ea.service.workflow.impl;

import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.dao.workflow.WorkflowTemplateMaintenanceDAO;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.domain.EaWorkflowRole;
import com.hkha.ea.dto.workflow.WorkflowDetailDTO;
import com.hkha.ea.dto.workflow.WorkflowEnquiryDTO;
import com.hkha.ea.service.workflow.WorkflowTemplateMaintenanceService;

@Service("workflowTemplateMaintenanceService")
public class WorkflowTemplateMaintenanceServiceImpl implements  WorkflowTemplateMaintenanceService{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(WorkflowTemplateMaintenanceServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private WorkflowTemplateMaintenanceDAO workflowTemplateMaintenanceDAO;

	
	public List<WorkflowEnquiryDTO> workflowTemplateSearch(WorkflowEnquiryDTO workflowEnquiryDTO){
		return workflowTemplateMaintenanceDAO.search(workflowEnquiryDTO);
	}
	
	public WorkflowDetailDTO searchWorkflowRoleInfoDetail(String workflowId){
		long workflowTemplateId=Long.parseLong(workflowId);		
		return workflowTemplateMaintenanceDAO.searchWorkflowRoleInfoDetail(workflowTemplateId);
	}
	
	public WorkflowDetailDTO searchWorkflowInfo(String workflowId){
		long workflowTemplateId=Long.parseLong(workflowId);		
		return workflowTemplateMaintenanceDAO.searchWorkflowRoleInfoDetail(workflowTemplateId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	public String saveWorkflowRoleInfoDetail(WorkflowDetailDTO dto){
		String msg="Success";
		try {
			if(0!=dto.getWorkflowTemplateId()){
				long wfId=dto.getWorkflowTemplateId();
				//modify
				EaWorkflow ewf=workflowTemplateMaintenanceDAO.searchWorkflowInfo(wfId);		
				workflowTemplateMaintenanceDAO.modifyWorkflowRoleInfoDetail(dto,ewf);
			
			}else{
				//add new
				workflowTemplateMaintenanceDAO.addWorkflowRoleInfoDetail(dto);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.severe(e.toString());
			 msg="Fail";
		}
		return msg;
	}
	
	//elina
	public EaWorkflow searchById(long wfId){
		return workflowTemplateMaintenanceDAO.searchWorkflowInfo(wfId);	
	}

	public List<EaWorkflowRole> searchByWorkflowAndRole(long wfId, String role) {
		return workflowTemplateMaintenanceDAO.searchByWorkflowAndRole(wfId, role);
	}
	//Coral for Workflow Validator
	public EaWorkflow searchFreshTemplateForWorkflowById(Long wfId){
		return workflowTemplateMaintenanceDAO.searchFreshTemplateForWorkflowById(wfId);	
	}
}
