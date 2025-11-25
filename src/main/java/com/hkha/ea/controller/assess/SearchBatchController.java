package com.hkha.ea.controller.assess;

import java.sql.SQLException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Util;
import com.hkha.ea.controller.common.ListPageController;
import com.hkha.ea.dto.assess.AppraiseeEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.ReportNextRoleDto;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;
import com.hkha.ea.service.assess.AppraiseeBatchConverAndGenTableService;
import com.hkha.ea.service.assess.AppraiseeBatchEnquiryService;
import com.hkha.ea.service.assess.AppraiseeReportMaintenanceService;
import com.hkha.ea.service.common.NotificationManagerService;
import com.hkha.ea.validator.assess.SearchAppraiseeValidator;

@Controller
public class SearchBatchController extends ListPageController {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SearchBatchController.class.getName());
	//End 20231201 Write log in catalina.out

	@Autowired
	private AppraiseeBatchEnquiryService appraiseeBatchEnquiryService;

	@Autowired
	private AppraiseeReportMaintenanceService appraiseeReportMaintenanceService;

	@Autowired
	private NotificationManagerService notificationManagerService;
	
	@Autowired
	private AppraiseeBatchConverAndGenTableService appraiseeBatchConverAndGenTableService;
	
	@Autowired
	private SearchAppraiseeValidator searchAppraiseeValidator;

    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/initBatch.do")
	public ModelAndView initSearchBatchForm(HttpServletRequest request,@ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto) {
		request.setAttribute("functionMessageKeySelected", "Performance Appraisal Report Maintenance-search batch");
		ModelAndView mv = new ModelAndView("/assess/SearchBatch");
		request.getSession().setAttribute("batchForAssign","N");
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
	@RequestMapping("/assess/searchBatch.do")
	public ModelAndView searchBatchList(HttpServletRequest request,
			@ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto, HttpServletResponse response) {
		request.setAttribute("functionMessageKeySelected", "Performance Appraisal Report Maintenance-search batch");
		ModelAndView mv = new ModelAndView("/assess/ListBatch");
		String flag = request.getParameter("flag");
		if(!Util.isEmptyString(flag) && "Y".equals(flag)){
			searchAppraiseeDto.setSelectedOptions("");
			searchAppraiseeDto.setSelectedOptionsAll("");
		}
		
		if (searchAppraiseeDto != null) {
			List<BatchEnquiryDto> results = appraiseeBatchEnquiryService.searchBatch(searchAppraiseeDto.getBatchNameJmesa(),
					searchAppraiseeDto.getRankJmesa(), searchAppraiseeDto.getPostUnitJmesa(), searchAppraiseeDto.getPostTitleJmesa(),
					SecurityContextHolder.getContext().getAuthentication().getName());

			TableModel tableModel = new TableModel("html", request, response);
			tableModel.setItems(results);
			tableModel.setTable(appraiseeBatchConverAndGenTableService.getHtmlBatchList(searchAppraiseeDto));
			request.setAttribute("batchList", tableModel.render());
			//searchAppraiseeDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedDto(results));
			searchAppraiseeDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverOenPageAllSelectedJmesa(searchAppraiseeDto.getSelOptionsAll()));
			mv.addObject(searchAppraiseeDto);
		}

		return mv;
	}

	/*@RequestMapping("/assess/searchReport")
	public ModelAndView searchReportList(HttpServletRequest request,
			@ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto, HttpServletResponse response) {
		request.setAttribute("functionMessageKeySelected", "Performance Appraisal Report Maintenance-search batch");

		String fromPage = request.getParameter("fromPage");
		String batchName = request.getParameter("batchName");
		
		ModelAndView mv = new ModelAndView("/assess/ListAppraisee");
		List<AppraiseeEnquiryDto> results = new ArrayList<AppraiseeEnquiryDto>();

		if (Util.isEqual(fromPage, PAGE_MODIFY_BATCH_LINK) && !Util.isEmptyString(batchName)) {
			results= appraiseeBatchEnquiryService.searchReport(batchName, null, null, null,
					SecurityContextHolder.getContext().getAuthentication().getName(), false);
			request.getSession().setAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE, PAGE_MODIFY_BATCH_LINK);
		}
		else{
			if (searchAppraiseeDto != null) {
				results = appraiseeBatchEnquiryService.searchReport(
						searchAppraiseeDto.getBatchNameJmesa(), searchAppraiseeDto.getRankJmesa(),searchAppraiseeDto.getPostUnitJmesa(),
						searchAppraiseeDto.getPostTitleJmesa(), SecurityContextHolder.getContext().getAuthentication().getName(),
						true);
				request.getSession().setAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE, PAGE_MODIFY_BATCH);
			}
		}

		TableModel tableModel = new TableModel("html", request, response);
		tableModel.setItems(results);
		tableModel.setTable(appraiseeBatchConverAndGenTableService.getHtmlReportAppraiseeList());
		request.setAttribute("appraiseeList", tableModel.render());
		//request.getSession().setAttribute("fromPage", fromPage);
		//request.getSession().setAttribute("batchName", batchName);
		//request.getSession().setAttribute("DS_BATCH", batchEnquiryDto);
		//mv.addObject(batchEnquiryDto);
		searchAppraiseeDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedJmesa(results));
		return mv;
	}*/

    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/dispatchBatch.do")
	public ModelAndView dispatchBatch(HttpServletRequest request,
			@ModelAttribute("searchAppraiseeDto") SearchAppraiseeDto searchAppraiseeDto, BindingResult result,
			HttpServletResponse response) throws SQLException, EAException {
		log.info("dispatch start");
		ModelAndView mv = new ModelAndView("/assess/ListBatch");
		// BatchEnquiryDto = (SearchAppraiseeDto)
		// request.getSession().getAttribute("searchAppraiseeDto");
		
		String batchid = request.getParameter("selbatId");
		searchAppraiseeValidator.checkBatchId(batchid,result);
		if(result.hasErrors()){
			//mv.addObject("savedType", "");
			mv.addObject("numberOfErrors", result.getErrorCount());
		}else{
			List<String> batchIds = appraiseeBatchConverAndGenTableService.convertToList(batchid);
			List<ReportNextRoleDto> dsNextRole = appraiseeReportMaintenanceService.updateReportRoleByBatch(batchIds);
			if (dsNextRole != null && dsNextRole.size() > 0) {
				try {
					notificationManagerService.generateDispatchNotification(dsNextRole);
				} catch (EAException e) {
					e.printStackTrace();
					log.severe(e.toString());
				}
			}
		}
		
		this.backToListBatch(request,searchAppraiseeDto,response);
		return mv;
	}

	private void backToListBatch(HttpServletRequest request, SearchAppraiseeDto searchAppraiseeDto,HttpServletResponse response) {
		List<BatchEnquiryDto> results = appraiseeBatchEnquiryService.searchBatch(searchAppraiseeDto.getBatchNameJmesa(),
				searchAppraiseeDto.getRankJmesa(), searchAppraiseeDto.getPostUnitJmesa(), searchAppraiseeDto.getPostTitleJmesa(),
				SecurityContextHolder.getContext().getAuthentication().getName());

		TableModel tableModel = new TableModel("html", request, response);
		tableModel.setItems(results);
		tableModel.setTable(appraiseeBatchConverAndGenTableService.getHtmlBatchList(searchAppraiseeDto));
		request.setAttribute("batchList", tableModel.render());
		searchAppraiseeDto.setSelectedOptionsAll(appraiseeBatchConverAndGenTableService.coverAllSelectedDto(results));
		
	}

	

	@Override
	public void generateList() throws EAException {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetList() throws EAException {
		// TODO Auto-generated method stub

	}
}
