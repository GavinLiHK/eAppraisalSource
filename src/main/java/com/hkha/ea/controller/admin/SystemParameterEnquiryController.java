//package statement 
package com.hkha.ea.controller.admin;
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
import org.springframework.web.servlet.view.RedirectView;

import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.validator.GeneralValidator;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.service.admin.SystemParameterMaintenanceService;


@Controller
public class SystemParameterEnquiryController {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SystemParameterEnquiryController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private SystemParameterMaintenanceService systemParameterMaintenanceService;
	
	@Autowired
	private GeneralValidator generalValidator;

	public static final String SELECT_FLAG = "SELECT_FLAG";

		@RequestMapping("/admin/iniSystemParameterEnquiry.do")
		public ModelAndView iniSystemParameterMaintenance(HttpServletRequest request,@ModelAttribute("systemParameterDTO") SystemParameterDTO systemParameterDTO){
	        //log.info("***Function first entered");
			
	        ModelAndView mv = new ModelAndView("/SystemParameterEnquiry");
	        
			request.setAttribute("functionMessageKeySelected", "Admin");
			
			/*
			systemParameterDTO = new SystemParameterDTO();
			
			log.info("Before systemParameterMaintenanceService.search");
			List<SystemParameterDTO> results = systemParameterMaintenanceService.search(systemParameterDTO);
			log.info("After systemParameterMaintenanceService.search");
			if(results != null && results.size()>0){
				for(int i=0; i<results.size(); i++){
					log.info(results.get(i).toString());
				};
			}
			else{
				log.info("results list is null");
			}
			*/
			
			return mv;
		}
		
		
		@RequestMapping("/admin/searchSystemParameter.do")
		public ModelAndView enquireSystemParameter(HttpServletRequest request,@ModelAttribute("systemParameterDTO") SystemParameterDTO systemParameterDTO, HttpServletResponse response){
	        ModelAndView mv = new ModelAndView("/SystemParameterEnquiry");
			request.setAttribute("functionMessageKeySelected", "Admin");
			
			//log.info("Before systemParameterMaintenanceService.search");
			List<SystemParameterDTO> results = systemParameterMaintenanceService.search(systemParameterDTO);
			//log.info("After systemParameterMaintenanceService.search");
			
			/*
			if(results != null && results.size()>0){ 
				for(int i=0; i<results.size(); i++){
					log.info(results.get(i).toString());
				};
			}
			*/
			
			TableModel tableModel = new TableModel("html",request,response);  
			tableModel.setItems(results);
			tableModel.setTable(getHtml());  
			request.setAttribute("html",tableModel.render());
			request.getSession(false).setAttribute("systemParameterDTOEnquiry", systemParameterDTO);
			
			return mv;
		}
		
		@RequestMapping("/admin/addSystemParameter.do")
		public ModelAndView addSystemParameter(HttpServletRequest request, HttpServletResponse response){
	        ModelAndView mv = new ModelAndView("/SystemParameterDetail");
	        request.getSession(false).removeAttribute("systemParameterDTOForUpdated");
	        request.setAttribute("functionMessageKeySelected", "Admin");
			return mv;
		}
		
		@RequestMapping("/admin/updateSystemParameter.do")
		public ModelAndView updateSystemParameter(HttpServletRequest request, HttpServletResponse response) throws Exception{
			log.info("Entering update system parameter function ");
	        ModelAndView mv = new ModelAndView("/SystemParameterDetail");
	        String paramName = request.getParameter("paramName");
			request.setAttribute("functionMessageKeySelected", "Admin");
			
			SystemParameterDTO systemParameterDTO = systemParameterMaintenanceService.getByParamName(paramName);
			
			log.info(systemParameterDTO.toString());
			request.getSession(false).setAttribute("systemParameterDTOForUpdated", systemParameterDTO);
			mv.addObject(systemParameterDTO);
			return mv;
		}
		
