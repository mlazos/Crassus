/**
 * 
 */
package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Matthew
 *
 */
@SuppressWarnings("serial")
public class TickerDialog extends JDialog {
	
	/**
	 * @author Matthew
	 *
	 */
	public class OkListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			if(listener!=null)
				listener.tickerDialogClosedWithTicker(text.getText());
			dispose();
		}
	}
	
	public class CancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	private JTextField text;
	private TickerDialogCloseListener listener;

	public TickerDialog(JFrame frame){
		super(frame,"Add Ticker");
		
		
		this.setLayout(new BorderLayout());
		
		text = new JTextField();
		text.setMinimumSize(new Dimension(50,15));
		text.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10,10,0,10),
				BorderFactory.createEtchedBorder()));
		text.addActionListener(new OkListener());
		this.add(text,BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(Color.WHITE);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new OkListener());
		buttonPanel.add(okButton);
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.addActionListener(new CancelListener());
		buttonPanel.add(cancelButton);

		this.add(buttonPanel,BorderLayout.SOUTH);
		
		this.setModal(true);
		
		this.pack();
		this.setResizable(false);
	}
	
	public void setTickerDialogCloseListener(TickerDialogCloseListener listener){
		this.listener = listener;
	}
}
