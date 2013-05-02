package edu.brown.cs32.atian.crassus.gui;

import edu.brown.cs32.atian.crassus.backend.Stock;

public class UndoableStockTableChange implements Undoable {

	final private CrassusStockTableModel model;
	final private Stock stock;
	final private int index;
	final private boolean added;
	
	public UndoableStockTableChange(CrassusStockTableModel model, Stock stock, int index, boolean added){
		this.model = model;
		this.stock = stock;
		this.index = index;
		this.added = added;
	}
	
	@Override
	public void undo() {
		if(added)
			model.removeStock(index);
		else
			model.addStock(index,stock);
	}

	@Override
	public void redo() {
		if(added)
			model.addStock(index,stock);
		else
			model.removeStock(index);
	}

}
