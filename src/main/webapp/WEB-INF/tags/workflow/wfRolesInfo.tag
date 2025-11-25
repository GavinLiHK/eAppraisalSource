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


<!---------- Appraising officer --------------->
<common:searchMsgDialog id="notiSmDialog${id}" parentId="notification${id}" />
<common:searchMsgDialog id="firstReminderSmDialog${id}" parentId="firstReminder${id}" />
<common:searchMsgDialog id="secondReminderSmDialog${id}" parentId="secondReminder${id}" />
<common:searchMsgDialog id="thirdReminderSmDialog${id}" parentId="thirdReminder${id}"/>
<common:searchMsgDialog id="subsReminderSmDialog${id}" parentId="subsReminder${id}" />

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
								<form:hidden id="role${id}" path="${dto}.role"/>&nbsp;   
									<form:checkbox id="roleCheck${id}" path="${dto}.roleCheck" value="Y"/>&nbsp;   			          				
			          				
                      				${title}
								</td>
								
								
								<td class="wpsPortletTitle" align="right" height="10" nowrap>
									<input type="image" align="right" id="btnMax${id}" name="btnMax" src="${ctx}/images/title_maximize.gif" style="display:none"/>
									<input type="image" align="right" id="btnMin${id}" name="btnMin" src="${ctx}/images/title_minimize.gif" />
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
								 	<!-- #1 Responsive Officer , Deadline-->
								 	
										<tr >
										  <td class="wpsLabelText" style="width:10%"> Deadline</td>
										  <td class="wpsLabelText" style="width:5%"></td>
										  <td class="wpsLabelText" align="left" style="width:47%"> 
										   <common:commonDate id="deadline${id}" name="${dto}.deadline" />
								
										 </td>
										<td class="wpsLabelText" ></td>
										</tr>	
										
										<!-- #2  Notification-->															 
										<tr id="box2${id}">
									 		<td valign="top" class="wpsLabelText" >Notification</td>
									 		<td   valign="top" class="wpsLabelText">&nbsp;</td>
											<td class="wpsLabelText">
												<form:textarea id="notification${id}" path="${dto}.notification" value="" cols="120" maxlength="980" size="1000" wrap="soft" rows="5" />
	 										
										  </td>
										  <td   valign="top"  class="wpsLabelText">
										  <input type="button" id="sel${id}NotificationLink" name="sel${id}NotificationLink" value="Search" onclick="searchMsg('notiSmDialog${id}');"/>
										 
										   </td>
										</tr>
										
										<!-- #3 Interval-->
										<tr id="box3${id}">									
											<td  class="wpsLabelText">&nbsp;</td>
											<td class="wpsLabelText" colspan="3">Interval</td>
										</tr>
										<!-- #4 First Reminder-->
										<tr id="box4${id}">
										 <td  valign="top" class="wpsLabelText">First Reminder *</td>
									 	 <td  valign="top" class="wpsLabelText">
											<form:input id="firstRemInterval${id}"  path="${dto}.firstRemInterval" type="text" size="8" maxlength="3" value="" class="dginput" />
										 </td>
									 	<td >
										 	<form:textarea id="firstReminder${id}" path="${dto}.firstReminder" value="" cols="120" rows="5" wrap="soft" maxlength="980" size="1000" />
	 										
									 	</td>									
										 <td valign="top" align="left" class="wpsLabelText">
										 <input id="sel${id}FirstReminder" name="sel${id}FirstReminder" type="button" value="Search" onclick="searchMsg('firstReminderSmDialog${id}');"/>
										
							 		 	</td>
										</tr>
										
										
										<!-- #5 Second Reminder-->
										<tr id="box5${id}">
										 <td valign="top" class="wpsLabelText">Second Reminder *</td>										  
										 <td valign="top" class="wpsLabelText">
										 	<form:input id="secondRemInterval${id}" path="${dto}.secondRemInterval" type="text" size="8" maxlength="3" class="dginput" />
										 	</input>
										 </td>
										 <td class="wpsLabelText">
										   <form:textarea id="secondReminder${id}" path="${dto}.secondReminder" value="" cols="120" rows="5" wrap="soft" maxlength="980" size="1000"  />
	 										
										 </td>							
										 <td valign="top" align="left">
										  <input id="sel${id}SecondReminder" name="sel${id}SecondReminder" type="button" value="Search" onclick="searchMsg('secondReminderSmDialog${id}');"/>
										 
										 </td>
										</tr>	
										
										
									
									<!-- #6 Third Reminder-->
									<tr id="box6${id}">
									 <td valign="top" class="wpsLabelText"> Third Reminder *</td>									  
									  <td valign="top" class="wpsLabelText">
										 <form:input id="thirdRemInterval${id}" path="${dto}.thirdRemInterval" type="text" size="8" maxlength="3" value="" class="dginput" />
										 
									 </td>
									<td class="wpsLabelText">
										<form:textarea id="thirdReminder${id}" path="${dto}.thirdReminder" value="" cols="120" rows="5" wrap="soft" maxlength="980" size="1000" />								   
	 										
									</td>
									<td valign="top" align="left" class="wpsLabelText"> 
									 <input id="sel${id}ThirdReminder" name="sel${id}ThirdReminder" type="button" value="Search" onclick="searchMsg('thirdReminderSmDialog${id}');"/>
								
									 </td>
									</tr>	
									
									
								<!-- #7 Subsequent Reminder-->
									<tr id="box7${id}">
									 <td valign="top" class="wpsLabelText">Subsequent Reminder *</td>
									 <td valign="top" class="wpsLabelText">
										  <form:input id="subsRemInterval${id}" path="${dto}.subsRemInterval" type="text" size="8" maxlength="3" value="" class="dginput"  />
									 </td>
									<td class="wpsLabelText" >
										<form:textarea id="subsReminder${id}" path="${dto}.subsReminder" value="" cols="120" rows="5" wrap="soft" maxlength="980" size="1000" />
	 										
									</td>
									<td valign="top" align="left" class="wpsLabelText">
									 <input id="sel${id}SubsReminder" name="sel${id}SubsReminder" type="button" value="Search" onclick="searchMsg('subsReminderSmDialog${id}');"/>
										
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
<script>

