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
public interface StockHistData {
    void setTicker(String ticker);   
    void setFreq(String freq);   // // freq = "daily" or "monthly" or "weekly"
    
    ArrayList<StockHistTimeFrameData> getHistData();   
    float getOpenPrice();    
    float getPertChgFromOpen();
    float getWeek52Low();
    float getWeek52High();     
    
    String getFreq();    
}
