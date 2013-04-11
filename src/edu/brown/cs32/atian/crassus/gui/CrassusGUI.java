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
		GUI gui = new CrassusGUI();
		gui.launch();
	}
	
	private JFrame frame;
	
	private JScrollPane stockListScrollPane;
	
	private JScrollPane eventListScrollPane;
	
	private JComponent plotView;

	public CrassusGUI() {
		
		frame = new JFrame("Crassus");
		frame.getContentPane().setBackground(Color.WHITE);
	
		//MAKE LIST OF STOCKS
		String[] strings = {"one","two","three","four","five"};
		JList list = new JList(strings);
		//list.setModel(new DefaultListModel());
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		stockListScrollPane = new JScrollPane(list);
		stockListScrollPane.setBackground(Color.WHITE);
		stockListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		stockListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		stockListScrollPane.setPreferredSize(new Dimension(180,250));
		
		//CREATE AND USE BORDER FOR STOCK LIST
		{
			//Border line = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			Border margin = BorderFactory.createEmptyBorder(5,5,5,5);
			//Border stockBorder = BorderFactory.createCompoundBorder(margin,line);
			//stockListScrollPane.setBorder(stockBorder);
			stockListScrollPane.setBorder(margin);
		}
		
		JLabel stockLabel = new JLabel("Tickers", JLabel.CENTER);
		stockLabel.setBackground(Color.WHITE);
		
		JPanel stockBox = new JPanel();
		stockBox.setBackground(Color.WHITE);
		stockBox.setLayout(new BorderLayout());
		stockBox.add(stockLabel,BorderLayout.NORTH);
		stockBox.add(stockListScrollPane,BorderLayout.CENTER);
		

		//CREATE AND USE BORDER FOR STOCK BOX
		{
			Border line = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			Border margin = BorderFactory.createEmptyBorder(10,10,50,10);
			Border stockBorder = BorderFactory.createCompoundBorder(margin,line);
			stockBox.setBorder(stockBorder);
		}
		
		
		frame.add(stockBox, BorderLayout.WEST);
		
		//MAKE LIST OF EVENTS
		String[] strings2 = {"alpha","beta","gamma"};
		JList list2 = new JList(strings2);
		//list2.setModel(new DefaultListModel());
		list2.setLayoutOrientation(JList.VERTICAL);
		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		eventListScrollPane = new JScrollPane(list2);
		eventListScrollPane.setBackground(Color.WHITE);
		eventListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		eventListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		eventListScrollPane.setPreferredSize(new Dimension(180,250));
		
		//CREATE AND USE BORDER FOR EVENT
		{
			//Border blackLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
			//Border eventBorder = BorderFactory.createCompoundBorder(margin,blackLine);
			//eventListScrollPane.setBorder(eventBorder);
			eventListScrollPane.setBorder(margin);
		}

		JLabel eventLabel = new JLabel("Events",JLabel.CENTER);
		eventLabel.setBackground(Color.WHITE);
		
		JPanel eventBox = new JPanel();
		eventBox.setBackground(Color.WHITE);
		eventBox.setLayout(new BorderLayout());
		eventBox.add(eventLabel,BorderLayout.NORTH);
		eventBox.add(eventListScrollPane,BorderLayout.CENTER);

		//CREATE AND USE BORDER FOR EVENT BOX
		{
			Border line = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			Border margin = BorderFactory.createEmptyBorder(10,10,50,10);
			Border stockBorder = BorderFactory.createCompoundBorder(margin,line);
			eventBox.setBorder(stockBorder);
		}
		
		
		frame.add(eventBox, BorderLayout.EAST);
		
		//MAKE PLOT (WITH FAKE IMAGE)
		//TODO replace fake image with actual data
		BufferedImage defaultImage = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);
		plotView = new JLabel(new ImageIcon(defaultImage));
		
		//CREATE AND USE BORDER FOR STOCK
		{
			Border margin = BorderFactory.createEmptyBorder(30,30,30,30);
			plotView.setBorder(margin);
		}
		
		frame.add(plotView,BorderLayout.CENTER);

			
		//TODO initialize Swing components
		
		//frame.add(plotView, BorderLayout.CENTER);
		
	}
	
	/* (non-Javadoc)
	 * @see edu.brown.cs32.atian.crassus.gui.GUI#launch()
	 */
	@Override
	public void launch() {
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
