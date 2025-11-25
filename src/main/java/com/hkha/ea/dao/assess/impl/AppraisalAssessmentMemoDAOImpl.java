package com.hkha.ea.dao.assess.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import jakarta.servlet.http.HttpServletRequest;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Message;
import com.hkha.ea.common.StringUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.assess.AppraisalAssessmentDAO;
import com.hkha.ea.dao.assess.AppraisalAssessmentMemoDAO;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.common.EmployeeEnquiryDAO;
import com.hkha.ea.dao.common.NotificationManagerDAO;
import com.hkha.ea.dao.security.UserEnquiryDAO;
import com.hkha.ea.domain.DummyReportInfo;
import com.hkha.ea.domain.EaMemoDetails;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.assess.AssessAppraisalMemoDTO;
import com.hkha.ea.dto.assess.MemoInfo;
import com.hkha.ea.dto.assess.MemoPart1Info;
import com.hkha.ea.dto.assess.MemoPart2Part5Info;
import com.hkha.ea.dto.assess.MemoPart3Part4Info;
import com.hkha.ea.dto.assess.PrintSubmitInfo;
import com.hkha.ea.dto.assess.SignatureInfo;
import com.hkha.ea.dto.assess.SubmitActionInfo;
import com.hkha.ea.dto.common.EmployeeEnquiryDTO;
import com.hkha.ea.dto.common.ReportUserRole;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;
import com.hkha.ea.service.assess.AssignOfficerService;

@Repository("appraisalAssessmentMemoDAO")
public class AppraisalAssessmentMemoDAOImpl  implements AppraisalAssessmentMemoDAO{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AppraisalAssessmentMemoDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private AppraisalAssessmentDAO appraisalAssessmentDAO;

	@Autowired
	private UserEnquiryDAO userEnquiryDAO;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	@Autowired
	private AssignOfficerService assignOfficerService;
	
