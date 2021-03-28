package ui;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Smart Stock Portfolio Application (Graphical)
// References: SimpleDrawingPlayer, IntersectionGUI
public class PortfolioAppGraphics extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    private static final String JSON_STORE = "./data/portfolio.json";

    private Portfolio portfolio;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel inputPanel;
    private JPanel stocksPanel;
    private JPanel textPanel;
    private JLabel textBox;
    private JScrollPane sp;

    // EFFECTS: initializes portfolio graphically
    // Source: SimpleDrawingPlayer, IntersectionGUI
    public PortfolioAppGraphics() {
        super("My Portfolio");
        try {
            initializeFields();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initialize fields
    // Source: SimpleDrawingPlayer
    private void initializeFields() throws FileNotFoundException {
        portfolio = new Portfolio();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initialize graphics
    // Source: SimpleDrawingPlayer
    private void initializeGraphics() {
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputButtons();
        getContentPane().add(inputPanel, "East");
        getContentPane().add(textPanel, "South");
        sp = new JScrollPane(
                stocksPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        getContentPane().add(sp);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    // Source: IntersectionGUI
    private void inputButtons() {
        inputPanel = new JPanel(new GridLayout(5, 1));
        stocksPanel = new JPanel(new GridLayout(10, 4));
        addButton();
        deleteButton();
        saveButton();
        loadButton();
        quitButton();
        textInputBox();
    }

    // MODIFIES: this
    // EFFECTS: creates the add stock button
    // Source: IntersectionGUI
    private void addButton() {
        JButton addButton = new JButton("Add stock");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(
                        inputPanel,
                        "What is the name of your stock? (Ticker symbol preferred)",
                        null
                );
                double volume = tryDouble("How much volume did you buy of the stock?");
                double initPrice = tryDouble("What was the price of the stock at the time of purchase? (in CAD)");
                Stock stock = new Stock(name, volume, initPrice);
                stockExistAlready(stock);
            }
        });
        inputPanel.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: finds if inputted stock already exists
    private void stockExistAlready(Stock stockInput) {
        Boolean found = false;
        for (Stock stock : portfolio.getPortfolio()) {
            if (stock.getName().equals(stockInput.getName())) {
                JOptionPane.showMessageDialog(
                        inputPanel,
                        "The stock you inputted is already in the portfolio so it was not added."
                );
                found = true;
            }
        }
        if (!found) {
            portfolio.addStock(stockInput);
            createStockButton(stockInput);
            textBox.setText("Thank you, your stock has been added.");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the delete stock button
    // Source: IntersectionGUI
    private void deleteButton() {
        JButton deleteButton = new JButton("Delete stock");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(
                        inputPanel,
                        "What is the name of the stock you wish to delete?",
                        null
                );
                Stock stock = portfolio.findStock(name);
                if (stock == null) {
                    JOptionPane.showMessageDialog(
                            inputPanel,
                            "That stock doesn't exist"
                    );
                } else {
                    portfolio.deleteStock(stock);
                    updateStocksPanel();
                    textBox.setText("The stock has been deleted from the portfolio.");
                }
            }
        });
        inputPanel.add(deleteButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the save portfolio button
    // Source: IntersectionGUI
    private void saveButton() {
        JButton saveButton = new JButton("Save portfolio");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        inputPanel.add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the load portfolio button
    // Source: IntersectionGUI
    private void loadButton() {
        JButton loadButton = new JButton("Load portfolio");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    portfolio = jsonReader.read();
                    updateStocksPanel();
                    textBox.setText("Loaded the portfolio from " + JSON_STORE);
                } catch (IOException exception) {
                    textBox.setText("Unable to read from file: " + JSON_STORE);
                }
            }
        });
        inputPanel.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the quit button
    // Source: IntersectionGUI
    private void quitButton() {
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String answer = JOptionPane.showInputDialog(
                        inputPanel,
                        "Would you like to save your portfolio to a file before quitting?\ny -> Yes\nn -> No",
                        null
                );
                if (answer.equals("y")) {
                    save();
                }
                setVisible(false);
                dispose();
            }
        });
        inputPanel.add(quitButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the text box
    private void textInputBox() {
        textPanel = new JPanel(new GridLayout(1, 1));
        textBox = new JLabel("Messages here!");
        textPanel.add(textBox);
    }

    // MODIFIES: this
    // EFFECTS: creates the individual stock button
    private void createStockButton(Stock stock) {
        JButton stockBox = new JButton(stock.getName());
        stockBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StockEditGraphics stockGraphics = new StockEditGraphics(stock);
            }
        });
        stocksPanel.add(stockBox);
    }

    // EFFECTS: Try and catch to see if input is valid double, if valid, returns input, otherwise try again
    private double tryDouble(String msg) {
        try {
            String value = JOptionPane.showInputDialog(inputPanel, msg, null);
            return Double.parseDouble(value);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    inputPanel,
                    "What you inputted was not a number, try again."
            );
        }
        return tryDouble(msg);
    }

    // EFFECTS: saves portfolio to JSON_STORE
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(portfolio);
            jsonWriter.close();
            textBox.setText("Saved the portfolio to " + JSON_STORE);
        } catch (FileNotFoundException exception) {
            textBox.setText("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: updates the stocks panel
    private void updateStocksPanel() {
        stocksPanel.removeAll();
        stocksPanel.repaint();
        for (Stock stock : portfolio.getPortfolio()) {
            createStockButton(stock);
        }
    }
}
