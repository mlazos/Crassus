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

import edu.brown.cs32.atian.crassus.indicators.Indicator;
import edu.brown.cs32.atian.crassus.indicators.MACD;
import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockFreqType;
import edu.brown.cs32.atian.crassus.gui.WindowCloseListener;

public class MACDPanel extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WindowCloseListener closeListener;
	private JDialog parent;
	private Stock stock;
	private JTextField signalP;
	private JTextField shortP;
	private JTextField longP;
	
	public MACDPanel(WindowCloseListener closeListener, JDialog parent, Stock stock)
	{
		this.closeListener = closeListener;
		this.parent = parent;
		this.stock = stock;
		
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
	
	class OkListener extends AbstractOkListener
	{

		public OkListener() 
		{
			super(parent);
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			String signalPArg = signalP.getText();
			String shortPArg = shortP.getText();
			String longPArg = longP.getText();
			
			if(signalPArg == null || shortPArg == null || longPArg == null)
			{
				showErrorDialog("You must enter values.");
			}
			else
			{
				try
				{
					Indicator ind = new MACD(stock.getStockPriceData(StockFreqType.DAILY), Integer.parseInt(signalPArg), Integer.parseInt(shortPArg), Integer.parseInt(longPArg));
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
		return "Moving Avg. Convergence Event";
	}
}
