<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="com.hkha.ea.common.Constants" %>
<%@ attribute name="title" type="java.lang.String" required="true"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="dto" type="java.lang.String" required="true"%>
<%@ attribute name="hiddenDeadline" type="java.lang.String" required="false"%>
<%@ attribute name="hiddenFirstReminder" type="java.lang.String" required="false"%>
<%@ attribute name="hiddenSecondReminder" type="java.lang.String" required="false"%>
<%@ attribute name="hiddenThirdReminder" type="java.lang.String" required="false"%>
<%@ attribute name="hiddenSubsReminder" type="java.lang.String" required="false"%>
<%@ attribute name="disable" type="java.lang.String" required="false"%>

<common:searchMsgDialog id="notiSmDialog${id}" parentId="notification${id}" />
<common:searchMsgDialog id="firstReminderSmDialog${id}" parentId="firstReminder${id}" />
<common:searchMsgDialog id="secondReminderSmDialog${id}" parentId="secondReminder${id}" />
<common:searchMsgDialog id="thirdReminderSmDialog${id}" parentId="thirdReminder${id}"/>
<common:searchMsgDialog id="subsReminderSmDialog${id}" parentId="subsReminder${id}" />
<form:input type="hidden" id="${id}roleSequence" path="${dto}.roleSequence" />
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
								<td width="90%" align="left" valign="middle" nowrap class="wpsPortletTitle">
			                   ${title}
								</td>
								<td width="10%" class="wpsPortletTitle" align="right" height="10" nowrap>
								<!-- <input type="image" align="right" id="btnMax${id}" name="btnAOMax" src="${ctx}/images/title_maximize.gif" style="display:none"></input>
									<input type="image" align="right" id="btnMin${id}" name="btnAOMin" src="${ctx}/images/title_minimize.gif"></input>  -->
									<input type="image" align="right" id="btnMax${id}" name="btnAOMax" src="${ctx}/images/title_maximize.gif" style="display:inline"></input>
									<input type="image" align="right" id="btnMin${id}" name="btnAOMin" src="${ctx}/images/title_minimize.gif" style="display:none"></input> 
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
								 	<!-- #1 Deadline-->
										<tr>
											<td>
											Deadline
										    </td>
										 <td>&nbsp;</td>
										 <td width="85%" colspan="2"> 
										<!--  <input name="deadlineAO" type="text" size="11" maxlength="10" datasource="dsAO:ha_hr_ea_report_role.deadline" displayformatlocalekey="format.displayDate"></input> -->
										 <c:choose>
										 <c:when test="${isRemaining}">
										 	<common:commonDate id="deadline${id}" name="${dto}.deadline" disabled="${!disable}" readonly='Y'/></td>
										 </c:when>
										 <c:otherwise>
										 	<common:commonDate id="deadline${id}" name="${dto}.deadline" disabled="${!disable}" /></td>
										 </c:otherwise>
										 </c:choose>
										  <!-- <common:commonDate id="deadline${id}" name="${dto}.deadline" disabled="${!disable}"/></td> -->
										</tr>
										
									<!-- #2 Employee number-->
										<tr>
										  <td width="15%">Officer Employee Number/Name
										  </td>
										 <td>&nbsp;</td>
										 <td width="85%" colspan="2"> 
										 <c:choose>
										 <c:when test="${disable}">
										 <common:employeeSearch empNumId="${id}employeeNum" empNum="${dto}.employeeNum" empNameId="${id}employeeName" empName="${dto}.employeeName" 
										 empNameIdReadOnly="${!disable}" disabled="${!disable}"/>
										 </c:when>
										 <c:otherwise>
										 <common:employeeSearch empNumId="${id}employeeNum" empNum="${dto}.employeeNum" empNameId="${id}employeeName" empName="${dto}.employeeName" 
										 empNameIdReadOnly="${!disable}" disabled="${!disable}" searchBtnHidden="Y"/>
										 </c:otherwise>
										 </c:choose>
							 			</td>
										</tr>
										<c:if test="${id =='RO'}">
										<tr>
										  <td width="15%">Handling Officer
										  </td>
										 <td>&nbsp;</td>
										 <td width="85%" colspan="2"> 
										 <c:choose>
										 <c:when test="${disable}">
										 <common:employeeSearch empNumId="${id}employeeNumROHO" empNum="${dto}.employeeNumROHO" empNameId="${id}employeeNameROHO" empName="${dto}.employeeNameROHO" 
										 empNameIdReadOnly="${!disable}" disabled="${!disable}"/>
										 </c:when>
										 <c:otherwise>
										 <common:employeeSearch empNumId="${id}employeeNumROHO" empNum="${dto}.employeeNumROHO" empNameId="${id}employeeNameROHO" empName="${dto}.employeeNameROHO" 
										 empNameIdReadOnly="${!disable}" disabled="${!disable}" searchBtnHidden="Y"/>
										 </c:otherwise>
										 </c:choose>
							 			</td>
										</tr>
										</c:if>
									
								  </table>									
								  <table width="100%"  border="0" cellpadding="0">
									
									<!-- #3 Notification-->
									<tr id="box3${id}">
									 <td valign="top">
									 Notification*</td>
									<td valign="top">&nbsp;</td>
										<td width="50%">
										   <form:textarea id="notification${id}" path="${dto}.notification" rows="5" cols="95" maxlength="980" size="1000" wrap="soft" disabled="${!disable}"></form:textarea>
										  	</td>
										  <td width="35%" valign="top" align="left">
										  <c:if test="${disable}">
										  <input type="button" id="sel${id}NotificationLink" name="sel${id}NotificationLink" value="Search" onclick="searchMsg('notiSmDialog${id}');" />
										  </c:if>
										  </td>
									</tr>

									<!-- #4 Interval-->
										<tr id="box4${id}">									
											<td  class="wpsLabelText">&nbsp;</td>
											<td class="wpsLabelText" colspan="3">Interval</td>
										</tr>

									<!-- #5 First Reminder-->
									<tr id="box5${id}">
									 <td valign="top">First Reminder *</td>
										  
										   <td valign="top">
										   <form:input id="firstRemInterval${id}"  path="${dto}.firstRemInterval" type="text" size="8" maxlength="3" value="" class="dginput"  disabled="${!disable}"/>
										   </td>
										   <td >
										   <form:textarea id="firstReminder${id}" path="${dto}.firstReminder" rows="5" cols="95" maxlength="980" size="1000" wrap="soft" disabled="${!disable}"></form:textarea>
										  </td>
										<c:if test="${disable}">
										  <td valign="top" align="left">
										  <input id="sel${id}FirstReminder" name="sel${id}FirstReminder" type="button" value="Search" onclick="searchMsg('firstReminderSmDialog${id}');"/>
										  </td>
										  </c:if>
									</tr>
									
									<!-- #6 Second Reminder-->
									<tr id="box6${id}">
									 <td valign="top">
									 Second Reminder *</td>
										  
										 <td valign="top">
										 <form:input id="secondRemInterval${id}" path="${dto}.secondRemInterval" type="text" size="8" maxlength="3" class="dginput" disabled="${!disable}"/>
										 </td>
										   <td>
										   <form:textarea id="secondReminder${id}" path="${dto}.secondReminder" rows="5" cols="95" maxlength="980" size="1000" wrap="soft" disabled="${!disable}"></form:textarea>
										   </td>
										<c:if test="${disable}">
										  <td valign="top" align="left">
										  <input id="sel${id}SecondReminder" name="sel${id}SecondReminder" type="button" value="Search" onclick="searchMsg('secondReminderSmDialog${id}');" />
										  </td>
										  </c:if>
									</tr>
										
									 
										
									<!-- #7 Third Reminder-->
									<tr id="box7${id}">
									 <td valign="top">
									 Third Reminder *</td>
										  
										  <td valign="top">
										  <form:input id="thirdRemInterval${id}" path="${dto}.thirdRemInterval" type="text" size="8" maxlength="3" value="" class="dginput"  disabled="${!disable}"/>
										  </td>
										   <td>
										   <form:textarea id="thirdReminder${id}" path="${dto}.thirdReminder" rows="5" cols="95" maxlength="980" size="1000" wrap="soft" disabled="${!disable}"></form:textarea>
										  </td>
										<c:if test="${disable}">
										  <td valign="top" align="left"> 
										  <input id="sel${id}ThirdReminder" name="sel${id}ThirdReminder" type="button" value="Search" onclick="searchMsg('thirdReminderSmDialog${id}');"/>
										  </td>
										  </c:if>
									</tr>	
										
								<!-- #8 Subsequent Reminder-->
									<tr id="box8${id}">
									 <td valign="top">
									 Subsequent Reminder *</td>
										  
										  <td valign="top">
										  <form:input id="subsRemInterval${id}" path="${dto}.subsRemInterval" type="text" size="8" maxlength="3" value="" class="dginput" disabled="${!disable}"></form:input>
										  </td>
										   <td>
										    <form:textarea id="subsReminder${id}" path="${dto}.subsReminder" rows="5" cols="95" maxlength="980" size="1000" wrap="soft" disabled="${!disable}"></form:textarea>
										  </td>
										<c:if test="${disable}">
										  <td valign="top" align="left">
										  <input id="sel${id}SubsReminder" name="sel${id}SubsReminder" type="button" value="Search" onclick="searchMsg('subsReminderSmDialog${id}');" /></td>
										</tr>	
										</c:if>
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
<script>
$( document ).ready(function() {
	document.getElementById("box3${id}").hidden=true;
	document.getElementById("box4${id}").hidden=true;
	document.getElementById("box5${id}").hidden=true;
	document.getElementById("box6${id}").hidden=true;
	document.getElementById("box7${id}").hidden=true;
	document.getElementById("box8${id}").hidden=true;
});

