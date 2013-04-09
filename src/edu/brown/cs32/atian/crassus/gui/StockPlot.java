/*
 * 
 */
package edu.brown.cs32.atian.crassus.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jfree.chart.JFreeChart;

/**
 * 
 * Container for two {@link JFreeChart} objects, one of which is the main chart (the prices of the stock
 * at different times) and the other of which is the relative strength chart, which contains relative strengths
 * 
 * @author Matthew
 *
 */
public interface StockPlot {

	/*
	 * This function allows retrieval of the primary chart, so that it can be changed or drawn to the screen
	 * @return the primary chart, which contains the prices of the stock over time
	 */
	//public JFreeChart getPrimaryChart();
	
	/*
	 * This function allows retrieval of the relative strength chart, so that it can be changed or drawn to the screen.
	 * IMPORTANT, in many cases, 
	 * @return the relative strength chart, which contains the relative strength of the stock over time
	 */
	//public JFreeChart getRelativeStrengthChart();
	
	public void addDatasetToPrimaryChart(Color color );
	
	/**
	 * @param state indicates whether the relative strength data should be drawn on the same chart as the stock price data
	 */
	public void setRsOnSameChart(boolean state);
	
	/**
	 * turns the Relative Strength chart on
	 */
	public void turnRsOn() throws CantTurnRsOnAfterChartsRetreivedException;
	
	public boolean isRsOn();
	
	public BufferedImage getPrimaryBufferedImage(int width, int height);
	
	public BufferedImage getRsBufferedImage(int width, int height);
	
}
