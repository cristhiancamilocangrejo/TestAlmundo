package com.cccs.test.almundo.entities;

public class Operador extends Empleado {

   @Override
   public String getTipo() {
      return Tipo.OPERADOR.getTipo();
   }

}
