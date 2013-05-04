package edu.brown.cs32.atian.crassus.gui.mainwindow.table.indicator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.gui.undoable.UndoableStack;

@SuppressWarnings("serial")
public class CrassusIndicatorTableEditor extends AbstractCellEditor implements TableCellEditor {

	private CrassusCheckBoxEye cbe;
	private CrassusCheckBoxAlert cba;
	private Stock stock;
	
	private UndoableStack undoables;
	private CrassusIndicatorTableModel model;
	
	boolean usingEye;
	
	public CrassusIndicatorTableEditor(CrassusIndicatorTableModel model, UndoableStack undoables){
		cbe = new CrassusCheckBoxEye();
		cba = new CrassusCheckBoxAlert();
		this.undoables = undoables;
		this.model = model;
	}
	
	@Override
	public Object getCellEditorValue() {
		if(usingEye)
			return cbe.isSelected();
		else
			return cba.isSelected();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		JCheckBox cb = (column==0) ? cbe : cba;
		usingEye = (column==0);
		
		if (value instanceof Boolean) {
            boolean selected = (Boolean) value;
            cb.setSelected(selected);
            undoables.push(new CheckBoxUndoable(selected,table,model,row,column));
        }

		if(row%5==0)//(stocks.getStockList().get(row).isTriggered() == StockEventType.BUY)
			cb.setBackground(new Color(170,230,170));
		else if(row%5==1)//(stocks.getStockList().get(row).isTriggered() == StockEventType.SELL)
			cb.setBackground(new Color(255,150,150));
		else
			cb.setBackground(Color.WHITE);


		JPanel panel = new JPanel(new BorderLayout());
		panel.add(cb,BorderLayout.CENTER);
		panel.setBackground(cb.getBackground());

		
		if(column==0)
			panel.setBorder(BorderFactory.createMatteBorder(3,3,3,0,Color.BLACK));
		else if(column==1)
			panel.setBorder(BorderFactory.createMatteBorder(3,0,3,0,Color.BLACK));
		
        return panel;
	}


	public void changeToStock(Stock stock){
		this.stock = stock;
		this.fireEditingStopped();
	}

	public void indicatorWasRemoved() {
		this.fireEditingStopped();
	}
}