	/**
	 * 
	 * Search Memo Report Methods
	 * 
	 * **/
	public AssessAppraisalMemoDTO searchAppraisalAssessmentMemoById(long id , HttpServletRequest request){
		AssessAppraisalMemoDTO dto=new AssessAppraisalMemoDTO();
		EaMemoDetails emd=appraiseeCommonSearchDAO.findEaMemoDetailsById(id);
		EaReport er=appraiseeCommonSearchDAO.findEareportById(id);
		String currentUsername=SecurityContextHolder.getContext().getAuthentication().getName();
		List<EaReportRole>errlist=appraiseeCommonSearchDAO.searchReportRoleDetailByReportId(id);
		//EaContract ec=appraiseeCommonSearchDAO.findEaContractById(id);
		dto.setReportId(id);
		ReportUserRole rur=appraisalAssessmentDAO.getReportUserRoleByReportIdAndUserId(id,currentUsername);
		dto.setReportUserRole(rur);
		dto=convertToAssessAppraisalMemoDTOByMI(dto,emd);
		dto=convertToAssessAppraisalMemoDTOByPart1Info(dto,er);
//		ReportUserRole rur=appraisalAssessmentDAO.getReportUserRoleByReportIdAndUserId(id,currentUsername);
//		dto.setReportUserRole(rur);
		dto=convertToAssessAppraisalMemoDTOByPrintSubmit(dto,er,errlist, request);
		return dto;
	}
	

	
	/**
	 * 	Convert Memo Report Methods
	 */
	private AssessAppraisalMemoDTO convertToAssessAppraisalMemoDTOByMI(AssessAppraisalMemoDTO dto,EaMemoDetails emd){
		MemoInfo mi=new MemoInfo();		
		//for part2 Info 
		MemoPart2Part5Info mp2= new MemoPart2Part5Info();
		
		//for part3 Info 
		MemoPart3Part4Info mp3= new MemoPart3Part4Info();
		
		//for part4 Info 
		MemoPart3Part4Info mp4= new MemoPart3Part4Info();
		
		//for part5 Info 
		MemoPart2Part5Info mp5= new MemoPart2Part5Info();
		
		//for signature Info Part3		
		SignatureInfo smp3=new SignatureInfo();
		
		//for signature Info Part4	
		SignatureInfo smp4=new SignatureInfo();
		
		//for signature Info Part5	
		SignatureInfo smp5=new SignatureInfo();
		
		
		
		if(null!=emd){					
			//Memo Info			
			mi.setMemoFrom(emd.getMemoFrom());
			mi.setMemoTo(emd.getMemoTo());
			mi.setRef1(emd.getRef1());
			mi.setRef2(emd.getRef2());
			mi.setTelNo(emd.getTelNo());
			mi.setFaxNo(emd.getFaxNo());
			mi.setMemoFromDate(emd.getMemoFromDate());
			mi.setMemoToDate(emd.getMemoToDate());
			mi.setTotalPages(emd.getTotalPages());
			mi.setYourRef1(emd.getYourRef1());
			mi.setYourRef2(emd.getYourRef2());	
			mi.setVia(emd.getVia());
			//for part2 Info 
			mp2.setDutyDesc(emd.getDutyDesc());
			
			//for part3 Info 
			mp3.setOverallComment(emd.getOverallComment());
			mp3.setOverallPerf(emd.getOverallPerf());
			mp3.setPerfBonus(emd.getPerfBonusAo());
			//mp3.setSalaryAdj(emd.getSalaryADJAo());
			mp3.setSalaryAdj(emd.getSalaryAdjAo());
			
			//for part4 Info 
			mp4.setPerfBonus(emd.getPerfBonusCo());
			//mp4.setSalaryAdj(emd.getSalaryADJCo());
			mp4.setSalaryAdj(emd.getSalaryAdjCo());
			
			//for part5 Info 
			mp5.setAppInterview(emd.getAppInterview());
				
			//for signature Info Part3			
			smp3.setNameOfAppraisee(emd.getAppName());
			smp3.setOfficerName(emd.getAoName());
			smp3.setOfficerRank(emd.getAoRank());
			smp3.setOfficerDate(emd.getAppDate());
			
			//for signature Info Part4				
			smp4.setNameOfAppraisee(emd.getAppName());
			smp4.setOfficerName(emd.getCoName());
			smp4.setOfficerRank(emd.getCoRank());
			smp4.setOfficerDate(Util.date2String(emd.getCoDate(),Constants.DISPLAY_DATE_FORMAT));
			
			//for signature Info Part5				
			smp5.setNameOfAppraisee(emd.getAppName());
			smp5.setOfficerName(emd.getIoName());
			smp5.setOfficerRank(emd.getIoRank());
			smp5.setOfficerDate(Util.date2String(emd.getIoDate(),Constants.DISPLAY_DATE_FORMAT));
		}
		
		//appraisee is allow bonus and salary
		try{	
			boolean[] allowBSD = appraisalAssessmentDAO.allowBonusSalary(dto.getReportId());
			boolean isAllowBonus = allowBSD[0];//is allow bonus
			boolean isDimmedBonus = (allowBSD[2]==true || (!isAllowBonus));
			boolean isDimmedSalary = !allowBSD[1];
			
			/*20170221
			EaReport er = assignOfficerService.searchByReportID(String.valueOf(emd.getReportId()));
			Date dateOfAppFirstRank = er.getDateOfAppFirstRank();
			Date dateOfSatisfyBnous = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			StringBuffer satisfyBnousDate = new StringBuffer();
			satisfyBnousDate.append("01/07/2013");
			
			dateOfAppFirstRank = DateTimeUtil.string2Date(sdf.format(dateOfAppFirstRank));
			dateOfSatisfyBnous = DateTimeUtil.string2Date(satisfyBnousDate.toString());
			*/
			
			if(!isDimmedBonus){
				mp3.setBonusDimmed("Y");
				mp4.setBonusDimmed("Y");
			}else{
				mp3.setBonusDimmed("N");
				mp4.setBonusDimmed("N");
			}
			//is allow salary
			if(!isDimmedSalary){
				mp3.setSalaryDimmed("Y");
				mp4.setSalaryDimmed("Y");
			}else{
				mp3.setSalaryDimmed("N");
				mp4.setSalaryDimmed("N");
			}
			
			/*20170221
			if(dateOfAppFirstRank.compareTo(dateOfSatisfyBnous)>0){
				mp3.setBonusDimmed("N");
				mp4.setBonusDimmed("N");
			}
			*/
			
			if("IO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				//set bonus is dimmed
				mp3.setBonusDimmed("N");
				mp4.setBonusDimmed("N");
				//set salary is dimmed
				mp3.setSalaryDimmed("N");
				mp4.setSalaryDimmed("N");
			}

			
			
		}catch(Exception e){
			e.printStackTrace();
			log.severe(e.toString());
		}

		 
		dto.setMemoInfo(mi);
		dto.setMemoPart2Info(mp2);
		dto.setMemoPart3Info(mp3);
		dto.setMemoPart4Info(mp4);
		dto.setMemoPart5Info(mp5);
		dto.setSignatureInfoMemoP3(smp3);
		dto.setSignatureInfoMemoP4(smp4);
		dto.setSignatureInfoMemoP5(smp5);
		return dto;
	}
	
