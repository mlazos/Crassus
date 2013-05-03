package edu.brown.cs32.atian.crassus.launcher;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.UIManager;

import edu.brown.cs32.atian.crassus.backend.StockList;
import edu.brown.cs32.atian.crassus.backend.StockListImpl;
import edu.brown.cs32.atian.crassus.gui.mainwindow.CrassusGUI;
import edu.brown.cs32.atian.crassus.gui.mainwindow.GUI;

public class Main {

	public static class TimerListener implements ActionListener {
		@Override public void actionPerformed(ActionEvent arg0) {
			//long start = System.currentTimeMillis();
			stocks.refreshAll();
			//long middle = System.currentTimeMillis();
			gui.update();
			//long end = System.currentTimeMillis();
			//System.out.println("refreshing stocks: "+(middle-start));
			//System.out.println("updating GUI: "+(end-middle));
		}
	}
	
	private static GUI gui;
	private static StockList stocks;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		stocks = new StockListImpl();
		
		UIManager.getDefaults().put("Button.background",Color.WHITE);//make JButtons less ugly
		UIManager.getDefaults().put("OptionPane.background", Color.WHITE);//change dialog box color
		UIManager.getDefaults().put("Panel.background", Color.WHITE);//
		UIManager.getDefaults().put("RadioButton.background", Color.WHITE);
		gui = new CrassusGUI(stocks);
		gui.launch();
	
		Timer timer = new Timer(60000, new TimerListener());
		timer.setRepeats(true);
		timer.start();
	}

}
