package GerenciamentoHotelProjeto.CONTROLLER;

import GerenciamentoHotelProjeto.DB.FuncionarioDAO;
import GerenciamentoHotelProjeto.MODEL.Funcionario;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FuncionarioController {
    private FuncionarioDAO funcionarioDAO;

    // Construtor do FuncionarioController
    public FuncionarioController(Connection connection) {
        this.funcionarioDAO = new FuncionarioDAO(connection);
    }

    // Método para adicionar um novo funcionário
    public void addFuncionario(String nome, String cpf, String dataNascimento, String cargo, double salario, String turno) throws SQLException {
        // Cria um objeto Funcionario com os dados fornecidos, ID será gerado pelo banco de dados
        Funcionario funcionario = new Funcionario(0, nome, cpf, dataNascimento, cargo, salario, turno); 
        // Insere o novo funcionário no banco de dados
        funcionarioDAO.insert(funcionario);
    }

    // Método para buscar um funcionário pelo ID
    public Funcionario getFuncionarioById(int id) throws SQLException {
        // Obtém o funcionário do banco de dados pelo ID
        return funcionarioDAO.findById(id);
    }

    // Método para atualizar os dados de um funcionário existente
    public void updateFuncionario(int id, String nome, String cpf, String dataNascimento, String cargo, double salario, String turno) throws SQLException {
        // Cria um objeto Funcionario com os dados atualizados
        Funcionario funcionario = new Funcionario(id, nome, cpf, dataNascimento, cargo, salario, turno);
        // Atualiza os dados do funcionário no banco de dados
        funcionarioDAO.update(funcionario);
    }

    // Método para obter a lista de todos os funcionários
    public String obterTodosFuncionarios() {
        StringBuilder sb = new StringBuilder();
        try {
            // Obtém a lista de todos os funcionários do banco de dados
            List<Funcionario> funcionarios = funcionarioDAO.findAll(); // Ajuste para o DAO correto
            if (funcionarios.isEmpty()) {
                sb.append("Não há funcionários registrados no momento.");
            } else {
                sb.append("Lista de Funcionários:\n");
                // Adiciona cada funcionário à lista formatada
                for (Funcionario funcionario : funcionarios) {
                    sb.append("ID: ").append(funcionario.getId())
                      .append(", Nome: ").append(funcionario.getNome())
                      .append(", CPF: ").append(funcionario.getCpf())
                      .append(", Data de Nascimento: ").append(funcionario.getDataNascimento())
                      .append(", Cargo: ").append(funcionario.getCargo())
                      .append(", Salário: ").append(funcionario.getSalario())
                      .append(", Turno: ").append(funcionario.getTurno()).append("\n");
                }
            }
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha ao obter a lista de funcionários
            sb.append("Erro ao buscar funcionários: ").append(e.getMessage());
        }
        return sb.toString();
    }
    
    // Método para registrar horas trabalhadas de um funcionário
    public void registrarHoras(int idFuncionario, double horasTrabalhadas) throws SQLException {
        funcionarioDAO.registrarHoras(idFuncionario, horasTrabalhadas);
    }

    // Método para calcular o salário de um funcionário
    public double calcularSalario(int idFuncionario) throws SQLException {
        return funcionarioDAO.calcularSalario(idFuncionario);
    }

    // Método para atualizar o salário de um funcionário
    public void atualizarSalario(int idFuncionario, double salario) throws SQLException {
        funcionarioDAO.atualizarSalario(idFuncionario, salario);
    }
}
