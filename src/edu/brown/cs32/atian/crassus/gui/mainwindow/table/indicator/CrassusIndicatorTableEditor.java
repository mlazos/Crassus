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
import edu.brown.cs32.atian.crassus.gui.mainwindow.CrassusPlotIsObsoleteListener;
import edu.brown.cs32.atian.crassus.gui.undoable.UndoableStack;
import edu.brown.cs32.atian.crassus.indicators.Indicator;

@SuppressWarnings("serial")
public class CrassusIndicatorTableEditor extends AbstractCellEditor implements TableCellEditor {

	public class EyeBoxListener implements ActionListener {

		public void possiblyChangeState(boolean state, int otherRow){
			if(row!=otherRow || !usingEye)
				return;
			System.out.println("eye-box-listener.... fucking weird");
			cbe.removeActionListener(this);
			cbe.setSelected(state);
			cbe.addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			indicator.setVisible(cbe.isSelected());
			listener.informPlotIsObsolete();
			undoables.push(new EyeBoxUndoable(cbe.isSelected(),indicator,listener,table,row,this));
//			
			cbe.removeActionListener(this);
			stopCellEditing();
		}

	}

	public class AlertBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			indicator.setActive(cba.isSelected());
			
		}

	}

	private CrassusCheckBoxEye cbe;
	private EyeBoxListener eyeListener;
	private CrassusCheckBoxAlert cba;
	private AlertBoxListener alertListener;
	private Stock stock;
	
	private Indicator indicator;
	private JTable table;
	private int row;
	
	private UndoableStack undoables;
	
	private boolean usingEye;
	
	private CrassusPlotIsObsoleteListener listener;
	
	public CrassusIndicatorTableEditor(CrassusPlotIsObsoleteListener listener, UndoableStack undoables){
		cbe = new CrassusCheckBoxEye();
		eyeListener = new EyeBoxListener();
		cba = new CrassusCheckBoxAlert();
		alertListener = new AlertBoxListener();
		cba.addActionListener(new AlertBoxListener());
		
		this.listener = listener;
		this.undoables = undoables;
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
		indicator = stock.getEventList().get(row);
		this.table = table;
		this.row = row;
		
		if (value instanceof Boolean) {
            boolean selected = (boolean) value;
            if(column==0){
            	cbe.setSelected(selected);
            	cbe.addActionListener(eyeListener);
            }
            else{
            	cba.setSelected(selected);
            	cba.addActionListener(alertListener);
            }
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
