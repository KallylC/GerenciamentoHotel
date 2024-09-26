package GerenciamentoHotelProjeto.DB;

import GerenciamentoHotelProjeto.MODEL.Hospede;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HospedeDAO {
    private Connection connection;

    public HospedeDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para inserir um hóspede
    public int insert(Hospede hospede) throws SQLException {
        String insertSQL = "INSERT INTO hospedes (nome, cpf, dataNascimento, endereco, numeroContato) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, hospede.getNome());
            pstmt.setString(2, hospede.getCpf());
            pstmt.setString(3, hospede.getDataNascimento());
            pstmt.setString(4, hospede.getEndereco());
            pstmt.setString(5, hospede.getContato());
            pstmt.executeUpdate();
            
            // Recuperar o ID gerado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID gerado.");
                }
            }
        }
    }      

    public Hospede findById(int id) throws SQLException {
        String selectSQL = "SELECT * FROM hospedes WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Hospede(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("dataNascimento"),
                        rs.getString("endereco"),
                        rs.getString("numeroContato")
                    );
                }
            }
        }
        return null;
    }

    // Método para atualizar um hóspede
    public void update(Hospede hospede) throws SQLException {
        String updateSQL = "UPDATE hospedes SET nome = ?, dataNascimento = ?, endereco = ?, numeroContato = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, hospede.getNome());
            pstmt.setString(2, hospede.getDataNascimento());
            pstmt.setString(3, hospede.getEndereco());
            pstmt.setString(4, hospede.getContato());
            pstmt.setInt(5, hospede.getId());
            pstmt.executeUpdate();
            System.out.println("Hóspede atualizado com sucesso!");
        }
    }
    

    // Método para excluir um hóspede pelo CPF
    public void delete(String cpf) throws SQLException {
        String deleteSQL = "DELETE FROM hospedes WHERE cpf = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, cpf);
            pstmt.executeUpdate();
            System.out.println("Hóspede excluído com sucesso!");
        }
    }

    // Método para listar todos os hóspedes
    public List<Hospede> findAll() throws SQLException {
        List<Hospede> hospedes = new ArrayList<>();
        String sql = "SELECT * FROM hospedes";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Hospede hospede = new Hospede(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("dataNascimento"),
                    rs.getString("endereco"),
                    rs.getString("numeroContato")
                );
                hospedes.add(hospede);
            }
        }
        return hospedes;
    }

    public int getIdByCpf(String cpf) throws SQLException {
        String sql = "SELECT id FROM hospedes WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1; 
    }
}
