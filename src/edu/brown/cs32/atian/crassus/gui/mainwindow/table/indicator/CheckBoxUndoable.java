package edu.brown.cs32.atian.crassus.gui.mainwindow.table.indicator;

import javax.swing.JTable;

import edu.brown.cs32.atian.crassus.gui.undoable.Undoable;

public class CheckBoxUndoable implements Undoable {

	private final boolean selected;
	private final JTable table;
	private final CrassusIndicatorTableModel model;
	private final int row;
	private final int column;
	
	public CheckBoxUndoable(boolean selected, JTable table, CrassusIndicatorTableModel model, int row, int column) {
		this.selected = selected;
		this.table = table;
		this.model = model;
		this.row = row;
		this.column = column;
	}

	@Override
	public void undo() {
		System.out.println("undoing...");
		table.setValueAt(!selected, row, column);
		model.fireTableCellUpdated(row, column);
		table.revalidate();
		table.repaint();
	}

	@Override
	public void redo() {
		System.out.println("redoing...");
		table.setValueAt(selected, row, column);
		model.fireTableCellUpdated(row, column);
	}

}
