/**
 * 
 */
package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockList;

/**
 * @author Matthew
 *
 */
public class CrassusGUI implements GUI {
	
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

	public CrassusGUI(StockList stocks) {
		
		frame = new JFrame("Crassus");
		
		try {
			BufferedImage img = ImageIO.read(new File("icons/programIcon.png"));
			frame.setIconImage(img);
		} catch (IOException e) {}//not a disaster, can be ignored....
		
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		stockBox = new CrassusStockTablePane(frame,stocks);
		stockBox.setChangeStockListener(new CompoundChangeStockListener());

		eventBox = new CrassusIndicatorTablePane(frame);
		
		//make plot pane
		plotPane = new CrassusPlotPane();
		
		
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
