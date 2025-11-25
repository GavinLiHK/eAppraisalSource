<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@attribute name="modelAttribute" type="java.lang.String" required="true"%>
	    
    <spring:hasBindErrors name="${modelAttribute}">
		<div id="mvcErrorContainer">
	      <ul id="errorList">
	      <li><form:errors path="*" delimiter="</li><br/><li>"></form:errors></li>
	      </ul>
	    </div>
	</spring:hasBindErrors>
	
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
	    font-size: 12px;
	    color: #FF0000;
	   
	    font-weight:bold;
	    
	}
</style>

