package com.hkha.ea.service.assess;

import java.util.List;

import org.jmesa.view.component.Table;

import com.hkha.ea.dto.assess.AppraiseeEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.BatchEnquiryVo;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;

public interface AppraiseeBatchConverAndGenTableService {
	
	public String converAssVo(List<BatchEnquiryVo> volist,String selectedOptions);
	
	public String converAssDto(List<BatchEnquiryDto> dtolist,String selectedOptions);
	
	public String converStaVo(List<BatchEnquiryVo> volist,String selectedOptions);
	
	public String converStaDto(List<BatchEnquiryDto> dtolist,String selectedOptions);
	
	public List<String> converReportIds(String selectedOptions);
	
	public String coverAllSelectedVo(List<BatchEnquiryVo> results);
	
	public String coverAllSelectedDto(List<BatchEnquiryDto> results);
	
	public String converEmpVo(List<BatchEnquiryVo> volist,String selectedOptions);
	
	public List<String> convertToList(String selComDate);
	
	public List<Long> getSelectedReportID(String selBatch);
	
	public Table genBatchAppraiseeListHtml();
	
	public Table getSearchBatchAppraiseeHtml();

	public String coverAllSelectedJmesa(List<AppraiseeEnquiryDto> results);

	public Table getHtmlBatchList(SearchAppraiseeDto searchAppraiseeDto);

	public Table getHtmlReportAppraiseeList(SearchAppraiseeDto searchAppraiseeDto);

	public Table getHtmlAppraiseeList(SearchAppraiseeDto searchAppraiseeDto);

	public String coverOenPageAllSelectedJmesa(List<String> selOptionsAll);

}
