package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
//@IdClass(com.hkha.ea.domain.EaMessageLogPK.class)
@Table(name = "HA_HR_EA_MESSAGE_LOG")
@SequenceGenerator(name="seqMessageId",sequenceName="HA_HR_EA_SEQ_MESSAGE_ID",allocationSize=1,initialValue=1)
public class EaMessageLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 210185815839864945L;
	
	@Id
/*	@EmbeddedId
	private EaMessageLogPK id;*/
	@Column(name = "REPORT_ID", nullable = false,length = 22)
	private Long reportId;
	
	@Id
	@GeneratedValue(generator="seqMessageId")
	@Column(name = "MESSAGE_ID", nullable = false,length = 22)
	private Long messageId;
	
	//@EmbeddedId
	//private EaMessageLogPK id;
	/*@Id
	private Long reportId;
	@Id
	private String messageId;*/
	
	@Column(name = "MESSAGE_TYPE", length = 1,nullable = false)
	private String messageType;
	
	@Column(name = "TYPE", length = 1,nullable = false)
	private String type;
	
	@Column(name = "ROLE", length = 3)
	private String role;
	
	@Column(name = "RECIPIENT", length = 10)
	private String recipient;
	
	@Column(name = "CC1", length = 10)
	private String cc1;
	
	@Column(name = "CC2", length = 10)
	private String cc2;
	
	@Column(name = "CC3", length = 10)
	private String cc3;
	
	@Column(name = "CC4", length = 10)
	private String cc4;
	
	@Column(name = "CC5", length = 10)
	private String cc5;
	
	@Column(name = "LAST_UPDATED_BY", length = 100)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE", length = 7)
	private Date lastUpdateDate;
	
	@Column(name = "CREATED_BY", length = 100)
	private String createdBy;
	
	@Column(name = "CREATION_DATE", length = 7)
	private Date creationDate;
	
	@Column(name = "EMAIL_IND", length = 1)
	private String emailInd;
	
	@Column(name = "RECIPIENT_ROLE", length = 3)
	private String recipientRole;
	
	
	public Long getMessageId() {
		return messageId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getCc1() {
		return cc1;
	}
	public void setCc1(String cc1) {
		this.cc1 = cc1;
	}
	public String getCc2() {
		return cc2;
	}
	public void setCc2(String cc2) {
		this.cc2 = cc2;
	}
	public String getCc3() {
		return cc3;
	}
	public void setCc3(String cc3) {
		this.cc3 = cc3;
	}
	public String getCc4() {
		return cc4;
	}
	public void setCc4(String cc4) {
		this.cc4 = cc4;
	}
	public String getCc5() {
		return cc5;
	}
	public void setCc5(String cc5) {
		this.cc5 = cc5;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getEmailInd() {
		return emailInd;
	}
	public void setEmailInd(String emailInd) {
		this.emailInd = emailInd;
	}
	public String getRecipientRole() {
		return recipientRole;
	}
	public void setRecipientRole(String recipientRole) {
		this.recipientRole = recipientRole;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
