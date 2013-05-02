package edu.brown.cs32.atian.crassus.indicators;

import java.util.Date;
import java.util.List;

import edu.brown.cs32.atian.crassus.backend.StockEventType;
import edu.brown.cs32.atian.crassus.backend.StockTimeFrameData;
import edu.brown.cs32.atian.crassus.gui.StockPlot;

/**
 * @author atian
 *
 * A momentum indicator which shows the location of the close relative
 * to the high-low range over a set number of periods.
 * 
 * It follows speed or momentum of price.
 * 
 * It is useful for identifying overbought and oversold levels because
 * it is range bound.
 * 
 * Typical period is 14 periods, which can be days, weeks, months, or
 * an intraday time frame.
 * 
 * %K = (Current Close - Lowest Low)/(Highest High - Lowest Low) * 100
 * %D = 3-day SMA of %K (signal or trigger line)
 * 
 * Lowest low and highest high is for the look-back period
 *  
 * source: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:stochastic_oscillator 
 */
public class StochasticOscillator implements Indicator {

	private List<StockTimeFrameData> data;
	private int period;
	private int SMAPeriod;
	private List<IndicatorDatum> stocOscillator;
	private List<IndicatorDatum> signalLine;
    private boolean isActive;
    private boolean isVisible;
    private Date startTime;
	
	public StochasticOscillator(List<StockTimeFrameData> data, int period, Date startTime) {
		if (period == 0) throw new IllegalArgumentException("ERROR: " + period + " is not a valid period");
		this.startTime = startTime;
		this.data = data;
		this.period = period;
		this.SMAPeriod = 3;
		
		refresh(data, startTime);
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
	 * Returns an array of high and low values from start index to end index
	 * of data List.
	 * 
	 * @param startIndex		int start index
	 * @param endIndex			int end index
	 * @return					double array; [0] - high, [1] - low
	 */
	private double[] getHighLow(int startIndex, int endIndex) {
		
		double currHigh = Double.MIN_VALUE;
		double currLow = Double.MAX_VALUE;
		for (int i = startIndex; i <= endIndex; i++) {
			if (data.get(i).getHigh() > currHigh) {
				currHigh = data.get(i).getHigh();
			}
			
			if (data.get(i).getLow() < currLow) {
				currLow = data.get(i).getLow();
			}
		}
		
		double[] highLow = new double[2];
		highLow[0] = currHigh;
		highLow[1] = currLow;
		
		return highLow;
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
	
	private void updateStochasticOscillator() {
		
		for (int i = (period - 1); i < data.size(); i++) {
			
			double[] highLow = getHighLow(i - period - 1, i);
			double high = highLow[0];
			double low = highLow[1];
			double D = calcSMA(i - SMAPeriod - 1, i);
			
			double K = (data.get(i).getClose() - low) / (high - low) * 100;
			
			signalLine.add(new IndicatorDatum(data.get(i).getTime(), data.get(i).getTimeInNumber(), D));
			stocOscillator.add(new IndicatorDatum(data.get(i).getTime(), data.get(i).getTimeInNumber(), K));
		}
	}
	
	@Override
	public String getName() {
		return "Stochastic Oscillator";
	}
	
	@Override
	public void addToPlot(StockPlot stockPlot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(List<StockTimeFrameData> data, Date startTime) {
		this.data = data;
		this.startTime = startTime;
		updateStochasticOscillator();
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