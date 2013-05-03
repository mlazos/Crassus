package edu.brown.cs32.atian.crassus.gui.mainwindow;

import javax.swing.JTable;

public class CrassusTableRowSelector {

	private JTable table;
	
	public CrassusTableRowSelector(JTable table){
		this.table = table;
	}
	
	public void select(int i){
		if(i!=-1 && i<table.getRowCount()){
			table.setRowSelectionInterval(i,i);
		}
	}

	public void selectLast() {
		select(table.getRowCount()-1);
	}
	
	public void deselectCurrentSelection(){
		if(table.getRowCount()==0)
			return;
		
		int index = table.getSelectedRow();
		if(table.getRowCount()>1){
			if(index == table.getRowCount()-1)
				table.setRowSelectionInterval(index-1,index-1);
			else
				table.setRowSelectionInterval(index+1,index+1);
		}
	}

	public void deselect(int index) {
		if(index != table.getSelectedRow())
			return;
		
		if(table.getRowCount()==0)
			return;
		
		if(table.getRowCount()>1){
			if(index == table.getRowCount()-1)
				table.setRowSelectionInterval(index-1,index-1);
			else
				table.setRowSelectionInterval(index+1,index+1);
		}
	}

	public void deselectLast() {
		deselect(table.getRowCount()-1);
	}

}
