package View;

import java.util.List;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Connection.CarrosDAO;
import Connection.ClientesDAO;
import Controler.CarrosConstrol;
import Controler.ClientesConstrol;
import Model.Carros;
import Model.Clientes;

public class ClientesPainel extends JPanel {

    // Atributos
    private JButton cadastrar, apagar, editar;
    private JTextField inputNome, inputDataNasc, inputCpf, inputNumTel, inputEndereco;
    private List<Clientes> clientes;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1; // Valor para quando não selecionar nada

    // Construtor

    public ClientesPainel() {
        super();

        // entrada de dados
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Cadastro Clientes"));
        JPanel inputPanelClien = new JPanel();

        // Implementando os componentes
        // NOME
        inputPanelClien.setLayout(new GridLayout(5, 2));
        inputNome = new JTextField(20);
        inputPanelClien.add(new JLabel("Nome"));
        inputPanelClien.add(inputNome);

        // CPF
        inputPanelClien.setLayout(new GridLayout(5, 2));
        inputCpf = new JTextField(20);
        inputPanelClien.add(new JLabel("CPF"));
        inputPanelClien.add(inputCpf);

        // DATA DE NASCIMENTO
        inputPanelClien.setLayout(new GridLayout(5, 2));
        inputDataNasc = new JTextField(20);

        inputPanelClien.add(new JLabel("Data Nascimento"));
        inputPanelClien.add(inputDataNasc);

        // NUMERO TELEFONE
        inputPanelClien.setLayout(new GridLayout(5, 2));
        inputNumTel = new JTextField(20);
        inputPanelClien.add(new JLabel("Telefone"));
        inputPanelClien.add(inputNumTel);

        // ENDEREÇO
        inputPanelClien.setLayout(new GridLayout(5, 2));
        inputEndereco = new JTextField(20);
        inputPanelClien.add(new JLabel("Endereço"));
        inputPanelClien.add(inputEndereco);
        add(inputPanelClien);

        // Criando um painel chamado botoes e add os componentes
        JPanel botoes = new JPanel();
        botoes.add(cadastrar = new JButton("Cadastrar"));
        botoes.add(editar = new JButton("Editar"));
        botoes.add(apagar = new JButton("Apagar"));
        
        add(botoes);

        // tabela de carros
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);

        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Nome", "Data Nascimento", "CPF", "Numero Telefone", "Endereço" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        // Cria o banco de dados caso não tenha sido criado
        new ClientesDAO().criarTabela();

        // incluindo elementos do banco na criação do painel
        atualizarTabela();

