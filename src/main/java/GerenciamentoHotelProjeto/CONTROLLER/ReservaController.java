package GerenciamentoHotelProjeto.CONTROLLER;

import GerenciamentoHotelProjeto.DB.ReservaDAO;
import GerenciamentoHotelProjeto.DB.QuartoDAO;
import GerenciamentoHotelProjeto.MODEL.Reserva;
import GerenciamentoHotelProjeto.MODEL.Quarto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReservaController {
    private ReservaDAO reservaDAO;
    private QuartoDAO quartoDAO;

    // Construtor do ReservaController
    public ReservaController(Connection conn) {
        reservaDAO = new ReservaDAO(conn);
        quartoDAO = new QuartoDAO(conn);
    }

    // Método para criar uma nova reserva
    public void createReserva(String cpfHospede, int numeroQuarto, String dataEntrada, String dataSaida, int quantidadeHospedes) {
        try {
            // Verifica se o quarto existe
            Quarto quarto = quartoDAO.findByNumero(numeroQuarto);
            if (quarto == null) {
                System.out.println("Erro: Quarto não encontrado.");
                return;
            }

            // Verifica se o quarto está disponível para as datas fornecidas
            if (reservaDAO.isRoomAvailable(numeroQuarto, dataEntrada, dataSaida)) {
                // Atualiza o status do quarto para "reservado"
                quartoDAO.updateStatus(numeroQuarto, "reservado");

                // Cria um novo objeto Reserva e o insere no banco de dados
                Reserva reserva = new Reserva(0, cpfHospede, numeroQuarto, dataEntrada, dataSaida, quantidadeHospedes);
                reservaDAO.insert(reserva);
                System.out.println("Reserva criada com sucesso!");
            } else {
                System.out.println("Quarto não disponível para as datas selecionadas.");
            }
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha na criação da reserva
            System.out.println("Erro ao criar reserva: " + e.getMessage());
        }
    }

    // Método para cancelar uma reserva existente
    public void cancelReserva(int id) {
        try {
            // Busca a reserva para obter o número do quarto associado
            Reserva reserva = reservaDAO.findById(id);
            if (reserva != null) {
                // Atualiza o status do quarto para "disponível"
                quartoDAO.updateStatus(reserva.getNumeroQuarto(), "disponível");
                
                // Cancela a reserva (deleta a reserva do banco de dados)
                reservaDAO.delete(id);
                
                System.out.println("Reserva cancelada e status do quarto atualizado para 'disponível' com sucesso!");
            } else {
                System.out.println("Reserva não encontrada.");
            }
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha ao cancelar a reserva
            System.out.println("Erro ao cancelar a reserva: " + e.getMessage());
        }
    }

    // Método para obter uma reserva pelo ID
    public Reserva getReservaById(int id) {
        try {
            return reservaDAO.findById(id);
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha ao buscar a reserva
            System.out.println("Erro ao buscar reserva: " + e.getMessage());
        }
        return null;
    }

    // Método para verificar se um quarto está disponível em uma data específica
    public boolean isRoomAvailableOnDate(int numeroQuarto, String data) {
        try {
            // Verifica se o quarto existe
            Quarto quarto = quartoDAO.findByNumero(numeroQuarto);
            if (quarto == null) {
                System.out.println("Erro: Quarto não encontrado.");
                return false;
            }
    
            // Verifica se o quarto está disponível na data específica
            return reservaDAO.isRoomAvailableOnDate(numeroQuarto, data);
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha na verificação de disponibilidade
            System.out.println("Erro ao verificar disponibilidade do quarto: " + e.getMessage());
            return false;
        }
    }
    
    // Método para obter todas as reservas
    public List<Reserva> getAllReservas() {
        try {
            return reservaDAO.findAll();
        } catch (SQLException e) {
            // Mensagem de erro em caso de falha ao obter todas as reservas
            System.out.println("Erro ao obter reservas: " + e.getMessage());
        }
        return null;
    }
}
