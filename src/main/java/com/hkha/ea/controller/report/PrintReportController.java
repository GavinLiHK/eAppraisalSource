package com.hkha.ea.controller.report;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//20231201 Write log in catalina.out
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//End 20231201 Write log in catalina.out

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/* 
	Following import statement was obsoleted on 20/09/2021.
	
	Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
		the Spring framework was required to upgrade from version 4 to version 5.
	
	The org.springframework.web.servlet.view.jasperreports was obsoleted in Spring 5.
			  	
	Modified on 20/09/2021
*/
//import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import com.hkha.ea.dto.common.PrintReportPrepareDTO;
import com.hkha.ea.service.common.PrintReportService;


/* 
	Following import statement was imported on 20/09/2021.
	
	Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
		the Spring framework was required to upgrade from version 4 to version 5.
	
	Use following jasper report libraries to generate PDF reports 
			  	
	Modified on 20/09/2021
*/
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperCompileManager;
//20231127 close the established connection for saving the connection pool size
import java.sql.Connection;
//End 20231127 close the established connection for saving the connection pool size


@Controller
public class PrintReportController  {
	@Autowired
	private PrintReportService printReportService;
	
	@Autowired
	@Qualifier("applicatioPro")	
	private Properties applicatioPro;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(PrintReportController.class.getName());
	//End 20231201 Write log in catalina.out
	
	private List<JasperReport> compileSubreports(){
		ArrayList<JasperReport> subreports = new ArrayList<>();
		
		return subreports;
	}
	
