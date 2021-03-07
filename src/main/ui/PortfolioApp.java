package ui;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;

// Smart Stock Portfolio Application
// References: TellerApp, JsonSerializationDemo
public class PortfolioApp {
    private static final String JSON_STORE = "./data/portfolio.json";
    private Portfolio portfolio;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: initializes portfolio
    // Source: TellerApp, JsonSerializationDemo
    public PortfolioApp() throws FileNotFoundException {
        portfolio = new Portfolio();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPortfolio();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // Source: TellerApp
    private void runPortfolio() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = saveOrNot();
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    // Source: TellerApp
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddStock();
        } else if (command.equals("d")) {
            doDeleteStock();
        } else if (command.equals("e")) {
            doEditStock();
        } else if (command.equals("v")) {
            printPortfolio();
        } else if (command.equals("p")) {
            printTotalProfit();
        } else if (command.equals("s")) {
            savePortfolio();
        } else if (command.equals("l")) {
            loadPortfolio();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    // Source: TellerApp
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add a stock");
        System.out.println("\td -> Delete a stock");
        System.out.println("\te -> Edit a stock");
        System.out.println("\tv -> View the portfolio");
        System.out.println("\tp -> View the total profit/loss of the portfolio");
        System.out.println("\ts -> Save portfolio to file");
        System.out.println("\tl -> load portfolio from file");
        System.out.println("\tq -> To quit");
    }

