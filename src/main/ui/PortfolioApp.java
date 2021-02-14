package ui;

import model.*;

import java.util.Scanner;

// Smart Stock Portfolio Application
// References: TellerApp
public class PortfolioApp {
    private Portfolio portfolio;
    private Scanner input;

    public PortfolioApp() {
        runPortfolio();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runPortfolio() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
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
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add a stock");
        System.out.println("\td -> Delete a stock");
        System.out.println("\te -> Edit a stock");
        System.out.println("\tv -> View the portfolio");
        System.out.println("\tp -> View the total profit/loss of the portfolio");
        System.out.println("\tq -> To quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes portfolio
    private void init() {
        portfolio = new Portfolio();
        input = new Scanner(System.in);
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
    // EFFECTS: deletes a stock from portfolio
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
    // EFFECTS: conducts an edit to a stock in portfolio
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
        System.out.println(portfolio);
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

    // EFFECTS: displays commands and allows for the input to be processed
    private void doStockCommand(Stock stock) {
        System.out.println("\nWhat would you like to edit?");
        System.out.println("\ta -> Add volume");
        System.out.println("\ts -> Subtract volume");
        System.out.println("\tv -> View the stock");
        System.out.println("\tu -> Update the current price");
        System.out.println("\tq -> To quit");
        String command = input.next();
        command = command.toLowerCase();
        processIndividualStockCommand(command, stock);
    }

    // MODIFIES: this
    // EFFECTS: processes users command for individual stock
    private void processIndividualStockCommand(String command, Stock stock) {
        if (command.equals("a")) {
            doAddVolume(stock);
        } else if (command.equals("s")) {
            doSubtractVolume(stock);
        } else if (command.equals("v")) {
            doPrintStock(stock);
        } else if (command.equals("u")) {
            doSetPrice(stock);
        } else if (command.equals("q")) {
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
    // EFFECTS: add volume to given stock
    private void doAddVolume(Stock stock) {
        System.out.println("How much volume would you like to add to the stock?");
        double volume = tryDouble();
        stock.addVolume(volume);
        System.out.println("Done...");
    }

    // MODIFIES: this
    // EFFECTS: subtracts volume from given stock
    private void doSubtractVolume(Stock stock) {
        System.out.println("How much volume would you like to subtract from the stock?");
        double volume = tryDouble();
        if (volume > stock.getVolume()) {
            System.out.println("Sorry, you're input is invalid. The number is too great.");
        } else {
            stock.subtractVolume(volume);
            System.out.println("Done...");
        }
    }

    // MODIFIES: this
    // EFFECTS: prints stock to the screen
    private void doPrintStock(Stock stock) {
        System.out.println(stock);
    }

    // MODIFIES: this
    // EFFECTS: sets the current price of the stock to an amount
    private void doSetPrice(Stock stock) {
        System.out.println("What is the current price of the stock?");
        double currentPrice = tryDouble();
        stock.setCurrentPriceCAD(currentPrice);
        System.out.println("Done...");
    }

    // EFFECTS: Try and catch to see if input is valid, if valid, returns input, otherwise try again
    private double tryDouble() {
        double value;
        try {
            value = input.nextDouble();
            return value;
        } catch (Exception e) {
            System.out.println("Error! You inputted something other than a number! Try again please.");
            String throwAway = input.next(); // Just so that the useless input would not feed into a useful input
        }
        return tryDouble();
    }
}
