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
public class PivotPoints implements Indicator {
	
	private List<IndicatorDatum> pivotPoints;
	private List<IndicatorDatum> support1;
	private List<IndicatorDatum> support2;
	private List<IndicatorDatum> support3;
	private List<IndicatorDatum> resistance1;
	private List<IndicatorDatum> resistance2;
	private List<IndicatorDatum> resistance3;
	private List<StockTimeFrameData> data;
	private int pivotType;

	public PivotPoints(List<StockTimeFrameData> data, String pivotOption) {
		this.data = data;
		pivotPoints = new ArrayList<IndicatorDatum>();
		support1 = new ArrayList<IndicatorDatum>();
		support2 = new ArrayList<IndicatorDatum>();
		support3 = new ArrayList<IndicatorDatum>();
		resistance1 = new ArrayList<IndicatorDatum>();
		resistance2 = new ArrayList<IndicatorDatum>();
		resistance3 = new ArrayList<IndicatorDatum>();
		if(pivotOption.equals("standard")) {
                    pivotType = 0;
                } else if(pivotOption.equals("fibonacci")) {
                    pivotType = 1;
                } else if(pivotOption.equals("demark")) {
                    pivotType = 2;
                } else  {
                    pivotType = 0;
                }
// my compile don't like switch for string
//		switch (pivotOption) {
//		case "standard": 
//			pivotType = 0;
//			break;
//		case "fibonacci":
//			pivotType = 1;
//			break;
//		case "demark":
//			pivotType = 2;
//			break;
//		default:
//			pivotType = 0;
//			break;
//		}
		
		refresh(data);
	}

	@Override
	public void addToPlot(StockPlot stockPlot) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Updates the standard pivot points and resistance and support lines. 
	 */
	private void updatePivot() {
		
		// first value has no previous
		for (int i = 1; i < data.size(); i++) {
			StockTimeFrameData previous = data.get(i - 1);
			String currTimeLabel = data.get(i).getTime();
			
			double pivot = (previous.getHigh() + previous.getClose() + previous.getLow()) / 3;
			double high = previous.getHigh();
			double low = previous.getLow();
			
			
			double s1, s2, s3, r1, r2, r3;
			if (pivotType == 1) {								// fibonacci
				
				s1 = pivot - (0.832 * (high - low));
				s2 = pivot - (0.618 * (high - low));
				s3 = pivot - (high - low);
				r1 = pivot - (0.382 * (high - low));
				r2 = pivot + (0.618 * (high - low));
				r3 = pivot + (high - low);
			
			} else {											// standard pivot
			
				r1 = 2 * pivot - low;
				s1 = 2 * pivot - high;
				r2 = pivot + r1 - s1;
				s2 = pivot - (r1 - s1);
				s3 = low - 2 * (high - pivot);
				r3 = high + 2 * (pivot - low);
			
			}
			
			pivotPoints.add(new IndicatorDatum(currTimeLabel, pivot));
			support1.add(new IndicatorDatum(currTimeLabel, s1));
			support2.add(new IndicatorDatum(currTimeLabel, s2));
			support3.add(new IndicatorDatum(currTimeLabel, s3));
			
			resistance1.add(new IndicatorDatum(currTimeLabel, r1));
			resistance2.add(new IndicatorDatum(currTimeLabel, r2));
			resistance3.add(new IndicatorDatum(currTimeLabel, r3));
		}
	}
	
	/**
	 * Updates demark pivot points; differ from above in that demark only
	 * has one resistance and one support line from the pivot.
	 */
	private void updateDemark() {
		
		// first value has no previous
		for (int i = 1; i < data.size(); i++) {
			StockTimeFrameData previous = data.get(i - 1);
			String currTimeLabel = data.get(i).getTime();
			
			double pivot = (previous.getHigh() + previous.getClose() + previous.getLow()) / 3;
			double high = previous.getHigh();
			double low = previous.getLow();
			
			double close = previous.getClose();
			double open = previous.getOpen();
			double x;
			if (close < open) {
				x = high + (2 * low) + close;
			} else if (close > open) {
				x = (2 * high) + low + close;
			} else {
				x = high + low + (2 * close);
			}
			
			pivot = x / 4;
			double s1 = x/2 - high;
			double r1 = x/2 - low;
			
			pivotPoints.add(new IndicatorDatum(currTimeLabel, pivot));
			support1.add(new IndicatorDatum(currTimeLabel, s1));
			resistance1.add(new IndicatorDatum(currTimeLabel, r1));
		}
	}
	

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		
		if (pivotType == 2) updateDemark();
		else updatePivot();
	}
}