package com.hkha.ea.domain;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class LinkForbiddenEntryPoint implements AuthenticationEntryPoint{

	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String msg = "You are not allowed to access this resource";
		msg = URLEncoder.encode(msg, "ISO-8859-1");
	    httpResponse.sendRedirect("/ea/invalidLogin.do?message="+msg);
	}

}
