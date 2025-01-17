package org.example.bll;

import org.example.bll.valid.Clients;
import org.example.bll.valid.Validator;
import org.example.data_access.ClientDAO;
import org.example.model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * aplic logica ent Client
 * gestioneaza op CRUD pt clienti
 */

public class ClientBll {
    private Validator<Client> validators;
    private ClientDAO clientDAO;

    /**
     * construct clasei clientbll
     * init
     */

    public ClientBll() {
        validators = new Clients();
       // validators.add(new Clients());
       // validators.add(new StudentAgeValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * gaseste client dupa id
     * @param id
     * @return ob client gasit
     * @throws NoSuchElementException daca nu gasim un client cu acel id
     */

    public Client findStudentById(int id) {
        Client c = clientDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("Clientul cu id-ul= " + id + " nu a fost gasit!");
        }
        return c;
    }

    /**
     * ret o lista cu toti clienti
     * @return list clienti
     */
    public List<Client> findAll() {
        List<Client> r=clientDAO.findAll();
        return r;
    }

    /**
     * inserez client nou
     * @param c client de inserat
     */
    public void insert(Client c) {
        validators.validate(c);
        clientDAO.insert(c);
    }

    /**
     * act inf
     * @param c clientul cu inf de act
     * @param id
     * @throws SQLException asta e pt in cazul in care apare o err sql in timpul actualizarii
     * @throws IllegalAccessException daca nu putem accesa membrul
     */
    public void update(Client c,int id) throws SQLException, IllegalAccessException {
        validators.validate(c);

        clientDAO.update(c,id);
    }

    /**
     *  sterge client din bd
     * @param id
     */
    public void delete(int id) {
        clientDAO.delete("id",id);
    }


}
