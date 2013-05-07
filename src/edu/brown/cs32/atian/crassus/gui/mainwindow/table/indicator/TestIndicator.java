package edu.brown.cs32.atian.crassus.gui.mainwindow.table.indicator;

import java.util.Date;
import java.util.List;

import edu.brown.cs32.atian.crassus.backend.StockEventType;
import edu.brown.cs32.atian.crassus.backend.StockTimeFrameData;
import edu.brown.cs32.atian.crassus.gui.StockPlot;
import edu.brown.cs32.atian.crassus.indicators.Indicator;

public class TestIndicator implements Indicator {

	@Override
	public void addToPlot(StockPlot stockPlot, Date startTime, Date endTime) {
		// TODO Auto-generated method stub

	}

	StockEventType state = StockEventType.NONE;
	
	@Override
	public void refresh(List<StockTimeFrameData> data) {
		switch(state){
		case BUY:
			state = StockEventType.SELL;
			return;
		case SELL:
			state = StockEventType.NONE;
			return;
		case NONE:
		default:
			state = StockEventType.BUY;
			return;
		}
	}

	@Override
	public double getTestResults() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	private boolean visible;
	@Override
	public boolean getVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean isVisible) {
		visible = isVisible;
	}

	private boolean active;
	@Override
	public boolean getActive() {
		return active;
	}

	@Override
	public void setActive(boolean isActive) {
		active = isActive;
	}

	@Override
	public StockEventType isTriggered() {
		if(active)
			return state;
		return StockEventType.NONE;
	}

	@Override
	public String getName() {
		return "Test Indicator";
	}

}
