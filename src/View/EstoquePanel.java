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

import Control.EstoquesControl;
import Control.EstoquesDAO;
import Model.Estoque;

public class EstoquePanel extends JPanel {

    private JPanel buttonPanel;
    private JButton adicionaProduto, editaProduto, removeProduto;
    private JTextField inputCodigoProduto, inputNomeProduto, inputDataEntrada, inputQuantidade;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> estoques = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane jSPane;

    public EstoquePanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        adicionaProduto = new JButton("Adicionar Produto");
        removeProduto = new JButton("Remover Produto");
        editaProduto = new JButton("Editar Produto");
        inputCodigoProduto = new JTextField(7);
        inputNomeProduto = new JTextField(4);
        inputDataEntrada = new JTextField(5);
        inputQuantidade = new JTextField(12);

        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Controle de Estoque"));
        add(title);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 2, 4));
        inputPanel.add(new JLabel("Código do Produto:"));
        inputPanel.add(inputCodigoProduto);
        inputPanel.add(new JLabel("Nome do Produto:"));
        inputPanel.add(inputNomeProduto);
        inputPanel.add(new JLabel("Data de Entrada:"));
        inputPanel.add(inputDataEntrada);
        inputPanel.add(new JLabel("Quantidade em Estoque:"));
        inputPanel.add(inputQuantidade);
        add(inputPanel);

        buttonPanel = new JPanel();
        buttonPanel.add(adicionaProduto);
        buttonPanel.add(editaProduto);
        buttonPanel.add(removeProduto);
        add(buttonPanel);

        jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Código do Produto", "Nome do Produto", "Data de Entrada", "Quantidade em Estoque" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        new EstoquesDAO().criaTabela();
        atualizarTabela();

        adicionaProduto.setBackground(new Color(46, 128, 32));
        adicionaProduto.setForeground(new Color(255, 255, 255));
        removeProduto.setBackground(new Color(168, 3, 3));
        removeProduto.setForeground(new Color(255, 255, 255));
        editaProduto.setBackground(new Color(109, 110, 109));
        editaProduto.setForeground(new Color(255, 255, 255));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                linhaSelecionada = table.rowAtPoint(e.getPoint());
                if (linhaSelecionada != -1) {
                    inputCodigoProduto.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputNomeProduto.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputDataEntrada.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputQuantidade.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        EstoquesControl control = new EstoquesControl(estoques, tableModel, table);

        adicionaProduto.addActionListener(e -> {
            try {
                if (!inputCodigoProduto.getText().isEmpty() && !inputNomeProduto.getText().isEmpty()
                        && !inputDataEntrada.getText().isEmpty() && !inputQuantidade.getText().isEmpty()) {

                    if (control.validarNomeProduto(inputCodigoProduto.getText().trim())
                            && control.validarData(inputDataEntrada.getText().trim())) {

                        control.cadastrarEstoque(inputNomeProduto.getText(), inputQuantidade.getText(), inputNomeProduto.getText());

                        inputCodigoProduto.setText("");
                        inputNomeProduto.setText("");
                        inputDataEntrada.setText("");
                        inputQuantidade.setText("");

                    } else {
                        JOptionPane.showMessageDialog(inputPanel,
                                "Preencha os campos corretamente para adicionar um produto ao estoque!!", null,
                                JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(inputPanel,
                            "Preencha os campos corretamente para adicionar um produto ao estoque!!", null,
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception err) {
                System.out.println(err.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Verifique se os dados escritos estão corretos e tente novamente!", "ERRO!",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        editaProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int res = JOptionPane.showConfirmDialog(null, "Deseja atualizar as informações deste produto?",
                            "Editar Produto", JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION) {
                        if (control.validaCodigoProduto(inputCodigoProduto.getText().trim())
                                && control.validarData(inputDataEntrada.getText().trim())) {
                            control.editarProduto(inputCodigoProduto.getText(), inputNomeProduto.getText(),
                                    inputDataEntrada.getText(), inputQuantidade.getText());
                        } else {
                            JOptionPane.showMessageDialog(inputPanel,
                                    "Preencha os campos corretamente para atualizar os dados do produto!", null,
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

        removeProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja remover este produto do estoque?",
                        "Remover Produto", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    control.removerProduto(inputCodigoProduto.getText());
                    inputCodigoProduto.setText("");
                    inputNomeProduto.setText("");
                    inputDataEntrada.setText("");
                    inputQuantidade.setText("");
                }
            }
        });
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        estoques = new EstoquesDAO().listarTodos();
        for (Estoque estoques : estoques) {
            tableModel.addRow(new Object[] { estoques.getPreco(), estoques.getProdutos(),
                    estoques.getQuant()});
        }
    }
}