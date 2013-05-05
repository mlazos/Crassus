package edu.brown.cs32.atian.crassus.gui.dialogs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import edu.brown.cs32.atian.crassus.backend.StockList;
import edu.brown.cs32.atian.crassus.file.FileIO;

public class DotCrassusFileGui {

	private JFrame frame;
	private JFileChooser fc = new JFileChooser();
	
	private FileIO fio = new FileIO();
	
	private StockList stocks;
	
	public DotCrassusFileGui(StockList stocks, JFrame frame){
		this.stocks = stocks;
		this.frame = frame;
		
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new DotCrassusFileFilter());
		fc.setFileView(new DotCrassusFileView());
	}

	public void fileSaveAs() {
		
		int fcResult = fc.showSaveDialog(frame);
		
		if(fcResult == JFileChooser.APPROVE_OPTION){
			File file = new File(ExtensionUtils.setExtension("crassus",fc.getSelectedFile().getAbsolutePath()));
			
			
			
			try {
				
				fio.write(file,stocks);
				
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(frame, "Something went wrong. Your file could not be saved.");
			} catch (ParserConfigurationException e) {
				JOptionPane.showMessageDialog(frame, "Something went wrong. Your file could not be saved.");
			} catch (TransformerException e) {
				JOptionPane.showMessageDialog(frame, "Something went wrong. Your file could not be saved.");
			}
		}
	}

	public StockList fileOpen() {
		
		int fcResult = fc.showOpenDialog(frame);
		
		if(fcResult == JFileChooser.APPROVE_OPTION){
			
			File file = fc.getSelectedFile();
			
			try{
				
				return fio.read(file);
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
