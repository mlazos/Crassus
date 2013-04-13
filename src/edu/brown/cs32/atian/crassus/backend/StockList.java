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
public interface StockList {
    ArrayList<Stock> getStockList();
    Stock getStock(String ticker);
    void add(Stock s);
    void delete(String ticker);
    void refreshAll();
}
