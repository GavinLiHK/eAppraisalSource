<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="java.lang.String" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0">
				<tr>
					<td width="13%"  class="wpsDesc">
						<strong>Part V</strong>
						<br/>
						<strong>第五部分</strong>
					</td>
					<td width="87%"  class="wpsDesc">
						<strong>Record of Appraisal Interview</strong>
						<br>
						<strong>評核會見記錄</strong>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br/>
<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0" class="wpsPortletBody">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
                            		Record of Appraisal Interview <strong>評核會見記錄</strong>
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr>
					<td bgcolor="#CFD9E5" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" valign="top">
						<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
							<tr>
								<td dir="ltr" valign="top" align="right">
									<!-- ha_hr_ea_memo_details.APP_INTERVIEW -->
									<form:textarea path="${dto}.appInterview" name="appInterview" id="appInterview" wrap="soft" style="width:100%" maxlength="1960" rows="10" font="wpsPortletTitle" classname="inputfield" />
									<!--
									<salmon:input type="submit" name="btnSaveIoAppInterview" value="Save"></salmon:input>  -->
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" height="100%" width="1">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="100%" colspan="3">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>