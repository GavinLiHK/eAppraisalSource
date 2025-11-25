package com.hkha.ea.dao.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Util;
import com.hkha.ea.dao.common.CommonSysParaDAO;
import com.hkha.ea.domain.EaSysPara;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.common.EaUserGroupFunctionDTO;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out


@Repository("commonSysParaDAO")
public class CommonSysParaDAOImpl implements CommonSysParaDAO{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(CommonSysParaDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<SystemParameterDTO> SysParaSearch(final String paraName) {
		List<SystemParameterDTO> sysList=new ArrayList<SystemParameterDTO> ();
		
		/*
	 	Following part was created to update hibernate.Query to hibernate.query.Query on 18/11/2021
	 	
	 	The hibernate.Query is deprecated in Hibernate 5.2.
	 	
	 	Modified on 18/11/2021
		*/
		
//		List<EaSysPara> spList=(List<EaSysPara>)hibernateTemplate.execute(new HibernateCallback>(){
//		public Object doInHibernate(Session session)throws HibernateException{
//			String sql="from EaSysPara s where s.paraName like :paraName ";
//			Query<?> query=session.createQuery(sql);
//			query.setParameter("paraName", "%"+paraName+"%");
//				return query.list();
//	}});
//
		
		List<EaSysPara> spList=hibernateTemplate.execute(new HibernateCallback<List<EaSysPara>>(){
			public List<EaSysPara> doInHibernate(Session session)throws HibernateException{
				String sql="from EaSysPara s where s.paraName like :paraName ";
				Query<EaSysPara> query=session.createQuery(sql, EaSysPara.class);
				query.setParameter("paraName", "%"+paraName+"%");		
					return query.list();
		}});
		
		
	
		if(null!=spList){
			for(EaSysPara sp:spList){
				SystemParameterDTO dto=new SystemParameterDTO();
				dto.setParamName(sp.getParaName());
				dto.setParamValue(sp.getParaValue());
				dto.setParamDesc(sp.getParaDesc());
				sysList.add(dto);
			}
		}
		
//		for(EaSysPara esp: spList) {
//			log.info("SysParaSearch - esp: " + esp.getParaName() + ", " + esp.getParaDesc());
//		}
//		
		return sysList;
	}
	public List<SystemParameterDTO> SysParaSearchMessage(String paraName,String messageName,String messageContent) {
		List<SystemParameterDTO> sysList=new ArrayList<SystemParameterDTO> ();
		
		    StringBuffer criteria = new StringBuffer("select * from HA_HR_EA_SYS_PARA where para_name like '"+paraName+"%'");
	
		      if (!Util.isEmptyString(messageName)) {
	         criteria.append(" and para_desc like '%").append(messageName).append("%' ");
		       }
	 
	       if (!Util.isEmptyString(messageContent)) {
		        criteria.append(" and para_value like '%").append(messageContent).append("%' ");
		       }
	       List<EaSysPara> spList= namedParameterJdbcTemplate.query(criteria.toString(), new BeanPropertyRowMapper<EaSysPara>(EaSysPara.class));
	     
		if(null!=spList){
			for(EaSysPara sp:spList){
				SystemParameterDTO dto=new SystemParameterDTO();
				dto.setParamName(sp.getParaName());
				dto.setParamValue(sp.getParaValue());
				dto.setParamDesc(sp.getParaDesc());
				sysList.add(dto);
			}
		}

		
		return sysList;
	}
	
	public EaSysPara SysParaSearchMessageByDesc(String paraName,String messageName) {
		
		
		    StringBuffer criteria = new StringBuffer("select * from HA_HR_EA_SYS_PARA where para_name like :paraName and para_desc like :messageName");
		    MapSqlParameterSource paramSource = new MapSqlParameterSource();
		    paramSource.addValue("paraName", "%"+paraName+"%");
		      if (!Util.isEmptyString(messageName)) {
		    	  paramSource.addValue("messageName", "%"+messageName+"%");
		       }
	 
	       
	       EaSysPara esp= namedParameterJdbcTemplate.queryForObject(criteria.toString(), paramSource, new BeanPropertyRowMapper<EaSysPara>(EaSysPara.class));
	     
		return esp;
		}
	
	//for EA0016-0018
		public EaUserGroupFunctionDTO searchUserGroupFunction(String userId,String functionId){
			StringBuilder sql = new StringBuilder();
			sql.append("select u.user_id,u.group_id,g.function_id,f.function_desc "
					+ "from ha_hr_ea_USER u left join ha_hr_ea_GROUP_FUNCTION g on u.group_id=g.group_id "
					+ "left join ha_hr_ea_FUNCTION f on g.function_id = f.function_id "
					+ "where u.status='Y' "
					+ "AND g.FUNCTION_ID=:functionId "
					+ "AND g.ACCESS_RIGHT='Y' "
					+ "and u.user_id= :userId"); 

			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("userId", userId);
			paramSource.addValue("functionId", functionId);
			List<EaUserGroupFunctionDTO> list =  namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<EaUserGroupFunctionDTO>(EaUserGroupFunctionDTO.class));
			if(list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
		}
		

}
