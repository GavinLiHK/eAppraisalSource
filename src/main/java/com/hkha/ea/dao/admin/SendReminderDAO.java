package com.hkha.ea.dao.admin;

import java.util.List;

import com.hkha.ea.dto.admin.ReminderRecipientVo;
import com.hkha.ea.dto.admin.SendAutoReminderVo;

public interface SendReminderDAO {

	public List<ReminderRecipientVo> search(List<Long> reportIDs);
	
	public List<SendAutoReminderVo> searchRpForSendAutoReminder();
	
	public String searchEmailAddressByEmployeeNumber(String employeeNumber);
	
}
