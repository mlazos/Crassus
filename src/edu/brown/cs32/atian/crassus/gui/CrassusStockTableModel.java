package edu.brown.cs32.atian.crassus.gui;

import javax.swing.table.AbstractTableModel;

public class CrassusStockTableModel extends AbstractTableModel {
	
	public CrassusStockTableModel(){
		
	}

	@Override
	public int getColumnCount() {
		return 4;
	}
	
	@Override
	public String getColumnName(int col) {
		switch(col){
		case 0:
			return "ticker";
		case 1:
			return "price";
		case 2:
			return "high";
		case 3:
			return "low";
		default:
			return "ERR";
		}
	}

	@Override
	public int getRowCount() {
		return 20;
	}

	@Override
	public Object getValueAt(int row, int col) {
		switch(col){
		case 0:
			return "GOOG";
		case 1:
			return "32.59";
		case 2:
			return "36.74";
		case 3:
			return "29.99";
		default:
			return "ERR";
		}
	}

}
