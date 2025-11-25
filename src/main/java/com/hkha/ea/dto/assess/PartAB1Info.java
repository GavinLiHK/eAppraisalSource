package com.hkha.ea.dto.assess;

public class PartAB1Info {
	
	private String keyObjectives;
	
	private String resultAchieved;
	
	//edited on 20171205 change weighting from int to double
	//private Integer weighting;
	private Double weighting;
	//end edited on 20171205 change weighting from int to double
	private String rating;
	
	private String type;
	
	private long seqNo;

	public String getKeyObjectives() {
		return keyObjectives;
	}

	public void setKeyObjectives(String keyObjectives) {
		this.keyObjectives = keyObjectives;
	}

	public String getResultAchieved() {
		return resultAchieved;
	}

	public void setResultAchieved(String resultAchieved) {
		this.resultAchieved = resultAchieved;
	}
	//edited on 20171205 change weighting from int to double
	/*public Integer getWeighting() {
		return weighting;
	}

	public void setWeighting(Integer weighting) {
		this.weighting = weighting;
	}*/
	public Double getWeighting() {
		return weighting;
	}

	public void setWeighting(Double weighting) {
		this.weighting = weighting;
	}
	// end edited on 20171205 change weighting from int to double
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
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
	
	
}
