package edu.brown.cs32.atian.crassus.gui.undoable;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.gui.CrassusStockTableModel;
import edu.brown.cs32.atian.crassus.gui.CrassusTableRowSelector;

public class AddStockUndoable implements Undoable {

	private final CrassusStockTableModel model;
	private final Stock stock;
	private final int previousIndex;
	private final CrassusTableRowSelector selector;
	
	public AddStockUndoable(CrassusStockTableModel model, Stock stock, int previousIndex, CrassusTableRowSelector selector){
		this.model = model;
		this.stock = stock;
		this.previousIndex = previousIndex;
		this.selector = selector;
	}
	
	@Override
	public void undo() {
		selector.deselectLast();
		model.removeLastStock();
	}

	@Override
	public void redo() {
		model.addLastStock(stock);
		selector.selectLast();
	}

}
