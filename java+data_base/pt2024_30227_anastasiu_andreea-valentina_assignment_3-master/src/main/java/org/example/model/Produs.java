package org.example.model;
/**
 * Reprezintă un obiect produs care poate fi stocat în baza de date.
 */
public class Produs {
    private int id;
    private String nume;
    private int cantitate;
    private double pret;

    public Produs() {
    }

    public Produs(int id, String nume, int cantitate, double pret) {
        this.id = id;
        this.nume = nume;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }
    public String toString() {
        return nume;
    }
}