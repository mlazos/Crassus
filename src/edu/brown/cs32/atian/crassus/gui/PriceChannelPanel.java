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

import edu.brown.cs32.atian.crassus.backend.Stock;

public class PriceChannelPanel extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WindowCloseListener closeListener;
	private Stock stock;
	private JDialog parent;
	private JTextField lookBack;
	
	public PriceChannelPanel(WindowCloseListener closeListener, JDialog parent, Stock stock)
	{
		this.closeListener = closeListener;
		this.parent = parent;
		this.stock = stock;
		
		NumberVerifier inputValidator = new NumberVerifier(this);
		//top panel
		JLabel lookBackLabel = new JLabel("Look Back Period:");
		
		lookBack = new JTextField();
		lookBack.setInputVerifier(inputValidator);
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
		return "Price Channel Event";
	}

}
