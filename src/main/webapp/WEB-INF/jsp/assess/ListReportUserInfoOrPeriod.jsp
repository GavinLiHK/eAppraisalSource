<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<html>
<head>
<script type="text/javascript">
$( document ).ready(function() {
	multiSelectedAssignmentForJmesa("checkBth_",$("#selectedOptions").val());
	returnResults();	   
});
function backBtn(){
	$('#pageForm').attr('action', getJunction('/ea/')+'${ctx}/assess/SearchRpUserInfoOrPeriod.do');
	$("#selectedOptions").val("");
	$('#pageForm').submit();
}
function returnResults(){
	 var numberOfErrors = $('#numberOfErrors').val();
	 var savedType = $('#savedType').val();
	 if(savedType != null){
	    alert('<spring:message code="'+savedTypes+'"/>');
	 }else{
	    if(numberOfErrors != null && numberOfErrors != ''){
	    	alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text="Are you sure to delete the system parameter?"/>');
	    }
	 }	
}
function selectAllClick(){
	 var all=$("#selectedOptionsAll").val();	
	 $("#selectedOptions").val(all);
	 multiSelectedAssignmentForJmesa("checkBth_",$("#selectedOptions").val());
} 
function clearAllClick(){
	 $("#selectedOptions").val("");
	 multiSelectedAssignmentForJmesa("checkBth_",$("#selectedOptions").val());	
} 
function assignOfficerClick(){
	$("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/rptUserInfoMainAssginAction.do");	
	$("#pageForm").submit();	
} 
function cancelClick(){
	location.href =getJunction('/ea/')+"${ctx}/assess/ListOutstandingReport.do";
}
function changeReportPeriod(){
	$("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/changeReportPeriods.do");	
	$("#pageForm").submit();	
} 
</script>
</head>
<body>
<form:form id="pageForm" modelAttribute="batchEnquiryDto" action="${ctx}/assess/getReportsAppraiseeList.do" >
<input type="hidden" name="functionNum" id="functionNum" value="${batchEnquiryDto.functionNum}"/>
<input type="hidden" name="selectedOptions" id="selectedOptions" value="${batchEnquiryDto.selectedOptions}"/>
<input type="hidden" name="selectedOptionsAll" id="selectedOptionsAll" value="${batchEnquiryDto.selectedOptionsAll}"/>
<form:hidden id="batchName" path="batchName"/>
<input type="hidden" name="batchNameJmesa"  id="batchNameJmesa" value="${batchEnquiryDto.batchNameJmesa}"/>
<form:hidden id="name" path="name"/>
<form:hidden id="subRank" path="subRank"/>
<form:hidden id="employeeNum" path="employeeNum"/>
<form:hidden id="employeeName" path="employeeName"/>
<form:hidden id="rank" path="rank"/>
<form:hidden id="postUnit" path="postUnit"/>
<form:hidden id="postTitle" path="postTitle"/>
<form:hidden id="assigned" path="assigned"/>
<form:hidden id="status" path="status"/>
<form:hidden id="commenceDate" path="commenceDate"/>
<form:hidden id="endDate" path="endDate"/>

<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	      <c:if test="${'EA016'==batchEnquiryDto.functionNum}">
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Report Period Maintenance > List Appraisee
	        </td>
	        </c:if>
	        <c:if test="${'EA017'==batchEnquiryDto.functionNum}">
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Report User Information Maintenance > List Appraisee
	        </td>
	        </c:if>
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
	        	<common:mvcErrorTable modelAttribute="batchEnquiryDto"/>
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
				
				 <!-- portlet body -->
				
					<tr height="100%">
						<td width="100%" valign="top">
							<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
								<tr>
									<td dir="ltr" valign="top">
					  <table width="100%"  border="0" cellpadding="0">
						<tr align="center">
						  <td width="100%" align="left" class="wpsLabelText"><strong><P>Please assign officers to the following appraisee(s)</P></strong></td>
					    </tr>
						<tr align="center">
							<td align="left" width="100%" class="wpsLabelText">To assign an appraising officer to an appraisee, please<br>
							1).	Select the appraisee<br>
							<c:if test="${'EA016'==batchEnquiryDto.functionNum}">
							2).	Press "Change Report Period" button<br>
							
							</c:if>
							<c:if test="${'EA017'==batchEnquiryDto.functionNum}">
							2).	Press "assign" button<br>
							Note: For those appraisees who have the same appraising officer, you may assign them in one time.
							</c:if>
							
							</td>
						</tr>
					  </table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				
				 <!-- bottom border -->
			</table>
		</td>
	</tr>
</table>  
<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<div align="left"><common:jmesaScript action="${ctx}/assess/getReportsAppraiseeList.do" />
					${reportAppraiseeList}
			</div>
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
				<input type="button" name="btnBack" value="Back" onclick="backBtn()"/>
				<input type="button" name="btnSelectAll" value="Select All" onclick="selectAllClick()"/>
				<input type="button" name="btnClearAll" value="Clear All" onclick="clearAllClick()"/>
				 <c:if test="${'EA017'==batchEnquiryDto.functionNum}">
				<input type="button" name="btnAssign" value="Assign" onclick="assignOfficerClick()"/>
				</c:if>
				 <c:if test="${'EA016'==batchEnquiryDto.functionNum}">
				<input type="button" name="btnChangeReportPeriod" value="Change Report Period" onclick="changeReportPeriod()"/>
				</c:if>
				<input type="button" name="btnCancel" value="Cancel" onclick="cancelClick()"/>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>
</form:form>
</body>
</html>
