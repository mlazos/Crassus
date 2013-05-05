package edu.brown.cs32.atian.crassus.gui.undoable;

import java.util.Deque;
import java.util.LinkedList;

public class UndoableStack {
	
	private Deque<Undoable> undoables = new LinkedList<Undoable>();
	private Deque<Undoable> redoables = new LinkedList<Undoable>();
	private int maximumCapacity;
	
	public UndoableStack(int maximumCapacity){
		this.maximumCapacity = maximumCapacity;
	}
	
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
		if(undoables.size()>maximumCapacity)
			undoables.removeLast();
	}

	public void clear() {
		undoables.clear();
		redoables.clear();
	}
}
