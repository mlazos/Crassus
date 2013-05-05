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

public class FileIO {
	
	private void writeStock(Document doc, Element parent, Stock stock){
		
		Element elStock = doc.createElement("stock");
		
		elStock.setAttribute("ticker",stock.getTicker());
		
		//TODO add contained indicators
		
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
