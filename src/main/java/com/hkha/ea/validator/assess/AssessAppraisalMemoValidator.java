package com.hkha.ea.validator.assess;

import java.util.Date;
import java.util.List;
import java.util.Map;
//20220630 Prevent empty next officer ID
import java.util.Objects;
//20220630 End - Prevent empty next officer ID
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.Util;
//20220630 Prevent empty next officer ID
import com.hkha.ea.dao.common.EmployeeEnquiryDAO;
//20220630 End - Prevent empty next officer ID
import com.hkha.ea.dao.assess.AppraisalAssessmentDAO;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
//20220630 Prevent empty next officer ID
import com.hkha.ea.dao.security.UserEnquiryDAO;
//20220630 End - Prevent empty next officer ID
import com.hkha.ea.domain.EaMemoDetails;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.dto.assess.AppraiseeInfo;
import com.hkha.ea.dto.assess.AssessAppraisalDTO;
import com.hkha.ea.dto.assess.AssessAppraisalMemoDTO;
import com.hkha.ea.dto.assess.MemoInfo;
import com.hkha.ea.dto.assess.MemoPart1Info;
import com.hkha.ea.dto.assess.MemoPart2Part5Info;
import com.hkha.ea.dto.assess.MemoPart3Part4Info;
import com.hkha.ea.dto.assess.PartAB1Info;
import com.hkha.ea.dto.assess.PartB2Info;
import com.hkha.ea.dto.assess.PartB3Info;
import com.hkha.ea.dto.assess.PartB4Info;
import com.hkha.ea.dto.assess.PartB5B7B8Info;
import com.hkha.ea.dto.assess.PartB6Info;
import com.hkha.ea.dto.assess.PersonalInfo;
import com.hkha.ea.dto.assess.PrintSubmitInfo;
import com.hkha.ea.dto.assess.SignatureInfo;
import com.hkha.ea.dto.assess.UserTableColumn;
//20220630 Prevent empty next officer ID
import com.hkha.ea.dto.common.EaLoginDTO;
import com.hkha.ea.dto.common.EmployeeEnquiryDTO;
//20220630 End - Prevent empty next officer ID
import com.hkha.ea.dto.common.ReportUserRole;
import com.hkha.ea.helper.EaValidatorUtil;

@Component( "assessAppraisalMemoValidator" )
public class AssessAppraisalMemoValidator  implements Validator{

	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AssessAppraisalMemoValidator.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private AppraisalAssessmentDAO appraisalAssessmentDAO;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	//added on 20220609 incorrect employee number issue
	@Autowired
	private EmployeeEnquiryDAO employeeEnquiryDAO;
	
	@Autowired
	private UserEnquiryDAO userEnquiryDAO;
	
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private final static String el="(((0[1-9]|[12][0-9]|3[01])/((0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)/(0[469]|11))|(0[1-9]|[1][0-9]|2[0-8])/(02))/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}))|(29/02/(([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00)))";
	
	public void validateAssessAppraisalMemoMandatory(AssessAppraisalMemoDTO dto,Errors errors){
		
		Map<String,String> pagesMap=appraisalAssessmentDAO.getAssessAppraisalMemoPageMapsByRole(dto.getReportUserRole().getCurrentUserRoleStatus());
		String currentPage=pagesMap.get(dto.getCurrentPage());
		//validate memoInfo
		errors=validateMemoInfo(dto.getMemoInfo(),errors);
		
		
		if("P1".equalsIgnoreCase(currentPage)){
			errors=validateMemoPart1Info(dto.getMemoPart1Info(),errors);
			
		}else if("P2".equalsIgnoreCase(currentPage)){
			if(StringUtil.isEmptyString(dto.getMemoPart2Info().getDutyDesc())){
				errors.rejectValue("memoPart2Info.dutyDesc", "error.message.empty.content", new String[]{"Brief Description of Duties"}, "Brief Description of Duties is required.");
			}
			
		}else if("P3".equalsIgnoreCase(currentPage)){
			errors=validateMemoPart3Info(dto.getMemoPart3Info(),errors);
			errors=validateMemoSignatureInfoP3(dto.getSignatureInfoMemoP3(),errors);
			
		}else if("P4".equalsIgnoreCase(currentPage)){
			errors=validateMemoSignatureInfoP4(dto.getSignatureInfoMemoP4(),errors);
			if("Y".equalsIgnoreCase(dto.getMemoPart4Info().getBonusDimmed())){
				if(StringUtil.isEmptyString(dto.getMemoPart4Info().getPerfBonus())){
					errors.rejectValue("memoPart4Info.perfBonus", "error.message.empty.content", new String[]{"Merit Payment"}, "Merit Payment is required.");
				}
			}
			if("Y".equalsIgnoreCase(dto.getMemoPart4Info().getSalaryDimmed())){
				if(StringUtil.isEmptyString(dto.getMemoPart4Info().getSalaryAdj())){
					errors.rejectValue("memoPart4Info.salaryAdj", "error.message.empty.content", new String[]{"Annual Salary Adjustment"}, "Annual Salary Adjustment is required.");
				}
			}
			
		}else if("P5".equalsIgnoreCase(currentPage)){
			errors=validateMemoSignatureInfoP5(dto.getSignatureInfoMemoP5(),errors);
			if(StringUtil.isEmptyString(dto.getMemoPart5Info().getAppInterview())){
				errors.rejectValue("memoPart5Info.appInterview", "error.message.empty.content", new String[]{"Record of Appraisal Interview"}, "Record of Appraisal Interview is required.");
			}
		}else if("PS".equalsIgnoreCase(currentPage)){
			if(StringUtil.isEmptyString(dto.getMemoPrintSubmitInfo().getRouting())){
				errors.rejectValue("memoPrintSubmitInfo.routing", "error.message.empty.content", new String[]{"Routing"}, "Routing is required.");
			}
		}
	}
	