		@RequestMapping("/admin/addOrModifySystemParameter.do")
		public ModelAndView saveSystemParameter(HttpServletRequest request,@ModelAttribute("systemParameterDTO") SystemParameterDTO systemParameterDTO, BindingResult result, HttpServletResponse response){
	        ModelAndView mv = new ModelAndView("/SystemParameterDetail");
	        
			request.setAttribute("functionMessageKeySelected", "Admin");
			SystemParameterDTO systemParameterDTOBeforeUpdated = (SystemParameterDTO) request.getSession().getAttribute("systemParameterDTOForUpdated");
			log.info("Before systemParameterMaintenanceService.search");
			log.info(systemParameterDTO.toString());
			
			generalValidator.validateSystemParam(systemParameterDTO, result);
			if(result.hasErrors()){
				mv = new ModelAndView("/SystemParameterDetail");
				mv.addObject("numberOfErrors",result.getErrorCount());
				request.getSession(false).setAttribute("systemParameterDTOForUpdated", systemParameterDTO);
				mv.addObject(systemParameterDTO);
				return mv;
			}
			
			//20240814 Handle saving failed when adding a system parameter failed in validation 
			if(systemParameterDTOBeforeUpdated != null){
				if(!systemParameterDTOBeforeUpdated.getParamValue().trim().isEmpty() && !systemParameterDTOBeforeUpdated.getParamName().trim().isEmpty() && !systemParameterDTOBeforeUpdated.getParamDesc().trim().isEmpty() ){
			//End 20240814 Handle saving failed when adding a system parameter failed in validation 
					log.info(systemParameterDTOBeforeUpdated.toString());
					systemParameterDTOBeforeUpdated = systemParameterMaintenanceService.getByParamName(systemParameterDTOBeforeUpdated.getParamName());
					if(systemParameterDTOBeforeUpdated != null && !StringUtil.isEmptyString(systemParameterDTOBeforeUpdated.getParamName())){ 
						systemParameterMaintenanceService.update(systemParameterDTO,systemParameterDTOBeforeUpdated);
						request.getSession(false).setAttribute("systemParameterDTOForUpdated", systemParameterDTO);
					}
					else{
						result.rejectValue("paramName", "error.er0071", "System parameter has been updated before deletion");
						mv = new ModelAndView("/SystemParameterDetail");
						mv.addObject(systemParameterDTO);
						return mv;
					}
				}else {
					systemParameterMaintenanceService.update(systemParameterDTO, systemParameterDTOBeforeUpdated);
					request.getSession(false).setAttribute("systemParameterDTOForUpdated", systemParameterDTO);
				}
			}
			else{
				systemParameterMaintenanceService.save(systemParameterDTO);
				request.getSession(false).setAttribute("systemParameterDTOForUpdated", systemParameterDTO);
			}
			mv.addObject(systemParameterDTO);
			mv.addObject("saved","Y");
			return mv;
		}
		
		@RequestMapping("/admin/deleteSystemParameter.do")
		public ModelAndView deleteSystemParameter(HttpServletRequest request,@ModelAttribute("systemParameterDTO") SystemParameterDTO systemParameterDTO, BindingResult result, HttpServletResponse response){
	        ModelAndView mv = new ModelAndView("/SystemParameterDetail");
	        
	        SystemParameterDTO systemParameterDTOBeforeDeleted = (SystemParameterDTO) request.getSession().getAttribute("systemParameterDTOForUpdated");
			request.setAttribute("functionMessageKeySelected", "Admin");
			log.info("Before systemParameterMaintenanceService.search");
			if(systemParameterDTO != null){
				if(!systemParameterDTO.equals(systemParameterDTOBeforeDeleted)){
					result.rejectValue("paramName", "error.er0070", "System parameter has been updated before deletion");
					mv = new ModelAndView("/SystemParameterDetail");
					mv.addObject(systemParameterDTO);
					return mv;
				}
				else{
					systemParameterDTOBeforeDeleted = systemParameterMaintenanceService.getByParamName(systemParameterDTO.getParamName());
					if(systemParameterDTOBeforeDeleted != null){ 
						systemParameterMaintenanceService.delete(systemParameterDTO);
					}
					else{
						result.rejectValue("paramName", "error.er0070", "System parameter intended to delete does not exist.");
						mv = new ModelAndView(new RedirectView("/admin/searchSystemParameter.do", true));
						mv.addObject(systemParameterDTO);
						return mv;
					}
				}
			}
			mv.addObject("systemParameterDTOEnquiry",request.getSession(false).getAttribute("systemParameterDTOEnquiry"));
			mv.addObject(systemParameterDTO);
			mv.addObject("deleted","Y");
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
	        HtmlColumn paramName = new HtmlColumn("paramName").title("Parameter Name");  
	        paramName.setCellEditor(new CellEditor() {  
		        public Object getValue(Object item, String property, int rowcount){  
		         
	                HtmlBuilder html = new HtmlBuilder();  
	                String value = (String)ItemUtils.getItemValue(item, property);
	                html.a().href().quote().append("/ea/admin/updateSystemParameter.do?paramName="+value).quote().close();  
	                html.append(value);
	                html.aEnd();  
	                return html.toString();  
		    }  
		   });  
		   row.addColumn(paramName);
		    
		   // show parameter description
	       HtmlColumn paramDesc = new HtmlColumn("paramDesc").title("Parameter Description");  
	       row.addColumn(paramDesc);  
	       return htmlTable; 
		}
}
