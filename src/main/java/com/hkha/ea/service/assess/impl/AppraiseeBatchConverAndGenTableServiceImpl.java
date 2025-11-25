package com.hkha.ea.service.assess.impl;

import java.util.ArrayList;
import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

import org.apache.commons.lang3.StringUtils;
//20231201 Write log in catalina.out 
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.jfree.util.Log;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.springframework.stereotype.Service;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.Util;
import com.hkha.ea.dto.assess.AppraiseeEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryVo;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;
import com.hkha.ea.service.assess.AppraiseeBatchConverAndGenTableService;

@Service("appraiseeBatchConverAndGenTableService")
public class AppraiseeBatchConverAndGenTableServiceImpl implements AppraiseeBatchConverAndGenTableService{
	
	//20231201 Write log in catalina.out
//	private org.apache.commons.logging.Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AppraiseeBatchConverAndGenTableServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	
	public String converAssVo(List<BatchEnquiryVo> volist,String selectedOptions){
		String str="";
		String rptIdArry[]=selectedOptions.split(",");
		for(int i=0;i<rptIdArry.length;i++){
			for(BatchEnquiryVo vo:volist){
				if(vo.getReportId()==Long.parseLong(rptIdArry[i])){
					str=str+vo.getAssigned()+",";
				}
			}
		}
		
		return str;
	}
	
	public String converStaVo(List<BatchEnquiryVo> volist,String selectedOptions){
		String str="";
		String rptIdArry[]=selectedOptions.split(",");
		for(int i=0;i<rptIdArry.length;i++){
			for(BatchEnquiryVo vo:volist){
				if(vo.getReportId()==Long.parseLong(rptIdArry[i])){
					str=str+vo.getStatus()+",";
				}
			}
		}
		
		return str;
	}
	
	public String converAssDto(List<BatchEnquiryDto> dtolist,String selectedOptions){
		String str="";
		String rptIdArry[]=selectedOptions.split(",");
		for(int i=0;i<rptIdArry.length;i++){
			for(BatchEnquiryDto dto:dtolist){
				if(dto.getReportId()==Long.parseLong(rptIdArry[i])){
					str=str+dto.getAssigned()+",";
				}
			}
		}
		
		return str;
	}
	
	public String converStaDto(List<BatchEnquiryDto> dtolist,String selectedOptions){
		String str="";
		String rptIdArry[]=selectedOptions.split(",");
		for(int i=0;i<rptIdArry.length;i++){
			for(BatchEnquiryDto dto:dtolist){
				if(dto.getReportId()==Long.parseLong(rptIdArry[i])){
					str=str+dto.getStatus()+",";
				}
			}
		}
		
		return str;
	}
	public List<String> converReportIds(String selectedOptions){
		String str="";
		String rptIdArry[]=selectedOptions.split(",");
		List<String> list=new ArrayList<String>();
		for(int i=0;i<rptIdArry.length;i++){
			list.add(rptIdArry[i]);
			
		}
		
		return list;
	}
	
	
	public String coverAllSelectedVo(List<BatchEnquiryVo> results) {
		String str="";
		for(BatchEnquiryVo vo:results){
			str=str+vo.getReportId()+",";
		}
		return str;
	}
	
	public String coverAllSelectedDto(List<BatchEnquiryDto> results) {
		String str="";
		for(BatchEnquiryDto dto:results){
			str=str+dto.getReportId()+",";
		}
		return str;
	}
	
	public List<String> convertToList(String selComDate) {
		List<String> list = new ArrayList<String>();
		if(!Util.isEmptyString(selComDate)){
			String[] arrs = selComDate.split(",");	
			if(arrs != null && arrs.length > 0){
				for(int i=0; i<arrs.length; i++){
					list.add(arrs[i]);
				}
			}
		}
		return list;
	}
	
	public List<Long> getSelectedReportID(String selBatch) {
		List<Long> reportIds = new ArrayList<Long>();
		if(!Util.isEmptyString(selBatch)){
			String[] s = selBatch.split(",");
			if(s != null && s.length > 0){
				for(int i=0; i<s.length; i++){
					if(!Util.isEmptyString(s[i])){
						reportIds.add(Long.valueOf(s[i]));
					}
				}
			}
		}
		return reportIds;
	}
	
