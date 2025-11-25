<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="com.hkha.ea.common.Constants" %>
<%@ attribute name="dto" type="java.lang.String" required="true"%>
<%@ attribute name="officerName" type="java.lang.String" required="true"%>
<%@ attribute name="signatureFlag" type="java.lang.String" required="false"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
							  <td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
                                  Signature Info 簽署人資料
							  </td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr height="100%">
					<td bgcolor="#CFD9E5" height="100%" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" valign="top">
						<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
							<tr>
								<td dir="ltr" valign="top">
									<table width="100%"  border="0" cellpadding="0">
										<tr>
										    <td width="18%">
										    	Name of Appraisee
										    </td>
											<td width="22%">
												<!-- edited on 20170224 user able to update appraisee's name -->
												<!-- <form:input id="nameOfAppraisee" path="${dto}.nameOfAppraisee" name="nameOfAppraisee" size="30" classname="inputfield"  readonly="true" /> -->
												<form:input id="nameOfAppraisee" path="${dto}.nameOfAppraisee" name="nameOfAppraisee" size="30" classname="inputfield" />
											</td>
											<c:choose>
												<c:when test="${signatureFlag=='MEMO'}">
													<td width="13%"></td>
													<td width="27%"></td>
													<td width="7%"></td>
													<td width="13%" nowrap></td>
												</c:when>
												<c:otherwise>
												   <td width="13%">
										    	Rank/Post
										    	<br/>
												職級/職位
											</td>
											<td width="27%">
												<form:input id="appraiseeRankPost " path="${dto}.appraiseeRankPost" name="appraiseeRankPost" maxlength="40" size="40" classname="inputfield"  />
											</td>
										    <td width="7%">
										    	Date
												<br/>
												日期
										    </td>
											<td width="13%" nowrap>
												<common:commonDate id="${dto}appraiseeSignDate" name="${dto}.appraiseeSignDate" />	
												
											</td>
												</c:otherwise>
											</c:choose>
										 
										</tr>
										<tr>
										    <td width="18%">
										    	${officerName}
										    </td>
											<td width="22%">
												<!-- edited on 20170224 user able to update officer's name -->
												<!-- <form:input path="${dto}.officerName"  id="perfAoName"  size="30" classname="inputfield"  readonly="true" /> -->
												<form:input path="${dto}.officerName"  id="perfAoName"  size="30" classname="inputfield" /> 
											</td>
										    <td width="13%">
										    	Rank/Post
										    	<br/>
										    	職級/職位
										    </td>
											<td width="27%">
												<form:input path="${dto}.officerRank" id="perfAoRank"   maxlength="40" size="40" classname="inputfield"  />
											</td>
										    <td width="7%">
										    	Date
										    	<br/>
										    	日期
										    </td>
											<td width="13%" nowrap>
												<common:commonDate id="${dto}officerDate" name="${dto}.officerDate" />	
											</td>
											
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
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