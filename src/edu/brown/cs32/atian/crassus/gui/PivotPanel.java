package edu.brown.cs32.atian.crassus.gui;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PivotPanel extends JPanel {

	public PivotPanel()
	{
		//top panel
		ButtonGroup radioButtons = new ButtonGroup();
		
		JPanel standard = new JPanel();
		standard.setLayout(new FlowLayout());
		JRadioButton stan = new JRadioButton("Standard");
		radioButtons.add(stan);
		standard.add(stan);
		
		JPanel fib = new JPanel();
		fib.setLayout(new FlowLayout());
		JRadioButton fibo = new JRadioButton("Fibonacci");
		radioButtons.add(fibo);
		fib.add(fibo);
		
		JPanel demark = new JPanel();
		demark.setLayout(new FlowLayout());
		JRadioButton dem = new JRadioButton("Demark");
		radioButtons.add(dem);
		demark.add(dem);
		
		JPanel parameters = new JPanel();
		parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
		parameters.add(standard);
		parameters.add(fib);
		parameters.add(demark);
		
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
