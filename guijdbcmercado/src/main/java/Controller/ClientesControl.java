package Controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.ClientesDAO;
import Exception.*;
import Model.Clientes;

public class ClientesControl {

    private List<Clientes> clientesVip;
    private DefaultTableModel tableModel;
    private JTable table;

    public ClientesControl(List<Clientes> clientesVip, DefaultTableModel tableModel, JTable table) {
        this.clientesVip = clientesVip;
        this.tableModel = tableModel;
        this.table = table;
    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        clientesVip = new ClientesDAO().listarTodos();
        // Obtém os carros atualizados do banco de dados
        for (Clientes clientes : clientesVip) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { clientes.getNome(), clientes.getCpf() });
        }
    }

    // ATUALIZAR OS MÉTODOS DEPOIS

    // Método para cadastrar um novo cliente no banco de dados
    public void cadastrar(String nome, String cpf) {
        try {
            if (!(nome.isEmpty() || cpf.isEmpty())) {

                clientesVip = new ClientesDAO().listarTodos();
                boolean cpfJaCadastrado = false;

                for (Clientes clientes : clientesVip) {
                    if (cpf.equals(clientes.getCpf())) {
                        cpfJaCadastrado = true;
                        break; // CPF já está cadastrado, então podemos parar de procurar
                    }
                }

                if (!cpfJaCadastrado) {
                    new ClientesDAO().cadastrar(nome, cpf);
                    // Chama o método de cadastro no banco de dados

                    atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
                } else {
                    throw new CpfValidationException("CPF já cadastrado. Por favor, digite um novo CPF.");
                }

            } else {
                throw new NullPointerException("Informações inválidas. Por favor, preencha as informações vazias.");
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (CpfValidationException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro não identificado.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para atualizar os dados de um cliente no banco de dados
    public void atualizar(String nome, String cpf) {
        try {
            if (!(nome.isEmpty() || cpf.isEmpty())) {
                new ClientesDAO().Atualizar(nome, cpf);
                // Chama o método de atualização no banco de dados
                atualizarTabela(); // Atualiza a tabela de exibição após a atualização
            } else {
                throw new NullPointerException("Informações inválidas. Por favor preencha as informações vazias.");
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro não identificado.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para apagar um cliente do banco de dados
    public void apagar(String cpf) {
        try {
            if (!(cpf.isEmpty())) {
                new ClientesDAO().apagar(cpf);
                // Chama o método de exclusão no banco de dados
                atualizarTabela(); // Atualiza a tabela de exibição após a exclusão^
            } else {
                throw new NullPointerException("Erro de Seleção, por favor selecione uma linha.");
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro não identificado.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpar(String Nome, String cpf) {

        atualizarTabela();
    }

    public void desconto(String cpfDesconto) {

    }
}