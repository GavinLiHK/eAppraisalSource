package com.hkha.ea.dto.assess;

import java.util.List;
import java.util.Map;

import com.hkha.ea.dto.admin.SystemParameterDTO;

public class PrintSubmitInfo {
	
	private String routing;
	
	private String nextOfficerId;
	
	private String nextOfficerName;
	
	private String nextOfficerRole;
	
	private String deadline;
	
	private String gmOfficerId;
	
	private String gmOfficerName;
	
	private String gmDeadline;
	
	private String routingTo;
	
	private List<SystemParameterDTO> routingToList;
	
	private String routingReason;
	
	private String confirmReject;

	private List<SystemParameterDTO> routingLabelList;
	
	
	public String getRouting() {
		return routing;
	}

	public void setRouting(String routing) {
		this.routing = routing;
	}

	public String getNextOfficerId() {
		return nextOfficerId;
	}

	public void setNextOfficerId(String nextOfficerId) {
		this.nextOfficerId = nextOfficerId;
	}

	public String getNextOfficerName() {
		return nextOfficerName;
	}

	public void setNextOfficerName(String nextOfficerName) {
		this.nextOfficerName = nextOfficerName;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getGmOfficerId() {
		return gmOfficerId;
	}

	public void setGmOfficerId(String gmOfficerId) {
		this.gmOfficerId = gmOfficerId;
	}

	public String getGmOfficerName() {
		return gmOfficerName;
	}

	public void setGmOfficerName(String gmOfficerName) {
		this.gmOfficerName = gmOfficerName;
	}

	public String getGmDeadline() {
		return gmDeadline;
	}

	public void setGmDeadline(String gmDeadline) {
		this.gmDeadline = gmDeadline;
	}

	public String getRoutingTo() {
		return routingTo;
	}

	public void setRoutingTo(String routingTo) {
		this.routingTo = routingTo;
	}


	public List<SystemParameterDTO> getRoutingToList() {
		return routingToList;
	}

	public void setRoutingToList(List<SystemParameterDTO> routingToList) {
		this.routingToList = routingToList;
	}

	public String getRoutingReason() {
		return routingReason;
	}

	public void setRoutingReason(String routingReason) {
		this.routingReason = routingReason;
	}

	public String getConfirmReject() {
		return confirmReject;
	}

	public void setConfirmReject(String confirmReject) {
		this.confirmReject = confirmReject;
	}

	public List<SystemParameterDTO> getRoutingLabelList() {
		return routingLabelList;
	}

	public void setRoutingLabelList(List<SystemParameterDTO> routingLabelList) {
		this.routingLabelList = routingLabelList;
	}

	public String getNextOfficerRole() {
		return nextOfficerRole;
	}

	public void setNextOfficerRole(String nextOfficerRole) {
		this.nextOfficerRole = nextOfficerRole;
	}








	
	
}
