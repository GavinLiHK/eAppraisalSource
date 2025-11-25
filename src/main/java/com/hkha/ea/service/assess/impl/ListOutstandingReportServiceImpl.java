package com.hkha.ea.service.assess.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkha.ea.dao.assess.ListOutstandingReportDAO;
import com.hkha.ea.dto.assess.AppraisalReportRoleDTO;
import com.hkha.ea.dto.assess.AppraiseeBatchDTO;
import com.hkha.ea.dto.assess.ListHistoryDTO;
import com.hkha.ea.service.assess.ListOutstandingReportService;

@Service("listOutstandingReportService")
public class ListOutstandingReportServiceImpl implements ListOutstandingReportService{

	@Autowired
	private ListOutstandingReportDAO listOutstandingReportDAO;
	
	public List<AppraiseeBatchDTO> searchAppraiseeBatch(String currentUser) {
		List<AppraiseeBatchDTO> results = listOutstandingReportDAO.searchAppraiseeBatch(currentUser);
		
		return results;
	}

	public List<AppraisalReportRoleDTO> searchOutstandingList(String currentUser) {
		List<AppraisalReportRoleDTO> results = listOutstandingReportDAO.searchOutstandingList(currentUser);
		return results;
	}

	public List<ListHistoryDTO> searchHistory(String currentUser) {
		List<ListHistoryDTO> results = listOutstandingReportDAO.searchHistory(currentUser);
		return results;
	}

}
