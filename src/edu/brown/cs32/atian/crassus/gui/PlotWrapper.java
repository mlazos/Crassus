package edu.brown.cs32.atian.crassus.gui;


import java.awt.Paint;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.util.List;
import org.jfree.chart.JFreeChart;


public class PlotWrapper implements StockPlot 
{

	private List<SeriesWrapper> series;
	private List<SeriesWrapper> rsSeries;
	private boolean isRsOn;
	private boolean isRsOnSameChart;
	private JFreeChart primaryChart;
	private JFreeChart rsChart;
	
	//add a series to the list of series
	public void addSeries(SeriesWrapper series)
	{
		this.series.add(series);
	}

	//set whether the relative strength chart should be separate from the primary chart
	@Override
	public void setRsOnSameChart(boolean isRsOnSameChart) 
	{
		this.isRsOnSameChart = isRsOnSameChart;
	}

	@Override
	public boolean isRsOn() 
	{
		return isRsOnSameChart;
	}

	@Override
	public BufferedImage getPrimaryBufferedImage(int width, int height) 
	{
		return null;
	}

	@Override
	public BufferedImage getRsBufferedImage(int width, int height) 
	{

		return null;
	}
	
	

}
