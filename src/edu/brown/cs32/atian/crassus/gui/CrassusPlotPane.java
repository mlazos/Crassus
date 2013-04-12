package edu.brown.cs32.atian.crassus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class CrassusPlotPane extends JPanel {

	CrassusImageDisplayer imageDisplayer;
	
	public CrassusPlotPane(){
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(300,300));
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(20,20,20,20),
						BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)),
				BorderFactory.createEmptyBorder(5,5,5,5)));
		
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File("chart.png"));
		}
		catch(IOException e){
			System.out.println("sample image failed");
		}
		
		imageDisplayer = new CrassusImageDisplayer();
		imageDisplayer.setImage(image);
		this.add(imageDisplayer, BorderLayout.CENTER);
		//TODO make this class use images from actual stock data
	}
	
}
