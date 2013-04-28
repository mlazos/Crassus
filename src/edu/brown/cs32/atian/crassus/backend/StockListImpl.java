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
        _stocks = new ArrayList<Stock>();
    }
    

    @Override
    public ArrayList<Stock> getStockList() {
        return _stocks;
    }
    

    @Override
    public Stock getStock(String ticker) {
        for(Stock s : _stocks) {
            if(s.getTicker().equalsIgnoreCase(ticker)) {
                return s;
            }
        }
        return null;
    }
    

    @Override
    public void add(Stock s) {
        _stocks.add(s);
    }
    

    @Override
    public void delete(String ticker) {
         Stock s = this.getStock(ticker);
         if(s!=null) {
            _stocks.remove(s);
         }
     }
    
    @Override 
    public void refreshAll() {
        for(Stock s : _stocks) {
            s.refresh();
        }         
     }
}
