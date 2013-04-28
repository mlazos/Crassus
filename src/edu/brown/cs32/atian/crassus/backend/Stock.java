/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lyzhang
 */
public interface Stock {
    //void setTicker(String ticker);

    boolean initialize();   // false mean it fails to get data from data source

    void addEvent(Indicator event);

    String getTicker();

    String getCompanyName();
 
    // getStockHistData and  getStockRealTimeData are now combined into a single method getStockPriceData()
    List<StockTimeFrameData> getStockPriceData(String freq);
    
    ArrayList<Indicator> getEventList();

//    Indicator getEvent(String eventName);

    double getWeek52Low();
    double getWeek52High();    
    
    void refresh();
    void deleteEvent(Indicator event);
}
