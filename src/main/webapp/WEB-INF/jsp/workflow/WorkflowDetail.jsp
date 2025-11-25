<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="wfRolesInfo" tagdir="/WEB-INF/tags/workflow/"%>

<html>
<head>
<script>
$( document ).ready(function() {	
    var numberOfErrors = $('#numberOfErrors').val();  
    var msg = $('#msg').val(); 
  	if(numberOfErrors != null && numberOfErrors != ''){
    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text="" />');
   	 }
    
   	if("Success"==msg){
   		alert('<spring:message code="info.if0001" text=""/>');
   		$('#msg').val("");
   		location.href =getJunction('/ea/')+"${ctx}/workflow/WorkflowEnquiry.do";
   	}
   	
   	$("#cancelDialog").dialog({
		autoOpen: false,
		height: 150,
	      width: 350,
	      modal: true,
	      buttons: {
	          "Yes": function() {
	        	 $("#pageForm").submit();
	       		$( this ).dialog( "close" );
	       		
	            	
	          },
	          "No":function(){
	        	  location.href =getJunction('/ea/')+"${ctx}/workflow/WorkflowEnquiry.do";
	        	  $( this ).dialog( "close" );
	          }}
	});
    	
});
function cancel(){
	$("#cancelDialog").dialog("open");
}
function searchMessage(){
	$("#smDialogs").dialog("open");

}


</script>
</head>
<body>
<form:form id="pageForm" modelAttribute="workflowDetailDTO" action="${ctx}/workflow/WorkflowDetailSave.do" method="post">
<input type="hidden" name="msg" id="msg" value="${msg}" />
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<common:searchMsgDialog id="smDialogs" parentId="notificationCD" />
<div id="cancelDialog" title="HA-EA"><p>Do you want to save changes you have made</p></div>
 <!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class ="PageHeadingFont">Workflow Template Maintenance > Search Workflow Template
	         </td>
	      </tr>
	      <tr>
	        <td font="InformationFont"> </td>
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
	    	  <common:mvcErrorTable modelAttribute="workflowDetailDTO"/>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table> 
   
  <!------------- Master Detail ----------->
  
<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='${ctx}/images/dot.gif'></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
			                      Workflow Details
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src='${ctx}/images/cal.gif'></td>
				</tr>
				 <!-- portlet body -->
				
				<tr height="100%">
					<td bgcolor="#CFD9E5" height="100%" width="1"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
					<td width="100%" valign="top">
						<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
							<tr>
								<td dir="ltr" valign="top">
				  
								 
								  <table width="100%"  border="0" cellpadding="0">
								 <!-- #1 Workflow template name -->
						  
						  <tr>
                            <td width="20%" class="wpsLabelText">
                             Workflow Template Name*	
                            </td>
                            <td class="wpsLabelText">
                              <form:input id="workflowTemplateName"  path="workflowTemplateName"  classname="inputfield" size="30" maxlength="40" />
                              
                              <form:hidden id="workflowTemplateId" path="workflowTemplateId"/>
                            </td>
                          </tr>
                          
						  <!-- #2 Report type -->
						  <tr>
                            <td class="wpsLabelText">
                             Report Template Name*
                            </td>
                            <td class="wpsLabelText">
                               <form:select path="reportTemplate" id="reportTemplate" >
			                     <form:option value="">Please select...</form:option>
			                      <form:options items="${rpTempNameList}" itemLabel="paramDesc" itemValue="paramValue"/>
			                      </form:select>	
							  <form:hidden id="reportDesc" path="reportDesc"/>
                            </td>
                          </tr>

						  <!-- #3 Overall deadline -->
						  <tr>
                            <td class="wpsLabelText">
                             Overall Deadline*
                            </td>
                            <td class="wpsLabelText">
                            
                            <common:commonDate id="overallDeadline" name="overallDeadline" />
                         
                              
                            </td>
                          </tr>
                          
                          <!-- #4 Appraisee's deadline -->
                         <!--  <tr>
                            <td class="wpsLabelText">
                              Appraisee's Deadline
                            </td>
                            <td class="wpsLabelText">
                              <common:commonDate id="appraiseeDeadline" name="appraiseeDeadline"/>                       
                            </td>
                          </tr>-->

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
<!--------------- Coordinator ------------------>

 <table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='${ctx}/images/dot.gif'></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
			                       Coordinator
								</td>
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
								 <!-- #1 Noftfication -->
						 				<tr>
						 				 <td width="10%" valign="top" class="wpsLabelText"> Notification* </td>								
									 	 <td width="25%">
										  <form:textarea id="notificationCD" path="workflowRoleInfoCD.notification"  cols="120" rows="5" maxlength="980" size="1000" wrap="soft" />
 										  <form:hidden id="roleCD" path="workflowRoleInfoCD.role" value="CD"/>
 										   <form:hidden id="roleCheckCD" path="workflowRoleInfoCD.roleCheck" value="Y"/>
									     </td>
									
										 <td width="25%" valign="top" align="left" class="wpsLabelText">
										 <input type="button" value="Search" name="selCoordinatorNotification" onclick="searchMessage();">
										 
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

