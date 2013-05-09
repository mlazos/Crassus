package edu.brown.cs32.atian.crassus.gui.mainwindow.table.indicator;

import edu.brown.cs32.atian.crassus.gui.undoable.Undoable;
import edu.brown.cs32.atian.crassus.indicators.Indicator;

public class ChangeIndicatorUndoable implements Undoable {

	private CrassusIndicatorTableModel model;
	private int index;
	private Indicator prevInd;
	private Indicator ind;
	
	public ChangeIndicatorUndoable(CrassusIndicatorTableModel model, int index,
			Indicator prevInd, Indicator ind) {
		
		this.model = model;
		this.index = index;
		this.prevInd = prevInd;
		this.ind = ind;
		
	}

	@Override
	public void undo() {
		model.changeIndicator(index, prevInd);
	}

	@Override
	public void redo() {
		model.changeIndicator(index, ind);
	}

	@Override
	public boolean isIntense() {
		return true;
	}

	@Override
	public String getName() {
		if(ind.getName().equals(prevInd.getName())){
			return "Alteration Of " + ind.getName() + " Indicator";
		}
		else
			return "Change Of " + prevInd.getName() + " to " + ind.getName();
	}

}
