package edu.brown.cs32.atian.crassus.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StochOscillPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 12154087L;

	public StochOscillPanel()
	{
		//top panel
		JLabel periodLabel = new JLabel("Period:");
		
		JTextField period = new JTextField();
		period.setInputVerifier(new NumberVerifier(periodLabel));
		period.setSize(50, 20);
		period.setPreferredSize(new Dimension(50, 20));
		
		JPanel periodInput = new JPanel();
		periodInput.setLayout(new FlowLayout());
		periodInput.add(periodLabel);
		periodInput.add(period);
		
		JPanel parameters = new JPanel();
		parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
		parameters.add(periodInput);
		
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

	public String toString()
	{
		return "Stochastic Oscillator Event";
	}

}
