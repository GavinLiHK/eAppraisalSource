package com.hkha.ea.dao.assess.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.assess.AssignOfficerDAO;
import com.hkha.ea.dao.common.EmployeeEnquiryDAO;
import com.hkha.ea.dao.common.NotificationManagerDAO;
import com.hkha.ea.domain.DummyEmployee;
import com.hkha.ea.domain.DummyReportInfo;
import com.hkha.ea.domain.EaBatch;
import com.hkha.ea.domain.EaContract;
import com.hkha.ea.domain.EaCoreCompetency;
import com.hkha.ea.domain.EaMemoDetails;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.domain.EaObjectives;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.domain.EaTrainPlan;
import com.hkha.ea.dto.assess.AssignOfficerDto;
import com.hkha.ea.dto.assess.AssignofficerPartialDto;
import com.hkha.ea.dto.assess.Pem001RDto;
import com.hkha.ea.dto.assess.ReportRoleDto;
import com.hkha.ea.dto.common.EmployeeEnquiryDTO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoAO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoCD;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoCO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoEO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoIO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoRO;
import com.hkha.ea.service.common.NotificationManagerService;

@Repository("assignOfficerDAO")
public class AssignOfficerDAOImpl implements AssignOfficerDAO {

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AssignOfficerDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out

	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private EmployeeEnquiryDAO employeeEnquiryDAO;

	@Autowired
	private NotificationManagerDAO notificationManagerDAO;

	@Autowired
	private NotificationManagerService notificationManagerService;


	public void saveAction(String currentUsername, List<String> appraisees, AssignOfficerDto assignOfficerDto, EaReport rp, Pem001RDto pemModel, 
			AssignofficerPartialDto p) throws Exception {
		
		String reportTemplate = assignOfficerDto.getReportTemplate();
		
		// insert batch
		this.insertBatch(currentUsername, assignOfficerDto);
		
		

		// insert first report record
		this.insertReport(currentUsername, appraisees, rp, assignOfficerDto, pemModel, p);
		long nextReportId = rp.getReportId();

		//this.freezeAduitLog((int) nextReportId, "GM");

		//insert Report Role
		this.insertReportRole(currentUsername, nextReportId, appraisees, rp, assignOfficerDto, pemModel, p);

		this.insertCompetency(currentUsername, nextReportId, pemModel);
		if (Util.isEqual(Constants.WORKFLOW_REPORT_TEMPLATE_MEMO, reportTemplate)) {
			this.insertMemoDetail(currentUsername, p,pemModel,nextReportId);
			//this.insertCompetency(currentUsername, nextReportId, pemModel);
		} else {
			this.insertContract(currentUsername, nextReportId, pemModel, p);
		}

		// #2
		// Other record
		//EaReport dsReportBatch = new EaReport();
		if (appraisees != null && appraisees.size() > 1) {
			for (int i = 1; i < appraisees.size(); i++) {
				EaReport dsReportBatch = new EaReport();
				Pem001RDto otherPemModel = appraiseeCommonSearchDAO.searchPemByEmployeeNum(currentUsername,
						appraisees.get(i));
				
				dsReportBatch.setEmployeeNumber(appraisees.get(i));
				this.copyDataFromReportModel(rp, dsReportBatch, true);
				this.ReportCopyDataFromPem001R(dsReportBatch, otherPemModel);
				hibernateTemplate.save(dsReportBatch);
				nextReportId = dsReportBatch.getReportId();
				//insert Report Role
				this.insertReportRole(currentUsername, nextReportId, appraisees, rp, assignOfficerDto, pemModel, null);
				
				this.insertCompetency(currentUsername, nextReportId, otherPemModel);
				if (Util.isEqual(Constants.WORKFLOW_REPORT_TEMPLATE_MEMO, reportTemplate)) {
					this.insertMemoDetail(currentUsername, p,otherPemModel,nextReportId);
					//this.insertCompetency(currentUsername, nextReportId, otherPemModel);
				} else {
					this.insertContract(currentUsername, nextReportId, otherPemModel, p);
				}
			}
		}

		for (int j = 0; j < appraisees.size(); j++) {
			Double reportID = Double.valueOf(appraisees.get(j));
			//this.freezeAduitLog(reportID.intValue(), "GM");
		}
	}

	private void insertContract(String currentUsername, long nextReportId, Pem001RDto pemModel, AssignofficerPartialDto p) {
		EaContract reportContentModel = new EaContract();
		this.contractCopyDataFromPem001R(reportContentModel, pemModel, nextReportId);
		this.setContractOfficerInfo(reportContentModel, p);
		
		reportContentModel.setCreatedBy(currentUsername);
		reportContentModel.setCreationDate(new Date());
		hibernateTemplate.save(reportContentModel);
		
	}

	private void insertCompetency(String currentUsername, long nextReportId, Pem001RDto pemModel) {
		List<EaCoreCompetency> competencyModel = new ArrayList<EaCoreCompetency>();
		competencyModel = this.competencyCopyDataFromPem001R(pemModel, nextReportId);
		if (competencyModel != null && competencyModel.size() > 0) {
			for (int i = 0; i < competencyModel.size(); i++) {
				EaCoreCompetency core = competencyModel.get(i);
				core.setCreatedBy(currentUsername);
				core.setCreationDate(new Date());
				hibernateTemplate.save(core);
			}
		}
		
	}

	private void insertMemoDetail(String currentUsername, AssignofficerPartialDto p, Pem001RDto pemModel, long nextReportId) {
		EaMemoDetails reportContentModel = new EaMemoDetails();
		this.memoCopyDataFromPem001R(reportContentModel, pemModel, nextReportId);
		this.setMemoOfficerInfo(reportContentModel, p);
		reportContentModel.setCreatedBy(currentUsername);
		reportContentModel.setCreationDate(new Date());
		hibernateTemplate.save(reportContentModel);
		
	}

	private void insertReportRole(String currentUsername, long nextReportId, List<String> appraisees, EaReport rp,
			AssignOfficerDto assignOfficerDto, Pem001RDto pemModel, AssignofficerPartialDto p) {
		// Set report role info for the first appraisal report
		List<EaReportRole> roles = this.copyDataFromReportRoleModel(nextReportId, assignOfficerDto, p);
		if (roles != null && roles.size() > 0) {
			for (int i = 0; i < roles.size(); i++) {
				EaReportRole role = roles.get(i);
				role.setCreatedBy(currentUsername);
				role.setCreationDate(new Date());
				hibernateTemplate.save(role);
			}
		}
	}

