package edu.brown.cs32.atian.crassus.launcher;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;

import edu.brown.cs32.atian.crassus.backend.DataSourceType;
import edu.brown.cs32.atian.crassus.backend.StockList;
import edu.brown.cs32.atian.crassus.backend.StockListImpl;
import edu.brown.cs32.atian.crassus.file.FileIO;
import edu.brown.cs32.atian.crassus.gui.mainwindow.CrassusGUI;
import edu.brown.cs32.atian.crassus.gui.mainwindow.GUI;

public class Main {

	private static GUI gui;
	private static StockList stocks;

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		UIManager.getDefaults().put("Button.background",Color.WHITE);//make JButtons less ugly
		UIManager.getDefaults().put("OptionPane.background", Color.WHITE);//change dialog box color
		UIManager.getDefaults().put("Panel.background", Color.WHITE);//
		UIManager.getDefaults().put("RadioButton.background", Color.WHITE);
		
		UIManager.getDefaults().put("MenuItem.background",new Color(240,240,255));
		UIManager.getDefaults().put("MenuBar.background",new Color(240,240,255));
		
		
		String[] possibilities = {"Yahoo Finance","Demo Data"};
		String result = (String) JOptionPane.showInputDialog(new JFrame(), "choose a data source", "Message", 
				JOptionPane.PLAIN_MESSAGE, null, possibilities, "Yahoo Finance");
		
		DataSourceType source;
		
		if("Yahoo Finance".equals(result))
			source = DataSourceType.YAHOOFINANCE;
		else
			source = DataSourceType.DEMODATA;
		
		FileIO.DATA_SOURCE_TYPE = source;
		
		gui = new CrassusGUI();
		gui.launch();
	
	}

}
