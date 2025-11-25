package com.hkha.ea.controller.common;

//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com


import java.io.IOException;
import java.util.Vector;

//20231201 Write log in catalina.out
import java.util.logging.*;
//End 20231201 Write log in catalina.out

//20231201 Write log in catalina.out 
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory; 
//End 20231201 Write log in catalina.out

import com.hkha.ea.common.Constants;
import com.hkha.ea.common.SessionManager;
import com.hkha.ea.common.SiteMapConstants;
import com.hkha.ea.common.EAException;
import com.hkha.ea.common.Util;
import com.hkha.ea.common.Message;
//import com.salmonllc.html.events.*;
//import com.salmonllc.localizer.LanguagePreferences;
//import com.salmonllc.util.MessageLog;
//import com.salmonllc.sql.DBConnection;
//import com.salmonllc.sql.DataStoreException;
//import com.salmonllc.sql.DirtyDataException;


/**
This controller is used as the ancestor for the other controllers in the example app.
Generally, every controller will extend from this one, but the examples all extend JspController in order to keep them encapsulated.
This controller has more functionality than is requied by the sample application so it can be copied and used as a starting point for your application's base controller.
It implements frequently used interfaces at the base level to avoid having to implement all the methods in each controller.
Also, it provides some basic functionality such as checking if the session or page is required and redirecting the user to the appropriate page.
*/
public class BaseController extends AbstractController implements Constants {
	
	//20231201 Write log in catalina.out
//	private Log log = LogFactory.getLog(this.getClass());
	private Logger log = Logger.getLogger(BaseController.class.getName());
	//End 20231201 Write log in catalina.out

//  public com.salmonllc.jsp.JspContainer _noCache;

  private boolean _redirected = true;
  private boolean _checkSessionExpired = true;
  private boolean _checkPageExpired = true;
  private boolean _checkDB = true;
  
  protected String _userId = "";
  protected Vector _userFunction = new Vector();
  protected String _userMenu = "";
  protected String _userRole = "";

  public static final String SPLITTER_OF_POPUP_JSCRIPT = ";/*popup-msg-is-here*/;";
  protected StringBuffer popupMsg = null;

//  public com.salmonllc.jsp.JspRaw _menuDef;
//  public com.salmonllc.html.HtmlText _information;
//  public com.salmonllc.html.HtmlValidatorText _errorMessage;

  /**
   *	This method tries to get the string parameter passed into this function from the URL.  It then checks to see if that "name" parameter is of boolean type.  The default is FALSE.
   *	@param name - name of the parameter that is being looked up
   */

//  public boolean getBooleanParameter(String name) {
//      String s = getParameter(name);
//      return s != null && (s.equals("true") || s.equals("TRUE") || s.equals("1"));
//  }


  /**
   * Return the text of the request referer, if available, else null.
   * @param key - name of the cookie
   * @return java.lang.String
   */
//  public Cookie getCookie(String key) {
//      Cookie ret = null;
//
//      Cookie cookArr[] = getCurrentRequest().getCookies();
//      if (cookArr != null) {
//          for (int i = 0; i < cookArr.length; i++) {
//              if (cookArr[i].getName().equals(key)) {
//                  ret = cookArr[i];
//              }
//              break;
//          }
//      }
//      return ret;
//  }

  /**
   *	This method tries to get the integer value from the parameter in the URL called passed into this method.  If the value of the parameter is not an integer, a -1 value will be returned.
   *	@param name - name of the parameter that is being looked up
   * 	@return int - returns the value of the parameter as an int, if the value can NOT be cast to an int -1 is returned
   */

//  public int getIntParameter(String name) {
////      String s = getParameter(name);
//      try {
//          return Integer.parseInt(s);
//      } catch (Exception e) {
//          return -1;
//      }
//  }
//
//  /**
//   * This method
//   *  - creates a SessionManager a page Listener
//   *  - adds a page Listener
//   *  - adds a page Listener
//   * @throws Exception if anything goes wrong
//   */
//  public void initialize() throws Exception {
//      addPageListener(this);
//      setLanguagePrefrence(new LanguagePreferences("en"));
//      //setApplicationName(APPLICATION_SHORT_NAME);
//  }

  /**
   * 	This method/event will get fired each time a page is requested by the browser before Html is generated and sent back.
   *    This is used by subclasses to set default values for its own components 
   */
  public void eaInitDefaultValues()
  {
  }
      
  /**
   * 	This method/event will be called within pageRequested.
   *	@param p PageEvent
   *  @throws EAException if anything goes wrong
   */
//  public void eaPageRequested(PageEvent p) throws EAException
  {
  }
      
