package edu.brown.cs32.atian.crassus.gui;


import java.awt.Paint;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeriesCollection;


public class PlotWrapper implements StockPlot 
{
	private String stockName;
	private XYSeriesCollection series;
	private XYSeriesCollection rsSeries;
	private List<Color> seriesColors;
	private List<Color> rsSeriesColors;
	private boolean isRsOn;
	private boolean isRsOnSameChart;
	private JFreeChart primaryChart;
	private JFreeChart rsChart;
	
	public PlotWrapper(String stockName){ this.stockName = stockName; }
	
	//add a series to the list of series
	public void addSeries(SeriesWrapper series)
	{
		this.series.addSeries(series.getSeries());
		this.seriesColors.add(series.getColor());
	}
	
	public void addRsSeries(SeriesWrapper series)
	{
		this.rsSeries.addSeries(series.getSeries());
		this.rsSeriesColors.add(series.getColor());
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
		JFreeChart chart;
		
		if(isRsOnSameChart)
		{
			//fill colors list with all the colors from the stored lists
			List<Color> chartColors = new ArrayList<Color>(seriesColors);
			chartColors.addAll(rsSeriesColors);
			
			XYSeriesCollection chartSeries = new XYSeriesCollection();
			
			//fill chartSeries with all neccessary series
			for(int i = 0; i < series.getSeriesCount(); i++)
			{
				chartSeries.addSeries(series.getSeries(i));
			}
			
			for(int i = 0; i < rsSeries.getSeriesCount(); i++)
			{
				chartSeries.addSeries(rsSeries.getSeries(i));
			}
			
			chart = generateChart(stockName, chartSeries, chartColors);
			
			return chart.createBufferedImage(width, height);
		}
		else
		{
			chart = generateChart(stockName, series, seriesColors);
			return chart.createBufferedImage(width, height);
		}
		
	}

	@Override
	public BufferedImage getRsBufferedImage(int width, int height) 
	{
		if(!isRsOn || isRsOnSameChart)
		{
			throw new Error("This method can only be called if RSI is not on the primary and RSI is set to on.");
		}

		JFreeChart chart = generateChart("Relative Strength Index", rsSeries, rsSeriesColors);
		
		return chart.createBufferedImage(width, height);
	}

	@Override
	public void setRS(boolean isRsOn) 
	{
		this.isRsOn = isRsOn;
	}
	
	private JFreeChart generateChart(String title, XYSeriesCollection series, List<Color> colors)
	{
		JFreeChart chart = ChartFactory.createXYLineChart(title, "X", "Y", series, PlotOrientation.HORIZONTAL, false, false, false);
		XYPlot plot  = (XYPlot)chart.getPlot();
		XYItemRenderer ren = plot.getRenderer();
		
		for(int i = 0; i < colors.size(); i++)
		{
			ren.setSeriesPaint(i, colors.get(i));
		}
		
		return chart;
	}
	
	

}
