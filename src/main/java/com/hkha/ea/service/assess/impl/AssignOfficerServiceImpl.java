package com.hkha.ea.service.assess.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraisalAssessmentDAO;
import com.hkha.ea.dao.assess.AppraiseeBatchEnquiryDAO;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.assess.AppraiseeReportMaintenanceDAO;
import com.hkha.ea.dao.assess.AssignOfficerDAO;
import com.hkha.ea.dao.workflow.WorkflowTemplateMaintenanceDAO;
import com.hkha.ea.domain.EaBatch;
import com.hkha.ea.domain.EaContract;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.domain.EaWorkflowRole;
import com.hkha.ea.dto.assess.AssignOfficerDto;
import com.hkha.ea.dto.assess.AssignofficerPartialDto;
import com.hkha.ea.dto.assess.Pem001RDto;
import com.hkha.ea.dto.assess.PeopleAssignmentDto;
import com.hkha.ea.dto.assess.ReportRoleDto;
import com.hkha.ea.dto.assess.SearchAppraiseeDto;
import com.hkha.ea.dto.common.ReportUserRole;
import com.hkha.ea.service.assess.AssignOfficerService;

@Service("assignOfficerService")
public class AssignOfficerServiceImpl implements AssignOfficerService{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AssignOfficerServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private AppraisalAssessmentDAO appraisalAssessmentDAO;
	
	@Autowired
	private WorkflowTemplateMaintenanceDAO workflowTemplateMaintenanceDAO;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	@Autowired
	private AppraiseeBatchEnquiryDAO appraiseeBatchEnquiryDAO;
	
	@Autowired
	private AssignOfficerDAO assignOfficerDAO;
	
	@Autowired
	private AppraiseeReportMaintenanceDAO appraiseeReportMaintenanceDAO;

	public ReportUserRole checkRole(String reportID) {
		if(!Util.isEmptyString(reportID)){
			long rpId = Long.valueOf(reportID);
			String currentUsername=SecurityContextHolder.getContext().getAuthentication().getName();
			return appraisalAssessmentDAO.getReportUserRoleByReportIdAndUserId(rpId, currentUsername);
		}
		return new ReportUserRole();
	}
	
	/**
	 * Initializing empty data source to handle new inputs
	 * 
	 * @param wfId
	 * @param commenceDate
	 * @throws Exception
	 */
	public AssignOfficerDto pageInitialize(Long wfId) throws Exception {
		
		AssignOfficerDto dto = new AssignOfficerDto();
			List<EaWorkflowRole> dsWFRole = workflowTemplateMaintenanceDAO.searchByWorkflowAndRole(wfId, null);
			//============== Prepare for insert into HA_HR_EA_REPORT_ROLE
			// ================
			for (int row = 0; row < dsWFRole.size(); row++) {
				if(dsWFRole.get(row) != null){
					String role = dsWFRole.get(row).getRole();
					if (Util.isEqual(role, Constants.ROLE_ABBR_AO)) {
						ReportRoleDto ao = this.copyDataFromWorkflowTemplate(role,dsWFRole.get(row));
						dto.setReportRoleAO(ao);
					}else if (Util.isEqual(role, Constants.ROLE_ABBR_CO)) {
						ReportRoleDto co = this.copyDataFromWorkflowTemplate(role,dsWFRole.get(row));
						dto.setReportRoleCO(co);
					}else if (Util.isEqual(role, Constants.ROLE_ABBR_IO)) {
						ReportRoleDto io = this.copyDataFromWorkflowTemplate(role,dsWFRole.get(row));
						dto.setReportRoleIO(io);
					}else if (Util.isEqual(role, Constants.ROLE_ABBR_EO)) {
						ReportRoleDto eo = this.copyDataFromWorkflowTemplate(role,dsWFRole.get(row));
						dto.setReportRoleEO(eo);
					}else if (Util.isEqual(role, Constants.ROLE_ABBR_RO)) {
						ReportRoleDto ro = this.copyDataFromWorkflowTemplate(role,dsWFRole.get(row));
						dto.setReportRoleRO(ro);
					}else if (Util.isEqual(role, Constants.ROLE_ABBR_CD)) {
						ReportRoleDto cd = this.copyDataFromWorkflowTemplate(role,dsWFRole.get(row));
						dto.setReportRoleCD(cd);
					}
				}

			}
			return dto;
		}

