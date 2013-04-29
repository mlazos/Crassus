package edu.brown.cs32.atian.crassus.backend;

import java.util.*;
import org.jfree.data.xy.*;

import edu.brown.cs32.atian.crassus.gui.StockPlot;

/**
 * @author atian
 * 
 * Bollinger Bands can be used to measure price action volatility.
 * 
 * Typical value of period is 20 days with bands 2 * standard deviations
 * above and below the moving average.
 * 
 * Bandwidth is the number of standard deviations above and below SMA; 
 * typical value is 2.
 *
 * Bands are calculated using close prices.
 */
public class BollingerBands implements Indicator {

	private List<IndicatorDatum> middleBand;
	private List<IndicatorDatum> lowerBand;
	private List<IndicatorDatum> upperBand;
	private int period;
	private List<StockTimeFrameData> data;
	private int bandWidth;
	private boolean isActive;
	private boolean isVisible;
	
	public BollingerBands(List<StockTimeFrameData> data, int period, int bandWidth) {
		this.data = data;
		this.period = period;
		this.bandWidth = bandWidth;
		middleBand = new ArrayList<IndicatorDatum>();
		upperBand = new ArrayList<IndicatorDatum>();
		lowerBand = new ArrayList<IndicatorDatum>();
		refresh(data);
	}
	
	/**
	 * Creates new Bollinger Band stock event.
	 * 
	 * @param period	int period of SMA
	 * @param k			int multiplier of standard deviation; determines
	 * 					width of the bands.
	 */
	public BollingerBands(int period, int bandWidth) {
		this.period = period;
		this.lowerBand = new ArrayList<IndicatorDatum>();
		this.upperBand = new ArrayList<IndicatorDatum>();
		this.bandWidth = bandWidth;
		updateBollingerBands();
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
	
	/**
	 * Calculates the standard deviation given start and end index of data inclusive.
	 * 
	 * Uses formula stdDev = sqrt(1/N * sum((x(i) - movingAvg))^2) for i = 1:N 
	 * 
	 * @param startIndex						int start index
	 * @param endIndex							int end index
	 * @param movingAvg							double moving average
	 * @return									double standard deviation of given close values
	 * @throws ArrayIndexOutOfBoundsException	if array index of data is out of bounds
	 */
	double calcStdDev(int startIndex, int endIndex, double movingAvg) 
			throws ArrayIndexOutOfBoundsException {
		
		double sum = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			double diff = data.get(i).getClose() - movingAvg;
			sum +=  (diff * diff);
		}
		
		return Math.sqrt(sum / (endIndex - startIndex + 1));
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
	double calcSMA(int startIndex, int endIndex) throws ArrayIndexOutOfBoundsException {
		
		double sum = 0;
		for (int i = startIndex; i <= endIndex; i++) {
			sum += data.get(i).getClose();
		}
		
		return sum / (endIndex - startIndex + 1);
	}

	@Override
	public void addToPlot(StockPlot stockPlot) {
		//XYSeries xyseries = new XYSeries();
		
	}
	
	/**
	 * Updates the Bollinger Bands.
	 */
	private void updateBollingerBands() {
		for (int i = 0; (i + period - 1) < data.size(); i++) {
			double avg = calcSMA(i, i + period - 1);
			double stdDev = calcStdDev(i, i + period - 1, avg);
			
			/*if (i == 0) {
				for (int j = 0; j < period - 1; j++) {
					middleBand.add(new IndicatorDatum(data.get(j).getTime(), avg));		// add (period-1) times
				}
			}*/

			middleBand.add(new IndicatorDatum(data.get(i + period - 1).getTime(), avg));
			upperBand.add(new IndicatorDatum(data.get(i + period - 1).getTime(), avg + (bandWidth * stdDev)));
			lowerBand.add(new IndicatorDatum(data.get(i + period - 1).getTime(), avg - (bandWidth * stdDev)));
		}
	}
	
	
	List<IndicatorDatum> getUpperBand() {
		return upperBand;
	}
	
	List<IndicatorDatum> getMiddleBand() {
		return middleBand;
	}
	
	List<IndicatorDatum> getLowerBand() {
		return lowerBand;
	}

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		updateBollingerBands();
	}
}