<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="com.hkha.ea.dto.assess.AssessAppraisalDTO" required="true"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0">
				<tr align="center">
					<td colspan="2" class="wpsDesc">
						<strong>PART B : YEAR-END REVIEW</strong>
						<br>
						<strong>乙部 : 年終檢討</strong>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2" align="left" class="wpsDesc">
						&nbsp;
					</td>
				</tr>
				<tr align="center">
					<td align="left" class="wpsDesc">
						<strong>Section 1</strong>
						<br/>
						<strong>第一欄</strong>
		      		</td>
					<td width="89%" align="left" class="wpsDesc">
						<strong>Appraisal of Performance <em> (to be completed by Appraising Officer)</em></strong>
						<br/>
						<strong>工作表現評核<em>( 由評核人員填寫 )</em></strong>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2" align="left" class="wpsDesc">
						Indicate how effectively the planned results were actually achieved during the appraisal period.
						<br> 
						請列明評核期內主要工作目標所取得的成效。
					</td>
				</tr>
			</table>
			<br/>
			<table width="100%"  border="0" cellpadding="0">
				<tr>
					<td colspan="3">
						<strong> Rating 評級 :</strong>
					</td>
				</tr>
				<tr valign="top">
					<td width="19%">
						<strong>A – Outstanding 優</strong>
					</td>
					<td width="3%" align="right">
						:
					</td>
					<td>
						Consistently excelled in all responsibility areas
						<br/>
						表現優異，工作成效遠超應有水平
					</td>
				</tr>
				<tr valign="top">
					<td>
						<strong> B – Very Effective 良</strong>
					</td>
					<td align="right">
						:
					</td>
					<td>
						Met and occasionally exceeded requirements in all responsibility areas
						<br/>
						表現良好，工作成效達到並偶爾超越應有水平
					</td>
				</tr>
				<tr valign="top">
					<td>
						<strong> C – Effective 常</strong>
					</td>
					<td align="right" valign="top">
						:
					</td>
					<td>
						Generally met overall requirements of the responsibility areas
						<br/>
						工作成效達到應有水平
					</td>
				</tr>
				<tr valign="top">
					<td>
						<strong> D – Below Expectation</strong><strong> 差</strong>
					</td>
					<td align="right">
						:
					</td>
					<td>
						Partially met the requirements of the responsibility areas and had definite performance deficiencies
						<br/>
						表現確實有缺點，工作成效只有部分達到應有水平
					</td>
				</tr>
				<tr valign="top">
					<td>
						<strong> E – Poor 劣</strong>
					</td>
					<td align="right" valign="top">
						:
					</td>
					<td>
						Key objectives and results not achieved
						<br/>
						主要工作目標和成效未能達到
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
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" width="1" src="${ctx}/images/dot.gif">
					</td>
					<td width="100%" valign="top"  nowrap>		
						<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
							<tr>
								<td dir="ltr" valign="top">
									<table width="100%" border="0" cellpadding="0">
										<tr valign="middle" align="center" class="wpsPortletTitle" bgcolor="#A7E6F8">
											<td width="30%" rowspan="2">Key Objectives
												<br/>主要工作目標
											</td>
											<td width="50%" rowspan="2">Results Achieved
												<br/>取得的成效
											</td>
											<td width="10%" rowspan="2">Weighting
												<br/>比重 %
											</td>
											<td width="10%" rowspan="2">Rating 評級
												<br/>(Please select
												<br/>appropriate boxes 
												<br/>請選擇適當方格)
												<table width="100%" border="0" cellpadding="0">
													<tr cosplan="5" align="center" class="wpsPortletTitle" bgcolor="#A7E6F8">
														<td valign="middle">A</td>
														<td valign="middle">B</td>
														<td valign="middle">C</td>
														<td valign="middle">D</td>
														<td valign="middle">E</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr></tr>
										<c:forEach items="${dto.partB1InfoList}" var="item" varStatus="status">
										<tr valign="top" <c:if test="${status.index%2!=0}">style="background-color: #DEF8FF;"</c:if> >
											<td  class="wpsPortletText">	
												<!-- added on 20180115 each role should only able to edit their own part-->
												<c:choose>
    											<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        											<form:textarea path="partB1InfoList[${status.index}].keyObjectives" id="${id}keyObjectives${status.index}" name="partB1InfoList[${status.index}].keyObjectives" wrap="soft" style="width:100%; white-space:pre-wrap" rows="10" maxlength="440"  disabled="true"/>
    											</c:when>    
   										 		<c:otherwise>
													<form:textarea path="partB1InfoList[${status.index}].keyObjectives" id="${id}keyObjectives${status.index}" name="partB1InfoList[${status.index}].keyObjectives" wrap="soft" style="width:100%; white-space:pre-wrap" rows="10" maxlength="440"  />
    											</c:otherwise>
												</c:choose>
												<!-- end added on 20180115 each role should only able to edit their own part-->
											</td>
											<td align="right"  class="wpsPortletText">
												<!-- added on 20180115 each role should only able to edit their own part-->
												<c:choose>
    											<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        											<form:textarea path="partB1InfoList[${status.index}].resultAchieved" id="${id}resultAchieved${status.index}" name="partB1InfoList[${status.index}].resultAchieved" wrap="soft" style="width:100%; white-space:pre-wrap" rows="10" maxlength="1400" disabled="true"/>
    											</c:when>    
   										 		<c:otherwise>
													<form:textarea path="partB1InfoList[${status.index}].resultAchieved" id="${id}resultAchieved${status.index}" name="partB1InfoList[${status.index}].resultAchieved" wrap="soft" style="width:100%; white-space:pre-wrap" rows="10" maxlength="1400" />
    											</c:otherwise>
												</c:choose>
												<!-- end added on 20180115 each role should only able to edit their own part-->
											</td>
											<td align="center"  class="wpsPortletText">										
												<!-- added on 20180115 each role should only able to edit their own part-->
												<c:choose>
    											<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        											<form:input path="partB1InfoList[${status.index}].weighting" id="${id}weighting${status.index}" name="partB1InfoList[${status.index}].weighting"  size="7" maxlength="6" style="width:50%" disabled="true"/>
    											</c:when>    
   										 		<c:otherwise>
													<form:input path="partB1InfoList[${status.index}].weighting" id="${id}weighting${status.index}" name="partB1InfoList[${status.index}].weighting"  size="7" maxlength="6" style="width:50%" />
    											</c:otherwise>
												</c:choose>
												<!-- end added on 20180115 each role should only able to edit their own part-->		
												<form:hidden path="partB1InfoList[${status.index}].seqNo" id="${id}seqNo${status.index}" name="partB1InfoList[${status.index}].seqNo"   />		
											</td>
											<td valign="top"  class="wpsPortletText">
												<table width="100%" border="0" cellpadding="0">
													<tr cosplan="5" align="center">
													<!-- added on 20180115 each role should only able to edit their own part-->
													
													<%--
													<c:choose>
    												<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        												<td valign="middle"  class="wpsPortletText"><form:radiobutton path="partB1InfoList[${status.index}].rating" value="A" disabled="true"/></td>
														<td valign="middle"  class="wpsPortletText"><form:radiobutton path="partB1InfoList[${status.index}].rating" value="B" disabled="true"/></td>
														<td valign="middle"  class="wpsPortletText"><form:radiobutton path="partB1InfoList[${status.index}].rating" value="C" disabled="true"/></td>
														<td valign="middle"  class="wpsPortletText"><form:radiobutton path="partB1InfoList[${status.index}].rating" value="D" disabled="true"/></td>
														<td valign="middle"  class="wpsPortletText"><form:radiobutton path="partB1InfoList[${status.index}].rating" value="E" disabled="true"/></td>
    												</c:when>    
   										 			<c:otherwise>
														<td valign="middle"  class="wpsPortletText"><form:radiobutton path="partB1InfoList[${status.index}].rating" value="A"/></td>
														<td valign="middle"  class="wpsPortletText"><form:radiobutton path="partB1InfoList[${status.index}].rating" value="B"/></td>
														<td valign="middle"  class="wpsPortletText"><form:radiobutton path="partB1InfoList[${status.index}].rating" value="C"/></td>
														<td valign="middle"  class="wpsPortletText"><form:radiobutton path="partB1InfoList[${status.index}].rating" value="D"/></td>
														<td valign="middle"  class="wpsPortletText"><form:radiobutton path="partB1InfoList[${status.index}].rating" value="E"/></td>
    												</c:otherwise>
													</c:choose> --%>
													
													<!-- added custom radio button on 20211110 by pccw -->
													<c:choose>
    												<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        												<td valign="middle"  class="wpsPortletText">
        												<label class="container">
        													<form:radiobutton path="partB1InfoList[${status.index}].rating" value="A" disabled="true"/>
        													<span class="checkmark"></span>
        												</label>
        												</td>
														<td valign="middle"  class="wpsPortletText">
														<label class="container">
															<form:radiobutton path="partB1InfoList[${status.index}].rating" value="B" disabled="true"/>
															<span class="checkmark"></span>
        												</label>
														</td>
														<td valign="middle"  class="wpsPortletText">
														<label class="container">
															<form:radiobutton path="partB1InfoList[${status.index}].rating" value="C" disabled="true"/>
															<span class="checkmark"></span>
        												</label>
														</td>
														<td valign="middle"  class="wpsPortletText">
														<label class="container">
															<form:radiobutton path="partB1InfoList[${status.index}].rating" value="D" disabled="true"/>
															<span class="checkmark"></span>
        												</label>
														</td>
														<td valign="middle"  class="wpsPortletText">
														<label class="container">
															<form:radiobutton path="partB1InfoList[${status.index}].rating" value="E" disabled="true"/>
															<span class="checkmark"></span>
        												</label>
														</td>
    												</c:when>    
   										 			<c:otherwise>
														<td valign="middle"  class="wpsPortletText">
														<label class="container">
															<form:radiobutton path="partB1InfoList[${status.index}].rating" value="A"/>
															<span class="checkmark"></span>
        												</label>
														</td>
														<td valign="middle"  class="wpsPortletText">
														<label class="container">
															<form:radiobutton path="partB1InfoList[${status.index}].rating" value="B"/>
															<span class="checkmark"></span>
        												</label>
														</td>
														<td valign="middle"  class="wpsPortletText">
														<label class="container">
															<form:radiobutton path="partB1InfoList[${status.index}].rating" value="C"/>
															<span class="checkmark"></span>
        												</label>
														</td>
														<td valign="middle"  class="wpsPortletText">
														<label class="container">
															<form:radiobutton path="partB1InfoList[${status.index}].rating" value="D"/>
															<span class="checkmark"></span>
        												</label>
														</td>
														<td valign="middle"  class="wpsPortletText">
														<label class="container">
															<form:radiobutton path="partB1InfoList[${status.index}].rating" value="E"/>
															<span class="checkmark"></span>
        												</label>
														</td>
    												</c:otherwise>
													</c:choose>
													<!-- end added custom radio button on 20211110 by pccw -->														
													
													<!-- end added on 20180115 each role should only able to edit their own part-->		
													</tr>
													</table>					
											</td>
										</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" width="1" src="${ctx}/images/dot.gif">
					</td>
				<tr/>
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="100%" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>