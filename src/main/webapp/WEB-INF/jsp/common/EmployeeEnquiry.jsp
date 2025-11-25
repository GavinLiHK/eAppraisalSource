<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script>
function closeWindows() {
	window.close();
	//window.opener.focus();
}
function returnPar(numVal,fullVal){	
	$("#${pidNum}",window.opener.document).val(numVal);
	$("#${pidName}",window.opener.document).val(fullVal);
    window.close();
}
function cancelAndReset(){
	$("#${pidNum}",window.opener.document).val("");
	$("#${pidName}",window.opener.document).val("");
	 window.close();
}

</script>
</head>
<body>
<form:form name="pageForm" action="${ctx}/common/EmployeeEnquiry.do?pidNum=${pidNum}&pidName=${pidName}&pidEmpName=${pidEmpName}&initPage='search'" method="post" modelAttribute="employeeEnquiryDTO">
<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td align="right">	          
			  <table>
				<tr>
					<td nowrap>
					<a name="closeLink" href="javascript:if(confirm('Close this window?')) closeWindows();" >
					Close
					</a>
					</td>
				</tr>
			  </table>			  
	        </td>
	      </tr>
	      <tr>
	        <td height="50" valign="bottom">
	        <FONT face="Verdana, Arial, Helvetica, sans-serif" STYLE="FONT-SIZE:large;FONT-WEIGHT:bold;LINE-WEIGHT:120%;PADDING-LEFT:5px;" COLOR="#333333"><B>Search Employee Number</B></FONT>
	         
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
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='${ctx}/images/dot.gif'></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
			                        Searching Criteria
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
								 <!-- #1 Rank-->
									<tr>
									  <td width="28%" class="wpsLabelText">
			                              Rank
									  </td> 
									  <td width="72%" class="wpsPortletText">
			                              <form:input id="rank" path="rank" classname="editableinputfield" size="30" maxlength="30" />
									  </td>
									</tr>
									
									
									<!-- #2 Post Unit -->
									<tr>
									  <td class="wpsLabelText">
			                              Post Unit
									  </td> 
									 <td class="wpsPortletText">
			                              <form:input id="postUnit" path="postUnit" classname="editableinputfield" size="30" maxlength="30" />
			              			</td>
								    </tr>

									<!-- #2 Post Title -->
									<tr>
									  <td class="wpsLabelText">
			                             Post Title
									  </td> 
									 <td class="wpsPortletText">
			                              <form:input id="postTitle" path="postTitle" classname="editableinputfield" size="30" maxlength="30" />
			              			</td>
								    </tr>

									<!-- #3 First name -->
									<tr>
									  <td class="wpsLabelText">
			                            First Name
									  </td> 
									 <td class="wpsPortletText">
			                              <form:input id="firstName" path="firstName" classname="editableinputfield" size="30" maxlength="30" />
			              			</td>
								    </tr>

									<!-- #4 Last name -->
									<tr>
									  <td class="wpsLabelText">
			                             Last Name
									  </td> 
									 <td class="wpsPortletText">
			                              <form:input id="lastName" path="lastName" classname="editableinputfield" size="30" maxlength="30"  />
			              			</td>
								    </tr>
									<!-- #5 Employee Number -->
								
									<tr>
									  <td class="wpsLabelText">
			                             Employee Number
									  </td> 
									 <td class="wpsPortletText">
			                              <form:input id="employeeNum" path="employeeNum" classname="editableinputfield" size="30" maxlength="10" />
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
  
  <!-- Buttons -->
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="middle">
            <table width="100%" border="0" cellpadding="5" cellspacing="0">
              <tr>
                <td align="right">
					<input type="submit" name="btnSearch" value="Search"></input>
					<!-- <input type="button" name="btnCancel" value="Cancel" onclick="cancelAndReset();" /> -->
					<input type="button" name="btnCancel" value="Cancel" onclick="testing();" />
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
  
<br>
   <div align="left" id="htmlTable"><common:jmesaScript  action="${ctx}/common/EmployeeEnquiry.do"  />
		${html }
		</div>

</form:form>
</body>
</html>

