package com.hkha.ea.dao.security.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.security.UserEnquiryDAO;
import com.hkha.ea.domain.EaLogin;
import com.hkha.ea.domain.EaUser;
import com.hkha.ea.dto.common.EaLoginDTO;
import com.hkha.ea.dto.common.EaUserDTO;
import com.hkha.ea.dto.security.UserEnquiryDto;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;
import com.hkha.ea.helper.BaseEntityUtility;

@Repository("userEnquiryDAO")
@Transactional
public class UserEnquiryDAOImpl implements UserEnquiryDAO {
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(UserEnquiryDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	//20240821 Name Presentation
	private String fullNamePattern = " initcap(p.title) || ' ' || upper(p.last_name) || ' ' || initcap(p.first_name) || decode(p.PRE_NAME_ADJUNCT, null, '', (' ' || initcap(lower(p.PRE_NAME_ADJUNCT)))) || decode(p.suffix, null, '', (', ' || p.suffix)) as fullName ,";
	//End 20240821 Name Presentation
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private Session session = null;
	private Transaction tx = null;
	
	public EaLoginDTO searchByLoginID(String loginID) {

		EaLogin loginEmployee = null;
		
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			/*
		 	Following part was created to update hibernate.Query to hibernate.query.Query on 18/11/2021
		 	
		 	The hibernate.Query is deprecated in Hibernate 5.2.
		 	
		 	Modified on 18/11/2021
			*/
			
			
//			Query query = session.createQuery("from EaLogin o where o.loginId = :loginID");
//			query.setString("loginID", loginID);
//			loginEmployee = (EaLogin) query.uniqueResult();
			
			Query<EaLogin> query = session.createQuery("from EaLogin o where o.loginId = :loginID", EaLogin.class);
			query.setParameter("loginID", loginID);
			loginEmployee = (EaLogin) query.uniqueResult();
			
			tx.commit();
		}catch(Exception e){
			log.severe(e.toString());
			tx.rollback();
		}finally{
			session.close();
		}
		
		if (loginEmployee != null){
			EaLoginDTO loginEmployeeDTO = new EaLoginDTO();
			BeanUtils.copyProperties(loginEmployee, loginEmployeeDTO);
			return loginEmployeeDTO;
		}
		return null;
	}
	
