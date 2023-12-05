package Model;

public class Clientes {
    //Atributos
    public String nome;
    public String endereco;
    public String numTel;
    public String cpf;
    public String dataNasc;

    //Construtor
    public Clientes(String nome, String endereco, String numTel, String cpf, String dataNasc) {

        this.nome = nome;
        this.endereco = endereco;
        this.numTel = numTel;
        this.cpf = cpf;
        this.dataNasc = dataNasc;
    }

    //Getters and setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    //Construtor default
    public Clientes() {
        super();
    }
    
}
