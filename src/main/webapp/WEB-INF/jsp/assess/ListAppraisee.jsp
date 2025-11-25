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
    if(savedType != null && savedType != ''){
    	alert('<spring:message code="'+savedTypes+'"/>');
    }else{
    	if(numberOfErrors != null && numberOfErrors != ''){
    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text="Are you sure to delete the system parameter?"/>');
    	}
    }
});
</script>
</head>
<body>
<form:form id="pageForm" action="${ctx}/assess/SearchAppraisee.do" modelAttribute="searchAppraiseeDto" method="post">
<input type="hidden" name="isSearch" id="isSearch" value="${searchAppraiseeDto.isSearch}"/>
<input type="hidden" name="selectedOptions" id="selectedOptions" value="${searchAppraiseeDto.selectedOptions}"/>
<input type="hidden" name="selectedBatchId" id="selectedBatchId" value="${searchAppraiseeDto.selectedBatchId}"/>
<input type="hidden" name="selectedRptId" id="selectedRptId" value="${searchAppraiseeDto.selectedRptId}"/>
<input type="hidden" name="selectedEmployee" id="selectedEmployee" value="${searchAppraiseeDto.selectedEmployee}"/>
<input type="hidden" name="selectedOptionsAll" id="selectedOptionsAll" value="${searchAppraiseeDto.selectedOptionsAll}"/>
<input type="hidden" name="batchNameJmesa" id="batchNameJmesa" value="${searchAppraiseeDto.batchNameJmesa}"/>
<input type="hidden" name="batchNameLink" id="batchNameLink" value="${searchAppraiseeDto.batchNameLink}"/>
<input type="hidden" name="yearJmesa" id="yearJmesa" value="${searchAppraiseeDto.yearJmesa}"/>
<input type="hidden" name="rankJmesa" id="rankJmesa" value="${searchAppraiseeDto.rankJmesa}" />
<input type="hidden" name="postUnitJmesa" id="postUnitJmesa" value="${searchAppraiseeDto.postUnitJmesa}"/>
<input type="hidden" name="employeeNumberJmesa" id="employeeNumberJmesa" value="${searchAppraiseeDto.employeeNumberJmesa}"/>
<input type="hidden" name="trackDateJmesa" id="trackDateJmesa" value="${searchAppraiseeDto.trackDateJmesa}"/>
<input type="hidden" name="reportGeneratedJmesa" id="reportGeneratedJmesa" value="${searchAppraiseeDto.reportGeneratedJmesa}"/>
<input type="hidden" name="appraisalPeriodStartJmesa" id="appraisalPeriodStartJmesa" value="${searchAppraiseeDto.appraisalPeriodStartJmesa}"/>
<input type="hidden" name="appraisalPeriodEndJmesa" id="appraisalPeriodEndJmesa" value="${searchAppraiseeDto.appraisalPeriodEndJmesa}"/>
<input type="hidden" name="mode" id="mode" value="${searchAppraiseeDto.mode}"/>
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />

<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">Performance Appraisal Report Maintenance > List Appraisee 
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
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='${ctx}/images/dot.gif'></td>
					<td width="100%" height="12" nowrap>
                          <div align="left"><common:jmesaScript action="${ctx}/assess/SearchAppraisee.do" />
								${appraiseeList}
						</div>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
				</tr>
				
					
			
			</table>
		</td>
	</tr>

   <tr align="right">
   <td>
<input type="button" name="btnBack" value="Back" onclick="back()"></input> &nbsp;
<input type="button" name="btnCheckAll" value="Select All" onclick="selectAll()"></input> &nbsp;
<input type="button" name="btnClearAll" value="Clear All" onclick="clearAll()"></input>&nbsp;   
<input type="button" name="btnAssignOfficer" value="Assign Officer" onclick="assignOfficer()"></input>&nbsp;
<input type="button" name="btnHandleByExcel" value="Handle By Excel" onclick="handleByExcel()"></input>&nbsp;
<input type="button" name="btnDispatch" value="Dispatch" onclick="dispatch()"></input>&nbsp;
<input type="button" name="btnDelete" value="Delete" onclick="deleteAppraisee()"></input>&nbsp;
<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href=getJunction('/ea/')+'${ctx}/assess/ListOutstandingReport.do'"></input>&nbsp;

   </td>
   </tr>
</table>	  
</form:form>
<script language="javascript"> 
function back(){
	 $("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/back.do");	
	 $("#pageForm").submit();
	//location.href ="${ctx}/assess/back.do"; 
	} 
	
