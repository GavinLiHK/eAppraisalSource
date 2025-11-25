package com.hkha.ea.controller.assess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out

public class DtreeMenuTag extends TagSupport{
	
	private String role;
	
	private String pageId;
	
	private String reportType;
	
	private String roleChecked;
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}	
	
	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
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
	   
		boolean showTag = false;
	
		List<String> pages = new ArrayList<String>();
		
		
		if("M".equalsIgnoreCase(reportType)){
			/*
			if("AP".equals(role)){
				pages.add("P1");
				pages.add("P2");
				pages.add("Ps");
			}else if("AO".equals(role)){
				pages.add("P1");
				pages.add("P2");
				pages.add("P3");
				pages.add("Ps");
			}else if("CO".equals(role)){
				pages.add("P1");
				pages.add("P2");
				pages.add("P3");
				pages.add("P4");
				pages.add("Ps");
			}else if("IO".equals(role)||"GM".equals(role)||"SU".equals(role)){
				pages.add("P1");
				pages.add("P2");
				pages.add("P3");
				pages.add("P4");
				pages.add("P5");
				pages.add("Ps");
			}*/
			pages.add("P1");
			pages.add("P2");
			pages.add("P3");
			pages.add("P4");
			pages.add("P5");
			pages.add("Ps");
		}else if("C".equalsIgnoreCase(reportType)||"H".equalsIgnoreCase(reportType)){
			/*if("AP".equals(role)){
				pages.add("PI");
				pages.add("A");
				pages.add("PS");
			}else if("AO".equals(role)){
				pages.add("PI");
				pages.add("A");
				pages.add("B1");
				pages.add("B2");
				pages.add("B3");
				pages.add("B4");			
				pages.add("PS");
			}else if("CO".equals(role)){
				pages.add("PI");
				pages.add("A");
				pages.add("B1");
				pages.add("B2");
				pages.add("B3");
				pages.add("B4");
				pages.add("B5");
				pages.add("PS");
			}else if("IO".equals(role)){
				pages.add("PI");
				pages.add("A");
				pages.add("B1");
				pages.add("B2");
				pages.add("B3");
				pages.add("B4");
				pages.add("B5");
				pages.add("B6");
				pages.add("PS");
			}else if("EO".equals(role)){
				pages.add("PI");
				pages.add("A");
				pages.add("B1");
				pages.add("B2");
				pages.add("B3");
				pages.add("B4");
				pages.add("B5");
				pages.add("B6");
				pages.add("B7");
				pages.add("PS");
			}else if("RO".equals(role)||"GM".equals(role)||"SU".equals(role)){
				pages.add("PI");
				pages.add("A");
				pages.add("B1");
				pages.add("B2");
				pages.add("B3");
				pages.add("B4");
				pages.add("B5");
				pages.add("B6");
				pages.add("B7");
				pages.add("B8");				
				pages.add("PS");
			}*/

			pages.add("PI");
			pages.add("A");
			pages.add("B1");
			pages.add("B2");
			pages.add("B3");
			pages.add("B4");
			pages.add("B5");
			pages.add("B6");
			if(roleChecked.equalsIgnoreCase("B7RoleChecked")){
				pages.add("B7");
			}
			
			pages.add("B8");				
			pages.add("PS");

		}

		if(pages.contains(pageId)){
			showTag = true;
		}
	    
	    if(showTag){
			return EVAL_BODY_INCLUDE;
		}else{
			return SKIP_BODY;
		}


	  }
}
