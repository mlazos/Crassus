/**
 * 
 */
package edu.brown.cs32.atian.crassus.gui.mainwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
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
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockList;
import edu.brown.cs32.atian.crassus.gui.mainwindow.table.indicator.CrassusIndicatorTablePane;
import edu.brown.cs32.atian.crassus.gui.mainwindow.table.stock.CrassusStockTablePane;
import edu.brown.cs32.atian.crassus.gui.undoable.UndoableStack;

/**
 * @author Matthew
 *
 */
public class CrassusGUI implements GUI {
	
	@SuppressWarnings("serial")
	public class CtrlZAction extends AbstractAction implements Action {
		@Override public void actionPerformed(ActionEvent e) {
			undoables.undo();
		}
	}

	@SuppressWarnings("serial")
	public class CtrlYAction extends AbstractAction implements Action {
		@Override public void actionPerformed(ActionEvent e) {
			undoables.redo();
		}
	}

	public class CompoundChangeStockListener implements CrassusChangeStockListener {
		@Override public void changeToStock(Stock stock) {
			if(stock==null && stockInfoStateNormal){
				frame.remove(stockInfo);
				frame.add(nullStockInfo,BorderLayout.CENTER);
				stockInfoStateNormal = false;
				frame.revalidate();
				frame.setVisible(true);
				frame.repaint();
			}
			else if(!stockInfoStateNormal){
				frame.remove(nullStockInfo);
				frame.add(stockInfo,BorderLayout.CENTER);
				stockInfoStateNormal = true;
				frame.revalidate();
				frame.setVisible(true);
				frame.repaint();
			}
			
			plotPane.changeToStock(stock);
			eventBox.changeToStock(stock);
			
			//frame.getContentPane().revalidate();
			//frame.repaint();
		}
	}

	private JFrame frame;
	
	private CrassusPlotPane plotPane;
	
	private CrassusStockTablePane stockBox;
	private CrassusIndicatorTablePane eventBox;
	
	private JPanel stockInfo;
	private JPanel nullStockInfo;
	private boolean stockInfoStateNormal = false;
	
	UndoableStack undoables;

	public CrassusGUI(StockList stocks) {
		
		frame = new JFrame("Crassus");
		
		undoables = new UndoableStack(32);

		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK),
				"CTRL Z");
		frame.getRootPane().getActionMap().put("CTRL Z", new CtrlZAction());
		
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK),
				"CTRL Y");
		frame.getRootPane().getActionMap().put("CTRL Y", new CtrlYAction());
		
		try {
			BufferedImage img = ImageIO.read(new File("icons/programIcon.png"));
			frame.setIconImage(img);
		} catch (IOException e) {}//not a disaster, can be ignored....
		
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		stockBox = new CrassusStockTablePane(frame,stocks,undoables);
		stockBox.setChangeStockListener(new CompoundChangeStockListener());

		eventBox = new CrassusIndicatorTablePane(frame);
		
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

		nullStockInfo = new JPanel();
		nullStockInfo.setBackground(Color.WHITE);
		nullStockInfo.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(20,20,20,20),
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		nullStockInfo.setLayout(new BorderLayout());
		JPanel greyPanel = new JPanel();
		greyPanel.setBackground(new Color(210,210,210));
		nullStockInfo.add(greyPanel, BorderLayout.CENTER);
		
		frame.add(stockBox, BorderLayout.WEST);
		frame.add(nullStockInfo,BorderLayout.CENTER);
		frame.setMinimumSize(new Dimension(1050,500));
	}
	
	@Override
	public void launch() {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	@Override
	public void update() {
		stockBox.refresh();
		plotPane.refresh();
	}

}
