/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

/**
 *
 * @author lyzhang
 */
public interface StockRealTimeData {
    float getOpenPrice();
    float getCurrPrice();
    float getTodayLow();
    float getTodayHigh();
    
    void refresh();  
}
