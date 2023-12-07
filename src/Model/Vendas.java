package Model;

public class Vendas {
    // Atributos
    String cliente;
    String produtos;
    String preco;
    String data;
    
    //construtor
    public Vendas(String cliente, String produtos, String preco, String data) {
        this.cliente = cliente;
        this.produtos = produtos;
        this.preco = preco;
        this.data = data;
    }

    //gets and sets
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProdutos() {
        return produtos;
    }

    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}