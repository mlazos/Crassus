package edu.brown.cs32.atian.crassus.gui;

import edu.brown.cs32.atian.crassus.indicators.Indicator;

public interface WindowCloseListener {

	public void windowClosedWithEvent(Indicator e);
	
	public void windowClosedWithCancel();
	
}
