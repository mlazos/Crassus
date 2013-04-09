/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

/**
 *
 * @author lyzhang
 */
 interface StockHistTimeFrameData {
     String getBeginTime();   
     float getOpenPrice();
     float getClosePrice();
     float getHigh();
     float getLow();    
}
