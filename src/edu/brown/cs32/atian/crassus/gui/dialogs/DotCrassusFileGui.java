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

import edu.brown.cs32.atian.crassus.backend.DataSourceType;
import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockList;
import edu.brown.cs32.atian.crassus.backend.StockListImpl;
import edu.brown.cs32.atian.crassus.file.FileIO;
import edu.brown.cs32.atian.crassus.gui.mainwindow.GUI;

public class DotCrassusFileGui {
	
	
	final private static int TIMER_REFRESH_PERIOD = 2000;

	public class TimerListener implements ActionListener {
		
		private int counter = 0;
		
		@Override 
		public void actionPerformed(ActionEvent arg0) {
			while(true){
				try{
					if(counter==5){
						stocks.refreshAll();
						gui.update();
						counter = 0;
					}
					else{
						stocks.refreshPriceDataOnly();
						gui.refreshStockBox();
					}
					counter++;
					return;
				}catch(Exception e){
					String[] options = {"Try Again","Exit"};
					int result = JOptionPane.showOptionDialog(frame, 
							"Your connection with the server has been lost. You can either try to connect again, or exit the program",  
							"Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, 0);
					if(result==1){
						
						if(maybeSave("Would you like to save before you exit?")){
							frame.dispose();
							return;
						}
					}
				}
			}
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

	private boolean tryWrite(File file) {
		try {
			
			fio.write(file,stocks);
			this.f=file;
			return true;
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(frame, "Something went wrong. Your file could not be saved.");
			return false;
		} catch (ParserConfigurationException e) {
			JOptionPane.showMessageDialog(frame, "Something went wrong. Your file could not be saved.");
			return false;
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(frame, "Something went wrong. Your file could not be saved.");
			return false;
		}
	}

	public boolean fileSave() {
		if(f==null)
			return fileSaveAs();
		
		return tryWrite(f);
	}
	
	public boolean fileSaveAs() {
		
		int fcResult = fc.showSaveDialog(frame);
		
		if(fcResult == JFileChooser.APPROVE_OPTION){
			File file = new File(ExtensionUtils.setExtension("crassus",fc.getSelectedFile().getAbsolutePath()));
			
			if(file.exists()){
				int result = JOptionPane.showConfirmDialog(frame, file.getName() + " already exists in this directory. Would you like to overwrite it?");
				if(result==0)
					return tryWrite(file);
				if(result==1)
					return fileSaveAs();
				if(result==2)
					return false;
			}
		}
		return false;
	}
	
	public boolean maybeSave(String prompt) {
		
		if(stocks==null || stocks.getStockList().isEmpty())
			return true;
		
		int result = JOptionPane.showConfirmDialog(frame, prompt, "Message", 
				JOptionPane.YES_NO_CANCEL_OPTION);
		
		if(result==0){
			return fileSave();
		}
		if(result==1){
			return true;
		}
		return false;
	}
	
	public StockList fileNew() {
		
		if(!maybeSave("Would you like to save the current session before creating a new file?"))
			return stocks;
		
		if(timer!=null)
			timer.stop();
		
		String[] possibilities = {"Yahoo Finance","Demo Data"};
		String result = (String) JOptionPane.showInputDialog(frame, "choose a data source", "Message", 
				JOptionPane.PLAIN_MESSAGE, null, possibilities, "Yahoo Finance");
		
		DataSourceType source;
		
		if("Yahoo Finance".equals(result))
			source = DataSourceType.YAHOOFINANCE;
		else
			source = DataSourceType.DEMODATA;
		
		StockList stocks = new StockListImpl(source);
		
		this.stocks = stocks;
		
		timer = new Timer(TIMER_REFRESH_PERIOD, new TimerListener());
		timer.setRepeats(true);
		timer.start();
		
		return stocks;
		
	}

	public StockList fileOpen() {
		
		if(!maybeSave("Would you like to save the current session before opening a different file?"))
			return stocks;
		
		int fcResult = fc.showOpenDialog(frame);
		
		if(fcResult == JFileChooser.APPROVE_OPTION){
			
			File file = new File(ExtensionUtils.setExtension("crassus",fc.getSelectedFile().getAbsolutePath()));
			
			try{
				
				if(timer!=null)
					timer.stop();
				
				StockList stocks = fio.read(file);
				this.f=file;
				this.stocks = stocks;
				
				timer = new Timer(TIMER_REFRESH_PERIOD, new TimerListener());
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
	
	public boolean fileExit(){
		
		return maybeSave("Would you like to save the current session before you exit?");
		
	}
	
}
