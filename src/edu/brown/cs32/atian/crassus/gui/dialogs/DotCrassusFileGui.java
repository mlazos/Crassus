package edu.brown.cs32.atian.crassus.gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import edu.brown.cs32.atian.crassus.backend.StockList;
import edu.brown.cs32.atian.crassus.backend.StockListImpl;
import edu.brown.cs32.atian.crassus.file.FileIO;
import edu.brown.cs32.atian.crassus.gui.mainwindow.GUI;

public class DotCrassusFileGui {
	

	public class TimerListener implements ActionListener {
		@Override public void actionPerformed(ActionEvent arg0) {
			stocks.refreshAll();
			gui.update();
		}
	}
	

	private JFrame frame;
	private JFileChooser fc = new JFileChooser();
	
	private GUI gui;
	private Timer timer;
	
	private File f;
	
	private FileIO fio = new FileIO();
	
	private StockList stocks;
	
	public DotCrassusFileGui(JFrame frame, GUI gui){
		this.gui = gui;
		this.frame = frame;
		
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new DotCrassusFileFilter());
		fc.setFileView(new DotCrassusFileView());
	}

	private void tryWrite(File file) {
		try {
			
			fio.write(file,stocks);
			this.f=file;
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(frame, "Something went wrong. Your file could not be saved.");
		} catch (ParserConfigurationException e) {
			JOptionPane.showMessageDialog(frame, "Something went wrong. Your file could not be saved.");
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(frame, "Something went wrong. Your file could not be saved.");
		}
	}

	public void fileSave() {
		if(f==null)
			fileSaveAs();
		
		tryWrite(f);
	}
	
	public void fileSaveAs() {
		
		int fcResult = fc.showSaveDialog(frame);
		
		if(fcResult == JFileChooser.APPROVE_OPTION){
			File file = new File(ExtensionUtils.setExtension("crassus",fc.getSelectedFile().getAbsolutePath()));
			
			tryWrite(file);
		}
	}
	
	public StockList fileNew() {
		
		StockList stocks = new StockListImpl();
		
		this.stocks = stocks;
		
		timer = new Timer(60000, new TimerListener());
		timer.setRepeats(true);
		timer.start();
		
		return stocks;
		
	}

	public StockList fileOpen() {
		
		int fcResult = fc.showOpenDialog(frame);
		
		if(fcResult == JFileChooser.APPROVE_OPTION){
			
			File file = new File(ExtensionUtils.setExtension("crassus",fc.getSelectedFile().getAbsolutePath()));
			
			try{
				
				if(timer!=null)
					timer.stop();
				
				StockList stocks = fio.read(file);
				this.f=file;
				this.stocks = stocks;
				
				timer = new Timer(60000, new TimerListener());
				timer.setRepeats(true);
				timer.start();
				
				return stocks;
				
			} catch (ParserConfigurationException e) {
				JOptionPane.showMessageDialog(frame, "Something went wrong. The file you selected could not be opened.");
				stocks = null;
			} catch (SAXException e) {
				JOptionPane.showMessageDialog(frame, "Something went wrong. The file you selected could not be opened.");
				stocks = null;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "Something went wrong. The file you selected could not be opened.");
				stocks = null;
			}
		}
		
		return fileNew();
	}
	

}
