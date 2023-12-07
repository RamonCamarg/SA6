package View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Control.VendasControl;
import Control.VendasDAO;
import Model.Vendas;

public class VendasPanel extends JPanel {

    private JPanel buttonPanel;
    private JButton realizaVenda, editaVenda, cancelaVenda;
    private JTextField inputCliente, inputProduto, inputData, inputPreco;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Vendas> vendas = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane jSPane;

    public VendasPanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        realizaVenda = new JButton("Realizar Venda");
        cancelaVenda = new JButton("Cancelar Venda");
        editaVenda = new JButton("Editar Venda");
        inputCliente = new JTextField(7);
        inputProduto = new JTextField(4);
        inputData = new JTextField(5);
        inputPreco = new JTextField(12);

        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Registro de Vendas"));
        add(title);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 2, 4));
        inputPanel.add(new JLabel("ID do Cliente:"));
        inputPanel.add(inputCliente);
        inputPanel.add(new JLabel("Produto Vendido:"));
        inputPanel.add(inputProduto);
        inputPanel.add(new JLabel("Data da Venda:"));
        inputPanel.add(inputData);
        inputPanel.add(new JLabel("Valor Total:"));
        inputPanel.add(inputPreco);
        add(inputPanel);

        buttonPanel = new JPanel();
        buttonPanel.add(realizaVenda);
        buttonPanel.add(editaVenda);
        buttonPanel.add(cancelaVenda);
        add(buttonPanel);

        jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Cliente", "Produto", "Data da Venda", "Valor Total" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        new VendasDAO().criaTabela();
        atualizarTabela();

        realizaVenda.setBackground(new Color(46, 128, 32));
        realizaVenda.setForeground(new Color(255, 255, 255));
        cancelaVenda.setBackground(new Color(168, 3, 3));
        cancelaVenda.setForeground(new Color(255, 255, 255));
        editaVenda.setBackground(new Color(109, 110, 109));
        editaVenda.setForeground(new Color(255, 255, 255));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                linhaSelecionada = table.rowAtPoint(e.getPoint());
                if (linhaSelecionada != -1) {
                    inputCliente.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputProduto.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputData.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputPreco.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        VendasControl control = new VendasControl(vendas, tableModel, table);

        realizaVenda.addActionListener(e -> {
            try {
                if (!inputCliente.getText().isEmpty() && !inputProduto.getText().isEmpty()
                        && !inputData.getText().isEmpty() && !inputPreco.getText().isEmpty()) {

                    if (control.validaClientes(inputCliente.getText().trim())
                            && control.validarData(inputData.getText().trim())) {

                        control.realizarVendas(inputCliente.getText(), inputProduto.getText(),
                                inputData.getText(), inputPreco.getText());

                        inputCliente.setText("");
                        inputProduto.setText("");
                        inputData.setText("");
                        inputPreco.setText("");

                    } else {
                        JOptionPane.showMessageDialog(inputPanel,
                                "Preencha os campos corretamente para realizar uma venda!", null,
                                JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(inputPanel,
                            "Preencha os campos corretamente para realizar uma venda!", null,
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception err) {
                System.out.println(err.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Verifique se os dados escritos estão corretos e tente novamente!", "ERRO!",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        editaVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int res = JOptionPane.showConfirmDialog(null, "Deseja atualizar as informações desta venda?",
                            "Editar Venda", JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION) {
                        if (control.validaClientes(inputCliente.getText().trim())
                                && control.validarData(inputData.getText().trim())) {
                            control.editarVendas(inputCliente.getText(), inputProduto.getText(),
                                    inputData.getText(), inputPreco.getText());
                        } else {
                            JOptionPane.showMessageDialog(inputPanel,
                                    "Preencha os campos corretamente para atualizar os dados da venda!", null,
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                    JOptionPane.showMessageDialog(null,
                            "Verifique se os dados escritos estão corretos e tente novamente!", "ERRO!",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancelaVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja cancelar esta venda?", "Cancelar Venda",
                        JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    control.cancelarVendas(inputCliente.getText());
                    inputCliente.setText("");
                    inputProduto.setText("");
                    inputData.setText("");
                    inputPreco.setText("");
                }
            }
        });
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Vendas venda : vendas) {
            tableModel.addRow(new Object[] { venda.getCliente(), venda.getProdutos(), venda.getData(),
                    venda.getPreco() });
        }
    }
}
