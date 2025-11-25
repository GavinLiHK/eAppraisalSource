package com.hkha.ea.service.security.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkha.ea.dao.security.UserEnquiryDAO;
import com.hkha.ea.dto.common.EaLoginDTO;
import com.hkha.ea.dto.common.EaUserDTO;
import com.hkha.ea.dto.security.UserEnquiryDto;
import com.hkha.ea.service.security.UserEnquireService;

@Service("userEnquireService")
public class UserEnquireServiceImpl implements UserEnquireService {
	
	@Autowired
	private UserEnquiryDAO userEnquiryDAO;

	public EaLoginDTO searchByLoginID(String loginID){
		return userEnquiryDAO.searchByLoginID(loginID);
	}
	
	public void saveLoginInformation(EaLoginDTO loginDTO) {
		userEnquiryDAO.saveLoginInformation(loginDTO);
	}

	public void updateLoginInformation(EaLoginDTO loginDTO) {
		userEnquiryDAO.updateLoginInformation(loginDTO);
	}

	public EaUserDTO searchByUserID(String userID) {
		return userEnquiryDAO.searchByUserID(userID);
	}

	public EaUserDTO searchByUserIDAndStatus(String userID, String status) {
		return userEnquiryDAO.searchByUserIDAndStatus(userID, status);
	}

	public List<UserEnquiryDto> search(UserEnquiryDto userEnquiryDto) {
		return userEnquiryDAO.search(userEnquiryDto);
	}

	public void insert(UserEnquiryDto userEnquiryDto) {
		userEnquiryDAO.insert(userEnquiryDto);
	}

	public void update(UserEnquiryDto userEnquiryDto,String oldUserId) {
		userEnquiryDAO.update(userEnquiryDto,oldUserId);
		
	}
	
	public String getUserIdByIvUser(String ivUser) {
		return userEnquiryDAO.getUserIdByIvUser(ivUser);
	}


}
