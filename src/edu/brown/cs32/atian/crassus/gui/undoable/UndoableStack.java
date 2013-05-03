package edu.brown.cs32.atian.crassus.gui.undoable;

import java.util.Deque;
import java.util.LinkedList;

public class UndoableStack {
	
	Deque<Undoable> undoables = new LinkedList<>();
	Deque<Undoable> redoables = new LinkedList<>();
	
	public void undo(){
		if(undoables.isEmpty())
			return;
		
		Undoable u = undoables.pop();
		u.undo();
		redoables.push(u);
	}
	
	public void redo(){
		if(redoables.isEmpty())
			return;
		
		Undoable u = redoables.pop();
		u.redo();
		undoables.push(u);
	}
	
	public void push(Undoable u){
		redoables.clear();
		undoables.push(u);
	}
}
