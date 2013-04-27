/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

/**
 *
 * @author lyzhang
 */
class StockTimeFrameData {

    private String _time;
    private double _open;
    private double _high;
    private double _low;
    private double _close;
    private int _volume;
    private double _adjustedClose;

    public StockTimeFrameData(String time, double open, double high, double low,
            double close, int volume, double adjustedClose) {
        _time = time;
        _open = open;
        _high = high;
        _low = low;
        _close = close;
        _volume = volume;
        _adjustedClose = adjustedClose;
    }

    public String getTime() {
        return _time;
    }

    public double getOpen() {
        return _open;
    }

    public double getHigh() {
        return _high;
    }

    public double getLow() {
        return _low;
    }

    public double getClose() {
        return _close;
    }

    public int getVolume() {
        return _volume;
    }

    public double getAdjustedClose() {
        return _adjustedClose;
    }
}