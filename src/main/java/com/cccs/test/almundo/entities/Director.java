package com.cccs.test.almundo.entities;

public class Director extends Empleado {

   @Override
   public String getTipo() {
      return Tipo.DIRECTOR.getTipo();
   }
}
