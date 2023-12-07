package Control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Model.Clientes;

public class ClientesControl {
    // CRUD
    private List<Clientes> clientes;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public ClientesControl(List<Clientes> clientes, DefaultTableModel tableModel, JTable table) {
        this.clientes = clientes;
        this.tableModel = tableModel;
        this.table = table;
    }
    // -------------------*
    // Métodos

    public void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        clientes = new ClientesDAO().listarTodos();
        // Obtém os clientes atualizados do banco de dados
        for (Clientes cliente : clientes) {
            // Adiciona os dados de cada cliente como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { cliente.getCpf(), cliente.getNome(), cliente.getDataNascimento(),
                    cliente.getIdade() });
        }

    }

    public void cadastrarCliente(String cpf, String nomeCompleto, String dataNascimento, String idade) {
        // Adiciona a tabela
        Clientes cliente = new Clientes(cpf.trim().toUpperCase(), nomeCompleto.trim().toUpperCase(),
                dataNascimento.trim(), idade.trim().toUpperCase());
        clientes.add(cliente);
        // -----------------------*
        // Adicionar ao banco de dados
        new ClientesDAO().cadastrar(cpf, nomeCompleto, dataNascimento, idade);
        // -----------------------*
        atualizarTabela();// Atualiza a tabela
        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
        // Atualiza o banco de dados
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String cpf) {
        new ClientesDAO().apagar(cpf);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
        JOptionPane.showMessageDialog(table, "Cliente removido!", null , JOptionPane.ERROR_MESSAGE);
    }

    public void atualizar(String cpf, String nomeCompleto, String dataNascimento, String idade) {
        new ClientesDAO().atualizar(cpf, nomeCompleto, dataNascimento, idade);
        // Chama o método de atualização no banco de dados
        JOptionPane.showMessageDialog(null, "Cliente atualizado", null, JOptionPane.INFORMATION_MESSAGE);
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    public boolean validaCpf(String cpf) { // Verifica o texto digitado no inputCpf (apenas dígitos e tamanho igual a 11 'ex: 12345678910')
        if (cpf.matches("[0-9]+") && cpf.length() == 11) {
            return true;
        } else {
            return false;
        }
    }
    public boolean validaIdade(String idade) { // Verifica o texto digitado no inputIdade (apenas dígitos e número maior que 0)
        if (idade.matches("[0-9]+") && Integer.parseInt(idade) > 0) {
            return true;
        } else {
            return false;
        }
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
}
