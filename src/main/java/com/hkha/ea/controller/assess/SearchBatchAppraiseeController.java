package com.hkha.ea.controller.assess;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jmesa.model.TableModel;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryVo;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;
import com.hkha.ea.service.assess.AppraiseeBatchConverAndGenTableService;
import com.hkha.ea.service.assess.AppraiseeBatchEnquiryService;
import com.hkha.ea.validator.assess.AssignOfficerValidator;

@Controller
public class SearchBatchAppraiseeController {

	@Autowired
	private AppraiseeBatchEnquiryService appraiseeBatchEnquiryService;
	
	@Autowired
	private AssignOfficerValidator assignOfficerValidator;
	
	@Autowired
	private AppraiseeBatchConverAndGenTableService appraiseeBatchConverAndGenTableService;

    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/initBatchAppraisee.do")
	public ModelAndView initSearchBatchForm(HttpServletRequest request, @ModelAttribute("batchEnquiryDto") BatchEnquiryDto batchEnquiryDto) {
		request.setAttribute("functionMessageKeySelected", "Assign Remaining Officer - search batch");
		ModelAndView mv = new ModelAndView("/assess/SearchBatchAppraisee");
		mv.addObject(batchEnquiryDto);
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
	@RequestMapping("/assess/SearchBatchAppraisee.do")
	public ModelAndView searchBatchList(HttpServletRequest request,
			@ModelAttribute("batchEnquiryDto") BatchEnquiryDto batchEnquiryDto, HttpServletResponse response) {
		request.setAttribute("functionMessageKeySelected", "Assign Remaining Officer - search batch");
		ModelAndView mv = new ModelAndView("/assess/SearchBatchAppraisee");
		if (batchEnquiryDto != null) {
			List<BatchEnquiryDto> results = appraiseeBatchEnquiryService.searchAppraiseeBatch(
					SecurityContextHolder.getContext().getAuthentication().getName(), batchEnquiryDto.getBatchName(),
					batchEnquiryDto.getEmployeeNum(), batchEnquiryDto.getEmployeeName(), batchEnquiryDto.getRank(),
					batchEnquiryDto.getPostUnit(), batchEnquiryDto.getPostTitle());

			TableModel tableModel = new TableModel("html", request, response);
			tableModel.setItems(results);
			tableModel.setTable(appraiseeBatchConverAndGenTableService.getSearchBatchAppraiseeHtml());
			request.setAttribute("searchBatchAppraiseeList", tableModel.render());
			
		}
		request.setAttribute("batchEnquiryDto",batchEnquiryDto);
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
	@RequestMapping("/assess/getBatchAppraiseeList.do")
	public ModelAndView getBatchAppraiseeList(HttpServletRequest request,
			@ModelAttribute("batchEnquiryDto") BatchEnquiryDto batchEnquiryDto, HttpServletResponse response) {
		String frompage = (String) request.getSession().getAttribute(Constants.FROM_ASSIGN_OFFICER);
		
		if(!Util.isEmptyString(frompage) && Constants.YES.equals(frompage))
			batchEnquiryDto = (BatchEnquiryDto) request.getSession().getAttribute(Constants.APPRAISEE_OBJECT);
		
		String functionId = request.getParameter("funcitonId");
		String batchName = request.getParameter("batchName");
		if(Util.isEmptyString(batchEnquiryDto.getFunctionNum())){
			if(!Util.isEmptyString(functionId)){
				batchEnquiryDto.setBatchNameJmesa(batchName);
				batchEnquiryDto.setFunctionNum("EA015");
			}else{
				batchEnquiryDto.setFunctionNum("EA002");
			}
		}
		
		ModelAndView mv = new ModelAndView("/assess/ListBatchAppraisee");
		if (batchEnquiryDto != null) {
			List<BatchEnquiryVo> results = appraiseeBatchEnquiryService.searchAppraiseeBatchVoList(batchEnquiryDto.getBatchNameJmesa());

			TableModel tableModel = new TableModel("html", request, response);
			tableModel.setItems(results);
			tableModel.setTable(appraiseeBatchConverAndGenTableService.genBatchAppraiseeListHtml());
			mv.addObject("batchAppraiseeList", tableModel.render());
			batchEnquiryDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedVo(results));	
			
		}
		 mv.addObject("batchEnquiryDto",batchEnquiryDto);
		 request.getSession().setAttribute(Constants.FROM_ASSIGN_OFFICER,Constants.NO);

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
	@RequestMapping("/assess/gotoAssignOfficerFromRemind.do")
	public ModelAndView assign(HttpServletRequest request,
			@ModelAttribute("batchEnquiryDto") BatchEnquiryDto batchEnquiryDto, BindingResult result,
			HttpServletResponse response){
			request.setAttribute("functionMessageKeySelected", "EA002-Assgin Officer");  
		    ModelAndView mv =null;
		     
		    	 
		    String selectedOptions=batchEnquiryDto.getSelectedOptions();
		    
		    List<String> rptIds=null;
		   
		    String ass="";
		    String sta="";
		     	
		List<BatchEnquiryVo> dtolist =appraiseeBatchEnquiryService.searchAppraiseeBatchVoList(batchEnquiryDto.getBatchNameJmesa());
		if(dtolist.size()>0&&!StringUtil.isEmptyString(selectedOptions)){				
			ass=appraiseeBatchConverAndGenTableService.converAssVo(dtolist,selectedOptions);
			sta=appraiseeBatchConverAndGenTableService.converStaVo(dtolist,selectedOptions);
		}
			
		request.setAttribute("batchEnquiryDto",batchEnquiryDto);
			
		//validate reportId
		assignOfficerValidator.checkSelectedReportID(selectedOptions, result);
		assignOfficerValidator.checkSelectedAssignCaseExist(ass, result);
		assignOfficerValidator.checkReportStatusForMultipleSelection(sta, result);
		
		if(result.hasErrors()){
			mv = new ModelAndView("/assess/ListBatchAppraisee");
			mv.addObject("numberOfErrors",result.getErrorCount());

			TableModel tableModel = new TableModel("html", request, response);
			tableModel.setItems(dtolist);
			tableModel.setTable(appraiseeBatchConverAndGenTableService.genBatchAppraiseeListHtml());
			mv.addObject("batchAppraiseeList", tableModel.render());				
			batchEnquiryDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedVo(dtolist));
			return mv;
		}
		
		rptIds=appraiseeBatchConverAndGenTableService.converReportIds(selectedOptions);
		 
		mv = new ModelAndView("forward:/assess/AssignOfficer.do");	
		request.getSession().setAttribute(Constants.SESS_SELECTED_APPRAISEE, rptIds);
		request.getSession().setAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE, Constants.PAGE_ASSIGN_REMAINING);
		request.getSession().setAttribute(Constants.APPRAISEE_OBJECT,batchEnquiryDto);
		
		return mv;
		
//		String batchName = (String) request.getSession().getAttribute("batchName");
//		ModelAndView mv = new ModelAndView();
//		String selBatch = request.getParameter("selBthId");
//		String selAssign = request.getParameter("selAss");
//		String status = request.getParameter("selSta");
//		
//		List<Long> reportIDs = this.getSelectedReportID(selBatch);
//		if(reportIDs == null || (reportIDs != null && reportIDs.size()<=0)){
//			mv = new ModelAndView("/assess/ListBatchAppraisee");
//			result.rejectValue("", "error."+Constants.ERROR_SELECT_ONE_APPRAISEE);
//			mv.addObject("numberOfErrors",result.getErrorCount());
//			this.gethtml(batchName,reportIDs,request,response);
//			return mv;
//		}
//		request.getSession().setAttribute("batchAppraiseeRep", reportIDs);
//		
//		if(getSelectedAssignCaseExist(selAssign)){
//			mv = new ModelAndView("/assess/ListBatchAppraisee");
//			result.rejectValue("", "error."+Constants.ERROR_SELECTED_REPORT_ALREADY_ASSIGNED);
//			mv.addObject("numberOfErrors",result.getErrorCount());
//			this.gethtml(batchName,reportIDs,request,response);
//			return mv;
//		}
//		if(!checkReportStatusForMultipleSelection(status)){
//			mv = new ModelAndView("/assess/ListBatchAppraisee");
//			result.rejectValue("", "error."+Constants.ERROR_ASSIGN_ONE_RECORD_ONLY);
//			mv.addObject("numberOfErrors",result.getErrorCount());
//			this.gethtml(batchName,reportIDs,request,response);
//			return mv;
//		}
//		mv = new ModelAndView("forward:/assess/AssignOfficer.do");
//		List<String> rpList = this.convertToString(reportIDs);
//		request.getSession().setAttribute(Constants.SESS_SELECTED_APPRAISEE, rpList);
//		request.getSession().setAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE, Constants.PAGE_ASSIGN_REMAINING);
//		return mv;
	}
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/backToBatAppraiseeOrListOutstanddingReport.do")
	public ModelAndView back(HttpServletRequest request,
			@ModelAttribute("batchEnquiryDto") BatchEnquiryDto batchEnquiryDto, HttpServletResponse response) {
		
		ModelAndView mv = null;
		String functionNo = batchEnquiryDto.getFunctionNum();
		if("EA002".equals(functionNo)){
			mv = new ModelAndView("/assess/SearchBatchAppraisee");
		}
		if("EA015".equals(functionNo)){
			mv = new ModelAndView("forward:/assess/ListOutstandingReport.do");
		}

		return mv;
	}


}
