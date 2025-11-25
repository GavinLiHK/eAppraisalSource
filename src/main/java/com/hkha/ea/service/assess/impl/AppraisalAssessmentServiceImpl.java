package com.hkha.ea.service.assess.impl;

import java.util.List;
import java.util.Map;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkha.ea.common.StringUtil;
import com.hkha.ea.dao.assess.AppraisalAssessmentDAO;
import com.hkha.ea.dao.assess.AppraisalAssessmentMemoDAO;
import com.hkha.ea.dao.assess.AppraisalAssessmentReportDAO;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.domain.EaWorkflow;
import com.hkha.ea.dto.assess.AssessAppraisalDTO;
import com.hkha.ea.dto.assess.AssessAppraisalMemoDTO;
import com.hkha.ea.dto.assess.CoreCompetencyInfo;
import com.hkha.ea.dto.assess.PartB4Info;
import com.hkha.ea.dto.assess.PrintSubmitInfo;
import com.hkha.ea.dto.assess.SubmitActionInfo;
import com.hkha.ea.dto.common.ReportUserRole;
import com.hkha.ea.service.assess.AppraisalAssessmentService;

import jakarta.servlet.http.HttpServletRequest;

@Service("appraisalAssessmentService")
public class AppraisalAssessmentServiceImpl implements AppraisalAssessmentService{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AppraiseeBatchConverAndGenTableServiceImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private AppraisalAssessmentMemoDAO appraisalAssessmentMemoDAO;
	
	@Autowired
	private AppraisalAssessmentReportDAO appraisalAssessmentReportDAO;
	
	@Autowired
	private AppraisalAssessmentDAO appraisalAssessmentDAO;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	/**
	 * Search Assess Appraisal Report 
	 * **/
	public AssessAppraisalDTO searchAssessAppraisalReportById(String reportId,String reportType,boolean isEORoleChecked,HttpServletRequest request){
		long id=Long.parseLong(reportId);
		AssessAppraisalDTO dto=appraisalAssessmentReportDAO.searchAppraisalAssessmentFullReportById(id, request);
		dto.setPages(this.convertPagesForAssessAppraisalReportAndMemo(dto.getReportUserRole(), reportType,isEORoleChecked));
		return dto;
	}
	
	/**
	 * Search Assess Appraisal Memo 
	 * **/
	public AssessAppraisalMemoDTO searchAssessAppraisalMemoById(String reportId,String reportType,String btl,HttpServletRequest request){
		long id=Long.parseLong(reportId);
		AssessAppraisalMemoDTO dto=appraisalAssessmentMemoDAO.searchAppraisalAssessmentMemoById(id,request);
		dto.setBackToListType(btl);
		dto.setReportType(reportType);
		dto.setPages(this.convertPagesForAssessAppraisalReportAndMemo(dto.getReportUserRole(), reportType,false));
		return dto;
	}
	
	/**
	 * Saving Assess Appraisal Memo 
	 * **/
	
