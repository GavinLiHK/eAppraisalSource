package com.hkha.ea.service.common.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.workflow.WorkflowTemplateMaintenanceDAO;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.dto.common.PrintReportPrepareDTO;
import com.hkha.ea.service.common.PrintReportService;

@Service("printReportService")
public class PrintReportServiceImpl implements PrintReportService{
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	@Autowired
	private WorkflowTemplateMaintenanceDAO workflowTemplateMaintenanceDAO;
	
	@Autowired
	@Qualifier("applicatioPro")	
	private Properties applicatioPro;
	
	private String getReportType(String reportId){	
		
		String reportTypeCode = "";
		
		EaReport er=appraiseeCommonSearchDAO.findEareportById(Long.parseLong(reportId));
		if(null!=er){
			EaWorkflow ew=workflowTemplateMaintenanceDAO.searchWorkflowInfo(er.getWorkflowTemplateId());
			if(null!=ew){
				reportTypeCode = ew.getReportTemplate();
			}
		}
		 if ("C".equalsIgnoreCase(reportTypeCode)){
			return "CS";
		 }else if ("H".equalsIgnoreCase(reportTypeCode)){
			return "CHO";
		 }else if ("M".equalsIgnoreCase(reportTypeCode)) {
			return "MEMO";
		 }
		return "";
	}
	
	public PrintReportPrepareDTO generateReportPrepare(String rptId,String roleCode){
		String reportType = "";

		if ((!Util.isEmptyString(rptId)) && (!Util.isEmptyString(roleCode))) {
			reportType = this.getReportType(rptId);
		 }

		 String reportFileType = "";
		 Map<String,Object> parameters = new HashMap<String,Object>(); 
		 String toPage = "";
		 
		 if ((Util.isEqual(reportType, "CS")) || (Util.isEqual(reportType, "CHO"))) {	
			 reportFileType = "AAR_Report";
			 parameters.put("StaffType", reportType);
			 toPage = "J";
			 
			 if (!Util.isEmptyString(roleCode)) {
				 if(Util.isEqual(roleCode, "AO")){
					 toPage = "F";
				 }else if(Util.isEqual(roleCode, "CD")){
					 toPage = "";
				 }else if (Util.isEqual(roleCode, "AP")){
					 toPage = "C";
				 }else if (Util.isEqual(roleCode, "CO")){
					 toPage = "G";
				 }else if (Util.isEqual(roleCode, "IO")){
					 toPage = "H";
				 }else if (Util.isEqual(roleCode, "EO")){
					 toPage = "I";
				 }
			 }       
		 }else{
		     reportFileType = "Memo_Report";
		     toPage = "C";
		     
		     if ((Util.isEqual(roleCode, "AO")) || (Util.isEqual(roleCode, "CO"))){
		    	   toPage = "B";
		      } else if (Util.isEqual(roleCode, "AP")){
		    	   toPage = "A";
		      }else if (Util.isEqual(roleCode, "CD")) {
		          toPage = "";
		      }
		  }	 
		     
		 String rptDirRealPath ="";//(String)applicatioPro.getProperty("jasperReportHead");
		 
		 PrintReportPrepareDTO dto=new PrintReportPrepareDTO();
		 dto.setPath(reportFileType);
		 dto.setReportId(rptId);
		 dto.setReportDirectory(rptDirRealPath);
		 dto.setRole(toPage);
		 dto.setRoleCode(roleCode);
		 dto.setStaffType(reportType);
		 return dto;
		
	}
	

}
