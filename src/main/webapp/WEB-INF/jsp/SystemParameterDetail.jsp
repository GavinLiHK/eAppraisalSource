<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	$( document ).ready(function() {
	    var saved = $('#saved').val();
	    var numberOfErrors = $('#numberOfErrors').val();
	    var deleted = $('#deleted').val();
	    if(saved != null && saved == 'Y'){
	    	alert('<spring:message code="info.if0001" text="Save Successful!"/>');
	    	window.location.href = getJunction('/ea/')+'${ctx}/admin/iniSystemParameterEnquiry.do';
	    }
	    else{
	    	if(numberOfErrors != null && numberOfErrors != ''){
	    		alert(numberOfErrors + ' '+ '<spring:message code="error.er0049" text="Are you sure to delete the system parameter?"/>');
	    	}
	    	else{
	    		if(deleted != null && deleted == 'Y'){
	    			alert('<spring:message code="info.if0029" text="Deleted Sussessfully."/>');
	    			window.location.href = getJunction('/ea/')+'${ctx}/admin/searchSystemParameter.do';
	    		}
	    	}
	    }
	});
	
</script>
</head>
<body>
<form:form id="pageForm" modelAttribute="systemParameterDTO" action="${ctx}/admin/addOrModifySystemParameter.do" method="post">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class ="PageHeadingFont">System Parameter Maintenance > Add/Modify System Parameter
	         </td>
	      </tr>
	      <tr>
	        <td font="InformationFont">
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
	        	<common:mvcErrorTable modelAttribute="systemParameterDTO"/>
	          <!-- <salmon:text name="information" text="" visible="false" font="InformationFont"/>
	          <salmon:validator name="errorMessage" visible="false" breaksafter="2"  allowmultipleerrors="true" focuslinks="true" datasource="dsPrimary" rulejs="false" submitcomponents="btnSave" validatorclass="com.hkha.ea.common.validator.GeneralValidator">
	          </salmon:validator>   -->
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
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='${ctx}/images/dot.gif'></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle" name="txtSubHeading" >
			                       System Parameter Maintenance
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
									<!-- #1 Parameter name -->
						  
									  <tr>
			                            <td width="20%" class="wpsPortletText" name="capParamName">
			                              Parameter Name
			                            </td>
			                            <td class="wpsPortletText">
			                            <c:choose>
											 <c:when test="${not empty systemParameterDTOForUpdated.paramName}">
											    <input type="text" name="paramName" id="paramName" size="20" maxlength="20" classname="inputfield" value="${systemParameterDTOForUpdated.paramName}"></input>
											 </c:when>
											 <c:otherwise>
											     <input type="text" name="paramName" id="paramName" size="20" maxlength="20" classname="inputfield"></input>
											 </c:otherwise>
										</c:choose>
			                            </td>
			                          </tr>
			                          
									  <!-- #2 Parameter description -->
									  <tr>
			                            <td class="wpsPortletText" name="capParamDes">
			                              Parameter Description
			                            </td>
			                            <td class="wpsPortletText"  name="paramDesc" >
			                            	<c:choose>
												 <c:when test="${not empty systemParameterDTOForUpdated.paramName}">
												    <input type="text" name="paramDesc" id="paramDesc" size="100" maxlength="100" classname="wpsPortletText" value="${systemParameterDTOForUpdated.paramDesc}"></input>
												 </c:when>
												 <c:otherwise>
												     <input type="text" name="paramDesc" id="paramDesc" size="100" maxlength="100" classname="wpsPortletText" ></input>
												 </c:otherwise>
											</c:choose>
			                            </td>
			                          </tr>
			
			
			
			
									   <!-- #3 Para value -->
									  <tr>
			                            <td valign="top" class="wpsPortletText" name="capParaValue">
			                               Parameter  Value
			                            </td>
			                            <td class="wpsPortletText" name="paramValue">
				                            <c:choose>
												 <c:when test="${not empty systemParameterDTOForUpdated.paramName}">
												   <textarea form ="pageForm" name="paramValue" id="paramValue" classname="wpsPortletText" wrap="soft" rows="10" maxlength="3920" style="width:100%"><c:out value="${systemParameterDTOForUpdated.paramValue}"/></textarea>
												 </c:when>
												 <c:otherwise>
												     <textarea form ="pageForm" name="paramValue" id="paramValue" classname="wpsPortletText" wrap="soft" rows="10" maxlength="3920" style="width:100%"></textarea>
												 </c:otherwise>
											</c:choose>
			                             	
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
					<td bgcolor="#CFD9E5" width="1" height="1"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
					<td bgcolor="#CFD9E5" height="1"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
					<td bgcolor="#CFD9E5" width="1" height="1"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<input type="hidden" name="saved" id="saved" value="${saved}" />
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<input type="hidden" name="deleted" id="deleted" value="${deleted}" />
<!-- Buttons -->
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="middle">
            <table width="100%" border="0" cellpadding="5" cellspacing="0">
              <tr>
                <td align="right">
                  <input type="submit" name="btnSave" value="Save"></input>
                  <input type="button" name="btnDelete" value="Delete" onclick="deleteSysParam()"></input>
				  <input type="button" name="btnCancel" value="Cancel" onclick="cancel()"></input>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>


    
</form:form>

<script type="text/javascript">
	function addOrModify() {
		var form = document.getElementById('pageForm');
		var paramName = "${systemParameterDTOForUpdated.paramName}";
		if(paramName == null || paramName == ""){
			
		}
		form.submit();
		
	}
	function deleteSysParam() {
		var form = document.getElementById('pageForm');
		if (confirm('<spring:message code="confirm.delete.sysparam" text="Are you sure to delete the system parameter?"/>')) {
			form.action = getJunction('/ea/')+"${ctx}/admin/deleteSystemParameter.do";
			form.target = "_self";
			form.submit();	
     	}
	}
	function cancel() {
		window.location.href = getJunction('/ea/')+'${ctx}/admin/iniSystemParameterEnquiry.do';
	}
</script>
</body>
</html>