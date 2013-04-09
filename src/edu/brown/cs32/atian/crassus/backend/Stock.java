/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

/**
 *
 * @author lyzhang
 */
public interface Stock {
     void setTicker(String ticker);
     String getTicker();
     
     boolean initialize();   // false mean it fails to get data from data source
     
     StockHistData getStockHistData(String freq);   // freq = "daily" or "monthly" or "weekly"
     StockRealTimeData getStockRealTimeData();     
     
     ArrayList<Event> getEventList();     
     
     void addEvent(Event event);
     void refresh();
}
