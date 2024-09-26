package GerenciamentoHotelProjeto.DB;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    // Caminho relativo ao diretório de trabalho atual do projeto
    private static final String DB_DIRECTORY = "testegerenciamentohotel/src/main/java/GerenciamentoHotelProjeto/DB";
    private static final String DB_FILE = "HotelDB.db";
    private static final String URL = "jdbc:sqlite:" + new File(DB_DIRECTORY, DB_FILE).getAbsolutePath();
    private static Connection connection;

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            File dbDir = new File(DB_DIRECTORY);
            File dbFile = new File(DB_DIRECTORY, DB_FILE);

            // Verifica se o diretório DB existe, se não, cria-o
            if (!dbDir.exists()) {
                if (dbDir.mkdirs()) {
                    System.out.println("Diretório DB criado com sucesso.");
                } else {
                    throw new SQLException("Não foi possível criar o diretório DB.");
                }
            }

            // Verifica se o arquivo DB existe, se não, cria-o e configura as tabelas
            if (!dbFile.exists()) {
                connection = DriverManager.getConnection(URL);
                createTables(); // Cria as tabelas quando o banco de dados é criado
            } else {
                // O banco de dados já existe
                connection = DriverManager.getConnection(URL);
            }
        }
        return connection;
    }

    // Método para criar as tabelas no banco de dados
    private static void createTables() {
        try (Statement stmt = connection.createStatement()) {
            // Criação da tabela de hóspedes
            String createHospedesTableSQL = "CREATE TABLE IF NOT EXISTS hospedes (" +
                                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "nome TEXT NOT NULL, " +
                                            "cpf TEXT NOT NULL UNIQUE, " +
                                            "dataNascimento TEXT NOT NULL, " +
                                            "endereco TEXT, " +
                                            "numeroContato TEXT);";
            // Criação da tabela de reservas
            String createReservasTableSQL = "CREATE TABLE IF NOT EXISTS reservas (" +
                                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "cpfHospede TEXT NOT NULL, " +
                                            "numeroQuarto INTEGER NOT NULL, " +
                                            "dataEntrada TEXT NOT NULL, " +
                                            "dataSaida TEXT NOT NULL, " +
                                            "quantidadeHospedes INTEGER NOT NULL, " +
                                            "FOREIGN KEY (numeroQuarto) REFERENCES quartos(numero), " +
                                            "FOREIGN KEY (cpfHospede) REFERENCES hospedes(cpf));";
            // Criação da tabela de funcionários
            String createFuncionariosTableSQL = "CREATE TABLE IF NOT EXISTS funcionarios (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "nome TEXT NOT NULL, " +
                                                "cpf TEXT NOT NULL UNIQUE, " +
                                                "dataNascimento TEXT NOT NULL, " +
                                                "cargo TEXT NOT NULL, " +
                                                "salario REAL NOT NULL, " +
                                                "turno TEXT NOT NULL" +
                                                ");";
            // Criação da tabela de quartos
            String createQuartosTableSQL = "CREATE TABLE IF NOT EXISTS quartos (" +
                                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "numero INTEGER NOT NULL UNIQUE, " +
                                            "tipo TEXT NOT NULL, " +
                                            "capacidade INTEGER NOT NULL, " +
                                            "preco REAL NOT NULL, " +
                                            "status TEXT NOT NULL CHECK(status IN ('disponível', 'ocupado', 'em manutenção', 'reservado')));";

            String createEstadiaTableSQL = "CREATE TABLE IF NOT EXISTS estadia (" +
                                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "id_quarto INTEGER NOT NULL, " +
                                            "id_hospede INTEGER NOT NULL, " +
                                            "data_checkin TEXT NOT NULL, " +
                                            "data_checkout TEXT, " + 
                                            "valor REAL NOT NULL, " +
                                            "FOREIGN KEY(id_quarto) REFERENCES quartos(numero), " +
                                            "FOREIGN KEY(id_hospede) REFERENCES hospedes(id)" +
                                            ");";

            // Executa os comandos SQL para criar as tabelas
            stmt.execute(createHospedesTableSQL);
            stmt.execute(createReservasTableSQL);
            stmt.execute(createFuncionariosTableSQL);
            stmt.execute(createQuartosTableSQL);
            stmt.execute(createEstadiaTableSQL);

            System.out.println("Tabelas criadas com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
