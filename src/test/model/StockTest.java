package model;

import model.exceptions.IllegalNameException;
import model.exceptions.IllegalPriceException;
import model.exceptions.IllegalVolumeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockTest {
    private Stock stock;

    @BeforeEach
    public void startup() {
        try {
            stock = new Stock("ETH", 11.42, 2325.61);
        } catch (Exception e) {
            fail("Not supposed to catch an exception.");
        }
    }

    @Test
    public void testConstructorBadName() {
        try {
            new Stock("", 15.32, 124.21);
        } catch (IllegalNameException e) {
            // excepted
        } catch (IllegalVolumeException e) {
            fail("Thrown IllegalVolumeException when not supposed to");
        } catch (IllegalPriceException e) {
            fail("Thrown IllegalPriceException when not supposed to");
        }
    }

    @Test
    public void testConstructorBadVolume() {
        try {
            new Stock("REEF", -23.2, 0.492);
        } catch (IllegalNameException e) {
            fail("Thrown IllegalNameException when not supposed to");
        } catch (IllegalVolumeException e) {
            // expected
        } catch (IllegalPriceException e) {
            fail("Thrown IllegalPriceException when not supposed to");
        }
    }

    @Test
    public void testConstructorBadInitPrice() {
        try {
            new Stock("REEF", 3.2, -0.492);
        } catch (IllegalNameException e) {
            fail("Thrown IllegalNameException when not supposed to");
        } catch (IllegalVolumeException e) {
            fail("Thrown IllegalVolumeException when not supposed to");
        } catch (IllegalPriceException e) {
            // expected
        }
    }

    @Test
    public void testJSONConstructorBadName() {
        try {
            new Stock("", 1.32, 153.23, 120.53);
        } catch (IllegalNameException e) {
            // excepted
        } catch (IllegalVolumeException e) {
            fail("Thrown IllegalVolumeException when not supposed to");
        } catch (IllegalPriceException e) {
            fail("Thrown IllegalPriceException when not supposed to");
        }
    }

    @Test
    public void testJSONConstructorBadVolume() {
        try {
            new Stock("XLM", -23.2, 0.342, 0.5123);
        } catch (IllegalNameException e) {
            fail("Thrown IllegalNameException when not supposed to");
        } catch (IllegalVolumeException e) {
            // expected
        } catch (IllegalPriceException e) {
            fail("Thrown IllegalPriceException when not supposed to");
        }
    }

    @Test
    public void testJSONConstructorBadInitPrice() {
        try {
            new Stock("XRP", 19.2, -0.492, 0.3532);
        } catch (IllegalNameException e) {
            fail("Thrown IllegalNameException when not supposed to");
        } catch (IllegalVolumeException e) {
            fail("Thrown IllegalVolumeException when not supposed to");
        } catch (IllegalPriceException e) {
            // expected
        }
    }

    @Test
    public void testJSONConstructorBadCurPrice() {
        try {
            new Stock("XRP", 19.2, 0.292, -0.3754);
        } catch (IllegalNameException e) {
            fail("Thrown IllegalNameException when not supposed to");
        } catch (IllegalVolumeException e) {
            fail("Thrown IllegalVolumeException when not supposed to");
        } catch (IllegalPriceException e) {
            // expected
        }
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
    public void testAddVolumeGoodInput() {
        assertEquals(11.42, stock.getVolume());
        try {
            stock.addVolume(3.413);
        } catch (IllegalVolumeException e) {
            fail("Not supposed to throw an IllegalVolumeException");
        }
        assertEquals(14.833, stock.getVolume());
    }

    @Test
    public void testAddVolumeNegInput() {
        assertEquals(11.42, stock.getVolume());
        try {
            stock.addVolume(-4.2);
            fail("Supposed to throw an IllegalVolumeException");
        } catch (IllegalVolumeException e) {
            // excepted
        }
        assertEquals(11.42, stock.getVolume());
    }

    @Test
    public void testSubtractVolumeGoodInput() {
        assertEquals(11.42, stock.getVolume());
        try {
            stock.subtractVolume(4.716);
        } catch (IllegalVolumeException e) {
            fail("Not supposed to throw an IllegalVolumeException");
        }
        assertEquals(6.704, stock.getVolume());
    }

    @Test
    public void testSubtractVolumeNegInput() {
        assertEquals(11.42, stock.getVolume());
        try {
            stock.subtractVolume(-6.23);
            fail("Supposed to throw an IllegalVolumeException");
        } catch (IllegalVolumeException e) {
            // expected
        }
        assertEquals(11.42, stock.getVolume());
    }

    @Test
    public void testSubtractVolumeTooBigInput() {
        assertEquals(11.42, stock.getVolume());
        try {
            stock.subtractVolume(14.23);
            fail("Supposed to throw an IllegalVolumeException");
        } catch (IllegalVolumeException e) {
            // expected
        }
        assertEquals(11.42, stock.getVolume());
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