	/**
	 * Copy data from Workflow Template to Report Role
	 * 
	 * @param fromRow
	 * @param toRow
	 * @param dsSource
	 * @param dsTarget
	 * @throws Exception
	 */
	public ReportRoleDto copyDataFromWorkflowTemplate(String role,EaWorkflowRole dsSource)
			throws Exception {
		ReportRoleDto dto = new ReportRoleDto();
		dto.setFirstReminder(dsSource.getFirstReminder());
		dto.setSecondReminder(dsSource.getSecondReminder());
		dto.setThirdReminder(dsSource.getThirdReminder());
		dto.setSubsReminder(dsSource.getSubsReminder());
		dto.setFirstRemInterval(dsSource.getFirstRemInterval());
		dto.setSecondRemInterval(dsSource.getSecondRemInterval());
		dto.setThirdRemInterval(dsSource.getThirdRemInterval());
		//edited on 20180302
		//dto.setSubsRemInterval(dsSource.getSecondRemInterval());
		dto.setSubsRemInterval(dsSource.getSubsRemInterval());
		//end edited on 20180302
		dto.setDeadline(Util.date2String(dsSource.getDeadline()));
		dto.setNotification(dsSource.getNotification());
		dto.setRole(role);
		dto.setRoleChecked(true);
		dto.setRoleSequence(dsSource.getRoleSequence());
		
		return dto;
	}

	/**
	 * Enquiry report roles' detail from session by report ID.
	 * 
	 * Post-condition: 
	 * The report id is stored in <code>(Double)((Vector)Session[Constants.SESS_SELECTED_APPRAISEE]).element[0]</code>
	 * 
	 * @throws Exception
	 */
	public AssignOfficerDto pageEnquiry(List<String> appraisees) {
		AssignOfficerDto dto = new AssignOfficerDto();
		if(!Util.isEmptyString(appraisees.get(0))){
			EaReport rp = appraiseeCommonSearchDAO.findEareportById(Long.valueOf(appraisees.get(0)));
			//set value 
			dto.setBatchName(rp.getBatchId());
			dto.setOldBatchName(rp.getBatchId());
			dto.setDeadline(Util.date2String(rp.getOverallDeadline()));
			dto.setAppraiseeDeadline(Util.date2String(rp.getAppraiseeDeadline()));
			dto.setCommenceDate(Util.date2String(rp.getCommenceDate()));
			dto.setEndDate(Util.date2String(rp.getEndDate()));
			if(rp.getWorkflowTemplateId() != 0){
				EaWorkflow e = workflowTemplateMaintenanceDAO.searchWorkflowInfo(rp.getWorkflowTemplateId());
				if(e != null)
					dto.setReportTemplate(e.getReportTemplate());
			}
						
			ReportRoleDto cd = this.retrieveRoleDetail(Long.valueOf(appraisees.get(0)), Constants.ROLE_ABBR_CD);
			ReportRoleDto co = this.retrieveRoleDetail(Long.valueOf(appraisees.get(0)), Constants.ROLE_ABBR_CO);
			ReportRoleDto ao = this.retrieveRoleDetail(Long.valueOf(appraisees.get(0)), Constants.ROLE_ABBR_AO);
			ReportRoleDto io = this.retrieveRoleDetail(Long.valueOf(appraisees.get(0)), Constants.ROLE_ABBR_IO);
			ReportRoleDto eo = this.retrieveRoleDetail(Long.valueOf(appraisees.get(0)), Constants.ROLE_ABBR_EO);
			ReportRoleDto ro = this.retrieveRoleDetail(Long.valueOf(appraisees.get(0)), Constants.ROLE_ABBR_RO);
			
			dto.setReportRoleCD(cd);
			dto.setReportRoleAO(ao);
			dto.setReportRoleCO(co);
			dto.setReportRoleIO(io);
			dto.setReportRoleEO(eo);
			if(ro != null){
				ro.setEmployeeNumROHO(rp.getHoId());
				ro.setEmployeeNameROHO(rp.getHoName());
				dto.setReportRoleRO(ro);
			}
		}
		
		return dto;
	}

