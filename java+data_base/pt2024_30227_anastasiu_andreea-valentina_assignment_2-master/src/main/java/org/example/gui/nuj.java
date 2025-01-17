package org.example.gui;

import org.example.business_logic.SelectionPolicy;
import org.example.business_logic.SimulationManager;

import javax.swing.*;

public class nuj extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField2;
    private JTextField textField7;
    private JButton startButton;
    private JRadioButton SHORTEST_QUEUERadioButton;
    private JRadioButton SHORTEST_TIMERadioButton;
    private JPanel panel111;
    private JTextArea textArea1;
    public JTextArea getTextArea1() {
        return textArea1;
    }



    private SimulationManager simulationManager; // Adăugăm o referință la obiectul SimulationManager

    public nuj() {
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ex");
        //anel111.add(scroll);
        setContentPane(panel111);
        setVisible(true);
        pack();
        createActionListener();
    }

    private void createActionListener() {
        startButton.addActionListener(e -> {
            Integer client = Integer.valueOf(textField1.getText());
            Integer minService = Integer.valueOf(textField3.getText());
            Integer maxService = Integer.valueOf(textField4.getText());
            Integer minArrival = Integer.valueOf(textField5.getText());
            Integer maxArrival = Integer.valueOf(textField6.getText());
            Integer queue = Integer.valueOf(textField2.getText());
            Integer simtime=Integer.valueOf(textField7.getText());

            if (SHORTEST_QUEUERadioButton.isSelected()) {
                simulationManager = new SimulationManager(
                        nuj.this, SelectionPolicy.SHORTEST_QUEUE,client,simtime,maxService,minService,queue,maxArrival,minArrival
                        
                        
                        
                        
                        
                        
                );
            } else if (SHORTEST_TIMERadioButton.isSelected()) {
                simulationManager = new SimulationManager(
                       nuj.this,  SelectionPolicy.SHORTEST_TIME,client,simtime,maxService,minService,queue,maxArrival,minArrival
                       
                       
                );
            }

            if (simulationManager != null) {
                Thread t = new Thread(simulationManager);
                t.start();
            }
        });
    }

    public void updateGUI(String state) {
        textArea1.setText(state);
    }


}
