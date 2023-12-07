package Control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Model.Vendas;

public class VendasControl {
    // CRUD
    private List<Vendas> vendas;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public VendasControl(List<Vendas> vendas, DefaultTableModel tableModel, JTable table) {
        this.vendas = vendas;
        this.tableModel = tableModel;
        this.table = table;
    }
    // -------------------*
    // Métodos

    public void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        vendas = new VendasDAO().listarTodos();
        // Obtém os clientes atualizados do banco de dados
        for (Vendas venda : vendas) {
            // Adiciona os dados de cada cliente como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { venda.getPreco(), venda.getCliente(), venda.getData(),
                    venda.getProdutos() });
        }

    }

    public void cadastrarVenda(String cliente, String preco, String produtos, String data) {
        // Adiciona a tabela
        Vendas venda = new Vendas(cliente.trim().toUpperCase(), preco.trim().toUpperCase(), produtos.trim(), data.trim());
        vendas.add(venda);
        // -----------------------*
        // Adicionar ao banco de dados
        new VendasDAO().cadastrar(cliente, preco, produtos, data);
        // -----------------------*
        atualizarTabela();// Atualiza a tabela
        JOptionPane.showMessageDialog(null, "Venda feita com sucesso!");
        // Atualiza o banco de dados
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String produtos) {
        new VendasDAO().apagar(produtos);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
        JOptionPane.showMessageDialog(table, "Venda cancelada!", null, JOptionPane.ERROR_MESSAGE);
    }

    public void atualizar(String cliente, String preco, String produtos, String data) {
        new VendasDAO().atualizar(cliente, preco, produtos, data);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    public boolean validarData(String date) { // Verifica se a data digitada segue o formato 'dd/mm/yyyy'
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate d = LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean validarValor(String preco) { // Verifica o texto digitado no inputValor *(apenas dígitos e número
                                                // maior que 0)*
        if (preco.matches("[0-9]+") && Integer.parseInt(preco) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
