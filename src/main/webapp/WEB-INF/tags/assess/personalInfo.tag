<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="com.hkha.ea.dto.assess.AssessAppraisalDTO" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0">
				<tr>
					<td colspan="2" class="wpsDesc">
						<strong>This Performance Management and Development System consists of <em>two</em> parts.</strong>
						<br/>
						<strong> 工作表現管理及員工發展制度共分<em>兩</em>個部分</strong>。
						</td>
				</tr>
				<tr>
					<td width="13%" class="wpsDesc">
						<strong>Part A</strong>
						<br/>
						<strong>甲部</strong>
					</td>
					<td width="87%" class="wpsDesc">
						<strong>Performance Planning</strong>
						<em> (to be completed at the beginning of the appraisal period)</em>
						<br>
						<strong> 制定工作表現目標 </strong><em> ( 於評核期開始時填寫 )</em>
					</td>
				</tr>
				<tr>
					<td class="wpsDesc">
						<strong>Part B</strong>
						<br/>
						<strong>乙部</strong>
					</td>
					<td class="wpsDesc">
						<strong>Year-End Review</strong>
						<em> (to be completed at the end of the appraisal period)</em>
						<br>
						<strong> 年終檢討 </strong><em> ( 於評核期完結時填寫 )</em>
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
                            		Personal Particulars <strong> 個人資料</strong>
									<br/>
									(<em>to be completed by the Grade Management Section</em>)
									<em>(由職系管理組填寫)</em>
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

									<table width="100%" border="0" cellpadding="0">
										<tr>
											<td colspan="4">
												<strong>About the appraisee 受評人資料</strong>
											</td>
										</tr>
										<tr><td>&nbsp;</td></tr>
										<tr>
											<td colspan="2">
												Name in full : (English) 英文全名
											</td>
											<td>
												(Chinese) 中文全名 :
											</td>
											<td width="20%">
											</td>
										</tr>
										<tr>
											<td colspan="2">
											<!-- ha_hr_ea_report.NAME -->
												<form:input path="personalInfo.englishName" name="englishName" size="40" maxlength="60" font="wpsPortletTitle" classname="inputfield" readonly="true"  />
											</td>
											<td>
											<!-- ha_hr_ea_report.CHINESE_NAME -->
												<form:input path="personalInfo.chineseName" name="chineseName" size="40" maxlength="60" font="wpsPortletTitle"  classname="inputfield" readonly="true"  />
											</td>
										</tr>
										<tr>
											<td colspan="2">
												Rank on first appointment 最初受聘職級 :
											</td>
											<td>
												Date of appointment 受聘日期 :
											</td>
										</tr>
										<tr>
											<td colspan="2">
											<!-- ha_hr_ea_report.FIRST_APPOINTMENT_RANK -->
												<form:input path="personalInfo.rankOnFirstAppoint" name="rankOnFirstAppoint" size="40" maxlength="60" font="wpsPortletTitle"  classname="inputfield" readonly="true"  />
											</td>
											<td>
											<!-- ha_hr_ea_report.DATE_OF_APP_FIRST_RANK  -->
												<common:commonDate id="dateOnFirstAppoint" name="personalInfo.dateOnFirstAppoint" readonly="Y"/>
												
											</td>
										</tr>
										<tr>
											<td colspan="2">
												Present substantive rank 現時實任職級 :
											</td>
											<td>
												Date of appointment 受聘日期 :
											</td>
										</tr>
										<tr>
											<td colspan="2">
											<!-- ha_hr_ea_report.SUBSTANTIVE_RANK -->
												<form:input path="personalInfo.rankOnPresentAppoint" name="rankOnPresentAppoint" size="40" maxlength="60" font="wpsPortletTitle" classname="inputfield" readonly="true" />
											</td>
											<td>
											<!-- ha_hr_ea_report.DATE_OF_APP_SUB_RANK  -->
												<common:commonDate id="dateOnPresentAppoint" name="personalInfo.dateOnPresentAppoint" readonly="Y"/>
												
											</td>
										</tr>
										<tr>
											<td>
												Post Unit 現時調派職位 :
											</td>
											<td>
												Telephone 電話 :
											</td>
											<td>
												Date of posting 調派日期 :
											</td>
										</tr>
										<tr>
											<td>
											<!-- ha_hr_ea_report.PRESENT_POST -->
												<form:input path="personalInfo.presentPost" name="presentPost" size="40" maxlength="40" font="wpsPortletTitle"  classname="inputfield" />
											</td>
											<td>
											<!-- ha_hr_ea_report.TELEPHONE -->
												<form:input path="personalInfo.telephone" name="telephone" size="20" maxlength="20" font="wpsPortletTitle"  classname="inputfield" />
											</td>
											<td>
											<!-- ha_hr_ea_report.DATE_OF_POSTING -->
												<common:commonDate id="dateOfPosting" name="personalInfo.dateOfPosting" readonly="Y"/>
												
											</td>
										</tr>
										<tr>
											<td colspan="2">
												Division/section 科 / 組 :
											</td>
											<td>
												Current contract period 合約期 :
											</td>
										</tr>
										<tr>
											<td colspan="2">
											<!-- ha_hr_ea_report.DIVISION -->
												<form:input path="personalInfo.divisionSection" name="divisionSection" size="40" maxlength="40" font="wpsPortletTitle" classname="inputfield" />
											</td>
											<td>
											<!-- ha_hr_ea_report.CURRENT_CONTRACT_START_DATE -->
											<common:commonDate id="currentContractPeriodStart" name="personalInfo.currentContractPeriodStart" readonly="Y"/>					
												to 至
												<!-- ha_hr_ea_report.CURRENT_CONTRACT_END_DATE -->
												<common:commonDate id="currentContractPeriodEnd" name="personalInfo.currentContractPeriodEnd" readonly="Y"/>		

											</td>
										</tr>
										<tr><td>&nbsp;</td></tr>
										<tr>
											<td colspan="4">
												<strong>Commendations/disciplinary offences during appraisal period</strong>
												<br/>
												評核期內曾獲嘉獎/曾有違反紀律行為
											</td>
										</tr>
										<tr>
											<td colspan="4">
											<!-- ha_hr_ea_contract.COMMENDATION -->
												<form:textarea id="commendation" path="personalInfo.commendation" name="personalInfo.commendation" maxlength="196" wrap="soft" style="width:100%" rows="5" font="wpsPortletTitle" classname="inputfield" />
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
									<em> (To be completed by the appraisee) ( 由受評人填寫 )</em>
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr>
					<td bgcolor="#CFD9E5" height="100%" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
					<td>
						<table width="100%" border="0" cellpadding="0">
							<tr>
								<td>
									<br/>
									<strong>Acting Appointments (regardless of duration, with dates) during the Appraisal Period</strong>
									<br/>
									<strong> 評核期內曾署理的職位</strong>
									(
									<strong> 不論任期長短，並須註明日期</strong>
									)
								</td>
							</tr>
							<tr>
								<td align="right">
								<!--ha_hr_ea_contract.ACTING_APPOINT -->
									<form:textarea id="actingAppointment" path="personalInfo.actingAppointment" name="personalInfo.actingAppointment" wrap="soft" style="width:100%" maxlength="637" rows="5" font="wpsPortletTitle"  classname="inputfield" />
									
								</td>
							</tr>
							<tr>
								<td>
									<strong>Training Courses Attended during the Appraisal Period</strong>
									<br/>
									<strong> 評核期內曾參加的培訓課程</strong>
								</td>
							</tr>
							<tr>
								<td align="right">
								<!-- ha_hr_ea_contract.TRAINING_COURSE -->
									<form:textarea path="personalInfo.trainingInReviewPeriod" id="trainingInReviewPeriod" name="personalInfo.trainingInReviewPeriod" wrap="soft" style="width:100%" maxlength="1274" rows="5" font="wpsPortletTitle" classname="inputfield" />
									
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
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