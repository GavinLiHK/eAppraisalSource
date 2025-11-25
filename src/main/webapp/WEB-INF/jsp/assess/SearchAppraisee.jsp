<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<html>
<head>
<script type="text/javascript">
	$( document ).ready(function() {
	    var numberOfErrors = $('#numberOfErrors').val();
	    
	    	if(numberOfErrors != null && numberOfErrors != ''){
	    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text="Are you sure to delete the system parameter?"/>');
	    	}
	});
    
    function cancel(){
        location.href =getJunction('/ea/')+"${ctx}/assess/ListOutstandingReport.do";
    }
</script>
</head>
<body>
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<!-- Title Header  -->
<form:form id="pageForm" action="${ctx}/assess/SearchAppraisee.do" modelAttribute="searchAppraiseeDto" method="post">
<form:hidden id="isSearch" path="isSearch"/>
<form:hidden id="mode" path="mode"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Performance Appraisal Report Maintenance > Search Appraisee
	         
	        </td>
	      </tr>
	      <tr>
	        <td>
	        <!--
	        Following part was created to use modelAttribute on 20/11/2021
	        
	        Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
	        	the Spring framework was required to upgrade from version 4 to version 5.
	        
	        The commandName tag is deprecated since Spring framework 4.3, the modelAttribute tag replaces it in Spring 5.
	        
	        Modified on 20/10/2021
	        -->
	        <common:mvcErrorTable modelAttribute="searchAppraiseeDto"/>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>

<!-- First block -->
 <table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='${ctx}/images/dot.gif'></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
									Search Appraisees </td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
				</tr>
				 <!-- portlet body -->
				
					<tr height="100%">
						<td bgcolor="#CFD9E5" height="100%" width="1"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
						<td width="100%" valign="top">
							<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
								<tr>
									<td dir="ltr" valign="top">
					  <table width="100%"  border="0" cellpadding="0">
						
						<!-- #1 Year  -->
 						
 						<tr>
 						  <td width="28%" class="wpsLabelText">
                           	Year *
 						  </td>
 						  <td width="72%">
                              <input type="text" id="year"  name="year" value="${searchAppraiseeDto.year}" classname="editableinputfield" size="4" maxlength="4"></input>
 						  </td>
 						</tr>
						<!-- #2 Rank  -->
						<tr>
						  <td class="wpsLabelText">
                          	Rank
						  </td>
						  <td>
                             <input type="text" id="rank"  name="rank" value="${searchAppraiseeDto.rank}" classname="editableinputfield" size="30" maxlength="30"></input>
						  </td>
					    </tr>


                        <!-- #3 Post unit  -->
						<tr>
						  <td class="wpsLabelText">
                          	Post Unit
						  </td>
						  <td>
                             <input type="text" id="postUnit" name="postUnit" value="${searchAppraiseeDto.postUnit}" classname="editableinputfield" size="30" maxlength="30"></input>
						  </td>
					    </tr>

						
						<!-- #4 Employee number  -->
						<tr>
						  <td class="wpsLabelText">
                          	Employee Number/Name
						  </td>
						  <td>
                             <common:employeeSearch empNumId="employeeNumber" empNum="employeeNumber" empNameId="employeeName" empName="employeeName"/>
						  	
						  </td>
					    </tr>


						<!-- #5 Check Date  -->
						<tr>
						  <td class="wpsLabelText">
                          	Track Date *
						  </td>
						  <td>
						  
						   <common:commonDate id="trackDate" name="trackDate" />             
						  </td>
					    </tr>




						
						<!-- #6 Report generated ?  -->
						<tr>
						  <td class="wpsLabelText">
                          	Report Generated
						  </td>
						  <td>
						  <form:select id="reportGenerated" path="reportGenerated" classname="editableinputfield">
						  	<form:option value="" >Please select ...</form:option>
						    <form:option value="Y" >Yes</form:option>
						    <form:option value="N" >No</form:option>
						  </form:select>	
						  </td>
					    </tr>
						
						<!-- #7 Appraisal period start  -->
						<tr>
						  <td class="wpsLabelText">
                          	Appraisal Period Start *
						  </td>
						  <td>
						  	<common:commonDate id="appraisalPeriodStart" name="appraisalPeriodStart"  />
                            
						  </td>
					    </tr>

						
						<!-- #8 Appraisal period end  -->
						
						<tr>
						  <td class="wpsLabelText">
                          	Appraisal Period End *
						  </td>
						  <td>
						  	<common:commonDate id="appraisalPeriodEnd" name="appraisalPeriodEnd"/>
                           
						  </td>
					    </tr>
						
						<!-- #9 Workflow template name  -->
						<tr>
						  <td class="wpsLabelText">
                          	Workflow Template Name *
						  </td>
						  <td>
						  
						  <form:select id="wfTemplate" path="wfTemplateId" classname="wpsLabelText">
						  	<form:option value=""></form:option>
						  	<c:forEach var="wfTemp" items="${workflowTemList}">
							<form:option value="${wfTemp.workflowId}">${wfTemp.workflowTemplateName}</form:option>
							</c:forEach>
						  </form:select>
						  </td>
						</tr>
					  </table>
									</td>
								</tr>
							</table>
						</td>
						<td bgcolor="#CFD9E5" width="1"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
					</tr>
				
				 <!-- bottom border -->
				<tr height="1">
					<td bgcolor="#CFD9E5" width="100%" height="1" colspan="3">
				</tr>
			</table>
		</td>
	</tr>
</table>

  <br>
  <!-- Buttons -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td align="right">
				<input type="submit" name="btnSearch" value="Search"></input>
				<input type="button" name="btnCancel" value="Cancel" onclick="cancel();"></input>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>

</form:form>
</body>
</html>
