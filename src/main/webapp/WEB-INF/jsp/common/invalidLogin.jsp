<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<body>
<c:if test="${not empty logout}">
    <c:if test="${logout == 1}">
        <script type="text/javascript">
            window.close()
        </script>
    </c:if>
</c:if>

<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">Invalid Login</td>
	      </tr>
	      <tr>
	        <td font="InformationFont">
	        </td>
	      </tr>
	    </table>
	  </td>
	</tr>
</table>
<div align="left">
    ${message} <br/> <br/>
    Please re-login to the e-Appraisal System
</div>
</body>
</html>