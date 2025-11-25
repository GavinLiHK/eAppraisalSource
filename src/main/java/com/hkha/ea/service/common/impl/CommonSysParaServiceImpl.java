package com.hkha.ea.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.dao.common.CommonSysParaDAO;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.common.EaUserGroupFunctionDTO;
import com.hkha.ea.service.common.CommonSysParaService;

@Service("commonSysParaService")
public class CommonSysParaServiceImpl implements CommonSysParaService{
	@Autowired
	private CommonSysParaDAO commonSysParaDAO;
	
	@Transactional(readOnly = true)
	public List<SystemParameterDTO> SysParaSearch(String paraName) {
		List<SystemParameterDTO> sysList=new ArrayList<SystemParameterDTO> ();
		if(!StringUtil.isEmptyString(paraName)){
			if("reportTemplateName".equals(paraName)){
				sysList=commonSysParaDAO.SysParaSearch(Constants.SYS_PARA_PREFIX_REPORT_TEMPLATE);
			}
		}
		
		return sysList;
	}
	
	@Transactional(readOnly = true)
	public List<SystemParameterDTO> SysParaSearch(String paraName,String desc,String value) {
		List<SystemParameterDTO> sysList=new ArrayList<SystemParameterDTO> ();
		if(!StringUtil.isEmptyString(paraName)){
			if("notification".equals(paraName)){
				
			sysList=commonSysParaDAO.SysParaSearchMessage(Constants.PREFIX_SYSTEM_DEFAULT_MAIL_MESSAGE,desc,value);
			}
		}
		
		return sysList;
	}

	public EaUserGroupFunctionDTO searchUserGroupFunction(String userId, String functionId) {
		
		return commonSysParaDAO.searchUserGroupFunction(userId, functionId);
	}


	
}
