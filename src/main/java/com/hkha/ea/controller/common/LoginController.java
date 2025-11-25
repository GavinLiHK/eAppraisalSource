package com.hkha.ea.controller.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

import org.apache.commons.lang3.StringUtils;
//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.hkha.ea.common.Constants;
import com.hkha.ea.domain.EaURL;
import com.hkha.ea.dto.common.EaLoginDTO;
import com.hkha.ea.dto.common.EaUserDTO;
import com.hkha.ea.dto.security.GroupFunctionDto;
import com.hkha.ea.helper.BaseDTOUtility;
import com.hkha.ea.service.security.UserEnquireService;
import com.hkha.ea.service.security.UserGroupMaintenanceService;
import com.hkha.ea.service.manager.EaSampleAuthenticationManager;

@Controller
public class LoginController {
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(LoginController.class.getName());
	//End 20231201 Write log in catalina.out
		
	@Autowired
	private UserEnquireService userEnquireService;
	
	@Autowired
	private UserGroupMaintenanceService userGroupMaintenanceService;
	
	@Autowired
	private EaSampleAuthenticationManager eaSampleAuthenticationManager;
	
	@RequestMapping( value="/invalidLogin.do" )
	private ModelAndView invalidLogin(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) final String logout,
			@RequestParam(required = false) String message
			){
		ModelAndView mvErr = new ModelAndView( "common/invalidLogin" );
		log.info("message: " + message);
		
		if (message.indexOf("<") >= 0 || message.contains("script") || message.indexOf(">") >= 0){
			message = "Invalid Login";
		}
		
		mvErr.addObject("message", message);
		mvErr.addObject("logout", logout);
		return mvErr;
	}
	
	@RequestMapping(value="/logout.do")
	public ModelAndView processLogout(
			HttpServletRequest request,
			HttpServletResponse response,
			SessionStatus status
			) throws UnsupportedEncodingException{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String msg = "You have successfully logged-out";
		if (auth != null){
			String currentPrincipalName = auth.getName();
			
			// Processing logout				
			EaLoginDTO loggedEmployeeDTO = userEnquireService.searchByLoginID(currentPrincipalName);
			if (loggedEmployeeDTO != null){
				loggedEmployeeDTO.setLastLogout(new Date());
				userEnquireService.updateLoginInformation(loggedEmployeeDTO);
			}
			
			// actual logout
			status.setComplete();
			new SecurityContextLogoutHandler().logout(request, response, auth);
			
			// logged-out, should return logout success instead.
		}
		msg = URLEncoder.encode(msg, "ISO-8859-1");
		return new ModelAndView("redirect:/invalidLogin.do?logout=1&message="+msg);
	}
	
	@RequestMapping( value={"/loginSSO.do"} )
	public ModelAndView initLoginProcessSSO(
			HttpServletRequest request,
			HttpServletResponse response,
			SessionStatus status
			) throws IOException{
		
		String msg = "";
		String ssoEmployeeNumber = request.getHeader(Constants.HTTP_HEADER_EMPLOYEE_ID);
		String ssoIvUser = request.getHeader(Constants.HTTP_HEADER_IV_USER);
		
		// get login information from attribute instead
		if (StringUtils.isEmpty(ssoEmployeeNumber)){
			
			if (!StringUtils.isEmpty(ssoIvUser)){
				log.info("iv-user Header found: " + ssoIvUser);
				ssoEmployeeNumber = userEnquireService.getUserIdByIvUser(ssoIvUser);
			}			
			if (StringUtils.isEmpty(ssoEmployeeNumber)){
				ssoEmployeeNumber = (String) request.getSession().getAttribute(Constants.HTTP_HEADER_EMPLOYEE_ID);
			}
		}
					
		if (StringUtils.isEmpty(ssoEmployeeNumber)){
			msg = "No employee number in header";
			log.severe(msg);
			msg = URLEncoder.encode(msg, "ISO-8859-1");
			return new ModelAndView("redirect:/invalidLogin.do?message="+msg);
		}
		
		log.info("Employee Number in header = " + ssoEmployeeNumber);
		// login from SSO
		// If user have record in HA_HR_EA_USER, its status should not be 'N'
		EaUserDTO loginUserDTO = userEnquireService.searchByUserIDAndStatus(ssoEmployeeNumber, "N");
		if (loginUserDTO != null) {
			msg = "Invalid user id";
			log.severe(msg);
			msg = URLEncoder.encode(msg, "ISO-8859-1");
			return new ModelAndView("redirect:/invalidLogin.do?message="+msg);
		}
		loginUserDTO = null;
		
		String userToken;
		
		// Login through portal (self-service)
		EaLoginDTO loginEmployeeDTO = userEnquireService.searchByLoginID(ssoEmployeeNumber);
		
		// Login info not found in DB
		if (loginEmployeeDTO == null) {
			loginEmployeeDTO = new EaLoginDTO();
			
			userToken = generateToken().toString();
			loginEmployeeDTO.setLoginId(ssoEmployeeNumber);
			loginEmployeeDTO.setToken(userToken);
			loginEmployeeDTO.setLastLogin(new Date());
							
			BaseDTOUtility.setEADTOCommonProperties(loginEmployeeDTO, ssoEmployeeNumber);
			userEnquireService.saveLoginInformation(loginEmployeeDTO);
		} else {
			userToken = loginEmployeeDTO.getToken();
		}
		
		// Generate the login link
		String linkParam = "";
		linkParam += "?employeeNumber=" + ssoEmployeeNumber + "&token=" + userToken;
		return new ModelAndView("redirect:/Jsp/common/check_pop.htm"+linkParam);
	}
	
	@RequestMapping( value={"/login.do"} )
	public ModelAndView initLoginProcess(
			HttpServletRequest request,
			HttpServletResponse response,
			SessionStatus status,
			@RequestParam(required = false) final String logout,
			@RequestParam(required = false) final String employeeNumber,
			@RequestParam(required = false) final String token
			) throws IOException{
		
		if (!StringUtils.isEmpty(logout)){
			// Processing logout				
			return new ModelAndView("redirect:/logout.do");
		}

		ModelAndView mv = new ModelAndView( "/helloworld" );
		String msg = "";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		// Logout the existing user
		if (hasAuthority(auth, Constants.LOGIN_USER_ROLE)){
			String currentPrincipalName = auth.getName();
						
			if ( (!StringUtils.isEmpty(employeeNumber)) && (!currentPrincipalName.equalsIgnoreCase(employeeNumber)) ){
				// logout the existing user and login with the new one
				log.info("Logout existing user: " + currentPrincipalName);
				status.setComplete();
				new SecurityContextLogoutHandler().logout(request, response, auth);
				auth = SecurityContextHolder.getContext().getAuthentication();
			}
	    }
		
		EaLoginDTO loginEmployeeDTO = null;

		if ((auth != null) && (hasAuthority(auth, Constants.LOGIN_USER_ROLE)) && (!StringUtils.isEmpty(employeeNumber))){
			log.info("User " + auth.getName() + " re-visit the login page.");
		} else {
			
			// Login from HRMS
			
			log.info("Employee Number in URL = " + employeeNumber);
			if (StringUtils.isEmpty(employeeNumber) || StringUtils.isEmpty(token)){
				msg = "No employee number in URL";
				log.severe(msg);
				msg = URLEncoder.encode(msg, "ISO-8859-1");
				return new ModelAndView("redirect:/invalidLogin.do?message="+msg);
			}
			
			mv.addObject("name", employeeNumber);
			
			// If user have record in HA_HR_EA_USER, its status should not be 'N'
			EaUserDTO loginUserDTO = userEnquireService.searchByUserIDAndStatus(employeeNumber, "N");
			if (loginUserDTO != null) {
				msg = "Invalid user id";
				log.severe(msg);
				msg = URLEncoder.encode(msg, "ISO-8859-1");
				return new ModelAndView("redirect:/invalidLogin.do?message="+msg);
			}
			loginUserDTO = null;
			
			String userToken = token;
			
			loginEmployeeDTO = userEnquireService.searchByLoginID(employeeNumber);
			
			// Login info not found in DB
			if (loginEmployeeDTO == null) {
				loginEmployeeDTO = new EaLoginDTO();
				
				userToken = generateToken().toString();
				loginEmployeeDTO.setLoginId(employeeNumber);
				loginEmployeeDTO.setToken(userToken);
				loginEmployeeDTO.setLastLogin(new Date());
								
				BaseDTOUtility.setEADTOCommonProperties(loginEmployeeDTO, employeeNumber);
				userEnquireService.saveLoginInformation(loginEmployeeDTO);
			}

			//perform authentication
			try {
				Authentication authRequest = new UsernamePasswordAuthenticationToken(employeeNumber, userToken);
				Authentication result = eaSampleAuthenticationManager.authenticate(authRequest);
//				SecurityContextHolder.getContext().setAuthentication(result);
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				context.setAuthentication(result);
				HttpSessionSecurityContextRepository secRepo = new HttpSessionSecurityContextRepository();
				secRepo.saveContext(context, request, response);
				
				auth = result;
			} catch(AuthenticationException e) {
				msg = "Bad Credentials";
				log.severe(msg);
				msg = URLEncoder.encode(msg, "ISO-8859-1");
				return new ModelAndView("redirect:/invalidLogin.do?message="+msg);
			}

			StringBuffer tokenBuf = generateToken();
			
			// Insert login info and token into table
			loginEmployeeDTO.setLoginId(employeeNumber);
			loginEmployeeDTO.setLastLogin(new Date());
			loginEmployeeDTO.setToken(tokenBuf.toString());
						
			userEnquireService.updateLoginInformation(loginEmployeeDTO);
		}
				
		if ( !auth.getAuthorities().isEmpty() ){
			// Build menu
			
			// Get the unique function id by employee number
			//EaUserDTO eaUser = userEnquireService.searchByUserID(employeeNumber);
			EaUserDTO eaUser = userEnquireService.searchByUserID(auth.getName());
			List<GroupFunctionDto> userGroupFunctions = null;
			Set<Integer> userFunc = new HashSet<Integer>();
			if (eaUser != null){
				userGroupFunctions = userGroupMaintenanceService.getGroupAccessFunctions(eaUser.getGroupId());
				
				if (userGroupFunctions != null){
					for (GroupFunctionDto gfd:userGroupFunctions){
						userFunc.add((int) gfd.getFunctionId());
					}
				}
			}
			
			request.getSession().setAttribute(Constants.SESS_EMPLOYEE_NUMBER, auth.getName());
			if(eaUser != null){
				request.getSession().setAttribute(Constants.SESS_EMPLOYEE_USER_MENU, generateMenu(userFunc,eaUser.getGroupId()).toString());
			}else{
				request.getSession().setAttribute(Constants.SESS_EMPLOYEE_USER_MENU, generateMenu(userFunc,Long.valueOf("0")).toString());
			}
			
	
			// Redirect to default page
			boolean redirect = true;
			if (redirect){
				return new ModelAndView("redirect:/assess/ListOutstandingReport.do");
			}
	
			return mv;
		} else {
			msg = "User " + auth.getName() + " is not allowed to access eAppraisal";
			log.severe(msg);
			msg = URLEncoder.encode(msg, "ISO-8859-1");
			return new ModelAndView("redirect:/invalidLogin.do?message="+msg);
		}
	}
		
	private StringBuffer generateToken(){
		Random random = new Random();
		StringBuffer tokenBuf = new StringBuffer();
		tokenBuf.append(Math.abs(random.nextLong()));
		if (tokenBuf.length() > Constants.MAX_TOKEN_LENGTH){
			tokenBuf.delete(Constants.MAX_TOKEN_LENGTH, tokenBuf.length());
		}
		
		return tokenBuf;
	}
	
	private StringBuffer generateMenu(Set<Integer> userFunc, long groupid){
		EaURL report = new EaURL("Report", "#");
		EaURL outstandingReportList = new EaURL("Outstanding Report List", "/ea/assess/ListOutstandingReport.do");
		EaURL monitorReportProgress = new EaURL("Monitor Report Progress", "/ea/admin/iniMonitorReport.do");
		EaURL displayFinalReport = new EaURL("Display Final Report", "/ea/assess/initSearchFinalReport.do");
		
		EaURL reportStatusMaintenance =new EaURL("Report Status Maintenance", "/ea/assess/initRptStatusMain.do");
		EaURL reportUserInformationMaintenance=new EaURL("Report User Information Maintenance","/ea/assess/initRptUserInfoMain.do");
		
		EaURL reportPeriodMaintenance=new EaURL("Report Period Maintenance","/ea/assess/initRptPeriodMain.do");
		
		EaURL admin = new EaURL("Admin", "#");
		EaURL workflowTemplateMain = new EaURL("Workflow Template Maintenance", "/ea/workflow/WorkflowEnquiry.do");
		EaURL appraisalReportMain = new EaURL("Performance Appraisal Report Maintenance", "#");
		EaURL createBatch = new EaURL("Create Batch", "/ea/assess/initSearchAppraisee.do");
		EaURL modifyBatch = new EaURL("Modify Batch", "/ea/assess/initBatch.do");
		EaURL systemParamMain = new EaURL("System Parameter Maintenance", "/ea/admin/iniSystemParameterEnquiry.do");
		EaURL assignRemainingOfficer = new EaURL("Assign Remaining Officer", "/ea/assess/initBatchAppraisee.do");
		
		EaURL security = new EaURL("Security", "#");
		EaURL userGroupMain = new EaURL("User Group Maintenance", "/ea/security/UserGroupEnquiry.do");
		EaURL userMain = new EaURL("User Maintenance", "/ea/security/UserEnquiry.do");
		
	
		
		StringBuffer userMenu = new StringBuffer();
		
		// Menu Start
		userMenu.append("<ul>");
		
		// Report Menu Start
		userMenu.append(generateSubMenuHead(report));
		userMenu.append("<ul>");
		
		userMenu.append( generateMenuItem(outstandingReportList) );
		
		if (userFunc.contains(Constants.FUNCTION_ID_MONITOR_REPORT_PROGRESS)){
			userMenu.append(generateMenuItem(monitorReportProgress));
		}
		
		if (userFunc.contains(Constants.FUNCTION_ID_DISPLAY_FINAL_REPORT)){
			userMenu.append( generateMenuItem(displayFinalReport) );
		}
		log.info("groupid "+groupid);
		if (userFunc.contains(Constants.FUNCTION_ID_CONFIRM_REJECT_REPORT) || (groupid==4||groupid==5)){
			//upadted on 20170310 set Super User able to access Report Status Maintenance
			/*if(groupid==4 || groupid==5){
				userMenu.append(generateMenuItem(reportUserInformationMaintenance));
				userMenu.append(generateMenuItem(reportPeriodMaintenance));
			}else{
				userMenu.append(generateMenuItem(reportStatusMaintenance));
				userMenu.append(generateMenuItem(reportUserInformationMaintenance));
				userMenu.append(generateMenuItem(reportPeriodMaintenance));
			}*/
			userMenu.append(generateMenuItem(reportStatusMaintenance));
			userMenu.append(generateMenuItem(reportUserInformationMaintenance));
			userMenu.append(generateMenuItem(reportPeriodMaintenance));
			
			//end updated on 20170310
		}
		
		
		userMenu.append("</ul>");
		userMenu.append("</li>");
		// Report Menu End
		
		// Admin Menu Start
		userMenu.append(generateSubMenuHead(admin));
		userMenu.append("<ul>");
		
		if (userFunc.contains(Constants.FUNCTION_ID_WORKFLOW_TEMPLATE_MAINTENANCE)){
			userMenu.append(generateMenuItem(workflowTemplateMain));
		}
		
		if ( (userFunc.contains(Constants.FUNCTION_ID_PERFORMANCE_APPRAISAL_REPORT_MAINTENANCE_CREATE_BATCH)) || (userFunc.contains(Constants.FUNCTION_ID_PERFORMANCE_APPRAISAL_REPORT_MAINTENANCE_MODIFY_BATCH)) ){
			userMenu.append(generateSubMenuHead(appraisalReportMain));
			userMenu.append("<ul>");
			
			if (userFunc.contains(Constants.FUNCTION_ID_PERFORMANCE_APPRAISAL_REPORT_MAINTENANCE_CREATE_BATCH)){
				userMenu.append(generateMenuItem(createBatch));
			}
			
			if (userFunc.contains(Constants.FUNCTION_ID_PERFORMANCE_APPRAISAL_REPORT_MAINTENANCE_MODIFY_BATCH)){
				userMenu.append(generateMenuItem(modifyBatch));
			}
			
			userMenu.append("</ul>");
			userMenu.append("</li>");
		}
		
		if (userFunc.contains(Constants.FUNCTION_ID_SYSTEM_PARAMETERS_MAINTENANCE)){
			userMenu.append(generateMenuItem(systemParamMain));
		}
		
		userMenu.append(generateMenuItem(assignRemainingOfficer));
		
		userMenu.append("</ul>");
		userMenu.append("</li>");
		// Admin Menu End
		
		// Security Menu Start
		if ( (userFunc.contains(Constants.FUNCTION_ID_USER_GROUP_MAINTENANCE)) || (userFunc.contains(Constants.FUNCTION_ID_USER_MAINTENANCE)) ){
			userMenu.append(generateSubMenuHead(security));
			userMenu.append("<ul>");
			
			if (userFunc.contains(Constants.FUNCTION_ID_USER_GROUP_MAINTENANCE)){
				userMenu.append(generateMenuItem(userGroupMain));
			}
			
			if (userFunc.contains(Constants.FUNCTION_ID_USER_MAINTENANCE)){
				userMenu.append( generateMenuItem(userMain) );
			}
			
			userMenu.append("</ul>");
			userMenu.append("</li>");
		}
		// Security Menu End

		userMenu.append("</ul>");
		// Menu End
		
		return userMenu;
	}
	
	private String generateSubMenuHead(EaURL eaURL){
		return String.format("<li class='has-sub'><a name='%s' href='%s'><span>%s</span></a>", eaURL.getId(), eaURL.getUrl(), eaURL.getDisplayName());
	}
	
	private String generateMenuItem(EaURL eaURL){
		return String.format("<li><a name='%s' href='%s'><span>%s</span></a></li>", eaURL.getId(), eaURL.getUrl(), eaURL.getDisplayName());
	}
	
	private boolean hasAuthority(Authentication auth, String authority){
		return auth.getAuthorities().contains(new SimpleGrantedAuthority(authority));
	}
}
