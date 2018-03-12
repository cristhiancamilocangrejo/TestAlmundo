package com.cccs.test.almundo.manager;

import com.cccs.test.almundo.entities.Empleado;
import com.cccs.test.almundo.entities.Operador;
import com.cccs.test.almundo.exception.EmployeeNotFoundException;
import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

@RunWith(MockitoJUnitRunner.class)
public class CallManagerTest {

   @InjectMocks
   private CallManager callManager;

   @Mock
   private EmployeeManager employeeManager;

   @Mock
   private RetryTemplate retryTemplate;


   @Before
   public void init() {
      MockitoAnnotations.initMocks(this);
   }

   @Test
   public void runTest() throws EmployeeNotFoundException {

      when(employeeManager.findEmployee()).thenReturn(getEmployee());

      callManager.run();

      verify(retryTemplate, times(1)).execute(any(RetryCallback.class), any(RecoveryCallback.class));
      verify(employeeManager, times(1)).findEmployee();

   }

   private Empleado getEmployee(){

      Operador operador = mock(Operador.class);
      doNothing().when(operador).receiveCall(anyString());

      return operador;
   }


}
