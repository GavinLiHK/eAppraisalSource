<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="arRolesInfo" tagdir="/WEB-INF/tags/assess/"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	$( document ).ready(function(e) {
			msgDialog();
			cancelDialog();
		    var numberOfErrors = $('#numberOfErrors').val();
		    var saved = $('#saved').val();
		    var savedType = $('#savedType').val();
		    if(saved != null && saved == 'Y'){
		    	//alert('<spring:message code="'+savedTypes+'"/>');
		    	if('info.if0001'==savedType){
		    		$("#msg").html("Saved successfully.");	
		    	}
		    	if('info.if0011'==savedType){
		    		$("#msg").html("Report has been sent to Appraising Officer.");
		    	}	
		    	if('info.if0015'==savedType){
		    		$("#msg").html("Officer has been assigned successfully.");
		    	}
				$("#msgDialog").dialog("open");
		    }else{
		    	if(numberOfErrors != null && numberOfErrors != ''){
		    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text="Are you sure to delete the system parameter?"/>');
		    	}
		    }
	});
	$(document).ready(function(){		// prevent dual submission 20231016
		window.history.replaceState("","",window.location.href)
	});
    $(document).ready(function(){		// prevent dual submission 20231016
     $("#pageForm").submit(function() {
            $(this).submit(function() {
                return false;
            });
            return true;
        }); 
    }); 
	
	function searchMessage(){
		$("#smDialogs").dialog("open");

	}
	function back(){
		location.href =getJunction('/ea/')+"${ctx}/assess/backFromAssignOfficer.do";
	}
	function cancel() {
		$("#cmsg").html("Press confirm to save or cancel to quite without saving.");
		$("#calDialog").dialog("open");
		//window.opener=this;
		//back();
		//window.location='/ea/logout.do?logout=1';
	}
	function msgDialog(){
			$("#msgDialog").dialog({
				autoOpen: false,
				height: 150,
			      width: 350,
			      modal: true,
			      buttons: {
			          "OK": function() {
			       		$( this ).dialog( "close" );
			       		//location.href =getJunction('/ea/')+"${ctx}/assess/ListOutstandingReport.do";
			       		location.href =getJunction('/ea/')+"${ctx}/assess/backFromAssignOfficer.do";
			          }
			}});
		
	}
	function cancelDialog(){
		$("#calDialog").dialog({
			autoOpen: false,
			height: 150,
		      width: 350,
		      modal: true,
		      buttons: {
		          "OK": function() {
		       		$( this ).dialog( "close" );
		       		saveAction();
		          },
		          "Cancel": function() {
			       		$( this ).dialog( "close" );
			       		back();
			          }
		}});
	}
	function saveAction(){
	 $("#pageForm").attr("action",getJunction('/ea/')+"${ctx}/assess/addOrModifyAssignOfficer.do");	
        $("#pageForm").submit(); 
	}
	$(function(){
		console.log(${isAssigned});
	});
	
	
	$(document).keypress(function(event) {
	    if (event.key == "Enter") {
	        return false;
	    }
	});
	
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
<form:form id="pageForm" modelAttribute="assignOfficerDto" action="${ctx}/assess/addOrModifyAssignOfficer.do" method="post">
<common:searchMsgDialog id="smDialogs" parentId="notificationCD" />
<form:input	type="hidden" id="commenceDate" path="commenceDate"/>
<form:input type="hidden" id="endDate" path="endDate"/>
<form:input type="hidden" id="reportTemplate" path="reportTemplate"/>
<input type="hidden" id="isRemaining" value="${isRemaining}" />
<input type="hidden" id="isAssigned" value="${isAssigned}" />
<input type="hidden" id="numberOfErrors" value="${numberOfErrors}" />
<input type="hidden" name="saved" id="saved" value="${saved}" />
<input type="hidden" name="savedType" id="savedType" value="${savedType}" />
<form:input type="hidden" id="firstReminder" path="reportRoleCD.firstReminder"/>
<form:input type="hidden" id="secondReminder" path="reportRoleCD.secondReminder"/>
<form:input type="hidden" id="isRemaining" path="reportRoleCD.thirdReminder" />
<form:input type="hidden" id="numberOfErrors" path="reportRoleCD.subsReminder" />
<form:input type="hidden" id="aoroleChecked" path="reportRoleAO.roleChecked" />
<form:input type="hidden" id="cdroleChecked" path="reportRoleCD.roleChecked" />
<form:input type="hidden" id="coroleChecked" path="reportRoleCO.roleChecked" />
<form:input type="hidden" id="ioroleChecked" path="reportRoleIO.roleChecked" />
<form:input type="hidden" id="eoroleChecked" path="reportRoleEO.roleChecked" />
<form:input type="hidden" id="roroleChecked" path="reportRoleRO.roleChecked" />
<form:input type="hidden" id="aooldEmployeeNum" path="reportRoleAO.oldEmployeeNum" />
<form:input type="hidden" id="cooldEmployeeNum" path="reportRoleCO.oldEmployeeNum" />
<form:input type="hidden" id="iooldEmployeeNum" path="reportRoleIO.oldEmployeeNum" />
<form:input type="hidden" id="eooldEmployeeNum" path="reportRoleEO.oldEmployeeNum" />
<form:input type="hidden" id="rooldEmployeeNum" path="reportRoleRO.oldEmployeeNum" />
<form:input type="hidden" id="roleSequence" path="reportRoleCD.roleSequence" />
  
