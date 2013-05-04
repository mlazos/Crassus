package edu.brown.cs32.atian.crassus.gui.undoable;

import java.util.Deque;
import java.util.LinkedList;

public class UndoableStack {
	
	private Deque<Undoable> undoables = new LinkedList<>();
	private Deque<Undoable> redoables = new LinkedList<>();
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
		System.out.println("received undoable: "+u);
		
		redoables.clear();
		undoables.push(u);
		if(undoables.size()>maximumCapacity)
			undoables.removeLast();
	}
}
