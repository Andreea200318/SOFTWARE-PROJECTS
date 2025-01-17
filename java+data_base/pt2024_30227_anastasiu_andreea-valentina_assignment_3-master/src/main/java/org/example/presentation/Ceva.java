package org.example.presentation;

import org.example.bll.OrderBll;
import org.example.model.Client;
import org.example.model.Produs;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * interfata pentru plasare comanda
 */
public class Ceva {
    private JComboBox<Produs> comboBoxProduct;
    private JComboBox<Client> comboBoxClient;
    private JTextField textFieldQuantity;
    private JButton finalizeOrderButton;
    private JPanel mainPanel;
    private OrderBll orderBll;

    public Ceva() throws SQLException {
        orderBll = new OrderBll();
        initComponents();
        addOrderButtonListener();
    }

    private void initComponents() throws SQLException {
        // Initialize JComboBox for products
        List<Produs> products = orderBll.getAllProducts();
        for (Produs product : products) {
            comboBoxProduct.addItem(product);
        }

        // Initialize JComboBox for clients
        List<Client> clients = orderBll.getAllClients();
        for (Client client : clients) {
            comboBoxClient.addItem(client);
        }
    }

    private void addOrderButtonListener() {
        finalizeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        });
    }

    private void placeOrder() {
        Produs selectedProduct = (Produs) comboBoxProduct.getSelectedItem();
        Client selectedClient = (Client) comboBoxClient.getSelectedItem();
        try {
            int quantity = Integer.parseInt(textFieldQuantity.getText());
            orderBll.placeOrder(selectedProduct.getId(), selectedClient.getId(), quantity);
            JOptionPane.showMessageDialog(null, "Order s-a plasat cu succes!!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Pune o cantitate valida.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Err: " + ex.getMessage(), "Err", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void show() {
        JFrame frame = new JFrame("Pune o comanda");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


  /* public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Place an Order");
        frame.setContentPane(new Ceva().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }*/
}
