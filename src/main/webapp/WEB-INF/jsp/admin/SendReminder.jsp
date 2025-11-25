<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>

<html>
<head>
<script type="text/javascript">
$( document ).ready(function() {
    var numberOfErrors = $('#numberOfErrorsForReminder').val();
    var saved = $('#saved').val();
    if(saved != null && saved == 'Y'){
    	alert('<spring:message code="info.if0003"/>');
    	window.location.href = getJunction('/ea/')+'${ctx}/admin/searchMonitorReport.do?fromSendReminder=Y';    	
    }else{
    	if(numberOfErrors != null && numberOfErrors != ''){
    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049"/>');
    	}
    }
    	
});
</script>
</head>
<body>
<input type="hidden" name="numberOfErrorsForReminder" id="numberOfErrorsForReminder" value="${numberOfErrorsForReminder}" />
<input type="hidden" name="saved" id="saved" value="${saved}" />
<!--
#Spring5Upgrade #OpenJDK11 #Java11
Following part was created to use modelAttribute on 20/11/2021

Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
	the Spring framework was required to upgrade from version 4 to version 5.

The commandName tag is deprecated since Spring framework 4.3, the modelAttribute tag replaces it in Spring 5.

Modified on 20/10/2021
-->
<form:form name="pageForm" modelAttribute="reportReminderDto" action="${ctx}/admin/sendManualReminder.do" method="post">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Monitor Report Progress > Send Reminder
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
	        <common:mvcErrorTable modelAttribute="reportReminderDto"/>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src="${ctx}/images/dot.gif"></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
									Reminder Recipients
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
				</tr>
				<tr height="100%">
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="100%" src="${ctx}/images/dot.gif">
					</td>
					<td>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%">

						<table width="100%" border="0" cellpadding="0">
						 <c:if test="${not empty reportReminderDto.recipientList}">
							<tr valign="top">
								<td width="25%">
						  			Recipient Employee Number/Name
						  		</td>
								<td width="75%">
								<table>
								<c:forEach items="${reportReminderDto.recipientList}" var="recipient" varStatus="status">
								<tr>
								<td>
								<%-- <common:employeeSearch empNumId="employeeNum${status.index}" empNum="recipientList[${status.index}].peopleEmployeeNum" empNameId="reportName${status.index}" empName="recipientList[${status.index}].fullName" 
									empNameIdReadOnly="true" empNameIdHidden="true" searchBtnHidden="Y"/> --%>
									<input id="employeeNum${status.index}" name="recipientList[${status.index}].peopleEmployeeNum" value="${recipient.peopleEmployeeNum}" classname="inputfield" size="15" maxlength="10" readonly="readonly"/>&nbsp;&nbsp;
									<input id="reportName${status.index}" name="recipientList[${status.index}].fullName" value="${recipient.fullName}" classname="editableinputfield" size="40" readonly="readonly"/>
								</td>
								</tr>
								</c:forEach>
								</table>
									
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							</c:if>
							<tr valign="top">
								<td td width="25%">
									Employee Numbers/Names
									<br/>
									for Other Recipients
								</td>
								<td td width="75%">
								<table>
									<c:forEach items="${reportReminderDto.otherRecipientList}" var="otherRec" varStatus="status">
									<tr>
									<td>
										<common:employeeSearch empNumId="peopleEmployeeNum${status.index}" empNum="otherRecipientList[${status.index}].peopleEmployeeNum" empNameId="fullName${status.index}" empName="otherRecipientList[${status.index}].fullName" 
										/>
									</td>
									</tr>
									</c:forEach>
								</table>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td valign="top">Message Body </td>
								<td>
									<form:textarea id="messageBody" path="messageBody"  wrap="soft" style="width:100%" rows="15" maxlength="980" classname="editableinputfield" />
								</td>
						    </tr>
						</table>

								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="100%" src="${ctx}/images/dot.gif">
					</td>
				</tr>
				<tr height="1">
					<td bgcolor="#CFD9E5" width="100%" height="1" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<br/>
<table width="100%"  border="0">
	<tr>
		<td align="right">
			<input type="submit" name="btnSend" value="Send"></input>
			<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href = getJunction('/ea/')+'${ctx}/admin/searchMonitorReport.do?fromSendReminder=Y'"></input>
		</td>
	</tr>
</table>

<script>
	function trySetAttribute(obj, idx, attrName, val) {
		var attr = null;
		if (obj != null) {
			if (idx != null) {
				var idxObj = obj[idx];
				if (idxObj != null) {
					attr = idxObj.attributes[attrName];
					if (attr != null) {
						attr.value = val;
					}
				}
			} else {
				attr = obj.attributes[attrName];
				if (attr != null) {
					attr.value = val;
				}
			}
		}
	}
<salmon:raw name="rawOnPageLoaded"/>
</script>

<script>
	<salmon:raw name="rawMaxLenCheckingScript" />
</script>

</form:form>
</body>
<script language="javascript">
function searchEmployee(inIdField, inCodeField, inDescField) {
    childWin = window.open("/ea/Jsp/common/EmployeeEnquiry.jsp?queryMode=Y&pageAction=reset", childWinName, "height=400px,width=600px,location=no,menubar=no,resizable=no,scrollbars=yes,status=yes,titlebar=no;toolbar=no");
    idField=inIdField;
    codeField=inCodeField;
    descField=inDescField;
}
</script>
</html>
