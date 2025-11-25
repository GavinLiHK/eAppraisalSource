package com.hkha.ea.helper;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hkha.ea.domain.EaBaseEntity;

public class BaseEntityUtility {
	public static void setEACommonProperties(EaBaseEntity bea){
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		setEACommonProperties(bea, currentPrincipalName);
	}
	
	public static void setEACommonProperties(EaBaseEntity bea, String createdBy){
		if (StringUtils.isEmpty(bea.getCreatedBy())){
			bea.setCreatedBy(createdBy);
			bea.setCreationDate(new Date());
		}
		
		updateEACommonProperties(bea, createdBy);
	}
	
	public static void updateEACommonProperties(EaBaseEntity bea){
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		updateEACommonProperties(bea, currentPrincipalName);
	}
	
	public static void updateEACommonProperties(EaBaseEntity bea, String lastUpdatedBy){
		bea.setLastUpdatedBy(lastUpdatedBy);
		bea.setLastUpdatedDate(new Date());
	}
}
