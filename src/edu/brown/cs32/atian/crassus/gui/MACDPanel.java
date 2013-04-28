package edu.brown.cs32.atian.crassus.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MACDPanel extends JPanel {

	public MACDPanel()
	{
		//top panel
		JLabel signalLabel = new JLabel("Signal Period:");
		JLabel shortLabel = new JLabel("Shorter Period:");
		JLabel longLabel = new JLabel("Longer Period:");
		
		JTextField signalP = new JTextField();
		signalP.setSize(50, 20);
		signalP.setPreferredSize(new Dimension(50, 20));
		
		JTextField shortP = new JTextField();
		shortP.setSize(50, 20);
		shortP.setPreferredSize(new Dimension(50,20));
		
		JTextField LongP = new JTextField();
		LongP.setSize(50, 20);
		LongP.setPreferredSize(new Dimension(50,20));
		
		JPanel signalInput = new JPanel();
		signalInput.setLayout(new FlowLayout());
		signalInput.add(signalLabel);
		signalInput.add(signalP);
		
		JPanel shorterInput = new JPanel();
		shorterInput.setLayout(new FlowLayout());
		shorterInput.add(shortLabel);
		shorterInput.add(shortP);
		
		JPanel longerInput = new JPanel();
		longerInput.setLayout(new FlowLayout());
		longerInput.add(longLabel);
		longerInput.add(LongP);
		
		JPanel parameters = new JPanel();
		parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
		parameters.add(signalInput);
		parameters.add(shorterInput);
		parameters.add(longerInput);
		
		//middle panel
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		JButton ok = new JButton("Ok");
		JButton test = new JButton("Test");
		JButton cancel = new JButton("Cancel");
		buttons.add(ok);
		buttons.add(test);
		buttons.add(cancel);
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(parameters);
		this.add(buttons);

		
	}
	/*
	class OkListener implements ActionListener
	{
		
	}
	
	class TestListener implements ActionListener
	{
		
	}
	*/

}
