package edu.brown.cs32.atian.crassus.indicators;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.atian.crassus.backend.StockEventType;
import edu.brown.cs32.atian.crassus.backend.StockTimeFrameData;
import edu.brown.cs32.atian.crassus.gui.StockPlot;

/**
 * @author atian
 * 
 * MACD subtracts longer moving average from shorter moving average
 * to a momentum oscillator (offers trend following and momentum).
 * 
 * MACD fluctuates above and below zero line as moving averages converge,
 * cross zero, and diverge.
 * 
 * As MACD is unbounded, it is not very useful for identifying overbought
 * and oversold levels.
 * 
 * Typical moving average period is 9, 12, 26 day; the shorter moving average period
 * is faster; longer is slower and less reactive to price changes. These three values
 * can be changed.
 *  
 * MACD Line: (shorterPeriod EMA - longerPeriod EMA) 
 * Signal Line: signalPeriod EMA of MACD Line
 * MACD Histogram: MACD Line - Signal Line
 * 
 * Source: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_average_conve
 */
public class MACD implements Indicator {
	
	private List<StockTimeFrameData> data;
	private int signalPeriod;
	private int shortPeriod;
	private int longPeriod;
	private List<IndicatorDatum> MACDLine;
	private List<IndicatorDatum> signalLine;
	private List<IndicatorDatum> MACDHistogram;
	private boolean isActive;
	private boolean isVisible;
	
	public MACD(List<StockTimeFrameData> data, int signalPeriod, int shorterPeriod, int longerPeriod) {
		this.data = data;
		this.signalPeriod = signalPeriod;
		this.shortPeriod = shorterPeriod;
		this.longPeriod = longerPeriod;
		
		MACDLine = new ArrayList<IndicatorDatum>();
		signalLine = new ArrayList<IndicatorDatum>();
		MACDHistogram = new ArrayList<IndicatorDatum>();
		
		refresh(data);
	}
	
	@Override
	public boolean getVisible() {
		return isVisible;
	}
	@Override
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	@Override
	public boolean getActive() {
		return isActive;
	}
	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	@Override
	public String getName() {
		return "MACD";
	}
	
	/**
	 * Calculates the SMA from start index to end index of data
	 * inclusively.
	 * 
	 * @param startIndex	int start index 
	 * @param endIndex		int end index
	 * @return				double simple moving average of given close values
	 * @throws ArrayIndexOutOfBoundsException	if array index of data is out of bounds
	 */
	private double calcSMA(int startIndex, int endIndex) throws ArrayIndexOutOfBoundsException {
		
		double sum = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			sum += data.get(i).getClose();
		}
		
		return sum / (endIndex - startIndex + 1);
	}
	
	/**
	 * Calculates EMA
	 * 
	 * @param prevEMA		double previous EMA value
	 * @param period		double period of EMA 
	 * @param close			double close value
	 * @return
	 */
	private double calcEMA(double prevEMA, double period, double close) {

		double multiplier = 2 / (period + 1);
		return (close - prevEMA) * multiplier + prevEMA;
	}
	
	/**
	 * Returns the max of three values.
	 * 
	 * @param a		int
	 * @param b		int
	 * @param c		int
	 * @return
	 */
	private int max(int a, int b, int c) {
		int currMax = a;
		if (b > currMax) {
			currMax = b;
		}
		
		if (c > currMax) {
			currMax = c;
		}
		return currMax;
	}
	
	/**
	 * Updates the MACD.
	 */
	private void updateMACD() {

		int maxPeriod = max(signalPeriod, shortPeriod, longPeriod);
		int startIndex = (maxPeriod - 1);
		
		double firstSignalEMA = calcSMA(startIndex - signalPeriod + 1, startIndex);
		double firstShortEMA = calcSMA(startIndex - shortPeriod + 1, startIndex);
		double firstLongEMA = calcSMA(startIndex - longPeriod + 1, startIndex);
		
		MACDLine.add(new IndicatorDatum(data.get(startIndex).getTime(), firstShortEMA - firstLongEMA));
		signalLine.add(new IndicatorDatum(data.get(startIndex).getTime(), firstSignalEMA));
		MACDHistogram.add(new IndicatorDatum(data.get(startIndex).getTime(), firstShortEMA - firstLongEMA - firstSignalEMA));
		
		double prevSigEMA = firstSignalEMA;
		double prevShortEMA = firstShortEMA;
		double prevLongEMA = firstLongEMA;
		for (int i = maxPeriod; i < data.size(); i++) {
			double close = data.get(i).getClose();
			double currSigEMA = calcEMA(prevSigEMA, signalPeriod, close);
			double currShortEMA = calcEMA(prevShortEMA, shortPeriod, close);
			double currLongEMA = calcEMA(prevLongEMA, longPeriod, close);
			
			double MACDPoint = currShortEMA - currLongEMA;
			double MACDHistPoint = MACDPoint - currSigEMA;
			
			String currTimeLabel = data.get(i).getTime();
			MACDLine.add(new IndicatorDatum(currTimeLabel, MACDPoint));
			signalLine.add(new IndicatorDatum(currTimeLabel, currSigEMA));
			MACDHistogram.add(new IndicatorDatum(currTimeLabel, MACDHistPoint));
			
			prevSigEMA = currSigEMA;		// save prev EMA values for next EMA calculation
			prevShortEMA = currShortEMA;
			prevLongEMA =  currLongEMA;
		}
	}

	@Override
	public void addToPlot(StockPlot stockPlot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		updateMACD();
	}

	@Override
	public StockEventType isTriggered() {
		// TODO Auto-generated method stub
		return null;
	}
}