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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;

// Smart Stock Portfolio Application (Graphical)
// References: SimpleDrawingPlayer, IntersectionGUI, StackOverflow, SuaveSnippets
public class PortfolioAppGraphics extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    private static final String JSON_STORE = "./data/portfolio.json";

    private static final String STOCK_IMAGE = "./data/stock.Default";
    protected static final String BUTTON_SOUND = "./data/click.wav";

    private Portfolio portfolio;
    private Stock stockToDelete;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel inputPanel;
    private JPanel stocksPanel;
    private JPanel textPanel;
    private JLabel textBox;
    private JFrame frameYesOrNo;
    private JPanel panelYesOrNo;
    private JLabel text;

    // EFFECTS: initializes portfolio graphically
    // Source: SimpleDrawingPlayer, IntersectionGUI
    public PortfolioAppGraphics(String name, String action) {
        super(name);
        if (action.equals("start")) {
            try {
                initializeFields();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to run application: file not found");
            }
            initializeGraphics();
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize fields
    // Source: SimpleDrawingPlayer
    private void initializeFields() throws FileNotFoundException {
        portfolio = new Portfolio();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        inputPanel = new JPanel(new GridLayout(5, 1));
        stocksPanel = new JPanel();
        textPanel = new JPanel(new GridLayout(1, 1));
        textBox = new JLabel("Messages here!");
        frameYesOrNo = new JFrame();
        panelYesOrNo = new JPanel();
        text = new JLabel("");
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
        getContentPane().add(stocksPanel);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    // Source: IntersectionGUI
    private void inputButtons() {
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
                playSound(BUTTON_SOUND);
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
        Boolean badName = false;
        for (Stock stock : portfolio.getPortfolio()) {
            if (stock.getName().equals(stockInput.getName())) {
                JOptionPane.showMessageDialog(
                        inputPanel,
                        "The stock you inputted is already in the portfolio so it was not added."
                );
                badName = true;
            }
        }
        if (stockInput.getName().equals("")) {
            JOptionPane.showMessageDialog(
                    inputPanel,
                    "The stock you inputted has no name so it was not added."
            );
            badName = true;
        }
        if (!badName) {
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
                playSound(BUTTON_SOUND);
                String name = JOptionPane.showInputDialog(
                        inputPanel,
                        "What is the name of the stock you wish to delete?",
                        null
                );
                stockToDelete = portfolio.findStock(name);
                if (stockToDelete == null) {
                    JOptionPane.showMessageDialog(
                            inputPanel,
                            "That stock doesn't exist"
                    );
                } else {
                    yesOrNo("Are you sure you want to delete " + stockToDelete.getName() + "?", "delete");
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
                playSound(BUTTON_SOUND);
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
                playSound(BUTTON_SOUND);
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
                playSound(BUTTON_SOUND);
                yesOrNo("Would you like to save your portfolio to a file before quitting?", "save");
                setVisible(false);
                dispose();
            }
        });
        inputPanel.add(quitButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the text box
    private void textInputBox() {
        textPanel.add(textBox);
    }

    // MODIFIES: this
    // EFFECTS: creates the individual stock button
    private void createStockButton(Stock stock) {
        JButton stockBox = new JButton(stock.getName());
        ImageIcon icon = new ImageIcon(STOCK_IMAGE);
        Image img = icon.getImage().getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        stockBox.setIcon(new ImageIcon(img));
        stockBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(BUTTON_SOUND);
                new StockEditGraphics(stock);
            }
        });
        stocksPanel.add(stockBox);
        stocksPanel.validate();
        stocksPanel.repaint();
    }

    // EFFECTS: Try and catch to see if input is valid double, if valid, returns input, otherwise try again
    protected double tryDouble(String msg) {
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

    // MODIFIES: this
    // EFFECTS: Asks user if they would like to save the portfolio or not
    private void yesOrNo(String msg, String function) {
        frameYesOrNo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(BUTTON_SOUND);
                callFunction(function);
                disappearYesOrNo();
            }
        });
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(BUTTON_SOUND);
                disappearYesOrNo();
            }
        });
        text.setText(msg);
        addToYesOrNo(yesButton, noButton);
        frameYesOrNo.pack();
        frameYesOrNo.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: makes frameYesOrNo disappear
    private void disappearYesOrNo() {
        frameYesOrNo.setVisible(false);
        frameYesOrNo.dispose();
    }

    // MODIFIES: this
    // EFFECTS: adds components in yes or no popup
    private void addToYesOrNo(JButton yes, JButton no) {
        panelYesOrNo.removeAll();
        frameYesOrNo.getContentPane().add(text, "North");
        panelYesOrNo.add(yes, "West");
        panelYesOrNo.add(no, "East");
        frameYesOrNo.getContentPane().add(panelYesOrNo, "South");
    }

    // MODIFIES: this
    // EFFECTS: depending on the inputted string, will call the appropriate function
    private void callFunction(String fn) {
        if (fn.equals("save")) {
            save();
        } else if (fn.equals("delete")) {
            portfolio.deleteStock(stockToDelete);
            updateStocksPanel();
        }
    }

    // EFFECTS: plays the audio clip with name inputted, will catch exception if audio name doesn't exists
    // Source: SuaveSnippets
    protected void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}
