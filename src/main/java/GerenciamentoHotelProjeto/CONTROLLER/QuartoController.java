package GerenciamentoHotelProjeto.CONTROLLER;

import GerenciamentoHotelProjeto.DB.QuartoDAO;
import GerenciamentoHotelProjeto.MODEL.Quarto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class QuartoController {
    private QuartoDAO quartoDAO;

      // Construtor sem parâmetros
    public QuartoController() {
    }

    // Construtor que recebe uma conexão
    public QuartoController(Connection conn) {
        this.quartoDAO = new QuartoDAO(conn);
    }

    // Método para adicionar um quarto
    public void addQuarto(int numero, String tipo, int capacidade, double preco, String status) throws SQLException {
        Quarto quarto = new Quarto(numero, tipo, capacidade, preco, status);
        quartoDAO.insert(quarto);
    }

    // Método para atualizar um quarto
    public void updateQuarto(int numero, String tipo, int capacidade, double preco, String status) throws SQLException {
        Quarto quarto = new Quarto(numero, tipo, capacidade, preco, status);
        quartoDAO.update(quarto);
    }

    // Método para deletar um quarto
    public void deleteQuarto(int numero) throws SQLException {
        quartoDAO.delete(numero);
    }

    // Método para buscar um quarto por número
    public Quarto getQuartoByNumero(int numero) throws SQLException {
        return quartoDAO.findByNumero(numero);
    }

    // Método para listar todos os quartos
    public List<Quarto> getAllQuartos() throws SQLException {
        return quartoDAO.findAll();
    }

    public String obterQuartosDisponiveis() {
        StringBuilder sb = new StringBuilder();
        try {
            List<Quarto> quartosDisponiveis = quartoDAO.findQuartosByStatus("disponível");
            if (quartosDisponiveis.isEmpty()) {
                sb.append("Não há quartos disponíveis no momento.");
            } else {
                sb.append("Quartos disponíveis:\n");
                for (Quarto quarto : quartosDisponiveis) {
                    sb.append("Número: ").append(quarto.getNumero())
                      .append(", Tipo: ").append(quarto.getTipo())
                      .append(", Capacidade: ").append(quarto.getCapacidade())
                      .append(", Preço: ").append(quarto.getPreco()).append("\n");
                }
            }
        } catch (SQLException e) {
            sb.append("Erro ao buscar quartos disponíveis: ").append(e.getMessage());
        }
        return sb.toString();
    }

     // Método para obter todos os quartos
     public String obterTodosQuartos() throws SQLException {
        List<Quarto> quartos = quartoDAO.findAll();
        StringBuilder sb = new StringBuilder();
        
        if (quartos.isEmpty()) {
            sb.append("Não há quartos registrados no momento.");
        } else {
            sb.append("Lista de Quartos:\n");
            for (Quarto quarto : quartos) {
                sb.append("Número: ").append(quarto.getNumero())
                  .append(", Tipo: ").append(quarto.getTipo())
                  .append(", Capacidade: ").append(quarto.getCapacidade())
                  .append(", Preço: ").append(quarto.getPreco())
                  .append(", Status: ").append(quarto.getStatus()).append("\n");
            }
        }
        return sb.toString();
    }
}