	private void insertReport(String currentUsername, List<String> appraisees, EaReport rp,
			AssignOfficerDto assignOfficerDto, Pem001RDto pemModel, AssignofficerPartialDto p) {
		rp.setEmployeeNumber(appraisees.get(0));
		rp.setHoName(p.getHoName());
		//edited on 20170831 missing ho_id
		rp.setHoId(assignOfficerDto.getReportRoleRO().getEmployeeNumROHO());
		//end edited on 20170831 missing ho_id
		rp.setOverallDeadline(Util.string2Date(assignOfficerDto.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
		if(assignOfficerDto.getAppraiseeDeadline() != null)
			rp.setAppraiseeDeadline(Util.string2Date(assignOfficerDto.getAppraiseeDeadline(), Constants.DISPLAY_DATE_FORMAT));
		
		rp.setBatchId(assignOfficerDto.getBatchName());
		this.ReportCopyDataFromPem001R(rp, pemModel);
		rp.setCreatedBy(currentUsername);
		rp.setCreationDate(new Date());
		hibernateTemplate.save(rp);

	}

	private void insertBatch(String currentUsername, AssignOfficerDto assignOfficerDto) {
		EaBatch batchModel = new EaBatch();
		batchModel.setBatchId(assignOfficerDto.getBatchName());
		batchModel.setCreatedBy(currentUsername);
		batchModel.setCreationDate(new Date());
		hibernateTemplate.save(batchModel);
	}

	private void setContractOfficerInfo(EaContract reportContentModel, AssignofficerPartialDto p) {
		if (p != null) {
			reportContentModel.setPerfAoName(p.getAoName());
			reportContentModel.setPerfAoRank(p.getAoRank());
			reportContentModel.setCompAoName(p.getAoName());
			reportContentModel.setCompAoRank(p.getAoRank());
			reportContentModel.setAsseCoName(p.getCoName());
			reportContentModel.setAsseCoRank(p.getCoRank());
			reportContentModel.setIntvIoName(p.getIoName());
			reportContentModel.setIntvIoRank(p.getIoRank());
			reportContentModel.setAsseEoName(p.getEoName());
			reportContentModel.setAsseEoRank(p.getEoRank());
			reportContentModel.setReviRoName(p.getRoName());
			reportContentModel.setReviRoRank(p.getRoRank());
		}

	}

	/**
	 * Copy data from Report
	 * 
	 * @param rp
	 *            Data source of source
	 * @param dsReportBatch
	 *            Data source of destination
	 * @throws Exception
	 */
	private void copyDataFromReportModel(EaReport rp, EaReport dsReportBatch, boolean copyAppraisalPeriodToo) {
		dsReportBatch.setWorkflowTemplateId(rp.getWorkflowTemplateId());
		if (copyAppraisalPeriodToo) {
			dsReportBatch.setCommenceDate(rp.getCommenceDate());
			dsReportBatch.setEndDate(rp.getEndDate());
		}
		dsReportBatch.setBatchId(rp.getBatchId());
		dsReportBatch.setHoId(rp.getHoId());
		dsReportBatch.setHoName(rp.getHoName());
		dsReportBatch.setOverallDeadline(rp.getOverallDeadline());
		dsReportBatch.setStatus(rp.getStatus());
		dsReportBatch.setTrackDate(rp.getTrackDate());
		dsReportBatch.setAssigned(rp.getAssigned());
		dsReportBatch.setAppraiseeDeadline(rp.getAppraiseeDeadline());
	}

	private List<EaCoreCompetency> competencyCopyDataFromPem001R(Pem001RDto pemModel, long nextReportId) {
		long count = 0;

		List<EaCoreCompetency> list = new ArrayList<EaCoreCompetency>();
		List<String[]> pe = this.pemConverToList(pemModel);

		if (pe != null && pe.size() > 0) {
			for (int i = 0; i < pe.size(); i++) {
				EaCoreCompetency com = new EaCoreCompetency();
				long competencyCode = Long.valueOf(pe.get(i)[1]);
				String competencyContent = pe.get(i)[0];
				com.setReportId(nextReportId);
				com.setSeqNo(count++);
				com.setCompetencyId(competencyCode);
				com.setCompetency(competencyContent);
				list.add(com);
			}
		}

		return list;
	}

	private List<String[]> pemConverToList(Pem001RDto pemModel) {

		List<String[]> pe = new ArrayList<String[]>();
		if (pemModel != null) {
			if (!Util.isEmptyString(pemModel.getCompName1())) {
				String[] s = new String[] { pemModel.getCompName1(), String.valueOf(pemModel.getCompCode1()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName2())) {
				String[] s = new String[] { pemModel.getCompName2(), String.valueOf(pemModel.getCompCode2()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName3())) {
				String[] s = new String[] { pemModel.getCompName3(), String.valueOf(pemModel.getCompCode3()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName4())) {
				String[] s = new String[] { pemModel.getCompName4(), String.valueOf(pemModel.getCompCode4()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName5())) {
				String[] s = new String[] { pemModel.getCompName5(), String.valueOf(pemModel.getCompCode5()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName6())) {
				String[] s = new String[] { pemModel.getCompName6(), String.valueOf(pemModel.getCompCode6()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName7())) {
				String[] s = new String[] { pemModel.getCompName7(), String.valueOf(pemModel.getCompCode7()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName8())) {
				String[] s = new String[] { pemModel.getCompName8(), String.valueOf(pemModel.getCompCode8()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName1())) {
				String[] s = new String[] { pemModel.getCompName9(), String.valueOf(pemModel.getCompCode9()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName10())) {
				String[] s = new String[] { pemModel.getCompName10(), String.valueOf(pemModel.getCompCode10()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName11())) {
				String[] s = new String[] { pemModel.getCompName11(), String.valueOf(pemModel.getCompCode11()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName12())) {
				String[] s = new String[] { pemModel.getCompName12(), String.valueOf(pemModel.getCompCode12()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName13())) {
				String[] s = new String[] { pemModel.getCompName13(), String.valueOf(pemModel.getCompCode13()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName14())) {
				String[] s = new String[] { pemModel.getCompName14(), String.valueOf(pemModel.getCompCode14()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName15())) {
				String[] s = new String[] { pemModel.getCompName15(), String.valueOf(pemModel.getCompCode15()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName16())) {
				String[] s = new String[] { pemModel.getCompName16(), String.valueOf(pemModel.getCompCode16()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName17())) {
				String[] s = new String[] { pemModel.getCompName17(), String.valueOf(pemModel.getCompCode17()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName18())) {
				String[] s = new String[] { pemModel.getCompName18(), String.valueOf(pemModel.getCompCode18()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName19())) {
				String[] s = new String[] { pemModel.getCompName19(), String.valueOf(pemModel.getCompCode19()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName20())) {
				String[] s = new String[] { pemModel.getCompName20(), String.valueOf(pemModel.getCompCode20()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName21())) {
				String[] s = new String[] { pemModel.getCompName21(), String.valueOf(pemModel.getCompCode21()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName22())) {
				String[] s = new String[] { pemModel.getCompName22(), String.valueOf(pemModel.getCompCode22()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName23())) {
				String[] s = new String[] { pemModel.getCompName23(), String.valueOf(pemModel.getCompCode23()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName24())) {
				String[] s = new String[] { pemModel.getCompName24(), String.valueOf(pemModel.getCompCode24()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName25())) {
				String[] s = new String[] { pemModel.getCompName25(), String.valueOf(pemModel.getCompCode25()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName26())) {
				String[] s = new String[] { pemModel.getCompName26(), String.valueOf(pemModel.getCompCode26()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName27())) {
				String[] s = new String[] { pemModel.getCompName27(), String.valueOf(pemModel.getCompCode27()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName28())) {
				String[] s = new String[] { pemModel.getCompName28(), String.valueOf(pemModel.getCompCode28()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName29())) {
				String[] s = new String[] { pemModel.getCompName29(), String.valueOf(pemModel.getCompCode29()) };
				pe.add(s);
			}
			if (!Util.isEmptyString(pemModel.getCompName30())) {
				String[] s = new String[] { pemModel.getCompName30(), String.valueOf(pemModel.getCompCode30()) };
				pe.add(s);
			}
		}

		return pe;
	}

	private void setMemoOfficerInfo(EaMemoDetails reportContentModel, AssignofficerPartialDto p) {
		reportContentModel.setMemoFrom(p.getAoName());
		reportContentModel.setAoName(p.getAoName());
		reportContentModel.setAoRank(p.getAoRank());
		reportContentModel.setCoName(p.getCoName());
		reportContentModel.setCoRank(p.getCoRank());
		reportContentModel.setIoName(p.getIoName());
		reportContentModel.setIoRank(p.getIoRank());
	}

	private void memoCopyDataFromPem001R(EaMemoDetails reportContentModel, Pem001RDto pemModel, long nextReportId) {
		if (pemModel != null) {
			reportContentModel.setReportId(nextReportId);
			reportContentModel.setTelNo("");
			reportContentModel.setMemoFromDate(DateTimeUtil.date2String(DateTimeUtil.getTodayDate()));
			reportContentModel.setAppName(pemModel.getEngName());
			reportContentModel.setMemoTo(Constants.MESSAGE_DEFAULT_MEMO_TO);
		}
	}

	private void contractCopyDataFromPem001R(EaContract reportContentModel, Pem001RDto pemModel, long nextReportId) {
		reportContentModel.setReportId(nextReportId);
		if (pemModel != null) {
			reportContentModel.setPerfAppName(pemModel.getEngName());
			reportContentModel.setPerfAppRank(pemModel.getRank());
			reportContentModel.setIntvAppName(pemModel.getEngName());
			reportContentModel.setIntvAppRank(pemModel.getRank());
		}

		reportContentModel.setCommendation(Constants.MESSAGE_COMMENDATIONS_DEFAULT);
	}

	private List<EaReportRole> copyDataFromReportRoleModel(long nextReportId, AssignOfficerDto assignOfficerDto,
			AssignofficerPartialDto pa) {
		List<EaReportRole> list = new ArrayList<EaReportRole>();
		if (assignOfficerDto.getReportRoleAO() != null && assignOfficerDto.getReportRoleAO().isRoleChecked()) {
			ReportRoleDto ao = assignOfficerDto.getReportRoleAO();
			EaReportRole role = new EaReportRole();
			BeanUtils.copyProperties(ao, role);
			if (nextReportId != 0L) {
				role.setReportId(nextReportId);
			}

			role.setRole("AO");
			role.setOfficerId(ao.getEmployeeNum());
			if (pa != null){
				role.setOfficerName(pa.getAoName());
			}else{
				role.setOfficerName(ao.getEmployeeName());
			}
				
			role.setOfficerId(assignOfficerDto.getReportRoleAO().getEmployeeNum());
			
			role.setDeadline(Util.string2Date(ao.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
			list.add(role);
		}
		if (assignOfficerDto.getReportRoleCO() != null && assignOfficerDto.getReportRoleCO().isRoleChecked()) {
			ReportRoleDto co = assignOfficerDto.getReportRoleCO();
			EaReportRole role = new EaReportRole();
			BeanUtils.copyProperties(co, role);
			if (nextReportId != 0L) {
				role.setReportId(nextReportId);
			}

			role.setRole("CO");
			role.setOfficerId(co.getEmployeeNum());
			if (pa != null)
				role.setOfficerName(pa.getCoName());
			else
				role.setOfficerName(co.getEmployeeName());
			role.setOfficerId(assignOfficerDto.getReportRoleCO().getEmployeeNum());

			role.setDeadline(Util.string2Date(co.getDeadline(), Constants.DISPLAY_DATE_FORMAT));

			list.add(role);
		}
		if (assignOfficerDto.getReportRoleIO() != null && assignOfficerDto.getReportRoleIO().isRoleChecked()) {
			ReportRoleDto io = assignOfficerDto.getReportRoleIO();
			EaReportRole role = new EaReportRole();
			BeanUtils.copyProperties(io, role);

			if (nextReportId != 0L) {
				role.setReportId(nextReportId);
			}

			role.setRole("IO");
			role.setOfficerId(io.getEmployeeNum());
			if (pa != null)
				role.setOfficerName(pa.getIoName());
			else
				role.setOfficerName(io.getEmployeeName());
			role.setOfficerId(assignOfficerDto.getReportRoleIO().getEmployeeNum());

			role.setDeadline(Util.string2Date(io.getDeadline(), Constants.DISPLAY_DATE_FORMAT));

			list.add(role);
		}
		if (assignOfficerDto.getReportRoleEO() != null && assignOfficerDto.getReportRoleEO().isRoleChecked()) {
			ReportRoleDto eo = assignOfficerDto.getReportRoleEO();
			EaReportRole role = new EaReportRole();
			BeanUtils.copyProperties(eo, role);
			if (nextReportId != 0L) {
				role.setReportId(nextReportId);
			}

			role.setRole("EO");
			role.setOfficerId(eo.getEmployeeNum());
			if (pa != null)
				role.setOfficerName(pa.getEoName());
			else
				role.setOfficerName(eo.getEmployeeName());
			role.setOfficerId(assignOfficerDto.getReportRoleEO().getEmployeeNum());

			role.setDeadline(Util.string2Date(eo.getDeadline(), Constants.DISPLAY_DATE_FORMAT));

			list.add(role);
		}
		if (assignOfficerDto.getReportRoleRO() != null && assignOfficerDto.getReportRoleRO().isRoleChecked()) {
			ReportRoleDto ro = assignOfficerDto.getReportRoleRO();
			EaReportRole role = new EaReportRole();
			BeanUtils.copyProperties(ro, role);
			if (nextReportId != 0L) {
				role.setReportId(nextReportId);
			}

			role.setRole("RO");

			role.setOfficerId(ro.getEmployeeNum());
			if (pa != null)
				role.setOfficerName(pa.getRoName());
			else
				role.setOfficerName(ro.getEmployeeName());
			role.setDeadline(Util.string2Date(ro.getDeadline(), Constants.DISPLAY_DATE_FORMAT));

			list.add(role);
		}
		if (assignOfficerDto.getReportRoleCD() != null && assignOfficerDto.getReportRoleCD().isRoleChecked()) {
			ReportRoleDto cd = assignOfficerDto.getReportRoleCD();
			EaReportRole role = new EaReportRole();
			BeanUtils.copyProperties(cd, role);
			if (nextReportId != 0L) {
				role.setReportId(nextReportId);
			}

			role.setRole("CD");
			role.setOfficerId(cd.getEmployeeNum());
			if (pa != null)
				role.setOfficerName(pa.getCdName());
			else
				role.setOfficerName(cd.getEmployeeName());
			role.setDeadline(Util.string2Date(cd.getDeadline(), Constants.DISPLAY_DATE_FORMAT));

			list.add(role);
		}
		return list;
	}

	public void ReportCopyDataFromPem001R(EaReport rp, Pem001RDto pemModel) {
		if (pemModel != null) {
			rp.setName(pemModel.getEngName());
			rp.setSubstantiveRank(pemModel.getRank());
			rp.setEmployeeNumber(String.valueOf(pemModel.getEmployeeNum()));
			rp.setChineseName(pemModel.getChiName());
			rp.setFirstAppointmentRank(pemModel.getFirstRank());
			rp.setDateOfAppFirstRank(pemModel.getDateFirstHired());
			rp.setDateOfAppSubRank(pemModel.getDateToRank());
			rp.setPresentPost(pemModel.getPostUnit());
			rp.setDivision(pemModel.getDivisionSection());
			rp.setDateOfPosting(pemModel.getDateToPost());
			rp.setCurrentContractStartDate(pemModel.getConStartDate());
			rp.setCurrentContractEndDate(pemModel.getConEndDate());


			if (DateTimeUtil.compare(rp.getCommenceDate(), pemModel.getDateFirstHired()) < 0)
				rp.setCommenceDate(pemModel.getDateFirstHired());

			log.info("Commence Date:" + rp.getCommenceDate() + "\nDate To Rank:" + pemModel.getDateToRank());

			if (DateTimeUtil.compare(rp.getCommenceDate(), pemModel.getDateToRank()) < 0)
				rp.setCommenceDate(pemModel.getDateToRank());
		}
	}

	/**
	 * Freeze audit log, probably executing on submit report
	 * 
	 * @param conn
	 *            Instance of DBConnection
	 * @param reportID
	 *            Report ID
	 * @param rptStatus
	 *            Current report status
	 * @throws EAException
	 */
	public void freezeAduitLog(int reportID, String rptStatus) throws Exception {
		try {
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			CallableStatement proc2 = null;
			proc2 = conn.prepareCall("{call HA_HR_EAPPRAISAL.ha_report_audit_freeze(?,?)}");
			proc2.setInt(1, reportID);
			proc2.setString(2, rptStatus);
			proc2.execute();
			proc2.close();

			DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
		} catch (Exception e) {
			// e.printStackTrace();
			//log.severe(e.toString());
			throw new Exception(e);
		}
	}

	public void updateAction(List<Long> appraisees, AssignOfficerDto assignOfficerDto, AssignofficerPartialDto p,
			EaReport rp, EaBatch batch, String role, String mode) throws Exception {
		
		boolean changeStatus = false;
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// Retrieve report info from table for all selected appraisee
		List<EaReport> dsReportBatch = appraiseeCommonSearchDAO.batchSearchReport(appraisees);

		if (!Util.isEmptyString(assignOfficerDto.getReportRoleRO().getEmployeeNumROHO()))
			rp.setHoName(p.getHoName());
		else
			rp.setHoName("");
		
		// send email
		if (Constants.PAGE_ASSIGN_REMAINING.equals(mode) && Constants.STATUS_CD.equals(rp.getStatus())
				&& assignOfficerDto.getReportRoleAO() != null) {
			
			log.info("Dispatch to AO");
			this.sendEmail(assignOfficerDto, dsReportBatch, rp);
		}
		// Set the report status and assigned flag if it is called from
		// assign remaining officers
		
		if (Constants.PAGE_ASSIGN_REMAINING.equals(mode)) {
			rp.setAssigned(Constants.YES);
			if (rp.getStatus().equalsIgnoreCase(Constants.STATUS_CD)) {
				rp.setStatus(Constants.STATUS_AO);
				changeStatus = true;
			}
		}
		
		
		assignOfficerDto.setChangeStatus(changeStatus);
		//update Batch
		this.updateBatch(username,batch,assignOfficerDto);
		
		//update first Report
		this.updateReport(username,rp,assignOfficerDto);
		
		//update Report role for first report
		this.updateReportRole(username,rp,assignOfficerDto,p);
		
		//update Report Batch
		this.updateReportBatch(username, rp, dsReportBatch);

		if(appraisees.size()>1){
			// Copy report role info of the first report to the rest of the reports and insert into DB
			this.insertReportRoleBatch(appraisees, assignOfficerDto, p);
		}
		

		if (Util.isEqual(Constants.WORKFLOW_REPORT_TEMPLATE_MEMO, assignOfficerDto.getReportTemplate())) {
			this.updateMemoDetail(appraisees, username, assignOfficerDto, p);
		} else {
			this.updateContract(appraisees, username, assignOfficerDto, p);
		}

		for (int i = 0; i < appraisees.size(); i++) {
			Double reportID = Double.valueOf(appraisees.get(i));
			this.freezeAduitLog(reportID.intValue(), role);
		}

	}

	private void updateReportRole(String username, EaReport rp, AssignOfficerDto assignOfficerDto,
			AssignofficerPartialDto p) {
		List<EaReportRole> rolsList = this.copyDataFromReportRoleModel(rp.getReportId(), assignOfficerDto, p);
		if (rolsList != null && rolsList.size() > 0) {
			for (int i = 0; i < rolsList.size(); i++) {
				rolsList.get(i).setLastUpdatedBy(username);
				rolsList.get(i).setLastUpdateDate(new Date());
				hibernateTemplate.update(rolsList.get(i));
			}
		}
		
	}

	private void updateReportBatch(String username, EaReport rp, List<EaReport> dsReportBatch) {
		// Copy report info of the first report to the rest of the reports
				if (dsReportBatch != null && dsReportBatch.size() > 0) {
					for (int i = 0; i < dsReportBatch.size(); i++) {
						this.copyDataFromReportModel(rp, dsReportBatch.get(i), false);
						
						dsReportBatch.get(i).setLastUpdatedBy(username);
						dsReportBatch.get(i).setLastUpdateDate(new Date());;
						hibernateTemplate.update(dsReportBatch.get(i));
					}
				}
		
	}

	private void updateReport(String username, EaReport rp, AssignOfficerDto assignOfficerDto) {
		rp.setBatchId(assignOfficerDto.getBatchName());
		rp.setLastUpdatedBy(username);
		rp.setLastUpdateDate(new Date());
		rp.setOverallDeadline(Util.string2Date(assignOfficerDto.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
		if(assignOfficerDto.getAppraiseeDeadline() != null)
			rp.setAppraiseeDeadline(Util.string2Date(assignOfficerDto.getAppraiseeDeadline(), Constants.DISPLAY_DATE_FORMAT));
		if(assignOfficerDto.getReportRoleRO().isRoleChecked())
			rp.setHoId(assignOfficerDto.getReportRoleRO().getEmployeeNumROHO());
		hibernateTemplate.update(rp);
		
	}

	private void updateBatch(String username, EaBatch batch, AssignOfficerDto assignOfficerDto) {
		batch.setBatchId(assignOfficerDto.getBatchName());
		batch.setLastUpdatedBy(username);
		batch.setLastUpdateDate(new Date());
		//hibernateTemplate.update(batch);
		hibernateTemplate.saveOrUpdate(batch);
	}

	private void updateContract(List<Long> appraisees, String username, AssignOfficerDto assignOfficerDto,
			AssignofficerPartialDto p) {
		
		List<EaContract> reportContentModel = new ArrayList<EaContract>();
		reportContentModel = appraiseeCommonSearchDAO.findEaContractByIds(appraisees);
		if (reportContentModel != null && reportContentModel.size() > 0) {
			for (int i = 0; i < reportContentModel.size(); i++) {
				String overallCommentStr = "";
				String overallComment2Str = "";
				String reviewInterviewStr = "";
				EaContract con = reportContentModel.get(i);
			
				if (!Util.isEmptyString(con.getOverallComment())) {
					overallCommentStr = con.getOverallComment().substring(1, (int) con.getOverallComment().length());
				}
				if (!Util.isEmptyString(con.getOverallComment2())) {
					overallComment2Str = con.getOverallComment2().substring(1, (int) con.getOverallComment2().length());
				}
				if (!Util.isEmptyString(con.getReviewInterview())) {
					reviewInterviewStr = con.getReviewInterview().substring(1, (int) con.getReviewInterview().length());
				}

				con.setOverallComment(overallCommentStr);
				con.setOverallComment2(overallComment2Str);
				con.setReviewInterview(reviewInterviewStr);
			}
			this.officerIsChangedForContract(appraisees, assignOfficerDto, reportContentModel);
			// Set officer info to each contract info of reports
			for (int i = 0; i < reportContentModel.size(); i++) {
				if (reportContentModel.get(i) != null) {
					this.setContractOfficerInfo(reportContentModel.get(i), p);

					reportContentModel.get(i).setLastUpdatedBy(username);
					reportContentModel.get(i).setLastUpdateDate(new Date());
					hibernateTemplate.update(reportContentModel.get(i));
				}

			}
		}
	}

	private void updateMemoDetail(List<Long> appraisees, String username, AssignOfficerDto assignOfficerDto,
			AssignofficerPartialDto p) {

		List<EaObjectives> objectiveModel = new ArrayList<EaObjectives>();
		List<EaTrainPlan> trainPlanModel = new ArrayList<EaTrainPlan>();
		List<EaCoreCompetency> coreCompetencyModel = new ArrayList<EaCoreCompetency>();
		List<EaMemoDetails> reportContentModel = new ArrayList<EaMemoDetails>();

		reportContentModel = appraiseeCommonSearchDAO.findEaMemoDetailsByIds(appraisees);
		// Reset officer input if its associated officer is changed
		if (reportContentModel != null && reportContentModel.size() > 0) {
			this.officerIsChangedForMemo(appraisees, assignOfficerDto, reportContentModel, objectiveModel,
					trainPlanModel, coreCompetencyModel);
			// Set officer info to each contract info of reports

			for (int i = 0; i < reportContentModel.size(); i++) {
				if (reportContentModel.get(i) != null) {
					this.setMemoOfficerInfo(reportContentModel.get(i), p);
					reportContentModel.get(i).setLastUpdatedBy(username);
					reportContentModel.get(i).setLastUpdateDate(new Date());
					hibernateTemplate.update(reportContentModel.get(i));
				}
			}
		}
		if (objectiveModel != null && objectiveModel.size() > 0) {
			for (int i = 0; i < objectiveModel.size(); i++) {
				if (objectiveModel.get(i) != null) {
					objectiveModel.get(i).setLastUpdatedBy(username);
					objectiveModel.get(i).setLastUpdateDate(new Date());
					hibernateTemplate.update(objectiveModel.get(i));
				}
			}
		}
		if (trainPlanModel != null && trainPlanModel.size() > 0) {
			for (int i = 0; i < trainPlanModel.size(); i++) {
				if (trainPlanModel.get(i) != null) {
					trainPlanModel.get(i).setLastUpdatedBy(username);
					trainPlanModel.get(i).setLastUpdateDate(new Date());
					hibernateTemplate.update(trainPlanModel.get(i));
				}
			}
		}
		if (coreCompetencyModel != null && coreCompetencyModel.size() > 0) {
			for (int i = 0; i < coreCompetencyModel.size(); i++) {
				if (coreCompetencyModel.get(i) != null) {
					coreCompetencyModel.get(i).setLastUpdatedBy(username);
					coreCompetencyModel.get(i).setLastUpdateDate(new Date());
					hibernateTemplate.update(coreCompetencyModel.get(i));
				}
			}
		}

	}

	private void officerIsChangedForContract(List<Long> appraisees, AssignOfficerDto assignOfficerDto,
			List<EaContract> reportContentModel) {
		if (assignOfficerDto.getReportRoleAO() != null && !assignOfficerDto.getReportRoleAO().getOldEmployeeNum()
				.equals(assignOfficerDto.getReportRoleAO().getEmployeeNum())) {
			reportContentModel = this.batchResetAOInfo(reportContentModel);
		}
		if (assignOfficerDto.getReportRoleCO() != null && !assignOfficerDto.getReportRoleCO().getOldEmployeeNum()
				.equals(assignOfficerDto.getReportRoleCO().getEmployeeNum())) {
			reportContentModel = this.batchResetCOInfo(reportContentModel);
		}
		if (assignOfficerDto.getReportRoleIO() != null && !assignOfficerDto.getReportRoleIO().getOldEmployeeNum()
				.equals(assignOfficerDto.getReportRoleIO().getEmployeeNum())) {
			reportContentModel = this.batchResetIOInfo(reportContentModel);
		}
		if (assignOfficerDto.getReportRoleEO() != null && !assignOfficerDto.getReportRoleEO().getOldEmployeeNum()
				.equals(assignOfficerDto.getReportRoleEO().getEmployeeNum())) {
			reportContentModel = this.batchResetEOInfo(reportContentModel);
		}
		if (assignOfficerDto.getReportRoleRO() != null && !assignOfficerDto.getReportRoleRO().getOldEmployeeNum()
				.equals(assignOfficerDto.getReportRoleRO().getEmployeeNum())) {
			reportContentModel = this.batchResetROInfo(reportContentModel);
		}

	}

	private List<EaContract> batchResetROInfo(List<EaContract> reportContentModel) {
		List<EaContract> mList = new ArrayList<EaContract>();
		if (reportContentModel != null && reportContentModel.size() > 0) {
			for (int i = 0; i < reportContentModel.size(); i++) {
				if (reportContentModel.get(i) != null) {
					EaContract m = reportContentModel.get(i);
					m.setReviewPotential("");
					m.setReviewComment("");
					m.setPerformanceBonusRo("");
					m.setSalaryAdjustmentRo("");
					m.setReviRoDate("");
					mList.add(m);
				}
			}
		}
		return mList;
	}

	private List<EaContract> batchResetEOInfo(List<EaContract> reportContentModel) {
		List<EaContract> mList = new ArrayList<EaContract>();
		if (reportContentModel != null && reportContentModel.size() > 0) {
			for (int i = 0; i < reportContentModel.size(); i++) {
				if (reportContentModel.get(i) != null) {
					EaContract m = reportContentModel.get(i);
					m.setOverallPerfEo("");
					m.setPerformanceBonusEo("");
					m.setSalaryAdjustmentEo("");
					m.setEndorsingPotential("");
					//m.setWorkWell("");
					m.setWorkWellEo("");
					m.setAsseEoDate("");
					m.setAsseEoTel("");
					mList.add(m);
				}
			}
		}
		return mList;
	}

	private List<EaContract> batchResetIOInfo(List<EaContract> reportContentModel) {
		List<EaContract> mList = new ArrayList<EaContract>();
		if (reportContentModel != null && reportContentModel.size() > 0) {
			for (int i = 0; i < reportContentModel.size(); i++) {
				if (reportContentModel.get(i) != null) {
					EaContract m = reportContentModel.get(i);
					//edited on 20170223
					//m.setReviewInterview("");
					m.setXxreviewInterview("");
					m.setIntvIoDate("");
					mList.add(m);
				}
			}
		}
		return mList;
	}

	private List<EaContract> batchResetCOInfo(List<EaContract> reportContentModel) {
		List<EaContract> mList = new ArrayList<EaContract>();
		if (reportContentModel != null && reportContentModel.size() > 0) {
			for (int i = 0; i < reportContentModel.size(); i++) {
				if (reportContentModel.get(i) != null) {
					EaContract m = reportContentModel.get(i);
					m.setWorkWell("");
					m.setPerformanceBonusCo("");
					m.setOverallPerformance("");
					m.setSalaryAdjustmentCo("");
					m.setCounterPotential("");
					m.setAsseCoDate("");
					m.setAsseCoTel("");
					mList.add(m);
				}
			}
		}
		return mList;
	}

	private List<EaContract> batchResetAOInfo(List<EaContract> reportContentModel) {
		List<EaContract> mList = new ArrayList<EaContract>();
		if (reportContentModel != null && reportContentModel.size() > 0) {
			for (int i = 0; i < reportContentModel.size(); i++) {
				if (reportContentModel.get(i) != null) {
					EaContract m = reportContentModel.get(i);
					m.setOverallRating("");
					m.setOverallComment("");
					m.setSpecialFactors("");
					m.setPerformanceBonusAo("");
					m.setSalaryAdjustmentAo("");
					m.setAppraisePotential("");
					m.setCompAoDate("");
					m.setCompAoTel("");
					mList.add(m);
				}
			}
		}
		return mList;
	}

	private void officerIsChangedForMemo(List<Long> appraisees, AssignOfficerDto assignOfficerDto,
			List<EaMemoDetails> reportContentModel, List<EaObjectives> objectiveModel, List<EaTrainPlan> trainPlanModel,
			List<EaCoreCompetency> coreCompetencyModel) {
		if (assignOfficerDto.getReportRoleAO() != null && !assignOfficerDto.getReportRoleAO().getOldEmployeeNum()
				.equals(assignOfficerDto.getReportRoleAO().getEmployeeNum())) {
			reportContentModel = this.batchResetAOInfoMemo(reportContentModel);
			objectiveModel = appraiseeCommonSearchDAO.batchResetObjectivesInfo(appraisees);
			coreCompetencyModel = appraiseeCommonSearchDAO.batchResetCoreCometencyInfo(appraisees);
		}
		if (assignOfficerDto.getReportRoleCO() != null && !assignOfficerDto.getReportRoleCO().getOldEmployeeNum()
				.equals(assignOfficerDto.getReportRoleCO().getEmployeeNum())) {
			reportContentModel = this.batchResetCOInfoMemo(reportContentModel);
		}
		if (assignOfficerDto.getReportRoleIO() != null && !assignOfficerDto.getReportRoleIO().getOldEmployeeNum()
				.equals(assignOfficerDto.getReportRoleIO().getEmployeeNum())) {
			reportContentModel = this.batchResetIOInfoMemo(reportContentModel);
			trainPlanModel = appraiseeCommonSearchDAO.batchResetTrainPlanInfo(appraisees);
		}
	}

	private List<EaMemoDetails> batchResetIOInfoMemo(List<EaMemoDetails> reportContentModel) {
		List<EaMemoDetails> mList = new ArrayList<EaMemoDetails>();
		if (reportContentModel != null && reportContentModel.size() > 0) {
			for (int i = 0; i < reportContentModel.size(); i++) {
				if (reportContentModel.get(i) != null) {
					EaMemoDetails m = reportContentModel.get(i);
					m.setAppInterview("");
					m.setIoName("");
					m.setIoRank("");
					m.setIoDate(null);
					mList.add(m);
				}
			}
		}
		return mList;
	}

	private List<EaMemoDetails> batchResetCOInfoMemo(List<EaMemoDetails> reportContentModel) {
		List<EaMemoDetails> mList = new ArrayList<EaMemoDetails>();
		if (reportContentModel != null && reportContentModel.size() > 0) {
			for (int i = 0; i < reportContentModel.size(); i++) {
				if (reportContentModel.get(i) != null) {
					EaMemoDetails m = reportContentModel.get(i);
					m.setPerfBonusCo("");
					//m.setSalaryADJCo("");
					m.setSalaryAdjCo("");
					m.setCoName("");
					m.setCoRank("");
					m.setCoDate(null);
					mList.add(m);
				}
			}
		}
		return mList;
	}

	private List<EaMemoDetails> batchResetAOInfoMemo(List<EaMemoDetails> reportContentModel) {
		List<EaMemoDetails> mList = new ArrayList<EaMemoDetails>();
		if (reportContentModel != null && reportContentModel.size() > 0) {
			for (int i = 0; i < reportContentModel.size(); i++) {
				if (reportContentModel.get(i) != null) {
					EaMemoDetails m = reportContentModel.get(i);
					m.setOverallPerf("");
					m.setOverallComment("");
					m.setPerfBonusAo("");
					//m.setSalaryADJAo("");
					m.setSalaryAdjAo("");
					m.setAoName("");
					m.setAoRank("");
					m.setAppDate(null);
					mList.add(m);
				}
			}
		}
		return mList;
	}

	private void insertReportRoleBatch(List<Long> appraisees, AssignOfficerDto assignOfficerDto,
			AssignofficerPartialDto p) {
		// Retrieve report role info for each role of all report except the
		// first one
		List<EaReportRole> dsCDRoleBatch = appraiseeCommonSearchDAO.batchSearch(appraisees, Constants.ROLE_ABBR_CD);
		List<EaReportRole> dsAORoleBatch = appraiseeCommonSearchDAO.batchSearch(appraisees, Constants.ROLE_ABBR_AO);
		List<EaReportRole> dsCORoleBatch = appraiseeCommonSearchDAO.batchSearch(appraisees, Constants.ROLE_ABBR_CO);
		List<EaReportRole> dsIORoleBatch = appraiseeCommonSearchDAO.batchSearch(appraisees, Constants.ROLE_ABBR_IO);
		List<EaReportRole> dsEORoleBatch = appraiseeCommonSearchDAO.batchSearch(appraisees, Constants.ROLE_ABBR_EO);
		List<EaReportRole> dsRORoleBatch = appraiseeCommonSearchDAO.batchSearch(appraisees, Constants.ROLE_ABBR_RO);

		if (assignOfficerDto.getReportRoleCD() != null) {
			if (!containValueField(assignOfficerDto.getReportRoleCD().getEmployeeNum()))
				p.setCdName("");
			assignOfficerDto.getReportRoleCD().setEmployeeName(p.getCdName());
			if (dsCDRoleBatch != null && dsCDRoleBatch.size() > 0) {
				 this.updateReportRoleBatch(assignOfficerDto.getReportRoleCD(), dsCDRoleBatch);
				//this.batchCopyDataFromReportRoleModel(assignOfficerDto, dsCDRoleBatch);
			}
		}
		if (assignOfficerDto.getReportRoleAO() != null && assignOfficerDto.getReportRoleAO().isRoleChecked()) {
			if (!containValueField(assignOfficerDto.getReportRoleAO().getEmployeeNum())) {
				p.setAoName("");
				p.setAoRank("");
			}

			assignOfficerDto.getReportRoleAO().setEmployeeName(p.getAoName());
			if (dsAORoleBatch != null && dsAORoleBatch.size() > 0) {
				this.updateReportRoleBatch(assignOfficerDto.getReportRoleAO(), dsAORoleBatch);
				//this.batchCopyDataFromReportRoleModel(assignOfficerDto, dsAORoleBatch);
			}
		}
		if (assignOfficerDto.getReportRoleCO() != null && assignOfficerDto.getReportRoleCO().isRoleChecked()) {
			if (!containValueField(assignOfficerDto.getReportRoleCO().getEmployeeNum())) {
				p.setCoName("");
				p.setCoRank("");
			}

			assignOfficerDto.getReportRoleCO().setEmployeeName(p.getCoName());
			if (dsCORoleBatch != null && dsCORoleBatch.size() > 0) {
				this.updateReportRoleBatch(assignOfficerDto.getReportRoleCO(), dsCORoleBatch);
				//this.batchCopyDataFromReportRoleModel(assignOfficerDto, dsCORoleBatch);
			}
		}
		if (assignOfficerDto.getReportRoleIO() != null && assignOfficerDto.getReportRoleIO().isRoleChecked()) {
			
			if (!containValueField(assignOfficerDto.getReportRoleIO().getEmployeeNum())) {
				p.setIoName("");
				p.setIoRank("");
			}

			assignOfficerDto.getReportRoleIO().setEmployeeName(p.getIoName());
			//assignOfficerDto.getReportRoleIO().setEmployeeName(p.getAoName());
			//if (dsAORoleBatch != null && dsAORoleBatch.size() > 0)
			if (dsIORoleBatch != null && dsIORoleBatch.size() > 0) {
				this.updateReportRoleBatch(assignOfficerDto.getReportRoleIO(), dsIORoleBatch);
				//this.batchCopyDataFromReportRoleModel(assignOfficerDto, dsIORoleBatch);
			}
		}
		if (assignOfficerDto.getReportRoleEO() != null && assignOfficerDto.getReportRoleEO().isRoleChecked()) {
			if (!containValueField(assignOfficerDto.getReportRoleEO().getEmployeeNum())) {
				p.setEoName("");
				p.setEoRank("");
			}

			assignOfficerDto.getReportRoleEO().setEmployeeName(p.getEoName());
			//assignOfficerDto.getReportRoleEO().setEmployeeName(p.getAoName());
			//if (dsAORoleBatch != null && dsAORoleBatch.size() > 0)
			if (dsEORoleBatch != null && dsEORoleBatch.size() > 0) {
				this.updateReportRoleBatch(assignOfficerDto.getReportRoleEO(), dsEORoleBatch);
				//this.batchCopyDataFromReportRoleModel(assignOfficerDto, dsEORoleBatch);
			}
		}
		if (assignOfficerDto.getReportRoleRO() != null && assignOfficerDto.getReportRoleRO().isRoleChecked()) {
			if (!containValueField(assignOfficerDto.getReportRoleRO().getEmployeeNum())) {
				p.setRoName("");
				p.setRoRank("");
			}

			assignOfficerDto.getReportRoleRO().setEmployeeName(p.getRoName());
			//assignOfficerDto.getReportRoleRO().setEmployeeName(p.getAoName());
			//if (dsAORoleBatch != null && dsAORoleBatch.size() > 0)
			if (dsRORoleBatch != null && dsRORoleBatch.size() > 0) {
				this.updateReportRoleBatch(assignOfficerDto.getReportRoleRO(), dsRORoleBatch);
				//this.batchCopyDataFromReportRoleModel(assignOfficerDto, dsRORoleBatch);
			}
		}

	}
	
	//added 20161104
	
	private void updateReportRoleBatch(ReportRoleDto roleDto , List<EaReportRole> dsCDRoleBatch){
		for (int i = 0; i < dsCDRoleBatch.size(); i++) {
			EaReportRole role = this.copyDataFromReportRoleModel(roleDto, dsCDRoleBatch.get(i));
			role.setLastUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			role.setLastUpdateDate(new Date());
			hibernateTemplate.update(role);
			//hibernateTemplate.saveOrUpdate(role);
		}
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	
	

	private void batchCopyDataFromReportRoleModel(AssignOfficerDto assignOfficerDto, List<EaReportRole> dsCDRoleBatch) {
		//for (int i = 0; i < dsCDRoleBatch.size(); i++)
		for (int i = 1; i < dsCDRoleBatch.size(); i++) {
			EaReportRole role = this.copyDataFromReportRoleModel(assignOfficerDto.getReportRoleAO(),
					dsCDRoleBatch.get(i));
			role.setLastUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			role.setLastUpdateDate(new Date());
			hibernateTemplate.update(role);
			//hibernateTemplate.saveOrUpdate(role);
		}
		hibernateTemplate.flush();
		hibernateTemplate.clear();

	}

	private EaReportRole copyDataFromReportRoleModel(ReportRoleDto dto, EaReportRole eaReportRole) {

		eaReportRole.setFirstReminder(dto.getFirstReminder());
		eaReportRole.setSecondReminder(dto.getSecondReminder());
		eaReportRole.setThirdReminder(dto.getThirdReminder());
		eaReportRole.setSubsReminder(dto.getSubsReminder());
		eaReportRole.setFirstRemInterval(dto.getFirstRemInterval());
		eaReportRole.setSecondRemInterval(dto.getSecondRemInterval());
		eaReportRole.setThirdRemInterval(dto.getThirdRemInterval());
		eaReportRole.setSubsRemInterval(dto.getSubsRemInterval());
		eaReportRole.setDeadline(Util.string2Date(dto.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
		eaReportRole.setNotification(dto.getNotification());
		//eaReportRole.setRole(dto.getRole());
		eaReportRole.setRoleSequence(dto.getRoleSequence());
		eaReportRole.setOfficerId(dto.getEmployeeNum());
		eaReportRole.setOfficerName(dto.getEmployeeName());
		return eaReportRole;
	}

	/**
	 * Component is filled ?
	 * 
	 * @param checkField
	 * @return
	 */
	private boolean containValueField(String checkField) {
		if (checkField == null || "".equals(checkField))
			return false;
		return true;
	}

	private void sendEmail(AssignOfficerDto assignOfficerDto, List<EaReport> dsReportBatch, EaReport rp)
			throws EAException {
		log.info("***Enter Send Email***");
		String recipientNumber = assignOfficerDto.getReportRoleAO().getEmployeeNum();
		Date aoDeadline = Util.string2Date(assignOfficerDto.getReportRoleAO().getDeadline(),
				Constants.DISPLAY_DATE_FORMAT);
		String recipientName = "";
		String recipientEmail = "";
		List<EmployeeEnquiryDTO> dsEmployeeEnquiry = employeeEnquiryDAO.searchByEmployee(recipientNumber);
		if (dsEmployeeEnquiry != null && dsEmployeeEnquiry.size() > 0) {
			recipientEmail = dsEmployeeEnquiry.get(0).getEmailAddress();
			recipientName = dsEmployeeEnquiry.get(0).getEmployeeFullName();
		}
		if (!Util.isEmptyString(recipientEmail)) {
			// got AO email address
			DummyEmployee de = new DummyEmployee(recipientNumber, recipientName, recipientEmail);

			// Including notification message
			de.setNotification(assignOfficerDto.getReportRoleAO().getNotification());
			// NOTE: Only pushing the report which status is CD
			if (dsReportBatch != null && dsReportBatch.size() > 0) {
				List<EaReport> dsReportBatchCopy = dsReportBatch;
				dsReportBatchCopy.add(rp);
				this.pushReportInfo(de, dsReportBatchCopy, Constants.STATUS_CD);
			}
			if(rp!=null){
				int rptId = (int)rp.getReportId();
				String rptName = rp.getName();
				String rptStatus = rp.getStatus();
				String rptRank = rp.getSubstantiveRank();
				Date rptCommenceDate = rp.getCommenceDate();
				Date rptEndDate = rp.getEndDate();
				
				DummyReportInfo reports = new DummyReportInfo(rptId,rptName,rptStatus,rptRank,rptCommenceDate,rptEndDate);
				de.setReports(new Vector());
				de.getReports().add(reports);
			}

			if (de.getReports().size() > 0) {
				// send email now
				EaMessageLog dsHaHrEaMessageLog = new EaMessageLog();
				List<EaMessageLog> messages = notificationManagerDAO.sendNotification(de, aoDeadline, false,
						dsHaHrEaMessageLog);
				notificationManagerDAO.updateMessageLog(messages);
			}
		}

	}

	/**
	 * Push reports info into DummyEmployee instance
	 * 
	 * @param nm
	 *            Instance of NotificationManager
	 * @param de
	 *            Instance of DummyEmployee
	 * @param dsReport
	 *            Instance of AppraisalReportModel
	 * @throws DataStoreException
	 */
	private void pushReportInfo(DummyEmployee de, List<EaReport> dsReport, String statusMask) {
		for (int i = 0; i < dsReport.size(); i++) {
			if (Util.isEmptyString(statusMask)) {
				// skip pushing if report status is not matching
				if (!statusMask.equalsIgnoreCase(dsReport.get(i).getStatus()))
					continue;
			}
			long reportID = dsReport.get(i).getReportId();
			String reportEmpName = dsReport.get(i).getName();
			String reportStatus = dsReport.get(i).getStatus();
			String reportEmpRank = dsReport.get(i).getSubstantiveRank();
			Date reportStart = dsReport.get(i).getCommenceDate();
			Date reportEnd = dsReport.get(i).getEndDate();
			DummyReportInfo ri = new DummyReportInfo(0, reportEmpName, reportStatus, reportEmpRank, reportStart,
					reportEnd);
			de.getReports().add(ri);
		}
	}
	
}
