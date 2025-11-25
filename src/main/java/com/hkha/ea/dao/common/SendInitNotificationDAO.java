package com.hkha.ea.dao.common;

import java.util.List;

import com.hkha.ea.dto.admin.SendAutoReminderVo;

public interface SendInitNotificationDAO {

	public List<SendAutoReminderVo> executeBatch();
}
