package com.hkha.ea.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
//@Embeddable  
public class EaWorkflowRolePK implements Serializable{
	
	private static final long serialVersionUID = -3304319243957837925L;  
	
	@Column(name = "WORKFLOW_TEMPLATE_ID", nullable = false,length = 22)
	private Long workflowTemplateId;
	@Column(name = "ROLE", nullable = false,length = 3)
	private String role;
	
	
	public EaWorkflowRolePK() {}
	
	public Long getWorkflowTemplateId() {
		return workflowTemplateId;
	}

	public void setWorkflowTemplateId(Long workflowTemplateId) {
		this.workflowTemplateId = workflowTemplateId;
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
        	EaWorkflowRolePK pk=(EaWorkflowRolePK)obj; 
            if(this.workflowTemplateId.equals(pk.workflowTemplateId)&&this.role.equals(pk.role)){ 
                return true; 
            } 
        } 
        return false; 
    }

    @Override 
    public int hashCode() { 
        return super.hashCode(); 
    }
	
}
