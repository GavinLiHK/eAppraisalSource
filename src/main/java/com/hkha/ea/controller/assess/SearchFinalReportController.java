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

import com.hkha.ea.common.Util;
import com.hkha.ea.dto.assess.FinalReportResultDto;
import com.hkha.ea.dto.assess.SearchFinalReportDto;
import com.hkha.ea.service.assess.FinalReportService;
import com.hkha.ea.validator.assess.SearchFinalReportValidator;


@Controller
public class SearchFinalReportController {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SearchFinalReportController.class.getName());
	//End 20231201 Write log in catalina.out

	@Autowired
	private SearchFinalReportValidator searchFinalReportValidator;
	
	@Autowired
	private FinalReportService finalReportService;

    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/assess/initSearchFinalReport.do")
	public ModelAndView initSearchFinalReport(HttpServletRequest request,@ModelAttribute("searchFinalReportDto") SearchFinalReportDto searchFinalReportDto) throws Exception {
		ModelAndView mv = new ModelAndView("/assess/SearchFinalReport");

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
	@RequestMapping("/assess/searchFinalReport.do")
	public ModelAndView searchFinalReport(HttpServletRequest request,
			@ModelAttribute("searchFinalReportDto") SearchFinalReportDto searchFinalReportDto, BindingResult result,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/assess/SearchFinalReportResult");
		String isSearch = (String)request.getParameter("isSearch");
		if(!Util.isEmptyString(isSearch) && "Y".equals(isSearch)){
			searchFinalReportValidator.validateSearchFinalReport(searchFinalReportDto, result);
			if(result.hasErrors()){
				mv = new ModelAndView("/assess/SearchFinalReport");
				mv.addObject("numberOfErrors",result.getErrorCount());
				return mv;
			}
			this.setValueJmesa(searchFinalReportDto);
		}
		
		List<FinalReportResultDto> results = finalReportService.searchReport(searchFinalReportDto);
		TableModel tableModel = new TableModel("finalReportList", request, response);
		tableModel.setItems(results);
		tableModel.setTable(getHtml());
		request.setAttribute("finalReportList", tableModel.render());
		return mv;
	}
	
	private void setValueJmesa(SearchFinalReportDto searchFinalReportDto) {
		
		searchFinalReportDto.setReportTypeJmesa(searchFinalReportDto.getReportType());
		searchFinalReportDto.setRankJmesa(searchFinalReportDto.getRank());
		searchFinalReportDto.setPostUnitJmesa(searchFinalReportDto.getPostUnit());
		searchFinalReportDto.setEmployeeNumberJmesa(searchFinalReportDto.getEmployeeNumber());
		searchFinalReportDto.setEmployeeNameJmesa(searchFinalReportDto.getEmployeeName());
		searchFinalReportDto.setCommenceStartDateJmesa(searchFinalReportDto.getCommenceStartDate());
		searchFinalReportDto.setCommenceEndDateJmesa(searchFinalReportDto.getCommenceEndDate());
	}

	private Table getHtml() {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);

		HtmlColumn number = new HtmlColumn("employeeNumber").title("Employee Number");

		number.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String value = (String) ItemUtils.getItemValue(item, property);
				String role = (String) ItemUtils.getItemValue(item, "reportTemplate");
				String rptId = (String) ItemUtils.getItemValue(item, "reportId");
				html.append("<a href=\"javascript:openReport('" + rptId + "','" + role + "');\">"+value+"</a>");
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

		HtmlColumn commenceDate = new HtmlColumn("commenceDate").title("Commence Date");
		row.addColumn(commenceDate);
		return htmlTable;
	}
}
