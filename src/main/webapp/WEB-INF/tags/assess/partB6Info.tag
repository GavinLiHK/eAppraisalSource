<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="com.hkha.ea.dto.assess.AssessAppraisalDTO" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%"  border="0" cellpadding="0">
				<tr valign="top">
					<td align="left" class="wpsDesc">
						<strong>Section 6</strong>
					</td>
					<td width="89%" align="left" class="wpsDesc">
						<strong>Performance Appraisal Interview</strong> <em>(To be completed by the Appraising Officer or the Countersigning Officer who conducted the interview)</em>
					</td>
				</tr>
				<tr valign="top">
					<td align="left" class="wpsDesc">
						<strong>第六欄</strong>
					</td>
					<td class="wpsDesc">
						<strong>工作表現評核會見</strong><em>(由負責進行會見的人員，即評核人員或加簽人員填寫)</em>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%"  border="0" cellpadding="0">
				<tr>
					<td align="left">
						Please show the entire report to the appraisee before the interview.  A summary of the discussion, including comments of the appraisee, should be recorded below. <br>請讓受評人於會見前閱讀整份報告，並在下面記錄面談概要(包括受評人的意見)。
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
					<!-- ha_hr_ea_contract.XXREVIEW_INTERVIEW  -->
						<!-- <salmon:input type="submit" name="btnSaveReviewInterview" value="Save"></salmon:input>	 -->	
						<!-- added on 20180115 each role should only able to edit their own part-->
						<c:choose>
    					<c:when test="${dto.reportUserRole.currentUserRoleStatus!='IO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        					<form:textarea  path="partB6Info.reviewInterview" wrap="soft" maxlength="3528" style="width:100%" rows="10" font="wpsPortletTitle" classname="inputfield" disabled="true"/>
    					</c:when>    
   						<c:otherwise>
							<form:textarea  path="partB6Info.reviewInterview" wrap="soft" maxlength="3528" style="width:100%" rows="10" font="wpsPortletTitle" classname="inputfield" />
    					</c:otherwise>
						</c:choose>
						<!-- end added on 20180115 each role should only able to edit their own part-->					  
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%"  border="0" cellpadding="0">
				<tr align="center">
					<td align="left" valign="top">
						<strong>Training & Development Plan of the Appraisee for the coming appraisal period</strong>
						<br/>
						受評人下一個評核期的培訓及發展計劃
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src="${ctx}/images/dot.gif">
					</td>
					<td>
						<table border="0"  align="Left" width="100%" cellpadding="3" cellspacing="1" class="wpsPortletBody">
							<tr align="center" valign="middle" classname="wpsPortletTitle" bgcolor="#A7E6F8">
								<td width="52%" >Development Needs <br/>發展需要</td>
								<td width="43%">Action Plan <br/>行動計劃</td>
								<td width="26%">Review Date <br/>檢討日期</td>							
							</tr>
							<c:forEach items="${dto.partB6Info.tpiList}" var="item" varStatus="status">
							<tr classname="wpsPortletText" valign="top" >
								<!-- added on 20180115 each role should only able to edit their own part-->
								<c:choose>
    							<c:when test="${dto.reportUserRole.currentUserRoleStatus!='IO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        							<td>
									<!-- ha_hr_ea_train_plan.DEVELOP_NEED -->
									<form:textarea path="partB6Info.tpiList[${status.index}].delevelopNeed" id="delevelopNeed${status.index}" wrap="soft" style="width:100%" rows="4" font="" classname="inputfield" maxlength="140" disabled="true"/>
								</td>
								<td>
									<!-- ha_hr_ea_train_plan.ACTION_PLAN -->
									<form:textarea path="partB6Info.tpiList[${status.index}].actionPlan" id="actionPlan${status.index}" wrap="soft" style="width:100%" rows="4" font="" classname="inputfield" maxlength="90" disabled="true"/>
								</td>
								<td <c:if test="${status.index%2!=0}">style="background-color: #DEF8FF;"</c:if>>
									<!-- ha_hr_ea_train_plan.REVIEW_DATE -->
									<common:commonDate id="reviewDate${status.index}" name="partB6Info.tpiList[${status.index}].reviewDate" disabled="true"/>		
								</td>
    							</c:when>    
   								<c:otherwise>
									<td>
									<!-- ha_hr_ea_train_plan.DEVELOP_NEED -->
									<form:textarea path="partB6Info.tpiList[${status.index}].delevelopNeed" id="delevelopNeed${status.index}" wrap="soft" style="width:100%" rows="4" font="" classname="inputfield" maxlength="140" />
								</td>
								<td>
									<!-- ha_hr_ea_train_plan.ACTION_PLAN -->
									<form:textarea path="partB6Info.tpiList[${status.index}].actionPlan" id="actionPlan${status.index}" wrap="soft" style="width:100%" rows="4" font="" classname="inputfield" maxlength="90" />
								</td>
								<td <c:if test="${status.index%2!=0}">style="background-color: #DEF8FF;"</c:if>>
									<!-- ha_hr_ea_train_plan.REVIEW_DATE -->
									<common:commonDate id="reviewDate${status.index}" name="partB6Info.tpiList[${status.index}].reviewDate" />		
								</td>
    							</c:otherwise>
								</c:choose>
								<!-- end added on 20180115 each role should only able to edit their own part-->	
							</tr>
							</c:forEach>
						</table>			
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
				</tr>
		        <tr height="1">
					<td bgcolor="#CFD9E5" widht="100%" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%"  border="0" cellpadding="0">
				<tr align="center">
					<td align="left" valign="top">
						I have read Sections 1 to 6 of Part B of this appraisal report.
						<br/>
						我已閱讀此評核報告乙部第一至第六欄的內容。
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>