package tema2.Model.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Repo {
    private static final String URL = "jdbc:mysql://localhost:3306/botanical_garden";
    private static final String USER = "root";
    private static final String PASSWORD = "Andreea_2003";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // încărcarea driverului JDBC pentru MySQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