    /*
	#Spring5Upgrade #OpenJDK11 #Java11
    Following part was created to use modelAttribute on 20/11/2021
    
    Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
    	the Spring framework was required to upgrade from version 4 to version 5.
    
    The @RequestMapping must be exactly matched with the URL.
    
    Modified on 20/11/2021
    */
	//@RequestMapping(value ="/common/printReport.do",method = RequestMethod.GET)
	@RequestMapping(value ="/common/printReport.do")
	//public ModelAndView printPdrReport(HttpServletRequest request,HttpServletResponse response){
	public void printPdrReport(HttpServletRequest request,HttpServletResponse response){
//		 20231127 close the established connection for saving the connection pool size
		 Connection connection = null;
//		 End 20231127 close the established connection for saving the connection pool size
		 String rptId=request.getParameter("rptId");
		 String role=request.getParameter("role");
		 
		 log.info("rptId: " + rptId);
		 log.info("role: " + role);
		 //log.info("JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY: " + JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY);
		 
		 PrintReportPrepareDTO dto=printReportService.generateReportPrepare(rptId, role);
		 
		 
		 //ModelAndView mv  = new ModelAndView(dto.getPath());
		
		 /*
		 	Following part was created to handle Jasper reports on 20/09/2021
		 	
			Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
				the Spring framework was required to upgrade from version 4 to version 5.
		 	
		 	Use jasper report libraries to generate PDF reports 
		 	
		 	Modified on 20/09/2021
		 */
		 
		 try {
			 // 1. Put all parameter values into parameters list for report template to retrieve data and display parts 
			 String path=request.getSession().getServletContext().getRealPath("/");
//			 20231127 close the established connection for saving the connection pool size
			 connection = jdbcTemplate.getDataSource().getConnection();
//			 End 20231127 close the established connection for saving the connection pool size
			 path=path+"WEB-INF/report/";
			 
			 Map<String, Object> parameters = new HashMap<String, Object>();
			 parameters.put("ReportID", dto.getReportId());
			 parameters.put("ReportDirectory", path);  
			 parameters.put("Role", dto.getRole());   
			 parameters.put("RoleCode", dto.getRoleCode());
			 parameters.put("StaffType",dto.getStaffType());
//			 parameters.put("dataSource", jdbcTemplate.getDataSource().getConnection());
			 parameters.put("dataSource", connection);
			 
			 /*
			 log.info("New-ReportID: " + dto.getReportId());
			 log.info("New-ReportDirectory: " + path);
			 log.info("New-dto - ReportDirectory: " + dto.getPath());
			 log.info("New-Role: " + dto.getRole());
			 log.info("New-RoleCode: " + dto.getRoleCode());
			 log.info("New-StaffType: " + dto.getStaffType());
			 log.info("New-dataSource: " + jdbcTemplate.getDataSource().getConnection());
		 	 */
			 
			 // 2. Determine the report template (AAR or Memo) by report type
			 String subpath = "";
			 log.info("path: " + path);
			 
			 JasperReport jasperMasterReport;
			 
			 String reportFileType = dto.getPath();
			 log.info("reportFileType: " + dto.getPath());
			 
			 log.info("this.getClass(): " + this.getClass());
			 
			 if (reportFileType.equals("AAR_Report")) {
				 subpath = "JRXML/AssessAppraisalReport/";
				 jasperMasterReport = JasperCompileManager.compileReport(path + subpath + "AAR_Report.jrxml");
				 
				 //AAR type sub-reports
				 //Compile all sub-reports
				 JasperReport sr_coverpage = JasperCompileManager.compileReport(path + subpath + "CoverPage.jrxml");
				 JasperReport sr_aar_pi = JasperCompileManager.compileReport(path + subpath + "AAR_PI.jrxml");
				 JasperReport sr_aar_pi_main = JasperCompileManager.compileReport(path + subpath + "AAR_PI_Main.jrxml");
				 JasperReport sr_aar_pi_aa = JasperCompileManager.compileReport(path + subpath + "AAR_PI_ActingAppoint.jrxml");
				 JasperReport sr_aar_pi_tc = JasperCompileManager.compileReport(path + subpath + "AAR_PI_TrainingCourse.jrxml");
				 JasperReport sr_aar_a = JasperCompileManager.compileReport(path + subpath + "AAR_A.jrxml");
				 JasperReport sr_aar_a_part2 = JasperCompileManager.compileReport(path + subpath + "AAR_A_Part2.jrxml");
				 JasperReport sr_aar_b1 = JasperCompileManager.compileReport(path + subpath + "AAR_B1.jrxml");
				 JasperReport sr_aar_b2 = JasperCompileManager.compileReport(path + subpath + "AAR_B2.jrxml");
				 JasperReport sr_aar_b2_part1 = JasperCompileManager.compileReport(path + subpath + "AAR_B2_Part1.jrxml");
				 JasperReport sr_aar_b2_part1_2 = JasperCompileManager.compileReport(path + subpath + "AAR_B2_Part1_2.jrxml");
				 JasperReport sr_aar_b2_part2 = JasperCompileManager.compileReport(path + subpath + "AAR_B2_Part2.jrxml");
				 JasperReport sr_aar_b3 = JasperCompileManager.compileReport(path + subpath + "AAR_B3.jrxml");
				 JasperReport sr_aar_b4 = JasperCompileManager.compileReport(path + subpath + "AAR_B4.jrxml");
				 JasperReport sr_aar_b5 = JasperCompileManager.compileReport(path + subpath + "AAR_B5.jrxml");
				 JasperReport sr_aar_b5_part2 = JasperCompileManager.compileReport(path + subpath + "AAR_B5_Part2.jrxml");
				 JasperReport sr_aar_b5_part3 = JasperCompileManager.compileReport(path + subpath + "AAR_B5_Part3.jrxml");
				 JasperReport sr_aar_b6 = JasperCompileManager.compileReport(path + subpath + "AAR_B6.jrxml");				 
				 JasperReport sr_aar_partb6_dn = JasperCompileManager.compileReport(path + subpath + "AAR_PartB6_DN.jrxml");
				 JasperReport sr_aar_b6_part2 = JasperCompileManager.compileReport(path + subpath + "AAR_B6_Part2.jrxml");
				 JasperReport sr_aar_b6_part3 = JasperCompileManager.compileReport(path + subpath + "AAR_B6_Part3.jrxml");
				 JasperReport sr_aar_b7_part3 = JasperCompileManager.compileReport(path + subpath + "AAR_B7_Part3.jrxml");
				 JasperReport sr_aar_b7_part4 = JasperCompileManager.compileReport(path + subpath + "AAR_B7_Part4.jrxml");
				 JasperReport sr_aar_b8_part2 = JasperCompileManager.compileReport(path + subpath + "AAR_B8_Part2.jrxml");
				 JasperReport sr_aar_b7 = JasperCompileManager.compileReport(path + subpath + "AAR_B7.jrxml");
				 JasperReport sr_aar_b8 = JasperCompileManager.compileReport(path + subpath + "AAR_B8.jrxml");
				 JasperReport sr_pa_box = JasperCompileManager.compileReport(path + subpath + "PA_Box.jrxml");
				 JasperReport sr_pb_sa_box = JasperCompileManager.compileReport(path + subpath + "PB_SA_Box.jrxml");
				 
				 JasperReport sr_memo_part1_2 = JasperCompileManager.compileReport(path + subpath + "Memo_Part1_2.jrxml");
				 
				 parameters.put("sr_coverpage", sr_coverpage);
				 parameters.put("sr_aar_pi", sr_aar_pi);
				 parameters.put("sr_aar_pi_main", sr_aar_pi_main);
				 parameters.put("sr_aar_pi_aa", sr_aar_pi_aa);
				 parameters.put("sr_aar_pi_tc", sr_aar_pi_tc);
				 parameters.put("sr_aar_a", sr_aar_a);
				 parameters.put("sr_aar_a_part2", sr_aar_a_part2);
				 parameters.put("sr_aar_b1", sr_aar_b1);
				 parameters.put("sr_aar_b2", sr_aar_b2);
				 parameters.put("sr_aar_b2_part1", sr_aar_b2_part1);
				 parameters.put("sr_aar_b2_part1_2", sr_aar_b2_part1_2);
				 parameters.put("sr_aar_b2_part2", sr_aar_b2_part2);
				 parameters.put("sr_aar_b3", sr_aar_b3);
				 parameters.put("sr_aar_b4", sr_aar_b4);
				 parameters.put("sr_aar_b5", sr_aar_b5);
				 parameters.put("sr_aar_b6", sr_aar_b6);
				 parameters.put("sr_aar_partb6_dn", sr_aar_partb6_dn);
				 parameters.put("sr_aar_b6_part2", sr_aar_b6_part2);
				 parameters.put("sr_aar_b6_part3", sr_aar_b6_part3);
				 parameters.put("sr_aar_b7", sr_aar_b7);
				 parameters.put("sr_aar_b8", sr_aar_b8);
				 parameters.put("sr_aar_b5_part2", sr_aar_b5_part2);
				 parameters.put("sr_aar_b8_part2", sr_aar_b8_part2);
				 parameters.put("sr_aar_b5_part3", sr_aar_b5_part3);
				 parameters.put("sr_aar_b7_part3", sr_aar_b7_part3);
				 parameters.put("sr_aar_b7_part4", sr_aar_b7_part4);
				 parameters.put("sr_pa_box", sr_pa_box);
				 parameters.put("sr_pb_sa_box", sr_pb_sa_box);
				 
				 parameters.put("sr_memo_part1_2", sr_memo_part1_2);
			 } else {
				 subpath = "JRXML/Memo/";
				 //Compile the master reports
				 jasperMasterReport = JasperCompileManager.compileReport(path + subpath + "Memo_Report.jrxml");
				 
				 //Memo type sub-reports
				 //Compile all sub-reports
				 JasperReport sr_memo_part1 = JasperCompileManager.compileReport(path + subpath + "Memo_Part1.jrxml");
				 JasperReport sr_memo_part1_ao = JasperCompileManager.compileReport(path + subpath + "Memo_Part1_AO.jrxml");
				 JasperReport sr_memo_part1_ap = JasperCompileManager.compileReport(path + subpath + "Memo_Part1_AP.jrxml");
				 JasperReport sr_memo_part2 = JasperCompileManager.compileReport(path + subpath + "Memo_Part2.jrxml");
				 JasperReport sr_memo_part2_ao = JasperCompileManager.compileReport(path + subpath + "Memo_Part2_AO.jrxml");
				 JasperReport sr_memo_part2_co = JasperCompileManager.compileReport(path + subpath + "Memo_Part2_CO.jrxml");
				 JasperReport sr_memo_part3 = JasperCompileManager.compileReport(path + subpath + "Memo_Part3.jrxml");
				 
				 //Put all sub-reports into parameters list
				 parameters.put("sr_memo_part1", sr_memo_part1);
				 parameters.put("sr_memo_part1_ao", sr_memo_part1_ao);
				 parameters.put("sr_memo_part1_ap", sr_memo_part1_ap);
				 parameters.put("sr_memo_part2", sr_memo_part2);
				 parameters.put("sr_memo_part2_ao", sr_memo_part2_ao);
				 parameters.put("sr_memo_part2_co", sr_memo_part2_co);
				 parameters.put("sr_memo_part3", sr_memo_part3);
			 }
			 
			 log.info("jasperReport: " + jasperMasterReport);
		 	 
			 // 3. Generate PDF report
//			 20231127 close the established connection for saving the connection pool size
//			 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, jdbcTemplate.getDataSource().getConnection());			 
			 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, connection);
			 connection.close();
//			 End 20231127 close the established connection for saving the connection pool size
			 
			 log.info("jasperPrint - after fillReport: " + jasperPrint);			 
			 
			 // 4. Respond the HttpServletRequest with PDF report
			 response.setHeader("Content-disposition", "inline; filename=" + rptId + "_e-appraisal.pdf");
			 response.setContentType("application/pdf");
			 final OutputStream outStream = response.getOutputStream();
			 JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			 log.info("outStream:" + outStream);
			 outStream.flush();
			 outStream.close();
			 log.info("JasperExportManager - after exportReportToPdfStream: " + jasperPrint);
			 
		 } catch (IOException ioe) {
			// TODO Auto-generated catch block
			 log.severe("IO Exception: ");
			 ioe.printStackTrace();
			 log.severe(ioe.toString());
		 
		 } catch (SQLException sqle) {
			// TODO Auto-generated catch block
			 log.severe("SQL Exception: ");
			 sqle.printStackTrace();
			 log.severe(sqle.toString());
			 
		 } catch (JRException jre) {
			// TODO Auto-generated catch block
			 log.severe("JR Exception: ");
			 jre.printStackTrace();
			 log.severe(jre.toString());
		 }
//		 20231127 close the established connection for saving the connection pool size
		 finally {
			 try {
				 connection.close();
			 } catch (Exception e) {
				 log.severe(e.toString());
			 }
		 }
//		 End 20231127 close the established connection for saving the connection pool size
		 		
