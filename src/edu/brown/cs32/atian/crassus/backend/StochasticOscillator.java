package edu.brown.cs32.atian.crassus.backend;

import java.util.List;

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
 * Typical period is 14 periods, which can be days, weeks, moneths, or
 * an intraday time frame.
 */
public class StochasticOscillator implements Indicator {

	private List<StockTimeFrameData> data;
	private int period;
	
	
	public StochasticOscillator(List<StockTimeFrameData> data) {
		this.data = data;
		this.period = period;
		
		refresh(data);
	}
	
	@Override
	public void addToPlot(StockPlot stockPlot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		// TODO Auto-generated method stub
		
	}

}
