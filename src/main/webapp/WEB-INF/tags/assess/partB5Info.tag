<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="com.hkha.ea.dto.assess.AssessAppraisalDTO" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%"  border="0" cellpadding="0">
				<tr align="center">
					<td align="left" class="wpsDesc">
						<strong>Section 5</strong>
						<br/>
						<strong>第五欄</strong>
					</td>
					<td width="89%" align="left" class="wpsDesc">
						<strong>Countersigning Officer's Assessment</strong>
						<br/>
						<strong>加簽人員的評核</strong>
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
					<td valign="top">
						(1)
					</td>
					<td>
						Please indicate below how well do you know the work of the appraisee.
						<br/>
						請略述你對受評人工作的認識
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
					<!-- ha_hr_ea_contract.WORK_WELL -->
						<!-- <form:textarea path="partB5Info.workWell" id="workWell" maxlength="686" wrap="soft" style="width:100%" rows="5" font="wpsPortletTitle" classname="inputfield" /> -->
						<!-- <salmon:input type="submit" name="btnSaveWorkWell" value="Save"></salmon:input> -->
						<!-- added on 20180115 each role should only able to edit their own part-->
						<c:choose>
    					<c:when test="${dto.reportUserRole.currentUserRoleStatus!='CO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        					<form:textarea path="partB5Info.workWell" id="workWell" maxlength="990" wrap="soft" style="width:100%" rows="5" font="wpsPortletTitle" classname="inputfield" disabled="true"/> 
    					</c:when>    
   						<c:otherwise>
							<form:textarea path="partB5Info.workWell" id="workWell" maxlength="990" wrap="soft" style="width:100%" rows="5" font="wpsPortletTitle" classname="inputfield" /> 
    					</c:otherwise>
						</c:choose>
						<!-- end added on 20180115 each role should only able to edit their own part-->	
					</td>
				</tr>
				<tr>
					<td><BR><BR></td>
				</tr>
				<tr>
					<td rowspan="2" valign="top">
						(2)
					</td>
					<td>
						<strong>Overall Performance</strong>
						<strong>整體表現</strong>
					</td>
				</tr>
				<tr>
					<td>
						Please give an assessment of the appraisee's overall performance during the appraisal period. Comment on the appraisee's <strong>competencies, training and development needs, postability and potential</strong>. If your assessment is significantly different from that of the Appraising Officer, please give reason(s).
						<br/>
						請評核受評人於評核期內的整體表現，並評述受評人的<strong>才能、培訓及發展需要</strong>、是否適宜調配到其他職位，以及具備的潛能。如你的評核與評核人員的評核有重大差異，請列明原因。
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
					<!-- ha_hr_ea_contract.OVERALL_PERFORMANCE -->
						<!--<salmon:input type="submit" name="btnSaveOverallPerformance" value="Save"></salmon:input>-->
						<!-- added on 20180115 each role should only able to edit their own part-->
						<c:choose>
    					<c:when test="${dto.reportUserRole.currentUserRoleStatus!='CO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        					<form:textarea type="textarea" path="partB5Info.overallPerformance" id="overallPerformance" maxlength="2940" wrap="soft" style="width:100%" rows="6" font="wpsPortletTitle" classname="inputfield"  disabled="true"/>
    					</c:when>    
   						<c:otherwise>
							<form:textarea type="textarea" path="partB5Info.overallPerformance" id="overallPerformance" maxlength="2940" wrap="soft" style="width:100%" rows="6" font="wpsPortletTitle" classname="inputfield" />
    					</c:otherwise>
						</c:choose>
						<!-- end added on 20180115 each role should only able to edit their own part-->	
					</td>
				</tr>
				<tr>
					<td><BR><BR></td>
				</tr>
				<tr align="center">
					<td align="left" valign="top" rowspan="2">
						(3)
					</td>
					<td align="left">
						<strong>Recommendation on Granting of Merit Payment & Annual Salary Adjustment</strong>
						<br/>
						<strong>建議是否給予工作表現獎金及每年薪金調整</strong>
						<BR/><BR/>
						Please indicate your recommendation on whether the appraisee should be granted a merit payment and the annual salary adjustment, subject to his meeting the eligibility criteria, by selecting the appropriate option. Please refer to the administrative guidelines for the eligibility criteria and principles to be considered before making recommendation.
						<BR/>
						如受評人符合有關給予工作表現獎金及每年薪金調整的資格，請選取適當的建議，表明是否建議給予受評人工作表現獎金及每年薪金調整。作出建議前請參考行政指引所列的資格及原則。
					</td>
				</tr>
			</table>
			<br/>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src="${ctx}/images/dot.gif">
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
									Merit Payment 工作表現獎金
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
				</tr>
				<tr>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
					<td>
						<table width="100%" border="0" cellpadding="3">
							<tr>
								<td width="100%" align="center">
								<!-- ha_hr_ea_contract.PERFORMANCE_BONUS_CO  -->
									<%--
									<form:radiobutton path="partB5Info.performanceBonus" value="1" class="coBonusB5"/>Recommended 建議給予
									<form:radiobutton path="partB5Info.performanceBonus" value="2" class="coBonusB5"/>Not Recommended 不建議給予 --%>
									
									<!-- added custom radio button on 20211110 by pccw -->
									<label class = "container">
										<form:radiobutton path="partB5Info.performanceBonus" value="1" class="coBonusB5"/>Recommended 建議給予
										<span class = "checkmark"></span>
        							</label>
        							<label class = "container">
										<form:radiobutton path="partB5Info.performanceBonus" value="2" class="coBonusB5"/>Not Recommended 不建議給予
										<span class = "checkmark"></span>
      							  	</label>
      								<!-- end added custom radio button on 20211110 by pccw -->
								
	                      		<!--	<salmon:text name="coPerformanceBonusDimmed" text="Dimmed" textlocalekey="" /> -->
	                      		<form:hidden path="partB5Info.bonusDimmed" id="bonusDimmedB5"/>
								</td>
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
			<br/>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src="${ctx}/images/dot.gif">
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
							  <td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
                                  Annual Salary Adjustment 每年薪金調整</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
				</tr>
				<tr>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
					<td>
						<table width="100%" border="0" cellpadding="3">
							<tr>
								<td width="100%" align="center">
								<!--ha_hr_ea_contract.SALARY_ADJUSTMENT_CO-->
								<%--
								<form:radiobutton path="partB5Info.salaryAdjustment" value="1"  class="coSalaryB5"/>Recommended 建議給予
								<form:radiobutton path="partB5Info.salaryAdjustment" value="2"  class="coSalaryB5"/>Not Recommended 不建議給予 --%>
								
								<!-- added custom radio button on 20211110 by pccw -->
								<label class = "container">
									<form:radiobutton path="partB5Info.salaryAdjustment" value="1"  class="coSalaryB5"/>Recommended 建議給予
									<span class = "checkmark"></span>
        						</label>
        						<label class = "container">
									<form:radiobutton path="partB5Info.salaryAdjustment" value="2"  class="coSalaryB5"/>Not Recommended 不建議給予
									<span class = "checkmark"></span>
        						</label>	
        						<!-- end added custom radio button on 20211110 by pccw -->								
									
	                      		<!-- <salmon:text name="coSalaryAdjustmentDimmed" text="Dimmed" textlocalekey="" /> -->	
	                      		<form:hidden path="partB5Info.salaryDimmed" id="salaryDimmedB5"/>
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
			<br/>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr>
					<td width="100%" valign="top">
						<table widht="100%" border="0" cellpadding="0">
							<tr>
						  		<td valign="top">
						  			(4)
						  		</td>
								<td>
									<strong>Potential Assessment 潛能評估</strong>
									<br/>
									Please rate the appraisee's potential for further advancement / development.
									<br/>
									請評估受評人是否具備晉升/發展潛能。
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
								<td>
						<table border="0"  cellpadding="0" width="100%"  >
						<!-- ha_hr_ea_contract.COUNTER_POTENTIAL -->
						<!-- added on 20180115 each role should only able to edit their own part-->
						<%--
						<c:choose>
    					<c:when test="${dto.reportUserRole.currentUserRoleStatus!='CO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        					<tr>
								<td><form:radiobutton path="partB5Info.potential" value="1" disabled="true"/></td>
								<td>Candidate with excellent potential for further advancement<br/>極具晉升潛能</td>
							</tr>
							<tr>
								<td><form:radiobutton path="partB5Info.potential" value="2" disabled="true"/></td>
								<td>Candidate with good potential for further advancement<br/>具備良好晉升潛能</td>
							</tr>
							<tr>
								<td><form:radiobutton path="partB5Info.potential" value="3" disabled="true"/></td>
								<td>Candidate with some potential and can be tested in the next higher rank if opportunities arise<br/>略具晉升潛能，如有機會可在高一級職位加以考驗</td>
							</tr>
							<tr>
								<td><form:radiobutton path="partB5Info.potential" value="4" disabled="true"/></td>
								<td>Potential yet to be developed<br/>暫未具備晉升潛能</td>
							</tr>
    					</c:when>    
   						<c:otherwise>
							<tr>
								<td><form:radiobutton path="partB5Info.potential" value="1"/></td>
								<td>Candidate with excellent potential for further advancement<br/>極具晉升潛能</td>
							</tr>
							<tr>
								<td><form:radiobutton path="partB5Info.potential" value="2"/></td>
								<td>Candidate with good potential for further advancement<br/>具備良好晉升潛能</td>
							</tr>
							<tr>
								<td><form:radiobutton path="partB5Info.potential" value="3"/></td>
								<td>Candidate with some potential and can be tested in the next higher rank if opportunities arise<br/>略具晉升潛能，如有機會可在高一級職位加以考驗</td>
							</tr>
							<tr>
								<td><form:radiobutton path="partB5Info.potential" value="4"/></td>
								<td>Potential yet to be developed<br/>暫未具備晉升潛能</td>
							</tr>
    					</c:otherwise>
						</c:choose> --%>

						<!-- added custom radio button on 20211110 by pccw -->
						<c:choose>
    					<c:when test="${dto.reportUserRole.currentUserRoleStatus!='CO' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        					<tr>
								<td>
									<label class = "container">
										<form:radiobutton path="partB5Info.potential" value="1" disabled="true"/>
										<span class = "checkmark"></span>
        							</label>
								</td>
								<td>Candidate with excellent potential for further advancement<br/>極具晉升潛能</td>
							</tr>
							<tr>
								<td>
									<label class = "container">
										<form:radiobutton path="partB5Info.potential" value="2" disabled="true"/>
										<span class = "checkmark"></span>
        							</label>
								</td>
								<td>Candidate with good potential for further advancement<br/>具備良好晉升潛能</td>
							</tr>
							<tr>
								<td>
									<label class = "container">
										<form:radiobutton path="partB5Info.potential" value="3" disabled="true"/>
										<span class = "checkmark"></span>
        							</label>
								</td>
								<td>Candidate with some potential and can be tested in the next higher rank if opportunities arise<br/>略具晉升潛能，如有機會可在高一級職位加以考驗</td>
							</tr>
							<tr>
								<td>
									<label class = "container">
										<form:radiobutton path="partB5Info.potential" value="4" disabled="true"/>
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
										<form:radiobutton path="partB5Info.potential" value="1"/>
										<span class = "checkmark"></span>
        							</label>
								</td>
								<td>Candidate with excellent potential for further advancement<br/>極具晉升潛能</td>
							</tr>
							<tr>
								<td>
									<label class = "container">
										<form:radiobutton path="partB5Info.potential" value="2"/>
										<span class = "checkmark"></span>
        							</label>
								</td>
								<td>Candidate with good potential for further advancement<br/>具備良好晉升潛能</td>
							</tr>
							<tr>
								<td>
									<label class = "container">
										<form:radiobutton path="partB5Info.potential" value="3"/>
										<span class = "checkmark"></span>
        							</label>
								</td>
								<td>Candidate with some potential and can be tested in the next higher rank if opportunities arise<br/>略具晉升潛能，如有機會可在高一級職位加以考驗</td>
							</tr>
							<tr>
								<td>
									<label class = "container">
										<form:radiobutton path="partB5Info.potential" value="4"/>
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
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>	
		</td>
	</tr>
</table>

<script>
if("Y"==$("#bonusDimmedB5").val()){
	$(".coBonusB5").attr("disabled",false);
}else{
	$(".coBonusB5").attr("disabled","disabled");
}

if("Y"==$("#salaryDimmedB5").val()){
	$(".coSalaryB5").attr("disabled",false);
}else{
	$(".coSalaryB5").attr("disabled","disabled");
}

<!-- added on 20180115 each role should only able to edit their own part-->
var role = '${assessAppraisalDTO.reportUserRole.currentUserRoleStatus}';
if (role!='CO' && role!='SU' && role!='GM'){
	$(".coBonusB5").attr("disabled",true);
	$(".coSalaryB5").attr("disabled",true);
}else{
	$(".coBonusB5").attr("disabled",false);
	$(".coSalaryB5").attr("disabled",false);
}
<!-- end added on 20180115 each role should only able to edit their own part-->

</script>