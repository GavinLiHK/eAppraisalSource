<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<html>
<head>
</head>
<body>
<!-- Title Header  -->
<form:form id="pageForm" action="${ctx}/assess/SearchAppraisee.do" modelAttribute="searchAppraiseeDto" method="post">
<form:hidden id="batchNameJmesa" path="batchNameJmesa"/>
<form:hidden id="rankJmesa" path="rankJmesa"/>
<form:hidden id="postUnitJmesa" path="postUnitJmesa"/>
<form:hidden id="postTitleJmesa" path="postTitleJmesa"/>
<form:hidden id="mode" path="mode"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	          Performance Appraisal Report Maintenance > Search Batch
	         
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
									Searching Criteria </td>
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
                           	Batch Name
 						  </td>
 						  <td width="72%">
                              <input type="text" id="batchName" name="batchName" value="${searchAppraiseeDto.batchName}" classname="wpsLabelText" size="30" maxlength="30"></input>
 						  </td>
 						</tr>
						<!-- #2 Rank  -->
						<tr>
						  <td class="wpsLabelText">
                          	Rank
						  </td>
						  <td>
                             <input type="text" id="rank" name="rank" value="${searchAppraiseeDto.rank}" classname="wpsLabelText" size="30" maxlength="30"></input>
						  </td>
					    </tr>


                        <!-- #3 Post unit  -->
						<tr>
						  <td class="wpsLabelText">
                          	Post Unit
						  </td>
						  <td>
                             <input type="text" id="postUnit" name="postUnit" value="${searchAppraiseeDto.postUnit}" classname="wpsLabelText" size="30" maxlength="30"></input>
						  </td>
					    </tr>

						
						<!-- #4 Post Title  -->
						<tr>
						  <td class="wpsLabelText">
                          	Post Title
						  </td>
						  <td>
                             <input type="text" id="postTitle" name="postTitle" value="${searchAppraiseeDto.postTitle}" classname="wpsLabelText" size="30" maxlength="30"></input>
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
				<input type="button" name="btnSearchBatch" value="Search Batch" onclick="searchBatch()"></input>
				<input type="button" name="btnSearchReport" value="Search Report" onclick="searchReport()"></input>
				<input type="button" name="btnCancel" value="Cancel" onclick="cancel();"></input>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>

</form:form>
<script type="text/javascript">
function searchBatch() {
	 var form = document.getElementById('pageForm');
	 $("#batchNameJmesa").val($("#batchName").val());
	 $("#rankJmesa").val($("#rank").val());
	 $("#postUnitJmesa").val($("#postUnit").val());
	 $("#postTitleJmesa").val($("#postTitle").val());
		form.action = getJunction('/ea/')+"${ctx}/assess/searchBatch.do";
		form.target = "_self";
		form.submit(); 	
	
}

function searchReport() {
	var mode = '<%=Constants.PAGE_MODIFY_BATCH%>';
	 $("#batchNameJmesa").val($("#batchName").val());
	 $("#rankJmesa").val($("#rank").val());
	 $("#postUnitJmesa").val($("#postUnit").val());
	 $("#postTitleJmesa").val($("#postTitle").val());
	 $("#mode").val(mode);
	 var form = document.getElementById('pageForm');
		form.action = getJunction('/ea/')+"${ctx}/assess/SearchAppraisee.do";
		form.target = "_self";
		form.submit(); 	
	
}

function cancel(){
	location.href =getJunction('/ea/')+"${ctx}/assess/ListOutstandingReport.do";
}
</script>
</body>
</html>
