package com.hkha.ea.dto.security;

public class GroupFunctionDto {

	private long groupId;
	private long functionId;
	private String functionDesc;
	private String accessRight;
	
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(long functionId) {
		this.functionId = functionId;
	}
	public String getAccessRight() {
		return accessRight;
	}
	public void setAccessRight(String accessRight) {
		this.accessRight = accessRight;
	}
	public String getFunctionDesc() {
		return functionDesc;
	}
	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}
	
}
