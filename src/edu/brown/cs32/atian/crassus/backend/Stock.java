/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import java.util.ArrayList;

/**
 *
 * @author lyzhang
 */
public interface Stock {
     //void setTicker(String ticker);
     boolean initialize();   // false mean it fails to get data from data source
     void addEvent(StockEvent event);
     void deleteEvent(String eventName);     
     StockEvent getEvent(String eventName);
     
     String getTicker();
     String getCompanyName();  
   
     StockHistData getStockHistData(String freq);   // freq = "daily" or "monthly" or "weekly"
     StockRealTimeData getStockRealTimeData();     
     
     ArrayList<StockEvent> getEventList();     
  
     void refresh();
}
