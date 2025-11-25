package com.hkha.ea.dto.assess;

import java.util.Date;
import java.util.List;

public class SearchAppraiseeDto {

	//search parameter
	private String year;
	private String rank;
	private String postUnit;
	private Long employeeNumber;
	private String employeeName;
	private String trackDate;
	private String reportGenerated;
	private String appraisalPeriodStart;
	private String appraisalPeriodEnd;
	private String wfTemplateId;
	
	//search result field
/*	private Date commenceDate;
	private Date endDate;*/
	private String engName;
	private String chiName;
	private Long computerNum;
	private String postTitle;
	private Long reportId;
	private String batchId;
	private String status;
	private Date dateToRank;
	
	//search batch
	private String batchName;
	private String name;
	private int totalAppraisees;
	private int noOfAppDispatched;
	private String subRank;
	private int numAssigned;
	private int numUnassigned;
	private int numAppraisee;
	private String assigned;
	
	//check page is EA016 or EA017
	private String functionNum;
	private String mode;//check mode is create or modify
	private String fromPage;//check page from assign officer or not
	
	//jmesa
	private String isSearch;
	private String selectedOptions;
	private String selectedBatchId;
	private String selectedRptId;
	private String selectedEmployee;
	private String selectedOptionsAll;
	private String batchNameJmesa;
	private String batchNameLink;//for appraisee list for batch link;
	private String yearJmesa;
	private String rankJmesa;
	private String postUnitJmesa;
	private String postTitleJmesa;
	private String employeeNumberJmesa;
	private String trackDateJmesa;
	private String reportGeneratedJmesa;
	private String appraisalPeriodStartJmesa;
	private String appraisalPeriodEndJmesa;
	private List<String> selOptionsAll;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getPostUnit() {
		return postUnit;
	}
	public void setPostUnit(String postUnit) {
		this.postUnit = postUnit;
	}
	public Long getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(Long employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getTrackDate() {
		return trackDate;
	}
	public void setTrackDate(String trackDate) {
		this.trackDate = trackDate;
	}
	public String getReportGenerated() {
		return reportGenerated;
	}
	public void setReportGenerated(String reportGenerated) {
		this.reportGenerated = reportGenerated;
	}
	public String getAppraisalPeriodStart() {
		return appraisalPeriodStart;
	}
	public void setAppraisalPeriodStart(String appraisalPeriodStart) {
		this.appraisalPeriodStart = appraisalPeriodStart;
	}
	public String getAppraisalPeriodEnd() {
		return appraisalPeriodEnd;
	}
	public void setAppraisalPeriodEnd(String appraisalPeriodEnd) {
		this.appraisalPeriodEnd = appraisalPeriodEnd;
	}
	public String getWfTemplateId() {
		return wfTemplateId;
	}
	public void setWfTemplateId(String wfTemplateId) {
		this.wfTemplateId = wfTemplateId;
	}
	/*public Date getCommenceDate() {
		return commenceDate;
	}
	public void setCommenceDate(Date commenceDate) {
		this.commenceDate = commenceDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}*/
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getChiName() {
		return chiName;
	}
	public void setChiName(String chiName) {
		this.chiName = chiName;
	}
	public Long getComputerNum() {
		return computerNum;
	}
	public void setComputerNum(Long computerNum) {
		this.computerNum = computerNum;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDateToRank() {
		return dateToRank;
	}
	public void setDateToRank(Date dateToRank) {
		this.dateToRank = dateToRank;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getSelectedOptions() {
		return selectedOptions;
	}
	public void setSelectedOptions(String selectedOptions) {
		this.selectedOptions = selectedOptions;
	}
	public String getSelectedOptionsAll() {
		return selectedOptionsAll;
	}
	public void setSelectedOptionsAll(String selectedOptionsAll) {
		this.selectedOptionsAll = selectedOptionsAll;
	}
	public String getYearJmesa() {
		return yearJmesa;
	}
	public void setYearJmesa(String yearJmesa) {
		this.yearJmesa = yearJmesa;
	}
	public String getIsSearch() {
		return isSearch;
	}
	public void setIsSearch(String isSearch) {
		this.isSearch = isSearch;
	}
	public String getRankJmesa() {
		return rankJmesa;
	}
	public void setRankJmesa(String rankJmesa) {
		this.rankJmesa = rankJmesa;
	}
	public String getPostUnitJmesa() {
		return postUnitJmesa;
	}
	public void setPostUnitJmesa(String postUnitJmesa) {
		this.postUnitJmesa = postUnitJmesa;
	}
	public String getEmployeeNumberJmesa() {
		return employeeNumberJmesa;
	}
	public void setEmployeeNumberJmesa(String employeeNumberJmesa) {
		this.employeeNumberJmesa = employeeNumberJmesa;
	}
	public String getTrackDateJmesa() {
		return trackDateJmesa;
	}
	public void setTrackDateJmesa(String trackDateJmesa) {
		this.trackDateJmesa = trackDateJmesa;
	}
	public String getReportGeneratedJmesa() {
		return reportGeneratedJmesa;
	}
	public void setReportGeneratedJmesa(String reportGeneratedJmesa) {
		this.reportGeneratedJmesa = reportGeneratedJmesa;
	}
	public String getAppraisalPeriodStartJmesa() {
		return appraisalPeriodStartJmesa;
	}
	public void setAppraisalPeriodStartJmesa(String appraisalPeriodStartJmesa) {
		this.appraisalPeriodStartJmesa = appraisalPeriodStartJmesa;
	}
	public String getAppraisalPeriodEndJmesa() {
		return appraisalPeriodEndJmesa;
	}
	public void setAppraisalPeriodEndJmesa(String appraisalPeriodEndJmesa) {
		this.appraisalPeriodEndJmesa = appraisalPeriodEndJmesa;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalAppraisees() {
		return totalAppraisees;
	}
	public void setTotalAppraisees(int totalAppraisees) {
		this.totalAppraisees = totalAppraisees;
	}
	public int getNoOfAppDispatched() {
		return noOfAppDispatched;
	}
	public void setNoOfAppDispatched(int noOfAppDispatched) {
		this.noOfAppDispatched = noOfAppDispatched;
	}
	public String getSubRank() {
		return subRank;
	}
	public void setSubRank(String subRank) {
		this.subRank = subRank;
	}
	public int getNumAssigned() {
		return numAssigned;
	}
	public void setNumAssigned(int numAssigned) {
		this.numAssigned = numAssigned;
	}
	public int getNumUnassigned() {
		return numUnassigned;
	}
	public void setNumUnassigned(int numUnassigned) {
		this.numUnassigned = numUnassigned;
	}
	public int getNumAppraisee() {
		return numAppraisee;
	}
	public void setNumAppraisee(int numAppraisee) {
		this.numAppraisee = numAppraisee;
	}
	public String getAssigned() {
		return assigned;
	}
	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}
	public String getFunctionNum() {
		return functionNum;
	}
	public void setFunctionNum(String functionNum) {
		this.functionNum = functionNum;
	}
	public String getBatchNameJmesa() {
		return batchNameJmesa;
	}
	public void setBatchNameJmesa(String batchNameJmesa) {
		this.batchNameJmesa = batchNameJmesa;
	}
	public String getPostTitleJmesa() {
		return postTitleJmesa;
	}
	public void setPostTitleJmesa(String postTitleJmesa) {
		this.postTitleJmesa = postTitleJmesa;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getBatchNameLink() {
		return batchNameLink;
	}
	public void setBatchNameLink(String batchNameLink) {
		this.batchNameLink = batchNameLink;
	}
	public String getFromPage() {
		return fromPage;
	}
	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}
	public String getSelectedBatchId() {
		return selectedBatchId;
	}
	public void setSelectedBatchId(String selectedBatchId) {
		this.selectedBatchId = selectedBatchId;
	}
	public String getSelectedRptId() {
		return selectedRptId;
	}
	public void setSelectedRptId(String selectedRptId) {
		this.selectedRptId = selectedRptId;
	}
	public String getSelectedEmployee() {
		return selectedEmployee;
	}
	public void setSelectedEmployee(String selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}
	public List<String> getSelOptionsAll() {
		return selOptionsAll;
	}
	public void setSelOptionsAll(List<String> selOptionsAll) {
		this.selOptionsAll = selOptionsAll;
	}
	
}
