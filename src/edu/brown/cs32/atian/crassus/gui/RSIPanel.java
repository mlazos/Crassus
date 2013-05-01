package edu.brown.cs32.atian.crassus.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RSIPanel extends JPanel 
{
	
	private WindowCloseListener closeListener;
	private JDialog parent;
	private JTextField period;

	public RSIPanel(WindowCloseListener closeListener, JDialog parent)
	{
		
		this.closeListener = closeListener;
		this.parent = parent;
		
		NumberVerifier inputValidator = new NumberVerifier(this);
		//top panel
		JLabel periodLabel = new JLabel("Period:");
		
		period = new JTextField();
		period.setInputVerifier(inputValidator);
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
		ok.addActionListener(new OkListener());
		JButton test = new JButton("Test");
		test.addActionListener(new TestListener());
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener(parent));
		buttons.add(ok);
		buttons.add(test);
		buttons.add(cancel);
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(parameters);
		this.add(buttons);
		
	}
	
	class OkListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
		}
		
	}
	
	class TestListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
		}
		
	}
	
	public String toString()
	{
		return "Relative Strength Index Event";
	}
}
