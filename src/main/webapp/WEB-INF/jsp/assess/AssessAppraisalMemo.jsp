<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="assess" tagdir="/WEB-INF/tags/assess/"%>

<%@ taglib uri="/WEB-INF/tld/secPage.tld" prefix="secPage"%>

<html>
<head>
<script>
$(document).ready(function(){		// prevent dual submission 20230116
	window.history.replaceState("","",window.location.href)
});
$(function() {
	$('#tabContainer').tabs();
	initDialog();
	bLoadTag();
	returnResults();
});
function returnResults(){
	var saveFlag=$("#actionFlag").val();
	
	if("success"==saveFlag){
		$("#msg").html("Save Successful!");
		$("#msgDialog").dialog("open");
	}else if("fail"==saveFlag){
		$("#msg").html("Save Fail!");
		$("#msgDialog").dialog("open");
	}else if("sendSuccess"==saveFlag){
		$("#msgResultSend").html("The report has been sent to responsible officer successfully.");
		$("#sendResultDialog").dialog("open");
	}else if("sendFail"==saveFlag){
		$("#msgResultSend").html("Submit is not successful. Please note the following errors.");
		$("#sendResultDialog").dialog("open");
	}else if("report"==saveFlag){
		/*
		var rptId=$("#reportId").val();
		var role=$("#currentUserRoleStatus").val();
		window.open(getJunction('/ea/')+"${ctx}/common/printReport.do?rptId="+rptId+"&role="+role);
		*/
		$("#printRptId").val($("#reportId").val());
		$("#printRole").val($("#currentUserRoleStatus").val());
		$("#printReport").submit();
	}
}
function btnInit(currentPage){
	var pages='${assessAppraisalMemoDTO.pages}';
	var role='${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}';
	//added by pccw on 20220105 to prevent double save/send 
	var dimTabFlag='${dimTabFlag}';
	console.log("pages "+pages);
	console.log("current pages "+currentPage);
	console.log("current user role "+role);
	console.log("dimTabFlag "+dimTabFlag);
	
	 $(".btnBars").empty();
	
	//added by pccw on 20220105 to prevent double save/send 
	if(dimTabFlag=='T'){
		$( "#tabContainer" ).tabs( "option", "disabled", true );
	} else if(currentPage==0 && dimTabFlag!='T'){			
		$(".btnBars").append("<input type='button' class='btnNext' value='Next' onclick='nextClick("+(Number(currentPage)+Number(1))+");'/>");
		$(".btnBars").append("<input type='button' class='btnSave' value='Save' onclick='saveClick("+Number(currentPage)+");'/>");	
	}else if(currentPage==pages){
		//$(".btnBars").append("<input type='button' class='btnBack' value='Back' onclick='backClick("+(Number(currentPage)-Number(1)) +");'/>");	
		if(role=="AP"){
			$(".btnBars").append("<input type='button' class='btnBack' value='Back' onclick='backClick("+Number(1) +");'/>");
		}else if(role=="AO"){
			$(".btnBars").append("<input type='button' class='btnBack' value='Back' onclick='backClick("+Number(2) +");'/>");
		}else if(role=="CO"){
			$(".btnBars").append("<input type='button' class='btnBack' value='Back' onclick='backClick("+Number(3) +");'/>");
		}else{
			$(".btnBars").append("<input type='button' class='btnBack' value='Back' onclick='backClick("+(Number(currentPage)-Number(1)) +");'/>");	
		}
		$(".btnBars").append("<input type='button' class='btnSave' value='Save' onclick='saveClick("+ Number(currentPage) +");'/>");
		$(".btnBars").append("<input type='button' class='btnPrint' value='Print' onclick='printClick();'/>");
		$(".btnBars").append("<input type='button' class='btnSubmit' value='Send' onclick='sendClick();'/>");	
		
	}else{
		$(".btnBars").append("<input type='button' class='btnBack' value='Back' onclick='backClick("+ (Number(currentPage)-Number(1)) +");'/>");
		//$(".btnBars").append("<input type='button' class='btnNext' value='Next' onclick='nextClick("+ (Number(currentPage)+Number(1)) +");'/>");
		if((role=="AP"&&currentPage==1) || (role=="AO"&&currentPage==2)||(role=="CO"&&currentPage==3)){
			$(".btnBars").append("<input type='button' class='btnNext' value='Next' onclick='nextClick("+ Number(pages) +");'/>");
		}else{
			$(".btnBars").append("<input type='button' class='btnNext' value='Next' onclick='nextClick("+ (Number(currentPage)+Number(1)) +");'/>");
		}
		$(".btnBars").append("<input type='button' class='btnSave' value='Save' onclick='saveClick("+ Number(currentPage) +");'/>");
	}		
}
//java control button function 
function bLoadTag(){
	var curp=$("#currentPage").val();
	$("#tabContainer").tabs( "option", "active", curp );
	btnInit(curp);
}
function nextClick(page){	
	$("#actionFlag").val("");
	$("#currentPage").val(page);
	$("#tabContainer").tabs( "option", "active", page );
	btnInit($("#currentPage").val());
		
}
function backClick(page){
	$("#actionFlag").val("");
	$("#currentPage").val(page);
	$("#tabContainer").tabs( "option", "active", page );
	btnInit($("#currentPage").val());
	
}
function saveClick(page){
	
	$("#msg").html("Save record only. It will not send the appraisal to next level!");
	$("#msgDialog").dialog("open");
	
	$("#actionFlag").val("save");
	$("#currentPage").val(page);
	
}

