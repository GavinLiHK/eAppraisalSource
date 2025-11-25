<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<form:form name="pageForm" modelAttribute="userGroupEnquiryDTO" action="${ctx}/security/searchUserGroupEnquiry.do" method="post">


<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">User Group Maintenance > User Group Enquiry
	          <!-- <salmon:text name="txtHeading" text="User Group Maintenance > User Group Enquiry" textlocalekey="" font="PageHeadingFont"/> -->
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
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src="${ctx}/image/dot.gif"></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">User Group Enquiry
			                        <!-- <salmon:text name="txtSubHeading" text="User Group Enquiry" textlocalekey=""/> -->
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td>
				</tr>
				 <!-- portlet body -->
				
				<tr height="100%">
					<td bgcolor="#CFD9E5" height="100%" width="1"><img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td>
					<td width="100%" valign="top">
						<table width="100%"  border="0" cellpadding="5">
							<tr>
								<td width="28%" class="wpsPortletText">User Group Name
									<!-- <salmon:text name="capserGroupName" text="User Group Name" textlocalekey=""/> -->
								</td> 
								<td width="72%" class="wpsPortletText">
									<input type="text" name="userGroupName" value="${userGroupEnquiryDTO.userGroupName}" classname="inputfield" size="30" maxlength="30"></input>
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1"><img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td>
				</tr>
				
				 <!-- bottom border -->
				<tr height="1">
					<td bgcolor="#CFD9E5" height="1" width="100%" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
  
 <!-- Buttons -->
<table width="100%" border="0" cellpadding="5" cellspacing="0">
  <tr>
    <td align="right">
		<input type="submit" name="btnSearch" value="Search"></input>
		<input type="button" name="btnAdd" value="Add" onclick="window.location.href = getJunction('/ea/')+'${ctx}/security/addUserGroupDetail.do'"></input>
		<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href = getJunction('/ea/')+'${ctx}/assess/ListOutstandingReport.do'"></input>
    </td>
  </tr>
</table>

<!----------- Search Result --------->
<div align="left"><common:jmesaScript action="${ctx}/security/searchUserGroupEnquiry.do" />
		${html }
</div>
</form:form>
</body>
</html>