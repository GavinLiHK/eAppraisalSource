<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="com.hkha.ea.dto.assess.AssessAppraisalDTO" required="true"%>


<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src="${ctx}/images/dot.gif">
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
									Routing
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
				</tr>
				<!-- Edited on 20170301 not displaying Next Officer in Print Submit Page for GM user -->
				    <c:if test="${dto.reportUserRole.currentUserRoleStatus!='GM' or dto.reportUserRole.currentReportStatus!='GM'}">	
					<tr height="100%">
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
					<td width="100%" valign="top"> 
						<table width="100%" border="0" cellpadding="3">
						 
							<tr valign="top">
								<td nowrap>		
									<%-- <form:radiobuttons path="printSubmitInfo.routing" items="${dto.printSubmitInfo.routingLabelList}"  itemLabel="paramDesc" itemValue="paramValue" />	
									<form:hidden path="printSubmitInfo.nextOfficerRole"/> --%>
									Next Processing Officer	
									<!--<salmon:input type="radiogroup" name="routing" value="NO" classname="inputfield" orientation="vertical" captionpos="right" />  -->
								</td>
								
								<td nowrap height="100%">

									<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
										<tr valign="top">
											<td>

												<!--<salmon:box name="boxNextOfficer" linewidth="0" margin="0" width="100%">  -->
													<table border="0" cellpadding="0" cellspacing="0" class="wpsPortletBody">
														<tr>
															<td valign="top" nowrap>
															<!-- ha_hr_ea_report_role.OFFICER_ID" -->																
																<!-- ha_hr_ea_report_role.OFFICER_NAME --> 
																<!-- edited on 20170306 -->
																<c:choose>		
																	<c:when test="${dto.reportUserRole.currentUserRoleStatus=='AO'or dto.reportUserRole.currentUserRoleStatus=='CO' or dto.reportUserRole.currentUserRoleStatus=='IO'}">
																		  <common:employeeSearch empNumId="nextOfficerId" empNum="printSubmitInfo.nextOfficerId" empNameId="nextOfficerName" empName="printSubmitInfo.nextOfficerName" />	 									
																	</c:when>
																	<c:when test="${dto.reportUserRole.currentUserRoleStatus=='RO'}">
																		 <common:employeeSearch empNumId="nextOfficerId" empNum="printSubmitInfo.nextOfficerId" empNameId="nextOfficerName" empName="printSubmitInfo.nextOfficerName" searchBtnHidden="Y" empNameIdHidden="none"/> 																		  	 									
																	</c:when>																
																	<c:when test="${dto.reportUserRole.currentUserRoleStatus=='AP'}">
																		 <common:employeeSearch  empNumId="nextOfficerId" empNum="printSubmitInfo.nextOfficerId" empNameId="nextOfficerName" empName="printSubmitInfo.nextOfficerName" empNameIdReadOnly="true"  searchBtnHidden="Y"/>	 
																	</c:when>
																</c:choose>
																<!-- end edited on 20170306 -->
															</td>
															<td>&nbsp;&nbsp;</td>
															<c:choose>
															<c:when test="${dto.reportUserRole.currentUserRoleStatus=='AP'or dto.reportUserRole.currentUserRoleStatus=='AO'or dto.reportUserRole.currentUserRoleStatus=='CO'or dto.reportUserRole.currentUserRoleStatus=='IO'or dto.reportUserRole.currentUserRoleStatus=='RO'}">
															
															<td>
																Deadline
															</td>
															<td>&nbsp;</td>
															<td nowrap>
															<!-- ha_hr_ea_report_role.DEADLINE -->
																<common:commonDate id="printSubmitInfoDeadline" name="printSubmitInfo.deadline" readonly="Y"/>	
																
															</td>
															</c:when>
															</c:choose>
														</tr>
													</table>
											<!--	</salmon:box> -->
													<c:if test="${dto.reportUserRole.currentUserRoleStatus=='SU'or dto.reportUserRole.currentUserRoleStatus=='GM'}">
											<!--	<salmon:box name="boxNextOfficerGM" linewidth="0" margin="0" width="100%">-->
													<table border="0" cellpadding="0" cellspacing="0" class="wpsPortletBody">
														<tr>
															<td valign="top" nowrap>
																	<c:if test="${dto.reportUserRole.currentUserRoleStatus=='SU'}">
																		<c:if test="${dto.reportUserRole.currentReportStatus=='I' or dto.reportUserRole.currentReportStatus=='AP' or dto.reportUserRole.currentReportStatus=='CD'
																		              or dto.reportUserRole.currentReportStatus=='AO' or dto.reportUserRole.currentReportStatus=='CO' or dto.reportUserRole.currentReportStatus=='IO'
																		              or dto.reportUserRole.currentReportStatus=='EO'}">
																				<form:input path="printSubmitInfo.nextOfficerId" id="nextOfficerId" size="11" maxlength="10" classname="inputfield" readonly="true" />
																				<form:input path="printSubmitInfo.nextOfficerName" id="nextOfficerName" size="40" maxlength="40" classname="inputfield" readonly="true" />	 
																		</c:if>
																		 <c:if test="${dto.reportUserRole.currentReportStatus=='RO' or dto.reportUserRole.currentReportStatus=='GM'}">
																		 		<form:input path="printSubmitInfo.gmOfficerId" id="gmOfficerID" size="11" maxlength="10" classname="inputfield" readonly="true" />
																		 		<form:input path="printSubmitInfo.gmOfficerName" id="gmOfficerName" size="40" maxlength="40" classname="inputfield" readonly="true" />
																		 </c:if>
																	</c:if>
																	<c:if test="${dto.reportUserRole.currentUserRoleStatus=='GM'}">
																		 <form:input path="printSubmitInfo.gmOfficerId" id="gmOfficerID" size="11" maxlength="10" classname="inputfield" readonly="true" />
																		 <form:input path="printSubmitInfo.gmOfficerName" id="gmOfficerName" size="40" maxlength="40" classname="inputfield" readonly="true" />	 
																	</c:if>
																	
																<!-- <form:input path="printSubmitInfo.gmOfficerId" id="gmOfficerID" size="11" maxlength="10" classname="inputfield" readonly="true" />
																<form:input path="printSubmitInfo.gmOfficerName" id="gmOfficerName" size="40" maxlength="40" classname="inputfield" readonly="true" /> -->
															</td>
															<td>&nbsp;&nbsp;</td>
															<td>
																Deadline
															</td>
															<td>&nbsp;</td>
															<td>
															
																<common:commonDate id="printSubmitInfo.gmDeadline" name="printSubmitInfo.gmDeadline" readonly="Y" />
																
															</td>
														</tr>
													</table>
													
												<!--</salmon:box>-->
											</td>
										</tr>
										<%-- <tr vlaign="bottom">
											<td height="20" >
												<strong>Role</strong>
												<form:select path="printSubmitInfo.routingTo" >
													<form:option value="">Please select...</form:option>
			                             			 <form:options items="${dto.printSubmitInfo.routingToList}" itemLabel="paramDesc" itemValue="paramValue"/>
												</form:select>
												<!--  <salmon:input type="select" name="routingTo" classname="inputfield" />-->
											</td>
										</tr> --%>
										</c:if>
									</table>
								</td>
							</tr>
							<tr valign="top">
								<td nowrap>		
									
									<!-- added custom radio button on 20211110 by pccw -->					
									<%--
									<label class = "container">
										<form:radiobuttons path="printSubmitInfo.routing" items="${dto.printSubmitInfo.routingLabelList}"  itemLabel="paramDesc" itemValue="paramValue" />	
									<span class = "checkmark"></span>
        							</label> --%>
														
									<c:forEach var = "func" items = "${dto.printSubmitInfo.routingLabelList}" varStatus = "Status">
										<label class = "container">
											<form:radiobutton path="printSubmitInfo.routing" value="${func.paramValue}"/>${func.paramDesc}
											<span class = "checkmark"></span>
										</label>
									</c:forEach>	
									
									<%-- <form:radiobuttons path="printSubmitInfo.routing" items="${dto.printSubmitInfo.routingLabelList}"  itemLabel="paramDesc" itemValue="paramValue" /> --%>	
									<!-- end added custom radio button on 20211110 by pccw -->
									
									<form:hidden path="printSubmitInfo.nextOfficerRole"/>
								</td>
							
								<td height="20" >
								<c:if test="${dto.reportUserRole.currentUserRoleStatus=='SU'or dto.reportUserRole.currentUserRoleStatus=='GM'}">
									<strong>Role</strong>
									<form:select path="printSubmitInfo.routingTo">	
										 <form:option value="">Please select...</form:option>
										 <!-- <form:options items="${dto.printSubmitInfo.routingToList}" itemLabel="paramDesc" itemValue="paramValue"/> -->
			                             <form:options items="${sessionScope.ROUTING_TO_LIST}" itemLabel="paramDesc" itemValue="paramValue"/>
									</form:select>
								</c:if>
								</td>
							</tr>
				</c:if>
				<!-- End edited on 20170301 not displaying Next Officer in Print Submit Page for GM user -->
				
							<c:if test="${dto.reportUserRole.currentUserRoleStatus=='SU' or dto.reportUserRole.currentUserRoleStatus=='GM'}">
							<tr>
								<td colspan="2">
									<input type="button" id="btnBackToList" value="Back To List" onclick="backToListBtnClick();"/>
								</td>
							</tr>
							</c:if>
							
							<tr valign="top">
								<td colspan="2" valign="top">
									Remarks
									<!-- ha_hr_ea_report.ROUTING_REASON -->
									<form:textarea path="printSubmitInfo.routingReason" id="routingReason" wrap="soft" style="width:100%" maxlength="196" rows="3" font="wpsPortletTitle" classname="inputfield" />
								</td>
						  	</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
				</tr>
				<tr>
					<td bgcolor="#CFD9E5" width="100%" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
	<c:if test="${dto.reportUserRole.currentUserRoleStatus=='GM' and dto.reportUserRole.currentReportStatus=='GM'}">
