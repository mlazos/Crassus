/**
 * 
 */
package edu.brown.cs32.atian.crassus.gui.mainwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockList;
import edu.brown.cs32.atian.crassus.gui.dialogs.DotCrassusFileGui;
import edu.brown.cs32.atian.crassus.gui.mainwindow.plot.CrassusPlotPane;
import edu.brown.cs32.atian.crassus.gui.mainwindow.table.indicator.CrassusIndicatorTablePane;
import edu.brown.cs32.atian.crassus.gui.mainwindow.table.stock.CrassusStockTablePane;
import edu.brown.cs32.atian.crassus.gui.undoable.UndoableStack;

/**
 * @author Matthew
 *
 */
public class CrassusGUI implements GUI {
	
	/*
	 * inner classes appear in same order that they do in the constructor.
	 */
	
	private class fileNewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			StockList stocks = fileGui.fileNew();
			changeStockListTo(stocks);
		}
	}

	private class FileOpenListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			StockList stocks = fileGui.fileOpen();
			changeStockListTo(stocks);
		}
	}

	private class FileSaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			fileGui.fileSave();
		}
	}

	private class FileSaveAsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			fileGui.fileSaveAs();
		}
	}

	private class FileExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frame.dispose();
		}
	}

	private class EditUndoListener implements ActionListener {
		
		@Override 
		public void actionPerformed(ActionEvent e) {
			undoables.undo();
		}
	}

	private class EditRedoListner implements ActionListener {
		
		@Override 
		public void actionPerformed(ActionEvent e) {
			undoables.redo();
		}
	}
	
	//TODO change above inner classes and possibly below inner classes to anonymous inner classes

	private class RefreshPlotListener implements CrassusPlotIsObsoleteListener {
		
		@Override
		public void informPlotIsObsolete() {
			plotPane.refresh();
		}
	}

	private class CompoundChangeStockListener implements CrassusChangeStockListener {
		
		@Override 
		public void changeToStock(Stock stock) {
			plotPane.changeToStock(stock);
			eventBox.changeToStock(stock);
		}
	}

	private JFrame frame;
	
	private CrassusPlotPane plotPane;
	
	private CrassusStockTablePane stockBox;
	private CrassusIndicatorTablePane eventBox;
	
	private JPanel stockInfo;
	
	private DotCrassusFileGui fileGui;
	
	private UndoableStack undoables;

	public CrassusGUI() {
		frame = new JFrame("Crassus");
		
		undoables = new UndoableStack(32);
		
		fileGui = new DotCrassusFileGui(frame,this);
		
		
		JMenuBar menuBar = new JMenuBar();
		
		//ADDING FILE MENU
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F1);
		menuBar.add(fileMenu);
		
		JMenuItem mNew = new JMenuItem("New");
		mNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		mNew.addActionListener(new fileNewListener());
		fileMenu.add(mNew);

		JMenuItem mOpen = new JMenuItem("Open");
		mOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		mOpen.addActionListener(new FileOpenListener());
		fileMenu.add(mOpen);
		
		JMenuItem mSave = new JMenuItem("Save");
		mSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		mSave.addActionListener(new FileSaveListener());
		fileMenu.add(mSave);
		
		JMenuItem mSaveAs = new JMenuItem("Save As");
		mSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK|ActionEvent.ALT_MASK));
		mSaveAs.addActionListener(new FileSaveAsListener());
		fileMenu.add(mSaveAs);
		
		fileMenu.addSeparator();
		
		JMenuItem mExit = new JMenuItem("Exit");
		mExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0));
		mExit.addActionListener(new FileExitListener());
		fileMenu.add(mExit);
		
		//ADDING EDIT MENU
		
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_F2);
		menuBar.add(editMenu);
		
		JMenuItem mUndo = new JMenuItem("Undo");
		mUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));
		mUndo.addActionListener(new EditUndoListener());
		editMenu.add(mUndo);
		
		JMenuItem mRedo = new JMenuItem("Redo");
		mRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK));
		mRedo.addActionListener(new EditRedoListner());
		editMenu.add(mRedo);
		
		//ADDING TICKER MENU
		
		JMenu tickerMenu = new JMenu("Tickers");
		tickerMenu.setMnemonic(KeyEvent.VK_F3);
		menuBar.add(tickerMenu);
		
		JMenuItem mAddTicker = new JMenuItem("Add New Ticker");
		mAddTicker.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_DOWN_MASK));
		mAddTicker.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent e) {stockBox.showNewTickerDialog();}
				});
		tickerMenu.add(mAddTicker);
		
		JMenuItem mRemoveTicker = new JMenuItem("Remove Selected Ticker");
		mRemoveTicker.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK));
		//mRemoveTicker.addActionListener(new TickerRemoveTickerListener());
		mRemoveTicker.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent e) {stockBox.removeSelectedTicker();}
				});
		tickerMenu.add(mRemoveTicker);
		
		//TODO sorting stuff, possibly selection stuff (not sure)
		
		//ADDING PLOT MENU
		
		JMenu plotMenu = new JMenu("Plot");
		plotMenu.setMnemonic(KeyEvent.VK_F4);
		menuBar.add(plotMenu);
		
		JMenuItem mRefresh = new JMenuItem("Refresh");
		mRefresh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_DOWN_MASK));
		mRefresh.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent e) {plotPane.refresh();}
				});
		plotMenu.add(mRefresh);
		
		plotMenu.addSeparator();
		
		JMenuItem mSetTimeScaleOneDay = new JMenuItem("Plot One Day");
		//TODO action listener
		plotMenu.add(mSetTimeScaleOneDay);
		
		JMenuItem mSetTimeScaleOneWeek = new JMenuItem("Plot One Week");
		//TODO action listener
		plotMenu.add(mSetTimeScaleOneWeek);
		
		JMenuItem mSetTimeScaleOneMonth = new JMenuItem("Plot One Month");
		//TODO action listener
		plotMenu.add(mSetTimeScaleOneMonth);
		
		//TODO etc......
		
		//TODO same for time frequencies.....
		
		//ADDING INDICATOR MENU
		
		JMenu indicatorMenu = new JMenu("Indicators");
		indicatorMenu.setMnemonic(KeyEvent.VK_5);
		menuBar.add(indicatorMenu);
		
		JMenuItem mAddIndicator = new JMenuItem("Add New Indicator");
		mAddIndicator.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_DOWN_MASK));
		mAddIndicator.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent arg0) {eventBox.showNewIndicatorDialog();}
				});
		indicatorMenu.add(mAddIndicator);
		
		JMenuItem mRemoveIndicator = new JMenuItem("Remove Selected Indicator");
		mRemoveIndicator.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK));
		mAddIndicator.addActionListener(
				new ActionListener(){@Override
					public void actionPerformed(ActionEvent e) {eventBox.removeSelectedIndicator();}
				});
		indicatorMenu.add(mRemoveIndicator);
		
		//TODO sorting for indicators
		
		frame.setJMenuBar(menuBar);
		
		//DONE SETTING UP THE MENU BAR!
		
		try {
			BufferedImage img = ImageIO.read(new File("img/crassus.png"));
			frame.setIconImage(img);
		} catch (IOException e) {}//not a disaster, can be ignored....
		
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		stockBox = new CrassusStockTablePane(frame,undoables);
		stockBox.setChangeStockListener(new CompoundChangeStockListener());

		eventBox = new CrassusIndicatorTablePane(frame,undoables, new RefreshPlotListener());
		
		//make plot pane
		plotPane = new CrassusPlotPane(undoables);
		
		
		stockInfo = new JPanel();
		stockInfo.setBackground(Color.WHITE);
		stockInfo.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(20,20,20,20),
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		stockInfo.setLayout(new BorderLayout());
		stockInfo.add(eventBox, BorderLayout.EAST);
		stockInfo.add(plotPane,BorderLayout.CENTER);
		
		frame.add(stockBox, BorderLayout.WEST);
		frame.add(stockInfo,BorderLayout.CENTER);
		frame.setMinimumSize(new Dimension(1080,500));
	}
	

	public void changeStockListTo(StockList stocks) {

		stockBox.changeStockListTo(stocks);

		if(stocks.getStockList().isEmpty()){
			plotPane.changeToStock(null);
			eventBox.changeToStock(null);
		}
		else{
			plotPane.changeToStock(stocks.getStockList().get(0));
			eventBox.changeToStock(stocks.getStockList().get(0));
		}
		undoables.clear();
	}


	@Override
	public void launch() {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		String[] options = {"New File","Open existing File"};
		int fileBehavior = JOptionPane.showOptionDialog(frame, 
				"Would you like to start a new file or open an existing one?","Crassus",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
				options, options[0]);
		
		StockList stocks;
		if(fileBehavior == 0)
			stocks = fileGui.fileNew();
		else
			stocks = fileGui.fileOpen();
		changeStockListTo(stocks);
	}

	@Override
	public void update() {
		stockBox.refresh();
		plotPane.refresh();
	}

}
