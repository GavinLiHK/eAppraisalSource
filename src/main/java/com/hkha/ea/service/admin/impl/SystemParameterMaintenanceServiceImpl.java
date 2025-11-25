package com.hkha.ea.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkha.ea.dao.admin.SystemParameterMaintenanceDAO;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.service.admin.SystemParameterMaintenanceService;

@Service("systemParameterMaintenanceService")
public class SystemParameterMaintenanceServiceImpl implements SystemParameterMaintenanceService{

	@Autowired
	private SystemParameterMaintenanceDAO systemParameterMaintenanceDAO;
	
	public void save(SystemParameterDTO systemParameterDTO){
		systemParameterMaintenanceDAO.save(systemParameterDTO);
	}
	
	public List<SystemParameterDTO> search(SystemParameterDTO systemParameterDTO){
		return systemParameterMaintenanceDAO.search(systemParameterDTO);
	}

	public void update(SystemParameterDTO systemParameterDTO,SystemParameterDTO systemParameterDTOBeforeUpdated){
		systemParameterMaintenanceDAO.update(systemParameterDTO,systemParameterDTOBeforeUpdated);
	}

	public SystemParameterDTO getByParamName(String paramName){
		return systemParameterMaintenanceDAO.getByParamName(paramName);
	}

	public void delete(SystemParameterDTO systemParameterDTO){
		systemParameterMaintenanceDAO.delete(systemParameterDTO);
	}
}
