package com.hkha.ea.controller.assess;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.dto.assess.AssessAppraisalDTO;
import com.hkha.ea.dto.assess.AssignOfficerDto;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;
import com.hkha.ea.dto.common.ReportUserRole;
import com.hkha.ea.service.assess.AssignOfficerService;
import com.hkha.ea.service.workflow.WorkflowTemplateMaintenanceService;
import com.hkha.ea.validator.assess.AssignOfficerValidator;

@Controller
public class AssignOfficerController {
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AssignOfficerController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private AssignOfficerService assignOfficerService;
	
	@Autowired
	private WorkflowTemplateMaintenanceService workflowTemplateMaintenanceService;
	
	@Autowired
	private AssignOfficerValidator assignOfficerValidator;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/AssignOfficer.do")
	public ModelAndView initPage(HttpServletRequest request, HttpServletResponse response)throws Exception{
		ModelAndView mv = new ModelAndView("/assess/AssignOfficer");
		log.info("AssignOfficer start");
		String wfId = (String) request.getSession().getAttribute("workflowId");
		String fromPage = (String) request.getSession().getAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE);
		List<String> appraisees = (List<String>) request.getSession().getAttribute(Constants.SESS_SELECTED_APPRAISEE);
		
		AssignOfficerDto assignOfficerDto = new AssignOfficerDto();
		//assignOfficerDto.setDeadline(Util.date2String(new Date()));
		if(appraisees != null && appraisees.size() >0){
			String role = checkRole(appraisees.get(0),fromPage);
			//===== Insert mode
			if (!Util.isEmptyString(wfId) && Util.isEqual(fromPage, Constants.PAGE_CREATE_BATCH)) {
				log.info("AssignOfficer start 1");
				EaWorkflow dsWF = workflowTemplateMaintenanceService.searchById(Long.valueOf(wfId));
				// Retrieve report template info
				String reportTemplate = dsWF.getReportTemplate();
				assignOfficerDto.setReportTemplate(dsWF.getReportTemplate());
				SearchAppraiseeDto sac = (SearchAppraiseeDto) request.getSession().getAttribute(Constants.APPRAISEE_OBJECT);
				//============== Prepare for insert into HA_HR_EA_REPORT
				// ================
				
				EaReport rp = new EaReport();
				rp.setOverallDeadline(dsWF.getOverallDeadline());
				rp.setAppraiseeDeadline(dsWF.getAppraiseeDeadline());
				rp.setWorkflowTemplateId(Long.valueOf(wfId));
				rp.setCommenceDate(Util.string2SqlDate(sac.getAppraisalPeriodStartJmesa()));
				rp.setEndDate(Util.string2SqlDate(sac.getAppraisalPeriodEndJmesa()));
				rp.setStatus(Constants.STATUS_INITIAL);
				rp.setTrackDate(Util.string2SqlDate(sac.getTrackDateJmesa()));
				rp.setAssigned(Constants.NO);
				
				request.getSession().setAttribute("appraisalReport", rp);
				
				assignOfficerDto = assignOfficerService.pageInitialize(Long.valueOf(wfId));
				
				assignOfficerDto.setDeadline(Util.date2String(dsWF.getOverallDeadline()));
				assignOfficerDto.setReportTemplate(reportTemplate);
			}
			//===== Edit mode
			else {
				log.info("AssignOfficer start 2");
				assignOfficerDto = assignOfficerService.pageEnquiry(appraisees);
			}
			
			if (Constants.PAGE_ASSIGN_REMAINING.equals(fromPage)){
				request.getSession().setAttribute("isAssigned", this.checkReportUnassigned(appraisees));
				
				request.getSession().setAttribute("isRemaining", true);
			}else{
				request.getSession().setAttribute("isRemaining", false);
			}
			
			if("CD".equals(role) || "".equals(role)){
				//set deadline of AO,CO,IO to readonly
			}
		}
		