<div id="msgDialog" title="HA-EA">
	<p><span id="msg"></span></p>
</div>
<div id="calDialog" title="HA-EA">
	<p><span id="cmsg"></span></p>
</div>
<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr height="1"><td><br></td></tr>
        <tr>
          <td valign="middle">
            <table width="100%" border="0" cellpadding="5" cellspacing="0">
              <tr>
                <td align="right">
                  <input type="button" name="btnBack" value="Back" onclick="back()"/>
                  <c:choose>
                  	<c:when test="${isRemaining and isAssigned}">
                  		<input type="submit" name="btnSave" value="Send" id="btnSaveAssign"></input>
                  	</c:when>
                  	<c:otherwise>
                  		<input type="submit" name="btnSave" value="Save" id="btnSaveAssign"></input>
                  	</c:otherwise>
                  </c:choose>
<!-- 				  <input type="button" name="btnCancel" value="Cancel" onclick="javascript:if (confirm('Press confirm to save or cancel to quite without saving.')) cancel();"/> -->
					<input type="button" name="btnCancel" value="Cancel" onclick="cancel()"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	        Performance Appraisal Report Maintenance > Assign Officer
	        </td>
	      </tr>
	      <tr>
	        <td>
	        <!--
	        #Spring5Upgrade #OpenJDK11 #Java11
	        Following part was created to use modelAttribute on 20/11/2021
	        
	        Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
	        	the Spring framework was required to upgrade from version 4 to version 5.
	        
	        The commandName tag is deprecated since Spring framework 4.3, the modelAttribute tag replaces it in Spring 5.
	        
	        Modified on 20/10/2021
	        -->
	        <common:mvcErrorTable modelAttribute="assignOfficerDto"/>
	        </td>
	      </tr>
		  <tr>
			<td align="left" width="100%" class="wpsLabelText">
			   <box name="boxNotice" linewidth="0" margin="0" width="100%">
