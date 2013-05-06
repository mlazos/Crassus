package edu.brown.cs32.atian.crassus.gui.mainwindow.plot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockFreqType;
import edu.brown.cs32.atian.crassus.gui.PlotWrapper;
import edu.brown.cs32.atian.crassus.gui.TimeFrame;
import edu.brown.cs32.atian.crassus.gui.undoable.UndoableStack;
import edu.brown.cs32.atian.crassus.indicators.Indicator;

@SuppressWarnings("serial")
public class CrassusPlotPane extends JPanel {

	private SharedState dropdownsShouldRespond = new SharedState(true);
	private int timeFreqOldIndex=0;

	public class TimeFreqChangeListener implements ActionListener {
		
		@Override 
		public void actionPerformed(ActionEvent e) {
			
			int index = timeFreq.getSelectedIndex();
			changeTimeFreq(timeFreqFromIndex(index));
			timeFreqOldIndex = index;
		}
	}

	public class TimeScaleChangeListener implements ActionListener {
		
		@Override 
		public void actionPerformed(ActionEvent arg0) {
			
			int index = timeframe.getSelectedIndex();
			changeTimeFrame(timeframeFromIndex(index));
			timeScaleOldIndex = index;
		}
	}

	public class ResizeListener implements ComponentListener {
		@Override public void componentHidden(ComponentEvent arg0) {}
		@Override public void componentMoved(ComponentEvent arg0) {}
		@Override public void componentResized(ComponentEvent arg0) {refresh();}
		@Override public void componentShown(ComponentEvent arg0) {}
	}

	private CrassusImageDisplayer primaryPanel;
	private CrassusImageDisplayer rsPanel;
	private boolean rsOnState;
	
	private JComboBox<String> timeframe;
	private JComboBox<String> timeFreq;
	private TimeScaleChangeListener timeframeListener;
	private TimeFreqChangeListener timeFreqListener;
	private Stock stock;
	
	private UndoableStack undoables;
	
	private JMenuItem mSetTimeFreqMinutely;
	private JMenuItem mSetTimeFreqDaily;
	private JMenuItem mSetTimeFreqWeekly;
	private JMenuItem mSetTimeFreqMonthly;
	
	public CrassusPlotPane(UndoableStack undoables){
		this.undoables = undoables;
		
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(300,300));
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(20,20,20,20),
						BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)),
				BorderFactory.createEmptyBorder(5,5,5,5)));
		
		primaryPanel = new CrassusImageDisplayer();
		
		rsPanel = new CrassusImageDisplayer();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,primaryPanel,rsPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(350);
		this.add(splitPane,BorderLayout.CENTER);
		//splitPane.setDividerLocation(splitPane.getSize().width - splitPane.getInsets().bottom - splitPane.getDividerSize() - 100);
		splitPane.setResizeWeight(1.0);
		
		timeframe = new JComboBox<String>();
		timeframe.addItem("One Day");
		timeframe.addItem("One Week");
		timeframe.addItem("One Month");
		timeframe.addItem("One Year");
		timeframe.addItem("Five Years");
		
		timeframeListener = new TimeScaleChangeListener();
		timeframe.addActionListener(timeframeListener);
		
		timeFreq = new JComboBox<String>();
		timeFreq.addItem("Minutely");
