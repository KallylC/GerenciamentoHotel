package GerenciamentoHotelProjeto.VIEW;

import GerenciamentoHotelProjeto.CONTROLLER.QuartoController;
import GerenciamentoHotelProjeto.CONTROLLER.ReservaController;
import GerenciamentoHotelProjeto.CONTROLLER.EstadiaController;
import GerenciamentoHotelProjeto.DB.QuartoDAO;
import GerenciamentoHotelProjeto.DB.HospedeDAO;
import GerenciamentoHotelProjeto.DB.ReservaDAO;
import GerenciamentoHotelProjeto.DB.DBConnection;
import GerenciamentoHotelProjeto.DB.EstadiaDAO;
import java.sql.Connection;
import java.sql.SQLException;

public class ViewManager {

    private ReservaController reservaController;
    private EstadiaController estadiaController;

    private QuartoDAO quartoDAO;
    private HospedeDAO hospedeDAO;
    public ViewManager() {
        // Inicializa os DAOs e controladores
        try {
            // Supondo que você tem uma classe DBConnection para criar a conexão
            Connection connection = DBConnection.getConnection();
            
            // Inicializa os DAOs
            quartoDAO = new QuartoDAO(connection);
            hospedeDAO = new HospedeDAO(connection);
            new ReservaDAO(connection);
            new EstadiaDAO(connection);
            
            // Inicializa os controladores
            reservaController = new ReservaController(connection);
            estadiaController = new EstadiaController(connection);  // Supondo que o construtor de EstadiaController é com EstadiaDAO
            new QuartoController(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Adicione tratamento de erro apropriado
        }
    }

    public void exibirMenuQuartos() {
        QuartoView quartoView = new QuartoView();
        quartoView.quartosView(); // Exibe o menu de opções
    }

    public void exibirMenuHospede() {
        HospedeView hospedeView = new HospedeView();
        hospedeView.HospedesView(); // Exibe o menu de opções
    }

    public void exibirMenuReserva() {
        ReservaView reservaView = new ReservaView();
        reservaView.ReservasView(); // Exibe o menu de opções
    }

    public void exibirMenuFuncionario() {
        FuncionarioView funcionarioView = new FuncionarioView();
        funcionarioView.FuncionariosView(); // Exibe o menu de opções
    }

    public void exibirMenuCheckin() {
        CheckinView checkinView = new CheckinView(reservaController, estadiaController, quartoDAO, hospedeDAO);
        checkinView.CheckinsView(); // Exibe o menu de opções
    }

    public static void main(String[] args) {
        // Cria uma instância de ViewManager e chama o método para exibir a MainView
        ViewManager viewManager = new ViewManager();
        viewManager.exibirMenuQuartos();
    }
}
