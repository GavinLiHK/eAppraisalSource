<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<html>
<head>
<script language="javascript">

function closeWindows() {
	window.close();
	//window.opener.focus();
}
</script>
</head>
<!-- <base target="_self"/> -->
<body>
<form:form name="pageForm" >

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td align="right">	          
			  <table>
				<tr>
					<td nowrap>
					<a name="closeLink" href="javascript:if(confirm('Close this window?')) closeWindows();" >
					Close
					</a>
					</td>
				</tr>
			  </table>			  
	        </td>
	      </tr>
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Monitor Report Progress > List Report > Reminder Sent History
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

  <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			
			<div align="left"><common:jmesaScript action="${ctx}/admin/monitorReportDetail.do" />
				${monitorReportDetail}
			</div>
		</td>
	</tr>
</table>

  <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<div align="left"><common:jmesaScript action="${ctx}/admin/monitorReportDetail.do" />
				${monitorReportDetailMsg}
			</div>                
		</td>
	</tr>
</table>

</form:form>
</body>
</html>
