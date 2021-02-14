package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockTest {
    private Stock stock;

    @BeforeEach
    public void startup() {
        stock = new Stock("ETH", 11.42, 2325.61);
    }

    @Test
    public void testGetProfit() {
        assertEquals(2325.61, stock.getInitialPriceCAD());
        stock.setCurrentPriceCAD(2632.34);
        assertEquals(2632.34, stock.getCurrentPriceCAD());
        assertEquals(11.42, stock.getVolume());
        assertEquals(3502.8566, stock.getProfit());
    }

    @Test
    public void testAddVolume() {
        assertEquals(11.42, stock.getVolume());
        stock.addVolume(3.413);
        assertEquals(14.833, stock.getVolume());
    }

    @Test
    public void testSubtractVolume() {
        assertEquals(11.42, stock.getVolume());
        stock.subtractVolume(4.716);
        assertEquals(6.704, stock.getVolume());
    }

    @Test
    public void testToString() {
     assertEquals("[ETH: +$0.00, " + stock.getVolume() + "]", stock.toString());
     stock.setCurrentPriceCAD(2400);
     assertEquals(11.42 * (2400 - 2325.61), stock.getProfit());
     assertEquals("[ETH: +$849.53, " + stock.getVolume() + "]", stock.toString());
     stock.setCurrentPriceCAD(2200);
     assertEquals(11.42 * (2200 - 2325.61), stock.getProfit());
     assertEquals("[ETH: -$1434.47, " + stock.getVolume() + "]", stock.toString());
    }

    @Test
    public void testGetName() {
        // Just to test all the methods in Stock class
        assertEquals("ETH", stock.getName());
    }
}
