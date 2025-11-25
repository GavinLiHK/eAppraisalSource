package com.hkha.ea.controller.assess;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
//20170314
import org.springframework.security.core.context.SecurityContextHolder;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.security.UserEnquiryDAO;
import com.hkha.ea.dto.assess.FinalReportResultDto;
import com.hkha.ea.dto.assess.SearchFinalReportDto;
import com.hkha.ea.dto.common.EaUserDTO;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;
import com.hkha.ea.dto.security.UserGroupEnquiryDTO;
import com.hkha.ea.service.assess.FinalReportService;
import com.hkha.ea.validator.assess.SearchFinalReportValidator;

@Controller
public class ReportStatusMaintenanceController{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(ReportStatusMaintenanceController.class.getName());
	//End 20231201 Write log in catalina.out
	

	@Autowired
	private SearchFinalReportValidator searchFinalReportValidator;
	
	@Autowired
	private FinalReportService finalReportService;
	
	@Autowired
	private UserEnquiryDAO userEnquiryDAO;
	
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/initRptStatusMain.do")
	public ModelAndView reportStatusMaintenanceEntry(HttpServletRequest request,HttpServletResponse response){
		 log.info("***EA018  Entry***");
	     request.setAttribute("functionMessageKeySelected", "Report Status Maintenance");

	     ModelAndView mv =new  ModelAndView ("/assess/SearchReportStatusMain");
	     mv.addObject("searchFinalReportDto",new SearchFinalReportDto());
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
	@RequestMapping("/assess/searchRptStatusMain.do")
	public ModelAndView searchReportStatusMaintenance(HttpServletRequest request,@ModelAttribute("searchFinalReportDto") SearchFinalReportDto searchFinalReportDto, BindingResult result,
			HttpServletResponse response){
		 log.info("***EA018  Search***");
	     request.setAttribute("functionMessageKeySelected", "Report Statu Maintenance");
	     
	     EaUserDTO eaUser = userEnquiryDAO.searchByUserID(SecurityContextHolder.getContext().getAuthentication().getName());
    
	     ModelAndView mv = new ModelAndView("/assess/SearchReportStatusMainResult");
	     /*String flagRSM=searchFinalReportDto.getResultFlag();
	     if(!StringUtil.isEmptyString(flagRSM)&&"revert".equals(flagRSM)){
	    	
	    		mv = new ModelAndView("forward:/assess/revertRptStatusMain.do");
				mv.addObject("searchFinalReportDto",searchFinalReportDto);
				return mv;*/
	    	
			
	     //}else{
	     searchFinalReportValidator.validateSearchFinalReport(searchFinalReportDto, result);
    	 if(result.hasErrors()){
			mv = new ModelAndView("/assess/SearchReportStatusMain");
			mv.addObject("numberOfErrors",result.getErrorCount());
			return mv;
		}
	     //}
	     //edited on 20170315 SU can retrieve Report in EA018
    	 List<FinalReportResultDto> results = new ArrayList<FinalReportResultDto>();
	     if (eaUser.getGroupId() == 4 || eaUser.getGroupId() == 5){
	    	 List<UserEnquiryModelDTO> gmlist = userEnquiryDAO.searchGMUserNameByGroupId(eaUser.getGroupId(), Constants.FUNCTION_ID_CONFIRM_REJECT_REPORT);
		     results = finalReportService.searchReportRSMSU(searchFinalReportDto, gmlist);
	     }else{
	    	 results = finalReportService.searchReportRSM(searchFinalReportDto);
	     }
	     //end edited on 20170315
	     //List<FinalReportResultDto> results = finalReportService.searchReportRSM(searchFinalReportDto);
			TableModel tableModel = new TableModel("finalReportListRSM", request, response);
			tableModel.setItems(results);
			tableModel.setTable(getHtml());
			mv.addObject("finalReportListRSM", tableModel.render());
			mv.addObject("searchFinalReportDto", searchFinalReportDto);
	     
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
	@RequestMapping("/assess/revertRptStatusMain.do")
	public ModelAndView revertReportStatusMaintenance(HttpServletRequest request,
			@ModelAttribute("searchFinalReportDto") SearchFinalReportDto searchFinalReportDto, BindingResult result, 
			HttpServletResponse response){
		
		log.info("***EA018  Revert***");
		ModelAndView mv= new ModelAndView("/assess/SearchReportStatusMainResult");
	    request.setAttribute("functionMessageKeySelected", "Report Status Maintenance");
	    String selectedOptions=searchFinalReportDto.getSelectedOptions();
	    if(Util.isEmptyString(selectedOptions)){
	    	result.rejectValue("", "error."+Constants.ERROR_SELECT_ONE_APPRAISEE);
	    	mv.addObject("numberOfErrors",result.getErrorCount());
	    	//return mv;
	    }else{
	    	String flag=finalReportService.revertReportStatusForRSM(searchFinalReportDto);
		    searchFinalReportDto.setResultFlag(flag);
		    searchFinalReportDto.setSelectedOptions("");
	    }
	    
	    //edited on 20170315 SU can retrieve Report in EA018
	    EaUserDTO eaUser = userEnquiryDAO.searchByUserID(SecurityContextHolder.getContext().getAuthentication().getName());
	    List<FinalReportResultDto> results = new ArrayList<FinalReportResultDto>();
	     if (eaUser.getGroupId() == 4 || eaUser.getGroupId() == 5){
	    	 List<UserEnquiryModelDTO> gmlist = userEnquiryDAO.searchGMUserNameByGroupId(eaUser.getGroupId(), Constants.FUNCTION_ID_CONFIRM_REJECT_REPORT);
		   //get jmesa results
		     results = finalReportService.searchReportRSMSU(searchFinalReportDto, gmlist);
	     }else{
	    	//get jmesa results
	    	 results = finalReportService.searchReportRSM(searchFinalReportDto);
	     }
	    //end edited on 20170315
	    //get jmesa results
	    //List<FinalReportResultDto> results = finalReportService.searchReportRSM(searchFinalReportDto);
	    //gen jmesa list
		TableModel tableModel = new TableModel("finalReportListRSM", request, response);
		tableModel.setItems(results);
		tableModel.setTable(getHtml());
		
		mv.addObject("finalReportListRSM", tableModel.render());
		mv.addObject("searchFinalReportDto", searchFinalReportDto);
	    return  mv;
	}
	private Table getHtml() {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);
		
		HtmlColumn reportId = new HtmlColumn().title(" ");
		
		reportId.setCellEditor(new CellEditor(){
			public Object getValue(Object item, String property, int rowcount){
				String rptId = (String) ItemUtils.getItemValue(item, "reportId");
				HtmlBuilder html = new HtmlBuilder();	
				html.append("<input type='checkbox' id='rptChk_"+rptId+"' name='rptChk_"+rptId+"' value='Y' class='chkGroup' onclick='multiSelectedForJmesa(\"rptChk_\","+rptId+",\"selectedOptions\");' /><input type='text' style='display:none' id='rptId"+rowcount+"' value='"+rptId+"' />");
				return html.toString();
			}
		});
		
		row.addColumn(reportId);

		HtmlColumn number = new HtmlColumn("employeeNumber").title("Employee Number");

		number.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String value = (String) ItemUtils.getItemValue(item, property);
				String role = (String) ItemUtils.getItemValue(item, "reportTemplate");
				String rptId = (String) ItemUtils.getItemValue(item, "reportId");
				html.append("<a href=\"javascript:openReport('" + rptId + "','" + role + "');\">"+value);
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(number);

		HtmlColumn name = new HtmlColumn("name").title("Appraisee Name");
		row.addColumn(name);

		HtmlColumn rank = new HtmlColumn("substantiveRank").title("Rank");
		row.addColumn(rank);
		
		HtmlColumn post = new HtmlColumn("presentPost").title("Post Unit");
		row.addColumn(post);

		HtmlColumn commenceDate = new HtmlColumn("commenceDateSort").title("Commence Date");
		commenceDate.setCellEditor(new CellEditor(){
			public Object getValue(Object item, String property, int rowcount){
				String value = (String) ItemUtils.getItemValue(item, "commenceDate");
				HtmlBuilder html = new HtmlBuilder();	
				//html.append("<input type='hidden' id='"+rowcount+"' value='"+date+"' />"+date);
				html.append(value);
				return html.toString();
			}
		});
		row.addColumn(commenceDate);
		return htmlTable;
	}

}
