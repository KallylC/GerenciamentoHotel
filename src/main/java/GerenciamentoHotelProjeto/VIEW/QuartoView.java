package GerenciamentoHotelProjeto.VIEW;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.SQLException;
import java.sql.Connection;
import GerenciamentoHotelProjeto.CONTROLLER.QuartoController;
import GerenciamentoHotelProjeto.DB.DBConnection;
import GerenciamentoHotelProjeto.MODEL.Quarto;

public class QuartoView {
    private QuartoController quartoController;
    private Connection conn;

    public QuartoView() {
        try {
            conn = DBConnection.getConnection();
            quartoController = new QuartoController(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Fecha a aplicação se não conseguir conectar
        }
    }

    public void quartosView() {
        int menuInput = 0;
        String[] menu = { "Cadastrar quarto", "Lista de quartos", "Atualizar status", "Sair" };

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
                // Opção "Cadastrar quarto"
                CadastrarQuarto();
                break;
            case 1:
                // Opção "Lista de quartos"
                ListaQuartosDisponiveis();
                break;
            case 2:
                // Opção "Verificar status"
                atualizarStatusQuarto();
                break;
            case 3:
                // Opção "Sair"
                break;
            default:
                JOptionPane.showMessageDialog(null, "Nenhuma opção selecionada.");
                break;
        }
    }

    public void CadastrarQuarto() {
        boolean dadosValidos = false;

        while (!dadosValidos) {
            JTextField numeroInput = new JTextField();
            JTextField tipoInput = new JTextField();
            JTextField capacidadeInput = new JTextField();
            JTextField precoInput = new JTextField();
            JTextField statusInput = new JTextField();

            Object[] message1 = {
                "Número do Quarto:", numeroInput,
                "Tipo (solteiro, casal, suíte):", tipoInput,
                "Capacidade:", capacidadeInput,
                "Preço:", precoInput,
                "Status (disponível, ocupado, manutenção):", statusInput // Adicionando o campo para status
            };

            int result1 = JOptionPane.showConfirmDialog(null, message1, "Cadastrar Quarto", JOptionPane.OK_CANCEL_OPTION);

            if (result1 == JOptionPane.OK_OPTION) {
                try {
                    int numero = Integer.parseInt(numeroInput.getText());
                    String tipo = tipoInput.getText();
                    int capacidade = Integer.parseInt(capacidadeInput.getText());
                    double preco = Double.parseDouble(precoInput.getText());
                    String status = statusInput.getText(); // Obtendo o status

                    // Chama o controller para cadastrar o quarto
                    quartoController.addQuarto(numero, tipo, capacidade, preco, status);

                    JOptionPane.showMessageDialog(null, "Quarto cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dadosValidos = true; // Sai do loop se os dados forem válidos
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Dados inválidos! Por favor, verifique os campos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar o quarto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Sai do loop se o usuário cancelar
                break;
            }
        }
    }

    public void ListaQuartosDisponiveis() {
        try {
            // Chama o controller para obter os quartos disponíveis
            String quartosDisponiveis = quartoController.obterQuartosDisponiveis();

            // Exibe a lista de quartos disponíveis em um JOptionPane
            JOptionPane.showMessageDialog(null, quartosDisponiveis, "Quartos Disponíveis", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar quartos disponíveis: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarStatusQuarto() {
        try {
            // Mostrar lista de todos os quartos
            String listaQuartos = quartoController.obterTodosQuartos(); // Método para listar todos os quartos
            JOptionPane.showMessageDialog(null, listaQuartos, "Lista de Quartos", JOptionPane.INFORMATION_MESSAGE);
    
            // Pedir o número do quarto a ser atualizado
            String numeroInput = JOptionPane.showInputDialog("Digite o número do quarto que deseja atualizar:");
            int numero = Integer.parseInt(numeroInput);
    
            // Pedir o novo status
            String novoStatus = JOptionPane.showInputDialog("Digite o novo status (disponível, ocupado, manutenção, reservado):");
    
            // Verificar se o status é válido
            if (!novoStatus.equals("disponível") && !novoStatus.equals("ocupado") && !novoStatus.equals("manutenção") && !novoStatus.equals("reservado")) {
                JOptionPane.showMessageDialog(null, "Status inválido! Os valores válidos são: disponível, ocupado, manutenção, reservado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Buscar os detalhes atuais do quarto
            Quarto quarto = quartoController.getQuartoByNumero(numero);
            if (quarto != null) {
                // Atualizar apenas o status do quarto
                quartoController.updateQuarto(
                    quarto.getNumero(), 
                    quarto.getTipo(), 
                    quarto.getCapacidade(), 
                    quarto.getPreco(), 
                    novoStatus
                );
                JOptionPane.showMessageDialog(null, "Status do quarto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Quarto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o status do quarto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número de quarto inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
