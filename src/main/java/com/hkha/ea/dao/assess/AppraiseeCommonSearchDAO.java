package com.hkha.ea.dao.assess;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.hkha.ea.domain.EaBatch;
import com.hkha.ea.domain.EaContract;
import com.hkha.ea.domain.EaCoreCompetency;
import com.hkha.ea.domain.EaMemoDetails;
import com.hkha.ea.domain.EaObjectives;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.domain.EaTrainPlan;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.dto.assess.BatchEnquiryDto;
import com.hkha.ea.dto.assess.Pem001RDto;
import com.hkha.ea.dto.assess.PeopleAssignmentDto;
import com.hkha.ea.dto.assess.UserTableColumn;

public interface AppraiseeCommonSearchDAO {

	//Create by Coral
	public EaReport findEareportById(long id);
	
	public EaContract findEaContractById(long id);
	
	public EaReportRole searchReportRoleByReportIdandRole(long id,String role);
	
	public EaBatch findEaBatchById(String batchID);
	
	public List<EaObjectives> findEaObjectivesByReportIdAndType(long id,String type);
	
	public List<EaCoreCompetency> findEaCoreCompetencyByReportId(long id );

	public List<EaReportRole> searchReportRoleDetailByReportIds(List<Long> reportIds);

	public List<EaContract> findEaContractByIds(List<Long> reportIds);

	public List<EaMemoDetails> findEaMemoDetailsByIds(List<Long> reportIds);

	public List<EaCoreCompetency> findEaCoreCompetencyByIds(List<Long> reportIds);

	public List<EaObjectives> findEaObjectivesByIds(List<Long> reportIds);

	public List<EaTrainPlan> findEaTrainPlanByIds(List<Long> reportIds);

	public List<EaReport> findEaReportByIds(List<Long> reportIds);
		
	public List<EaReportRole> searchReportRoleDetailByReportId(long id);
	
	public List<EaTrainPlan> findEaTrainPlanById(long id) ;
	
	public EaMemoDetails findEaMemoDetailsById(long id) ;
	
	public EaReport searchByReportID(String reportID);
	
	public int findHaCommonRptPeopleVPersonIdByEmployeeNumber(String employeeNumber);
	
	public List<PeopleAssignmentDto> searchPeopleAssignment(List<String> employeeNumbers,Date appraisalPeriodStart);
	
	public EaReportRole searchNextOfficerRoleByIdAndCurrentRptStatus(Long rptId ,String currentRptStatus);
	
	public EaReportRole searchNextOfficerByStatus(Long rptId, String currentRptStatus,String rountingTo);

	
	public EaReportRole searchPreviousOfficerRoleByIdAndCurrentRptStatus(Long rptId ,String currentRptStatus);


	public Pem001RDto searchPemByEmployeeNum(String currentUsername, String string);
	
	public List<EaReportRole> batchSearch(List<Long> appraisees, String roleAbbrCd);

	public List<EaReport> batchSearchReport(List<Long> appraisees);
	
	public List<EaObjectives> batchResetObjectivesInfo(List<Long> reportIds);
	
	public List<EaCoreCompetency> batchResetCoreCometencyInfo(List<Long> reportIds);

	public List<EaTrainPlan> batchResetTrainPlanInfo(List<Long> reportIds);

	public List<BatchEnquiryDto> searchAppraiseeBatchList(String batchName);
	
	public EaWorkflow searchReportTemplateByReportId(Long rptId);

	public List<EaReport> searchSpecialReports(String rank, String postUnit);

	public EaReport searchByEmployeeNumAndCommDate(String empNum, Date commenceDate);
	
	//added on 20170320 size limit for unicode characters
	public List<UserTableColumn> searchColumnSize(String mode);
	//end added on 20170320
}
