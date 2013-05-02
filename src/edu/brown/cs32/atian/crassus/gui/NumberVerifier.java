package edu.brown.cs32.atian.crassus.gui;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NumberVerifier extends InputVerifier {

	private JComponent parent;
	
	public NumberVerifier(JComponent parent)
	{
		this.parent = parent;
	}
	
	@Override
	public boolean verify(JComponent input) 
	{
		JTextField f = (JTextField)input;
		String fieldText = f.getText();
		boolean valid = isParsable(fieldText);
		
		if(!valid)
		{
			JOptionPane.showMessageDialog(parent, "Inputs must be positive whole numbers.", "Oops!", JOptionPane.ERROR_MESSAGE);
			f.setText("");
		}

		
		return valid;
	}
	
	private boolean isParsable(String s)
	{
		final String Digits     = "(\\p{Digit}+)";        
    	return Pattern.matches(Digits, s);
	}

}
