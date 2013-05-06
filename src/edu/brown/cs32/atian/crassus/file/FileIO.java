package edu.brown.cs32.atian.crassus.file;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.brown.cs32.atian.crassus.backend.Stock;
import edu.brown.cs32.atian.crassus.backend.StockImpl;
import edu.brown.cs32.atian.crassus.backend.StockList;
import edu.brown.cs32.atian.crassus.backend.StockListImpl;
import edu.brown.cs32.atian.crassus.indicators.BollingerBands;
import edu.brown.cs32.atian.crassus.indicators.Indicator;
import edu.brown.cs32.atian.crassus.indicators.MACD;
import edu.brown.cs32.atian.crassus.indicators.PivotPoints;
import edu.brown.cs32.atian.crassus.indicators.PriceChannel;
import edu.brown.cs32.atian.crassus.indicators.RSI;
import edu.brown.cs32.atian.crassus.indicators.StochasticOscillator;

public class FileIO {
	
	

	private void writeIndicator(Document doc, Element elStock, Indicator indicator) {
		
		Element elIndicator = doc.createElement("indicator");
		
		String visible = Boolean.toString(indicator.getVisible());
		elIndicator.setAttribute("visible", visible);
		
		String active = Boolean.toString(indicator.getActive());
		elIndicator.setAttribute("active", active);
		
		if(indicator instanceof BollingerBands){
			
			BollingerBands bb = (BollingerBands) indicator;
			elIndicator.setAttribute("implementation", "BollingerBands");
			
			String period = Integer.toString(bb.getPeriod());
			elIndicator.setAttribute("period", period);
			
			String bandWidth = Integer.toString(bb.getBandWidth());
			elIndicator.setAttribute("bandWidth", bandWidth);
			
		}
		else if(indicator instanceof MACD){
			
			MACD macd = (MACD) indicator;
			elIndicator.setAttribute("implementation", "MACD");
			
			String shortPeriod = Integer.toString(macd.getShortPeriod());
			elIndicator.setAttribute("shortPeriod", shortPeriod);
			
			String signalPeriod = Integer.toString(macd.getSignalPeriod());
			elIndicator.setAttribute("signalPeriod", signalPeriod);
			
			String longPeriod = Integer.toString(macd.getLongPeriod());
			elIndicator.setAttribute("longPeriod",longPeriod);
			
		}
		else if(indicator instanceof PivotPoints){
			
			PivotPoints pp = (PivotPoints) indicator;
			elIndicator.setAttribute("implementation", "PivotPoints");
			
			String pivotOption = pp.getPivotOption();
			elIndicator.setAttribute("pivotOption", pivotOption);
			
		}
		else if(indicator instanceof PriceChannel){
			
			PriceChannel pc = (PriceChannel) indicator;
			elIndicator.setAttribute("implementation", "PriceChannel");
			
			String lookBackPeriod = Integer.toString(pc.getLookBackPeriod());
			elIndicator.setAttribute("lookBackPeriod", lookBackPeriod);
			
		}
		else if(indicator instanceof RSI){
			
			RSI rsi = (RSI) indicator;
			elIndicator.setAttribute("implementation", "rsi");
			
			String period = Integer.toString(rsi.getPeriod());
			elIndicator.setAttribute("period", period);
			
		}
		else if(indicator instanceof StochasticOscillator){
			
			StochasticOscillator so = (StochasticOscillator) indicator;
			elIndicator.setAttribute("implementation", "StochasticOscillator");
			
			String period = Integer.toString(so.getPeriod());
			elIndicator.setAttribute("period", period);
			
		}
	}
	
	private void writeStock(Document doc, Element parent, Stock stock){
		
		Element elStock = doc.createElement("stock");
		elStock.setAttribute("ticker",stock.getTicker());
		
		for(Indicator indicator: stock.getEventList()){
			writeIndicator(doc, elStock, indicator);
		}
		
		parent.appendChild(elStock);
		
	}
	
	private Stock readStock(Element parent) {
		
		String ticker = parent.getAttribute("ticker");
		Stock stock = new StockImpl(ticker);
		//TODO deal with the contained indicators
		return stock;
		
	}
	
	public boolean write(File f, StockList stocks) throws ParserConfigurationException, FileNotFoundException, TransformerException {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.newDocument();

		Element root = doc.createElement("root");

		for(Stock stock: stocks.getStockList()){
			writeStock(doc,root,stock);
		}

		doc.appendChild(root);
		
		DOMSource source = new DOMSource(doc);

		PrintStream ps = new PrintStream(f);
		StreamResult result = new StreamResult(ps);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		
		transformer.transform(source, result);
		
		ps.close();
		
		return true;
	}

	public StockList read(File f) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document doc = db.parse(f);
		
		Element root = doc.getDocumentElement();
		
		StockList stocks = new StockListImpl();
		
		NodeList nList = root.getChildNodes();
		
		for(int i=0; i<nList.getLength(); i++){
			
			Node node = nList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE && "stock".equals(node.getNodeName())){
				
				Element elem = (Element) node;
				stocks.add(readStock(elem));
				//TODO handle for tickers that may have become obsolete
				
			}
			
		}
		
		
		return stocks;
	}

	
}
