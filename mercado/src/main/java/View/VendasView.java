package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import Connection.VendasDAO;
import Controler.VendasControl;
import Connection.CarrosDAO;
import Connection.ClientesDAO;
import Model.Carros;
import Model.Clientes;
import Model.Vendas;

public class VendasView extends JPanel {

    private JComboBox<String> carrosComboBox;
    private List<Carros> carros;
    private JButton comprar, limpar, historico;
    private JComboBox<String> clienteComboBox;
    private List<Clientes> clientes;
    private List<Vendas> vendas;
    private JTextField dataVenda;
    private JTextField valorCompra;

    private JTable tableClient;
    private DefaultTableModel tableModelClient;
    private int linhaSelecionada = -1; // Valor para quando não selecionar nada

    public VendasView() {
        super();
        new VendasDAO().criaTabela();

        JPanel painelPrinc = new JPanel(new GridLayout(4, 4));
        add(painelPrinc);

        // Listar carros cadastrados no JCombobox
        carrosComboBox = new JComboBox<>();
        // Listar clientes cadastrados no JCombobox
        clienteComboBox = new JComboBox<>();

        // Preencha o JComboBox com os campos
        // Carros
        carros = new CarrosDAO().listarTodos();
        carrosComboBox.addItem("Selecionar o carro");
        // Clientes
        clientes = new ClientesDAO().listarClientes();
        clienteComboBox.addItem("Selecionar o cliente");
        // Data Venda
        dataVenda = new JTextField();

        // Valor Compra
        valorCompra = new JTextField();

        // Preenche o comboBox
        for (Carros carro : carros) {
            carrosComboBox
                    .addItem(carro.getMarca() + "\t" + carro.getModelo() + "\t" + carro.getPlaca() + "\t"
                            + carro.getAno() + "\t" + carro.getValor());
        }

        for (Clientes cliente : clientes) {
            clienteComboBox.addItem(cliente.getNome() + "\t" + cliente.getCpf());
        }

        // Adiciona os componentes
        painelPrinc.add(new JLabel("Carro: "));
        painelPrinc.add(carrosComboBox);
        painelPrinc.add(new JLabel("Cliente: "));
        painelPrinc.add(clienteComboBox);
        painelPrinc.add(new JLabel("Digite a data da venda: "));
        painelPrinc.add(dataVenda);
        painelPrinc.add(new JLabel("Digite o valor da venda"));
        painelPrinc.add(valorCompra);

        // Criação de um painel para conter os botoes
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align buttons to the left
        comprar = new JButton("Comprar");
        historico = new JButton("Histórico");
        limpar = new JButton("Limpar");
        botoes.add(comprar);
        botoes.add(limpar);
        botoes.add(historico);
        add(botoes);

        // tabela de carros
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);

        DefaultTableModel TableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Data Venda", "Carro Vendido", "Cliente", "Valor de Compra" });
        tableClient = new JTable(tableModelClient);
        jSPane.setViewportView(tableClient);

        // Tratameno de eventos
        clienteComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                clienteComboBox.removeAllItems();
                clienteComboBox.addItem("Selecionar o cliente");
                for (Clientes cliente : clientes) {
                    clienteComboBox.addItem(cliente.getNome() + " \t" + cliente.getCpf());
                }
            }
        });

        carrosComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                carrosComboBox.removeAllItems();
                carrosComboBox.addItem("Selecionar o carro");
                for (Carros carro : carros) {
                    carrosComboBox
                            .addItem(carro.getMarca() + " \t" + carro.getModelo());
                }
            }
        });

        VendasControl control = new VendasControl(vendas, tableModelClient, tableClient);
        comprar.addActionListener(e -> {
            control.cadastrar(dataVenda.getText(), String.valueOf(carrosComboBox.getSelectedItem()),
                    String.valueOf(clienteComboBox.getSelectedItem()), valorCompra.getText());
        });
    }

    // Outros métodos e classes, se houver...
}
