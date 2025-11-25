<%@ include file="/common/taglibs.jsp"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dto" type="java.lang.String" required="true"%>

<table border="0" width="100%" cellpadding="0" cellspacing="5">
	<tr>
		<td>
			<table border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr height="1">
					<td border="0" bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" width="1" height="8" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" height="12" nowrap>
						<table border="0" width="100%" height="15" cellpadding="3" cellspacing="0">
							<tr>
								<td width="100%" align="left" valign="middle" nowrap class="wpsPortletTitle">
									Memo Information
								</td>
							</tr>
						</table>
					</td>
					<td bgcolor="#CFD9E5" width="1" height="12">
						<img alt="" border="0" width="1" height="1" src='${ctx}/images/dot.gif'>
					</td>
				</tr>
				<tr>
					<td border="0" bgcolor="#CFD9E5" width="1">
						<img alt="" width="1" height="8" src='${ctx}/images/dot.gif'>
					</td>
					<td width="100%" valign="top">
						<table width="100%" border="0" cellpadding="0">
							<tr>
								<td width="10%">
									From
								</td>
								<td width="30%">
									<!--ha_hr_ea_memo_details.MEMO_FROM  -->
									<form:input path="${dto}.memoFrom" id="memoFrom" name="memoFrom" size="30" maxlength="30"  font="wpsPortletTitle" classname="inputfield" />
									
								</td>
								<td width="10%">
									To
								</td>
								<td width="30%">								
								<!--ha_hr_ea_memo_details.MEMO_TO  -->
									<form:input path="${dto}.memoTo" id="memoTo" name="memoTo" size="30" maxlength="30"  font="wpsPortletTitle" classname="inputfield" />
									
								</td>
								<td rowspan="5" width="20%">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									Ref
								</td>
								<td nowrap>
								<!-- ha_hr_ea_memo_details.REF1  -->
									<form:input path="${dto}.ref1" id="ref1" name="ref1" size="6" maxlength="6"  font="wpsPortletTitle" classname="inputfield" />
									
									in									
									<!-- ha_hr_ea_memo_details.REF2  -->
									<form:input path="${dto}.ref2" id="ref2" name="ref2" size="20" maxlength="20"  font="wpsPortletTitle" classname="inputfield" />
									
								</td>
								<td>
									Via
								</td>
								<td>
								<!-- ha_hr_ea_memo_details.VIA -->
									<form:input path="${dto}.via" id="via" name="via" size="30" maxlength="30"  font="wpsPortletTitle" classname="inputfield" />
									
								</td>
							</tr>
							<tr>
								<td>
									Tel. No.
								</td>
								<td>
								<!-- ha_hr_ea_memo_details.TEL_NO -->
									<form:input path="${dto}.telNo" id="telNo" name="telNo" size="20" maxlength="20"  font="wpsPortletTitle" classname="inputfield" />
									
								</td>
								<td>
									Your Ref
								</td>
								<td nowrap>
								<!-- ha_hr_ea_memo_details.YOUR_REF1 -->
									<form:input path="${dto}.yourRef1" id="yourRef1" name="yourRef1" size="6" maxlength="6"  font="wpsPortletTitle" classname="inputfield" />
									
									in
									<!-- ha_hr_ea_memo_details.YOUR_REF2 -->
									<form:input path="${dto}.yourRef2" id="yourRef2" name="yourRef2" size="20" maxlength="20"  font="wpsPortletTitle" classname="inputfield" />
									
								</td>
							</tr>
							<tr>
								<td>
									Fax. No.
								</td>
								<td>
								<!-- ha_hr_ea_memo_details.FAX_NO -->
									<form:input path="${dto}.faxNo" id="faxNo" name="faxNo" size="20" maxlength="20"  font="wpsPortletTitle" classname="inputfield" />
									
								</td>
								<td>
									Dated
								</td>
								<td>
								<!-- ha_hr_ea_memo_details.MEMO_TO_DATE -->
									<common:commonDate id="memoToDate" name="${dto}.memoToDate" />					
									
								</td>
							</tr>
							<tr>
								<td>
									Date
								</td>
								<td>
								<!-- ha_hr_ea_memo_details.MEMO_FROM_DATE -->
									<common:commonDate id="memoFromDate" name="${dto}.memoFromDate" />	
									
								</td>
								<td>
									Total Pages
								</td>
								<td>
								<!-- ha_hr_ea_memo_details.TOTAL_PAGES -->
								<form:input path="${dto}.totalPages" id="totalPages" name="totalPages" size="20" maxlength="20"  font="wpsPortletTitle" classname="inputfield" />
									
								</td>
							</tr>
						</table>
					</td>
					<td border="0" bgcolor="#CFD9E5" width="1">
						<img alt="" width="1" height="1" src='${ctx}/images/dot.gif'>
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