Notes for assigning officer:<br>
<!-- 1.	You may set deadline for individual officer by clicking <%= Constants.WEB_HTML_CALENDAR_IMAGE_TAG %><br>--> 
  1.	The assignment of appraising officer is mandatory, but the assignment of other subsequent roles is optional;<br>
  2.	Please search the responsible officer by English name (For example, for staff "Mr. Chan Tai Man", please input "Chan%Tai%Man%), then press <%= Constants.WEB_HTML_SEARCH_IMAGE_TAG %>.<br>
  3.	After you've assigned the officers, press "send" button to send out the report(s) to responsible officers.
			   </box>
			</td>
		  </tr>
	    </table>
	  </td>
	</tr>
</table>
  
	  <!-- Buttons -->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">

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
			                        Batch info
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
								 <!-- #1 Batch name -->
						  
						  <tr>
                            <td width="20%">
                              Batch Name
                            </td>
                            <td>
                              <input type="text" name="batchName" value="${assignOfficerDto.batchName}" classname="inputfield" size="40" maxlength="30"/>
                              <input type="hidden" name="oldBatchName" value="${assignOfficerDto.oldBatchName}" classname="inputfield" size="40" maxlength="30"/>
                            </td>
                          </tr>
                          
						

						  <!-- #2 Overall deadline -->
						  <tr>
                            <td>
                              Overall Deadline
                            </td>
                            <td>
                            <c:choose>
			                  	<c:when test="${isRemaining}">
			                  		<common:commonDate id="deadline" name="deadline" readonly="Y" />
			                  	</c:when>
			                  	<c:otherwise>
			                  		<common:commonDate id="deadline" name="deadline"/>
			                  	</c:otherwise>
			                 </c:choose>
                              </td>
                          </tr>
                          <tr>
                            <td>
                              Appraisee Deadline
                            </td>
                            <td>
                              <c:choose>
			                  	<c:when test="${isRemaining}">
			                  		<common:commonDate id="appraiseeDeadline" name="appraiseeDeadline" readonly='Y'/>
			                  	</c:when>
			                  	<c:otherwise>
			                  		<common:commonDate id="appraiseeDeadline" name="appraiseeDeadline"/>
			                  	</c:otherwise>
			                 </c:choose>
			                 <!-- <common:commonDate id="appraiseeDeadline" name="appraiseeDeadline"/> -->
                              
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
								
								 <!-- #1 Employee number-->
										<tr>
										  <td>
										  	Officer Employee Number/Name
										  </td>
										 <td>&nbsp;</td>
										 <td colspan="2"> 
										 <common:employeeSearch empNumId="cdemployeeNum" empNum="reportRoleCD.employeeNum" empNameId="cdemployeeName" empName="reportRoleCD.employeeName" />
							 			</td>
							</tr>
								 
								 <!-- #2 Noftfication -->
						  
						 	<tr>
									 <td valign="top">
									 Notification* 
									 </td>
 									 <td valign="top">&nbsp;</td>
									 <td width="50%">
									 <form:textarea id="notificationCD" path="reportRoleCD.notification"  cols="95" rows="5" maxlength="980" size="1000" wrap="soft" />
									 </td>
									
									 <td width="35%" valign="top" align="left">
									  <input type="button" value="Search" name="selCoordinatorNotification" onclick="searchMessage();" />
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
<arRolesInfo:appReportRoleDetail title="Appraising officer" id="AO" dto="reportRoleAO" disable="${assignOfficerDto.reportRoleAO.roleChecked}"></arRolesInfo:appReportRoleDetail>

<!---------- Countersigning officer --------------->
<arRolesInfo:appReportRoleDetail title="Countersigning officer" id="CO" dto="reportRoleCO" disable="${assignOfficerDto.reportRoleCO.roleChecked}"></arRolesInfo:appReportRoleDetail>

<!---------- Interviewing officer --------------->
<arRolesInfo:appReportRoleDetail title="Interviewing officer" id="IO" dto="reportRoleIO" disable="${assignOfficerDto.reportRoleIO.roleChecked}"></arRolesInfo:appReportRoleDetail>

<!---------- Endorsing officer --------------->
<arRolesInfo:appReportRoleDetail title="Endorsing officer" id="EO" dto="reportRoleEO" disable="${assignOfficerDto.reportRoleEO.roleChecked}"></arRolesInfo:appReportRoleDetail>

<!---------- Reviewing officer --------------->
<arRolesInfo:appReportRoleDetail title="Reviewing officer" id="RO" dto="reportRoleRO" disable="${assignOfficerDto.reportRoleRO.roleChecked}"></arRolesInfo:appReportRoleDetail>

	  <!-- Buttons -->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="middle">
            <table width="100%" border="0" cellpadding="5" cellspacing="0">
              <tr>
                <td align="right">
                  <input type="button" name="btnBack" value="Back" onclick="back()"></input>
                  <c:choose>
                  	<c:when test="${isRemaining and isAssigned}">

                  		<input type="submit" name="btnSave" value="Send" />
                  	</c:when>
                  	<c:otherwise>
                  		
                  		<input type="submit" name="btnSave" value="Save" />
                  	</c:otherwise>
                  </c:choose>
				  <!-- <input type="button" name="btnCancel" value="Cancel" onclick="javascript:if (confirm('Press confirm to save or cancel to quite without saving.')) cancel();"></input> -->
				  <input type="button" name="btnCancel" value="Cancel" onclick="cancel()"/>
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
	function doDeadlineCheck(checkingType, hasAO, hasCO, hasIO, hasEO, hasRO) {

		var path = document.forms[0];
		var deadline = new Array(6);
		var title = new Array(6);

		deadline[5] = (path.htmlPageTopContainer_pageForm_overallDeadline != "" || path.htmlPageTopContainer_pageForm_overallDeadline != null)?
		path.htmlPageTopContainer_pageForm_overallDeadline.value : "";
		title[5] = "Overall Deadline";

		var errorStr = "";

		if (hasAO==true){
			deadline[0] = (path.htmlPageTopContainer_pageForm_boxOuterAO_deadlineAO != "" || path.htmlPageTopContainer_pageForm_boxOuterAO_deadlineAO != null)?
			path.htmlPageTopContainer_pageForm_boxOuterAO_deadlineAO.value : "";
			title[0] = "Appraising Officer Deadline";
			errorStr += doDeadlineDateCheck(deadline[0], deadline[5], title[0], title[5], 0, 5); 
		}
		if (hasCO==true){
			deadline[1] = (path.htmlPageTopContainer_pageForm_boxOuterCO_deadlineCO != "" || path.htmlPageTopContainer_pageForm_boxOuterCO_deadlineCO != null)?
			path.htmlPageTopContainer_pageForm_boxOuterCO_deadlineCO.value : "";
			title[1] = "Countersigning Officer Deadline";
			errorStr += doDeadlineDateCheck(deadline[1], deadline[5], title[1], title[5], 1, 5); 
		}
		if (hasIO==true){
			deadline[2] = (path.htmlPageTopContainer_pageForm_boxOuterIO_deadlineIO != "" || path.htmlPageTopContainer_pageForm_boxOuterIO_deadlineIO != null)?
			path.htmlPageTopContainer_pageForm_boxOuterIO_deadlineIO.value : "";
			title[2] = "Interviewing Officer Deadline";
			errorStr += doDeadlineDateCheck(deadline[2], deadline[5], title[2], title[5], 2, 5); 
		}
		if (hasEO==true){
			deadline[3] = (path.htmlPageTopContainer_pageForm_boxOuterEO_deadlineEO != "" || path.htmlPageTopContainer_pageForm_boxOuterEO_deadlineEO != null)?
			path.htmlPageTopContainer_pageForm_boxOuterEO_deadlineEO.value : "";
			title[3] = "Endorsing Officer Deadline";
			errorStr += doDeadlineDateCheck(deadline[3], deadline[5], title[3], title[5], 3, 5); 
		}
		if (hasRO==true){
			deadline[4] = (path.htmlPageTopContainer_pageForm_boxOuterRO_deadlineRO != "" || path.htmlPageTopContainer_pageForm_boxOuterRO_deadlineRO != null)?
			path.htmlPageTopContainer_pageForm_boxOuterRO_deadlineRO.value : "";
			title[4] = "Reviewing Officer Deadline";
			errorStr += doDeadlineDateCheck(deadline[4], deadline[5], title[4], title[5], 4, 5); 
		}


//		for (i = 0; i < 6; i++){
//			if (i!=checkingType){
//				errorStr += doDeadlineDateCheck(deadline[i], deadline[checkingType], title[i], title[checkingType], i, checkingType); 
//			}
//		}	
	
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
//		odTitle = "Overall Deadline";

		var errorStr = "";

		checkFieldValue = (checkField != "" || checkField != null)?	checkField.value : "";
		errorStr += doDeadlineDateCheck(checkFieldValue, od, title, odTitle, index, 5); 

		if (errorStr!="")
			alert(errorStr);

	}

</script>