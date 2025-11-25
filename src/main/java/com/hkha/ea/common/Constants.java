 package com.hkha.ea.common;

 public interface Constants { 

   public static final String APPLICATION_SHORT_NAME = "EA";
   public static final String APPLICATION_LONG_NAME = "Hong Kong Housing Authority e-Appraiseal System";

   public static final String MSG_FILE = "./WEB-INF/XML/message.en.xml";  
   public static final String WEB_CONTEXT = "/ea";
   public static final String WEB_CSS_PATH = WEB_CONTEXT + "/css";
   public static final String WEB_HOME_PAGE = "/common/Login.jsp";
   
   //constants for system parameter type
   public static final String SYS_PARAM_TYPE_LIB_PICKLIST_STATUS = "LIB_PICKLIST_STATUS";
   
   public static final String WEB_REPORT_OP = "Replet";
  
   //constants for js files
   public static final String WEB_JS_PATH = WEB_CONTEXT + "/js";
   public static final String WEB_JS_COMMON_PATH = WEB_JS_PATH + "/ea_common.js";
   public static final String WEB_JS_CALENDAR_PATH = WEB_JS_PATH + "/ea_calendar.js";
   public static final String WEB_JS_DATETIME_PATH = WEB_JS_PATH + "/ea_datetime.js";
   public static final String WEB_JS_LIB_PATH = WEB_JS_PATH + "/ea_lib.js";
   
   //constants for images
   public static final String WEB_IMAGE_PATH = WEB_CONTEXT + "/images";
   public static final String WEB_IMAGE_LOGO_PATH = WEB_IMAGE_PATH + "/ea_logo.gif";
   public static final String WEB_IMAGE_CALENDAR_PATH = WEB_IMAGE_PATH + "/cal.gif";
   public static final String WEB_IMAGE_SEARCH_PATH = WEB_IMAGE_PATH + "/img_search.jpg";
   public static final String WEB_IMAGE_BTN_RIGHT_DOUBLE_ARROW_PATH = WEB_IMAGE_PATH + "/but_right_double_arrow.jpg";
   public static final String WEB_IMAGE_BTN_RIGHT_SINGLE_ARROW_PATH = WEB_IMAGE_PATH + "/but_right_single_arrow.jpg";
   public static final String WEB_IMAGE_BTN_LEFT_SINGLE_ARROW_PATH = WEB_IMAGE_PATH + "/but_left_single_arrow.jpg";
   public static final String WEB_IMAGE_BTN_LEFT_DOUBLE_ARROW_PATH = WEB_IMAGE_PATH + "/but_left_double_arrow.jpg";
   
   public static final String WEB_HTML_LOGO_IMAGE_TAG = "<img src=\"" + WEB_IMAGE_LOGO_PATH + "\" width=\"336\" height=\"39\" border=\"0\">";
   public static final String WEB_HTML_CALENDAR_IMAGE_TAG = "<img src=\"" + WEB_IMAGE_CALENDAR_PATH + "\" width=\"16\" height=\"16\" border=\"0\" alt=\"Click Here to Pick up the date\">";
   public static final String WEB_HTML_SEARCH_IMAGE_TAG = "<img src=\"" + WEB_IMAGE_SEARCH_PATH + "\" width=\"40\" height=\"20\" border=\"0\" alt=\"\">";

   public static final String WEB_JSP_PATH = WEB_CONTEXT + "/Jsp";
   public static final String WEB_JSP_COM_PATH = WEB_JSP_PATH + "/com";
   public static final String WEB_JSP_LIB_PATH = WEB_JSP_PATH + "/lib";

   public static final String WEB_HEADER_PAGE = "/Jsp/common/Header.jsp";
   public static final String WEB_FOOTER_PAGE = "/Jsp/com/Footer.jsp";
   public static final String WEB_POPUP_HEADER_PAGE = "/Jsp/common/PopupHeader.jsp";
   public static final String WEB_POPUP_FOOTER_PAGE = "/Jsp/com/PopupFooter.jsp";  
   public static final String WEB_SPOTBOOKING_HEADER_PAGE = "/Jsp/com/SpotBookingHeader.jsp"; 
   
   public static final String DISPLAY_DATE_FORMAT = "dd/MM/yyyy";
   public static final String DISPLAY_TIME_FORMAT = "HH24:MI:SS";
   public static final String DISPLAY_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
   public static final String DB_DATETIME_FORMAT = "YYYY-MM-DD HH24:MI:SS";
   public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   public static final String DATE_FORMAT = "yyyy-MM-dd";  
   public static final String REPORT_DATE_FORMAT = "yyyyMMdd";  
   public static final String REPORT_TIME_FORMAT = "HH24:MI";
   public static final String BUDGET_IMPORT_DATE_FORMAT = "yyyy/MM/dd";
   public static final String BUDGET_IMPORT_ALTERATE_DATE_FORMAT = "yyyy/MM/dd";
   public static final String MONTH_YEAR_FORMAT = "MMMM-YYYY";


   public static final String CURRENCY_FORMAT = "$ #,###,##0.00";
   public static final String DECIMAL_FORMAT = "#,###,##0.00";  
   public static final String DB_DECIMAL_FORMAT = "#0.00";
   public static final String SHELF_BIN_FORMAT = "#00";
   public static final String EXCHANGE_RATE_FORMAT = "#0.00000";

   public static final String MIN_DISPLAY_DATE = "01/01/0001";
   public static final int MAX_COMPETENCY_ITEM = 30;
   public static final String MAX_DISPLAY_DATE = "31/12/9999";
   public static final String YEAR_START_DATE = "01/01";
   public static final String YEAR_END_DATE = "31/12";
   

   public static final String CHECK_BOX_TRUE_VALUE = "Y";
   public static final String CHECK_BOX_FALSE_VALUE = "N";

   public static final String HIDDEN_FIELD_TRUE_VALUE = "Y";
   public static final String HIDDEN_FIELD_FALSE_VALUE = "N";

   public static final String TRUE_VALUE = "true";
   public static final String FALSE_VALUE = "false";

   public static final String REQ_PARAM_INIT = "init";
   public static final String REQ_PARAM_RELOAD = "reload";

   public static final String REQ_PARAM_PAGE_ACTION = "pageAction";
   public static final String REQ_PARAM_CLEAN_DS = "cleanDS";
   public static final String PAGE_ACTION_REFRESH = "refresh";
   public static final String PAGE_ACTION_RESET = "reset";
   public static final String PAGE_ACTION_KEEP = "keep";
   public static final String PAGE_ACTION_BACK = "back";
   public static final String PAGE_ACTION_NEW = "new" ;

   public static final String REQ_PARAM_QUERY_MODE = "queryMode";
   
   public static final int PROG_SEGMENT_ENTRY = 6;

   public static final String SERVLET_CONTEXT_SESSION_LIST = "ea.sessions";
   
// -------------------------------------------------------------------------


   public static final String DELETE_FLAG = "DELETE_FLAG";

   // status usage for schedule
   public static final String OPEN = "0" ;
   public static final String TX_TO_OAO = "1" ;
   public static final String TX_BACK_FROM_OAO = "2";
   public static final String TX_TO_PRESENTATION = "3" ;
   public static final String TXED = "4" ;

   public static final String YES = "Y";
   public static final String NO = "N";

   public static final String MON = "Mon";
   public static final String TUE = "Tue";
   public static final String WED = "Wed";
   public static final String THU = "Thu";
   public static final String FRI = "Fri";
   public static final String SAT = "Sat";
   public static final String SUN = "Sun";
 	
   public static final String BUDGET_CODE_REGEXP = "[A-Za-z]{4}[0-9]{3}";
   public static final String HOUSE_NUM_REGEXP = "[A-Za-z]{1}[0-9]{6}|[A-Za-z]{2}[0-9]{5}";
   public static final String MEDIA_NO_REGEXP = "[A-Z0-9]*";
   	
   public static final String INTERSTITIAL = "INTER" ;  
   public static final String MASTER = "MASTER" ;  
   
   public static final int INPUT_HOUSE_NO_MAXLENGTH = 8;
   public static final int INPUT_HOUSE_NO_SIZE = 10;
   
   public static final String NICAM_MODE = "DUAL" ;
   
   public static final String REPORT_FORMAT_PDF = "PDF";
   
   public static final int POPUP_SEARCHBOX_WIDTH = 780; 
   public static final int POPUP_SEARCHBOX_HEIGHT = 580;
   public static final int POPUP_REPORT_WIDTH = 780; 
   public static final int POPUP_REPORT_HEIGHT = 580;  
   public static final String INTERFACE_FILENAME_DATETIME_FORMAT = "yyMMddHHmmss";
   
// constants for system parameter name 
   public static final String SYS_PARA_PREFIX_REPORT_TEMPLATE = "REPORT_LAYOUT";
 
// constants for system parameter name 
   public static final String IMAGE_BASE_URL="ImageBaseURL";
// The URL of the compiled Jasper Report object (*.jasper)
	public static final String REPORT_URL="reportURL";
	public static final String MODEL_CLASS="modelClass";
	public static final String SELECTION_CRITERIA="selectionCriteria";

// Constants for property prefix
	public static final String ROLE_DESC_PREFIX = "role.desc.";

//	 Constants for session variable name
	public static final String SESS_LOGIN_TOKEN = "LOGIN_TOKEN";
	public static final String SESS_EMPLOYEE_NUMBER = "EMPLOYEE_NUMBER";
	public static final String SESS_EMPLOYEE_USER_FUNCTION = "EMPLOYEE_USER_FUNCTION";
	public static final String SESS_EMPLOYEE_USER_MENU = "EMPLOYEE_USER_MENU";
	public static final String SESS_EMPLOYEE_ROLE = "EMPLOYEE_ROLE";
	public static final String SESS_INIT = "INIT";
	public static final String SESS_SELECTED_APPRAISEE = "SELECTED_APPRAISEE";
	public static final String SESS_ASSIGN_OFFICER_TYPE = "AssignOfficerType";
	public static final String SESS_ASSIGNING_OFFICER_REFERER = "AssignOfficerReferer";
	public static final String SESS_LIST_BATCH_REFERER = "ListBatchReferer";
	public static final String SESS_ROUTING_TO_LIST = "ROUTING_TO_LIST";
	
	public static final String PREFIX_SEARCH_MESSAGE_FUNCTION = "javascript:searchMessage(null, document.pageForm.";
	public static final String POSTFIX_SEARCH_MESSAGE_FUNCTION = ");";
	
	public static final String PREFIX_SEARCH_EMPLOYEE_NUMBER_FUNCTION = "javascript:searchEmployee(null, document.pageForm.";
	public static final String PREFIX_SEARCH_EMPLOYEE_BY_NAME_FUNCTION = "javascript:searchEmployeeByName(null, document.pageForm.";
	public static final String INFIX_SEARCH_EMPLOYEE_NUMBER_FUNCTION = ",document.pageForm.";
	public static final String POSTFIX_SEARCH_EMPLOYEE_NUMBER_FUNCTION = ");";
	
	public static final String PREFIX_SYSTEM_DEFAULT_MAIL_MESSAGE = "MAIL_MESSAGE_";
	public static final String PREFIX_SYSTEM_DEFAULT_FIRST_REMINDER = "Default first reminder";
	public static final String PREFIX_SYSTEM_DEFAULT_SECOND_REMINDER = "Default second reminder";
	public static final String PREFIX_SYSTEM_DEFAULT_THIRD_REMINDER = "Default third reminder";
	public static final String PREFIX_SYSTEM_DEFAULT_SUBS_REMINDER = "Default subsequent reminder";
	// Error code mapping
	public static final String ERROR_DB_ERROR = "er0001";
	public static final String ERROR_WORKFLOW_TEMPLATE_REQUIRED = "er0002";
	public static final String ERROR_EMPLOYEE_NUM_OR_RANK_REQUIRED = "er0003";
	public static final String ERROR_CD_AO_NOT_DEFINED = "er0004";
	public static final String ERROR_YEAR_REQUIRED = "er0005";
	public static final String ERROR_REPORT_CANNOT_DISPATCH = "er0006";
	public static final String ERROR_REPORT_CANNOT_DELETE = "er0007";
	public static final String ERROR_REPORT_CANNOT_HANDLE_BY_EXCEL = "er0008";
	public static final String ERROR_CANNOT_FIND_OFFICER_INFO = "er0009";
	public static final String ERROR_AO_INFO_NOT_FOUND = "er0010";
	public static final String ERROR_CO_INFO_NOT_FOUND = "er0011";
	public static final String ERROR_IO_INFO_NOT_FOUND = "er0012";
	public static final String ERROR_EO_INFO_NOT_FOUND = "er0013";
	public static final String ERROR_RO_INFO_NOT_FOUND = "er0014";
	public static final String ERROR_LOGIN_INFO_MISSING = "er0015";
	public static final String ERROR_CD_INFO_NOT_FOUND = "er0016";
	public static final String ERROR_SELECT_ONE_APPRAISEE = "er0017";
	public static final String ERROR_SELECT_ONE_BATCH = "er0018";
	public static final String ERROR_AT_LEAST_ONE_EMAIL_NOT_FOUND = "er0019";
	public static final String ERROR_WORKFLOW_NOT_FOUND = "er0020";
	public static final String ERROR_REPORT_NOT_FOUND = "er0021";
	public static final String ERROR_INVALID_EMPLOYEE_NUMBER = "er0022";
	public static final String ERROR_CS_OFFICER_SELECTION 		= "er0023";
	public static final String ERROR_CHO_OFFICER_SELECTION 		= "er0024";
	public static final String ERROR_MEMO_OFFICER_SELECTION		= "er0025";
	public static final String ERROR_2RI_SMALLER_THAN_1RI		= "er0026";
	public static final String ERROR_3RI_SMALLER_THAN_1RI		= "er0027";
	public static final String ERROR_3RI_SMALLER_THAN_2RI		= "er0028";
	public static final String ERROR_RANK_ALREADY_IN_OTHER_GROUP = "er0029";
	public static final String ERROR_UNIQUE_GROUP_NAME          = "er0030";
	public static final String ERROR_GROUP_ASSIGNED_GM          = "er0031";
	public static final String ERROR_MORE_THAN_ONE_GM           = "er0032";
	public static final String ERROR_UNIQUE_USER_ID             = "er0033";
	public static final String ERROR_HO_INFO_NOT_FOUND = "er0034";
	public static final String ERROR_BATCH_ALREADY_EXISTS = "er0035";
	public static final String ERROR_REASSIGN_BATCH_NAME = "er0036";
	public static final String ERROR_BATCH_ID_NOT_FOUND = "er0037";
	public static final String ERROR_REPORT_GENERATED 			= "er0038";
	public static final String ERROR_ALREADY_SUBMIT             = "er0039";
	public static final String ERROR_MISSING_RANK_OR_OFFICERID	= "er0040";
	public static final String ERROR_NO_ACCESS_RIGHT            = "er0041";
	public static final String ERROR_EMPLOYEE_NO_VALID_EMAIL    = "er0042";
	public static final String ERROR_MISSING_SYS_PARM_REPORT_LAYOUT = "er0043";
	public static final String ERROR_MISSING_SYS_PARM_MAIL_MESSAGE = "er0044";
	public static final String ERROR_SAME_OFFICER_FOR_AO_CO		= "er0045";
	public static final String ERROR_OFFICER_ID_FOR_CD_AO		= "er0046";
	public static final String ERROR_OFFICER_ID_FOR_AO			= "er0047";
	public static final String ERROR_INVALID_USER_ID			= "er0048";
	public static final String ERROR_OCCURS                     = "er0049";
	public static final String ERROR_INVALID_OFFICER            = "er0050";
	public static final String ERROR_ASSIGN_ONE_RECORD_ONLY		= "er0051";
	public static final String ERROR_INPUT_CHARACTERS			= "er0052";
	public static final String ERROR_RANK_IN_OTHER_GM_GROUP     = "er0053";
	public static final String ERROR_RANK_IN_OTHER_SU_GROUP     = "er0054";
	public static final String ERROR_GM_ASSIGN_ONE_RECORD_ONLY	= "er0055";
	public static final String ERROR_DIFFERENT_BATCH_SELECTION 	= "er0056";
	public static final String ERROR_BROWSING_BACKWARD          = "er0057";
	public static final String ERROR_REPORT_ALREADY_CREATED     = "er0058";
	public static final String ERROR_INVALID_EMAIL_ACCOUNT      = "er0059";
	public static final String ERROR_TRACK_DATE_REQUIRED        = "er0060";
	public static final String ERROR_APPRAISAL_PERIOD_START_REQ = "er0061";
	public static final String ERROR_APPRAISAL_PERIOD_END_REQ   = "er0062";
	public static final String ERROR_APPR_END_MUST_EAF_START_D  = "er0063";
	public static final String ERROR_APPR_END_MUST_EAF_TRACK_D  = "er0064";
	public static final String ERROR_AO_JOIN_AFTER_APP_END_DATE = "er0065";
	//Start enhancement on Log 0895
	public static final String ERROR_SELECTED_REPORT_ALREADY_ASSIGNED = "er0066";
	//End enhancement on Log 0895
	
	// Info code mapping
	public static final String INFO_SAVE_SUCCESS = "if0001";
	public static final String INFO_RECORD_NOT_FOUND = "if0002";
	public static final String INFO_EO_SHOULD_NOT_BE_SELECTED = "if0005";
	public static final String INFO_LIST_OUTSTANDING_REPORT = "if0007";
	public static final String INFO_REPORT_SENT_TO_AO = "if0011";
	public static final String INFO_NO_OUTSTANDING_REPORT = "if0012";
	public static final String INFO_OFFICER_ASSIGNED = "if0015";
	
	// Report Status
	public static String STATUS_HANDLED_BY_EXCEL = "EX";
	public static String STATUS_INITIAL = "I";
	public static String STATUS_CD = "CD";
	public static String STATUS_AO = "AO";
	public static String STATUS_AP = "AP";
	public static String STATUS_CO = "CO";
	public static String STATUS_IO = "IO";
	public static String STATUS_EO = "EO";
	public static String STATUS_RO = "RO";
	public static String STATUS_GM = "GM";
	public static String STATUS_CL = "CL";
	public static String STATUS_SUPERUSER = "SU";
	
	public static String STATUS_CD_FULL = "Coordinator";
	public static String STATUS_AO_FULL = "Appraising Officer";
	public static String STATUS_AP_FULL = "Appraisee";
	public static String STATUS_CO_FULL = "Countersigning Officer";
	public static String STATUS_IO_FULL = "Interviewing Officer";
	public static String STATUS_EO_FULL = "Endorsing Officer";
	public static String STATUS_HO_FULL = "Handling Officer";
	public static String STATUS_RO_FULL = "Reviewing Officer";
	public static String STATUS_GM_FULL = "Grade Management";
	

	// Role abbreviation
	public static final String ROLE_ABBR_CD = "CD";
	public static final String ROLE_ABBR_AO = "AO";
	public static final String ROLE_ABBR_CO = "CO";
	public static final String ROLE_ABBR_IO = "IO";
	public static final String ROLE_ABBR_EO = "EO";
	public static final String ROLE_ABBR_RO = "RO";
	
	// Workflow Report Template
	public static final String WORKFLOW_REPORT_TEMPLATE_CONTRACT_STAFF = "C";
	public static final String WORKFLOW_REPORT_TEMPLATE_HOUSING_OFFICER = "H";
	public static final String WORKFLOW_REPORT_TEMPLATE_MEMO = "M"; 
	
	// Constant message
	public static final String MESSAGE_CANCEL = "Do you want to save changes you have made?";
	public static final String MESSAGE_HANDLED_BY_EXCEL = "Handled By Excel";
	public static final String MESSAGE_COMMENDATIONS_DEFAULT = "No commendations/disciplinary offences during appraisal period.";	
	public static final String MESSAGE_DEFAULT_MEMO_TO = "O i/c Confidential Registry";	
	public static final String MESSAGE_DISPATCHED = "Dispatched";
	public static final String SELECT_FLAG = "SELECT_FLAG";

	// List appraisee action type
	public static final int ACTION_TYPE_DISPATCH = 0;
	public static final int ACTION_TYPE_DELETE = 1;
	public static final int ACTION_TYPE_HANDLE_BY_EXCEL = 2;
	
	// List appraisee action type
	public static final String JSP_PATH_SEARCH_APPRAISEE  = "assess.SearchAppraisee";
	public static final String JSP_PATH_SEARCH_BATCH  = "assess.SearchBatch";
	
	// Http Header
	public static final String HTTP_HEADER_EMPLOYEE_ID = "employee-id";
	public static final String HTTP_HEADER_IV_USER = "IV-USER";

	// Batch processing type
	public static final String ACTION_CREATE_BATCH = "createBatch";
	public static final String ACTION_MODIFY_BATCH = "modifyBatch";
	public static final String ACTION_ASSIGN_REMAINING = "assignRemain";

	// Assign officer processing type
	public static final String PAGE_CREATE_BATCH= "SearchAppraisee";
	public static final String PAGE_MODIFY_BATCH= "SearchBatch";
	public static final String PAGE_MODIFY_BATCH_LINK= "LinkSearchBatch";
	public static final String PAGE_ASSIGN_REMAINING = "AssignRemain";
	
	//Assess Control
	public static final int FUNCTION_ID_ASSESS_APPRAISAL = 304;	// Should be deleted
	public static final int FUNCTION_ID_WORKFLOW_TEMPLATE_MAINTENANCE = 10;
	public static final int FUNCTION_ID_ASSIGN_OFFICERS = 306;  // Should be deleted or change name
	public static final int FUNCTION_ID_CONFIRM_REJECT_REPORT = 50;
	public static final int FUNCTION_ID_MONITOR_REPORT_PROGRESS = 60;
	public static final int FUNCTION_ID_SEND_REMINDER = 80;
	public static final int FUNCTION_ID_SHOW_REPORT = 310;  // Should be deleted
	public static final int FUNCTION_ID_DISPLAY_FINAL_REPORT = 110;
	public static final int FUNCTION_ID_USER_GROUP_MAINTENANCE = 120;
	public static final int FUNCTION_ID_USER_MAINTENANCE = 130;
	public static final int FUNCTION_ID_SYSTEM_PARAMETERS_MAINTENANCE = 140;
	public static final int FUNCTION_ID_PERFORMANCE_APPRAISAL_REPORT_MAINTENANCE_CREATE_BATCH = 20;
	public static final int FUNCTION_ID_PERFORMANCE_APPRAISAL_REPORT_MAINTENANCE_MODIFY_BATCH = 21;
	
	// Input field styles
    public static final String ERR_INPUT_FIELD = "errinputfield";
    public static final String EDITABLE_INPUT_FIELD = "editableinputfield";
    public static final String INPUT_FIELD = "inputfield";

	// Input field styles
    public static final String OBJECTIVE_TYPE_PARTA = "PARTA";
    public static final String OBJECTIVE_TYPE_PARTB = "PARTB";

    public static final String COMPETENCY_ACTING = " for";
    
    public static final String MESSAGE_LOG_MESSAGE_TYPE_NOTIFICATION = "N";
    public static final String MESSAGE_LOG_MESSAGE_TYPE_1ST_REMINDER = "1";
    public static final String MESSAGE_LOG_MESSAGE_TYPE_2ND_REMINDER = "2";
    public static final String MESSAGE_LOG_MESSAGE_TYPE_3RD_REMINDER = "3";
    public static final String MESSAGE_LOG_MESSAGE_TYPE_SUBSEQUENT_REMINDER = "S";
    
    public static final String MESSAGE_LOG_TYPE_AUTO = "A";
    public static final String MESSAGE_LOG_TYPE_MANUAL = "M";

    public static final String COMPETENCY_DOT_CORE = ".CORE";
    
    //public static final String COMPETENCY_CHI_DESC_HEADER = "�@��¾�t�H����";
    
    public static final int MAX_DB_CONNECTION = 30;
    public static final int DB_WAIT_TIMEOUT = 10000;
    public static final int DB_IDLE_TIMEOUT = 10000;
    
    public static final int MAX_TOKEN_LENGTH = 20;

    public static final String TITLE_AO = "Appraising Officer";
    public static final String TITLE_CO = "Countersigning Officer";
    public static final String TITLE_IO = "Interviewing Officer";
    public static final String TITLE_EO = "Endorsing Officer";
    public static final String TITLE_RO = "Reviewing Officer";
    
    public static final String CHECKBOX_CHECKED = "1";
    public static final String CHECKBOX_UNCHECKED = "0"; 
    
    public static final String PROPERTY_TITLE = "ea.title";
    
    public static final int DB_ERROR_UNIQUE_CONTRAINT = 1; 

    public static final String LOGIN_USER_ROLE = "ROLE_USER";
    //added 20161014
    public static final String ROLE_SYSADM = "ROLE_SYSADM";//-1
    public static final String ROLE_CFM_RPT_GG = "ROLE_CFM_RPT_GG";//3
    public static final String ROLE_CRT_RPT_GG = "ROLE_CRT_RPT_GG";//4
    public static final String ROLE_CFM_RPT_GMH_GMW = "ROLE_CFM_RPT_GMH_GMW";//5
    public static final String ROLE_CFM_RPT_GMH1 = "ROLE_CFM_RPT_GMH1";//8
    public static final String ROLE_CFM_RPT_GMW1 = "ROLE_CFM_RPT_GMW1";//9
    public static final String ROLE_CFM_RPT_GMW2 = "ROLE_CFM_RPT_GMW2";//10
    public static final String ROLE_CFM_RPT_GMW3 = "ROLE_CFM_RPT_GMW3";//11
    public static final String ROLE_CFM_RPT_GG1 = "ROLE_CFM_RPT_GG1";//21
    public static final String ROLE_CFM_RPT_GMW4 = "ROLE_CFM_RPT_GMW4";//41
    public static final String ROLE_CFM_RPT_GMH2 = "ROLE_CFM_RPT_GMH2";//61
    public static final String ROLE_CFM_RPT_GMH3 = "ROLE_CFM_RPT_GMH3";//81
    public static final String ROLE_CFM_RPT_GMH4 = "ROLE_CFM_RPT_GMH4";//101
    //end 20161014
    
    //add by elina
    public static final String FROM_ASSIGN_OFFICER = "fromAssignOfficer";
    public static final String APPRAISEE_OBJECT = "Appraisee";
    public static final String FROM_REPORT_USER_INFORMATION = "EA017";
    public static final String PAGE_MONITOR_REPORT = "searchMonitor";
    //end
}
