package com.cccs.test.almundo.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {

   private List<Observer> observers = new ArrayList<>();

   public void attach(Observer observer){
      observers.add(observer);
   }

   public void unattach(Observer observer) {
      observers.remove(observer);
   }

   public boolean isAttach(Observer observer){
      return observers.contains(observer);
   }

}
