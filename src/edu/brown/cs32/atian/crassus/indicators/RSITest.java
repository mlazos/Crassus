package edu.brown.cs32.atian.crassus.indicators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs32.atian.crassus.backend.StockTimeFrameData;

public class RSITest {
	
	private static RSI rsi;

	@BeforeClass
	public static void setUp() {
		
		//new StockTimeFrameData(String time, double open, double high, double low,
	    //        double close, int volume, double adjustedClose)
		
		List<StockTimeFrameData> data = new ArrayList<>();
		data.add(new StockTimeFrameData("a", 0, 0, 0, 88.71, 0, 0, false)); 
		data.add(new StockTimeFrameData("b", 0, 0, 0, 89.05, 0, 0, false)); 
		data.add(new StockTimeFrameData("c", 0, 0, 0, 89.24, 0, 0, false)); 
		data.add(new StockTimeFrameData("d", 0, 0, 0, 89.19, 0, 0, false));	
		data.add(new StockTimeFrameData("e", 0, 0, 0, 89.51, 0, 0, false)); 
		data.add(new StockTimeFrameData("f", 0, 0, 0, 88.69, 0, 0, false)); 
		data.add(new StockTimeFrameData("g", 0, 0, 0, 88.9, 0, 0, false)); 	
		data.add(new StockTimeFrameData("h", 0, 0, 0, 89.2, 0, 0, false)); 
		rsi = new RSI(data, 4);
	}
	
	
	@Test
	public void test() {
		List<IndicatorDatum> RSIPoints = rsi.getRSIPoints();
		assertTrue(RSIPoints.size() == 4);
		assertTrue(Math.abs(RSIPoints.get(0).getValue() - 91.37931) < 0.00001);
		assertTrue(Math.abs(RSIPoints.get(1).getValue() - 95.0331125) < 0.00001);
		assertTrue(Math.abs(RSIPoints.get(2).getValue() - 38.81875) < 0.00001);
		assertTrue(Math.abs(RSIPoints.get(3).getValue() - 49.09977) < 0.00001);
	}

}
