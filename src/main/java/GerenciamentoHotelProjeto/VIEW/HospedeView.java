package GerenciamentoHotelProjeto.VIEW;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import GerenciamentoHotelProjeto.CONTROLLER.HospedeController;
import GerenciamentoHotelProjeto.DB.DBConnection;
import GerenciamentoHotelProjeto.MODEL.Hospede;

public class HospedeView {
    private HospedeController hospedeController; 
    private Connection conn;

    public HospedeView() {
        try {
            conn = DBConnection.getConnection();
            hospedeController = new HospedeController(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Fecha a aplicação se não conseguir conectar
        }
    }

    public void HospedesView() {
        int menuInput = 0;
        String[] menu = { "Cadastrar Hóspede", "Lista Hóspedes", "Editar Informações", "Sair" };

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
                // Opção "Cadastrar Hóspede"
                cadastrarHospede();
                break;
            case 1:
                ListarHospedes();
                break;
            case 2:
                atualizarInformacoesHospede();
                break;
            case 3:
                fecharConexao();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Nenhuma opção selecionada.");
                break;
        }
    }

    private void cadastrarHospede() {
        boolean dadosValidos = false;
    
        while (!dadosValidos) {
            JTextField nomeInput = new JTextField();
            JTextField cpfInput = new JTextField();
            JTextField dataNascimentoInput = new JTextField();
            JTextField enderecoInput = new JTextField();
            JTextField contatoInput = new JTextField();
    
            Object[] message = {
                "Nome do Hóspede:", nomeInput,
                "CPF:", cpfInput,
                "Data de Nascimento (formato: DD/MM/AAAA):", dataNascimentoInput,
                "Endereço:", enderecoInput,
                "Contato:", contatoInput
            };
    
            int result = JOptionPane.showConfirmDialog(null, message, "Cadastrar Hóspede", JOptionPane.OK_CANCEL_OPTION);
    
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nome = nomeInput.getText().trim();
                    String cpf = cpfInput.getText().trim();
                    String dataNascimento = dataNascimentoInput.getText().trim();
                    String endereco = enderecoInput.getText().trim();
                    String contato = contatoInput.getText().trim();
    
                    // Validação dos dados
                    if (nome.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty() || endereco.isEmpty() || contato.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
    
                    // Chama o controller para cadastrar o hóspede
                    hospedeController.addHospede(nome, cpf, dataNascimento, endereco, contato);
    
                    JOptionPane.showMessageDialog(null, "Hóspede cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dadosValidos = true; // Sai do loop se os dados forem válidos
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar o hóspede: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Sai do loop se o usuário cancelar
                break;
            }
        }
    }
     
    private void atualizarInformacoesHospede() {
        try {
            // Mostrar lista de hóspedes
            String listaHospedes = hospedeController.obterTodosHospedes(); // Método para listar todos os hóspedes
            JOptionPane.showMessageDialog(null, listaHospedes, "Lista de Hóspedes", JOptionPane.INFORMATION_MESSAGE);
    
            // Pedir o ID do hóspede a ser atualizado
            String idInput = JOptionPane.showInputDialog("Digite o ID do hóspede que deseja atualizar:");
    
            // Verificar se o ID é um número válido
            int id;
            try {
                id = Integer.parseInt(idInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID inválido. Por favor, insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Buscar os detalhes atuais do hóspede pelo ID
            Hospede hospede = hospedeController.getHospedeById(id); // Método a ser criado na classe HospedeController
            if (hospede != null) {
                // Pedir novas informações do hóspede
                JTextField nomeInput = new JTextField(hospede.getNome());
                JTextField dataNascimentoInput = new JTextField(hospede.getDataNascimento());
                JTextField enderecoInput = new JTextField(hospede.getEndereco());
                JTextField contatoInput = new JTextField(hospede.getContato());
    
                Object[] message = {
                    "Nome:", nomeInput,
                    "Data de Nascimento:", dataNascimentoInput,
                    "Endereço:", enderecoInput,
                    "Contato:", contatoInput
                };
    
                int result = JOptionPane.showConfirmDialog(null, message, "Atualizar Informações do Hóspede", JOptionPane.OK_CANCEL_OPTION);
    
                if (result == JOptionPane.OK_OPTION) {
                    // Atualizar as informações do hóspede
                    hospedeController.updateHospede(
                        id, // Passar o ID para atualizar
                        nomeInput.getText(),
                        dataNascimentoInput.getText(),
                        enderecoInput.getText(),
                        contatoInput.getText()
                    );
                    JOptionPane.showMessageDialog(null, "Informações do hóspede atualizadas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
    
            } else {
                JOptionPane.showMessageDialog(null, "Hóspede não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar as informações do hóspede: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }    
    private void ListarHospedes() {
        try {
            String listaHospedes = hospedeController.obterTodosHospedes(); // Método para listar todos os hóspedes
            JOptionPane.showMessageDialog(null, listaHospedes, "Lista de Hóspedes", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar hóspedes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }  
    private void fecharConexao() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexão com o banco de dados fechada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
        }
    }
}
