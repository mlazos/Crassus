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
	
	public class FileOpenListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			StockList stocks = fileGui.fileOpen();
			
			if(stocks!=null){
				stockBox.changeStockListTo(stocks);
				
				if(stocks.getStockList().isEmpty()){
					plotPane.changeToStock(null);
					eventBox.changeToStock(null);
				}
				else{
					plotPane.changeToStock(stocks.getStockList().get(0));
					eventBox.changeToStock(stocks.getStockList().get(0));
				}
			}
			undoables.clear();
		}
	}

	public class FileSaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			fileGui.fileSave();
		}
	}

	public class FileSaveAsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			fileGui.fileSaveAs();
		}
	}

	public class RefreshPlotListener implements CrassusPlotIsObsoleteListener {
		
		@Override
		public void informPlotIsObsolete() {
			plotPane.refresh();
		}
	}

	@SuppressWarnings("serial")
	public class CtrlZAction extends AbstractAction implements Action {
		
		@Override 
		public void actionPerformed(ActionEvent e) {
			undoables.undo();
		}
	}

	@SuppressWarnings("serial")
	public class CtrlYAction extends AbstractAction implements Action {
		
		@Override 
		public void actionPerformed(ActionEvent e) {
			undoables.redo();
		}
	}

	public class CompoundChangeStockListener implements CrassusChangeStockListener {
		
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
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F1);
		menuBar.add(fileMenu);
		
		JMenuItem mNew = new JMenuItem("New");
		mNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		//FILL THIS IN//mNew.addActionListener();
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
		
		frame.setJMenuBar(menuBar);

		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK),
				"CTRL Z");
		frame.getRootPane().getActionMap().put("CTRL Z", new CtrlZAction());
		
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK),
				"CTRL Y");
		frame.getRootPane().getActionMap().put("CTRL Y", new CtrlYAction());
		
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
	}

	@Override
	public void update() {
		stockBox.refresh();
		plotPane.refresh();
	}

}
