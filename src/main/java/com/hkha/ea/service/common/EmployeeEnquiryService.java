package com.hkha.ea.service.common;

import java.util.List;

import com.hkha.ea.dto.common.EmployeeEnquiryDTO;

public interface EmployeeEnquiryService {

	public List<EmployeeEnquiryDTO> search(String rank, String postUnit, String postTitle, String firstName, String lastName, String employeeNum);

	public String searchEmployeeName(String num);

	//20161229
	public  List<EmployeeEnquiryDTO> searchEmployeeByName(String employeeFullName);
}
