package com.hkha.ea.domain;

public class EaURL {

	private String id;
	
	private String displayName;
	
	private String url;
	
	public EaURL(String displayName, String url) {
		String id = displayName.replaceAll("\\s+","");
			
		this.id = id;
		this.displayName = displayName;
		this.url = url;
	}
	
	public EaURL(String id, String displayName, String url) {
		this.id = id;
		this.displayName = displayName;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
