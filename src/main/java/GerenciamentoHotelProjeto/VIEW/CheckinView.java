package GerenciamentoHotelProjeto.VIEW;

import javax.swing.JOptionPane;
import GerenciamentoHotelProjeto.CONTROLLER.ReservaController;
import GerenciamentoHotelProjeto.CONTROLLER.EstadiaController;
import GerenciamentoHotelProjeto.DB.QuartoDAO;
import GerenciamentoHotelProjeto.DB.HospedeDAO;
import GerenciamentoHotelProjeto.MODEL.Estadia;
import GerenciamentoHotelProjeto.MODEL.Reserva;

import java.sql.SQLException;
import java.util.List;

public class CheckinView {
    private ReservaController reservaController;
    private EstadiaController estadiaController;
    private QuartoDAO quartoDAO;
    private HospedeDAO hospedeDAO;

    public CheckinView(ReservaController reservaController, EstadiaController estadiaController, QuartoDAO quartoDAO, HospedeDAO hospedeDAO) {
        this.reservaController = reservaController;
        this.estadiaController = estadiaController;
        this.quartoDAO = quartoDAO;
        this.hospedeDAO = hospedeDAO;
    }

    public void CheckinsView() {
        int menuInput = 0;
        String[] menu = { "Realizar Check-in", "Realizar Check-out", "Sair" };

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
                startCheckin();
                break;
            case 1:
                startCheckout();
                break;
            case 2:
                // Opção "Sair"
                break;
            default:
                JOptionPane.showMessageDialog(null, "Nenhuma opção selecionada.");
                break;
        }
    }

    public void startCheckin() {
        try {
            // Obtém a lista de reservas
            List<Reserva> reservas = reservaController.getAllReservas(); 
    
            // Cria uma StringBuilder para exibir as reservas
            StringBuilder reservasList = new StringBuilder("Reservas:\n");
            for (Reserva reserva : reservas) {
                reservasList.append("ID: ").append(reserva.getId())
                            .append(", Quarto: ").append(reserva.getNumeroQuarto())
                            .append(", Hóspede CPF: ").append(reserva.getCpfHospede())
                            .append(", Entrada: ").append(reserva.getDataEntrada())
                            .append(", Saída: ").append(reserva.getDataSaida())
                            .append("\n");
            }
    
            // Exibe a lista de reservas
            JOptionPane.showMessageDialog(null, reservasList.toString(), "Lista de Reservas", JOptionPane.INFORMATION_MESSAGE);
    
            // Pede ao usuário o ID da reserva para check-in
            String reservaIdInput = JOptionPane.showInputDialog("Digite o ID da reserva para check-in:");
            int reservaId = Integer.parseInt(reservaIdInput);
    
            // Obtém a reserva selecionada
            Reserva reservaSelecionada = reservaController.getReservaById(reservaId); 
            if (reservaSelecionada != null) {
                // Converte o CPF em ID do hóspede
                int idHospede = hospedeDAO.getIdByCpf(reservaSelecionada.getCpfHospede());
    
                if (idHospede != -1) {
                    // Cria uma estadia com o ID do hóspede
                    Estadia estadia = new Estadia(
                        reservaSelecionada.getNumeroQuarto(),
                        idHospede,
                        reservaSelecionada.getDataEntrada(),
                        null, // Data de saída em aberto (verifique se aceita null)
                        0.0   // Valor em aberto (ou um valor padrão)
                    );
    
                    // Adiciona a estadia ao controller
                    estadiaController.adicionarEstadia(estadia);
    
                    // Atualiza o status do quarto para "ocupado"
                    quartoDAO.updateStatus(reservaSelecionada.getNumeroQuarto(), "ocupado");
    
                    JOptionPane.showMessageDialog(null, "Check-in realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Hóspede não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Reserva não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID da reserva inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar check-in: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void startCheckout() {
        try {
            // Obtém a lista de estadias
            List<Estadia> estadias = estadiaController.getAllEstadias();
    
            // Cria uma StringBuilder para exibir as estadias
            StringBuilder estadiasList = new StringBuilder("Estadias:\n");
            for (Estadia estadia : estadias) {
                estadiasList.append("ID: ").append(estadia.getId())
                            .append(", Quarto: ").append(estadia.getIdQuarto())
                            .append(", Hóspede ID: ").append(estadia.getIdHospede())
                            .append(", Entrada: ").append(estadia.getDataCheckin())
                            .append(", Saída: ").append(estadia.getDataCheckout() != null ? estadia.getDataCheckout() : "Data a ser colocada")
                            .append(", Valor: ").append(estadia.getDataCheckout() != null ? String.format("R$ %.2f", estadia.getValor()) : "Preço a ser calculado")
                            .append("\n");
            }
    
            // Exibe a lista de estadias
            JOptionPane.showMessageDialog(null, estadiasList.toString(), "Lista de Estadias", JOptionPane.INFORMATION_MESSAGE);
    
            // Pede ao usuário o ID da estadia para check-out
            String estadiaIdInput = JOptionPane.showInputDialog("Digite o ID da estadia para check-out:");
            int estadiaId = Integer.parseInt(estadiaIdInput);
    
            // Obtém a estadia selecionada
            Estadia estadiaSelecionada = estadias.stream()
                                                 .filter(e -> e.getId() == estadiaId)
                                                 .findFirst()
                                                 .orElse(null);
    
            if (estadiaSelecionada != null && estadiaSelecionada.getDataCheckout() == null) {
                // Pede a data de check-out
                String dataCheckoutInput = JOptionPane.showInputDialog("Digite a data de check-out (formato YYYY-MM-DD):");
    
                // Obtém o preço do quarto
                double precoPorNoite = quartoDAO.getPrecoPorId(estadiaSelecionada.getIdQuarto());
    
                // Calcula o valor total
                double valorTotal = precoPorNoite; // Valor fixo do quarto
    
                // Mostra o valor total ao usuário
                JOptionPane.showMessageDialog(null, "Valor total a pagar: R$ " + valorTotal, "Valor do Check-out", JOptionPane.INFORMATION_MESSAGE);
    
                // Atualiza a estadia com a data de check-out e o valor
                estadiaSelecionada.setDataCheckout(dataCheckoutInput);
                estadiaSelecionada.setValor(valorTotal);
    
                // Atualiza a estadia no banco de dados
                estadiaController.atualizarEstadia(estadiaSelecionada);
    
                // Atualiza o status do quarto para "disponível"
                quartoDAO.updateStatus(estadiaSelecionada.getIdQuarto(), "disponível");
    
                JOptionPane.showMessageDialog(null, "Check-out realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Estadia não encontrada ou já finalizada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID da estadia inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar check-out: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
