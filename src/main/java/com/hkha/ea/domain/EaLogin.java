package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA_HR_EA_LOGIN")
public class EaLogin extends EaBaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String loginId;
	
	private String token;
	
	private Date lastLogin;
	
	private Date lastLogout;

	@Id
	@Column(name = "LOGIN_ID", length = 10, nullable = false)
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	@Column(name = "TOKEN", length = 20, nullable = false)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "LAST_LOGIN")
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Column(name = "LAST_LOGOUT")
	public Date getLastLogout() {
		return lastLogout;
	}

	public void setLastLogout(Date lastLogout) {
		this.lastLogout = lastLogout;
	}
	
}