	public String converEmpVo(List<BatchEnquiryVo> volist,String selectedOptions){
		String str="";
		String rptIdArry[]=selectedOptions.split(",");
		for(int i=0;i<rptIdArry.length;i++){
			for(BatchEnquiryVo vo:volist){
				if(vo.getReportId()==Long.parseLong(rptIdArry[i])){
					str=str+vo.getEmployeeNum()+",";
				}
			}
		}
		str=str.substring(0, str.length()-1);
		return str;
	}
	
	
	public Table genBatchAppraiseeListHtml() {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);

		HtmlColumn select = new HtmlColumn("id").title("Send");
		
		select.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				Object value = ItemUtils.getItemValue(item, "reportId");
	            Long reportId = (Long)value;
				String ass = (String) ItemUtils.getItemValue(item, "assigned");
				String status = (String) ItemUtils.getItemValue(item, "status");
				String employeeNum = (String) ItemUtils.getItemValue(item, "employeeNum");
				String commenceDate = (String) ItemUtils.getItemValue(item, "commenceDate");
				String endDate = (String) ItemUtils.getItemValue(item, "endDate");
				
				html.append("<input type='checkbox' id='checkBth_"+reportId+"' name='checkBth_"+reportId+"'  value='Y'  onclick='multiSelectedForJmesa(\"checkBth_\","+reportId+",\"selectedOptions\");' /><input type='hidden' id='reportId"+rowcount+"' value='"+reportId+"' />");
				
