package com.hkha.ea.common;


/**
* This Exception is 
*/
public class EAException extends Exception {
  private String errorCode=null;
  

  /**
   * @return  The root cause of the exception if there is one or null if not
   */

  public Throwable getRootCause() {
      return getCause();
  }


  /**
   * Constructs an Exception with no specified detail message.
   */
  public EAException() {
      super();
  }

  /**
   * Constructs an Exception with specified exception object.
   */
  public EAException(Exception e) {
  	super(e);
//  	_rootCause = e;
  }

  /**
   * Constructs an Exception with the specified detail message.
   * @param s the error code.
   */
  public EAException(String s) {
    errorCode = s;
    System.out.println("***** error code = " + errorCode);
  }

   /**
   * Constructs an Exception with the specified detail message.
   * @param s the error code.
   */
  public EAException(String s, Throwable root) {
      super(root);
      errorCode = s;
  }
  
  /**
   * Retrieve error code
   */
  public String getErrorCode() {
      return errorCode;
  }
  
}
