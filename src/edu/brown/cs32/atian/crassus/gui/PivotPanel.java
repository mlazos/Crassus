package edu.brown.cs32.atian.crassus.gui;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import edu.brown.cs32.atian.crassus.backend.BollingerBands;
import edu.brown.cs32.atian.crassus.backend.Indicator;
import edu.brown.cs32.atian.crassus.backend.PivotPoints;
import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockFreqType;

public class PivotPanel extends JPanel {

	private WindowCloseListener closeListener;
	private Stock stock;
	private JDialog parent;
	private JRadioButton stan;
	private JRadioButton fibo;
	private JRadioButton demark;
	
	
	public PivotPanel(WindowCloseListener closeListener, JDialog parent, Stock stock)
	{
		this.closeListener = closeListener;
		this.parent = parent;
		this.stock = stock;
	
		
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
		
		stan.setSelected(true);

		
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
			String currentButton = "standard";
			
			if(stan.isSelected())
			{
				currentButton = "standard";
			}
			else if(fibo.isSelected())
			{
				currentButton = "fibonacci";
			}
			else if(demark.isSelected())
			{
				currentButton = "demark";
			}
			else
			{
				showErrorDialog("Please make a selection.");
			}
			
			
			try
			{
				Indicator ind = new PivotPoints(stock.getStockPriceData(StockFreqType.DAILY), currentButton);
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
			
		
		
		
	
	
	class TestListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

	public String toString()
	{
		return "Pivot Point Event";
	}
}
