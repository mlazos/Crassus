package edu.brown.cs32.atian.crassus.gui.mainwindow.table.stock;

import javax.swing.table.AbstractTableModel;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockList;
import edu.brown.cs32.atian.crassus.gui.mainwindow.table.CrassusTableRowSelector;

@SuppressWarnings("serial")
public class CrassusStockTableModel extends AbstractTableModel {
	
	private StockList stocks;
	
	private CrassusTableRowSelector selector;
	
	public CrassusStockTableModel(CrassusTableRowSelector selector){
		this.selector = selector;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public String getColumnName(int col) {
		switch(col){
		case 0:
			return "ticker";
		case 1:
			return "price";
		case 2:
			return "open";
		case 3:
			return "high";
		case 4:
			return "low";
		default:
			return "ERR";
		}
	}

	@Override
	public int getRowCount() {
		if(stocks == null)
			return 0;
		return stocks.getStockList().size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		switch(col){
		case 0://ticker
			return stocks.getStockList().get(row).getTicker();
		case 1://price
			return stocks.getStockList().get(row).getCurrPrice();
		case 2://open
			return stocks.getStockList().get(row).getOpenPrice();
		case 3://high
			return stocks.getStockList().get(row).getTodayHigh();
		case 4://low
			return stocks.getStockList().get(row).getTodayLow();
		default:
			return "ERR";
		}
	}

	public void addLastStock(Stock stock) {
		stocks.add(stock);
		this.fireTableRowsInserted(stocks.getStockList().size()-1, stocks.getStockList().size()-1);
		selector.select(stocks.getStockList().size()-1);
	}
	
	public void addStock(int i, Stock stock) {
		if(i==-1)
			return;
		
		this.stocks.getStockList().add(i,stock);
		this.fireTableRowsInserted(i, i);
		selector.select(i);
	}

	public Stock removeLastStock() {
		int i = stocks.getStockList().size()-1;
		return removeStock(i);
	}
	
	public Stock removeStock(int i) {
		if(i==-1)
			return null;

		selector.deselect(i);
		Stock stock = stocks.getStockList().remove(i);
		this.fireTableRowsDeleted(i,i);
		return stock;
	}

	public void refresh() {
		this.fireTableRowsUpdated(0,stocks.getStockList().size()-1);
	}

	public Stock getStock(int index) {
		return stocks.getStockList().get(index);
	}

	public void changeStockListTo(StockList stocks) {
		this.stocks = stocks;
		this.fireTableDataChanged();
	}


}
