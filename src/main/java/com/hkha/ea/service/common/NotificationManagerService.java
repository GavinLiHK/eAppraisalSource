package com.hkha.ea.service.common;

import java.util.Date;
import java.util.List;

import com.hkha.ea.common.EAException;
import com.hkha.ea.domain.DummyEmployee;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.dto.assess.ReportNextRoleDto;

public interface NotificationManagerService {

	public void generateDispatchNotification(List<ReportNextRoleDto> dsNextRole) throws EAException;

}
