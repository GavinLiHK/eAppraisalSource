package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Vector;

import com.hkha.ea.common.Util;

public class DummyEmployee extends java.lang.Object implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DeadlineAgent deadlineAgent = null;
	private String number = null;
	private String overridingNumber = null;
	private String name = null;
	private String email = null;
	private Vector reports = new Vector();

	public String batchID = null;
	private String notification = null;
	private String notificationFooter = null;

	public DummyEmployee(String nu, String na, String em) {
		number = nu; name = na; email = em;
	}
	public DummyEmployee(String nu, String na, String em, String bi, String ni) {
		number = nu; name = na; email = em; batchID = bi; notification = ni;
	}
	public DummyEmployee(String nu, String na, String em, String bi, String ni, String nf) {
		number = nu; name = na; email = em; batchID = bi; notification = ni; notificationFooter = nf;
	}
	public boolean hasEmail() {
	    return !Util.isEmptyString(email);
	}
	public String getHasEmail() {
	    return Util.isEmptyString(email)?"N":"Y";
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getOverridingNumber() {
		return overridingNumber;
	}
	public void setOverridingNumber(String overridingNumber) {
		this.overridingNumber = overridingNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Vector getReports() {
		return reports;
	}
	public void setReports(Vector reports) {
		this.reports = reports;
	}
	public String getBatchID() {
		return batchID;
	}
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getNotificationFooter() {
		return notificationFooter;
	}
	public void setNotificationFooter(String notificationFooter) {
		this.notificationFooter = notificationFooter;
	}
	public DeadlineAgent getDeadlineAgent() {
		return deadlineAgent;
	}
	public void setDeadlineAgent(DeadlineAgent deadlineAgent) {
		this.deadlineAgent = deadlineAgent;
	}
	
}