	private Errors validateMemoInfo(MemoInfo mi,Errors errors){		
		Pattern p = Pattern.compile(el); 
		//From part
		if(StringUtil.isEmptyString(mi.getMemoFrom())){
			errors.rejectValue("memoInfo.memoFrom", "error.message.empty.content", new String[]{"Memo Information From"}, "Memo Information From is required.");
		}
		if(StringUtil.isEmptyString(mi.getTelNo())){
			errors.rejectValue("memoInfo.telNo", "error.message.empty.content", new String[]{"Memo Information TelNo"}, "Memo Information TelNo is required.");
		}
		if(StringUtil.isEmptyString(mi.getMemoFromDate())){
			errors.rejectValue("memoInfo.memoFromDate", "error.message.empty.content", new String[]{"Memo Information From Date"}, "Memo Information From Date is required.");
		}else{
			Matcher m = p.matcher(mi.getMemoFromDate());
			if(!m.matches()){
				errors.rejectValue("memoInfo.memoFromDate", "error.message.empty.content.date.format", new String[]{"Memo Information From Date "}, "format must be 'dd/MM/yyyy'");
			}
		}
		
		//To part
		
		if(StringUtil.isEmptyString(mi.getMemoTo())){
			errors.rejectValue("memoInfo.memoTo", "error.message.empty.content", new String[]{"Memo Information To"}, "Memo Information To is required.");
		}
		
		
		return errors;
	}
	
	private Errors validateMemoPart1Info(MemoPart1Info mp1,Errors errors){
		Pattern p = Pattern.compile(el); 
		Date fromDate=null ;
		Date toDate = null;
		if(StringUtil.isEmptyString(mp1.getPresentPost())){
			errors.rejectValue("memoPart1Info.presentPost", "error.message.empty.content", new String[]{"Post"}, "Post is required.");
		}
		
		if(StringUtil.isEmptyString(mp1.getDivisionSection())){
			errors.rejectValue("memoPart1Info.divisionSection", "error.message.empty.content", new String[]{"Division/Section"}, "Division/Section is required.");
		}
		
		if(StringUtil.isEmptyString(mp1.getAppraisalPeriodStart())){
			errors.rejectValue("memoInfo.appraisalPeriodStart", "error.message.empty.content", new String[]{"Appraisal Period Start Date"}, "Appraisal Period Start Date is required.");
		}else{
			Matcher m = p.matcher(mp1.getAppraisalPeriodStart());
			if(!m.matches()){
				errors.rejectValue("memoInfo.appraisalPeriodStart", "error.message.empty.content.date.format", new String[]{"Appraisal Period Start Date "}, "format must be 'dd/MM/yyyy'");
			}else{
				fromDate=Util.string2Date(mp1.getAppraisalPeriodStart(),  Constants.DISPLAY_DATE_FORMAT);
			}
		}
		
		if(StringUtil.isEmptyString(mp1.getAppraisalPeriodEnd())){
			errors.rejectValue("memoInfo.appraisalPeriodEnd", "error.message.empty.content", new String[]{"Appraisal Period End Date"}, "Appraisal Period End Date is required.");
		}else{
			Matcher m = p.matcher(mp1.getAppraisalPeriodEnd());
			if(!m.matches()){
				errors.rejectValue("memoInfo.appraisalPeriodEnd", "error.message.empty.content.date.format", new String[]{"Appraisal Period End Date "}, "format must be 'dd/MM/yyyy'");
			}else{
				toDate=Util.string2Date(mp1.getAppraisalPeriodEnd(),  Constants.DISPLAY_DATE_FORMAT);
			}
		}
		
		if(null!=fromDate && null!=toDate){
			if(toDate.before(fromDate)){
				errors.rejectValue("memoInfo.appraisalPeriodEnd", "error.er0063", new String[]{"Appraisal Period End Date "}, "Appraisal Period End must equals or after Appraisal Period Start.");
			}
		}
		
		return errors;
	}
	