  /**
   * 	This method/event will get fired each time a page is requested by the browser before Html is generated and sent back.
   *	@param p PageEvent
   *  @throws Exception if anything goes wrong
   */
//  public void pageRequested(PageEvent p) throws Exception {
//  	try
//	{
//      checkPageRedirect();
//      if (hasPageRedirected())
//          p.setContinueProcessing(false);
//      else
//      {
//        popupMsg = new StringBuffer();
//
//	  	_userId = (String) SessionManager.getValue(getSession(), SESS_EMPLOYEE_NUMBER);
//	  	if (Util.isEmptyString(_userId) && !(this instanceof LoginController) && !(this instanceof InvalidLoginContoller)) {
//			MessageLog.writeInfoMessage("No login record found in DB", this);
//			setPageInfo(Message.getErrorMessage(ERROR_LOGIN_INFO_MISSING));
//			sendRedirect("/" + getApplicationName() + "/Jsp/common/InvalidLogin.jsp?pageAction="+Constants.PAGE_ACTION_REFRESH);
//	        p.setContinueProcessing(false);
//	        return;
//	  	}
//	  	
//  		_userMenu = (String) SessionManager.getValue(getSession(), SESS_EMPLOYEE_USER_MENU);
//  		_userFunction = (Vector) SessionManager.getValue(getSession(), SESS_EMPLOYEE_USER_FUNCTION);
//  		_userRole = (String) SessionManager.getValue(getSession(), SESS_EMPLOYEE_ROLE);
//
//      		if (_menuDef != null) {
//      			String menu = (String) SessionManager.getValue(getSession(), Constants.SESS_EMPLOYEE_USER_MENU);
//      			
//      			if (Util.isEmptyString(menu)) {
//      				_menuDef.setHtml("oM.makeMenu('','','','','',1,0);");
//      				_menuDef.setVisible(true);
//      			} else {
//      				_menuDef.setHtml(menu);
//      				_menuDef.setVisible(true);
//      			}
//      		}
//
//
//      		processPageInfo();
//      	
//          eaPageRequested(p);
//      }
//    }
//  	catch(EAException ex)
//	{
//  		processEAException(ex);
//  	}
//  	catch(Exception ex)
//	{
//  	    processException(ex);
//  	}
//  }
  
  protected void processPageInfo() {
//      if (_information != null) {
//	        String info = (String) SessionManager.getValue(getSession(), SessionManager.SESSION_PAGE_INFO);
//	       
//	        if (Util.isEmptyString(info)) {
//	            _information.setText("");
//	            _information.setVisible(false);
//	        } else {
//	            _information.setText(info);
//	            _information.setVisible(true);
//	            SessionManager.removeValue(getSession(), SessionManager.SESSION_PAGE_INFO);
//	        }
//	        
//	        //hijack info by popup
//          if (_information.getVisible() && !Util.isEmptyString(_information.getText())) {
//              popupMsg.append("" + _information.getText());
//	            _information.setText("");
//	            _information.setVisible(false);
//          }
//
//	      }
//        
//        if (_errorMessage != null) {
//            if (_errorMessage.getErrorCount() > 0) {
//                if (popupMsg.length() > 0) {
//                    popupMsg.append("\\n");
//                }
//                popupMsg.append("" + _errorMessage.getErrorCount() + " " + Message.getErrorMessage(ERROR_OCCURS)); 
//                // ##0 error(s) occur(s), please note the warning message on page top
//            }
//        }
//
//        //chop out-date script in controller cache(session)
//        String onload = (getOnLoad()==null)?"":getOnLoad();
//        if (!Util.isEmptyString(onload)) {
//            int idx = onload.indexOf(SPLITTER_OF_POPUP_JSCRIPT);
//            if (idx >= 0) onload = onload.substring(0, idx);
//        }
//
//        //append new popup script
//        if (popupMsg.length() > 0) {
//            this.setOnLoad( onload + SPLITTER_OF_POPUP_JSCRIPT + "alert('" + popupMsg.toString().replaceAll("'", "\\\\'") + "');" );
//        } else {
//            this.setOnLoad( onload );
//        }
  }

  /**
   * 	This method/event will get fired each time a page is requested by the browser after Html is generated and sent back.
   *	@param p PageEvent
   *  @throws Exception if anything goes wrong
   */
//  public void pageRequestEnd(PageEvent p) throws Exception {
//  	SessionManager.removeValue(getSession(), SessionManager.SESSION_PAGE_INFO);
//  	String info = (String) SessionManager.getValue(getSession(), SessionManager.SESSION_PAGE_INFO);
//  }

  /**
   * 	This method occurs each time a page is submitted before submit values are copied to components.
   *	@param p PageEvent
   */
//  public void pageSubmitted(PageEvent p) {
//      checkPageRedirect();
//      if (hasPageRedirected())
//          p.setContinueProcessing(false);
//  }

  /**
   * 	This method occurs each time a page is submitted after submit values are copied to components.
   *	@param p PageEvent
   */
//  public void pageSubmitEnd(PageEvent p) {
//
//  }


