package com.cccs.test.almundo.entities;

public enum Tipo {

   OPERADOR("Operador"), SUPERVISOR("Supervisor"), DIRECTOR("Director");

   private String tipo;

   Tipo(String tipo){
      this.tipo = tipo;
   }

   public String getTipo(){
      return tipo;
   }
}
