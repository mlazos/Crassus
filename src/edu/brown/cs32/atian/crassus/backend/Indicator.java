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
	
	/*
	 * Tests the indicator against data which should have been
	 * passed into the Indicator's constructor.
	 */
	//void test();
	
	/**
	 * Returns boolean whether Indicator is visible
	 */
	boolean getVisible();
	
	/**
	 * Sets whether Indicator is visible on the stock graph.
	 */
	void setVisible(boolean isVisible);
	
	/**
	 * Returns boolean whether Indicator is active.
	 */
	boolean getActive();
	
	/*
	 * Sets whether the current Indicator is active meaning user
	 * will be alerted to triggered events.
	 */
	void setActive(boolean isActive);
	
	/**
	 * Checks whether the indicator is triggered.
	 * 
	 * @return	StockEventType which is either BUY, SELL, or NONE
	 */
	StockEventType isTriggered();
	
	/**
	 * Gets the name of an Indicator
	 * 
	 * @return		String name of Indicator
	 */
	String getName();
	
}