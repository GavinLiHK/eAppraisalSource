package com.hkha.ea.dao.common;

import java.util.List;

import com.hkha.ea.domain.EaSysPara;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.common.EaUserGroupFunctionDTO;

public interface CommonSysParaDAO {
	public List<SystemParameterDTO> SysParaSearch(String paraName);
	public List<SystemParameterDTO> SysParaSearchMessage(String paraName,String messageName,String messageContent);
	public EaSysPara SysParaSearchMessageByDesc(String paraName,String messageName);
	
	public EaUserGroupFunctionDTO searchUserGroupFunction(String userId,String functionId);
}