	private ReportRoleDto retrieveRoleDetail(Long reportID, String roleAbbrRo) {
		ReportRoleDto ro = new ReportRoleDto();
		EaReportRole role = appraiseeCommonSearchDAO.searchReportRoleByReportIdandRole(reportID, roleAbbrRo);
		if(role != null){
			ro.setEmployeeNum(role.getOfficerId());
			ro.setEmployeeName(role.getOfficerName());
			ro.setOldEmployeeNum(role.getOfficerId());
			ro.setFirstReminder(role.getFirstReminder());
			ro.setSecondReminder(role.getSecondReminder());
			ro.setThirdReminder(role.getThirdReminder());
			ro.setSubsReminder(role.getSubsReminder());
			ro.setFirstRemInterval(role.getFirstRemInterval());
			ro.setSecondRemInterval(role.getSecondRemInterval());
			ro.setThirdRemInterval(role.getThirdRemInterval());
			//edited on 20180302 subsequent reminder interval does not match
			//ro.setSubsRemInterval(role.getSecondRemInterval());
			ro.setSubsRemInterval(role.getSubsRemInterval());
			//end edited on 20180302
			ro.setDeadline(Util.date2String(role.getDeadline()));
			ro.setNotification(role.getNotification());
			ro.setRole(role.getRole());
			ro.setRoleChecked(true);
			ro.setRoleSequence(role.getRoleSequence());
		}else{
			ro.setRoleChecked(false);
		}
	
		return ro;
	}

	public EaReport searchByReportID(String reportId) {
		return appraiseeCommonSearchDAO.searchByReportID(reportId);
	}

	public EaBatch searchByID(String batchId) {
		
		return appraiseeCommonSearchDAO.findEaBatchById(batchId);
	}

	@Transactional(rollbackFor=Exception.class)
	public String saveAction(List<String> appraisees,  AssignOfficerDto assignOfficerDto, SearchAppraiseeDto sac, EaReport rp, BindingResult result) throws Exception{
		
			Date trackDate = Util.string2SqlDate( sac.getTrackDateJmesa() );
			Date appraisalPeriodStart = Util.string2SqlDate( sac.getAppraisalPeriodStartJmesa() );
			Date appraisalPeriodEnd = Util.string2SqlDate( sac.getAppraisalPeriodEndJmesa() );
			String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
			
		
			// Store officer ID into a vector object
			List<String> officerNums = this.addOfficerNums(assignOfficerDto);
			AssignofficerPartialDto p = new AssignofficerPartialDto();
			EaBatch batch = appraiseeCommonSearchDAO.findEaBatchById(assignOfficerDto.getBatchName());
			if(batch != null){
				result.rejectValue("","error."+Constants.ERROR_BATCH_ALREADY_EXISTS);
				return "N";
			}
			
			// Retrieve appraisee HRMIS info
			Pem001RDto pemModel = appraiseeCommonSearchDAO.searchPemByEmployeeNum(currentUsername, appraisees.get(0));
			// Check unique constraints for report
			EaReport report = this.checkUniqueForReport(rp,pemModel);
			if(report != null){
				result.rejectValue("","error."+Constants.ERROR_REPORT_ALREADY_CREATED);
				return "N";
			}
			// Retrieve officer info
			this.retrieveOfficerInfo(officerNums,assignOfficerDto,p,appraisalPeriodStart, appraisalPeriodEnd,result);
			if(result.hasErrors()){
				return "N";
			}
			
			assignOfficerDAO.saveAction(currentUsername,appraisees,assignOfficerDto,rp,pemModel,p);
			return "Y";
	}

	private EaReport checkUniqueForReport(EaReport rp, Pem001RDto pemModel) {
		String empNum = String.valueOf(pemModel.getEmployeeNum());
		if (DateTimeUtil.compare(rp.getCommenceDate(), pemModel.getDateFirstHired()) < 0)
			rp.setCommenceDate(pemModel.getDateFirstHired());

		if (DateTimeUtil.compare(rp.getCommenceDate(), pemModel.getDateToRank()) < 0)
			rp.setCommenceDate(pemModel.getDateToRank());
		return appraiseeCommonSearchDAO.searchByEmployeeNumAndCommDate(empNum,rp.getCommenceDate());
		
	}

