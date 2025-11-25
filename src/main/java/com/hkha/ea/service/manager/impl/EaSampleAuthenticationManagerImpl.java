package com.hkha.ea.service.manager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.hkha.ea.common.Constants;
import com.hkha.ea.dao.security.UserEnquiryDAO;
import com.hkha.ea.dto.common.EaLoginDTO;
import com.hkha.ea.dto.common.EaUserDTO;
import com.hkha.ea.service.manager.EaSampleAuthenticationManager;

@Service("eaSampleAuthenticationManager")
public class EaSampleAuthenticationManagerImpl implements EaSampleAuthenticationManager{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(EaSampleAuthenticationManagerImpl.class.getName());
	//End 20231201 Write log in catalina.out
	  
	@Autowired
	private UserEnquiryDAO userEnquiryDAO;
	  
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		log.info("auth.getPrincipal(): " + auth.getName());
		
		EaUserDTO eaUser = userEnquiryDAO.searchByUserID(auth.getName());
		EaLoginDTO loggedEmployeeDTO = userEnquiryDAO.searchByLoginID(auth.getName());
		long groupId;
		if(eaUser != null){
			groupId = eaUser.getGroupId();
		}else{
			groupId = 0;
		}
		
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		//grantedAuthorities.add(new SimpleGrantedAuthority(Constants.LOGIN_USER_ROLE));
		log.info( Long.toString(groupId) );
		
		//Temporary switch statment in lieu of database table and DAO object.
        switch ((int)groupId){
            case -1:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_SYSADM));
                break;
            case 3:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GG));
                break;
            case 4:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CRT_RPT_GG));
                break;
            case 5:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GMH_GMW));
                break;
            case 8:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GMH1));
                break;
            case 9:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GMW1));
                break;
            case 10:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GMW2));
                break;
            case 11:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GMW3));
                break;
            case 21:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GG1));
                break;
            case 41:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GMW4));
                break;
            case 61:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GMH2));
                break;
            case 81:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GMH3));
                break;
            case 101:
                grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CFM_RPT_GMH4));
                break;
            default:
            	grantedAuthorities.add(new SimpleGrantedAuthority(Constants.LOGIN_USER_ROLE));
                break;
        }
		
		if (auth.getCredentials().equals(loggedEmployeeDTO.getToken())) {
			return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), grantedAuthorities);
		}
		throw new BadCredentialsException("Bad Credentials");
	}

}
