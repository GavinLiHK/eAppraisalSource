<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="com.hkha.ea.dto.assess.AssessAppraisalDTO" required="true"%>


<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td dir="ltr" valign="top">
						<table width="100%"  border="0" cellpadding="0">
							<tr align="center">
								<td align="left" class="wpsDesc">
									<strong>Section 4</strong>
									<br/>
									<strong>第四欄</strong>
								</td>
								<td width="89%" align="left" class="wpsDesc">
									<strong>Competency & Potential Assessment</strong>
									<br/>
									<strong>才能及潛能評核</strong>
								</td>
							</tr>
							<tr>
								<td class="wpsDesc">&nbsp;</td>
							</tr>
							<tr align="center">
								<td colspan="2" align="left">
									<table>
										<tr valign="top">
											<td>(1)</td>
											<td>
												Please refer to the competency profile and relevant behavioural indicators for different ranks attached. Where the appraisee is acting in a higher post, he/she should be assessed against the requirements at the higher rank.
												<br/>請參考附件所列各職級的才能綱要及相關的表現指標。如受評人正署理較高職位，評核時應參照所署理職位的才能綱要。
												<br/>
												<br/>Indicate your assessment of the appraisee's competency achievement using the following scale:
												<br/>請按以下評級評核受評人的才能表現:
											</td>
										</tr>
										<tr>
											<td></td>
											<td>
									<table width="100%"  border="0" cellpadding="0">
										<tr align="center">
											<td width="19%" align="left" valign="top">
												<strong>Level 5 - Outstanding<br>第五級 - 優</strong>
											</td>
											<td align="left">
												Consistently excelled in all competency requirements of current rank. Demonstrated 	strong potential for next higher rank.
												<br/>
												才能表現優異，能勝任高一級的職位。
											</td>
										</tr>
										<tr align="center">
											<td align="left" valign="top">
												<strong>Level 4 - Very Effective<br>第四級 - 良</strong>
											</td>
											<td align="left">
												Met and occasionally exceeded competency requirements of current rank. Demonstrated some potential for next higher rank.
												<br/>
												才能表現良好，達到並偶爾超越應有水平，略具擔任高一級職位的潛能。
											</td>
										</tr>
										<tr align="center">
											<td align="left" valign="top">
												<strong>Level 3 - Effective<br>第三級 - 常</strong>
											</td>
											<td align="left">
						 						Generally met overall requirements of current rank, but not yet competent for next higher rank.
												<br/>
												才能表現達到應有水平，但未能擔任高一級的職位。
											</td>
										</tr>
										<tr align="center">
											<td align="left" valign="top">
												<strong>Level 2 - Below Expectation<br>第二級 - 差</strong>
											</td>
											<td align="left">
												Partially met competency requirements of current rank.  Some aspects need to be further developed for current rank. 
												<br/>
												才能表現只有部份能達到應有水平，某些方面須予改善。
											</td>
										</tr>
										<tr align="left">
											<td valign="top">
												<strong>Level 1 - Poor<br>第一級 - 劣</strong>
											</td>
											<td>
												Basic requirements of current rank not met.  Significant improvement needed.
												<br>
												才能表現未達應有水平，各方面均須大大改善。
											</td>
										</tr>
									</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
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
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src="${ctx}/images/dot.gif">
					</td>
					<td width="100%" valign="top"  nowrap>
						<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
							<tr>
								<td dir="ltr" valign="top">
									<table width="100%" border="0" cellpadding="0">
										<tr valign="middle" align="center" classname="wpsPortletTitle" bgcolor="#A7E6F8">
											<td width="90%" rowspan="2" width="80%">
													Core Competencies
												  <br/>核心才能
												  <br/>(List the core competency dimensions in the table below)
												  <br/>(請於下列空格內列出各才能項目)												
											</td>
											<td  width="10%" colspan="6">
												Rating 評級
												<br/>(Please select
												<br/>appropriate boxes
												<br/>請選擇適當方格)		
												<table width="100%" border="0" cellpadding="0">
													<tr align="center" classname="wpsPortletTitle" bgcolor="#A7E6F8">
														<td valign="middle" style="width:17%">5</td>
														<td valign="middle" style="width:17%">4</td>
														<td valign="middle" style="width:17%">3</td>
														<td valign="middle" style="width:17%">2</td>
														<td valign="middle" style="width:17%">1</td>
														<td valign="middle" style="width:15%">N/A</td>
													</tr>
												</table>					
											</td>
										</tr>
										<tr></tr>
										<c:forEach items="${dto.partB4Info.ccInfoList}" var="item" varStatus="status">
										<tr valign="middle" classname="wpsPortletText" <c:if test="${status.index%2!=0}">style="background-color: #DEF8FF;"</c:if>>
											<td classname="wpsPortletTitle" >
											<!-- ha_hr_ea_core_competency.COMPETENCY" -->
											${item.competency}
											
											<form:hidden path="partB4Info.ccInfoList[${status.index}].seqNo"/>
											<form:hidden path="partB4Info.ccInfoList[${status.index}].competencyId"/>
											<form:hidden path="partB4Info.ccInfoList[${status.index}].reportId"/>
											</td>
											<td >
												<table width="100%" border="0" cellpadding="0">
													<tr align="center" classname="wpsPortletTitle" >
													<!-- ha_hr_ea_core_competency.RATING -->
													<!-- added on 20180115 each role should only able to edit their own part-->
													
													<%--
													<c:choose>
    												<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        												<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="5" disabled="true"/></td>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="4" disabled="true"/></td>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="3" disabled="true"/></td>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="2" disabled="true"/></td>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="1" disabled="true"/></td>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="0" disabled="true"/></td>
    												</c:when>    
   													<c:otherwise>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="5"/></td>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="4"/></td>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="3"/></td>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="2"/></td>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="1"/></td>
														<td valign="middle" ><form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="0"/></td>
    												</c:otherwise>
													</c:choose> --%>
													
													<!-- added custom radio button on 20211110 by pccw -->
													<c:choose>
    												<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        												<td valign="middle" >
	        												<label class = "container">
	        													<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="5" disabled="true"/>
	        													<span class = "checkmark"></span>
	        												</label>
        												</td>
        												<td valign="middle" >
	        												<label class = "container">
	        													<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="4" disabled="true"/>
	        													<span class = "checkmark"></span>
	        												</label>
        												</td>
        												<td valign="middle" >
	        												<label class = "container">
	        													<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="3" disabled="true"/>
	        													<span class = "checkmark"></span>
	        												</label>
        												</td>
        												<td valign="middle" >
	        												<label class = "container">
	        													<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="2" disabled="true"/>
	        													<span class = "checkmark"></span>
	        												</label>
        												</td>
        												<td valign="middle" >
	        												<label class = "container">
	        													<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="1" disabled="true"/>
	        													<span class = "checkmark"></span>
	        												</label>
        												</td>
        												<td valign="middle" >
	        												<label class = "container">
	        													<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="0" disabled="true"/>
	        													<span class = "checkmark"></span>
	        												</label>
        												</td>
    												</c:when>    
   													<c:otherwise>
														<td valign="middle" >
															<label class = "container">
															<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="5"/>
															<span class = "checkmark"></span>
	        												</label>
														</td>
														<td valign="middle" >
															<label class = "container">
															<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="4"/>
															<span class = "checkmark"></span>
	        												</label>
														</td>
														<td valign="middle" >
															<label class = "container">
															<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="3"/>
															<span class = "checkmark"></span>
	        												</label>
														</td>
														<td valign="middle" >
															<label class = "container">
															<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="2"/>
															<span class = "checkmark"></span>
	        												</label>
														</td>
														<td valign="middle" >
															<label class = "container">
															<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="1"/>
															<span class = "checkmark"></span>
	        												</label>
														</td>
														<td valign="middle" >
															<label class = "container">
															<form:radiobutton path="partB4Info.ccInfoList[${status.index}].rating" value="0"/>
															<span class = "checkmark"></span>
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
          			<td bgcolor="#CFD9E5" width="1" height="12">
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
			<table widht="100%" border="0" cellpadding="0">
				<tr>
			  		<td valign="top">
			  			(2)
			  		</td>
					<td>
						Please rate the appraisee's potential for further advancement / development.
						<br/>
						請評估受評人是否具備晉升/發展潛能。
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<table border="0"  cellpadding="0" width="100%"  >
							<!-- added on 20180115 each role should only able to edit their own part-->
							
							<%--
							<c:choose>
    						<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        						<tr>
									<td><form:radiobutton path="partB4Info.counterPotentialB4" value="1" disabled="true"/></td>
									<td>Candidate with excellent potential for further advancement<br/>極具晉升潛能</td>
								</tr>
								<tr>
									<td><form:radiobutton path="partB4Info.counterPotentialB4" value="2" disabled="true"/></td>
									<td>Candidate with good potential for further advancement<br/>具備良好晉升潛能</td>
								</tr>
								<tr>
									<td><form:radiobutton path="partB4Info.counterPotentialB4" value="3" disabled="true"/></td>
									<td>Candidate with some potential and can be tested in the next higher rank if opportunities arise<br/>略具晉升潛能，如有機會可在高一級職位加以考驗</td>
								</tr>
								<tr>
									<td><form:radiobutton path="partB4Info.counterPotentialB4" value="4" disabled="true"/></td>
									<td>Potential yet to be developed<br/>暫未具備晉升潛能</td>
								</tr>
    						</c:when>    
   							<c:otherwise>
								<tr>
									<td><form:radiobutton path="partB4Info.counterPotentialB4" value="1"/></td>
									<td>Candidate with excellent potential for further advancement<br/>極具晉升潛能</td>
								</tr>
								<tr>
									<td><form:radiobutton path="partB4Info.counterPotentialB4" value="2"/></td>
									<td>Candidate with good potential for further advancement<br/>具備良好晉升潛能</td>
								</tr>
								<tr>
									<td><form:radiobutton path="partB4Info.counterPotentialB4" value="3"/></td>
									<td>Candidate with some potential and can be tested in the next higher rank if opportunities arise<br/>略具晉升潛能，如有機會可在高一級職位加以考驗</td>
								</tr>
								<tr>
									<td><form:radiobutton path="partB4Info.counterPotentialB4" value="4"/></td>
									<td>Potential yet to be developed<br/>暫未具備晉升潛能</td>
								</tr>
    						</c:otherwise>
							</c:choose> --%>
							
							<!-- added custom radio button on 20211110 by pccw -->
							<c:choose>
    						<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        						<tr>
									<td>
										<label class = "container">
											<form:radiobutton path="partB4Info.counterPotentialB4" value="1" disabled="true"/>
											<span class = "checkmark"></span>
       									</label>
									</td>
									<td>Candidate with excellent potential for further advancement<br/>極具晉升潛能</td>
								</tr>
								<tr>
									<td>
										<label class = "container">
											<form:radiobutton path="partB4Info.counterPotentialB4" value="2" disabled="true"/>
											<span class = "checkmark"></span>
       									</label>
       								</td>
									<td>Candidate with good potential for further advancement<br/>具備良好晉升潛能</td>
								</tr>
								<tr>
									<td>
										<label class = "container">
											<form:radiobutton path="partB4Info.counterPotentialB4" value="3" disabled="true"/>
											<span class = "checkmark"></span>
       									</label>
									</td>
									<td>Candidate with some potential and can be tested in the next higher rank if opportunities arise<br/>略具晉升潛能，如有機會可在高一級職位加以考驗</td>
								</tr>
								<tr>
									<td>
										<label class = "container">
											<form:radiobutton path="partB4Info.counterPotentialB4" value="4" disabled="true"/>
											<span class = "checkmark"></span>
       									</label>
									</td>
									<td>Potential yet to be developed<br/>暫未具備晉升潛能</td>
								</tr>
    						</c:when>    
   							<c:otherwise>
								<tr>
									<td>
										<label class = "container">
											<form:radiobutton path="partB4Info.counterPotentialB4" value="1"/>
											<span class = "checkmark"></span>
       									</label>
									</td>
									<td>Candidate with excellent potential for further advancement<br/>極具晉升潛能</td>
								</tr>
								<tr>
									<td>
										<label class = "container">
											<form:radiobutton path="partB4Info.counterPotentialB4" value="2"/>
											<span class = "checkmark"></span>
       									</label>
									</td>
									<td>Candidate with good potential for further advancement<br/>具備良好晉升潛能</td>
								</tr>
								<tr>
									<td>
										<label class = "container">
											<form:radiobutton path="partB4Info.counterPotentialB4" value="3"/>
											<span class = "checkmark"></span>
       									</label>
									</td>
									<td>Candidate with some potential and can be tested in the next higher rank if opportunities arise<br/>略具晉升潛能，如有機會可在高一級職位加以考驗</td>
								</tr>
								<tr>
									<td>
										<label class = "container">
											<form:radiobutton path="partB4Info.counterPotentialB4" value="4"/>
											<span class = "checkmark"></span>
       									</label>
									</td>
									<td>Potential yet to be developed<br/>暫未具備晉升潛能</td>
								</tr>
    						</c:otherwise>
							</c:choose>
							<!-- end added custom radio button on 20211110 by pccw -->
							
							<!-- end added on 20180115 each role should only able to edit their own part-->	
						</table>
					<!-- ha_hr_ea_contract.APPRAISE_POTENTIAL">  -->
						
					</td>
				</tr>
			</table>

		</td>
	</tr>
</table>