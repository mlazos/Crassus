/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author lyzhang
 */
public class StockRealTimeDataImpl implements StockRealTimeData {

    private String _ticker;
    private ArrayList<StockTimeFrameData> _realtimeData;
    private Double _high = null;
    private Double _low = null;

    public StockRealTimeDataImpl(String ticker) {
        _ticker = ticker;
        _realtimeData = new ArrayList<StockTimeFrameData>();
    }

    public boolean Init() {
        _realtimeData.clear();

        String urlString = "http://chartapi.finance.yahoo.com/instrument/1.0/" + _ticker + "/chartdata;type=quote;range=1d/csv/";
        // http://chartapi.finance.yahoo.com/instrument/1.0/msft/chartdata;type=quote;range=1d/csv/        
        HttpURLConnection connection = null;
        URL serverAddress = null;
        BufferedReader reader = null;
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
            //sb = new StringBuilder();
            
            String[] sep_list = {",", ":"};
            StringBuilder regexp = new StringBuilder("");
            regexp.append("[");
            for (String s : sep_list) {
                regexp.append(Pattern.quote(s));;
            }
            regexp.append("]");
        
            line = reader.readLine();  // skip the header line
            while ((line = reader.readLine()) != null) {

                String[] splitted = line.split(regexp.toString());
                if (splitted.length == 3 && splitted[0].equals("high")) {
                    _high = Double.parseDouble(splitted[2]);
                    continue;
                }      
                if (splitted.length == 3 && splitted[0].equals("low")) {
                    _low = Double.parseDouble(splitted[1]);
                    continue;
                }                 
                if (splitted.length != 6) {
                    continue;
                }
                if(splitted[5].equals("volume")) {
                    continue;
                }
//                long tmp = (Long.parseLong(splitted[0]));
//                tmp = tmp*1000;
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(tmp);
//                String timeStamp = calendar.getTime().toString();
                
                StockTimeFrameData newTFData = new StockTimeFrameData(splitted[0], //Time
                        Double.parseDouble(splitted[4]),   //Open				
                        Double.parseDouble(splitted[2]),   //High				
                        Double.parseDouble(splitted[3]),   //Low
                        Double.parseDouble(splitted[1]),   //Close	
                        Integer.parseInt(splitted[5]),     //Volume
                        Double.parseDouble(splitted[1]),  // realtime data from yahoo has not adjusted close, we set it equal to Close
                        false);  
                    
                _realtimeData.add(newTFData);   // from the earliest to the latest
            }

            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<StockTimeFrameData> getRealTimeData() {
        return _realtimeData;
    }

    public double getPertChgFromOpen() {
        if(getOpenPrice() == 0.0){
            return 0;
        }
        else {
            double result = (getCurrPrice() - getOpenPrice() )/getOpenPrice();
            return result;
        }
    }

    public double getOpenPrice() {               

        StockTimeFrameData firstData = _realtimeData.get(0);
        return firstData.getOpen();             
    }

    public double getCurrPrice() {
        StockTimeFrameData firstData = _realtimeData.get(_realtimeData.size() - 1);
        return firstData.getClose();   
    }

    public double getTodayLow() {
        //throw new UnsupportedOperationException("Not supported yet.");
        if(_low != null) {
            return _low;
        } else {
            if(_realtimeData.size() == 0) {
                _low = 0.0;
                return 0.0;
            }
            double low = Double.MAX_VALUE;
            for(StockTimeFrameData tfData : _realtimeData ) {
                if(low > tfData.getLow()) {
                    low = tfData.getLow();
                }
            }
            _low = low;
            return low;
        }
    }

    public double getTodayHigh() {
        if(_high != null) {
            return _high;
        } else {
            if(_realtimeData.size() == 0) {
                _high = 0.0;
                return 0.0;
            }
            double high = 0.0;
            for(StockTimeFrameData tfData : _realtimeData ) {
                if(high < tfData.getHigh()) {
                    high = tfData.getHigh();
                }
            }
            _high = high;
            return high;
        }
    }

    public void refresh() {
        this.Init();
    }
    
}
