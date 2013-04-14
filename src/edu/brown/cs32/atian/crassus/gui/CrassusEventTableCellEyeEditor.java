package edu.brown.cs32.atian.crassus.gui;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class CrassusEventTableCellEyeEditor extends AbstractCellEditor implements TableCellEditor {

	private CrassusCheckBoxEye checkBox;
	
	public CrassusEventTableCellEyeEditor(){
		checkBox = new CrassusCheckBoxEye();
	}
	
	@Override
	public Object getCellEditorValue() {
		return !checkBox.isSelected();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if(value instanceof Boolean){
			checkBox.setSelected(!((Boolean) value).booleanValue());
		}
		return checkBox;
	}

}
