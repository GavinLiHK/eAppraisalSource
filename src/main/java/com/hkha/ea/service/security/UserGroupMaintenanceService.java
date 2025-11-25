package com.hkha.ea.service.security;

import java.util.List;

import com.hkha.ea.dto.security.FunctionDto;
import com.hkha.ea.dto.security.GroupFunctionDto;
import com.hkha.ea.dto.security.GroupRankDto;
import com.hkha.ea.dto.security.RankDto;
import com.hkha.ea.dto.security.UserGroupEnquiryDTO;

public interface UserGroupMaintenanceService {

	public List<UserGroupEnquiryDTO> search(UserGroupEnquiryDTO userGroupEnquiryDTO);

	public List<RankDto> getRankList(long groupId);

	public List<GroupFunctionDto> getFunctionList();

	public List<GroupRankDto> getGroupRankList(long groupId);

	public List<GroupFunctionDto> getGroupFunctionList(long groupId);
	
	public List<GroupFunctionDto> getGroupAccessFunctions(long groupId);

	public void insert(UserGroupEnquiryDTO userGroupEnquiryDTO);

	public void update(UserGroupEnquiryDTO userGroupEnquiryDTO);

}
