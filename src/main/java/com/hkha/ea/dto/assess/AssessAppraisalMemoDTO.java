package com.hkha.ea.dto.assess;

import com.hkha.ea.dto.common.ReportUserRole;

public class AssessAppraisalMemoDTO {

	private Long reportId;
	
	private String currentRole;

	private String currentPage;
	
	private MemoInfo memoInfo;
	
	private MemoPart1Info memoPart1Info;
	
	//part2 Info
	private MemoPart2Part5Info memoPart2Info;
	
	private MemoPart3Part4Info memoPart3Info;
	
	private SignatureInfo signatureInfoMemoP3;
	
	private MemoPart3Part4Info memoPart4Info;
	
	private SignatureInfo signatureInfoMemoP4;
	
	private MemoPart2Part5Info memoPart5Info;
	
	private SignatureInfo signatureInfoMemoP5;
	
	private ReportUserRole reportUserRole;
	
	private String actionFlag;

	private PrintSubmitInfo memoPrintSubmitInfo;
	
	private int pages;
	
	private String backToListType;
	
	private String reportType;
	
	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public MemoInfo getMemoInfo() {
		return memoInfo;
	}

	public void setMemoInfo(MemoInfo memoInfo) {
		this.memoInfo = memoInfo;
	}

	public MemoPart1Info getMemoPart1Info() {
		return memoPart1Info;
	}

	public void setMemoPart1Info(MemoPart1Info memoPart1Info) {
		this.memoPart1Info = memoPart1Info;
	}

	public MemoPart2Part5Info getMemoPart2Info() {
		return memoPart2Info;
	}

	public void setMemoPart2Info(MemoPart2Part5Info memoPart2Info) {
		this.memoPart2Info = memoPart2Info;
	}

	public MemoPart3Part4Info getMemoPart3Info() {
		return memoPart3Info;
	}

	public void setMemoPart3Info(MemoPart3Part4Info memoPart3Info) {
		this.memoPart3Info = memoPart3Info;
	}

	public SignatureInfo getSignatureInfoMemoP3() {
		return signatureInfoMemoP3;
	}

	public void setSignatureInfoMemoP3(SignatureInfo signatureInfoMemoP3) {
		this.signatureInfoMemoP3 = signatureInfoMemoP3;
	}

	public MemoPart3Part4Info getMemoPart4Info() {
		return memoPart4Info;
	}

	public void setMemoPart4Info(MemoPart3Part4Info memoPart4Info) {
		this.memoPart4Info = memoPart4Info;
	}

	public SignatureInfo getSignatureInfoMemoP4() {
		return signatureInfoMemoP4;
	}

	public void setSignatureInfoMemoP4(SignatureInfo signatureInfoMemoP4) {
		this.signatureInfoMemoP4 = signatureInfoMemoP4;
	}

	public MemoPart2Part5Info getMemoPart5Info() {
		return memoPart5Info;
	}

	public void setMemoPart5Info(MemoPart2Part5Info memoPart5Info) {
		this.memoPart5Info = memoPart5Info;
	}

	public SignatureInfo getSignatureInfoMemoP5() {
		return signatureInfoMemoP5;
	}

	public void setSignatureInfoMemoP5(SignatureInfo signatureInfoMemoP5) {
		this.signatureInfoMemoP5 = signatureInfoMemoP5;
	}

	public ReportUserRole getReportUserRole() {
		return reportUserRole;
	}

	public void setReportUserRole(ReportUserRole reportUserRole) {
		this.reportUserRole = reportUserRole;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public PrintSubmitInfo getMemoPrintSubmitInfo() {
		return memoPrintSubmitInfo;
	}

	public void setMemoPrintSubmitInfo(PrintSubmitInfo memoPrintSubmitInfo) {
		this.memoPrintSubmitInfo = memoPrintSubmitInfo;
	}

	public String getBackToListType() {
		return backToListType;
	}

	public void setBackToListType(String backToListType) {
		this.backToListType = backToListType;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
}
