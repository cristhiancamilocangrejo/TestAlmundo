package com.cccs.test.almundo.manager;

import com.cccs.test.almundo.entities.Empleado;
import com.cccs.test.almundo.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

/***
 * Esta clase se encarga de gestionar los recursos
 * de empleados disponibles y enlazar las llamadas
 * provenientes a los mismos.
 *
 * Por otra parte provee una lógica de reintentos para
 * las llamadas que no pueden ser ejecutadas.
 *
 *
 */
@Component
public class CallManager implements Runnable {

   @Autowired
   private EmployeeManager employeeManager;
   @Autowired
   private RetryTemplate retryTemplate;

   private String callIdentifier;

   public CallManager(){}

   public CallManager(String callIdentifier){
      this.callIdentifier =  callIdentifier;
   }

   /**
    * Por cada llamada se abre un hilo que la procesa
    *
    * Este hilo se mantiene vivo hasta que la llamada puede ser atendida o cuando
    * la lògica de reintentos no logra realizar el enlace con algùn empleado disponible
    *
    */
   @Override
   public void run() {

      RetryCallback<Void, EmployeeNotFoundException> retryCallback = retryContext -> {
         processCall();
         return null;
      };

      try {
         retryTemplate.execute(retryCallback, recoveryOperation());
      } catch (EmployeeNotFoundException ex){
         System.err.println(ex);
      }

   }

   /**
    * Después de tres reintentos de enlazar la llamada con un cliente, se lanza un mensaje al usuario
    * @return
    */
   private RecoveryCallback<Void> recoveryOperation() {
      return context -> {
         System.out.println("Your call wasn't possible to process");
         return null;
      };
   }


   private void processCall() throws EmployeeNotFoundException{
      System.out.println(callIdentifier + " is running");
      Empleado empleado = employeeManager.findEmployee();
      empleado.receiveCall(callIdentifier);
      System.out.println(callIdentifier + " finished");
   }

   public void setEmployeeManager(EmployeeManager employeeManager) {
      this.employeeManager = employeeManager;
   }

   public void setRetryTemplate(RetryTemplate retryTemplate) {
      this.retryTemplate = retryTemplate;
   }
}
