package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;

public class DummyReportInfo extends java.lang.Object implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String status;
	private String rank;
	private Date start;
	private Date end;
	private Date dueDate;
	private String recipientRole;
	private String presentPost;
	
	public DummyReportInfo(int d, String na, String ss, String ra, Date st, Date en) {
		id = d; name = na; status = ss; rank = ra; start = st; end = en;
	}
	
	public DummyReportInfo(int d, String na, String ss, String ra, Date st, Date en, String po) {
		id = d; name = na; status = ss; rank = ra; start = st; end = en;presentPost = po;
	}
	public String toDisplayString() {
	    return toDisplayString(false);
	}
	/**
	 * String in format (name, rank, appraisal start - end, [due date])
	 * @param showDueDate If true, show due date at the end
	 * @return String of report information
	 */
	public String toDisplayString(boolean showDueDate) {
		StringBuffer ret = new StringBuffer();
		ret
		.append(name).append(", ")
		.append(rank).append(", ")
		.append(DateTimeUtil.date2String(start, Constants.DISPLAY_DATE_FORMAT))
		.append(" - ")
		.append(DateTimeUtil.date2String(end, Constants.DISPLAY_DATE_FORMAT));
		if (showDueDate) {
		    if (dueDate != null)
			    ret
				.append(", ")
				.append(DateTimeUtil.date2String(dueDate, Constants.DISPLAY_DATE_FORMAT));
		}
		return ret.toString();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getRecipientRole() {
		return recipientRole;
	}
	public void setRecipientRole(String recipientRole) {
		this.recipientRole = recipientRole;
	}

	public String getPresentPost() {
		return presentPost;
	}

	public void setPresentPost(String presentPost) {
		this.presentPost = presentPost;
	}
	
}
