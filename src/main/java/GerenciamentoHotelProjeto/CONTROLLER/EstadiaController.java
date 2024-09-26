package GerenciamentoHotelProjeto.CONTROLLER;

import java.sql.Connection;
import GerenciamentoHotelProjeto.DB.EstadiaDAO;
import GerenciamentoHotelProjeto.MODEL.Estadia;
import java.util.List;
import java.sql.SQLException;

public class EstadiaController {

    private EstadiaDAO estadiaDAO;

    public EstadiaController(Connection connection) {
        this.estadiaDAO = new EstadiaDAO(connection);
    }

    // Método para adicionar uma nova estadia
    public boolean adicionarEstadia(Estadia estadia) {
        return estadiaDAO.addEstadia(estadia);
    }

    // Método para atualizar a estadia
    public void atualizarEstadia(Estadia estadia) throws SQLException {
        estadiaDAO.updateEstadia(estadia);
    }

    // Dentro da classe EstadiaController
    public List<Estadia> getAllEstadias() throws SQLException {
        return estadiaDAO.getAllEstadias();
    }

    // Método para deletar uma estadia
    public boolean deletarEstadia(int id) {
        return estadiaDAO.deleteEstadia(id);
    }
}
