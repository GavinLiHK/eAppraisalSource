package com.hkha.ea.controller.common;

import java.util.List;
import java.io.UnsupportedEncodingException;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

import org.apache.commons.lang3.StringEscapeUtils;
//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.jmesa.model.TableModel;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Util;
import com.hkha.ea.dto.common.EmployeeEnquiryDTO;
import com.hkha.ea.service.common.EmployeeEnquiryService;




@Controller
public class EmployeeEnquiryController extends ListPageController{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(EmployeeEnquiryController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private EmployeeEnquiryService employeeEnquiryService;

    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/common/EmployeeEnquiry.do")
	public ModelAndView searchEmployeeEnquiryForm(HttpServletRequest request,@ModelAttribute("employeeEnquiryDTO") EmployeeEnquiryDTO employeeEnquiryDTO,HttpServletResponse response){
		 request.setAttribute("functionMessageKeySelected", "Employee Enquiry");
		 ModelAndView mv = new ModelAndView("/common/EmployeeEnquiry");
		 String pidNum= request.getParameter("pidNum");
		 String pidName= request.getParameter("pidName");	
		 String pidEmpName = request.getParameter("pidEmpName");
		 String isInit = request.getParameter("initPage");

		 /*if(null!=employeeEnquiryDTO.getRank()||null!=employeeEnquiryDTO.getFirstName()||
				 null!=employeeEnquiryDTO.getLastName()||null!=employeeEnquiryDTO.getPostTitle()||
				 null!=employeeEnquiryDTO.getPostUnit()||null!=employeeEnquiryDTO.getEmployeeNum()){*/
		 if(employeeEnquiryDTO != null){
			 if(!Util.isEmptyString(isInit)&&Util.isEmptyString(pidEmpName)){
				 List<EmployeeEnquiryDTO> list= employeeEnquiryService.search(employeeEnquiryDTO.getRank(),employeeEnquiryDTO.getPostUnit(),employeeEnquiryDTO.getPostTitle(),employeeEnquiryDTO.getFirstName(),employeeEnquiryDTO.getLastName(),employeeEnquiryDTO.getEmployeeNum());
				 
				 TableModel tableModel = new TableModel("html",request,response);
			 		tableModel.setItems(list);
			 		tableModel.setTable(getHtml());  
			 		request.setAttribute("html",tableModel.render());
			 
			//20161229 		
			 }else if(!Util.isEmptyString(pidEmpName)){
				 List<EmployeeEnquiryDTO> list= employeeEnquiryService.searchEmployeeByName(pidEmpName);
				 TableModel tableModel = new TableModel("html",request,response);
			 		tableModel.setItems(list);
			 		tableModel.setTable(getHtml());  
			 		request.setAttribute("html",tableModel.render());
			 }

		 }

		request.setAttribute("pidNum",pidNum);
		request.setAttribute("pidName",pidName);
		
		request.setAttribute("employeeEnquiryDTO",employeeEnquiryDTO);
		
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
	@RequestMapping("/common/searchEmployeeName.do")
	@ResponseBody
	public String searchEmployeeName(HttpServletRequest request, HttpServletResponse response){
		 request.setAttribute("functionMessageKeySelected", "Employee Enquiry");
		 String num = request.getParameter("num");
		 String name = employeeEnquiryService.searchEmployeeName(num);
		return name;	
	}

    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	//20170106 added by joanna for searching employee by name
	@RequestMapping("/common/searchNoOfEmployee.do")
	@ResponseBody
	public String searchNoOfEmployee(HttpServletRequest request, HttpServletResponse response){
		 request.setAttribute("functionMessageKeySelected", "Employee Enquiry");
		 String name = request.getParameter("name");
		 String id = request.getParameter("pid");
		 String num = "";
		 List<EmployeeEnquiryDTO> list= employeeEnquiryService.searchEmployeeByName(name);
		 
		 if(id.equalsIgnoreCase("searchMulti")){
			 num = String.valueOf(list.size());
		 }else if(id.equalsIgnoreCase("searchSingle")){
			 num = list.get(0).getEmployeeNum();
		 }
		return num;	
	}
	//20170106 ended by joanna
	private Table getHtml() {
		 //New htmlTable 
     HtmlTable htmlTable = new HtmlTable().width("100%");  
   //  htmlTable.setCaptionKey("title");  

     //New HtmlRow 
     HtmlRow row = new HtmlRow();  
     htmlTable.setRow(row);

     //Show parameter name with Rank
     HtmlColumn rank = new HtmlColumn("rank").title("Rank");  
     rank.setStyleClass("display:none");
     rank.setCellEditor(new AbstractCellEditor() {  
	        public Object getValue(Object item, String property, int rowcount){  				
				HtmlBuilder html = new HtmlBuilder();  
				 Object obj = ItemUtils.getItemValue(item, "rank");
				   String str = (String)obj;
				   html.append(str);
				   html.aEnd();
				   return html.toString();                       
	    }  
	   });  
	   row.addColumn(rank);
	    
	 //Show parameter name with PostUnit
	   HtmlColumn postUnit = new HtmlColumn("postUnit").title("Post Unit");  
	   rank.setStyle(HIDDEN_FIELD_TRUE_VALUE);
	   postUnit.setCellEditor(new CellEditor() {  
		   public Object getValue(Object item, String property, int rowcount){          
			   HtmlBuilder html = new HtmlBuilder();          
			   Object obj = ItemUtils.getItemValue(item, "postUnit");			  
			   String str = (String)obj;
			   html.append(str);
			   html.aEnd();
			   return html.toString();  
		   }  
	   });  
	   row.addColumn(postUnit);  
	   
	   //Show parameter name with PostTitle
	  /* HtmlColumn postTitle = new HtmlColumn("postTitle").title("Post Title");  
	   rank.setStyle(HIDDEN_FIELD_TRUE_VALUE);
	   postTitle.setCellEditor(new CellEditor() {  
		   public Object getValue(Object item, String property, int rowcount){          
			   HtmlBuilder html = new HtmlBuilder();          
			   Object obj = ItemUtils.getItemValue(item, "postTitle");
			   String str = (String)obj;
			   html.append(str);
			   return html.toString();  
		   }  
	   });  
	   row.addColumn(postTitle); */ 
	   
	   //Show parameter name with employeeNumebr
	   HtmlColumn employeeNum = new HtmlColumn("employeeNum").title("Employee Number");  
	   employeeNum.setCellEditor(new CellEditor() {  
		   public Object getValue(Object item, String property, int rowcount){          
			   HtmlBuilder html = new HtmlBuilder();          
			   Object obj = ItemUtils.getItemValue(item, "employeeNum");
			   Object objf = ItemUtils.getItemValue(item, "employeeFullName");
			   Object objl = ItemUtils.getItemValue(item, "postUnit");
			   String strn = (String)obj;
			   //edited on 20170322 Search employee name
			   //String strf= (String)objf+" "+(String)objl;
			   String strf= (String)objf;
			   //end edited on 20170322
			   strf = StringEscapeUtils.escapeHtml4(strf);
			   
			   html.append("<a href=\"javascript:returnPar('" + strn + "','" + strf+ "');\">"+strn+"</a>");  
			   return html.toString();    
		   }  
	   });  
	   row.addColumn(employeeNum);  
	   
	   //Show parameter name with employeeFullName
	   HtmlColumn employeeFullName = new HtmlColumn("employeeFullName").title("Employee Name");  
	   /*employeeFullName.setCellEditor(new CellEditor() {  
		   public Object getValue(Object item, String property, int rowcount){          
			   HtmlBuilder html = new HtmlBuilder();          
			   String value = (String)ItemUtils.getItemValue(item, "employeeFullName");
			   html.append(value);
			   html.aEnd();
			   return html.toString();  
		   }  
	   });*/  
	   row.addColumn(employeeFullName);  
	   
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
