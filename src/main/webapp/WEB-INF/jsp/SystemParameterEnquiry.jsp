<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<body>
<form:form id="pageForm" modelAttribute="systemParameterDTO" action="${ctx}/admin/searchSystemParameter.do" method="post">
<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class ="PageHeadingFont">System Parameter Maintenance > Search System Parameter
	         </td>
	      </tr>
	      <tr>
	        <td font="InformationFont">
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
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='${ctx}/images/dot.gif'></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr bgcolor="#336699">
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">System Parameter Maintenance
								</td>
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
								 <!-- #1 Parameter type-->
									<tr>
									  <td width="28%" class="wpsPortletText">Parameter Name
									  </td> 
									  <td width="72%" class="wpsPortletText">
			                              <form:input id="paramName" path="paramName" classname="inputfield" size="30" maxlength="30" />
									  </td>
									</tr>
									
									
									<!-- #2 Parameter Description -->
									<tr>
									  <td class="wpsPortletText">Parameter Description</td> 
									 <td class="wpsPortletText">
			                              <form:input id="paramDesc" path="paramDesc" size="50" maxlength="100" />
			              			</td>
								    </tr>

								  </table>
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1"><img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif"></td>
				</tr>
				
				 <!-- bottom border -->
				<tr height="1">
					<td bgcolor="#CFD9E5" width="1" height="1"><img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif"></td>
					<td bgcolor="#CFD9E5" height="1"><img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif"></td>
					<td bgcolor="#CFD9E5" width="1" height="1"><img alt="" border="0" width="1" height="1" src="${ctx}/images/dot.gif"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
  
  <!-- Buttons -->
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="middle">
            <table width="100%" border="0" cellpadding="5" cellspacing="0">
              <tr>
                <td align="right">
					<input type="submit" name="btnSearch" value="Search"></input>
					<input type="button" name="btnAdd" value="Add" onclick="window.location.href = getJunction('/ea/')+'${ctx}/admin/addSystemParameter.do'"></input>
					<input type="button" name="btnCancel" value="Cancel" onclick="window.location.href = getJunction('/ea/')+'${ctx}/assess/ListOutstandingReport.do'"></input>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>  
      
	    <div align="left"><common:jmesaScript action="" />
		${html }
		</div>
      </form:form>
      </body>
</html>