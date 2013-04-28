/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lyzhang
 */
public class StockHistDataWeekly implements StockHistData {

    private String _ticker;
    private List<StockTimeFrameData> _histData;

    public StockHistDataWeekly(String ticker) {
        _ticker = ticker;
        _histData = new ArrayList<StockTimeFrameData>();
    }

    public boolean Init() {
        _histData.clear();
        String begYear = "1900";
        String urlString = "http://ichart.finance.yahoo.com/table.csv?s=" + _ticker + "&c=" + begYear + "&g=w&ignore=.csv";

        HttpURLConnection connection = null;
        URL serverAddress = null;
        //OutputStreamWriter wr = null;
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
            
            line = reader.readLine(); // skip the header line
            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split(",");
                if (splitted.length < 7) {
                    System.err.println("ERROR: wrong data:" + _ticker + ":" + line);
                    System.exit(1);
                }
                
           		StockTimeFrameData newTFData = new StockTimeFrameData(splitted[0],	// time
      					 Double.parseDouble(splitted[1]),							// open
      					 Double.parseDouble(splitted[2]), 							// high
      					 Double.parseDouble(splitted[3]), 							// low
      					 Double.parseDouble(splitted[4]),							// close
      					 Integer.parseInt(splitted[5]), 							// volume
      					 Double.parseDouble(splitted[6]));							// adjusted close

                _histData.add(0, newTFData);   // latest last
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            //System.exit(1);
            return false;
        } catch (ProtocolException e) {
            e.printStackTrace();
            //System.exit(1);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            //System.exit(1);
            return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //System.exit(1);
            return false;
        }
    }


    public List<StockTimeFrameData> getHistData() {
        return _histData;
    }
    
    public String getFreq() {
        return "Weekly";
    }
}