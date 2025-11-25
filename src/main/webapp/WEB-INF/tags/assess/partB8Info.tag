<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="com.hkha.ea.dto.assess.AssessAppraisalDTO" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%"  border="0" cellpadding="0">
				<tr align="center">
					<td align="left" class="wpsDesc">
					<!-- edited on 20170228 -->
						<c:choose>
							<c:when test="${isEORoleChecked}">
								<strong>Section 8</strong>
								<br/>
								<strong>第八欄</strong>
							</c:when>
							<c:otherwise>
								<strong>Section 7</strong>
								<br/>
								<strong>第七欄</strong>
							</c:otherwise>
						</c:choose>	
					<!-- edited on 20170228 -->		
						
					</td>
		      		<td width="89%" align="left" class="wpsDesc">
						<strong>Reviewing Officer's Assessment</strong>
						<br/>
						<strong>覆核人員的評核</strong>
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
						(1)
					</td>
					<td align="left">
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
					<!-- ha_hr_ea_contract.REVIEW_POTENTIAL  -->
						<table border="0"  cellpadding="0" width="100%"  >
							<tr>
								<!-- added custom radio button on 20211110 by pccw -->
								<%--  <td><form:radiobutton path="partB8Info.potential" value="1"/></td> --%>
								<td>								
									<label class = "container">
									<form:radiobutton path="partB8Info.potential" value="1"/>
									<span class = "checkmark"></span>
        							</label>
								</td>
								<!-- end added custom radio button on 20211110 by pccw -->
								<td>Candidate with excellent potential for further advancement<br/>極具晉升潛能</td>
							</tr>
							<tr>
								
								<!-- added custom radio button on 20211110 by pccw -->
								<%-- <td><form:radiobutton path="partB8Info.potential" value="2"/></td> --%>
								<td>
									<label class = "container">
									<form:radiobutton path="partB8Info.potential" value="2"/>
									<span class = "checkmark"></span>
        							</label>
								</td>
								<!-- end added custom radio button on 20211110 by pccw -->
								<td>Candidate with good potential for further advancement<br/>具備良好晉升潛能</td>
							</tr>
							<tr>
								<!-- added custom radio button on 20211110 by pccw -->
								<%-- <td><form:radiobutton path="partB8Info.potential" value="3"/></td> --%>
								<td>
									<label class = "container">
									<form:radiobutton path="partB8Info.potential" value="3"/>
									<span class = "checkmark"></span>
        							</label>
								</td>
								<!-- end added custom radio button on 20211110 by pccw -->
								<td>Candidate with some potential and can be tested in the next higher rank if opportunities arise<br/>略具晉升潛能，如有機會可在高一級職位加以考驗</td>
							</tr>
							<tr>
								<!-- added custom radio button on 20211110 by pccw -->
								<%-- <td><form:radiobutton path="partB8Info.potential" value="4"/></td> --%>
								<td>
									<label class = "container">
									<form:radiobutton path="partB8Info.potential" value="4"/>
									<span class = "checkmark"></span>
        							</label>
								</td>
								<!-- end added custom radio button on 20211110 by pccw -->
								<td>Potential yet to be developed<br/>暫未具備晉升潛能</td>
							</tr>
							
						</table>										
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br/>
<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%"  border="0" cellpadding="0">
				<tr>
					<td valign="top">
						(2)
					</td>
					<td>
						Please comment on the appraisee's <strong>performance and competencies, training and development needs, postability and potential.</strong>
						<br/>
						請評述受評人的工作表現、才能、培訓及發展需要、是否適宜調配到其他職位，以及具備的潛能。
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
					<!-- ha_hr_ea_contract.REVIEW_COMMENT  -->
						<form:textarea path="partB8Info.reviewComment" id="reviewComment" maxlength="1960" wrap="soft" style="width:100%" rows="5" font="wpsPortletTitle"  classname="inputfield" /> 
   					<!--	<salmon:input type="submit" name="btnSaveReviewComment" value="Save"></salmon:input>		-->				  
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
						<strong>Recommendation on Granting of Merit Payment & Annual Salary Adjustment</strong><br>建議是否給予工作表現獎金及每年薪金調整</strong>
						<br/><br/>
						Please indicate your recommendation on whether the appraisee should be granted a merit payment and the annual salary adjustment, subject to his meeting the eligibility criteria, by selecting the appropriate option. Please refer to the administrative guidelines for the eligibility criteria and principles to be considered before making recommendation.
						<br/>
						如受評人符合有關給予工作表現獎金及每年薪金調整的資格，請選取適當的建議，表明是否建議給予受評人工作表現獎金及每年薪金調整。作出建議前請參考行政指引所列的資格及原則。
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br/>
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
                                  Merit Payment 工作表現獎金 </td>
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
									<!-- ha_hr_ea_contract.PERFORMANCE_BONUS_RO -->
									
									<!-- added custom radio button on 20211110 by pccw -->
									<%-- 
									<form:radiobutton path="partB8Info.performanceBonus" value="1" class="roBonusB8"/>Recommended 建議給予
									<form:radiobutton path="partB8Info.performanceBonus" value="2"  class="roBonusB8"/>Not Recommended 不建議給予 --%>																
									<label class = "container">
										<form:radiobutton path="partB8Info.performanceBonus" value="1" class="roBonusB8"/>Recommended 建議給予
										<span class = "checkmark"></span>
        							</label>
									<label class = "container">
										<form:radiobutton path="partB8Info.performanceBonus" value="2"  class="roBonusB8"/>Not Recommended 不建議給予		
										<span class = "checkmark"></span>
        							</label>
									<!-- added custom radio button on 20211110 by pccw -->
									
									<form:hidden path="partB8Info.bonusDimmed" id="bonusDimmedB8"/>
	                      			<!--<salmon:text name="roPerformanceBonusDimmed" text="Dimmed" textlocalekey="" /> -->
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
								<!-- ha_hr_ea_contract.SALARY_ADJUSTMENT_RO -->
									<!-- added custom radio button on 20211110 by pccw -->
									<%-- 
									<form:radiobutton path="partB8Info.salaryAdjustment" value="1" class="roSalaryB8"/>Recommended 建議給予
									<form:radiobutton path="partB8Info.salaryAdjustment" value="2" class="roSalaryB8"/>Not Recommended 不建議給予 --%>
									
									<label class = "container">
										<form:radiobutton path="partB8Info.salaryAdjustment" value="1" class="roSalaryB8"/>Recommended 建議給予
										<span class = "checkmark"></span>
	        						</label>
	        						<label class = "container">
										<form:radiobutton path="partB8Info.salaryAdjustment" value="2" class="roSalaryB8"/>Not Recommended 不建議給予										
		                      			<span class = "checkmark"></span>
	        						</label>
	        						<!-- end added custom radio button on 20211110 by pccw -->
        																
	                      		<!--	<salmon:text name="roSalaryAdjustmentDimmed" text="Dimmed" textlocalekey="" />-->
	                      		<form:hidden path="partB8Info.salaryDimmed" id="salaryDimmedB8"/>
								</td>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
				</tr>
				<tr>
					<td bgcolor="#CFD9E5" width="100%" colspan="2">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script>
if("Y"==$("#bonusDimmedB8").val()){
	$(".roBonusB8").attr("disabled",false);
}else{
	$(".roBonusB8").attr("disabled","disabled");
}

if("Y"==$("#salaryDimmedB8").val()){
	$(".roSalaryB8").attr("disabled",false);
}else{
	$(".roSalaryB8").attr("disabled","disabled");
}

</script>