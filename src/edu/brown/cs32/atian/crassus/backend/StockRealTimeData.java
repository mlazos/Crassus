/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import java.util.List;

/**
 *
 * @author lyzhang
 */
public interface StockRealTimeData {
    boolean Init();
    // return up to days real time data (every row corresponds to about a minute) including the latest
    List<StockTimeFrameData> getRealTimeData();   // latest data last

    
    double getPertChgFromOpen();    
    double getOpenPrice();
    double getCurrPrice();
    double getTodayLow();
    double getTodayHigh();
    
    void refresh();  
}
