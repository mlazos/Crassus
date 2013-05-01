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

public class MACDPanel extends JPanel 
{
	private WindowCloseListener closeListener;
	private JDialog parent;
	private JTextField signalP;
	private JTextField shortP;
	private JTextField longP;
	
	public MACDPanel(WindowCloseListener closeListener, JDialog parent)
	{
		this.closeListener = closeListener;
		this.parent = parent;
		
		NumberVerifier inputValidator = new NumberVerifier(this);
		
		//top panel
		JLabel signalLabel = new JLabel("Signal Period:");
		JLabel shortLabel = new JLabel("Shorter Period:");
		JLabel longLabel = new JLabel("Longer Period:");
		
		signalP = new JTextField();
		signalP.setInputVerifier(inputValidator);
		signalP.setSize(50, 20);
		signalP.setPreferredSize(new Dimension(50, 20));
		
		shortP = new JTextField();
		shortP.setInputVerifier(inputValidator);
		shortP.setSize(50, 20);
		shortP.setPreferredSize(new Dimension(50,20));
		
		longP = new JTextField();
		longP.setInputVerifier(inputValidator);
		longP.setSize(50, 20);
		longP.setPreferredSize(new Dimension(50,20));
		
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
		longerInput.add(longP);
		
		JPanel parameters = new JPanel();
		parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
		parameters.add(signalInput);
		parameters.add(shorterInput);
		parameters.add(longerInput);
		
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
		return "Moving Avg. Convergence Event";
	}
}
