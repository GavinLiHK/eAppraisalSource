package com.hkha.ea.controller.workflow;


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
import org.jmesa.view.editor.AbstractCellEditor;
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

import com.hkha.ea.common.EAException;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.controller.common.ListPageController;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.workflow.WorkflowDetailDTO;
import com.hkha.ea.dto.workflow.WorkflowEnquiryDTO;
import com.hkha.ea.service.common.CommonSysParaService;
import com.hkha.ea.service.workflow.WorkflowTemplateMaintenanceService;
import com.hkha.ea.validator.workflow.WorkflowValidator;

@Controller
public class WorkflowEnquiryController extends ListPageController {
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(WorkflowEnquiryController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private CommonSysParaService commonSysParaService;
	
	@Autowired
	private WorkflowTemplateMaintenanceService workflowTemplateMaintenanceService;
	
	@Autowired
	private WorkflowValidator workflowValidator;
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/workflow/WorkflowEnquiry.do")
	public ModelAndView workflowEnquiryEntry(HttpServletRequest request,@ModelAttribute("workflowEnquiryDTO") WorkflowEnquiryDTO workflowEnquiryDTO,HttpServletResponse response){
        log.info("***EA001 Entry");
        
       request.setAttribute("functionMessageKeySelected", "Workflow Template Maintenance");
       List<SystemParameterDTO> rpTempNameList=new ArrayList<SystemParameterDTO>();
       rpTempNameList=commonSysParaService.SysParaSearch("reportTemplateName");
       ModelAndView mv = new ModelAndView("/workflow/WorkflowEnquiry");
       
        request.getSession().setAttribute("rpTempNameList", rpTempNameList);
       
        /*List<WorkflowEnquiryDTO> wflist=workflowTemplateMaintenanceService.workflowTemplateSearch(workflowEnquiryDTO);
        
        TableModel tableModel = new TableModel("html",request,response);  
        
    	tableModel.setItems(wflist);
    	tableModel.setTable(getHtml());  
    	request.setAttribute("html",tableModel.render());*/

		log.info("After WorkflowTemplateSearch");
		
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
	@RequestMapping("/workflow/SearchWorkflowEnquiry.do")
	public ModelAndView searchWorkflowEnquiryEntry(HttpServletRequest request,@ModelAttribute("workflowEnquiryDTO") WorkflowEnquiryDTO workflowEnquiryDTO,HttpServletResponse response){
        log.info("***EA001 search Entry");
       ModelAndView mv = new ModelAndView("/workflow/WorkflowEnquiry");
       
        List<WorkflowEnquiryDTO> wflist=workflowTemplateMaintenanceService.workflowTemplateSearch(workflowEnquiryDTO);
        
        TableModel tableModel = new TableModel("html",request,response);  
        
    	tableModel.setItems(wflist);
    	tableModel.setTable(getHtml());  
    	request.setAttribute("html",tableModel.render());

		log.info("After WorkflowTemplateSearch");
		
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
	@RequestMapping("/workflow/WorkflowDetail.do")
	public ModelAndView workflowDetailEntry(HttpServletRequest request,HttpServletResponse response){
        log.info("***EA001/EA-FR-001 Entry");
        request.setAttribute("functionMessageKeySelected", "Workflow Template Maintenance");
        
        ModelAndView mv = new ModelAndView("/workflow/WorkflowDetail");
     
        
        String workflowId=request.getParameter("w");
        String workflowId2=(String)request.getAttribute("w");
        WorkflowDetailDTO workflowDetailDTO=new WorkflowDetailDTO();
        if(!StringUtil.isEmptyString(workflowId)||!StringUtil.isEmptyString(workflowId2)){          	
        	workflowDetailDTO=workflowTemplateMaintenanceService.searchWorkflowRoleInfoDetail(workflowId);
        	 request.setAttribute("w", workflowId);
        	
        }
      
        mv.addObject("workflowDetailDTO",workflowDetailDTO);
       
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
	@RequestMapping("/workflow/WorkflowDetailSave.do")
	public ModelAndView workflowDetailSave(HttpServletRequest request,@ModelAttribute("workflowDetailDTO") WorkflowDetailDTO workflowDetailDTO,BindingResult result,HttpServletResponse response){
      
		log.info("***EA001/EA-FR-001 WorkflowDetailSave");
		 request.setAttribute("functionMessageKeySelected", "Workflow Template Maintenance");
		
		 ModelAndView mv = new ModelAndView("/workflow/WorkflowDetail");
		 
        String w=(String)request.getAttribute("w");
        
        workflowValidator.validateWorkflowDetailMandatory(workflowDetailDTO, result);
		if(result.hasErrors()){
			mv = new ModelAndView("/workflow/WorkflowDetail");
			mv.addObject("numberOfErrors",result.getErrorCount());
			mv.addObject(workflowDetailDTO);
			return mv;
		}
		log.info("deadline: "+workflowDetailDTO.getOverallDeadline());
		String msg=workflowTemplateMaintenanceService.saveWorkflowRoleInfoDetail(workflowDetailDTO);
        mv.addObject(workflowDetailDTO);
        mv.addObject("msg",msg);
		return mv;
	}
	
	private Table getHtml() {
		 //New htmlTable 
       HtmlTable htmlTable = new HtmlTable().width("100%");  
       htmlTable.setCaptionKey("title");  
 
       //New HtmlRow 
       HtmlRow row = new HtmlRow();  
       htmlTable.setRow(row);
 
       //Show parameter name with hyperlink  
       HtmlColumn wfTempName = new HtmlColumn("workflowTemplateName").title("Workflow Template Name");  
       wfTempName.setCellEditor(new AbstractCellEditor() {  
	        public Object getValue(Object item, String property, int rowcount){  

				Object obj = ItemUtils.getItemValue(item, "workflowTemplateName");
				Object obj2 = ItemUtils.getItemValue(item, "workflowId");
				String workflowTemplateName = (String)obj;
				Long workflowId = (Long)obj2;
				
	          HtmlBuilder html = new HtmlBuilder();  
             
			html.append("<a href=\"javascript:modify('" + workflowId + "');\">"+workflowTemplateName+"</a>");

             return html.toString();  
	    }  
	   });  
	   row.addColumn(wfTempName);
	    
	   // show parameter description
      HtmlColumn rptTempName = new HtmlColumn("reportTemplate").title("Report Template Name");  
      rptTempName.setCellEditor(new AbstractCellEditor() {  
	        public Object getValue(Object item, String property, int rowcount){  
				Object obj = ItemUtils.getItemValue(item, "reportTemplate");
				String reportTemplate = (String)obj;
				HtmlBuilder html = new HtmlBuilder();  
				html.append(reportTemplate);
				return html.toString();  
	        }  
	   });  
      row.addColumn(rptTempName);  
 
      return htmlTable; 
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
