package com.hkha.ea.dao.assess;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import com.hkha.ea.domain.EaContract;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.dto.assess.AppraiseeInfo;
import com.hkha.ea.dto.assess.AssessAppraisalDTO;
import com.hkha.ea.dto.assess.CoreCompetencyInfo;
import com.hkha.ea.dto.assess.SignatureInfo;
import com.hkha.ea.dto.assess.SubmitActionInfo;

public interface AppraisalAssessmentReportDAO {
	
	public AssessAppraisalDTO searchAppraisalAssessmentFullReportById(long id, HttpServletRequest request);
	
	public List<CoreCompetencyInfo> genCCInfoListForPartB4(Long id);
	
	public EaReport saveReportAppraisalInfo(AppraiseeInfo ai,EaReport er)throws Exception;
	
	public void saveReportPersonalInfo(AssessAppraisalDTO dto)throws Exception;
	
	public void saveReportPartAInfo(AssessAppraisalDTO dto, String mode)throws Exception;	
	
	public void saveReportPartB1Info(AssessAppraisalDTO dto)throws Exception;
	
	public void saveReportPartB2Info(AssessAppraisalDTO dto)throws Exception;
	
	public void saveReportPartB3Info(AssessAppraisalDTO dto)throws Exception;
	
	public void saveReportPartB4Info(AssessAppraisalDTO dto)throws Exception;
	
	public void saveReportPartB5Info(AssessAppraisalDTO dto)throws Exception;
	
	public void saveReportPartB6Info(AssessAppraisalDTO dto)throws Exception;
	
	public void saveReportPartB7Info(AssessAppraisalDTO dto)throws Exception;
	
	public void saveReportPartB8Info(AssessAppraisalDTO dto)throws Exception;
	
	public void saveReportSignatureInfo(SignatureInfo sf,Long rptId,String page) throws Exception;
	
	public void saveReportPrintSubmitInfo(AssessAppraisalDTO dto)throws Exception;
	
	public SubmitActionInfo submitReportByUpdateStatus(AssessAppraisalDTO dto)throws Exception;
	
	public void changeReportCompletionDateForAP(EaReport er,EaContract ec,String flag,String routing,Long rptId)throws Exception;
	
	public void changeReportCompletionDateForAO(EaReport er,EaContract ec,String flag,String routing,Long rptId)throws Exception;
	
	public void changeReportCompletionDateForCO(EaReport er,EaContract ec,String flag,String routing,Long rptId)throws Exception;
	
	public void changeReportCompletionDateForIO(EaReport er,EaContract ec,String flag,String routing,Long rptId)throws Exception;
	
	public void changeReportCompletionDateForRO(EaReport er,EaContract ec,String flag,String routing,Long rptId)throws Exception;
}
