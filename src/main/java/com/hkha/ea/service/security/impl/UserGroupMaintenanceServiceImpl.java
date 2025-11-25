package com.hkha.ea.service.security.impl;

import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.dao.security.UserGroupMaintenanceDAO;
import com.hkha.ea.dto.security.GroupFunctionDto;
import com.hkha.ea.dto.security.GroupRankDto;
import com.hkha.ea.dto.security.RankDto;
import com.hkha.ea.dto.security.UserGroupEnquiryDTO;
import com.hkha.ea.service.security.UserGroupMaintenanceService;

@Service("userGroupMaintenanceService")
public class UserGroupMaintenanceServiceImpl implements UserGroupMaintenanceService{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(UserGroupMaintenanceServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out

	@Autowired
	private UserGroupMaintenanceDAO userGroupMaintenanceDao;
	
	@Transactional(readOnly =  true)
	public List<UserGroupEnquiryDTO> search(UserGroupEnquiryDTO userGroupEnquiryDTO) {
		
		return userGroupMaintenanceDao.search(userGroupEnquiryDTO);
	}

	@Transactional(readOnly =  true)
	public List<RankDto> getRankList(long groupId) {
		
		return userGroupMaintenanceDao.getRankList(groupId);
	}

	@Transactional(readOnly =  true)
	public List<GroupFunctionDto> getFunctionList() {
		return userGroupMaintenanceDao.getFunctionList();
	}

	public List<GroupRankDto> getGroupRankList(long groupId) {
		return userGroupMaintenanceDao.getGroupRankList(groupId);
	}

	public List<GroupFunctionDto> getGroupFunctionList(long groupId) {
		return userGroupMaintenanceDao.getGroupFunctionList(groupId);
	}

	@Transactional(rollbackFor = Exception.class)
	public void insert(UserGroupEnquiryDTO userGroupEnquiryDTO) {
		try {
			userGroupMaintenanceDao.addUserGroup(userGroupEnquiryDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.severe(e.toString());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(UserGroupEnquiryDTO userGroupEnquiryDTO) {
		
		try {
			userGroupMaintenanceDao.updateUserGroup(userGroupEnquiryDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.severe(e.toString());
		}
	}

	public List<GroupFunctionDto> getGroupAccessFunctions(long groupId) {
		return userGroupMaintenanceDao.getGroupFunctionListByAccess(groupId, "Y");
	}
		

}
