package com.hkha.ea.dto.assess;

import java.util.List;

import com.hkha.ea.dto.common.ReportUserRole;

public class AssessAppraisalDTO {
	
	private long reportId;
	
	private String currentPage;	
	
	private AppraiseeInfo appraiseeInfo;
	
	private PersonalInfo personalInfo;
	
	private List<PartAB1Info> partAInfoList;
	
	private SignatureInfo signatureInfoA;
	
	private List<PartAB1Info> partB1InfoList;
	
	private PartB2Info partB2Info;
	
	private PartB3Info partB3Info;
	
	private PartB4Info partB4Info;
	
	private SignatureInfo signatureInfoB4;
	
	private PartB5B7B8Info partB5Info;
	
	private SignatureInfo signatureInfoB5;
	
	private PartB6Info partB6Info;
	
	private SignatureInfo signatureInfoB6;
	
	private PartB5B7B8Info partB7Info;
	
	private SignatureInfo signatureInfoB7;
	
	private PartB5B7B8Info partB8Info;
	
	private SignatureInfo signatureInfoB8;
	
	private ReportUserRole reportUserRole;
	
	private String actionFlag;

	private PrintSubmitInfo printSubmitInfo;
	
	private int pages;
	
	private String backToListType;


	public AppraiseeInfo getAppraiseeInfo() {
		return appraiseeInfo;
	}

	public void setAppraiseeInfo(AppraiseeInfo appraiseeInfo) {
		this.appraiseeInfo = appraiseeInfo;
	}

	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}



	public SignatureInfo getSignatureInfoA() {
		return signatureInfoA;
	}

	public void setSignatureInfoA(SignatureInfo signatureInfoA) {
		this.signatureInfoA = signatureInfoA;
	}

	public List<PartAB1Info> getPartAInfoList() {
		return partAInfoList;
	}

	public void setPartAInfoList(List<PartAB1Info> partAInfoList) {
		this.partAInfoList = partAInfoList;
	}

	public List<PartAB1Info> getPartB1InfoList() {
		return partB1InfoList;
	}

	public void setPartB1InfoList(List<PartAB1Info> partB1InfoList) {
		this.partB1InfoList = partB1InfoList;
	}

	public PartB2Info getPartB2Info() {
		return partB2Info;
	}

	public void setPartB2Info(PartB2Info partB2Info) {
		this.partB2Info = partB2Info;
	}

	public PartB3Info getPartB3Info() {
		return partB3Info;
	}

	public void setPartB3Info(PartB3Info partB3Info) {
		this.partB3Info = partB3Info;
	}

	public PartB4Info getPartB4Info() {
		return partB4Info;
	}

	public void setPartB4Info(PartB4Info partB4Info) {
		this.partB4Info = partB4Info;
	}

	public SignatureInfo getSignatureInfoB4() {
		return signatureInfoB4;
	}

	public void setSignatureInfoB4(SignatureInfo signatureInfoB4) {
		this.signatureInfoB4 = signatureInfoB4;
	}


	public PartB5B7B8Info getPartB5Info() {
		return partB5Info;
	}

	public void setPartB5Info(PartB5B7B8Info partB5Info) {
		this.partB5Info = partB5Info;
	}

	public SignatureInfo getSignatureInfoB5() {
		return signatureInfoB5;
	}

	public void setSignatureInfoB5(SignatureInfo signatureInfoB5) {
		this.signatureInfoB5 = signatureInfoB5;
	}

	public PartB6Info getPartB6Info() {
		return partB6Info;
	}

	public void setPartB6Info(PartB6Info partB6Info) {
		this.partB6Info = partB6Info;
	}

	public SignatureInfo getSignatureInfoB6() {
		return signatureInfoB6;
	}

	public void setSignatureInfoB6(SignatureInfo signatureInfoB6) {
		this.signatureInfoB6 = signatureInfoB6;
	}

	public PartB5B7B8Info getPartB7Info() {
		return partB7Info;
	}

	public void setPartB7Info(PartB5B7B8Info partB7Info) {
		this.partB7Info = partB7Info;
	}

	public SignatureInfo getSignatureInfoB7() {
		return signatureInfoB7;
	}

	public void setSignatureInfoB7(SignatureInfo signatureInfoB7) {
		this.signatureInfoB7 = signatureInfoB7;
	}

	public PartB5B7B8Info getPartB8Info() {
		return partB8Info;
	}

	public void setPartB8Info(PartB5B7B8Info partB8Info) {
		this.partB8Info = partB8Info;
	}

	public SignatureInfo getSignatureInfoB8() {
		return signatureInfoB8;
	}

	public void setSignatureInfoB8(SignatureInfo signatureInfoB8) {
		this.signatureInfoB8 = signatureInfoB8;
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



	public PrintSubmitInfo getPrintSubmitInfo() {
		return printSubmitInfo;
	}

	public void setPrintSubmitInfo(PrintSubmitInfo printSubmitInfo) {
		this.printSubmitInfo = printSubmitInfo;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getBackToListType() {
		return backToListType;
	}

	public void setBackToListType(String backToListType) {
		this.backToListType = backToListType;
	}

}
