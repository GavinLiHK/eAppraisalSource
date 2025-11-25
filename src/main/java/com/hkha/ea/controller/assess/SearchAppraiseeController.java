package com.hkha.ea.controller.assess;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Util;
import com.hkha.ea.domain.EaBatch;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.dto.assess.AppraiseeEnquiryDto;
import com.hkha.ea.dto.assess.ReportNextRoleDto;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;
import com.hkha.ea.dto.workflow.WorkflowEnquiryDTO;
import com.hkha.ea.service.assess.AppraiseeBatchConverAndGenTableService;
import com.hkha.ea.service.assess.AppraiseeBatchEnquiryService;
import com.hkha.ea.service.assess.AppraiseeReportMaintenanceService;
import com.hkha.ea.service.assess.AssignOfficerService;
import com.hkha.ea.service.common.NotificationManagerService;
import com.hkha.ea.service.workflow.WorkflowTemplateMaintenanceService;
import com.hkha.ea.validator.assess.SearchAppraiseeValidator;

@Controller
public class SearchAppraiseeController {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SearchAppraiseeController.class.getName());
	//End 20231201 Write log in catalina.out

	@Autowired
	private WorkflowTemplateMaintenanceService workflowTemplateMaintenanceService;

	@Autowired
	private AppraiseeReportMaintenanceService appraiseeReportMaintenanceService;

	@Autowired
	private SearchAppraiseeValidator searchAppraiseeValidator;

	@Autowired
	private NotificationManagerService notificationManagerService;

	@Autowired
	private AssignOfficerService assignOfficerService;
	
	@Autowired
	private AppraiseeBatchEnquiryService appraiseeBatchEnquiryService;

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
	@RequestMapping("/assess/initSearchAppraisee.do")
	public ModelAndView initSearchAppraiseeForm(HttpServletRequest request,
			@ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto, HttpServletResponse response) {
		request.setAttribute("functionMessageKeySelected", "Performance Appraisal Report Maintenance");
		ModelAndView mv = new ModelAndView("/assess/SearchAppraisee");
		List<WorkflowEnquiryDTO> wfList = workflowTemplateMaintenanceService
				.workflowTemplateSearch(new WorkflowEnquiryDTO());
		Date curDate = DateTimeUtil.getTodayDate();
		if (searchAppraiseeDto == null) {
			searchAppraiseeDto = new SearchAppraiseeDto();
			searchAppraiseeDto.setTrackDate(DateTimeUtil.date2String(curDate));
			searchAppraiseeDto.setAppraisalPeriodStart(Constants.YEAR_START_DATE + "/" + DateTimeUtil.getYear(curDate));
			searchAppraiseeDto.setAppraisalPeriodEnd(Constants.YEAR_END_DATE + "/" + DateTimeUtil.getYear(curDate));
		}

		if (searchAppraiseeDto != null) {
			if (searchAppraiseeDto.getTrackDate() == null)
				searchAppraiseeDto.setTrackDate(DateTimeUtil.date2String(curDate));
			if (searchAppraiseeDto.getAppraisalPeriodStart() == null)
				searchAppraiseeDto
						.setAppraisalPeriodStart(Constants.YEAR_START_DATE + "/" + DateTimeUtil.getYear(curDate));
			if (searchAppraiseeDto.getAppraisalPeriodEnd() == null)
				searchAppraiseeDto.setAppraisalPeriodEnd(Constants.YEAR_END_DATE + "/" + DateTimeUtil.getYear(curDate));
		}

		request.getSession().setAttribute("workflowTemList", wfList);
		searchAppraiseeDto.setIsSearch("Y");
		searchAppraiseeDto.setMode(Constants.PAGE_CREATE_BATCH);
		mv.addObject(searchAppraiseeDto);
		//log.info("init page workflow list===" + request.getSession().getAttribute("workflowTemList"));
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
	@RequestMapping("/assess/SearchAppraisee.do")
	public ModelAndView SearchAppraisee(HttpServletRequest request,
			@ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto, BindingResult result,
			HttpServletResponse response) throws Exception {

		ModelAndView mv = new ModelAndView("/assess/ListAppraisee");
		String frompage = (String) request.getSession().getAttribute(Constants.FROM_ASSIGN_OFFICER);
		
		if(!Util.isEmptyString(frompage) && Constants.YES.equals(frompage))
			searchAppraiseeDto = (SearchAppraiseeDto) request.getSession().getAttribute(Constants.APPRAISEE_OBJECT);
		
		String mode = searchAppraiseeDto.getMode();
		if(Constants.PAGE_CREATE_BATCH.equals(mode)){
			if ("Y".equals(searchAppraiseeDto.getIsSearch())) {
				request.getSession().setAttribute("workflowId", searchAppraiseeDto.getWfTemplateId());
				this.setValuesToJmesa(searchAppraiseeDto);
				searchAppraiseeValidator.validateSearchAppraisee(searchAppraiseeDto, result);
				if (result.hasErrors()) {
					mv = new ModelAndView("/assess/SearchAppraisee");
					mv.addObject("numberOfErrors", result.getErrorCount());
					return mv;
				}
			}
			
			searchAppraiseeDto.setIsSearch("N");
			mv = this.getAppraiseeListForCreat(request, searchAppraiseeDto, response, mv);
			
		}else if(Constants.PAGE_MODIFY_BATCH_LINK.equals(mode)){
			//selected record for assign officer
			//if(!Util.isEmptyString(frompage) && Constants.YES.equals(frompage)){
				
			//}else{
			//	searchAppraiseeDto.setSelectedOptions(null);
			//}
			//
			mv = this.getAppraiseeListForBatch(request, searchAppraiseeDto, response, mv);
			
		}else{
			mv = this.getAppraiseeListForReport(request, searchAppraiseeDto, response, mv);
			
		}
		searchAppraiseeDto.setMode(mode);
		mv.addObject("searchAppraiseeDto",searchAppraiseeDto);
		request.getSession().setAttribute(Constants.APPRAISEE_OBJECT,searchAppraiseeDto);
		request.getSession().setAttribute(Constants.FROM_ASSIGN_OFFICER,Constants.NO);
		request.getSession().setAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE,mode);
		return mv;
	}

	private void setValuesToJmesa(SearchAppraiseeDto searchAppraiseeDto) {
		String year = searchAppraiseeDto.getYear();
		String rank = searchAppraiseeDto.getRank();
		String postUnit = searchAppraiseeDto.getPostUnit();
		String trackDate = searchAppraiseeDto.getTrackDate();
		String reportGenerated = searchAppraiseeDto.getReportGenerated();
		String appraisalPeriodStart = searchAppraiseeDto.getAppraisalPeriodStart();
		String appraisalPeriodEnd = searchAppraiseeDto.getAppraisalPeriodEnd();
		String employeeNumber = String.valueOf(searchAppraiseeDto.getEmployeeNumber());

		searchAppraiseeDto.setYearJmesa(year);
		searchAppraiseeDto.setRankJmesa(rank);
		searchAppraiseeDto.setPostUnitJmesa(postUnit);
		searchAppraiseeDto.setTrackDateJmesa(trackDate);
		searchAppraiseeDto.setReportGeneratedJmesa(reportGenerated);
		searchAppraiseeDto.setAppraisalPeriodEndJmesa(appraisalPeriodEnd);
		searchAppraiseeDto.setAppraisalPeriodStartJmesa(appraisalPeriodStart);
		searchAppraiseeDto.setEmployeeNumberJmesa(employeeNumber);
	}

	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/back.do")
	public ModelAndView back(HttpServletRequest request, @ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto, HttpServletResponse response) {
		
		String fromPage = searchAppraiseeDto.getMode();
		ModelAndView mv = new ModelAndView();

		if (Util.isEqual(fromPage, Constants.PAGE_MODIFY_BATCH_LINK)) {
			mv = new ModelAndView("forward:/assess/searchBatch.do?flag=Y");
		} else if (Util.isEqual(fromPage, Constants.PAGE_CREATE_BATCH)) {
			mv = new ModelAndView("forward:/assess/initSearchAppraisee.do");
		} else {
			mv = new ModelAndView("/assess/SearchBatch");
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
	@RequestMapping("/assess/handleByExcel.do")
	public ModelAndView handleByExcel(HttpServletRequest request,
			@ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto, BindingResult result,
			HttpServletResponse response) throws Exception {
		log.info("handleByExcel start");
		ModelAndView mv = new ModelAndView("/assess/ListAppraisee");
		
		String reportId = request.getParameter("selRpId");
		List<Long> reportIds = this.validateReportId(reportId,result,Constants.ACTION_TYPE_HANDLE_BY_EXCEL);
		if(result.hasErrors()){
			mv.addObject("savedType", "");
			mv.addObject("numberOfErrors", result.getErrorCount());
		}else{
			List<ReportNextRoleDto> dsNextRole = appraiseeReportMaintenanceService.handleByExcel(reportIds);
		}
		
		mv = this.getAppraiseeList(mv,request,searchAppraiseeDto, response);
		return mv;
	}

	private List<Long> validateReportId(String reportId, BindingResult result, int actionType) {
		if (Util.isEmptyString(reportId)) {
			result.rejectValue("batchId", "error." + Constants.ERROR_SELECT_ONE_APPRAISEE);
			return null;
		}
		String[] reportIdArr = reportId.split(",");
		List<Long> reportIds = validateDispatch(reportIdArr, actionType, result);
		if (result.hasErrors()) {
			return null;
		}
		return reportIds;
	}

	private ModelAndView getAppraiseeList(ModelAndView mv, HttpServletRequest request, SearchAppraiseeDto searchAppraiseeDto, HttpServletResponse response) throws Exception {
		//ModelAndView mv = new ModelAndView("assess/ListAppraisee");
		String mode = searchAppraiseeDto.getMode();
		
		if (Constants.PAGE_CREATE_BATCH.equals(mode)) {
			mv = this.getAppraiseeListForCreat(request, searchAppraiseeDto, response, mv);
		}else if (Constants.PAGE_MODIFY_BATCH_LINK.equals(mode)){
			mv = this.getAppraiseeListForBatch(request, searchAppraiseeDto, response, mv);
		}else{
			mv = this.getAppraiseeListForReport(request, searchAppraiseeDto, response, mv);
		}
	
		return mv;
	}
	
	private ModelAndView getAppraiseeListForReport(HttpServletRequest request, SearchAppraiseeDto searchAppraiseeDto,
			HttpServletResponse response, ModelAndView mv) {
		
		List<AppraiseeEnquiryDto> results = appraiseeBatchEnquiryService.searchReport(
				searchAppraiseeDto.getBatchNameJmesa(), searchAppraiseeDto.getRankJmesa(),searchAppraiseeDto.getPostUnitJmesa(),
				searchAppraiseeDto.getPostTitleJmesa(), SecurityContextHolder.getContext().getAuthentication().getName(),
				true);
		
		TableModel tableModel = new TableModel("html", request, response);
		tableModel.setItems(results);
		tableModel.setTable(appraiseeBatchConverAndGenTableService.getHtmlReportAppraiseeList(searchAppraiseeDto));
		request.setAttribute("appraiseeList", tableModel.render());
		//searchAppraiseeDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedJmesa(results));
		searchAppraiseeDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverOenPageAllSelectedJmesa(searchAppraiseeDto.getSelOptionsAll()));
		return mv;
	}

	private ModelAndView getAppraiseeListForBatch(HttpServletRequest request, SearchAppraiseeDto searchAppraiseeDto,
			HttpServletResponse response, ModelAndView mv) {
		
		List<AppraiseeEnquiryDto> results = appraiseeBatchEnquiryService.searchReport(searchAppraiseeDto.getBatchNameLink(), null, null, null,
				SecurityContextHolder.getContext().getAuthentication().getName(), false);
		
		TableModel tableModel = new TableModel("html", request, response);
		tableModel.setItems(results);
		tableModel.setTable(appraiseeBatchConverAndGenTableService.getHtmlReportAppraiseeList(searchAppraiseeDto));
		request.setAttribute("appraiseeList", tableModel.render());
		//searchAppraiseeDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedJmesa(results));
		searchAppraiseeDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverOenPageAllSelectedJmesa(searchAppraiseeDto.getSelOptionsAll()));
		return mv;
	}

	private ModelAndView getAppraiseeListForCreat(HttpServletRequest request, SearchAppraiseeDto searchAppraiseeDto,
			HttpServletResponse response, ModelAndView mv) throws Exception {
		if (StringUtils.isBlank(searchAppraiseeDto.getTrackDateJmesa()))
			searchAppraiseeDto.setTrackDateJmesa(DateTimeUtil.date2String(DateTimeUtil.getTodayDate()));

		log.info("searchAppraiseeDto: "+searchAppraiseeDto.getEmployeeNumber()+searchAppraiseeDto.getRank());
		log.info("searchAppraiseeDto Jemsa: "+searchAppraiseeDto.getEmployeeNumberJmesa()+searchAppraiseeDto.getRankJmesa());
		
		List<AppraiseeEnquiryDto> results = appraiseeReportMaintenanceService.search(
				SecurityContextHolder.getContext().getAuthentication().getName(), searchAppraiseeDto.getYearJmesa(), searchAppraiseeDto.getTrackDateJmesa(), searchAppraiseeDto.getRankJmesa(), searchAppraiseeDto.getPostUnitJmesa(),
				searchAppraiseeDto.getEmployeeNumberJmesa(), searchAppraiseeDto.getAppraisalPeriodStartJmesa(), searchAppraiseeDto.getAppraisalPeriodEndJmesa(), searchAppraiseeDto.getReportGeneratedJmesa(), true);

		TableModel tableModel = new TableModel("html", request, response);
		tableModel.setItems(results);
		tableModel.setTable(appraiseeBatchConverAndGenTableService.getHtmlAppraiseeList(searchAppraiseeDto));
		mv.addObject("appraiseeList", tableModel.render());
		//searchAppraiseeDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedJmesa(results));
		searchAppraiseeDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverOenPageAllSelectedJmesa(searchAppraiseeDto.getSelOptionsAll()));
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
	@RequestMapping("/assess/dispatch.do")
	public ModelAndView dispatch(HttpServletRequest request,
			@ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto, BindingResult result,
			HttpServletResponse response) throws Exception {
		log.info("dispatch start");
		ModelAndView mv = new ModelAndView("/assess/ListAppraisee");
		
		String reportId = request.getParameter("selRpId");
		List<Long> reportIds = this.validateReportId(reportId,result,Constants.ACTION_TYPE_DISPATCH);
		if (result.hasErrors()) {
			mv.addObject("savedType", "");
			mv.addObject("numberOfErrors", result.getErrorCount());
		}else{
			List<ReportNextRoleDto> dsNextRole = appraiseeReportMaintenanceService.updateReport(reportIds);
			if (dsNextRole != null && dsNextRole.size() > 0) {
				try {
					notificationManagerService.generateDispatchNotification(dsNextRole);
				} catch (EAException e) {
					e.printStackTrace();
					log.severe(e.toString());
				}
			}
		}

		mv = this.getAppraiseeList(mv,request,searchAppraiseeDto, response);
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
	@RequestMapping("/assess/deleteAppraisee.do")
	public ModelAndView deleteAppraisee(HttpServletRequest request,
			@ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto, BindingResult result,
			HttpServletResponse response) throws Exception {
		log.info("deleteAppraisee start");
		ModelAndView mv = new ModelAndView("/assess/ListAppraisee");
		String reportId = request.getParameter("selRpId");
		String batchID = request.getParameter("selBthId");
		List<Long> reportIds = this.validateReportIdAndBatchId(reportId,batchID,result,Constants.ACTION_TYPE_DELETE);

		if(result.hasErrors()){
			mv.addObject("savedType", "");
			mv.addObject("numberOfErrors", result.getErrorCount());
		}else{
			String[] batArr = batchID.split(",");
			if (Util.isEmptyString(batArr[0])
					|| (!Util.isEmptyString(batArr[0]) && Util.isEmptyString(batArr[0].split(";")[0]))) {
				result.rejectValue("batchId", "error." + Constants.ERROR_SELECT_ONE_APPRAISEE);
				mv.addObject("savedType", "");
				mv.addObject("numberOfErrors", result.getErrorCount());
			}else{
				appraiseeReportMaintenanceService.deleteReport(reportIds, batArr[0].split(";")[0]);
			}
		}
		
		mv = this.getAppraiseeList(mv,request,searchAppraiseeDto, response);
		return mv;
	}

	private List<Long> validateReportIdAndBatchId(String reportId, String batchID, BindingResult result, int actionTypeDelete) {
		if (Util.isEmptyString(reportId) || Util.isEmptyString(batchID)) {
			result.rejectValue("batchId", "error." + Constants.ERROR_SELECT_ONE_APPRAISEE);
			return null;
		}
		String[] reportIdArr = reportId.split(",");
		List<Long> reportIds = validateDispatch(reportIdArr, actionTypeDelete, result);
		if (result.hasErrors()) {

			return null;
		}
		return reportIds;
	}

    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/gotoAssignOfficer.do")
	public ModelAndView selectAll(HttpServletRequest request,
			@ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto, BindingResult result,
			HttpServletResponse response) throws Exception {
		log.info("gotoAssignOfficer start");
		ModelAndView mv = new ModelAndView();
		String mode = searchAppraiseeDto.getMode();
		
		if (Constants.PAGE_CREATE_BATCH.equals(mode)) {
			mv = new ModelAndView("/assess/ListAppraisee");
			
			String em = request.getParameter("selAf");
			List<String> employee = converToStringList(em);
			if (!checkReportNotGenerated(em)) {
				result.rejectValue("reportGenerated", "error." + Constants.ERROR_REPORT_GENERATED);
				
				mv = this.getAppraiseeListForCreat(request, searchAppraiseeDto, response, mv);
				return mv;
			}
			if (employee == null || employee.size() == 0) {
				result.rejectValue("employeeNumber", "error." + Constants.ERROR_SELECT_ONE_APPRAISEE);
				
				mv = this.getAppraiseeListForCreat(request, searchAppraiseeDto, response, mv);
				return mv;
			}
			String wfId = (String) request.getSession().getAttribute("workflowId");
			EaWorkflow dsWF = workflowTemplateMaintenanceService.searchById(Long.valueOf(wfId));
			if (dsWF == null) {
				result.rejectValue("", "error." + Constants.ERROR_WORKFLOW_NOT_FOUND);
				mv = this.getAppraiseeListForCreat(request, searchAppraiseeDto, response, mv);
				return mv;
			}
			request.getSession().setAttribute(Constants.SESS_SELECTED_APPRAISEE, employee);
			mv = new ModelAndView("forward:/assess/AssignOfficer.do");
		}else{
			mv = new ModelAndView("/assess/ListAppraisee");
			String reportIDs = request.getParameter("selRpId");
			String selBthId = request.getParameter("selBthId");

			List<String> rpids = converToStringList(reportIDs);
			if (rpids == null || rpids.size()==0) {
				result.rejectValue("employeeNumber", "error." + Constants.ERROR_SELECT_ONE_APPRAISEE);
				if (Constants.PAGE_MODIFY_BATCH_LINK.equals(mode))
					mv = this.getAppraiseeListForBatch(request, searchAppraiseeDto, response, mv);
					//new ModelAndView("forward://assess/searchReport.do?fromPage="
							//+ Constants.PAGE_MODIFY_BATCH_LINK + "&batchName=" + selBthId);
				else
					mv = this.getAppraiseeListForReport(request, searchAppraiseeDto, response, mv);
				return mv;
			}else{
				if (!checkReportStatusForMultipleSelection(rpids, selBthId, result)) {
					if (Constants.PAGE_MODIFY_BATCH_LINK.equals(mode))
						mv = this.getAppraiseeListForBatch(request, searchAppraiseeDto, response, mv);
					else
						mv = this.getAppraiseeListForReport(request, searchAppraiseeDto, response, mv);
					return mv;
				}
				EaReport rp = assignOfficerService.searchByReportID(rpids.get(0));
				if (rp == null) {
					result.rejectValue("", "error." + Constants.ERROR_REPORT_NOT_FOUND);
					if (Constants.PAGE_MODIFY_BATCH_LINK.equals(mode))
						mv = this.getAppraiseeListForBatch(request, searchAppraiseeDto, response, mv);
					else
						mv = this.getAppraiseeListForReport(request, searchAppraiseeDto, response, mv);
					return mv;
				}

				EaBatch batch = assignOfficerService.searchByID(rp.getBatchId());
				if (batch == null) {
					result.rejectValue("", "error." + Constants.ERROR_BATCH_ID_NOT_FOUND);
					if (Constants.PAGE_MODIFY_BATCH_LINK.equals(mode))
						mv = this.getAppraiseeListForBatch(request, searchAppraiseeDto, response, mv);
					else
						mv = this.getAppraiseeListForReport(request, searchAppraiseeDto, response, mv);
					return mv;
				}
				request.getSession().setAttribute(Constants.SESS_SELECTED_APPRAISEE, rpids);
				mv = new ModelAndView("forward:/assess/AssignOfficer.do");
			}
		}
		request.getSession().setAttribute(Constants.APPRAISEE_OBJECT,searchAppraiseeDto);
		request.getSession().setAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE,mode);
		return mv;
	}

	/**
	 * To check all selected report(s) is/are valid to be modified
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean checkReportStatusForMultipleSelection(List<String> rpids, String selBthId, Errors errors)
			throws EAException {
		if (rpids != null && rpids.size() <= 1)
			return true;

		String batchID = null;
		String[] batArr = selBthId.split(",");
		for (int i = 0; i < rpids.size(); i++) {
			if (batArr[i] != null) {
				String[] s = batArr[i].split(";");
				if(s[1]!=null){
					String status = s[1];
				}
				String tempBatchID = s[0];
				/*
				if (!(Util.isEqual(status, Constants.STATUS_INITIAL) || Util.isEqual(status, Constants.STATUS_CD))) {
					errors.rejectValue("status", "error." + Constants.ERROR_GM_ASSIGN_ONE_RECORD_ONLY);
					return false;
				}
				*/
				if (Util.isEmptyString(batchID))
					batchID = tempBatchID;
				else {
					if (!(Util.isEqual(batchID, tempBatchID))) {
						errors.rejectValue("batchId", "error." + Constants.ERROR_DIFFERENT_BATCH_SELECTION);
						return false;
					}
				}
			}

		}
		return true;
	}

	/**
	 * Checking Only "N" in report generation can do assign officer in CREATE
	 * BATCH!!!
	 * 
	 * @return
	 * @throws EAException
	 */
	private boolean checkReportNotGenerated(String em) throws EAException {
		try {
			if (!Util.isEmptyString(em)) {
				String[] s = em.split(",");
				for (int i = 0; i < s.length; i++) {
					String s1 = s[i];
					if (!Util.isEmptyString(s1)) {
						String[] emp = s1.split(";");
						if (emp != null) {
							if (!Util.isEqual(emp[1], Constants.NO)) {
								return false;
							}
						}

					}
				}
			}
			return true;
		} catch (Exception e) {
			throw new EAException(e);
		}
	}

	private List<String> converToStringList(String em) {
		//List<String> list = null;
		List<String> list = new ArrayList<String>();
		if (!Util.isEmptyString(em)) {
			String[] s = em.split(",");
			for (int i = 0; i < s.length; i++) {
				String s1 = s[i];
				if (!Util.isEmptyString(s1)) {
					String[] emp = s1.split(";");
					if (emp != null) {
						if (!Util.isEmptyString(emp[0])) {
							//list = new ArrayList<String>();
							list.add(emp[0]);
						}
					}

				}
			}
		}
		return list;
	}

	private List<Long> validateDispatch(String[] reportIdArr, int type, Errors errors) {
		List<Long> reportIDs = new ArrayList<Long>();
		if (reportIdArr != null && reportIdArr.length > 0) {
			for (int i = 0; i < reportIdArr.length; i++) {
				String reportId = reportIdArr[i];
				if (!Util.isEmptyString(reportId)) {
					String[] arr = reportId.split(";");
					if (!Constants.YES.equalsIgnoreCase(arr[1])) {
						switch (type) {
						case Constants.ACTION_TYPE_DISPATCH:
							errors.rejectValue("", "error." + Constants.ERROR_REPORT_CANNOT_DISPATCH);
							return null;
							// throw new
							// EAException(Constants.ERROR_REPORT_CANNOT_DISPATCH);

						case Constants.ACTION_TYPE_DELETE:
							if (Constants.NO.equalsIgnoreCase(arr[1])) {
								errors.rejectValue("", "error." + Constants.ERROR_REPORT_CANNOT_DELETE);
								return null;
								// throw new
								// EAException(Constants.ERROR_REPORT_CANNOT_DELETE);
							} else if (!Util.isEmptyString(arr[0])) {
								reportIDs.add(Long.valueOf(arr[0]));
							}
							break;

						default:
							errors.rejectValue("", "error." + Constants.ERROR_REPORT_CANNOT_HANDLE_BY_EXCEL);
							return null;
						// throw new
						// EAException(Constants.ERROR_REPORT_CANNOT_HANDLE_BY_EXCEL);
						}
					} else {
						if (!Util.isEmptyString(arr[0])) {
							reportIDs.add(Long.valueOf(arr[0]));
						}
					}
				}
			}
		}
		return reportIDs;
	}

}
