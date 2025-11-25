package com.hkha.ea.dao.security;

import java.util.List;

import com.hkha.ea.dto.common.EaLoginDTO;
import com.hkha.ea.dto.common.EaUserDTO;
import com.hkha.ea.dto.security.UserEnquiryDto;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;


public interface UserEnquiryDAO {
	
	public EaLoginDTO searchByLoginID(String loginID);
	
	public void saveLoginInformation(EaLoginDTO loginDTO);
	
	public void updateLoginInformation(EaLoginDTO loginDTO);
	
	public EaUserDTO searchByUserID(String userID);
	
	public EaUserDTO searchByUserIDAndStatus(String userID, String status);

	public List<UserEnquiryDto> search(UserEnquiryDto userEnquiryDto);

	public void insert(UserEnquiryDto userEnquiryDto);

	public void update(UserEnquiryDto userEnquiryDto, String oldUserId);
	
	public List<UserEnquiryModelDTO> searchUserByReportIdAndFunctionId(long reportId,long functionId);
	
	public List<UserEnquiryModelDTO> searchUserEnquiryModelDTO(String userId, String groupName, String enable);
	
	public List<UserEnquiryModelDTO> searchGradeManagmentByReportID(long reportId);
	
	//added on 20170214 get GM List by SU group id
	public List<UserEnquiryModelDTO> searchGMUserNameByGroupId(long groupId, long functionId);

	public String getUserIdByIvUser(String ivUser);
	
}
