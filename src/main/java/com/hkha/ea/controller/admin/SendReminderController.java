package com.hkha.ea.controller.admin;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.Util;
import com.hkha.ea.dto.admin.ReportReminderDto;
import com.hkha.ea.service.admin.SendReminderService;

@Controller
public class SendReminderController {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SendReminderController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private SendReminderService sendReminderService;
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/admin/initSendManualReminder.do")
	public ModelAndView initSendManualReminder(HttpServletRequest request,
			@ModelAttribute("reportReminderDto") ReportReminderDto reportReminderDto, BindingResult result, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView();
		String selRpId = request.getParameter("selRpIds");
		
		List<Long> reportIds = this.convertToList(selRpId);
		/*if(reportIds.size() <= 0){
			mv = new ModelAndView("forward:/admin/searchMonitorReport.do");
			result.rejectValue("messageBody", "error."+Constants.INFO_RECORD_NOT_FOUND);
			mv.addObject("numberOfErrors",result.getErrorCount());
			return mv;
		}*/
		reportReminderDto = sendReminderService.generateList(request,reportIds);
		mv = new ModelAndView("/admin/SendReminder");
		mv.addObject(reportReminderDto);
		return mv;
	}

	private List<Long> convertToList(String selRpId) {
		List<Long> reportIds = new ArrayList<Long>();
		if(!Util.isEmptyString(selRpId)){
			String[] s = selRpId.split(",");
			if(s != null && s.length > 0){
				for(int i=0; i<s.length; i++){
					String rp = s[i];
					if(!Util.isEmptyString(rp)){
						//reportIds.add(Long.valueOf(rp));
						rp = rp.replaceAll("[\\[\\](){}]","");
						long id = Long.parseLong(rp.trim());						
						reportIds.add(id);
					}
						
				}
			}
		}
		return reportIds;
	}
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/admin/sendManualReminder.do")
	public ModelAndView sendManualReminder(HttpServletRequest request,
			@ModelAttribute("reportReminderDto") ReportReminderDto reportReminderDto, BindingResult result, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView("/admin/SendReminder");
		String status = "";
		try {
			
			status = sendReminderService.sendNotification(request,reportReminderDto);
			if ("valid".equals(status)) {
				mv = new ModelAndView("/admin/SendReminder");
				result.rejectValue("messageBody", "error."+Constants.ERROR_EMPLOYEE_NO_VALID_EMAIL);
				mv.addObject("numberOfErrorsForReminder",result.getErrorCount());
				return mv;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.severe(e.toString());
			
		}finally {
			mv.addObject("saved","Y");
			//log.info(status);
		}
			
		return mv;
	}
}
