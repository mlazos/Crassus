package edu.brown.cs32.atian.crassus.gui.indicatorwindows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockImpl;
import edu.brown.cs32.atian.crassus.gui.WindowCloseListener;

public class EventWindowFrame implements EventWindow {

	private JPanel currentPanel;
	private JDialog frame;
	
	public static void main(String[] args) 
	{
		JFrame p = new JFrame("this");
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EventWindowFrame w = new EventWindowFrame(p, new WindowCloseListenerStub(), new StockImpl("GOOG"));
		w.display();
	}
	
	public void display() 
	{
		frame.pack();
		frame.setVisible(true);
	}

	public EventWindowFrame(JFrame parent, WindowCloseListener closeListener, Stock stock)
	{
		frame = new JDialog(parent,"Bolinger Band Event");
		frame.setModal(true);
		frame.setResizable(false);
		
		
		currentPanel = new BolingerBandPanel(closeListener, frame, stock);
		
		//add dropdown to main Frame
		JPanel[] eventList = {new BolingerBandPanel(closeListener, frame, stock), 
							  new MACDPanel(closeListener, frame, stock), 
							  new PivotPanel(closeListener, frame, stock), 
							  new PriceChannelPanel(closeListener, frame, stock), 
							  new RSIPanel(closeListener, frame, stock), 
							  new StochOscillPanel(closeListener, frame, stock)};
		
		frame.setSize(350, eventList[0].getHeight() + 70);
		frame.setMinimumSize(new Dimension(350, eventList[0].getHeight() + 70));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout(new BorderLayout());
		
		
		JComboBox<JPanel> selectEvent = new JComboBox<JPanel>(eventList);
		selectEvent.addActionListener(new WindowChanger());
		JPanel dropDownPanel = new JPanel();
		dropDownPanel.setMinimumSize(new Dimension(250,20));
		dropDownPanel.setMaximumSize(new Dimension(250,20));
		dropDownPanel.add(selectEvent);
		dropDownPanel.setBorder(new EmptyBorder(20,20,20,20));

		
		
		frame.add(dropDownPanel, BorderLayout.NORTH);
		frame.add(currentPanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	class WindowChanger implements ActionListener
	{
		public WindowChanger(){}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println("selection made");
			@SuppressWarnings("unchecked")
			JComboBox<JPanel> selectEvent = (JComboBox<JPanel>)e.getSource();
			JPanel newPanel = (JPanel)selectEvent.getSelectedItem();
			Dimension panelDim = newPanel.getSize();
			frame.remove(currentPanel);
			currentPanel = newPanel;
			frame.setTitle(newPanel.toString());
			frame.add(currentPanel, BorderLayout.CENTER);
			frame.setSize(350, 70 + panelDim.height);
			frame.setMinimumSize(new Dimension(350, 70 + panelDim.height));
			display();
		}
		
	}
	
	@Override
	public void setWindowCloseListener(WindowCloseListener listener){}
}
