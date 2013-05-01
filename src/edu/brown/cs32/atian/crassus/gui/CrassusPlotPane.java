package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.gui.TimeFrame;

public class CrassusPlotPane extends JPanel {

	public class ResizeListener implements ComponentListener {
		@Override public void componentHidden(ComponentEvent arg0) {}
		@Override public void componentMoved(ComponentEvent arg0) {}
		@Override public void componentResized(ComponentEvent arg0) {refresh();}
		@Override public void componentShown(ComponentEvent arg0) {}
	}

	CrassusImageDisplayer imageDisplayer;
	Stock stock;
	
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
		
		this.addComponentListener(new ResizeListener());
	}
	
	public void changeToStock(Stock stock){
		this.stock = stock;
		refresh();
	}
	
	public void refresh(){
		//check width of imageDisplayer because when pane is swapped out it will be zero, plot object flips out
		System.out.println("is stock null: "+(stock==null));
		System.out.println("is width 0: "+(imageDisplayer.getWidth()==0));
		if(stock!=null && imageDisplayer.getWidth()!=0){
			PlotWrapper plot = new PlotWrapper(stock.getCompanyName(), TimeFrame.DAILY);
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
