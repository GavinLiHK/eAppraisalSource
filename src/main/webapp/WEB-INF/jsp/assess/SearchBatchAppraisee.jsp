<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<html>
<head>
<script>
function getReportsAppraiseeList(batName){
	$("#batchNameJmesa").val(batName);
	$("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/getBatchAppraiseeList.do");
	$("#pageForm").submit();
}

function cancel(){
	location.href =getJunction('/ea/')+"${ctx}/assess/ListOutstandingReport.do";
}
</script>
</head>
<body>
<form:form id="pageForm" modelAttribute="batchEnquiryDto" action="${ctx}/assess/SearchBatchAppraisee.do" method="post">
<form:hidden id="functionNum" path="functionNum"/>
<form:hidden id="batchNameJmesa" path="batchNameJmesa"/>
<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Assign Remaining Officer > Search Batch
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
 <table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='../images/dot.gif'></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
			                        Search Batch
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src='../images/dot.gif'></td>
				</tr>
				 <!-- portlet body -->
				
					<tr height="100%">
						<td bgcolor="#CFD9E5" height="100%" width="1"><img alt="" border="0" width="1" height="1" src='../images/dot.gif'></td>
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
			                              <input type="text" name="batchName" value="${batchEnquiryDto.batchName}" classname="wpsLabelText" size="30" maxlength="30"></input>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
			                              Appraisee Employee Number
                                        </td>
                                        <td>
			                              <input type="text" name="employeeNum" value="${batchEnquiryDto.employeeNum}" classname="wpsLabelText" size="30" maxlength="30"></input>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
			                              Appraisee Name
                                        </td>
                                        <td>
			                              <input type="text" name="name" value="${batchEnquiryDto.name}" classname="wpsLabelText" size="30" maxlength="30"></input>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
			                              Rank
                                        </td>
                                        <td>
			                              <input type="text" name="rank" value="${batchEnquiryDto.rank}" classname="wpsLabelText" size="30" maxlength="30"></input>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
			                              Post Unit
                                        </td>
                                        <td>
			                              <input type="text" name="postUnit" value="${batchEnquiryDto.postUnit}" classname="wpsLabelText" size="30" maxlength="30"></input>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td>
			                              Post Title
                                        </td>
                                        <td>
			                              <input type="text" name="postTitle" value="${batchEnquiryDto.postTitle}" classname="wpsLabelText" size="30" maxlength="30"></input>
                                        </td>
                                      </tr>
                                    </table>
                                    </td>
								</tr>
							</table>
						</td>
						<td bgcolor="#CFD9E5" width="1"><img alt="" border="0" width="1" height="1" src='../images/dot.gif'></td>
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
<table border="0" width="100%" height="100%">
	<tr>
		<td>
			<div align="left"><common:jmesaScript action="${ctx}/assess/SearchBatchAppraisee.do" />
					${searchBatchAppraiseeList}
			</div>
		</td>
	</tr>
</table>
  <!-- Buttons -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td align="right">
				<input type="submit" name="btnSearch" value="Search"></input>
				<input type="button" name="btnCancel" value="Cancel" onclick="cancel();"></input>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>
<BR>
</form:form>
</body>
</html>
