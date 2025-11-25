package com.hkha.ea.dto.assess;

public class AppraiseeBatchDTO {
	
	private String batchId;
	
	private int noOfAssigned;
	
	private int noOfUnassigned;
	
	private int noOfAppraisee;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public int getNoOfAssigned() {
		return noOfAssigned;
	}

	public void setNoOfAssigned(int noOfAssigned) {
		this.noOfAssigned = noOfAssigned;
	}

	public int getNoOfUnassigned() {
		return noOfUnassigned;
	}

	public void setNoOfUnassigned(int noOfUnassigned) {
		this.noOfUnassigned = noOfUnassigned;
	}

	public int getNoOfAppraisee() {
		return noOfAppraisee;
	}

	public void setNoOfAppraisee(int noOfAppraisee) {
		this.noOfAppraisee = noOfAppraisee;
	}

}
