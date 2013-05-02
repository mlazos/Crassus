package edu.brown.cs32.atian.crassus.backend;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs32.atian.crassus.indicators.BollingerBands;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<StockTimeFrameData> data = new ArrayList<>();
		data.add(new StockTimeFrameData("a", 0, 1, 3, 88.71, 0, 0, false)); 
		BollingerBands bb = new BollingerBands(data, 0, 20);

	}

}
