package com.hkha.ea.dto.common;

import java.util.Date;

public class EaLoginDTO extends EaBaseEntityDTO{
	
	private String loginId;
	
	private String token;
	
	private Date lastLogin;
	
	private Date lastLogout;
	
	public String getLoginId() {
		return loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public Date getLastLogout() {
		return lastLogout;
	}
	
	public void setLastLogout(Date lastLogout) {
		this.lastLogout = lastLogout;
	}

}
