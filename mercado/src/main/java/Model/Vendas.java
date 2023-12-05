package Model;

public class Vendas {
    //Atributos
    private String dataVenda;
    private String carroVendido;
    private String cliente;
    private String valorCompra;
    
    //Construtor
    public Vendas(String dataVenda, String carroVendi, String cliente, String valorCompra) {
        this.dataVenda = dataVenda;
        this.carroVendido = carroVendido;
        this.cliente = cliente;
        this.valorCompra = valorCompra;
    }

    //Getters and Setters
    public String getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }
    public String getCarroVendido() {
        return carroVendido;
    }
    public void setCarroVendido(String carroVendi) {
        this.carroVendido = carroVendido;
    }
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public String getValorCompra() {
        return valorCompra;
    }
    public void setValorCompra(String valorCompra) {
        this.valorCompra = valorCompra;
    }

    //Construtor default
    public Vendas() {
        super();
    }
}
