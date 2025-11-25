package com.hkha.ea.dao.security.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

import org.apache.commons.lang3.StringUtils;
//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Util;
import com.hkha.ea.dao.security.UserGroupMaintenanceDAO;
import com.hkha.ea.domain.EaGroupFunction;
import com.hkha.ea.domain.EaGroupRank;
import com.hkha.ea.domain.EaGroupRankPK;
import com.hkha.ea.domain.EaUserGrp;
import com.hkha.ea.dto.security.GroupFunctionDto;
import com.hkha.ea.dto.security.GroupRankDto;
import com.hkha.ea.dto.security.RankDto;
import com.hkha.ea.dto.security.UserGroupEnquiryDTO;

@Repository("userGroupMaintenanceDao")
public class UserGroupMaintenanceDAOImpl implements UserGroupMaintenanceDAO{

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(UserGroupMaintenanceDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public List<UserGroupEnquiryDTO> search(UserGroupEnquiryDTO userGroupEnquiryDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("select GROUP_ID groupId, GROUP_NAME userGroupName from HA_HR_EA_USER_GROUP ");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		if(userGroupEnquiryDTO != null && StringUtils.isNotBlank(userGroupEnquiryDTO.getUserGroupName())){
			sql.append("where 1 = 1 ");
			sql.append("and UPPER(GROUP_NAME) like  '%'||:userGroupName||'%' ");
			
			paramSource.addValue("userGroupName", userGroupEnquiryDTO.getUserGroupName().toUpperCase());
		}
		
		 List<UserGroupEnquiryDTO> list =  namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<UserGroupEnquiryDTO>(UserGroupEnquiryDTO.class));
		return list;
	}

	public List<RankDto> getRankList(long groupId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select RANK rank from HA_HR_RANK_MASTER ");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 
		if(groupId != 0L){
			sql.append("where RANK not in  ");
			sql.append("(select RANK from HA_HR_EA_GROUP_RANK where GROUP_ID = :groupId) ");
			
			paramSource.addValue("groupId", groupId);
		}
		sql.append("order by rank");
		 
		 List<RankDto> list =  namedParameterJdbcTemplate.query(sql.toString(), paramSource, new BeanPropertyRowMapper<RankDto>(RankDto.class));
		return list;
	}

