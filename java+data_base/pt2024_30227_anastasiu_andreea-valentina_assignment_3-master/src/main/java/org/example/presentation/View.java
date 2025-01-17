package org.example.presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class View {
    private JPanel panel1;
    private JButton button2;
    private JButton button1;
    private JButton button3;
    private Produs produsWindow;
    private boolean isDisplayed = false;

    public View() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ceva ceva = null;
                try {
                    ceva = new Ceva();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                ceva.show();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientView clientView = new ClientView();
                clientView.show();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isDisplayed) {
                    produsWindow = new Produs();
                    produsWindow.show();
                    isDisplayed = true;
                }
            }
        });
    }

    public void display() {
        JFrame frame = new JFrame("View");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
