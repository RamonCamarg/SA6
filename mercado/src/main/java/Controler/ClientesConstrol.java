package Controler;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.ClientesDAO;
import Model.Clientes;

public class ClientesConstrol { // AQUI É ONDE SE AFUNILA AS INFOMAÇÕES

    // Atributos
    private List<Clientes> clientes;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public ClientesConstrol(List<Clientes> clientes, DefaultTableModel tableModel, JTable table) {
        this.clientes = clientes;
        this.tableModel = tableModel;
        this.table = table;

    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        clientes = new ClientesDAO().listarClientes();
        // Obtém os carros atualizados do banco de dados
        for (Clientes cliente : clientes) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { cliente.getNome() , cliente.getEndereco() , cliente.getCpf() ,cliente.getDataNasc() , cliente.getNumTel() });
        }
    }

    // Método para cadastrar um novo cliente no banco de dados
    public void cadastrar (String nome, String endereco,  String numTel, String cpf, String dataNasc){
        // Chama o método de cadastro no banco de dados
        new ClientesDAO().cadastrar(nome, endereco, numTel, cpf, dataNasc);
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro

    }

    // Método para atualizar os dados de um cliente no banco de dados
    public void atualizar(String nome, String endereco,  String numTel, String cpf, String dataNasc) {
        new ClientesDAO().atualizar(nome, endereco, numTel, cpf, dataNasc);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um cliente do banco de dados
    public void apagar(String cpf) { //Placa é a minha chave primária
        new ClientesDAO().apagar(cpf);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }
}

