package com.hkha.ea.service.security;

import java.util.List;

import com.hkha.ea.dto.common.EaLoginDTO;
import com.hkha.ea.dto.common.EaUserDTO;
import com.hkha.ea.dto.security.UserEnquiryDto;

public interface UserEnquireService{
	
	public EaLoginDTO searchByLoginID(String loginID);
	
	public void saveLoginInformation(EaLoginDTO loginDTO);
	
	public void updateLoginInformation(EaLoginDTO loginDTO);
	
	public EaUserDTO searchByUserID(String userID);
	
	public EaUserDTO searchByUserIDAndStatus(String userID, String status);

	public List<UserEnquiryDto> search(UserEnquiryDto userEnquiryDto);

	public void insert(UserEnquiryDto userEnquiryDto);

	public void update(UserEnquiryDto userEnquiryDto, String oldUserId);

	public String getUserIdByIvUser(String ssoIvUser);
	
}
