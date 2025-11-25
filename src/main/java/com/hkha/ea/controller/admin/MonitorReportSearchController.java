package com.hkha.ea.controller.admin;

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
import com.hkha.ea.common.Util;
import com.hkha.ea.dto.admin.MonitorReportDto;
import com.hkha.ea.dto.admin.MonitorReportSearchDto;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.common.MessageLogDto;
import com.hkha.ea.service.admin.MonitorReportService;

@Controller
public class MonitorReportSearchController {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(MonitorReportSearchController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private MonitorReportService monitorReportService;
	
	@RequestMapping("/admin/iniMonitorReport.do")
	public ModelAndView iniMonitorReport(HttpServletRequest request,@ModelAttribute("monitorReportSearchDto") MonitorReportSearchDto monitorReportSearchDto){
        log.info("***Function first entered");
		
        ModelAndView mv = new ModelAndView("/admin/MonitorReportSearch");
        List<SystemParameterDTO> sortList = this.getSortList();
     
        monitorReportSearchDto.setIsSearch("Y");
        request.getSession().setAttribute("sortList", sortList);
		return mv;
	}

	private List<SystemParameterDTO> getSortList() {
		List<SystemParameterDTO> list = new ArrayList<SystemParameterDTO>();
		
		 SystemParameterDTO dto = new SystemParameterDTO();
	     dto.setParamDesc("Default Sorting Sequence");
	     dto.setParamValue("sort0");
	     list.add(dto);
	     
	     SystemParameterDTO dto1 = new SystemParameterDTO();
	     dto1.setParamDesc("Post Unit, Appraisee Name, Appraisal Period");
	     dto1.setParamValue("sort1");
	     list.add(dto1);
	     
	     SystemParameterDTO dto2 = new SystemParameterDTO();
	     dto2.setParamDesc("Post Unit, Current Officer, Appraisee Name");
	     dto2.setParamValue("sort2");
	     list.add(dto2);
	     
	     SystemParameterDTO dto3 = new SystemParameterDTO();
	     dto3.setParamDesc("Current Officer, Post Unit, Appraisee Name");
	     dto3.setParamValue("sort3");
	     list.add(dto3);
	     
	     SystemParameterDTO dto4 = new SystemParameterDTO();
	     dto4.setParamDesc("Role, Post Unit, Appraisee Name");
	     dto4.setParamValue("sort4");
	     list.add(dto4);
	     
	     return list;
	}
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/admin/searchMonitorReport.do")
	public ModelAndView searchMonitorReport(HttpServletRequest request,
			@ModelAttribute("monitorReportSearchDto") MonitorReportSearchDto monitorReportSearchDto, BindingResult result, HttpServletResponse response) {
		log.info("search monitor report begin=========");
		ModelAndView mv = new ModelAndView();
		String fromPage = request.getParameter("fromSendReminder");
		
		if(!Util.isEmptyString(fromPage) && "Y".equals(fromPage))
			monitorReportSearchDto = (MonitorReportSearchDto) request.getSession().getAttribute("monitorReportSearchDto");
		
		if(Constants.YES.equals(monitorReportSearchDto.getIsSearch())){
			this.setJmesaValues(monitorReportSearchDto);
			if(!getValidation(monitorReportSearchDto)){
				mv = new ModelAndView("/admin/MonitorReportSearch");
				result.rejectValue("rank", "error."+Constants.ERROR_MISSING_RANK_OR_OFFICERID,new String[]{"rank"},"");
				mv.addObject("numberOfErrors",result.getErrorCount());
				return mv;
			}
		}
		
		List<MonitorReportDto> list = monitorReportService.search(monitorReportSearchDto.getRankJmesa(),monitorReportSearchDto.getEmployeeNumberJmesa(),monitorReportSearchDto.getPostUnitJmesa(),monitorReportSearchDto.getSortSequenceJmesa());
		
		mv = new ModelAndView("/admin/MonitorReportList");
		TableModel tableModel = new TableModel("monitorReportList",request,response);  
		tableModel.setItems(list);
		tableModel.setTable(getHtml());  
		request.setAttribute("monitorReportList",tableModel.render());
		monitorReportSearchDto.setIsSearch(Constants.NO);
		request.getSession().setAttribute("monitorReportSearchDto",monitorReportSearchDto);
		request.getSession().setAttribute(Constants.SESS_ASSIGN_OFFICER_TYPE,Constants.PAGE_MONITOR_REPORT);
		request.getSession().setAttribute(Constants.FROM_ASSIGN_OFFICER,Constants.NO);
		return mv;
	}

	private void setJmesaValues(MonitorReportSearchDto monitorReportSearchDto) {
		monitorReportSearchDto.setRankJmesa(monitorReportSearchDto.getRank());
		monitorReportSearchDto.setEmployeeNumberJmesa(monitorReportSearchDto.getEmployeeNumber());
		monitorReportSearchDto.setPostUnitJmesa(monitorReportSearchDto.getPostUnit());
		monitorReportSearchDto.setSortSequenceJmesa(monitorReportSearchDto.getSortSequence());
	}

	private boolean getValidation(MonitorReportSearchDto monitorReportSearchDto) {
		if (!Util.isEmptyString(monitorReportSearchDto.getRank()) || !Util.isEmptyString(monitorReportSearchDto.getEmployeeNumber()))
			return true;
		return false;
	}

	private Table getHtml() {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);

		HtmlColumn select = new HtmlColumn("id").title("Select");

		select.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				Object obj = ItemUtils.getItemValue(item, "reportId");
				Long value = (Long)obj;
				html.append("<input name=\"checkBth\" type=\"checkbox\" value='" + value + "'/>");
				html.append("");
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(select);

		// Show parameter name with hyperlink
		HtmlColumn batchName = new HtmlColumn("reportEmployeeNum").title("Appraisee Number");
		batchName.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String value = (String) ItemUtils.getItemValue(item, "reportEmployeeNum");
				Object obj = ItemUtils.getItemValue(item, "reportId");
				Long reportId = (Long)obj;
				html.a().href().quote().append("/ea/assess/AssessAppraisal.do?reportId=" + reportId+"&btl=A").quote().close();
				html.append(value);
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(batchName);
		
		HtmlColumn appraiseeName = new HtmlColumn("reportName").title("Appraisee Name");
		row.addColumn(appraiseeName);

		HtmlColumn post = new HtmlColumn("persentPost").title("Post Unit");
		row.addColumn(post);
		
		HtmlColumn officer = new HtmlColumn("fullName").title("Current Officer");
		row.addColumn(officer);

		HtmlColumn rank = new HtmlColumn("substantiveRank").title("Current Officer Rank");
		row.addColumn(rank);
		
		HtmlColumn period = new HtmlColumn("commenceDate").title("Appraisal Period");
		period.sortable(false);
		period.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String startDate = (String) ItemUtils.getItemValue(item, "commenceDate");
				String endDate = (String)ItemUtils.getItemValue(item, "endDate");
				html.append(startDate+"~"+endDate);
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(period);

		HtmlColumn role = new HtmlColumn("role1").title("Role *");
		row.addColumn(role);
		
		HtmlColumn rem = new HtmlColumn("remSent").title("Rem. Sent");
		rem.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				Object value =  ItemUtils.getItemValue(item, "remSent");
				int rem = (Integer)value;
				String role = (String) ItemUtils.getItemValue(item, "role1");
				Object obj = ItemUtils.getItemValue(item, "reportId");
				Long reportId = (Long)obj;
				html.append("<a href=\"javascript:searchMonitorReportDetail('" + reportId + "','"+role+"');\">"+rem+"</a>");
				//html.a().href().quote().append("/ea/admin/monitorReportDetail.do?reportId=" + reportId+"&role="+role).quote().close();
				//html.append(rem);
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(rem);
		
		HtmlColumn emailInd = new HtmlColumn("emailAddress").title("Email Ind");
		row.addColumn(emailInd);

		HtmlColumn info = new HtmlColumn("noteInfo").title("Note. info");
		row.addColumn(info);
		
		return htmlTable;
	}
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/admin/monitorReportDetail.do")
	public ModelAndView monitorReportDetail(HttpServletRequest request,
			@ModelAttribute("monitorReportSearchDto") MonitorReportSearchDto monitorReportSearchDto, BindingResult result, HttpServletResponse response) {
		String selRpId = request.getParameter("reportId");
		String role = request.getParameter("role");
		ModelAndView mv = new ModelAndView();
		
		List<Long> reportIds = this.convertToList(selRpId);
		
		List<MonitorReportDto> list = monitorReportService.search(reportIds);
		
		TableModel tableModel = new TableModel("monitorReportDetail",request,response);  
		tableModel.setItems(list);
		tableModel.setTable(getMonitorReportDetailHtml());  
		request.setAttribute("monitorReportDetail",tableModel.render());
		
		List<MessageLogDto> msgList = monitorReportService.searchLogFromReportIDAndStatus(reportIds.get(0),role);
		TableModel tableModelMsg = new TableModel("monitorReportDetailMsg",request,response);  
		tableModelMsg.setItems(msgList);
		tableModelMsg.setTable(getMessageLogHtml());
		request.setAttribute("monitorReportDetailMsg",tableModelMsg.render());
		
		mv = new ModelAndView("/admin/MonitorReportDetail");
		return mv;
	}

