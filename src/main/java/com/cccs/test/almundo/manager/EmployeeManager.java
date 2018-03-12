package com.cccs.test.almundo.manager;

import com.cccs.test.almundo.entities.Director;
import com.cccs.test.almundo.entities.Empleado;
import com.cccs.test.almundo.entities.Operador;
import com.cccs.test.almundo.entities.Supervisor;
import com.cccs.test.almundo.exception.EmployeeNotFoundException;
import com.cccs.test.almundo.util.EmployeeUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Esta clase gestiona los empleados y su búsquda para atender llamadas
 */
@Component
public class EmployeeManager {

   private List<Empleado> employeeList;

   public EmployeeManager(){
      employeeList = new EmployeeUtil().createEmployees();
   }

   /**
    * Este método gestiona los empleados disponibles y posee la lógica referente a:
    *
    * Si no hay operadores disponibles, se transiferiere a un superivor y en caso de ser
    * necesario a un director
    *
    * @return
    * @throws EmployeeNotFoundException
    */
   @Transactional
   public Empleado findEmployee() throws EmployeeNotFoundException {

      Empleado operador = findOperador();
      Empleado supervisor = findSupervisor();
      Empleado director = findDirector();

      if (Objects.isNull(operador) && Objects.isNull(supervisor) && Objects.isNull(director)){
         throw new EmployeeNotFoundException("Employee available not found");
      } else {
         return find(operador, supervisor, director);
      }
   }

   private Empleado find(Empleado... empleados){
      for (Empleado empleado: empleados) {
         if (empleado!=null){
            return empleado;
         }
      }
      return null;
   }

   private Empleado findOperador() {
      return employeeList.stream().filter(empleado -> (empleado instanceof Operador) && (!empleado.isAttached())).findAny().orElse(null);
   }

   private Empleado findSupervisor() {
      return employeeList.stream().filter(empleado -> (empleado instanceof Supervisor) && (!empleado.isAttached())).findAny().orElse(null);
   }

   private Empleado findDirector() {
      return employeeList.stream().filter(empleado -> (empleado instanceof Director) && (!empleado.isAttached())).findAny().orElse(null);
   }


}
