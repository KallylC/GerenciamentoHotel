package GerenciamentoHotelProjeto.MODEL;

public class Reserva {
    private int id;
    private String cpfHospede;
    private int numeroQuarto;
    private String dataEntrada;
    private String dataSaida;
    private int quantidadeHospedes; // Novo atributo

    public Reserva(int id, String cpfHospede, int numeroQuarto, String dataEntrada, String dataSaida, int quantidadeHospedes) {
        this.id = id;
        this.cpfHospede = cpfHospede;
        this.numeroQuarto = numeroQuarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.quantidadeHospedes = quantidadeHospedes; // Inicializa novo atributo
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCpfHospede() {
        return cpfHospede;
    }
    public void setCpfHospede(String cpfHospede) {
        this.cpfHospede = cpfHospede;
    }

    public int getNumeroQuarto() {
        return numeroQuarto;
    }
    public void setNumeroQuarto(int numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }
    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }
    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public int getQuantidadeHospedes() {
        return quantidadeHospedes;
    }
    public void setQuantidadeHospedes(int quantidadeHospedes) {
        this.quantidadeHospedes = quantidadeHospedes;
    }

    @Override
    public String toString() {
        return "Reserva [id=" + id + ", cpfHospede=" + cpfHospede + ", numeroQuarto=" + numeroQuarto +
               ", dataEntrada=" + dataEntrada + ", dataSaida=" + dataSaida + ", quantidadeHospedes=" + quantidadeHospedes + "]";
    }
}
