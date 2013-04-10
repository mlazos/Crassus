package edu.brown.cs32.atian.crassus.gui;
import org.jfree.data.xy.*;
import java.awt.*;


public class SeriesWrapper 
{
	private XYSeries series;
	private Color color;
	
	
	public SeriesWrapper(XYSeries series, Color color)
	{
		this.series = series;
		this.color = color;
	}

	XYSeries getSeries()
	{
		return series;
	}
	
	Color getColor()
	{
		return color;
	}

}
