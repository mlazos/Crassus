package edu.brown.cs32.atian.crassus.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CrassusEventTableCellEyeRenderer extends CrassusCheckBoxEye implements TableCellRenderer {

	//private CrassusCheckBoxEye checkBox;
	
	public CrassusEventTableCellEyeRenderer(){
		//checkBox = new CrassusCheckBoxEye();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		System.out.println("value: "+value+", row: "+row+", column: "+column);
		if(value instanceof Boolean){
			super.setSelected(((Boolean) value).booleanValue());
		}
		return this;
	}

}
