package org.example.model;
/**
 * Reprezintă un obiect comanda care poate fi stocat în baza de date.
 */
public class Orders {
    private int id;
    private int clientid;
    private int produsid;
    private int pret;
    private int cantitate;

    public Orders() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public int getProdusid() {
        return produsid;
    }

    public void setProdusid(int produsid) {
        this.produsid = produsid;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public Orders(int id, int id1, int id2, int pret, int cantitate) {
        this.id = id;
        this.clientid = id1;
        this.produsid = id2;
        this.pret = pret;
        this.cantitate = cantitate;
    }
}
