package com.cccs.test.almundo.entities;

public class Supervisor extends Empleado {

   @Override
   public String getTipo() {
      return Tipo.OPERADOR.getTipo();
   }
}
