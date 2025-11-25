package com.hkha.ea.dao.assess;

import java.util.List;

import com.hkha.ea.dto.assess.BatchEnquiryDto;

public interface AppraiseeBatchEnquiryDAO {

	public List<BatchEnquiryDto> searchBatch(String batchName, String rank, String postUnit, String postTitle, String name);

	public List<BatchEnquiryDto> searchAppraiseeBatch(String curUser, String batchName, String employeeNum,
			String employeeName, String rank, String postUnit, String postTitle);

}
