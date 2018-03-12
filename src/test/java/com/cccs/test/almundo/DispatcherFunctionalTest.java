package com.cccs.test.almundo;

import com.cccs.test.almundo.manager.CallManager;
import com.cccs.test.almundo.manager.Dispatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml" })
public class DispatcherFunctionalTest {

   @Autowired
   private Dispatcher dispatcher;

   @Test
   public void dispatchCallTest(){
      for (int i = 1; i <= 10; i++) {
         dispatcher.dispatchCall(new CallManager(String.valueOf(i)));
      }
      dispatcher.shutDownExecutor();
   }



}