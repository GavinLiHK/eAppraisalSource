package com.hkha.ea.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HA_HR_EA_CONTRACT")
public class EaContract implements Serializable{
	//edited on 20170223 for overall comments content disappear
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "REPORT_ID", nullable = false,length = 22)
	private long reportId;
	
	@Column(name = "ACTING_APPOINT", length = 650)
	private String actingAppoint;
	
	@Column(name = "TRAINING_COURSE", length = 1950)
	private String trainingCourse;
	
	@Column(name = "COMMENDATION", length =200)
	private String commendation;
	
	@Column(name = "PERF_APP_NAME", length = 240)
	private String perfAppName;
	
	@Column(name = "PERF_APP_RANK", length =40)
	private String perfAppRank;
	
	@Column(name = "PERF_APP_DATE", length =10)
	private String perfAppDate;
	
	@Column(name = "PERF_AO_NAME", length =240)
	private String perfAoName;
	
	@Column(name = "PERF_AO_RANK", length =40)
	private String perfAoRank;
	
	@Column(name = "PERF_AO_DATE", length =10)
	private String perfAoDate;
	
	@Column(name = "OVERALL_RATING", length =1)
	private String overallRating;
	
	@Column(name = "OVERALL_COMMENT", length =4000)
	private String overallComment;
	
	@Column(name = "PERFORMANCE_BONUS_AO", length =1)
	private String performanceBonusAo;
	
	@Column(name = "SALARY_ADJUSTMENT_AO", length =1)
	private String salaryAdjustmentAo;
	
	@Column(name = "APPRAISE_POTENTIAL", length =1)
	private String appraisePotential;
	
	@Column(name = "COMP_AO_NAME", length =240)
	private String compAoName;
	
	@Column(name = "COMP_AO_RANK", length =40)
	private String compAoRank;
	
	@Column(name = "COMP_AO_DATE", length =10)
	private String compAoDate;
	
	@Column(name = "COMP_AO_TEL", length =30)
	private String compAoTel;
		
	@Column(name = "WORK_WELL", length =1050)
	private String workWell;
	
	@Column(name = "OVERALL_PERFORMANCE", length =3000)
	private String overallPerformance;
	
	@Column(name = "PERFORMANCE_BONUS_CO", length =1)
	private String performanceBonusCo;
	
	@Column(name = "SALARY_ADJUSTMENT_CO", length =1)
	private String salaryAdjustmentCo;
	
	@Column(name = "COUNTER_POTENTIAL", length =1)
	private String counterPotential;
	
	@Column(name = "ASSE_CO_NAME", length =240)
	private String asseCoName;
	
	@Column(name = "ASSE_CO_RANK", length =40)
	private String asseCoRank;
	
	@Column(name = "ASSE_CO_DATE", length =10)
	private String asseCoDate;
	
	@Column(name = "ASSE_CO_TEL", length =30)
	private String asseCoTel;
	
	@Column(name = "REVIEW_INTERVIEW", length =3600)
	private String reviewInterview;
	
	@Column(name = "INTV_APP_NAME", length =240)
	private String intvAppName;
	
	@Column(name = "INTV_APP_RANK", length =40)
	private String intvAppRank;
	
	@Column(name = "INTV_APP_DATE", length =10)
	private String intvAppDate;
	
	@Column(name = "INTV_IO_NAME", length =240)
	private String intvIoName;
	
	@Column(name = "INTV_IO_RANK", length =40)
	private String intvIoRank;
	
	@Column(name = "INTV_IO_Date", length =10)
	private String intvIoDate;
	
	@Column(name = "REVIEW_POTENTIAL", length =1)
	private String reviewPotential;
	
	@Column(name = "REVIEW_COMMENT", length =2000)
	private String reviewComment;
	
	@Column(name = "PERFORMANCE_BONUS_RO", length =1)
	private String performanceBonusRo;
	
	@Column(name = "SALARY_ADJUSTMENT_RO", length =1)
	private String salaryAdjustmentRo;
	
	@Column(name = "REVI_RO_NAME", length =240)
	private String reviRoName;
	
	@Column(name = "REVI_RO_RANK", length =40)
	private String reviRoRank;
	
	@Column(name = "REVI_RO_DATE", length =10)
	private String reviRoDate;
	
	@Column(name = "AGREEMENT", length =1)
	private String agreement;
	
	@Column(name = "AGREE_PREF_BONUS", length =1)
	private String argeePerfBonus;
	
	@Column(name = "AGREE_SAL_ADJUSTMENT", length =1)
	private String agreeSalAdjuestment;
	
	@Column(name = "DISAGREE_REASON", length =300)
	private String disagreeReason;
	
	@Column(name = "READ_APPRAISAL", length =1)
	private String readAppraisal;
	
	@Column(name = "PERFORMANCE_BONUS", length =1)
	private String performanceBonus;
	
	@Column(name = "SALARY_ADJUSTMENT", length =1)
	private String salaryAdjustment;
	
	@Column(name = "AGREE_PERF_BONUS", length =1)
	private String agreePerfBonus;
	
	@Column(name = "OVERALL_COMMENT2", length =3000)
	private String overallComment2;
	
	@Column(name = "SPECIAL_FACTORS", length =1000)
	private String specialFactors;
	
	@Column(name = "WORK_WELL_EO", length =700)
	private String workWellEo;
	
	@Column(name = "OVERALL_PERF_EO", length =3000)
	private String overallPerfEo;
	
	@Column(name = "PERFORMANCE_BONUS_EO", length =1)
	private String performanceBonusEo;
	
	@Column(name = "SALARY_ADJUSTMENT_EO", length =1)
	private String salaryAdjustmentEo;
	
	@Column(name = "ENDORSING_POTENTIAL", length =1)
	private String endorsingPotential;
	
	@Column(name = "ASSE_EO_NAME", length =240)
	private String asseEoName;
	
	@Column(name = "ASSE_EO_RANK", length =40)
	private String asseEoRank;
	
	@Column(name = "ASSE_EO_DATE", length =10)
	private String asseEoDate;
	
	@Column(name = "ASSE_EO_TEL", length =20)
	private String asseEoTel;
	
	@Column(name = "AUDIT_ENABLE_GM", length =1)
	private String auditEnableGm;
	
	@Column(name = "AUDIT_ENABLE_AP", length =1)
	private String auditEnableAp;
	
	@Column(name = "AUDIT_ENABLE_AO", length =1)
	private String auditEnableAo;
	
	@Column(name = "AUDIT_ENABLE_CO", length =1)
	private String auditEnableCo;
	
	@Column(name = "AUDIT_ENABLE_RO", length =1)
	private String auditEnableRo;
	
	@Column(name = "AUDIT_ENABLE_EO", length =1)
	private String auditEnableEo;
	
	@Column(name = "AUDIT_ENABLE_IO", length =1)
	private String auditEnableIo;	
	
	@Column(name = "XXOVERALL_COMMENT", length =8000)
	//private String xxOverallComment;
	private String xxoverallComment;
	
	@Column(name = "XXOVERALL_COMMENT2", length =4000)
	//private String xxOverallComment2;
	private String xxoverallComment2;
	
	@Column(name = "XXREVIEW_INTERVIEW", length =4000)
	//private String xxReviewInterview;
	private String xxreviewInterview;
	
	@Column(name = "CREATED_BY", length = 100)
	private String createdBy;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATED_BY", length = 100)
	private String lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getActingAppoint() {
		return actingAppoint;
	}

	public void setActingAppoint(String actingAppoint) {
		this.actingAppoint = actingAppoint;
	}

	public String getTrainingCourse() {
		return trainingCourse;
	}

	public void setTrainingCourse(String trainingCourse) {
		this.trainingCourse = trainingCourse;
	}

	public String getCommendation() {
		return commendation;
	}

	public void setCommendation(String commendation) {
		this.commendation = commendation;
	}

	public String getPerfAppName() {
		return perfAppName;
	}

	public void setPerfAppName(String perfAppName) {
		this.perfAppName = perfAppName;
	}

	public String getPerfAppRank() {
		return perfAppRank;
	}

	public void setPerfAppRank(String perfAppRank) {
		this.perfAppRank = perfAppRank;
	}

	public String getPerfAppDate() {
		return perfAppDate;
	}

	public void setPerfAppDate(String perfAppDate) {
		this.perfAppDate = perfAppDate;
	}

	public String getPerfAoName() {
		return perfAoName;
	}

	public void setPrefAoName(String perfAoName) {
		this.perfAoName = perfAoName;
	}

	public String getPerfAoRank() {
		return perfAoRank;
	}

	public void setPerfAoRank(String perfAoRank) {
		this.perfAoRank = perfAoRank;
	}

	public String getPrefAoDate() {
		return perfAoDate;
	}

	public void setPerfAoDate(String perfAoDate) {
		this.perfAoDate = perfAoDate;
	}

	public String getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(String overallRating) {
		this.overallRating = overallRating;
	}

	public String getOverallComment() {
		return overallComment;
	}

	public void setOverallComment(String overallComment) {
		this.overallComment = overallComment;
	}

	public String getPerformanceBonusAo() {
		return performanceBonusAo;
	}

	public void setPerformanceBonusAo(String performanceBonusAo) {
		this.performanceBonusAo = performanceBonusAo;
	}

	public String getSalaryAdjustmentAo() {
		return salaryAdjustmentAo;
	}

	public void setSalaryAdjustmentAo(String salaryAdjustmentAo) {
		this.salaryAdjustmentAo = salaryAdjustmentAo;
	}

	public String getAppraisePotential() {
		return appraisePotential;
	}

	public void setAppraisePotential(String appraisePotential) {
		this.appraisePotential = appraisePotential;
	}

	public String getCompAoName() {
		return compAoName;
	}

	public void setCompAoName(String compAoName) {
		this.compAoName = compAoName;
	}

	public String getCompAoRank() {
		return compAoRank;
	}

	public void setCompAoRank(String compAoRank) {
		this.compAoRank = compAoRank;
	}

	public String getCompAoDate() {
		return compAoDate;
	}

	public void setCompAoDate(String compAoDate) {
		this.compAoDate = compAoDate;
	}

	public String getCompAoTel() {
		return compAoTel;
	}

	public void setCompAoTel(String compAoTel) {
		this.compAoTel = compAoTel;
	}

	public String getWorkWell() {
		return workWell;
	}

	public void setWorkWell(String workWell) {
		this.workWell = workWell;
	}

	public String getOverallPerformance() {
		return overallPerformance;
	}

	public void setOverallPerformance(String overallPerformance) {
		this.overallPerformance = overallPerformance;
	}

	public String getPerformanceBonusCo() {
		return performanceBonusCo;
	}

	public void setPerformanceBonusCo(String performanceBonusCo) {
		this.performanceBonusCo = performanceBonusCo;
	}

	public String getSalaryAdjustmentCo() {
		return salaryAdjustmentCo;
	}

	public void setSalaryAdjustmentCo(String salaryAdjustmentCo) {
		this.salaryAdjustmentCo = salaryAdjustmentCo;
	}

	public String getCounterPotential() {
		return counterPotential;
	}

	public void setCounterPotential(String counterPotential) {
		this.counterPotential = counterPotential;
	}

	public String getAsseCoName() {
		return asseCoName;
	}

	public void setAsseCoName(String asseCoName) {
		this.asseCoName = asseCoName;
	}

	public String getAsseCoRank() {
		return asseCoRank;
	}

	public void setAsseCoRank(String asseCoRank) {
		this.asseCoRank = asseCoRank;
	}

	public String getAsseCoDate() {
		return asseCoDate;
	}

	public void setAsseCoDate(String asseCoDate) {
		this.asseCoDate = asseCoDate;
	}

	public String getAsseCoTel() {
		return asseCoTel;
	}

	public void setAsseCoTel(String asseCoTel) {
		this.asseCoTel = asseCoTel;
	}

	public String getReviewInterview() {
		return reviewInterview;
	}

	public void setReviewInterview(String reviewInterview) {
		this.reviewInterview = reviewInterview;
	}

	public String getIntvAppName() {
		return intvAppName;
	}

	public void setIntvAppName(String intvAppName) {
		this.intvAppName = intvAppName;
	}

	public String getIntvAppRank() {
		return intvAppRank;
	}

	public void setIntvAppRank(String intvAppRank) {
		this.intvAppRank = intvAppRank;
	}

	public String getIntvAppDate() {
		return intvAppDate;
	}

	public void setIntvAppDate(String intvAppDate) {
		this.intvAppDate = intvAppDate;
	}

	public String getIntvIoName() {
		return intvIoName;
	}

	public void setIntvIoName(String intvIoName) {
		this.intvIoName = intvIoName;
	}

	public String getIntvIoRank() {
		return intvIoRank;
	}

	public void setIntvIoRank(String intvIoRank) {
		this.intvIoRank = intvIoRank;
	}

	public String getIntvIoDate() {
		return intvIoDate;
	}

	public void setIntvIoDate(String intvIoDate) {
		this.intvIoDate = intvIoDate;
	}

	public String getReviewPotential() {
		return reviewPotential;
	}

	public void setReviewPotential(String reviewPotential) {
		this.reviewPotential = reviewPotential;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public String getPerformanceBonusRo() {
		return performanceBonusRo;
	}

	public void setPerformanceBonusRo(String performanceBonusRo) {
		this.performanceBonusRo = performanceBonusRo;
	}

	public String getSalaryAdjustmentRo() {
		return salaryAdjustmentRo;
	}

	public void setSalaryAdjustmentRo(String salaryAdjustmentRo) {
		this.salaryAdjustmentRo = salaryAdjustmentRo;
	}

	public String getPerfAoDate() {
		return perfAoDate;
	}

	public void setPerfAoName(String perfAoName) {
		this.perfAoName = perfAoName;
	}

	public String getReviRoName() {
		return reviRoName;
	}

	public void setReviRoName(String reviRoName) {
		this.reviRoName = reviRoName;
	}

	public String getReviRoRank() {
		return reviRoRank;
	}

	public void setReviRoRank(String reviRoRank) {
		this.reviRoRank = reviRoRank;
	}

	public String getReviRoDate() {
		return reviRoDate;
	}

	public void setReviRoDate(String reviRoDate) {
		this.reviRoDate = reviRoDate;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public String getArgeePerfBonus() {
		return argeePerfBonus;
	}

	public void setArgeePerfBonus(String argeePerfBonus) {
		this.argeePerfBonus = argeePerfBonus;
	}

	public String getAgreeSalAdjuestment() {
		return agreeSalAdjuestment;
	}

	public void setAgreeSalAdjuestment(String agreeSalAdjuestment) {
		this.agreeSalAdjuestment = agreeSalAdjuestment;
	}

	public String getDisagreeReason() {
		return disagreeReason;
	}

	public void setDisagreeReason(String disagreeReason) {
		this.disagreeReason = disagreeReason;
	}

	public String getReadAppraisal() {
		return readAppraisal;
	}

	public void setReadAppraisal(String readAppraisal) {
		this.readAppraisal = readAppraisal;
	}

	public String getPerformanceBonus() {
		return performanceBonus;
	}

	public void setPerformanceBonus(String performanceBonus) {
		this.performanceBonus = performanceBonus;
	}

	public String getSalaryAdjustment() {
		return salaryAdjustment;
	}

	public void setSalaryAdjustment(String salaryAdjustment) {
		this.salaryAdjustment = salaryAdjustment;
	}

	public String getAgreePerfBonus() {
		return agreePerfBonus;
	}

	public void setAgreePerfBonus(String agreePerfBonus) {
		this.agreePerfBonus = agreePerfBonus;
	}

	public String getOverallComment2() {
		return overallComment2;
	}

	public void setOverallComment2(String overallComment2) {
		this.overallComment2 = overallComment2;
	}

	public String getSpecialFactors() {
		return specialFactors;
	}

	public void setSpecialFactors(String specialFactors) {
		this.specialFactors = specialFactors;
	}

	public String getWorkWellEo() {
		return workWellEo;
	}

	public void setWorkWellEo(String workWellEo) {
		this.workWellEo = workWellEo;
	}

	public String getOverallPerfEo() {
		return overallPerfEo;
	}

	public void setOverallPerfEo(String overallPerfEo) {
		this.overallPerfEo = overallPerfEo;
	}

	public String getPerformanceBonusEo() {
		return performanceBonusEo;
	}

	public void setPerformanceBonusEo(String performanceBonusEo) {
		this.performanceBonusEo = performanceBonusEo;
	}

	public String getSalaryAdjustmentEo() {
		return salaryAdjustmentEo;
	}

	public void setSalaryAdjustmentEo(String salaryAdjustmentEo) {
		this.salaryAdjustmentEo = salaryAdjustmentEo;
	}

	public String getEndorsingPotential() {
		return endorsingPotential;
	}

	public void setEndorsingPotential(String endorsingPotential) {
		this.endorsingPotential = endorsingPotential;
	}

	public String getAsseEoName() {
		return asseEoName;
	}

	public void setAsseEoName(String asseEoName) {
		this.asseEoName = asseEoName;
	}

	public String getAsseEoRank() {
		return asseEoRank;
	}

	public void setAsseEoRank(String asseEoRank) {
		this.asseEoRank = asseEoRank;
	}

	public String getAsseEoDate() {
		return asseEoDate;
	}

	public void setAsseEoDate(String asseEoDate) {
		this.asseEoDate = asseEoDate;
	}

	public String getAsseEoTel() {
		return asseEoTel;
	}

	public void setAsseEoTel(String asseEoTel) {
		this.asseEoTel = asseEoTel;
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
/*
	public String getXxOverallComment() {
		return xxOverallComment;
	}

	public void setXxOverallComment(String xxOverallComment) {
		this.xxOverallComment = xxOverallComment;
	}

	public String getXxOverallComment2() {
		return xxOverallComment2;
	}

	public void setXxOverallComment2(String xxOverallComment2) {
		this.xxOverallComment2 = xxOverallComment2;
	}

	public String getXxReviewInterview() {
		return xxReviewInterview;
	}

	public void setXxReviewInterview(String xxReviewInterview) {
		this.xxReviewInterview = xxReviewInterview;
	}
*/
	public String getXxoverallComment() {
		return xxoverallComment;
	}

	public void setXxoverallComment(String xxoverallComment) {
		this.xxoverallComment = xxoverallComment;
	}

	public String getXxoverallComment2() {
		return xxoverallComment2;
	}

	public void setXxoverallComment2(String xxoverallComment2) {
		this.xxoverallComment2 = xxoverallComment2;
	}

	public String getXxreviewInterview() {
		return xxreviewInterview;
	}

	public void setXxreviewInterview(String xxreviewInterview) {
		this.xxreviewInterview = xxreviewInterview;
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
	
}
