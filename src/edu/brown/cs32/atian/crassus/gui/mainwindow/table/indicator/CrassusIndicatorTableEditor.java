package edu.brown.cs32.atian.crassus.gui.mainwindow.table.indicator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.gui.undoable.UndoableStack;
import edu.brown.cs32.atian.crassus.indicators.Indicator;

@SuppressWarnings("serial")
public class CrassusIndicatorTableEditor extends AbstractCellEditor implements TableCellEditor {

	public class EyeBoxListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			indicator.setVisible(cbe.isSelected());
            undoables.push(new CheckBoxUndoable(cbe.isSelected(),cbe,indicator,
            		table,model,outer(),row,column));
            fireEditingCanceled();
            fireEditingStopped();
		}
	}

	public class AlertBoxListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}
	}

	private CrassusCheckBoxEye cbe;
	private CrassusCheckBoxAlert cba;
	private Stock stock;
	
	private Indicator indicator;
	private JTable table;
	private int row;
	private int column;
	
	private UndoableStack undoables;
	private CrassusIndicatorTableModel model;
	
	boolean usingEye;
	
	public CrassusIndicatorTableEditor(CrassusIndicatorTableModel model, UndoableStack undoables){
		cbe = new CrassusCheckBoxEye();
		cbe.addActionListener(new EyeBoxListener());
		cba = new CrassusCheckBoxAlert();
		cba.addActionListener(new AlertBoxListener());
		this.undoables = undoables;
		this.model = model;
	}
	
	private CrassusIndicatorTableEditor outer(){
		return this;
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
		
		indicator = stock.getEventList().get(row);
		this.table = table;
		this.row = row;
		this.column = column;

		JCheckBox cb = (column==0) ? cbe : cba;
		usingEye = (column==0);
		
		if (value instanceof Boolean) {
            boolean selected = (Boolean) value;
            cb.setSelected(selected);
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
