package GerenciamentoHotelProjeto.CONTROLLER;

import GerenciamentoHotelProjeto.DB.HospedeDAO;
import GerenciamentoHotelProjeto.MODEL.Hospede;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class HospedeController {
    private HospedeDAO hospedeDAO;

    // Construtor do HospedeController
    public HospedeController(Connection conn) {
        hospedeDAO = new HospedeDAO(conn);
    }

    // Método para adicionar um novo hóspede
    public void addHospede(String nome, String cpf, String dataNascimento, String endereco, String telefone) {
        try {
            // Cria um novo objeto Hospede com os dados fornecidos
            Hospede hospede = new Hospede(0, nome, cpf, dataNascimento, endereco, telefone);
            // Insere o hóspede no banco de dados e obtém o ID gerado
            int idGerado = hospedeDAO.insert(hospede); // Receber ID gerado
            hospede.setId(idGerado); // Definir o ID no objeto Hospede
            System.out.println("Hóspede cadastrado com sucesso! ID: " + idGerado);
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha ao cadastrar o hóspede
            System.out.println("Erro ao cadastrar hóspede: " + e.getMessage());
        }
    }

    // Método para atualizar os dados de um hóspede existente
    public void updateHospede(int id, String nome, String dataNascimento, String endereco, String telefone) {
        try {
            // Cria um objeto Hospede com os dados atualizados
            Hospede hospede = new Hospede(id, nome, null, dataNascimento, endereco, telefone); // CPF pode ser nulo se não for usado para update
            // Atualiza os dados do hóspede no banco de dados
            hospedeDAO.update(hospede);
            System.out.println("Hóspede atualizado com sucesso!");
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha ao atualizar o hóspede
            System.out.println("Erro ao atualizar hóspede: " + e.getMessage());
        }
    }    

    // Método para excluir um hóspede pelo CPF
    public void deleteHospede(String cpf) {
        try {
            // Exclui o hóspede do banco de dados pelo CPF
            hospedeDAO.delete(cpf);
            System.out.println("Hóspede excluído com sucesso!");
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha ao excluir o hóspede
            System.out.println("Erro ao excluir hóspede: " + e.getMessage());
        }
    }

    // Método para buscar um hóspede pelo ID
    public Hospede getHospedeById(int id) {
        try {
            // Busca o hóspede no banco de dados pelo ID
            return hospedeDAO.findById(id); // Método a ser criado na classe HospedeDAO
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha ao buscar o hóspede
            System.out.println("Erro ao buscar hóspede: " + e.getMessage());
        }
        return null;
    }    

    // Método para obter a lista de todos os hóspedes
    public String obterTodosHospedes() {
        StringBuilder sb = new StringBuilder();
        try {
            // Obtém a lista de todos os hóspedes do banco de dados
            List<Hospede> hospedes = hospedeDAO.findAll();
            if (hospedes.isEmpty()) {
                sb.append("Não há hóspedes registrados no momento.");
            } else {
                sb.append("Lista de Hóspedes:\n");
                // Adiciona cada hóspede à lista formatada
                for (Hospede hospede : hospedes) {
                    sb.append("ID: ").append(hospede.getId())
                      .append(", Nome: ").append(hospede.getNome())
                      .append(", CPF: ").append(hospede.getCpf())
                      .append(", Data de Nascimento: ").append(hospede.getDataNascimento())
                      .append(", Endereço: ").append(hospede.getEndereco())
                      .append(", Contato: ").append(hospede.getContato()).append("\n");
                }
            }
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha ao obter a lista de hóspedes
            sb.append("Erro ao buscar hóspedes: ").append(e.getMessage());
        }
        return sb.toString();
    }
}
