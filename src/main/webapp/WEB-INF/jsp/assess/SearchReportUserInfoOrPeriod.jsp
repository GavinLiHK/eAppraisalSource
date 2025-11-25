<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<html>
<head>
<script>
function getReportsAppraiseeList(batName){
	$("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/getReportsAppraiseeList.do");
	$("#batchNameJmesa").val(batName);
	$("#pageForm").submit();
}
</script>
</head>
<body>
<form:form id="pageForm" modelAttribute="batchEnquiryDto" action="${ctx}/assess/SearchRpUserInfoOrPeriod.do" method="post">
<form:hidden id="functionNum" path="functionNum"/>
<form:hidden id="batchNameJmesa" path="batchNameJmesa"/>
<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	      	<c:if test="${'EA016'==batchEnquiryDto.functionNum}">
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Report Period Maintenance > Search Batch
	        </td>
	        </c:if>
	        <c:if test="${'EA017'==batchEnquiryDto.functionNum}">
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Report User Information Maintenance > Search Batch
	        </td>
	        </c:if>
	      </tr>
	      <tr>
	        <td> 
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
			                        Search Batch
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
                                        <td width="28%">
			                              Batch Name
                                        </td>
                                        <td width="72%">
			                              <form:input path='batchName' id="batchName" classname="wpsLabelText" size="30" maxlength="30"/>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
			                              Appraisee Employee Number
                                        </td>
                                        <td>
			                              <form:input path="employeeNum" id="employeeNum"  classname="wpsLabelText" size="30" maxlength="30"/>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
			                              Appraisee Name
                                        </td>
                                        <td>
			                              <form:input path="name" id="name" classname="wpsLabelText" size="30" maxlength="30"/>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
			                              Rank
                                        </td>
                                        <td>
			                              <form:input path="rank" id="rank" classname="wpsLabelText" size="30" maxlength="30"/>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
			                              Post Unit
                                        </td>
                                        <td>
			                              <form:input path="postUnit" id="postUnit"  classname="wpsLabelText" size="30" maxlength="30"/>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
			                              Post Title
                                        </td>
                                        <td>
			                              <form:input path="postTitle" id="postTitle" classname="wpsLabelText" size="30" maxlength="30"/>
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
<BR>

 <!-- Buttons -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td align="right">
				<input type="submit" name="btnSearch" value="Search"></input>
				<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href =getJunction('/ea/')+'${ctx}/assess/ListOutstandingReport.do'"></input>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>
<BR>
<table border="0" width="100%" height="100%">
	<tr>
		<td>
			<div id="jlist" align="left"><common:jmesaScript action="${ctx}/assess/SearchRpUserInfoOrPeriod.do" />
					${rptUserInfoOrPeriodList}
			</div>
		</td>
	</tr>
</table>
</form:form>
</body>
</html>
