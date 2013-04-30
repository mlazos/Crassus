package edu.brown.cs32.atian.crassus.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import edu.brown.cs32.atian.crassus.backend.Indicator;
import edu.brown.cs32.atian.crassus.backend.Stock;

public class CrassusEventTableModel extends AbstractTableModel {
	
	private boolean[][] tempBooleanHolder = new boolean[20][2];
	
	private Stock stock;
	
	public CrassusEventTableModel(){
		
	}

	@Override
	public int getColumnCount() {
		return 3;
	}
	
	@Override
	public String getColumnName(int col) {
		switch(col){
		case 0:
		case 1:
			return "";
		case 2:
			return "Event Type";
		default:
			return "ERR";
		}
	}
	
	@Override
	public Class<?> getColumnClass(int col) {
		switch(col){
		case 0:
		case 1:
			return Boolean.class;
		case 2:
			return String.class;
		default:
			return String.class;
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
			return tempBooleanHolder[row][0];
		case 1:
			return tempBooleanHolder[row][1];
		case 2:
			return "Name of an event";
		default:
			return "ERR";
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int col){
		return col <= 1;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		tempBooleanHolder[row][col] = ((Boolean)value).booleanValue();
	}

	public void addIndicator(Indicator ind) {
		stock.addEvent(ind);
	}

}
