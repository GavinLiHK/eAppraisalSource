package com.hkha.ea.service.filter;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

// Make it impossible to login through web url
public class UrlParametersAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	private final String authenPage = "login";
	
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		
		if (!servletPath.contains(authenPage)){
			return false;
		}
		
		if (request.getParameterMap().size() == 2) {
	        //return true;
			return false;
	    }
		
	    return false;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		
		if (!servletPath.contains(authenPage)){
			return null;
		}
		
	    String[] credentials = new String[2];
	    credentials[0] = request.getParameter("employeeNumber");
	    credentials[1] = request.getParameter("token");
	    //return credentials;
	    return null;

	}

}
