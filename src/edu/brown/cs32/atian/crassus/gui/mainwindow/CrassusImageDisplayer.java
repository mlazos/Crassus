package edu.brown.cs32.atian.crassus.gui.mainwindow;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CrassusImageDisplayer extends JPanel {

	private BufferedImage _image;
	
	public void setImage(BufferedImage image){
		_image = image;
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(_image, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
