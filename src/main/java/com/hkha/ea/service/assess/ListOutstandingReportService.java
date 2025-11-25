package com.hkha.ea.service.assess;

import java.util.List;

import com.hkha.ea.dto.assess.AppraisalReportRoleDTO;
import com.hkha.ea.dto.assess.AppraiseeBatchDTO;
import com.hkha.ea.dto.assess.ListHistoryDTO;

public interface ListOutstandingReportService {

	public List<AppraiseeBatchDTO> searchAppraiseeBatch(String currentUser);
	
	public List<AppraisalReportRoleDTO> searchOutstandingList(String currentUser);
	
	public List<ListHistoryDTO> searchHistory(String currentUser);
}
