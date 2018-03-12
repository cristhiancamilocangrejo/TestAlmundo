package com.cccs.test.almundo.util;

import com.cccs.test.almundo.entities.*;
import com.cccs.test.almundo.observer.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Esta es una clase utilitaria que crea un conjunto de empleados, de acuerdo a un rángo
 * definido en un archivo de configuración properties
 *
 *
 */
public class EmployeeUtil {

   private Integer numOperadores;
   private Integer numSupervisores;
   private Integer numDirectores;

   public EmployeeUtil(){
      try{
         InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("employee.properties");
         Properties properties = new Properties();
         properties.load(inputStream);
         numOperadores = Integer.valueOf(properties.getProperty("numOperadores"));
         numSupervisores = Integer.valueOf(properties.getProperty("numSupervisores"));
         numDirectores = Integer.valueOf(properties.getProperty("numDirectores"));
      }catch (IOException ex){
         System.err.println(ex);
      }
   }

   public  List<Empleado> createEmployees(){
      List<Empleado> empleados = new ArrayList<>();

      empleados.addAll(createEmployees(numOperadores, Tipo.OPERADOR));
      empleados.addAll(createEmployees(numSupervisores, Tipo.SUPERVISOR));
      empleados.addAll(createEmployees(numDirectores, Tipo.DIRECTOR));

      return empleados;
   }

   private  List<Empleado> createEmployees(int numEmpleados, Tipo tipo){
      List<Empleado> empleados = new ArrayList<>();
      Subject subject = new Subject();

      for (int i = 1; i <= numEmpleados; i++) {
         Empleado empleado = null;
         switch (tipo){
            case OPERADOR:
               empleado = new Operador();
               break;
            case SUPERVISOR:
               empleado = new Supervisor();
               break;
            case DIRECTOR:
               empleado = new Director();
               break;
         }
         empleado.setIdEmployee(String.valueOf(i));
         empleado.setSubject(subject);
         empleados.add(empleado);
      }
      return empleados;
   }

}
