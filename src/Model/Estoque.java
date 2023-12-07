package Model;

public class Estoque {

 //atributos
 public String produtos;
 public String quant;
 public String preco;

    //construtor
    public Estoque(String produtos, String quant, String preco) {
        this.produtos = produtos;
        this.quant = quant;
        this.preco = preco;
}

    //gets and sets
    public String getProdutos() {
        return produtos;
    }
    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }
    public String getQuant() {
        return quant;
    }
    public void setQuant(String quant) {
        this.quant = quant;
    }
    public String getPreco() {
        return preco;
    }
    public void setPreco(String preco) {
        this.preco = preco;
    }

    public void add(Estoque estoques) {
    }

}