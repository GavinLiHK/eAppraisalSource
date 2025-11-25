package com.hkha.ea.dao.security;

import java.util.List;

import com.hkha.ea.domain.EaGroupFunction;
import com.hkha.ea.domain.EaGroupRank;
import com.hkha.ea.domain.EaUserGrp;
import com.hkha.ea.dto.security.FunctionDto;
import com.hkha.ea.dto.security.GroupFunctionDto;
import com.hkha.ea.dto.security.GroupRankDto;
import com.hkha.ea.dto.security.RankDto;
import com.hkha.ea.dto.security.UserGroupEnquiryDTO;

public interface UserGroupMaintenanceDAO {

	public List<UserGroupEnquiryDTO> search(UserGroupEnquiryDTO userGroupEnquiryDTO);

	public List<RankDto> getRankList(long groupId);
	
	public List<GroupFunctionDto> getFunctionList();

	public List<GroupRankDto> getGroupRankList(long groupId);

	public List<GroupFunctionDto> getGroupFunctionList(long groupId);
	
	public List<GroupFunctionDto> getGroupFunctionListByAccess(long groupId, String access);

	public void addUserGroup(UserGroupEnquiryDTO userGroupEnquiryDTO) throws Exception;
	
	public void updateUserGroup(UserGroupEnquiryDTO userGroupEnquiryDTO) throws Exception;

}
