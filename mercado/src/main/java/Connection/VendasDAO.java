package Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Carros;
import Model.Vendas;

public class VendasDAO {
    // atributos
    private Connection connection;
    private List<Vendas> vendas;

    // construtor contendo a conexão
    public VendasDAO() {
        this.connection = ConnectionFactory.getConnection();
    }
    // criar Tabela
    public void criaTabela() {
        String query = "CREATE TABLE IF NOT EXISTS vendas_lojacarros (DATA_VENDA VARCHAR(255),CARRO_VENDIDO VARCHAR(255) PRIMARY KEY,CLIENTE VARCHAR(255),VALOR_DA_COMPRA VARCHAR(255))";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(query);
            System.out.println("Tabela criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    public List<Vendas> listarVendas() {
          PreparedStatement stmt = null;
        // Declaração do objeto PreparedStatement para executar a consulta
        ResultSet rs = null;
        // Declaração do objeto ResultSet para armazenar os resultados da consulta
        vendas = new ArrayList<>();
        // Cria uma lista para armazenar os vendas recuperados do banco de dados
        try {
            String query = "SELECT * FROM vendas_lojacarros";
            stmt = connection.prepareStatement(query);
            // Prepara a consulta SQL para selecionar todos os registros da tabela
            rs = stmt.executeQuery();
            // Executa a consulta e armazena os resultados no ResultSet
            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto Carros com os valores do
                // registro
                Vendas venda = new Vendas(
                        rs.getString("Data Venda"),
                        rs.getString("Carro Vendido"),
                        rs.getString("Cliente"),
                        rs.getString("Valor de Compra"));
                vendas.add(venda); // Adiciona o objeto Carros à lista de carros
            }
        } catch (SQLException ex) {
            System.out.println(ex); // Em caso de erro durante a consulta, imprime o erro
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
            // Fecha a conexão, o PreparedStatement e o ResultSet
        }
        return vendas; // Retorna a lista de carros recuperados do banco de dados
    }

    // Cadastrar Carro no banco
    public void comprar(String dataVenda, String carroVendido, String cliente, String valorCompra) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para cadastrar na tabela
        String query = "INSERT INTO vendas_lojacarros (DATA_VENDA, CARRO_VENDIDO, CLIENTE, VALOR_DA_COMPRA) VALUES (?, ?, ?, ?)";
        
        try {
            stmt = connection.prepareStatement(query);

            stmt.setString(1, dataVenda);
            stmt.setString(2, carroVendido);
            stmt.setString(3, cliente);
            stmt.setString(4, valorCompra);
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
        
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
        
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Atualizar dados no banco
    public void historico(String dataVenda, String carroVendido, String cliente, String valorCompra) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para atualizar dados pela cliente
        String query = "UPDATE carros_lojacarros SET marca = ?, modelo = ?, ano = ?, valor = ? WHERE cliente = ?"; //cliente é a minha chave primária //A ordem não importa, o que realmente importa é a ordem dos pontos de interrogação em relação a ordem no inferior
        
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, dataVenda);
            stmt.setString(2, carroVendido);
            stmt.setString(3, cliente);
            stmt.setString(4, valorCompra);
            stmt.executeUpdate();
            System.out.println("Dados atualizados com sucesso");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);
        
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Apagar dados do banco
    public void limpar(String dataVenda, String carroVendido, String cliente, String valorCompra) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para apagar dados pela cliente
        String query = "DELETE FROM carros_lojacarros WHERE cliente = ?";
        
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, cliente);
            stmt.executeUpdate(); // Executa a instrução SQL
            System.out.println("Dado apagado com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        } 
        ConnectionFactory.closeConnection(connection, stmt);
    }
}


    //Inserir a criação de uma tabela, contendo Primary KEY e Foreign KEY para buscar os dados dos nomes dos meus clientes (Usando a primary key de clientes) e a Foreign KEY de Carros para buscar Nome do carro e valor da compra (Usando a primary key de carros)

    //Não esqueça de criar o campo para inserção de data da venda, ou faça usando alguma função do proprio java com base na hora do computador.


    //FALTA A DOCUMENTAÇÃO, BASICAMENTE UM MANUAL DE COMO USAR

