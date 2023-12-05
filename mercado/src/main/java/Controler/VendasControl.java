package Controler;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.VendasDAO;
import Model.Vendas;

public class VendasControl { // AQUI É ONDE SE AFUNILA AS INFOMAÇÕES

    // Atributos
    private List<Vendas> vendas;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public VendasControl(List<Vendas> vendas, DefaultTableModel tableModel, JTable table) {
        this.vendas = vendas;
        this.tableModel = tableModel;
        this.table = table;

    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        vendas = new VendasDAO().listarVendas();
        // Obtém os carros atualizados do banco de dados
        for (Vendas venda : vendas) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { venda.getCliente(), venda.getDataVenda(),
                    venda.getCarroVendido(), venda.getValorCompra()});
        }
    }

    // Método para cadastrar um novo carro no banco de dados
    public void cadastrar(String dataVenda, String carroVendido, String cliente, String valorCompra) {
        new VendasDAO().comprar(dataVenda, carroVendido, cliente , valorCompra);
        // Chama o método de cadastro no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro

    }

    // Método para atualizar os dados de um carro no banco de dados
    public void atualizar(String dataVenda, String carroVendido, String cliente, String valorCompra) {
        new VendasDAO().historico(dataVenda, carroVendido, cliente, valorCompra);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String carroVendido) { //Placa é a minha chave primária
        new VendasDAO().limpar(carroVendido, carroVendido, carroVendido, carroVendido);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }

}
