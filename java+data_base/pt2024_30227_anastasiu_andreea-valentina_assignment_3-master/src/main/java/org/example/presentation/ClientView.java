package org.example.presentation;

import org.example.bll.ClientBll;
import org.example.model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;

/**
 * interfata pentru clienti
 */
public class ClientView {
    private static final Logger log= Logger.getLogger(ClientView.class.getName());
    private JTable table1;
    private JButton ADDCLIENTButton;
    private JButton EDITCLIENTButton;
    private JButton DELETECLIENTButton;
    private JPanel mainPanel;
    private ClientBll clientBll = new ClientBll();

    public ClientView() {

//        JFrame frame = new JFrame("Client Management");
//        frame.setContentPane(mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setSize(500, 400);
//        frame.setVisible(true);

        loadClients();

        ADDCLIENTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });

        EDITCLIENTButton.addActionListener(e -> editClient());

        DELETECLIENTButton.addActionListener(e -> deleteClient());
    }

    private void addClient() {
        try {
            JTextField nameField = new JTextField(5);
            JTextField emailField = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Name:"));
            myPanel.add(nameField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Email:"));
            myPanel.add(emailField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Pune numele si email", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Client client = new Client(0, nameField.getText(), emailField.getText()); // Assuming id is auto-generated
                try {
                    clientBll.insert(client);
                    loadClients();  // Refresh the client list
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error adaugare client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error adaugare client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editClient() {
        try {
            int row = table1.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Selecteaza clientul pt a edita", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int clientId = Integer.parseInt(table1.getValueAt(row, 0).toString());
            Client existingClient = clientBll.findStudentById(clientId);

            JTextField nameField = new JTextField(5);
            nameField.setText(existingClient.getNume());
            JTextField emailField = new JTextField(5);
            emailField.setText(existingClient.getEmail());

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Name:"));
            myPanel.add(nameField);
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(new JLabel("Email:"));
            myPanel.add(emailField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Edit Client ID: " + clientId, JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                existingClient.setNume(nameField.getText());
                existingClient.setEmail(emailField.getText());
                try {
                    clientBll.update(existingClient, clientId);
                    loadClients();  // Refresh the client list
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "err editare client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }catch (Exception ex){
            log.severe("edit client esuat: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "err editare client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteClient() {
        try {
            int row = table1.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "selecteaza clientul pt a-l sterge", "Warning", JOptionPane.WARNING_MESSAGE);
                return;

            }
            int clientId = Integer.parseInt(table1.getValueAt(row, 0).toString());
            clientBll.delete(clientId);
            loadClients(); // Refresh table data
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "err delete client: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadClients() {
        List<Client> clients = clientBll.findAll();
        String[][] data = new String[clients.size()][3];
        for (int i = 0; i < clients.size(); i++) {
            data[i][0] = String.valueOf(clients.get(i).getId());
            data[i][1] = clients.get(i).getNume();
            data[i][2] = clients.get(i).getEmail();
        }

        String[] columnNames = {"id", "Name", "Email"};
        table1.setModel(new DefaultTableModel(data, columnNames));
    }
    public void show() {
        JFrame frame = new JFrame("Client Management");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(500, 400);
        frame.setVisible(true);
    }




    /*public static void main(String[] args) {
        new ClientView();
    }*/
}
