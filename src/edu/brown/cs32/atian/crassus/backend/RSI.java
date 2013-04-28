package edu.brown.cs32.atian.crassus.backend;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.atian.crassus.gui.StockPlot;

public class RSI implements Indicator {

	private List<StockTimeFrameData> data;
	private List<IndicatorDatum> RSIPoints;
	private int period;
	
	public RSI(List<StockTimeFrameData> data, int period) {
		this.data = data;
		this.period = period;
		this.RSIPoints = new ArrayList<IndicatorDatum>();
		refresh(data);
	}
	
	List<IndicatorDatum> getRSIPoints() {
		return RSIPoints;
	}
	
	/**
	 * Updates the RSI data points
	 */
	private void updateRSI() {
		
		double avgGain = 0;
		double avgLoss = 0;
		double rs;
		double rsi;
		for (int i = 0; i + period < data.size(); i++) {

			if (i == 0) {
				double[] avgGainLoss = calculateAvgGainLoss(0, period - 1);				// first average (simple period average)
				avgGain = avgGainLoss[0];
				avgLoss = avgGainLoss[1];
				rs = avgGain / avgLoss;
				rsi = 100 - (100 / (1 + rs));
				RSIPoints.add(new IndicatorDatum(data.get(period).getTime(), rsi));
				continue;
			}
			
			double currChange = data.get(i + period - 1).getClose() - data.get(i + period - 2).getClose();
																						// smoothing technique similar to exponential moving average calculation
			if (currChange < 0) {														// currently a loss
				avgLoss = ((avgLoss * (period - 1)) + Math.abs(currChange)) / period;
				avgGain = (avgGain * (period - 1)) / period; 
			} else {																	// currently a gain
				avgLoss = (avgLoss * (period - 1)) / period;
				avgGain = ((avgGain * (period - 1)) + currChange) / period;
			}

			rs = avgGain / avgLoss;
			rsi = 100 - (100 / (1 + rs));
			RSIPoints.add(new IndicatorDatum(data.get(i + period).getTime(), rsi));
		}
	}
	
	/**
	 * Calculates the average gain loss from starting index to end index of data inclusive
	 * 
	 * @param startIndex		int start index
	 * @param endIndex			int end index
	 * @return					double array of size 2. [0] - gain; [1] - loss (absolute value)
	 */
	private double[] calculateAvgGainLoss(int startIndex, int endIndex) {
		
		double gain = 0;
		double loss = 0;
		double[] avgGainLoss = new double[2];
		for (int i = startIndex; i <= endIndex; i++) {
			
			if (i == 0) continue;
			
			double currClose = data.get(i).getClose();
			double prevClose = data.get(i-1).getClose();
			double change = currClose - prevClose;
			
			if (change < 0) {
				loss += change;
			} else if (change >= 0) {
				gain += change;
			}
		}
		
		avgGainLoss[0] = gain / period;				// gain
		avgGainLoss[1] = Math.abs(loss) / period;	// loss
		
		return avgGainLoss;
	}

	@Override
	public void addToPlot(StockPlot stockPlot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(List<StockTimeFrameData> data) {
		this.data = data;
		updateRSI();
	}
}