	private AssessAppraisalMemoDTO convertToAssessAppraisalMemoDTOByPart1Info(AssessAppraisalMemoDTO dto,EaReport er){
		MemoPart1Info mp1=new MemoPart1Info();		
		if(null!=er){					
			//Memo Part Info			
			mp1.setEnglishName(er.getName());
			mp1.setChineseName(er.getChineseName());
			mp1.setRankOnPresentAppoint(er.getSubstantiveRank());
			mp1.setPresentPost(er.getPresentPost());
			mp1.setDivisionSection(er.getDivision());
			mp1.setAppraisalPeriodEnd(Util.date2String(er.getEndDate(),Constants.DISPLAY_DATE_FORMAT));
			mp1.setAppraisalPeriodStart(Util.date2String(er.getCommenceDate(),Constants.DISPLAY_DATE_FORMAT));
		}
		
		dto.setMemoPart1Info(mp1);
		return dto;
	}
	
	private AssessAppraisalMemoDTO convertToAssessAppraisalMemoDTOByPrintSubmit(AssessAppraisalMemoDTO dto,EaReport er,List<EaReportRole> errlist, HttpServletRequest request){
		PrintSubmitInfo mps=new PrintSubmitInfo();		
		String userRole=dto.getReportUserRole().getCurrentUserRoleStatus();
		String rptRole=dto.getReportUserRole().getCurrentReportStatus();
		List<SystemParameterDTO> routingToList=new ArrayList<SystemParameterDTO>();
		
		
		if(null!=er){					
			//Memo Part Info			
			mps.setRoutingReason(er.getRoutingReason());
		}
		//set routingTo select box info 
		//if(("GM".equalsIgnoreCase(userRole)||"SU".equalsIgnoreCase(userRole)||"GM".equalsIgnoreCase(rptRole))){
		SystemParameterDTO spd=new SystemParameterDTO();
		spd.setParamName("AP");
		spd.setParamValue("AP");
		spd.setParamDesc("AP");
		routingToList.add(spd);
		for(EaReportRole err:errlist){
			SystemParameterDTO spd2=new SystemParameterDTO();
			spd2.setParamName(err.getRole());
			spd2.setParamValue(err.getRole());
			spd2.setParamDesc(err.getRole());
			routingToList.add(spd2);
		}
		SystemParameterDTO spd3=new SystemParameterDTO();
		spd3.setParamName("GM");
		spd3.setParamValue("GM");
		spd3.setParamDesc("GM");
		routingToList.add(spd3);
		
		request.getSession().setAttribute(Constants.SESS_ROUTING_TO_LIST, routingToList);
			
		mps.setRoutingToList(routingToList);
		
		if ((!"GM".equalsIgnoreCase(rptRole)) && (!"I".equalsIgnoreCase(rptRole))) {
			mps.setRoutingTo(rptRole);
		}
	//	}
		//set routing radio box label and value
		List<SystemParameterDTO> routingLabelList=new ArrayList<SystemParameterDTO>();
		SystemParameterDTO sdNo=new SystemParameterDTO();
			sdNo.setParamName("NO");
			sdNo.setParamDesc("To next processing officer");
			sdNo.setParamValue("NO");
			
		SystemParameterDTO sdAp=new SystemParameterDTO();
			sdAp.setParamName("AP");
			sdAp.setParamDesc("To appraisee");
			sdAp.setParamValue("AP");
			
		SystemParameterDTO sdPo=new SystemParameterDTO();
			sdPo.setParamName("PO");
			sdPo.setParamDesc("To previous officer");
			sdPo.setParamValue("PO");
			
		SystemParameterDTO sdAr=new SystemParameterDTO();
			sdAr.setParamName("AR");
			sdAr.setParamDesc("To any recipients");
			sdAr.setParamValue("AR");
			
		if("AP".equalsIgnoreCase(userRole)){	
			routingLabelList.add(sdNo);
			mps.setRouting("NO");//on checked
		}else if("AO".equalsIgnoreCase(userRole)){			
			routingLabelList.add(sdAp);
			routingLabelList.add(sdNo);
			mps.setRouting("NO");
		}else if("CO".equalsIgnoreCase(userRole)||"EO".equals(userRole)||"IO".equals(userRole)||"RO".equals(userRole)){
			
			routingLabelList.add(sdPo);
			routingLabelList.add(sdNo);
			mps.setRouting("NO");
		}else if ("GM".equalsIgnoreCase(userRole)||"SU".equalsIgnoreCase(userRole)) {
			
			routingLabelList.add(sdAr);
			mps.setRouting("AR");//on checked
		 }
		
		
		mps.setRoutingLabelList(routingLabelList);
		//set GM officer 
		mps.setGmOfficerId(dto.getReportUserRole().getGmId());
		mps.setGmOfficerName("Grade Management" );
		mps.setGmDeadline(Util.date2String(er.getOverallDeadline()));
		//set next officer 
		
		mps.setDeadline(dto.getReportUserRole().getNextOfficerDeadline());
		mps.setNextOfficerId(dto.getReportUserRole().getNextOfficerId());
		mps.setNextOfficerName(dto.getReportUserRole().getNextOfficerName());
		mps.setNextOfficerRole(dto.getReportUserRole().getNextOfficerRole());

		dto.setMemoPrintSubmitInfo(mps);
		return dto;
	}
	
