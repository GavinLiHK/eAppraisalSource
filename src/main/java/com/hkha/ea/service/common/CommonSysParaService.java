package com.hkha.ea.service.common;

import java.util.List;

import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.common.EaUserGroupFunctionDTO;

public interface CommonSysParaService {
	
	public List<SystemParameterDTO> SysParaSearch(String paraName);
	public List<SystemParameterDTO> SysParaSearch(String paraName,String desc,String value);
	
	public EaUserGroupFunctionDTO searchUserGroupFunction(String userId,String functionId);
}
