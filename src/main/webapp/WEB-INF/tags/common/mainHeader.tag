<%@ include file="/common/taglibs.jsp"%>
<HEAD><TITLE>Header</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META http-equiv="Pragma" CONTENT="no-cache">
<META http-equiv="CACHE-CONTROL" CONTENT="no-cache">
<%@ attribute name="currentFunction" required="false" type="java.lang.String"%>
<link rel="stylesheet" href="${ctx}/css/Styles_menu.css">

<script src="${ctx}/js/tabpane.js" type="text/javascript"></script>

<script type="text/javascript" src="${ctx}/js/timer.js"></script>
<script language="javascript">
function logout() {
    window.opener=this;
    window.location=getJunction('/ea/')+'${ctx}/logout.do?logout=1';
}
</script>

<div data-role="header" data-theme="c">
<table border="0" width="99.99%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
    <tr>
        <td align="left">
            <TABLE>
            <TR><TD WIDTH="50%">
               <img src="${ctx}/images/ea_logo.gif" width="450" height="52" border="0">
            </TD>
            <TD WIDTH="50%" ALIGN="middle">
               <B><font size="6">RESTRICTED (STAFF)</font></B>
            </TD>
            </TR>
            </TABLE>
        </td>
       
        <td align="right">
            <table>
                <tr>
                    <td nowrap>
                        <c:if test="${not empty sessionScope.EMPLOYEE_NUMBER}">
                            You are currently login as ${sessionScope.EMPLOYEE_NUMBER}
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <!-- <td >
                    <a href="javascript:popupHelp()">Help</a>
                            &nbsp;|&nbsp;
                        <salmon:box name="boxHelp" linewidth="0" margin="0" visible="false">
                            
                        </salmon:box>
                    </td> -->
                    <td nowrap align="right">
                        <a href='${ctx}/assess/ListOutstandingReport.do'>Home</a>
                        &nbsp;|&nbsp;
                        <a href="javascript:if(confirm('Close this window without saving/sending?')) logout();">Close</a>&nbsp;
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<!--
<div class="clBar">
    <c:choose>
        <c:when test="${not empty currentFunction}">
                Report&nbsp;&nbsp;&nbsp;<c:out value="${currentFunction}"/> 
        </c:when>
       <c:otherwise>
                Report&nbsp;&nbsp;&nbsp;Admin
        </c:otherwise>
    </c:choose>
</div>
-->
<div id='cssmenu'>
    ${sessionScope.EMPLOYEE_USER_MENU}
</div>