		mv.addObject("assignOfficerDto",assignOfficerDto);
		return mv;
	}
	
	/**
	 * Check role
	 * 
	 * @param reportID Report ID
	 * @param fromPage Token of referer
	 * @return
	 */
	private String checkRole(String reportID, String fromPage) {

		// Current User Role checking
		if (Constants.PAGE_ASSIGN_REMAINING.equals(fromPage)) {
			try {
				ReportUserRole userRole = assignOfficerService.checkRole(reportID);

				if ("CD".equals(userRole.getCurrentUserRoleStatus()))
					return "CD";
				else {
					// Error Message missing				
					return "";
				}
			} catch (Exception e) {
				
			}
		} else
			return "GM";
		return "";
	}
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/backFromAssignOfficer.do")
	public ModelAndView back(HttpServletRequest request,@ModelAttribute("assignOfficerDto")AssignOfficerDto  assignOfficerDto, BindingResult result, HttpServletResponse response)throws Exception{
		ModelAndView mv = new ModelAndView();
		String mode = (String) request.getSession().getAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE);
		request.getSession().setAttribute(Constants.FROM_ASSIGN_OFFICER,Constants.YES);
		String toEa017 = (String) request.getSession().getAttribute(Constants.FROM_REPORT_USER_INFORMATION);

		if (Constants.PAGE_ASSIGN_REMAINING.equals(mode)){
			if("Y".equals(toEa017)){
				mv = new ModelAndView("forward:/assess/getReportsAppraiseeList.do");
			}else{
				mv = new ModelAndView("forward:/assess/getBatchAppraiseeList.do"); //"ListBatchAppraisee";
			}
		}else if(Constants.PAGE_MONITOR_REPORT.equals(mode)){
			mv = new ModelAndView("forward:/admin/searchMonitorReport.do?fromSendReminder=Y");
		}else{
			mv = new ModelAndView("forward:/assess/SearchAppraisee.do");
		}
			
		return mv;
	}
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/addOrModifyAssignOfficer.do")
	public ModelAndView save(HttpServletRequest request,@ModelAttribute("assignOfficerDto")AssignOfficerDto  assignOfficerDto, BindingResult result, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("/assess/AssignOfficer");
		String status = "failed";
		String savedType = "";
		String mode = (String) request.getSession().getAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE);
		List<String> appraisees = (List<String>) request.getSession().getAttribute(Constants.SESS_SELECTED_APPRAISEE);
		
		try {
			log.info("interval: "+assignOfficerDto.getReportRoleAO().getSubsRemInterval());
			if(appraisees != null && appraisees.size() >0){
				assignOfficerValidator.validateWorkflowDetailMandatory(mode, assignOfficerDto.getReportTemplate(), assignOfficerDto, result);
				if (result.hasErrors()){
					mv = new ModelAndView("/assess/AssignOfficer");
					mv.addObject("numberOfErrors", result.getErrorCount());
					return mv;
				}
				if (Constants.PAGE_CREATE_BATCH.equals(mode)) {
					SearchAppraiseeDto sac = (SearchAppraiseeDto) request.getSession().getAttribute(Constants.APPRAISEE_OBJECT);
					EaReport rp = (EaReport) request.getSession().getAttribute("appraisalReport");
					log.info("Interval: "+assignOfficerDto.getReportRoleAO().getSubsRemInterval());
					status = assignOfficerService.saveAction(appraisees,assignOfficerDto,sac,rp,result);
					if("Y".equals(status)){
						//mv = new ModelAndView("forward:/assess/SearchAppraisee.do");
						savedType = "info."+Constants.INFO_SAVE_SUCCESS;
						//return mv;
					}
				} else {
					status = assignOfficerService.updateAction(appraisees,assignOfficerDto,result,mode);
					if("Y".equals(status)){
						if(Constants.PAGE_ASSIGN_REMAINING.equals(mode)){
							if(assignOfficerDto.isChangeStatus()){
								savedType = "info."+Constants.INFO_REPORT_SENT_TO_AO;
							}else{
								savedType = "info."+Constants.INFO_OFFICER_ASSIGNED;
							}
							//mv = new ModelAndView("forward:/assess/getBatchAppraiseeList.do?batchName="+assignOfficerDto.getBatchName());	
						}else{
							savedType = "info."+Constants.INFO_SAVE_SUCCESS;
							//mv = new ModelAndView("forward:/assess/searchBatch.do");
							request.getSession().setAttribute("batchForAssign", "Y");
						}
					}
				}
			}
		
		request.setAttribute("savedType", savedType);
		
		} catch (Exception e) {
			status = "N";
			result.rejectValue("", "", new String[]{""}, "Save fail!");		
			e.printStackTrace();
			log.severe(e.toString());
		}
		mv.addObject("saved",status);
		mv.addObject(assignOfficerDto);
		return mv;
	}

	private boolean checkReportUnassigned(List<String> appraisees){
		int count = 0;
		for(int i=0;i<appraisees.size();i++){
			if(!Util.isEmptyString(appraisees.get(i))){
				EaReport rp_i = appraiseeCommonSearchDAO.findEareportById(Long.valueOf(appraisees.get(i)));
					if(rp_i.getStatus().equalsIgnoreCase(Constants.STATUS_CD)){
						count++;
				}
			}
		}
		if(count == appraisees.size()){
			return true;
		}
		return false;
	}
}