<!--  <salmon:box name="boxConfirmReject" linewidth="0" margin="0" width="100%">-->
	<table border="0" width="100%" cellpadding="0" cellspacing="5">
		<tr>
			<td>
				<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
					<tr height="1">
						<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src="${ctx}/images/dot.gif"></td>
						<td width="100%" height="12" nowrap>
							<table border="0" width="100%" cellpadding="3" cellspacing="0">
								<tr>
									<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
										Confirm/Reject Report
									</td>
								</tr>
							</table>
						</td>
						<td bgcolor="#CFD9E5" width="1" height="12">
							<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
						</td>
					</tr>
					<tr height="100%">
						<td bgcolor="#CFD9E5" width="1">
							<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
						</td>
						<td>
							<table width="100%" border="0" cellpadding="3">
								<tr>
									<td width="100%" align="center">
											<!-- edited on 20170223 set confirm radio button as default -->
											<!-- added custom radio button on 20211110 by pccw -->	
											
											<%--
											<form:radiobutton value="C" checked="checked" path="printSubmitInfo.confirmReject"/>Confirm
											<form:radiobutton value="R" path="printSubmitInfo.confirmReject"/>Reject --%>
												
											<label class = "container">
												<form:radiobutton value="C" checked="checked" path="printSubmitInfo.confirmReject"/>Confirm
												<span class = "checkmark"></span>
       										</label>
       										<label class = "container">
												<form:radiobutton value="R" path="printSubmitInfo.confirmReject"/>Reject
												<span class = "checkmark"></span>
        									</label>
											<!-- end added custom radio button on 20211110 by pccw -->
										
									</td>
								</tr>
							</table>
						</td>
						<td bgcolor="#CFD9E5" width="1">
							<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
						</td>
					</tr>
					<tr heihgt="1">
						<td bgcolor="#CFD9E5" width="100%" colspan="3">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</c:if>
