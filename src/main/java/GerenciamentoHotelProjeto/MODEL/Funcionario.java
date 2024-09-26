package GerenciamentoHotelProjeto.MODEL;

public class Funcionario extends Pessoa {
    private String cargo;
    private double salario;
    private String turno;

    // Construtor
    public Funcionario(int id, String nome, String cpf, String dataNascimento, String cargo, double salario, String turno) {
        super(id, nome, cpf, dataNascimento); // Chama o construtor da classe Pessoa
        this.cargo = cargo;
        this.salario = salario;
        this.turno = turno;
    }

    // Getters e Setters
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
