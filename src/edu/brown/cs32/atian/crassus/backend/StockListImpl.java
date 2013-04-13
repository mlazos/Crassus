/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import java.util.ArrayList;

/**
 *
 * @author lyzhang
 */
public class StockListImpl implements StockList {
    
    private ArrayList<Stock> _stocks;
    
    public StockListImpl() {
        
    }
    

    public ArrayList<Stock> getStockList() {
        return _stocks;
    }
    

    public Stock getStock(String ticker) {
        for(Stock s : _stocks) {
            if(s.getTicker().equalsIgnoreCase(ticker)) {
                return s;
            }
        }
        return null;
    }
    

    public void add(Stock s) {
        _stocks.add(s);
    }
    

    public void delete(String ticker) {
         Stock s = this.getStock(ticker);
         if(s!=null) {
            _stocks.remove(s);
         }
     }
    
     public void refreshAll() {
         
     }
}
