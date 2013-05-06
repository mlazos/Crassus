package edu.brown.cs32.atian.crassus.gui.undoable;

public interface Undoable {
	public void undo();
	public void redo();
	public boolean isIntense();
	public String getName();
}
