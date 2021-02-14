package model;

import java.util.LinkedList;

public class Portfolio {
    private LinkedList<Stock> portfolio;

    // EFFECTS: an empty portfolio
    public Portfolio() {
        portfolio = new LinkedList<>();
    }

    // REQUIRES: stock.getName() must be unique compared to other stock in portfolio
    // MODIFIES: this
    // EFFECTS: add given stock to portfolio
    public void addStock(Stock stock) {
        portfolio.add(stock);
    }

    // MODIFIES: this
    // EFFECTS: deletes given stock from portfolio
    public void deleteStock(Stock stock) {
        portfolio.remove(stock);
    }

    // EFFECTS: calculates the total profit generated in portfolio
    public double totalProfit() {
        double totalProfit = 0.0;
        for (Stock stock : portfolio) {
            totalProfit += stock.getProfit();
        }
        return totalProfit;
    }

    // EFFECTS: returns the stock with the given name, otherwise null if it doesn't exists
    public Stock findStock(String name) {
        for (Stock stock : portfolio) {
            if (stock.getName().equals(name)) {
                return stock;
            }
        }
        return null;
    }

    // EFFECTS: returns the number of stocks in portfolio
    public int length() {
        return portfolio.size();
    }

    // EFFECTS: returns a string representation of portfolio
    @Override
    public String toString() {
        String str = "";
        for (Stock stock : portfolio) {
            str = str + stock.toString() + ", ";
        }
        return str.substring(0, str.length() - 2); // get rids of the ", " at the end
    }
}
