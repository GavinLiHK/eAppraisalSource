package com.hkha.ea.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

//@Embeddable 
public class EaMessageLogPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 895049974219309227L;

	@Column(name = "REPORT_ID", nullable = false,length = 22)
	private Long reportId;
	
	@Column(name = "MESSAGE_ID", nullable = false,length = 22)
	private Long messageId;
	
	public EaMessageLogPK(){
		
	}
	
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	
	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof EaWorkflowRolePK){ 
        	EaMessageLogPK pk=(EaMessageLogPK)obj; 
            if(this.reportId.equals(pk.reportId)&&this.messageId.equals(pk.messageId)){ 
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