	public List<GroupFunctionDto> getFunctionList() {
		StringBuffer sql = new StringBuffer();
		sql.append("select fun.FUNCTION_ID functionId, fun.FUNCTION_DESC functionDesc ");
		sql.append("from ha_hr_ea_function fun ");
		 
		 List<GroupFunctionDto> list =  namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<GroupFunctionDto>(GroupFunctionDto.class));
		return list;
	}

	public List<GroupRankDto> getGroupRankList(long groupId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select RANK rank from HA_HR_EA_GROUP_RANK ");
		sql.append("where 1 = 1 ");
		sql.append("and GROUP_ID = :groupId ");
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("groupId", groupId);
		 
		 List<GroupRankDto> list =  namedParameterJdbcTemplate.query(sql.toString(),paramSource, new BeanPropertyRowMapper<GroupRankDto>(GroupRankDto.class));
		return list;
	}

	public List<GroupFunctionDto> getGroupFunctionList(long groupId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select grpFun.FUNCTION_ID functionId, grpFun.ACCESS_RIGHT accessRight, fun.FUNCTION_DESC functionDesc ");
		sql.append("from HA_HR_EA_GROUP_FUNCTION grpFun,ha_hr_ea_function fun ");
		sql.append("where 1 = 1 ");
		sql.append("and grpFun.GROUP_ID = :groupId ");
		sql.append("and grpFun.FUNCTION_ID = fun.FUNCTION_ID ");
		
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("groupId", groupId);
		 
		 List<GroupFunctionDto> list =  namedParameterJdbcTemplate.query(sql.toString(),paramSource, new BeanPropertyRowMapper<GroupFunctionDto>(GroupFunctionDto.class));
			return list;
	}

	public void addUserGroup(UserGroupEnquiryDTO userGroupEnquiryDTO) throws Exception{

		log.info("insert user group======================");
		EaUserGrp usergrp = new EaUserGrp();
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		usergrp.setGroupName(userGroupEnquiryDTO.getUserGroupName());
		usergrp.setCreatedBy(username);
		usergrp.setCreationDate(new Date());
		usergrp.setLastUpdatedBy(username);
		usergrp.setLastUpdatedDate(new Date());
		hibernateTemplate.save(usergrp);
		
		Long groupId = usergrp.getGroupId();
		userGroupEnquiryDTO.setGroupId(groupId);
		
		log.info("insert user group rank======================"+userGroupEnquiryDTO.getSelRankList().size());
		//insert group rank
		//List<GroupRankDto> rankList = new ArrayList<GroupRankDto>();
		List<EaGroupRank> eaRankList = convertToEaRank(groupId,username,userGroupEnquiryDTO.getSelRankList());
		if(eaRankList.size() > 0){
			for(int i=0;i<eaRankList.size();i++){
				log.info("insert user group rank========"+eaRankList.get(i).getRank()+"=============");
				if(eaRankList.get(i) != null){
					hibernateTemplate.save(eaRankList.get(i));
				}
				
			}
		}
		
		log.info("insert user group function======================"+userGroupEnquiryDTO.getFunctionList().size());
		//insert group function
		List<EaGroupFunction> eafunList = new ArrayList<EaGroupFunction>();
		eafunList = convertToEaFun(groupId,userGroupEnquiryDTO.getFunctionList());
		for(EaGroupFunction fun : eafunList){
			log.info("insert user group function========"+fun.getFunctionId()+"=============");
			fun.setCreatedBy(username);
			fun.setCreationDate(new Date());
			fun.setLastUpdatedBy(username);
			fun.setLastUpdateDate(new Date());
			hibernateTemplate.save(fun);
		}
		
	}
	
	public void updateUserGroup(UserGroupEnquiryDTO userGroupEnquiryDTO) throws Exception{

		
		Long groupId = userGroupEnquiryDTO.getGroupId();
		log.info("update user group======================");
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		EaUserGrp usergrp = new EaUserGrp();
		usergrp.setGroupId(groupId);
		usergrp.setGroupName(userGroupEnquiryDTO.getUserGroupName());
		usergrp.setLastUpdatedBy(username);
		usergrp.setLastUpdatedDate(new Date());
		hibernateTemplate.update(usergrp);
		
		log.info("user group SelRank======================"+userGroupEnquiryDTO.getSelRankList().size());
		//update group rank
		List<GroupRankDto> rankList = getGroupRankList(groupId);
		List<EaGroupRank> dbRankList = convertToEaRank(groupId,username,rankList);
		List<EaGroupRank> eaRankList = convertToEaRank(groupId,username,userGroupEnquiryDTO.getSelRankList());
		if(dbRankList.size() > 0){
			for(int i=0;i<dbRankList.size();i++){
				log.info("delete user group Rank======================"+dbRankList.size());
				if(dbRankList.get(i) != null){
					hibernateTemplate.delete(dbRankList.get(i));
				}
			}
		}
		if(eaRankList.size() > 0){
			log.info("user group Rank======================"+eaRankList.size());
			for(int i=0;i<eaRankList.size();i++){
				if(eaRankList.get(i) != null){
					log.info("user group Rank date======================"+eaRankList.get(i).getLastUpdateDate());
					hibernateTemplate.save(eaRankList.get(i));
					/*if(eaRankList.get(i).getLastUpdatedDate() != null){
						log.info("update user group rank ==="+eaRankList.get(i).getId().getRank()+"==group id=="+eaRankList.get(i).getId().getGroupId());
						hibernateTemplate.update(eaRankList.get(i));
					}else{
						log.info("insert user group rank ==="+eaRankList.get(i).getId().getRank()+"==group id=="+eaRankList.get(i).getId().getGroupId());
						hibernateTemplate.save(eaRankList.get(i));
					}*/
				}
			}
		}
		
		log.info("update user group function======================"+userGroupEnquiryDTO.getFunctionList().size());
		//update group function
		List<EaGroupFunction> eafunList = new ArrayList<EaGroupFunction>();
		eafunList = convertToEaFun(groupId,userGroupEnquiryDTO.getFunctionList());
		for(EaGroupFunction fun : eafunList){
			fun.setCreatedBy(username);
			fun.setCreationDate(new Date());
			fun.setLastUpdatedBy(username);
			fun.setLastUpdateDate(new Date());
			hibernateTemplate.update(fun);
		}
		
	}

	private List<EaGroupFunction> convertToEaFun(Long groupId,List<GroupFunctionDto> functionList) {
		List<EaGroupFunction> eafunList = new ArrayList<EaGroupFunction>();
		
		for(int i=0; i<functionList.size(); i++){
			GroupFunctionDto fun = functionList.get(i);
			EaGroupFunction eaFun = new EaGroupFunction();
			//EaGroupFunctionPK pk = new EaGroupFunctionPK();
			
			eaFun.setGroupId(groupId);
			eaFun.setFunctionId(fun.getFunctionId());
			eaFun.setAccessRight(fun.getAccessRight());
			//eaFun.setId(pk);
			eafunList.add(eaFun);
		}
		
		return eafunList;
	}

	private List<EaGroupRank> convertToEaRank(Long groupId,String username, List<GroupRankDto> selRankList) {
		List<EaGroupRank> list = new ArrayList<EaGroupRank>();
		EaGroupRank eaRank = null;
		EaGroupRankPK pk = null;
		if(selRankList != null && selRankList.size() > 0){
			/*if(rankList != null && rankList.size() >0){
				for(GroupRankDto rank : rankList){
					for(int i=0; i<selRankList.size(); i++){
						GroupRankDto dto = selRankList.get(i);
						
						eaRank = new EaGroupRank();
						pk = new EaGroupRankPK();
						
						pk.setGroupId(groupId);
						pk.setRank(dto.getRank());
						eaRank.setId(pk);
						
						if(rank.getRank().equals(dto.getRank())){
							
							eaRank.setLastUpdatedBy(username);
							eaRank.setLastUpdatedDate(new Date());
							
						}else{
							
							eaRank.setCreatedBy(username);
							eaRank.setCreationDate(new Date());
							
						}
						list.add(eaRank);
					}
				}
				return list;
			}*/
			
			//add new rank
			for(int i=0; i<selRankList.size(); i++){
				GroupRankDto dto = selRankList.get(i);
				if(!Util.isEmptyString(dto.getRank())){
					eaRank = new EaGroupRank();
					//pk = new EaGroupRankPK();
					
					eaRank.setGroupId(groupId);
					eaRank.setRank(dto.getRank());
					
					//eaRank.setId(pk);
					eaRank.setCreatedBy(username);
					eaRank.setCreationDate(new Date());
					eaRank.setLastUpdatedBy(username);
					eaRank.setLastUpdateDate(new Date());
					
					list.add(eaRank);
				}
				
			}
		}
		
		return list;
	}

	public List<GroupFunctionDto> getGroupFunctionListByAccess(long groupId, String access) {
		StringBuffer sql = new StringBuffer();
		sql.append("select grpFun.FUNCTION_ID functionId, grpFun.ACCESS_RIGHT accessRight, fun.FUNCTION_DESC functionDesc ");
		sql.append("from HA_HR_EA_GROUP_FUNCTION grpFun,ha_hr_ea_function fun ");
		sql.append("where 1 = 1 ");
		sql.append("and grpFun.GROUP_ID = :groupId ");
		sql.append("and grpFun.ACCESS_RIGHT = :access ");
		sql.append("and grpFun.FUNCTION_ID = fun.FUNCTION_ID ");
		
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("groupId", groupId);
		paramSource.addValue("access", access);
		 
		 List<GroupFunctionDto> list =  namedParameterJdbcTemplate.query(sql.toString(),paramSource, new BeanPropertyRowMapper<GroupFunctionDto>(GroupFunctionDto.class));
			return list;
	}

}
