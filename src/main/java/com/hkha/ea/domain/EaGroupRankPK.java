package com.hkha.ea.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//@Embeddable
public class EaGroupRankPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7304019594640880146L;

	@Column(name="GROUP_ID", nullable = false,length = 22)
	private Long groupId;
	
	@Column(name="RANK", nullable = false,length = 40)
	private String rank;

	public EaGroupRankPK(){
		
	}
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof EaGroupRankPK){ 
        	EaGroupRankPK pk=(EaGroupRankPK)obj; 
            if(this.groupId.equals(pk.groupId)&&this.rank.equals(pk.rank)){ 
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
