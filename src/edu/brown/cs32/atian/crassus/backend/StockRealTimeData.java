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
public interface StockRealTimeData {
    
    // return up to days real time data (every row corresponds to about a minute) including the latest
    ArrayList<StockTimeFrameData> getRealTimeData(int days);   // latest data last
    // see example for real time data
    // http://chartapi.finance.yahoo.com/instrument/1.0/msft/chartdata;type=quote;range=1d/csv/
    
    float getPertChgFromOpen();    
    float getOpenPrice();
    float getCurrPrice();
    float getTodayLow();
    float getTodayHigh();
    
    void refresh();  
}