				html.append("<input type='hidden' id='ass"+rowcount+"' value='"+ass+"'/>");
				html.append("<input type='hidden' id='sta"+rowcount+"' value='"+status+"'/>");
				html.append("<input type='hidden' id='emp"+rowcount+"' value='"+employeeNum+"'/>");
				html.append("<input type='hidden' id='comDate"+rowcount+"' value='"+commenceDate+"'/>");
				html.append("<input type='hidden' id='endDate"+rowcount+"' value='"+endDate+"'/>");
				//html.aEnd();
				return html.toString();
			}
		});
		
		row.addColumn(select);

		HtmlColumn employeeNum = new HtmlColumn("employeeNum").title("Employee Number");
		row.addColumn(employeeNum);

		HtmlColumn appraiseeName = new HtmlColumn("name").title("Appraisee Name");
		row.addColumn(appraiseeName);
		
		HtmlColumn rank = new HtmlColumn("subRank").title("Rank Name");
		row.addColumn(rank);
		
		HtmlColumn postUnit = new HtmlColumn("postUnit").title("Post Unit");
		row.addColumn(postUnit);
		
		HtmlColumn assigned = new HtmlColumn("assigned").title("Assigned");
		row.addColumn(assigned);
		
		return htmlTable;
	}
	
	public Table getSearchBatchAppraiseeHtml() {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");
		
		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);

		HtmlColumn select = new HtmlColumn("batchName").title("Batch Name");

		select.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String value = (String) ItemUtils.getItemValue(item, property);				
				html.append("<a href=\"javascript:getReportsAppraiseeList('" + value + "');\">"+value);
				html.aEnd();
			
				return html.toString();
			}

		});
		row.addColumn(select);

		// Show parameter name with hyperlink
		HtmlColumn batchName = new HtmlColumn("numAppraisee").title("Total Number of Report Received");
		row.addColumn(batchName);

		HtmlColumn numAssigned = new HtmlColumn("numAssigned").title("Assigned");
		row.addColumn(numAssigned);

		HtmlColumn numUnassigned = new HtmlColumn("numUnassigned").title("Unassigned");
		row.addColumn(numUnassigned);
		return htmlTable;
	}

	public String coverAllSelectedJmesa(List<AppraiseeEnquiryDto> results) {
		String str="";
		for(AppraiseeEnquiryDto vo:results){
			str=str+vo.getEmployeeNum()+",";
		}
		return str;
	}

	public Table getHtmlBatchList(SearchAppraiseeDto searchAppraiseeDto) {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);

		final List<String> list = new ArrayList<String>();
		HtmlColumn select = new HtmlColumn("id").title("Send Batch");
		select.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String value = (String) ItemUtils.getItemValue(item, property);
				/*Object obj = ItemUtils.getItemValue(item, "reportId");
	            Long reportId = (Long)obj;
	            list.add(String.valueOf(reportId));
				*/
				Object obj = ItemUtils.getItemValue(item, "strReportId");
				String reportId = (String)obj;
				String s1="";
				if (!StringUtils.isEmpty(reportId)) {
					String[] s = reportId.split("-");
					for (int i = 0; i < s.length; i++) {
						s1 = s[i];
						if (!s1.isEmpty()) {
							list.add(s1);
						}
					}
				}
				html.append("<input type='checkbox' id='checkBth_"+s1+"' name='checkBth_"+s1+"'  value='Y'  onclick='multiSelectedForBatch(\"checkBth_\","+s1+",\"selectedOptions\",\"selectedBatchId\");' />");
				html.append("<input type=\"hidden\" id='" + s1 + "' value='" + value + "'/>");
				html.aEnd();
				return html.toString();
				
			}
		});
		row.addColumn(select);
					
		// Show parameter name with hyperlink
		//HtmlColumn batchName = new HtmlColumn("batchName").title("Batch Name");
		HtmlColumn batchName = new HtmlColumn("id").title("Batch Name");
		batchName.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String batchId = (String) ItemUtils.getItemValue(item, "id");
				/*html.a().href().quote().append("/ea/assess/searchReport.do?fromPage=" + Constants.PAGE_MODIFY_BATCH_LINK
						+ "&batchName=" + batchId).quote().close();*/
				html.append("<a href=\"javascript:getReportsAppraiseeList('" + batchId+"','"+Constants.PAGE_MODIFY_BATCH_LINK+"');\">"+batchId);
				//html.append(batchId);
				//html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(batchName);

		HtmlColumn total = new HtmlColumn("totalAppraisees").title("Total No. Of Appraisees");
		row.addColumn(total);

		HtmlColumn enable = new HtmlColumn("noOfAppDispatched").title("No. Of Appraisees Dispatched");
		row.addColumn(enable);
		
		searchAppraiseeDto.setSelOptionsAll(list);
		return htmlTable;	
	}
	
	public Table getHtmlReportAppraiseeList(SearchAppraiseeDto searchAppraiseeDto) {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);

		final List<String> list = new ArrayList<String>();
		HtmlColumn batchId = new HtmlColumn("batchId").title("Select");
		batchId.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				HtmlBuilder html = new HtmlBuilder();  
	              String value = (String)ItemUtils.getItemValue(item, "batchId");
	              String status = (String)ItemUtils.getItemValue(item, "status");
	              String employeeNum = (String) ItemUtils.getItemValue(item, "employeeNum");
	              Object obj = ItemUtils.getItemValue(item, "reportId");
	              Long reportId = (Long)obj;
	              list.add(String.valueOf(reportId));
	              String reportGenerated = (String)ItemUtils.getItemValue(item, "reportGenerated");
	             
	              /*html.append("<input name=\"checkBth\" type=\"checkbox\" value='"+reportId+';'+reportGenerated+"'/>"
	              		+ "<input type=\"hidden\" id='"+reportId+';'+reportGenerated+"' value='"+value+';'+status+"'/>"
	              				+"<input type=\"hidden\" id='"+reportId+';'+reportGenerated+"emp' value='"+employeeNum+';'+reportGenerated+"'/>");*/
	              html.append("<input type='checkbox' id='checkBth_" + reportId + "' name='checkBth_" + reportId+"' value='Y' onclick='multiSelectedForAppraisee(\"checkBth_\"," + reportId + ",\"selectedOptions\",\"selectedRptId\",\"selectedBatchId\",\"selectedEmployee\");' />"
	            		  + "<input type=\"hidden\" id='" + reportId + "' value='"+reportId+';'+reportGenerated+"'/>" 
	            		  + "<input type=\"hidden\" id='batSta" + reportId + "' value='" + value + ';'+ status + "'/>" 
	            		  + "<input type=\"hidden\" id='emp" + reportId + "' value='" + employeeNum + ';' + reportGenerated + "'/>");
	              html.aEnd();  
	              return html.toString();  
			}
		});
		row.addColumn(batchId);
		
		HtmlColumn batId = new HtmlColumn("batchId").title("Batch");
		row.addColumn(batId);
		
		// Show parameter name with hyperlink
		HtmlColumn employeeNum = new HtmlColumn("employeeNum").title("Employee Number");
		employeeNum.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				Object value = ItemUtils.getItemValue(item, property);
				Object obj1 = ItemUtils.getItemValue(item, "reportId");
				Long reportId = (Long) obj1;
				if (reportId != null && reportId > 0) {
					html.a().href().quote().append("/ea/assess/AssessAppraisal.do?reportId=" + reportId+"&btl=A").quote()
							.close();
				}
				html.append(value);
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(employeeNum);

		HtmlColumn appraiseeName = new HtmlColumn("fullName").title("Appraisee Name");
		row.addColumn(appraiseeName);

		HtmlColumn rank = new HtmlColumn("rank").title("Rank");
		row.addColumn(rank);

		HtmlColumn postUnit = new HtmlColumn("postUnit").title("Post Unit");
		row.addColumn(postUnit);

		HtmlColumn reportGenerated = new HtmlColumn("reportGenerated").title("Report Generated");
		row.addColumn(reportGenerated);

		searchAppraiseeDto.setSelOptionsAll(list);
		return htmlTable;
	}
	
	public Table getHtmlAppraiseeList(SearchAppraiseeDto searchAppraiseeDto) {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);

		final List<String> list = new ArrayList<String>();
		HtmlColumn batchId = new HtmlColumn("batchId").title("Select");
		batchId.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String value = (String) ItemUtils.getItemValue(item, "batchId");
				String status = (String) ItemUtils.getItemValue(item, "status");
				String employeeNum = (String) ItemUtils.getItemValue(item, "employeeNum");
				list.add(employeeNum);
				Object obj = ItemUtils.getItemValue(item, "reportId");
				Long reportId = (Long) obj;
				String reportGenerated = (String) ItemUtils.getItemValue(item, "reportGenerated");
				// html.input().type("checkbox").value(value);
				html.append("<input type='checkbox' id='checkBth_" + employeeNum + "' name='checkBth_" + employeeNum+"' value='" + employeeNum + "' onclick='multiSelectedForAppraisee(\"checkBth_\"," + employeeNum + ",\"selectedOptions\",\"selectedRptId\",\"selectedBatchId\",\"selectedEmployee\");' />"
					    + "<input type=\"hidden\" id='" + employeeNum + "' value='"+reportId+';'+reportGenerated+"'/>"
						+ "<input type=\"hidden\" id='batSta" + employeeNum + "' value='" + value + ';'+ status + "'/>" 
						+ "<input type=\"hidden\" id='emp" + employeeNum + "' value='" + employeeNum + ';' + reportGenerated + "'/>");
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(batchId);

		HtmlColumn batId = new HtmlColumn("batchId").title("Batch");
		row.addColumn(batId);

		// Show parameter name with hyperlink
		HtmlColumn employeeNum = new HtmlColumn("employeeNum").title("Employee Number");
		employeeNum.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String value = (String) ItemUtils.getItemValue(item, property);
				Object obj1 = ItemUtils.getItemValue(item, "reportId");
				Long reportId = (Long) obj1;
				if (reportId != null && reportId > 0) {
					html.a().href().quote().append("/ea/assess/AssessAppraisal.do?reportId=" + reportId + "&btl=A")
							.quote().close();
				}
				html.append(value);
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(employeeNum);

		HtmlColumn appraiseeName = new HtmlColumn("fullName").title("Appraisee Name");
		row.addColumn(appraiseeName);

		HtmlColumn rank = new HtmlColumn("rank").title("Rank");
		row.addColumn(rank);

		HtmlColumn postUnit = new HtmlColumn("postUnit").title("Post Unit");
		row.addColumn(postUnit);

		HtmlColumn reportGenerated = new HtmlColumn("reportGenerated").title("Report Generated");
		row.addColumn(reportGenerated);

		searchAppraiseeDto.setSelOptionsAll(list);
		return htmlTable;
	}

	public String coverOenPageAllSelectedJmesa(List<String> selOptionsAll) {
		String str="";
		for(String vo:selOptionsAll){
			str=str+vo+",";
		}
		return str;
	}
}
