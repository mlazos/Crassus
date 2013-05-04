package edu.brown.cs32.atian.crassus.gui.mainwindow.table.indicator;

import javax.swing.JCheckBox;
import javax.swing.JTable;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.gui.undoable.Undoable;
import edu.brown.cs32.atian.crassus.indicators.Indicator;

public class CheckBoxUndoable implements Undoable {

	private final boolean selected;
	private final JCheckBox cb;
	private final Indicator indicator;
	private final JTable table;
	private final CrassusIndicatorTableModel model;
	private final CrassusIndicatorTableEditor editor;
	private final int row;
	private final int column;
	
	public CheckBoxUndoable(boolean selected, JCheckBox cb, Indicator indicator, JTable table, CrassusIndicatorTableModel model, CrassusIndicatorTableEditor editor, int row, int column) {
		System.out.println("new check-box-undoable");
		this.selected = selected;
		this.cb = cb;
		this.indicator = indicator;
		this.table = table;
		this.model = model;
		this.editor = editor;
		this.row = row;
		this.column = column;
	}

	@Override
	public void undo() {
		indicator.setVisible(!selected);
		//cb.setSelected(!selected);
		System.out.println("undoing...");
		//table.setValueAt(!selected, row, column);
		model.fireTableCellUpdated(row, column);
		//editor.indicatorWasRemoved();
//		table.revalidate();
//		table.repaint();
	}

	@Override
	public void redo() {
		//indicator.setVisible(selected);
		//cb.setSelected(selected);
		System.out.println("redoing...");
		table.setValueAt(selected, row, column);
		model.fireTableCellUpdated(row, column);
		//editor.indicatorWasRemoved();
	}

}
