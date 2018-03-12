package com.cccs.test.almundo.entities;

import com.cccs.test.almundo.observer.Observer;
import com.cccs.test.almundo.observer.Subject;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

public abstract class Empleado extends Observer
{
   protected String idEmployee;

   public Empleado(){}

   public Empleado(String idEmployee){
      this.idEmployee = idEmployee;
   }

   public void setSubject(Subject subject){
      this.subject = subject;
   }

   @Transactional
   public void receiveCall(String callId){
      try{
         //Attach to observer
         this.subject.attach(this);
         System .out.println("Call "+ callId +" Received by: " + getTipo() + " " + idEmployee);
         Random random = new Random();
         int durationTimeInSeconds = (random.nextInt(10 - 5) + 5)*1000;
         Thread.sleep(durationTimeInSeconds);
         this.subject.unattach(this);
      }catch (InterruptedException ex){
         System.err.println(ex);
      }
   }

   public void setIdEmployee(String idEmployee) {
      this.idEmployee = idEmployee;
   }
   public boolean isAttached(){
      return this.subject.isAttach(this);
   }

   public abstract String getTipo();

}
