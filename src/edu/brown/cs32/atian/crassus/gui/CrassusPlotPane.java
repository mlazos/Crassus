package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.gui.TimeFrame;

public class CrassusPlotPane extends JPanel {

	public class TimeScaleChangeListener implements ActionListener {
		@Override public void actionPerformed(ActionEvent arg0) {
			refresh();
		}
	}

	public class ResizeListener implements ComponentListener {
		@Override public void componentHidden(ComponentEvent arg0) {}
		@Override public void componentMoved(ComponentEvent arg0) {}
		@Override public void componentResized(ComponentEvent arg0) {refresh();}
		@Override public void componentShown(ComponentEvent arg0) {}
	}

	private CrassusImageDisplayer imageDisplayer;
	private JComboBox<String> timeframe;
	private Stock stock;
	
	public CrassusPlotPane(){
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(300,300));
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(20,20,20,20),
						BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)),
				BorderFactory.createEmptyBorder(5,5,5,5)));
		
		imageDisplayer = new CrassusImageDisplayer();
		this.add(imageDisplayer, BorderLayout.CENTER);
		
		timeframe = new JComboBox<>();
		timeframe.addItem("Today");
		timeframe.addItem("This Week");
		timeframe.addItem("This Month");
		timeframe.addItem("This Year");
		timeframe.addItem("Five Years");
		
		timeframe.addActionListener(new TimeScaleChangeListener());
		
		JPanel timescalePanel = new JPanel();
		timescalePanel.setBackground(Color.WHITE);
		timescalePanel.setLayout(new FlowLayout());
		timescalePanel.add(new JLabel("Time-scale: "));
		timescalePanel.add(timeframe);
		
		this.add(timescalePanel, BorderLayout.SOUTH);
		
		this.addComponentListener(new ResizeListener());
	}
	
	public void changeToStock(Stock stock){
		this.stock = stock;
		refresh();
	}
	
	private TimeFrame timeframeFromIndex(int index){
		switch(index){
		case 0:
			return TimeFrame.DAILY;
		case 1:
			return TimeFrame.WEEKLY;
		case 2:
			return TimeFrame.MONTHLY;
		case 3:
			return TimeFrame.YEARLY;
		case 4:
		default:
			return TimeFrame.FIVE_YEAR;
		}
	}
	
	public void refresh(){
		//check width of imageDisplayer because when pane is swapped out it will be zero, plot object flips out
		if(stock!=null && imageDisplayer.getWidth()!=0){
			PlotWrapper plot = new PlotWrapper(stock.getCompanyName(), timeframeFromIndex(timeframe.getSelectedIndex()));
			plot.setAxesTitles("Time", "Price");
			stock.addToPlot(plot);
			BufferedImage image = plot.getPrimaryBufferedImage(imageDisplayer.getWidth(), imageDisplayer.getHeight());
			imageDisplayer.setImage(image);
		}
		else{
			imageDisplayer.setImage(null);
		}
		imageDisplayer.revalidate();
		imageDisplayer.repaint();
	}
	
}
