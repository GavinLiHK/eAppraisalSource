package com.hkha.ea.service.assess;

import java.util.Date;
import java.util.List;

import org.springframework.validation.BindingResult;

import com.hkha.ea.common.EAException;
import com.hkha.ea.domain.EaBatch;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.dto.assess.AssignOfficerDto;
import com.hkha.ea.dto.assess.PeopleAssignmentDto;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;
import com.hkha.ea.dto.common.ReportUserRole;

public interface AssignOfficerService {

	public ReportUserRole checkRole(String reportID);

	public AssignOfficerDto pageInitialize(Long valueOf) throws Exception;

	public AssignOfficerDto pageEnquiry(List<String> appraisees);

	public EaReport searchByReportID(String reportId);

	public EaBatch searchByID(String batchId);

	public String saveAction(List<String> appraisees, AssignOfficerDto assignOfficerDto, SearchAppraiseeDto sac, EaReport rp, BindingResult result)throws Exception;

	public List<PeopleAssignmentDto> searchPeopleAssignment(List<String> officerNums, Date appraisalPeriodStart);
	
	public String updateAction(List<String> appraisees,  AssignOfficerDto assignOfficerDto, BindingResult result,String mode)throws Exception;

}
