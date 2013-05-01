package edu.brown.cs32.atian.crassus.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BolingerBandPanel extends JPanel
{

	/**
	 * This is the panel that is displayed for Bolinger Bands Events
	 */
	private static final long serialVersionUID = 1L;
	
	private WindowCloseListener closeListener;
	private JDialog parent;
	JTextField periods;
	JTextField bandWidth;
	
	
	public BolingerBandPanel(WindowCloseListener closeListener, JDialog parent)
	{
		this.closeListener = closeListener;
		this.parent = parent;
		
		NumberVerifier inputValidator = new NumberVerifier(this);
		//top panel
		JLabel periodsLabel = new JLabel("Number of Periods:");
		JLabel bandWidthLabel = new JLabel("Bandwidth:");
		periods = new JTextField();
		periods.setInputVerifier(inputValidator);
		periods.setSize(50, 20);
		periods.setPreferredSize(new Dimension(50, 20));
		bandWidth = new JTextField();
		bandWidth.setInputVerifier(inputValidator);
		bandWidth.setSize(50, 20);
		bandWidth.setPreferredSize(new Dimension(50,20));
		
		JPanel periodsInput = new JPanel();
		periodsInput.setLayout(new FlowLayout());
		periodsInput.add(periodsLabel);
		periodsInput.add(periods);
		
		JPanel bandWidthInput = new JPanel();
		bandWidthInput.setLayout(new FlowLayout());
		bandWidthInput.add(bandWidthLabel);
		bandWidthInput.add(bandWidth);
		
		JPanel parameters = new JPanel();
		parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
		parameters.add(periodsInput);
		parameters.add(bandWidthInput);
		
		//middle panel
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		JButton ok = new JButton("Ok");
		ok.addActionListener(new OkListener());
		JButton test = new JButton("Test");
		test.addActionListener(new TestListener());
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelListener());
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
		return "Bolinger Band Event";
	}
}
