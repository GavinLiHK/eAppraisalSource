<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag import="com.hkha.ea.common.Constants" %>
<%@ attribute name="dto" type="java.lang.String" required="true"%>
<%@ attribute name="officerLabel" type="java.lang.String" required="true"%>
<%@ attribute name="hiddenTel" type="java.lang.String" required="false"%>

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
                                  Signature Info 簽署人資料
							  </td>
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
					<td width="100%">
						<table width="100%" border="0" cellpadding="0">
							<tr>
							    <td width="20%">
							   		 ${officerLabel }
							    	
								</td>
								<td width="30%">
									<!-- ha_hr_ea_contract.COMP_AO_NAME -->
									<!-- edited on 20170224 user able to update officer's name -->
									<!-- <form:input path="${dto}.officerName" size="30" classname="inputfield"  readonly="true"  /> -->
									<form:input path="${dto}.officerName" size="30" classname="inputfield"  />
								</td>
							    <td width="20%">
									Date
									<br/>
									日期
								</td>
								<td width="30%" nowrap>
									<common:commonDate id="${dto}officerDate" name="${dto}.officerDate" />	<!-- ha_hr_ea_contract.COMP_AO_DATE -->
								
								</td>
							</tr>
							<tr>
							    <td>
							    	Rank/Post
							    	<br/>
							    	職級/職位
								</td>
								<td>
								<!--ha_hr_ea_contract.COMP_AO_RANK -->
									<form:input path="${dto}.officerRank" id="compAoRank" size="40" classname="inputfield"  />
								</td>
								<c:choose>
									<c:when test="${hiddenTel=='Y'}">
										<td></td><td></td>
									</c:when>
									<c:otherwise>
										 <td>
							    			Telephone
							    			<br/>
											電話
							    		</td>
										<td>
											<!--ha_hr_ea_contract.COMP_AO_TEL -->
										<form:input path="${dto}.tel" id="compAoTel" size="20" classname="inputfield" maxlength="20" /> 
										</td>
									</c:otherwise>
								</c:choose>
							   
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