	@Transactional(rollbackFor=Exception.class)
	public String updateAction(List<String> appraisees,  AssignOfficerDto assignOfficerDto, BindingResult result,String mode)throws Exception{
		
			EaReport rp = appraiseeCommonSearchDAO.searchByReportID(appraisees.get(0));
			EaBatch batch = appraiseeCommonSearchDAO.findEaBatchById(rp.getBatchId());

			List<Long> reportIDs = this.convertToLongList(appraisees);
			
			String role= "";
			if(Constants.PAGE_ASSIGN_REMAINING.equals(mode))
				if(rp.getStatus().equalsIgnoreCase("CD")){
					role = Constants.STATUS_CD;
				}else{
					role = "SU";
				}
			else
				role = Constants.STATUS_GM;
			
			if(role.equals(Constants.STATUS_GM)||role.equals("SU")){
				boolean partialBat = appraiseeReportMaintenanceDAO.checkPartialBatch(reportIDs, assignOfficerDto.getBatchName());
				if(partialBat && Util.isEqual(assignOfficerDto.getBatchName(), batch.getBatchId())){
					result.rejectValue("", "error."+Constants.ERROR_REASSIGN_BATCH_NAME);
					return "N";
				}
			}

			// Store officer ID into a vector object
			List<String> officerNums = this.addOfficerNums(assignOfficerDto);
			AssignofficerPartialDto p = new AssignofficerPartialDto();
			//check batch is exist
			if(!assignOfficerDto.getOldBatchName().equals(assignOfficerDto.getBatchName())){
				EaBatch batch1 = appraiseeCommonSearchDAO.findEaBatchById(assignOfficerDto.getBatchName());
				if(batch1 != null){
					result.rejectValue("", "error."+Constants.ERROR_BATCH_ALREADY_EXISTS);
					return "N";
				}
			}

			// Retrieve officer rank and name info		
			this.retrieveOfficerInfo(officerNums,assignOfficerDto,p,DateTimeUtil.string2Date(assignOfficerDto.getCommenceDate()), DateTimeUtil.string2Date(assignOfficerDto.getEndDate()),result);
			if(result.hasErrors()){
				return "N";
			}

			assignOfficerDAO.updateAction(reportIDs,assignOfficerDto,p,rp,batch,role,mode);
			return "Y";
		
	}
	
	private List<String> addOfficerNums(AssignOfficerDto assignOfficerDto) {
		List<String> officerNums = new ArrayList<String>();
		if(!Util.isEmptyString(assignOfficerDto.getReportRoleCD().getEmployeeNum())){
			officerNums.add(assignOfficerDto.getReportRoleCD().getEmployeeNum());
		}
		if(!Util.isEmptyString(assignOfficerDto.getReportRoleAO().getEmployeeNum())){
			officerNums.add(assignOfficerDto.getReportRoleAO().getEmployeeNum());
		}
		if(!Util.isEmptyString(assignOfficerDto.getReportRoleCO().getEmployeeNum())){
			officerNums.add(assignOfficerDto.getReportRoleCO().getEmployeeNum());
		}
		if(!Util.isEmptyString(assignOfficerDto.getReportRoleIO().getEmployeeNum())){
			officerNums.add(assignOfficerDto.getReportRoleIO().getEmployeeNum());
		}
		if(!Util.isEmptyString(assignOfficerDto.getReportRoleEO().getEmployeeNum())){
			officerNums.add(assignOfficerDto.getReportRoleEO().getEmployeeNum());
		}
		if(!Util.isEmptyString(assignOfficerDto.getReportRoleRO().getEmployeeNum())){
			officerNums.add(assignOfficerDto.getReportRoleRO().getEmployeeNum());
		}
		if(!Util.isEmptyString(assignOfficerDto.getReportRoleRO().getEmployeeNumROHO())){
			officerNums.add(assignOfficerDto.getReportRoleRO().getEmployeeNumROHO());
		}
		return officerNums;
	}

	private List<Long> convertToLongList(List<String> appraisees) {
		List<Long> reportIDs = new ArrayList<Long>();
		for(int i=0; i<appraisees.size(); i++){
			if(!Util.isEmptyString(appraisees.get(i))){
				reportIDs.add(Long.valueOf(appraisees.get(i)));
			}
			
		}
		return reportIDs;
	}

	public List<PeopleAssignmentDto> searchPeopleAssignment(List<String> officerNums, Date appraisalPeriodStart) {
		return appraiseeCommonSearchDAO.searchPeopleAssignment(officerNums,appraisalPeriodStart);
	}

