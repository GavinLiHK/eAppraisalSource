package com.hkha.ea.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//@Embeddable 
public class EaObjectivesPK implements Serializable{
	
	private static final long serialVersionUID = -3304319243957837925L;
	
	@Column(name = "REPORT_ID", nullable = false,length = 22)
	private Long reportId;
	
	@Column(name = "TYPE", nullable = false,length = 10)
	private String type;
	
	@Column(name = "SEQ_NO", nullable = false,length = 22)
	private Long seqNo;

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}
	
	public EaObjectivesPK() {}
	
	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof EaObjectivesPK){ 
        	EaObjectivesPK pk=(EaObjectivesPK)obj; 
            if(this.reportId.equals(pk.reportId)&&this.type.equals(pk.type) && this.seqNo.equals(pk.seqNo)){ 
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