	@Transactional(rollbackFor=Exception.class)
	public String saveAssessAppraisalMemo(AssessAppraisalMemoDTO dto){
		String saveFlag="";
		String currentPageNo=dto.getCurrentPage();
		Map<String,String> pagesMap=appraisalAssessmentDAO.getAssessAppraisalMemoPageMapsByRole(dto.getReportUserRole().getCurrentUserRoleStatus());
		String currentPage=pagesMap.get(currentPageNo);
		Long rptId=dto.getReportId();
		try {
			if("P1".equalsIgnoreCase(currentPage)){
				appraisalAssessmentMemoDAO.saveMemoInfo(dto.getMemoInfo(),rptId);
				appraisalAssessmentMemoDAO.saveMemoPart1Info(dto.getMemoPart1Info(),rptId);
				
			}else if("P2".equalsIgnoreCase(currentPage)){
				appraisalAssessmentMemoDAO.saveMemoInfo(dto.getMemoInfo(),rptId);
				appraisalAssessmentMemoDAO.saveMemoPart2Part5Info(dto.getMemoPart2Info(),rptId,currentPage);
				
			}else if("P3".equalsIgnoreCase(currentPage)){
				appraisalAssessmentMemoDAO.saveMemoInfo(dto.getMemoInfo(),rptId);
				appraisalAssessmentMemoDAO.saveMemoPart3Part4Info(dto.getMemoPart3Info(),rptId,currentPage);
				appraisalAssessmentMemoDAO.saveMemoSignatureInfo(dto.getSignatureInfoMemoP3(),rptId,currentPage);
				
			}else if("P4".equalsIgnoreCase(currentPage)){
				appraisalAssessmentMemoDAO.saveMemoInfo(dto.getMemoInfo(),rptId);
				appraisalAssessmentMemoDAO.saveMemoPart3Part4Info(dto.getMemoPart4Info(),rptId,currentPage);
				appraisalAssessmentMemoDAO.saveMemoSignatureInfo(dto.getSignatureInfoMemoP4(),rptId,currentPage);
				
			}else if("P5".equalsIgnoreCase(currentPage)){
				appraisalAssessmentMemoDAO.saveMemoInfo(dto.getMemoInfo(),rptId);
				appraisalAssessmentMemoDAO.saveMemoPart2Part5Info(dto.getMemoPart5Info(),rptId,currentPage);
				appraisalAssessmentMemoDAO.saveMemoSignatureInfo(dto.getSignatureInfoMemoP5(),rptId,currentPage);
			}else if("PS".equalsIgnoreCase(currentPage)){
				appraisalAssessmentMemoDAO.saveMemoInfo(dto.getMemoInfo(),rptId);
				appraisalAssessmentMemoDAO.saveMemoPrintSubmitInfo(dto.getMemoPrintSubmitInfo(),rptId);
				if("AO".equals(dto.getReportUserRole().getCurrentReportStatus())||"CO".equals(dto.getReportUserRole().getCurrentReportStatus())){
					appraisalAssessmentDAO.changeNextOfficer(dto.getMemoPrintSubmitInfo(),dto.getReportUserRole(),dto.getReportId());
				}
				
			}
			saveFlag="success";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			log.severe(e.toString());
			saveFlag="fail";
		}
			
		return saveFlag;	
	}
	
	
	/**
	 * Save before send appraisal memo
	 * **/
	@Transactional(rollbackFor=Exception.class)
	public boolean saveAssessAppraisalMemoBeforeSubmit(AssessAppraisalMemoDTO dto){
		boolean saveFlag=false;
		Long rptId=dto.getReportId();
		String nextRole = dto.getReportUserRole().getNextOfficerRole();
		
		try{
			
			appraisalAssessmentMemoDAO.saveMemoInfo(dto.getMemoInfo(),rptId);
			//P1
			appraisalAssessmentMemoDAO.saveMemoPart1Info(dto.getMemoPart1Info(),rptId);
			//P2
			appraisalAssessmentMemoDAO.saveMemoPart2Part5Info(dto.getMemoPart2Info(),rptId,"P2");
			//PS
			appraisalAssessmentMemoDAO.saveMemoPrintSubmitInfo(dto.getMemoPrintSubmitInfo(),rptId);
			
			//20170221 set Officer name on signature info
			if(nextRole.equalsIgnoreCase("CO")&&StringUtil.isEmptyString(dto.getSignatureInfoMemoP4().getOfficerName())){
				dto.getSignatureInfoMemoP4().setOfficerName(dto.getMemoPrintSubmitInfo().getNextOfficerName());
				appraisalAssessmentMemoDAO.saveMemoSignatureInfo(dto.getSignatureInfoMemoP4(),rptId,"P4");
			}else if(nextRole.equalsIgnoreCase("IO")&&StringUtil.isEmptyString(dto.getSignatureInfoMemoP5().getOfficerName())){
				dto.getSignatureInfoMemoP5().setOfficerName(dto.getMemoPrintSubmitInfo().getNextOfficerName());
				appraisalAssessmentMemoDAO.saveMemoSignatureInfo(dto.getSignatureInfoMemoP5(),rptId,"P5");
			}
			
			
			if("AO".equals(dto.getReportUserRole().getCurrentReportStatus())||"CO".equals(dto.getReportUserRole().getCurrentReportStatus())){
				appraisalAssessmentDAO.changeNextOfficer(dto.getMemoPrintSubmitInfo(),dto.getReportUserRole(),dto.getReportId());
			}
			
			if("AO".equals(dto.getReportUserRole().getCurrentReportStatus())||"CO".equals(dto.getReportUserRole().getCurrentReportStatus())||
			   "IO".equals(dto.getReportUserRole().getCurrentReportStatus())||"GM".equals(dto.getReportUserRole().getCurrentReportStatus())||
			   "GM".equals(dto.getReportUserRole().getCurrentUserRoleStatus())||"SU".equals(dto.getReportUserRole().getCurrentUserRoleStatus())){
				//P3
				appraisalAssessmentMemoDAO.saveMemoPart3Part4Info(dto.getMemoPart3Info(),rptId,"P3");
				appraisalAssessmentMemoDAO.saveMemoSignatureInfo(dto.getSignatureInfoMemoP3(),rptId,"P3");	
			}
			if("CO".equals(dto.getReportUserRole().getCurrentReportStatus())||"IO".equals(dto.getReportUserRole().getCurrentReportStatus())||
				"GM".equals(dto.getReportUserRole().getCurrentReportStatus())||"GM".equals(dto.getReportUserRole().getCurrentUserRoleStatus())||
				"SU".equals(dto.getReportUserRole().getCurrentUserRoleStatus())){
				//P4	
				appraisalAssessmentMemoDAO.saveMemoPart3Part4Info(dto.getMemoPart4Info(),rptId,"P4");
				appraisalAssessmentMemoDAO.saveMemoSignatureInfo(dto.getSignatureInfoMemoP4(),rptId,"P4");
			}
			if("IO".equals(dto.getReportUserRole().getCurrentReportStatus())||"GM".equals(dto.getReportUserRole().getCurrentReportStatus())||
			   "GM".equals(dto.getReportUserRole().getCurrentUserRoleStatus())||"SU".equals(dto.getReportUserRole().getCurrentUserRoleStatus())){
				//P5
				appraisalAssessmentMemoDAO.saveMemoPart2Part5Info(dto.getMemoPart5Info(),rptId,"P5");
				appraisalAssessmentMemoDAO.saveMemoSignatureInfo(dto.getSignatureInfoMemoP5(),rptId,"P5");
			}
			
			saveFlag = true;
		}catch(Exception e){
			e.printStackTrace();
			log.severe(e.toString());
			saveFlag = false;
		}
		return saveFlag;
	}
	
	
	/**
	 * Submit and send Assess Appraisal Memo 
	 * **/
	
