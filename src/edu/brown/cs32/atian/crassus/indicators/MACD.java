package edu.brown.cs32.atian.crassus.indicators;

import java.util.ArrayList;
import java.util.Date;
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
	private boolean isActive;
	private boolean isVisible;
	private Date startTime;
	private Date endTime;
	
	public MACD(List<StockTimeFrameData> data, int signalPeriod, int shorterPeriod, int longerPeriod,
			Date startTime, Date endTime) throws IllegalArgumentException {
		if (shorterPeriod > longerPeriod) throw new IllegalArgumentException("ERROR: shorter period must not be greater than longer period");
		
		this.data = data;
		this.signalPeriod = signalPeriod;
		this.shortPeriod = shorterPeriod;
		this.longPeriod = longerPeriod;
		this.startTime = startTime;
		this.endTime = endTime;
		
		MACDLine = new ArrayList<IndicatorDatum>();
		signalLine = new ArrayList<IndicatorDatum>();
		
		refresh(data, startTime, endTime);
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
	
	List<IndicatorDatum> getSignalLine() {
		return signalLine;
	}
	
	List<IndicatorDatum> getMACDLine() {
		return MACDLine;
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
			sum += data.get(i).getAdjustedClose();
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
	private int min(int a, int b, int c) {
		int currMin = a;
		if (b < currMin) {
			currMin = b;
		}
		
		if (c < currMin) {
			currMin = c;
		}
		return currMin;
	}
	
	/**
	 * Updates the MACD.
	 */
	/*private void updateMACD() {

		int maxPeriod = max(signalPeriod, shortPeriod, longPeriod);
		int startIndex = (maxPeriod - 1);
		
		double firstSignalEMA = calcSMA(startIndex - signalPeriod + 1, startIndex);
		double firstShortEMA = calcSMA(startIndex - shortPeriod + 1, startIndex);
		double firstLongEMA = calcSMA(startIndex - longPeriod + 1, startIndex);
		
		MACDLine.add(new IndicatorDatum(data.get(startIndex).getTime(), data.get(startIndex).getTimeInNumber(), firstShortEMA - firstLongEMA));
		signalLine.add(new IndicatorDatum(data.get(startIndex).getTime(), data.get(startIndex).getTimeInNumber(), firstSignalEMA));
		MACDHistogram.add(new IndicatorDatum(data.get(startIndex).getTime(), data.get(startIndex).getTimeInNumber(), firstShortEMA - firstLongEMA - firstSignalEMA));
		
		double prevSigEMA = firstSignalEMA;
		double prevShortEMA = firstShortEMA;
		double prevLongEMA = firstLongEMA;
		for (int i = maxPeriod; i < data.size(); i++) {
			double close = data.get(i).getAdjustedClose();
			double currSigEMA = calcEMA(prevSigEMA, signalPeriod, close);
			double currShortEMA = calcEMA(prevShortEMA, shortPeriod, close);
			double currLongEMA = calcEMA(prevLongEMA, longPeriod, close);
			
			System.out.println(String.format("currshortEMA=[%s], currLongEMA=[%s]", currShortEMA,currLongEMA));
			double MACDPoint = currShortEMA - currLongEMA;
			double MACDHistPoint = MACDPoint - currSigEMA;
			
			String currTimeLabel = data.get(i).getTime();
			long currTime = data.get(i).getTimeInNumber();
			MACDLine.add(new IndicatorDatum(currTimeLabel, currTime, MACDPoint));
			signalLine.add(new IndicatorDatum(currTimeLabel, currTime, currSigEMA));
			MACDHistogram.add(new IndicatorDatum(currTimeLabel, currTime, MACDHistPoint));
			
			prevSigEMA = currSigEMA;		// save prev EMA values for next EMA calculation
			prevShortEMA = currShortEMA;
			prevLongEMA =  currLongEMA;
		}
	}*/
	
	private void updateMACD() {

		int startIndex = (min(signalPeriod, shortPeriod, longPeriod) - 1);
		double prevSigEMA = 0;
		double prevShortEMA = 0;
		double currShortEMA = 0;
		double prevLongEMA = 0;
		double currLongEMA;
		for (int i = startIndex; i < data.size(); i++) {
			

			if (i >= signalPeriod - 1) {
				if (i == signalPeriod - 1) {
					double firstSignalEMA = calcSMA(i - signalPeriod + 1, i);
					signalLine.add(new IndicatorDatum(data.get(startIndex).getTime(), data.get(startIndex).getTimeInNumber(), firstSignalEMA));
					prevSigEMA = firstSignalEMA;
				} else {
					double currSigEMA = calcEMA(prevSigEMA, signalPeriod, data.get(i).getAdjustedClose());
					signalLine.add(new IndicatorDatum(data.get(startIndex).getTime(), data.get(startIndex).getTimeInNumber(), currSigEMA));
					prevSigEMA = currSigEMA;		// save prev EMA values for next EMA calculation
				}
			}
			
			if (i >= shortPeriod - 1) {
				if (i == shortPeriod - 1) {
					double firstShortEMA = calcSMA(i - shortPeriod + 1, i);
					prevShortEMA = firstShortEMA;
					currShortEMA = firstShortEMA;
				} else {
					currShortEMA = calcEMA(prevShortEMA, shortPeriod, data.get(i).getAdjustedClose());
					prevShortEMA = currShortEMA;
				}
			}
			
			if (i >= longPeriod - 1) {
				if (i == longPeriod - 1) {
					double firstLongEMA = calcSMA(i - longPeriod + 1, i);
					prevLongEMA = firstLongEMA;
					currLongEMA = firstLongEMA;
					MACDLine.add(new IndicatorDatum(data.get(i).getTime(), data.get(i).getTimeInNumber(), currShortEMA - currLongEMA));
				} else {
					currLongEMA = calcEMA(prevLongEMA, longPeriod, data.get(i).getAdjustedClose());
					MACDLine.add(new IndicatorDatum(data.get(i).getTime(), data.get(i).getTimeInNumber(), currShortEMA - currLongEMA));
					prevLongEMA = currLongEMA;
				}
			}

			
			//System.out.println(String.format("currshortEMA=[%s], currLongEMA=[%s]", currShortEMA,currLongEMA));

		}
	}
	
	

	@Override
	public void addToPlot(StockPlot stockPlot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(List<StockTimeFrameData> data, Date startTime, Date endTime) {
		this.data = data;
		this.endTime = endTime;
		this.startTime = startTime;
		updateMACD();
	}

	@Override
	public StockEventType isTriggered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTestResults() {
		// TODO Auto-generated method stub
		return 0;
	}
}