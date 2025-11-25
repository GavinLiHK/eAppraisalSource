package com.hkha.ea.controller.common;


import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.EAException;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.common.SearchMessageDTO;
import com.hkha.ea.service.common.CommonSysParaService;

@Controller
public class SearchMessageController extends ListPageController{

	@Override
	public void generateList() throws EAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetList() throws EAException {
		// TODO Auto-generated method stub
		
	}
	
	@Autowired
	private CommonSysParaService commonSysParaService;
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/common/SearchMessage.do")
	public ModelAndView initSearchMessageForm(HttpServletRequest request,@ModelAttribute("searchMessageDTO") SearchMessageDTO searchMessageDTO,HttpServletResponse response){
		 request.setAttribute("functionMessageKeySelected", "Workflow Template Maintenance");
		 ModelAndView mv = new ModelAndView("/common/SearchMessage");
		 String pid= request.getParameter("pId");
		 
		 List<SystemParameterDTO> list=commonSysParaService.SysParaSearch("notification", searchMessageDTO.getParaDesc(), searchMessageDTO.getParaValue());
		 
		 TableModel tableModel = new TableModel("html",request,response);
	 	 tableModel.setItems(list);
	 	 tableModel.setTable(getHtml());  
	 	 request.setAttribute("html",tableModel.render());
	 	 request.setAttribute("pId",pid);
	 	 request.setAttribute("searchMessageDTO",searchMessageDTO);
		
		return mv;
	}
	private Table getHtml() {
		 //New htmlTable 
      HtmlTable htmlTable = new HtmlTable().width("100%");  
    //  htmlTable.setCaptionKey("title");  

      //New HtmlRow 
      HtmlRow row = new HtmlRow();  
      htmlTable.setRow(row);

      //Show parameter name with hyperlink  
      HtmlColumn msgDesc = new HtmlColumn("msgDesc").title("Message Description");  
      msgDesc.setCellEditor(new AbstractCellEditor() {  
	        public Object getValue(Object item, String property, int rowcount){  

				Object pdObj = ItemUtils.getItemValue(item, "paramDesc");
				Object pvObj = ItemUtils.getItemValue(item, "paramValue");
				String paraDesc = (String)pdObj;
				String paraValue = (String)pvObj;
				
	          HtmlBuilder html = new HtmlBuilder();  
              
			html.append("<a href=\"javascript:backPar('"+ paraValue.replaceAll("\n", "<br/>") + "');\">"+paraDesc+"</a>");

              return html.toString();  
	    }  
	   });  
	   row.addColumn(msgDesc);
	    
	   // show parameter description
     HtmlColumn msgContent = new HtmlColumn("msgContent").title("Message Content");  
     msgContent.setCellEditor(new CellEditor() {  
    	 public Object getValue(Object item, String property, int rowcount){  
         
         HtmlBuilder html = new HtmlBuilder();          
			Object pvObj = ItemUtils.getItemValue(item, "paramValue");
			
			String paraValue = (String)pvObj;
			
			html.append(paraValue.replaceAll("\n", "<br/>"));
         return html.toString();  
   }  
  });  
     row.addColumn(msgContent);  

     return htmlTable; 
	}

}
