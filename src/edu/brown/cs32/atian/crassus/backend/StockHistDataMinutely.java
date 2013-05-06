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
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author lyzhang
 */
public class StockHistDataMinutely implements StockHistData {
    private String _ticker;
    private List<StockTimeFrameData> _histData;
    private Date latestCheckTime = null;
   
    
    public StockHistDataMinutely(String ticker) {
        _ticker = ticker;
        _histData = new ArrayList<StockTimeFrameData>();
    }

    public boolean Init() {
        
        Date now = new Date();        
        
        if( latestCheckTime != null && now.getTime() < latestCheckTime.getTime() + 300000) {   // minutely time only update once every 300 seond
            return true;  
        }
        
        _histData.clear();

        String urlString = "http://chartapi.finance.yahoo.com/instrument/1.0/" + _ticker + "/chartdata;type=quote;range=15d/csv/";
        // http://chartapi.finance.yahoo.com/instrument/1.0/msft/chartdata;type=quote;range=15d/csv/        
        HttpURLConnection connection = null;
        URL serverAddress = null;
        BufferedReader reader = null;
        String line = null;

        try {
            latestCheckTime = now;
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
                
                if (splitted.length != 6) {
                    continue;
                }
                if(splitted[5].equals("volume") || splitted[0].equals("labels")) {
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
                    
                _histData.add(newTFData);   // from the earliest to the latest
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
    

    @Override
    public List<StockTimeFrameData> getHistData() {
        return _histData;
    }

    @Override
    public String getFreq() {
        return "Minutely";
    }
    
}
