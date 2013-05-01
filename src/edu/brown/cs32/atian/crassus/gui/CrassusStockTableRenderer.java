package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import edu.brown.cs32.atian.crassus.backend.StockEventType;
import edu.brown.cs32.atian.crassus.backend.StockList;

public class CrassusStockTableRenderer extends DefaultTableCellRenderer {

	StockList stocks;
	
	public CrassusStockTableRenderer(StockList stocks){
		this.stocks = stocks;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
			boolean hasFocus, int row, int column){
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(row%5==0)//(stocks.getStockList().get(row).isTriggered() == StockEventType.BUY)
//			if(isSelected)
				c.setBackground(new Color(0,230,0));
//			else
//				c.setBackground(new Color(170,230,170));
		else if(row%5==1)//(stocks.getStockList().get(row).isTriggered() == StockEventType.SELL)
//			if(isSelected)
				c.setBackground(new Color(255,50,50));
//			else
//				c.setBackground(new Color(255,150,150));
		else
//			if(isSelected)
//			//	c.setBackground(new Color(210,210,255));
//				c.setBackground(new Color(130,130,180));
//			else
				c.setBackground(Color.WHITE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(c,BorderLayout.CENTER);
		
		if(isSelected){
			c.setFont(new Font("Arial Bold", Font.BOLD, 12));
			panel.setBackground(Color.BLACK);
		}
		else{
			panel.setBackground(c.getBackground());
		}
		
		if(column==0)
			panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,0));
		else if(column==4)
			panel.setBorder(BorderFactory.createEmptyBorder(2,0,2,2));
		else
			panel.setBorder(BorderFactory.createEmptyBorder(2,0,2,0));

		return panel;
	}
}
