<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
$( document ).ready(function() {
	multiSelectedAssignmentForJmesa("checkBth_",$("#selectedOptions").val());
	var numberOfErrors = $('#numberOfErrors').val();
    var savedType = $('#savedType').val();
   // alert('savedType=='+savedType+'==numberOfErrors=='+numberOfErrors);
    if(savedType != null && savedType != ''){
    	alert('<spring:message code="'+savedTypes+'"/>');
    }else{
    	if(numberOfErrors != null && numberOfErrors != ''){
    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text=""/>');
    	}
    }
});
function getReportsAppraiseeList(batName,fromPage){
	$("#batchNameLink").val(batName);
	$("#mode").val(fromPage);
	$("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/SearchAppraisee.do");
	$("#pageForm").submit();
}
</script>
</head>
<body>
<form:form id="pageForm" modelAttribute="searchAppraiseeDto" action="${ctx}/assess/searchBatch.do" method="post">
<%-- <input type="hidden" name="savedType" id="savedType" value="${savedType}" /> --%>
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<form:hidden id="batchNameJmesa" path="batchNameJmesa"/>
<form:hidden id="batchNameLink" path="batchNameLink"/>
<form:hidden id="rankJmesa" path="rankJmesa"/>
<form:hidden id="postUnitJmesa" path="postUnitJmesa"/>
<form:hidden id="postTitleJmesa" path="postTitleJmesa"/>
<form:hidden id="selectedOptions" path="selectedOptions"/>
<form:hidden id="selectedBatchId" path="selectedBatchId"/>
<form:hidden id="selectedOptionsAll" path="selectedOptionsAll"/>
<form:hidden id="mode" path="mode"/>
<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Performance Appraisal Report Maintenance > List Batch
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
	        <common:mvcErrorTable modelAttribute="searchAppraiseeDto"/>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>



 <!----------- Search Result --------->
	  

<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<div align="left"><common:jmesaScript action="${ctx}/assess/searchBatch.do" />
					${batchList}
			</div>
		</td>
	</tr>

   <tr align="right">
   <td>
		<input name="btnBack" type="button" value="Back" onClick="window.location.href = getJunction('/ea/')+'${ctx}/assess/initBatch.do'">   
		<input type="button" name="btnCheckAll" value="Select All" onclick="selectAll()"></input> &nbsp;
		<input type="button" name="btnClearAll" value="Clear All" onclick="clearAll()"></input>&nbsp;   
		<input type="button" name="btnDispatch" value="Dispatch" onclick="dispatchBatch()"></input>&nbsp;
		<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href = getJunction('/ea/')+'${ctx}/assess/ListOutstandingReport.do'"></input>&nbsp;

   </td>
   </tr>
</table>	  

</form:form>
<script type="text/javascript">
function selectAll(){
	  /* $("input[name='checkBth']:checkbox").each(function(){
		  $("[name = checkBth]:checkbox").attr("checked", true);
		 });  */ 
	var all=$("#selectedOptionsAll").val();	
	$("#selectedOptions").val(all);
	/* alert($("#selectedOptions").val());
	alert($("#selectedBatchId").val());
	//multiSelectedAssignmentForJmesa("checkBth_",$("#selectedOptions").val());
	alert('end=='+$("#selectedBatchId").val()); */
	selectedOnePageAssignmentForJmesa("checkBth_",$("#selectedOptions").val(),"selectedBatchId",null,null);
	
} 
function clearAll(){
	  /* $("input[name='checkBth']:checkbox").each(function(){
		  $("[name = checkBth]:checkbox").attr("checked", false);
		 }); */ 
	//$("#selectedOptions").val("");
	//multiSelectedAssignmentForJmesa("checkBth_",$("#selectedOptions").val());
	selectedOnePageAssignmentForJmesa("checkBth_","","selectedBatchId",null,null);
} 
function dispatchBatch() {
		
		var bat =$("#selectedBatchId").val();
	  /* $("input[name='checkBth']:checkbox").each(function(){
		 if ('checked' == $(this).attr("checked")) {
			 bat += $(this).attr('value')+',';
		 }
		 });  */ 
		/* var selOp = $("#selectedOptions").val();
		 if(selOp != null || '' != selOp){
			 var strsArray=selOp.split(",");
			 for(var i=0;i<strsArray.length;i++){
				 if(strsArray[i] != null && strsArray[i] != ''){
					 bat += $("#"+strsArray[i]).val()+',';
				 }
			 }
		 } */
		 $("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/dispatchBatch.do?selbatId="+bat);	
		 $("#pageForm").submit();
	 //location.href ="${ctx}/assess/dispatchBatch.do?selbatId="+bat;	
}
</script>
</body>
</html>
