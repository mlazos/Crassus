package edu.brown.cs32.atian.crassus.backend;

public interface StockEvent {

	void getIntraDayTestData();

	void getLongTermTestData();
	
	void addToPlot();
	
	void refresh();
}