	/**
	 * Memo Save method
	 * 
	 * */
	
	public void saveMemoInfo(MemoInfo mi,Long rptId) throws Exception{
	
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		EaMemoDetails emd=appraiseeCommonSearchDAO.findEaMemoDetailsById(rptId);
		
		if(emd==null){
			emd=new EaMemoDetails();	
			emd.setCreatedBy(currentLoginUserId);
			emd.setCreationDate(new Date());
			emd.setReportId(rptId);
		}
		
		emd.setMemoFrom(mi.getMemoFrom());
		emd.setMemoTo(mi.getMemoTo());
		emd.setRef1(mi.getRef1());
		emd.setRef2(mi.getRef2());
		emd.setTelNo(mi.getTelNo());
		emd.setFaxNo(mi.getFaxNo());
		emd.setMemoFromDate(mi.getMemoFromDate());
		emd.setMemoToDate(mi.getMemoToDate());
		emd.setTotalPages(mi.getTotalPages());
		emd.setYourRef1(mi.getYourRef1());
		emd.setYourRef2(mi.getYourRef2());	
		emd.setLastUpdateDate(new Date());
		emd.setLastUpdatedBy(currentLoginUserId);
		emd.setVia(mi.getVia());
		hibernateTemplate.saveOrUpdate(emd);
		
		//hibernateTemplate.flush();
		//hibernateTemplate.clear();
	}
	
	public void saveMemoPart1Info(MemoPart1Info mp1,Long rptId ) throws Exception{
		//long rptId=dto.getReportId();
		EaReport er=appraiseeCommonSearchDAO.findEareportById(rptId);
		//MemoPart1Info mp1=dto.getMemoPart1Info();
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
			if(null!=er){			
				er.setPresentPost(mp1.getPresentPost());
				er.setDivision(mp1.getDivisionSection());
				er.setCommenceDate(Util.string2Date(mp1.getAppraisalPeriodStart(), Constants.DISPLAY_DATE_FORMAT));
				er.setEndDate(Util.string2Date(mp1.getAppraisalPeriodEnd(), Constants.DISPLAY_DATE_FORMAT));
				er.setLastUpdatedBy(currentLoginUserId);
				er.setLastUpdateDate(new Date());
				hibernateTemplate.update(er);
			}
			appraisalAssessmentDAO.updatePem001RCommenceDateAndEndDate(mp1.getAppraisalPeriodStart(),mp1.getAppraisalPeriodEnd(),currentLoginUserId,er.getEmployeeNumber());

			hibernateTemplate.flush();
			hibernateTemplate.clear();
	}
	
	public void saveMemoPart2Part5Info(MemoPart2Part5Info mp25,Long rptId,String page) throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		EaMemoDetails emd=appraiseeCommonSearchDAO.findEaMemoDetailsById(rptId);
		
		if(emd==null){
			emd=new EaMemoDetails();	
			emd.setCreatedBy(currentLoginUserId);
			emd.setCreationDate(new Date());
			emd.setReportId(rptId);
		}
		
