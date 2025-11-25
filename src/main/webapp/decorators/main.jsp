<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="common"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
          "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title><c:choose>
		<c:when test="${not empty pageTitle}">
			<c:out value="${pageTitle}" />
		</c:when>
		<c:otherwise>HA E-Appraisal</c:otherwise>
	</c:choose></title>
<%– your old meta.jsp –%>
<%@ include file="/common/meta.jsp"%>

<%– optional per-page head content –%>
<c:if test="${not empty headContent}">
      ${headContent}
</c:if>
</head>

<body>
	<div id="pageAll">

		<div class="pageTop">
			<common:mainHeader currentFunction="${functionMessageKeySelected}" />
		</div>

		<div id="pageMain">
			<%– this pulls in the actual content JSP –%>
			<jsp:include page="${contentPage}" flush="true" />
		</div>

		<div id="pageFooter">
			<common:mainFooter />
		</div>

	</div>
</body>
</html>