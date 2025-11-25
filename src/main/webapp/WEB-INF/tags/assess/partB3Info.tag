<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="com.hkha.ea.dto.assess.AssessAppraisalDTO" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%"  border="0" cellpadding="0">
				<tr align="center">
					<td align="left" class="wpsDesc">
						<strong>Section 3</strong>
						<br/>
						<strong>第三欄</strong>
					</td>
					<td width="89%" align="left" class="wpsDesc">
						<strong>Recommendation on Granting of Merit Payment & Annual Salary Adjustment</strong>
						<br/>
						<strong>建議是否給予工作表現獎金及每年薪金調整</strong>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="2" align="left" class="wpsDesc">
						Please indicate your recommendation on whether the appraisee should be granted a merit payment and the annual salary adjustment, subject to his meeting the eligibility criteria, by selecting the appropriate option. Please refer to the administrative guidelines for the eligibility criteria and principles to be considered before making recommendation.
						<br/>
						如受評人符合有關給予工作表現獎金及每年薪金調整的資格，請選取適當的建議，表明是否建議給予受評人工作表現獎金及每年薪金調整。作出建議前請參考行政指引所列的資格及原則。
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
								<td width="100%" valign="middle" nowrap class="wpsPortletTitle">
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
									<%--
									<form:radiobutton path="partB3Info.aoPerformanceBonus" value="1" class="aoBonusB3"/>Recommended 建議給予
									<form:radiobutton path="partB3Info.aoPerformanceBonus" value="2" class="aoBonusB3"/>Not Recommended 不建議給予 --%>
									
									<!-- added custom radio button on 20211110 by pccw -->
									<label class = "container">
										<form:radiobutton path="partB3Info.aoPerformanceBonus" value="1" class="aoBonusB3"/>Recommended 建議給予
										<span class = "checkmark"></span>
        							</label>
        							<label class = "container">
										<form:radiobutton path="partB3Info.aoPerformanceBonus" value="2" class="aoBonusB3"/>Not Recommended 不建議給予
										<span class = "checkmark"></span>
        							</label>
									<!-- end added custom radio button on 20211110 by pccw -->
									
									<!-- ha_hr_ea_contract.PERFORMANCE_BONUS_AO"> 
									
	                      			<salmon:text name="aoPerformanceBonusDimmed" text="Dimmed" textlocalekey="" />-->
	                      			<form:hidden path="partB3Info.bonusDimmed" id="bonusDimmedB3" />
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif">
					</td>
				</tr>
				<tr hieght="1">
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
                                	Annual Salary Adjustment 每年薪金調整
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
									<%--
									<form:radiobutton path="partB3Info.aoSalaryAdjustment" value="1"  class="aoSalaryB3"/>Recommended 建議給予
									<form:radiobutton path="partB3Info.aoSalaryAdjustment" value="2"  class="aoSalaryB3"/>Not Recommended 不建議給予 --%>
								
									<!-- added custom radio button on 20211110 by pccw -->
									<label class = "container">	
										<form:radiobutton path="partB3Info.aoSalaryAdjustment" value="1"  class="aoSalaryB3"/>Recommended 建議給予
										<span class = "checkmark"></span>
									</label>
									<label class = "container">
										<form:radiobutton path="partB3Info.aoSalaryAdjustment" value="2"  class="aoSalaryB3"/>Not Recommended 不建議給予
										<span class = "checkmark"></span>
									</label>
									<!-- end added custom radio button on 20211110 by pccw -->
																	
									<!-- ha_hr_ea_contract.SALARY_ADJUSTMENT_AO">
									
	                      			<salmon:text name="aoSalaryAdjustmentDimmed" text="Dimmed" textlocalekey="" />-->
	                      			<form:hidden path="partB3Info.salaryDimmed" id="salaryDimmedB3" />
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
<script>
if("Y"==$("#bonusDimmedB3").val()){
	$(".aoBonusB3").attr("disabled",false);
}else{
	$(".aoBonusB3").attr("disabled","disabled");
}

if("Y"==$("#salaryDimmedB3").val()){
	$(".aoSalaryB3").attr("disabled",false);
}else{
	$(".aoSalaryB3").attr("disabled","disabled");
}

<!-- added on 20180115 each role should only able to edit their own part-->
var role = '${assessAppraisalDTO.reportUserRole.currentUserRoleStatus}';
if (role!='AO' && role!='SU' && role!='GM'){
	$(".aoBonusB3").attr("disabled",true);
	$(".aoSalaryB3").attr("disabled",true);
}else{
	$(".aoBonusB3").attr("disabled",false);
	$(".aoSalaryB3").attr("disabled",false);
}
<!-- end added on 20180115 each role should only able to edit their own part-->
</script>