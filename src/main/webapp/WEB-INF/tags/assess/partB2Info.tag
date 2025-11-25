<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="com.hkha.ea.dto.assess.AssessAppraisalDTO" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%"  border="0" cellpadding="0">
				<tr align="center">
					<td align="left" class="wpsDesc">
						<strong>Section 2</strong>
						<br/>
						<strong>第二欄</strong>
					</td>
					<td width="89%" align="left" class="wpsDesc">
						<strong>Assessment of Overall Performance</strong>
						<br/>
						<strong>整體工作表現評核</strong>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src="${ctx}/images/dot.gif"></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
							  <td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
                                  Overall Rating 整體評級 </td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif"></td>
				</tr>
				<tr>
					<td bgcolor="#CFD9E5" height="100%" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
					<td width="100%" valign="top">
						<table width="100%" border="0" cellpadding="0">
							<tr>
								<td width="100%" align="center">
									<!-- dsHaHrEaContract:ha_hr_ea_contract.OVERALL_RATING"> -->
											<!-- added on 20180115 each role should only able to edit their own part-->
											
											<%--
											<c:choose>
    										<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        										<form:radiobutton path="partB2Info.overallRating" value="A" disabled="true"/>A
												<form:radiobutton path="partB2Info.overallRating" value="B" disabled="true"/>B
												<form:radiobutton path="partB2Info.overallRating" value="C" disabled="true"/>C
												<form:radiobutton path="partB2Info.overallRating" value="D" disabled="true"/>D
												<form:radiobutton path="partB2Info.overallRating" value="E" disabled="true"/>E
    										</c:when>    
   										 	<c:otherwise>
												<form:radiobutton path="partB2Info.overallRating" value="A" />A
												<form:radiobutton path="partB2Info.overallRating" value="B" />B
												<form:radiobutton path="partB2Info.overallRating" value="C" />C
												<form:radiobutton path="partB2Info.overallRating" value="D" />D
												<form:radiobutton path="partB2Info.overallRating" value="E" />E
    										</c:otherwise>
											</c:choose> --%>
											
											<!-- added custom radio button on 20211110 by pccw -->
											<c:choose>
    										<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        										<label class = "container">
        											<form:radiobutton path="partB2Info.overallRating" value="A" disabled="true"/>A
        											<span class = "checkmark"></span>
        										</label>
        										<label class = "container">
        											<form:radiobutton path="partB2Info.overallRating" value="B" disabled="true"/>B
        											<span class = "checkmark"></span>
        										</label>
        										<label class = "container">
        											<form:radiobutton path="partB2Info.overallRating" value="C" disabled="true"/>C
        											<span class = "checkmark"></span>
        										</label>
        										<label class = "container">
        											<form:radiobutton path="partB2Info.overallRating" value="D" disabled="true"/>D
        											<span class = "checkmark"></span>
        										</label>
        										<label class = "container">
        											<form:radiobutton path="partB2Info.overallRating" value="E" disabled="true"/>E
        											<span class = "checkmark"></span>
        										</label>
    										</c:when>    
   										 	<c:otherwise>
   										 		<label class = "container">
													<form:radiobutton path="partB2Info.overallRating" value="A" />A
													<span class = "checkmark"></span>
        										</label>
        										<label class = "container">
													<form:radiobutton path="partB2Info.overallRating" value="B" />B
													<span class = "checkmark"></span>
        										</label>
        										<label class = "container">
													<form:radiobutton path="partB2Info.overallRating" value="C" />C
													<span class = "checkmark"></span>
        										</label>
        										<label class = "container">
													<form:radiobutton path="partB2Info.overallRating" value="D" />D
													<span class = "checkmark"></span>
        										</label>
        										<label class = "container">
													<form:radiobutton path="partB2Info.overallRating" value="E" />E
													<span class = "checkmark"></span>
        										</label>
    										</c:otherwise>
											</c:choose>
											<!-- end added custom radio button on 20211110 by pccw -->	
																						
											<!-- end added on 20180115 each role should only able to edit their own part-->		
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
				</tr>
				<tr height="1">
					<td bgcolor="#CFD9E5" width="100%" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr>
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src="${ctx}/images/dot.gif">
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
							  <td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
                                  Comments on Overall Performance 整體工作表現評語
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
					<td width="100%" valign="top">
						<table width="100%"  border="0" cellpadding="0">
							<tr>
								<td>
									Please provide an overall appraisal of the appraisee's performance. Comment on notable strengths and weaknesses which have affected performance. 
									<br>
									請在本欄評核受評人的整體工作表現，並列出影響其表現的優點及缺點。
								</td>
							</tr>
							<tr valign="top">
								<td align="right">
								<!-- ha_hr_ea_contract.XXOVERALL_COMMENT -->
									<!-- added on 20180115 each role should only able to edit their own part-->
									<c:choose>
    								<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        								<form:textarea path="partB2Info.overallPerformanceComment" id="overallPerformanceComment" maxlength="8000" wrap="soft" style="width:100%" rows="30" font="wpsPortletTitle" classname="inputfield" disabled="true"/>
    								</c:when>    
   									<c:otherwise>
										<form:textarea path="partB2Info.overallPerformanceComment" id="overallPerformanceComment" maxlength="8000" wrap="soft" style="width:100%" rows="30" font="wpsPortletTitle" classname="inputfield"  />
    								</c:otherwise>
									</c:choose>
									<!-- end added on 20180115 each role should only able to edit their own part-->		
								</td>
				        	</tr>
		        	
							<tr>
								<td>
									Were there any special factors such as ill health, frequent absence due to sick leave or personal problems which in your opinion had affected the appraisee's performance? Please mention the appraisee's sick leave record during the appraisal period if you consider that his/her performance has been affected by his/her sick leave.
									<br/>
									你認為受評人的工作表現，是否受到特別因素影響，例如健康欠佳、因病假而經常缺勤或私人問題？如果你認為受評人的工作表現受到病假影響，請載列他 / 她在評核期內的病假記錄。
								</td>
							</tr>
							<tr valign="top">
								<td align="right">
								<!-- ha_hr_ea_contract.SPECIAL_FACTORS -->
									<!-- added on 20180115 each role should only able to edit their own part-->
									<c:choose>
    								<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        								<form:textarea path="partB2Info.specialFactors" id="specialFactors" maxlength="980" wrap="soft" style="width:100%" rows="5" font="wpsPortletTitle" textlocalekey="" classname="inputfield" disabled="true" />
    								</c:when>    
   									<c:otherwise>
										<form:textarea path="partB2Info.specialFactors" id="specialFactors" maxlength="980" wrap="soft" style="width:100%" rows="5" font="wpsPortletTitle" textlocalekey="" classname="inputfield"  />
    								</c:otherwise>
									</c:choose>
									<!-- end added on 20180115 each role should only able to edit their own part-->		
   			                    <!--  	<salmon:input type="submit" name="btnSaveSpecialFactors" value="Save"></salmon:input>-->
								</td>
				        	</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
				</tr>
				<tr height="1">
					<td bgcolor="#CFD9E5" width="100%" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>