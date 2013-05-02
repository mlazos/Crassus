package edu.brown.cs32.atian.crassus.gui;

import java.util.Stack;

import javax.swing.table.AbstractTableModel;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockList;

@SuppressWarnings("serial")
public class CrassusStockTableModel extends AbstractTableModel {
	
	private StockList _stocks;
	private Stack<Undoable> undoables;
	
	public CrassusStockTableModel(StockList stocks, Stack<Undoable> undoables){
		_stocks = stocks;
		this.undoables = undoables;
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

	public void addStock(Stock stock) {
		_stocks.add(stock);
		this.fireTableRowsInserted(_stocks.getStockList().size()-1, _stocks.getStockList().size()-1);
		//undoables.add(new UndoableStockTableChange(this, stock, _stocks.getStockList().size()-1, true));
	}
	
	public void addStock(int i, Stock stock) {
		_stocks.getStockList().add(i,stock);
		//undoables.add(new UndoableStockTableChange(this, stock, i, true));
	}

	public Stock removeStock(int i) {
		if(i!=-1){
			Stock stock = _stocks.getStockList().remove(i);
			this.fireTableRowsDeleted(i,i);
			//undoables.add(new UndoableStockTableChange(this, stock, i, false));
			return stock;
		}
		else return null;
	}

	public void refresh() {
		this.fireTableRowsUpdated(0,_stocks.getStockList().size()-1);
	}

	public Stock getStock(int index) {
		return _stocks.getStockList().get(index);
	}

}
