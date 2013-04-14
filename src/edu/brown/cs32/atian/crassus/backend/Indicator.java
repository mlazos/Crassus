package edu.brown.cs32.atian.crassus.backend;

import java.util.*;
import edu.brown.cs32.atian.crassus.gui.StockPlot;

public interface Indicator {
	
	/**
	 * Adds the indicator data to the plot.
	 */
	void addToPlot(StockPlot stockPlot);
	
	/**
	 * Refreshes the indicator data values.
	 */
	void refresh(List<StockTimeFrameData> data);
}
