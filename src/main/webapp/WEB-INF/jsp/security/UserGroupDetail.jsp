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
		       		location.href = getJunction('/ea/')+"${ctx}/security/UserGroupEnquiry.do";
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
		          "Yes": function() {
		       		$( this ).dialog( "close" );
		       		savebefore();
		          },
		          "No": function() {
			       		$( this ).dialog( "close" );
			       		location.href = getJunction('/ea/')+"${ctx}/security/UserGroupEnquiry.do";
			          }
		}});
	}
</script>
</head>
<body>
<form:form id="pageForm" modelAttribute="userGroupEnquiryDTO"  method="post">
<div id="msgDialog" title="HA-EA">
	<p><span id="msg"></span></p>
</div>
<div id="calDialog" title="HA-EA">
	<p><span id="cmsg"></span></p>
</div>
<input type="hidden" name="saved" id="saved" value="${saved}" />
<input type="hidden" name="numberOfErrors" id="numberOfErrors" value="${numberOfErrors}" />
<input type="hidden" name="isAdd" value="${isAdd}" />
<input type="hidden" id="avaRankarr" name="avaRankarr" value="${userGroupEnquiryDTO.avaRankarr}" />
<input type="hidden" id="selRankarr" name="selRankarr" value="${userGroupEnquiryDTO.selRankarr}" />
<input type="hidden" id="functionarr" name="functionarr" value="${userGroupEnquiryDTO.functionarr}" />
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">User Group Maintenance > Add/Modifiy User Group
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
	        	<common:mvcErrorTable modelAttribute="userGroupEnquiryDTO"/>
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
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src="${ctx}/image/dot.gif"></td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">User Group Maintenance
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td>
				</tr>
				 <!-- portlet body -->
				
				<tr height="100%">
					<td bgcolor="#CFD9E5" height="100%" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif">
					</td>
					<td width="100%" valign="top">
						<table width="100%" border="0" cellpadding="5">
							<tr>
								<td width="20%" class="wpsPortletText">User Group Name
                            	</td>
								<td class="wpsPortletText">
									<input type="text" name="userGroupName" value="${userGroupEnquiryDTO.userGroupName}" classname="inputfield" size="30" maxlength="30"></input>
									<input type="hidden" name="groupId" value="${userGroupEnquiryDTO.groupId}" />
                            	</td>
                        	</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif">
					</td>
				</tr>
				
				 <!-- bottom border -->
				<tr height="1">
					<td bgcolor="#CFD9E5" width="100%" height="1" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<!--------- Rank ---------->
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
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">Rank
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src="${ctx}/image/dot.gif"></td>
				</tr>
				 <!-- portlet body -->
				
				<tr height="100%">
					<td bgcolor="#CFD9E5" height="100%" width="1">
						<img alt="" border="0" width="1" height="100%" src="${ctx}/image/dot.gif">
					</td>
					<td width="100%" valign="top" align="center">
						<table border="0" cellpadding="5" cellspacing="0">
							<tr valign="middle">
								<td>
									Available Ranks<br/>
									<select id="avaRankSelect" multiple="multiple" size="13">
									 		<c:forEach var="avaRank" items="${avaRankList}">
									 		 <option value="${avaRank.rank}">${avaRank.rank}</option>
									 		</c:forEach>
									</select>
									<!-- <input type="select" name="avaRank" classname="inputfield" multiple="True" size="13"/> -->
								</td>
								<td width="40" align="center">
									<input type="image" id="SelRightAllId" name="btnSelectAll" src="<%= Constants.WEB_IMAGE_BTN_RIGHT_DOUBLE_ARROW_PATH %>"></input>
									<br/>
									<input type="image" id="SelRightOneId" name="btnSelect" src="<%= Constants.WEB_IMAGE_BTN_RIGHT_SINGLE_ARROW_PATH %>" ></input>
									<br/>
									<input type="image" id="SelLeftOneId" name="btnUnSelect" src="<%= Constants.WEB_IMAGE_BTN_LEFT_SINGLE_ARROW_PATH %>"></input>
									<br/>
									<input type="image" id="SelLeftAllId" name="btnUnSelectAll" src="<%= Constants.WEB_IMAGE_BTN_LEFT_DOUBLE_ARROW_PATH %>"></input>
								</td>
								<td>
									Selected Ranks<br/>
									<select id="selRankSelect" multiple="multiple" size="13">
									 		<c:forEach var="selRank" items="${userGroupEnquiryDTO.selRankList}">
									 		 <option value="${selRank.rank}">${selRank.rank}</option>
									 		</c:forEach>
									</select>
									<!-- <input type="select" name="selRank" classname="inputfield" multiple="True" size="13"/> -->
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="100%" src="${ctx}/image/dot.gif">
					</td>
				</tr>
				
				 <!-- bottom border -->
				<tr height="1">
					<td bgcolor="#CFD9E5" width="100%" height="1" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<!--------- Function ---------->
