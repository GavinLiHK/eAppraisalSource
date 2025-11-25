package com.hkha.ea.helper;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;

import com.hkha.ea.dto.common.EaBaseEntityDTO;

public class BaseDTOUtility {

	public static void setEADTOCommonProperties(EaBaseEntityDTO bea){
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		setEADTOCommonProperties(bea, currentPrincipalName);
	}
	
	public static void setEADTOCommonProperties(EaBaseEntityDTO bea, String createdBy){
		bea.setCreatedBy(createdBy);
		bea.setCreationDate(new Date());
		updateEADTOCommonProperties(bea, createdBy);
	}
	
	public static void updateEADTOCommonProperties(EaBaseEntityDTO bea){
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		updateEADTOCommonProperties(bea, currentPrincipalName);
	}
	
	public static void updateEADTOCommonProperties(EaBaseEntityDTO bea, String lastUpdatedBy){
		bea.setLastUpdatedBy(lastUpdatedBy);
		bea.setLastUpdatedDate(new Date());
	}
}
