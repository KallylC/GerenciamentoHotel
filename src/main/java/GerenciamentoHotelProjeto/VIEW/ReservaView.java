package GerenciamentoHotelProjeto.VIEW;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import GerenciamentoHotelProjeto.CONTROLLER.ReservaController;
import GerenciamentoHotelProjeto.DB.DBConnection;
import GerenciamentoHotelProjeto.MODEL.Reserva;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReservaView {
    private ReservaController reservaController;
    private Connection conn;

    // Construtor da classe ReservaView
    public ReservaView() {
        try {
            // Obtém a conexão com o banco de dados e inicializa o ReservaController
            conn = DBConnection.getConnection();
            reservaController = new ReservaController(conn);
        } catch (SQLException e) {
            // Exibe uma mensagem de erro e encerra a aplicação se não conseguir conectar
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Fecha a aplicação se não conseguir conectar
        }
    }
    
    // Método principal para exibir o menu de reservas
    public void ReservasView() {
        int menuInput = 0;
        String[] menu = { "Cadastrar reserva", "Cancelar reserva", "Verificar disponibilidade", "Sair" };

        // Exibe o menu de opções
        menuInput = JOptionPane.showOptionDialog(
            null,
            "Escolha uma opção:",
            "Hotel K - Menu",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            menu,
            menu[0]
        );

        // Processa a escolha do usuário
        switch (menuInput) {
            case 0:
                // Opção "Cadastrar reserva"
                cadastrarReserva();
                break;
            case 1:
                // Opção "Cancelar/Atualizar reserva"
                cancelarReserva();
                break;
            case 2:
                // Opção "Verificar disponibilidade"
                verificarDisponibilidade();
                break;
            case 3:
                // Opção "Sair"
                break;
            default:
                // Mensagem caso nenhuma opção seja selecionada
                JOptionPane.showMessageDialog(null, "Nenhuma opção selecionada.");
                break;
        }
    }

    // Método para cadastrar uma nova reserva
    private void cadastrarReserva() {
        JTextField cpfInput = new JTextField();
        JTextField numeroQuartoInput = new JTextField();
        JTextField dataEntradaInput = new JTextField();
        JTextField dataSaidaInput = new JTextField();
        JTextField quantidadeHospedesInput = new JTextField();

        Object[] message = {
            "CPF do Hóspede:", cpfInput,
            "Número do Quarto:", numeroQuartoInput,
            "Data de Entrada (formato: DD/MM/AAAA):", dataEntradaInput,
            "Data de Saída (formato: DD/MM/AAAA):", dataSaidaInput,
            "Quantidade de Hóspedes:", quantidadeHospedesInput
        };

        // Exibe o diálogo para cadastrar a reserva
        int result = JOptionPane.showConfirmDialog(null, message, "Cadastrar Reserva", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Obtém os valores inseridos pelo usuário
                String cpfHospede = cpfInput.getText();
                int numeroQuarto = Integer.parseInt(numeroQuartoInput.getText());
                String dataEntrada = dataEntradaInput.getText();
                String dataSaida = dataSaidaInput.getText();
                int quantidadeHospedes = Integer.parseInt(quantidadeHospedesInput.getText());

                // Usando o reservaController para criar a reserva
                reservaController.createReserva(cpfHospede, numeroQuarto, dataEntrada, dataSaida, quantidadeHospedes);

                // Mensagem de sucesso
                JOptionPane.showMessageDialog(null, "Reserva cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                // Mensagem de erro em caso de dados inválidos
                JOptionPane.showMessageDialog(null, "Erro: Dados inválidos. Verifique os campos de número e quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para cancelar uma reserva existente
    private void cancelarReserva() {
        try {
            // Obter todas as reservas
            List<Reserva> reservas = reservaController.getAllReservas();
    
            // Verifica se há reservas para exibir
            if (reservas == null || reservas.isEmpty()) {
                // Mensagem caso não haja reservas
                JOptionPane.showMessageDialog(null, "Não há reservas disponíveis para cancelar.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
    
            // Cria uma StringBuilder para exibir as reservas
            StringBuilder reservasList = new StringBuilder("Reservas:\n");
            for (Reserva reserva : reservas) {
                reservasList.append("ID: ").append(reserva.getId())
                            .append(", Quarto: ").append(reserva.getNumeroQuarto())
                            .append(", Hóspede: ").append(reserva.getCpfHospede())
                            .append(", Entrada: ").append(reserva.getDataEntrada())
                            .append(", Saída: ").append(reserva.getDataSaida())
                            .append("\n");
            }
    
            // Exibe a lista de reservas
            JOptionPane.showMessageDialog(null, reservasList.toString(), "Lista de Reservas", JOptionPane.INFORMATION_MESSAGE);
    
            // Solicita o ID da reserva a ser cancelada
            JTextField reservaIdInput = new JTextField();
            Object[] message = {
                "ID da Reserva:", reservaIdInput
            };
    
            // Exibe o diálogo para cancelar a reserva
            int result = JOptionPane.showConfirmDialog(null, message, "Cancelar Reserva", JOptionPane.OK_CANCEL_OPTION);
    
            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Obtém o ID da reserva a ser cancelada
                    int reservaId = Integer.parseInt(reservaIdInput.getText());
    
                    // Usando o reservaController para cancelar a reserva
                    reservaController.cancelReserva(reservaId);
    
                    // Mensagem de sucesso
                    JOptionPane.showMessageDialog(null, "Reserva cancelada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e) {
                    // Mensagem de erro em caso de ID inválido
                    JOptionPane.showMessageDialog(null, "Erro: ID da reserva inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            // Mensagem de erro ao obter reservas
            JOptionPane.showMessageDialog(null, "Erro ao obter reservas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para verificar a disponibilidade de um quarto
    private void verificarDisponibilidade() {
        JTextField numeroQuartoInput = new JTextField();
        JTextField dataInput = new JTextField();
    
        Object[] message = {
            "Número do Quarto:", numeroQuartoInput,
            "Data (formato: DD/MM/AAAA):", dataInput
        };
    
        // Exibe o diálogo para verificar a disponibilidade
        int result = JOptionPane.showConfirmDialog(null, message, "Verificar Disponibilidade", JOptionPane.OK_CANCEL_OPTION);
    
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Obtém o número do quarto e a data inseridos pelo usuário
                int numeroQuarto = Integer.parseInt(numeroQuartoInput.getText());
                String data = dataInput.getText();
    
                // Usando o reservaController para verificar a disponibilidade
                boolean disponivel = reservaController.isRoomAvailableOnDate(numeroQuarto, data);
    
                // Mensagem indicando se o quarto está disponível ou não
                if (disponivel) {
                    JOptionPane.showMessageDialog(null, "O quarto está disponível na data " + data + "!", "Disponibilidade", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "O quarto não está disponível na data " + data + ".", "Disponibilidade", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException e) {
                // Mensagem de erro em caso de número do quarto inválido
                JOptionPane.showMessageDialog(null, "Erro: Número do quarto inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }    
}
