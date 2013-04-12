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
    void setFreq(String freq);   // // freq = "daily" or "monthly" or "weekly", we will support daily first
    
    ArrayList<StockTimeFrameData> getHistData(int begYear);    // latest data last
    // see example of historial data: (we will make lastest data last)
    // http://ichart.finance.yahoo.com/table.csv?s=AAPL&c=1962
    
    float getWeek52Low();
    float getWeek52High();     
    
    String getFreq();    
}
