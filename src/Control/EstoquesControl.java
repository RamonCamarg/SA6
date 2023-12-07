package Control;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Model.Estoque;
import Control.EstoquesDAO;

public class EstoquesControl {
    // CRUD
    private List<Estoque> estoques;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public EstoquesControl(List<Estoque> estoques, DefaultTableModel tableModel, JTable table) {
        this.estoques = estoques;
        this.tableModel = tableModel;
        this.table = table;
    }
    // -------------------*
    // Métodos

    
    public void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        estoques = new EstoquesDAO().listarTodos();
        // Obtém os estoques atualizados do banco de dados
        for (Estoque estoques : estoques) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { estoques.getPreco(), estoques.getProdutos(),
                    estoques.getQuant()});
        }

    }

    public void cadastrarEstoque(String preco, String quant, String produtos ) {
        // Adiciona a tabela
        Estoque estoques = new Estoque(preco.trim().toUpperCase(), quant.trim().toUpperCase(), produtos.trim());
        estoques.add(estoques);
        // -----------------------*
        // Adicionar ao banco de dados
        new EstoquesDAO().cadastrar(preco, quant, produtos);
        // -----------------------*
        atualizarTabela();// Atualiza a tabela
        JOptionPane.showMessageDialog(null, "Carro cadastrado com sucesso!");
        // Atualiza o banco de dados
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String produtos) {
        new EstoquesDAO().apagar(produtos);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
        JOptionPane.showMessageDialog(table, "Produto Removido!", null, JOptionPane.ERROR_MESSAGE);
    }

    public void atualizar(String preco, String quant, String produtos) {
        new EstoquesDAO().atualizar(preco, quant, produtos);
        // Chama o método de atualização no banco de dados
        JOptionPane.showMessageDialog(null, "Estoque atualizado!", null, JOptionPane.INFORMATION_MESSAGE);
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    public boolean ProdutoCadastrado(String produtos) { // Verifica se a placa ja não foi cadastrada
        for (Estoque estoques : estoques) {
            if (estoques.getProdutos().equalsIgnoreCase(produtos)) {
                return false;
            }
        }
        return true;
    }

    public boolean validarValor(String preco) { // Verifica o texto digitado no inputValor *(apenas dígitos e número maior que 0)*
        if (preco.matches("[0-9]+") && Integer.parseInt(preco) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarQuant(String quant) { // Verfica o texto digitado no inputAno *(apenas dígitos e tamanho igual a 4 'ex: 2023')*
        if (quant.matches("[0-9]+") && quant.length() == 4) {
            return true;
        } else {
            return false;
        }
    }

}
