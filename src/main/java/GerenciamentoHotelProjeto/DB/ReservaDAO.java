package GerenciamentoHotelProjeto.DB;

import GerenciamentoHotelProjeto.MODEL.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private Connection conn;

    public ReservaDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Método atualizado para aceitar um objeto Reserva
    public void insert(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (cpfHospede, numeroQuarto, dataEntrada, dataSaida, quantidadeHospedes) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reserva.getCpfHospede());
            stmt.setInt(2, reserva.getNumeroQuarto());
            stmt.setString(3, reserva.getDataEntrada());
            stmt.setString(4, reserva.getDataSaida());
            stmt.setInt(5, reserva.getQuantidadeHospedes());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String deleteSQL = "DELETE FROM reservas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Reserva cancelada com sucesso!");
        }
    }

    public Reserva findById(int id) throws SQLException {
        String selectSQL = "SELECT * FROM reservas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Reserva(
                        rs.getInt("id"),
                        rs.getString("cpfHospede"),
                        rs.getInt("numeroQuarto"),
                        rs.getString("dataEntrada"),
                        rs.getString("dataSaida"),
                        rs.getInt("quantidadeHospedes")
                    );
                }
            }
        }
        return null;
    }

    public boolean isRoomAvailable(int numeroQuarto, String dataEntrada, String dataSaida) throws SQLException {
        String selectSQL = "SELECT COUNT(*) FROM reservas WHERE numeroQuarto = ? AND " +
                           "(dataEntrada < ? AND dataSaida > ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setInt(1, numeroQuarto);
            pstmt.setString(2, dataEntrada);
            pstmt.setString(3, dataSaida);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.getInt(1) == 0;
            }
        }
    }

    public boolean isRoomAvailableOnDate(int numeroQuarto, String data) throws SQLException {
        String query = "SELECT COUNT(*) FROM reservas WHERE numeroQuarto = ? AND ? BETWEEN dataEntrada AND dataSaida";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, numeroQuarto);
            stmt.setString(2, data);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count == 0; // Retorna true se não houver reservas para essa data
        }
    }
    
    // Novo método para listar todas as reservas
    public List<Reserva> findAll() throws SQLException {
        String selectSQL = "SELECT * FROM reservas";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            List<Reserva> reservas = new ArrayList<>();
            while (rs.next()) {
                reservas.add(new Reserva(
                    rs.getInt("id"),
                    rs.getString("cpfHospede"),
                    rs.getInt("numeroQuarto"),
                    rs.getString("dataEntrada"),
                    rs.getString("dataSaida"),
                    rs.getInt("quantidadeHospedes")
                ));
            }
            return reservas;
        }
    }
}
