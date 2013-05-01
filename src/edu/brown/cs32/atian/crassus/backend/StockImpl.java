/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import edu.brown.cs32.atian.crassus.gui.SeriesWrapper;
import edu.brown.cs32.atian.crassus.gui.StockPlot;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.jfree.data.time.Day;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author lyzhang
 */
public class StockImpl implements Stock {

    String _ticker;
    String _companyName = null;
    
    StockHistData _minutely = null;    
    StockHistData _daily = null;
    StockHistData _weekly = null;
    StockHistData _monthly = null;
    StockRealTimeData _realTime = null;
    Double _week52Low = null;
    Double _week52High = null;
    
    ArrayList<Indicator> _events = null;

    
    StockFreqType _currFreq = StockFreqType.DAILY;    // be default se use daily
    
    public StockImpl(String ticker) {
        _ticker = ticker;
        _minutely = new StockHistDataMinutely(_ticker);
        _daily = new StockHistDataDaily(_ticker);
        _weekly = new StockHistDataWeekly(_ticker);
        _monthly = new StockHistDataMonthly(_ticker);
        _realTime = new StockRealTimeDataImpl(_ticker);
        _events = new ArrayList<Indicator>();

        // if getCompanyName() return same string as ticker that the ticker is invalid
        _companyName = getCompanyName();
        if (_companyName.equalsIgnoreCase(_ticker)) {
            throw new IllegalArgumentException("Error: ticker " + ticker + " does not exist!");
        }
    }