//		timeFreq.addItem("Daily");
//		timeFreq.addItem("Weekly");
//		timeFreq.addItem("Monthly");
//		timeFreq.addItem("Yearly");
		
		timeFreqListener = new TimeFreqChangeListener();
		timeFreq.addActionListener(timeFreqListener);
		
		
		JPanel timePanel = new JPanel();
		timePanel.setBackground(Color.WHITE);
		timePanel.setLayout(new FlowLayout());
		timePanel.add(new JLabel("Time-scale: "));
		timePanel.add(timeframe);
		timePanel.add(new JLabel("  Time-frequency: "));
		timePanel.add(timeFreq);
		
		this.add(timePanel, BorderLayout.SOUTH);
		
		primaryPanel.addComponentListener(new ResizeListener());
		rsPanel.addComponentListener(new ResizeListener());
	}

	public void changeToStock(Stock stock){
		this.stock = stock;
		if(stock!=null){
			this.stock.setTimeFrame(timeframeFromIndex(timeframe.getSelectedIndex()));
			this.stock.setCurrFreq(timeFreqFromIndex(timeFreq.getSelectedIndex()));
			this.stock.refresh();
		}
		refresh();
	}

	private TimeFrame timeframeFromIndex(int index){
		switch(index){
		case 0:
			return TimeFrame.DAILY;
		case 1:
			return TimeFrame.WEEKLY;
		case 2:
			return TimeFrame.MONTHLY;
		case 3:
			return TimeFrame.YEARLY;
		case 4:
		default:
			return TimeFrame.FIVE_YEAR;
		}
	}
	
	private int indexFromTimeFrame(TimeFrame tf){
		switch(tf){
		case DAILY:
			return 0;
		case WEEKLY:
			return 1;
		case MONTHLY:
			return 2;
		case YEARLY:
			return 3;
		case FIVE_YEAR:
		default:
			return 4;
		}
	}

	private int timeScaleOldIndex=0;
	
	public void changeTimeFrame(TimeFrame tf){

		timeFreq.removeActionListener(timeFreqListener);//must be done to keep timeFreq from going bannanas; added back at the end of function
		timeFreq.removeAllItems();
		switch(tf){
		case FIVE_YEAR:
			mSetTimeFreqMinutely.setEnabled(false);
			
			timeFreq.addItem("Daily");
			mSetTimeFreqDaily.setEnabled(true);
			timeFreq.addItem("Weekly");
			mSetTimeFreqWeekly.setEnabled(true);
			timeFreq.addItem("Monthly");
			mSetTimeFreqMonthly.setEnabled(true);
			break;
		case YEARLY:
			mSetTimeFreqMinutely.setEnabled(false);
			
			timeFreq.addItem("Daily");
			mSetTimeFreqDaily.setEnabled(true);
			timeFreq.addItem("Weekly");
			mSetTimeFreqWeekly.setEnabled(true);
			timeFreq.addItem("Monthly");
			mSetTimeFreqMonthly.setEnabled(true);
			break;
		case MONTHLY:
			mSetTimeFreqMinutely.setEnabled(false);
			
			timeFreq.addItem("Daily");
			mSetTimeFreqDaily.setEnabled(true);
			timeFreq.addItem("Weekly");
			mSetTimeFreqWeekly.setEnabled(true);
			
			mSetTimeFreqMonthly.setEnabled(false);
			break;
		case WEEKLY:
			timeFreq.addItem("Minutely");
			mSetTimeFreqMinutely.setEnabled(true);
			timeFreq.addItem("Daily");
			mSetTimeFreqDaily.setEnabled(true);
			
			mSetTimeFreqWeekly.setEnabled(false);
			mSetTimeFreqMonthly.setEnabled(false);
			break;
		case DAILY:
			timeFreq.addItem("Minutely");
			mSetTimeFreqMinutely.setEnabled(true);

			mSetTimeFreqDaily.setEnabled(false);
			mSetTimeFreqWeekly.setEnabled(false);
			mSetTimeFreqMonthly.setEnabled(false);
			break;
		}
		
		if(stock!=null)
			stock.setTimeFrame(tf);
		
		timeFreq.addActionListener(timeFreqListener);
		//timeFreq can now respond. In fact we may want it to

		
		//the next four lines of code MUST occur before timeFreq.setSelectedIndex(0), or else undo functionality will not work
		int index = indexFromTimeFrame(tf);
		timeframe.removeActionListener(timeframeListener);
		timeframe.setSelectedIndex(index);
		timeframe.addActionListener(timeframeListener);
		
		if(dropdownsShouldRespond.getState()){
			timeFreq.setSelectedIndex(0);//force timeFreq to update. This will make 'refresh' unnecessary
		}
		else{
			refresh();
		}
		
		timeScaleOldIndex = index;
		
	}
	
	private StockFreqType timeFreqFromIndex(int index){
		
		switch(timeframeFromIndex(timeframe.getSelectedIndex())){
		case FIVE_YEAR:
		case YEARLY:
		case MONTHLY:
			index++;//first three don't allow minutely data
		default:
		}
		
		switch(index){
		case 0:
			return StockFreqType.MINUTELY;
		case 1:
			return StockFreqType.DAILY;
		case 2:
			return StockFreqType.WEEKLY;
		case 3:
		default:
			return StockFreqType.MONTHLY;
		}
	}
	
	private int indexFromTimeFreq(StockFreqType freq){
		switch(timeframeFromIndex(timeframe.getSelectedIndex())){
		case FIVE_YEAR:
		case YEARLY:
		case MONTHLY:
			switch(freq){
			case DAILY:
				return 0;
			case WEEKLY:
				return 1;
			case MONTHLY:
			default:
				return 2;
			}
		default:
			switch(freq){
			case MINUTELY:
				return 0;
			case DAILY:
				return 1;
			case WEEKLY:
				return 2;
			case MONTHLY:
			default:
				return 3;
			}
		}
	}

	public void changeTimeFreq(StockFreqType freq) {

		int index = indexFromTimeFreq(freq);
		
		if(dropdownsShouldRespond.getState())
			undoables.push(new DropdownSelectionUndoable(dropdownsShouldRespond, 
					timeFreq, timeFreqOldIndex, index, 
					timeframe, timeScaleOldIndex, timeframe.getSelectedIndex()));
		
		if(stock!=null){
			stock.setCurrFreq(freq);
			refresh();
		}
		
		timeFreq.removeActionListener(timeFreqListener);
		timeFreq.setSelectedIndex(index);
		timeFreq.addActionListener(timeFreqListener);
		
		timeFreqOldIndex = index;
	}

	public void refresh(){
		//check width of primaryPanel because when pane is swapped out it will be zero, plot object flips out
		if(stock==null || primaryPanel.getWidth()==0){
			primaryPanel.setImage(null);
		}
		else{
			PlotWrapper plot = new PlotWrapper(stock.getCompanyName(), timeframeFromIndex(timeframe.getSelectedIndex()));
			plot.setAxesTitles("Time", "Price");
			
			stock.addToPlot(plot);
			for (Indicator ind: stock.getEventList()){
				if (ind.getVisible()) {
					ind.addToPlot(plot, stock.getStartTime(), stock.getEndTime());
				}
			}
			
			BufferedImage primary = plot.getPrimaryBufferedImage(primaryPanel.getWidth(), primaryPanel.getHeight());
			primaryPanel.setImage(primary);
			
			if(plot.isRsOn()){
				BufferedImage rs = plot.getRsBufferedImage(rsPanel.getWidth(), rsPanel.getHeight());
				rsPanel.setImage(rs);
				
				rsPanel.revalidate();
				rsPanel.repaint();
			}
		}
	
		primaryPanel.revalidate();
		primaryPanel.repaint();
	}

	public void setMenuItems(
			JMenuItem mSetTimeFreqMinutely,
			JMenuItem mSetTimeFreqDaily, 
			JMenuItem mSetTimeFreqWeekly,
			JMenuItem mSetTimeFreqMonthly) {
		this.mSetTimeFreqMinutely = mSetTimeFreqMinutely;
		this.mSetTimeFreqDaily = mSetTimeFreqDaily;
		mSetTimeFreqDaily.setEnabled(false);
		this.mSetTimeFreqWeekly = mSetTimeFreqWeekly;
		mSetTimeFreqWeekly.setEnabled(false);
		this.mSetTimeFreqMonthly = mSetTimeFreqMonthly;
		mSetTimeFreqMonthly.setEnabled(false);
	}
	
}
