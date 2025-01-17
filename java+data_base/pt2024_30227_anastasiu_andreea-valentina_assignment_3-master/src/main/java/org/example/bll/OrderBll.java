package org.example.bll;

import org.example.bll.valid.ValidOrder;
import org.example.bll.valid.Validator;
import org.example.data_access.ClientDAO;
import org.example.data_access.OrdersDAO;
import org.example.data_access.ProduseDAO;
import org.example.model.Client;
import org.example.model.Orders;
import org.example.model.Produs;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * clasa gestionează logica comenzilor.
 *  metode pentru plasarea comenzilor, obținerea informațiilor despre produse, clienți și comenzi.
 */
public class OrderBll {
    private OrdersDAO orderDAO;
    private ProduseDAO productDAO;
    private Validator<Orders> validator;
    private ClientDAO clientDAO;

    /**
     * Construct OrderBll
     * Inițializează
     */
    public OrderBll() {
        validator = new ValidOrder();
        orderDAO = new OrdersDAO();
        productDAO = new ProduseDAO();
        clientDAO = new ClientDAO();
    }

    /**
     * Plasează o comandă .
     *
     * @param productId   ID-ul produsului
     * @param customerId  ID-ul clientului
     * @param quantity    Cantitatea
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public void placeOrder(int productId, int customerId, int quantity) throws SQLException, IllegalAccessException {
        Client client= clientDAO.findById(customerId);
        if(client==null)
        {
            throw new NoSuchElementException("Clientul nu a fost găsit");
        }
        Produs product =  productDAO.findById(productId);
        if (product == null) {
            throw new NoSuchElementException("Produsul nu a fost găsit cu ID: " + productId);
        }
        if (product.getCantitate() < quantity) {
            throw new IllegalArgumentException("Nu există suficient stoc pentru produsul cu ID: " + productId);
        }

        product.setCantitate(product.getCantitate() - quantity);
        productDAO.update(product,productId);

        Orders order = new Orders();
        order.setClientid(customerId);
        order.setCantitate(quantity);
        order.setProdusid(productId);

        orderDAO.insert(order);
    }

    /**
     * Returnează o listă cu toate produsele
     *
     * @return Lista de produse
     * @throws SQLException
     */
    public List<Produs> getAllProducts() throws SQLException {
        return productDAO.findAll();
    }

    /**
     * Returnează o listă cu toți clienții
     *
     * @return Lista de clienți.
     * @throws SQLException
     */
    public List<Client> getAllClients() throws SQLException {
        return clientDAO.findAll();
    }

    /**
     * Returnează o listă cu toate comenzile
     *
     * @return Lista de comenzi.
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<Orders> getAllOrders() throws SQLException, IllegalAccessException {
        return orderDAO.findAll();
    }
}
