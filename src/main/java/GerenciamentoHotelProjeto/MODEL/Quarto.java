package GerenciamentoHotelProjeto.MODEL;

public class Quarto {
    private int numero;
    private String tipo;
    private int capacidade;
    private double preco;
    private String status;

    // Construtor
    public Quarto(int numero, String tipo, int capacidade, double preco, String status) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.preco = preco;
        this.status = status;
    }

    // Getters e Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