function changeAttributeBeforeSubmit(){
	//set radio button not dim 
	if("N"==$("#bonusDimmedP3").val()){	
		$(".perfBonusP3").attr("disabled",false);
	}
	if("N"==$("#salaryDimmedP3").val()){
		$(".salaryAdjP3").attr("disabled",false);
	}
	if("N"==$("#bonusDimmedP4").val()){
		$(".perfBonusP4").attr("disabled",false);
	}
	if("N"==$("#salaryDimmedP4").val()){
		$(".salaryAdjP4").attr("disabled",false);
	}
  //added by pccw on 20220105 to prevent double save/send 
  $(".btnSave").attr("disabled",true); 
  $(".btnBack").attr("disabled",true); 
	  $( "#tabContainer" ).tabs( "option", "disabled", true );
	  $(".btnPrint").attr("disabled",true);
  $(".btnSubmit").attr("disabled",true);
  //end edit by pccw         		    
}

function printClick(page){	
/*	
	var rptId=$("#reportId").val();
	var role=$("#currentUserRoleStatus").val();
	window.open(getJunction('/ea/')+"${ctx}/common/printReport.do?rptId="+rptId+"&role="+role);
*/
	$("#actionFlag").val("save_report");
	$("#currentPage").val(page);
	changeAttributeBeforeSubmit();	
	$("#pageForm").submit();
}
function pageTabClick(){
	var active = $("#tabContainer").tabs( "option", "active" );
	$("#currentPage").val(active);
	btnInit($("#currentPage").val());
	
}
function initDialog(){
	$("#msgDialog").dialog({
		autoOpen: false,
		height: 150,
	      width: 350,
	      modal: true,
	      buttons: {
	          "OK": function() {
	        	  if("save"==$("#actionFlag").val()){
	        		  changeAttributeBeforeSubmit();
	        		  $("#pageForm").submit();
	        	  }
	        	$("#actionFlag").val("");
	       		$( this ).dialog( "close" );
	          }
	}});
	
	$("#sendDialog").dialog({
		autoOpen: false,
		height: 150,
	      width: 350,
	      modal: true,
	      buttons: {
	          "Confirm": function() {
	        	  $("#actionFlag").val("send");
	        	  changeAttributeBeforeSubmit();
	        	  $("#pageForm").submit();
	       		  $( this ).dialog( "close" );
	          },
	          "Cancel":function(){
	        	  $( this ).dialog( "close" );
	        	  $("#actionFlag").val("");
	          }
	}});
	$("#btlDialog").dialog({
		autoOpen: false,
		height: 150,
	      width: 350,
	      modal: true,
	      buttons: {
	          "OK": function() {
	        	  $("#actionFlag").val("backToList");
	        	  $("#pageForm").submit();
	       		  $( this ).dialog( "close" );
	          },
	          "Cancel":function(){
	        	  $( this ).dialog( "close" );
	        	  $("#actionFlag").val("");
	          }
	}});
	$("#sendResultDialog").dialog({
		autoOpen: false,
		height: 150,
	      width: 350,
	      modal: true,
	      buttons: {
	          "OK": function() {
	        	  if("sendSuccess"==$("#actionFlag").val()){
	        		$("#actionFlag").val("backToList");
		        	$("#pageForm").submit();
	        	  }else{
	        	 	$("#actionFlag").val("");
	        	  	btnInit($("#currentPage").val());	
	        	  }
	       		$( this ).dialog( "close" );
	          }         
	}});
}
function sendClick(page){
	
	var sendMsg = "Have you printed a copy before submitting?  As the report cannot be accessed anymore after sending, you may press "+"'Cancel'"+" to stay on the same page or press "+"'Confirm'"+" to proceed with the send function.";
	<!-- $("#msgSend").html("Have you printed a copy before submitting?  As the report cannot be accessed anymore after sending, you may press \\'Cancel\\' to stay on the same page or press \\'Confirm\\' to proceed with the send function."); -->
	$("#msgSend").html(sendMsg);
	$("#sendDialog").dialog({'height': 'auto'});
	$("#sendDialog").dialog("open");
	
	$("#currentPage").val(page);
}
function backToListBtnClick(){
	$("#msgBtl").html("Back to list without saving/sending?");
	$("#btlDialog").dialog("open");	

}

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
<form:form id="printReport" action="${ctx}/common/printReport.do"  method="get" target="_blank">
	<input type="hidden" id="printRptId" name="rptId"  />
	<input type="hidden" id="printRole" name="role"  />