<!---------- Appraising officer --------------->

<wfRolesInfo:wfRolesInfo title="Appraising officer" id="AO" dto="workflowRoleInfoAO"></wfRolesInfo:wfRolesInfo>
<!---------- Countersigning officer --------------->
<wfRolesInfo:wfRolesInfo title="Countersigning officer" id="CO" dto="workflowRoleInfoCO"></wfRolesInfo:wfRolesInfo>

<!---------- Interviewing officer --------------->
<wfRolesInfo:wfRolesInfo title="Interviewing officer" id="IO" dto="workflowRoleInfoIO"></wfRolesInfo:wfRolesInfo>

<!---------- Endorsing officer --------------->
<wfRolesInfo:wfRolesInfo title="Endorsing officer" id="EO" dto="workflowRoleInfoEO"></wfRolesInfo:wfRolesInfo>

<!---------- Reviewing officer --------------->
<wfRolesInfo:wfRolesInfo title="Reviewing officer" id="RO" dto="workflowRoleInfoRO"></wfRolesInfo:wfRolesInfo>


	  <!-- Buttons -->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="middle">
            <table width="100%" border="0" cellpadding="5" cellspacing="0">
              <tr>
                <td align="right">
                  <input type="submit" name="btnSave" value="Save" ></input>
				  <input type="button" name="btnCancel" value="Cancel" onclick="cancel();"></input>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>  
</form:form>
</body>
</html>


<script language='javascript'>

	function doDeadlineCheck(checkingType) {

		var path = document.forms[0];
		var deadline = new Array(6);
		var title = new Array(6);

		deadline[0] = (path.htmlPageTopContainer_pageForm_deadlineAO != "" || path.htmlPageTopContainer_pageForm_deadlineAO != null)?
		path.htmlPageTopContainer_pageForm_deadlineAO.value : "";
		title[0] = "Appraising Officer Deadline";

		deadline[1] = (path.htmlPageTopContainer_pageForm_deadlineCO != "" || path.htmlPageTopContainer_pageForm_deadlineCO != null)?
		path.htmlPageTopContainer_pageForm_deadlineCO.value : "";
		title[1] = "Countersigning Officer Deadline";

		deadline[2] = (path.htmlPageTopContainer_pageForm_deadlineIO != "" || path.htmlPageTopContainer_pageForm_deadlineIO != null)?
		path.htmlPageTopContainer_pageForm_deadlineIO.value : "";
		title[2] = "Interviewing Officer Deadline";

		deadline[3] = (path.htmlPageTopContainer_pageForm_deadlineEO != "" || path.htmlPageTopContainer_pageForm_deadlineEO != null)?
		path.htmlPageTopContainer_pageForm_deadlineEO.value : "";
		title[3] = "Endorsing Officer Deadline";

		deadline[4] = (path.htmlPageTopContainer_pageForm_deadlineRO != "" || path.htmlPageTopContainer_pageForm_deadlineRO != null)?
		path.htmlPageTopContainer_pageForm_deadlineRO.value : "";
		title[4] = "Reviewing Officer Deadline";

		deadline[5] = (path.htmlPageTopContainer_pageForm_overallDeadline != "" || path.htmlPageTopContainer_pageForm_overallDeadline != null)?
		path.htmlPageTopContainer_pageForm_overallDeadline.value : "";
		title[5] = "Overall Deadline";

		var errorStr = "";

		for (i = 0; i < 6; i++){
			if (i!=checkingType){
				errorStr += doDeadlineDateCheck(deadline[i], deadline[checkingType], title[i], title[checkingType], i, checkingType); 
			}
		}	

		if (errorStr!="")
			alert(errorStr);

	}

	function doToODCheck(checkField, title, index) {

		var path = document.forms[0];
		var od, odTitle;

		od = (path.htmlPageTopContainer_pageForm_overallDeadline != "" || path.htmlPageTopContainer_pageForm_overallDeadline != null)?
		path.htmlPageTopContainer_pageForm_overallDeadline.value : "";
// Modified by FW		
		odTitle = "The deadline you set has exceeded the overall deadline.";

		var errorStr = "";

		checkFieldValue = (checkField != "" || checkField != null)?	checkField.value : "";
		errorStr += doDeadlineDateCheck(checkFieldValue, od, title, odTitle, index, 5); 

		if (errorStr!="")
			alert(errorStr);

	}
</script>