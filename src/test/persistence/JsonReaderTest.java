package persistence;

import model.Portfolio;
import model.Stock;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

// References: JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Portfolio portfolio = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyPortfolio() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPortfolio.json");
        try {
            Portfolio portfolio = reader.read();
            assertEquals(0, portfolio.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralPortfolio() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPortfolio.json");
        try {
            Portfolio portfolio = reader.read();
            LinkedList<Stock> stocks = portfolio.getPortfolio();
            assertEquals(2, stocks.size());
            checkStock("ETH", 3.42, 1985, 1985, stocks.get(0));
            checkStock("DOGE", 564.78, 0.0542, 0.0645, stocks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // Won't ever receive a bad JSON stock unless manually put
    @Test
    public void testReaderBadPortfolio() {
        JsonReader reader = new JsonReader("./data/testReaderBadPortfolio.json");
        try {
            Portfolio portfolio = reader.read();
            // Will get "Bad input" from System.out.print
            LinkedList<Stock> stocks = portfolio.getPortfolio();
            // Supposed to have added two stocks but one failed so it was not added (good thing)
            assertEquals(1, stocks.size());
            checkStock("CLR", 144.23, 0.064, 0.077, stocks.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
