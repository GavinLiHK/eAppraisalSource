package com.hkha.ea.dao.common;

import java.util.Date;
import java.util.List;

import com.hkha.ea.dto.common.EmployeeEnquiryDTO;

public interface EmployeeEnquiryDAO {

	public List<EmployeeEnquiryDTO> search(String rank, String postUnit, String postTitle, String firstName, String lastName, String employeeNum);
	
	public List<EmployeeEnquiryDTO>  searchEmployeeNameAndRankByEmployeeNum(String employeeNum) ;

	public List<EmployeeEnquiryDTO> searchByEmployee(String employeeNumber);

	//20161229 
	public List<EmployeeEnquiryDTO>  searchByFullName (String employeeFullName) ;
	
	//added on 20180131 incorrect employee number issue
	public boolean isEmployeeExist(String empNum, String CommDate);
}
