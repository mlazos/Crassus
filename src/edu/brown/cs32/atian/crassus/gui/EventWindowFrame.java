package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class EventWindowFrame implements EventWindow {

	private JPanel currentPanel;
	private JDialog frame;
	private WindowCloseListener closeListener;
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		JFrame p = new JFrame("this");
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EventWindowFrame w = new EventWindowFrame(p);
		w.display();
	}
	
	public void display() 
	{
		frame.pack();
		frame.setVisible(true);
	}

	public EventWindowFrame(JFrame parent)
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
		
		frame = new JDialog(parent,"Bolinger Band Event");
		frame.setResizable(false);
		frame.setSize(325, 450);
		frame.setMinimumSize(new Dimension(325, 450));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout(new BorderLayout());
		frame.add(dropDownPanel, BorderLayout.NORTH);
		frame.add(currentPanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
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
			frame.setTitle(newPanel.toString());
			frame.add(currentPanel, BorderLayout.CENTER);
			frame.setSize(330, 70 + panelDim.height);
			frame.setMinimumSize(new Dimension(330, 70 + panelDim.height));
			frame.pack();
			frame.setVisible(true);
			
		}
		
	}
	
	@Override
	public void setWindowCloseListener(WindowCloseListener listener) 
	{
		
	}
}
