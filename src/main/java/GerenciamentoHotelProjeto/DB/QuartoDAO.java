package GerenciamentoHotelProjeto.DB;

import GerenciamentoHotelProjeto.MODEL.Quarto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuartoDAO {
    private Connection connection;

    public QuartoDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para inserir um quarto
    public void insert(Quarto quarto) throws SQLException {
        String sql = "INSERT INTO quartos (numero, tipo, capacidade, preco, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quarto.getNumero());
            stmt.setString(2, quarto.getTipo());
            stmt.setInt(3, quarto.getCapacidade());
            stmt.setDouble(4, quarto.getPreco());
            stmt.setString(5, quarto.getStatus());
            stmt.executeUpdate();
        }
    }

    // Método para atualizar um quarto
    public void update(Quarto quarto) throws SQLException {
        String sql = "UPDATE quartos SET tipo = ?, capacidade = ?, preco = ?, status = ? WHERE numero = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, quarto.getTipo());
            stmt.setInt(2, quarto.getCapacidade());
            stmt.setDouble(3, quarto.getPreco());
            stmt.setString(4, quarto.getStatus());
            stmt.setInt(5, quarto.getNumero());
            stmt.executeUpdate();
        }
    }

    // Método para deletar um quarto
    public void delete(int numero) throws SQLException {
        String sql = "DELETE FROM quartos WHERE numero = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.executeUpdate();
        }
    }

    // Método para encontrar um quarto por número
    public Quarto findByNumero(int numero) throws SQLException {
        String sql = "SELECT * FROM quartos WHERE numero = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Quarto(
                        rs.getInt("numero"),
                        rs.getString("tipo"),
                        rs.getInt("capacidade"),
                        rs.getDouble("preco"),
                        rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    // Método para listar todos os quartos
    public List<Quarto> findAll() throws SQLException {
        List<Quarto> quartos = new ArrayList<>();
        String sql = "SELECT * FROM quartos";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                quartos.add(new Quarto(
                    rs.getInt("numero"),
                    rs.getString("tipo"),
                    rs.getInt("capacidade"),
                    rs.getDouble("preco"),
                    rs.getString("status")
                ));
            }
        }
        return quartos;
    }

    public List<Quarto> findQuartosByStatus(String status) throws SQLException {
        String sql = "SELECT * FROM quartos WHERE status = ?";
        List<Quarto> quartos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Quarto quarto = new Quarto(
                            rs.getInt("numero"),
                            rs.getString("tipo"),
                            rs.getInt("capacidade"),
                            rs.getDouble("preco"),
                            rs.getString("status")
                    );
                    quartos.add(quarto);
                }
            }
        }
        return quartos;
    }

    public void updateStatus(int numero, String status) throws SQLException {
        String sql = "UPDATE quartos SET status = ? WHERE numero = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, numero);
            stmt.executeUpdate();
        }
    }
    // Método para obter o preço do quarto com base no ID
    public double getPrecoPorId(int idQuarto) throws SQLException {
        String sql = "SELECT preco FROM quartos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idQuarto);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("preco");
            } else {
                throw new SQLException("Quarto não encontrado com o ID: " + idQuarto);
            }
        }
    }
}