        // tratamento de Eventos
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputNome.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputDataNasc.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputCpf.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputNumTel.setText((String) table.getValueAt(linhaSelecionada, 3));
                    inputEndereco.setText((String) table.getValueAt(linhaSelecionada, 4));
                }
            }
        });

        // Tratamento de eventos

        // Cria um objeto operacoes da classe CarrosControl para executar operações no
        // banco
        ClientesConstrol operacoes = new ClientesConstrol(clientes, tableModel, table);

        // Configura a ação do botão "cadastrar" para adicionar um novo registro no
        // banco de dados
        cadastrar.addActionListener(e -> {

            // Verifica se todos os campos estão preenchidos
            if (!inputNome.getText().isEmpty() || !inputEndereco.getText().isEmpty()
                    || !inputNumTel.getText().isEmpty() || !inputCpf.getText().isEmpty()
                    || !inputDataNasc.getText().isEmpty()) {

                // Pergunta se o usuario quer realmente se cadastrar
                int podCadast = JOptionPane.showConfirmDialog(cadastrar, "Tem certeza que deseja cadastrar?",
                        "Escolha uma opção", JOptionPane.YES_NO_OPTION);

                // Verifica se a escolha foi YES
                if (podCadast == JOptionPane.YES_OPTION) {
                    // Transforma os dados de anoDigitado em Inteiro
                    int anoDigitado = Integer.parseInt(inputDataNasc.getText());

                    // Compara se o ano está entre 1920 && 2024
                    if (anoDigitado > 1920 && anoDigitado < 2024) {
                        operacoes.cadastrar(inputNome.getText(), inputDataNasc.getText(),
                                inputCpf.getText(), inputNumTel.getText(), inputEndereco.getText());
                        // Limpa os campos de entrada após a operação de cadastro
                        inputNome.setText("");
                        inputDataNasc.setText("");
                        inputCpf.setText("");
                        inputNumTel.setText("");
                        inputEndereco.setText("");
                        JOptionPane.showMessageDialog(editar, "Cliente cadastrado com sucesso!");

                    } else {
                        JOptionPane.showMessageDialog(null, "DATA INVALÍDA\nDigite uma data válida (1920 até 2024)");
                    }
                } else {
                    // Se a escolha for <> de YES
                    // Busca uma segunda confirmação
                    int desejReturn = JOptionPane.showConfirmDialog(cadastrar, "Deseja retornar ao cadastro?",
                            "Escolha uma opção", JOptionPane.YES_NO_OPTION);
                    if (desejReturn == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    } else {

                        inputNome.setText("");
                        inputDataNasc.setText("");
                        inputCpf.setText("");
                        inputNumTel.setText("");
                        inputEndereco.setText("");

                    }
                }
            } else {
                JOptionPane.showMessageDialog(cadastrar, "Por favor, preencha todos os campos.");
            }
        });

        // Configura a ação do botão "editar" para atualizar um registro no banco de
        // dados
        editar.addActionListener(e -> {
            // Confirmação se deseja realmente atualizar os dados
            int podAtualizar = JOptionPane.showConfirmDialog(editar, "Deseja realmente atualizar os dados",
                    "Escolha uma opção: ", JOptionPane.YES_NO_OPTION);
            if (podAtualizar == JOptionPane.YES_OPTION) {
                // Chama o método "atualizar" do objeto operacoes com os valores dos campos de
                // entrada
                operacoes.atualizar(inputNome.getText(), inputDataNasc.getText(),
                       inputCpf.getText(), inputNumTel.getText(), inputEndereco.getText());
                // Limpa os campos de entrada após a operação de atualização
                inputNome.setText("");
                inputDataNasc.setText("");
                inputCpf.setText("");
                inputNumTel.setText("");
                inputEndereco.setText("");

                // Retorna ao usuario uma confirmação
                JOptionPane.showMessageDialog(editar, "Dados atualizados com sucesso!");
            } else {
                JOptionPane.showMessageDialog(editar,
                        "Infelizmente, não foi possivel cadastrar seus dados!\nPor favor, verifique se os dados estão corretos!");
                System.exit(0);
            }
        });

        // Configura a ação do botão "apagar" para excluir um registro no banco de dados
        apagar.addActionListener(e -> {
            int desejApag = JOptionPane.showConfirmDialog(apagar, "Deseja realmente deletar estes dados?",
                    "Escolha uma opção: ", JOptionPane.YES_NO_OPTION);

            if (desejApag == JOptionPane.YES_OPTION) {

                // Chama o método "apagar" do objeto operacoes com o valor do campo de entrada
                // "placa"
                operacoes.apagar(inputCpf.getText());
                // Limpa os campos de entrada após a operação de exclusão
                inputNome.setText("");
                inputDataNasc.setText("");
                inputCpf.setText("");
                inputNumTel.setText("");
                inputEndereco.setText("");

                JOptionPane.showMessageDialog(apagar, "Dados apagados com sucesso!");
            } else {
                // Se a escolha for <> de YES
                // Busca uma segunda confirmação
                int desejReturn = JOptionPane.showConfirmDialog(cadastrar, "Deseja retornar ao cadastro?",
                        "Escolha uma opção", JOptionPane.YES_NO_OPTION);
                if (desejReturn == JOptionPane.YES_OPTION) {
                    // Fecha somente o JOptionPane
                    System.exit(0);
                } else {
                    // Limpa todos os campos
                    inputNome.setText("");
                    inputDataNasc.setText("");
                    inputCpf.setText("");
                    inputNumTel.setText("");
                    inputEndereco.setText("");
                    // Fecha somente o JOptionPane
                    System.exit(0);
                }
            }
        });

    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        clientes = new ClientesDAO().listarClientes();
        // Obtém os carros atualizados do banco de dados
        for (Clientes cliente : clientes) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { cliente.getNome(), cliente.getDataNasc(),
                    cliente.getCpf(), cliente.getNumTel(), cliente.getEndereco() });
        }
    }
}
