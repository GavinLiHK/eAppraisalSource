package com.hkha.ea.dto.admin;

import java.util.List;

public class ReportReminderDto {
	
	private List<ReminderRecipientDto> recipientList;
	private List<ReminderRecipientDto> otherRecipientList;
	private String commenceDate;
	private String endDate;
	private String messageBody;
	
	public List<ReminderRecipientDto> getRecipientList() {
		return recipientList;
	}
	public void setRecipientList(List<ReminderRecipientDto> recipientList) {
		this.recipientList = recipientList;
	}
	
	public List<ReminderRecipientDto> getOtherRecipientList() {
		return otherRecipientList;
	}
	public void setOtherRecipientList(List<ReminderRecipientDto> otherRecipientList) {
		this.otherRecipientList = otherRecipientList;
	}
	public String getCommenceDate() {
		return commenceDate;
	}
	public void setCommenceDate(String commenceDate) {
		this.commenceDate = commenceDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
}