    @Override
    public boolean initialize() {   // false mean it fails to get data from data source
        boolean init = false;
        
        switch(this._currFreq) {
            case MINUTELY:
                init = _minutely.Init();
                break;
            case DAILY:
                init = _daily.Init();
                break;
            case WEEKLY:
                init = _weekly.Init();
                break;
            case MONTHLY:
                init = _monthly.Init();
                break;
        }

        boolean init2 = this._realTime.Init();
        
        if (init && init2 ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getTicker() {
        return this._ticker;
    }

    @Override
    // http://stackoverflow.com/questions/885456/stock-ticker-symbol-lookup-api
    public String getCompanyName() {
        if (_companyName != null) {
            return _companyName;
        }
        //http://finance.yahoo.com/d/quotes.csv?s=MSFT&f=sn
        String urlString = "http://finance.yahoo.com/d/quotes.csv?s=" + _ticker + "&f=sn";

        HttpURLConnection connection = null;
        URL serverAddress = null;
        //OutputStreamWriter wr = null;
        BufferedReader reader = null;
        //StringBuilder sb = null;
        String line = null;

        try {
            serverAddress = new URL(urlString);
            //set up out communications stuff
            connection = null;

            //Set up the initial connection
            connection = (HttpURLConnection) serverAddress.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);

            connection.connect();
            //read the result from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            line = reader.readLine();  // skip the header line
            String[] splitted = line.split(",");
            if (splitted.length == 2) {
                _companyName = splitted[1];
                _companyName = _companyName.substring(1, _companyName.length()-1);
            } else {
                _companyName = "";
            }
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
            _companyName = "";
        } catch (ProtocolException e) {
            e.printStackTrace();
            _companyName = "";
        } catch (IOException e) {
            e.printStackTrace();
            _companyName = "";
        }
        return _companyName;
    }

    @Override    
    public void setCurrFreq(StockFreqType currFreq) {
        _currFreq = currFreq;
        this.refreshStockPrice();
        this.refreshIndicator();
    }
    
    // combine both the history data with today's data so the plot and indicator calculation don't need to combine the hist data with realtime data
    @Override
    public List<StockTimeFrameData> getStockPriceData(StockFreqType freq) {  // freq = "minutely", or "daily" or "monthly" or "weekly"

        List<StockTimeFrameData> realTime = this._realTime.getRealTimeData();
        List<StockTimeFrameData> result = null;
        if (freq == StockFreqType.MINUTELY) {   
            return _minutely.getHistData();   // just return MINUTELY data, which includes most recent 15 days' minute by minute data including most recent minute
        }
        
        // we other frequency (daily, weekly, monthly) we need to combine all history data with  today's most recent data.
        
        if (freq == StockFreqType.DAILY) {
            result = _daily.getHistData();
        } else if (freq == StockFreqType.WEEKLY) {
            result = _weekly.getHistData();
        } else if (freq == StockFreqType.MONTHLY) {
            result = _monthly.getHistData();
        }
        
        // latestRealTime is most recent data in realtime
        StockTimeFrameData latestRealTime = new StockTimeFrameData(realTime.get(realTime.size() - 1));
        if (realTime.size() >= 1) {

            long tmp = (Long.parseLong(latestRealTime.getTime()));
            tmp = tmp * 1000;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(tmp);

            // time in history data has format "yyyy-MM-dd" while time in realtime data has format 1367006400
            // here we realtime Data format to "yyyy-MM-dd"
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(calendar.getTime());
            latestRealTime.setTime(date);
            latestRealTime.setIsHist(true);
        }


        if (result.size() > 0) {
            // after 4PM of each trading day, the history data will already include today's data, and we don't need to add today's data to history data
            if (! result.get(result.size() - 1).getTime().equalsIgnoreCase(latestRealTime.getTime())) {
                // append today's latest price info at the end of history data and return.
                result.add(latestRealTime);
            }
        }
        return result;
    }

    public StockRealTimeData getStockRealTimeData() {
        return _realTime;
    }
            
    @Override
    public ArrayList<Indicator> getEventList() {
        return _events;
    }

    @Override
    public void removeEventList() {
        _events.clear();
    }

    @Override
    public void addEvent(Indicator event) {
        _events.add(event);
    }

    @Override
    public void deleteEvent(Indicator event) {
        _events.remove(event);
    }

    @Override
    public double getWeek52Low() {
        if (_week52Low != null) {
            return _week52Low;
        }
        if ( _weekly.getHistData().size() == 0) {
             _weekly.Init();
        }
        double low = Double.MAX_VALUE;
        List<StockTimeFrameData> weeklyData = _weekly.getHistData();
        int end = weeklyData.size();
        int begin;
        if (weeklyData.size() < 52) {
            begin = 0;
        } else {
            begin = end - 52;
        }
        for (int i = begin; i < end; i++) {
            double tmp = weeklyData.get(i).getLow();
            if (tmp < low) {
                low = tmp;
            }
        }
        _week52Low = low;
        return _week52Low;
    }

    @Override
    public double getWeek52High() {
        if (_week52High != null) {
            return _week52High;
        }

        if ( _weekly.getHistData().size() == 0) {
             _weekly.Init();
        }
        
        double high = 0.0;
        List<StockTimeFrameData> weeklyData = _weekly.getHistData();
        int end = weeklyData.size();
        int begin;
        if (weeklyData.size() < 52) {
            begin = 0;
        } else {
            begin = end - 52;
        }
        for (int i = begin; i < end; i++) {
            double tmp = weeklyData.get(i).getLow();
            if (tmp > high) {
                high = tmp;
            }
        }
        _week52High = high;
        return _week52High;
    }
    
    @Override
    public void refresh() {
        refreshStockPrice();
        refreshIndicator();
    }
    
    @Override
    public void addToPlot(StockPlot stockPlot) {
        TimeSeries series = new TimeSeries(_ticker);
        List<StockTimeFrameData> stockPriceData = getStockPriceData(_currFreq);
        
        for(StockTimeFrameData tf : stockPriceData) {
             // tf.getTimeInNumber() return time represented by a second value 
             long tmp = tf.getTimeInNumber() * 1000;    // from second to Millisecond
             Calendar calendar = Calendar.getInstance();
             calendar.setTimeInMillis(tmp);
             Date date = calendar.getTime();         
             series.add(new Day(date) , tf.getAdjustedClose());
        }
        
        SeriesWrapper sw = new SeriesWrapper(series, Color.BLACK);
        stockPlot.addSeries(sw);
    }
    
    private void refreshStockPrice() {        
        // refresh stock price
        this.initialize();
        _week52Low = null;
        _week52High = null;
    }

    private void refreshIndicator() {
        List<StockTimeFrameData> stockPriceData = getStockPriceData(_currFreq);
         
        for (Indicator ind : _events) {
            ind.refresh(stockPriceData);
        }        
    }
    
    public StockEventType isTriggered() {
        StockEventType stType = StockEventType.NONE;
        
        for (Indicator ind : _events) {
            stType = ind.isTriggered();
            if(stType != StockEventType.NONE) {
                return stType;
            }
        } 
        
        return stType;
    }
    
}