function selectAll(){
/* 	var all=$("#selectedOptionsAll").val();
	$("#selectedOptions").val(all);
	multiSelectedAssignmentForJmesa("checkBth_",$("#selectedOptions").val()); */
	var all=$("#selectedOptionsAll").val();
	$("#selectedOptions").val(all);
	selectedOnePageAssignmentForJmesa("checkBth_",$("#selectedOptions").val(),"selectedRptId","selectedBatchId","selectedEmployee");
} 
function clearAll(){
	/* $("#selectedOptions").val("");
	multiSelectedAssignmentForJmesa("checkBth_",$("#selectedOptions").val());	 */
	 selectedOnePageAssignmentForJmesa("checkBth_","","selectedRptId","selectedBatchId","selectedEmployee");
} 

function handleByExcel(){
	
	var bat =$("#selectedRptId").val();;
  /* $("input[name='checkBth']:checkbox").each(function(){
	 
	 if ('checked' == $(this).attr("checked")) {
		 bat += $(this).attr('value')+',';
	 }
	 });  */ 
	 /* var selOp = $("#selectedOptions").val();
	 alert('11=='+selOp);
	 if(selOp != null || '' != selOp){
		 var strsArray=selOp.split(",");
		 alert('22=='+strsArray);
		 for(var i=0;i<strsArray.length;i++){
			 if(strsArray[i] != null && strsArray[i] != ''){
				 alert('33==');
				 bat += $("#"+strsArray[i]).val()+',';
			 }
		 }
	 } */
	 $("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/handleByExcel.do?selRpId="+bat);	
	 $("#pageForm").submit();
 //location.href ="${ctx}/assess/handleByExcel.do?selRpId="+bat;
	
} 

function dispatch(){
	
	var bat =$("#selectedRptId").val();
  /* $("input[name='checkBth']:checkbox").each(function(){
	  
	 if ('checked' == $(this).attr("checked")) {
		 bat += $(this).attr('value')+',';
	 }
	 });  */ 
	/*  var selOp = $("#selectedOptions").val();
	 if(selOp != null || '' != selOp){
		 var strsArray=selOp.split(",");
		 for(var i=0;i<strsArray.length;i++){
			 if(strsArray[i] != null && strsArray[i] != ''){
				 bat += $("#"+strsArray[i]).val()+',';
			 }
		 }
	 } */
 //location.href ="${ctx}/assess/dispatch.do?selRpId="+bat;
	 $("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/dispatch.do?selRpId="+bat);	
	  $("#pageForm").submit();
	
} 
function deleteAppraisee(){
	
	var bat =$("#selectedRptId").val();
	var batId = "";
	var first = "";
 /*  $("input[name='checkBth']:checkbox").each(function(){
	 if ('checked' == $(this).attr("checked")) {
		 if("" == batId && "" == first ){
			 var b = $(this).attr('value');
			 batId = document.getElementById(b).value;
			 first = "yes";
		 }
		 
		 bat += $(this).attr('value')+',';
	 }
	 });  */
	 
	 var selOp = $("#selectedOptions").val();
	 if(selOp != null || '' != selOp){
		 var strsArray=selOp.split(",");
		 var selbat = $("#selectedBatchId").val();
		 var batArr = "";
		 if(selbat != null || '' != selbat){
			 batArr=selbat.split(",");
		 }
		 for(var i=0;i<strsArray.length;i++){
			 if(strsArray[i] != null && strsArray[i] != ''){
				 if("" == batId && "" == first && (batArr[i] != null && batArr[i] != '')){
					 
					 batId = batArr[i];
					 first = "yes";
				 }
			 }
		 }
	 }
	 
	 $("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/deleteAppraisee.do?selRpId="+bat+"&selBthId="+batId);	
	  $("#pageForm").submit();
 //location.href ="${ctx}/assess/deleteAppraisee.do?selRpId="+bat+"&selBthId="+batId;

} 

function assignOfficer(){
	var af = $("#selectedEmployee").val();
	var rpt = $("#selectedRptId").val();
	var bat = $("#selectedBatchId").val();
  /* $("input[name='checkBth']:checkbox").each(function(){
	 
	 if ('checked' == $(this).attr("checked")) {
			 rpt += $(this).attr('value')+',';
			 var b = $(this).attr('value');
			 bat += document.getElementById(b).value+',';
			 af += document.getElementById(b+'emp').value+',';
	 }
	 });   */
	/* var selOp = $("#selectedOptions").val();
	 
	 if(selOp != null || '' != selOp){
		 var strsArray=selOp.split(",");
		 
		 for(var i=0;i<strsArray.length;i++){
			 if(strsArray[i] != null && strsArray[i] != ''){
				 rpt += $("#"+strsArray[i]).val()+',';
				 bat += $("#batSta"+strsArray[i]).val()+',';
				 af += $("#emp"+strsArray[i]).val()+',';
			 }
		 }
	 } */
 //location.href ="${ctx}/assess/gotoAssignOfficer.do?selAf="+af+"&selRpId="+rpt+"&selBthId="+bat;
  $("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/gotoAssignOfficer.do?selAf="+af+"&selRpId="+rpt+"&selBthId="+bat);	
  $("#pageForm").submit();
	
} 
</script> 
</body>
</html>
