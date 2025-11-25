package com.hkha.ea.validator.assess;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
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
import com.hkha.ea.dao.assess.AppraisalAssessmentDAO;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.common.EmployeeEnquiryDAO;
import com.hkha.ea.dao.security.UserEnquiryDAO;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.dto.assess.AppraiseeInfo;
import com.hkha.ea.dto.assess.AssessAppraisalDTO;
import com.hkha.ea.dto.assess.CoreCompetencyInfo;
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
import com.hkha.ea.dto.common.EmployeeEnquiryDTO;
import com.hkha.ea.dto.common.ReportUserRole;
import com.hkha.ea.domain.EaLogin;
import com.hkha.ea.domain.EaUser;
import com.hkha.ea.dto.common.EaLoginDTO;
import com.hkha.ea.dto.common.EaUserDTO;
import com.hkha.ea.dto.security.UserEnquiryDto;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;

import com.hkha.ea.helper.EaValidatorUtil;
@Component( "assessAppraisalReportValidator" )
public class AssessAppraisalReportValidator implements Validator{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AssessAppraisalReportValidator.class.getName());
	//End 20231201 Write log in catalina.out
	
	//added on 20180130 incorrect employee number issue
	@Autowired
	private EmployeeEnquiryDAO employeeEnquiryDAO;
	
	@Autowired
	private UserEnquiryDAO userEnquiryDAO;


	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}
	@Autowired
	private AppraisalAssessmentDAO appraisalAssessmentDAO;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	private final static String el="(((0[1-9]|[12][0-9]|3[01])/((0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)/(0[469]|11))|(0[1-9]|[1][0-9]|2[0-8])/(02))/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}))|(29/02/(([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00)))";
	
	public void validateAssessAppraisalReportMandatory(AssessAppraisalDTO dto,Errors errors){
		
		Map<String,String> pagesMap=appraisalAssessmentDAO.getAssessAppraisalReportPageMapsByRole(dto.getReportUserRole().getCurrentUserRoleStatus());
		String currentPage=pagesMap.get(dto.getCurrentPage());
		//validator ai
		errors=validateAppraiseeInfo(dto.getAppraiseeInfo(),errors);
		if("PI".equalsIgnoreCase(currentPage)){
			errors=validatePersonalInfo(dto.getPersonalInfo(),errors);
		}else if("A".equalsIgnoreCase(currentPage)){
			errors=validatePartAB1Info(dto.getPartAInfoList(),errors,currentPage);
			errors=validateSignatureInfo("A",dto.getSignatureInfoA(),errors,currentPage);
			
		}else if("B1".equalsIgnoreCase(currentPage)){
			errors=validatePartAB1Info(dto.getPartB1InfoList(),errors,currentPage);
			
		}else if("B2".equalsIgnoreCase(currentPage)){
			errors=validatePartB2Info(dto.getPartB2Info(),errors);
			
		}else if("B3".equalsIgnoreCase(currentPage)){
			errors=validatePartB3Info(dto.getPartB3Info(), errors);
			
		}else if("B4".equalsIgnoreCase(currentPage)){
			errors=validateSignatureInfo("B4",dto.getSignatureInfoB4(),errors,currentPage);
			errors=validatePartB4Info(dto.getPartB4Info(),errors);
			
		}else if("B5".equalsIgnoreCase(currentPage)){
			errors=validateSignatureInfo("B5",dto.getSignatureInfoB5(),errors,currentPage);
			errors=validatePartB578Info(dto.getPartB5Info(),errors,currentPage);
		}else if("B6".equalsIgnoreCase(currentPage)){
			errors=validateSignatureInfo("B6",dto.getSignatureInfoB6(),errors,currentPage);
			errors=validatePartB6Info(dto.getPartB6Info(),errors);
		}else if("B7".equalsIgnoreCase(currentPage)){
			errors=validateSignatureInfo("B7",dto.getSignatureInfoB7(),errors,currentPage);
			errors=validatePartB578Info(dto.getPartB7Info(),errors,currentPage);
		}else if("B8".equalsIgnoreCase(currentPage)){
			errors=validateSignatureInfo("B8",dto.getSignatureInfoB8(),errors,currentPage);
			errors=validatePartB578Info(dto.getPartB8Info(),errors,currentPage);
		}
	}
	
	public void validateAssessAppraisalReportSend(AssessAppraisalDTO dto,Errors errors){
		errors=validatePrintSubmitInfo(dto,errors);
	}
	
	private  Errors validateAppraiseeInfo(AppraiseeInfo ai,Errors errors){
		Pattern p = Pattern.compile(el); 
		Date fromDate=null;
		Date toDate=null;
		if(StringUtil.isEmptyString(ai.getAppraisalPeriodStart())){
			errors.rejectValue("appraiseeInfo.appraisalPeriodStart", "error.message.empty.content", new String[]{"Appraisal Period Start"}, "Appraisal Period Start is required.");
		}else{
			Matcher m = p.matcher(ai.getAppraisalPeriodStart());
			if(!m.matches()){
				errors.rejectValue("appraiseeInfo.appraisalPeriodStart", "error.message.empty.content.date.format", new String[]{"Appraisal Period Start "}, "format must be 'dd/MM/yyyy'");
			}else{
				fromDate=Util.string2Date(ai.getAppraisalPeriodStart(),  Constants.DISPLAY_DATE_FORMAT);
			}
		}
		
		if(StringUtil.isEmptyString(ai.getAppraisalPeriodEnd())){
			errors.rejectValue("appraiseeInfo.appraisalPeriodEnd", "error.message.empty.content", new String[]{"Appraisal Period To"}, "Appraisal Period To is required.");
		}else{
			Matcher m = p.matcher(ai.getAppraisalPeriodEnd());
			if(!m.matches()){
				errors.rejectValue("appraiseeInfo.appraisalPeriodEnd", "error.message.empty.content.date.format", new String[]{"Appraisal Period To "}, "format must be 'dd/MM/yyyy'");
			}else{
				toDate=Util.string2Date(ai.getAppraisalPeriodEnd(),  Constants.DISPLAY_DATE_FORMAT);
			}
		}
		
		if(null!=fromDate && null!=toDate){
			if(toDate.before(fromDate)){
				errors.rejectValue("appraiseeInfo.appraisalPeriodEnd", "error.er0063", new String[]{"Appraisal Period To"}, "Appraisal Period To must equals or after Appraisal Period Start.");
			}
		}
		
		return errors;
	}
	
	private  Errors validatePersonalInfo(PersonalInfo pi,Errors errors){
		if(StringUtil.isEmptyString(pi.getPresentPost())){
			errors.rejectValue("personalInfo.presentPost", "error.message.empty.content", new String[]{"Personal Information Present Post"}, "Personal Information Present Post is required.");
		}
		
		if(StringUtil.isEmptyString(pi.getDivisionSection())){
			errors.rejectValue("personalInfo.divisionSection", "error.message.empty.content", new String[]{"Personal Information Division/Section"}, "Personal Information Division/Section is required.");
		}
		

		return errors;
	}
	
	private  Errors validatePartAB1Info(List<PartAB1Info> palist,Errors errors,String flag){
		
		for(int i=0;i<palist.size();i++){
			/*if(StringUtil.isEmptyString(palist.get(i).getKeyObjectives())){
				if("A".equalsIgnoreCase(flag)){
					//errors.rejectValue("partAB1Info.keyObjectives", "error.message.empty.content", new String[]{"Part A key Objectives"}, "Part A key Objectives is required.");
					errors.rejectValue("", "error.message.empty.content", new String[]{"Part A key Objectives"}, "Part A key Objectives is required.");
				}else{
					errors.rejectValue("", "error.message.empty.content", new String[]{"Part B key Objectives"}, "Part B key Objectives is required.");
				}
				
			}
			if(StringUtil.isEmptyString(palist.get(i).getResultAchieved())){
				if("A".equalsIgnoreCase(flag)){
					errors.rejectValue("", "error.message.empty.content", new String[]{"Part A Result To Be Achieved"}, "Part A Result To Be Achieved is required.");
				}else{
					//errors.rejectValue("partAB1Info.resultAchieved", "error.message.empty.content", new String[]{"Part B Result To Be Achieved"}, "Part B Result To Be Achieved is required.");
					errors.rejectValue("", "error.message.empty.content", new String[]{"Part B Result To Be Achieved"}, "Part B Result To Be Achieved is required.");
				}
			}
			
			if(StringUtil.isEmptyString(String.valueOf(palist.get(i).getWeighting()))){
				if("A".equalsIgnoreCase(flag)){
					errors.rejectValue("", "error.message.empty.content", new String[]{"Part A Weighting"}, "Part A Weighting is required.");
				}else{
					errors.rejectValue("", "error.message.empty.content", new String[]{"Part B Weighting"}, "Part B Weighting is required.");
				}
				
			}*/
			if(!StringUtil.isEmptyString(String.valueOf(palist.get(i).getWeighting())) && !"null".equals(String.valueOf(palist.get(i).getWeighting()))){
				if(palist.get(i).getWeighting() <0 || palist.get(i).getWeighting() >100){
					if("A".equalsIgnoreCase(flag)){
						errors.rejectValue("", "error.er0075", new String[]{"Part A Weighting"}, "");
						//errors.rejectValue("", "error.message.empty.content", new String[]{"Part A Weighting"}, "Part A Weighting is required.");
					}else{
						errors.rejectValue("", "error.er0075", new String[]{"Part B Weighting"}, "");
						//errors.rejectValue("", "error.message.empty.content", new String[]{"Part B Weighting"}, "Part B Weighting is required.");
					}
				}
				if("B1".equalsIgnoreCase(flag)){
					if(StringUtil.isEmptyString(palist.get(i).getRating())){
						errors.rejectValue("", "error.message.empty.content", new String[]{"Part B Rating"}, "Part B Rating is required.");
					}
				}
			}
		}

		return errors;
	}
	
	private  Errors validateSignatureInfo(String partType,SignatureInfo si,Errors errors,String flag){
		Pattern p = Pattern.compile(el); 
		
			if(StringUtil.isEmptyString(si.getOfficerDate())){
				errors.rejectValue("signatureInfo"+partType+".officerDate", "error.message.empty.content", new String[]{"Signature Information Officer Date"}, "Signature Information Officer Date is required.");
			}else{
				Matcher m = p.matcher(si.getOfficerDate());
				if(!m.matches()){
					errors.rejectValue("signatureInfo"+partType+".officerDate", "error.message.empty.content.date.format", new String[]{"Signature Information Officer Date "}, "format must be 'dd/MM/yyyy'");
				}
			}
			
			if(StringUtil.isEmptyString(si.getOfficerRank())){
				errors.rejectValue("signatureInfo"+partType+".officerRank", "error.message.empty.content", new String[]{"Signature Information Officer Rank"}, "Signature Information Officer Rank is required.");
			}

		if("A".equals(flag)||"B6".equals(flag)){
			if(StringUtil.isEmptyString(si.getAppraiseeSignDate())){
				errors.rejectValue("signatureInfo"+partType+".appraiseeSignDate", "error.message.empty.content", new String[]{"Signature Information Appraisee Date"}, "Signature Information Appraisee Date is required.");
			}else{
				Matcher m = p.matcher(si.getOfficerDate());
				if(!m.matches()){
					errors.rejectValue("signatureInfo"+partType+".appraiseeSignDate", "error.message.empty.content.date.format", new String[]{"Signature Information Appraisee Date "}, "format must be 'dd/MM/yyyy'");
				}
			}
			
			if(StringUtil.isEmptyString(si.getAppraiseeRankPost())){
				errors.rejectValue("signatureInfo"+partType+".appraiseeRankPost", "error.message.empty.content", new String[]{"Signature Information Appraisee Rank"}, "Signature Information Appraisee Rank is required.");
			}
		}
		
		return errors;
	}
	
	private  Errors validatePartB2Info(PartB2Info pb2,Errors errors){
		
		if(StringUtil.isEmptyString(pb2.getOverallRating())){
			errors.rejectValue("partB2Info.overallRating", "error.message.empty.content", new String[]{"Part B2 Information Overall Rating "}, "Part B2 Information Overall Rating is required.");
		}	
		return errors;
	}
	
	private  Errors validatePartB3Info(PartB3Info pb3,Errors errors){
		
		if("Y".equals(pb3.getBonusDimmed())){
			if(StringUtil.isEmptyString(pb3.getAoPerformanceBonus())){
				errors.rejectValue("partB3Info.aoPerformanceBonus", "error.message.empty.content", new String[]{"Part B3 Information Performance Bonus "}, "Part B3 Information Performance Bonus is required.");
			}		
		}
		
		if("Y".equals(pb3.getSalaryDimmed())){
			if(StringUtil.isEmptyString(pb3.getAoSalaryAdjustment())){
				errors.rejectValue("partB3Info.aoSalaryAdjustment", "error.message.empty.content", new String[]{"Part B3 Information Salary Adjustment "}, "Part B3 Information Salary Adjustment is required.");
			}	
		}
		return errors;
	}
	
	private  Errors validatePartB4Info(PartB4Info pb4,Errors errors){
		if(null == pb4)
			pb4 = new PartB4Info();
		
		if(StringUtil.isEmptyString(pb4.getCounterPotentialB4())){
			errors.rejectValue("partB4Info.counterPotentialB4", "error.message.empty.content", new String[]{"Part B4 Information Counter Potential "}, "Part B4 Information Counter Potential is required.");
		}
		if(pb4.getCcInfoList() != null && pb4.getCcInfoList().size() > 0){
			for(int i=0;i<pb4.getCcInfoList().size();i++){
				if(StringUtil.isEmptyString(pb4.getCcInfoList().get(i).getRating())){
					errors.rejectValue("partB4Info.counterPotentialB4", "error.message.empty.content", new String[]{"Part B4 Information "+pb4.getCcInfoList().get(i).getCompetency()}, "Part B4 Information "+pb4.getCcInfoList().get(i).getCompetency()+" is required.");
				}	
			}
		}else{
			errors.rejectValue("partB4Info.counterPotentialB4", "error.message.empty.content", new String[]{"Part B4 Core Competencies"} , "Part B4 Core Competencies are empty. Please contact IT department.");
		}
		
		return errors;
	}
	
	private  Errors validatePartB578Info(PartB5B7B8Info pb578,Errors errors,String flag){
		
		if(StringUtil.isEmptyString(pb578.getPotential())){
			if("B5".equals(flag)){
				errors.rejectValue("partB5Info.potential", "error.message.empty.content", new String[]{"Part B5 Information Potential Assessment"}, "Part B5 Information Potential Assessment is required.");
			}else if("B7".equals(flag)){
				errors.rejectValue("partB7Info.potential", "error.message.empty.content", new String[]{"Part B7 Information Potential Assessment"}, "Part B7 Information Potential Assessment is required.");
			}else if("B8".equals(flag)){
				errors.rejectValue("partB8Info.potential", "error.message.empty.content", new String[]{"Part B8 Information Potential Assessment"}, "Part B8 Information Potential Assessment is required.");
			}
		}
		
		if("Y".equals(pb578.getBonusDimmed())){
			if(StringUtil.isEmptyString(pb578.getPerformanceBonus())){
				if("B5".equals(flag)){
					errors.rejectValue("partB5Info.performanceBonus", "error.message.empty.content", new String[]{"Part B5 Information Performance Bonus "}, "Part B5 Information Performance Bonus is required.");
				}else if("B7".equals(flag)){
					errors.rejectValue("partB7Info.performanceBonus", "error.message.empty.content", new String[]{"Part B7 Information Performance Bonus "}, "Part B7 Information Performance Bonus is required.");
				}else if("B8".equals(flag)){
					errors.rejectValue("partB8Info.performanceBonus", "error.message.empty.content", new String[]{"Part B8 Information Performance Bonus "}, "Part B8 Information Performance Bonus is required.");
				}
			}	
		}
		
		if("Y".equals(pb578.getSalaryDimmed())){
			if(StringUtil.isEmptyString(pb578.getSalaryAdjustment())){
				if("B5".equals(flag)){
					errors.rejectValue("partB5Info.salaryAdjustment", "error.message.empty.content", new String[]{"Part B5 Information Salary Adjustment "}, "Part B5 Information Salary Adjustment is required.");
				}else if("B7".equals(flag)){
					errors.rejectValue("partB7Info.salaryAdjustment", "error.message.empty.content", new String[]{"Part B7 Information Salary Adjustment "}, "Part B7 Information Salary Adjustment is required.");
				}else if("B8".equals(flag)){
					errors.rejectValue("partB8Info.salaryAdjustment", "error.message.empty.content", new String[]{"Part B8 Information Salary Adjustment "}, "Part B8 Information Salary Adjustment is required.");
				}
			}	
		}
		
		if(StringUtil.isEmptyString(pb578.getOverallPerformance())){
			if("B5".equals(flag)){
				errors.rejectValue("partB5Info.overallPerformance", "error.message.empty.content", new String[]{"Part B5 Information Overall Performance "}, "Part B5 Information Overall Performance is required.");
			}else if("B7".equals(flag)){
				errors.rejectValue("partB7Info.overallPerformance", "error.message.empty.content", new String[]{"Part B7 Information Overall Comment "}, "Part B7 Information Overall Comment is required.");
			}/*else if("B8".equals(flag)){
				errors.rejectValue("partB8Info.overallPerformance", "error.message.empty.content", new String[]{"Part B8 Information Overall Comment "}, "Part B8 Information Overall Comment is required.");
			}*/
		}
		
		//add by elina for issue #52582
		if(StringUtil.isEmptyString(pb578.getReviewComment()) && "B8".equals(flag)){
			errors.rejectValue("partB8Info.reviewComment", "error.message.empty.content", new String[]{"Part B8 Review comment of the appraisee "}, "Part B8 Review comment of the appraisee is required.");
		}
		//end

		if(StringUtil.isEmptyString(pb578.getWorkWell()) && "B5".equals(flag)){
			errors.rejectValue("partB5Info.workWell", "error.message.empty.content", new String[]{"Part B5 Information Work Well "}, "Part B5 Information Work Well is required.");	
		}
		if(StringUtil.isEmptyString(pb578.getWorkWell()) && "B7".equals(flag)){
			errors.rejectValue("partB7Info.workWell", "error.message.empty.content", new String[]{"Part B7 Information Work Well "}, "Part B7 Information Work Well is required.");
		}
		
		return errors;
	}

	private  Errors validatePartB6Info(PartB6Info pb6,Errors errors){
		
		if(StringUtil.isEmptyString(pb6.getReviewInterview())){
			errors.rejectValue("partB6Info.reviewInterview", "error.message.empty.content", new String[]{"Part B6 Information Discussion Summary "}, "Part B6 Information Discussion Summary is required.");
		}	
		
		return errors;
	}
	
	private  Errors validatePrintSubmitInfo(AssessAppraisalDTO dto,Errors errors){
		
		if("AO".equals(dto.getReportUserRole().getCurrentUserRoleStatus())||"CO".equals(dto.getReportUserRole().getCurrentUserRoleStatus())){
			if(StringUtil.isEmptyString(dto.getPrintSubmitInfo().getNextOfficerId())){
				errors.rejectValue("printSubmitInfo.nextOfficerId", "error.er0050", new String[]{"Print/Send - Routing - Next Processing Officer is invalid."}, "Print/Send - Routing - Next Processing Officer is invalid.");
			}			
			if(StringUtil.isEmptyString(dto.getPrintSubmitInfo().getNextOfficerName())){
				errors.rejectValue("printSubmitInfo.nextOfficerName", "error.message.empty.content", new String[]{"Print/Send - Routing - Next Processing Officer "}, "Print/Send - Routing - Next Processing Officer is invalid.");
			}
		}
		
		if("GM".equals(dto.getReportUserRole().getCurrentUserRoleStatus())){
			if(StringUtil.isEmptyString(dto.getPrintSubmitInfo().getConfirmReject())){
				errors.rejectValue("printSubmitInfo.confirmReject", "error.message.empty.content", new String[]{"Print/Send Information Confirm/Reject Report"}, "Print/Send Information Confirm/Reject Report is required.");
			}else{
				if("R".equals(dto.getPrintSubmitInfo().getConfirmReject()) && StringUtil.isEmptyString(dto.getPrintSubmitInfo().getRoutingTo())){
					errors.rejectValue("printSubmitInfo.routingTo", "error.message.empty.content", new String[]{"Print/Send Information Routing To"}, "Print/Send Information Routing To is required.");
				}
			}
		}
		
		return errors;
	}
	public Errors validateInputFields(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		return validateInputFields(dto,errors, submitReport, false);
	}
	public Errors validateInputFields(AssessAppraisalDTO dto,Errors errors, boolean submitReport, boolean isValidateAll){
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
		//boolean isSUGM = role.isSuperUser() || role.isGradeManagement();
		boolean isSUGM = (role.getCurrentUserRoleStatus().equalsIgnoreCase("SU")) || isGM;
		
		if (isI || isGM || isSUGM) {
			errors = validateIGmSugm(dto, errors, submitReport || isValidateAll);
		}
		if (isAP){
			errors = validateAp(dto, errors, submitReport || isValidateAll);
		} else if (isAO){
			errors = validateAo(dto, errors, submitReport || isValidateAll);
		} else if (isCO){
			errors = validateCo(dto, errors, submitReport || isValidateAll);
		} else if (isIO){
			errors = validateIo(dto, errors, submitReport || isValidateAll);
		} else if (isEO){
			errors = validateEo(dto, errors, submitReport || isValidateAll);
		} else if (isRO){
			errors = validateRo(dto, errors, submitReport || isValidateAll);
		} else if (isGM){
			errors = validateGm(dto, errors, submitReport || isValidateAll);
		}
		if (isSUGM){
			errors = validateSugm(dto, errors, submitReport || isValidateAll);
		}
		if (isGM || isSUGM){
			errors = validateGmSugm(dto, errors, submitReport || isValidateAll);
		}
		
		if (isI || isCD || isAO || isAP || isCO || isIO || isEO){
			// no isValidateAll on next officer field
			errors = validateICdAoApCoIoEo(dto, errors, submitReport);
		}
		
		errors = validateSizeLimitFullReport(dto,errors);
		
		//20220630 Prevent empty next officer ID
//		log.info(dto.getPrintSubmitInfo().getRouting());
		if(!(isGM || isSUGM)) {
			if (!dto.getPrintSubmitInfo().getRouting().equalsIgnoreCase("AP") && !dto.getPrintSubmitInfo().getRouting().equalsIgnoreCase("PO")) {
				errors = validateNextOfficerID(dto,errors);
			}
		}
		//20220630 End - Prevent empty next officer ID
		
		return errors;
	}
	//edited on 20170329 size limit of text area in full report
	private Errors validateSizeLimitFullReport(AssessAppraisalDTO dto, Errors errors){
		List<UserTableColumn> utc = appraiseeCommonSearchDAO.searchColumnSize("C");
		PersonalInfo pi = dto.getPersonalInfo(); 
		List<PartAB1Info> palist = dto.getPartAInfoList();
		List<PartAB1Info> pb1list = dto.getPartB1InfoList();
		PartB2Info pb2 = dto.getPartB2Info();
		PartB5B7B8Info pb5 = dto.getPartB5Info();
		PartB6Info pb6 = dto.getPartB6Info();
		PartB5B7B8Info pb8 = dto.getPartB8Info();
		try{
		for(int i=0; i<utc.size();i++){
			if(utc.get(i).getColumnName().equalsIgnoreCase("ACTING_APPOINT")){
				if(!StringUtil.isEmptyString(pi.getActingAppointment())){
					if(pi.getActingAppointment().getBytes("UTF-8").length > utc.get(i).getDataLength()){
						errors.rejectValue("", "error.message.length.toolong", new String[]{"Personal Info - Acting Appointments "}, "Personal Info - Acting Appointments " + " is too long.");
					}
				}
			}
			if(utc.get(i).getColumnName().equalsIgnoreCase("TRAINING_COURSE")){
				if(!StringUtil.isEmptyString(pi.getTrainingInReviewPeriod())){
					if(pi.getTrainingInReviewPeriod().getBytes("UTF-8").length > utc.get(i).getDataLength()){
						errors.rejectValue("", "error.message.length.toolong", new String[]{"Personal Info - Commendations/disciplinary offences during appraisal period "}, "Personal Info - Commendations/disciplinary offences during appraisal period " + " is too long.");
					}
				}
			}
			if(utc.get(i).getColumnName().equalsIgnoreCase("COMMENDATION")){
				if(!StringUtil.isEmptyString(pi.getCommendation())){
					if(pi.getCommendation().getBytes("UTF-8").length > utc.get(i).getDataLength()){
						errors.rejectValue("", "error.message.length.toolong", new String[]{"Personal Info - Training Courses Attended during the Appraisal Period "}, "Personal Info - Training Courses Attended during the Appraisal Period " + " is too long.");
					}
				}
			}
			if(utc.get(i).getColumnName().equalsIgnoreCase("KEY_OBJECTIVE")){
				for(int j=0;j<palist.size();j++){
					if(!StringUtil.isEmptyString(palist.get(j).getKeyObjectives())){
						
						if(palist.get(j).getKeyObjectives().getBytes("UTF-8").length >utc.get(i).getDataLength()){
								errors.rejectValue("", "error.message.length.toolong", new String[]{String.valueOf(j+1)+". Part A - Key Objectives "}, String.valueOf(j+1)+ ". Part A - Key Objectives " + " is too long.");
						}		
					}
					if(!StringUtil.isEmptyString(pb1list.get(j).getKeyObjectives())){
						
						if(pb1list.get(j).getKeyObjectives().getBytes("UTF-8").length > utc.get(i).getDataLength()){
								errors.rejectValue("", "error.message.length.toolong", new String[]{String.valueOf(j+1)+". Part B-1 - Key Objectives "}, String.valueOf(j+1)+ ". Part B-1 - Key Objectives " + " is too long.");
						}
					}
				}
			}
			if(utc.get(i).getColumnName().equalsIgnoreCase("RESULTS_ACHIEVED")){
				for(int j=0;j<palist.size();j++){
					if(!StringUtil.isEmptyString(palist.get(j).getResultAchieved())){
						
						if(palist.get(j).getResultAchieved().getBytes("UTF-8").length >utc.get(i).getDataLength()){
								errors.rejectValue("", "error.message.length.toolong", new String[]{String.valueOf(j+1)+". Part A - Results Achieved "}, String.valueOf(j+1)+ ". Part A - Results Achieved " + " is too long.");
						}		
					}
					if(!StringUtil.isEmptyString(pb1list.get(j).getResultAchieved())){
					
						if(pb1list.get(j).getResultAchieved().getBytes("UTF-8").length > utc.get(i).getDataLength()){
								errors.rejectValue("", "error.message.length.toolong", new String[]{String.valueOf(j+1)+". Part B-1 - Results Achieved "}, String.valueOf(j+1)+ ". Part B-1 - Results Achieved " + " is too long.");
						}
					}
				}
			}
			if(utc.get(i).getColumnName().equalsIgnoreCase("SPECIAL_FACTORS")){
				if(!StringUtil.isEmptyString(pb2.getSpecialFactors())){
					if(pb2.getSpecialFactors().getBytes("UTF-8").length > utc.get(i).getDataLength()){
						errors.rejectValue("", "error.message.length.toolong", new String[]{"Part B-2 - Special Factor "}, "Part B-2 - Special Factor " + " is too long.");
					}
				}
			}
			if(utc.get(i).getColumnName().equalsIgnoreCase("WORK_WELL")){
				if(!StringUtil.isEmptyString(pb5.getWorkWell())){
					if(pb5.getWorkWell().getBytes("UTF-8").length > utc.get(i).getDataLength()){
						errors.rejectValue("", "error.message.length.toolong", new String[]{"Part B-5 - How well the work of appraisee "}, "Part B-5 - How well the work of appraisee " + " is too long.");
					}
				}
			}
			if(utc.get(i).getColumnName().equalsIgnoreCase("OVERALL_PERFORMANCE")){
				if(!StringUtil.isEmptyString(pb5.getOverallPerformance())){
					if(pb5.getOverallPerformance().getBytes("UTF-8").length>utc.get(i).getDataLength()){
						errors.rejectValue("", "error.message.length.toolong", new String[]{"Part B-5 - Overall Performance "}, "Part B-5 - Overall Performance " + " is too long.");
					}
				}
			}
			
			if(utc.get(i).getColumnName().equalsIgnoreCase("DEVELOP_NEED")){
				for(int j=0;j<pb6.getTpiList().size();j++){
					if(!StringUtil.isEmptyString(pb6.getTpiList().get(j).getDelevelopNeed())){
						if(pb6.getTpiList().get(j).getDelevelopNeed().getBytes("UTF-8").length > utc.get(i).getDataLength()){
							errors.rejectValue("", "error.message.length.toolong", new String[]{"Part B-6 - Development Need "}, "Part B-6 - Development Need " + " is too long.");
						}
					}
				}
			}
			if(utc.get(i).getColumnName().equalsIgnoreCase("ACTION_PLAN")){
				for(int j=0;j<pb6.getTpiList().size();j++){
					if(!StringUtil.isEmptyString(pb6.getTpiList().get(j).getActionPlan())){
						if(pb6.getTpiList().get(j).getActionPlan().getBytes("UTF-8").length > utc.get(i).getDataLength()){
							errors.rejectValue("", "error.message.length.toolong", new String[]{"Part B-6 - Action Plan "}, "Part B-6 - Action plan " + " is too long.");
						}
					}
				}
			}
			if(utc.get(i).getColumnName().equalsIgnoreCase("REVIEW_COMMENT")){
				if(!StringUtil.isEmptyString(pb8.getReviewComment())){
					if(pb8.getReviewComment().getBytes("UTF-8").length > utc.get(i).getDataLength()){
						errors.rejectValue("", "error.message.length.toolong", new String[]{"Part B-7 - Review Comment "}, "Part B-7 - Review Comment " + " is too long.");
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
	//end edited on 20170403 size limit of text area
	//added on 20180131 check incorrect employee number input
	private Errors validateNextOfficerID(AssessAppraisalDTO dto, Errors errors){
//		log.info("dto.getPrintSubmitInfo().getNextOfficerId()" + dto.getPrintSubmitInfo().getNextOfficerId());
		String empNum = dto.getPrintSubmitInfo().getNextOfficerId();
		log.info("empNumber:"+empNum);
		List<EmployeeEnquiryDTO> listEmp= employeeEnquiryDAO.searchByEmployee(empNum);
		//edited on 20180322 fix bug (GM)
		if (empNum != null){
			EaLoginDTO loginEmployeeDTO = userEnquiryDAO.searchByLoginID(empNum);
			if (listEmp.size() == 0 && !empNum.equalsIgnoreCase("") && loginEmployeeDTO == null){
				errors.rejectValue("printSubmitInfo.nextOfficerId", "error.er0050", new String[]{"Print/Send - Routing - Next Processing Officer is invalid."}, "Print/Send - Routing - Next Processing Officer is invalid.");
			}
		}
		//end edited on 20180322 fix bug (GM) 
		return errors;
	}
	//end added on 20180131 check incorrect employee number input
	private Errors validateIGmSugm(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		AppraiseeInfo ai = dto.getAppraiseeInfo();
		errors = EaValidatorUtil.checkMustBeValidDate(ai.getAppraisalPeriodStart(), errors, "appraiseeInfo.appraisalPeriodStart", "Appraisal Period Start");

		errors = EaValidatorUtil.checkMustBeValidDate(ai.getAppraisalPeriodEnd(), errors, "appraiseeInfo.appraisalPeriodEnd", "Appraisal Period To");		
		errors = EaValidatorUtil.checkMandatory(ai.getAppraisalPeriodStart(), errors, "appraiseeInfo.appraisalPeriodStart", "Appraisal Period Start");
		errors = EaValidatorUtil.checkMandatory(ai.getAppraisalPeriodEnd(), errors, "appraiseeInfo.appraisalPeriodEnd", "Appraisal Period To");
		
		
		
		if (submitReport){
			PersonalInfo pi = dto.getPersonalInfo();
			Date fromDate = Util.string2Date(ai.getAppraisalPeriodStart(),  Constants.DISPLAY_DATE_FORMAT);
			Date toDate = Util.string2Date(ai.getAppraisalPeriodEnd(),  Constants.DISPLAY_DATE_FORMAT);
			errors = EaValidatorUtil.checkMustBeLater(fromDate, toDate, errors, "appraiseeInfo.appraisalPeriodEnd", "Appraisal Period Start", "Appraisal Period To");
			errors = EaValidatorUtil.checkMandatory(pi.getPresentPost(), errors, "personalInfo.presentPost", "Personal Information Present Post");
			errors = EaValidatorUtil.checkMandatory(pi.getDivisionSection(), errors, "personalInfo.divisionSection", "Personal Information Division/Section");
		}
		return errors;
	}
	private Errors validateAp(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		if (submitReport){
			PersonalInfo pi = dto.getPersonalInfo();
			SignatureInfo siA = dto.getSignatureInfoA();
			List<PartAB1Info> palist = dto.getPartAInfoList();

			errors = EaValidatorUtil.checkMandatory(pi.getPresentPost(), errors, "personalInfo.presentPost", "Personal Information Present Post");
			errors = validateInputFieldObjectives(palist, errors, "Part A", true, true, true, false);
			errors = EaValidatorUtil.checkMustBeValidDate(siA.getAppraiseeSignDate(), errors, "signatureInfoA.appraiseeSignDate", "Part A - Signature Info - Appraisee Sign Date");
			errors = EaValidatorUtil.checkMandatory(siA.getAppraiseeSignDate(), errors, "signatureInfoA.appraiseeSignDate", "Part A - Signature Info - Appraisee Sign Date");
		}
		return errors;
	}
	private Errors validateAo(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		SignatureInfo siA = dto.getSignatureInfoA();
		SignatureInfo siB4 = dto.getSignatureInfoB4();
		PrintSubmitInfo psi = dto.getPrintSubmitInfo();
		List<PartAB1Info> palist = dto.getPartB1InfoList();
		PartB2Info pb2 = dto.getPartB2Info();
		PartB3Info pb3 = dto.getPartB3Info();
		PartB4Info pb4 = dto.getPartB4Info();
		
		errors = EaValidatorUtil.checkMustBeValidDate(siA.getOfficerDate(), errors, "signatureInfoA.officerDate", "Part A - Signature Info - Appraising Officer Sign Date");

		errors = EaValidatorUtil.checkMustBeValidDate(siB4.getOfficerDate(), errors, "signatureInfoB4.officerDate", "Part B-4 - Signature Info - Appraising Officer Sign Date");
		if ((submitReport) && (!StringUtil.isEmptyString(siA.getAppraiseeSignDate()))){
			errors = EaValidatorUtil.checkMandatory(siA.getOfficerRank(), errors, "signatureInfoA.officerRank", "Part A - Signature Info - Appraising Officer Rank/Post");
			errors = EaValidatorUtil.checkMandatory(siA.getOfficerDate(), errors, "signatureInfoA.officerDate", "Part A - Signature Info - Appraising Officer Sign Date");
			Date apDate = Util.string2Date(siA.getAppraiseeSignDate(),  Constants.DISPLAY_DATE_FORMAT);
			Date aoADate = Util.string2Date(siA.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
			Date aoB4Date = Util.string2Date(siB4.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
			errors = EaValidatorUtil.checkMustBeLater(apDate, aoADate, errors, "signatureInfoA.officerDate", "Appraisee Completion Date", "Part A - Appraising Officer Completion Date");
			errors = EaValidatorUtil.checkMustBeLater(apDate, aoB4Date, errors, "signatureInfoB4.officerDate", "Appraisee Completion Date", "Part B-4 - Appraising Officer Completion Date");
			errors = validateInputFieldObjectives(palist, errors, "Part B-1", true, true, true, true);
			errors = EaValidatorUtil.checkMandatory(pb2.getOverallRating(), errors, "partB2Info.overallRating", "Part B-2 - Overall Rating");
			errors = EaValidatorUtil.checkMandatory(pb2.getOverallPerformanceComment(), errors, "partB2Info.overallPerformanceComment", "Part B-2 - Comments on Overall Performance");
			errors = validateInputFieldBonusSalary(pb3.getBonusDimmed(), pb3.getSalaryDimmed(), pb3.getAoPerformanceBonus(), pb3.getAoSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-3");
			errors = EaValidatorUtil.checkMandatory(siB4.getOfficerRank(), errors, "signatureInfoB4.officerRank", "Part B-4 - Signature Info - Appraising Officer Rank/Post");
			errors = EaValidatorUtil.checkMandatory(siB4.getOfficerDate(), errors, "signatureInfoB4.officerDate", "Part B-4 - Signature Info - Appraising Officer Sign Date");
			if(dto.getPartB4Info() == null){
				errors = EaValidatorUtil.checkMandatory(null, errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");	
			}else{
				try{
					if(dto.getPartB4Info().getCcInfoList().size()>0){
						errors = validateInputFieldCoreCompetency(pb4.getCcInfoList(), errors, "Part B-4");
		        	}
				}catch(Exception e){
					log.info("cc info list is null.");
				}
	        	if(StringUtil.isEmptyString(dto.getPartB4Info().getCounterPotentialB4())){
	        		errors = EaValidatorUtil.checkMandatory(pb4.getCounterPotentialB4(), errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");
	        	}
			}
			
		} else if ((submitReport) && (StringUtil.isEmptyString(siA.getAppraiseeSignDate())) && (!StringUtil.isEmptyString(psi.getRouting())) && (!"AP".equalsIgnoreCase(psi.getRouting())) ){
			errors = EaValidatorUtil.checkMandatory(null, errors, "printSubmitInfo.routing", "Print/Send - Routing - If 'Part A' is not finished yet, sending 'To appraisee'");
		}

		return errors;
	}
	private Errors validateCo(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		SignatureInfo siA = dto.getSignatureInfoA();
		SignatureInfo siB4 = dto.getSignatureInfoB4();
		SignatureInfo siB5 = dto.getSignatureInfoB5();
		List<PartAB1Info> palist = dto.getPartB1InfoList();
		PartB2Info pb2 = dto.getPartB2Info();
		PartB3Info pb3 = dto.getPartB3Info();
		PartB4Info pb4 = dto.getPartB4Info();
		PartB5B7B8Info pb5 = dto.getPartB5Info();
		errors = EaValidatorUtil.checkMustBeValidDate(siB5.getOfficerDate(), errors, "signatureInfoB5.officerDate", "Part B-5 - Signature Info - Countersigning Officer Sign Date");
		if (submitReport){
			errors = validateInputFieldObjectives(palist,errors, "Part B-1", false, false, false, true);
			errors = EaValidatorUtil.checkMandatory(pb2.getOverallRating(), errors, "partB2Info.overallRating", "Part B-2 - Overall Rating");
			errors = validateInputFieldBonusSalary(pb3.getBonusDimmed(), pb3.getSalaryDimmed(), pb3.getAoPerformanceBonus(), pb3.getAoSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-3");
			//errors = validateInputFieldCoreCompetency(pb4.getCcInfoList(), errors, "Part B-4");
			//errors = EaValidatorUtil.checkMandatory(pb4.getCounterPotentialB4(), errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");
			
			if(dto.getPartB4Info() == null){
				errors = EaValidatorUtil.checkMandatory(null, errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");	
			}else{
				try{
					if(dto.getPartB4Info().getCcInfoList().size()>0){
						errors = validateInputFieldCoreCompetency(pb4.getCcInfoList(), errors, "Part B-4");
		        	}
				}catch(Exception e){
					log.info("cc info list is null.");
				}
	        	if(StringUtil.isEmptyString(dto.getPartB4Info().getCounterPotentialB4())){
	        		errors = EaValidatorUtil.checkMandatory(pb4.getCounterPotentialB4(), errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");
	        	}
			}
			
			errors = EaValidatorUtil.checkMandatory(pb5.getWorkWell(), errors, "partB5Info.workWell", "Part B-5 - How well you know the work of the appraisee");
			errors = EaValidatorUtil.checkMandatory(pb5.getOverallPerformance(), errors, "partB5Info.overallPerformance", "Part B-5 - Overall Performance");
			errors = EaValidatorUtil.checkMandatory(pb5.getPotential(), errors, "partB5Info.potential", "Part B-5 - Potential Assessment");
			errors = EaValidatorUtil.checkMandatory(siB5.getOfficerRank(), errors, "signatureInfoB5.officerRank", "Part B-5 - Signature Info - Countersigning Officer Rank/Post");
			errors = EaValidatorUtil.checkMandatory(siB5.getOfficerDate(), errors, "signatureInfoB5.officerDate", "Part B-5 - Signature Info - Appraising Officer Sign Date");
			errors = validateInputFieldBonusSalary(pb5.getBonusDimmed(), pb5.getSalaryDimmed(), pb5.getPerformanceBonus(), pb5.getSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-5");
			Date aoADate = Util.string2Date(siA.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
			Date aoB4Date = Util.string2Date(siB4.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
			Date coB5Date = Util.string2Date(siB5.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
			errors = EaValidatorUtil.checkMustBeLater(aoADate, coB5Date, errors, "signatureInfoB5.officerDate", "Part A - Appraising Officer Completion Date", "Part B-5 - Countersigning Completion Date");
			errors = EaValidatorUtil.checkMustBeLater(aoB4Date, aoB4Date, errors, "signatureInfoB5.officerDate", "Part B-4 - Appraising Officer Completion Date", "Part B-5 - Countersigning Completion Date");
		}
		return errors;
	}
	private Errors validateIo(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		SignatureInfo siB5 = dto.getSignatureInfoB5();
		SignatureInfo siB6 = dto.getSignatureInfoB6();
		PartB6Info pb6 = dto.getPartB6Info();
		errors = EaValidatorUtil.checkMustBeValidDate(siB6.getAppraiseeSignDate(), errors, "signatureInfoB6.appraiseeSignDate", "Part B-6 - Signature Info - Appraisee Sign Date");
		errors = EaValidatorUtil.checkMustBeValidDate(siB6.getOfficerDate(), errors, "signatureInfoB6.officerDate", "Part B-6 - Signature Info - Interviewing Officer Sign Date");
		if (submitReport){
			errors = EaValidatorUtil.checkMandatory(pb6.getReviewInterview(), errors, "partB6Info.reviewInterview", "Part B-6 - Interview Review");
			errors = EaValidatorUtil.checkMandatory(siB6.getAppraiseeRankPost(), errors, "signatureInfoB6.appraiseeRankPost", "Part B-6 - Signature Info - Appraisee Rank/Post");
			errors = EaValidatorUtil.checkMandatory(siB6.getAppraiseeSignDate(), errors, "signatureInfoB6.appraiseeSignDate", "Part B-6 - Signature Info - Appraisee Sign Date");
			errors = EaValidatorUtil.checkMandatory(siB6.getOfficerRank(), errors, "signatureInfoB6.officerRank", "Part B-6 - Signature Info - Interviewing Officer Rank/Post");
			errors = EaValidatorUtil.checkMandatory(siB6.getOfficerDate(), errors, "signatureInfoB6.officerDate", "Part B-6 - Signature Info - Interviewing Officer Sign Date");
			Date apB6Date = Util.string2Date(siB6.getAppraiseeSignDate(),  Constants.DISPLAY_DATE_FORMAT);
			Date ioB6Date = Util.string2Date(siB6.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
			Date coB5Date = Util.string2Date(siB5.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
			errors = EaValidatorUtil.checkMustBeSameDate(apB6Date, ioB6Date, errors, "signatureInfoB6.officerDate", "Part B-6 - The Completion Date of Interviewing Officer", "The Completion Date of Appraisee");
			errors = EaValidatorUtil.checkMustBeLater(coB5Date, apB6Date, errors, "signatureInfoB6.appraiseeSignDate", "Part B-5 - Countersigning Completion Date", "Part B-6 - Appraisee Completion Date");
			errors = EaValidatorUtil.checkMustBeLater(coB5Date, ioB6Date, errors, "signatureInfoB6.officerDate", "Part B-5 - Countersigning Completion Date", "Part B-6 - Interviewing Officer Sign Date");
		}
		return errors;
	}
	private Errors validateEo(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		SignatureInfo siB7 = dto.getSignatureInfoB7();
		errors = EaValidatorUtil.checkMustBeValidDate(siB7.getOfficerDate(), errors, "signatureInfoB7.officerDate", "Part B-7 - Signature Info - Endorsing Officer Sign Date");
		if (submitReport){
			List<PartAB1Info> palist = dto.getPartB1InfoList();
			PartB2Info pb2 = dto.getPartB2Info();
			PartB3Info pb3 = dto.getPartB3Info();
			PartB4Info pb4 = dto.getPartB4Info();
			PartB5B7B8Info pb5 = dto.getPartB5Info();
			PartB5B7B8Info pb7 = dto.getPartB7Info();
			errors = validateInputFieldObjectives(palist,errors, "Part B-1", false, false, false, true);
			errors = EaValidatorUtil.checkMandatory(pb2.getOverallRating(), errors, "partB2Info.overallRating", "Part B-2 - Overall Rating");
			errors = validateInputFieldBonusSalary(pb3.getBonusDimmed(), pb3.getSalaryDimmed(), pb3.getAoPerformanceBonus(), pb3.getAoSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-3");
			//errors = validateInputFieldCoreCompetency(pb4.getCcInfoList(), errors, "Part B-4");
			//errors = EaValidatorUtil.checkMandatory(pb4.getCounterPotentialB4(), errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");
			if(dto.getPartB4Info() == null){
				errors = EaValidatorUtil.checkMandatory(null, errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");	
			}else{
				try{
					if(dto.getPartB4Info().getCcInfoList().size()>0){
						errors = validateInputFieldCoreCompetency(pb4.getCcInfoList(), errors, "Part B-4");
		        	}
				}catch(Exception e){
					log.info("cc info list is null.");
				}
	        	if(StringUtil.isEmptyString(dto.getPartB4Info().getCounterPotentialB4())){
	        		errors = EaValidatorUtil.checkMandatory(pb4.getCounterPotentialB4(), errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");
	        	}
			}
			
			errors = EaValidatorUtil.checkMandatory(pb5.getPotential(), errors, "partB5Info.potential", "Part B-5 - Potential Assessment");
			errors = validateInputFieldBonusSalary(pb5.getBonusDimmed(), pb5.getSalaryDimmed(), pb5.getPerformanceBonus(), pb5.getSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-5");
			errors = EaValidatorUtil.checkMandatory(pb7.getWorkWell(), errors, "partB7Info.workWell", "Part B-7 - How well you know the work of the appraisee");
			errors = EaValidatorUtil.checkMandatory(pb7.getOverallPerformance(), errors, "partB7Info.overallPerformance", "Part B-7 - Overall Performance");
			errors = EaValidatorUtil.checkMandatory(pb7.getPotential(), errors, "partB7Info.potential", "Part B-7 - Potential Assessment");
			errors = EaValidatorUtil.checkMandatory(siB7.getOfficerName(), errors, "signatureInfoB7.officerName", "Part B-7 - Signature Info - Endorsing Officer Name");
			errors = EaValidatorUtil.checkMandatory(siB7.getOfficerRank(), errors, "signatureInfoB7.officerRank", "Part B-7 - Signature Info - Endorsing Officer Rank/Post");
			errors = EaValidatorUtil.checkMandatory(siB7.getOfficerDate(), errors, "signatureInfoB7.officerDate", "Part B-7 - Signature Info - Endorsing Officer Sign Date");
			errors = validateInputFieldBonusSalary(pb7.getBonusDimmed(), pb7.getSalaryDimmed(), pb7.getPerformanceBonus(), pb7.getSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-7");
		}
		return errors;
	}
	private Errors validateRo(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		SignatureInfo siB8 = dto.getSignatureInfoB8();
		if(dto.getPartB7Info()==null){
			errors = EaValidatorUtil.checkMustBeValidDate(siB8.getOfficerDate(), errors, "signatureInfoB8.officerDate", "Part B-7 - Signature Info - Reviewing Officer Sign Date");
		}else{
			errors = EaValidatorUtil.checkMustBeValidDate(siB8.getOfficerDate(), errors, "signatureInfoB8.officerDate", "Part B-8 - Signature Info - Reviewing Officer Sign Date");
		}
		
		if (submitReport){
			List<PartAB1Info> palist = dto.getPartB1InfoList();
			SignatureInfo siA = dto.getSignatureInfoA();
			PartB2Info pb2 = dto.getPartB2Info();
			PartB3Info pb3 = dto.getPartB3Info();
			PartB4Info pb4 = dto.getPartB4Info();
			SignatureInfo siB4 = dto.getSignatureInfoB4();
			PartB5B7B8Info pb5 = dto.getPartB5Info();
			SignatureInfo siB5 = dto.getSignatureInfoB5();
			SignatureInfo siB6 = dto.getSignatureInfoB6();
			PartB5B7B8Info pb7 = dto.getPartB7Info();
			SignatureInfo siB7 = dto.getSignatureInfoB7();
			PartB5B7B8Info pb8 = dto.getPartB8Info();
			errors = validateInputFieldObjectives(palist,errors, "Part B-1", false, false, false, true);
			errors = EaValidatorUtil.checkMandatory(pb2.getOverallRating(), errors, "partB2Info.overallRating", "Part B-2 - Overall Rating");
			errors = validateInputFieldBonusSalary(pb3.getBonusDimmed(), pb3.getSalaryDimmed(), pb3.getAoPerformanceBonus(), pb3.getAoSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-3");
			//errors = validateInputFieldCoreCompetency(pb4.getCcInfoList(), errors, "Part B-4");
			//errors = EaValidatorUtil.checkMandatory(pb4.getCounterPotentialB4(), errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");
			if(dto.getPartB4Info() == null){
				errors = EaValidatorUtil.checkMandatory(null, errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");	
			}else{
				try{
					if(dto.getPartB4Info().getCcInfoList().size()>0){
						errors = validateInputFieldCoreCompetency(pb4.getCcInfoList(), errors, "Part B-4");
		        	}
				}catch(Exception e){
					log.info("cc info list is null.");
				}
	        	if(StringUtil.isEmptyString(dto.getPartB4Info().getCounterPotentialB4())){
	        		errors = EaValidatorUtil.checkMandatory(pb4.getCounterPotentialB4(), errors, "partB4Info.counterPotentialB4", "Part B-4 - Potential Assessment");
	        	}
			}
			
			errors = EaValidatorUtil.checkMandatory(pb5.getPotential(), errors, "partB5Info.potential", "Part B-5 - Potential Assessment");
			errors = validateInputFieldBonusSalary(pb5.getBonusDimmed(), pb5.getSalaryDimmed(), pb5.getPerformanceBonus(), pb5.getSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-5");
			if(dto.getSignatureInfoB7()!=null){
				if (!Util.isEmptyString(siB7.getOfficerDate())){
					errors = EaValidatorUtil.checkMandatory(pb7.getPotential(), errors, "partB7Info.potential", "Part B-7 - Potential Assessment");
					errors = validateInputFieldBonusSalary(pb7.getBonusDimmed(), pb7.getSalaryDimmed(), pb7.getPerformanceBonus(), pb7.getSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-7");
				}
				errors = EaValidatorUtil.checkMandatory(pb8.getReviewComment(), errors, "partB8Info.reviewComment", "Part B-8 - Review comment of the appraisee");
				errors = EaValidatorUtil.checkMandatory(pb8.getPotential(), errors, "partB8Info.potential", "Part B-8 - Potential Assessment");
				errors = EaValidatorUtil.checkMandatory(siB8.getOfficerRank(), errors, "signatureInfoB8.officerRank", "Part B-8 - Signature Info - Reviewing Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siB8.getOfficerDate(), errors, "signatureInfoB8.officerDate", "Part B-8 - Signature Info - Reviewing Officer Sign Date");
				errors = validateInputFieldBonusSalary(pb8.getBonusDimmed(), pb8.getSalaryDimmed(), pb8.getPerformanceBonus(), pb8.getSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-8");
				Date ioB6Date = Util.string2Date(siB6.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
				Date roB8Date = Util.string2Date(siB8.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
				
				errors = EaValidatorUtil.checkMandatory(siA.getAppraiseeSignDate(), errors, "signatureInfoA.appraiseeSignDate", "Part A - Signature Info - Appraisee Sign Date");
				errors = EaValidatorUtil.checkMandatory(siA.getOfficerRank(), errors, "signatureInfoA.officerRank", "Part A - Signature Info - Appraising Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siA.getOfficerDate(), errors, "signatureInfoA.officerDate", "Part A - Signature Info - Appraising Officer Sign Date");
				errors = EaValidatorUtil.checkMandatory(siB4.getOfficerRank(), errors, "signatureInfoB4.officerRank", "Part B-4 - Signature Info - Appraising Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siB4.getOfficerDate(), errors, "signatureInfoB4.officerDate", "Part B-4 - Signature Info - Appraising Officer Sign Date");
				errors = EaValidatorUtil.checkMandatory(siB5.getOfficerRank(), errors, "signatureInfoB5.officerRank", "Part B-5 - Signature Info - Countersigning Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siB5.getOfficerDate(), errors, "signatureInfoB5.officerDate", "Part B-5 - Signature Info - Appraising Officer Sign Date");
				errors = EaValidatorUtil.checkMandatory(siB6.getAppraiseeRankPost(), errors, "signatureInfoB6.appraiseeRankPost", "Part B-6 - Signature Info - Appraisee Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siB6.getAppraiseeSignDate(), errors, "signatureInfoB6.appraiseeSignDate", "Part B-6 - Signature Info - Appraisee Sign Date");
				errors = EaValidatorUtil.checkMandatory(siB6.getOfficerRank(), errors, "signatureInfoB6.officerRank", "Part B-6 - Signature Info - Interviewing Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siB6.getOfficerDate(), errors, "signatureInfoB6.officerDate", "Part B-6 - Signature Info - Interviewing Officer Sign Date");
				
				errors = EaValidatorUtil.checkMustBeLater(ioB6Date, roB8Date, errors, "signatureInfoB8.officerDate", "Part B-6 - Interviewing Officer Sign Date", "Part B-8 - Reviewing Completion Date");
			}else{
				errors = EaValidatorUtil.checkMandatory(pb8.getReviewComment(), errors, "partB8Info.reviewComment", "Part B-7 - Review comment of the appraisee");
				errors = EaValidatorUtil.checkMandatory(pb8.getPotential(), errors, "partB8Info.potential", "Part B-7 - Potential Assessment");
				errors = EaValidatorUtil.checkMandatory(siB8.getOfficerRank(), errors, "signatureInfoB8.officerRank", "Part B-7 - Signature Info - Reviewing Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siB8.getOfficerDate(), errors, "signatureInfoB8.officerDate", "Part B-7 - Signature Info - Reviewing Officer Sign Date");
				
				errors = EaValidatorUtil.checkMandatory(siA.getAppraiseeSignDate(), errors, "signatureInfoA.appraiseeSignDate", "Part A - Signature Info - Appraisee Sign Date");
				errors = EaValidatorUtil.checkMandatory(siA.getOfficerRank(), errors, "signatureInfoA.officerRank", "Part A - Signature Info - Appraising Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siA.getOfficerDate(), errors, "signatureInfoA.officerDate", "Part A - Signature Info - Appraising Officer Sign Date");
				errors = EaValidatorUtil.checkMandatory(siB4.getOfficerRank(), errors, "signatureInfoB4.officerRank", "Part B-4 - Signature Info - Appraising Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siB4.getOfficerDate(), errors, "signatureInfoB4.officerDate", "Part B-4 - Signature Info - Appraising Officer Sign Date");
				errors = EaValidatorUtil.checkMandatory(siB5.getOfficerRank(), errors, "signatureInfoB5.officerRank", "Part B-5 - Signature Info - Countersigning Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siB5.getOfficerDate(), errors, "signatureInfoB5.officerDate", "Part B-5 - Signature Info - Appraising Officer Sign Date");
				errors = EaValidatorUtil.checkMandatory(siB6.getAppraiseeRankPost(), errors, "signatureInfoB6.appraiseeRankPost", "Part B-6 - Signature Info - Appraisee Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siB6.getAppraiseeSignDate(), errors, "signatureInfoB6.appraiseeSignDate", "Part B-6 - Signature Info - Appraisee Sign Date");
				errors = EaValidatorUtil.checkMandatory(siB6.getOfficerRank(), errors, "signatureInfoB6.officerRank", "Part B-6 - Signature Info - Interviewing Officer Rank/Post");
				errors = EaValidatorUtil.checkMandatory(siB6.getOfficerDate(), errors, "signatureInfoB6.officerDate", "Part B-6 - Signature Info - Interviewing Officer Sign Date");
				
				errors = validateInputFieldBonusSalary(pb8.getBonusDimmed(), pb8.getSalaryDimmed(), pb8.getPerformanceBonus(), pb8.getSalaryAdjustment(), pb2.getOverallRating(), errors, "Part B-7");
				Date ioB6Date = Util.string2Date(siB6.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
				Date roB8Date = Util.string2Date(siB8.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
				errors = EaValidatorUtil.checkMustBeLater(ioB6Date, roB8Date, errors, "signatureInfoB8.officerDate", "Part B-6 - Interviewing Officer Sign Date", "Part B-7 - Reviewing Completion Date");
				
			}

		}
		return errors;
	}
	private Errors validateGm(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		if (submitReport){
			List<PartAB1Info> palist = dto.getPartB1InfoList();
			PartB2Info pb2 = dto.getPartB2Info();
			PartB4Info pb4 = dto.getPartB4Info();
			errors = validateInputFieldObjectives(palist,errors, "Part B-1", false, false, false, true);
			errors = EaValidatorUtil.checkMandatory(pb2.getOverallRating(), errors, "partB2Info.overallRating", "Part B-2 - Overall Rating");
			errors = validateInputFieldCoreCompetency(pb4.getCcInfoList(), errors, "Part B-4");
		}
		return errors;
	}
	private Errors validateSugm(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		AppraiseeInfo ai = dto.getAppraiseeInfo();
		SignatureInfo siA = dto.getSignatureInfoA();
		SignatureInfo siB6 = dto.getSignatureInfoB6();
		errors = EaValidatorUtil.checkMandatory(ai.getAppraisalPeriodStart(), errors, "appraiseeInfo.appraisalPeriodStart", "Appraisal Period Start");
		errors = EaValidatorUtil.checkMandatory(ai.getAppraisalPeriodEnd(), errors, "appraiseeInfo.appraisalPeriodEnd", "Appraisal Period To");
		Date fromDate = Util.string2Date(ai.getAppraisalPeriodStart(),  Constants.DISPLAY_DATE_FORMAT);
		Date toDate = Util.string2Date(ai.getAppraisalPeriodEnd(),  Constants.DISPLAY_DATE_FORMAT);
		errors = EaValidatorUtil.checkMustBeLater(fromDate, toDate, errors, "appraiseeInfo.appraisalPeriodEnd", "Appraisal Period Start", "Appraisal Period To");
		//edited on 20171205 validation of weighting range
		//if (!StringUtil.isEmptyString(siA.getAppraiseeSignDate())){
			List<PartAB1Info> palist = dto.getPartAInfoList();
			List<PartAB1Info> pb1list = dto.getPartB1InfoList();
			errors = validateInputFieldObjectivesLite(palist, errors, "Part A");
			errors = validateInputFieldObjectivesLite(pb1list, errors, "Part B-1");
		//}
		//end edited on 20171205 validation of weighting range
		//added on 20180123 check ap and io same signature date
			Date apB6Date = Util.string2Date(siB6.getAppraiseeSignDate(),  Constants.DISPLAY_DATE_FORMAT);
			Date ioB6Date = Util.string2Date(siB6.getOfficerDate(),  Constants.DISPLAY_DATE_FORMAT);
			errors = EaValidatorUtil.checkMustBeSameDate(apB6Date, ioB6Date, errors, "signatureInfoB6.officerDate", "Part B-6 - The Completion Date of Interviewing Officer", "The Completion Date of Appraisee");
		//end added on 20180123	check ap and io same signature date
		return errors;
	}
	private Errors validateGmSugm(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		if (submitReport){
			PrintSubmitInfo psi = dto.getPrintSubmitInfo();
			ReportUserRole role = dto.getReportUserRole();
			String confirmReject = psi.getConfirmReject();
			String routing = psi.getRouting();
			String routintTo = psi.getRoutingTo();
			EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
			EaReportRole err = appraiseeCommonSearchDAO.searchNextOfficerByStatus(dto.getReportId(), er.getStatus(), dto.getPrintSubmitInfo().getRoutingTo());

			
			if(Constants.STATUS_CD.equalsIgnoreCase(dto.getPrintSubmitInfo().getRoutingTo()) && StringUtil.isEmptyString(err.getOfficerId()) && !"GM".equalsIgnoreCase(dto.getPrintSubmitInfo().getRoutingTo())){
				errors.rejectValue("printSubmitInfo.routingTo","error.er0081","Coordinator not exist - You have not assigned CD officer of this report.");
			}
			if(role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_SUPERUSER)){
				errors = EaValidatorUtil.checkMandatory(psi.getRouting(), errors, "printSubmitInfo.routing", "Print/Send - Routing");
			}
			if (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_GM)){
				errors = EaValidatorUtil.checkMandatory(psi.getConfirmReject(), errors, "printSubmitInfo.confirmReject", "Print/Send - Confirm/Reject");
				if ((!Util.isEmptyString(confirmReject)) && (!Util.isEmptyString(routing))) {
					if (confirmReject.equalsIgnoreCase("R") && !routing.equalsIgnoreCase("AR")) {
						
						errors.rejectValue("printSubmitInfo.confirmReject", "error.er0079", "Print/Send - Confirm/Reject Report - If the 'Reject' option is selected, the Routing option should be selected 'To any recipients'.");
					}
				}
			}
			if (!Util.isEmptyString(routing)){
				if (routing.equalsIgnoreCase("AR") && Util.isEmptyString(routintTo) && !Util.isEmptyString(confirmReject)) {
					if(confirmReject.equalsIgnoreCase("R")){
						errors = EaValidatorUtil.checkMandatory(routintTo, errors, "printSubmitInfo.routingTo", "Print/Send - Confirm/Reject Report - If the 'To any recipients' option is selected, the Routing Role");	
					}
				}
			}
		}
		return errors;
	}
	private Errors validateICdAoApCoIoEo(AssessAppraisalDTO dto,Errors errors, boolean submitReport){
		if (submitReport){
			PrintSubmitInfo psi = dto.getPrintSubmitInfo();
			String routing = psi.getRouting();
			//20220630 Prevent empty next officer ID
			String nextOfficerId = psi.getNextOfficerId().trim();
			String nextOfficeName = psi.getNextOfficerName();					

//			log.info("nextOfficerId " + nextOfficerId);
//			log.info("nextOfficeName " + nextOfficeName);
			//20220630 End - Prevent empty next officer ID
			
			errors = EaValidatorUtil.checkMandatory(psi.getRouting(), errors, "printSubmitInfo.routing", "Print/Send - Routing");
			if (!Util.isEmptyString(routing)){
				if (routing.equalsIgnoreCase("NO")) {
					//20220630 Prevent empty next officer ID
//					log.info("checking nextofficerid mandatory");
					//errors = EaValidatorUtil.checkMandatory(nextOfficeName, errors, "PrintSubmitInfo.nextOfficerName", "Print/Send - Routing - Next Processing Officer");
					errors = EaValidatorUtil.checkMandatory(nextOfficerId, errors, "PrintSubmitInfo.nextOfficerId", "Print/Send - Routing - Next Processing Officer");
//					log.info("checking nextofficerid mandatory1");
					//20220630 End - Prevent empty next officer ID
					ReportUserRole role = dto.getReportUserRole();
					//20220630 Prevent empty next officer ID
//					log.info("role.getCurrentOfficerId()" + role.getCurrentOfficerId());
//					log.info("role.getNextOfficerId()" + role.getNextOfficerId());
					//if role.getCurrentOfficerId() not null  
					String roleNextOfficerId = Objects.toString(role.getNextOfficerId(), "");
					//20220630 End - Prevent empty next officer ID
					if (role.getCurrentUserRoleStatus().equalsIgnoreCase(Constants.STATUS_AO)){
						//20220630 Prevent empty next officer ID
						if (roleNextOfficerId.equalsIgnoreCase(role.getCurrentOfficerId())){
							if (nextOfficerId.equalsIgnoreCase(role.getCurrentOfficerId())) {
								log.info("nextOfficeName " + nextOfficeName);
								errors.rejectValue("printSubmitInfo.nextOfficerName", "error.er0080", "Print/Send - Routing - Appraising Officer and Countersigning Officer cannot be same.");
							}							
						}
						//20220630 End - Prevent empty next officer ID
					}
				}
			}
		}
		return errors;
	}
	//edited on 20170424 size limit of Part A and Part B1
	private Errors validateInputFieldObjectives(List<PartAB1Info> objectiveList, Errors errors, String sectionTitle, boolean checkKeyObjective, boolean checkResultsAchieved, boolean checkWeighting, boolean checkRating){
		boolean isNeededToFillAny = true;
		boolean mandatoryFail = false;
		boolean objectiveTooLongFail = false;
		boolean resultTooLongFail = false;
		boolean numericWeightFail = false;
		boolean ratingFail = false;
		int sumOfWeighting = (checkWeighting)? 0 : 100;	// Finally, much equal to 100
		int filledRow = 0;
		if ((objectiveList != null) && (objectiveList.size() > 0)) {
			for(PartAB1Info objective:objectiveList){
				boolean filledAny = false;
				filledAny |= !Util.isEmptyString(objective.getKeyObjectives());
				filledAny |= !Util.isEmptyString(objective.getResultAchieved());
				filledAny |= (objective.getWeighting() != null);
				filledRow += filledAny?1:0;
				if (!filledAny){
					continue;
				}
				if (checkKeyObjective || checkResultsAchieved || checkWeighting){
					boolean filledAllMandatory = true;
					if (objective.getWeighting() != null){
						filledAllMandatory &= !Util.isEmptyString(objective.getKeyObjectives());
						filledAllMandatory &= !Util.isEmptyString(objective.getResultAchieved());
					}
					if (filledAny && !filledAllMandatory) {
						mandatoryFail |= true;
					}
					//if ((objective.getKeyObjectives() != null) && (objective.getKeyObjectives().getBytes().length > 294)){
					if ((objective.getKeyObjectives() != null) && (objective.getKeyObjectives().getBytes().length > 440)){
						objectiveTooLongFail |= true;
					}
					//if ((objective.getResultAchieved() != null) && (objective.getResultAchieved().getBytes().length > 980)){
					if ((objective.getResultAchieved() != null) && (objective.getResultAchieved().getBytes().length > 1500)){
						resultTooLongFail |= true;
					}
					if (checkWeighting){
						if (objective.getWeighting() != null){
							if ((objective.getWeighting() < 0) || (objective.getWeighting() > 100)){
								numericWeightFail |= true;
							}
							sumOfWeighting += objective.getWeighting();
						}
					}
				}
				if (checkRating){
					if ((objective.getWeighting() != null) && (Util.isEmptyString(objective.getRating()))){
						ratingFail |= true;
					}
				}
			}
		}
		if (mandatoryFail){
			errors.rejectValue(null, "error.message.empty.content", new String[]{sectionTitle + " - Key Objective, Results to be Achieved, and Weighting"}, sectionTitle + " - Key Objective, Results to be Achieved, and Weighting are required.");
		}
		if (objectiveTooLongFail){
			errors.rejectValue(null, "error.message.length.toolong", new String[]{sectionTitle + " - Key Objective"}, sectionTitle + " - Key Objective is too long.");
		}
		if (resultTooLongFail) {
			if (sectionTitle.equalsIgnoreCase("Part A")){
				errors.rejectValue(null, "error.message.length.toolong", new String[]{sectionTitle + " - Results to be Achieved"}, sectionTitle + " - Results to be Achieved is too long.");
			} else {
				errors.rejectValue(null, "error.message.length.toolong", new String[]{sectionTitle + " - Results Achieved"}, sectionTitle + " - Results Achieved is too long.");
			}
		}
		if (numericWeightFail) {
			errors.rejectValue(null, "error.er0075", new String[]{sectionTitle + " - Weighting"}, sectionTitle + " - Weighting must be between 0 and 100.");
		}
		if (isNeededToFillAny && filledRow < 1) {
			errors.rejectValue(null, "error.message.empty.content", new String[]{sectionTitle + " - Key Objective"}, sectionTitle + " - Key Objective.");
		}else if (sumOfWeighting != 100 && filledRow > 0 && !numericWeightFail) {
			errors.rejectValue(null, "error.er0075a", new String[]{sectionTitle + " - Weighting"}, "Sum of " + sectionTitle + " - Weighting must be equal to 100.");
		}
		if (ratingFail) {
			errors.rejectValue(null, "error.message.empty.content", new String[]{sectionTitle + " - Rating"}, sectionTitle + " - Rating is required.");
		}
		return errors;
	}
	//end edited on 20170424
	//edited on 20170424 size limit of Part A and Part B1
	private Errors validateInputFieldObjectivesLite(List<PartAB1Info> objectiveList, Errors errors, String sectionTitle){
		boolean objectiveTooLongFail = false;
		boolean resultTooLongFail = false;
		boolean numericWeightFail = false;
		if ((objectiveList != null) && (objectiveList.size() > 0)) {
			for(PartAB1Info objective:objectiveList){
				if (objective.getWeighting() != null){
					if ((objective.getWeighting() < 0) || (objective.getWeighting() > 100)){
						numericWeightFail |= true;
					}
				}
				//if ((objective.getKeyObjectives() != null) && (objective.getKeyObjectives().getBytes().length > 294)){
				if ((objective.getKeyObjectives() != null) && (objective.getKeyObjectives().getBytes().length > 440)){	
					objectiveTooLongFail |= true;
				}
				//if ((objective.getResultAchieved() != null) && (objective.getResultAchieved().getBytes().length > 980)){
				if ((objective.getResultAchieved() != null) && (objective.getResultAchieved().getBytes().length > 1500)){
					resultTooLongFail |= true;
				}
			}
		}
		if (objectiveTooLongFail){
			errors.rejectValue(null, "error.message.length.toolong", new String[]{sectionTitle + " - Key Objective"}, sectionTitle + " - Key Objective is too long.");
		}
		if (resultTooLongFail) {
			if (sectionTitle.equalsIgnoreCase("Part A")){
				errors.rejectValue(null, "error.message.length.toolong", new String[]{sectionTitle + " - Results to be Achieved"}, sectionTitle + " - Results to be Achieved is too long.");
			} else {
				errors.rejectValue(null, "error.message.length.toolong", new String[]{sectionTitle + " - Results Achieved"}, sectionTitle + " - Results Achieved is too long.");
			}
		}
		if (numericWeightFail) {
			errors.rejectValue(null, "error.er0075", new String[]{sectionTitle + " - Weighting"}, sectionTitle + " - Weighting must be between 0 and 100.");
		}
		return errors;
	}
	//end edited on 20170424
	private Errors validateInputFieldCoreCompetency(List<CoreCompetencyInfo> competencyList, Errors errors, String sectionTitle){
		boolean mandatoryFail = false;
		String key = "";
		if (sectionTitle.equalsIgnoreCase("Part B-4")){
			key = "partB4Info.counterPotentialB4";
		}
		if ((competencyList != null) && (competencyList.size() > 0)){
			for (CoreCompetencyInfo cci:competencyList){
				if (StringUtil.isEmptyString(cci.getRating())){
					mandatoryFail |= true;
				}
			}
		}
		if (mandatoryFail){
			errors.rejectValue(key, "error.message.empty.content", new String[]{sectionTitle + " - Core Competencies Rating"}, sectionTitle + " - Core Competencies Rating is required.");
		}
		return errors;
	}
	private Errors validateInputFieldBonusSalary(String bonusDimmed, String salaryDimmed, String bonus, String salaryAdjust, String overallRating, Errors errors, String sectionTitle){
		String keyBonus = "";
		String keySalary = "";
		if (sectionTitle.equalsIgnoreCase("Part B-3")){
			keyBonus = "partB3Info.aoPerformanceBonus";
			keySalary = "partB3Info.aoSalaryAdjustment";
		} else if (sectionTitle.equalsIgnoreCase("Part B-5")) {
			keyBonus = "partB5Info.performanceBonus";
			keySalary = "partB5Info.salaryAdjustment";
		} else if (sectionTitle.equalsIgnoreCase("Part B-7")) {
			keyBonus = "partB7Info.performanceBonus";
			keySalary = "partB7Info.salaryAdjustment";
		} else if (sectionTitle.equalsIgnoreCase("Part B-8")) {
			keyBonus = "partB8Info.performanceBonus";
			keySalary = "partB8Info.salaryAdjustment";
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