		if("P2".equalsIgnoreCase(page)){
			emd.setDutyDesc(mp25.getDutyDesc());
			
		}else if("P5".equalsIgnoreCase(page)){
			emd.setAppInterview(mp25.getAppInterview());
		}
			
		emd.setLastUpdatedBy(currentLoginUserId);
		emd.setLastUpdateDate(new Date());
		hibernateTemplate.saveOrUpdate(emd);
		
		hibernateTemplate.flush();
		hibernateTemplate.clear();
			
	}
	
	
	public void saveMemoPart3Part4Info(MemoPart3Part4Info mp34,Long rptId,String page) throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		EaMemoDetails emd=appraiseeCommonSearchDAO.findEaMemoDetailsById(rptId);
		
		if(emd==null){
			emd=new EaMemoDetails();	
			emd.setCreatedBy(currentLoginUserId);
			emd.setCreationDate(new Date());
			emd.setReportId(rptId);
		}
		if("P3".equalsIgnoreCase(page)){
			emd.setOverallPerf(mp34.getOverallPerf());
			emd.setOverallComment(mp34.getOverallComment());
			emd.setPerfBonusAo(mp34.getPerfBonus());
			//emd.setSalaryADJAo(mp34.getSalaryAdj());
			emd.setSalaryAdjAo(mp34.getSalaryAdj());
			
		}else if("P4".equalsIgnoreCase(page)){
			emd.setPerfBonusCo(mp34.getPerfBonus());
			//emd.setSalaryADJCo(mp34.getSalaryAdj());
			emd.setSalaryAdjCo(mp34.getSalaryAdj());
		}
	
		emd.setLastUpdatedBy(currentLoginUserId);
		emd.setLastUpdateDate(new Date());
		hibernateTemplate.saveOrUpdate(emd);
		
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	
	public void saveMemoSignatureInfo(SignatureInfo sf,Long rptId,String page) throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		EaMemoDetails emd=appraiseeCommonSearchDAO.findEaMemoDetailsById(rptId);
		
		if(emd==null){
			emd=new EaMemoDetails();	
			emd.setCreatedBy(currentLoginUserId);
			emd.setCreationDate(new Date());
			emd.setReportId(rptId);
		}
		
		emd.setAppName(sf.getNameOfAppraisee());
		
		if("P3".equalsIgnoreCase(page)){
			//for signature Info Part3					
			emd.setAppDate(sf.getOfficerDate());
			emd.setAoName(sf.getOfficerName());
			emd.setAoRank(sf.getOfficerRank());
		}else if("P4".equalsIgnoreCase(page)){
			//for signature Info Part4				
			emd.setCoName(sf.getOfficerName());
			emd.setCoRank(sf.getOfficerRank());
			emd.setCoDate(Util.string2Date(sf.getOfficerDate(), Constants.DISPLAY_DATE_FORMAT));
		}else if("P5".equalsIgnoreCase(page)){
			//for signature Info Part5	
			emd.setIoName(sf.getOfficerName());
			emd.setIoRank(sf.getOfficerRank());
			emd.setIoDate(Util.string2Date(sf.getOfficerDate(), Constants.DISPLAY_DATE_FORMAT));
		}
			
		emd.setLastUpdatedBy(currentLoginUserId);
		emd.setLastUpdateDate(new Date());
		
		hibernateTemplate.saveOrUpdate(emd);
		
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	
	public void saveMemoPrintSubmitInfo(PrintSubmitInfo mps,Long rptId) throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		//EaMemoDetails emd=appraiseeCommonSearchDAO.findEaMemoDetailsById(rptId);
		EaReport er=appraiseeCommonSearchDAO.findEareportById(rptId);
		if(er==null){
			er=new EaReport();	
			er.setCreatedBy(currentLoginUserId);
			er.setCreationDate(new Date());
			er.setReportId(rptId);
		}	
		er.setRoutingReason(mps.getRoutingReason());
		er.setLastUpdatedBy(currentLoginUserId);
		er.setLastUpdateDate(new Date());
		hibernateTemplate.saveOrUpdate(er);
		
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		
//		EaReportRole err=appraiseeCommonSearchDAO.searchReportRoleByReportIdandRole(rptId,mps.getNextOfficerRole());
//		if(null!=err){
//			err.setLastUpdatedBy(currentLoginUserId);
//			err.setLastUpdatedDate(new Date());
//			err.setOfficerId(mps.getNextOfficerId());
//			err.setOfficerName(mps.getNextOfficerName());
//			err.setDeadline(Util.string2Date(mps.getDeadline(), Constants.DISPLAY_DATE_FORMAT));
//			hibernateTemplate.saveOrUpdate(err);
//		}	
			
	}
	
	/**
	 * 
	 * Submit Memo Report
	 * 
	 * */
	
	public SubmitActionInfo submitMemoByUpdateStatus(AssessAppraisalMemoDTO dto){
		//GM submit
		String submitAction = "sent";
		String submitToEmployeeNum = "";
		String submitNextDeadDate = "";
		String submitNotificationContent = "";
		String submitToAddress = "";
		String newStatus = "";
	//	boolean bypassToAnyRecipients = ("GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus()))&&();
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		EaMemoDetails emd=appraiseeCommonSearchDAO.findEaMemoDetailsById(dto.getReportId());
		List<UserEnquiryModelDTO> gmList=userEnquiryDAO.searchUserByReportIdAndFunctionId(dto.getReportId(), Constants.FUNCTION_ID_CONFIRM_REJECT_REPORT);
		
		//for GM 
		/*edited on 20170301 for GM Confirm/Reject
		if ("AR".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRouting()) && "GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())) {
			if("R".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getConfirmReject())){
				newStatus = dto.getMemoPrintSubmitInfo().getRoutingTo();
			 	submitAction = "returned";
			 	if ("AP".equalsIgnoreCase(newStatus)) {
			 		newStatus = "AP";
			 		submitToEmployeeNum = er.getEmployeeNumber();
			 	} else {
			 		EaReportRole err=appraiseeCommonSearchDAO.searchReportRoleByReportIdandRole(dto.getReportId(),newStatus);
			 		if (null!=err) {
			 			submitToEmployeeNum = err.getOfficerId();
			 			submitNextDeadDate = Util.date2String(err.getDeadline(),Constants.DISPLAY_DATE_FORMAT);
			 			submitNotificationContent = err.getNotification();
			 		}
			 	}
			}else{
				newStatus = "CL";
			}	
		}
		*/
		if ("GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentReportStatus()) && "GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())) {
			if("R".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getConfirmReject())){
				newStatus = "IO";
			 	submitAction = "returned";
			 	if ("AP".equalsIgnoreCase(newStatus)) {
			 		newStatus = "AP";
			 		submitToEmployeeNum = er.getEmployeeNumber();
			 	} else {
			 		EaReportRole err=appraiseeCommonSearchDAO.searchReportRoleByReportIdandRole(dto.getReportId(),newStatus);
			 		if (null!=err) {
			 			submitToEmployeeNum = err.getOfficerId();
			 			submitNextDeadDate = Util.date2String(err.getDeadline(),Constants.DISPLAY_DATE_FORMAT);
			 			submitNotificationContent = err.getNotification();
			 		}
			 	}
			}else{
				newStatus = "CL";
			}	
		}
		
		//for AO
		if ("AP".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRouting())) {
			newStatus = "AP";
			submitToEmployeeNum = er.getEmployeeNumber();
		}
		//for CO IO RO
		if("PO".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRouting())){
			if ("CL".equalsIgnoreCase(dto.getReportUserRole().getCurrentReportStatus())) {
				newStatus = "GM";
				submitAction = "returned";
				
				if(gmList.size()>0){
					submitToEmployeeNum = gmList.get(0).getUserId();
				 	submitToAddress = gmList.get(0).getEmailAddress();
				}
				
					
			}else if ("GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentReportStatus())) {
				newStatus = "IO";
				submitAction = "returned";
				EaReportRole err=appraiseeCommonSearchDAO.searchReportRoleByReportIdandRole(dto.getReportId(),newStatus);
		 		if (null!=err) {
		 			submitToEmployeeNum = err.getOfficerId();
		 			submitNextDeadDate = Util.date2String(err.getDeadline(),Constants.DISPLAY_DATE_FORMAT);
		 			submitNotificationContent = err.getNotification();
		 		}
			} else {
				submitAction = "returned";		
				EaReportRole err=appraiseeCommonSearchDAO.searchPreviousOfficerRoleByIdAndCurrentRptStatus(dto.getReportId(),dto.getReportUserRole().getCurrentReportStatus());
		 		if (null!=err) {
		 			newStatus=err.getRole();
		 			submitToEmployeeNum = err.getOfficerId();
		 			submitNextDeadDate = Util.date2String(err.getDeadline(),Constants.DISPLAY_DATE_FORMAT);
		 			submitNotificationContent = err.getNotification();
		 		}else if ("CD".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())) {
					newStatus = "I";
				}
			}
				
		}
		if("NO".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRouting())){
			EaReportRole err=appraiseeCommonSearchDAO.searchNextOfficerRoleByIdAndCurrentRptStatus(dto.getReportId(),er.getStatus());
	 		if (null!=err) {
	 			newStatus=err.getRole();
	 			submitToEmployeeNum = err.getOfficerId();
	 			submitNextDeadDate = Util.date2String(err.getDeadline(),Constants.DISPLAY_DATE_FORMAT);
	 			submitNotificationContent = err.getNotification();
	 		}else{
	 			 newStatus = "GM";	
	 			submitNextDeadDate = Util.date2String(er.getOverallDeadline(),Constants.DISPLAY_DATE_FORMAT);
				if(gmList.size()>0){
					submitToEmployeeNum = gmList.get(0).getUserId();
				 	submitToAddress = gmList.get(0).getEmailAddress();
				}
	 		}
		}
