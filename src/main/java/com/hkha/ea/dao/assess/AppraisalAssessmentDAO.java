package com.hkha.ea.dao.assess;

import java.sql.SQLException;
import java.util.Map;

import com.hkha.ea.common.EAException;
import com.hkha.ea.dto.assess.AssessAppraisalDTO;
import com.hkha.ea.dto.assess.AssessAppraisalMemoDTO;
import com.hkha.ea.dto.assess.MemoInfo;
import com.hkha.ea.dto.assess.MemoPart1Info;
import com.hkha.ea.dto.assess.MemoPart2Part5Info;
import com.hkha.ea.dto.assess.MemoPart3Part4Info;
import com.hkha.ea.dto.assess.PrintSubmitInfo;
import com.hkha.ea.dto.assess.SignatureInfo;
import com.hkha.ea.dto.assess.SubmitActionInfo;
import com.hkha.ea.dto.common.ReportUserRole;

public interface AppraisalAssessmentDAO {
	
	public ReportUserRole getReportUserRoleByReportIdAndUserId(long id,String userId);

	public boolean[] allowBonusSalary(Long id)throws SQLException,EAException;
	
	public void updatePem001RCommenceDateAndEndDate(String commenceDate,String endDate,String curUser,String employeeNum)throws Exception;
	
	public void freezeAduitLog(Long reportID, String rptStatus) throws SQLException,EAException;

	public  Object[] moveTable(int reportID, boolean memoReport)throws EAException;

	public Map<String,String> getAssessAppraisalReportPageMapsByRole(String role);
	
	public Map<String,String> getAssessAppraisalMemoPageMapsByRole(String role);
	
	public void changeNextOfficer(PrintSubmitInfo psi,ReportUserRole rur,Long rptId)throws Exception;
	
	public boolean sendEmailAndAduitLog(Long rptId,String currentUserRoleStatus,String flag,SubmitActionInfo msi)throws Exception;
	
	public PrintSubmitInfo genRoutingLabelAndRoutingToListForPrintSubmit(PrintSubmitInfo mps,String userRole);
	
	public int convertPagesForAssessAppraisalReportAndMemo(ReportUserRole rur,String reportType,boolean isEORoleCkecked);
}
