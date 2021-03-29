package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Individual Stock (Graphical)
// References: SimpleDrawingPlayer, IntersectionGUI, StackOverflow
public class StockEditGraphics extends PortfolioAppGraphics {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    private static final int FONT_SIZE = 25;

    private Stock stock;
    private JPanel inputPanel;
    private JPanel visualPanel;
    private JPanel textPanel;
    private JLabel textBox;
    private JLabel volume;
    private JLabel profit;
    private JLabel initPrice;

    // EFFECTS: initializes stock graphically
    public StockEditGraphics(Stock stock) {
        super(stock.getName(), "");
        this.stock = stock;
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initialize graphics
    // Source: SimpleDrawingPlayer
    private void initializeGraphics() {
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputButtons();
        initializeVisuals();
        getContentPane().add(inputPanel, "East");
        getContentPane().add(textPanel, "South");
        getContentPane().add(visualPanel, "Center");
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    // Source: IntersectionGUI
    private void inputButtons() {
        inputPanel = new JPanel(new GridLayout(4, 1));
        visualPanel = new JPanel(new GridLayout(3, 1));
        addVolumeButton();
        subtractVolumeButton();
        updatePriceButton();
        exitButton();
        textInputBox();
    }

    // MODIFIES: this
    // EFFECTS: initializes the individual stock graphics
    private void initializeVisuals() {
        visualPanel = new JPanel(new GridLayout(3, 1));
        volume = new JLabel("      Volume: " + (stock.getVolume()));
        profit = new JLabel("      Profit: $" + (stock.getProfit()));
        initPrice = new JLabel("      Initial price: $" + (stock.getInitialPriceCAD()));
        volume.setFont(new Font(volume.getName(), Font.PLAIN, FONT_SIZE));
        profit.setFont(new Font(profit.getName(), Font.PLAIN, FONT_SIZE));
        initPrice.setFont(new Font(initPrice.getName(), Font.PLAIN, FONT_SIZE));
        visualPanel.add(volume);
        visualPanel.add(profit);
        visualPanel.add(initPrice);
    }

    // MODIFIES: this
    // EFFECTS: creates the add volume button
    private void addVolumeButton() {
        JButton addButton = new JButton("Add volume");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(BUTTON_SOUND);
                double addVolume = tryDouble("How much volume would you like to add to the stock?");
                stock.addVolume(addVolume);
                volume.setText("      Volume: " + stock.getVolume());
                profit.setText("      Profit: " + formatMoney(stock.getProfit()));
                textBox.setText("Added the new volume.");
            }
        });
        inputPanel.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the subtract volume button
    private void subtractVolumeButton() {
        JButton addButton = new JButton("Subtract volume");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(BUTTON_SOUND);
                double subVolume = tryDouble("How much volume would you like to subtract from the stock?");
                stock.subtractVolume(subVolume);
                volume.setText("      Volume: " + (stock.getVolume()));
                profit.setText("      Profit: " + formatMoney(stock.getProfit()));
                textBox.setText("Subtracted volume.");
            }
        });
        inputPanel.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the update price button
    private void updatePriceButton() {
        JButton addButton = new JButton("Update current price");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(BUTTON_SOUND);
                double curPrice = tryDouble("What is the current price of the stock? (in CAD)");
                stock.setCurrentPriceCAD(curPrice);
                profit.setText("      Profit: " + formatMoney(stock.getProfit()));
            }
        });
        inputPanel.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the exit button
    private void exitButton() {
        JButton addButton = new JButton("Go back");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(BUTTON_SOUND);
                setVisible(false);
                dispose();
            }
        });
        inputPanel.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the text box
    private void textInputBox() {
        textPanel = new JPanel(new GridLayout(1, 1));
        textBox = new JLabel("Messages here!");
        textPanel.add(textBox);
    }
}
