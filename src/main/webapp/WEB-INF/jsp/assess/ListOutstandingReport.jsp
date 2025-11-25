<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:if test="${(empty html_os_report) and (empty html_history) and (empty html_batch)}">
    <script type="text/javascript">
        window.onload = function(){
            alert("Record not found.");
        }
    </script>
</c:if>
<script type="text/javascript">
$(function() {
	 var ctxSys="${ctx}";
	 var ctxNow=$("#aa").val();
	 console.log("ctxSys:"+ctxSys);
	 console.log("ctxNow:"+ctxNow);
     
	 var preCtx=getJunction('/ea/');
     console.log("preCtx:"+preCtx);
});
   
</script>
</head>
<body>
<form:form id="pageForm" >
<!------- Title ------->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
	  <td valign="middle">
	    <table width="100%" border="0" cellpadding="5" cellspacing="0">
	      <tr>
	        <td height="50" valign="bottom" class="PageHeadingFont">
	       
	        List Outstanding Report
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



<!------- First block - Batch ------->
<c:if test="${not empty html_batch}">
<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
		<div align="left" id="htmlBatchTable"><common:jmesaScript action="${ctx}/assess/ListOutstandingReport.do" />
			
		    You are a coordinator for the following batch of appraisal reports.  Please assign responsible appraising officers to the appraisees.<br/>
		   
		    ${html_batch}
		    <br/>
		</div>
		</td>
	</tr>
</table>
</c:if>



<!------- Second block - Outstanding Report ------->
<c:if test="${not empty html_os_report}">
<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
		<div align="left" id="htmlOSReportTable"><common:jmesaScript action="${ctx}/assess/ListOutstandingReport.do" />
		    Please complete the following outstanding report(s). <br/>
		    ${html_os_report}
		    <br/>
		</div>
		</td>
	</tr>
</table>
</c:if>



<!------- Third block - History ------->
<c:if test="${not empty html_history}">
<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
		<div align="left" id="htmlHistoryTable"><common:jmesaScript action="${ctx}/assess/ListOutstandingReport.do" />
		    Record of processed Appraisal Report <br/>
		    
		    ${html_history}
		    <br/>
		</div>
		</td>
	</tr>
</table>
</c:if>


<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<!-- Skin header -->
				<tr height="1">
					<!-- left border -->
					<td border="0" bgcolor="#CFD9E5" width="1" height="12"><img alt="" width="1" height="8" src='${ctx}/images/dot.gif'></td>
					<td width="100%" height="12" nowrap>
                        <table name="dtImportantNotice" width="100%" align="Left" border="0" cellpadding="3" cellspacing="0">
                            <!-- Column names -->
                            <tr classname="wpsPortletTitle" bgcolor="#A7E6F8">
                                <td width="100%" align="left" valign="middle" classname="wpsPortletTitle">
                                    Important Notice
                                </td>
                            </tr>
                            <tr classname="wpsLabelText">
                                <td align="left" valign="top" classname="wpsLabelText">
                                <!-- edited on 20170428 change 20 mins to 300 mins
                                 There's no auto-save function. Your input will be lost if you have not saved it within 30 minutes -->
                                    There's no auto-save function. Your input will be lost if you have not saved it within 300 minutes
                                </td>
                            </tr>
                        </table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12"><img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'></td>
				</tr>
				 <!-- portlet body -->

				 <!-- bottom border -->
				<tr height="1">
					<td bgcolor="#CFD9E5" width="100%" height="1" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<input type="hidden" id="aa" name="aa"  value="${ctx}"/>
</form:form>
</body>

</html>