	@Transactional(rollbackFor=Exception.class)
	public String sendAssessAppraisalMemo(AssessAppraisalMemoDTO dto)throws Exception{
		String saveFlag="";
		boolean success=false;
		boolean saveSuccess=false;
		
		saveFlag=this.saveAssessAppraisalMemo(dto);
		
		if("success".equals(saveFlag)){
			saveSuccess=true;
		}

		SubmitActionInfo msi=appraisalAssessmentMemoDAO.submitMemoByUpdateStatus(dto);
		
		if("AO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			appraisalAssessmentDAO.changeNextOfficer(dto.getMemoPrintSubmitInfo(),dto.getReportUserRole(),dto.getReportId());				
		}else if("CO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			appraisalAssessmentDAO.changeNextOfficer(dto.getMemoPrintSubmitInfo(),dto.getReportUserRole(),dto.getReportId());
		}
		
		success=appraisalAssessmentDAO.sendEmailAndAduitLog(dto.getReportId(),dto.getReportUserRole().getCurrentUserRoleStatus(),"memo",msi);
			
		if(success&&saveSuccess){
			saveFlag="sendSuccess";
		}else{
			saveFlag="sendFail";
		}

		return saveFlag;	
	}
	
	/**
	 * Saving Assess Appraisal Report 
	 * **/
	@Transactional(rollbackFor=Exception.class)
	public String saveAssessAppraisalReport(AssessAppraisalDTO dto){
		String saveFlag="";
		String currentPageNo=dto.getCurrentPage();
		Map<String,String> pagesMap=appraisalAssessmentDAO.getAssessAppraisalReportPageMapsByRole(dto.getReportUserRole().getCurrentUserRoleStatus());
		String currentPage=pagesMap.get(currentPageNo);
		Long rptId=dto.getReportId();
		
		try {
			if("PI".equalsIgnoreCase(currentPage)){			
				appraisalAssessmentReportDAO.saveReportPersonalInfo(dto);
				
			}else if("A".equalsIgnoreCase(currentPage)){				
				appraisalAssessmentReportDAO.saveReportPartAInfo(dto, "save");
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoA(), rptId, currentPage);
				
			}else if("B1".equalsIgnoreCase(currentPage)){
				appraisalAssessmentReportDAO.saveReportPartB1Info(dto);
				
			}else if("B2".equalsIgnoreCase(currentPage)){
				appraisalAssessmentReportDAO.saveReportPartB2Info(dto);
				
			}else if("B3".equalsIgnoreCase(currentPage)){
				appraisalAssessmentReportDAO.saveReportPartB3Info(dto);
				
			}else if("B4".equalsIgnoreCase(currentPage)){
				appraisalAssessmentReportDAO.saveReportPartB4Info(dto);
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB4(), rptId, currentPage);
			}else if("B5".equalsIgnoreCase(currentPage)){
				appraisalAssessmentReportDAO.saveReportPartB5Info(dto);
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB5(), rptId, currentPage);
			}else if("B6".equalsIgnoreCase(currentPage)){
				appraisalAssessmentReportDAO.saveReportPartB6Info(dto);
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB6(), rptId, currentPage);
			}else if("B7".equalsIgnoreCase(currentPage)){
				appraisalAssessmentReportDAO.saveReportPartB7Info(dto);
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB7(), rptId, currentPage);
				
			}else if("B8".equalsIgnoreCase(currentPage)){
				appraisalAssessmentReportDAO.saveReportPartB8Info(dto);
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB8(), rptId, currentPage);
				
			}else if("PS".equalsIgnoreCase(currentPage)){
				appraisalAssessmentReportDAO.saveReportPrintSubmitInfo(dto);
				if("AO".equals(dto.getReportUserRole().getCurrentReportStatus())||"CO".equals(dto.getReportUserRole().getCurrentReportStatus())){
					appraisalAssessmentDAO.changeNextOfficer(dto.getPrintSubmitInfo(),dto.getReportUserRole(),dto.getReportId());
				}
			}
			
			 if("AP".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				 //save ec
				 appraisalAssessmentReportDAO.changeReportCompletionDateForAP(null,null,"save","",dto.getReportId());
				
			}else if("AO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				 //save ec
				appraisalAssessmentReportDAO.changeReportCompletionDateForAO(null,null,"save","",dto.getReportId());
				
			}else if("CO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				 //save ec
				appraisalAssessmentReportDAO.changeReportCompletionDateForCO(null,null,"save","",dto.getReportId());
				
			}else if("IO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				 //save ec
				appraisalAssessmentReportDAO.changeReportCompletionDateForIO(null,null,"save","",dto.getReportId());
				
			}else if("RO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				 //save ec
				appraisalAssessmentReportDAO.changeReportCompletionDateForRO(null,null,"save","",dto.getReportId());
				
			}
			saveFlag="success";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			log.severe(e.toString());
			saveFlag="fail";
		}
			
		return saveFlag;	
	}
	
	
	/**
	 * Save report before send Appraisal Report
	 * **/
	@Transactional(rollbackFor=Exception.class)
	public boolean saveAssessAppraisalReportBeforeSubmit(AssessAppraisalDTO dto, String mode){
		boolean saveFlag = false;	
		Long rptId=dto.getReportId();
		String nextRole = dto.getReportUserRole().getNextOfficerRole();
		try{
			
			//PI
			appraisalAssessmentReportDAO.saveReportPersonalInfo(dto);
			//A
			appraisalAssessmentReportDAO.saveReportPartAInfo(dto, mode);
			appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoA(), rptId, "A");
			//PS
			appraisalAssessmentReportDAO.saveReportPrintSubmitInfo(dto);
			if("AO".equals(dto.getReportUserRole().getCurrentReportStatus())||"CO".equals(dto.getReportUserRole().getCurrentReportStatus())){
				appraisalAssessmentDAO.changeNextOfficer(dto.getPrintSubmitInfo(),dto.getReportUserRole(),dto.getReportId());
			}
			//20170221 set Officer name on signature info
			if(nextRole.equalsIgnoreCase("CO")&&StringUtil.isEmptyString(dto.getSignatureInfoB5().getOfficerName())){
				dto.getSignatureInfoB5().setOfficerName(dto.getPrintSubmitInfo().getNextOfficerName());
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB5(), rptId, "B5");
			}else if(nextRole.equalsIgnoreCase("IO")&&StringUtil.isEmptyString(dto.getSignatureInfoB6().getOfficerName())){
				dto.getSignatureInfoB6().setOfficerName(dto.getPrintSubmitInfo().getNextOfficerName());
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB6(), rptId, "B6");	
			}

			if("AO".equals(dto.getReportUserRole().getCurrentReportStatus()) || "CO".equals(dto.getReportUserRole().getCurrentReportStatus())||
				"IO".equals(dto.getReportUserRole().getCurrentReportStatus()) || "EO".equals(dto.getReportUserRole().getCurrentReportStatus())||
				"RO".equals(dto.getReportUserRole().getCurrentReportStatus()) || "GM".equals(dto.getReportUserRole().getCurrentReportStatus()) ||
				"GM".equals(dto.getReportUserRole().getCurrentUserRoleStatus())||"SU".equals(dto.getReportUserRole().getCurrentUserRoleStatus())){
				//B1
				appraisalAssessmentReportDAO.saveReportPartB1Info(dto);
				//B2
				appraisalAssessmentReportDAO.saveReportPartB2Info(dto);
				//B3
				appraisalAssessmentReportDAO.saveReportPartB3Info(dto);
				//B4
				try{
					if(dto.getPartB4Info() != null){
						appraisalAssessmentReportDAO.saveReportPartB4Info(dto);
						appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB4(), rptId, "B4");
					}
				}catch(Exception ex){
					log.info("Part B-4 "+ dto.getPartB4Info());
				}

			}

			if ("CO".equals(dto.getReportUserRole().getCurrentReportStatus()) || "IO".equals(dto.getReportUserRole().getCurrentReportStatus())||
				"EO".equals(dto.getReportUserRole().getCurrentReportStatus()) || "RO".equals(dto.getReportUserRole().getCurrentReportStatus())|| 
				"GM".equals(dto.getReportUserRole().getCurrentReportStatus()) || "GM".equals(dto.getReportUserRole().getCurrentUserRoleStatus())||
				"SU".equals(dto.getReportUserRole().getCurrentUserRoleStatus())) {
				//B5
				appraisalAssessmentReportDAO.saveReportPartB5Info(dto);
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB5(), rptId, "B5");
			}
			
			if ("IO".equals(dto.getReportUserRole().getCurrentReportStatus()) ||"EO".equals(dto.getReportUserRole().getCurrentReportStatus()) || 
				"RO".equals(dto.getReportUserRole().getCurrentReportStatus()) || "GM".equals(dto.getReportUserRole().getCurrentReportStatus())|| 
				"GM".equals(dto.getReportUserRole().getCurrentUserRoleStatus())||"SU".equals(dto.getReportUserRole().getCurrentUserRoleStatus())) {
				//B6
				appraisalAssessmentReportDAO.saveReportPartB6Info(dto);
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB6(), rptId, "B6");	
			}
			
			if ("EO".equals(dto.getReportUserRole().getCurrentReportStatus()) || "RO".equals(dto.getReportUserRole().getCurrentReportStatus()) 
				|| "GM".equals(dto.getReportUserRole().getCurrentReportStatus())|| "GM".equals(dto.getReportUserRole().getCurrentUserRoleStatus())||
				"SU".equals(dto.getReportUserRole().getCurrentUserRoleStatus())) {
				//B7
				appraisalAssessmentReportDAO.saveReportPartB7Info(dto);
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB7(), rptId, "B7");
			}
			
			if ("RO".equals(dto.getReportUserRole().getCurrentReportStatus()) || "GM".equals(dto.getReportUserRole().getCurrentReportStatus())
				|| "GM".equals(dto.getReportUserRole().getCurrentUserRoleStatus())|| "SU".equals(dto.getReportUserRole().getCurrentUserRoleStatus())) {
				//B8
				appraisalAssessmentReportDAO.saveReportPartB8Info(dto);
				appraisalAssessmentReportDAO.saveReportSignatureInfo(dto.getSignatureInfoB8(), rptId, "B8");
			}

			
			 if("AP".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				 //save ec
				 appraisalAssessmentReportDAO.changeReportCompletionDateForAP(null,null,"save","",dto.getReportId());
				
			}else if("AO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				 //save ec
				appraisalAssessmentReportDAO.changeReportCompletionDateForAO(null,null,"save","",dto.getReportId());
				
			}else if("CO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				 //save ec
				appraisalAssessmentReportDAO.changeReportCompletionDateForCO(null,null,"save","",dto.getReportId());
				
			}else if("IO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				 //save ec
				appraisalAssessmentReportDAO.changeReportCompletionDateForIO(null,null,"save","",dto.getReportId());
				
			}else if("RO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
				 //save ec
				appraisalAssessmentReportDAO.changeReportCompletionDateForRO(null,null,"save","",dto.getReportId());
				
			}
			
			saveFlag=true;
		}catch(Exception e){
			e.printStackTrace();
			log.severe(e.toString());
			saveFlag = false;
		}
		return saveFlag;
	}
	
	/**
	 * Submit and send Assess Appraisal Memo 
	 * **/
	
	@Transactional(rollbackFor=Exception.class)
	public String sendAssessAppraisalReport(AssessAppraisalDTO dto)throws Exception{
		String saveFlag="";
		boolean success=false;
	
		SubmitActionInfo msi=appraisalAssessmentReportDAO.submitReportByUpdateStatus(dto);
		if("AO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			appraisalAssessmentDAO.changeNextOfficer(dto.getPrintSubmitInfo(),dto.getReportUserRole(),dto.getReportId());			
		}else if("CO".equalsIgnoreCase(dto.getReportUserRole().getCurrentUserRoleStatus())){
			appraisalAssessmentDAO.changeNextOfficer(dto.getPrintSubmitInfo(),dto.getReportUserRole(),dto.getReportId());	
		}
		
		success=appraisalAssessmentDAO.sendEmailAndAduitLog(dto.getReportId(),dto.getReportUserRole().getCurrentUserRoleStatus(),"report",msi);
		
		if(success){
			saveFlag="sendSuccess";
		}else{
			saveFlag="sendFail";
		}

		return saveFlag;	
	}
	
	public AssessAppraisalDTO genCCInfoListForPartB4(AssessAppraisalDTO dto){
		List<CoreCompetencyInfo>ccInfoList=appraisalAssessmentReportDAO.genCCInfoListForPartB4(dto.getReportId());	
		//20161128
		try{
			for(int i=0;i<dto.getPartB4Info().getCcInfoList().size();i++){
				String rating = dto.getPartB4Info().getCcInfoList().get(i).getRating();
				ccInfoList.get(i).setRating(rating);
			}
			
		}catch(Exception ex){
			log.info("ccInfoList is null.");
		}
		
		PartB4Info pb4=dto.getPartB4Info();
		if(null==pb4){
			pb4=new PartB4Info();	
		}
		pb4.setCcInfoList(ccInfoList);
		dto.setPartB4Info(pb4);
		return dto;
	}
	
	public String searchReportTemplateByReportId(Long rptId){
		String flag="";
		EaWorkflow ewf=appraiseeCommonSearchDAO.searchReportTemplateByReportId(rptId);
		if(null!=ewf){
			flag=ewf.getReportTemplate();
		}
		return flag;
	}
	
	public PrintSubmitInfo genRoutingLabelAndRoutingToListForPrintSubmit(PrintSubmitInfo psi,String userRole){
		 psi=appraisalAssessmentDAO.genRoutingLabelAndRoutingToListForPrintSubmit(psi,userRole);
		//dto.setPrintSubmitInfo(psi);
		return psi;
	}
	
	public int convertPagesForAssessAppraisalReportAndMemo(ReportUserRole rur,String reportType,boolean isEORoleChecked){
		int pages=appraisalAssessmentDAO.convertPagesForAssessAppraisalReportAndMemo(rur, reportType,isEORoleChecked);
		return pages;
	}
	
	public ReportUserRole getReportUserRoleByReportIdAndUserId(Long rptId,String currentUserId){
		ReportUserRole rur=appraisalAssessmentDAO.getReportUserRoleByReportIdAndUserId(rptId, currentUserId);
		return rur;
	}
	
	
	
}
