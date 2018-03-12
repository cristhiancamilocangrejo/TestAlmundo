package com.cccs.test.almundo.exception;

public class EmployeeNotFoundException extends Exception {

   public EmployeeNotFoundException() {
      super();
   }

   public EmployeeNotFoundException(String message) {
      super(message);
   }

   public EmployeeNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }
}
