<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.hkha.ea.common.Constants" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head><TITLE>HKHA - EA</TITLE>
<script>
function backPar(value){

	var aa=value.replace(/<br\/>/g, "\n");
	
	$("#${pId}",window.opener.document).val(aa);
	
    window.close();
}
function cancelAndRest(){
	$("#${pId}",window.opener.document).val("");
    window.close();
}
</script>
</head>
<body>
<form:form id="pageForm" action="${ctx}/common/SearchMessage.do?pId=${pId}" modelAttribute="searchMessageDTO">
<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom">
	        	<FONT face="Verdana, Arial, Helvetica, sans-serif" STYLE="FONT-SIZE:large;FONT-WEIGHT:bold;LINE-WEIGHT:120%;PADDING-LEFT:5px;" COLOR="#333333"><B>Search Message</B></FONT> 
	        </td>
	      </tr>
	      <tr>
	        <td>
	          <!-- <input name="information" text="" visible="false" font="InformationFont"/> -->
	         
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>
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
								<td width="100%" align="left" valign="middle"  class="wpsPortletTitle">
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
					
						<!-- # 1 Message Id -->
						<tr>
						  <td width="28%" class="wpsPortletText">
	              			Message Description
						  </td>
						  <td width="72%">
                            <input type="text" id="paraDesc" name="paraDesc" value="${searchMessageDTO.paraDesc}" classname="inputfield" size="60" maxlength="40"></input>
						  </td>
						</tr>
						
						
						<!-- #3 Message contents  -->	
						<tr>
						  <td class="wpsPortletText">
	              			Message Content
						  </td>
						  <td>
                            <input type="text" id="parcValue" name="parcValue" value="" classname="inputfield" size="60" ></input>
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
					<input type="submit" name="btnSearch" value="Search"/>
					
					<input type="button" name="btnCancel" value="Cancel" onclick="cancelAndRest();"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>

<br>
   <div align="left" id="htmlTable"><common:jmesaScript action="${ctx}/common/SearchMessage.do" />
		${html }
		</div>


</form:form>
</body>
</html>
