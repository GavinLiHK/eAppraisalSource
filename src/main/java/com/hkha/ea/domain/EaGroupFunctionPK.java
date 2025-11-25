package com.hkha.ea.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//@Embeddable
public class EaGroupFunctionPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086792384381945645L;

	@Column(name="GROUP_ID", nullable = false,length = 22)
	private Long groupId;
	
	@Column(name="FUNCTION_ID", nullable = false,length = 22)
	private Long functionId;

	public EaGroupFunctionPK(){
		
	}
	
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	
	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof EaGroupFunctionPK){ 
        	EaGroupFunctionPK pk=(EaGroupFunctionPK)obj; 
            if(this.groupId.equals(pk.groupId)&&this.functionId.equals(pk.functionId)){ 
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
