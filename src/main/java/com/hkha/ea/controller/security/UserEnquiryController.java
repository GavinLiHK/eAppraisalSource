package com.hkha.ea.controller.security;

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

import com.hkha.ea.dto.common.EaUserDTO;
import com.hkha.ea.dto.security.UserEnquiryDto;
import com.hkha.ea.dto.security.UserGroupEnquiryDTO;
import com.hkha.ea.service.security.UserEnquireService;
import com.hkha.ea.service.security.UserGroupMaintenanceService;
import com.hkha.ea.validator.security.UserEnquireValidator;

@Controller
public class UserEnquiryController{

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(UserEnquiryController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private UserGroupMaintenanceService userGroupMaintenanceService;
	
	@Autowired
	private UserEnquireService userEnquireService;
	
	@Autowired
	private UserEnquireValidator userEnquireValidator;
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/security/UserEnquiry.do")
	public ModelAndView initUserEnquiry(HttpServletRequest request,@ModelAttribute("userEnquiryDto") UserEnquiryDto userEnquiryDto,HttpServletResponse response){ 
        log.info("***Function first entered");
		
        ModelAndView mv = new ModelAndView("security/UserEnquiry");
        UserGroupEnquiryDTO dto = null;
        List<UserGroupEnquiryDTO> groupNameList = userGroupMaintenanceService.search(dto);
        
        request.getSession().setAttribute("groupNameList",groupNameList);
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
	@RequestMapping("/security/searchUserEnquiry.do")
	public ModelAndView search(HttpServletRequest request,@ModelAttribute("userEnquiryDto") UserEnquiryDto userEnquiryDto, HttpServletResponse response){
        log.info("***Function first entered");
        log.info("***userEnquiry data===="+userEnquiryDto.getEnable());
        ModelAndView mv = new ModelAndView("security/UserEnquiry");
        
		List<UserEnquiryDto> results = userEnquireService.search(userEnquiryDto);
		//List<UserGroupEnquiryDTO> groupNameList = userGroupMaintenanceService.search(null);
		
		log.info("After UserEnquiryService.search");
		
		TableModel tableModel = new TableModel("html",request,response);  
		tableModel.setItems(results);
		tableModel.setTable(getHtml());  
		request.setAttribute("html",tableModel.render());
		
        //mv.addObject("groupNameList",groupNameList);
		mv.addObject(userEnquiryDto);
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
       HtmlColumn paramName = new HtmlColumn("userId").title("User ID");  
       paramName.setCellEditor(new CellEditor() {  
	        public Object getValue(Object item, String property, int rowcount){  
	         
               HtmlBuilder html = new HtmlBuilder();  
               String value = (String)ItemUtils.getItemValue(item, property);
               String groupName = (String)ItemUtils.getItemValue(item, "groupName");
               String enable = (String)ItemUtils.getItemValue(item, "enable");
               Object obj = ItemUtils.getItemValue(item, "groupId");
               Long groupId = (Long)obj;
               
               /*
               #Spring5Upgrade #OpenJDK11 #Java11
               Following part was created to use modelAttribute on 20/11/2021
    
               Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
               	the Spring framework was required to upgrade from version 4 to version 5.
    
               The @RequestMapping must be exactly matched with the URL.
    
               Modified on 20/11/2021
               */
               html.a().href().quote().append("/ea/security/updateUserDetail.do?userId="+value+"&&groupId="+groupId+"&&groupName="+groupName+"&&enable="+enable).quote().close();  
               html.append(value);
               html.aEnd();  
               return html.toString();  
	    }  
	   });  
	   row.addColumn(paramName);
 
	   HtmlColumn groupName = new HtmlColumn("groupName").title("Group Name");  
       row.addColumn(groupName); 
       
       HtmlColumn enable = new HtmlColumn("enable").title("Enable");  
       row.addColumn(enable); 
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
	@RequestMapping("/security/addUserDetail.do")
	public ModelAndView addUserDetail(HttpServletRequest request){
        log.info("***Function first entered");
		
        ModelAndView mv = new ModelAndView("security/UserDetail");
        
        //List<UserGroupEnquiryDTO> groupNameList = userGroupMaintenanceService.search(null);
        
        //mv.addObject("groupNameList",groupNameList);
        request.getSession(false).setAttribute("isAdd", true);
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
	@RequestMapping("/security/updateUserDetail.do")
	public ModelAndView updateUserDetail(HttpServletRequest request,@ModelAttribute("userEnquiryDto") UserEnquiryDto userEnquiryDto, HttpServletResponse response){
        log.info("***Function first entered");
		
        ModelAndView mv = new ModelAndView("security/UserDetail");
        
        String userId = request.getParameter("userId");
        String userGroupName = request.getParameter("groupName");
        String obj = request.getParameter("groupId");
        long groupId = Long.valueOf(obj);
        String enable = request.getParameter("enable"); 
        
        userEnquiryDto.setUserId(userId);
        userEnquiryDto.setGroupName(userGroupName);
        userEnquiryDto.setEnable(enable);
        
        //List<UserGroupEnquiryDTO> groupNameList = userGroupMaintenanceService.search(null);
        
        //mv.addObject("groupNameList",groupNameList);
        mv.addObject(userEnquiryDto);
        request.getSession(false).setAttribute("isAdd", false);
        request.getSession(false).setAttribute("oldUserId", userId);
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
	@RequestMapping("/security/saveUserDetail.do")
	public ModelAndView save(HttpServletRequest request,@ModelAttribute("userEnquiryDto") UserEnquiryDto userEnquiryDto, BindingResult result, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("security/UserDetail");
		boolean isAdd = (Boolean) request.getSession().getAttribute("isAdd");
		String oldUserId = (String) request.getSession().getAttribute("oldUserId");
		//List<UserGroupEnquiryDTO> groupNameList = userGroupMaintenanceService.search(null);
		
        //mv.addObject("groupNameList",groupNameList);
        
		userEnquireValidator.validateUserEnquire(userEnquiryDto, result);
		if(result.hasErrors()){
			mv.addObject("numberOfErrors",result.getErrorCount());
			mv.addObject(userEnquiryDto);
			return mv;
		}
		
		EaUserDTO dto = userEnquireService.searchByUserID(userEnquiryDto.getUserId());
		if(dto != null){
			if(isAdd){
				result.rejectValue("userId", "error.er0033", "Unqiue user id is required.");
				mv.addObject(userEnquiryDto);
				return mv;
			}else{
				if(!userEnquiryDto.getUserId().equals(oldUserId)){
					result.rejectValue("userId", "error.er0033", "Unqiue user id is required.");
					mv.addObject(userEnquiryDto);
					return mv;
				}
			}
			
		}
		
		if(isAdd){
			userEnquireService.insert(userEnquiryDto);
		}else{
			
			userEnquireService.update(userEnquiryDto,oldUserId);
		}
		mv.addObject("saved","Y");
		request.getSession(false).setAttribute("oldUserId", userEnquiryDto.getUserId());
		request.getSession(false).setAttribute("isAdd", false);
		return mv;
	}
}
