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
import org.jmesa.model.TableModel;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryVo;
import com.hkha.ea.service.assess.AppraiseeBatchConverAndGenTableService;
import com.hkha.ea.service.assess.AppraiseeBatchEnquiryService;
import com.hkha.ea.service.assess.ReportUserInfoOrPeriodService;
import com.hkha.ea.validator.assess.AssignOfficerValidator;

@Controller
public class ReportUserInformationMaintenanceController {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(ReportUserInformationMaintenanceController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private ReportUserInfoOrPeriodService reportUserInfoOrPeriodService;
	
	@Autowired
	private AppraiseeBatchConverAndGenTableService appraiseeBatchConverAndGenTableService;
	
	@Autowired
	private AssignOfficerValidator assignOfficerValidator;
	

    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/initRptUserInfoMain.do")
	public ModelAndView reportStatusMaintenanceEntry(HttpServletRequest request,HttpServletResponse response){
		 log.info("***EA017  Entry***");
	     request.setAttribute("functionMessageKeySelected", "Report User Information Maintenance");
	     BatchEnquiryDto  batchEnquiryDto =new BatchEnquiryDto();
	     batchEnquiryDto.setFunctionNum("EA017");
	     ModelAndView mv =new  ModelAndView ("/assess/SearchReportUserInfoOrPeriod");
	     request.setAttribute("batchEnquiryDto",batchEnquiryDto);
	     return  mv;
	}
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/rptUserInfoMainAssginAction.do")
	public ModelAndView reportStatusMaintenanceAssginAction(HttpServletRequest request,@ModelAttribute("batchEnquiryDto") BatchEnquiryDto batchEnquiryDto, BindingResult result, HttpServletResponse response){
		 
		log.info("***EA017  Entry***");
	    request.setAttribute("functionMessageKeySelected", "Report User Information Maintenance -Assgin Officer");  
	    ModelAndView mv =null;
	     
	    	 
	    String selectedOptions=batchEnquiryDto.getSelectedOptions();
	    
	    List<String> rptIds=null;
	   
	    String ass="";
	    String sta="";
	     	
		List<BatchEnquiryVo> volist =reportUserInfoOrPeriodService.searchAppraiseeBatchList(batchEnquiryDto.getBatchNameJmesa());
		if(volist.size()>0&&!StringUtil.isEmptyString(selectedOptions)){				
			ass=appraiseeBatchConverAndGenTableService.converAssVo(volist,selectedOptions);
			sta=appraiseeBatchConverAndGenTableService.converStaVo(volist,selectedOptions);
		}
			
		request.setAttribute("batchEnquiryDto",batchEnquiryDto);

		//validate reportId
		assignOfficerValidator.checkSelectedReportID(selectedOptions, result);
		//assignOfficerValidator.checkSelectedAssignCaseExist(ass, result);
		//assignOfficerValidator.checkReportStatusForMultipleSelection(sta, result);
			 
		if(result.hasErrors()){
			mv = new ModelAndView("/assess/ListReportUserInfoOrPeriod");
			mv.addObject("numberOfErrors",result.getErrorCount());

			TableModel tableModel = new TableModel("html", request, response);
			tableModel.setItems(volist);
			tableModel.setTable(appraiseeBatchConverAndGenTableService.genBatchAppraiseeListHtml());
			mv.addObject("reportAppraiseeList", tableModel.render());				
			batchEnquiryDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedVo(volist));
			return mv;
		}
		
		rptIds=appraiseeBatchConverAndGenTableService.converReportIds(selectedOptions);
	 
		mv = new ModelAndView("forward:/assess/AssignOfficer.do");	
		request.getSession().setAttribute(Constants.SESS_SELECTED_APPRAISEE, rptIds);
		request.getSession().setAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE, Constants.PAGE_ASSIGN_REMAINING);
		request.getSession().setAttribute(Constants.FROM_REPORT_USER_INFORMATION, "Y");	 
		request.getSession().setAttribute(Constants.APPRAISEE_OBJECT,batchEnquiryDto);
		return mv;   

	}

}