</form:form>
<form:form id="pageForm" action="${ctx}/assess/AssessAppraisalMemoAction.do"  modelAttribute="assessAppraisalMemoDTO" method="post">
<table width="100%" cellpadding="5">
	<tr>
		<td align="right" class="btnBars">
		
		</td>
	</tr>
</table>
<!--<secPage:buttonBar role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" currentPage="${assessAppraisalMemoDTO.currentPage}" reportType="M"/>  --> 

 
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="middle">
			<table width="100%" border="0" cellpadding="5" cellspacing="0">
				<tr>
					<td>
						<!--<salmon:text name="information" text="" visible="false" font="InformationFont"/>
						<salmon:validator name="errorMessage" visible="false" breaksafter="2"  allowmultipleerrors="true" focuslinks="true" rulejs="false"></salmon:validator>
						-->
					<!--
					#Spring5Upgrade #OpenJDK11 #Java11
					Following part was created to use modelAttribute on 20/11/2021
	        
					Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
						the Spring framework was required to upgrade from version 4 to version 5.
	        
					The commandName tag is deprecated since Spring framework 4.3, the modelAttribute tag replaces it in Spring 5.
	        
					Modified on 20/10/2021
					-->
					<common:mvcErrorTable modelAttribute="assessAppraisalMemoDTO"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<div id="msgDialog" title="HA-EA">
	<p><span id="msg"></span></p>
</div>
<div id="sendDialog" title="HA-EA">
	<p><span id="msgSend"></span></p>
</div>
<div id="btlDialog" title="HA-EA">
	<p><span id="msgBtl"></span></p>
</div>
<div id="sendResultDialog" title="HA-EA">
	<p><span id="msgResultSend"></span></p>