	public void saveLoginInformation(EaLoginDTO loginDTO) {
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			EaLogin loginEA = new EaLogin();
			BeanUtils.copyProperties(loginDTO, loginEA);
			BaseEntityUtility.setEACommonProperties(loginEA, loginEA.getLoginId());

			session.save(loginEA);
			session.flush();

			tx.commit();
		}catch(Exception e){
			log.severe(e.toString());
			tx.rollback();
		}finally{
			session.close();
		}
	}

	public void updateLoginInformation(EaLoginDTO loginDTO) {
		
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			EaLogin loginEA = session.get(EaLogin.class, loginDTO.getLoginId());
			BeanUtils.copyProperties(loginDTO, loginEA);
			BaseEntityUtility.updateEACommonProperties(loginEA, loginEA.getLoginId());

			session.saveOrUpdate(loginEA);
			session.flush();
			
			tx.commit();
		}catch(Exception e){
			log.severe(e.toString());
			tx.rollback();
		}finally{
			session.close();
		}
		
	}

	public EaUserDTO searchByUserID(String userID) {
		EaUser eaUser = null;
		
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			/*
		 	Following part was created to update hibernate.Query to hibernate.query.Query on 18/11/2021
		 	
		 	The hibernate.Query is deprecated in Hibernate 5.2.
		 	
		 	Modified on 18/11/2021
			*/
			
//			Query query = session.createQuery("from EaUser o where o.userId = :userID");
//			query.setString("userID", userID);

			Query<EaUser> query = session.createQuery("from EaUser o where o.userId = :userID", EaUser.class);
			query.setParameter("userID", userID);
			eaUser = (EaUser) query.uniqueResult();			
			
						
			tx.commit();
		}catch(Exception e){
			log.severe(e.toString());
			tx.rollback();
		}finally{
			session.close();
		}
		
		if (eaUser != null){
			EaUserDTO loginUserDTO = new EaUserDTO();
			BeanUtils.copyProperties(eaUser, loginUserDTO);
			return loginUserDTO;
		}
		return null;
	}

	public EaUserDTO searchByUserIDAndStatus(String userID, String status) {
		EaUser eaUser = null;
		
		try{
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			/*
		 	Following part was created to update hibernate.Query to hibernate.query.Query on 18/11/2021
		 	
		 	The hibernate.Query is deprecated in Hibernate 5.2.
		 	
		 	Modified on 18/11/2021
			*/
//			Query query = session.createQuery("from EaUser o where o.userId = :userID and o.status = :status");
//			query.setString("userID", userID);
//			query.setString("status", status);
			
			Query<EaUser> query = session.createQuery("from EaUser o where o.userId = :userID and o.status = :status", EaUser.class);
			query.setParameter("userID", userID);
			query.setParameter("status", status);
			eaUser = (EaUser) query.uniqueResult();
			tx.commit();
		}catch(Exception e){
			log.severe(e.toString());
			tx.rollback();
		}finally{
			session.close();
		}
		
		if (eaUser != null){
			EaUserDTO loginUserDTO = new EaUserDTO();
			BeanUtils.copyProperties(eaUser, loginUserDTO);
			return loginUserDTO;
		}
		return null;
	}

	//add by elina start on 2016-04-12
	@Transactional(readOnly = true,rollbackFor=Exception.class)
	public List<UserEnquiryDto> search(UserEnquiryDto userEnquiryDto) {
		StringBuffer sql = new StringBuffer();
		sql.append("select USER_ID userId ,u.GROUP_ID groupId, grp.GROUP_NAME groupName, STATUS enable ");
		sql.append("from HA_HR_EA_USER u, HA_HR_EA_USER_GROUP grp ");
		sql.append("where 1 = 1 ");
		log.info("userEnquiryDto =========="+userEnquiryDto.getGroupId()+"====enable======"+userEnquiryDto.getEnable());
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		if(userEnquiryDto != null && StringUtils.isNotBlank(userEnquiryDto.getUserId())){
			sql.append("and UPPER(USER_ID) like  '%'||:userId||'%' ");
			
			paramSource.addValue("userId", userEnquiryDto.getUserId().toUpperCase());
		}
		
		if(userEnquiryDto != null && userEnquiryDto.getGroupId() != null){
			sql.append("and u.GROUP_ID = :groupId ");
			paramSource.addValue("groupId", userEnquiryDto.getGroupId());
		}
		
		if(userEnquiryDto != null && StringUtils.isNotBlank(userEnquiryDto.getEnable())){
			sql.append("and UPPER(STATUS) = :enable ");
			paramSource.addValue("enable", userEnquiryDto.getEnable().toUpperCase());
		}
		
		sql.append("and u.GROUP_ID = grp.GROUP_ID ");
		
		log.info("user sql=="+sql.toString());
		
		 List<UserEnquiryDto> list =  namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<UserEnquiryDto>(UserEnquiryDto.class));
			return list;
	}

	@Transactional(rollbackFor=Exception.class)
	public void insert(UserEnquiryDto userEnquiryDto) {
		log.info("insert data ======"+userEnquiryDto.getGroupId()+"===enable===="+userEnquiryDto.getEnable());
		
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into HA_HR_EA_USER ");
		sql.append("(USER_ID,GROUP_ID,STATUS,created_by, creation_date, last_updated_by, last_update_date) ");
		sql.append(" values (:userId, :groupId, :enable, :createdBy, sysdate, :createdBy, sysdate) ");
		
		 log.info("sql is "+sql.toString());
		 log.info(userEnquiryDto.toString());
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("userId", userEnquiryDto.getUserId().toUpperCase());
		 paramSource.addValue("groupId", userEnquiryDto.getGroupId());
		 paramSource.addValue("enable", userEnquiryDto.getEnable().toUpperCase());
		 paramSource.addValue("createdBy", createdBy);
		 log.info("insert sql ======"+sql.toString());
		 namedParameterJdbcTemplate.update(sql.toString(), paramSource);
		
	}

	@Transactional(rollbackFor=Exception.class)
	public void update(UserEnquiryDto userEnquiryDto,String oldUserId) {
		log.info("update data ======"+userEnquiryDto.getGroupId()+"===enable===="+userEnquiryDto.getEnable());
		
		String lastUpdatedBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		StringBuilder sql = new StringBuilder();
		sql.append("update HA_HR_EA_USER ");
		sql.append("set USER_ID = :userId, ");
		sql.append("GROUP_ID = :groupId, ");
		sql.append("STATUS = :enable, ");
		sql.append("last_updated_by = :lastUpdatedBy, ");
		sql.append("last_update_date = sysdate ");
		sql.append("where UPPER(USER_ID) = :oldUserId ");
		
		 log.info("sql is "+sql.toString());
		 log.info(userEnquiryDto.toString());
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("userId", userEnquiryDto.getUserId().toUpperCase());
		 paramSource.addValue("groupId", userEnquiryDto.getGroupId());
		 paramSource.addValue("enable", userEnquiryDto.getEnable().toUpperCase());
		 paramSource.addValue("oldUserId", oldUserId.toUpperCase());
		 paramSource.addValue("lastUpdatedBy", lastUpdatedBy);
		 log.info("update sql ======"+sql.toString());
		 namedParameterJdbcTemplate.update(sql.toString(), paramSource);
		
	}
	
	public List<UserEnquiryModelDTO> searchGradeManagmentByReportID(long reportId){
		
		StringBuilder sql = new StringBuilder();
		sql.append("select u.user_Id as userId,u.group_Id as groupId,"
				//20240821 Name Presentation
//				+ "p.full_name as fullName,"
				+(fullNamePattern)
				//End of 20240821 Name Presentation
				+ "u.status as status,g.group_name as groupName,p.email_address as emailAddress,p.employee_number as employeeNumber, "
				+ "v.eng_rank as engRank ,v.primary_Flag as primaryFlag, fnd_user.EMAIL_ADDRESS userEmailAddress "
				+ "from ha_hr_ea_user u,per_all_people_f p,HA_HR_EA_COMMON_ASSIGNMENT_V v , ha_hr_ea_user_group g, fnd_user  ");
		sql.append("where u.GROUP_ID in (")
       	.append("   select g.GROUP_ID from ")
		.append("    ha_hr_ea_report r, ")
		.append("    ha_hr_ea_group_rank g, ")
		.append("    ha_hr_ea_group_function f ")
		.append("   where r.REPORT_ID = ").append(reportId).append(" ")
		.append("   and g.rank = r.SUBSTANTIVE_RANK ")
		.append("   and f.GROUP_ID = g.GROUP_ID ")
		.append("   and f.FUNCTION_ID = ").append(Constants.FUNCTION_ID_CONFIRM_REJECT_REPORT).append(" ")
		.append("   and f.ACCESS_RIGHT = 'Y' ")
     	.append(" ) ")
		.append(" and u.STATUS = 'Y' ");

		sql.append(" and u.USER_ID = p.employee_number (+) ");
		sql.append(" and p.PERSON_ID = v.PERSON_ID (+) ");
		sql.append(" and ( ");
		sql.append("   ( ");
		sql.append("       p.effective_start_date is null ");
		sql.append("   and v.primary_flag is null ");
		sql.append("   and v.people_start_date is null ");
		sql.append("   and v.assignment_start_date is null ");
		sql.append("   ) or (");
		
		sql.append("       p.effective_start_date >= ( ");
		sql.append("   	select max(papfm.effective_start_date) from per_all_people_f papfm "); 
     	sql.append("   	where papfm.employee_number = p.employee_number ");
     	sql.append("       ) ");
     	
     	sql.append("   and v.primary_flag = 'Y' ");
     	sql.append("   and v.assignment_start_date >= ( ");
     	sql.append("   	select max(hcavm.assignment_start_date) from HA_HR_EA_COMMON_ASSIGNMENT_V hcavm "); 
     	sql.append("   	where hcavm.employee_number = v.employee_number ");
     	sql.append("   ) ");
 		
     	sql.append("   ) ");
     	sql.append(" ) ");
        sql.append(" and fnd_user.USER_NAME = u.USER_ID").append("(+)");
        
		List<UserEnquiryModelDTO> list =  namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<UserEnquiryModelDTO>(UserEnquiryModelDTO.class));
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				if(list.get(i) != null){
					if(list.get(i).getEmailAddress() == null)
						list.get(i).setEmailAddress(list.get(i).getUserEmailAddress());
				}
			}
		}
		return list;
	}
	
	//end
	
	public List<UserEnquiryModelDTO> searchUserByReportIdAndFunctionId(long reportId,long functionId){
		//functionId=20 SuperUser functionId=50 GM
		StringBuilder sql = new StringBuilder();
		sql.append("select u.user_Id as userId,u.group_Id as groupId,"
				//20240821 Name Presentation
//				+ "p.full_name as fullName,"
				+(fullNamePattern)
				//End of 20240821 Name Presentation
				+ "u.status as status,g.group_name as groupName,p.email_address as emailAddress,p.employee_number as employeeNumber, "
				+ "v.eng_rank as engRank ,v.primary_Flag as primaryFlag "
				+ "from ha_hr_ea_user u,per_all_people_f p,HA_HR_EA_COMMON_ASSIGNMENT_V v , ha_hr_ea_user_group g  ");
		sql.append("where u.GROUP_ID in  ");
		sql.append("(select g.GROUP_ID from  ha_hr_ea_report r, ha_hr_ea_group_rank g, ha_hr_ea_group_function f ");
		sql.append(" where r.REPORT_ID = :reportId and g.rank = r.SUBSTANTIVE_RANK and f.GROUP_ID = g.GROUP_ID and f.FUNCTION_ID = :functionId and f.ACCESS_RIGHT = 'Y' ) ");
		sql.append(" and u.STATUS  = 'Y'  ");
		sql.append(" and g.group_Id = u.group_Id ");
		sql.append(" and u.USER_ID = p.employee_number (+) ");
		sql.append(" and p.PERSON_ID = v.PERSON_ID (+) ");
		sql.append(" and  (( p.effective_start_date is null  and v.primary_flag is null and p.start_date is null  and v.ASSIGNMENT_START_DATE is null ) ");
		sql.append(" or ( p.effective_start_date >= (select max(papfm.effective_start_date) from per_all_people_f papfm where papfm.employee_number = p.employee_number) ");
		sql.append(" and v.primary_flag = 'Y' ");
		sql.append(" and v.ASSIGNMENT_START_DATE >= ( select max(hcavm.ASSIGNMENT_START_DATE) from HA_HR_EA_COMMON_ASSIGNMENT_V hcavm where hcavm.employee_number = v.employee_number)))");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("reportId", reportId);
		paramSource.addValue("functionId", functionId);
		
		List<UserEnquiryModelDTO> list =  namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<UserEnquiryModelDTO>(UserEnquiryModelDTO.class));
		return list;
	}
	
	public List<UserEnquiryModelDTO> searchUserEnquiryModelDTO(String userId, String groupName, String enable){
		//functionId=20 SuperUser functionId=50 GM
		StringBuilder sql = new StringBuilder();
		sql.append("select u.user_Id as userId,u.group_Id as groupId,"
				//20240821 Name Presentation
//				+ "p.full_name as fullName,"
				+(fullNamePattern)
				//End of 20240821 Name Presentation
				+ "u.status as status,g.group_name as groupName,p.email_address as emailAddress,p.employee_number as employeeNumber, "
				+ "v.eng_rank as engRank ,v.primary_Flag as primaryFlag "
				+ "from ha_hr_ea_user u,per_all_people_f p,HA_HR_EA_COMMON_ASSIGNMENT_V v , ha_hr_ea_user_group g where 1=1");
		 if (!Util.isEmptyString(userId)) {
			 sql.append(" and UPPER(").append("u.USER_ID").append(") like '").append(userId.trim().toUpperCase()).append("%'");
		  }
		 if (!Util.isEmptyString(groupName)) {
			 sql.append(" and UPPER(").append("g.GROUP_NAME").append(") like '").append(groupName.trim().toUpperCase()).append("%'");
		 }
		 if (!Util.isEmptyString(enable)) {
			 sql.append(" and UPPER(").append("u.STATUS").append(") like '").append(enable.trim().toUpperCase()).append("%'");
		 }
		 
		 sql.append(" and u.USER_ID = p.employee_number (+) ");
		 sql.append(" and p.PERSON_ID = v.PERSON_ID (+) ");
		 sql.append(" and  (( p.effective_start_date is null  and v.primary_flag is null and p.start_date is null  and v.ASSIGNMENT_START_DATE is null ) ");
		 sql.append(" or ( p.effective_start_date >= (select max(papfm.effective_start_date) from per_all_people_f papfm where papfm.employee_number = p.employee_number) ");
		 sql.append(" and v.primary_flag = 'Y' ");
		 sql.append(" and v.ASSIGNMENT_START_DATE >= ( select max(hcavm.ASSIGNMENT_START_DATE) from HA_HR_EA_COMMON_ASSIGNMENT_V hcavm where hcavm.employee_number = v.employee_number)))");

	
		List<UserEnquiryModelDTO> list =  namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<UserEnquiryModelDTO>(UserEnquiryModelDTO.class));
		return list;
	}
	
	//added on 201710315 Search GmList by SU group id
	public List<UserEnquiryModelDTO> searchGMUserNameByGroupId(long groupId, long functionId){
		StringBuilder sql = new StringBuilder();
		sql.append("select u.user_Id as userId,u.group_Id as groupId,"
				//20240821 Name Presentation
//				+ "p.full_name as fullName,"
				+(fullNamePattern)
				//End of 20240821 Name Presentation
				+ "u.status as status,g.group_name as groupName,p.email_address as emailAddress,p.employee_number as employeeNumber, "
				+ "v.eng_rank as engRank ,v.primary_Flag as primaryFlag "
				+ "from ha_hr_ea_user u,per_all_people_f p,HA_HR_EA_COMMON_ASSIGNMENT_V v , ha_hr_ea_user_group g ");
		sql.append("where u.GROUP_ID in  ");
		sql.append("(select g.GROUP_ID from  ha_hr_ea_report r, ha_hr_ea_group_rank g, ha_hr_ea_group_function f ");
		sql.append("where 1=1 ");
		sql.append("and g.rank in (select hhegr.rank from  ha_hr_ea_user_group hheug, ha_hr_ea_group_rank hhegr where 1=1 and HHEUG.GROUP_ID = HHEGR.GROUP_ID and HHEUG.GROUP_ID = :groupId ) ");
		sql.append("and g.rank = r.SUBSTANTIVE_RANK and f.GROUP_ID = g.GROUP_ID and f.FUNCTION_ID = :functionId and f.ACCESS_RIGHT = 'Y' ) ");
		sql.append("and u.STATUS  = 'Y'  ");
		sql.append("and g.group_Id = u.group_Id ");
		sql.append("and u.USER_ID = p.employee_number (+) ");
		sql.append("and p.PERSON_ID = v.PERSON_ID (+) ");
		sql.append("and  (( p.effective_start_date is null  and v.primary_flag is null and p.start_date is null  and v.ASSIGNMENT_START_DATE is null ) ");
		sql.append("or ( p.effective_start_date >= (select max(papfm.effective_start_date) from per_all_people_f papfm where papfm.employee_number = p.employee_number) ");
		sql.append("and v.primary_flag = 'Y' ");
		sql.append("and v.ASSIGNMENT_START_DATE >= ( select max(hcavm.ASSIGNMENT_START_DATE) from HA_HR_EA_COMMON_ASSIGNMENT_V hcavm where hcavm.employee_number = v.employee_number)))");

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("groupId", groupId);
		paramSource.addValue("functionId", functionId);
		
		log.info("sql: "+sql);
		
		List<UserEnquiryModelDTO> gmUserList = namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<UserEnquiryModelDTO>(UserEnquiryModelDTO.class));
		
		return gmUserList;
	}
	//end added on 20170315

	@Transactional(readOnly = true,rollbackFor=Exception.class)
	public String getUserIdByIvUser(String ivUser) {
		StringBuffer sql = new StringBuffer();
        String result;
		sql.append("select distinct ppx.employee_number ");
		sql.append("from FND_USER fu, ha_common_people_v ppx ");
		sql.append("where fu.employee_id=ppx.person_id ");
		sql.append("and trunc(sysdate) between ppx.people_start_date and ppx.people_end_date ");
		sql.append("and upper(fu.user_name)=UPPER(:ivUser) ");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("ivUser", ivUser);
		
		
		//List<String> result =  namedParameterJdbcTemplate.queryForList(sql.toString(), paramSource, String.class);
		//if (result.size()>0)
		//	return result.get(0);
		//return null;
        try{
            result =  namedParameterJdbcTemplate.queryForObject(sql.toString(), paramSource, String.class);
        }
        catch (org.springframework.dao.EmptyResultDataAccessException ex){
            result = "NOT_FOUND";
        }
		return result;

	}
}
