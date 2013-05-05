package edu.brown.cs32.atian.crassus.gui.indicatorwindows;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockFreqType;
import edu.brown.cs32.atian.crassus.gui.WindowCloseListener;
import edu.brown.cs32.atian.crassus.indicators.Indicator;
import edu.brown.cs32.atian.crassus.indicators.PivotPoints;

public class PivotPanel extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WindowCloseListener closeListener;
	private Stock stock;
	private JDialog parent;
	private JRadioButton stan;
	private JRadioButton fibo;
	private JRadioButton dem;
	private String stantt = "<html>Standard begins with a base pivot calcuated <br> from the simple average of the previous period's high, low and close.</html>";
	private String fibott = "<html>Fibonacci begins with a base pivot calculated <br> from the simple average of the previous period's high, low and close, <br> but fibonacci multiples of the high-low differential are added to <br> calculate resistance levels and subtracted to form support levels.</html>";
	private String demtt = "<html>Demark uses a different base pivot depending on the <br> relationship between the previous period's high and low.</html>";
	
	
	public PivotPanel(WindowCloseListener closeListener, JDialog parent, Stock stock)
	{
		this.closeListener = closeListener;
		this.parent = parent;
		this.stock = stock;
		
		//top panel
		ButtonGroup radioButtons = new ButtonGroup();
		
		JPanel standard = new JPanel();
		standard.setLayout(new FlowLayout());
		stan = new JRadioButton("Standard");
		stan.setToolTipText(stantt);
		radioButtons.add(stan);
		standard.add(stan);
		
		JPanel fib = new JPanel();
		fib.setLayout(new FlowLayout());
		fibo = new JRadioButton("Fibonacci");
		fibo.setToolTipText(fibott);
		radioButtons.add(fibo);
		fib.add(fibo);
		
		JPanel demark = new JPanel();
		demark.setLayout(new FlowLayout());
		dem = new JRadioButton("Demark");
		dem.setToolTipText(demtt);
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
			else if(dem.isSelected())
			{
				currentButton = "demark";
			}
			else
			{
				showErrorDialog("Please make a selection.");
			}
			
			
			try
			{
				Indicator ind = new PivotPoints(stock.getStockPriceData(StockFreqType.DAILY), currentButton, 
						stock.getStartTime(), stock.getEndTime());
				closeListener.windowClosedWithEvent(ind);
				parent.dispose();
				
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
