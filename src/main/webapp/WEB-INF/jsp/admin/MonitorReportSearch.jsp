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
<!--
#Spring5Upgrade #OpenJDK11 #Java11
Following part was created to use modelAttribute on 20/11/2021

Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
	the Spring framework was required to upgrade from version 4 to version 5.

The commandName tag is deprecated since Spring framework 4.3, the modelAttribute tag replaces it in Spring 5.

Modified on 20/10/2021
-->
<form:form name="pageForm" modelAttribute="monitorReportSearchDto" action="${ctx}/admin/searchMonitorReport.do" method="post">
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<form:hidden id="isSearch" path="isSearch"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Monitor Report Progress > Search Report
	        </td>
	      </tr>
	      <tr>
	        <td>
	        <!--
	        #Spring5Upgrade #OpenJDK11 #Java11
	        Following part was created to use modelAttribute on 20/11/2021
	        
	        Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
	        	the Spring framework was required to upgrade from version 4 to version 5.
	        
	        The commandName tag is deprecated since Spring framework 4.3, the modelAttribute tag replaces it in Spring 5.
	        
	        Modified on 20/10/2021
	        -->
	        <common:mvcErrorTable modelAttribute="monitorReportSearchDto"/>
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
			                        Search Appraisees
								</td>
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
					  <table width="100%"  border="0" cellpadding="0">
						<tr>
						  <td width="35%">
                             Appraisee Rank
                          </td>
						  <td width="65%">
                             <input type="text" name="rank" value="${monitorReportSearchDto.rank}" classname="editableinputfield" size="40" maxlength="40"></input>
						  </td>
					    </tr>
						<tr>
						  <td>
                             Appraisee Post Unit
						  </td>
						  <td>
                             <input type="text" name="postUnit" value="${monitorReportSearchDto.postUnit}" classname="editableinputfield" size="40" maxlength="40"></input>
						  </td>
					    </tr>
						<tr>
						  <td>
                             Responsible Officer Employee Number/Name
						  </td>
						  <td>
						  <common:employeeSearch empNumId="employeeNumber" empNum="employeeNumber" empNameId="employeeName" empName="employeeName"/>
						  </td>
					    </tr>
						<!-- Added by Ken on 31-OCT-2008 for eAppraisal enhancement -->
						<tr>
						  <td width="35%">
                             Sorting Sequence
                          </td>
						  <td width="65%">
						  <form:select path="sortSequence" id="sortSequence" >
			                    <form:options items="${sortList}" itemLabel="paramDesc" itemValue="paramValue"/>
			              </form:select>
						  </td>
					    </tr>		
						<!-- End of 31-OCT-2008  eAppraisal enhancement -->
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
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td align="right">
				<input type="submit" name="btnSearch" value="Search"></input>
				<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href =window.location.href=getJunction('/ea/')+'${ctx}/assess/ListOutstandingReport.do'"></input>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>
      
</form:form>
</body>
</html>
