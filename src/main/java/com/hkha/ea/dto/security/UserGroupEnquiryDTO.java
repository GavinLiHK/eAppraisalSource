package com.hkha.ea.dto.security;

import java.util.List;

public class UserGroupEnquiryDTO {

	private Long groupId;
	private String userGroupName;
	private String avaRankarr;
	private String selRankarr;
	private String functionarr;
	private List<GroupFunctionDto> functionList;
	private List<GroupRankDto> selRankList;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public List<GroupFunctionDto> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<GroupFunctionDto> functionList) {
		this.functionList = functionList;
	}

	public List<GroupRankDto> getSelRankList() {
		return selRankList;
	}

	public void setSelRankList(List<GroupRankDto> selRankList) {
		this.selRankList = selRankList;
	}

	public String getSelRankarr() {
		return selRankarr;
	}

	public void setSelRankarr(String selRankarr) {
		this.selRankarr = selRankarr;
	}

	public String getFunctionarr() {
		return functionarr;
	}

	public void setFunctionarr(String functionarr) {
		this.functionarr = functionarr;
	}

	public String getAvaRankarr() {
		return avaRankarr;
	}

	public void setAvaRankarr(String avaRankarr) {
		this.avaRankarr = avaRankarr;
	}
	
}