</div>
<!--  Memo Info -->
<assess:memoInfo dto="memoInfo"/>
<br/>
<div id="tabContainer">	
   <ul>
    <secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="P1" reportType="M" roleChecked="P1RoleChecked">
     <li id="memoPart1" onclick="pageTabClick();"><a href="#memoP1" >Part I</a></li>
	</secPage:dtreeMenu>
	
 	<secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="P2" reportType="M" roleChecked="P2RoleChecked">
     <li id="memoPart2" onclick="pageTabClick();"><a href="#memoP2" >Part II</a></li>
	 </secPage:dtreeMenu>  
	 
 	<secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="P3" reportType="M" roleChecked="P3RoleChecked">     
    <!-- <li id="memoPart3" onclick="pageTabClick();"><a href="#memoP3" >Part III (AO)</a></li> -->    
     
     <c:choose>
    	<c:when test="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus!='AP'}">
			<li id="memoPart3" onclick="pageTabClick();"><a href="#memoP3" >Part III (AO)</a></li>
   		</c:when>    
    	<c:otherwise>
        	<li id="memoPart3" class="inactiveLink" ><a href="#memoP3"><FONT color="grey">Part III (AO)</FONT></a></li>
    	</c:otherwise>
	 </c:choose>
     
      
  	</secPage:dtreeMenu>
  	
  	<secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="P4" reportType="M" roleChecked="P4RoleChecked">    
  	  <!-- <li id="memoPart4" onclick="pageTabClick();"><a href="#memoP4" >Part IV (CO)</a></li> -->
  	  
  	  <c:choose>
    	<c:when test="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus =='CO' or assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus =='IO' or
    			assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus =='SU' or assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus =='GM'}">
			<li id="memoPart4" onclick="pageTabClick();"><a href="#memoP4" >Part IV (CO)</a></li>
   		</c:when>    
    	<c:otherwise>
        	<li id="memoPart4" class="inactiveLink" ><a href="#memoP4"><FONT color="grey">Part IV (CO)</FONT></a></li>
    	</c:otherwise>
	 </c:choose>
  	  
	</secPage:dtreeMenu>
	
	<secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="P5" reportType="M" roleChecked="P5RoleChecked">     
   	 <!-- <li id="memoPart5" onclick="pageTabClick();"><a href="#memoP5" >Part V (IO)</a></li>  -->
   	 
   	 <c:choose>
    	<c:when test="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus =='IO' or
    			assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus =='SU' or assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus =='GM'}">
			<li id="memoPart5" onclick="pageTabClick();"><a href="#memoP5" >Part V (IO)</a></li> 
   		</c:when>    
    	<c:otherwise>
        	<li id="memoPart5" class="inactiveLink" ><a href="#memoP5"><FONT color="grey">Part V (IO)</FONT></a></li>
    	</c:otherwise>
	 </c:choose>
   	 
	</secPage:dtreeMenu>
	
 	<secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="Ps" reportType="M" roleChecked="PsRoleChecked">     
   		<li id="memoPrintSubmit"  onclick="pageTabClick();"><a href="#memoPs" >Print Submit</a></li>
	</secPage:dtreeMenu>  
	   
   </ul>  
   
  <secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="P1" reportType="M" roleChecked="P1RoleChecked">
   	<div id="memoP1">
    	<!-- PartI-->
		<assess:memoPart1Info dto="memoPart1Info"/>
 	</div>
  </secPage:dtreeMenu>
  
   <secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="P2" reportType="M" roleChecked="P2RoleChecked">
  	<div id="memoP2">
  		 <!-- Part II Info -->
  		 <assess:memoPart2Info dto="memoPart2Info" />
 	 </div>	
   </secPage:dtreeMenu>
   
   <secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="P3" reportType="M" roleChecked="P3RoleChecked">
  	<div id="memoP3">	
  	 	<!-- Part III Info -->
  		<assess:memoPart3Info dto="memoPart3Info" />
  		<assess:signatureInfo dto="signatureInfoMemoP3" officerName="Name of Appraising Officer" signatureFlag='MEMO'/>
  	</div>
  </secPage:dtreeMenu>
  
  <secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="P4" reportType="M" roleChecked="P4RoleChecked"> 
   	<div id="memoP4">
    	<!-- Part IV Info -->
  		<assess:memoPart4Info dto="memoPart4Info" />
  		<assess:signatureInfo dto="signatureInfoMemoP4" officerName="Name of Countersigning Officer" signatureFlag='MEMO'/>
 	</div>
 </secPage:dtreeMenu>
 
  <secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="P5" reportType="M" roleChecked="P5RoleChecked"> 
    <div id="memoP5">
    	 <!-- Part V Info -->
   	 	<assess:memoPart5Info dto="memoPart5Info" />
  		<assess:signatureInfo dto="signatureInfoMemoP5" officerName="Name of Interviewing Officer" signatureFlag='MEMO'/>
  	</div>
  </secPage:dtreeMenu>
  
   <secPage:dtreeMenu role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" pageId="Ps" reportType="M" roleChecked="PsRoleChecked"> 
    <div id="memoPs">
    	 <!-- Print Submit Info -->
     <assess:memoPrintSubmit dto="${assessAppraisalMemoDTO}" />
  	</div>
   </secPage:dtreeMenu>
   
</div>
<br/>
<table width="100%" cellpadding="5">
	<tr>
		<td align="right" class="btnBars">
		
		</td>
	</tr>
</table>
<!-- <secPage:buttonBar role="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus}" currentPage="${assessAppraisalMemoDTO.currentPage}" reportType="M"/> --> 

<form:hidden path="reportUserRole.currentReportStatus" id="currentReportStatus" />
<form:hidden path="reportUserRole.currentOfficerId" id="currentOfficerId" />
<form:hidden path="reportUserRole.gmId" id="gmId" />
<form:hidden path="reportUserRole.currentUserRoleStatus" id="currentUserRoleStatus" />
<form:hidden path="reportUserRole.nextOfficerId" id="nextOfficerIdR" />
<form:hidden path="reportUserRole.nextOfficerName" id="nextOfficerNameR" />
<form:hidden path="reportUserRole.nextOfficerDeadline" id="nextOfficerDeadlineR" />
<form:hidden path="reportUserRole.nextOfficerRole" id="nextOfficerRoleR" />

<form:hidden path="reportId" id="reportId" />
<form:hidden path="currentPage" id="currentPage" />
<form:hidden path="actionFlag" id="actionFlag" />
<form:hidden path="backToListType" id="backToListType" />
<form:hidden path="pages" id="pages" />
<form:hidden path="reportType" id="reportType" />
</form:form>

</body>
</html>
