package com.hkha.ea.dao.assess.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Vector;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
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
import com.hkha.ea.dao.assess.AppraiseeCommonSearchDAO;
import com.hkha.ea.dao.common.EmployeeEnquiryDAO;
import com.hkha.ea.dao.common.NotificationManagerDAO;
import com.hkha.ea.dao.security.UserEnquiryDAO;
import com.hkha.ea.domain.DummyReportInfo;
import com.hkha.ea.domain.EaMessageLog;
import com.hkha.ea.domain.EaReport;
import com.hkha.ea.domain.EaReportRole;
import com.hkha.ea.dto.admin.SystemParameterDTO;
import com.hkha.ea.dto.assess.AssessAppraisalDTO;
import com.hkha.ea.dto.assess.PrintSubmitInfo;
import com.hkha.ea.dto.assess.SubmitActionInfo;
import com.hkha.ea.dto.common.EmployeeEnquiryDTO;
import com.hkha.ea.dto.common.ReportUserRole;
import com.hkha.ea.dto.security.UserEnquiryModelDTO;


@Repository("appraisalAssessmentDAO")
public class AppraisalAssessmentDAOImpl implements AppraisalAssessmentDAO{
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(AppraisalAssessmentDAOImpl.class.getName());
	//End 20231201 Write log in catalina.out
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private EmployeeEnquiryDAO employeeEnquiryDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserEnquiryDAO userEnquiryDAO;
	
	@Autowired
	private AppraiseeCommonSearchDAO appraiseeCommonSearchDAO;
	
	@Autowired
	private NotificationManagerDAO notificationManagerDAO;
	/***
	 * 
	 *  get Report User role
	 *       
	 */ 
	
