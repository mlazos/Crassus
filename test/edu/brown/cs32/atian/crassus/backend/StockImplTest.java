/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lyzhang
 */
public class StockImplTest {
    
    public StockImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of getCompanyName method, of class StockImpl.
     */
    @Test
    public void testGetCompanyName() {
        System.out.println("getCompanyName");
        
        Stock googleStock = new StockImpl("GOOG");
        //googleStock.initialize();
        String result = googleStock.getCompanyName();

    }

}
