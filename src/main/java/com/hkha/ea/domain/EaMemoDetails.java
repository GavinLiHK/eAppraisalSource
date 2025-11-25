package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA_HR_EA_MEMO_DETAILS")
public class EaMemoDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5642071355446602691L;

	@Id
	@Column(name = "REPORT_ID", nullable = false,length = 22)
	private long reportId;
	
	@Column(name = "MEMO_FROM", length = 240)
	private String memoFrom;
	
	@Column(name = "REF1", length = 6)
	private String ref1;
	
	@Column(name = "REF2", length = 20)
	private String ref2;
	
	@Column(name = "TEL_NO", length = 20)
	private String telNo;
	
	@Column(name = "FAX_NO", length = 20)
	private String faxNo;
	
	@Column(name = "MEMO_FROM_DATE", length = 20)
	private String memoFromDate;
	
	@Column(name = "MEMO_TO", length = 240)
	private String memoTo;
	
	@Column(name = "VIA", length = 30)
	private String via;
	
	@Column(name = "YOUR_REF1", length = 6)
	private String yourRef1;
	
	@Column(name = "YOUR_REF2", length = 20)
	private String yourRef2;
	
	@Column(name = "MEMO_TO_DATE", length = 20)
	private String memoToDate;
	
	@Column(name = "TOTAL_PAGES", length = 10)
	private String totalPages;
	
	@Column(name = "DUTY_DESC", length = 3000)
	private String dutyDesc;
	
	@Column(name = "OVERALL_PERF", length = 1)
	private String overallPerf;
	
	@Column(name = "OVERALL_COMMENT", length = 3000)
	private String overallComment;
	
	@Column(name = "PERF_BONUS_AO", length = 1)
	private String perfBonusAo;
	//edited on20170228 SALARY_ADJ_AO value disappear
	@Column(name = "SALARY_ADJ_AO", length = 1)
	//private String salaryADJAo;
	private String salaryAdjAo;
	
	@Column(name = "APP_INTERVIEW", length = 2000)
	private String appInterview;
	
	@Column(name = "APP_NAME", length = 240)
	private String appName;
	
	@Column(name = "APP_DATE", length = 10)
	private String appDate;
	
	@Column(name = "AO_NAME", length = 240)
	private String aoName;
	
	@Column(name = "AO_RANK", length = 40)
	private String aoRank;
	
	@Column(name = "PERF_BONUS_CO", length = 1)
	private String perfBonusCo;
	//edited on 20170228
	@Column(name = "SALARY_ADJ_CO", length = 1)
	//private String salaryADJCo;
	private String salaryAdjCo;
	
	@Column(name = "CO_NAME", length = 240)
	private String coName;
	
	@Column(name = "CO_DATE", length = 7)
	private Date coDate;
	
	@Column(name = "CO_RANK", length = 40)
	private String coRank;
	
	@Column(name = "IO_NAME", length = 240)
	private String ioName;
	
	@Column(name = "IO_RANK", length = 40)
	private String ioRank;
	
	@Column(name = "IO_DATE", length = 7)
	private Date ioDate;
	
	@Column(name = "AUDIT_ENABLE_GM", length = 1)
	private String auditEnableGm;
	
	@Column(name = "AUDIT_ENABLE_AP", length = 1)
	private String auditEnableAp;
	
	@Column(name = "AUDIT_ENABLE_AO", length = 1)
	private String auditEnableAo;
	
	@Column(name = "AUDIT_ENABLE_CO", length = 1)
	private String auditEnableCo;
	
	@Column(name = "AUDIT_ENABLE_RO", length = 1)
	private String auditEnableRo;
	
	@Column(name = "AUDIT_ENABLE_EO", length = 1)
	private String auditEnableEo;
	
	@Column(name = "AUDIT_ENABLE_IO", length = 1)
	private String auditEnableIo;
	
	@Column(name = "LAST_UPDATED_BY", length = 100)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE", length = 7)
	private Date lastUpdateDate;
	
	@Column(name = "CREATED_BY", length = 100)
	private String createdBy;
	
	@Column(name = "CREATION_DATE", length = 7)
	private Date creationDate;

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getMemoFrom() {
		return memoFrom;
	}

	public void setMemoFrom(String memoFrom) {
		this.memoFrom = memoFrom;
	}

	public String getRef1() {
		return ref1;
	}

	public void setRef1(String ref1) {
		this.ref1 = ref1;
	}

	public String getRef2() {
		return ref2;
	}

	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getMemoFromDate() {
		return memoFromDate;
	}

	public void setMemoFromDate(String memoFromDate) {
		this.memoFromDate = memoFromDate;
	}

	public String getMemoTo() {
		return memoTo;
	}

	public void setMemoTo(String memoTo) {
		this.memoTo = memoTo;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getYourRef1() {
		return yourRef1;
	}

	public void setYourRef1(String yourRef1) {
		this.yourRef1 = yourRef1;
	}

	public String getYourRef2() {
		return yourRef2;
	}

	public void setYourRef2(String yourRef2) {
		this.yourRef2 = yourRef2;
	}

	public String getMemoToDate() {
		return memoToDate;
	}

	public void setMemoToDate(String memoToDate) {
		this.memoToDate = memoToDate;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	public String getDutyDesc() {
		return dutyDesc;
	}

	public void setDutyDesc(String dutyDesc) {
		this.dutyDesc = dutyDesc;
	}

	public String getOverallPerf() {
		return overallPerf;
	}

	public void setOverallPerf(String overallPerf) {
		this.overallPerf = overallPerf;
	}

	public String getOverallComment() {
		return overallComment;
	}

	public void setOverallComment(String overallComment) {
		this.overallComment = overallComment;
	}

	public String getPerfBonusAo() {
		return perfBonusAo;
	}

	public void setPerfBonusAo(String perfBonusAo) {
		this.perfBonusAo = perfBonusAo;
	}
	/* edited on 20170228 
	public String getSalaryADJAo() {
		return salaryADJAo;
		return salaryAdjAo;
	}

	public void setSalaryADJAo(String salaryADJAo) {
		this.salaryADJAo = salaryADJAo;
	}
	*/
	public String getSalaryAdjAo() {
		return salaryAdjAo;
	}

	public void setSalaryAdjAo(String salaryAdjAo) {
		this.salaryAdjAo = salaryAdjAo;
	}
	
	public String getAppInterview() {
		return appInterview;
	}

	public void setAppInterview(String appInterview) {
		this.appInterview = appInterview;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getAoName() {
		return aoName;
	}

	public void setAoName(String aoName) {
		this.aoName = aoName;
	}

	public String getAoRank() {
		return aoRank;
	}

	public void setAoRank(String aoRank) {
		this.aoRank = aoRank;
	}

	public String getPerfBonusCo() {
		return perfBonusCo;
	}

	public void setPerfBonusCo(String perfBonusCo) {
		this.perfBonusCo = perfBonusCo;
	}
	/*edited on 20170228
	public String getSalaryADJCo() {
		return salaryADJCo;
	}

	public void setSalaryADJCo(String salaryADJCo) {
		this.salaryADJCo = salaryADJCo;
	}
	*/
	public String getSalaryAdjCo() {
		return salaryAdjCo;
	}

	public void setSalaryAdjCo(String salaryAdjCo) {
		this.salaryAdjCo = salaryAdjCo;
	}
	
	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public Date getCoDate() {
		return coDate;
	}

	public void setCoDate(Date coDate) {
		this.coDate = coDate;
	}

	public String getCoRank() {
		return coRank;
	}

	public void setCoRank(String coRank) {
		this.coRank = coRank;
	}

	public String getIoName() {
		return ioName;
	}

	public void setIoName(String ioName) {
		this.ioName = ioName;
	}

	public String getIoRank() {
		return ioRank;
	}

	public void setIoRank(String ioRank) {
		this.ioRank = ioRank;
	}

	public Date getIoDate() {
		return ioDate;
	}

	public void setIoDate(Date ioDate) {
		this.ioDate = ioDate;
	}

	public String getAuditEnableGm() {
		return auditEnableGm;
	}

	public void setAuditEnableGm(String auditEnableGm) {
		this.auditEnableGm = auditEnableGm;
	}

	public String getAuditEnableAp() {
		return auditEnableAp;
	}

	public void setAuditEnableAp(String auditEnableAp) {
		this.auditEnableAp = auditEnableAp;
	}

	public String getAuditEnableAo() {
		return auditEnableAo;
	}

	public void setAuditEnableAo(String auditEnableAo) {
		this.auditEnableAo = auditEnableAo;
	}

	public String getAuditEnableCo() {
		return auditEnableCo;
	}

	public void setAuditEnableCo(String auditEnableCo) {
		this.auditEnableCo = auditEnableCo;
	}

	public String getAuditEnableRo() {
		return auditEnableRo;
	}

	public void setAuditEnableRo(String auditEnableRo) {
		this.auditEnableRo = auditEnableRo;
	}

	public String getAuditEnableEo() {
		return auditEnableEo;
	}

	public void setAuditEnableEo(String auditEnableEo) {
		this.auditEnableEo = auditEnableEo;
	}

	public String getAuditEnableIo() {
		return auditEnableIo;
	}

	public void setAuditEnableIo(String auditEnableIo) {
		this.auditEnableIo = auditEnableIo;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
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