	private Errors validateMemoPart3Info(MemoPart3Part4Info mp3,Errors errors){
		Pattern p = Pattern.compile(el); 
		if(StringUtil.isEmptyString(mp3.getOverallPerf())){
			errors.rejectValue("memoPart3Info.overallPerf", "error.message.empty.content", new String[]{"Overall Rating"}, "Overall Rating is required.");
		}
		if(StringUtil.isEmptyString(mp3.getOverallComment())){
			errors.rejectValue("memoPart3Info.overallComment", "error.message.empty.content", new String[]{"Comments on Overall Performance"}, "Comments on Overall Performance is required.");
		}
		if("Y".equalsIgnoreCase(mp3.getBonusDimmed())){
			if(StringUtil.isEmptyString(mp3.getPerfBonus())){
				errors.rejectValue("memoPart3Info.perfBonus", "error.message.empty.content", new String[]{"Merit Payment"}, "Merit Payment is required.");
			}
		}
		if("Y".equalsIgnoreCase(mp3.getSalaryDimmed())){
			if(StringUtil.isEmptyString(mp3.getSalaryAdj())){
				errors.rejectValue("memoPart3Info.salaryAdj", "error.message.empty.content", new String[]{"Annual Salary Adjustment"}, "Annual Salary Adjustment is required.");
			}
		}		
		return errors;
	}
	
	private Errors validateMemoSignatureInfoP3(SignatureInfo sf,Errors errors){
		Pattern p = Pattern.compile(el); 
		if(StringUtil.isEmptyString(sf.getOfficerRank())){
			errors.rejectValue("signatureInfoMemoP3.officerRank", "error.message.empty.content", new String[]{"Signature Officer Rank"}, "Signature Officer Rank is required.");
		}
		if(StringUtil.isEmptyString(sf.getOfficerDate())){
			errors.rejectValue("signatureInfoMemoP3.officerDate", "error.message.empty.content", new String[]{"Signature Officer Date"}, "Signature Officer Date is required.");
		}else{
			Matcher m = p.matcher(sf.getOfficerDate());
			if(!m.matches()){
				errors.rejectValue("signatureInfoMemoP3.officerDate", "error.message.empty.content.date.format", new String[]{"Signature Officer Date"}, "format must be 'dd/MM/yyyy'");
			}
		}
		
		
		return errors;
	}
	
	private Errors validateMemoSignatureInfoP4(SignatureInfo sf,Errors errors){
		Pattern p = Pattern.compile(el); 
		if(StringUtil.isEmptyString(sf.getOfficerRank())){
			errors.rejectValue("signatureInfoMemoP4.officerRank", "error.message.empty.content", new String[]{"Part IV - Signature Info - Countersigning Officer Rank/Post"}, "Part IV - Signature Info - Countersigning Officer Rank/Post is required.");
		}
		if(StringUtil.isEmptyString(sf.getOfficerDate())){
			errors.rejectValue("signatureInfoMemoP4.officerDate", "error.message.empty.content", new String[]{"Part IV - Signature Info - Countersigning Officer Appraisal Date"}, "Part IV - Signature Info - Countersigning Officer Appraisal Date is required.");
		}else{
			Matcher m = p.matcher(sf.getOfficerDate());
			if(!m.matches()){
				errors.rejectValue("signatureInfoMemoP4.officerDate", "error.message.empty.content.date.format", new String[]{"Part IV - Signature Info - Countersigning Officer Appraisal Date"}, "Part IV - Signature Info - Countersigning Officer Appraisal Date format must be 'dd/MM/yyyy'");
			}
		}
		
		
		return errors;
	}
	
	private Errors validateMemoSignatureInfoP5(SignatureInfo sf,Errors errors){
		Pattern p = Pattern.compile(el); 
		if(StringUtil.isEmptyString(sf.getOfficerRank())){
			errors.rejectValue("signatureInfoMemoP5.officerRank", "error.message.empty.content", new String[]{"Part V - Signature Info - Interviewing Officer Rank/Post"}, "Part V - Signature Info - Interviewing Officer Rank/Post is required.");
		}
		if(StringUtil.isEmptyString(sf.getOfficerDate())){
			errors.rejectValue("signatureInfoMemoP5.officerDate", "error.message.empty.content", new String[]{"Part V - Signature Info - Interviewing Officer Appraisal Date"}, "Part V - Signature Info - Interviewing Officer Appraisal Date is required.");
		}else{
			Matcher m = p.matcher(sf.getOfficerDate());
			if(!m.matches()){
				errors.rejectValue("signatureInfoMemoP5.officerDate", "error.message.empty.content.date.format", new String[]{"Part V - Signature Info - Interviewing Officer Appraisal Date"}, "Part V - Signature Info - Interviewing Officer Appraisal Date format must be 'dd/MM/yyyy'");
			}
		}
		
		
		return errors;
	}
	
