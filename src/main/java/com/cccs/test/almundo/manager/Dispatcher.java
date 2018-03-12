package com.cccs.test.almundo.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 *
 * Esta clase representa el punto de entrada y/o interfaz
 * para recibir llamadas
 *
 */
@Component("dispatcher")
public class Dispatcher {

   @Autowired
   private ThreadPoolTaskExecutor taskExecutor;
   @Autowired
   private EmployeeManager employeeManager;
   @Autowired
   private RetryTemplate retryTemplate;

   /**
    * Este método procesa las llamadas de los clientes
    * que se encapsulan en el parámetro
    *
    * Para soportar 10 llamadas concurrentes, se tiene un taskExecutor
    * el cuál provee 10 hilos con ejecución en paralelo y provee una cola
    * para las llamadas que no pueden ser gestionadas
    *
    * El taskexecutor se encuentra configurado mediante XML
    *
    * @param callManager
    */
   public void dispatchCall(CallManager callManager){
      callManager.setEmployeeManager(employeeManager);
      callManager.setRetryTemplate(retryTemplate);

      taskExecutor.execute(callManager);
      System.out.println("Active threads: " + taskExecutor.getActiveCount());
   }


   /**
    *
    * Este método apaga la ejecución del taskexecutor cuándo todas los hilos
    * han terminado su trabako
    *
    */
   public void shutDownExecutor(){
      while (true)
      {
         int count = taskExecutor.getActiveCount();
         System.out.println("Active Threads : " + count);
         try {
            Thread.sleep(5000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         if (count == 0) {
            taskExecutor.shutdown();
            break;
         }
      }
   }
}
