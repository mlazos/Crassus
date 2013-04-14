package edu.brown.cs32.atian.crassus.backend;

import java.util.ArrayList;
import java.util.List;

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
	private int shorterPeriod;
	private int longerPeriod;
	private List<IndicatorDatum> MACDLine;
	private List<IndicatorDatum> signalLine;
	private List<IndicatorDatum> MACDHistogram;
	private double signalEMAmultiplier;
	private double shorterEMAmultiplier;
	private double longerEMAmultiplier;
	
	public MACD(List<StockTimeFrameData> data, int signalPeriod, int shorterPeriod, int longerPeriod) {
		this.data = data;
		this.signalPeriod = signalPeriod;
		this.shorterPeriod = shorterPeriod;
		this.longerPeriod = longerPeriod;
		
		MACDLine = new ArrayList<IndicatorDatum>();
		signalLine = new ArrayList<IndicatorDatum>();
		MACDHistogram = new ArrayList<IndicatorDatum>();
		
		this.signalEMAmultiplier = 2 / (signalPeriod + 1);
		this.shorterEMAmultiplier = 2 / (shorterPeriod + 1);
		this.longerEMAmultiplier = 2 / (longerPeriod + 1);
		
		refresh(data);
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
	 * Updates the MACD.
	 */
	private void updateMACD() {

		double firstSignalEMA = calcSMA(0, signalPeriod - 1);
		/*for (int i = 0; i + period - 1 < data.size(); i++) {
			
		}*/

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
}