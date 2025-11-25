<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="com.hkha.ea.common.Constants" %>
<%@ attribute name="dto" type="java.lang.String" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" height="15" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
									Appraisee Information
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr>
					<td border="0" bgcolor="#CFD9E5" width="1">
						<img alt="" width="1" height="8" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" valign="top">
						<table width="100%" border="0" cellpadding="0">
							<tr>
								<td width="20%">
									Name of Appraisee<br/>受評人姓名
								</td>
								<td width="30%" colspan="3">
								<!-- ha_hr_ea_report.NAME -->
									<form:input path="${dto}.appraiseeName" id="appraiseeName" name ="appraiseeName" readonly="true" size="40" maxlength="60" font="wpsPortletTitle" classname="inputfield" />
								</td>
								<td rowspan="2" width="5%">
									&nbsp;
								</td>
								<td widht="20%">
									Rank<br/>職級
								</td>
								<td width="25%" colspan="3">
								<!-- ha_hr_ea_report.SUBSTANTIVE_RANK -->
									<form:input path="${dto}.rank" id="rank"  name="rank" readonly="true" size="25" classname="inputfield"  /> 
								</td>
							</tr>
							<tr>
								<td>
									Employee Number<br/>員工編號
								</td>
								<td colspan="3">
								<!-- ha_hr_ea_report.EMPLOYEE_NUMBER -->
									<form:input path="${dto}.employeeNumber" id="employeeNumber" name="employeeNumber" readonly="true" font="wpsPortletTitle" classname="inputfield" />
								</td>
								<td>
									Appraisal Period<br/>評核期
								</td>
								<td nowrap>
								<!-- ha_hr_ea_report.COMMENCE_DATE -->
								<!-- start edited on 20170201 -->
								<c:choose>
									<c:when test="${assessAppraisalDTO.reportUserRole.currentUserRoleStatus=='GM' or assessAppraisalDTO.reportUserRole.currentUserRoleStatus=='SU'}">
										<common:commonDate id="appraisalPeriodStartAI" name="${dto}.appraisalPeriodStart" />
									</c:when>
									<c:otherwise>
										<common:commonDate id="appraisalPeriodStartAI" name="${dto}.appraisalPeriodStart" readonly="Y"/>
									</c:otherwise>
								</c:choose>
								<!-- <common:commonDate id="appraisalPeriodStartAI" name="${dto}.appraisalPeriodStart" /> -->
									
								<!-- end edited on 20170201 -->								
								</td>
								<td align="center" nowrap>
									to 至
								</td>
								<td nowrap>
								<!-- ha_hr_ea_report.END_DATE -->
								<!-- start edited on 20170201 -->
								<c:choose>
									<c:when test="${assessAppraisalDTO.reportUserRole.currentUserRoleStatus=='GM' or assessAppraisalDTO.reportUserRole.currentUserRoleStatus=='SU'}">
										<common:commonDate id="appraisalPeriodEndAI" name="${dto}.appraisalPeriodEnd" />
									</c:when>
									<c:otherwise>
										<common:commonDate id="appraisalPeriodEndAI" name="${dto}.appraisalPeriodEnd" readonly="Y"/>
									</c:otherwise>
								</c:choose>
									<!-- <common:commonDate id="appraisalPeriodEndAI" name="${dto}.appraisalPeriodEnd" /> -->
								<!-- end edited on 20170201 -->								
								</td>
							</tr>
						</table>
					</td>
					<td border="0" bgcolor="#CFD9E5" width="1">
						<img alt="" width="1" height="1" src='${ctx}/images/dot.gif'>
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