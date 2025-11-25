package com.hkha.ea.controller.assess;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out

public class ButtonBarTag extends TagSupport{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(ButtonBarTag.class.getName());
	//End 20231201 Write log in catalina.out
	
	private String role;
	
	private String currentPage;
	
	private String reportType;

	private String roleChecked;
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	
	
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	public void setRoleChecked(String roleChecked){
		this.roleChecked = roleChecked;
	}
	
	public String getRoleChecked(){
		return roleChecked;
	}

	public int doStartTag() throws JspException {
		   
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='100%' cellpadding='5'>");
		sb.append("<tr><td align='right'>");
		
		String pages[]={};
		
		if("H".equalsIgnoreCase(reportType)||"C".equalsIgnoreCase(reportType)){
			String apPages[]= {"0","1","2"};//{ "PI", "A", "PS" };
			
			String aoPages[]={"0","1","2","3","4","5","6"};//{ "PI", "A", "B1", "B2", "B3", "B4", "PS" };
			
			String coPages[]={"0","1","2","3","4","5","6","7" };//{ "PI", "A", "B1", "B2", "B3", "B4", "B5", "PS" };
			
			String ioPages[]={"0","1","2","3","4","5","6","7","8" };//{ "PI", "A", "B1", "B2", "B3", "B4", "B5", "B6", "PS" };
			 
			String eoPages[]= {"0","1","2","3","4","5","6","7","8","9"};//{ "PI", "A", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "PS" };
			
			String roPages[]= {"0","1","2","3","4","5","6","7","8","9","10"};//{ "PI", "A", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "PS" };
			
			if("AP".equals(role)){
				pages=apPages;			
			}else if("AO".equals(role)){
				pages=aoPages;
			}else if("CO".equals(role)){
				pages=coPages;
			}else if("IO".equals(role)){
				pages=ioPages;
			}else if("EO".equals(role)){
				pages=eoPages;
			}else if("RO".equals(role)||"GM".equals(role)||"SU".equals(role)){
				pages=roPages;
			}
			
			
		}else if("M".equalsIgnoreCase(reportType)){
		
			String apPages[]={"0","1","2"};//{"P1","P2","Ps"};
			String aoPages[]={"0","1","2","3"};//{"P1","P2","P3","Ps"};
			String coPages[]={"0","1","2","3","4"};//{"P1","P2","P3","P4","Ps"};
			String ioPages[]={"0","1","2","3","4","5"};//{"P1","P2","P3","P4","P5","Ps"};
			
			if("AP".equals(role)){
				pages=apPages;
				
			}else if("AO".equals(role)){
				pages=aoPages;
				
			}else if("CO".equals(role)){
				pages=coPages;
			}else if("IO".equals(role)||"GM".equals(role)||"SU".equals(role)){
				pages=ioPages;
			}
		}
		
		for(int i=0;i<pages.length;i++){
			if(pages[i].equalsIgnoreCase(currentPage)){
				if(i==0){
					sb.append("<input type='button' class='btnNext' value='Next' onclick='nextClick(\""+pages[i+1]+"\");'/>");
					sb.append("<input type='button' class='btnSave' value='Save' onclick='saveClick(\""+pages[i]+"\");'/>");
				}else if(i+1==pages.length){
					sb.append("<input type='button' class='btnBack' value='Back' onclick='backClick(\""+pages[i-1]+"\");'/>");				
					sb.append("<input type='button' class='btnSave' value='Save' onclick='saveClick(\""+pages[i]+"\");'/>");
					sb.append("<input type='button' class='btnPrint' value='Print' onclick='printClick();'/>");
					sb.append("<input type='button' class='btnSubmit' value='Send' onclick='sendClick();'/>");
				}else{
					sb.append("<input type='button' class='btnBack' value='Back' onclick='backClick(\""+pages[i-1]+"\");'/>");
					sb.append("<input type='button' class='btnNext' value='Next' onclick='nextClick(\""+pages[i+1]+"\");'/>");
					sb.append("<input type='button' class='btnSave' value='Save' onclick='saveClick(\""+pages[i]+"\");'/>");
				}
			
			}
		}
		
		sb.append("</td></tr></table>");
		 
		try {
			pageContext.getOut().write(sb.toString());
		} catch (IOException e) {			
			e.printStackTrace();
			log.severe(e.toString());
		}
			
		   return EVAL_PAGE;
	  }

}