	public ReportUserRole getReportUserRoleByReportIdAndUserId(long id,String userId){
		ReportUserRole urf=new ReportUserRole();	
		String role="";
		String gmOfficerId="";
		String currentOfficerId = "";
		//current report 
		EaReport er=appraiseeCommonSearchDAO.findEareportById(id);
		//current report status
		String rptStatus=er.getStatus();
		urf.setCurrentReportStatus(rptStatus);
		
		//search super user list
		List<UserEnquiryModelDTO> superUserList=userEnquiryDAO.searchUserByReportIdAndFunctionId(id, Constants.FUNCTION_ID_PERFORMANCE_APPRAISAL_REPORT_MAINTENANCE_CREATE_BATCH);
		//determine is it super User.
		if(superUserList.size()>0){
			for(UserEnquiryModelDTO u:superUserList){
				if(u.getUserId().equals(userId)){
					role="SU";
					urf.setSuperUser(true);
					break;
				}
			}
		}
		
		//search GM  list
		List<UserEnquiryModelDTO> gmList=userEnquiryDAO.searchUserByReportIdAndFunctionId(id, Constants.FUNCTION_ID_CONFIRM_REJECT_REPORT);
		//determine is it GM 
		if(gmList.size()>0){
			for(UserEnquiryModelDTO u:gmList){			
				if(u.getUserId().equals(userId)){
					role="GM";	
					gmOfficerId=u.getUserId();
					urf.setGradeManagement(true);
					break;
				}
			}
		}
		if((("GM".equalsIgnoreCase(rptStatus)) || ("CL".equalsIgnoreCase(rptStatus))) && !"SU".equalsIgnoreCase(role) ){//&& (("GM".equalsIgnoreCase(role)))){
				role="GM";
				gmOfficerId=userId;
		}
		
		urf.setGmId(gmOfficerId);

		//determine is it AP
		//nowAP = true & 
		boolean nowAP=true;
		if((er.getEmployeeNumber().equalsIgnoreCase(userId)) && ("AP".equalsIgnoreCase(rptStatus))){
			role="AP";	
			currentOfficerId = er.getEmployeeNumber();
		}else{
			nowAP=false;
		}

		//determine AO CD CD IO EO RO
		if(!nowAP){
			List<EaReportRole> rolelist=appraiseeCommonSearchDAO.searchReportRoleDetailByReportId(id);
			for(EaReportRole roleDetail:rolelist){
				if(rptStatus.equalsIgnoreCase(roleDetail.getRole())){
					String officerId=roleDetail.getOfficerId();

					if((officerId != null) && (officerId.equalsIgnoreCase(userId))){					
						if("CD".equalsIgnoreCase(roleDetail.getRole())){
						//determine CD
							role="CD";
						}else if("AO".equalsIgnoreCase(roleDetail.getRole())){
						//determine AO
							role="AO";				
						}else if("IO".equalsIgnoreCase(roleDetail.getRole())){
						//determine IO
							role="IO";
						}else if("CO".equalsIgnoreCase(roleDetail.getRole())){
						//determine CO
							role="CO";
						}else if("EO".equalsIgnoreCase(roleDetail.getRole())){
						//determine EO
							role="EO";
						}else if(("RO".equalsIgnoreCase(roleDetail.getRole()))){
						//determine RO
							if(Util.isEmptyString(er.getHoId())){
								role="RO";
							}else if(("" + er.getHoId()).equalsIgnoreCase("" + userId)){
								role="RO";
							}else if(officerId.equalsIgnoreCase(userId) && !"SU".equalsIgnoreCase(role)){ //set role RO for Reviewing officer if it has Handling officer 
								role="RO";
							}
						}
						currentOfficerId = roleDetail.getOfficerId();
					}else if((officerId!=null&&"RO".equalsIgnoreCase(roleDetail.getRole()))&&(!officerId.equalsIgnoreCase(userId))){
						if(Util.isEmptyString(er.getHoId()) && !"SU".equalsIgnoreCase(role)){
							role="RO";
						}else if(("" + er.getHoId()).equalsIgnoreCase("" + userId) && !"SU".equalsIgnoreCase(role)){
							role="RO";
						}
					}					
				}
			}		
		}
		
		if ("I".equalsIgnoreCase(rptStatus)){
		    currentOfficerId = gmOfficerId;
		 }else if ("AP".equalsIgnoreCase(rptStatus)){
		    currentOfficerId=er.getEmployeeNumber();	
		 }else if("GM".equalsIgnoreCase(rptStatus)||"CL".equalsIgnoreCase(rptStatus)){
		    currentOfficerId = gmOfficerId;
		 }

		  urf.setCurrentUserRoleStatus(role);
		  urf.setCurrentOfficerId(currentOfficerId);

		  
		  //set next Officer Id
		  EaReportRole err=appraiseeCommonSearchDAO.searchNextOfficerRoleByIdAndCurrentRptStatus(id, rptStatus);
		  if((null!=err && StringUtil.isEmptyString(err.getOfficerId())) && rptStatus.equalsIgnoreCase("I")){
			  	//status I
				err = appraiseeCommonSearchDAO.searchNextOfficerByStatus(id, rptStatus, null);
			}
		  if(null!=err){
			  urf.setNextOfficerId(err.getOfficerId());
			  urf.setNextOfficerName(err.getOfficerName());			
			  urf.setNextOfficerDeadline(Util.date2String(err.getDeadline(),Constants.DISPLAY_DATE_FORMAT));
			  urf.setNextOfficerRole(err.getRole());
		  }else{
			  //current rptstaus=ro and next officer is gm or rptstaus=cl have not next officer but still take gm on it. 
			  urf.setNextOfficerId(gmList.get(0).getUserId());
			  urf.setNextOfficerName("");			
			  urf.setNextOfficerDeadline(Util.date2String(er.getOverallDeadline(),Constants.DISPLAY_DATE_FORMAT));
			  urf.setNextOfficerRole(Constants.STATUS_GM);
			  
			  if(("RO".equalsIgnoreCase(urf.getCurrentReportStatus())&&"GM".equalsIgnoreCase(urf.getNextOfficerRole()))||"GM".equalsIgnoreCase(urf.getCurrentReportStatus())
					  || ("IO".equalsIgnoreCase(urf.getCurrentReportStatus())&&"GM".equalsIgnoreCase(urf.getNextOfficerRole()))){
				  urf.setNextOfficerRole("GM");
				  urf.setGmId(gmList.get(0).getUserId());
				  urf.setGradeManagement(true);
			  }
			  
			  
		  }
		   return urf;
	}	 
	/**
	 * BonusSalary Checking and convert 
	 * 
	 * */
	public boolean[] allowBonusSalary(Long id)throws SQLException,EAException{
		EaReport er=appraiseeCommonSearchDAO.findEareportById(id);
		String employeeNumber=er.getEmployeeNumber();// HA_RA_EA_EMPLOYEE_NUMBER
		 int trackYear;//HaHrEaReport.getHaHrEaReportTrackDate
		if(null!=er.getTrackDate()){
			trackYear=Util.getYear(er.getTrackDate()) ;
		}else{
			trackYear= Calendar.getInstance().get(1);
		}
		//debug
		log.info("track year: "+trackYear+" "+er.getTrackDate());
		
		 boolean[] bNs = { false, false, false };
		 int personId=appraiseeCommonSearchDAO.findHaCommonRptPeopleVPersonIdByEmployeeNumber(employeeNumber);
		 if(0!=personId){
			 int p_year = trackYear;
			 int p_bonus = 0;
			 int p_salary = 0;
			 int p_person_id = personId;
			 //eddited on 20171114 Modify program logic of bonus and salary adjustment
			 int p_rpt_id = id.intValue();
			 log.info("p_rpt_id: "+p_rpt_id);
			 String procdure = "{call ha_hr_pem001r_pkg.bonus_salary(?,?,?,?,?)}";    
			 Connection conn =jdbcTemplate.getDataSource().getConnection();		 
			 CallableStatement proc2 = conn.prepareCall(procdure);
			 proc2.setInt(1, p_person_id);
			 proc2.setInt(2, p_year);
			 proc2.setInt(3, p_rpt_id);
			 proc2.setInt(4, p_bonus);
			 proc2.setInt(5, p_salary);
			 proc2.registerOutParameter(4, 4);
			 proc2.registerOutParameter(5, 4);
			 proc2.execute();
			 bNs[0] = (Boolean) (proc2.getInt(4) == 1 ? true : false);
			 bNs[1] = (Boolean) (proc2.getInt(5) == 1 ? true : false);
			 bNs[2] = false;
			//end edited on 20171114 Modify program logic of bonus and salary adjustment
			 proc2.close();
			 DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
		 }
		 return bNs;
	}
	
