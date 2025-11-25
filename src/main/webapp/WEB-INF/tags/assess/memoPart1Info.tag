<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="java.lang.String" required="true"%>


<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0">
				<tr>
					<td width="13%" class="wpsDesc">
						<strong>Part I</strong>
						<br/>
						<strong>第一部分</strong>
					</td>
					<td width="87%" class="wpsDesc">
						<strong>to be completed by Grade Management</strong>
						<br>
						<strong>由職系管理組填寫</strong>
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
											<td colspan="2">
												Name in full (English) 英文全名
											</td>
											<td>
												(Chinese) 中文全名
											</td>
											<td width="20%">
											</td>
										</tr>
										<tr>
											<td colspan="2">
											
											<!--ha_hr_ea_report.NAME  -->
												<form:input path="${dto}.englishName" id="englishName" name="englishName" size="40" maxlength="40"  readonly="true" font="wpsPortletTitle" classname="inputfield" />
												
											</td>
											<td>
											<!--ha_hr_ea_report.CHINESE_NAME  -->
												<form:input path="${dto}.chineseName" id="chineseName" name="chineseName" size="40" maxlength="40"  readonly="true" font="wpsPortletTitle" classname="inputfield" />
												
											</td>
										</tr>
										<tr>
											<td colspan="2">
												Substantive Rank 實任職級
											</td>
											<td>
												Post 職位
											</td>
										</tr>
										<tr>
											<td colspan="2">
											<!--ha_hr_ea_report.SUBSTANTIVE_RANK  -->
												<form:input path="${dto}.rankOnPresentAppoint" id="rankOnPresentAppoint" name="rankOnPresentAppoint" size="40" maxlength="60"  readonly="true" font="wpsPortletTitle" classname="inputfield" />
											</td>
											<td>
											<!--ha_hr_ea_report.PRESENT_POST  -->
												<form:input path="${dto}.presentPost" id="presentPost" name="presentPost" size="40" maxlength="40"   font="wpsPortletTitle" classname="inputfield" />
											</td>
										</tr>
										<tr>
											<td colspan="2">
												Division/section 科 / 組
											</td>
											<td>
												Appraisal Period 評核期
											</td>
										</tr>
										<tr>
											<td colspan="2">
											<!-- ha_hr_ea_report.DIVISION  -->
												<form:input path="${dto}.divisionSection" name="divisionSection" size="40" maxlength="40" font="wpsPortletTitle" classname="inputfield" />
												
											</td>
											<td nowrap>
												<!-- ha_hr_ea_report.COMMENCE_DATE -->
												<!-- start edited on 20170201 -->
												<c:choose>
													<c:when test="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus=='SU'or assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus=='GM'}">
														<common:commonDate id="appraisalPeriodStart" name="${dto}.appraisalPeriodStart" />	
													</c:when>
													<c:otherwise>
														<common:commonDate id="appraisalPeriodStart" name="${dto}.appraisalPeriodStart" readonly="Y"/>	
													</c:otherwise>
												</c:choose>
												<!-- <common:commonDate id="appraisalPeriodStart" name="${dto}.appraisalPeriodStart" /> -->																																						
												<!-- end edited on 20170201 -->
												to 至
												<!-- ha_hr_ea_report.END_DATE -->
												<!-- start edited on 20170201 -->
												<c:choose>
													<c:when test="${assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus=='SU'or assessAppraisalMemoDTO.reportUserRole.currentUserRoleStatus=='GM'}">
														<common:commonDate id="appraisalPeriodEnd" name="${dto}.appraisalPeriodEnd" />
													</c:when>
													<c:otherwise>
														<common:commonDate id="appraisalPeriodEnd" name="${dto}.appraisalPeriodEnd" readonly="Y"/>
													</c:otherwise>
												</c:choose>
												<!-- <common:commonDate id="appraisalPeriodEnd" name="${dto}.appraisalPeriodEnd" readonly="Y"/>	-->
												<!-- end edited on 20170201 -->
												
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