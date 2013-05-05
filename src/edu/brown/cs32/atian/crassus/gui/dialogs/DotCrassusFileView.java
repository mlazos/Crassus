package edu.brown.cs32.atian.crassus.gui.dialogs;

import java.io.File;

import javax.swing.filechooser.FileView;

public class DotCrassusFileView extends FileView {

	@Override
	public String getTypeDescription(File f){
		return "Crassus Environment File";
	}
}
