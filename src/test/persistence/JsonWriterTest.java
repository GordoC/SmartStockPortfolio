package persistence;

import model.Portfolio;
import model.Stock;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// References: JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Portfolio portfolio = new Portfolio();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPortfolio() {
        try {
            Portfolio portfolio = new Portfolio();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPortfolio.json");
            writer.open();
            writer.write(portfolio);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPortfolio.json");
            portfolio = reader.read();
            assertEquals(0, portfolio.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPortfolio() {
        try {
            Portfolio portfolio = new Portfolio();
            portfolio.addStock(new Stock("AAPL", 14, 158, 132));
            portfolio.addStock(new Stock("TSLA", 9, 749));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPortfolio.json");
            writer.open();
            writer.write(portfolio);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPortfolio.json");
            portfolio = reader.read();
            LinkedList<Stock> stocks = portfolio.getPortfolio();
            assertEquals(2, stocks.size());
            checkStock("AAPL", 14, 158, 132, stocks.get(0));
            checkStock("TSLA", 9, 749, 749, stocks.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
