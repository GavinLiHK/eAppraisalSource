<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<style type="text/css">
	#mvcErrorContainer{
	  float:left;
  	  text-align: left; 
  	  width:100%;
  	  background-color:#E0FFFF;	  
	}
	#mvcErrorContainer LI { 
		margin-left:0px;
		
		LINE-HEIGHT: normal;
		font-family: Arial, Helvetica, sans-serif;
	    font-size: 16px;
	    color: #FF0000;
	   
	    font-weight:bold;
	    
	}
</style>
</head>
<body>

<div id="mvcErrorContainer">
	<ul id="errorList">
	 <li>${errors}</li>
	</ul>
</div>

</body>
</html>