<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<html>
<head>
<script type="text/javascript">
	$( document ).ready(function() {
	    var numberOfErrors = $('#numberOfErrors').val();
	    
	    	if(numberOfErrors != null && numberOfErrors != ''){
	    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text="Are you sure to delete the system parameter?"/>');
	    	}
	});
</script>
</head>
<body>
<form:form name="pageForm" modelAttribute="searchFinalReportDto" action="${ctx}/assess/searchFinalReport.do?isSearch=Y" method="post">
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Display Final Report > Search Report
	        </td>
	      </tr>
	      <tr>
	        <td>
	        <!--
	        Following part was created to use modelAttribute on 20/11/2021
	        
	        Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
	        	the Spring framework was required to upgrade from version 4 to version 5.
	        
	        The commandName tag is deprecated since Spring framework 4.3, the modelAttribute tag replaces it in Spring 5.
	        
	        Modified on 20/10/2021
	        -->
	        <common:mvcErrorTable modelAttribute="searchFinalReportDto"/>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>
  <table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='${ctx}/images/dot.gif'></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
									Show Final Report </td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
				</tr>
				 <!-- portlet body -->
				
					<tr height="100%">
						<td bgcolor="#CFD9E5" height="100%" width="1"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
						<td width="100%" valign="top">
							<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
								<tr>
									<td dir="ltr" valign="top">
					  
					  <!-- #1 Report type-->
					  <table width="100%"  border="0" cellpadding="0">
						<tr>
						  <td width="28%">Full Report Type *</td> 
						  <td width="72%">
						  	<form:select id="reportType" path="reportType" classname="inputfield">
						  		<form:option value="C"> Full report for Contract Staff</form:option>
								<form:option value="H"> Full report for Contract Housing Officer</form:option>
								<form:option value="M"> Memo format report</form:option>
						  	</form:select>
						  </td>
						</tr>						
						<!-- #2 Year-->

						<!-- #3 Rank-->
						<tr>
						  <td>Rank</td> 
						  <td><form:input type="text" path="rank" classname="editableinputfield" size="20"  maxlength="40"/></td>
					    </tr>
						<!-- #4 Post unit-->
						<tr>
						  <td>Post Unit </td>
						  <td><form:input type="text" path="postUnit" classname="editableinputfield" size="20"  maxlength="40"/></td>
						</tr>
						<!-- #5 Employee number-->
						<tr>
						  <td width="20%">Employee Name</td> 
						  <td>
						  <common:employeeSearch empNumId="employeeNumber" empNum="employeeNumber" empNameId="employeeName" empName="employeeName" empNumHidden="none"/>
						  </td>
					    </tr>
<!-- #6 Appraisal start date-->
	<tr>
      <td width="28%">Commence Date Start Period *</td>
      <td>
      <common:commonDate id="commenceStartDate" name="commenceStartDate" /> 
	  </td>
    </tr>
<!-- #7 Appraisal end date-->
	<tr>
      <td width="28%">Commence Date End Period *</td>
      <td>
      <common:commonDate id="commenceEndDate" name="commenceEndDate" /> 
</td>
    </tr>


					  </table>
									</td>
								</tr>
							</table>
						</td>
						<td bgcolor="#CFD9E5" width="1"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
					</tr>
				
				 <!-- bottom border -->
				<tr height="1">
				<td bgcolor="#CFD9E5" width="100%" height="1" colspan="3">
				</tr>
			</table>
		</td>
	</tr>
</table>

  <br>

  <!-- Buttons -->
<table width="100%"  border="0">
    <tr>
      <td>&nbsp;</td>
      <td align="right">
		<input type="submit" name="btnSearch" value="Search"></input>
		<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href=getJunction('/ea/')+'${ctx}/assess/ListOutstandingReport.do'"></input>
      </td>
    </tr>
  </table>
  <p>&nbsp;</p>
</form:form>
</body>
</html>
