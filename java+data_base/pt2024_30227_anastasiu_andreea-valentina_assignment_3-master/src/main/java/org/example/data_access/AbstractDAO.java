package org.example.data_access;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.example.connection.ConnectionFactory;
/**
 * Clasa AbstractDAO este o clasă generică responsabilă pentru operațiile CRUD (create, read, update, delete)
 * pe obiectele stocate într-o bază de date.
 *
 * @param <T> Tipul obiectelor manipulate de către această clasă.
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    protected Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        Type t = getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            this.type = (Class<T>) pt.getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Tip param T nu s-a gasit.");
        }
    }
    /**
     * Creează o interogare SELECT pentru a obține un singur obiect din baza de date
     * bazat pe valoarea unui anumit câmp.
     *
     * @param field Numele câmpului folosit pentru a filtra rezultatele interogării.
     * @return Interogarea SELECT generată.
     */

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     * Creează o interogare SELECT pentru a obține toate obiectele din baza de date.
     *
     * @return Interogarea SELECT generată.
     */

    public String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }
    /**
     * Creează o interogare DELETE pentru a șterge un obiect din baza de date
     * bazat pe valoarea unui anumit câmp.
     *
     * @param field Numele câmpului folosit pentru a identifica obiectul care trebuie șters.
     * @return Interogarea DELETE generată.
     */

    public String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     * Creează o interogare UPDATE pentru a actualiza un obiect existent în baza de date.
     *
     * @param t   Obiectul care conține valorile actualizate.
     * @param id  Id-ul obiectului care trebuie actualizat.
     * @return Interogarea UPDATE generată.
     * @throws SQLException             Lansată în caz de eroare SQL.
     * @throws IntrospectionException   Lansată în caz de eroare la introspecție.
     */


    private String createUpdateQuery(T t, int id) throws SQLException, IntrospectionException {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        boolean isFirstField = true;
        for (Field f : type.getDeclaredFields()) {
            f.setAccessible(true);
            if (!isFirstField) {
                sb.append(", ");
            } else {
                isFirstField = false;
            }
            sb.append(f.getName()).append(" = ?");
        }
        sb.append(" WHERE id = ?");
        return sb.toString();
    }
    /**
     * Creează o interogare INSERT pentru a insera un nou obiect în baza de date.
     *
     * @param t Obiectul care urmează să fie inserat.
     * @return Interogarea INSERT generată.
     * @throws SQLException             Lansată în caz de eroare SQL.
     * @throws IllegalAccessException Lansată în caz de eroare de acces ilegal.
     * @throws IntrospectionException   Lansată în caz de eroare la introspecție.
     */

    private String createInsertQuery(T t) throws SQLException, IllegalAccessException, IntrospectionException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");
        StringBuilder values = new StringBuilder();
        values.append(" VALUES (");
        int count = 0;
        for (Field f : type.getDeclaredFields()) {
            f.setAccessible(true);
            if (!f.getName().equals("id")) {
                sb.append(f.getName()).append(", ");
                values.append("?, ");
                count++;
            }
        }
        if (count == 0) {
            throw new SQLException("Nu s-a gasit field pt inserat " + type.getSimpleName());
        }
        sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1).append(")");
        values.deleteCharAt(values.length() - 1).deleteCharAt(values.length() - 1).append(")");
        sb.append(values);
        return sb.toString();
    }

    /**
     * Returnează o listă de toate obiectele de tipul specificat din baza de date.
     *
     * @return Lista de obiecte recuperate din baza de date.
     */

    public List<T> findAll() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = createSelectAllQuery();
        try {
            con = ConnectionFactory.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            return createObjects(rs);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findall " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(ps);
            ConnectionFactory.close(con);
        }
        return null;
    }
    /**
     * Returnează obiectul specificat din baza de date pe baza unui ID dat.
     *
     * @param id ID-ul obiectului căutat.
     * @return Obiectul cu ID-ul specificat sau null dacă nu este găsit.
     */

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    /**
     * Returnează o listă de obiecte create din rezultatul unei interogări SELECT.
     *
     * @param resultSet Rezultatul interogării SELECT.
     * @return Lista de obiecte create din rezultatul interogării.
     */

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException | NoSuchMethodException | IntrospectionException e) {
            LOGGER.log(Level.WARNING, "Eroare facere obiect: " + e.getMessage());
        }
        return list;
    }
    /**
     * Inserează un nou obiect în baza de date.
     *
     * @param t Obiectul care trebuie inserat.
     * @return Obiectul inserat.
     */

    public T insert(T t) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ConnectionFactory.getConnection();
            String query = createInsertQuery(t);
            ps = con.prepareStatement(query);
            int i = 1;
            for (Field f : type.getDeclaredFields()) {
                f.setAccessible(true);
                if (!f.getName().equals("id")) {
                    ps.setObject(i++, f.get(t));
                }
            }
            ps.executeUpdate();
        } catch (SQLException | IntrospectionException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Eroare inserare obiect: " + e.getMessage());
        } finally {
            ConnectionFactory.close(con);
            ConnectionFactory.close(ps);
        }
        return t;
    }
    /**
     * Actualizează un obiect existent în baza de date.
     *
     * @param t  Obiectul cu valorile actualizate.
     * @param id Id-ul obiectului care trebuie actualizat.
     * @return Obiectul actualizat.
     * @throws SQLException             Lansată în caz de eroare SQL.
     */


    public T update(T t, int id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ConnectionFactory.getConnection();
            String query = createUpdateQuery(t, id);
            ps = con.prepareStatement(query);
            int i = 1;
            for (Field f : type.getDeclaredFields()) {
                f.setAccessible(true);
                ps.setObject(i++, f.get(t));
            }
            ps.setInt(i, id); // Set the id parameter
            ps.executeUpdate();
        } catch (SQLException | IntrospectionException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Eroare update obiect: " + e.getMessage());
        } finally {
            ConnectionFactory.close(con);
            ConnectionFactory.close(ps);
        }
        return t;
    }
    /**
     * Șterge un obiect din baza de date.
     *
     * @param field Câmpul pe baza căruia se va face ștergerea.
     * @param id    Id-ul obiectului care trebuie șters.
     */


    public void delete(String field, int id) {
        Connection con = null;
        PreparedStatement ps = null;
        String q = createDeleteQuery(field);
        try {
            con = ConnectionFactory.getConnection();
            ps = con.prepareStatement(q);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Eroare delete ob: " + e.getMessage());
        } finally {
            ConnectionFactory.close(con);
            ConnectionFactory.close(ps);
        }
    }
}