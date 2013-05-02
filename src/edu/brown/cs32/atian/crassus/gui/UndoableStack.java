package edu.brown.cs32.atian.crassus.gui;

import java.util.Stack;

@SuppressWarnings("serial")
public class UndoableStack extends Stack<Undoable> {
	
	Stack<Undoable> redoables = new Stack<>();
	
	public void undo(){
		Undoable undoable = this.pop();
		if(undoable!=null){
			undoable.undo();
			redoables.push(undoable);
		}
	}
	
	public void redo(){
		Undoable undoable = redoables.pop();
		if(undoable!=null){
			undoable.redo();
			this.push(undoable);
		}
	}
	
	public void add(){
		redoables = new Stack<>();
	}
}
