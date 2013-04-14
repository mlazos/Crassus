package edu.brown.cs32.atian.crassus.backend;

/**
 * @author atian
 * 
 * A wrapper for indicator data points used for storing points calculated
 * from financial indicator formulae.
 */
public class IndicatorDatum {
	
	private String timeLabel;
	private double value;
	
	public IndicatorDatum(String timeLabel, double value) {
		this.timeLabel = timeLabel;
		this.value = value;
	}
	
	public String getTimeLabel() {
		return timeLabel;
	}
	
	public double getValue() {
		return value;
	}
}
