package com.hkha.ea.controller.common;


import jakarta.servlet.http.HttpServletRequest;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.service.admin.SendReminderService;

@Controller
public class SendAutoReminderController {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SendAutoReminderController.class.getName());
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
	@RequestMapping("/common/SendAutoReminder.do")
	public ModelAndView sendAutoReminder(HttpServletRequest request) {
		log.info("Start sending Auto-Reminder");
		sendReminderService.sendAutoReminder();
		log.info("Send Auto-Reminder completed");
		return new ModelAndView("/common/requestCompleted");
	}
	
}
