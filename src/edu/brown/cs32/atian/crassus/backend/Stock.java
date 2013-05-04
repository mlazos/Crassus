/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import edu.brown.cs32.atian.crassus.gui.StockPlot;
import edu.brown.cs32.atian.crassus.gui.TimeFrame;
import edu.brown.cs32.atian.crassus.indicators.Indicator;

import java.util.ArrayList;
import java.util.Date;
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
    void setTimeFrame(TimeFrame timeFrame);
    
    void setTimeFrame(Date beginTime, Date endTime);
    
    StockFreqType getCurrFreq();   // MINUTELY, DAILY, WEEKLY, MONTHLY
    TimeFrame getTimeFrame();
    
    StockRealTimeData getStockRealTimeData();
    
    List<StockTimeFrameData> getStockPriceData(StockFreqType freq);
    
    ArrayList<Indicator> getEventList();
    void removeEventList();
    void addEvent(Indicator event);
    void deleteEvent(Indicator event);   
    
    void refresh();
    
    void addToPlot(StockPlot stockPlot);   
    
    Date getStartTime();
    
    StockEventType isTriggered();

	void setSelectedIndicatorIndex(int i);
    int getSelectedIndicatorIndex();
}
