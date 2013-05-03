package edu.brown.cs32.atian.crassus.gui.mainwindow.table.stock;

import javax.swing.table.AbstractTableModel;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockList;
import edu.brown.cs32.atian.crassus.gui.mainwindow.table.CrassusTableRowSelector;

@SuppressWarnings("serial")
public class CrassusStockTableModel extends AbstractTableModel {
	
	private StockList _stocks;
	
	private CrassusTableRowSelector selector;
	
	public CrassusStockTableModel(StockList stocks, CrassusTableRowSelector selector){
		this._stocks = stocks;
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
		return _stocks.getStockList().size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		switch(col){
		case 0://ticker
			return _stocks.getStockList().get(row).getTicker();
		case 1://price
			return _stocks.getStockList().get(row).getStockRealTimeData().getCurrPrice();
		case 2://open
			return _stocks.getStockList().get(row).getStockRealTimeData().getOpenPrice();
		case 3://high
			return _stocks.getStockList().get(row).getStockRealTimeData().getTodayHigh();
		case 4://low
			return _stocks.getStockList().get(row).getStockRealTimeData().getTodayLow();
		default:
			return "ERR";
		}
	}

	public void addLastStock(Stock stock) {
		_stocks.add(stock);
		this.fireTableRowsInserted(_stocks.getStockList().size()-1, _stocks.getStockList().size()-1);
		selector.select(_stocks.getStockList().size()-1);
	}
	
	public void addStock(int i, Stock stock) {
		if(i==-1)
			return;
		
		_stocks.getStockList().add(i,stock);
		this.fireTableRowsInserted(i, i);
		selector.select(i);
	}

	public Stock removeLastStock() {
		int i = _stocks.getStockList().size()-1;
		return removeStock(i);
	}
	
	public Stock removeStock(int i) {
		if(i==-1)
			return null;

		selector.deselect(i);
		Stock stock = _stocks.getStockList().remove(i);
		this.fireTableRowsDeleted(i,i);
		return stock;
	}

	public void refresh() {
		this.fireTableRowsUpdated(0,_stocks.getStockList().size()-1);
	}

	public Stock getStock(int index) {
		return _stocks.getStockList().get(index);
	}


}
