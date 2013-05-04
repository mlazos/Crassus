package edu.brown.cs32.atian.crassus.gui.mainwindow.table.indicator;

import javax.swing.JTable;

import edu.brown.cs32.atian.crassus.gui.mainwindow.CrassusPlotIsObsoleteListener;
import edu.brown.cs32.atian.crassus.gui.mainwindow.table.indicator.CrassusIndicatorTableEditor.EyeBoxListener;
import edu.brown.cs32.atian.crassus.gui.undoable.Undoable;
import edu.brown.cs32.atian.crassus.indicators.Indicator;

public class EyeBoxUndoable implements Undoable {

	private final boolean selected;
	private final Indicator indicator;
	private final CrassusPlotIsObsoleteListener plotListener;
	private final JTable table;
	private final int row;
	private final EyeBoxListener eyeListener;
	
	public EyeBoxUndoable(boolean selected, Indicator indicator, CrassusPlotIsObsoleteListener plotListener, JTable table, int row, EyeBoxListener eyeListener) {
		this.selected = selected;
		this.indicator = indicator;
		this.plotListener = plotListener;
		this.table = table;
		this.row = row;
		this.eyeListener = eyeListener;
	}

	@Override
	public void undo() {
		if(table.isEditing())
			table.getCellEditor().stopCellEditing();
		//indicator.setVisible(!selected);
		table.getModel().setValueAt(!selected, row, 0);
		//eyeListener.possiblyChangeState(!selected, row);
		plotListener.informPlotIsObsolete();
	}

	@Override
	public void redo() {
		if(table.isEditing())
			table.getCellEditor().stopCellEditing();
		//indicator.setVisible(selected);
		table.getModel().setValueAt(selected, row, 0);
		eyeListener.possiblyChangeState(selected, row);
		plotListener.informPlotIsObsolete();
	}

}
