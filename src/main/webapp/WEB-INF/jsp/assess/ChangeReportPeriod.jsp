<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<html>
<head>
<script type="text/javascript">
$( document ).ready(function(){
	initDialog();
	returnResults();	 	
});
function saveClick(){
	$("#pageForm").submit();
}
function cancelClick(){
	$("#commenceDate").val("");
 	$("#endDate").val("");
	$("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/getReportsAppraiseeList.do");
	$("#pageForm").submit();
}
function returnResults(){
	 var numberOfErrors = $('#numberOfErrors').val();
	 var saveType = $('#saveType').val();
	
	 if(numberOfErrors != null && numberOfErrors != ''){
	    alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text="Are you sure to delete the system parameter?"/>');
	 }
	 if("success"==saveType){
		 $("#msg").html("Change Appraisal Report Period Success!");
		 $("#msgDialog").dialog("open");
	 }else if("fail"==saveType){
		 $("#msg").html("Change Appraisal Report Period Fail!");
		 $("#msgDialog").dialog("open");
	 }
}
function initDialog(){
	$("#msgDialog").dialog({
		autoOpen: false,
		height: 150,
	      width: 350,
	      modal: true,
	      buttons: {
	          "OK": function() {	
	        	  if("success"==$('#saveType').val()){
	        		 $("#selectedOptions").val("");
	 	        	 $("#commenceDate").val("");
	 	        	 $("#endDate").val("");
	 	        	 $("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/getReportsAppraiseeList.do");
		        	 $("#pageForm").submit();
	        	}

	       		 $( this ).dialog( "close" );
	          }         
	}});
}
</script>
</head>
<body>
<!-- Title Header  -->
<form:form id="pageForm" action="${ctx}/assess/saveAppraisalReportPeriod.do" modelAttribute="batchEnquiryDto" method="post">
<form:hidden id="functionNum" path="functionNum"/>
<form:hidden id="selectedOptions" path="selectedOptions"/>
<form:hidden id="selectedOptionsAll" path="selectedOptionsAll"/>
<form:hidden id="batchName" path="batchName"/>
<form:hidden id="batchNameJmesa" path="batchNameJmesa"/>
<form:hidden id="name" path="name"/>
<form:hidden id="subRank" path="subRank"/>
<form:hidden id="employeeNum" path="employeeNum"/>
<form:hidden id="employeeName" path="employeeName"/>
<form:hidden id="rank" path="rank"/>
<form:hidden id="postUnit" path="postUnit"/>
<form:hidden id="postTitle" path="postTitle"/>
<form:hidden id="assigned" path="assigned"/>
<form:hidden id="status" path="status"/>
<input type="hidden" id="numberOfErrors" name="numberOfErrors" value="${numberOfErrors}"/>
<input type="hidden" id="saveType" name="saveType" value="${saveType}"/>
<div id="msgDialog" title="HA-EA">
	<p><span id="msg"></span></p>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Report Period Maintenance > Change Appraisal Report Period
	         
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
	        <common:mvcErrorTable modelAttribute="batchEnquiryDto"/>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>

<!-- First block -->
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
									Change Appraisal Report Period </td>
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
						
						<!-- #1 Year  -->
 						
 						<tr>
 						  <td width="28%" class="wpsLabelText">
                           	Appraisal Period Start *
 						  </td>
 						  <td width="72%">
                              <common:commonDate id="commenceDate" name="commenceDate" />   
 						  </td>
 						</tr>
						<!-- #2 Rank  -->
						<tr>
						  <td class="wpsLabelText">
                          	Appraisal Period End *
						  </td>
						  <td>
                             <common:commonDate id="endDate" name="endDate" />   
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
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td align="right">
				<input type="button" name="btnSave" value="Save" onclick="saveClick()"></input>
				<input type="button" name="btnCancel" value="Cancel" onclick="cancelClick()"></input>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>

</form:form>

</body>
</html>
