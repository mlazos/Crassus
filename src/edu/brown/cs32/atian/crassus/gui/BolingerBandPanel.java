package edu.brown.cs32.atian.crassus.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BolingerBandPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BolingerBandPanel()
	{
		//top panel
		JLabel periodsLabel = new JLabel("Number of Periods:");
		JLabel bandWidthLabel = new JLabel("Bandwidth:");
		JTextField periods = new JTextField();
		periods.setSize(50, 20);
		periods.setPreferredSize(new Dimension(50, 20));
		JTextField bandWidth = new JTextField();
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
		return "Bolinger Band Event";
	}
}