document.getElementById("btnMax${id}").onclick=function(){
	document.getElementById("btnMin${id}").style.display="inline";
	document.getElementById("btnMax${id}").style.display="none";
	document.getElementById("box3${id}").hidden=false;
	document.getElementById("box4${id}").hidden=false;
	document.getElementById("box5${id}").hidden=false;
	document.getElementById("box6${id}").hidden=false;
	document.getElementById("box7${id}").hidden=false;
	document.getElementById("box8${id}").hidden=false;
	return false;
	
};
document.getElementById("btnMin${id}").onclick=function(){
	document.getElementById("box3${id}").hidden=true;
	document.getElementById("box4${id}").hidden=true;
	document.getElementById("box5${id}").hidden=true;
	document.getElementById("box6${id}").hidden=true;
	document.getElementById("box7${id}").hidden=true;
	document.getElementById("box8${id}").hidden=true;
	
	document.getElementById("btnMin${id}").style.display="none";
	document.getElementById("btnMax${id}").style.display="inline";
	return false;
	
};
function searchMsg(dialogId){
	$("#"+dialogId).dialog("open");
}

$(".dginput").keyup(function(){
	var tmptxt=$(this).val();     
    $(this).val(tmptxt.replace(/\D|^0/g,''));     
}).bind("paste",function(){     
    var tmptxt=$(this).val();     
    $(this).val(tmptxt.replace(/\D|^0/g,''));     
}).css("ime-mode", "disabled");   


</script>