//		else if((!"CL".equalsIgnoreCase(dto.getReportUserRole().getCurrentReportStatus()))&& null==appraiseeCommonSearchDAO.searchNextOfficerRoleByIdAndCurrentRptStatus(dto.getReportId(),dto.getReportUserRole().getCurrentReportStatus())){
//			  newStatus = "GM";			 
//			  submitNextDeadDate = Util.date2String(er.getOverallDeadline(),Constants.DISPLAY_DATE_FORMAT);
//				if(gmList.size()>0){
//					submitToEmployeeNum = gmList.get(0).getUserId();
//				 	submitToAddress = gmList.get(0).getEmailAddress();
//				}
//			 
//		}
		
		//For SUGM pass report to next officer
		if ("AR".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRouting())
				&& "SU".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())) {
			EaReportRole err = appraiseeCommonSearchDAO.searchNextOfficerRoleByIdAndCurrentRptStatus(dto.getReportId(),
					er.getStatus());

			if(null != err && StringUtil.isEmptyString(err.getOfficerId())){
				//status I
				err = appraiseeCommonSearchDAO.searchNextOfficerByStatus(dto.getReportId(), er.getStatus(),null);
			}

			if (null != err) {
				newStatus = err.getRole();
				submitToEmployeeNum = err.getOfficerId();
				submitNextDeadDate = Util.date2String(err.getDeadline(), Constants.DISPLAY_DATE_FORMAT);
				submitNotificationContent = err.getNotification();

			} else {
				if ("IO".equalsIgnoreCase(er.getStatus())||"RO".equalsIgnoreCase(er.getStatus())) {
					newStatus = "GM";
					submitNextDeadDate = Util.date2String(er.getOverallDeadline(), Constants.DISPLAY_DATE_FORMAT);
					if (gmList.size() > 0) {
						submitToEmployeeNum = gmList.get(0).getUserId();
						submitToAddress = gmList.get(0).getEmailAddress();
					}
				}
			}
		}
		
		//For GM and SU pass report to any recipient
				if ("AR".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRouting())
						&& ("SU".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())
								|| "GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus()))
						&& !StringUtil.isEmptyString(dto.getMemoPrintSubmitInfo().getRoutingTo())){
					EaReportRole err = new EaReportRole();
					
					if("AP".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRoutingTo())){
						newStatus = "AP";
						submitToEmployeeNum = er.getEmployeeNumber();
					}else if("CD".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRoutingTo()) || "AO".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRoutingTo())
							|| "CO".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRoutingTo()) || "IO".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRoutingTo()) ||
							"RO".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRoutingTo())){
						err = appraiseeCommonSearchDAO.searchNextOfficerByStatus(dto.getReportId(), er.getStatus(),dto.getMemoPrintSubmitInfo().getRoutingTo());
						if (null != err) {
							newStatus = err.getRole();
							submitToEmployeeNum = err.getOfficerId();
							submitNextDeadDate = Util.date2String(err.getDeadline(), Constants.DISPLAY_DATE_FORMAT);
							submitNotificationContent = err.getNotification();
							if("CD".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRoutingTo())){
								er.setAssigned("N");
							}
						}
					}else if("GM".equalsIgnoreCase(dto.getMemoPrintSubmitInfo().getRoutingTo())){
						newStatus = "GM";
						submitNextDeadDate = Util.date2String(er.getOverallDeadline(), Constants.DISPLAY_DATE_FORMAT);
						if (gmList.size() > 0) {
							submitToEmployeeNum = gmList.get(0).getUserId();
							submitToAddress = gmList.get(0).getEmailAddress();
						}
						
					}
				}
		
		
		
		if("AO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			 //save emd
				submitMemoByAO(er,emd,dto.getMemoPrintSubmitInfo().getRouting());
			
		}else if("CO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			 //save emd
				submitMemoByCO(er,emd,dto.getMemoPrintSubmitInfo().getRouting());
			
		}else if("IO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			 //save emd
				submitMemoByIO(er,emd,dto.getMemoPrintSubmitInfo().getRouting());
			
		}
		
		if (!Util.isEmptyString(newStatus)) {
			er.setStatus(newStatus);
				if("SU".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus()) && "AO".equalsIgnoreCase(newStatus)){
					er.setAssigned("Y");
				}
			hibernateTemplate.saveOrUpdate(er);
		}
		
		SubmitActionInfo msi=new SubmitActionInfo();
		msi.setNewStatus(newStatus);
		msi.setSubmitAction(submitAction);
		msi.setSubmitNextDeadDate(submitNextDeadDate);
		msi.setSubmitNotificationContent(submitNotificationContent);
		msi.setSubmitToAddress(submitToAddress);
		msi.setSubmitToEmployeeNum(submitToEmployeeNum);
		return msi;
	}
	
	private void submitMemoByAO(EaReport er,EaMemoDetails emd,String routing){

		 String stringOfToday = DateTimeUtil.date2String(new  Date(), "dd/MM/yyyy");
		if ((!Util.isEmptyString(emd.getAppDate())) && 
			(!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			(stringOfToday.compareToIgnoreCase(emd.getAppDate()) != 0)) {
			er.setCompletionDateCompare1("Y");
			 
		}
		if (Util.isEmptyString(er.getCompletionDateCompare1())) {
			er.setCompletionDateCompare1("N");
			
		}if ((!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			("N".compareToIgnoreCase(er.getCompletionDateCompare1()) == 0) && (!routing.equalsIgnoreCase("PO"))) {
			emd.setAppDate(stringOfToday);
		}
		
		er.setCompletionDateCompare1("N");
		er.setCompletionDateCompare2("N");
	
		hibernateTemplate.saveOrUpdate(emd);
	}
	private void submitMemoByCO(EaReport er,EaMemoDetails emd,String routing){
		
		 String stringOfToday = DateTimeUtil.date2String(new  Date(), "dd/MM/yyyy");
		if ((!Util.isEmptyString(Util.date2String(emd.getCoDate()))) && 
			(!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			(stringOfToday.compareToIgnoreCase(Util.date2String(emd.getCoDate())) != 0)) {
			er.setCompletionDateCompare1("Y");
			 
		}
		if (Util.isEmptyString(er.getCompletionDateCompare1())) {
			er.setCompletionDateCompare1("N");
			
		}if ((!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			("N".compareToIgnoreCase(er.getCompletionDateCompare1()) == 0) && (!routing.equalsIgnoreCase("PO"))) {
			emd.setCoDate(Util.string2Date(stringOfToday,Constants.DISPLAY_DATE_FORMAT));
		}
		
		er.setCompletionDateCompare1("N");
		er.setCompletionDateCompare2("N");
	
		hibernateTemplate.saveOrUpdate(emd);
	}
	private void submitMemoByIO(EaReport er,EaMemoDetails emd,String routing){
	
		 String stringOfToday = DateTimeUtil.date2String(new  Date(), "dd/MM/yyyy");
		if ((!Util.isEmptyString(Util.date2String(emd.getIoDate()))) && 
			(!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			(stringOfToday.compareToIgnoreCase(Util.date2String(emd.getIoDate())) != 0)) {
			er.setCompletionDateCompare1("Y");
			 
		}
		if (Util.isEmptyString(er.getCompletionDateCompare1())) {
			er.setCompletionDateCompare1("N");
			
		}if ((!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			("N".compareToIgnoreCase(er.getCompletionDateCompare1()) == 0) && (!routing.equalsIgnoreCase("PO"))) {
			emd.setIoDate(Util.string2Date(stringOfToday,Constants.DISPLAY_DATE_FORMAT));
		}
		
		er.setCompletionDateCompare1("N");
		er.setCompletionDateCompare2("N");
	
		hibernateTemplate.saveOrUpdate(emd);
	}
	

}
