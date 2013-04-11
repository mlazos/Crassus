package edu.brown.cs32.atian.crassus.gui;

public interface GUI {

	/**
	 * Launches the graphical display
	 */
	public void launch();
	
	/**
	 * Should be called when there is new data to draw to the screen
	 */
	public void update();
}
