/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import java.util.List;

/**
 *
 * @author lyzhang
 */
public interface StockList {

    List<Stock> getStockList();
    Stock getStock(String ticker);
    void add(Stock s);
    void delete(String ticker);
    void refreshAll();
    List<String> getTickerSuggestion(String tickerPrefix);    
//    List<String> getCompanyNameSuggestion(String tickerPrefix);       
}
