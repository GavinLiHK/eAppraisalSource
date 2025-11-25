<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<html>
<head>
<script type="text/javascript">
$( document ).ready(function() {
	multiSelectedAssignmentForJmesa("rptChk_",$("#selectedOptions").val());
	initDialog();
	returnResults();
});


function openReport(rptId,role){
	window.open(getJunction('/ea/')+"${ctx}/common/printReport.do?rptId="+rptId+"&role="+role);	
}
function btnRevertClick(){
	 $("#resultFlag").val("revert");
	 $("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/revertRptStatusMain.do");
	 $("#pageForm").submit();
}
function initDialog(){
	$("#msgDialog").dialog({
		autoOpen: false,
		height: 150,
	      width: 350,
	      modal: true,
	      buttons: {
	          "OK": function() {
	        	$("#resultFlag").val("");
	       		$( this ).dialog( "close" );
	          }
	}});
}
function returnResults(){
	var resultFlag=$("#resultFlag").val();
	if("success"==resultFlag){
		$("#msg").html("Revert Successful!");		
		$("#msgDialog").dialog("open");
	}else if("fail"==resultFlag){
		$("#msg").html("Revert Fail!");
		$("#msgDialog").dialog("open");
	}else{
		var numberOfErrors = $('#numberOfErrors').val();
		if(numberOfErrors != null && numberOfErrors != ''){
    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text="Are you sure to delete the system parameter?"/>');
    	}
	}
}

</script>
</head>
<body>
<form:form id="pageForm" modelAttribute="searchFinalReportDto" action="${ctx}/assess/searchRptStatusMain.do" method="post">
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Report Status Maintenance > Search Report - Result
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
<div id="msgDialog" title="HA-EA">
	<p><span id="msg">do you want to save?</span></p>
</div>
<form:hidden path="reportType" id="reportType"  />
<form:hidden path="rank" id="rank"  />
<form:hidden path="postUnit" id="postUnit"  />
<form:hidden path="employeeNumber" id="employeeNumber"  />
<form:hidden path="employeeName" id="employeeName"  />
<form:hidden path="commenceStartDate" id="commenceStartDate"  />
<form:hidden path="commenceEndDate" id="commenceEndDate"  />
<form:hidden path="batchName" id="batchName"  />
<form:hidden path="selectedOptions" id="selectedOptions"  />
<form:hidden path="resultFlag" id="resultFlag"/>
<table border="0" width="100%" height="" cellpadding="0" cellspacing="5">
	<tr>
		<td>

  <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<div align="left"><common:jmesaScript action="${ctx}/assess/searchRptStatusMain.do"/>
					${finalReportListRSM}
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%"  border="0">
			    <tr>
			      <td>&nbsp;</td>
			      <td align="right">
			     	<input type="button" name="btnRevert" value="Revert" onclick="btnRevertClick();" />
					<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href =getJunction('/ea/')+'${ctx}/assess/initRptStatusMain.do'"></input>
			      	
			      </td></tr>
			</table>
		</td>
	</tr>
</table>

		</td>
	</tr>
</table>

</form:form>
</body>
</html>