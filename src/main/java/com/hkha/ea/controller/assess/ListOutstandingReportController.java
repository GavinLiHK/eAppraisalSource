package com.hkha.ea.controller.assess;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

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
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.Constants;
import com.hkha.ea.dto.assess.AppraisalReportRoleDTO;
import com.hkha.ea.dto.assess.AppraiseeBatchDTO;
import com.hkha.ea.dto.assess.ListHistoryDTO;
import com.hkha.ea.service.assess.ListOutstandingReportService;

@Controller
public class ListOutstandingReportController {
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(ListOutstandingReportController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private ListOutstandingReportService listOutstandingReportService;
	
	@RequestMapping("/assess/ListOutstandingReport.do")
	public ModelAndView listOutstandingReport(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/assess/ListOutstandingReport");
				
		String htmlBatch = "";
		String htmlOSReport = "";
		String htmlHistory = "";
		
		TableModel tableModel = null;
		
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<AppraiseeBatchDTO> resultBatch = listOutstandingReportService.searchAppraiseeBatch(currentUser);
		
		if (resultBatch != null) {
			tableModel = new TableModel("html_batch", request);
			tableModel.setItems(resultBatch);
			tableModel.setTable(getHtmlBatch());
			htmlBatch = tableModel.render();
		}
		
		List<AppraisalReportRoleDTO> resultOutstandingList = listOutstandingReportService.searchOutstandingList(currentUser);
		if (resultOutstandingList != null) {
			tableModel = new TableModel("html_os_report", request);
			tableModel.setItems(resultOutstandingList);
			tableModel.setTable(getHtmlOutstandingList());
			htmlOSReport = tableModel.render();
		}
		
		List<ListHistoryDTO> resultHistory = listOutstandingReportService.searchHistory(currentUser);
		if (resultHistory != null) {
			tableModel = new TableModel("html_history", request);
			tableModel.setItems(resultHistory);
			tableModel.setTable(getHtmlHistory());
			htmlHistory = tableModel.render();
		}
		
		mv.addObject("html_batch", htmlBatch);
		mv.addObject("html_os_report", htmlOSReport);
		mv.addObject("html_history", htmlHistory);
		
		return mv;
	}
	
	private Table getHtmlBatch() {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);

		// Show parameter name with hyperlink
		HtmlColumn batchName = new HtmlColumn("batchId").title("Batch Name");
		batchName.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String batchId = (String) ItemUtils.getItemValue(item, "batchId");
				//html.a().href().quote().append("/ea/assess/ListBatchAppraisee.do?batchName=" + batchId).quote().close();
				html.a().href().quote().append("/ea/assess/getBatchAppraiseeList.do?batchName=" + batchId+"&funcitonId=EA015").quote().close();
				html.append(batchId);
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(batchName);

		HtmlColumn noOfAppraisee = new HtmlColumn("noOfAppraisee").title("Total Number of Report Received");
		row.addColumn(noOfAppraisee);

		HtmlColumn noOfAssigned = new HtmlColumn("noOfAssigned").title("Assigned");
		row.addColumn(noOfAssigned);
		
		HtmlColumn noOfUnassigned = new HtmlColumn("noOfUnassigned").title("Unassigned");
		row.addColumn(noOfUnassigned);
		
		return htmlTable;
	}
	
	private Table getHtmlOutstandingList(){
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);
		
		// Show parameter name with hyperlink
		HtmlColumn employeeNumber = new HtmlColumn("employeeNumber").title("Employee Number");
		employeeNumber.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String reportId = Long.toString((Long) ItemUtils.getItemValue(item, "reportId"));
				html.a().href().quote().append("/ea/assess/AssessAppraisal.do?reportId=" + reportId+"&btl=L").quote().close();
				html.append((String) ItemUtils.getItemValue(item, "employeeNumber"));
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(employeeNumber);
		
		HtmlColumn name = new HtmlColumn("name").title("Appraisee Name");
		row.addColumn(name);
		
		HtmlColumn substantiveRank = new HtmlColumn("substantiveRank").title("Rank");
		row.addColumn(substantiveRank);
		
		HtmlColumn status = new HtmlColumn("status").title("Your Role");
		row.addColumn(status);
		
		HtmlColumn deadline = new HtmlColumn("deadline").title("Due Date");
		deadline.cellEditor(new DateCellEditor(Constants.DISPLAY_DATE_FORMAT));
		row.addColumn(deadline);
		
		return htmlTable;
	}
	
	private Table getHtmlHistory(){
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);
		
		HtmlColumn employeeNum = new HtmlColumn("employeeNum").title("Appraisee Employee Number");
		row.addColumn(employeeNum);
		
		HtmlColumn fullName = new HtmlColumn("fullName").title("Appraisee Name");
		row.addColumn(fullName);
		
		HtmlColumn rank = new HtmlColumn("rank").title("Rank");
		row.addColumn(rank);
		
		HtmlColumn remark = new HtmlColumn("remark").title("Remark");
		row.addColumn(remark);
		
		return htmlTable;		
	}
	
}
