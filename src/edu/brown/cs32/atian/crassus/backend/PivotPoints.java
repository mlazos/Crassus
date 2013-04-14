package edu.brown.cs32.atian.crassus.backend;

import java.util.ArrayList;
import java.util.List;
import edu.brown.cs32.atian.crassus.gui.StockPlot;

/**
 *
 * @author atian
 * 
 * Pivot points are calculated on past data and are predictive as
 * opposed to lagging. 
 * 
 * This class can take in data of varying time
 * divisions e.g. daily, weekly, monthly, etc. 
 * 
 * Once pivot points and resistance and support lines are calculated,
 * they remain in effect for the duration of the time division; e.g.
 * the pivot point calculated for one day based on the previous day
 * remains active for the duration of that day.
 *
 */
public class PivotPoints implements StockEvent {
	
	private List<IndicatorDatum> pivotPoints;
	private List<IndicatorDatum> support1;
	private List<IndicatorDatum> support2;
	private List<IndicatorDatum> support3;
	private List<IndicatorDatum> resistance1;
	private List<IndicatorDatum> resistance2;
	private List<IndicatorDatum> resistance3;
	private List<StockTimeFrameData> data;

	
	public PivotPoints(List<StockTimeFrameData> data) {
		this.data = data;
		pivotPoints = new ArrayList<IndicatorDatum>();
		support1 = new ArrayList<IndicatorDatum>();
		support2 = new ArrayList<IndicatorDatum>();
		support3 = new ArrayList<IndicatorDatum>();
		resistance1 = new ArrayList<IndicatorDatum>();
		resistance2 = new ArrayList<IndicatorDatum>();
		resistance3 = new ArrayList<IndicatorDatum>();
	}

	@Override
	public void addToPlot(StockPlot stockPlot) {
		// TODO Auto-generated method stub
		
	}
	
	private List<IndicatorDatum> calcStdPivot() {
		
		// first value has no previous
		for (int i = 1; i < data.size(); i++) {
			StockTimeFrameData previous = data.get(i - 1);
			String currTimeLabel = data.get(i).getTime();
			
			double pivot = (previous.getHigh() + previous.getClose() + previous.getLow()) / 3;
			double r1 = 2 * pivot - previous.getLow();
			double s1 = 2 * pivot - previous.getHigh();
			double r2 = pivot + r1 - s1;
			double s2 = pivot - (r1 - s1);
			double s3 = previous.getLow() - 2 * (previous.getHigh() - pivot);
			double r3 = previous.getHigh() + 2 * (pivot - previous.getLow());
			
			pivotPoints.add(new IndicatorDatum(currTimeLabel, pivot));
			support1.add(new IndicatorDatum(currTimeLabel, s1));
			support2.add(new IndicatorDatum(currTimeLabel, s2));
			support3.add(new IndicatorDatum(currTimeLabel, s3));
			
			resistance1.add(new IndicatorDatum(currTimeLabel, r1));
			resistance2.add(new IndicatorDatum(currTimeLabel, r2));
			resistance3.add(new IndicatorDatum(currTimeLabel, r3));
		}
		
		return null;
	}
	

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		calcStdPivot();
	}
}