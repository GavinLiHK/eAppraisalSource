package com.hkha.ea.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//@Embeddable  
public class EaCoreCompetencyPK implements Serializable{
	
	private static final long serialVersionUID = -3304319243957837925L; 
	
	@Column(name = "REPORT_ID", nullable = false,length = 22)
	private Long reportId;
	
	@Column(name = "SEQ_NO", nullable = false,length = 22)
	private Long seqNo;

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}
	
	public EaCoreCompetencyPK(){}
	
	@Override 
    public boolean equals(Object obj) { 
        if(obj instanceof EaCoreCompetencyPK){ 
        	EaCoreCompetencyPK pk=(EaCoreCompetencyPK)obj; 
            if(this.reportId.equals(pk.reportId) && this.seqNo.equals(pk.seqNo)){ 
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
