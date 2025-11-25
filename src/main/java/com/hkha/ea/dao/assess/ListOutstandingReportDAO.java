package com.hkha.ea.dao.assess;

import java.util.List;

import com.hkha.ea.dto.assess.AppraisalReportRoleDTO;
import com.hkha.ea.dto.assess.AppraiseeBatchDTO;
import com.hkha.ea.dto.assess.ListHistoryDTO;

public interface ListOutstandingReportDAO {
	public List<AppraiseeBatchDTO> searchAppraiseeBatch(String currentUser);
	
	public List<AppraisalReportRoleDTO> searchOutstandingList(String currentUser);
	
	public List<ListHistoryDTO> searchHistory(String currentUser);
}
