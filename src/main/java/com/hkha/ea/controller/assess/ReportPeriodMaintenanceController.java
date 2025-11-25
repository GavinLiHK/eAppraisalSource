package com.hkha.ea.controller.assess;

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
import com.hkha.ea.dto.common.EaUserDTO;
import com.hkha.ea.service.assess.AppraiseeBatchConverAndGenTableService;
import com.hkha.ea.service.assess.ReportUserInfoOrPeriodService;
import com.hkha.ea.service.security.UserEnquireService;
import com.hkha.ea.validator.assess.AppraisalReportPeriodValidator;
import com.hkha.ea.validator.assess.AssignOfficerValidator;

@Controller
public class ReportPeriodMaintenanceController {
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(ReportPeriodMaintenanceController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private UserEnquireService userEnquireService;
	
	@Autowired
	private ReportUserInfoOrPeriodService reportUserInfoOrPeriodService;
	
	@Autowired
	private AppraiseeBatchConverAndGenTableService appraiseeBatchConverAndGenTableService;
	
	@Autowired
	private AssignOfficerValidator assignOfficerValidator;
	
	@Autowired
	private AppraisalReportPeriodValidator appraisalReportPeriodValidator;
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/initRptPeriodMain.do")
	public ModelAndView reportPeriodMaintenanceEntry(HttpServletRequest request,HttpServletResponse response){
		 log.info("***EA016  Entry***");
	     request.setAttribute("functionMessageKeySelected", "Report Period Maintenance");
	     //test
	     BatchEnquiryDto batchEnquiryDto = new BatchEnquiryDto();
	     batchEnquiryDto.setFunctionNum("EA016");
	     ModelAndView mv =new  ModelAndView ("/assess/SearchReportUserInfoOrPeriod");
	     mv.addObject(batchEnquiryDto);
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
	@RequestMapping("/assess/SearchRpUserInfoOrPeriod.do")
	public ModelAndView searchRpUserInfoOrPeriod(HttpServletRequest request,
			@ModelAttribute("batchEnquiryDto") BatchEnquiryDto batchEnquiryDto, HttpServletResponse response) {
		request.setAttribute("functionMessageKeySelected", "Report Period Maintenance - Search batch");
			
		ModelAndView mv = new ModelAndView("/assess/SearchReportUserInfoOrPeriod");
		
		EaUserDTO eaUser = userEnquireService.searchByUserID(SecurityContextHolder.getContext().getAuthentication().getName());
		if (batchEnquiryDto != null) {
			List<BatchEnquiryDto> results = reportUserInfoOrPeriodService.searchRpUserInfoOrPeriod(
					SecurityContextHolder.getContext().getAuthentication().getName(), batchEnquiryDto.getBatchName(),
					batchEnquiryDto.getEmployeeNum(), batchEnquiryDto.getName(), batchEnquiryDto.getRank(),
					batchEnquiryDto.getPostUnit(), batchEnquiryDto.getPostTitle(), eaUser.getGroupId());

			TableModel tableModel = new TableModel("html", request, response);
			tableModel.setItems(results);
			tableModel.setTable(appraiseeBatchConverAndGenTableService.getSearchBatchAppraiseeHtml());
			mv.addObject("rptUserInfoOrPeriodList", tableModel.render());
			
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
	@RequestMapping("/assess/getReportsAppraiseeList.do")
	public ModelAndView getBatchAppraiseeList(HttpServletRequest request,
			@ModelAttribute("batchEnquiryDto") BatchEnquiryDto batchEnquiryDto,HttpServletResponse response) {

		ModelAndView mv = new ModelAndView("/assess/ListReportUserInfoOrPeriod");
		String frompage = (String) request.getSession().getAttribute(Constants.FROM_ASSIGN_OFFICER);
		
		if(!Util.isEmptyString(frompage) && Constants.YES.equals(frompage))
			batchEnquiryDto = (BatchEnquiryDto) request.getSession().getAttribute(Constants.APPRAISEE_OBJECT);
		
		if (batchEnquiryDto != null) {		
			List<BatchEnquiryVo> volist =reportUserInfoOrPeriodService.searchAppraiseeBatchList(batchEnquiryDto.getBatchNameJmesa());
				
			TableModel tableModel = new TableModel("html", request, response);
			tableModel.setItems(volist);
			tableModel.setTable(appraiseeBatchConverAndGenTableService.genBatchAppraiseeListHtml());
			mv.addObject("reportAppraiseeList", tableModel.render());
			
			batchEnquiryDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedVo(volist));		
		}
		
		 //request.setAttribute("batchEnquiryDto",batchEnquiryDto);
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
	@RequestMapping("/assess/changeReportPeriods.do")
	public ModelAndView changeReportPeriods(HttpServletRequest request,
			@ModelAttribute("batchEnquiryDto") BatchEnquiryDto batchEnquiryDto, BindingResult result,
			HttpServletResponse response){
		ModelAndView mv = null;
		String selectedOptions=batchEnquiryDto.getSelectedOptions();
		
		List<BatchEnquiryVo> volist =reportUserInfoOrPeriodService.searchAppraiseeBatchList(batchEnquiryDto.getBatchNameJmesa());
		//validate reportId
		assignOfficerValidator.checkSelectedReportID(selectedOptions, result);
		if(result.hasErrors()){
			mv = new ModelAndView("/assess/ListReportUserInfoOrPeriod");
			mv.addObject("numberOfErrors",result.getErrorCount());

			TableModel tableModel = new TableModel("html", request, response);
			tableModel.setItems(volist);
			tableModel.setTable(appraiseeBatchConverAndGenTableService.genBatchAppraiseeListHtml());
			mv.addObject("reportAppraiseeList", tableModel.render());				
			batchEnquiryDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedVo(volist));
			request.setAttribute("batchEnquiryDto",batchEnquiryDto);
			return mv;
		}
		List<String> rptIds=appraiseeBatchConverAndGenTableService.converReportIds(selectedOptions);

		if(volist.size()>0){				
			for(BatchEnquiryVo bvo:volist){
				if(bvo.getReportId()==Long.parseLong(rptIds.get(0))){
					batchEnquiryDto.setCommenceDate(bvo.getCommenceDate());
					batchEnquiryDto.setEndDate(bvo.getEndDate());
				}
			}
		}
		request.setAttribute("batchEnquiryDto",batchEnquiryDto);
		mv = new ModelAndView("/assess/ChangeReportPeriod");
		return mv;
//		String batchName = (String) request.getSession().getAttribute("batchName");
//		ModelAndView mv = new ModelAndView();
//		String selBatch = request.getParameter("selBthId");
//		String selComDate = request.getParameter("selComDate");
//		String selEndDate = request.getParameter("selEndDate");
//		
//		List<Long> reportIDs = appraiseeBatchConverAndGenTableService.getSelectedReportID(selBatch);
//		if(reportIDs == null || (reportIDs != null && reportIDs.size()<=0)){
//			mv = new ModelAndView("/assess/ListReportUserInfoOrPeriod");
//			result.rejectValue("", "error."+Constants.ERROR_SELECT_ONE_APPRAISEE);
//			mv.addObject("numberOfErrors",result.getErrorCount());
//		//	this.gethtml(batchName,reportIDs,request,response);
//			return mv;
//		}
//		List<String> comDates = appraiseeBatchConverAndGenTableService.convertToList(selComDate);
//		List<String> endDates = appraiseeBatchConverAndGenTableService.convertToList(selEndDate);
//		batchEnquiryVo.setCommenceDate(comDates.get(0));
//		batchEnquiryVo.setEndDate(endDates.get(0));
//		mv = new ModelAndView("/assess/ChangeReportPeriod");
	//	return mv;
	}
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/saveAppraisalReportPeriod.do")
	public ModelAndView save(HttpServletRequest request,
			@ModelAttribute("batchEnquiryDto") BatchEnquiryDto batchEnquiryDto, BindingResult result,
			HttpServletResponse response){
		ModelAndView mv =null;
		String selectedOptions=batchEnquiryDto.getSelectedOptions();
		List<String> rptIds=appraiseeBatchConverAndGenTableService.converReportIds(selectedOptions);
		List<BatchEnquiryVo> volist =reportUserInfoOrPeriodService.searchAppraiseeBatchList(batchEnquiryDto.getBatchNameJmesa());
		String selectedEmployees="";
		if(volist.size()>0&&!StringUtil.isEmptyString(selectedOptions)){				
			selectedEmployees=appraiseeBatchConverAndGenTableService.converEmpVo(volist,selectedOptions);			
		}
		
		appraisalReportPeriodValidator.checkAppraisalPeriodMandatory(batchEnquiryDto.getCommenceDate(), batchEnquiryDto.getEndDate(), result);
		
		if(result.hasErrors()){
			mv = new ModelAndView("/assess/ChangeReportPeriod");
			mv.addObject("numberOfErrors",result.getErrorCount());
			request.setAttribute("batchEnquiryDto",batchEnquiryDto);
			return mv;
		}
		
		appraisalReportPeriodValidator.checkAppraisalPeriodOverlappedWithExistingReport(batchEnquiryDto.getCommenceDate(), batchEnquiryDto.getEndDate(), selectedEmployees, result);
		
		if(result.hasErrors()){
			mv = new ModelAndView("/assess/ChangeReportPeriod");
			mv.addObject("numberOfErrors",result.getErrorCount());
			request.setAttribute("batchEnquiryDto",batchEnquiryDto);
			return mv;
		}
		
		String flag="success";
		try {
			reportUserInfoOrPeriodService.saveChangeAppraisalPeriodDate(rptIds, batchEnquiryDto.getCommenceDate(), batchEnquiryDto.getEndDate());
		} catch (Exception e) {
			flag="fail";
			e.printStackTrace();
			log.severe(e.toString());
		}
		
		mv = new ModelAndView("/assess/ChangeReportPeriod");
		mv.addObject("saveType",flag);
		request.setAttribute("batchEnquiryDto",batchEnquiryDto);
		return mv;

	}


//	private void gethtml(String batchName,List<Long> reportIDs,HttpServletRequest request,HttpServletResponse response){
//		List<BatchEnquiryDto> results = appraiseeBatchEnquiryService.searchAppraiseeBatchList(batchName);
//
//		TableModel tableModel = new TableModel("html", request, response);
//		tableModel.setItems(results);
//		tableModel.setTable(getBatchAppraiseeListHtml(reportIDs));
//		request.setAttribute("reportAppraiseeList", tableModel.render());
//	}
	
	
	

}
