package org.example.interfata;
import org.example.polinom.Operatii;
import org.example.polinom.Polinom;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Tema1 extends JFrame {
    private JPanel panel1;
    private JTextField textfieldpol1;
    private JTextField textField1;
    private JButton adunareButton;
    private JButton scadereButton;
    private JButton inmultireButton;
    private JButton impartireButton;
    private JButton integrareButton;
    private JButton derivareButton;
    private JTextField textField2;



    public Tema1(){
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ex");
        setContentPane(panel1);
        setVisible(true);
        pack();

        adunareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polinom polinomUtil = new Polinom();

                String inputPolinom1 = textfieldpol1.getText();
                HashMap<Integer, Double> polinom1 = Polinom.transformStringToPolinomHashMap(inputPolinom1);

                String inputPolinom2 = textField1.getText();
                HashMap<Integer, Double> polinom2 = Polinom.transformStringToPolinomHashMap(inputPolinom2);


                HashMap<Integer, Double> rezultatAdunare = Operatii.suma(polinom1, polinom2);




                String rezultatStrstr = polinomUtil.convertFromHashMap(rezultatAdunare);
                textField2.setText(rezultatStrstr);
            }



            });
        scadereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polinom polinomUtil = new Polinom();

                String inputPolinom1 = textfieldpol1.getText();
                HashMap<Integer, Double> polinom1 = Polinom.transformStringToPolinomHashMap(inputPolinom1);

                String inputPolinom2 = textField1.getText();
                HashMap<Integer, Double> polinom2 = Polinom.transformStringToPolinomHashMap(inputPolinom2);



                HashMap<Integer, Double> rezultatAdunare = Operatii.diferenta(polinom1, polinom2);




                String rezultatStrstr = polinomUtil.convertFromHashMap(rezultatAdunare);
                textField2.setText(rezultatStrstr);

            }
        });
        inmultireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polinom polinomUtil = new Polinom();

                String inputPolinom1 = textfieldpol1.getText();
                HashMap<Integer, Double> polinom1 = Polinom.transformStringToPolinomHashMap(inputPolinom1);

                String inputPolinom2 = textField1.getText();
                HashMap<Integer, Double> polinom2 = Polinom.transformStringToPolinomHashMap(inputPolinom2);

                HashMap<Integer, Double> rezultatAdunare = Operatii.inmultire(polinom1, polinom2);




                String rezultatStrstr = polinomUtil.convertFromHashMap(rezultatAdunare);
                textField2.setText(rezultatStrstr);

            }
        });
        impartireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        integrareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polinom polinomUtil = new Polinom();

                String inputPolinom1 = textfieldpol1.getText();
                HashMap<Integer, Double> polinom1 = Polinom.transformStringToPolinomHashMap(inputPolinom1);



                HashMap<Integer, Double> rezultatAdunare = Operatii.integrala(polinom1);




                String rezultatStrstr = polinomUtil.convertFromHashMap(rezultatAdunare);
                textField2.setText(rezultatStrstr);

            }
        });
        derivareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polinom polinomUtil = new Polinom();

                String inputPolinom1 = textfieldpol1.getText();
                HashMap<Integer, Double> polinom1 = Polinom.transformStringToPolinomHashMap(inputPolinom1);



                HashMap<Integer, Double> rezultatAdunare = Operatii.derivata(polinom1);




                String rezultatStrstr = polinomUtil.convertFromHashMap(rezultatAdunare);
                textField2.setText(rezultatStrstr);

            }
        });
    }

}
