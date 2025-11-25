package com.hkha.ea.controller.common;

//import com.salmonllc.html.events.PageEvent;
//import com.salmonllc.sql.DataStoreBuffer;
import com.hkha.ea.common.EAException;

public abstract class ListPageController extends BaseController {
  /**
   * This function is called within commongGenerateList and is used to put 
   * controller-specific default values or actions here.  
   */
  public abstract void generateList() throws EAException;

  /**
   * This function is called within commonResetList and is used to put 
   * controller-specific default values or actions here.  
   */
  public abstract void resetList() throws EAException;
  
//  public void eaPageRequested(PageEvent e) throws EAException {
////    super.pageRequested(e);
//  	try
//	{
//	    if (!isReferredByCurrentPage()) {
//	      String pageAction = this.getParameter(REQ_PARAM_PAGE_ACTION);
//	      if (pageAction == null) {
//	        //
//	      } else if (pageAction.equals(PAGE_ACTION_REFRESH)) {
//	        commonGenerateList();
//	      } else if (pageAction.equals(PAGE_ACTION_RESET)) {
//	        commonResetList();
//	      } else if (pageAction.equals(PAGE_ACTION_KEEP)) {
//	        //
//	      } else {
//	        // 
//	      }
//	    }
//    }
//  	catch(EAException ex)
//	{
//  		throw ex;
//	}
//  	catch(Exception ex)
//	{
//  		throw new EAException(ex);
//	}
//  }

  public void commonGenerateList() throws EAException
  {
  	try
	{
  		generateList();
	}
  	catch(EAException e)
	{
  		throw e;
	}
  	catch(Exception e)
	{
  		throw new EAException(e);
	}
  }

  /**
   * This function reset all input fields to empty string.   
   */
//  public void commonResetList() throws EAException 
//  {
//  	try
//	{
//		// Loop through all input fields to perform reset action
//		for ( Enumeration coms = getComponents(); coms.hasMoreElements(); )
//		{
//			Object obj = coms.nextElement();
//			if ( obj instanceof com.salmonllc.html.HtmlFormComponent )
//			{
//				// Reset all input fields
//				((com.salmonllc.html.HtmlFormComponent)obj).setValue("");
//			}
//		}
//	
//		// Loop through all data sources to perform reset action
//		
//		for ( Enumeration dataStores = getDataSources(); dataStores.hasMoreElements(); )
//		{
//			Object obj = dataStores.nextElement();
//			// Reset the data store
//			((DataStoreBuffer)obj).reset();
//		}
//		
//		// Do controller specific action here.
//	    resetList();
//	}
//  	catch(EAException e)
//	{
//  		throw e;
//	}
//  	catch(Exception e)
//	{
//  		throw new EAException(e);
//	}
//  }
  
}
