/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import edu.brown.cs32.atian.crassus.gui.StockPlot;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lyzhang
 */
public interface Stock {
    //void setTicker(String ticker);

    boolean initialize();   // false mean it fails to get data from data source

    String getTicker();

    String getCompanyName();
    
    void setCurrFreq(StockFreqType currFreq);   // MINUTELY, DAILY, WEEKLY, MONTHLY
 
    StockRealTimeData getStockRealTimeData();
    
    List<StockTimeFrameData> getStockPriceData(StockFreqType freq);
    
    ArrayList<Indicator> getEventList();
    void removeEventList();
    void addEvent(Indicator event);
    void deleteEvent(Indicator event);
    
//    Indicator getEvent(String eventName);

    double getWeek52Low();
    double getWeek52High();    
    
    void refresh();
    
    void addToPlot(StockPlot stockPlot);   
    
    StockEventType isTriggered();
}