  /**
   *	This method is used to signal that a submit button has been pressed, and that the user has intended
   *  to submit the descendant class/page.
   *	@param e SubmitEvent
   *  @throws Exception if anything goes wrong
   *	@return true to continue processing events on the page or false to abort
   */

//  public boolean eaSubmitPerformed(SubmitEvent e) throws EAException {
//  		return true;
//  }

  /**
   *	This method is used to signal that a submit button has been pressed, and that the user has intended
   *  to submit the descendant class/page.
   *	@param e SubmitEvent
   *  @throws Exception if anything goes wrong
   *	@return true to continue processing events on the page or false to abort
   */

//  public boolean submitPerformed(SubmitEvent e) throws Exception {
//  	try
//	{   
//  		return eaSubmitPerformed(e); 
//	}
//  	catch(EAException ex)
//	{
//  		processEAException(ex);
//  		return true;
//	}
//  	catch(Exception ex)
//	{
//  		//throw ex;
//  	    processException(ex);
//  	    return true;
//	}
//  }

  /**
   * This method will check if the page is expired or the session is expired and redirect to the appropriate page
   */
//  private void checkPageRedirect() {
//
//      try {
//          _redirected = false;
//          if (_checkSessionExpired)
//              if (isSessionExpired()) {
//              	System.out.println("*********Inside isSessionExpired *********");
//                  _redirected = true;
//					gotoSiteMapPage(SiteMapConstants.SESSION_EXPIRED_PAGE);
//                  return;
//              }
//
//          if (_checkPageExpired)
//              if (isExpired()) {
//              	System.out.println("*********Inside isExpired *********");
//                  _redirected = true;
//					gotoSiteMapPage(SiteMapConstants.PAGE_EXPIRED_PAGE);
//                  return;
//              }
//
//          if (_checkDB) {
//              DBConnection conn = null;
//              try {
//                  conn = DBConnection.getConnection(getApplicationName());
//              }
//              catch (Exception e) {
//                  _redirected = true;
//                  e.printStackTrace();
//					gotoSiteMapPage( SiteMapConstants.DB_CONNECT_FAILED_PAGE);
//              }
//              finally {
//                  if (conn != null)
//                      conn.freeConnection();
//              }
//          }
//
//      } catch (Exception e) {
//          MessageLog.writeErrorMessage("checkPageRedirect()", e, this);
//      }
//  }

  /**
   * Returns true if either the page or the session is expired and the page has been redirected to another page indicating that
   */
  public boolean hasPageRedirected() {
      return _redirected;
  }

  /**
   * Tells the page whether or not it should check whether the page is expired on each request
   */
  public void setCheckPageExpired(boolean check) {
         _checkPageExpired = check;
  }
  /**
   * Tells the page whether or not it should check whether the session is expired on each request
   */
  public void setCheckSessionExpired(boolean check) {
         _checkSessionExpired = check;
  }

  /**
   * If you want to have the browser cache this page instead of reloading it each time call this method with a true argument.
   */
//  public void setNoCache(boolean noCache) {
//      _noCache.setVisible(noCache);
//  }
  public void setPageInfo(String info) {
    
//  	SessionManager.putValue(getSession(), SessionManager.SESSION_PAGE_INFO, info);
}
  
  public void returnHome() throws IOException {
//  	System.out.println("Home url = " + getServerURL() + Message.getProperty("ea.defaultPage"));
//  	sendRedirect( getServerURL() + Message.getProperty("ea.defaultPage"));
  }

  /**
   * This function handle the EAException  
   */
  private void processEAException(EAException e) 
  {
  	e.printStackTrace();
  	log.severe(e.toString());
  	if ( !Util.isEmptyString(e.getErrorCode()))
//  		_errorMessage.addErrorMessage(Message.getErrorMessage(e.getErrorCode()));
//  	else
  		//_errorMessage.addErrorMessage(e.getMessage());
  	  processException(e.getCause());
  }

  private void processException(Object e) 
  {
    Exception ex = (Exception) e;
      
// 	if (e instanceof DataStoreException)
// 	   _errorMessage.addErrorMessage(Message.getErrorMessage("er9001") + ex.getMessage()); // DB error, save failed. blah..balh...blaah...blah.
// 	
// 	else if (e instanceof DirtyDataException)
//  	   _errorMessage.addErrorMessage(Message.getErrorMessage("er9002")); // Working data is out of date, please reload and try again
// 	
// 	else if (e instanceof java.sql.SQLException)
//  	   _errorMessage.addErrorMessage(Message.getErrorMessage("er9001") + ex.getMessage()); // DB error, save failed. blah..balh...blaah...blah.
// 	
// 	else
//  	   _errorMessage.addErrorMessage(Message.getErrorMessage("er9003") + ex.getMessage()); // Other exception occurs. blah..balh...blaah...blah.
  }

}
