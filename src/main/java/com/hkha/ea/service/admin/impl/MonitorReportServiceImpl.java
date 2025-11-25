package com.hkha.ea.service.admin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.DateTimeUtil;
import com.hkha.ea.common.Util;
import com.hkha.ea.dao.admin.MonitorReportDAO;
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.security.UserEnquiryDAO;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.dto.admin.MonitorReportDto;
import com.hkha.ea.dto.admin.MonitorReportVo;
import com.hkha.ea.dto.common.MessageLogDto;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;
import com.hkha.ea.service.admin.MonitorReportService;

@Service("monitorReportService")
public class MonitorReportServiceImpl implements MonitorReportService{
	
	@Autowired
	private MonitorReportDAO monitorReportDAO;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	@Autowired
	private UserEnquiryDAO userEnquiryDAO;

	public List<MonitorReportDto> search(String rank, String employeeNumber, String postUnit, String sortSequence) {
		
		//20231201 Write log in catalina.out
//		private Log log = LogFactory.getLog(this.getClass());
		Logger log = Logger.getLogger(MonitorReportServiceImpl.class.getName());
		//End 20231201 Write log in catalina.out
		
		List<MonitorReportVo> dsPrimary = monitorReportDAO.searchAll( rank,  employeeNumber,  postUnit,  sortSequence);
		if(dsPrimary != null && dsPrimary.size() >0){
			for(int i=0; i<dsPrimary.size(); i++){
				MonitorReportVo vo = dsPrimary.get(i);
				if(vo != null){
					if("AP".equals(vo.getSpeReport())){
						List<EaMessageLog> msg = monitorReportDAO.searchLogFromReportIDAndStatus(vo.getReportId(),vo.getStatus());
						if(msg != null && msg.size() >0)
							vo.setRemSent(msg.size());
						else
							vo.setRemSent(0);
						vo.setNoteInfo(getNotificationInfo(vo.getReportId()));
						//vo.setRole1(vo.getStatus());
					}else if("GM".equals(vo.getSpeReport())){
						long reportID = vo.getReportId();
						String status = vo.getStatus();
						
						if(Constants.STATUS_INITIAL.equals(status)){
							//vo.setRole1("Initial");
							vo.setEmailAddress("N");
							vo.setRemSent(0);
							vo.setNoteInfo(getNotificationInfo(reportID));
						}else{
							//vo.setRole1(Constants.STATUS_GM);
							List<UserEnquiryModelDTO> user = userEnquiryDAO.searchGradeManagmentByReportID(reportID);
							if(user != null && user.size() >0){
								//vo.setPeopleEmplyNum(user.get(0).getEmployeeNumber());
								//vo.setFullName(user.get(0).getFullName());
								//vo.setSubstantiveRank(user.get(0).getEngRank());
								// Set available email flag for GM
								if (Util.isEmptyString(user.get(0).getEmailAddress()))
									vo.setEmailAddress("N");
								else
									vo.setEmailAddress("Y");
							}
							List<EaMessageLog> msg = monitorReportDAO.searchLogFromReportIDAndStatus(reportID,status);
							if(msg != null && msg.size() >0)
								vo.setRemSent(msg.size());
							else
								vo.setRemSent(0);
							vo.setNoteInfo(getNotificationInfo(reportID));
						}
					}
				}
			}
		}
		/*List<MonitorReportVo> dsPrimary = monitorReportDAO.search( rank,  employeeNumber,  postUnit,  sortSequence);
		if(dsPrimary == null || (dsPrimary != null && dsPrimary.size() < 0))
			dsPrimary = new ArrayList<MonitorReportVo>();
		
		// Search Appraisee Record
		List<MonitorReportVo> dsSearchAP = monitorReportDAO.searchAP( rank,  employeeNumber,  postUnit,  sortSequence);
		// Copy Appraisee Record to main Dataset
		if(dsSearchAP != null && dsSearchAP.size() >0){
			for(int i=0; i<dsSearchAP.size(); i++){
				MonitorReportVo ap = dsSearchAP.get(i);
				if(ap != null){
					List<EaMessageLog> msg = monitorReportDAO.searchLogFromReportIDAndStatus(ap.getReportId(),ap.getStatus());
					if(msg != null && msg.size() >0)
						ap.setRemSent(msg.size());
					else
						ap.setRemSent(0);
					ap.setNoteInfo(getNotificationInfo(ap.getReportId()));
					dsPrimary.add(ap);
				}
			}
		}
		
		// Copy GM and Initial Records to main dataset
		List<EaReport> rp = appraiseeCommonSearchDAO.searchSpecialReports(rank,  postUnit);
		if(rp != null && rp.size() >0){
			for(int i=0; i<rp.size(); i++){
				EaReport rpt = rp.get(i);
				if(rpt != null){
					MonitorReportVo dto = this.copyDataFormReport(i,rpt);
					dsPrimary.add(dto);
				}
			}
		}*/
		List<MonitorReportDto> dto = this.copyDataFromVo(dsPrimary);
		return dto;
	}
	
