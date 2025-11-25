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
    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049"/>');
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
<form:form id="pageForm" modelAttribute="monitorReportSearchDto">
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<input type="hidden" name="isSearch" id="isSearch" value="${monitorReportSearchDto.isSearch}"/>
<input type="hidden" name="rankJmesa" id="rankJmesa" value="${monitorReportSearchDto.rankJmesa}"/>
<input type="hidden" name="postUnitJmesa" id="postUnitJmesa" value="${monitorReportSearchDto.postUnitJmesa}"/>
<input type="hidden" name="employeeNumberJmesa" id="employeeNumberJmesa" value="${monitorReportSearchDto.employeeNumberJmesa}"/>
<input type="hidden" name="employeeNameJmesa" id="employeeNameJmesa" value="${monitorReportSearchDto.employeeNameJmesa}"/>
<input type="hidden" name="sortSequenceJmesa" id="sortSequenceJmesa" value="${monitorReportSearchDto.sortSequenceJmesa}"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Monitor Report Progress > List Report
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
  <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<div align="left"><common:jmesaScript action="${ctx}/admin/nextsMonitorReport.do" />
								${monitorReportList}
			</div>
		</td>
	</tr>
</table>
<table width="100%"  border="0">
    <tr>
      <td>&nbsp;</td>
      <td align="right">
		<input type="button" name="btnSelectAll" value="Select All" onclick="selectAll()"></input> &nbsp;
		<input type="button" name="btnClearAll" value="Clear All" onclick="clearAll()"></input>&nbsp;   
		<input type="button" name="btnSend" value="Send Manual Reminder" onclick="sendReminder()"></input>
		<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href = getJunction('/ea/')+'${ctx}/admin/iniMonitorReport.do'"></input>
      </td></tr>
</table>
<table border="0" width="380" cellpadding="0" cellspacing="5">
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
								<td width="15%" class="wpsPortletTitle">Role</td>
							    <td width="85%" class="wpsPortletTitle">Description</td>
						  </tr>
						  <tr class="eaListOddRow">
							  	<td>AO</td>
						    	<td>Appraising Officer</td>
					      </tr>
						  <tr class="eaListEvenRow">
								<td>AP</td>
							    <td>Appraisee</td>
					      </tr>
						  <tr class="eaListOddRow">
								<td>CO</td>
							    <td>Countersigning Officer </td>
					      </tr>							
						  <tr class="eaListEvenRow">
								<td>CD</td>
							    <td>Coordinator</td>
					      </tr>
						  <tr class="eaListOddRow">
								<td>EO</td>
							    <td>Endorsing Officer </td>
					      </tr>							
						  <tr class="eaListEvenRow">
								<td>GM</td>
							    <td>Grade Management And Appointment Section </td>
					      </tr>
						  <tr class="eaListOddRow">
								<td>IO</td>
							    <td>Interviewing Officer </td>
					      </tr>							
						  <tr class="eaListEvenRow">
								<td>RO</td>
							    <td>Reviewing Officer </td>
						  </tr>
				      </table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
				</tr>
				 <!-- portlet body -->
				
				
				 <!-- bottom border -->
				<tr height="1">
				<td bgcolor="#CFD9E5" width="100%" height="1" colspan="3">
				</tr>
			</table>
		</td>
	</tr>
</table>
 <br>
</form:form>
<script type="text/javascript">
function selectAll(){
	  $("input[name='checkBth']:checkbox").each(function(){
		  $("[name = checkBth]:checkbox").attr("checked", true);
		 });  
	} 
function clearAll(){
	  $("input[name='checkBth']:checkbox").each(function(){
		  $("[name = checkBth]:checkbox").attr("checked", false);
		 });  
	} 
function sendReminder(){
	var rpId = "";
  $("input[name='checkBth']:checkbox").each(function(){
	 
	 if ('checked' == $(this).attr("checked")) {
		 rpId += $(this).attr('value')+',';
	 }
	 });
  //location.href ="${ctx}/admin/validateReportIdsForSendRemindr.do?selRpId="+rpId;
  $("#pageForm").attr("action", getJunction('/ea/')+"${ctx}/admin/validateReportIdsForSendRemindr.do?selRpId="+rpId);	
  $("#pageForm").submit();
	
}

function searchMonitorReportDetail(reportId,status){
	//window.showModalDialog("${ctx}/admin/monitorReportDetail.do?reportId=" + reportId+"&role="+status,"window","dialogWidth:1000px;DialogHeight=600px;status:no;scroll=yes;help:no;resizable:1;toolbar=no; menubar=no;");
	window.open(getJunction('/ea/')+"${ctx}/admin/monitorReportDetail.do?reportId=" + reportId+"&role="+status,"window","width:1000px,height=600px,toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");
}
</script>
</body>
</html>
