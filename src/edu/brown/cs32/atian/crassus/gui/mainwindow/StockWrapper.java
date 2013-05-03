package edu.brown.cs32.atian.crassus.gui.mainwindow;

import edu.brown.cs32.atian.crassus.backend.Stock;

public class StockWrapper {

	private final Stock stock;
	
	private int selectedIndex = -1;
	
	public StockWrapper(Stock stock){
		this.stock = stock;
	}
	
	public Stock getStock(){
		return stock;
	}
	
	public void setSelectedIndex(int i){
		selectedIndex = i;
	}
	
	public int getSelectedIndex(){
		return selectedIndex;
	}
}
