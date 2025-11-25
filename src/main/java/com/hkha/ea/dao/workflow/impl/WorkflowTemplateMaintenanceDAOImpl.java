package com.hkha.ea.dao.workflow.impl;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.common.CommonSysParaDAO;
import com.hkha.ea.dao.workflow.WorkflowTemplateMaintenanceDAO;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.domain.EaWorkflowRole;
import com.hkha.ea.dto.workflow.WorkflowDetailDTO;
import com.hkha.ea.dto.workflow.WorkflowEnquiryDTO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoAO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoCD;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoCO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoEO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoIO;
import com.hkha.ea.dto.workflow.WorkflowRoleInfoRO;

@Repository("workflowTemplateMaintenanceDAO")
public class WorkflowTemplateMaintenanceDAOImpl implements WorkflowTemplateMaintenanceDAO {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private CommonSysParaDAO commonSysParaDAO;

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(WorkflowTemplateMaintenanceDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out

	public List<WorkflowEnquiryDTO> search(WorkflowEnquiryDTO workflowEnquiryDTO) {

		List<WorkflowEnquiryDTO> workflowEnquiryDTOList = new ArrayList<WorkflowEnquiryDTO>();
		StringBuilder sql = new StringBuilder();
		sql.append("select WORKFLOW_TEMPLATE_ID workflowId,WORKFLOW_TEMPLATE_NAME workflowTemplateName,REPORT_TEMPLATE reportTemplate,OVERALL_DEADLINE overallDeadline,APPRAISEE_DEADLINE appraoseeDeadline from ha_hr_ea_workflow ");
		sql.append("where 1 = 1 ");
		String wfName = workflowEnquiryDTO.getWorkflowTemplateName();
		String reportTemplate = workflowEnquiryDTO.getReportTemplate();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		if (null != wfName && !"".equals(wfName.trim())) {
			sql.append("and upper(WORKFLOW_TEMPLATE_NAME) like :workflowTemplateName ");
			paramSource.addValue("workflowTemplateName", "%" + wfName.trim().toUpperCase() + "%");
		}
		if (null != reportTemplate && !"".equals(reportTemplate.trim())) {
			sql.append("and REPORT_TEMPLATE like :reportTemplate ");
			paramSource.addValue("reportTemplate", "%" + reportTemplate + "%");
		}

		sql.append("order by WORKFLOW_TEMPLATE_NAME");
		log.info("sql is " + sql.toString());

		workflowEnquiryDTOList = namedParameterJdbcTemplate.query(sql.toString(), paramSource,
				new BeanPropertyRowMapper<WorkflowEnquiryDTO>(WorkflowEnquiryDTO.class));

		return workflowEnquiryDTOList;
	}

	public List<WorkflowEnquiryDTO> searchWorkflowDetail(WorkflowEnquiryDTO workflowEnquiryDTO) {

		List<WorkflowEnquiryDTO> workflowEnquiryDTOList = new ArrayList<WorkflowEnquiryDTO>();
		StringBuilder sql = new StringBuilder();
		sql.append("select WORKFLOW_TEMPLATE_ID workflowId, WORKFLOW_TEMPLATE_NAME workflowTemplateName,REPORT_TEMPLATE reportTemplate,OVERALL_DEADLINE overallDeadline,APPRAISEE_DEADLINE appraoseeDeadline from ha_hr_ea_workflow ");
		sql.append("where 1 = 1 ");
		String wfName = workflowEnquiryDTO.getWorkflowTemplateName();
		String reportTemplate = workflowEnquiryDTO.getReportTemplate();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		if (null != wfName && !"".equals(wfName.trim())) {
			sql.append("and WORKFLOW_TEMPLATE_NAME like :workflowTemplateName ");
			paramSource.addValue("workflowTemplateName", "%" + wfName + "%");
		}
		if (null != reportTemplate && !"".equals(reportTemplate.trim())) {
			sql.append("and REPORT_TEMPLATE like :reportTemplate");
			paramSource.addValue("reportTemplate", "%" + reportTemplate + "%");
		}

		log.info("sql is " + sql.toString());

		workflowEnquiryDTOList = namedParameterJdbcTemplate.query(sql.toString(), paramSource,
				new BeanPropertyRowMapper<WorkflowEnquiryDTO>(WorkflowEnquiryDTO.class));

		return workflowEnquiryDTOList;
	}

	@SuppressWarnings("unchecked")
	public WorkflowDetailDTO searchWorkflowRoleInfoDetail(long workflowTemplateId) {
		WorkflowDetailDTO wfd = new WorkflowDetailDTO();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select W.WORKFLOW_TEMPLATE_ID workflowTemplateId, W.WORKFLOW_TEMPLATE_NAME workflowTemplateName,S.PARA_DESC reportDesc,W.REPORT_TEMPLATE reportTemplate,W.OVERALL_DEADLINE overallDeadline,W.APPRAISEE_DEADLINE appraoseeDeadline ");
		sql.append(
				"FROM ha_hr_ea_workflow w left join HA_HR_EA_SYS_PARA S ON S.PARA_VALUE=W.REPORT_TEMPLATE WHERE S.PARA_NAME LIKE '%REPORT_LAYOUT_%' ");
		sql.append("and W.WORKFLOW_TEMPLATE_ID = :workflowTemplateId");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("workflowTemplateId", workflowTemplateId);
		log.info("sql is " + sql.toString());
		EaWorkflow ewf = namedParameterJdbcTemplate.queryForObject(sql.toString(), paramSource,
				new BeanPropertyRowMapper<EaWorkflow>(EaWorkflow.class));
		BeanUtils.copyProperties(ewf, wfd);
		wfd.setOverallDeadline(Util.date2String(ewf.getOverallDeadline(), "dd/MM/yyyy"));
		wfd.setAppraiseeDeadline(Util.date2String(ewf.getAppraiseeDeadline(), "dd/MM/yyyy"));

		/*
		#Spring5Upgrade #OpenJDK11 #Java11
		Following part was created to remove HibernateTemplate.find on 12/11/2021
		 
		Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, the Spring
			framework was required to upgrade from version 4 to version 5.
		 
		The hibernateTemplate.find is deprecated in Hibernate 5.
		
		Modified on 12/11/2021
		*/
		//List<EaWorkflowRole> rolelist=(List<EaWorkflowRole>)hibernateTemplate.find("from EaWorkflowRole r where r.workflowTemplateId = "+workflowTemplateId);
		
		StringBuffer criteria = new StringBuffer();
		criteria.append("select * FROM HA_HR_EA_WORKFLOW_ROLE")
				.append(" where WORKFLOW_TEMPLATE_ID = :workflowTemplateId ");
		
		List<EaWorkflowRole> rolelist = namedParameterJdbcTemplate.query(criteria.toString(), paramSource, new BeanPropertyRowMapper<EaWorkflowRole>(EaWorkflowRole.class));

		wfd = convertToWorkflowDetailDTO(rolelist, wfd);

		return wfd;
	}

	public EaWorkflow searchWorkflowInfo(long workflowTemplateId) {
		EaWorkflow ewf = new EaWorkflow();
		StringBuilder sql = new StringBuilder();
		sql.append("select * FROM ha_hr_ea_workflow w ");
		sql.append("where w.WORKFLOW_TEMPLATE_ID = :workflowTemplateId");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("workflowTemplateId", workflowTemplateId);
		log.info("sql is " + sql.toString());
		ewf = namedParameterJdbcTemplate.queryForObject(sql.toString(), paramSource,
				new BeanPropertyRowMapper<EaWorkflow>(EaWorkflow.class));

		return ewf;
	}

	public List<EaWorkflowRole> searchWorkflowRoleInfoDetailById(long workflowTemplateId) {
		// WorkflowDetailDTO wfd=new WorkflowDetailDTO();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("workflowTemplateId", workflowTemplateId);
		// StringBuilder sql = new StringBuilder();

		/*
		#Spring5Upgrade #OpenJDK11 #Java11
		Following part was created to remove HibernateTemplate.find on 12/11/2021
		 
		Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, the Spring
			framework was required to upgrade from version 4 to version 5.
		 
		The hibernateTemplate.find is deprecated in Hibernate 5.
		
		Modified on 12/11/2021
		*/

		// List<EaWorkflowRole>
		// rolelist=(List<EaWorkflowRole>)hibernateTemplate.find("from EaWorkflowRole r
		// where r.workflowTemplateId = "+workflowTemplateId);

		StringBuffer criteria = new StringBuffer();
		criteria.append("select * FROM HA_HR_EA_WORKFLOW_ROLE")
				.append(" where WORKFLOW_TEMPLATE_ID = :workflowTemplateId ");

		List<EaWorkflowRole> rolelist = namedParameterJdbcTemplate.query(criteria.toString(), paramSource,
				new BeanPropertyRowMapper<EaWorkflowRole>(EaWorkflowRole.class));

		return rolelist;
	}

	public void modifyWorkflowRoleInfoDetail(WorkflowDetailDTO dto, EaWorkflow ewf) throws Exception {
		log.info("deadline_2: " + dto.getOverallDeadline() + " and " + ewf.getOverallDeadline());
		BeanUtils.copyProperties(dto, ewf);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		ewf.setOverallDeadline(DateTimeUtil.string2Date(dto.getOverallDeadline()));
		ewf.setLastUpdatedBy(username);
		ewf.setLastUpdatedDate(new Date());
		log.info("deadline_3: " + dto.getOverallDeadline() + " and " + ewf.getOverallDeadline());
		hibernateTemplate.update(ewf);

		List<EaWorkflowRole> list = searchWorkflowRoleInfoDetailById(dto.getWorkflowTemplateId());
		for (EaWorkflowRole r : list) {
			hibernateTemplate.delete(r);
		}

		saveRoleInfo(dto);
	}

	public void addWorkflowRoleInfoDetail(WorkflowDetailDTO dto) throws Exception {
		// addNew
		EaWorkflow wf = new EaWorkflow();

		BeanUtils.copyProperties(dto, wf);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		wf.setOverallDeadline(Util.string2Date(dto.getOverallDeadline(), Constants.DISPLAY_DATE_FORMAT));
		wf.setCreatedBy(username);
		wf.setCreationDate(new Date());
		wf.setLastUpdatedBy(username);
		wf.setLastUpdatedDate(new Date());
		hibernateTemplate.save(wf);
		dto.setWorkflowTemplateId(wf.getWorkflowTemplateId());
		try {
			saveRoleInfo(dto);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.severe(ex.toString());
			log.info(ex.getMessage());
		}

	}

	private void saveRoleInfo(WorkflowDetailDTO dto) throws Exception {
		List<EaWorkflowRole> list = new ArrayList<EaWorkflowRole>();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		list = convertToEaWorkflowRole(list, dto);
		for (EaWorkflowRole wr : list) {

			wr.setCreatedBy(username);
			wr.setCreationDate(new Date());
			wr.setLastUpdatedBy(username);
			wr.setLastUpdateDate(new Date());
			hibernateTemplate.save(wr);
		}

	}

	private WorkflowDetailDTO convertToWorkflowDetailDTO(List<EaWorkflowRole> rolelist, WorkflowDetailDTO wfd) {
		for (EaWorkflowRole r : rolelist) {
			if ("AO".equals(r.getRole())) {
				WorkflowRoleInfoAO ao = new WorkflowRoleInfoAO();
				BeanUtils.copyProperties(r, ao);
				ao.setRoleCheck("Y");
				ao.setRole(r.getRole());
				ao.setDeadline(Util.date2String(r.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
				wfd.setWorkflowRoleInfoAO(ao);
			}
			if ("CO".equals(r.getRole())) {
				WorkflowRoleInfoCO co = new WorkflowRoleInfoCO();
				BeanUtils.copyProperties(r, co);
				co.setRoleCheck("Y");
				co.setRole(r.getRole());
				co.setDeadline(Util.date2String(r.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
				wfd.setWorkflowRoleInfoCO(co);
			}
			if ("CD".equals(r.getRole())) {
				WorkflowRoleInfoCD cd = new WorkflowRoleInfoCD();
				BeanUtils.copyProperties(r, cd);
				cd.setRoleCheck("Y");
				cd.setRole(r.getRole());
				cd.setDeadline(Util.date2String(r.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
				wfd.setWorkflowRoleInfoCD(cd);
			}
			if ("IO".equals(r.getRole())) {
				WorkflowRoleInfoIO io = new WorkflowRoleInfoIO();
				BeanUtils.copyProperties(r, io);
				io.setRoleCheck("Y");
				io.setRole(r.getRole());
				io.setDeadline(Util.date2String(r.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
				wfd.setWorkflowRoleInfoIO(io);
			}
			if ("RO".equals(r.getRole())) {
				WorkflowRoleInfoRO ro = new WorkflowRoleInfoRO();
				BeanUtils.copyProperties(r, ro);
				ro.setRoleCheck("Y");
				ro.setRole(r.getRole());
				ro.setDeadline(Util.date2String(r.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
				wfd.setWorkflowRoleInfoRO(ro);
			}
			if ("EO".equals(r.getRole())) {
				WorkflowRoleInfoEO eo = new WorkflowRoleInfoEO();
				BeanUtils.copyProperties(r, eo);
				eo.setRole(r.getRole());
				eo.setRoleCheck("Y");
				eo.setDeadline(Util.date2String(r.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
				wfd.setWorkflowRoleInfoEO(eo);
			}
		}

		return wfd;
	}

	private List<EaWorkflowRole> convertToEaWorkflowRole(List<EaWorkflowRole> rolelist, WorkflowDetailDTO wfd)
			throws Exception {
		log.info("***Enter covert***");
		EaWorkflowRole ao = new EaWorkflowRole();
		EaWorkflowRole cd = new EaWorkflowRole();
		EaWorkflowRole co = new EaWorkflowRole();
		EaWorkflowRole io = new EaWorkflowRole();
		EaWorkflowRole ro = new EaWorkflowRole();
		EaWorkflowRole eo = new EaWorkflowRole();
//		EaWorkflowRolePK aopk = new EaWorkflowRolePK();
//		EaWorkflowRolePK cdpk=new EaWorkflowRolePK();
//		EaWorkflowRolePK copk=new EaWorkflowRolePK();
//		EaWorkflowRolePK iopk=new EaWorkflowRolePK();
//		EaWorkflowRolePK ropk=new EaWorkflowRolePK();
//		EaWorkflowRolePK eopk=new EaWorkflowRolePK();

//		aopk.setRole("AO");	
//		cdpk.setRole("CD");			 
//		copk.setRole("CO");
//		iopk.setRole("IO");			 
//		ropk.setRole("RO");			
//		eopk.setRole("EO");

		if ("Y".equals(wfd.getWorkflowRoleInfoAO().getRoleCheck())) {

			ao.setWorkflowTemplateId(wfd.getWorkflowTemplateId());
			ao.setRole("AO");

			ao.setDeadline(Util.string2Date(wfd.getWorkflowRoleInfoAO().getDeadline(), Constants.DISPLAY_DATE_FORMAT));
			BeanUtils.copyProperties(wfd.getWorkflowRoleInfoAO(), ao);
			ao.setRoleSequence(Long.parseLong("1"));
			rolelist.add(ao);

		}
		if ("Y".equals(wfd.getWorkflowRoleInfoCO().getRoleCheck())) {

			co.setWorkflowTemplateId(wfd.getWorkflowTemplateId());
			co.setRole("CO");
			// co.setId(copk);
			co.setDeadline(Util.string2Date(wfd.getWorkflowRoleInfoCO().getDeadline(), Constants.DISPLAY_DATE_FORMAT));

			BeanUtils.copyProperties(wfd.getWorkflowRoleInfoCO(), co);
			co.setRoleSequence(Long.parseLong("2"));

			rolelist.add(co);
		}
		if ("Y".equals(wfd.getWorkflowRoleInfoCD().getRoleCheck())) {

			cd.setWorkflowTemplateId(wfd.getWorkflowTemplateId());
			cd.setRole("CD");
			// cd.setId(cdpk);
			cd.setDeadline(Util.string2Date(wfd.getWorkflowRoleInfoCD().getDeadline(), Constants.DISPLAY_DATE_FORMAT));
			cd.setDeadline(Util.string2Date(wfd.getWorkflowRoleInfoCD().getDeadline(), Constants.DISPLAY_DATE_FORMAT));

			
			
			/*
			#Spring5Upgrade #OpenJDK11 #Java11
			Following part was created to remove HibernateTemplate.find on 12/11/2021
			 
			Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, the Spring
				framework was required to upgrade from version 4 to version 5.
			 
			The StringUtils.isEmpty is deprecated since Spring Framework 5.3. !StringUtils.hasLength is a replacement of StringUtils.isEmpty.
			
			Modified on 12/11/2021
			*/
			
			//if (StringUtils.isEmpty(wfd.getWorkflowRoleInfoCD().getFirstReminder())) {
			if (!StringUtils.hasLength(wfd.getWorkflowRoleInfoCD().getFirstReminder())) {
				wfd.getWorkflowRoleInfoCD().setFirstReminder(
						commonSysParaDAO.SysParaSearchMessageByDesc(Constants.PREFIX_SYSTEM_DEFAULT_MAIL_MESSAGE,
								Constants.PREFIX_SYSTEM_DEFAULT_FIRST_REMINDER).getParaValue());
			}
			//if (StringUtils.isEmpty(wfd.getWorkflowRoleInfoCD().getSecondReminder())) {
			if (!StringUtils.hasLength(wfd.getWorkflowRoleInfoCD().getSecondReminder())) {
				wfd.getWorkflowRoleInfoCD().setSecondReminder(
						commonSysParaDAO.SysParaSearchMessageByDesc(Constants.PREFIX_SYSTEM_DEFAULT_MAIL_MESSAGE,
								Constants.PREFIX_SYSTEM_DEFAULT_SECOND_REMINDER).getParaValue());
			}
			//if (StringUtils.isEmpty(wfd.getWorkflowRoleInfoCD().getThirdReminder())) {
			if (!StringUtils.hasLength(wfd.getWorkflowRoleInfoCD().getThirdReminder())) {
				wfd.getWorkflowRoleInfoCD().setThirdReminder(
						commonSysParaDAO.SysParaSearchMessageByDesc(Constants.PREFIX_SYSTEM_DEFAULT_MAIL_MESSAGE,
								Constants.PREFIX_SYSTEM_DEFAULT_THIRD_REMINDER).getParaValue());
			}
			//if (StringUtils.isEmpty(wfd.getWorkflowRoleInfoCD().getSubsReminder())) {
			if (!StringUtils.hasLength(wfd.getWorkflowRoleInfoCD().getSubsReminder())) {
				wfd.getWorkflowRoleInfoCD().setSubsReminder(
						commonSysParaDAO.SysParaSearchMessageByDesc(Constants.PREFIX_SYSTEM_DEFAULT_MAIL_MESSAGE,
								Constants.PREFIX_SYSTEM_DEFAULT_SUBS_REMINDER).getParaValue());
			}
			BeanUtils.copyProperties(wfd.getWorkflowRoleInfoCD(), cd);
			cd.setRoleSequence(Long.parseLong("0"));

			rolelist.add(cd);
		}
		if ("Y".equals(wfd.getWorkflowRoleInfoIO().getRoleCheck())) {

			io.setWorkflowTemplateId(wfd.getWorkflowTemplateId());
			io.setRole("IO");
			// io.setId(iopk);
			io.setDeadline(Util.string2Date(wfd.getWorkflowRoleInfoIO().getDeadline(), Constants.DISPLAY_DATE_FORMAT));

			BeanUtils.copyProperties(wfd.getWorkflowRoleInfoIO(), io);
			io.setRoleSequence(Long.parseLong("3"));

			rolelist.add(io);

		}
		if ("Y".equals(wfd.getWorkflowRoleInfoRO().getRoleCheck())) {

			ro.setWorkflowTemplateId(wfd.getWorkflowTemplateId());
			ro.setRole("RO");
			// ro.setId(ropk);
			ro.setDeadline(Util.string2Date(wfd.getWorkflowRoleInfoRO().getDeadline(), Constants.DISPLAY_DATE_FORMAT));

			BeanUtils.copyProperties(wfd.getWorkflowRoleInfoRO(), ro);

			if ("Y".equals(wfd.getWorkflowRoleInfoEO().getRoleCheck())) {
				ro.setRoleSequence(Long.parseLong("5"));
			} else {
				ro.setRoleSequence(Long.parseLong("4"));
			}

			rolelist.add(ro);

		}
		if ("Y".equals(wfd.getWorkflowRoleInfoEO().getRoleCheck())) {
			eo.setWorkflowTemplateId(wfd.getWorkflowTemplateId());
			eo.setRole("EO");
			// eo.setId(eopk);
			eo.setDeadline(Util.string2Date(wfd.getWorkflowRoleInfoEO().getDeadline(), Constants.DISPLAY_DATE_FORMAT));

			BeanUtils.copyProperties(wfd.getWorkflowRoleInfoEO(), eo);
			eo.setRoleSequence(Long.parseLong("4"));

			rolelist.add(eo);

		}

		return rolelist;
	}

	// elina
	public List<EaWorkflowRole> searchByWorkflowAndRole(long id, String role) {
		
		/*
	 	Following part was created to remove HibernateTemplate.find on 12/11/2021
	 	
		Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
			the Spring framework was required to upgrade from version 4 to version 5.
	 	
	 	The hibernateTemplate.find is deprecated in Hibernate 5.
	 	
	 	Modified on 12/11/2021
		*/
		
//		StringBuffer criteria = new StringBuffer(" from EaWorkflowRole r where 1=1 ");
//
//		if (id != 0)
//			criteria.append(" and ").append("r.id.workflowTemplateId").append(" = ").append(id).append(" ");
//
//		if (!Util.isEmptyString(role))
//			criteria.append(" and ").append("r.role").append(" = '").append(role).append("' ");
//
//		List<EaWorkflowRole> rolelist = (List<EaWorkflowRole>) hibernateTemplate.find(criteria.toString());
		// List<EaWorkflowRole> result =
		// namedParameterJdbcTemplate.query(criteria.toString(), new
		// BeanPropertyRowMapper<EaWorkflowRole>(EaWorkflowRole.class));
		
		StringBuffer criteria = new StringBuffer();
		criteria.append("select * from HA_HR_EA_WORKFLOW_ROLE where 1=1 ");
		if (id != 0) {
			criteria.append(" and WORKFLOW_TEMPLATE_ID = ").append(id);
		}
		if (!Util.isEmptyString(role)) {
			criteria.append(" and ROLE = ").append(role);
		}
		
		List<EaWorkflowRole> rolelist = namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaWorkflowRole>(EaWorkflowRole.class));
		
		
		
		return rolelist;
	}

	// Coral for Workflow Validator
	public EaWorkflow searchFreshTemplateForWorkflowById(Long wfId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ha_hr_ea_workflow where ha_hr_ea_workflow.WORKFLOW_TEMPLATE_ID = :wfId ");
		sql.append(" and ha_hr_ea_workflow.WORKFLOW_TEMPLATE_ID in(  select distinct workflow_template_id from ha_hr_ea_report where workflow_template_id = :wfId )");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("wfId", wfId);
		List<EaWorkflow> ewflist = namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<EaWorkflow>(EaWorkflow.class));
		EaWorkflow ewf = null;
		if (0 < ewflist.size()) {
			ewf = ewflist.get(0);
		}
		return ewf;
	}
}
