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
			JOptionPane.showMessageDialog(parent, "Input must be a number.", "Oops!", JOptionPane.ERROR_MESSAGE);
			f.setText("");
		}

		
		return valid;
	}
	
	private boolean isParsable(String s)
	{
		
		final String Digits     = "(\\p{Digit}+)";
    	final String HexDigits  = "(\\p{XDigit}+)";
    	// an exponent is 'e' or 'E' followed by an optionally 
    	// signed decimal integer.
    	final String Exp        = "[eE][+-]?"+Digits;
    	final String fpRegex    =
        ("[\\x00-\\x20]*"+  // Optional leading "whitespace"
         "[+-]?(" + // Optional sign character
         "NaN|" +           // "NaN" string
         "Infinity|" +      // "Infinity" string

         // A decimal floating-point string representing a finite positive
         // number without a leading sign has at most five basic pieces:
         // Digits . Digits ExponentPart FloatTypeSuffix
         // 
         // Since this method allows integer-only strings as input
         // in addition to strings of floating-point literals, the
         // two sub-patterns below are simplifications of the grammar
         // productions from the Java Language Specification, 2nd 
         // edition, section 3.10.2.

         // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
         "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

         // . Digits ExponentPart_opt FloatTypeSuffix_opt
         "(\\.("+Digits+")("+Exp+")?)|"+

   		// Hexadecimal strings
   		"((" +
   		// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
   		"(0[xX]" + HexDigits + "(\\.)?)|" +

    	// 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
    	"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

    	")[pP][+-]?" + Digits + "))" +
         "[fFdD]?))" +
         "[\\x00-\\x20]*");// Optional trailing "whitespace"
        
    	return Pattern.matches(fpRegex, s);
	}

}
