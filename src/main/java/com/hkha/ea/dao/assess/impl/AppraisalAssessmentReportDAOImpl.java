package com.hkha.ea.dao.assess.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import jakarta.servlet.http.HttpServletRequest;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
import com.hkha.ea.dao.assess.AppraisalAssessmentReportDAO;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.common.EmployeeEnquiryDAO;
import com.hkha.ea.dao.common.NotificationManagerDAO;
import com.hkha.ea.dao.security.UserEnquiryDAO;
import com.hkha.ea.domain.DummyReportInfo;
import com.hkha.ea.domain.EaContract;
import com.hkha.ea.domain.EaCoreCompetency;
import com.hkha.ea.domain.EaMemoDetails;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.domain.EaObjectives;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.domain.EaTrainPlan;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.assess.AppraiseeInfo;
import com.hkha.ea.dto.assess.AssessAppraisalDTO;
import com.hkha.ea.dto.assess.AssessAppraisalMemoDTO;
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
import com.hkha.ea.dto.assess.SubmitActionInfo;
import com.hkha.ea.dto.assess.TrainPlanInfo;
import com.hkha.ea.dto.common.ReportUserRole;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;

@Repository("appraisalAssessmentReportDAO")
public class AppraisalAssessmentReportDAOImpl implements AppraisalAssessmentReportDAO{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AppraisalAssessmentReportDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
		
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
		
	@Autowired
	private AppraisalAssessmentDAO appraisalAssessmentDAO;
	
	@Autowired
	private UserEnquiryDAO userEnquiryDAO;
	
	
	/**
	 * 	Search For Report
	 */
	public AssessAppraisalDTO searchAppraisalAssessmentFullReportById(long id, HttpServletRequest request){
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		AssessAppraisalDTO dto=new AssessAppraisalDTO();
		//search data
		EaReport er=appraiseeCommonSearchDAO.findEareportById(id);
		EaContract ec=appraiseeCommonSearchDAO.findEaContractById(id);
		List<EaObjectives> eolist=appraiseeCommonSearchDAO.findEaObjectivesByReportIdAndType(id,"PARTA");
		List<EaObjectives> eolistB=appraiseeCommonSearchDAO.findEaObjectivesByReportIdAndType(id,"PARTB");
		List<EaCoreCompetency> ecclist = appraiseeCommonSearchDAO.findEaCoreCompetencyByReportId(id);
		List<EaTrainPlan> etplist= appraiseeCommonSearchDAO.findEaTrainPlanById(id);
		List<EaReportRole> errlist=appraiseeCommonSearchDAO.searchReportRoleDetailByReportId(id);

		dto.setReportId(id);
		ReportUserRole rur=appraisalAssessmentDAO.getReportUserRoleByReportIdAndUserId(id,currentLoginUserId);
		dto.setReportUserRole(rur);
		dto=convertToAssessAppraisalDTOByPartB23578InfoAndAIPIInfo(dto,ec,er);		
		dto=convertToAssessAppraisalDTOByPartAB1InfoList(dto, eolist,"A");
		dto=convertToAssessAppraisalDTOByPartAB1InfoList(dto, eolistB,"B1");
		dto=convertToAssessAppraisalDTOByPartB4Info(dto,ecclist,ec);
		dto=convertToAssessAppraisalDTOByPartB6Info(dto,ec,etplist);
		dto=convertToAssessAppraisalDTOBySignatureInfoAB45678(dto,ec);
//		ReportUserRole rur=appraisalAssessmentDAO.getReportUserRoleByReportIdAndUserId(id,currentLoginUserId);
//		dto.setReportUserRole(rur);

		dto=convertToAssessAppraisalReportDTOByPrintSubmit(dto,er,errlist,request);
		return dto;
	}
	

	
	/**
	 * Convert Methods
	 *
	 * */
	
