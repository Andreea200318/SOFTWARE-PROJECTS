package tema2.Model.Repo;

import tema2.Model.Planta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlantaRepo {
    public void adaugaPlanta(Planta plant) {
        String sql = "INSERT INTO Planta (denumire, tip, specie, planta_carnivora, imagini) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = Repo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, plant.getDenumire());
            statement.setString(2, plant.getTip());
            statement.setString(3, plant.getSpecie());
            statement.setBoolean(4, plant.isPlanta_carnivora());
            statement.setString(5, plant.getImagini());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                plant.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void stergePlanta(int idPlanta) {
        String sql = "DELETE FROM Planta WHERE id = ?";

        try (Connection connection = Repo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idPlanta);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizeazaPlanta(Planta plant) {
        String sql = "UPDATE Planta SET denumire = ?, tip = ?, specie = ?, planta_carnivora = ?, imagini = ? WHERE id = ?";

        try (Connection connection = Repo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, plant.getDenumire());
            statement.setString(2, plant.getTip());
            statement.setString(3, plant.getSpecie());
            statement.setBoolean(4, plant.isPlanta_carnivora());
            statement.setString(5, plant.getImagini());
            statement.setInt(6, plant.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Optional<Planta> getUltimaPlantaAdaugata() {
        String sql = "SELECT * FROM Planta ORDER BY id DESC LIMIT 1";

        try (Connection connection = Repo.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            if (rs.next()) {
                return Optional.of(mapResultSetToPlant(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Planta> getToatePlanteleSortate() {
        List<Planta> plante = new ArrayList<>();
        String sql = "SELECT * FROM Planta ORDER BY tip ASC, specie ASC";

        try (Connection connection = Repo.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                plante.add(mapResultSetToPlant(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plante;
    }

    public List<Planta> filtreazaPlante(String tip, String specie, Boolean carnivora) {
        List<Planta> plante = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Planta WHERE 1=1");

        if (tip != null && !tip.isEmpty()) sql.append(" AND tip = ?");
        if (specie != null && !specie.isEmpty()) sql.append(" AND specie = ?");
        if (carnivora != null) sql.append(" AND planta_carnivora = ?");

        try (Connection connection = Repo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (tip != null && !tip.isEmpty()) statement.setString(paramIndex++, tip);
            if (specie != null && !specie.isEmpty()) statement.setString(paramIndex++, specie);
            if (carnivora != null) statement.setBoolean(paramIndex, carnivora);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                plante.add(mapResultSetToPlant(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plante;
    }

    public Optional<Planta> cautaPlantaDupaID(int id) {
        String sql = "SELECT * FROM Planta WHERE id = ?";

        try (Connection connection = Repo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToPlant(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Planta mapResultSetToPlant(ResultSet rs) throws SQLException {
        return new Planta(
                rs.getInt("id"),
                rs.getString("denumire"),
                rs.getString("tip"),
                rs.getString("specie"),
                rs.getBoolean("planta_carnivora"),
                rs.getString("imagini")
        );
    }
}
