package com.hkha.ea.dao.assess;

import com.hkha.ea.dto.assess.AssessAppraisalMemoDTO;
import com.hkha.ea.dto.assess.MemoInfo;
import com.hkha.ea.dto.assess.MemoPart1Info;
import com.hkha.ea.dto.assess.MemoPart2Part5Info;
import com.hkha.ea.dto.assess.MemoPart3Part4Info;
import com.hkha.ea.dto.assess.PrintSubmitInfo;
import com.hkha.ea.dto.assess.SignatureInfo;
import com.hkha.ea.dto.assess.SubmitActionInfo;

import jakarta.servlet.http.HttpServletRequest;

public interface AppraisalAssessmentMemoDAO {
	
	public AssessAppraisalMemoDTO searchAppraisalAssessmentMemoById(long id, HttpServletRequest request);
	
	public void saveMemoInfo(MemoInfo mi,Long rptId) throws Exception;
	
	public void saveMemoPart1Info(MemoPart1Info mp1,Long rptId ) throws Exception;
	
	public void saveMemoPart2Part5Info(MemoPart2Part5Info mp25,Long rptId,String page) throws Exception;
	
	public void saveMemoPart3Part4Info(MemoPart3Part4Info mp3,Long rptId,String page) throws Exception;
	
	public void saveMemoSignatureInfo(SignatureInfo sf,Long rptId,String page) throws Exception;
	
	public void saveMemoPrintSubmitInfo(PrintSubmitInfo mps,Long rptId) throws Exception;
	
	public SubmitActionInfo submitMemoByUpdateStatus(AssessAppraisalMemoDTO dto);
	
}