	private AssessAppraisalDTO convertToAssessAppraisalDTOByPartB23578InfoAndAIPIInfo(AssessAppraisalDTO dto,EaContract ec,EaReport er){
		AppraiseeInfo ai=new AppraiseeInfo();
		PersonalInfo pi=new PersonalInfo();		
		PartB2Info pb2=new PartB2Info();
		PartB3Info pb3=new PartB3Info();
		PartB5B7B8Info pb5=new PartB5B7B8Info();
		PartB5B7B8Info pb7=new PartB5B7B8Info();
		PartB5B7B8Info pb8=new PartB5B7B8Info();

		if(null!=ec){
			//For PartB2Info
			//edited on 20170223 for overall comment disappear
			//pb2.setOverallPerformanceComment(ec.getXxOverallComment());
			pb2.setOverallPerformanceComment(ec.getXxoverallComment());
			
			//pb2.setOverallPerformanceComment2(ec.getXxOverallComment2());
			pb2.setOverallRating(ec.getOverallRating());
			pb2.setSpecialFactors(ec.getSpecialFactors());
			
			//For PartB3Info
			pb3.setAoPerformanceBonus(ec.getPerformanceBonusAo());
			pb3.setAoSalaryAdjustment(ec.getSalaryAdjustmentAo());
			
			//For PartB5Info
			pb5.setWorkWell(ec.getWorkWell());
			pb5.setOverallPerformance(ec.getOverallPerformance());
			pb5.setPerformanceBonus(ec.getPerformanceBonusCo());
			pb5.setSalaryAdjustment(ec.getSalaryAdjustmentCo());
			pb5.setPotential(ec.getCounterPotential());
			
			//For PartB7Info
			pb7.setWorkWell(ec.getWorkWellEo());
			pb7.setOverallPerformance(ec.getOverallPerfEo());
			pb7.setPerformanceBonus(ec.getPerformanceBonusEo());
			pb7.setSalaryAdjustment(ec.getSalaryAdjustmentEo());
			pb7.setPotential(ec.getEndorsingPotential());
		
			//For PartB8Info
			pb8.setReviewComment(ec.getReviewComment());
			pb8.setPerformanceBonus(ec.getPerformanceBonusRo());
			pb8.setSalaryAdjustment(ec.getSalaryAdjustmentRo());
			pb8.setPotential(ec.getReviewPotential());
			
			//For PersonalInfo
			pi.setCommendation(ec.getCommendation());
			pi.setActingAppointment(ec.getActingAppoint());
			pi.setTrainingInReviewPeriod(ec.getTrainingCourse());
		}
		
		if(null!=er){					
			//For PersonalInfo			
			pi.setChineseName(er.getChineseName());
			pi.setEnglishName(er.getName());
			pi.setRankOnFirstAppoint(er.getFirstAppointmentRank());
			pi.setDateOnFirstAppoint(Util.date2String(er.getDateOfAppFirstRank(),Constants.DISPLAY_DATE_FORMAT));
			pi.setRankOnPresentAppoint(er.getSubstantiveRank());
			pi.setDateOnPresentAppoint(Util.date2String(er.getDateOfAppSubRank(),Constants.DISPLAY_DATE_FORMAT));
			pi.setPresentPost(er.getPresentPost());
			pi.setTelephone(er.getTelephone());
			pi.setDivisionSection(er.getDivision());
			pi.setDateOfPosting(Util.date2String(er.getDateOfPosting(),Constants.DISPLAY_DATE_FORMAT));
			pi.setCurrentContractPeriodStart(Util.date2String(er.getCurrentContractStartDate(),Constants.DISPLAY_DATE_FORMAT));
			pi.setCurrentContractPeriodEnd(Util.date2String(er.getCurrentContractEndDate(),Constants.DISPLAY_DATE_FORMAT));
			
			//For AppraiseeInfo			
			ai.setAppraiseeName(er.getChineseName());
			ai.setEmployeeNumber(er.getEmployeeNumber());
			ai.setRank(er.getSubstantiveRank());
			ai.setAppraisalPeriodStart(Util.date2String(er.getCommenceDate(),Constants.DISPLAY_DATE_FORMAT));
			ai.setAppraisalPeriodEnd(Util.date2String(er.getEndDate(),Constants.DISPLAY_DATE_FORMAT));
			ai.setTrackDate(Util.date2String(er.getTrackDate(),Constants.DISPLAY_DATE_FORMAT));
			//debug
			log.info("TrackDate:"+ai.getTrackDate());
		}
		
		//appraisee is allow bonus and salary
		try{	
			boolean[] allowBSD = appraisalAssessmentDAO.allowBonusSalary(dto.getReportId());
			boolean isAllowBonus = allowBSD[0];//is allow bonus
			boolean isDimmedBonus = (allowBSD[2]==true || (!isAllowBonus));
			boolean isDimmedSalary = !allowBSD[1];

			/*20170221
			Date dateOfAppFirstRank = er.getDateOfAppFirstRank();
			Date dateOfSatisfyBnous = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.DATE_FORMAT);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			StringBuffer satisfyBnousDate = new StringBuffer();
			satisfyBnousDate.append("01/07/2013");
			//satisfyBnousDate.append(year);
			dateOfAppFirstRank = DateTimeUtil.string2Date(sdf.format(dateOfAppFirstRank));
			dateOfSatisfyBnous = DateTimeUtil.string2Date(satisfyBnousDate.toString());
			*/
			//Y-->not dimmed N-->dimmed
			//edited on 20180319re move dimming of merit payment and salary adjustment
			//if(!isDimmedBonus){
				//CAN BE CHOICE
				pb3.setBonusDimmed("Y");
				pb5.setBonusDimmed("Y");
				pb7.setBonusDimmed("Y");
				pb8.setBonusDimmed("Y");
			/*}else{
				pb3.setBonusDimmed("N");
				pb5.setBonusDimmed("N");
				pb7.setBonusDimmed("N");
				pb8.setBonusDimmed("N");
			}*/

			//is allow salary
			//if(!isDimmedSalary){
				//CAN BE CHOICE
				pb3.setSalaryDimmed("Y");
				pb5.setSalaryDimmed("Y");
				pb7.setSalaryDimmed("Y");
				pb8.setSalaryDimmed("Y");
			/*}else{
				pb3.setSalaryDimmed("N");
				pb5.setSalaryDimmed("N");
				pb7.setSalaryDimmed("N");
				pb8.setSalaryDimmed("N");
			}*/
			//edited on 20180319re move dimming of merit payment and salary adjustment
			/* 20170221
			if(dateOfAppFirstRank.compareTo(dateOfSatisfyBnous)>0){
				pb3.setBonusDimmed("N");
				pb5.setBonusDimmed("N");
				pb7.setBonusDimmed("N");
				pb8.setBonusDimmed("N");
			}
			*/
			if("IO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				//set bonus is dimmed
				pb3.setBonusDimmed("N");
				pb5.setBonusDimmed("N");			
				//set salary is dimmed
				pb3.setSalaryDimmed("N");
				pb5.setSalaryDimmed("N");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.severe(e.toString());
		}
		
		dto.setPartB2Info(pb2);
		dto.setPartB3Info(pb3);
		dto.setPartB5Info(pb5);
		dto.setPartB7Info(pb7);
		dto.setPartB8Info(pb8);
		dto.setPersonalInfo(pi);
		dto.setAppraiseeInfo(ai);
		
		return dto;
	}
	
	private AssessAppraisalDTO convertToAssessAppraisalDTOByPartAB1InfoList(AssessAppraisalDTO dto,List<EaObjectives> eolist,String flag){
		List<PartAB1Info> pab1list= new ArrayList<PartAB1Info>();
		
		if(eolist.size()>0){
			for(EaObjectives eo:eolist){			
				PartAB1Info pab1=new PartAB1Info();
				pab1.setKeyObjectives(eo.getKeyObjective());
				pab1.setSeqNo(eo.getSeqNo());
				pab1.setRating(eo.getRating());
				pab1.setResultAchieved(eo.getResultsAchieved());
				pab1.setType(eo.getType());
				//edited on 20180108
				if(eo.getWeighting() != null){
					pab1.setWeighting((double)eo.getWeighting());
				}
				//end edited on 20180108
				pab1list.add(pab1);	
			}
			
			int pab1size=pab1list.size();
			if(pab1size<8){
				for(int i=0;i<8-pab1size;i++){
					PartAB1Info pab1=new PartAB1Info();
					pab1list.add(pab1);
					
				}
			}
		}else{
			for(int i=0;i<8;i++){
				PartAB1Info pab1=new PartAB1Info();
				pab1list.add(pab1);
				
			}
		}
		if("A".equalsIgnoreCase(flag)){
			dto.setPartAInfoList(pab1list);
		}else if("B1".equalsIgnoreCase(flag)){
			dto.setPartB1InfoList(pab1list);
		}
		return dto;
	}
	
	private AssessAppraisalDTO convertToAssessAppraisalDTOByPartB4Info(AssessAppraisalDTO dto,List<EaCoreCompetency> ecclist,EaContract ec){
		PartB4Info pb4= new PartB4Info();
		List<CoreCompetencyInfo> ccInfoList=new ArrayList<CoreCompetencyInfo>();
		if(ecclist.size()>0){
			for(EaCoreCompetency ecc:ecclist){
				CoreCompetencyInfo cc=new CoreCompetencyInfo();
				cc.setReportId(ecc.getReportId());
				cc.setSeqNo(ecc.getSeqNo());
				cc.setCompetencyId(ecc.getCompetencyId());
				cc.setCompetency(ecc.getCompetency());
				cc.setRating(ecc.getRating());
				ccInfoList.add(cc);
			}
		}
		pb4.setCcInfoList(ccInfoList);
		pb4.setCounterPotentialB4(ec.getAppraisePotential());
		dto.setPartB4Info(pb4);
		return dto;
	}
	
	private AssessAppraisalDTO convertToAssessAppraisalDTOByPartB6Info(AssessAppraisalDTO dto,EaContract ec,List<EaTrainPlan> etplist){
		PartB6Info pb6=new PartB6Info();
		if(null!=ec){		
			//edited on 20170223
			//pb6.setReviewInterview(ec.getXxReviewInterview());
			pb6.setReviewInterview(ec.getXxreviewInterview());
		}
		List<TrainPlanInfo> tpilist=new ArrayList<TrainPlanInfo>();
		if(etplist.size()>0){
			for(EaTrainPlan etp:etplist){
				TrainPlanInfo tpi=new TrainPlanInfo();
				tpi.setReportId(etp.getReportId());
				tpi.setSeqNo(etp.getSeqNo());
				tpi.setActionPlan(etp.getActionPlan());
				tpi.setDelevelopNeed(etp.getDevelopNeed());
				tpi.setReviewDate(Util.date2String(etp.getReviewDate(),Constants.DISPLAY_DATE_FORMAT));
				tpilist.add(tpi);
			}
			
			int tpilistsize=tpilist.size();
			if(tpilistsize<4){
				for(int i=0;i<4-tpilistsize;i++){
					TrainPlanInfo tpi=new TrainPlanInfo();
					tpilist.add(tpi);
					
				}
			}
		}else{
			for(int i=0;i<4;i++){
				TrainPlanInfo tpi=new TrainPlanInfo();
				tpilist.add(tpi);
			}
		}
		pb6.setTpiList(tpilist);
		dto.setPartB6Info(pb6);
		return dto;
	}
	
	private AssessAppraisalDTO convertToAssessAppraisalDTOBySignatureInfoAB45678(AssessAppraisalDTO dto,EaContract ec){
		SignatureInfo siA=new SignatureInfo();
		SignatureInfo siB4=new SignatureInfo();
		SignatureInfo siB5=new SignatureInfo();
		SignatureInfo siB6=new SignatureInfo();
		SignatureInfo siB7=new SignatureInfo();
		SignatureInfo siB8=new SignatureInfo();
				
		if(null!=ec){
			//For SignatureInfoA
			siA.setNameOfAppraisee(ec.getPerfAppName());
			siA.setAppraiseeRankPost(ec.getPerfAppRank());
			siA.setAppraiseeSignDate(ec.getPerfAppDate());
			//siA.setOfficerDate(ec.getPerfAppDate());
			siA.setOfficerDate(ec.getPrefAoDate());
			siA.setOfficerName(ec.getPerfAoName());
			siA.setOfficerRank(ec.getPerfAoRank());
			
			//For SignatureInfoB4
			siB4.setOfficerDate(ec.getCompAoDate());
			siB4.setOfficerName(ec.getCompAoName());
			siB4.setOfficerRank(ec.getCompAoRank());
			siB4.setTel(ec.getCompAoTel());
			
			//For SignatureInfoB5
			siB5.setOfficerDate(ec.getAsseCoDate());
			siB5.setOfficerName(ec.getAsseCoName());
			siB5.setOfficerRank(ec.getAsseCoRank());
			siB5.setTel(ec.getAsseCoTel());
			
			//For SignatureInfoB6
			siB6.setNameOfAppraisee(ec.getIntvAppName());
			siB6.setAppraiseeRankPost(ec.getIntvAppRank());
			siB6.setAppraiseeSignDate(ec.getIntvAppDate());
			siB6.setOfficerDate(ec.getIntvIoDate());
			siB6.setOfficerName(ec.getIntvIoName());
			siB6.setOfficerRank(ec.getIntvIoRank());
			
			//For SignatureInfoB7			 
			siB7.setOfficerDate(ec.getAsseEoDate());
			siB7.setOfficerName(ec.getAsseEoName());
			siB7.setOfficerRank(ec.getAsseEoRank());
			siB7.setTel(ec.getAsseEoTel());
			
			//For SignatureInfoB8
			siB8.setOfficerDate(ec.getReviRoDate());
			siB8.setOfficerName(ec.getReviRoName());
			siB8.setOfficerRank(ec.getReviRoRank());
		}
		dto.setSignatureInfoA(siA);
		dto.setSignatureInfoB4(siB4);
		dto.setSignatureInfoB5(siB5);
		dto.setSignatureInfoB6(siB6);
		dto.setSignatureInfoB7(siB7);
		dto.setSignatureInfoB8(siB8);
		
		return dto;
	}
	
	public List<CoreCompetencyInfo> genCCInfoListForPartB4(Long id){
		List<EaCoreCompetency> ecclist = appraiseeCommonSearchDAO.findEaCoreCompetencyByReportId(id);
		List<CoreCompetencyInfo> ccInfoList=new ArrayList<CoreCompetencyInfo>();
		if(ecclist.size()>0){
			for(EaCoreCompetency ecc:ecclist){
				CoreCompetencyInfo cc=new CoreCompetencyInfo();
				cc.setReportId(ecc.getReportId());
				cc.setSeqNo(ecc.getSeqNo());
				cc.setCompetencyId(ecc.getCompetencyId());
				cc.setCompetency(ecc.getCompetency());
				cc.setRating(ecc.getRating());
				ccInfoList.add(cc);
			}
		}
		return ccInfoList;
	}
	
	private AssessAppraisalDTO convertToAssessAppraisalReportDTOByPrintSubmit(AssessAppraisalDTO dto,EaReport er,List<EaReportRole> errlist, HttpServletRequest request){
		PrintSubmitInfo mps=new PrintSubmitInfo();		
		String userRole=dto.getReportUserRole().getCurrentUserRoleStatus();
		String rptRole=dto.getReportUserRole().getCurrentReportStatus();
		List<SystemParameterDTO> routingToList=new ArrayList<SystemParameterDTO>();

		if(null!=er){					
			// Part Info			
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
			//edited on 20171010 for send report to appraisee
			if(!StringUtil.isEmptyString(dto.getPartAInfoList().get(0).getKeyObjectives())){
				routingLabelList.add(sdAp);
				routingLabelList.add(sdNo);
				mps.setRouting("NO");//on checked
			}else{
				routingLabelList.add(sdAp);
				;
				mps.setRouting("AP");//on checked
			}
			//end edited on 20171010
		}else if("CO".equalsIgnoreCase(userRole)||"EO".equals(userRole)||"IO".equals(userRole)||"RO".equals(userRole)){
			mps.setRouting("NO");//on checked
			routingLabelList.add(sdPo);
			routingLabelList.add(sdNo);
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
		if ("AO".equalsIgnoreCase(userRole)){
			
			if(!StringUtil.isEmptyString(dto.getPartAInfoList().get(0).getKeyObjectives())){
				log.info("Check id:"+dto.getReportUserRole().getNextOfficerId()+" "+dto.getReportUserRole().getNextOfficerName()+" "+dto.getReportUserRole().getNextOfficerRole());
				mps.setNextOfficerId(dto.getReportUserRole().getNextOfficerId());
				mps.setNextOfficerName(dto.getReportUserRole().getNextOfficerName());
				mps.setNextOfficerRole(dto.getReportUserRole().getNextOfficerRole());
			}else{
				mps.setNextOfficerId(dto.getAppraiseeInfo().getEmployeeNumber());
				mps.setNextOfficerName(dto.getAppraiseeInfo().getAppraiseeName());
				mps.setNextOfficerRole("AP");
			}
		}else{
			mps.setNextOfficerId(dto.getReportUserRole().getNextOfficerId());
			mps.setNextOfficerName(dto.getReportUserRole().getNextOfficerName());
			mps.setNextOfficerRole(dto.getReportUserRole().getNextOfficerRole());
		}
		
		dto.setPrintSubmitInfo(mps);
		return dto;
	}
	
	/**
	 * Report Save method
	 * 
	 * */
	
	public EaReport saveReportAppraisalInfo(AppraiseeInfo ai,EaReport er)throws Exception{	
		//Save AppraiseeInfo	
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		er.setCommenceDate(Util.string2Date(ai.getAppraisalPeriodStart(), Constants.DISPLAY_DATE_FORMAT));
		er.setEndDate(Util.string2Date(ai.getAppraisalPeriodEnd(), Constants.DISPLAY_DATE_FORMAT));		
		
		appraisalAssessmentDAO.updatePem001RCommenceDateAndEndDate(ai.getAppraisalPeriodStart(),ai.getAppraisalPeriodEnd(),currentLoginUserId,er.getEmployeeNumber());
		return er;
	
	}
	public void saveReportPersonalInfo(AssessAppraisalDTO dto)throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		PersonalInfo pi=dto.getPersonalInfo();
		
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		//save AppraiseeInfo
		er=saveReportAppraisalInfo(dto.getAppraiseeInfo(),er);
		
		//save PersonalInfo --Eareport	
		er.setPresentPost(pi.getPresentPost());
		er.setTelephone(pi.getTelephone());
		er.setDivision(pi.getDivisionSection());
		er.setLastUpdatedBy(currentLoginUserId);
		er.setLastUpdateDate(new Date());
		hibernateTemplate.saveOrUpdate(er);
		
		//save PersonalInfo --EaContract
		EaContract ec=appraiseeCommonSearchDAO.findEaContractById(dto.getReportId());
		if(null==ec){
			ec=new EaContract();
			ec.setReportId(dto.getReportId());
			ec.setCreatedBy(currentLoginUserId);
			ec.setCreationDate(new Date());
		}
		ec.setCommendation(pi.getCommendation());
		ec.setActingAppoint(pi.getActingAppointment());
		ec.setTrainingCourse(pi.getTrainingInReviewPeriod());
		ec.setLastUpdatedBy(currentLoginUserId);
		ec.setLastUpdateDate(new Date());
		
		hibernateTemplate.saveOrUpdate(ec);
		
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	
	public void saveReportPartAInfo(AssessAppraisalDTO dto, String mode)throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();	
		List<PartAB1Info> palist=dto.getPartAInfoList();
		List<PartAB1Info> pb1list=dto.getPartB1InfoList();
		
		//debug
		//log.info("Rating A:"+palist.get(0).getRating());
		//log.info("Rating B1:"+pb1list.get(0).getRating());
		
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		//save AppraiseeInfo
		er=saveReportAppraisalInfo(dto.getAppraiseeInfo(),er);
		hibernateTemplate.saveOrUpdate(er);
		
		//save PartAInfo --EaObjectives
		List<EaObjectives> eoalist=appraiseeCommonSearchDAO.findEaObjectivesByReportIdAndType(dto.getReportId(),"PARTA");
		List<EaObjectives> eoblist=appraiseeCommonSearchDAO.findEaObjectivesByReportIdAndType(dto.getReportId(),"PARTB");
		Date existCreateDate=new Date();
		String existCreateBy="";
		if(eoalist.size()>0 || eoblist.size()>0){
			//edited on 20180115 index out of bound
				if (eoalist.size()>0){
					existCreateDate=eoalist.get(0).getCreationDate();
					existCreateBy=eoalist.get(0).getCreatedBy();
				}else{
					existCreateDate=eoblist.get(0).getCreationDate();
					existCreateBy=eoblist.get(0).getCreatedBy();
				}
			 //edited on 20180115 index out of bound
			for(EaObjectives eoa:eoalist){
				hibernateTemplate.delete(eoa);
			}
			if (mode.equals("send")) {	// 20240201 do not copy to part b if save only
				for(EaObjectives eob:eoblist){
					hibernateTemplate.delete(eob);
				}
			}
					
		}
			//add new
			for(int i=0;i<palist.size();i++){
				//if(!StringUtil.isEmptyString(palist.get(i).getKeyObjectives())){					
					EaObjectives eo=new EaObjectives();	
					eo.setSeqNo(Long.parseLong(String.valueOf(i+1)));
					eo.setType("PARTA");
					eo.setReportId(dto.getReportId());
					eo.setRating(palist.get(i).getRating());
					//edited on 20170108 cannot save weighting issue
					if(palist.get(i).getWeighting() != null){
						eo.setWeighting(palist.get(i).getWeighting().intValue());
					}
					//edited on 20170108 cannot save weighting issue
					//log.info("PA list obj:"+palist.get(i).getKeyObjectives());
					//log.info("PA list res:"+palist.get(i).getResultAchieved());
					eo.setResultsAchieved(palist.get(i).getResultAchieved());
					eo.setKeyObjective(palist.get(i).getKeyObjectives());
					if(StringUtil.isEmptyString(existCreateBy)){
						existCreateBy=currentLoginUserId;
					}
					eo.setCreatedBy(existCreateBy);
					eo.setCreationDate(existCreateDate);
					eo.setLastUpdatedBy(currentLoginUserId);
					eo.setLastUpdateDate(new Date());
					hibernateTemplate.save(eo);
					
					if (mode.equals("send")) {	// 20240201 do not copy to part b if save only
						EaObjectives eoPB=new EaObjectives();
						if(StringUtil.isEmptyString(pb1list.get(i).getResultAchieved())){
							eo.setResultsAchieved(palist.get(i).getResultAchieved());
						}
						BeanUtils.copyProperties(eo, eoPB);
						eoPB.setType("PARTB");
						
						//edited on 20180108 keep PartB1 result achieved
						if(!StringUtil.isEmptyString(pb1list.get(i).getResultAchieved())){
							eoPB.setResultsAchieved(pb1list.get(i).getResultAchieved());
						}
						//added on 20180118 keep PartB1 rating
						if(!StringUtil.isEmptyString(pb1list.get(i).getRating())){
							eoPB.setRating(pb1list.get(i).getRating());
						}
						//end added on 20180118 keep PartB1 rating
						//end edited on 20180108 keep PartB1 result achieved
						hibernateTemplate.save(eoPB);
					}
				//}
			}
			/*
			//edited on 20170307 creation date of HA_HR_EA_OBJECTIVES incorrect issue 
			for(int j=0; j<eoalist.size();j++){
				EaObjectives eoa = eoalist.get(j);
				eoa.setCreationDate(existCreateDate);
				hibernateTemplate.update(eoa);
				hibernateTemplate.flush();
				hibernateTemplate.clear();
			}
			
			for(int k=0;k<eoblist.size();k++){
				EaObjectives eob = eoblist.get(k);
				eob.setCreationDate(existCreateDate);
				hibernateTemplate.update(eob);
				hibernateTemplate.flush();
				hibernateTemplate.clear();
			}
			//end edited on 20170307 creation date of HA_HR_EA_OBJECTIVES incorrect issue
			*/
			hibernateTemplate.flush();
			hibernateTemplate.clear();
	}
	
	public void saveReportPartB1Info(AssessAppraisalDTO dto)throws Exception{
		List<EaObjectives> eoblist=appraiseeCommonSearchDAO.findEaObjectivesByReportIdAndType(dto.getReportId(),"PARTB");
		List<PartAB1Info> pblist=dto.getPartB1InfoList();
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();	
		Date existCreateDate=new Date();
		String existCreateBy="";

		if(eoblist.size()>0){
			 existCreateDate=eoblist.get(0).getCreationDate();
			 existCreateBy=eoblist.get(0).getCreatedBy();
			for(EaObjectives eob:eoblist){
				hibernateTemplate.delete(eob);
			}
		}

			//add new
			for(int i=0;i<pblist.size();i++){
				/*if(!StringUtil.isEmptyString(pblist.get(i).getKeyObjectives())||
					!StringUtil.isEmptyString(pblist.get(i).getResultAchieved())
						){*/					
					EaObjectives eo=new EaObjectives();	
					eo.setSeqNo(Long.parseLong(String.valueOf(i+1)));
					eo.setType("PARTB");
					eo.setReportId(dto.getReportId());
					eo.setRating(pblist.get(i).getRating());
					//edited on 20180108
					if(pblist.get(i).getWeighting() != null){
						eo.setWeighting(pblist.get(i).getWeighting().intValue());
					}
					//end edited on 20180108
					eo.setResultsAchieved(pblist.get(i).getResultAchieved());
					eo.setKeyObjective(pblist.get(i).getKeyObjectives());
					if(StringUtil.isEmptyString(existCreateBy)){
						existCreateBy=currentLoginUserId;
					}
					eo.setCreatedBy(existCreateBy);
					eo.setCreationDate(existCreateDate);
					eo.setLastUpdatedBy(currentLoginUserId);
					eo.setLastUpdateDate(new Date());
					hibernateTemplate.save(eo);
					//hibernateTemplate.flush();
					//hibernateTemplate.clear();
				//}
			}
			/*
			//edited on 20170307 creation date of HA_HR_EA_OBJECTIVES incorrect issue
			for(int j=0; j<eoblist.size();j++){
				EaObjectives eob = eoblist.get(j);
				eob.setCreationDate(existCreateDate);
				eob.setCreatedBy(existCreateBy);
				eob.setLastUpdateDate(new Date());
				eob.setLastUpdatedBy(currentLoginUserId);
				log.info("eob:"+eob.getCreationDate());
				hibernateTemplate.update(eob);
				hibernateTemplate.flush();
				hibernateTemplate.clear();
			}
			//end edited on 20170307 creation date of HA_HR_EA_OBJECTIVES incorrect issue
			*/
			hibernateTemplate.flush();
			hibernateTemplate.clear();
	}
	

	
	public void saveReportPartB2Info(AssessAppraisalDTO dto)throws Exception{
			PartB2Info pb2=dto.getPartB2Info();
			String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
			
			EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
			//save AppraiseeInfo
			er=saveReportAppraisalInfo(dto.getAppraiseeInfo(),er);
			hibernateTemplate.saveOrUpdate(er);
			
			EaContract ec=appraiseeCommonSearchDAO.findEaContractById(dto.getReportId());
			if(null==ec){
				
				ec=new EaContract();	
				ec.setCreatedBy(currentLoginUserId);
				ec.setCreationDate(new Date());
				ec.setReportId(dto.getReportId());
			}
			//save PartB2Info
			//edited on 20170223
			//ec.setXxOverallComment(pb2.getOverallPerformanceComment());
			ec.setXxoverallComment(pb2.getOverallPerformanceComment());
			//ec.setXxOverallComment2(pb2.getOverallPerformanceComment2());
			ec.setOverallRating(pb2.getOverallRating());
		
			//log.info("pb2: "+pb2.getSpecialFactors().getBytes("UTF-8").length);
			ec.setSpecialFactors(pb2.getSpecialFactors());

			ec.setLastUpdatedBy(currentLoginUserId);
			ec.setLastUpdateDate(new Date());
			
			
			hibernateTemplate.saveOrUpdate(ec);		
			
			hibernateTemplate.flush();
			hibernateTemplate.clear();
		
		
	}
	
	public void saveReportPartB3Info(AssessAppraisalDTO dto)throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		//save AppraiseeInfo
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		er=saveReportAppraisalInfo(dto.getAppraiseeInfo(),er);
		hibernateTemplate.saveOrUpdate(er);
		//Save PartB3Info
		
		PartB3Info pb3=dto.getPartB3Info();
		EaContract ec=appraiseeCommonSearchDAO.findEaContractById(dto.getReportId());
		if(null==ec){			
			ec=new EaContract();	
			ec.setCreatedBy(currentLoginUserId);
			ec.setCreationDate(new Date());
			ec.setReportId(dto.getReportId());
		}
		
		ec.setPerformanceBonusAo(pb3.getAoPerformanceBonus());
		ec.setSalaryAdjustmentAo(pb3.getAoSalaryAdjustment());
		
		ec.setLastUpdatedBy(currentLoginUserId);
		ec.setLastUpdateDate(new Date());
		
		hibernateTemplate.saveOrUpdate(ec);
		
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	
	public void saveReportPartB4Info(AssessAppraisalDTO dto)throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		//save AppraiseeInfo
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		er=saveReportAppraisalInfo(dto.getAppraiseeInfo(),er);
		hibernateTemplate.saveOrUpdate(er);
		//Save PartB3Info
		
		PartB4Info pb4=dto.getPartB4Info();
		EaContract ec=appraiseeCommonSearchDAO.findEaContractById(dto.getReportId());
		if(null==ec){			
			ec=new EaContract();	
			ec.setCreatedBy(currentLoginUserId);
			ec.setCreationDate(new Date());
			ec.setReportId(dto.getReportId());
		}
		ec.setAppraisePotential(pb4.getCounterPotentialB4());
		
		ec.setLastUpdatedBy(currentLoginUserId);
		ec.setLastUpdateDate(new Date());
		
		hibernateTemplate.saveOrUpdate(ec);
		
		List<EaCoreCompetency> ecclist = appraiseeCommonSearchDAO.findEaCoreCompetencyByReportId(dto.getReportId());
		List<CoreCompetencyInfo> ccInfoList=pb4.getCcInfoList();
		if(ecclist.size()>0){
			for(int i=0;i<ccInfoList.size();i++){
				for(EaCoreCompetency ecc:ecclist){
					if(ecc.getSeqNo()==ccInfoList.get(i).getSeqNo()){
						ecc.setRating(ccInfoList.get(i).getRating());
						hibernateTemplate.saveOrUpdate(ecc);
						break;
					}
				}
			}
		}
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		
	}
	