	public void validateAssessAppraisalMemoSendMandatory(AssessAppraisalMemoDTO dto,Errors errors){
		if(StringUtil.isEmptyString(dto.getMemoPrintSubmitInfo().getRouting())){
			errors.rejectValue("memoPrintSubmitInfo.routing", "error.message.empty.content", new String[]{"Routing"}, "Routing is required.");
		}
		/*EaMemoDetails emd=appraiseeCommonSearchDAO.findEaMemoDetailsById(dto.getReportId());
		 * if(StringUtil.isEmptyString(emd.getDutyDesc())&&(!"AP".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRouting()))){
			errors.rejectValue("memoPrintSubmitInfo.routing", "error.er0067", new String[]{"Routing"}, "Print/Send - Routing - If 'Part II - Brief Description of Duties' is not finished yet, sending 'To appraisee'.");
		}*/
		if(StringUtil.isEmptyString(dto.getMemoPart2Info().getDutyDesc())&&(!"AP".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRouting()))){
			errors.rejectValue("memoPrintSubmitInfo.routing", "error.er0067", new String[]{"Routing"}, "Print/Send - Routing - If 'Part II - Brief Description of Duties' is not finished yet, sending 'To appraisee'.");
		}
		
	}
	
	public Errors validateInputFields(AssessAppraisalMemoDTO dto, Errors errors, boolean submitReport){
		return validateInputFields(dto,errors, submitReport, false);
	}
	public Errors validateInputFields(AssessAppraisalMemoDTO dto, Errors errors, boolean submitReport, boolean isValidateAll){
		ReportUserRole role = dto.getReportUserRole();
		String rptStatus = role.getCurrentReportStatus();
		boolean isI = rptStatus.equalsIgnoreCase(Constants.STATUS_INITIAL);
		//boolean isCL = rptStatus.equalsIgnoreCase(Constants.STATUS_CL);
		boolean isCD = (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_CD));
		boolean isAP = (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_AP));
		boolean isAO = (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_AO));
		boolean isIO = (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_IO));
		boolean isCO = (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_CO));
		boolean isEO = (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_EO));
		boolean isRO = (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_RO));
		boolean isGM = (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_GM));
		
//		log.info("role.getCurrentUserRoleStatus():" + role.getCurrentUserRoleStatus());
		
		//boolean isSUGM = role.isSuperUser() || role.isGradeManagement();
		boolean isSUGM = (role.getCurrentUserRoleStatus().equalsIgnoreCase("SU"))||isGM;
		
