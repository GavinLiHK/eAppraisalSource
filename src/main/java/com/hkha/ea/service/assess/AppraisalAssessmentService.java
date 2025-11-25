package com.hkha.ea.service.assess;

import jakarta.servlet.http.HttpServletRequest;

import com.hkha.ea.dto.assess.AssessAppraisalDTO;
import com.hkha.ea.dto.assess.AssessAppraisalMemoDTO;
import com.hkha.ea.dto.assess.PrintSubmitInfo;
import com.hkha.ea.dto.common.ReportUserRole;

public interface AppraisalAssessmentService {
	
	public AssessAppraisalDTO searchAssessAppraisalReportById(String reportId,String reportType, boolean isEORoleChecked, HttpServletRequest request);
	
	public AssessAppraisalMemoDTO searchAssessAppraisalMemoById(String reportId,String reportType,String btl, HttpServletRequest request);
	
	public String saveAssessAppraisalMemo(AssessAppraisalMemoDTO dto);
	
	public String sendAssessAppraisalMemo(AssessAppraisalMemoDTO dto)throws Exception;
	
	public boolean saveAssessAppraisalMemoBeforeSubmit(AssessAppraisalMemoDTO dto);
	
	public String saveAssessAppraisalReport(AssessAppraisalDTO dto);
	
	public boolean saveAssessAppraisalReportBeforeSubmit(AssessAppraisalDTO dto, String mode);
	
	public AssessAppraisalDTO genCCInfoListForPartB4(AssessAppraisalDTO dto);
	
	public String sendAssessAppraisalReport(AssessAppraisalDTO dto)throws Exception;
	
	public String searchReportTemplateByReportId(Long rptId);
	
	public PrintSubmitInfo genRoutingLabelAndRoutingToListForPrintSubmit(PrintSubmitInfo psi,String userRole);
	
	public int convertPagesForAssessAppraisalReportAndMemo(ReportUserRole rur,String reportType,boolean isEORoleChecked);
	
	public ReportUserRole getReportUserRoleByReportIdAndUserId(Long rptId,String currentUserId);
}