	public void saveReportPartB5Info(AssessAppraisalDTO dto)throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		//save AppraiseeInfo
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		er=saveReportAppraisalInfo(dto.getAppraiseeInfo(),er);
		hibernateTemplate.saveOrUpdate(er);
		
		//Save PartB5Info
		PartB5B7B8Info pb5=dto.getPartB5Info();
		
		EaContract ec=appraiseeCommonSearchDAO.findEaContractById(dto.getReportId());
		if(null==ec){			
			ec=new EaContract();	
			ec.setCreatedBy(currentLoginUserId);
			ec.setCreationDate(new Date());
			ec.setReportId(dto.getReportId());
		}
		ec.setWorkWell(pb5.getWorkWell());
		ec.setOverallPerformance(pb5.getOverallPerformance());
		ec.setPerformanceBonusCo(pb5.getPerformanceBonus());
		ec.setSalaryAdjustmentCo(pb5.getSalaryAdjustment());
		ec.setCounterPotential(pb5.getPotential());
		
		ec.setLastUpdatedBy(currentLoginUserId);
		ec.setLastUpdateDate(new Date());
		
		hibernateTemplate.saveOrUpdate(ec);
		
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	
	public void saveReportPartB6Info(AssessAppraisalDTO dto)throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		//save AppraiseeInfo
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		er=saveReportAppraisalInfo(dto.getAppraiseeInfo(),er);
		hibernateTemplate.saveOrUpdate(er);
		
