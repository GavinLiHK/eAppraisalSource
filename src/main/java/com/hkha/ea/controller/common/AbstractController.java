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


//import com.salmonllc.html.events.*;
//import com.salmonllc.jsp.JspController;
//import com.salmonllc.jsp.JspLink;
//import com.salmonllc.localizer.LanguagePreferences;
//import com.salmonllc.util.MessageLog;
//import com.salmonllc.sql.DBConnection;

/**
This controller is used as the ancestor for the other controllers in the example app.
Generally, every controller will extend from this one, but the examples all extend JspController in order to keep them encapsulated.
This controller has more functionality than is requied by the sample application so it can be copied and used as a starting point for your application's base controller.
It implements frequently used interfaces at the base level to avoid having to implement all the methods in each controller.
Also, it provides some basic functionality such as checking if the session or page is required and redirecting the user to the appropriate page.
*/
abstract public class AbstractController{
//	abstract public boolean eaSubmitPerformed(SubmitEvent e) throws EAException;
//	abstract public void eaPageRequested(PageEvent p) throws EAException;
}
