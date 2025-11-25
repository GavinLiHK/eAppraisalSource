<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="java.lang.String" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0">
				<tr>
					<td width="13%"  class="wpsDesc">
						<strong>Part III</strong>
						<br/>
						<strong>第三部分</strong>
					</td>
					<td width="87%"  class="wpsDesc">
						<strong>to be completed by Appraising Officer</strong>
						<br>
						<strong>由評核人員填寫</strong>
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
						<img alt="" width="1" height="8" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
                            		A. Overall Rating <strong>整體工作表現</strong>
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%">
						<table width="100%" border="0" cellpadding="5" cellspacing="0">
							<tr>
								<td width="100%" align="center">
								<!-- ha_hr_ea_memo_details.OVERALL_PERF --> <!--overallPerf-->
									<!-- added custom radio button on 20211110 by pccw -->
									<%--
									<form:radiobutton path="${dto}.overallPerf"  value="A"  />A
									<form:radiobutton path="${dto}.overallPerf"  value="B"  />B
									<form:radiobutton path="${dto}.overallPerf"  value="C"  />C
									<form:radiobutton path="${dto}.overallPerf"  value="D"  />D
									<form:radiobutton path="${dto}.overallPerf"  value="E"  />E
									--%>
									<label class = "container">
										<form:radiobutton path="${dto}.overallPerf"  value="A"  />A
										<span class = "checkmark"></span>
									</label>
									<label class = "container">
										<form:radiobutton path="${dto}.overallPerf"  value="B"  />B
										<span class = "checkmark"></span>
									</label>
									<label class = "container">
										<form:radiobutton path="${dto}.overallPerf"  value="C"  />C
										<span class = "checkmark"></span>
									</label>
									<label class = "container">
										<form:radiobutton path="${dto}.overallPerf"  value="D"  />D
										<span class = "checkmark"></span>
									</label>
									<label class = "container">
										<form:radiobutton path="${dto}.overallPerf"  value="E"  />E								
										<span class = "checkmark"></span>
									</label>
									<!-- end added custom radio button on 20211110 by pccw -->							
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" height="100%" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="100%" colspan="3">
					</td>
				</tr>
			</table>
			<br/>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
                            		B. Comments on Overall Performance <strong>整體工作表現評語</strong>
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" valign="top">
						<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
							<tr>
								<td dir="ltr" valign="top" align="right">
									<!-- ha_hr_ea_memo_details.OVERALL_COMMENT -->
									<form:textarea  path="${dto}.overallComment" id="overallComment" wrap="soft" style="width:100%" maxlength="2940" rows="10" font="wpsPortletTitle"   classname="inputfield" />
									<!--<salmon:input type="submit" name="btnSaveOverallComment" value="Save"></salmon:input>  -->
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" height="100%" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="100%" colspan="3">
					</td>
				</tr>
			</table>
			<br/>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
								
									<span style="margin-left: 0em">C. Recommendation on
										Granting of Merit Payment & Annual Salary Adjustment</span></br>
									<span style="margin-left: 1em"><strong>建議是否給予工作表現獎金及每年薪金調整</strong></span> 
									
									<!-- <table border="1" cellpadding="0" cellspacing="0">
										<tr valign="top">	
										 <td rowspan="2" class="wpsPortletTitle">
											 <strong>C.&nbsp;</strong> 
											 </td> 		
											
											 <td class="wpsPortletTitle" >
											 <strong>Recommendation on Granting of Merit Payment & Annual Salary Adjustment</strong></td> 
										</tr>
										<tr>
											<td class="wpsPortletTitle">
												<strong>建議是否給予工作表現獎金及每年薪金調整</strong>
											</td>
										</tr>
									</table> -->
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" valign="top">
						<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
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
															<!-- ha_hr_ea_memo_details.PERF_BONUS_AO  -->
															<!-- added custom radio button on 20211110 by pccw -->
															<%--
															<form:radiobutton path="${dto}.perfBonus" value="1" class="perfBonusP3"/>Recommended 建議給予
															<form:radiobutton path="${dto}.perfBonus" value="2" class="perfBonusP3"/>Not Recommended 不建議給予 --%>
															
															<label class = "container">
																<form:radiobutton path="${dto}.perfBonus" value="1" class="perfBonusP3"/>Recommended 建議給予
																<span class = "checkmark"></span>
	       												 	</label>
	       												 	<label class = "container">
																<form:radiobutton path="${dto}.perfBonus" value="2" class="perfBonusP3"/>Not Recommended 不建議給予
								                      			<span class = "checkmark"></span>
	       												 	</label>
								                      		<!--end added custom radio button on 20211110 by pccw -->															
							                      			<form:hidden path="${dto}.bonusDimmed" id="bonusDimmedP3"/> 
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
															<!-- ha_hr_ea_memo_details.SALARY_ADJ_AO  -->
															<!-- added custom radio button on 20211110 by pccw -->
															<%--
															<form:radiobutton path="${dto}.salaryAdj" value="1" class="salaryAdjP3"/>Recommended 建議給予
															<form:radiobutton path="${dto}.salaryAdj" value="2" class="salaryAdjP3"/>Not Recommended 不建議給予
															--%>
															<label class = "container">
																<form:radiobutton path="${dto}.salaryAdj" value="1" class="salaryAdjP3"/>Recommended 建議給予
																<span class = "checkmark"></span>
	       												 	</label>
	       												 	<label class = "container">
																<form:radiobutton path="${dto}.salaryAdj" value="2" class="salaryAdjP3"/>Not Recommended 不建議給予
																<span class = "checkmark"></span>
	       												 	</label>
	       												 	<!-- end added custom radio button on 20211110 by pccw -->															
	                      								<form:hidden path="${dto}.salaryDimmed" id="salaryDimmedP3" /> 
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
					<td bgcolor="#CFD9E5" height="100%" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="100%" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script>
if("Y"==$("#bonusDimmedP3").val()){	
	$(".perfBonusP3").attr("disabled",false);
}else{
	$(".perfBonusP3").attr("disabled","disabled");
}
if("Y"==$("#salaryDimmedP3").val()){
	$(".salaryAdjP3").attr("disabled",false);
}else{
	$(".salaryAdjP3").attr("disabled","disabled");
	
}

</script>