		//Save PartB6Info
		PartB6Info pb6=dto.getPartB6Info();
		
		EaContract ec=appraiseeCommonSearchDAO.findEaContractById(dto.getReportId());
		if(null==ec){			
			ec=new EaContract();	
			ec.setCreatedBy(currentLoginUserId);
			ec.setCreationDate(new Date());
			ec.setReportId(dto.getReportId());
		}
		//edited on 20170223
		//ec.setXxReviewInterview(pb6.getReviewInterview());
		ec.setXxreviewInterview(pb6.getReviewInterview());
		
		ec.setLastUpdatedBy(currentLoginUserId);
		ec.setLastUpdateDate(new Date());
		hibernateTemplate.saveOrUpdate(ec);
		
		List<EaTrainPlan> etplist= appraiseeCommonSearchDAO.findEaTrainPlanById(dto.getReportId());
		List<TrainPlanInfo> tpilist=pb6.getTpiList();
		Date existCreateDate=new Date();
		String existCreateBy="";
		if(etplist.size()>0){
			existCreateDate=etplist.get(0).getCreationDate();
			existCreateBy=etplist.get(0).getCreatedBy();
			for(EaTrainPlan etpe:etplist){
				hibernateTemplate.delete(etpe);
				
			}
		}

		for(int i=0;i<tpilist.size();i++){
			if(!StringUtil.isEmptyString(tpilist.get(i).getDelevelopNeed())){					
				EaTrainPlan etp=new EaTrainPlan();	
				etp.setReportId(dto.getReportId());
				etp.setSeqNo(Long.parseLong(String.valueOf(i+1)));
				etp.setActionPlan(tpilist.get(i).getActionPlan());
				etp.setDevelopNeed(tpilist.get(i).getDelevelopNeed());
				etp.setReviewDate(Util.string2Date(tpilist.get(i).getReviewDate(), Constants.DISPLAY_DATE_FORMAT));
				if(StringUtil.isEmptyString(existCreateBy)){
					existCreateBy=currentLoginUserId;
				}
				etp.setCreatedBy(existCreateBy);
				etp.setCreationDate(existCreateDate);
				etp.setLastUpdatedBy(currentLoginUserId);
				etp.setLastUpdateDate(new Date());
				
				hibernateTemplate.save(etp);				
			}
		}
		
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	
	public void saveReportPartB7Info(AssessAppraisalDTO dto)throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		//save AppraiseeInfo
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		er=saveReportAppraisalInfo(dto.getAppraiseeInfo(),er);
		hibernateTemplate.saveOrUpdate(er);
		
