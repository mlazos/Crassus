/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs32.atian.crassus.backend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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
public class StockRealTimeDataImplTest {

    public StockRealTimeDataImplTest() {
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
     * Test of getRealTimeData method, of class StockRealTimeDataImpl.
     */
    @Test
    public void testGetRealTimeData() {
        System.out.println("getRealTimeData");
        //System.out.println(System.currentTimeMillis());
        StockRealTimeDataImpl instance = new StockRealTimeDataImpl("msft");
        instance.Init();
        long now = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
              String timeStampNow = calendar.getTime().toString();
        System.out.println("Current time: " + timeStampNow);
        
        List<StockTimeFrameData> allRealTimeData = instance.getRealTimeData();          

        StockTimeFrameData firstData = allRealTimeData.get(0);
        System.out.println("First data timestamp: " + firstData.getTime());

        StockTimeFrameData lastData = allRealTimeData.get(allRealTimeData.size() - 1);
        System.out.println("Latest data timestamp: " + lastData.getTime());
    }
}