	/**
	 * Retrieve officer information from database based on track date
	 * 
	 * @param trackDate Track date
	 * @throws EAException
	 */
	public void retrieveOfficerInfo(List<String> officerNums,AssignOfficerDto dto,AssignofficerPartialDto p,Date appraisalPeriodStart, Date appraisalPeriodEnd,Errors errors) throws EAException {
		if(officerNums != null && officerNums.size() > 0){
			List<PeopleAssignmentDto> officerModel = appraiseeCommonSearchDAO.searchPeopleAssignment(officerNums,appraisalPeriodStart);
			int officerRow = 0;
			
			if(officerModel != null && officerModel.size() >0){
				if (!Util.isEmptyString(dto.getReportRoleCD().getEmployeeNum())) {
					officerRow = this.locateRow(dto.getReportRoleCD().getEmployeeNum(),officerModel);
					if (officerRow != -1) {
						p.setCdName(officerModel.get(officerRow).getEngName());
						p.setCdRank(officerModel.get(officerRow).getEngRank());
					} else
						errors.rejectValue("", "error."+Constants.ERROR_CD_INFO_NOT_FOUND);
				}

				if (!Util.isEmptyString(dto.getReportRoleAO().getEmployeeNum())) {
					officerRow = this.locateRow(dto.getReportRoleAO().getEmployeeNum(),officerModel);
					if (officerRow != -1) {
					    //
					    // Rule 3. Staff joining HA after Appraisal Period End Date cannot be selected for AP and AO.
					    //
					    Date firstHiredDate = DateTimeUtil.string2Date(officerModel.get(officerRow).getAttribute17());
					    if (firstHiredDate.compareTo(appraisalPeriodEnd) > 0)
					    	errors.rejectValue("", "error."+Constants.ERROR_AO_JOIN_AFTER_APP_END_DATE);

					    p.setAoName(officerModel.get(officerRow).getEngName());
						p.setAoRank(officerModel.get(officerRow).getEngRank());
					} else
						errors.rejectValue("", "error."+Constants.ERROR_AO_INFO_NOT_FOUND);
				}

				if (!Util.isEmptyString(dto.getReportRoleCO().getEmployeeNum())) {
					officerRow = this.locateRow(dto.getReportRoleCO().getEmployeeNum(),officerModel);
					if (officerRow != -1) {
						p.setCoName(officerModel.get(officerRow).getEngName());
						p.setCoRank(officerModel.get(officerRow).getEngRank());
					} else
						errors.rejectValue("", "error."+Constants.ERROR_CO_INFO_NOT_FOUND);
				}

				if (!Util.isEmptyString(dto.getReportRoleIO().getEmployeeNum())) {
					officerRow = this.locateRow(dto.getReportRoleIO().getEmployeeNum(),officerModel);
					if (officerRow != -1) {
						p.setIoName(officerModel.get(officerRow).getEngName());
						p.setIoRank(officerModel.get(officerRow).getEngRank());
					} else
						errors.rejectValue("", "error."+Constants.ERROR_IO_INFO_NOT_FOUND);
				}

				if (!Util.isEmptyString(dto.getReportRoleEO().getEmployeeNum())) {
					officerRow = this.locateRow(dto.getReportRoleEO().getEmployeeNum(),officerModel);
					if (officerRow != -1) {
						p.setEoName(officerModel.get(officerRow).getEngName());
						p.setEoRank(officerModel.get(officerRow).getEngRank());
					} else
						errors.rejectValue("", "error."+Constants.ERROR_EO_INFO_NOT_FOUND);
				}

				if (!Util.isEmptyString(dto.getReportRoleRO().getEmployeeNum())) {
					officerRow = this.locateRow(dto.getReportRoleRO().getEmployeeNum(),officerModel);
					if (officerRow != -1) {
						p.setRoName(officerModel.get(officerRow).getEngName());
						p.setRoRank(officerModel.get(officerRow).getEngRank());
					} else
						errors.rejectValue("", "error."+Constants.ERROR_RO_INFO_NOT_FOUND);
				}

				if (!Util.isEmptyString(dto.getReportRoleRO().getEmployeeNumROHO())) {
					officerRow = this.locateRow(dto.getReportRoleRO().getEmployeeNumROHO(),officerModel);
					if (officerRow != -1) {
						p.setHoName(officerModel.get(officerRow).getEngName());
						p.setHoRank(officerModel.get(officerRow).getEngRank());
					} else
						errors.rejectValue("", "error."+Constants.ERROR_HO_INFO_NOT_FOUND);
				}
			}
		}
	}
	
	public int locateRow(String employeeNumber,List<PeopleAssignmentDto> officerModel) throws EAException
	 {
		try
		{
			int i = 0;
			boolean found = false;
			while (!found && i < officerModel.size())
			{
				if (employeeNumber.equals(officerModel.get(i).getEmployeeNum()))
					found = true;
				else
					i++;
			}
			return found? i : -1;
		}
		catch(Exception e)
		{
			throw new EAException(e);
		}
	 }
	
}
