package edu.brown.cs32.atian.crassus.gui.indicatorwindows;

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
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;

import edu.brown.cs32.atian.crassus.gui.WindowCloseListener;
import edu.brown.cs32.atian.crassus.indicators.Indicator;
import edu.brown.cs32.atian.crassus.indicators.RSI;
import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockFreqType;

public class RSIPanel extends JPanel 
{

	private static final long serialVersionUID = 6684347084049152488L;
	private WindowCloseListener closeListener;
	private Stock stock;
	private JDialog parent;
	private JTextField period;
	private String periodtt = "<html>The number of days to use when calculating the RSI.</html>";

	public RSIPanel(WindowCloseListener closeListener, JDialog parent, Stock stock)
	{
		
		this.closeListener = closeListener;
		this.parent = parent;
		this.stock = stock;
		
		final String undo = "undo";
		final String redo = "redo";
		final KeyStroke undoKey = KeyStroke.getKeyStroke("control Z");
		final KeyStroke redoKey = KeyStroke.getKeyStroke("control Y");
		final UndoManager undoM = new UndoManager();
		
		NumberVerifier inputValidator = new NumberVerifier(this);
		//top panel
		JLabel periodLabel = new JLabel("Period:");
		periodLabel.setToolTipText(periodtt);
		
		period = new CrassusTextField("14", periodtt, inputValidator, undoM);
		
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
	
	class OkListener extends AbstractOkListener
	{

		public OkListener() 
		{
			super(parent);
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String periodArg = period.getText();
			
			if(periodArg == null)
			{
				showErrorDialog("You must enter a value.");
			}
			else
			{
				try
				{
					Indicator ind = new RSI(stock.getStockPriceData(StockFreqType.DAILY), Integer.parseInt(periodArg),
							stock.getStartTime());
					parent.dispose();
					closeListener.windowClosedWithEvent(ind);
				}
				catch(NumberFormatException nfe)
				{
					showErrorDialog();
				}
				catch(IllegalArgumentException iae)
				{
					showErrorDialog(iae.getMessage());
				}
			}
			
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
