package GerenciamentoHotelProjeto.MODEL;

public class Hospede extends Pessoa {
    private String endereco;
    private String contato;

    // Construtor
    public Hospede(int id, String nome, String cpf, String dataNascimento, String endereco, String contato) {
        super(id, nome, cpf, dataNascimento);  // Chama o construtor da classe Pessoa
        this.endereco = endereco;
        this.contato = contato;
    }

    // Getters e Setters
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}