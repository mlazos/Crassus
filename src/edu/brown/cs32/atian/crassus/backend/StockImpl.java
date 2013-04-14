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
public class StockImpl implements Stock {

    String _ticker;
    StockHistData _daily = null;
    StockHistData _weekly = null;
    StockHistData _monthly = null;
    ArrayList<StockEvent> _events = null;

    public StockImpl(String ticker) {
        _ticker = ticker;
        _daily = new StockHistDataDaily(_ticker);
        _weekly = new StockHistDataWeekly(_ticker);
        _monthly = new StockHistDataMonthly(_ticker);
        _events = new ArrayList<StockEvent>();
    }

    @Override
    public boolean initialize() {   // false mean it fails to get data from data source
        boolean init1 = _daily.Init();
        boolean init2 = _weekly.Init();
        boolean init3 = _monthly.Init();

        if (init1 && init2 && init3) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addEvent(StockEvent event) {
        _events.add(event);
    }

    @Override
    public void deleteEvent(String eventName) {
        StockEvent e = this.getEvent(eventName);
        if (e != null) {
            this._events.remove(e);
        }
    }

    @Override
    public String getTicker() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getCompanyName() {
        return "";
    }

    @Override
    public StockHistData getStockHistData(String freq) {

        if (freq.equalsIgnoreCase("daily")) {
            return _daily;
        } else if (freq.equalsIgnoreCase("weekly")) {
            return _weekly;
        } else if (freq.equalsIgnoreCase("monthly")) {
            return _weekly;
        } else {
            throw new IllegalArgumentException("freq can only be daily, weekly or monthly");
        }
    }

    @Override
    public StockRealTimeData getStockRealTimeData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<StockEvent> getEventList() {
        return _events;
    }

    @Override
    public StockEvent getEvent(String eventName) {
        for (StockEvent e : _events) {
//            if (s.getEventname().equalsIgnoreCase(eventName)) {
//                return s;
//            }
        }
        return null;
    }

    @Override
    public void refresh() {
    }
}
