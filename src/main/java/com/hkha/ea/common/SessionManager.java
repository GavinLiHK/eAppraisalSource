package com.hkha.ea.common;

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


import jakarta.servlet.http.HttpSession;



/**
* Used to manage values put on the session. This class mainly adds the APP_NAME to all keys added with it.
* The class is not actually used in the application, but is here in case you want to use the sample application
* as a starting point for your own application. Generally it isn't good practice to write to the session directly.
* Instead implement get/set method in this class that do it for you. Then in your application always access the session
* via the get and set methods in this class. It makes it much easier to trap errors related to storing items on the session.
*/
public class SessionManager {

public static String _appName=Constants.APPLICATION_SHORT_NAME;
public static final String SESSION_PAGE_MENU = "pageMenu";
public static final String SESSION_PAGE_INFO = "pageInfo";

/**
 *	This method returns the current time with precision into milliseconds.
 *	@return java.sql.Timestamp current time
 */

public static java.sql.Timestamp getCurrentTime() {
    return new java.sql.Timestamp(System.currentTimeMillis());
}

/**
 * Gets a value off the session. Automatically using the APP_NAME in the key value.
 * @return java.lang.Object
 * @param name java.lang.String
 */
public static Object getValue(HttpSession sess, String name) {
    return sess.getAttribute("SessionManager_" + _appName + "_" + name);
}

public static Object getJspValue(HttpSession sess, String appName, String name) {
	StringBuffer sb = new StringBuffer();
	sb.append("$jsp$.").append(appName).append(".Jsp.");
	sb.append(name).append(".jsp");
	return sess.getAttribute(sb.toString());
}
private static void init(HttpSession sess) {
    if (getValue(sess, Constants.SESS_INIT) == null) {
        putValue(sess, Constants.SESS_INIT, new Boolean(true));
    }
    //If you want to load any values from the database or property files into the session,
    //this is the place to do it.
}
/**
 * Puts a value on the session. Automatically using the APP_NAME in the key value.
 * @param name java.lang.String
 * @param value java.lang.Object
 */
public static void putValue(HttpSession sess, String name, Object value) {
    sess.setAttribute("SessionManager_" + _appName + "_" + name, value);
}


/**
 * Removes a value from the session. Automatically using the APP_NAME in the key value.
 */
public static void removeValue(HttpSession sess, String name) {
    sess.removeAttribute("SessionManager_" + _appName + "_" + name);
}

public static void invalidateSession(HttpSession sess) {
  try {
    sess.invalidate();
  } catch (IllegalStateException ex) {
  } catch (Exception ex) {
  }
}

}
