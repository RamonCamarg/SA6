package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {
    private JLabel img;
    private JPanel panel, cards, menu, app;
    private JButton entrar, sair;

    public MainFrame() {
        super("Loja de Carros");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        CardLayout cl = new CardLayout();
        panel = new JPanel();
        cards = new JPanel();
        cards.setLayout(cl);
        // ---------------------*
        // Menu do app:
        menu = new JPanel(new BorderLayout()); // Definindo o layout do JPanel menu

        img = new JLabel();
        ImageIcon iconMenu = new ImageIcon(getClass().getResource("imgs/menu.jpg"));
        img.setIcon(iconMenu);
        menu.add(img); // Adicionando uma imagem ao menu do app

        entrar = new JButton("Entrar"); // Inicializando o botão entrar

        menu.add(entrar, BorderLayout.SOUTH);
        cards.add(menu, "Menu");

        // ---------------------*
        // Aplicativo principal:
        app = new JPanel(new BorderLayout());
        JTabbedPane abas = new JTabbedPane();
        abas.add("Carros", new EstoquePanel()); // Adiciona o painel de carros ao TabbedPane
        abas.add("Clientes", new ClientesPanel()); // Adiciona o painel de cliente ao TabbedPane
        abas.add("Vendas", new VendasPanel()); // Adiciona o painel de vendas ao TabbedPane
        sair = new JButton("Sair");
        app.add(abas);
        app.add(sair, BorderLayout.SOUTH);
        cards.add(app, "App");
        // ---------------------*
        panel.add(cards);
        add(panel);
        // ---------------------*
        /*
         * Estilização
         */
        entrar.setBackground(new Color(199, 36, 44));
        entrar.setForeground(new Color(255, 255, 255));
        sair.setBackground(new Color(199, 36, 44));
        sair.setForeground(new Color(255, 255, 255));
        abas.setBackground(new Color(186, 82, 87));
        abas.setForeground(new Color(255, 255, 255));
        app.setBackground(new Color(199, 36, 44));
        // ---------------------*
        // Tratamento de eventos:

        entrar.addActionListener(new ActionListener() { // Entra no app
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bem-Vindo :)", "MegaMotors", JOptionPane.INFORMATION_MESSAGE);
                cl.show(cards, "App");
            }
        });

        sair.addActionListener(new ActionListener() { // Sai do app e volta para o menu
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?",
                        "Editar", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    cl.show(cards, "Menu");
                }
            }
        });
        // ---------------------*
        addWindowListener(new WindowAdapter() { // Questiona o usuário se realmente ele deseja fechar a aplicação

            @Override
            public void windowClosing(WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?",
                        "MegaMotors", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(2);
                }
            }

        });
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