//		log.info(dto.getMemoPrintSubmitInfo().getRouting());
//		boolean toAP = (dto.getMemoPrintSubmitInfo().getRouting().equalsIgnoreCase("AP"));
		
		
		if (isAP){
			errors = validateAp(dto, errors, submitReport || isValidateAll);
		} else if (isAO){
			errors = validateAo(dto, errors, submitReport || isValidateAll);
		} else if (isCO){
			errors = validateCo(dto, errors, submitReport || isValidateAll);
		} else if (isIO){
			errors = validateIo(dto, errors, submitReport || isValidateAll);
		} 
		if (isSUGM){
			errors = validateSugm(dto, errors, submitReport || isValidateAll);
		}
		if (isGM || isSUGM){
			errors = validateGmSugm(dto, errors, submitReport || isValidateAll);
		}
		if((isGM|| isSUGM) && submitReport){
			errors = validateGmSugmSubRpt(dto,errors,submitReport || isValidateAll);
		}
		
		if (isI || isCD || isAO || isAP || isCO || isIO || isEO){
			// no isValidateAll on next officer field
			errors = validateICdAoApCoIoEo(dto, errors, submitReport);
		}
		
		//20220630 Prevent empty next officer ID
		if(!(isGM || isSUGM)) {
			errors = validateSizeLimitMemoReport(dto,errors);
			if (!dto.getMemoPrintSubmitInfo().getRouting().equalsIgnoreCase("AP") && !dto.getMemoPrintSubmitInfo().getRouting().equalsIgnoreCase("PO")) {
				errors = validateNextOfficerID(dto,errors);
			}
		}
		//20220630 End - Prevent empty next officer ID
		
		return errors;
	}
	
	//added on 20170403 size limit of text area in memo report
	private Errors validateSizeLimitMemoReport(AssessAppraisalMemoDTO dto, Errors errors){
		List<UserTableColumn> utc = appraiseeCommonSearchDAO.searchColumnSize("M");
		MemoPart2Part5Info mp2 = dto.getMemoPart2Info();
		MemoPart3Part4Info mp3 = dto.getMemoPart3Info();
		MemoPart2Part5Info mp5 = dto.getMemoPart5Info();
		try{
			for(int i=0;i<utc.size();i++){
				if(utc.get(i).getColumnName().equalsIgnoreCase("DUTY_DESC")){
					if(!StringUtil.isEmptyString(mp2.getDutyDesc())){
						if(mp2.getDutyDesc().getBytes("UTF-8").length > utc.get(i).getDataLength()){
							errors.rejectValue("", "error.message.length.toolong", new String[]{"Part II - Brief Description of Duties "}, "Part II - Brief Description of Duties " + " is too long.");
						}
					}
				}
				if(utc.get(i).getColumnName().equalsIgnoreCase("OVERALL_COMMENT")){
					if(!StringUtil.isEmptyString(mp3.getOverallComment())){
						if(mp3.getOverallComment().getBytes("UTF-8").length > utc.get(i).getDataLength()){
							errors.rejectValue("", "error.message.length.toolong", new String[]{"Part III - Comments on Overall Performance "}, "Part III - Comments on Overall Performance " + " is too long.");
						}
					}
				}
				if(utc.get(i).getColumnName().equalsIgnoreCase("APP_INTERVIEW")){
					if(!StringUtil.isEmptyString(mp5.getAppInterview())){
						if(mp5.getAppInterview().getBytes("UTF-8").length > utc.get(i).getDataLength()){
							errors.rejectValue("", "error.message.length.toolong", new String[]{"Part V - Record of Appraisal Interview "}, "Part V - Record of Appraisal Interview " + " is too long.");
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.severe(ex.toString());
		}
		
		return errors;
	}
	//end added on 20170405 size limit of text area in memo report
	
	//added on 20220609 check incorrect employee number input
	private Errors validateNextOfficerID(AssessAppraisalMemoDTO dto, Errors errors){
		String empNum = dto.getMemoPrintSubmitInfo().getNextOfficerId().replace(" ", "").trim();
		log.info("empNumber:"+">" + empNum+"<");
		List<EmployeeEnquiryDTO> listEmp= employeeEnquiryDAO.searchByEmployee(empNum);
		//edited on 20180322 fix bug (GM)
		if (empNum != null) {
			//20220630
			EaLoginDTO loginEmployeeDTO = userEnquiryDAO.searchByLoginID(empNum);
			if (listEmp.size() == 0 && !empNum.equalsIgnoreCase("") && loginEmployeeDTO == null) {
				errors.rejectValue("memoPrintSubmitInfo.nextOfficerId", "error.er0050", new String[]{"Print/Send - Routing - Next Processing Officer is invalid."}, "Print/Send - Routing - Next Processing Officer is invalid.");
			}
			//20220630				
		}  
		return errors;
	}
	//end added on 20220609 check incorrect employee number input
	
	private Errors validateAp(AssessAppraisalMemoDTO dto,Errors errors, boolean submitReport){
		String desc = dto.getMemoPart2Info().getDutyDesc();
		if(submitReport){
			//part 2
			errors = EaValidatorUtil.checkMandatory(desc, errors, "memoPart2Info.dutyDesc", "Part II - Brief Description of Duties");
		}
		return errors;
	}
	private Errors validateAo(AssessAppraisalMemoDTO dto,Errors errors, boolean submitReport){
		MemoPart1Info mp1 = dto.getMemoPart1Info();
		MemoPart3Part4Info mp3 = dto.getMemoPart3Info();
		SignatureInfo si = dto.getSignatureInfoMemoP3();
		PrintSubmitInfo psi = dto.getMemoPrintSubmitInfo();
		MemoInfo mi = dto.getMemoInfo();
		
		String fromDate = mp1.getAppraisalPeriodStart();
		String toDate = mp1.getAppraisalPeriodEnd();	
		String appDate = si.getOfficerDate();

		//Part 1
		errors = EaValidatorUtil.checkMustBeValidDate(fromDate, errors, "memoPart1Info.appraisalPeriodStart", "Memo Info - Date");
		errors = EaValidatorUtil.checkMustBeValidDate(toDate, errors, "memoPart1Info.appraisalPeriodEnd", "Memo Info - Dated");
		errors = EaValidatorUtil.checkMustBeValidDate(appDate, errors, "signatureInfoMemoP3.officerDate", "Part III - Signature Info - Appraising Officer Appraisal Date");
		
		if (!StringUtil.isEmptyString(dto.getMemoPart2Info().getDutyDesc())){
			errors = EaValidatorUtil.checkMustBeValidDate(appDate, errors, "signatureInfoMemoP3.officerDate", "Part III - Signature Info - Appraising Officer Appraisal Date");
		}
		
		if(submitReport){
			errors = EaValidatorUtil.checkMandatory(mi.getMemoFrom(), errors, "memoInfo.memoFrom", "Memo Info - From");
			errors = EaValidatorUtil.checkMandatory(mi.getMemoFromDate(), errors, "memoInfo.memoFromDate", "Memo Info - Date");
			errors = EaValidatorUtil.checkMandatory(mi.getMemoTo(), errors, "memoInfo.memoTo", "Memo Info - To");
			if(!psi.getRouting().equalsIgnoreCase(Constants.STATUS_AP)){
				errors = EaValidatorUtil.checkMandatory(mi.getTelNo(), errors, "memoInfo.telNo", "Memo Info - Tel. No.");
			}
			
			//Part 3
			if (!StringUtil.isEmptyString(dto.getMemoPart2Info().getDutyDesc())){
				errors = EaValidatorUtil.checkMandatory(mp3.getOverallPerf(), errors, "memoPart3Info.overallPerf", "Part III - Overall Rating");
				errors = EaValidatorUtil.checkMandatory(mp3.getOverallComment(), errors, "memoPart3Info.overallComment", "Part III - Comments on Overall Performance");
				errors = EaValidatorUtil.checkMandatory(si.getOfficerRank(), errors, "signatureInfoMemoP3.officerRank", "Part III - Signature Info - Appraising Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(appDate, errors, "signatureInfoMemoP3.officerDate", "Part III - Signature Info - Appraising Officer Appraisal Date");
				errors = validateInputFieldBonusSalary(mp3.getBonusDimmed(),mp3.getSalaryDimmed(),mp3.getPerfBonus(),mp3.getSalaryAdj(),mp3.getOverallPerf(),errors,"Part III");
			}
			
			if (StringUtil.isEmptyString(dto.getMemoPart2Info().getDutyDesc())){
				if (!StringUtil.isEmptyString(psi.getRouting())){
					if(!psi.getRouting().equalsIgnoreCase("AP")){
						errors = EaValidatorUtil.checkMandatory(null, errors, "memoPrintSubmitInfo.routing", "Print/Send - Routing - If 'Part II - Brief Description of Duties' is not finished yet, sending 'To appraisee'");
					}
				}
			}
			
		}
		
		
		return errors;
	}
	private Errors validateCo(AssessAppraisalMemoDTO dto,Errors errors, boolean submitReport){
		MemoPart3Part4Info mp4 = dto.getMemoPart4Info();
		MemoPart3Part4Info mp3 = dto.getMemoPart3Info();
		SignatureInfo si = dto.getSignatureInfoMemoP4();
		SignatureInfo siAO = dto.getSignatureInfoMemoP3();
		PrintSubmitInfo psi = dto.getMemoPrintSubmitInfo();
		String appDate = si.getOfficerDate();
		
		Date apDate = Util.string2Date(siAO.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
		Date coDate = Util.string2Date(si.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
		//Part 4
		errors = EaValidatorUtil.checkMustBeValidDate(appDate, errors, "signatureInfoMemoP4.officerDate", "Part IV - Signature Info - Appraising Officer Appraisal Date");

		
		if(submitReport){
			//Part 3
			errors = EaValidatorUtil.checkMandatory(mp3.getOverallPerf(), errors, "memoPart3Info.overallPerf", "Part III - Overall Rating");
			errors = validateInputFieldBonusSalary(mp3.getBonusDimmed(),mp3.getSalaryDimmed(),mp3.getPerfBonus(),mp3.getSalaryAdj(),mp3.getOverallPerf(),errors,"Part III");
			
			//Part 4
			errors = EaValidatorUtil.checkMandatory(si.getOfficerRank(), errors, "signatureInfoMemoP4.officerRank", "Part IV - Signature Info - Countersigning Officer Rank/Post");
			errors = EaValidatorUtil.checkMandatory(si.getOfficerDate(), errors, "signatureInfoMemoP4.officerDate", "Part IV - Signature Info - Countersigning Officer Appraisal Date");
			errors = validateInputFieldBonusSalary(mp4.getBonusDimmed(),mp4.getSalaryDimmed(),mp4.getPerfBonus(),mp4.getSalaryAdj(),mp3.getOverallPerf(),errors,"Part IV");
			
			errors = EaValidatorUtil.checkMustBeLater(apDate, coDate, errors, "signatureInfoMemoP4.officerDate", "Appraisee Completion Date", "Part IV - Countersigning Officer Completion Date");
		}
		return errors;
	}
	private Errors validateIo(AssessAppraisalMemoDTO dto,Errors errors, boolean submitReport){
		MemoPart2Part5Info mp5 = dto.getMemoPart5Info();
		SignatureInfo si = dto.getSignatureInfoMemoP5();
		SignatureInfo siCO = dto.getSignatureInfoMemoP4();
		PrintSubmitInfo psi = dto.getMemoPrintSubmitInfo();
		String appDate = si.getOfficerDate();
		
		Date coDate = Util.string2Date(siCO.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
		Date ioDate = Util.string2Date(si.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
		//Part 5
		errors = EaValidatorUtil.checkMustBeValidDate(appDate, errors, "signatureInfoMemoP5.officerDate", "Part V - Signature Info - Interviewing Officer Appraisal Date");
		
		if(submitReport){
			errors = EaValidatorUtil.checkMandatory(mp5.getAppInterview(), errors, "memoPart5Info.appInterview", "Part V - Record of Appraisal Interview");
			errors = EaValidatorUtil.checkMandatory(si.getOfficerRank(), errors, "signatureInfoMemoP5.officerRank", "Part V - Signature Info - Interviewing Officer Rank/Post");
			errors = EaValidatorUtil.checkMandatory(si.getOfficerDate(), errors, "signatureInfoMemoP5.officerDate", "Part V - Signature Info - Interviewing Officer Appraisal Date");
			errors = EaValidatorUtil.checkMustBeLater(coDate, ioDate, errors, "signatureInfoMemoP5.officerDate", "Countersigning Officer Completion Date", "Part V - Interviewing Officer Completion Date");
		}
		return errors;
	}
	
	private Errors validateSugm(AssessAppraisalMemoDTO dto,Errors errors, boolean submitReport){
		MemoPart1Info mp1 = dto.getMemoPart1Info();
		Date commenceStartDate = Util.string2Date(mp1.getAppraisalPeriodStart(),  Constants.DISPLAY_DATE_FORMAT);
		Date commenceEndDate = Util.string2Date(mp1.getAppraisalPeriodEnd(),  Constants.DISPLAY_DATE_FORMAT);
		
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		EaReportRole err = appraiseeCommonSearchDAO.searchNextOfficerByStatus(dto.getReportId(), er.getStatus(), dto.getMemoPrintSubmitInfo().getRoutingTo());
		
		if(Constants.STATUS_CD.equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRoutingTo()) && StringUtil.isEmptyString(err.getOfficerId())){
			errors.rejectValue("memoPrintSubmitInfo.routingTo","error.er0081","Coordinator not exist - You have not assigned CD officer of this report.");
		}

		//check empty of commence date
		errors = EaValidatorUtil.checkMandatory(mp1.getAppraisalPeriodStart(), errors,"memoPart1Info.appraisalPeriodStart", "Commence Start Date");
		errors = EaValidatorUtil.checkMandatory(mp1.getAppraisalPeriodEnd(), errors, "memoPart1Info.appraisalPeriodEnd", "Commence End Date");
		
		//check validate of commence date
		if(!StringUtil.isEmptyString(mp1.getAppraisalPeriodStart()) && !StringUtil.isEmptyString(mp1.getAppraisalPeriodEnd())){
			errors = EaValidatorUtil.checkMustBeValidDate(mp1.getAppraisalPeriodStart(), errors, "memoPart1Info.appraisalPeriodStart", "Commence Start Date");
			errors = EaValidatorUtil.checkMustBeValidDate(mp1.getAppraisalPeriodEnd(), errors, "memoPart1Info.appraisalPeriodEnd", "Commence End Date");
			errors = EaValidatorUtil.checkMustBeLater(commenceStartDate, commenceEndDate, errors, "memoPart1Info.appraisalPeriodEnd", "Commence Start Date", "Commence End Date");
		}
		
		//check mandatory of post and division
		errors = EaValidatorUtil.checkMandatory(mp1.getPresentPost(), errors, "memoPart1Info.presentPost", "Present Post");
		errors = EaValidatorUtil.checkMandatory(mp1.getDivisionSection(), errors, "memoPart1Info.divisionSection", "Division Section");
		
		return errors;
	}
	private Errors validateGmSugm(AssessAppraisalMemoDTO dto,Errors errors, boolean submitReport){
		MemoPart1Info mp1 = dto.getMemoPart1Info();
		Date commenceStartDate = Util.string2Date(mp1.getAppraisalPeriodStart(),  Constants.DISPLAY_DATE_FORMAT);
		Date commenceEndDate = Util.string2Date(mp1.getAppraisalPeriodEnd(),  Constants.DISPLAY_DATE_FORMAT);
		
		//Part 1
		errors = EaValidatorUtil.checkMustBeValidDate(mp1.getAppraisalPeriodStart(), errors, "memoPart1Info.appraisalPeriodStart", "Part I - Personal Particulars - Appraisal Period Start");
		errors = EaValidatorUtil.checkMustBeValidDate(mp1.getAppraisalPeriodEnd(), errors, "memoPart1Info.appraisalPeriodEnd", "Part I - Personal Particulars - Appraisal Period End");
		errors = EaValidatorUtil.checkMandatory(mp1.getAppraisalPeriodStart(), errors, "memoPart1Info.appraisalPeriodStart", "Part I - Personal Particulars - Appraisal Period Start");
		errors = EaValidatorUtil.checkMandatory(mp1.getAppraisalPeriodEnd(), errors, "memoPart1Info.appraisalPeriodEnd", "Part I - Personal Particulars - Appraisal Period End");
		
		if(submitReport){
			errors = EaValidatorUtil.checkMandatory(mp1.getPresentPost(), errors, "memoPart1Info.presentPost", "Part I - Personal Particulars - Present Post");
			errors = EaValidatorUtil.checkMandatory(mp1.getDivisionSection(), errors, "memoPart1Info.divisionSection", "Part I - Personal Particulars - Division/section");
		}
		
		errors = EaValidatorUtil.checkMustBeLater(commenceStartDate, commenceEndDate, errors, "memoPart1Info.appraisalPeriodEnd", "Commence Start Date", "Commence End Date");
		
		return errors;
	}
	
	private Errors validateGmSugmSubRpt(AssessAppraisalMemoDTO dto,Errors errors, boolean submitReport){
		PrintSubmitInfo psi = dto.getMemoPrintSubmitInfo();
		ReportUserRole role = dto.getReportUserRole();
		if(submitReport){
			//print submit
			if(!role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_GM)){
				errors = EaValidatorUtil.checkMandatory(psi.getRouting(), errors, "memoPrintSubmitInfo.routing", "Print/Send - Routing");
			}
			//for whom has access right to confirm/reject 
			if(role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_GM)){
				errors = EaValidatorUtil.checkMandatory(psi.getConfirmReject(), errors, "memoPrintSubmitInfo.confirmReject", "Print/Send - Confirm/Reject");
				if(!StringUtil.isEmptyString(psi.getConfirmReject()) && !StringUtil.isEmptyString(psi.getRouting())){
					if(psi.getConfirmReject().equalsIgnoreCase("R") && !psi.getRouting().equalsIgnoreCase("AR")){
						errors.rejectValue("memoPrintSubmitInfo.confirmReject", "error.er0079", "Print/Send - Confirm/Reject Report - If the 'Reject' option is selected, the Routing option should be selected 'To any recipients'.");
					}
					
				}
			}	
			if (!StringUtil.isEmptyString(psi.getRouting())) {
				if (psi.getRouting().equalsIgnoreCase("AR") && StringUtil.isEmptyString(psi.getRoutingTo())
						&& !StringUtil.isEmptyString(psi.getConfirmReject())) {
					if (psi.getConfirmReject().equalsIgnoreCase("R")) {
						errors = EaValidatorUtil.checkMandatory(psi.getRoutingTo(), errors,
								"memoPrintSubmitInfo.routingTo",
								"Print/Send - Confirm/Reject Report - If the 'To any recipients' option is selected, the Routing Role");
					}
				}
			}
		}
		
		return errors;
	}
	private Errors validateICdAoApCoIoEo(AssessAppraisalMemoDTO dto,Errors errors, boolean submitReport){
		if(submitReport){
			PrintSubmitInfo psi = dto.getMemoPrintSubmitInfo();
			ReportUserRole role = dto.getReportUserRole();
			String routing = psi.getRouting();
			//20220630 Prevent empty next officer ID
			String nextOfficerId = psi.getNextOfficerId().trim();
			String nextOfficeName = psi.getNextOfficerName();
            //20220630 End - Prevent empty next officer ID
       
//			System.out.println("nextOfficerId >" + nextOfficerId + "<");
//			System.out.println("StringUtil.isEmptyString(nextOfficerId)" + StringUtil.isEmptyString(nextOfficerId));
			errors = EaValidatorUtil.checkMandatory(routing, errors, "memoPrintSubmitInfo.routing", "Print/Send - Routing");
			if(!Util.isEmptyString(routing)){
				if (routing.equalsIgnoreCase("NO") && !role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_IO)){
					log.info("checking nextofficerid mandatory");
					//errors = EaValidatorUtil.checkMandatory(nextOfficeName, errors, "memoPrintSubmitInfo.nextOfficerName", "Print/Send - Routing - Next Processing Officer");
					//20220630 End - Prevent empty next officer ID
					errors = EaValidatorUtil.checkMandatory(nextOfficerId, errors, "memoPrintSubmitInfo.nextOfficerId", "Print/Send - Routing - Next Processing Officer");
					//20220630 Prevent empty next officer ID
					String roleNextOfficerId = Objects.toString(role.getNextOfficerId(), "");
					//20220630 End - Prevent empty next officer ID					
					if (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_AO)){
						if (roleNextOfficerId.equalsIgnoreCase(role.getCurrentOfficerId())){
							//20220630 Prevent empty next officer ID
							if (nextOfficeName.equalsIgnoreCase(role.getCurrentOfficerId())) {
								errors.rejectValue("memoPrintSubmitInfo.nextOfficerName", "error.er0080", "Print/Send - Routing - Appraising Officer and Countersigning Officer cannot be same.");
							}
							//20220630 End - Prevent empty next officer ID						
						}
					}
				}
			}
			
		}
		return errors;
	}
	
	
	private Errors validateInputFieldBonusSalary(String bonusDimmed, String salaryDimmed, String bonus, String salaryAdjust, String overallRating, Errors errors, String sectionTitle){
		String keyBonus = "";
		String keySalary = "";
		if (sectionTitle.equalsIgnoreCase("Part III")){
			keyBonus = "memoPart3Info.perfBonus";
			keySalary = "memoPart3Info.salaryAdj";
		} else if (sectionTitle.equalsIgnoreCase("Part IV")) {
			keyBonus = "memoPart4Info.perfBonus";
			keySalary = "memoPart4Info.salaryAdj";
		} 
		
		if ("Y".equalsIgnoreCase(bonusDimmed)){
			if (StringUtil.isEmptyString(bonus)){
				errors.rejectValue(keyBonus, "error.message.empty.content", new String[]{sectionTitle + " - Recommendation on Merit Payment"}, sectionTitle + " - Recommendation on Merit Payment is required.");
			}
			if ("1".equalsIgnoreCase(bonus)){
				if (!Util.isEmptyString(overallRating)) {
					if ( ! ("A".equalsIgnoreCase(overallRating) || "B".equalsIgnoreCase(overallRating)) ) {
						errors.rejectValue(keyBonus, "error.er0076", new String[]{sectionTitle}, sectionTitle + " - To be eligible for consideration/award of bonus, the staff should attain an overall performance rating equivalent to \"B !V Very Effective\" level or above.");
					}
				}
			}
		}
		if ("Y".equalsIgnoreCase(salaryDimmed)){
			if (StringUtil.isEmptyString(salaryAdjust)){
				errors.rejectValue(keySalary, "error.message.empty.content", new String[]{sectionTitle + " - Recommendation on Annual Salary Adjustment"}, sectionTitle + " - Recommendation on Annual Salary Adjustment is required.");
			}
			if (!Util.isEmptyString(overallRating)) {
				if ("1".equalsIgnoreCase(salaryAdjust)){
					if ( "D".equalsIgnoreCase(overallRating) || "E".equalsIgnoreCase(overallRating) ) {
						errors.rejectValue(keySalary, "error.er0077", new String[]{sectionTitle}, sectionTitle +  " - The appraisee should not be recommended for the award of annual salary adjustment since he/she has attained an overall performance rating equivalent to \"D !V Below Expectation\" level or below.");
					}
				}else if ("2".equalsIgnoreCase(salaryAdjust)){
					if ( "A".equalsIgnoreCase(overallRating) || "B".equalsIgnoreCase(overallRating) || "C".equalsIgnoreCase(overallRating) ) {
						errors.rejectValue(keySalary, "error.er0078", new String[]{sectionTitle}, sectionTitle + "The appraisee should be recommended for the award of annual salary adjustment since he/she has attained an overall performance rating equivalent to \"C !V Effective\" level or above.");
					}
				}
			}			
		}
		return errors;
	}
	

}
