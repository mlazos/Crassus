package edu.brown.cs32.atian.crassus.backend;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class BollingerBandsTest {

	private static BollingerBands bb; 
	
	@BeforeClass
	public static void setUp() {
		
		//new StockTimeFrameData(String time, double open, double high, double low,
	    //        double close, int volume, double adjustedClose)
		
		List<StockTimeFrameData> data = new ArrayList<>();
		data.add(new StockTimeFrameData("a", 0, 0, 0, 88.71, 0, 0, false)); 
		data.add(new StockTimeFrameData("b", 0, 0, 0, 89.05, 0, 0, false)); 
		data.add(new StockTimeFrameData("c", 0, 0, 0, 89.24, 0, 0, false)); 
		data.add(new StockTimeFrameData("d", 0, 0, 0, 89.39, 0, 0, false));	
		data.add(new StockTimeFrameData("e", 0, 0, 0, 89.51, 0, 0, false)); 
		data.add(new StockTimeFrameData("f", 0, 0, 0, 89.69, 0, 0, false)); 
		data.add(new StockTimeFrameData("g", 0, 0, 0, 89.75, 0, 0, false)); 	
		data.add(new StockTimeFrameData("h", 0, 0, 0, 89.91, 0, 0, false)); 
		data.add(new StockTimeFrameData("i", 0, 0, 0, 90.08, 0, 0, false)); 
		data.add(new StockTimeFrameData("j", 0, 0, 0, 90.38, 0, 0, false)); 
		data.add(new StockTimeFrameData("k", 0, 0, 0, 90.66, 0, 0, false)); 
		data.add(new StockTimeFrameData("l", 0, 0, 0, 90.86, 0, 0, false)); 
		data.add(new StockTimeFrameData("m", 0, 0, 0, 90.88, 0, 0, false)); 
		data.add(new StockTimeFrameData("n", 0, 0, 0, 90.91, 0, 0, false)); 
		data.add(new StockTimeFrameData("o", 0, 0, 0, 90.99, 0, 0, false)); 
		data.add(new StockTimeFrameData("p", 0, 0, 0, 91.15, 0, 0, false));
		data.add(new StockTimeFrameData("q", 0, 0, 0, 91.19, 0, 0, false)); 
		data.add(new StockTimeFrameData("r", 0, 0, 0, 91.12, 0, 0, false)); 
		data.add(new StockTimeFrameData("s", 0, 0, 0, 91.17, 0, 0, false)); 
		data.add(new StockTimeFrameData("t", 0, 0, 0, 91.25, 0, 0, false)); 
		data.add(new StockTimeFrameData("u", 0, 0, 0, 91.24, 0, 0, false)); 
		data.add(new StockTimeFrameData("v", 0, 0, 0, 91.17, 0, 0, false)); 
		data.add(new StockTimeFrameData("x", 0, 0, 0, 91.05, 0, 0, false)); 
		bb = new BollingerBands(data, 20, 2);
	}
	
	@Test
	public void testCalcSMA() {
		assertTrue(bb.calcSMA(0, 0) == 88.71);									// 1 day SMA
		assertTrue(bb.calcSMA(0, 1) == 88.88);									// 2 day SMA
		assertTrue(bb.calcSMA(0, 2) == 89);										// 3 day SMA
		assertTrue(bb.calcSMA(0, 3) == 89.0975);								// 4 day SMA
		assertTrue(Math.abs((bb.calcSMA(0, 4) - 89.18)) < 0.00001);				// 5 day SMA
		assertTrue(Math.abs((bb.calcSMA(0, 5) - 89.265)) < 0.00001);			// 6 day SMA
		assertTrue(Math.abs((bb.calcSMA(0, 6) - 89.33428571)) < 0.00001);		// 7 day SMA
		assertTrue(bb.calcSMA(0, 19) == 90.2945);								// 20 day SMA
		assertTrue(Math.abs((bb.calcSMA(18, 22) - 91.176)) < 0.00001);			// 5 day SMA at the end
	}
	
	@Test
	public void testCalcStdDev() {
		assertTrue(bb.calcStdDev(0, 0, bb.calcSMA(0, 0)) == 0);
		assertTrue(Math.abs(bb.calcStdDev(0, 1, bb.calcSMA(0, 1)) - 0.17) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 2, bb.calcSMA(0, 2)) - 0.21924) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 3, bb.calcSMA(0, 3)) - 0.254103) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 4, bb.calcSMA(0, 4)) - 0.280855) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 5, bb.calcSMA(0, 5)) - 0.319152) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 6, bb.calcSMA(0, 6)) - 0.340749) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(0, 19, bb.calcSMA(0, 19)) - 0.806892) < 0.00001);
		assertTrue(Math.abs(bb.calcStdDev(18,22, bb.calcSMA(18, 22)) - 0.0714422) < 0.00001);
	}
	
	@Test
	public void testGetBands() {

		List<IndicatorDatum> upperBand = bb.getUpperBand();
		List<IndicatorDatum> middleBand = bb.getMiddleBand();
		List<IndicatorDatum> lowerBand = bb.getLowerBand();
		
		assertTrue(upperBand.size() == 4);
		assertTrue(middleBand.size() == 4);
		assertTrue(lowerBand.size() == 4);
		
		assertTrue(Math.abs(upperBand.get(0).getValue()) - 91.908284 < 0.00001);
		assertTrue(upperBand.get(0).getTimeLabel().equals("t"));
		assertTrue(Math.abs(middleBand.get(0).getValue()) - 90.2945 < 0.00001);
		assertTrue(middleBand.get(0).getTimeLabel().equals("t"));
		assertTrue(Math.abs(lowerBand.get(0).getValue()) - 88.680716 < 0.00001);
		assertTrue(lowerBand.get(0).getTimeLabel().equals("t"));

		assertTrue(Math.abs(upperBand.get(1).getValue()) - 91.90994 < 0.00001);
		assertTrue(upperBand.get(1).getTimeLabel().equals("u"));
		assertTrue(Math.abs(middleBand.get(1).getValue()) - 90.421 < 0.00001);
		assertTrue(middleBand.get(1).getTimeLabel().equals("u"));
		assertTrue(Math.abs(lowerBand.get(1).getValue()) - 88.93506 < 0.00001);
		assertTrue(lowerBand.get(1).getTimeLabel().equals("u"));

		assertTrue(Math.abs(upperBand.get(2).getValue() - 91.9084) < 0.00001);
		assertTrue(upperBand.get(2).getTimeLabel().equals("v"));
		assertTrue(Math.abs(middleBand.get(2).getValue() - 90.5270) < 0.00001);
		assertTrue(middleBand.get(2).getTimeLabel().equals("v"));
		assertTrue(Math.abs(lowerBand.get(2).getValue() - 89.145594) < 0.00001);
		assertTrue(lowerBand.get(2).getTimeLabel().equals("v"));

		assertTrue(Math.abs(upperBand.get(3).getValue()) - 91.88199 < 0.00001);
		assertTrue(upperBand.get(3).getTimeLabel().equals("x"));
		assertTrue(Math.abs(middleBand.get(3).getValue()) - 90.6175 < 0.00001);
		assertTrue(middleBand.get(3).getTimeLabel().equals("x"));
		assertTrue(Math.abs(lowerBand.get(3).getValue()) - 89.3530 < 0.00001);
		assertTrue(lowerBand.get(3).getTimeLabel().equals("x"));
	}
}