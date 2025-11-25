<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<html>
<head>
<script type="text/javascript">
function openReport(rptId,role){
	window.open(getJunction('/ea/')+"${ctx}/common/printReport.do?rptId="+rptId+"&role="+role);	
}
</script>
</head>
<body>
<form:form name="pageForm" modelAttribute="searchFinalReportDto" action="${ctx}/assess/searchFinalReport.do?isSearch=N" method="post">
<form:hidden id="reportTypeJmesa" path="reportTypeJmesa"/>
<form:hidden id="rankJmesa" path="rankJmesa"/>
<form:hidden id="postUnitJmesa" path="postUnitJmesa"/>
<form:hidden id="employeeNumberJmesa" path="employeeNumberJmesa"/>
<form:hidden id="employeeNameJmesa" path="employeeNameJmesa"/>
<form:hidden id="commenceStartDateJmesa" path="commenceStartDateJmesa"/>
<form:hidden id="commenceEndDateJmesa" path="commenceEndDateJmesa"/>
<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Display Final Report > Search Report - Result
	        </td>
	      </tr>
	      <tr>
	        <td> 
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>

<table border="0" width="100%" height="" cellpadding="0" cellspacing="5">
	<tr>
		<td>

  <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<div align="left"><common:jmesaScript action="${ctx}/assess/searchFinalReport.do?isSearch=N" />
					${finalReportList }
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%"  border="0">
			    <tr>
			      <td>&nbsp;</td>
			      <td align="right">
					<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href =getJunction('/ea/')+'${ctx}/assess/initSearchFinalReport.do'"></input>
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