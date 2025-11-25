package com.hkha.ea.common;
import java.io.IOException;
import java.util.Properties;

//20231201 Write log in catalina.out
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

public class Message {
	
	//20231201 Write log in catalina.out
//	private static Log log = LogFactory.getLog(LogFactory.class);
	private static Logger log = Logger.getLogger(Message.class.getName());
	//End 20231201 Write log in catalina.out

	private static final Resource resource = new ClassPathResource("/client_config.properties");
	
	private static Properties _p = new Properties();

	public static final String ERROR = "error";

	public static final String INFO = "info";

	
	public static String getErrorMessage(String code) {
		String errorMsg = "";
		errorMsg = _p.getProperty(ERROR + "." + code);
		return errorMsg;
	}

	public static String getInfoMessage(String code) {
		String infoMsg = "";
		infoMsg = _p.getProperty(INFO + "." + code);
		return infoMsg;
	}

	public static String getProperty(String code) {
		return _p.getProperty(code);
	}

	public static void setProperty(){
		try {
			_p = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
			log.severe(e.toString());
		}
	}
}