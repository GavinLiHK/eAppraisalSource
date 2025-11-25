<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<body>
<form:form name="pageForm" modelAttribute="userEnquiryDto" action="${ctx}/security/searchUserEnquiry.do" method="post">
 <!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">User Maintenance > User Enquiry
	        </td>
	      </tr>
	      <tr>
	        <td>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>
 <!------- First block ------->
   <table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='/ea/image/dot.gif'></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">User Maintenance
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src='/ea/image/dot.gif'></td>
				</tr>
				 <!-- portlet body -->
				
				<tr height="100%">
					<td bgcolor="#CFD9E5" height="100%" width="1"><img alt="" border="0" width="1" height="1" src='/ea/image/dot.gif'></td>
					<td width="100%" valign="top">
						<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
							<tr>
								<td dir="ltr" valign="top">
				  
								 
								  <table width="100%"  border="0" cellpadding="0">
								 <!-- #1 User ID -->
									<tr>
									  <td width="28%">User ID
									  </td> 
									  <td width="72%">
			                              <input type="text" name="userId" value="${userEnquiryDto.userId}" classname="inputfield" size="30" maxlength="30"></input>
									  </td>
									</tr>
									
									
									<!-- #2 User Group -->
									<tr>
									  <td>Group Name
									  </td> 
									 <td>
			                             <form:select path="groupId">
			                             	<form:option value=""></form:option>
									 		<c:forEach var="grpName" items="${groupNameList}">
									 		 <form:option value="${grpName.groupId}">${grpName.userGroupName}</form:option>
									 		</c:forEach>
									 	</form:select>
			                             <!-- <input type="select" name="groupName" value="" classname="inputfield" size="40" maxlength="100" /> -->
			              			</td>
								    </tr>

									<!-- #3 Enable -->
									<tr>
									  <td>Enable
									  </td> 
									 <td>
									 	<form:select path="enable">
									 		 <form:option value="">All</form:option>
									 		 <form:option value="Y">Yes</form:option>
									 		 <form:option value="N">No</form:option>
									 	</form:select>
			                             <!-- <input type="select" name="enable" value="" classname="inputfield" size="20" maxlength="100"/> -->
			              			</td>
								    </tr>

								  </table>
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1"><img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td>
				</tr>
				
				 <!-- bottom border -->
				<tr height="1">
					<td bgcolor="#CFD9E5" width="100%" height="1" colspan="3">
<%-- 					<td bgcolor="#CFD9E5" width="1" height="1"><img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td>
					<td bgcolor="#CFD9E5" height="1"><img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td>
					<td bgcolor="#CFD9E5" width="1" height="1"><img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td> --%>
				</tr>
			</table>
		</td>
	</tr>
</table>

	  <!-- Butons -->
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="middle">
            <table width="100%" border="0" cellpadding="5" cellspacing="0">
              <tr>
                <td align="right">
                  <input type="submit" name="btnSearch" value="Search"></input>
				  <input type="button" name="btnAdd" value="Add" onclick="window.location.href = getJunction('/ea/')+'${ctx}/security/addUserDetail.do'"></input>
				  <input type="button" name="btnCancel" value="Cancel" onclick="window.location.href = getJunction('/ea/')+'${ctx}/assess/ListOutstandingReport.do'"></input>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>


<!-------  Search result ------>
<div align="left"><common:jmesaScript action="${ctx}/security/searchUserEnquiry.do" />
		${html }
</div>
</form:form>
</body>
</html>