package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class EventWindowFrame implements EventWindow {

	private JFrame frame;
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		EventWindowFrame w = new EventWindowFrame("Set Bolinger Band Event", new StochOscillPanel());
		w.display();
	}
	
	public void display() 
	{
		frame.pack();
		frame.setVisible(true);
	}

	public EventWindowFrame(String eventType, JPanel panel)
	{
		frame = new JFrame(eventType);
		frame.setResizable(false);
		frame.setSize(500, 450);
		frame.setMinimumSize(new Dimension(500, 450));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void setWindowCloseListener(WindowCloseListener listener) 
	{
		
	}

}
