package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PortfolioTest {
    private Portfolio portfolio;

    @BeforeEach
    public void startup() {
        portfolio = new Portfolio();
    }

    @Test
    public void testAddStock() {
        assertEquals(0, portfolio.length());
        Stock stock = new Stock("BTC", 2.3, 4351.23);
        portfolio.addStock(stock);
        assertEquals(1, portfolio.length());
    }

    @Test
    public void testSubtractStockOne() {
        Stock stock = new Stock("DOT", 9.01, 29.83);
        portfolio.addStock(stock);
        assertEquals(1, portfolio.length());
        portfolio.deleteStock(stock);
        assertEquals(0, portfolio.length());
    }

    @Test
    public void testSubtractStockMany() {
        Stock stock1 = new Stock("LTC", 1.31, 213.15);
        Stock stock2 = new Stock("NIO", 3, 61.43);
        Stock stock3 = new Stock("BB", 23, 24.78);
        portfolio.addStock(stock1);
        portfolio.addStock(stock2);
        portfolio.addStock(stock3);
        assertEquals(3, portfolio.length());
        portfolio.deleteStock(stock2);
        assertEquals(2, portfolio.length());
    }

    @Test
    public void testTotalProfit() {
        Stock stock1 = new Stock("AMC", 164, 14.32);
        Stock stock2 = new Stock("GME", 10, 420.69);
        Stock stock3 = new Stock("NOK", 6, 6.48);
        stock1.setCurrentPriceCAD(16.74);
        stock2.setCurrentPriceCAD(832);
        stock3.setCurrentPriceCAD(5.23);
        double totalProfit = stock1.getProfit() + stock2.getProfit() + stock3.getProfit();
        portfolio.addStock(stock1);
        portfolio.addStock(stock2);
        portfolio.addStock(stock3);
        assertEquals(totalProfit, portfolio.totalProfit());
    }

    @Test
    public void testFindStock() {
        Stock stock1 = new Stock("DOGE", 1432.43, 0.0649);
        Stock stock2 = new Stock("ADA", 415.32, 0.8796);
        Stock stock3 = new Stock("TSLA", 3, 816.12);
        portfolio.addStock(stock1);
        portfolio.addStock(stock2);
        portfolio.addStock(stock3);
        assertEquals(stock1, portfolio.findStock("DOGE"));
        assertEquals(stock2, portfolio.findStock("ADA"));
        assertEquals(stock3, portfolio.findStock("TSLA"));
        assertEquals(null, portfolio.findStock("GORDON"));
    }

    @Test
    public void testToString() {
        Stock stock1 = new Stock("AAPL", 13, 135.37);
        Stock stock2 = new Stock("DIS", 23, 187.67);
        Stock stock3 = new Stock("NFLX", 5, 556.52);
        stock1.setCurrentPriceCAD(142.43);
        stock2.setCurrentPriceCAD(132.58);
        stock3.setCurrentPriceCAD(632.41);
        portfolio.addStock(stock1);
        portfolio.addStock(stock2);
        portfolio.addStock(stock3);
        String totalStr = stock1.toString() + ", " + stock2.toString() + ", " + stock3.toString();
        assertEquals(totalStr, portfolio.toString());
    }
}