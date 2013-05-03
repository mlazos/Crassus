package edu.brown.cs32.atian.crassus.gui.mainwindow.table.stock;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.gui.mainwindow.table.CrassusTableRowSelector;
import edu.brown.cs32.atian.crassus.gui.undoable.Undoable;

public class RemoveStockUndoable implements Undoable {

	private final CrassusStockTableModel model;
	private final Stock stock;
	private final int index;
	private final CrassusTableRowSelector selector;
	
	public RemoveStockUndoable(CrassusStockTableModel model, Stock stock, int index, CrassusTableRowSelector selector){
		this.model = model;
		this.stock = stock;
		this.index = index;
		this.selector = selector;
	}
	
	@Override
	public void undo() {
		model.addStock(index,stock);
		selector.select(index);
	}

	@Override
	public void redo() {
		selector.deselect(index);
		model.removeStock(index);
	}

}
