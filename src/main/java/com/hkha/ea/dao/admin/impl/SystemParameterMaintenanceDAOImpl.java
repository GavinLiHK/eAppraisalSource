package com.hkha.ea.dao.admin.impl;

import java.util.ArrayList;
import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.admin.SystemParameterMaintenanceDAO;
import com.hkha.ea.dto.admin.SystemParameterDTO;

@Repository("systemParameterMaintenanceDAO")
public class SystemParameterMaintenanceDAOImpl implements
SystemParameterMaintenanceDAO {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(SystemParameterMaintenanceDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	    
	@Transactional(rollbackFor=Exception.class) 
	public void save(SystemParameterDTO systemParameterDTO) {
		StringBuilder sql = new StringBuilder();
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		sql.append("insert into ha_hr_ea_sys_para ");
		sql.append("(para_name,para_desc,para_value,created_by, creation_date, last_updated_by, last_update_date) ");
		sql.append(" values (:paraName, :paramDesc, :paramValue, :createdBy, sysdate, :createdBy, sysdate) ");
		
		 log.info("sql is "+sql.toString());
		 log.info(systemParameterDTO.toString());
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("paraName", systemParameterDTO.getParamName().toUpperCase());
		 paramSource.addValue("paramDesc", systemParameterDTO.getParamDesc());
		 paramSource.addValue("paramValue", systemParameterDTO.getParamValue());
		 paramSource.addValue("createdBy", currentPrincipalName);
		 
		 namedParameterJdbcTemplate.update(sql.toString(), paramSource);
	}

	@Transactional(rollbackFor=Exception.class) 
	public void update(SystemParameterDTO systemParameterDTO, SystemParameterDTO systemParameterDTOBeforeUpdated){
		StringBuilder sql = new StringBuilder();
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		sql.append("update ha_hr_ea_sys_para ");
		sql.append("set para_name = :paramName, ");
		sql.append("para_desc = :paramDesc, ");
		sql.append("para_value = :paramValue, ");
		sql.append("last_updated_by = :lastUpdateBy, ");
		sql.append("last_update_date = sysdate ");
		sql.append("where UPPER(PARA_NAME) = :paramNameB4Updated ");
		
		 log.info("sql is "+sql.toString());
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("paramName", systemParameterDTO.getParamName().toUpperCase());
		 paramSource.addValue("paramDesc", systemParameterDTO.getParamDesc());
		 paramSource.addValue("paramValue", systemParameterDTO.getParamValue());
		 paramSource.addValue("lastUpdateBy", currentPrincipalName);
		 paramSource.addValue("paramNameB4Updated",systemParameterDTOBeforeUpdated.getParamName().toUpperCase());
		 namedParameterJdbcTemplate.update(sql.toString(), paramSource);
	}
	
	//@Transactional(readOnly=true)
	public List<SystemParameterDTO> search(SystemParameterDTO systemParameterDTO){
		List<SystemParameterDTO> systemParameterDTOList = new ArrayList<SystemParameterDTO>();  
		StringBuilder sql = new StringBuilder();
		sql.append("select PARA_NAME paramName, PARA_DESC paramDesc from ha_hr_ea_sys_para ");
		sql.append("where 1 = 1 ");
		
		if(!Util.isEmptyString(systemParameterDTO.getParamName()))
			sql.append("and UPPER(PARA_NAME) like '%").append(systemParameterDTO.getParamName().trim().toUpperCase()).append("%' ");
	     	
	    if(!Util.isEmptyString(systemParameterDTO.getParamDesc()))
	    	sql.append("and UPPER(PARA_DESC) like '%").append(systemParameterDTO.getParamDesc().trim().toUpperCase()).append("%' ");
	         	
		sql.append("order by PARA_NAME ");
		
		 log.info("sql is "+sql.toString());
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("paramName", StringUtil.isEmptyString(systemParameterDTO.getParamName())?systemParameterDTO.getParamName():systemParameterDTO.getParamName().toUpperCase());
		 paramSource.addValue("paramDesc", systemParameterDTO.getParamDesc());

		 systemParameterDTOList = namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<SystemParameterDTO>(SystemParameterDTO.class));
		  if(systemParameterDTOList != null && systemParameterDTOList.size() > 0){
			 log.info(" systemParameterDTOList.get(0).getParamName() is " + systemParameterDTOList.get(0).getParamName());
		  }
		  else{
			 log.info(" systemParameterDTOList.get(0).getParamName() is null");
		  }
		  
		  return systemParameterDTOList;
	}
	
	@Transactional(readOnly=true)
	public SystemParameterDTO getByParamName(String paramName){
		SystemParameterDTO systemParameterDTO = new SystemParameterDTO();  
		StringBuilder sql = new StringBuilder();
		sql.append("select PARA_NAME paramName, PARA_DESC paramDesc, para_value paramValue from ha_hr_ea_sys_para ");
		sql.append("where 1 = 1 ");
		sql.append("and UPPER(PARA_NAME) = :paramName ");
		
		 log.info("sql is "+sql.toString());
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("paramName", StringUtil.isEmptyString(paramName)? paramName:paramName.toUpperCase());
//		  systemParameterDTOList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SystemParameterDTO.class)); 
		  
		 systemParameterDTO = namedParameterJdbcTemplate.queryForObject(sql.toString(), paramSource,  new BeanPropertyRowMapper<SystemParameterDTO>(SystemParameterDTO.class));
		 log.info(systemParameterDTO.toString());
		 return systemParameterDTO;
	}

	@Transactional(rollbackFor=Exception.class) 
	public void delete(SystemParameterDTO systemParameterDTO){
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ha_hr_ea_sys_para ");
		sql.append("where UPPER(PARA_NAME) = :paramName ");
		
		 log.info("sql is "+sql.toString());
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("paramName", systemParameterDTO.getParamName().toUpperCase());
		 namedParameterJdbcTemplate.update(sql.toString(), paramSource);
	}

}