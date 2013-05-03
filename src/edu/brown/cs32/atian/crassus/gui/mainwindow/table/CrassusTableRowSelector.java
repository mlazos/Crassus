package edu.brown.cs32.atian.crassus.gui.mainwindow.table;

import javax.swing.JTable;

public class CrassusTableRowSelector {

	private JTable table;
	private boolean shouldRegisterSelection = true;
	
	public CrassusTableRowSelector(JTable table){
		this.table = table;
	}
	
	public boolean shouldRegisterSelection(){
		return shouldRegisterSelection;
	}
	
	public void select(int i){
		if(i!=-1 && i<table.getRowCount()){
			shouldRegisterSelection = false;
			table.setRowSelectionInterval(i,i);
			shouldRegisterSelection = true;
		}
	}

	public void selectLast() {
		select(table.getRowCount()-1);
	}
	
	public void deselect(int index) {
		if(index != table.getSelectedRow())
			return;
		
		if(table.getRowCount()==0)
			return;
		
		if(table.getRowCount()>1){
			shouldRegisterSelection = false;
			if(index == table.getRowCount()-1)
				table.setRowSelectionInterval(index-1,index-1);
			else
				table.setRowSelectionInterval(index+1,index+1);
			shouldRegisterSelection = true;
		}
	}

	public void deselectLast() {
		deselect(table.getRowCount()-1);
	}

}
