package GerenciamentoHotelProjeto.DB;

import GerenciamentoHotelProjeto.MODEL.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para inserir um funcionário
    public void insert(Funcionario funcionario) throws SQLException {
        String insertSQL = "INSERT INTO funcionarios (nome, cpf, dataNascimento, cargo, salario, turno) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getCpf());
            pstmt.setString(3, funcionario.getDataNascimento());
            pstmt.setString(4, funcionario.getCargo());
            pstmt.setDouble(5, funcionario.getSalario());
            pstmt.setString(6, funcionario.getTurno());
            pstmt.executeUpdate();
            System.out.println("Funcionário adicionado com sucesso!");
        }
    }

    public Funcionario findById(int id) throws SQLException {
        String selectSQL = "SELECT * FROM funcionarios WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("dataNascimento"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"),
                        rs.getString("turno")
                    );
                }
            }
        }
        return null;
    }

    // Método para atualizar um funcionário
    public void update(Funcionario funcionario) throws SQLException {
        String updateSQL = "UPDATE funcionarios SET nome = ?, cpf = ?, dataNascimento = ?, cargo = ?, salario = ?, turno = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getCpf());
            pstmt.setString(3, funcionario.getDataNascimento());
            pstmt.setString(4, funcionario.getCargo());
            pstmt.setDouble(5, funcionario.getSalario());
            pstmt.setString(6, funcionario.getTurno());
            pstmt.setInt(7, funcionario.getId());
            pstmt.executeUpdate();
            System.out.println("Funcionário atualizado com sucesso!");
        }
    }

    // Método para listar todos os funcionários
    public List<Funcionario> findAll() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("dataNascimento"),
                    rs.getString("cargo"),
                    rs.getDouble("salario"),
                    rs.getString("turno")
                );
                funcionarios.add(funcionario);
            }
        }
        return funcionarios;
    }

    // Método para registrar horas trabalhadas
    public void registrarHoras(int idFuncionario, double horasTrabalhadas) throws SQLException {
        String updateSQL = "UPDATE funcionarios SET horasTrabalhadas = horasTrabalhadas + ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setDouble(1, horasTrabalhadas);
            pstmt.setInt(2, idFuncionario);
            pstmt.executeUpdate();
        }
    }

    // Método para calcular o salário baseado em horas trabalhadas
    public double calcularSalario(int idFuncionario) throws SQLException {
        String selectSQL = "SELECT salario, horasTrabalhadas FROM funcionarios WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setInt(1, idFuncionario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double salarioPorHora = rs.getDouble("salario");
                    double horasTrabalhadas = rs.getDouble("horasTrabalhadas");
                    return salarioPorHora * horasTrabalhadas;
                }
            }
        }
        return 0;
    }
    
    // Método para atualizar o salário do funcionário no banco de dados
    public void atualizarSalario(int id, double salario) throws SQLException {
        String updateSQL = "UPDATE funcionarios SET salario = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setDouble(1, salario);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }
}