document.getElementById("btnMax${id}").onclick=function(){
	document.getElementById("btnMin${id}").style.display="inline";
	document.getElementById("btnMax${id}").style.display="none";
	document.getElementById("box2${id}").hidden=false;
	document.getElementById("box3${id}").hidden=false;
	document.getElementById("box4${id}").hidden=false;
	document.getElementById("box5${id}").hidden=false;
	document.getElementById("box6${id}").hidden=false;
	document.getElementById("box7${id}").hidden=false;
	return false;
	
};
document.getElementById("btnMin${id}").onclick=function(){
	document.getElementById("box2${id}").hidden=true;
	document.getElementById("box3${id}").hidden=true;
	document.getElementById("box4${id}").hidden=true;
	document.getElementById("box5${id}").hidden=true;
	document.getElementById("box6${id}").hidden=true;
	document.getElementById("box7${id}").hidden=true;
	
	document.getElementById("btnMin${id}").style.display="none";
	document.getElementById("btnMax${id}").style.display="inline";
	return false;
	
};
function searchMsg(dialogId){
	$("#"+dialogId).dialog("open");
}


$("#roleCheck${id}").change(function(){
	if($("#roleCheck${id}").is(':checked')){
		$("#role${id}").val("${id}");
	}else{
		$("#role${id}").val("");
	}
});

$(".dginput").keyup(function(){
	var tmptxt=$(this).val();     
    $(this).val(tmptxt.replace(/\D|^0/g,''));     
}).bind("paste",function(){     
    var tmptxt=$(this).val();     
    $(this).val(tmptxt.replace(/\D|^0/g,''));     
}).css("ime-mode", "disabled");   


</script>