package model;

import model.exceptions.IllegalNameException;
import model.exceptions.IllegalPriceException;
import model.exceptions.IllegalVolumeException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a stock having a name, volume, initial price in CAD, current price in CAD,
// and current profit generated
// References: JsonSerializationDemo
public class Stock implements Writable {
    private String name;
    private double volume;
    private double initialPriceCAD;
    private double currentPriceCAD;
    private double profit;

    // EFFECTS: stock has given name, volume, and the initial price of the stock in CAD.
    // Also sets current price of the stock to the initial price of the stock in CAD.
    // Throws appropriate exception if there is an illegal input.
    public Stock(String name, double volume, double initialPriceCAD) throws IllegalVolumeException,
            IllegalPriceException, IllegalNameException {
        if (name.length() == 0) {
            throw new IllegalNameException();
        }
        if (volume < 0) {
            throw new IllegalVolumeException();
        }
        if (initialPriceCAD < 0) {
            throw new IllegalPriceException();
        }
        this.name = name;
        this.volume = volume;
        this.initialPriceCAD = initialPriceCAD;
        this.currentPriceCAD = initialPriceCAD;
    }

    // EFFECTS: stock has given name, volume, the initial price of the stock in CAD,
    // the current price of the stock in CAD. Throws appropriate exception if there is an illegal input.
    // This constructor is meant for JSON
    public Stock(String name, double volume, double initialPriceCAD, double currentPriceCAD)
            throws IllegalVolumeException, IllegalPriceException, IllegalNameException {
        if (name.length() == 0) {
            throw new IllegalNameException();
        }
        if (volume < 0) {
            throw new IllegalVolumeException();
        }
        if (initialPriceCAD < 0 || currentPriceCAD < 0) {
            throw new IllegalPriceException();
        }
        this.name = name;
        this.volume = volume;
        this.initialPriceCAD = initialPriceCAD;
        this.currentPriceCAD = currentPriceCAD;
    }

    // EFFECTS: returns name
    public String getName() {
        return name;
    }

    // EFFECTS: returns volume
    public double getVolume() {
        return volume;
    }

    // EFFECTS: returns initial price of the stock in CAD
    public double getInitialPriceCAD() {
        return initialPriceCAD;
    }

    // EFFECTS: returns current price of the stock in CAD
    public double getCurrentPriceCAD() {
        return currentPriceCAD;
    }

    // MODIFIES: this
    // EFFECTS: calculates the current profit generated and returns the profit
    public double getProfit() {
        profit = volume * (currentPriceCAD - initialPriceCAD);
        return profit;
    }

    // MODIFIES: this
    // EFFECTS: sets the current price of the stock in CAD
    public void setCurrentPriceCAD(double currentPriceCAD) {
        this.currentPriceCAD = currentPriceCAD;
    }

    // MODIFIES: this
    // EFFECTS: adds given amount of volume to the total volume if added volume is not a negative number,
    // else will throw an IllegalVolumeException
    public void addVolume(double volume) throws IllegalVolumeException {
        if (volume < 0) {
            throw new IllegalVolumeException();
        } else {
            this.volume += volume;
        }
    }

    // MODIFIES: this
    // EFFECTS: subtracts given amount of volume to the total volume if given volume is not a negative number,
    // or if the given volume is less than the current total volume, else will throw an IllegalVolumeException
    public void subtractVolume(double volume) throws IllegalVolumeException {
        if (volume < 0 || this.volume - volume < 0) {
            throw new IllegalVolumeException();
        }
        this.volume -= volume;
    }

    // EFFECTS: returns a string representation of stock
    @Override
    public String toString() {
        String profitStr = String.format("%.2f", getProfit()); // get profit to 2 decimal places as a string
        String volumeStr = String.format("%.2f", volume); // get volume to 2 decimal places as a string
        if (profit >= 0) {
            return "[" + name + ": +$" + profitStr + ", " + volumeStr + "]";
        } else {
            // properly formats if profit is negative
            return "[" + name + ": -$" + profitStr.substring(1) + ", " + volumeStr + "]";
        }
    }

    // EFFECTS: returns the fields in this stock as a JSON array
    // Source: JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("volume", volume);
        json.put("initialPrice", initialPriceCAD);
        json.put("currentPrice", currentPriceCAD);
        return json;
    }
}
