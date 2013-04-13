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

/**
 *
 * @author lyzhang
 */
public class StockHistDataDaily implements StockHistData {

    private String _ticker;
    private ArrayList<StockTimeFrameData> histData;

    public StockHistDataDaily(String ticker) {
        _ticker = ticker;
        histData = new ArrayList<StockTimeFrameData>();
    }

    @Override
    public boolean Init() {
        String begYear = "1900";
        String urlString = "http://ichart.finance.yahoo.com/table.csv?s=" + _ticker + "&c=" + begYear;
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
            //sb = new StringBuilder();

            line = reader.readLine();  // skip the header line
            while ((line = reader.readLine()) != null) {
                StockTimeFrameData newTFData = new StockTimeFrameData();
                String[] splitted = line.split(",");
                if (splitted.length < 7) {
                    System.err.println("ERROR: wrong data:" + _ticker + ":" + line);
                    System.exit(1);

                }
                newTFData.time = splitted[0];
                newTFData.open = Float.parseFloat(splitted[1]);
                newTFData.high = Float.parseFloat(splitted[2]);
                newTFData.low = Float.parseFloat(splitted[3]);
                newTFData.close = Float.parseFloat(splitted[4]);
                newTFData.volume = Integer.parseInt(splitted[5]);
                newTFData.adjustedClose = Float.parseFloat(splitted[6]);

                histData.add(0, newTFData);   // latest last
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

    @Override
    public ArrayList<StockTimeFrameData> getHistData() {
        return histData;
    }

    @Override
    public float getWeek52Low() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getWeek52High() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getFreq() {
        return "Daily";
    }
}