<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr>
					<!-- left border -->
					<td bgcolor="#CFD9E5" width="1" height="100%"><img alt="" width="1" height="100%" src="${ctx}/image/dot.gif"></td>
					<td width="100%" height="100%" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
							<td width="50%" valign="middle" class="wpsPortletTitle">Function
							</td>
							<td width="50%" valign="middle" class="wpsPortletTitle">Access Right
							</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="100%"><img alt="" width="1" height="100%" src="${ctx}/image/dot.gif"></td>
				</tr>
				<c:forEach var="func" items="${userGroupEnquiryDTO.functionList}" varStatus="status">
				<tr <c:if test="${ status.index%2==1}">bgcolor="#E0FFFF"</c:if> classname="wpsPortletText" height="12" nowrap>
					<td bgcolor="#CFD9E5" width="1" height="100%"><img alt="" width="1" height="100%" src="${ctx}/image/dot.gif"></td>
					<td width="50%">
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
							<td width="50%" valign="middle">${func.functionDesc}
								<input type="hidden" class="fun" id="funId${status.index}" value="${func.functionId}" />
							</td>
							<td width="50%" valign="middle" class="acc">
<%-- 								<input id="ARYes${status.index}" type="radio" value="Y" <c:if test="${func.accessRight eq 'Y'}">checked</c:if> onclick="checkYes(${status.index})">Yes
						        <input id="ARNo${status.index}" type="radio" value="N" <c:if test="${isAdd || func.accessRight eq 'N'}">checked</c:if> onclick="checkNo(${status.index})">No --%>
						        
						        <!-- added custom radio button on 20211110 by pccw -->
						        <!--
						        <input name="ar${status.index}" type="radio" value="Y" <c:if test="${func.accessRight eq 'Y'}">checked</c:if>>Yes
						        <input name="ar${status.index}" type="radio" value="N" <c:if test="${isAdd || func.accessRight eq 'N'}">checked</c:if>>No -->
						        
						        <!-- added custom radio button on 20211110 by pccw -->
						        <label class = "container">
						        	<input name="ar${status.index}" type="radio" value="Y" <c:if test="${func.accessRight eq 'Y'}">checked</c:if>>Yes
						        	<span class = "checkmark"></span>
        						</label>
        						<label class = "container">
							        <input name="ar${status.index}" type="radio" value="N" <c:if test="${isAdd || func.accessRight eq 'N'}">checked</c:if>>No
									<span class = "checkmark"></span>
        						</label>
        						<!--end added custom radio button on 20211110 by pccw -->
							</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="100%"><img alt="" width="1" height="100%" src="${ctx}/image/dot.gif"></td>
				</tr>
				</c:forEach>
				<!-- bottom border -->
				<tr>
					<td bgcolor="#CFD9E5" colspan="3" width="100%" height="1">
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
      <input type="button" name="btnSave" value="Save" onclick="savebefore()"/>
<!-- 	  <input type="button" name="btnCancel" value="Cancel" onclick="javascript:if (confirm('Do you want to save changes you have made?')) cancel();"/> -->
	  <input type="button" name="btnCancel" value="Cancel" onclick="cancel()"/>
    </td>
  </tr>
</table>
    
</form:form>
<script language="javascript"> 
$(function(){  
	//select all from left to right
	 $("#SelRightAllId").click(function(){  
		 
         $("#avaRankSelect option").clone().appendTo("#selRankSelect");  
         $("#avaRankSelect option").remove(); 
         return false;
     }); 
	
	//select one from left to right
	 $("#SelRightOneId").click(function(){  
         $("#avaRankSelect option:selected").clone().appendTo("#selRankSelect");  
         $("#avaRankSelect option:selected").remove();  
         return false;
     });  
	
	//select one from right to left
	 $("#SelLeftOneId").click(function(){  
            $("#selRankSelect option:selected").clone().appendTo("#avaRankSelect");  
            $("#selRankSelect option:selected").remove();  
            return false;
        });  
	
	//select all from right to left
	 $("#SelLeftAllId").click(function(){  
         $("#selRankSelect option").clone().appendTo("#avaRankSelect");  
         $("#selRankSelect option").remove(); 
         return false;
     });
	
});


function savebefore(){
	var inFun = "",selRak = "",avaRak = "",acc = "";
	
    $("#avaRankSelect option").each(function(){
    	avaRak+= $(this).attr("value")+",";
   	 });
    $("#avaRankarr").val(avaRak);
    
    $("#selRankSelect option").each(function(){
    	selRak+= $(this).attr("value")+",";
   	
    });
    $("#selRankarr").val(selRak);
	
	$(".fun").each(function(index){
		var chkRadio = document.getElementsByName("ar"+index);
		for(var i = 0; i < chkRadio.length; i++)
		if(chkRadio[i].checked){
			acc= chkRadio[i].value;
		}
		inFun+= $(this).attr("value")+";"+acc+",";
 });
	$("#functionarr").val(inFun);
	
	var form = document.getElementById('pageForm');
	form.action = getJunction('/ea/')+"${ctx}/security/saveUserGroupDetail.do";
	form.target = "_self";
	form.submit();	
}

</script> 
</body> 
</html>