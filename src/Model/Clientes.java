package Model;

public class Clientes {

    // Atributos
    public String nome;
    public String dataNascimento;
    public String idade;
    public String cpf;

    //construtor
    public Clientes(String nome, String dataNascimento, String idade, String cpf) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.cpf = cpf;
    }

    //gets and sets
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}