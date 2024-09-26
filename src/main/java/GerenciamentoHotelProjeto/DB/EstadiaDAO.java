package GerenciamentoHotelProjeto.DB;

import java.sql.*;
import GerenciamentoHotelProjeto.MODEL.Estadia;
import java.util.ArrayList;
import java.util.List;

public class EstadiaDAO {

    private Connection connection;

    public EstadiaDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para adicionar uma nova estadia
    public boolean addEstadia(Estadia estadia) {
        String sql = "INSERT INTO estadia (id_quarto, id_hospede, data_checkin, data_checkout, valor) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, estadia.getIdQuarto());
            stmt.setInt(2, estadia.getIdHospede());
            stmt.setString(3, estadia.getDataCheckin());
            stmt.setString(4, estadia.getDataCheckout());
            stmt.setDouble(5, estadia.getValor());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Retorna verdadeiro se a inserção foi bem-sucedida
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna falso se houver um erro
        }
    }

    // Método para atualizar a estadia no banco de dados
    public void updateEstadia(Estadia estadia) throws SQLException {
        String sql = "UPDATE estadia SET data_checkout = ?, valor = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, estadia.getDataCheckout());
            statement.setDouble(2, estadia.getValor());
            statement.setInt(3, estadia.getId());
            statement.executeUpdate();
        }
    }

    // Método para deletar uma estadia pelo ID
    public boolean deleteEstadia(int id) {
        String sql = "DELETE FROM estadia WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar estadia: " + e.getMessage());
            return false;
        }
    }
    public List<Estadia> getAllEstadias() throws SQLException {
        List<Estadia> estadias = new ArrayList<>();
        String sql = "SELECT * FROM estadia"; //

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estadia estadia = new Estadia(
                    rs.getInt("id_quarto"),
                    rs.getInt("id_hospede"),
                    rs.getString("data_checkin"),
                    rs.getString("data_checkout"),
                    rs.getDouble("valor")
                );
                estadia.setId(rs.getInt("id"));
                estadias.add(estadia);
            }
        }
        return estadias;
    }
}
