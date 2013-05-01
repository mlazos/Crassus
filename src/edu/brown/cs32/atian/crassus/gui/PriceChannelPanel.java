package edu.brown.cs32.atian.crassus.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PriceChannelPanel extends JPanel 
{

	public PriceChannelPanel()
	{
		//top panel
		JLabel lookBackLabel = new JLabel("Look Back Period:");
		
		JTextField lookBack = new JTextField();
		lookBack.setInputVerifier(new NumberVerifier(lookBackLabel));
		lookBack.setSize(50, 20);
		lookBack.setPreferredSize(new Dimension(50, 20));
		
		JPanel lookBackInput = new JPanel();
		lookBackInput.setLayout(new FlowLayout());
		lookBackInput.add(lookBackLabel);
		lookBackInput.add(lookBack);
		
		JPanel parameters = new JPanel();
		parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
		parameters.add(lookBackInput);
		
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
	
	public String toString()
	{
		return "Price Channel Event";
	}

}
