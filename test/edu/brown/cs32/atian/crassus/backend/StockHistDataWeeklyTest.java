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
public class StockHistDataWeeklyTest {
    
    public StockHistDataWeeklyTest() {
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
     * Test of Init method, of class StockHistDataWeekly.
     */
    @Test
    public void testInit() {
        System.out.println("Init");
        StockHistDataWeekly instance = null;
        instance.Init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHistData method, of class StockHistDataWeekly.
     */
    @Test
    public void testGetHistData() {
        System.out.println("getHistData");
        StockHistDataWeekly instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getHistData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWeek52Low method, of class StockHistDataWeekly.
     */
    @Test
    public void testGetWeek52Low() {
        System.out.println("getWeek52Low");
        StockHistDataWeekly instance = null;
        float expResult = 0.0F;
        float result = instance.getWeek52Low();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWeek52High method, of class StockHistDataWeekly.
     */
    @Test
    public void testGetWeek52High() {
        System.out.println("getWeek52High");
        StockHistDataWeekly instance = null;
        float expResult = 0.0F;
        float result = instance.getWeek52High();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFreq method, of class StockHistDataWeekly.
     */
    @Test
    public void testGetFreq() {
        System.out.println("getFreq");
        StockHistDataWeekly instance = null;
        String expResult = "";
        String result = instance.getFreq();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