		 /* 
		  	Following part was obsoleted on 20/09/2021.
		  	
		  	Due to the Java version was upgraded from OpenJDK 8 to OpenJDK 11, 
		 		the Spring framework was required to upgrade from version 4 to version 5.
		 	
		  	The org.springframework.web.servlet.view.jasperreports was obsoleted in Spring 5.
		  			  	
		   	Modified on 20/09/2021
		 */
		 /*
		 mv.addObject(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY,"pdf");
		 String path=request.getSession().getServletContext().getRealPath("/");
		 path=path+"\\WEB-INF\\report\\";
		 mv.addObject("ReportID", dto.getReportId());
		 mv.addObject("ReportDirectory", path);  
		 mv.addObject("Role", dto.getRole());   
		 mv.addObject("RoleCode", dto.getRoleCode());
		 mv.addObject("StaffType",dto.getStaffType());
		 mv.addObject("dataSource", jdbcTemplate.getDataSource());
		 
		 log.info("Ori-ReportID: " + dto.getReportId());
		 log.info("Ori-ReportDirectory: " + path);
		 log.info("Ori-dto - ReportDirectory: " + dto.getPath());
		 log.info("Ori-Role: " + dto.getRole());
		 log.info("Ori-RoleCode: " + dto.getRoleCode());
		 log.info("Ori-StaffType: " + dto.getStaffType());
		 log.info("Ori-dataSource: " + jdbcTemplate.getDataSource());
		 	
		return mv;
		*/
	}  

}
