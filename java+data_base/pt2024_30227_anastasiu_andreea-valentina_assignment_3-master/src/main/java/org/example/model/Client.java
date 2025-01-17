package org.example.model;
/**
 * Reprezintă un obiect client care poate fi stocat în baza de date.
 */

public class Client {
    private int id;
    private String nume;
      private String email;

    public Client() {
    }

    public Client(int Id, String clientName, String clientAddress) {
          this.id = Id;
          this.nume = clientName;
          this.email = clientAddress;
      }

    public int getId() {
        return id;
    }

    public void setId(int id_client) {
        this.id = id_client;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String toString() {
        return nume;
    }
}
