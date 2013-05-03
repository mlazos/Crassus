package edu.brown.cs32.atian.crassus.gui.mainwindow.table.stock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockList;

@SuppressWarnings("serial")
public class CrassusStockTableRenderer extends DefaultTableCellRenderer {

	public class DeleteStockListener implements ActionListener {
		private Stock s;
		public DeleteStockListener(Stock s){this.s = s;}
		@Override public void actionPerformed(ActionEvent arg0) {
			System.out.println("going to try deleting stock");
			int index = stocks.getStockList().indexOf(s);
			model.removeStock(index);
		}
	}

	public class RightClickListener extends MouseAdapter{
		private Stock s;
		
		public RightClickListener(Stock s){
			this.s = s;
		}
		
		@Override public void mousePressed(MouseEvent e){
			showPopup(e);
		}
		
		@Override public void mouseReleased(MouseEvent e){
			showPopup(e);
		}
		
		private void showPopup(MouseEvent e){
			System.out.println("in right click listener");
			if(e.isPopupTrigger()){
				JPopupMenu menu = new JPopupMenu();
				JMenuItem delete = new JMenuItem("delete");
				delete.addActionListener(new DeleteStockListener(s));
				menu.add(delete);
				menu.show(e.getComponent(),e.getX(),e.getY());
			}
		}
	}

	private StockList stocks;
	private CrassusStockTableModel model;
	
	public CrassusStockTableRenderer(StockList stocks, CrassusStockTableModel model){
		this.stocks = stocks;
		this.model = model;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
			boolean hasFocus, int row, int column){
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		Stock s = stocks.getStockList().get(row);
		
		if(row%5==0)//(stocks.getStockList().get(row).isTriggered() == StockEventType.BUY)
			c.setBackground(new Color(170,230,170));
		else if(row%5==1)//(stocks.getStockList().get(row).isTriggered() == StockEventType.SELL)
			c.setBackground(new Color(255,150,150));
		else
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
			panel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(3,3,3,0),
					BorderFactory.createMatteBorder(0, 0, 0, 3, c.getBackground())));
		else if(column==4)
			panel.setBorder(BorderFactory.createEmptyBorder(3,0,3,3));
		else
			panel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(3,0,3,0),
					BorderFactory.createMatteBorder(0, 0, 0, 3, c.getBackground())));

		panel.setToolTipText("<html>"+s.getCompanyName()+"<br>"
				+"52-week high: "+s.getStockRealTimeData().getWeek52High()+"<br>"
				+"52-week low:  "+s.getStockRealTimeData().getWeek52Low()+"<br>"
				+"percent change: "+s.getStockRealTimeData().getChgAndPertChg()+"</html>");
		
//		panel.addMouseListener(new RightClickListener(s));
//
//		JPopupMenu menu = new JPopupMenu();
//		JMenuItem delete = new JMenuItem("delete");
//		delete.addActionListener(new DeleteStockListener(s));
//		menu.add(delete);
//		panel.setComponentPopupMenu(menu);
		
		return panel;
	}
}
