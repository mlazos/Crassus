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
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author lyzhang
 */
public class StockImpl implements Stock {

    String _ticker;
    String _companyName = null;
    StockHistData _daily = null;
    StockHistData _weekly = null;
    StockHistData _monthly = null;
    StockRealTimeData _realTime = null;
    ArrayList<Indicator> _events = null;
    Double _week52Low = null;
    Double _week52High = null;
    
    String _currFreq = "daily";    // be default se use daily
    
    public StockImpl(String ticker) {
        _ticker = ticker;
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
        boolean init1 = _daily.Init();
        boolean init2 = _weekly.Init();
        boolean init3 = _monthly.Init();
        boolean init4 = _realTime.Init();

        if (init1 && init2 && init3 && init4) {
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
    public void setCurrFreq(String currFreq) {
        _currFreq = currFreq;
        refreshIndicator();
    }
    
    
    @Override
    public List<StockTimeFrameData> getStockPriceData(String freq) {  // freq = "minutely", or "daily" or "monthly" or "weekly"

        List<StockTimeFrameData> realTime = this._realTime.getRealTimeData();
        List<StockTimeFrameData> result = null;
        if (freq.equals("minutely")) {
            return realTime;
        }
        
        if (freq.equals("daily")) {
            result = _daily.getHistData();
        } else if (freq.equals("weekly")) {
            result = _weekly.getHistData();
        } else if (freq.equals("monthly")) {
            result = _monthly.getHistData();
        }
        
        StockTimeFrameData latestRealTime = new StockTimeFrameData(realTime.get(realTime.size() - 1));
        if (realTime.size() >= 1) {
            //latestRealTime = realTime.get(realTime.size() - 1);
            long tmp = (Long.parseLong(latestRealTime.getTime()));
            tmp = tmp * 1000;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(tmp);

            //String timeStamp = calendar.getTime().toString();
            //DateFormat df = DateFormat.getDateInstance();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //Date latestDate =  df.parse(latestRealTime.getTime());
            String date = df.format(calendar.getTime());
            latestRealTime.setTime(date);
            latestRealTime.setIsHist(true);
        }


        if (result.size() > 0) {
            if (! result.get(result.size() - 1).getTime().equalsIgnoreCase(latestRealTime.getTime())) {
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
        if (_weekly == null || _weekly.getHistData().size() == 0) {
            return 0.0;
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
        if (_weekly == null || _weekly.getHistData().size() == 0) {
            return 0.0;
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
        XYSeries series = new XYSeries(_ticker);
        List<StockTimeFrameData> stockPriceData = getStockPriceData(_currFreq);
        
        for(StockTimeFrameData tf : stockPriceData) {
            series.add(tf.getTimeInNumber(), tf.getAdjustedClose());
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
    
    
}