	private List<Long> convertToList(String selRpId) {
		List<Long> reportIds = new ArrayList<Long>();
		if(!Util.isEmptyString(selRpId)){
			String[] s = selRpId.split(",");
			if(s != null && s.length > 0){
				for(int i=0; i<s.length; i++){
					String rp = s[i];
					if(!Util.isEmptyString(rp))
						reportIds.add(Long.valueOf(rp));
				}
			}
		}
		return reportIds;
	}
	
	private Table getMonitorReportDetailHtml() {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);
		
		HtmlColumn appraiseeNum = new HtmlColumn("reportEmployeeNum").title("Appraisee Number");
		row.addColumn(appraiseeNum);
		
		HtmlColumn appraiseeName = new HtmlColumn("reportName").title("Appraisee Name");
		row.addColumn(appraiseeName);

		HtmlColumn post = new HtmlColumn("persentPost").title("Post Unit");
		row.addColumn(post);
		
		HtmlColumn officer = new HtmlColumn("fullName").title("Current Officer");
		row.addColumn(officer);

		HtmlColumn rank = new HtmlColumn("substantiveRank").title("Current Officer Rank");
		row.addColumn(rank);
		
		HtmlColumn period = new HtmlColumn("commenceDate").title("Appraisal Period");
		period.sortable(false);
		period.setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				String startDate = (String) ItemUtils.getItemValue(item, "commenceDate");
				String endDate = (String)ItemUtils.getItemValue(item, "endDate");
				html.append(startDate+"~"+endDate);
				html.aEnd();
				return html.toString();
			}
		});
		row.addColumn(period);

		HtmlColumn role = new HtmlColumn("role1").title("Role *");
		row.addColumn(role);
		
		return htmlTable;
	}
	
	private Table getMessageLogHtml() {
		// New htmlTable
		HtmlTable htmlTable = new HtmlTable().width("100%");
		htmlTable.setCaptionKey("title");

		// New HtmlRow
		HtmlRow row = new HtmlRow();
		htmlTable.setRow(row);
		
		HtmlColumn appraiseeNum = new HtmlColumn("lastUpdateDate").title("Reminder Sent Date");
		row.addColumn(appraiseeNum);
		
		HtmlColumn appraiseeName = new HtmlColumn("emailInd").title("Email Ind");
		row.addColumn(appraiseeName);
		
		return htmlTable;
	}
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/admin/validateReportIdsForSendRemindr.do")
	public ModelAndView validateReportIdsForSendRemindr(HttpServletRequest request,
			@ModelAttribute("monitorReportSearchDto") MonitorReportSearchDto monitorReportSearchDto, BindingResult result, HttpServletResponse response) {
		
		ModelAndView mv =  new ModelAndView("/admin/MonitorReportList");
		String selRpId = request.getParameter("selRpId");
		
		String reportIds = this.delStringSpaces(selRpId);
		if(reportIds.length() <= 0){
			result.rejectValue("", "info."+Constants.INFO_RECORD_NOT_FOUND);
			mv.addObject("numberOfErrors",result.getErrorCount());
			
			List<MonitorReportDto> list = monitorReportService.search(monitorReportSearchDto.getRankJmesa(),monitorReportSearchDto.getEmployeeNumberJmesa(),monitorReportSearchDto.getPostUnitJmesa(),monitorReportSearchDto.getSortSequenceJmesa());
			
			TableModel tableModel = new TableModel("monitorReportList",request,response);  
			tableModel.setItems(list);
			tableModel.setTable(getHtml());  
			request.setAttribute("monitorReportList",tableModel.render());
			return mv;
		}
		mv = new ModelAndView("forward:/admin/initSendManualReminder.do?selRpIds="+reportIds.toString());
		return mv;
	}

	private String delStringSpaces(String selRpId) {
		String reportId = "";
		if(!Util.isEmptyString(selRpId)){
			String[] s = selRpId.split(",");
			if(s != null && s.length > 0){
				for(int i=0; i<s.length; i++){
					String rp = s[i];
					if(!Util.isEmptyString(rp))
						reportId = reportId + rp + ",";
				}
			}
		}
		return reportId;
	}
}
