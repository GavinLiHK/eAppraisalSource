package com.hkha.ea.service.common.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hkha.ea.common.Constants;
import com.hkha.ea.dto.common.EaLoginDTO;
import com.hkha.ea.service.common.UserDetailsService;
import com.hkha.ea.service.security.UserEnquireService;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserEnquireService userEnquireService;

	public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
		UserDetails userDetails = null;
		
		String[] credentials = (String[]) token.getPrincipal();
		boolean principal = Boolean.valueOf(token.getCredentials().toString());
		boolean verified = false;
		if (credentials != null && principal == true) {
			String loginName = credentials[0];
			String loginToken = credentials[1];
			
			EaLoginDTO loggedEmployeeDTO = userEnquireService.searchByLoginID(loginName);
			
			// should changed to verify DB token
			verified = loginToken.equals(loggedEmployeeDTO.getToken());
			
			// bypass checking *** FOR TESTING ONLY ***
			if (!verified){
				verified = loginName.equalsIgnoreCase(loginToken);
			}
			
			if (verified) {
				
				Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
				grantedAuthorities.add(new SimpleGrantedAuthority(Constants.LOGIN_USER_ROLE));
				
				userDetails = new User(loginName, loginToken, true, true, true, true, grantedAuthorities);
			}
			
		}
		
		if (userDetails == null) {
	        throw new UsernameNotFoundException("Could not load user : " + credentials[0]);
	    }
		
		return userDetails;
	}

}
