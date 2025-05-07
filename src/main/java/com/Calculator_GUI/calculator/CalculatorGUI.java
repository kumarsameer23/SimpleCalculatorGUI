package com.Calculator_GUI.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalculatorGUI {
    private final Calculator calculator = new Calculator();
    private JTextField display;

    public CalculatorGUI() {
        JFrame frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(this::handleButtonClick);
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void handleButtonClick(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "=" -> calculateResult();
            case "C" -> display.setText("");
            default -> display.setText(display.getText() + command);
        }
    }

    private void calculateResult() {
        try {
            String expression = display.getText();
            double result = evaluateExpression(expression);
            display.setText(String.valueOf(result));
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private double evaluateExpression(String expr) {
        String[] parts = expr.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
        double a = Double.parseDouble(parts[0]);
        double b = Double.parseDouble(parts[2]);
        
        return switch (parts[1]) {
            case "+" -> calculator.add(a, b);
            case "-" -> calculator.subtract(a, b);
            case "*" -> calculator.multiply(a, b);
            case "/" -> calculator.divide(a, b);
            default -> throw new IllegalArgumentException("Invalid operator");
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorGUI::new);
    }
}
