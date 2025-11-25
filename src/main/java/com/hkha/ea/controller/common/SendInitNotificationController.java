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

import com.hkha.ea.service.common.SendInitNotificationService;

@Controller
public class SendInitNotificationController {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SendInitNotificationController.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private SendInitNotificationService sendInitNotificationService;
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	@RequestMapping("/common/SendInitNotification.do")
	public ModelAndView SendInitNotification(HttpServletRequest request) {
		log.info("Start sending initial notification");
		sendInitNotificationService.executeBatch();
		log.info("Send initial notification completed");
		return new ModelAndView("/common/requestCompleted");
	}
}