	/**
	 * freeze Aduit log
	 * 
	 * */
	public void freezeAduitLog(Long reportID, String rptStatus) throws SQLException,EAException{
		try{
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			CallableStatement proc2 = null;
			int rptId=Integer.parseInt(reportID.toString());
			proc2 = conn.prepareCall("{call HA_HR_EAPPRAISAL.ha_report_audit_freeze(?,?)}");
			proc2.setInt(1, rptId);
			proc2.setString(2, rptStatus);
			proc2.execute();
			proc2.close();
			DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
		}catch(SQLException e){
			throw new EAException(e);
		}
	}
	
	/**
	 * Update Pem001R Commence Date And End Date
	 * 
	 * */
	public void updatePem001RCommenceDateAndEndDate(String commenceDate,String endDate,String curUser,String employeeNum)throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append("update ha_hr_pem001r ");
		sql.append("set commence_date = :commenceDate, ");
		sql.append("end_date = :endDate ");
		sql.append("where ha_hr_pem001r.COMPUTER_NUM = :curUser ");
		sql.append( " and ha_hr_pem001r.EMPLOYEE_NUM = :employeeNum");
		 log.info("sql is "+sql.toString());
		 MapSqlParameterSource paramSource = new MapSqlParameterSource();
		 paramSource.addValue("commenceDate",Util.string2Date(commenceDate, Constants.DISPLAY_DATE_FORMAT));
		 paramSource.addValue("endDate", Util.string2Date(endDate, Constants.DISPLAY_DATE_FORMAT));
		 paramSource.addValue("curUser",curUser);
		 paramSource.addValue("employeeNum",employeeNum);
		 namedParameterJdbcTemplate.update(sql.toString(), paramSource);
	}
	
	
	/**
	 * move Table 
	 * 
	 * */	
