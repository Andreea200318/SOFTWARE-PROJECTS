package org.example.bll;

import org.example.bll.valid.Clients;
import org.example.bll.valid.ProdusValid;
import org.example.bll.valid.Validator;
import org.example.data_access.ClientDAO;
import org.example.data_access.ProduseDAO;
import org.example.model.Client;
import org.example.model.Produs;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * Această clasă gest logica pt produse
 * metode pentru găsire, afisare, inserare, actualizare și ștergere
 */
public class ProdusBll {
    private Validator<Produs> validators;
    private ProduseDAO prodDAO;

    public ProdusBll() {
        validators = new ProdusValid();
// validators.add(new Clients());
// validators.add(new StudentAgeValidator());

        prodDAO = new ProduseDAO();
    }
    /**
     * Găsește un produs după ID.
     *
     * @param id ID-ul produsului căutat.
     * @return Produsul găsit.
     * @throws NoSuchElementException
     */

    public Produs findStudentById(int id) {
        Produs c = prodDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("Produsul cu id =" + id + " nu a fost gasit!");
        }
        return c;
    }
    /**
     * Returnează o listă cu toate produsele.
     *
     * @return Lista de produse.
     */

    public List<Produs> findAll() {
        List<Produs> r = prodDAO.findAll();
        return r;
    }
    /**
     * Inserează un nou produs in bd
     *
     * @param c Prod inserat
     */

    public void insert(Produs c) {
        validators.validate(c);
        prodDAO.insert(c);
    }
    /**
     * Actual inf unui produs existent
     *
     * @param c Prod de actual
     * @param id      ID-ul prod de actual
     * @throws SQLException
     * @throws IllegalAccessException
     */

    public void update(Produs c, int id) throws SQLException, IllegalAccessException {
        validators.validate(c);
        prodDAO.update(c, id);
    }
    /**
     * Șterge un produs din baza de date după ID.
     *
     * @param id ID-ul produsului de șters.
     */

    public void delete(int id) {
        prodDAO.delete("id", id);
    }
}