package com.hkha.ea.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//@Embeddable  
public class EaReportRolePK  implements Serializable{
	
	private static final long serialVersionUID = -3304319243957837925L;  
	
	@Column(name = "REPORT_ID", nullable = false,length = 22)
	private Long reportId;
	@Column(name = "ROLE", nullable = false,length = 3)
	private String role;
	
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof EaWorkflowRolePK){ 
        	EaReportRolePK pk=(EaReportRolePK)obj; 
            if(this.reportId.equals(pk.reportId)&&this.role.equals(pk.role)){ 
                return true; 
            } 
        } 
        return false; 
    }

    @Override 
    public int hashCode() { 
        return super.hashCode(); 
    }
    public EaReportRolePK() {}
}
