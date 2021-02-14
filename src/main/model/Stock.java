package model;

// Represents a stock having a name, volume, initial price in CAD, current price in CAD,
// and current profit generated
public class Stock {
    private String name;
    private double volume;
    private double initialPriceCAD;
    private double currentPriceCAD;
    private double profit;

    // REQUIRES: given volume and initialPriceCAD is not negative. Also given name has a non-zero length
    // EFFECTS: stock has given name, volume, and the initial price of the stock in CAD.
    // Also sets current price of the stock to the initial price of the stock in CAD
    public Stock(String name, double volume, double initialPriceCAD) {
        this.name = name;
        this.volume = volume;
        this.initialPriceCAD = initialPriceCAD;
        this.currentPriceCAD = initialPriceCAD;
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

    // REQUIRES: given volume >= 0
    // MODIFIES: this
    // EFFECTS: adds given amount of volume to the total volume
    public void addVolume(double volume) {
        this.volume += volume;
    }

    // REQUIRES: given volume >= 0 and <= this.volume
    // MODIFIES: this
    // EFFECTS: subtracts given amount of volume to the total volume
    public void subtractVolume(double volume) {
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
}
