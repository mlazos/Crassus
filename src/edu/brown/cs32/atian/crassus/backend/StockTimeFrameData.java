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
	 
     private String time;   
     private double open;
     private double close;
     private double high;
     private double low;    
     private int volume;
     private double adjustedClose;
	 
	 public StockTimeFrameData(String time, double open, double high, double low,
			 double close, int volume, double adjustedClose) {
		 
		 this.open = open;
		 this.close = close;
		 this.high = high;
		 this.low = low;
		 this.volume = volume;
		 this.adjustedClose = adjustedClose;
	 }

	public String getTime() {
		return time;
	}
	
	public double getOpen() {
		return open;
	}
	 
	public double getHigh() {
		return high;
	}
	
	public double getLow() {
		return low;
	}
	
	public double getClose() {
		return close;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public double getAdjustedClose() {
		return adjustedClose;
	}
}