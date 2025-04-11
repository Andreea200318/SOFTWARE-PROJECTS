package tema2.Model.Repo;

import tema2.Model.Exemplar;
import tema2.Model.Planta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExemplarRepo {
    // Adăugarea unui exemplar
    public void adaugaExemplar(Exemplar exemplar) {
        String sql = "INSERT INTO Exemplar (imagine, zona, planta_id, denumire) VALUES (?, ?, ?, ?)";

        try (Connection connection = Repo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, exemplar.getImagine());
            statement.setString(2, exemplar.getZona());
            statement.setInt(3, exemplar.getPlantaId());
            statement.setString(4, exemplar.getDenumire());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                exemplar.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ștergerea unui exemplar
    public void stergeExemplar(int id) {
        String sql = "DELETE FROM Exemplar WHERE id = ?";

        try (Connection connection = Repo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Actualizarea unui exemplar
    public void actualizeazaExemplar(Exemplar exemplar) {
        String sql = "UPDATE Exemplar SET imagine = ?, zona = ?, planta_id = ?, denumire = ? WHERE id = ?";

        try (Connection connection = Repo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, exemplar.getImagine());
            statement.setString(2, exemplar.getZona());
            statement.setInt(3, exemplar.getPlantaId());
            statement.setString(4, exemplar.getDenumire());
            statement.setInt(5, exemplar.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Obținerea tuturor exemplarelor
    public List<Exemplar> getToateExemplarele() {
        List<Exemplar> exemplare = new ArrayList<>();
        String sql = "SELECT * FROM Exemplar";

        try (Connection connection = Repo.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                exemplare.add(mapResultSetToExemplar(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exemplare;
    }

    // Căutarea exemplarelor după specie (folosind JOIN cu tabela Planta)
    public List<Exemplar> getExemplareBySpecie(String specie) {
        List<Exemplar> exemplare = new ArrayList<>();
        String sql = "SELECT e.* FROM Exemplar e " +
                "JOIN Planta p ON e.planta_id = p.id " +
                "WHERE p.specie = ?";

        try (Connection connection = Repo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, specie);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                exemplare.add(mapResultSetToExemplar(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exemplare;
    }

    // Obține un exemplar după ID
    public Optional<Exemplar> cautaExemplarDupaID(int id) {
        String sql = "SELECT * FROM Exemplar WHERE id = ?";

        try (Connection connection = Repo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToExemplar(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // Obține informații extinse despre exemplare (pentru afișare)
    public List<Object[]> getExemplareWithPlantaInfo() {
        List<Object[]> rezultate = new ArrayList<>();
        String sql = "SELECT e.*, p.denumire AS planta_denumire, p.specie FROM Exemplar e " +
                "JOIN Planta p ON e.planta_id = p.id";

        try (Connection connection = Repo.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = new Object[5]; // Date din Exemplar + Date din Planta

                // Date din Exemplar
                row[0] = rs.getInt("id");
                row[1] = rs.getString("imagine");
                row[2] = rs.getString("zona");

                // Date din Planta
                row[3] = rs.getString("planta_denumire"); // Folosim alias pentru a evita confuzia cu denumire din Exemplar
                row[4] = rs.getString("specie");

                rezultate.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultate;
    }

    private Exemplar mapResultSetToExemplar(ResultSet rs) throws SQLException {
        Exemplar exemplar = new Exemplar();
        exemplar.setId(rs.getInt("id"));
        exemplar.setImagine(rs.getString("imagine"));
        exemplar.setZona(rs.getString("zona"));
        exemplar.setPlantaId(rs.getInt("planta_id"));

        // Verifică dacă există coloana denumire în rezultat
        try {
            exemplar.setDenumire(rs.getString("denumire"));
        } catch (SQLException e) {
            // Dacă nu există, setăm valoarea default
            exemplar.setDenumire("");
        }

        return exemplar;
    }
}