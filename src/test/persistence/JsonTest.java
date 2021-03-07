package persistence;

import model.Stock;

import static org.junit.jupiter.api.Assertions.*;

// References: JsonSerializationDemo
public class JsonTest {
    protected void checkStock(String name, double volume, double initialPriceCAD,
                              double currentPriceCAD, Stock stock) {
        assertEquals(name, stock.getName());
        assertEquals(volume, stock.getVolume());
        assertEquals(initialPriceCAD, stock.getInitialPriceCAD());
        assertEquals(currentPriceCAD, stock.getCurrentPriceCAD());
    }
}