	private List<MonitorReportDto> copyDataFromVo(List<MonitorReportVo> dsPrimary) {
		List<MonitorReportDto> list = new ArrayList<MonitorReportDto>();
		if(dsPrimary != null && dsPrimary.size() > 0){
			for(int i=0; i<dsPrimary.size(); i++){
				MonitorReportVo vo = dsPrimary.get(i);
				MonitorReportDto dto = new MonitorReportDto();
				 BeanUtils.copyProperties(vo,dto);
				 dto.setCommenceDate(DateTimeUtil.date2String(vo.getCommenceDate()));
				 dto.setEndDate(DateTimeUtil.date2String(vo.getEndDate()));
				 dto.setOverallDeadLine(DateTimeUtil.date2String(vo.getOverallDeadLine()));
				 dto.setDeadLine(DateTimeUtil.date2String(vo.getDeadLine()));
				 
				 list.add(dto);
			}
		}
		return list;
	}

	private MonitorReportVo copyDataFormReport(int i, EaReport rpt) {
		MonitorReportVo dto = new MonitorReportVo();
		long reportID = rpt.getReportId();
		String status = rpt.getStatus();
		
		dto.setReportId(reportID);
		dto.setReportEmployeeNum(rpt.getEmployeeNumber());
		dto.setReportName(rpt.getName());
		dto.setCommenceDate(rpt.getCommenceDate());
		dto.setEndDate(rpt.getEndDate());
		dto.setSubstantiveRank(rpt.getSubstantiveRank());
		dto.setPersentPost(rpt.getPresentPost());
		dto.setStatus(rpt.getStatus());
		
		if(Constants.STATUS_INITIAL.equals(status)){
			dto.setRole1("Initial");
			dto.setEmailAddress("N");
			dto.setRemSent(0);
			dto.setNoteInfo(getNotificationInfo(reportID));
		}else{
			dto.setRole1(Constants.STATUS_GM);
			List<UserEnquiryModelDTO> user = userEnquiryDAO.searchGradeManagmentByReportID(reportID);
			if(user != null && user.size() >0){
				dto.setPeopleEmplyNum(user.get(0).getEmployeeNumber());
				dto.setFullName(user.get(0).getFullName());
				dto.setSubstantiveRank(user.get(0).getEngRank());
				// Set available email flag for GM
				if (Util.isEmptyString(user.get(0).getEmailAddress()))
					dto.setEmailAddress("N");
				else
					dto.setEmailAddress("Y");
			}
			List<EaMessageLog> msg = monitorReportDAO.searchLogFromReportIDAndStatus(reportID,status);
			if(msg != null && msg.size() >0)
				dto.setRemSent(msg.size());
			else
				dto.setRemSent(0);
			dto.setNoteInfo(getNotificationInfo(reportID));
		}
		return dto;
	}

	/**
	 * Get Notification Information from ha_hr_ea_message_log by report id
	 * @param reportID Report ID
	 * @return Notification Information
	 * @throws EAException
	 */
	private String getNotificationInfo(Long reportID){
			int noteCount = 0;
			List<EaMessageLog> msg = monitorReportDAO.searchLogNotification(reportID);
			if(msg != null &&  msg.size() >0)
				noteCount = msg.size();
			 
			String emailSent = "";
			if (noteCount == 0){
				emailSent = "N";
			}else{
				List<EaMessageLog> newMsg = monitorReportDAO.searchLogNewestNotification(reportID);
				if(newMsg != null && newMsg.size() > 0)
					emailSent = newMsg.get(0).getEmailInd();
			}
			return "" + noteCount + "  " + emailSent;
	}

	public List<MonitorReportDto> search(List<Long> reportIds) {
		List<MonitorReportVo> list = monitorReportDAO.search( reportIds);
		List<MonitorReportDto> dto = this.copyDataFromVo(list);
		return dto;
	}

	public List<MessageLogDto> searchLogFromReportIDAndStatus(Long reportID, String status) {
		List<MessageLogDto> msgList = new ArrayList<MessageLogDto>();
		List<EaMessageLog> list = monitorReportDAO.searchLogFromReportIDAndStatus(reportID,status);
		if(list != null && list.size()>0){
			for(int i=0; i<list.size(); i++){
				if(list.get(i) != null){
					MessageLogDto dto = new MessageLogDto();
					BeanUtils.copyProperties(list.get(i), dto);
					
					dto.setLastUpdateDate(DateTimeUtil.date2String(list.get(i).getLastUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
					msgList.add(dto);
				}
			}
		}
		return msgList;
	} 
}
