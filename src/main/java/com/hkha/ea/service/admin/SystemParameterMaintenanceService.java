package com.hkha.ea.service.admin;

import java.util.List;

import com.hkha.ea.dto.admin.SystemParameterDTO;

public interface SystemParameterMaintenanceService {
	
	public void save(SystemParameterDTO SystemParameterDTO);
	
	public void update(SystemParameterDTO SystemParameterDTO,SystemParameterDTO systemParameterDTOBeforeUpdated);
	
	public List<SystemParameterDTO> search(SystemParameterDTO systemParameterDTO);
	
	public SystemParameterDTO getByParamName(String paramName);
	
	public void delete(SystemParameterDTO systemParameterDTO);
}
