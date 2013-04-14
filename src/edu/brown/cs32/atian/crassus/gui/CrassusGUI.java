/**
 * 
 */
package edu.brown.cs32.atian.crassus.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * @author Matthew
 *
 */
public class CrassusGUI implements GUI {
	
	/*
	 * for testing purposes only
	 */
	public static void main(String[] args){
		UIManager.getDefaults().put("Button.background",new Color(0xFFFFFF));//make JButtons less ugly
		GUI gui = new CrassusGUI();
		gui.launch();
	}
	
	private JFrame frame;
	
	private JScrollPane stockListScrollPane;
	
	private JScrollPane eventListScrollPane;
	
	private CrassusPlotPane plotPane;

	public CrassusGUI() {
		
		frame = new JFrame("Crassus");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CrassusStockTablePane stockBox = new CrassusStockTablePane();

		CrassusEventTablePane eventBox = new CrassusEventTablePane();
		
		//frame.add(eventBox, BorderLayout.EAST);
		
		//make plot pane
		plotPane = new CrassusPlotPane();
		
		
		JPanel stockInfo = new JPanel();
		stockInfo.setBackground(Color.WHITE);
		stockInfo.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(20,20,20,20),
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		stockInfo.setLayout(new BorderLayout());
		stockInfo.add(eventBox, BorderLayout.EAST);
		stockInfo.add(plotPane,BorderLayout.CENTER);

		frame.add(stockBox, BorderLayout.WEST);
		frame.add(stockInfo,BorderLayout.CENTER);
		frame.setMinimumSize(new Dimension(700,400));
	}
	
	/* (non-Javadoc)
	 * @see edu.brown.cs32.atian.crassus.gui.GUI#launch()
	 */
	@Override
	public void launch() {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