//edited on 20170321 cannot move table to HA_HR_APPRASIAL_CONTRACT
	public  Object[] moveTable(int reportID, boolean memoReport)throws EAException{
		Object[] ret = { null, null };

		String errbuf = "";
		int retcode = 0;
		int p_report_id = reportID;
		log.info("report_id: "+p_report_id);
		try{
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			CallableStatement proc2 = null;
			 if (memoReport){
				 proc2 = conn.prepareCall("{call HA_HR_EAPPRAISAL.HA_MOVE_TABLES_MEMO(?,?,?)}");
			 }else{
				 proc2 = conn.prepareCall("{call HA_HR_EAPPRAISAL.HA_MOVE_TABLES(?,?,?)}");
				 /*proc2.setString(1, errbuf);
				 proc2.setInt(2, retcode);
				 proc2.setInt(3, p_report_id);
				 proc2.registerOutParameter(1, 12);
				 proc2.registerOutParameter(2, 4);
				 proc2.execute();
				 ret[0] = proc2.getString(1);
				 ret[1] = new Integer(proc2.getInt(2));
				 proc2.close();
				 DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());*/
			  }
			 proc2.setString(1, errbuf);
			 proc2.setInt(2, retcode);
			 proc2.setInt(3, p_report_id);
			 proc2.registerOutParameter(1, 12);
			 proc2.registerOutParameter(2, 4);
			 proc2.execute();
			 ret[0] = proc2.getString(1);
			 ret[1] = new Integer(proc2.getInt(2));
			 proc2.close();
			 DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			}catch (SQLException e){
				throw new EAException(e);
			}
			 
			 return ret;
	 }
	
	/**
	 * Assess Appraisal Report Page Maps
	 * */
	public Map<String,String> getAssessAppraisalReportPageMapsByRole(String role){
		Map<String,String> pagesMap=new HashMap<String,String>();
		
		if("AP".equalsIgnoreCase(role)){
			pagesMap.put("0", "PI");
			pagesMap.put("1", "A");
			pagesMap.put("2", "PS");
			
		}else if("AO".equalsIgnoreCase(role)){
			pagesMap.put("0", "PI");
			pagesMap.put("1", "A");
			pagesMap.put("2", "B1");
			pagesMap.put("3", "B2");
			pagesMap.put("4", "B3");
			pagesMap.put("5", "B4");
			pagesMap.put("6", "PS");
			
			
		}else if("CO".equalsIgnoreCase(role)){
			pagesMap.put("0", "PI");
			pagesMap.put("1", "A");
			pagesMap.put("2", "B1");
			pagesMap.put("3", "B2");
			pagesMap.put("4", "B3");
			pagesMap.put("5", "B4");
			pagesMap.put("6", "B5");
			pagesMap.put("7", "PS");
			
			
		}else if("IO".equalsIgnoreCase(role)){
			pagesMap.put("0", "PI");
			pagesMap.put("1", "A");
			pagesMap.put("2", "B1");
			pagesMap.put("3", "B2");
			pagesMap.put("4", "B3");
			pagesMap.put("5", "B4");
			pagesMap.put("6", "B5");
			pagesMap.put("7", "B6");
			pagesMap.put("8", "PS");
			
			
		}else if("EO".equalsIgnoreCase(role)){
			pagesMap.put("0", "PI");
			pagesMap.put("1", "A");
			pagesMap.put("2", "B1");
			pagesMap.put("3", "B2");
			pagesMap.put("4", "B3");
			pagesMap.put("5", "B4");
			pagesMap.put("6", "B5");
			pagesMap.put("7", "B6");
			pagesMap.put("8", "B7");
			pagesMap.put("9", "PS");
			
			
		}else if("RO".equalsIgnoreCase(role)||"SU".equalsIgnoreCase(role)||"GM".equalsIgnoreCase(role)){
			pagesMap.put("0", "PI");
			pagesMap.put("1", "A");
			pagesMap.put("2", "B1");
			pagesMap.put("3", "B2");
			pagesMap.put("4", "B3");
			pagesMap.put("5", "B4");
			pagesMap.put("6", "B5");
			pagesMap.put("7", "B6");
			pagesMap.put("8", "B7");
			pagesMap.put("9", "B8");
			pagesMap.put("10", "PS");
			
		}	
		
		return pagesMap;
	}
	
	/**
	 * Assess Appraisal Memo Page Maps
	 * */
	public Map<String,String> getAssessAppraisalMemoPageMapsByRole(String role){
		Map<String,String> pagesMap=new HashMap<String,String>();
		
		if("AP".equalsIgnoreCase(role)){
			pagesMap.put("0", "P1");
			pagesMap.put("1", "P2");
			pagesMap.put("2", "PS");
			
		}else if("AO".equalsIgnoreCase(role)){
			pagesMap.put("0", "P1");
			pagesMap.put("1", "P2");
			pagesMap.put("2", "P3");
			pagesMap.put("3", "PS");
		}else if("CO".equalsIgnoreCase(role)){
			pagesMap.put("0", "P1");
			pagesMap.put("1", "P2");
			pagesMap.put("2", "P3");
			pagesMap.put("3", "P4");
			pagesMap.put("4", "PS");
			
		}else if("IO".equalsIgnoreCase(role)||"SU".equalsIgnoreCase(role)||"GM".equalsIgnoreCase(role)){
			pagesMap.put("0", "P1");
			pagesMap.put("1", "P2");
			pagesMap.put("2", "P3");
			pagesMap.put("3", "P4");
			pagesMap.put("4", "P5");
			pagesMap.put("5", "PS");	
		}	
		
		return pagesMap;
	}
	
	public void changeNextOfficer(PrintSubmitInfo psi,ReportUserRole rur,Long rptId)throws Exception{
		 String newOfficerName = "";
		 String newOfficerRank = "";
		 String newOfficerId = "";
		 String nextOfficerStatus = "";
		 newOfficerId = psi.getNextOfficerId().trim();
		 newOfficerName = psi.getNextOfficerName();
		 String roleNextOfficerId = Objects.toString(rur.getNextOfficerId(), "");
		 String roleNextOfficerName = Objects.toString(rur.getNextOfficerName(), "");
		 
		 //debug
		 //log.info("debug:"+newOfficerId+" - "+rur.getNextOfficerId());
		 //log.info("debug_2:"+rur.getCurrentUserRoleStatus()+" "+rur.getNextOfficerRole()+" "+psi.getNextOfficerRole());
		 //edited on 20180131 incorrect employee number issue
//		 log.info("!psi.getRouting().equalsIgnoreCase(\"AP\"): "+!psi.getRouting().equalsIgnoreCase("AP"));
		 log.info("psi.getRouting(): " + psi.getRouting());
		 
		 if(!psi.getNextOfficerRole().equalsIgnoreCase("AP")){			 
			 if(!psi.getRouting().equalsIgnoreCase("AP") && !psi.getRouting().equalsIgnoreCase("PO")) {
				 log.info("roleNextOfficerId: " + roleNextOfficerId);
				 log.info("roleNextOfficerName: " + roleNextOfficerName);
				 log.info("newOfficerId: " + newOfficerId);
				 log.info("newOfficerName: " + newOfficerName);
				 if((!roleNextOfficerId.contains(newOfficerId))&&(!roleNextOfficerName.contains(newOfficerName))
							||!roleNextOfficerId.equalsIgnoreCase(newOfficerId)){
						//end edited on 20180131 incorrect employee number issue
					 		 log.info("debug_3: testing testing testing");
					 		 nextOfficerStatus =rur.getNextOfficerRole();
							 EaReportRole err=appraiseeCommonSearchDAO.searchReportRoleByReportIdandRole(rptId, nextOfficerStatus);
							 err.setOfficerId(newOfficerId);
							 err.setOfficerName(newOfficerName);
							 List<EmployeeEnquiryDTO> eedList= employeeEnquiryDAO.searchEmployeeNameAndRankByEmployeeNum(newOfficerId);
							 if(eedList.size()>0){
								 newOfficerName = eedList.get(0).getEmployeeFullName();
								 newOfficerRank =  eedList.get(0).getRank();
							 }
							 log.info("newOfficerName + \"     \" + newOfficerRank: " + newOfficerName + "     " + newOfficerRank);
							 err.setOfficerName(newOfficerName + "     " + newOfficerRank);
	//						 System.out.println("err.getLastUpdateDate() + err.getLastUpdatedBy()" + err.getLastUpdateDate() + " " + err.getLastUpdatedBy());
	//						 log.info("err.getLastUpdateDate() + err.getLastUpdatedBy()" + err.getLastUpdateDate() + " " + err.getLastUpdatedBy());
							 hibernateTemplate.saveOrUpdate(err);
						 }
			 }
		 } 	 
	}
	
	public boolean sendEmailAndAduitLog(Long rptId,String currentUserRoleStatus,String flag,SubmitActionInfo msi)throws Exception{
		boolean success =false;
		String submitAction = msi.getSubmitAction();
		String submitToEmployeeNum = msi.getSubmitToEmployeeNum();
		String submitNextDeadDate = msi.getSubmitNextDeadDate();
		String submitNotificationContent = msi.getSubmitNotificationContent();
		String submitToAddress =msi.getSubmitToAddress();
		String newStatus = msi.getNewStatus();

		log.info("Dispatch to "+newStatus);
	
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("application-local.properties"));
		final String eAppraisialURL = Util.getEAppURL();	// 20231120 Update eApp URL
		final String notificationSender = props.getProperty("email.NotificationSender");
		
		EaReport er=appraiseeCommonSearchDAO.findEareportById(rptId);
		
		if (!Util.isEmptyString(submitToEmployeeNum)){
		    if (Util.isEmptyString(submitToAddress)) {
		    	Vector empNums = new Vector();
		    	empNums.add(submitToEmployeeNum);
		    	Hashtable email = notificationManagerDAO.searchEmailAddressByEmployeeNumber(empNums);
			   if (email.containsKey(submitToEmployeeNum)) {
				   submitToAddress = (String)email.get(submitToEmployeeNum);
		          }
		
		      }

		String mailRecipient = submitToAddress;
		String sentEmail = Util.isEmptyString(mailRecipient) ? "N" : "Y";
		if (!Util.isEmptyString(mailRecipient)) {
			String mailSubject="";
			if("report".equals(flag)){
				mailSubject = "Appraisal Report Notification";
			}else if("memo".equals(flag)){
				mailSubject = "Memo Appraisal Report Notification";
			}
			
			//Vector mailBody = new Vector();
			StringBuffer mailBody = new StringBuffer();
			mailBody.append("The appraisal report(s) of the following officer(s) has/have been sent to you via the web");
			mailBody.append("\n\n");
		
			String reportEmpName = er.getName();
			String reportStatus = er.getStatus();
			String reportEmpRank = er.getSubstantiveRank();
			java.sql.Date reportStart = DateTimeUtil.toSQLDate(er.getCommenceDate());
			java.sql.Date reportEnd = DateTimeUtil.toSQLDate(er.getEndDate());
			 int reportID = Integer.valueOf(String.valueOf(rptId));
			 DummyReportInfo ri = new DummyReportInfo(reportID, reportEmpName, reportStatus, reportEmpRank, reportStart, reportEnd);
		
			mailBody.append(ri.toDisplayString(false));
			mailBody.append("\n\n");
		
			if (!Util.isEmptyString(submitNotificationContent)) {
				mailBody.append(submitNotificationContent);
				mailBody.append("\n");
		     }
		
			mailBody.append("Please complete the report(s) accordingly by using the following link:");
		
			mailBody.append("\n");
			mailBody.append(eAppraisialURL);
			mailBody.append("\n");
		     try {
		        notificationManagerDAO.send(notificationSender, mailRecipient, null, mailSubject, mailBody.toString());
		     } catch (EAException ex) {
		         sentEmail = "F";
		      }
		 }
		
			List<EaMessageLog> msgLogList = new ArrayList<EaMessageLog>();
			
			EaMessageLog dsHaHrEaMessageLog =  new EaMessageLog();
			dsHaHrEaMessageLog.setReportId(Long.valueOf(String.valueOf(er.getReportId())));	
		//	dsHaHrEaMessageLog.setMessageId(this._dsHaHrEaReport.getHaHrEaReportReportId(0));
			dsHaHrEaMessageLog.setRole(er.getStatus());
			dsHaHrEaMessageLog.setRecipient(submitToEmployeeNum);
			dsHaHrEaMessageLog.setRecipientRole(newStatus);
			dsHaHrEaMessageLog.setMessageType(Constants.MESSAGE_LOG_MESSAGE_TYPE_NOTIFICATION);
			dsHaHrEaMessageLog.setType(Constants.MESSAGE_LOG_TYPE_MANUAL);
			dsHaHrEaMessageLog.setEmailInd(sentEmail);
			dsHaHrEaMessageLog.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			dsHaHrEaMessageLog.setCreationDate(new Date());
			dsHaHrEaMessageLog.setLastUpdatedBy(dsHaHrEaMessageLog.getCreatedBy());
			dsHaHrEaMessageLog.setLastUpdateDate(new Date());
			
			msgLogList.add(dsHaHrEaMessageLog);
			
			//insert message log to DB
			notificationManagerDAO.updateMessageLog(msgLogList, SecurityContextHolder.getContext().getAuthentication().getName());
		 }
		
		log.info("currentUserRoleStatus: "+currentUserRoleStatus+" newStatus: "+newStatus);
		log.info("flag: "+flag);
		if (("GM".equalsIgnoreCase(currentUserRoleStatus)) && ("CL".equals(newStatus))) {
			log.info("move Table");
			Object[] ret = {null, null};
			if("report".equalsIgnoreCase(flag)){
				ret = moveTable(Integer.parseInt(String.valueOf(rptId)), false);
			}else if("memo".equalsIgnoreCase(flag)){
				ret = moveTable(Integer.parseInt(String.valueOf(rptId)), true);
			}
			//Object[] ret = moveTable(Integer.parseInt(String.valueOf(rptId)), true);
			String errbuf = (String)ret[0];
			if (!Util.isEmptyString(errbuf)) {
				throw new EAException("er0039");
			}
		
		}
		
		
		freezeAduitLog(rptId,currentUserRoleStatus);
		     
		
		
		success = true;
	//	String referer = (String)SessionManager.getValue(getSession(), "AssessAppraisalMemo_referer" + reportID);
		// SessionManager.removeValue(getSession(), "AssessAppraisalMemo_referer" + reportID);
		//if (!Util.isEmptyString(referer)){
		//sendRedirect(referer);
		//}else{
		//sendRedirect("ListOutstandingReport.jsp?pageAction=refresh");
		      //}	
	
		return success;
	}
	
	public PrintSubmitInfo genRoutingLabelAndRoutingToListForPrintSubmit(PrintSubmitInfo mps,String userRole){
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
		}else if("CO".equalsIgnoreCase(userRole)||"EO".equals(userRole)||"IO".equals(userRole)||"RO".equals(userRole)){
			
			routingLabelList.add(sdPo);
			routingLabelList.add(sdNo);
		}else if ("GM".equalsIgnoreCase(userRole)||"SU".equalsIgnoreCase(userRole)) {
			
			routingLabelList.add(sdAr);
			mps.setRouting("AR");//on checked
		 }
		
		
		mps.setRoutingLabelList(routingLabelList);
		return mps;
	}
	//edited on 20170228
	public int convertPagesForAssessAppraisalReportAndMemo(ReportUserRole rur,String reportType,boolean isEORoleChecked){
		String role=rur.getCurrentUserRoleStatus();
		String pages[]={};

		if("H".equalsIgnoreCase(reportType)||"C".equalsIgnoreCase(reportType)){
			String apPages[]= {"0","1","2"};//{ "PI", "A", "PS" };
			
			String aoPages[]={"0","1","2","3","4","5","6"};//{ "PI", "A", "B1", "B2", "B3", "B4", "PS" };
			
			String coPages[]={"0","1","2","3","4","5","6","7" };//{ "PI", "A", "B1", "B2", "B3", "B4", "B5", "PS" };
			
			String ioPages[]={"0","1","2","3","4","5","6","7","8" };//{ "PI", "A", "B1", "B2", "B3", "B4", "B5", "B6", "PS" };
			 
			String eoPages[]={"0","1","2","3","4","5","6","7","8","9"};//{ "PI", "A", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "PS" };
			
			String roPages[]={"0","1","2","3","4","5","6","7","8","9","10"};//{ "PI", "A", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "PS" };
			
			String roPagesNoEO[]={"0","1","2","3","4","5","6","7","8","9"};//{ "PI", "A", "B1", "B2", "B3", "B4", "B5", "B6", "B7","PS" };
			
			if("AP".equals(role)){
				pages=apPages;			
			}else if("AO".equals(role)){
				pages=aoPages;
			}else if("CO".equals(role)){
				pages=coPages;
			}else if("IO".equals(role)){
				pages=ioPages;
			}else if("EO".equals(role)){
				pages=eoPages;
			}else if("RO".equals(role)||"GM".equals(role)||"SU".equals(role)){
				if(isEORoleChecked){
					pages=roPages;
				}else{
					pages=roPagesNoEO;
				}
				
			}
			//pages = roPages;
			if(isEORoleChecked){
				pages=roPages;
			}else{
				pages=roPagesNoEO;
			}
		}else if("M".equalsIgnoreCase(reportType)){
		
			String apPages[]={"0","1","2"};//{"P1","P2","Ps"};
			String aoPages[]={"0","1","2","3"};//{"P1","P2","P3","Ps"};
			String coPages[]={"0","1","2","3","4"};//{"P1","P2","P3","P4","Ps"};
			String ioPages[]={"0","1","2","3","4","5"};//{"P1","P2","P3","P4","P5","Ps"};
			
			if("AP".equals(role)){
				pages=apPages;
				
			}else if("AO".equals(role)){
				pages=aoPages;
				
			}else if("CO".equals(role)){
				pages=coPages;
			}else if("IO".equals(role)||"GM".equals(role)||"SU".equals(role)){
				pages=ioPages;
			}
			
			pages=ioPages;
		}

		return pages.length-1;
	}

}
