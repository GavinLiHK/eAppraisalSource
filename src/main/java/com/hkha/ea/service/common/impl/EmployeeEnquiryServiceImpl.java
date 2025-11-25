package com.hkha.ea.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkha.ea.dao.common.EmployeeEnquiryDAO;
import com.hkha.ea.dto.common.EmployeeEnquiryDTO;
import com.hkha.ea.service.common.EmployeeEnquiryService;

@Service("employeeEnquiryService")
public class EmployeeEnquiryServiceImpl implements EmployeeEnquiryService{
	
	@Autowired
	private EmployeeEnquiryDAO employeeEnquiryDAO;

	public List<EmployeeEnquiryDTO> search(String rank, String postUnit, String postTitle, String firstName,
			String lastName, String employeeNum) {
		return employeeEnquiryDAO.search( rank,  postUnit,  postTitle, firstName, lastName, employeeNum);
	}

	public String searchEmployeeName(String num){
		List<EmployeeEnquiryDTO> list = employeeEnquiryDAO.searchByEmployee(num);
		if(list != null && list.size() > 0){
			return list.get(0).getEmployeeFullName();
		}
		return null;
	}
	
	public List<EmployeeEnquiryDTO> searchEmployeeByName(String employeeFullName){
		return employeeEnquiryDAO.searchByFullName(employeeFullName);
	}
}
