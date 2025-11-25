<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="java.lang.String" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0">
				<tr>
					<td width="13%"  class="wpsDesc">
						<strong>Part IV</strong>
						<br/>
						<strong>第四部分</strong>
					</td>
					<td width="87%"  class="wpsDesc">
						<strong>Recommendation by Countersigning Officer</strong>
						<br>
						<strong>加簽人員填寫</strong>
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
			<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0" class="wpsPortletBody">
				<tr>
					<td dir="ltr" valign="top">
						Please indicate your recommendation on whether the appraisee should be granted a merit payment and the annual salary adjustment, subject to his meeting the eligibility criteria, by selecting the appropriate option. Please refer to the administrative guidelines for the eligibility criteria and principles to be considered before making recommendation.
						<br/>
						如受評人符合有關給予工作表現獎金及每年薪金調整的資格，請選取適當的建議，表明是否建議給予受評人工作表現獎金及每年薪金調整。作出建議前請參考行政指引所列的資格及原則。
					</td>
				</tr>
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
												<!-- ha_hr_ea_memo_details.PERF_BONUS_CO  -->
												<%--
												<form:radiobutton path="${dto}.perfBonus" value="1" class="perfBonusP4"/>Recommended 建議給予
												<form:radiobutton path="${dto}.perfBonus" value="2" class="perfBonusP4"/>Not Recommended 不建議給予 --%>
												
												<!-- added custom radio button on 20211110 by pccw -->
												<label class = "container">
													<form:radiobutton path="${dto}.perfBonus" value="1" class="perfBonusP4"/>Recommended 建議給予
													<span class = "checkmark"></span>
        										</label>
        										<label class = "container">
													<form:radiobutton path="${dto}.perfBonus" value="2" class="perfBonusP4"/>Not Recommended 不建議給予
													<span class = "checkmark"></span>
        										</label>
												<form:hidden path="${dto}.bonusDimmed" id="bonusDimmedP4"/> 
				                      		<!-- added custom radio button on 20211110 by pccw -->
				                      		
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
												<!-- ha_hr_ea_memo_details.SALARY_ADJ_CO  -->
												<%--
												<form:radiobutton path="${dto}.salaryAdj" value="1" class="salaryAdjP4" />Recommended 建議給予
												<form:radiobutton path="${dto}.salaryAdj" value="2" class="salaryAdjP4" />Not Recommended 不建議給予 --%>
	                      						
	                      						<!-- added custom radio button on 20211110 by pccw -->
												<label class = "container">
													<form:radiobutton path="${dto}.salaryAdj" value="1" class="salaryAdjP4" />Recommended 建議給予
													<span class = "checkmark"></span>
        										</label>
        										<label class = "container">
													<form:radiobutton path="${dto}.salaryAdj" value="2" class="salaryAdjP4" />Not Recommended 不建議給予
	                      							<span class = "checkmark"></span>
        										</label>
	                      						<form:hidden path="${dto}.salaryDimmed" id="salaryDimmedP4" />
	                      						<!-- end added custom radio button on 20211110 by pccw --> 
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
		</td>
	</tr>
</table>
<script>
if("Y"==$("#bonusDimmedP4").val()){
	$(".perfBonusP4").attr("disabled",false);
}else{
	$(".perfBonusP4").attr("disabled","disabled");
}
if("Y"==$("#salaryDimmedP4").val()){
	$(".salaryAdjP4").attr("disabled",false);
}else{
	$(".salaryAdjP4").attr("disabled","disabled");
}
</script>