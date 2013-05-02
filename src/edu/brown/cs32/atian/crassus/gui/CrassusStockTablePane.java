package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Stack;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockImpl;
import edu.brown.cs32.atian.crassus.backend.StockList;

@SuppressWarnings("serial")
public class CrassusStockTablePane extends JPanel {

	public class ChangeStockListenerForwarder implements ListSelectionListener {
		Stock lastStock;
		@Override public void valueChanged(ListSelectionEvent e) {
			if(_listener!=null){
				int index = table.getSelectedRow();
				if(index!=-1){
					Stock nextStock = model.getStock(table.getSelectedRow());
					if(lastStock!=nextStock)
						_listener.changeToStock(nextStock);
					lastStock = nextStock;
				}
			}
		}
	}

	private static final Pattern p =  Pattern.compile("[^a-zA-Z0-9]");
	public class NewTickerListener implements TickerDialogCloseListener {
		@Override public void tickerDialogClosedWithTicker(String symbol) {
			addTicker(symbol);
		}
	}

	public class PlusButtonListener implements ActionListener {
		@Override public void actionPerformed(ActionEvent arg0) {
			launchTickerCreator();
		}
	}

	public class CtrlTAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			launchTickerCreator();
		}
	}

	public class MinusButtonListener implements ActionListener {
		@Override public void actionPerformed(ActionEvent arg0) {
			removeSelectedTicker();
		}
	}

	public class CtrlShiftTAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			removeSelectedTicker();
		}
	}

	private JFrame _frame;
	private JTable table;
	private CrassusStockTableModel model;
	private CrassusChangeStockListener _listener;
	
	private StockList stocks;
	
	public CrassusStockTablePane(JFrame frame, StockList stocks, Stack<Undoable> undoables){
		_frame = frame;
		
		this.stocks = stocks;
		
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.getTableHeader().setBackground(Color.WHITE);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setFont(new Font("SansSerif",Font.BOLD,12));
		
		model = new CrassusStockTableModel(stocks,undoables);
		table.setModel(model);
		table.setShowGrid(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//allow only one row to be selected at a time
		table.setFillsViewportHeight(true);//makes extra space below table entries white
		table.setRowHeight(22);
		
		table.setIntercellSpacing(new Dimension(0,2));
		
		for(int i=0; i<5; i++){
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(50);
			column.setMaxWidth(50);
			column.setResizable(false);
			
			if(i==0){
				DefaultTableCellRenderer renderer = new CrassusStockTableRenderer(stocks,model);
				renderer.setHorizontalAlignment(SwingConstants.CENTER);
				column.setCellRenderer(renderer);
			}
			else{
				DefaultTableCellRenderer renderer = new CrassusStockTableRenderer(stocks,model);
				renderer.setHorizontalAlignment(SwingConstants.RIGHT);
				column.setCellRenderer(renderer);
			}
		}
		
		table.getSelectionModel().addListSelectionListener(new ChangeStockListenerForwarder());
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10,20,0,0));//right border (0) taken care of by increased table size (to deal with scroll-bar)
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(290,250));
		
		JLabel title = new JLabel("Tickers",JLabel.CENTER);
		title.setFont(new Font("SansSerif",Font.BOLD,18));
		this.add(title, BorderLayout.NORTH);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(20,20,20,20),
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		
		JButton addButton = new JButton("+");
		addButton.addActionListener(new PlusButtonListener());
		addButton.setToolTipText("add new ticker");
		addButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_DOWN_MASK),
				"CTRL T");
		addButton.getActionMap().put("CTRL T", new CtrlTAction());
		
		JButton removeButton = new JButton("-");
		removeButton.addActionListener(new MinusButtonListener());
		removeButton.setToolTipText("remove selected ticker");
		removeButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK), 
				"CTRL SHIFT T");
		removeButton.getActionMap().put("CTRL SHIFT T", new CtrlShiftTAction());
		
		JPanel buttonHolder = new JPanel();
		buttonHolder.setBackground(Color.WHITE);
		buttonHolder.setLayout(new FlowLayout());
		buttonHolder.add(addButton);
		buttonHolder.add(removeButton);
		
		
		JPanel buttonAndLine = new JPanel();
		buttonAndLine.setLayout(new BoxLayout(buttonAndLine,BoxLayout.Y_AXIS));
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		sep.setBackground(Color.GRAY);
		buttonAndLine.add(sep);
		buttonAndLine.add(buttonHolder);
		
		this.add(buttonAndLine, BorderLayout.SOUTH);
	}
	

	public void launchTickerCreator() {
		TickerDialog tickerFrame = new TickerDialog(_frame);
		tickerFrame.setTickerDialogCloseListener(new NewTickerListener());
		tickerFrame.setVisible(true);
	}

	public void addTicker(String symbol) {
		try{
			if(p.matcher(symbol).find())
				throw new IllegalArgumentException();
			symbol = symbol.toUpperCase();
			
			for(Stock other: stocks.getStockList()){
				if(symbol.equals(other.getTicker())){
					JOptionPane.showMessageDialog(_frame, "\'"+symbol+"\' is already in your ticker-table");
					int index = stocks.getStockList().indexOf(other);
					table.setRowSelectionInterval(index,index);
					return;
				}
			}
			
			Stock stock = new StockImpl(symbol);
			stock.refresh();
			model.addStock(stock);
			table.setRowSelectionInterval(model.getRowCount()-1, model.getRowCount()-1);
			
		}catch(IllegalArgumentException e){
			JOptionPane.showMessageDialog(_frame,"\'"+symbol+"\' is not a valid ticker symbol");
		}
	}

	public void removeSelectedTicker() {
		if(table.getRowCount()==0)
			return;
		
		int index = table.getSelectedRow();
		if(table.getRowCount()>1){
			if(index == table.getRowCount()-1)
				table.setRowSelectionInterval(index-1,index-1);
			else
				table.setRowSelectionInterval(index+1,index+1);
			
			_listener.changeToStock(model.getStock(table.getSelectedRow()));
		}
		else{
			_listener.changeToStock(null);
		}
		
		model.removeStock(index);
	}

	public void setChangeStockListener(CrassusChangeStockListener listener){
		_listener = listener;
	}

	public void refresh() {
		model.refresh();
	}
	
}