		//Save PartB7Info
		if(dto.getPartB7Info()!=null){
			PartB5B7B8Info pb7=dto.getPartB7Info();
			EaContract ec=appraiseeCommonSearchDAO.findEaContractById(dto.getReportId());
			if(null==ec){			
				ec=new EaContract();	
				ec.setCreatedBy(currentLoginUserId);
				ec.setCreationDate(new Date());
				ec.setReportId(dto.getReportId());
			}
			//For PartB7Info
			ec.setWorkWellEo(pb7.getWorkWell());
			ec.setOverallPerfEo(pb7.getOverallPerformance());
			ec.setPerformanceBonusEo(pb7.getPerformanceBonus());
			ec.setSalaryAdjustmentEo(pb7.getSalaryAdjustment());
			ec.setEndorsingPotential(pb7.getPotential());
			
			ec.setLastUpdatedBy(currentLoginUserId);
			ec.setLastUpdateDate(new Date());
			
			hibernateTemplate.saveOrUpdate(ec);
			
			hibernateTemplate.flush();
			hibernateTemplate.clear();
		
		}
	}
	
	public void saveReportPartB8Info(AssessAppraisalDTO dto)throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		//save AppraiseeInfo
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		er=saveReportAppraisalInfo(dto.getAppraiseeInfo(),er);
		hibernateTemplate.saveOrUpdate(er);
		
		//Save PartB8Info
		PartB5B7B8Info pb8=dto.getPartB8Info();
		
		EaContract ec=appraiseeCommonSearchDAO.findEaContractById(dto.getReportId());
		if(null==ec){			
			ec=new EaContract();	
			ec.setCreatedBy(currentLoginUserId);
			ec.setCreationDate(new Date());
			ec.setReportId(dto.getReportId());
		}
		
		ec.setReviewComment(pb8.getReviewComment());
		ec.setPerformanceBonusRo(pb8.getPerformanceBonus());
		ec.setSalaryAdjustmentRo(pb8.getSalaryAdjustment());
		ec.setReviewPotential(pb8.getPotential());
		
		ec.setLastUpdatedBy(currentLoginUserId);
		ec.setLastUpdateDate(new Date());
		
		hibernateTemplate.saveOrUpdate(ec);
		
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	
	public void saveReportPrintSubmitInfo(AssessAppraisalDTO dto)throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		PrintSubmitInfo psi=dto.getPrintSubmitInfo();
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		er=saveReportAppraisalInfo(dto.getAppraiseeInfo(),er);
		if(er==null){
			er=new EaReport();	
			er.setCreatedBy(currentLoginUserId);
			er.setCreationDate(new Date());
			er.setReportId(dto.getReportId());
		}	
		er.setRoutingReason(psi.getRoutingReason());
		er.setLastUpdatedBy(currentLoginUserId);
		er.setLastUpdateDate(new Date());
		hibernateTemplate.saveOrUpdate(er);
		
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	//edited on 20170224 user able to update officer's name or appraisee's name
	public void saveReportSignatureInfo(SignatureInfo sf,Long rptId,String page) throws Exception{
		String currentLoginUserId=SecurityContextHolder.getContext().getAuthentication().getName();
		EaContract ec=appraiseeCommonSearchDAO.findEaContractById(rptId);
		
		if(null==ec){
			ec=new EaContract();	
			ec.setCreatedBy(currentLoginUserId);
			ec.setCreationDate(new Date());
			ec.setReportId(rptId);
		}

		if("A".equalsIgnoreCase(page)){
			//for signature Info PartA
			ec.setPerfAppName(sf.getNameOfAppraisee());
			ec.setPerfAoName(sf.getOfficerName());
			
			ec.setPerfAppDate(sf.getAppraiseeSignDate());
			ec.setPerfAppRank(sf.getAppraiseeRankPost());
			ec.setPerfAoDate(sf.getOfficerDate());
			ec.setPerfAoRank(sf.getOfficerRank());
			
		}else if("B4".equalsIgnoreCase(page)){
			//for signature Info PartB4			
			ec.setCompAoDate(sf.getOfficerDate());
			ec.setCompAoName(sf.getOfficerName());
			ec.setCompAoRank(sf.getOfficerRank());
			ec.setCompAoTel(sf.getTel());
		}else if("B5".equalsIgnoreCase(page)){
			//for signature Info PartB5	
			//20170221
			ec.setAsseCoDate(sf.getOfficerDate());
			ec.setAsseCoName(sf.getOfficerName());
			ec.setAsseCoRank(sf.getOfficerRank());
			ec.setAsseCoTel(sf.getTel());
	
		}else if("B6".equalsIgnoreCase(page)){
			//for signature Info PartB6		
			ec.setIntvAppName(sf.getNameOfAppraisee());
			ec.setIntvAppRank(sf.getAppraiseeRankPost());
			ec.setIntvAppDate(sf.getAppraiseeSignDate());
			//debug
			log.info("B6 appraisee deadline: "+sf.getAppraiseeSignDate());
			ec.setIntvIoDate(sf.getOfficerDate());
			ec.setIntvIoName(sf.getOfficerName());
			ec.setIntvIoRank(sf.getOfficerRank());
	
		}else if("B7".equalsIgnoreCase(page)){
			if(sf!=null){
				//For SignatureInfoB7
				ec.setAsseEoDate(sf.getOfficerDate());
				ec.setAsseEoName(sf.getOfficerName());
				ec.setAsseEoRank(sf.getOfficerRank());
				ec.setAsseEoTel(sf.getTel());
			}

		}else if("B8".equalsIgnoreCase(page)){
			//For SignatureInfoB8	
			ec.setReviRoDate(sf.getOfficerDate());
			ec.setReviRoName(sf.getOfficerName());
			ec.setReviRoRank(sf.getOfficerRank());
		}  
		
		ec.setLastUpdatedBy(currentLoginUserId);
		ec.setLastUpdateDate(new Date());
		
		hibernateTemplate.saveOrUpdate(ec);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	//end edited on 20170224
	/**
	 * 
	 * Submit  Report
	 * 
	 * */
	
	public SubmitActionInfo submitReportByUpdateStatus(AssessAppraisalDTO dto)throws Exception{
		//GM submit
		String submitAction = "sent";
		String submitToEmployeeNum = "";
		String submitNextDeadDate = "";
		String submitNotificationContent = "";
		String submitToAddress = "";
		String newStatus = "";
	//	boolean bypassToAnyRecipients = ("GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus()))&&();
		EaReport er=appraiseeCommonSearchDAO.findEareportById(dto.getReportId());
		EaContract ec=appraiseeCommonSearchDAO.findEaContractById(dto.getReportId());
		List<UserEnquiryModelDTO> gmList=userEnquiryDAO.searchUserByReportIdAndFunctionId(dto.getReportId(), Constants.FUNCTION_ID_CONFIRM_REJECT_REPORT);
		
		//for GM 
		//edited on 20170301 for GM Confirm/Reject
		/*if ("AR".equalsIgnoreCase(dto.getPrintSubmitInfo().getRouting()) && "GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())) {
			if("R".equalsIgnoreCase(dto.getPrintSubmitInfo().getConfirmReject())){
				newStatus = dto.getPrintSubmitInfo().getRoutingTo();
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
		}*/

		if ("GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentReportStatus()) && "GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())) {
			if("R".equalsIgnoreCase(dto.getPrintSubmitInfo().getConfirmReject())){
				newStatus = "RO";
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
		//end edited on 20170301
		
		//for AO
		if ("AP".equalsIgnoreCase(dto.getPrintSubmitInfo().getRouting())) {
			newStatus = "AP";
			submitToEmployeeNum = er.getEmployeeNumber();
		}
		//for CO IO RO
		if("PO".equalsIgnoreCase(dto.getPrintSubmitInfo().getRouting())){
			if ("CL".equalsIgnoreCase(dto.getReportUserRole().getCurrentReportStatus())) {
				newStatus = "GM";
				submitAction = "returned";
				
				if(gmList.size()>0){
					submitToEmployeeNum = gmList.get(0).getUserId();
				 	submitToAddress = gmList.get(0).getEmailAddress();
				}
				
					
			}else if ("GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentReportStatus())) {
				newStatus = "RO";
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
		
		if("NO".equalsIgnoreCase(dto.getPrintSubmitInfo().getRouting())){
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
		
		 if ((newStatus.equals("RO")) && (!Util.isEmptyString(er.getHoId()))) {
 			 submitToEmployeeNum =er.getHoId();
 		}
		 
		//for SUGM pass report to next officer or gm
		if ("AR".equalsIgnoreCase(dto.getPrintSubmitInfo().getRouting())
				&& "SU".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())) {
			EaReportRole err = appraiseeCommonSearchDAO.searchNextOfficerRoleByIdAndCurrentRptStatus(dto.getReportId(),
					er.getStatus());

			if(null != err && StringUtil.isEmptyString(err.getOfficerId())){
				//Ststus I
				err = appraiseeCommonSearchDAO.searchNextOfficerByStatus(dto.getReportId(), er.getStatus(),null);
			}

			if (null != err) {
				newStatus = err.getRole();
				submitToEmployeeNum = err.getOfficerId();
				submitNextDeadDate = Util.date2String(err.getDeadline(), Constants.DISPLAY_DATE_FORMAT);
				submitNotificationContent = err.getNotification();

			} else {
				if ("RO".equalsIgnoreCase(er.getStatus())) {
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
		if ("AR".equalsIgnoreCase(dto.getPrintSubmitInfo().getRouting())
				&& ("SU".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())
						|| "GM".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus()))
				&& !StringUtil.isEmptyString(dto.getPrintSubmitInfo().getRoutingTo())){
			EaReportRole err = new EaReportRole();
			
			if("AP".equalsIgnoreCase(dto.getPrintSubmitInfo().getRoutingTo())){
				newStatus = "AP";
				submitToEmployeeNum = er.getEmployeeNumber();
			}else if("CD".equalsIgnoreCase(dto.getPrintSubmitInfo().getRoutingTo()) || "AO".equalsIgnoreCase(dto.getPrintSubmitInfo().getRoutingTo())
					|| "CO".equalsIgnoreCase(dto.getPrintSubmitInfo().getRoutingTo()) || "IO".equalsIgnoreCase(dto.getPrintSubmitInfo().getRoutingTo()) ||
					"RO".equalsIgnoreCase(dto.getPrintSubmitInfo().getRoutingTo())){
				err = appraiseeCommonSearchDAO.searchNextOfficerByStatus(dto.getReportId(), er.getStatus(),dto.getPrintSubmitInfo().getRoutingTo());
				if (null != err) {
					newStatus = err.getRole();
					submitToEmployeeNum = err.getOfficerId();
					submitNextDeadDate = Util.date2String(err.getDeadline(), Constants.DISPLAY_DATE_FORMAT);
					submitNotificationContent = err.getNotification();
					if("CD".equalsIgnoreCase(dto.getPrintSubmitInfo().getRoutingTo())){
						er.setAssigned("N");
					}
				}
			}else if("GM".equalsIgnoreCase(dto.getPrintSubmitInfo().getRoutingTo())){
				newStatus = "GM";
				submitNextDeadDate = Util.date2String(er.getOverallDeadline(), Constants.DISPLAY_DATE_FORMAT);
				if (gmList.size() > 0) {
					submitToEmployeeNum = gmList.get(0).getUserId();
					submitToAddress = gmList.get(0).getEmailAddress();
				}
				
			}
		}

		 if("AP".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			 //save ec
			 changeReportCompletionDateForAP(er,ec,"send",dto.getPrintSubmitInfo().getRouting(),null);
			
		}else if("AO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			 //save ec
			changeReportCompletionDateForAO(er,ec,"send",dto.getPrintSubmitInfo().getRouting(),null);
			
		}else if("CO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			 //save ec
			changeReportCompletionDateForCO(er,ec,"send",dto.getPrintSubmitInfo().getRouting(),null);
			
		}else if("IO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			 //save ec
			changeReportCompletionDateForIO(er,ec,"send",dto.getPrintSubmitInfo().getRouting(),null);
			
		}else if("RO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			 //save ec
			changeReportCompletionDateForRO(er,ec,"send",dto.getPrintSubmitInfo().getRouting(),null);
			
		}
		
		if (!Util.isEmptyString(newStatus)) {
			er.setStatus(newStatus);
			if("SU".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus()) && "AO".equalsIgnoreCase(newStatus)){
				er.setAssigned("Y");
			}
			hibernateTemplate.saveOrUpdate(er);
		}
		//debug
		log.info("submit last updated by: "+er.getLastUpdatedBy());
		
		SubmitActionInfo msi=new SubmitActionInfo();
		msi.setNewStatus(newStatus);
		msi.setSubmitAction(submitAction);
		msi.setSubmitNextDeadDate(submitNextDeadDate);
		msi.setSubmitNotificationContent(submitNotificationContent);
		msi.setSubmitToAddress(submitToAddress);
		msi.setSubmitToEmployeeNum(submitToEmployeeNum);
		return msi;
	}
	
	public void changeReportCompletionDateForAP(EaReport er,EaContract ec,String flag,String routing,Long rptId)throws Exception{
		if(null==er||null==ec){
			er=appraiseeCommonSearchDAO.findEareportById(rptId);
			ec=appraiseeCommonSearchDAO.findEaContractById(rptId);
		}
		 String stringOfToday = DateTimeUtil.date2String(new  Date(), "dd/MM/yyyy");
		if ((!Util.isEmptyString(ec.getPerfAppDate())) && 
			(!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			(stringOfToday.compareToIgnoreCase(ec.getPerfAppDate()) != 0)) {
			er.setCompletionDateCompare1("Y");
			 
		}
		if (Util.isEmptyString(er.getCompletionDateCompare1())) {
			er.setCompletionDateCompare1("N");
			
		}if ((!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			("N".compareToIgnoreCase(er.getCompletionDateCompare1()) == 0) && (!routing.equalsIgnoreCase("PO"))) {
			ec.setPerfAppDate(stringOfToday);
		}
		if("send".equals(flag)){
			er.setCompletionDateCompare1("N");
			er.setCompletionDateCompare2("N");
			hibernateTemplate.saveOrUpdate(er);
		}

		hibernateTemplate.saveOrUpdate(ec);
	}
	
	public void changeReportCompletionDateForAO(EaReport er,EaContract ec,String flag,String routing,Long rptId)throws Exception{
		if(null==er||null==ec){
			er=appraiseeCommonSearchDAO.findEareportById(rptId);
			ec=appraiseeCommonSearchDAO.findEaContractById(rptId);
		}

		 String stringOfToday = DateTimeUtil.date2String(new  Date(), "dd/MM/yyyy");
		if ((!Util.isEmptyString(ec.getPerfAoDate())) && 
			(!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			(stringOfToday.compareToIgnoreCase(ec.getPerfAoDate()) != 0)) {
			er.setCompletionDateCompare1("Y");
			 
		}
		if (Util.isEmptyString(er.getCompletionDateCompare1())) {
			er.setCompletionDateCompare1("N");
			
		}if ((!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			("N".compareToIgnoreCase(er.getCompletionDateCompare1()) == 0) && (!routing.equalsIgnoreCase("PO"))) {
			ec.setPerfAoDate(stringOfToday);
		}
		
		if ((!Util.isEmptyString(ec.getCompAoDate())) && 
				(!Util.isEmptyString(er.getCompletionDateCompare1())) && 
				(stringOfToday.compareToIgnoreCase(ec.getCompAoDate()) != 0)) {
				er.setCompletionDateCompare1("Y");
				 
			}
			if (Util.isEmptyString(er.getCompletionDateCompare1())) {
				er.setCompletionDateCompare1("N");
				
			}if ((!Util.isEmptyString(er.getCompletionDateCompare1())) && 
				("N".compareToIgnoreCase(er.getCompletionDateCompare1()) == 0) && (!routing.equalsIgnoreCase("PO"))) {
				ec.setCompAoDate(stringOfToday);
			}
		
		//if user has updated/ changed the report, the completion date should be change as well	
		Date compAoDate = DateTimeUtil.string2Date(ec.getCompAoDate());
		Date today = DateTimeUtil.string2Date(stringOfToday);

		if(!Util.isEmptyString(ec.getCompAoDate()) && today.compareTo(compAoDate)>0){
			ec.setCompAoDate(stringOfToday);
		}
		
		if("send".equals(flag)){
			er.setCompletionDateCompare1("N");
			er.setCompletionDateCompare2("N");
			hibernateTemplate.saveOrUpdate(er);
		}

		hibernateTemplate.saveOrUpdate(ec);
	}
	public void changeReportCompletionDateForCO(EaReport er,EaContract ec,String flag,String routing,Long rptId)throws Exception{
		if(null==er||null==ec){
			er=appraiseeCommonSearchDAO.findEareportById(rptId);
			ec=appraiseeCommonSearchDAO.findEaContractById(rptId);
		}
		
		 String stringOfToday = DateTimeUtil.date2String(new  Date(), "dd/MM/yyyy");
		if ((!Util.isEmptyString(ec.getAsseCoDate())) && 
			(!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			(stringOfToday.compareToIgnoreCase(ec.getAsseCoDate()) != 0)) {
			er.setCompletionDateCompare1("Y");
			 
		}
		if (Util.isEmptyString(er.getCompletionDateCompare1())) {
			er.setCompletionDateCompare1("N");
			
		}if ((!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			("N".compareToIgnoreCase(er.getCompletionDateCompare1()) == 0) && (!routing.equalsIgnoreCase("PO"))) {
			ec.setAsseCoDate(stringOfToday);
		}

		//if user has updated/ changed the report, the completion date should be change as well	
		Date asseCoDate = DateTimeUtil.string2Date(ec.getAsseCoDate());
		Date getAoDate= DateTimeUtil.string2Date(ec.getCompAoDate());
		if(!Util.isEmptyString(ec.getAsseCoDate()) && getAoDate.compareTo(asseCoDate)>0){
			ec.setCompAoDate(stringOfToday);
		}
		
		if("send".equals(flag)){
			er.setCompletionDateCompare1("N");
			er.setCompletionDateCompare2("N");
			hibernateTemplate.saveOrUpdate(er);
		}
	
		hibernateTemplate.saveOrUpdate(ec);
	}
	public void changeReportCompletionDateForIO(EaReport er,EaContract ec,String flag,String routing,Long rptId)throws Exception{
		if(null==er||null==ec){
			er=appraiseeCommonSearchDAO.findEareportById(rptId);
			ec=appraiseeCommonSearchDAO.findEaContractById(rptId);
		}
		//added on 20170108 PartB6 Appraisee Date issue
		String tempIntvAppdate = ec.getIntvAppDate();
		//end added on 20170108 PartB6 Appraisee Date issue
		 String stringOfToday = DateTimeUtil.date2String(new  Date(), "dd/MM/yyyy");
		if ((!Util.isEmptyString(ec.getIntvAppDate())) && 
			(!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			(stringOfToday.compareToIgnoreCase(ec.getIntvAppDate()) != 0)) {
			er.setCompletionDateCompare1("Y");
			 
		}
		if (Util.isEmptyString(er.getCompletionDateCompare1())) {
			er.setCompletionDateCompare1("N");
			
		}if ((!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			("N".compareToIgnoreCase(er.getCompletionDateCompare1()) == 0) && (!routing.equalsIgnoreCase("PO"))) {
			//added on 20170108 PartB6 Appraisee Date issue
			if (StringUtil.isEmptyString(tempIntvAppdate)){
				ec.setIntvAppDate(stringOfToday);
			}else{
				ec.setIntvAppDate(tempIntvAppdate);
			}
			//end added on 20170108 PartB6 Appraisee Date issue
		}
		
		if ((!Util.isEmptyString(ec.getIntvIoDate())) && 
				(!Util.isEmptyString(er.getCompletionDateCompare1())) && 
				(stringOfToday.compareToIgnoreCase(ec.getIntvIoDate()) != 0)) {
				er.setCompletionDateCompare1("Y");
				 
			}
			if (Util.isEmptyString(er.getCompletionDateCompare1())) {
				er.setCompletionDateCompare1("N");
				
			}if ((!Util.isEmptyString(er.getCompletionDateCompare1())) && 
				("N".compareToIgnoreCase(er.getCompletionDateCompare1()) == 0) && (!routing.equalsIgnoreCase("PO"))) {
				ec.setIntvIoDate(stringOfToday);
			}

			//if user has updated/ changed the report, the completion date should be change as well	
			Date intvAppDate = DateTimeUtil.string2Date(ec.getIntvAppDate());
			Date intvIODate = DateTimeUtil.string2Date(ec.getIntvIoDate());
			Date asseCoDate = DateTimeUtil.string2Date(ec.getAsseCoDate());
			if((!Util.isEmptyString(ec.getIntvAppDate()) && asseCoDate.compareTo(intvAppDate)>0)){
				//edited on 20170108 PartB6 Appraisee Date issue
				//ec.setIntvAppDate(stringOfToday);
				ec.setIntvAppDate(tempIntvAppdate);
				//end edited on 20170108 PartB6 Appraisee Date issue
			}
			if(!Util.isEmptyString(ec.getIntvIoDate()) && asseCoDate.compareTo(intvIODate)>0){
				ec.setIntvIoDate(stringOfToday);
			}

		if("send".equals(flag)){
			er.setCompletionDateCompare1("N");
			er.setCompletionDateCompare2("N");
			hibernateTemplate.saveOrUpdate(er);
		}
	
		hibernateTemplate.saveOrUpdate(ec);
	}
	
	public void changeReportCompletionDateForRO(EaReport er,EaContract ec,String flag,String routing,Long rptId)throws Exception{
		
		if(null==er||null==ec){
			er=appraiseeCommonSearchDAO.findEareportById(rptId);
			ec=appraiseeCommonSearchDAO.findEaContractById(rptId);
		}
		
		 String stringOfToday = DateTimeUtil.date2String(new  Date(), "dd/MM/yyyy");
		if ((!Util.isEmptyString(ec.getReviRoDate())) && 
			(!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			(stringOfToday.compareToIgnoreCase(ec.getReviRoDate()) != 0)) {
			er.setCompletionDateCompare1("Y");
			 
		}
		if (Util.isEmptyString(er.getCompletionDateCompare1())) {
			er.setCompletionDateCompare1("N");
			
		}if ((!Util.isEmptyString(er.getCompletionDateCompare1())) && 
			("N".compareToIgnoreCase(er.getCompletionDateCompare1()) == 0) && (!routing.equalsIgnoreCase("PO"))) {
			ec.setReviRoDate(stringOfToday);
		}
		
		//if user has updated/changed the report, the completion date should be change as well	
		Date intvIoDate = DateTimeUtil.string2Date(ec.getIntvIoDate());
		Date getReviRoDate= DateTimeUtil.string2Date(ec.getReviRoDate());
		log.info("getReviRoDate: "+ec.getReviRoDate()+" intvIoDate: "+intvIoDate+" getReviRoDate: "+getReviRoDate);
		if(!Util.isEmptyString(ec.getReviRoDate()) && intvIoDate.compareTo(getReviRoDate)>0){
			ec.setReviRoDate(stringOfToday);
		}
		
		if("send".equals(flag)){
			er.setCompletionDateCompare1("N");
			er.setCompletionDateCompare2("N");
			hibernateTemplate.saveOrUpdate(er);
		}
	
		hibernateTemplate.saveOrUpdate(ec);
	}
	

}
