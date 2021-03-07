package persistence;

import model.Stock;
import model.Portfolio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads portfolio from JSON data stored in file
// References: JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Portfolio from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Portfolio read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePortfolio(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Portfolio from JSON object and returns it
    private Portfolio parsePortfolio(JSONObject jsonObject) {
        Portfolio portfolio = new Portfolio();
        addStocks(portfolio, jsonObject);
        return portfolio;
    }

    // MODIFIES: portfolio
    // EFFECTS: parses stocks from JSON object and adds them to Portfolio
    private void addStocks(Portfolio portfolio, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("stocks");
        for (Object json : jsonArray) {
            JSONObject nextStock = (JSONObject) json;
            addStock(portfolio, nextStock);
        }
    }

    // MODIFIES: portfolio
    // EFFECTS: parses stock from JSON object and adds it to Portfolio
    private void addStock(Portfolio portfolio, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double volume = jsonObject.getDouble("volume");
        double initialPriceCAD = jsonObject.getDouble("initialPrice");
        double currentPriceCAD = jsonObject.getDouble("currentPrice");
        Stock stock = new Stock(name, volume, initialPriceCAD, currentPriceCAD);
        portfolio.addStock(stock);
    }
}