    // MODIFIES: this
    // EFFECTS: reminds user if they would like to save the portfolio to file or not
    private boolean saveOrNot() {
        System.out.println("Would you like to save your portfolio to a file before quitting?");
        System.out.println("\ty -> Yes");
        System.out.println("\tn -> No");
        String command = input.next();
        if (command.equals("y")) {
            savePortfolio();
            return false;
        } else if (command.equals("n")) {
            System.out.println("Your portfolio was not saved...");
            return false;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds a stock to portfolio
    private void doAddStock() {
        System.out.println("What is the name of your stock? (Ticker symbol preferred)");
        String name = input.next();
        System.out.println("How much volume did you buy of the stock?");
        double volume = tryDouble();
        System.out.println("What was the price of the stock at the time of purchase? (in CAD)");
        double initPrice = tryDouble();
        Stock stock = new Stock(name, volume, initPrice);
        portfolio.addStock(stock);
        System.out.println("Thank you, your stock has been added.");
    }

    // MODIFIES: this
    // EFFECTS: deletes a stock from portfolio,
    //          if there is no such stock, prompts to try again or not
    private void doDeleteStock() {
        System.out.println("What is the name of the stock you wish to delete?");
        String name = input.next();
        Stock stock = portfolio.findStock(name);
        if (stock == null) {
            System.out.println("It seems the name of the stock you entered doesn't exist in the portfolio");
            System.out.println("\nTry again?");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            String command = input.next();
            command = command.toLowerCase();
            if (command.equals("y")) {
                doDeleteStock();
            } else {
                System.out.println("Okay...");
            }
        } else {
            portfolio.deleteStock(stock);
            System.out.println("The stock has been deleted from the portfolio.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts an edit to a stock in portfolio,
    //          if there is no such stock, prompts to try again or not
    private void doEditStock() {
        System.out.println("What is the name of the stock you wish to edit?");
        String name = input.next();
        Stock stock = portfolio.findStock(name);
        if (stock == null) {
            System.out.println("It seems the name of the stock you entered doesn't exist in the portfolio");
            System.out.println("\nTry again?");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            String command = input.next();
            command = command.toLowerCase();
            if (command.equals("y")) {
                doEditStock();
            } else {
                System.out.println("Okay...");
            }
        } else {
            doStockCommand(stock);
        }
    }

    // EFFECTS: prints portfolio to the screen
    private void printPortfolio() {
        if (portfolio.length() == 0) {
            System.out.println("It seems that you don't have any stocks in your portfolio. Try adding one first.");
        } else {
            System.out.println(portfolio);
        }
    }

    // EFFECTS: prints total profit/loss of portfolio to the screen
    private void printTotalProfit() {
        String profitStr = String.format("%.2f", portfolio.totalProfit());
        if (portfolio.totalProfit() >= 0) {
            System.out.println("+$" + profitStr);
        } else {
            System.out.println("-$" + profitStr.substring(1)); // properly formats if total profit is negative
        }
    }

    // MODIFIES: this
    // EFFECTS: displays commands and allows for the input to be processed to edit an
    //          individual stock
    private void doStockCommand(Stock stock) {
        System.out.println("\nWhat would you like to edit on " + stock.getName() + "?");
        System.out.println("\ta -> Add volume");
        System.out.println("\ts -> Subtract volume");
        System.out.println("\tv -> View the stock");
        System.out.println("\tu -> Update the current price");
        System.out.println("\tb -> To go back");
        String command = input.next();
        command = command.toLowerCase();
        processIndividualStockCommand(command, stock);
    }

    // EFFECTS: saves the portfolio to file
    // Source: JsonSerializationDemo
    private void savePortfolio() {
        try {
            jsonWriter.open();
            jsonWriter.write(portfolio);
            jsonWriter.close();
            System.out.println("Saved the portfolio to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the portfolio from file
    // Source: JsonSerializationDemo
    private void loadPortfolio() {
        try {
            portfolio = jsonReader.read();
            System.out.println("Loaded the portfolio from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user's edit command for individual stock
    private void processIndividualStockCommand(String command, Stock stock) {
        if (command.equals("a")) {
            doAddVolume(stock);
        } else if (command.equals("s")) {
            doSubtractVolume(stock);
        } else if (command.equals("v")) {
            doPrintStock(stock);
        } else if (command.equals("u")) {
            doSetPrice(stock);
        } else if (command.equals("b")) {
            System.out.println("Okay...");
        } else {
            System.out.println("Selection not valid...");
            System.out.println("\nTry again?");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            String commandTryAgain = input.next();
            commandTryAgain = commandTryAgain.toLowerCase();
            if (commandTryAgain.equals("y")) {
                doStockCommand(stock);
            } else {
                System.out.println("Okay...");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: add volume to given stock,
    //          if the input is valid, prompts to try again or not
    private void doAddVolume(Stock stock) {
        System.out.println("How much volume would you like to add to the stock?");
        double volume = tryDouble();
        if (volume < 0) {
            System.out.println("Sorry, your input is invalid. The number is negative. Try again please.");
            doAddVolume(stock);
        } else {
            stock.addVolume(volume);
            System.out.println("Done...");
        }
        doStockCommand(stock);
    }

    // MODIFIES: this
    // EFFECTS: subtracts volume from given stock,
    //          if the input is valid, prompts to try again or not
    private void doSubtractVolume(Stock stock) {
        System.out.println("How much volume would you like to subtract from the stock?");
        double volume = tryDouble();
        if (volume > stock.getVolume()) {
            System.out.println("Sorry, your input is invalid. The number is too great. Try again please.");
            doSubtractVolume(stock);
        } else {
            stock.subtractVolume(volume);
            System.out.println("Done...");
        }
        doStockCommand(stock);
    }

    // EFFECTS: prints stock to the screen
    private void doPrintStock(Stock stock) {
        System.out.println(stock);
        doStockCommand(stock);
    }

    // MODIFIES: this
    // EFFECTS: sets the current price of the stock to an amount
    private void doSetPrice(Stock stock) {
        System.out.println("What is the current price of the stock? (in CAD)");
        double currentPrice = tryDouble();
        stock.setCurrentPriceCAD(currentPrice);
        System.out.println("Done...");
        doStockCommand(stock);
    }

    // EFFECTS: Try and catch to see if input is valid, if valid, returns input, otherwise try again
    private double tryDouble() {
        double value;
        try {
            value = input.nextDouble();
            return value;
        } catch (Exception e) {
            System.out.println("Error! You inputted something other than a number! Try again please.");
            String throwAway = input.next(); // So the useless input would not feed into the next useful input
        }
        return tryDouble();
    }
}
