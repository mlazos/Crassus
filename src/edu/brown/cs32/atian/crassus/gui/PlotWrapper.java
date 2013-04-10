package edu.brown.cs32.atian.crassus.gui;


import java.awt.Paint;
import java.awt.Color;
import java.awt.BasicStroke;
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
	
	
	@Override
	public JFreeChart getPrimaryChart() 
	{
		return null;
	}

	@Override
	public JFreeChart getRelativeStrengthChart() 
	{
		if(isRsOnSameChart)
		{
			throw new Error("RS chart can't be retrieved, it is set to be on the primary chart.");
		}
		
		
		return rsChart;
	}

	@Override
	public void setRsOnSameChart(boolean state) 
	{
		isRsOnSameChart = true;
	}

	@Override
	public void turnRsOn() 
	{
		isRsOn = true;
	}
	
	public void addSeries(SeriesWrapper series)
	{
		this.series.add(series);
	}
	
	

}
