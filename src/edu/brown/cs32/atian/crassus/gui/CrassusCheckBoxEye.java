package edu.brown.cs32.atian.crassus.gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class CrassusCheckBoxEye extends JCheckBox {


	static Icon greyEye;
	static Icon blackEye;
	
	{//STATIC CODE BLOCK
		try {
			greyEye = new ImageIcon(ImageIO.read(new File("icons/shittyTest.png")));
			
			blackEye = new ImageIcon(ImageIO.read(new File("icons/blackEye.png")));
		} catch (IOException e) {
			e.printStackTrace();
			greyEye = null;
			blackEye = null;
		}
	}
	

	@Override
	public void setSelected(boolean sel){
		System.out.println("setSelected("+sel+");");
		super.setSelected(sel);
		if(sel)
			setIcon(blackEye);
		else
			setIcon(greyEye);
	}
}
