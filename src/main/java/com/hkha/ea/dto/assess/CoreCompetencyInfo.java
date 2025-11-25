package com.hkha.ea.dto.assess;

public class CoreCompetencyInfo {
	
	private long reportId;
	
	private long seqNo;
	
	private String competency;
	
	private String rating;
	
	private long competencyId;

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}

	public String getCompetency() {
		return competency;
	}

	public void setCompetency(String competency) {
		this.competency = competency;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public long getCompetencyId() {
		return competencyId;
	}

	public void setCompetencyId(long competencyId) {
		this.competencyId = competencyId;
	}
	
	
}
