<%@ page import="com.hkha.ea.common.Constants"%>
<%@ page import="java.net.URLEncoder"%>
<html>
<body>
<%
	String headerEmployeeID = request.getHeader(Constants.HTTP_HEADER_EMPLOYEE_ID);
    String headerIvUser = request.getHeader(Constants.HTTP_HEADER_IV_USER);
	String employeeNumber = request.getParameter("employeeNumber");
	String token = request.getParameter("token");

    // New location to be redirected
    String site = "/ea";
    String msg = "";
	
    /* For testing only
    if (headerEmployeeID == null){
        headerEmployeeID = request.getParameter("header");
    }
    */
    
	if ((employeeNumber != null) && (token != null)){
		site += "/login.do?employeeNumber=" + employeeNumber + "&token=" + token;
	} else if (headerEmployeeID != null){
        site += "/loginSSO.do";
        request.getSession().setAttribute(Constants.HTTP_HEADER_EMPLOYEE_ID, headerEmployeeID);
	} else if (headerIvUser != null){
        site += "/loginSSO.do";
        request.getSession().setAttribute(Constants.HTTP_HEADER_IV_USER, headerIvUser);
    }else {
        msg = "Missing header or employee number";
        msg = URLEncoder.encode(msg, "ISO-8859-1");
        site += "/invalidLogin.do?message="+msg;
    }
    response.sendRedirect(site);
%>
</body>
</html>
