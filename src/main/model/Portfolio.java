package model;

import model.exceptions.DuplicateStockException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.LinkedList;

// Represents a portfolio that can hold multiple stocks
// References: JsonSerializationDemo
public class Portfolio implements Writable {
    private LinkedList<Stock> portfolio;

    // EFFECTS: an empty portfolio
    public Portfolio() {
        portfolio = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add given stock to portfolio if the given stock's name is not the same to any other stock
    // in the portfolio already, else will throw a DuplicateStockException
    public void addStock(Stock stock) throws DuplicateStockException {
        for (Stock s : portfolio) {
            if (s.getName().equals(stock.getName())) {
                throw new DuplicateStockException();
            }
        }
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

    // EFFECTS: returns the stocks in portfolio
    public LinkedList<Stock> getPortfolio() {
        return portfolio;
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

    // EFFECTS: returns the fields in this portfolio as a JSON array
    // Source: JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("stocks", stocksToJson());
        return json;
    }

    // EFFECTS: returns stocks in this Portfolio as a JSON array
    // Source: JsonSerializationDemo
    private JSONArray stocksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Stock s : portfolio) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}
