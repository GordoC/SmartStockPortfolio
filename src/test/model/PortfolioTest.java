package model;

import model.exceptions.DuplicateStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PortfolioTest {
    private Portfolio portfolio;

    @BeforeEach
    public void startup() {
        portfolio = new Portfolio();
    }

    @Test
    public void testAddOneStock() {
        assertEquals(0, portfolio.length());
        Stock stock = initializeGoodStock("BTC", 2.3, 4351.23);
        addGoodStock(stock);
        assertEquals(1, portfolio.length());
    }

    @Test
    public void testAddManyStock() {
        assertEquals(0, portfolio.length());
        Stock stock1 = initializeGoodStock("BTC", 2.3, 4351.23);
        addGoodStock(stock1);
        assertEquals(1, portfolio.length());
        Stock stock2 = initializeGoodStock("ETH", 12.3, 1451.19);
        addGoodStock(stock2);
    }

    @Test
    public void testAddDuplicateNameStock() {
        assertEquals(0, portfolio.length());
        Stock stock1 = initializeGoodStock("BTC", 2.3, 4351.23);
        addGoodStock(stock1);
        assertEquals(1, portfolio.length());
        Stock stock2 = initializeGoodStock("BTC", 5.3, 50051.23);
        try {
            portfolio.addStock(stock2);
            fail("Should have thrown a DuplicateStockException");
        } catch (DuplicateStockException e) {
            // expected
        }
        assertEquals(1, portfolio.length());
    }

    @Test
    public void testSubtractStockOne() {
        Stock stock = initializeGoodStock("DOT", 9.01, 29.83);
        addGoodStock(stock);
        assertEquals(1, portfolio.length());
        portfolio.deleteStock(stock);
        assertEquals(0, portfolio.length());
    }

    @Test
    public void testSubtractStockMany() {
        Stock stock1 = initializeGoodStock("LTC", 1.31, 213.15);
        Stock stock2 = initializeGoodStock("NIO", 3, 61.43);
        Stock stock3 = initializeGoodStock("BB", 23, 24.78);
        addGoodStock(stock1);
        addGoodStock(stock2);
        addGoodStock(stock3);
        assertEquals(3, portfolio.length());
        portfolio.deleteStock(stock2);
        assertEquals(2, portfolio.length());
    }

    @Test
    public void testTotalProfit() {
        Stock stock1 = initializeGoodStock("AMC", 164, 14.32);
        Stock stock2 = initializeGoodStock("GME", 10, 420.69);
        Stock stock3 = initializeGoodStock("NOK", 6, 6.48);
        stock1.setCurrentPriceCAD(16.74);
        stock2.setCurrentPriceCAD(832);
        stock3.setCurrentPriceCAD(5.23);
        double totalProfit = stock1.getProfit() + stock2.getProfit() + stock3.getProfit();
        addGoodStock(stock1);
        addGoodStock(stock2);
        addGoodStock(stock3);
        assertEquals(totalProfit, portfolio.totalProfit());
    }

    @Test
    public void testFindStock() {
        Stock stock1 = initializeGoodStock("DOGE", 1432.43, 0.0649);
        Stock stock2 = initializeGoodStock("ADA", 415.32, 0.8796);
        Stock stock3 = initializeGoodStock("TSLA", 3, 816.12);
        addGoodStock(stock1);
        addGoodStock(stock2);
        addGoodStock(stock3);
        assertEquals(stock1, portfolio.findStock("DOGE"));
        assertEquals(stock2, portfolio.findStock("ADA"));
        assertEquals(stock3, portfolio.findStock("TSLA"));
        assertEquals(null, portfolio.findStock("GORDON"));
    }

    @Test
    public void testToString() {
        Stock stock1 = initializeGoodStock("AAPL", 13, 135.37);
        Stock stock2 = initializeGoodStock("DIS", 23, 187.67);
        Stock stock3 = initializeGoodStock("NFLX", 5, 556.52);
        stock1.setCurrentPriceCAD(142.43);
        stock2.setCurrentPriceCAD(132.58);
        stock3.setCurrentPriceCAD(632.41);
        addGoodStock(stock1);
        addGoodStock(stock2);
        addGoodStock(stock3);
        String totalStr = stock1.toString() + ", " + stock2.toString() + ", " + stock3.toString();
        assertEquals(totalStr, portfolio.toString());
    }

    // helper method so I don't have to add multiple try and catch blocks
    public Stock initializeGoodStock(String name, double volume, double initPrice) {
        try {
            return new Stock(name, volume, initPrice);
        } catch (Exception e) {
            fail("Bad input");
        }
        return null;
    }

    // helper method so I don't have to add multiple try and catch blocks
    public void addGoodStock(Stock stock) {
        try {
            portfolio.addStock(stock);
        } catch (Exception e) {
            fail("Bad input");
        }
    }
}