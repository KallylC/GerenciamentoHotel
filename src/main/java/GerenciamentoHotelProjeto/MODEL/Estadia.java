package GerenciamentoHotelProjeto.MODEL;

public class Estadia {
    private int id;
    private int idQuarto;
    private int idHospede;
    private String dataCheckin;
    private String dataCheckout;
    private double valor;

    // Construtor com todos os parâmetros (exceto id, que será gerado automaticamente)
    public Estadia(int idQuarto, int idHospede, String dataCheckin, String dataCheckout, Double valor) {
        this.idQuarto = idQuarto;
        this.idHospede = idHospede;
        this.dataCheckin = dataCheckin;
        this.dataCheckout = dataCheckout;
        this.valor = valor;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    public int getIdHospede() {
        return idHospede;
    }

    public void setIdHospede(int idHospede) {
        this.idHospede = idHospede;
    }

    public String getDataCheckin() {
        return dataCheckin;
    }

    public void setDataCheckin(String dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public String getDataCheckout() {
        return dataCheckout;
    }

    public void setDataCheckout(String dataCheckout) {
        this.dataCheckout = dataCheckout;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
