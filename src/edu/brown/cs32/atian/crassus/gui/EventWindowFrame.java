package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class EventWindowFrame implements EventWindow {

	private JPanel currentPanel;
	
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
		currentPanel = new BolingerBandPanel();
		
		//add dropdown to main Frame
		JPanel[] eventList = {new BolingerBandPanel(), new MACDPanel(), new PivotPanel(), new PriceChannelPanel(), new RSIPanel(), new StochOscillPanel()};
		JComboBox<JPanel> selectEvent = new JComboBox<JPanel>(eventList);
		selectEvent.addActionListener(new WindowChanger());
		JPanel dropDownPanel = new JPanel();
		dropDownPanel.setMinimumSize(new Dimension(250,20));
		dropDownPanel.setMaximumSize(new Dimension(250,20));
		dropDownPanel.add(selectEvent);
		dropDownPanel.setBorder(new EmptyBorder(20,20,20,20));
		
		frame = new JFrame("Bolinger Band Event");
		frame.setResizable(false);
		frame.setSize(500, 450);
		frame.setMinimumSize(new Dimension(500, 450));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout(new BorderLayout());
		frame.add(dropDownPanel, BorderLayout.NORTH);
		frame.add(currentPanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		selectEvent.setSelectedIndex(0); 
	}
	
	class WindowChanger implements ActionListener
	{
		public WindowChanger(){}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			@SuppressWarnings("unchecked")
			JComboBox<JPanel> selectEvent = (JComboBox<JPanel>)e.getSource();
			JPanel newPanel = (JPanel)selectEvent.getSelectedItem();
			Dimension panelDim = newPanel.getSize();
			frame.remove(currentPanel);
			currentPanel = newPanel;
			frame.add(currentPanel, BorderLayout.CENTER);
			frame.setSize(500, 60 + panelDim.height);
			frame.setMinimumSize(new Dimension(500, 60 + panelDim.height));
			frame.pack();
			frame.setVisible(true);
			
		}
		
	}
	
	@Override
	public void setWindowCloseListener(WindowCloseListener listener) 
	{
		
	}

}
