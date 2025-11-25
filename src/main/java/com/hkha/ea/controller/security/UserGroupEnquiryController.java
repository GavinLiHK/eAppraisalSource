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

import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.security.FunctionDto;
import com.hkha.ea.dto.security.GroupFunctionDto;
import com.hkha.ea.dto.security.GroupRankDto;
import com.hkha.ea.dto.security.RankDto;
import com.hkha.ea.dto.security.UserGroupEnquiryDTO;
import com.hkha.ea.service.security.UserGroupMaintenanceService;
import com.hkha.ea.validator.security.UserGroupValidator;

@Controller
public class UserGroupEnquiryController{

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(UserGroupEnquiryController.class.getName());
	//End 20231201 Write log in catalina.out

	@Autowired
	private UserGroupMaintenanceService userGroupMaintenanceService;
	
	@Autowired
	private UserGroupValidator userGroupValidator;

    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/security/UserGroupEnquiry.do")
	public ModelAndView init(HttpServletRequest request){
        log.info("***Function first entered");
		
        ModelAndView mv = new ModelAndView("security/UserGroupEnquiry");
        
		request.setAttribute("functionMessageKeySelected", "User Group Enquiry");
		
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
	@RequestMapping("/security/searchUserGroupEnquiry.do")
	public ModelAndView search(HttpServletRequest request,@ModelAttribute("userGroupEnquiryDTO") UserGroupEnquiryDTO userGroupEnquiryDTO, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("/security/UserGroupEnquiry");
        
		request.setAttribute("functionMessageKeySelected", "User Group Enquiry");
		
		log.info("Before UserGroupEnquiryService.search");
	
		List<UserGroupEnquiryDTO> results = userGroupMaintenanceService.search(userGroupEnquiryDTO);
		
		log.info("After UserGroupEnquiryService.search"+results);
		
		TableModel tableModel = new TableModel("html",request,response);  
		tableModel.setItems(results);
		tableModel.setTable(getHtml());  
		request.setAttribute("html",tableModel.render());
		mv.addObject(userGroupEnquiryDTO);
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
        HtmlColumn paramName = new HtmlColumn("userGroupName").title("User Group Name");  
        paramName.setCellEditor(new CellEditor() {  
	        public Object getValue(Object item, String property, int rowcount){  
	         
                HtmlBuilder html = new HtmlBuilder();  
                String value = (String)ItemUtils.getItemValue(item, property);
                Object obj1 = ItemUtils.getItemValue(item, "groupId");
                long groupId = (Long)obj1;
                
                /*
                #Spring5Upgrade #OpenJDK11 #Java11
                Following part was created to use modelAttribute on 20/11/2021
    
                Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
                	the Spring framework was required to upgrade from version 4 to version 5.
    
                The @RequestMapping must be exactly matched with the URL.
    
                Modified on 20/11/2021
                */
                html.a().href().quote().append("/ea/security/updateUserGroupDetail.do?userGroupName="+value+"&&groupId="+groupId).quote().close();  
                html.append(value);
                html.aEnd();  
                return html.toString();  
	    }  
	   });  
	   row.addColumn(paramName);
  
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
	@RequestMapping("/security/updateUserGroupDetail.do")
	public ModelAndView updateSystemParameter(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("Entering update User Group Detail function ");
        ModelAndView mv = new ModelAndView("security/UserGroupDetail");
        
        String userGroupName = request.getParameter("userGroupName");
        String obj = request.getParameter("groupId");
        long groupId = Long.valueOf(obj);
        
		request.setAttribute("functionMessageKeySelected", "System Parameter Maintenance");
		
		List<RankDto> avaRankList = userGroupMaintenanceService.getRankList(groupId);
		List<GroupRankDto> selRankList = userGroupMaintenanceService.getGroupRankList(groupId);
		List<GroupFunctionDto> groupFunList = userGroupMaintenanceService.getGroupFunctionList(groupId);
		
		//SystemParameterDTO systemParameterDTO = systemParameterMaintenanceService.getByParamName(paramName);
		UserGroupEnquiryDTO dto = new UserGroupEnquiryDTO();
		dto.setGroupId(groupId);
		dto.setUserGroupName(userGroupName);
		dto.setFunctionList(groupFunList);
		dto.setSelRankList(selRankList);
		
		request.getSession(false).setAttribute("avaRankList", avaRankList);
        mv.addObject("isAdd",false);
		mv.addObject(dto);
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
	@RequestMapping("/security/addUserGroupDetail.do")
	public ModelAndView initDetail(HttpServletRequest request){
        log.info("***Function first entered");
		
        ModelAndView mv = new ModelAndView("security/UserGroupDetail");
        
        UserGroupEnquiryDTO dto = new UserGroupEnquiryDTO();
        List<RankDto> avaRankList = userGroupMaintenanceService.getRankList(0L);
        List<GroupFunctionDto> funcList = userGroupMaintenanceService.getFunctionList();
        
        dto.setFunctionList(funcList);
/*        mv.addObject("avaRankList",avaRankList);
        mv.addObject("funcList",funcList);
        mv.addObject("selRankList",selRankList);*/
       // mv.addObject("avaRankList",avaRankList);
        
        request.getSession(false).setAttribute("avaRankList", avaRankList);
        mv.addObject("isAdd",true);
        mv.addObject(dto);
        
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
	@RequestMapping("/security/saveUserGroupDetail.do")
	public ModelAndView save(HttpServletRequest request,@ModelAttribute("userGroupEnquiryDTO") UserGroupEnquiryDTO userGroupEnquiryDTO, BindingResult result, HttpServletResponse response){
        log.info("***Function save start");
        
		log.info("group function access right========="+userGroupEnquiryDTO.getFunctionarr());
		log.info("group rank========="+userGroupEnquiryDTO.getSelRankarr());
		
		List<GroupFunctionDto> funcListadd = userGroupMaintenanceService.getFunctionList();
		List<GroupRankDto> selRankList = convertRankArr(userGroupEnquiryDTO.getSelRankarr());
		List<GroupFunctionDto> funcList = convertFunArr(funcListadd,userGroupEnquiryDTO.getFunctionarr());
		
		userGroupEnquiryDTO.setSelRankList(selRankList);
		userGroupEnquiryDTO.setFunctionList(funcList);
		
        ModelAndView mv = new ModelAndView("security/UserGroupDetail");
        userGroupValidator.validateUserGroup(userGroupEnquiryDTO, result);
        
        if(result.hasErrors()){
        	mv.addObject("numberOfErrors",result.getErrorCount());
			mv.addObject(userGroupEnquiryDTO);
			return mv;
        }
        
        log.info("user group id========="+userGroupEnquiryDTO.getGroupId());
        if(userGroupEnquiryDTO.getGroupId() == null){
        	userGroupMaintenanceService.insert(userGroupEnquiryDTO);
        }else{
        	userGroupMaintenanceService.update(userGroupEnquiryDTO);
        }
        
        request.getSession(false).setAttribute("avaRankList", convertRank(userGroupEnquiryDTO.getAvaRankarr()));
        mv.addObject(userGroupEnquiryDTO);
        mv.addObject("saved","Y");
		return mv;
	}

	private List<GroupFunctionDto> convertFunArr(List<GroupFunctionDto> funlist,String functionarr) {
		List<GroupFunctionDto> list = new ArrayList<GroupFunctionDto>();
		String[] funObj = functionarr.split(",");
		for(int i=0;i<funObj.length;i++){
			GroupFunctionDto dto = new GroupFunctionDto();
			String [] func = funObj[i].toString().split(";");
			dto.setFunctionDesc(funlist.get(i).getFunctionDesc());
			dto.setFunctionId(Long.valueOf(func[0].toString()));
			dto.setAccessRight(func[1].toString());
			
			list.add(dto);
			
		}
		return list;
	}

	private List<GroupRankDto> convertRankArr(String selRankarr) {
		String[] ran = selRankarr.split(",");
		List<GroupRankDto> list = new ArrayList<GroupRankDto>();
		for(int i=0;i<ran.length;i++){
			GroupRankDto dto = new GroupRankDto();
			dto.setRank(ran[i].toString());
			
			list.add(dto);
		}
		return list;
	}
	
	private List<RankDto> convertRank(String selRankarr) {
		String[] ran = selRankarr.split(",");
		List<RankDto> list = new ArrayList<RankDto>();
		for(int i=0;i<ran.length;i++){
			RankDto dto = new RankDto();
			dto.setRank(ran[i].toString());
			
			list.add(dto);
		}
		return list;
	}
}
