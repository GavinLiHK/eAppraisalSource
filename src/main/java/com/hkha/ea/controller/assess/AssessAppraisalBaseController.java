package com.hkha.ea.controller.assess;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Date;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.controller.common.ListPageController;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.common.EmployeeEnquiryDAO;
import com.hkha.ea.dao.workflow.WorkflowTemplateMaintenanceDAO;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.dto.assess.AssessAppraisalDTO;
import com.hkha.ea.dto.assess.AssessAppraisalMemoDTO;
import com.hkha.ea.dto.assess.AssignOfficerDto;
import com.hkha.ea.dto.assess.MemoPart2Part5Info;
import com.hkha.ea.dto.assess.PartAB1Info;
import com.hkha.ea.dto.assess.PartB2Info;
import com.hkha.ea.dto.assess.ReportRoleDto;
import com.hkha.ea.dto.assess.UserTableColumn;
import com.hkha.ea.dto.common.EmployeeEnquiryDTO;
import com.hkha.ea.dto.common.ReportUserRole;
import com.hkha.ea.service.assess.AppraisalAssessmentService;
import com.hkha.ea.validator.assess.AssessAppraisalMemoValidator;
import com.hkha.ea.validator.assess.AssessAppraisalReportValidator;
@Controller
public class AssessAppraisalBaseController extends ListPageController {
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AssessAppraisalBaseController.class.getName());
	//End 20231201 Write log in catalina.out

	@Override
	public void generateList() throws EAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetList() throws EAException {
		// TODO Auto-generated method stub
		
	}
	
	@Autowired 
	private AppraisalAssessmentService appraisalAssessmentService;
	
	@Autowired
	private AssessAppraisalMemoValidator assessAppraisalMemoValidator;
	
	@Autowired
	private AssessAppraisalReportValidator assessAppraisalReportValidator;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	//edited on 20170228 skip EO if workflow do not require EO
	@Autowired
	private WorkflowTemplateMaintenanceDAO workflowTemplateMaintenanceDAO;

    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	//@RequestMapping("/assess/AssessAppraisal")
	@RequestMapping("/assess/AssessAppraisal.do")
	public ModelAndView assessAppraisalEntry(HttpServletRequest request,HttpServletResponse response){
        log.info("***EA004  Entry");
       
        request.setAttribute("functionMessageKeySelected", "APPRAISAL ASSESSMENT");
        
        ModelAndView mv;
        Long rptId=(long) 0;
        String reportId=request.getParameter("reportId");
        if(!StringUtil.isEmptyString(reportId)){
        	rptId=Long.parseLong(reportId);
        }  
        
        String flag=appraisalAssessmentService.searchReportTemplateByReportId(rptId);
        String btl=request.getParameter("btl");
        
        String currentUsername=SecurityContextHolder.getContext().getAuthentication().getName();
        ReportUserRole rur= appraisalAssessmentService.getReportUserRoleByReportIdAndUserId(rptId,currentUsername);
        //debug
        log.info("report user role: "+rur.getCurrentUserRoleStatus());
        if(StringUtil.isEmptyString(rur.getCurrentUserRoleStatus())){    
        	mv = new ModelAndView("assess/assessErrorPage");
        	mv.addObject("errors", "You have no access right to view this report.");
        	return mv;
        }
            
       if("M".equalsIgnoreCase(flag)){
    	   mv = new ModelAndView("redirect:/assess/AssessAppraisalMemo.do?reportId="+reportId+"&flag="+flag+"&btl="+btl);
       }else if("C".equalsIgnoreCase(flag)||"H".equalsIgnoreCase(flag)){
    	   mv = new ModelAndView("redirect:/assess/AssessAppraisalReport.do?reportId="+reportId+"&flag="+flag+"&btl="+btl);
       }else{
    	   mv = new ModelAndView("redirect:/assess/ListOutstandingReport.do");
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
	//@RequestMapping("/assess/AssessAppraisalReport")
	@RequestMapping("/assess/AssessAppraisalReport.do")
	public ModelAndView assessAppraisalReportEntry(HttpServletRequest request,HttpServletResponse response){
        request.setAttribute("functionMessageKeySelected", "APPRAISAL ASSESSMENT");

        ModelAndView mv = new ModelAndView("/assess/AssessAppraisalReport");
        String reportId=request.getParameter("reportId");
        String reportType=request.getParameter("flag");
        String btl=request.getParameter("btl");
        boolean isEORoleChecked = false;
      //edited on 20170228 skip EO if workflow do not require EO
        EaReport rp = appraiseeCommonSearchDAO.findEareportById(Long.valueOf(reportId));        

        if(rp.getWorkflowTemplateId() != 0){
			EaWorkflow e = workflowTemplateMaintenanceDAO.searchWorkflowInfo(rp.getWorkflowTemplateId());
			ReportRoleDto co = this.retrieveRoleDetail(Long.valueOf(reportId), Constants.ROLE_ABBR_CO);
			ReportRoleDto ao = this.retrieveRoleDetail(Long.valueOf(reportId), Constants.ROLE_ABBR_AO);
			ReportRoleDto io = this.retrieveRoleDetail(Long.valueOf(reportId), Constants.ROLE_ABBR_IO);
			ReportRoleDto eo = this.retrieveRoleDetail(Long.valueOf(reportId), Constants.ROLE_ABBR_EO);
			ReportRoleDto ro = this.retrieveRoleDetail(Long.valueOf(reportId), Constants.ROLE_ABBR_RO);
			isEORoleChecked = eo.isRoleChecked();
			request.getSession().setAttribute("isCORoleChecked", co.isRoleChecked());
			request.getSession().setAttribute("isAORoleChecked", ao.isRoleChecked());
			request.getSession().setAttribute("isIORoleChecked", io.isRoleChecked());
			request.getSession().setAttribute("isEORoleChecked", eo.isRoleChecked());
			request.getSession().setAttribute("isRORoleChecked", ro.isRoleChecked());
		}
        
        AssessAppraisalDTO assessAppraisalDTO=appraisalAssessmentService.searchAssessAppraisalReportById(reportId,reportType,isEORoleChecked,request);
        assessAppraisalDTO.setBackToListType(btl);
        assessAppraisalDTO.setCurrentPage("0");
        //end edited on 20170228
        
        log.info("Appraisee Info: "+assessAppraisalDTO.getAppraiseeInfo().getEmployeeNumber()+" - "+assessAppraisalDTO.getPersonalInfo().getEnglishName()+" "+assessAppraisalDTO.getReportUserRole().getNextOfficerRole());
        
        mv.addObject("assessAppraisalDTO",assessAppraisalDTO);
       
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
	@RequestMapping("/assess/AssessAppraisalReportAction.do")
	public ModelAndView assessAppraisalReportAction(HttpServletRequest request,@ModelAttribute("assessAppraisalDTO") AssessAppraisalDTO assessAppraisalDTO,BindingResult result,HttpServletResponse response){
        log.info("***EA004 report Action");
        request.setAttribute("functionMessageKeySelected", "APPRAISAL ASSESSMENT Report ");   
        ModelAndView mv = new ModelAndView("/assess/AssessAppraisalReport");
        
        try {
       
        String actionFlag=assessAppraisalDTO.getActionFlag();
        String flag="";
        //20220630 Prevent empty next officer ID
        String dimTabFlag="";
        //20220630 End - Prevent empty next officer ID
        
        setTabSpaceReport(assessAppraisalDTO);
        
       if("save".equals(actionFlag)){
    	   //assessAppraisalReportValidator.validateAssessAppraisalReportMandatory(assessAppraisalDTO,result);
    	   assessAppraisalReportValidator.validateInputFields(assessAppraisalDTO,result,false);
    	   if(result.hasErrors()){
    		   mv= new ModelAndView("/assess/AssessAppraisalReport");
    		   assessAppraisalDTO=appraisalAssessmentService.genCCInfoListForPartB4(assessAppraisalDTO);
    	       assessAppraisalDTO.setPrintSubmitInfo(appraisalAssessmentService.genRoutingLabelAndRoutingToListForPrintSubmit(assessAppraisalDTO.getPrintSubmitInfo(),assessAppraisalDTO.getReportUserRole().getCurrentUserRoleStatus()));
    		   mv.addObject("assessAppraisalMemoDTO",assessAppraisalDTO);
    		   return mv;
    	   }else{
    		   //flag=appraisalAssessmentService.saveAssessAppraisalReport(assessAppraisalDTO);
    		   boolean checkSave = appraisalAssessmentService.saveAssessAppraisalReportBeforeSubmit(assessAppraisalDTO, actionFlag);
    		   if(checkSave){
    			   flag = "success";
    		   }
        	   assessAppraisalDTO.setActionFlag(flag);
    		  
    	   }
    	   
       }else if("save_report".equals(actionFlag)){
    	   //assessAppraisalReportValidator.validateAssessAppraisalReportMandatory(assessAppraisalDTO,result);
    	   assessAppraisalReportValidator.validateInputFields(assessAppraisalDTO,result,false);
    	   if(result.hasErrors()){
    		   mv= new ModelAndView("/assess/AssessAppraisalReport");
    		   assessAppraisalDTO=appraisalAssessmentService.genCCInfoListForPartB4(assessAppraisalDTO);
    	       assessAppraisalDTO.setPrintSubmitInfo(appraisalAssessmentService.genRoutingLabelAndRoutingToListForPrintSubmit(assessAppraisalDTO.getPrintSubmitInfo(),assessAppraisalDTO.getReportUserRole().getCurrentUserRoleStatus()));
    		   mv.addObject("assessAppraisalMemoDTO",assessAppraisalDTO);
    		   return mv;
    	   }else{
    		   //flag=appraisalAssessmentService.saveAssessAppraisalReport(assessAppraisalDTO);
    		   boolean checkSave = appraisalAssessmentService.saveAssessAppraisalReportBeforeSubmit(assessAppraisalDTO, "save");
        	   if("PO".equalsIgnoreCase(assessAppraisalDTO.getPrintSubmitInfo().getRouting()) || "AP".equalsIgnoreCase(assessAppraisalDTO.getPrintSubmitInfo().getRouting())){
        		   //assessAppraisalReportValidator.validateInputFields(assessAppraisalDTO,result,false);
        	   }else{
        		   assessAppraisalReportValidator.validateInputFields(assessAppraisalDTO,result,false,true);
        	   }
    		   if(checkSave){
    			   flag = "report";
    		   }
        	   assessAppraisalDTO.setActionFlag(flag);
    		   
        	   if(result.hasErrors()){
        		   mv= new ModelAndView("/assess/AssessAppraisalReport");
        		   assessAppraisalDTO=appraisalAssessmentService.genCCInfoListForPartB4(assessAppraisalDTO);
        	       assessAppraisalDTO.setPrintSubmitInfo(appraisalAssessmentService.genRoutingLabelAndRoutingToListForPrintSubmit(assessAppraisalDTO.getPrintSubmitInfo(),assessAppraisalDTO.getReportUserRole().getCurrentUserRoleStatus()));
        		   mv.addObject("assessAppraisalMemoDTO",assessAppraisalDTO);
        		   return mv;
        	   }
    	   }
    	   
       }else if("send".equals(actionFlag)){
    	   //20220630 Prevent empty next officer ID
    	   //assessAppraisalReportValidator.validateAssessAppraisalReportMandatory(assessAppraisalDTO,result);
    	   //assessAppraisalReportValidator.validateAssessAppraisalReportSend(assessAppraisalDTO,result);
    	   
    	   dimTabFlag = "T";
    	       	   
    	   //refresh dto rur
    	   long rptId=assessAppraisalDTO.getReportId();
    	   String currentUsername=SecurityContextHolder.getContext().getAuthentication().getName();
    	   ReportUserRole rur= appraisalAssessmentService.getReportUserRoleByReportIdAndUserId(rptId,currentUsername); 
    	   assessAppraisalDTO.setReportUserRole(rur);
    	   //log.info("assessAppraisalDTO.getReportUserRole().getNextOfficerId()" + assessAppraisalDTO.getReportUserRole().getNextOfficerId());
    	   //20220630 End - Prevent empty next officer ID

    	   if("PO".equalsIgnoreCase(assessAppraisalDTO.getPrintSubmitInfo().getRouting()) || "AP".equalsIgnoreCase(assessAppraisalDTO.getPrintSubmitInfo().getRouting())){
    		   assessAppraisalReportValidator.validateInputFields(assessAppraisalDTO,result,false);
    	   }else{
    		   assessAppraisalReportValidator.validateInputFields(assessAppraisalDTO,result,true);
    	   }
    	   
    	   if(result.hasErrors()){
    		   mv= new ModelAndView("/assess/AssessAppraisalReport");
    		   assessAppraisalDTO=appraisalAssessmentService.genCCInfoListForPartB4(assessAppraisalDTO);
    	       assessAppraisalDTO.setPrintSubmitInfo(appraisalAssessmentService.genRoutingLabelAndRoutingToListForPrintSubmit(assessAppraisalDTO.getPrintSubmitInfo(),assessAppraisalDTO.getReportUserRole().getCurrentUserRoleStatus()));
    		   mv.addObject("assessAppraisalMemoDTO",assessAppraisalDTO);
   			   return mv;
    	   }else{	  
    		   try {  
    			  // if("AP".equalsIgnoreCase(assessAppraisalDTO.getPrintSubmitInfo().getRouting())){
    			  //	    flag = appraisalAssessmentService.sendAssessAppraisalReport(assessAppraisalDTO);
    			  // }else{	
    			  // }
    			   boolean checkSave = appraisalAssessmentService.saveAssessAppraisalReportBeforeSubmit(assessAppraisalDTO, actionFlag);
					if (checkSave) {
						flag = appraisalAssessmentService.sendAssessAppraisalReport(assessAppraisalDTO);
					}

    		   } catch (Exception e) {
    			   e.printStackTrace();
    			   log.severe(e.toString());
    			   flag="fail";
    		   }
    		   assessAppraisalDTO.setActionFlag(flag);
    	   }
       }else if("backToList".equals(actionFlag)){
    	   if("A".equalsIgnoreCase(assessAppraisalDTO.getBackToListType())){
    		   mv= new ModelAndView("redirect:/assess/backFromAssignOfficer.do");
    	   }else if("L".equalsIgnoreCase(assessAppraisalDTO.getBackToListType())){
    		   mv= new ModelAndView("redirect:/assess/ListOutstandingReport.do");
    	   }	  
    	   return mv;
       }
       assessAppraisalDTO=appraisalAssessmentService.genCCInfoListForPartB4(assessAppraisalDTO);
       assessAppraisalDTO.setPrintSubmitInfo(appraisalAssessmentService.genRoutingLabelAndRoutingToListForPrintSubmit(assessAppraisalDTO.getPrintSubmitInfo(),assessAppraisalDTO.getReportUserRole().getCurrentUserRoleStatus()));
      
       mv.addObject("assessAppraisalDTO",assessAppraisalDTO);
       //20220630 Prevent empty next officer ID
       mv.addObject("dimTabFlag", dimTabFlag);
       //20220630 End - Prevent empty next officer ID
       
        } catch (NullPointerException e) {			// prevent dual submission 20230116
     	   log.info("Skipped assessAppraisalReportAction");
     	   log.severe(e.toString());
     	   mv= new ModelAndView("redirect:/assess/ListOutstandingReport.do");
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
	@RequestMapping("/assess/AssessAppraisalMemo.do")
	public ModelAndView assessAppraisalMemoEntry(HttpServletRequest request,HttpServletResponse response){
        log.info("***EA004 Memo Entry");
        request.setAttribute("functionMessageKeySelected", "APPRAISAL ASSESSMENT Memo ");
        
        ModelAndView mv = new ModelAndView("/assess/AssessAppraisalMemo");
        
        
        String reportId=request.getParameter("reportId");
        String reportType=request.getParameter("flag");
        String btl=request.getParameter("btl");
     
        AssessAppraisalMemoDTO assessAppraisalMemoDTO=appraisalAssessmentService.searchAssessAppraisalMemoById(reportId,reportType,btl,request);
        assessAppraisalMemoDTO.setCurrentPage("0");
       
        mv.addObject("assessAppraisalMemoDTO",assessAppraisalMemoDTO);
       
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
	@RequestMapping("/assess/AssessAppraisalMemoAction.do")
	public ModelAndView assessAppraisalMemoAction(HttpServletRequest request,@ModelAttribute("assessAppraisalMemoDTO") AssessAppraisalMemoDTO assessAppraisalMemoDTO,BindingResult result,HttpServletResponse response){
        log.info("***EA004 Memo Action");
        request.setAttribute("functionMessageKeySelected", "APPRAISAL ASSESSMENT Memo ");
        
       ModelAndView mv = new ModelAndView("/assess/AssessAppraisalMemo");
       
       try {

       String actionFlag=assessAppraisalMemoDTO.getActionFlag();
       String flag="";
       String currentPage="";
       //20220630 Prevent empty next officer ID
       String dimTabFlag="";
       //20220630 End - Prevent empty next officer ID

       setTabSpaceMemo(assessAppraisalMemoDTO);
       
       if("save".equals(actionFlag)){
    	   //assessAppraisalMemoValidator.validateAssessAppraisalMemoMandatory(assessAppraisalMemoDTO,result);
    	   assessAppraisalMemoValidator.validateInputFields(assessAppraisalMemoDTO, result, false);
    	   if(result.hasErrors()){
    		    mv= new ModelAndView("/assess/AssessAppraisalMemo");
    		    assessAppraisalMemoDTO.setMemoPrintSubmitInfo(appraisalAssessmentService.genRoutingLabelAndRoutingToListForPrintSubmit(assessAppraisalMemoDTO.getMemoPrintSubmitInfo(),assessAppraisalMemoDTO.getReportUserRole().getCurrentUserRoleStatus()));
    		    mv.addObject("assessAppraisalMemoDTO",assessAppraisalMemoDTO);
   				return mv;
    	   }else{
    		    currentPage=assessAppraisalMemoDTO.getCurrentPage();
    		    //flag=appraisalAssessmentService.saveAssessAppraisalMemo(assessAppraisalMemoDTO);
    		    boolean checkSave = appraisalAssessmentService.saveAssessAppraisalMemoBeforeSubmit(assessAppraisalMemoDTO);
    		    if(checkSave){
    		    	//flag = "sendSuccess";
    		    	flag = "success";
    		    }
    		    assessAppraisalMemoDTO.setActionFlag(flag); 
    	   }
    	   
       }else if("save_report".equals(actionFlag)){
    	   //assessAppraisalReportValidator.validateAssessAppraisalReportMandatory(assessAppraisalDTO,result);
    	   assessAppraisalMemoValidator.validateInputFields(assessAppraisalMemoDTO,result,false);
    	   if(result.hasErrors()){
    		   mv= new ModelAndView("/assess/AssessAppraisalMemo");
	   		    assessAppraisalMemoDTO.setMemoPrintSubmitInfo(appraisalAssessmentService.genRoutingLabelAndRoutingToListForPrintSubmit(assessAppraisalMemoDTO.getMemoPrintSubmitInfo(),assessAppraisalMemoDTO.getReportUserRole().getCurrentUserRoleStatus()));
	   		    mv.addObject("assessAppraisalMemoDTO",assessAppraisalMemoDTO);
    		   mv.addObject("assessAppraisalMemoDTO",assessAppraisalMemoDTO);
    		   return mv;
    	   }else{
	   		    currentPage=assessAppraisalMemoDTO.getCurrentPage();
	   		    //flag=appraisalAssessmentService.saveAssessAppraisalMemo(assessAppraisalMemoDTO);
	   		    boolean checkSave = appraisalAssessmentService.saveAssessAppraisalMemoBeforeSubmit(assessAppraisalMemoDTO);
	     	   if("PO".equalsIgnoreCase(assessAppraisalMemoDTO.getMemoPrintSubmitInfo().getRouting()) || "AP".equalsIgnoreCase(assessAppraisalMemoDTO.getMemoPrintSubmitInfo().getRouting())){
	    		   //assessAppraisalMemoValidator.validateInputFields(assessAppraisalMemoDTO, result, false);
	    	   }else{
	    		   assessAppraisalMemoValidator.validateInputFields(assessAppraisalMemoDTO, result, false, true);
	    	   }
    		   if(checkSave){
    			   flag = "report";
    		   }
   		    	assessAppraisalMemoDTO.setActionFlag(flag); 
    		   
        	   if(result.hasErrors()){
       		    mv= new ModelAndView("/assess/AssessAppraisalMemo");
       		    assessAppraisalMemoDTO.setMemoPrintSubmitInfo(appraisalAssessmentService.genRoutingLabelAndRoutingToListForPrintSubmit(assessAppraisalMemoDTO.getMemoPrintSubmitInfo(),assessAppraisalMemoDTO.getReportUserRole().getCurrentUserRoleStatus()));
       		    mv.addObject("assessAppraisalMemoDTO",assessAppraisalMemoDTO);
     		   return mv;
        	   }
    	   }
    	   
       }else if("send".equals(actionFlag)){
       	   //20220630 Prevent empty next officer ID
    	   //assessAppraisalMemoValidator.validateAssessAppraisalMemoSendMandatory(assessAppraisalMemoDTO,result);
    	   dimTabFlag = "T";
    	   
    	   //refresh dto rur
    	   long rptId=assessAppraisalMemoDTO.getReportId();
    	   String currentUsername=SecurityContextHolder.getContext().getAuthentication().getName();
    	   ReportUserRole rur= appraisalAssessmentService.getReportUserRoleByReportIdAndUserId(rptId,currentUsername); 
    	   assessAppraisalMemoDTO.setReportUserRole(rur);
    	   //log.info("assessAppraisalMemoDTO.getReportUserRole().getNextOfficerId()" + assessAppraisalMemoDTO.getReportUserRole().getNextOfficerId());
    	   
    	   //log.info("assessAppraisalMemoDTO.getMemoPrintSubmitInfo().getRouting() " + assessAppraisalMemoDTO.getMemoPrintSubmitInfo().getRouting());
       	   //20220630 End - Prevent empty next officer ID
    	   if("PO".equalsIgnoreCase(assessAppraisalMemoDTO.getMemoPrintSubmitInfo().getRouting()) || "AP".equalsIgnoreCase(assessAppraisalMemoDTO.getMemoPrintSubmitInfo().getRouting())){
    		   assessAppraisalMemoValidator.validateInputFields(assessAppraisalMemoDTO, result, false);
    	   }else{
    		   assessAppraisalMemoValidator.validateInputFields(assessAppraisalMemoDTO, result, true);
    	   }
    	   
    	   if(result.hasErrors()){
    		    mv= new ModelAndView("/assess/AssessAppraisalMemo");
    		    assessAppraisalMemoDTO.setMemoPrintSubmitInfo(appraisalAssessmentService.genRoutingLabelAndRoutingToListForPrintSubmit(assessAppraisalMemoDTO.getMemoPrintSubmitInfo(),assessAppraisalMemoDTO.getReportUserRole().getCurrentUserRoleStatus()));
    		    mv.addObject("assessAppraisalMemoDTO",assessAppraisalMemoDTO);
   				return mv;
    	   }else{
    		   currentPage=assessAppraisalMemoDTO.getCurrentPage();
    		   try {
    			//   if("AP".equalsIgnoreCase(assessAppraisalMemoDTO.getMemoPrintSubmitInfo().getRouting())){
    			//	   flag = appraisalAssessmentService.sendAssessAppraisalMemo(assessAppraisalMemoDTO);
    			//   }else{
    			 //  }
    			   boolean checkSave = appraisalAssessmentService.saveAssessAppraisalMemoBeforeSubmit(assessAppraisalMemoDTO);
    			   if(checkSave){
    				  flag = appraisalAssessmentService.sendAssessAppraisalMemo(assessAppraisalMemoDTO);
    			   }
    		   } catch (Exception e) {   			  
    			   flag="fail";
    			   e.printStackTrace();
    			   log.severe(e.toString());
    		   }
    		   assessAppraisalMemoDTO.setActionFlag(flag);
    	   }
       }else if("backToList".equals(actionFlag)){
    	   if("A".equalsIgnoreCase(assessAppraisalMemoDTO.getBackToListType())){
    		   mv= new ModelAndView("redirect:/assess/backFromAssignOfficer.do");
    	   }else if("L".equalsIgnoreCase(assessAppraisalMemoDTO.getBackToListType())){
    		   mv= new ModelAndView("redirect:/assess/ListOutstandingReport.do");
    	   }	  
    	   return mv;
       }
       AssessAppraisalMemoDTO dto=new AssessAppraisalMemoDTO();
       dto =appraisalAssessmentService.searchAssessAppraisalMemoById(assessAppraisalMemoDTO.getReportId().toString(),assessAppraisalMemoDTO.getReportType(),assessAppraisalMemoDTO.getBackToListType(),request);
       dto.setActionFlag(flag);
       dto.setCurrentPage(currentPage);
       mv.clear();
       mv= new ModelAndView("/assess/AssessAppraisalMemo");
       mv.addObject("assessAppraisalMemoDTO",dto);
       //20220630 Prevent empty next officer ID
       mv.addObject("dimTabFlag", dimTabFlag);
       //20220630 End - Prevent empty next officer ID
      
       } catch (NullPointerException e) {			// prevent dual submission 20230116
    	   log.info("Skipped assessAppraisalMemoAction");
    	   mv= new ModelAndView("redirect:/assess/ListOutstandingReport.do");
       }
       
       return mv;
	}
	
	//edited on 20170228
	private ReportRoleDto retrieveRoleDetail(Long reportID, String roleAbbrRo) {
		ReportRoleDto ro = new ReportRoleDto();
		EaReportRole role = appraiseeCommonSearchDAO.searchReportRoleByReportIdandRole(reportID, roleAbbrRo);
		if(role != null){
			ro.setEmployeeNum(role.getOfficerId());
			ro.setEmployeeName(role.getOfficerName());
			ro.setOldEmployeeNum(role.getOfficerId());
			ro.setFirstReminder(role.getFirstReminder());
			ro.setSecondReminder(role.getSecondReminder());
			ro.setThirdReminder(role.getThirdReminder());
			ro.setSubsReminder(role.getSubsReminder());
			ro.setFirstRemInterval(role.getFirstRemInterval());
			ro.setSecondRemInterval(role.getSecondRemInterval());
			ro.setThirdRemInterval(role.getThirdRemInterval());
			//edited on 20180302 subsequent reminder interval does not match
			//ro.setSubsRemInterval(role.getSecondRemInterval());
			ro.setSubsRemInterval(role.getSubsRemInterval());
			//end edited on 20180302
			ro.setDeadline(Util.date2String(role.getDeadline()));
			ro.setNotification(role.getNotification());
			ro.setRole(role.getRole());
			ro.setRoleChecked(true);
			ro.setRoleSequence(role.getRoleSequence());
		}else{
			ro.setRoleChecked(false);
		}
	
		return ro;
	}
	
	//added on 20180419 for fixing tabs space problem REPORT
	private void setTabSpaceReport(AssessAppraisalDTO dto){
		List<PartAB1Info> pab1list= new ArrayList<PartAB1Info>();
		List<PartAB1Info> pb1list = new ArrayList<PartAB1Info>();
		PartB2Info pb2list= new PartB2Info();
		
		if(dto.getPartAInfoList()!=null){
			for (int i=0; i< dto.getPartAInfoList().size();i++){
				PartAB1Info pab1=new PartAB1Info();
				pab1.setKeyObjectives(dto.getPartAInfoList().get(i).getKeyObjectives());
				pab1.setWeighting(dto.getPartAInfoList().get(i).getWeighting());
				pab1.setSeqNo(dto.getPartAInfoList().get(i).getSeqNo());
				pab1.setType(dto.getPartAInfoList().get(i).getType());
				pab1.setRating(dto.getPartAInfoList().get(i).getRating());
				
				String a = dto.getPartAInfoList().get(i).getResultAchieved();
				String e = "";
				if (!StringUtil.isEmptyString(a)){
					 e = a.replaceAll("\t", "     ");	
				}else{
					 e = a;
				}
		        pab1.setResultAchieved(e);
		        pab1list.add(pab1);
			}
			
		}
		//new added on 20190117 remove tab in PartB1
		if(dto.getPartB1InfoList()!=null){
			for (int i=0; i< dto.getPartB1InfoList().size();i++){
				PartAB1Info pab1=new PartAB1Info();
				pab1.setKeyObjectives(dto.getPartB1InfoList().get(i).getKeyObjectives());
				pab1.setWeighting(dto.getPartB1InfoList().get(i).getWeighting());
				pab1.setSeqNo(dto.getPartB1InfoList().get(i).getSeqNo());
				pab1.setType(dto.getPartB1InfoList().get(i).getType());
				pab1.setRating(dto.getPartB1InfoList().get(i).getRating());
				
				String a = dto.getPartB1InfoList().get(i).getResultAchieved();
				String e = "";
				if (!StringUtil.isEmptyString(a)){
					 e = a.replaceAll("\t", "     ");	
				}else{
					 e = a;
				}
		        pab1.setResultAchieved(e);
		        pb1list.add(pab1);
			}
			
		}
		
		//end added on 20190117
		
		//new added on 20181227 remove tab in overall comment PartB2
		if(dto.getPartB2Info() != null){

			pb2list.setOverallPerformanceComment2(dto.getPartB2Info().getOverallPerformanceComment2());
			pb2list.setOverallRating(dto.getPartB2Info().getOverallRating());
			pb2list.setSpecialFactors(dto.getPartB2Info().getSpecialFactors());
			
			String tempOverCom = dto.getPartB2Info().getOverallPerformanceComment();
			String temp = "";
			if (!StringUtil.isEmptyString(tempOverCom)){
				temp = tempOverCom.replaceAll("\t", "     ");	
			}else{
				temp = tempOverCom;
			}
			pb2list.setOverallPerformanceComment(temp);

		}
        /*String b = a.substring(0, a.indexOf("\n"));
        String b2 = "\t"+b;
        String c = a.substring(a.indexOf("\n")+1,a.length());
        String d = b2.concat(c);
        log.info("debug:"+b);
        log.info("debug_2:"+c);
        log.info("debug_3:"+d);*/
        dto.setPartAInfoList(pab1list);
        dto.setPartB1InfoList(pb1list);
        dto.setPartB2Info(pb2list);
	}
	//end added on 20180419 for fixing tabs space problem

	//added on 20180419 for fixing tabs space problem MEMO
		private void setTabSpaceMemo(AssessAppraisalMemoDTO dto){
			MemoPart2Part5Info mp2 = new MemoPart2Part5Info();
			
			if(dto.getMemoPart2Info().getDutyDesc()!=null){
					String a = dto.getMemoPart2Info().getDutyDesc();
			        String e = a.replaceAll("\t", "     ");
			        mp2.setDutyDesc(e);  
			}
			dto.setMemoPart2Info(mp2);

		}
		//end added on 20180419 for fixing tabs space problem

}
