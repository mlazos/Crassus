package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class EventWindowFrame implements EventWindow {

	private JFrame frame;
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		EventWindowFrame w = new EventWindowFrame();
		w.display();
	}
	
	public void display() 
	{
		frame.pack();
		frame.setVisible(true);
	}

	public EventWindowFrame()
	{
		//add dropdown to main Frame
		JPanel[] eventList = {new BolingerBandPanel(), new MACDPanel(), new PivotPanel(), new PriceChannelPanel(), new RSIPanel(), new StochOscillPanel()};
		JComboBox<JPanel> selectEvent = new JComboBox<JPanel>(eventList);
		selectEvent.setSelectedIndex(0); 
		//selectEvent.setPreferredSize(new Dimension(200,20));
		JPanel dropDownPanel = new JPanel();
		//dropDownPanel.setMinimumSize(new Dimension(200,20));
		//dropDownPanel.setMaximumSize(new Dimension(200, 20));
		dropDownPanel.add(selectEvent);
		dropDownPanel.setBorder(new EmptyBorder(20,20,20,20));
		
		frame = new JFrame("Bolinger Band Event");
		frame.setResizable(false);
		frame.setSize(500, 450);
		frame.setMinimumSize(new Dimension(500, 450));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout(new BorderLayout());
		frame.add(dropDownPanel, BorderLayout.NORTH);
		frame.add(new BolingerBandPanel(), BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void setWindowCloseListener(WindowCloseListener listener) 
	{
		
	}

}
