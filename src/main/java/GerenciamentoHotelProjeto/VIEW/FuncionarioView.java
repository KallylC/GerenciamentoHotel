package GerenciamentoHotelProjeto.VIEW;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import GerenciamentoHotelProjeto.CONTROLLER.FuncionarioController;
import GerenciamentoHotelProjeto.DB.DBConnection;
import GerenciamentoHotelProjeto.MODEL.Funcionario;

public class FuncionarioView {
    private FuncionarioController funcionarioController;
    private Connection conn;

    public FuncionarioView() {
        try {
            conn = DBConnection.getConnection();
            funcionarioController = new FuncionarioController(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Fecha a aplicação se não conseguir conectar
        }
    }

    public void FuncionariosView() {
        int menuInput = 0;
        String[] menu = { "Cadastrar Funcionário", "Editar Informações", "Lista de funcionários", "Registrar Horas/calcular salário", "Sair" };

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
                // Opção "Cadastrar Funcionário"
                cadastrarFuncionario();
                break;
            case 1:
                // Opção "Editar Informações"
                atualizarInformacoesFuncionario();
                break;
            case 2:
                // Opção "Lista De Funcionários"
                listarFuncionarios();
                break;
            case 3:
                // Opção "Registrar Horas"
                registrarHorasECalcularSalario();
                break;
            case 4:
                // Opção "Sair"
                fecharConexao();
                System.exit(0);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Nenhuma opção selecionada.");
                break;
        }
    }

    private void cadastrarFuncionario() {
        boolean dadosValidos = false;

        while (!dadosValidos) {
            JTextField nomeInput = new JTextField();
            JTextField cpfInput = new JTextField();
            JTextField dataNascimentoInput = new JTextField();
            JTextField cargoInput = new JTextField();
            JTextField salarioInput = new JTextField();
            JTextField turnoInput = new JTextField();

            Object[] message = {
                "Nome do Funcionário:", nomeInput,
                "CPF:", cpfInput,
                "Data de Nascimento (formato: DD/MM/AAAA):", dataNascimentoInput,
                "Cargo:", cargoInput,
                "Salário:", salarioInput,
                "Turno:", turnoInput
            };

            int result = JOptionPane.showConfirmDialog(null, message, "Cadastrar Funcionário", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String nome = nomeInput.getText().trim();
                    String cpf = cpfInput.getText().trim();
                    String dataNascimento = dataNascimentoInput.getText().trim();
                    String cargo = cargoInput.getText().trim();
                    double salario = Double.parseDouble(salarioInput.getText().trim());
                    String turno = turnoInput.getText().trim();

                    // Validação dos dados
                    if (nome.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty() || cargo.isEmpty() || turno.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                        continue;
                    }

                    // Chama o controller para cadastrar o funcionário
                    funcionarioController.addFuncionario(nome, cpf, dataNascimento, cargo, salario, turno);

                    JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dadosValidos = true; // Sai do loop se os dados forem válidos
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar o funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Sai do loop se o usuário cancelar
                break;
            }
        }
    }

    private void atualizarInformacoesFuncionario() {
        try {
            // Mostrar lista de funcionários
            String listaFuncionarios = funcionarioController.obterTodosFuncionarios().toString(); // Método para listar todos os funcionários
            JOptionPane.showMessageDialog(null, listaFuncionarios, "Lista de Funcionários", JOptionPane.INFORMATION_MESSAGE);

            // Pedir o ID do funcionário a ser atualizado
            String idInput = JOptionPane.showInputDialog("Digite o ID do funcionário que deseja atualizar:");

            // Verificar se o ID é um número válido
            int id;
            try {
                id = Integer.parseInt(idInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID inválido. Por favor, insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buscar os detalhes atuais do funcionário pelo ID
            Funcionario funcionario = funcionarioController.getFuncionarioById(id);
            if (funcionario != null) {
                // Pedir novas informações do funcionário
                JTextField nomeInput = new JTextField(funcionario.getNome());
                JTextField cpfInput = new JTextField(funcionario.getCpf());
                JTextField dataNascimentoInput = new JTextField(funcionario.getDataNascimento());
                JTextField cargoInput = new JTextField(funcionario.getCargo());
                JTextField salarioInput = new JTextField(String.valueOf(funcionario.getSalario()));
                JTextField turnoInput = new JTextField(funcionario.getTurno());

                Object[] message = {
                    "Nome:", nomeInput,
                    "CPF:", cpfInput,
                    "Data de Nascimento:", dataNascimentoInput,
                    "Cargo:", cargoInput,
                    "Salário:", salarioInput,
                    "Turno:", turnoInput
                };

                int result = JOptionPane.showConfirmDialog(null, message, "Atualizar Informações do Funcionário", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    // Atualizar as informações do funcionário
                    funcionarioController.updateFuncionario(
                        id, // Passar o ID para atualizar
                        nomeInput.getText(),
                        cpfInput.getText(),
                        dataNascimentoInput.getText(),
                        cargoInput.getText(),
                        Double.parseDouble(salarioInput.getText()),
                        turnoInput.getText()
                    );
                    JOptionPane.showMessageDialog(null, "Informações do funcionário atualizadas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Funcionário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar as informações do funcionário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarFuncionarios() {
        try {
            String listaFuncionarios = funcionarioController.obterTodosFuncionarios();
            JOptionPane.showMessageDialog(null, listaFuncionarios, "Lista de Funcionários", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar funcionários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarHorasECalcularSalario() {
        try {
            // Solicitar ID do funcionário
            String idInput = JOptionPane.showInputDialog("Digite o ID do funcionário:");
            int idFuncionario = Integer.parseInt(idInput);

            // Solicitar horas semanais trabalhadas
            String horasInput = JOptionPane.showInputDialog("Digite o número de horas trabalhadas na semana:");
            double horasTrabalhadas = Double.parseDouble(horasInput);

            // Solicitar valor da hora
            String valorHoraInput = JOptionPane.showInputDialog("Digite o valor da hora (em R$):");
            double valorHora = Double.parseDouble(valorHoraInput);

            // Calcular salário
            double salarioTotal = horasTrabalhadas * valorHora;

            // Atualizar o salário no banco de dados
            funcionarioController.atualizarSalario(idFuncionario, salarioTotal);

            // Mostrar o salário total
            JOptionPane.showMessageDialog(null, "O salário total calculado é: R$ " + salarioTotal);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar horas ou calcular salário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
