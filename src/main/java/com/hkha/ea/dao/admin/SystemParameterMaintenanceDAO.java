package com.hkha.ea.dao.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hkha.ea.dto.admin.SystemParameterDTO;

public interface SystemParameterMaintenanceDAO {
	
	public void save(SystemParameterDTO systemParameterDTO);
	
	public List<SystemParameterDTO> search(SystemParameterDTO systemParameterDTO);
	
	public void update(SystemParameterDTO systemParameterDTO, SystemParameterDTO systemParameterDTOBeforeUpdated);
	
	public SystemParameterDTO getByParamName(String paramName);
	
	public void delete(SystemParameterDTO systemParameterDTO);
}
