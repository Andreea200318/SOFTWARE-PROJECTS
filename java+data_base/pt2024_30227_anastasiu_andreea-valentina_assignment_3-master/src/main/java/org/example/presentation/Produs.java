package org.example.presentation;

import org.example.bll.ProdusBll;
import org.example.model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;

/**
 * interfata pentru produse
 */

public class Produs {
    private static final Logger log= Logger.getLogger(ClientView.class.getName());
    private JTable table1;
    private JPanel panel1;
    private JButton EDITButton;
    private JButton DELETEPRODUSButton;
    private JButton ADDPRODUSButton;
    private ProdusBll prodbll=new ProdusBll();

    public Produs() {
//        JFrame frame = new JFrame();
//        frame.setTitle("Produs");
//        frame.setContentPane(panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setSize(500,400);
//        frame.setVisible(true);
        loadProd();
        ADDPRODUSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {addProd();}

        });

        EDITButton.addActionListener(e -> editProd());

        DELETEPRODUSButton.addActionListener(e -> deleteProd());

    }
    private void addProd() {
        try {
            JTextField nameField = new JTextField(5);
            JTextField pret = new JTextField(5);
            JTextField cantitate = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Name:"));
            myPanel.add(nameField);
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(new JLabel("Pret:"));
            myPanel.add(pret);
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(new JLabel("Cantitate:"));
            myPanel.add(cantitate);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Aduga Nume, Pret, and Cantitate", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText();
                double price = Double.parseDouble(pret.getText());
                int quantity = Integer.parseInt(cantitate.getText());


                org.example.model.Produs p = new org.example.model.Produs(1, name, quantity, price);
                try {
                    prodbll.insert(p);
                    loadProd(); // Refresh the produs list
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Err adaug produs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Err adaug produs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void editProd() {
        try {
            int row = table1.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Selecteaza un produs pt a-l edita", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int prodId = Integer.parseInt(table1.getValueAt(row, 0).toString());
            org.example.model.Produs existingprodus = prodbll.findStudentById(prodId); // This method's name seems a bit off, consider renaming it to findClientById in your BLL

            JTextField nameField = new JTextField(5);
            nameField.setText(existingprodus.getNume());
            JTextField pret = new JTextField(5);
            pret.setText(String.valueOf(existingprodus.getPret()));
            JTextField cantitate = new JTextField(5);
            cantitate.setText(String.valueOf(existingprodus.getCantitate()));

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Name:"));
            myPanel.add(nameField);
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(new JLabel("Pret:"));
            myPanel.add(pret);
            JPanel myPanel2 = new JPanel();
            myPanel2.add(new JLabel("Cantitate:"));
            myPanel.add(cantitate);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Edit Produs ID: " + prodId, JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                existingprodus.setNume(nameField.getText());
                existingprodus.setCantitate(Integer.parseInt(cantitate.getText()));
                existingprodus.setPret(Double.parseDouble(pret.getText()));

                try {
                    prodbll.update(existingprodus, prodId);
                    loadProd(); // Refresh the client list
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error edit produs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }catch(Exception ex){
            log.severe("edit prod a dat gres"+ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error edit produs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void deleteProd() {
        try {
            int row = table1.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(null, "Selecteaza un produs pt a-l sterge", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int clientId = Integer.parseInt(table1.getValueAt(row, 0).toString());
            prodbll.delete(clientId);
            loadProd(); // Refresh table data
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Err stergere produs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void loadProd(){
        List<org.example.model.Produs> produs=prodbll.findAll();
        String[][] data=new String[((List<?>)produs).size()][4];
        for(int i=0;i<produs.size();i++){
            data[i][0]=String.valueOf(produs.get(i).getId());
            data[i][1]=produs.get(i).getNume();
            data[i][2]=String.valueOf(produs.get(i).getPret());
            data[i][3]=String.valueOf(produs.get(i).getCantitate());

        }
        String[] numecol={"Prod id","Prod name","Prod price","Prod cantitate"};
        table1.setModel(new DefaultTableModel(data,numecol));
    }
    public void show() {
        JFrame frame = new JFrame();
        frame.setTitle("Produs");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(500,400);
        frame.setVisible(true);
    }

/*public static void main(String[] args) {
new Produs();
}*/
}