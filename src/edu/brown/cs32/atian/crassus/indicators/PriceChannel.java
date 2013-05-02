package edu.brown.cs32.atian.crassus.indicators;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.atian.crassus.backend.StockEventType;
import edu.brown.cs32.atian.crassus.backend.StockTimeFrameData;
import edu.brown.cs32.atian.crassus.gui.StockPlot;

/**
 * @author atian
 * 
 * For N-period price channel, the upper channel is N-period
 * high (highest high in period) and lower channel is N-period low (lowest
 * low in period). The centre line is the midpoint of the two.
 * 
 * Price channels can be used to identify upward thrusts that signal
 * start of uptrend or downtrend.
 * 
 * Price channels are based on prior data and do not use current period.
 * 
 * Typical is 20-day lookback period. Lowering this value produces
 * tighter channel lines. Does not include current day in the lookback.
 * 
 * Upper Channel Line: lockBackPeriod high
 * Lower Channel Line: lockBackPeriod low
 * Centerline: (lockBackPeriod high + lockBackPeriod low)/2 
 * 
 * source: http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:price_channels
 *
 */
public class PriceChannel implements Indicator {

	private List<IndicatorDatum> upperChannel;
	private List<IndicatorDatum> lowerChannel;
	private List<IndicatorDatum> centreLine;
	private List<StockTimeFrameData> data;
	private int lookBackPeriod;
	private boolean isActive;
	private boolean isVisible;
	
	public PriceChannel(List<StockTimeFrameData> data, int lookBackPeriod) {
		this.data = data;
		this.lookBackPeriod = lookBackPeriod;
		upperChannel = new ArrayList<IndicatorDatum>();
		lowerChannel = new ArrayList<IndicatorDatum>();
		centreLine = new ArrayList<IndicatorDatum>();
		refresh(data);
	}
	
	List<IndicatorDatum> getUpperChannel() {
		return upperChannel;
	}
	
	List<IndicatorDatum> getLowerChannel() {
		return lowerChannel;
	}
	
	List<IndicatorDatum> getCentreLine() {
		return centreLine;
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
		return "Price Channel";
	}
	
	/**
	 * Updates the price channel bands and centre line.
	 */
	private void updatePriceChannel() {
		
		for (int i = 0; i + lookBackPeriod < data.size(); i++) {
			double[] periodHighLow = getHighLow(i, i + lookBackPeriod - 1);
			double periodHigh = periodHighLow[0];
			double periodLow = periodHighLow[1];
			double centreVal = (periodHigh + periodLow) / 2;
			
			upperChannel.add(new IndicatorDatum(data.get(i + lookBackPeriod).getTime(), periodHigh));
			lowerChannel.add(new IndicatorDatum(data.get(i + lookBackPeriod).getTime(), periodLow));
			centreLine.add(new IndicatorDatum(data.get(i + lookBackPeriod).getTime(), centreVal));
		}
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
	
	@Override
	public void addToPlot(StockPlot stockPlot, int startIndex, int endIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		updatePriceChannel();
	}

	@Override
	public StockEventType isTriggered() {
		// TODO Auto-generated method stub
		return null;
	}
}