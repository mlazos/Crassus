package edu.brown.cs32.atian.crassus.gui.undoable;

public interface UndoableStackListener {

	public void changeUndo(String string);	
	public void changeRedo(String string);
	public void informHasChanges(boolean b);
	
}
