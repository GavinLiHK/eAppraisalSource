<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	$( document ).ready(function() {
		msgDialog();
		cancelDialog();
	    var saved = $('#saved').val();
	    var numberOfErrors = $('#numberOfErrors').val();
	    if(saved != null && saved == 'Y'){
	    	//alert('<spring:message code="info.if0001" text="Are you sure to delete the system parameter?"/>');
            $("#msg").html("Save Successful!");		
            $("#msgDialog").dialog("open");
	    }
	    else{
	    	if(numberOfErrors != null && numberOfErrors != ''){
	    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text="Are you sure to delete the system parameter?"/>');
	    	}
	    }
	});
	function msgDialog(){
		$("#msgDialog").dialog({
			autoOpen: false,
			height: 150,
		      width: 350,
		      modal: true,
		      buttons: {
		          "OK": function() {
		       		$( this ).dialog( "close" );
		       		location.href = getJunction('/ea/')+"${ctx}/security/UserEnquiry.do";
		          }
		}});
	}
	
	function cancel(){
		$("#cmsg").html("Do you want to save changes you have made?");
		$("#calDialog").dialog("open");
	}
	function cancelDialog(){
		$("#calDialog").dialog({
			autoOpen: false,
			height: 150,
		      width: 350,
		      modal: true,
		      buttons: {
		          "OK": function() {
		       		$( this ).dialog( "close" );
		       		savebefore();
		          },
		          "Cancel": function() {
			       		$( this ).dialog( "close" );
			       		location.href = getJunction('/ea/')+"${ctx}/security/UserEnquiry.do";
			          }
		}});
	}
</script>
</head>
<body>
<form:form id="pageForm" modelAttribute="userEnquiryDto"  method="post">
<div id="msgDialog" title="HA-EA">
	<p><span id="msg"></span></p>
</div>
<div id="calDialog" title="HA-EA">
	<p><span id="cmsg"></span></p>
</div>
<input type="hidden" name="saved" id="saved" value="${saved}" />
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<input type="hidden" name="isAdd" value="${isAdd}" />

 <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">User Maintenance > Add/Modifiy User
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
	        	<common:mvcErrorTable modelAttribute="userEnquiryDto"/>
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>

<!--------- Detail---------->
    <table border="0" width="100%" cellpadding="0" cellspacing="5">
			<tr>
				<td>
					<table border="0" width="100%" cellpadding="0" cellspacing="0"
						class="wpsPortletBody">
						<!-- Skin header -->
						<tr height="1">
							<!-- left border -->
							<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img
								alt="" width="1" height="8" src="${ctx}/image/dot.gif"></td>
							<td width="100%" height="12" nowrap>
								<table border="0" width="100%" cellpadding="3" cellspacing="0">
									<tr>
										<td width="100%" align="left" valign="middle" nowrap
											class="wpsPortletTitle">User Maintenance</td>
									</tr>
								</table>
							</td>
							<td bgcolor="#CFD9E5" width="1" height="12"><img alt=""
								border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td>
						</tr>
						<!-- portlet body -->

						<tr height="100%">
							<td bgcolor="#CFD9E5" height="100%" width="1"><img alt=""
								border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td>
							<td width="100%" valign="top">
								<table width="100%" height="100%" border="0" cellpadding="5"
									cellspacing="0">
									<tr>
										<td dir="ltr" valign="top">


											<table width="100%" border="0" cellpadding="0">

												<!-- #1 user name -->
												<tr>
													<td width="28%">User ID</td>
													<td width="72%">
													 <input type="text" name="userId" value="${userEnquiryDto.userId}" classname="inputfield" size="30" maxlength="30"></input>
													</td>
												</tr>

												<!-- #2 group name -->
												<tr>
													<td>Group Name</td>
													<td><select name="groupId">
															<option value=""></option>
															<c:forEach var="grpName" items="${groupNameList}">
																<option value="${grpName.groupId}" <c:if test="${userEnquiryDto.groupId eq grpName.groupId}">selected</c:if> >${grpName.userGroupName}</option>
															</c:forEach>
													</select></td>
												</tr>

												<!-- #3 enable -->
												<tr>
													<td>Enable</td>
													<td><select name="enable">
															<option value="" <c:if test="${userEnquiryDto.enable eq ''}">selected</c:if> >All</option>
															<option value="Y" <c:if test="${userEnquiryDto.enable eq 'Y'}">selected</c:if>>Yes</option>
															<option value="N" <c:if test="${userEnquiryDto.enable eq 'N'}">selected</c:if>>No</option>
													</select></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td bgcolor="#CFD9E5" width="1"><img alt="" border="0"
								width="1" height="1" src="${ctx}/image/dot.gif"></td>
						</tr>

						<!-- bottom border -->
						<tr height="1">
							<td bgcolor="#CFD9E5" width="100%" height="1" colspan="3"></td>
						</tr>
					</table>
				</td>
			</tr>
	</table>
<input type="hidden" name="oldUserId" id="oldUserId" value="${oldUserId}" />

		<!-- Buttons -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="middle">
					<table width="100%" border="0" cellpadding="5" cellspacing="0">
						<tr>
							<td align="right"><input type="button" name="btnSave"
								value="Save" onclick="savebefore()"></input> <input type="button" name="btnCancel"
								value="Cancel"
								onclick="cancel()"></input>
								<!-- <input type="button" name="btnCancel"
								value="Cancel"
								onclick="javascript:if (confirm('Do you want to save changes you have made?')) cancel();"></input> -->
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
    
</form:form>
<script language="javascript"> 

function savebefore(){
	
	var form = document.getElementById('pageForm');
	form.action = getJunction('/ea/')+"${ctx}/security/saveUserDetail.do";
	form.target = "_self";
	form.submit();	

}

</script> 
</body> 
</html>