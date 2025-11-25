package com.hkha.ea.domain.security;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hkha.ea.common.Constants;


public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
    private String userName;
    
    private String password;
    
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Constants.LOGIN_USER_ROLE));
        return grantedAuthorities;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
    
    
}
