package com.hkha.ea.service.assess;

import java.util.List;

import org.jmesa.view.component.Table;

import com.hkha.ea.dto.assess.AppraiseeEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryVo;

public interface AppraiseeBatchEnquiryService {

	public List<BatchEnquiryDto> searchBatch(String batchName, String rank, String postUnit, String postTitle, String name);

	public List<AppraiseeEnquiryDto> searchReport(String batchName, String rank, String postUnit, String postTitle, String name,
			boolean b);

	public List<BatchEnquiryDto> searchAppraiseeBatch(String name, String batchName, String employeeNum,
			String employeeName, String rank, String postUnit, String postTitle);

	public List<BatchEnquiryDto> searchAppraiseeBatchList(String batchName);
	
	
	public List<BatchEnquiryVo> searchAppraiseeBatchVoList(String batchName) ;
}
