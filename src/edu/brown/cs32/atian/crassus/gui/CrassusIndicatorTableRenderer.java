package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import edu.brown.cs32.atian.crassus.backend.Stock;

public class CrassusIndicatorTableRenderer implements TableCellRenderer{// extends DefaultTableCellRenderer {

	Stock stock;
	
	
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		JComponent c = null;

		switch(column){
		case 0:
			JCheckBox cb1 = new CrassusCheckBoxEye();
			if(value.equals(true))
				cb1.setSelected(true);
			c = cb1;
			break;
		case 1:
			JCheckBox cb2 = new CrassusCheckBoxAlert();
			if(value.equals(true))
				cb2.setSelected(true);
			c = cb2;
			break;
		case 2:
			JLabel label = new JLabel((String)value,JLabel.CENTER);
			label.setOpaque(true);
			c = label;
			break;
		}
		

		if(row%5==0)//(stocks.getStockList().get(row).isTriggered() == StockEventType.BUY)
			c.setBackground(new Color(170,230,170));
		else if(row%5==1)//(stocks.getStockList().get(row).isTriggered() == StockEventType.SELL)
			c.setBackground(new Color(255,150,150));
		else
			c.setBackground(Color.WHITE);

			
		return c;
	}
	
	public void changeToStock(Stock stock){
		this.stock = stock;
	}
}
