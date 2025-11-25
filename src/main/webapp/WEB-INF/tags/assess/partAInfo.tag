<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="com.hkha.ea.dto.assess.AssessAppraisalDTO" required="true"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
		  <table width="100%"  border="0" cellpadding="0">
			<tr align="center">
			  <td width="100%" class="wpsDesc">
			  	<strong>PART A : PERFORMANCE PLANNING</strong>
		      	<br>
		      	<strong>甲部 : 制定工作表現目標</strong>
		      </td>
		    </tr>
		    <tr>
		    	<td class="wpsDesc">&nbsp;</td>
		    </tr>
			<tr>
			  <td class="wpsDesc">
			  	Describe key objectives with corresponding results to be achieved in the appraisal period. In order that the list is manageable, it should not include more than 8 objectives and should be agreed between the appraisee and the appraising officer at the beginning of the appraisal period.
				<br>
				列述評核期內的主要工作目標及預期取得的成效。所列工作目標應以 8 項為限，並在評核期開始時由評核人員及受評人商定。
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
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" valign="top" nowrap >
					<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
						<tr>
							<td dir="ltr" valign="top">
							<table width="100%" border="0" cellpadding="0">
								<tr valign="middle" align="center" class="wpsPortletTitle" bgcolor="#A7E6F8">
									<td width="25%">
										<strong>Key Objectives</strong> 
										<br/>
										<strong>主要工作目標</strong>	
									</td>
									<td width="65%">
										<strong>Results to be Achieved</strong> 
										<br/>
										<strong>預期取得的成效</strong>	
									</td>
									<td width="10%">
										<strong>Weighting</strong> 
										<br/>
										<strong>比重 %</strong>	
									</td>
								</tr>
							<c:forEach items="${dto.partAInfoList}" var="item" varStatus="status">
								
								<tr align="left" valign="top" class="wpsPortletText" <c:if test="${status.index%2!=0}">style="background-color: #DEF8FF;"</c:if>>
									<td>
									<!-- ha_hr_ea_objectives.KEY_OBJECTIVE  -->
									<!-- added on 20180115 each role should only able to edit their own part-->
									<c:choose>
    								<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AP' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        								<form:textarea path="partAInfoList[${status.index}].keyObjectives" id="${id}keyObjectives${status.index}" name="partAInfoList[${status.index}].keyObjectives" wrap="soft" style="width:100%; white-space:pre-wrap" rows="10" maxlength="440" disabled="true" /> 
    								</c:when>    
   									 <c:otherwise>
										<form:textarea path="partAInfoList[${status.index}].keyObjectives" id="${id}keyObjectives${status.index}" name="partAInfoList[${status.index}].keyObjectives" wrap="soft" style="width:100%; white-space:pre-wrap" rows="10" maxlength="440"/> 
    								</c:otherwise>
									</c:choose>
									<!-- end added on 20180115 each role should only able to edit their own part-->
									</td>
									<td>
									<!-- ha_hr_ea_objectives.RESULTS_ACHIEVED  -->
									<!-- added on 20180115 each role should only able to edit their own part-->
									<c:choose>
    								<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AP' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        								<form:textarea path="partAInfoList[${status.index}].resultAchieved" id="${id}resultAchieved${status.index}" name="partAInfoList[${status.index}].resultAchieved" wrap="soft" style="width:100%; white-space:pre-wrap" rows="10" maxlength="1400" disabled="true"/>
    								</c:when>    
   									 <c:otherwise>
										<form:textarea path="partAInfoList[${status.index}].resultAchieved" id="${id}resultAchieved${status.index}" name="partAInfoList[${status.index}].resultAchieved" wrap="soft" style="width:100%; white-space:pre-wrap" rows="10" maxlength="1400" />
    								</c:otherwise>
									</c:choose>
									<!-- end added on 20180115 each role should only able to edit their own part-->
									</td>
									<td align="center">
										<!-- ha_hr_ea_objectives.WEIGHTING  -->
									<!-- added on 20180115 each role should only able to edit their own part-->
									<c:choose>
    								<c:when test="${dto.reportUserRole.currentUserRoleStatus!='AP' and dto.reportUserRole.currentUserRoleStatus!='GM' and dto.reportUserRole.currentUserRoleStatus!='SU'}">
        								<form:input path="partAInfoList[${status.index}].weighting" id="${id}weighting${status.index}" name="partAInfoList[${status.index}].weighting"  size="7" maxlength="6" style="width:50%" disabled="true"/>	
    								</c:when>    
   									 <c:otherwise>
										<form:input path="partAInfoList[${status.index}].weighting" id="${id}weighting${status.index}" name="partAInfoList[${status.index}].weighting"  size="7" maxlength="6" style="width:50%" />	
    								</c:otherwise>
									</c:choose>
									<!-- end added on 20180115 each role should only able to edit their own part-->	
										<form:hidden path="partAInfoList[${status.index}].seqNo" id="${id}seqNo${status.index}" name="partAInfoList[${status.index}].seqNo"   />		
									</td>
								</tr>
							</c:forEach>
							</table>
							</td>
						</tr>
					</table>						
		  			</td>
          			<td bgcolor="#CFD9E5" width="1" height="12">
          				<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
          			</td>
        		</tr>
        		<tr height="1">
          			<td bgcolor="#CFD9E5" width="1" height="1" colspan="3">
          			</td>
        		</tr>
			</table>
		</td>
	</tr>
</table>