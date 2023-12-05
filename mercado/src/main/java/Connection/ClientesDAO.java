package Connection;

import java.util.ArrayList;
import java.util.List;

import Model.Clientes;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientesDAO { // Responsável pela injeção de SQL
    // Atributos
    private Connection connection;
    private List<Clientes> clientes;

    // Construtor para conexão
    public ClientesDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    // Metodo para Criação da tabela de conexão
    public void criarTabela() {
        String query = "CREATE TABLE IF NOT EXISTS clientes_lojacarros (nome VARCHAR(255), endereco VARCHAR(255), numtel VARCHAR(255), cpf VARCHAR(255) PRIMARY KEY, datanasc VARCHAR(255))";

        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(query);
            System.out.println("Tabela criada com sucesso.");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    // Lista de todos os valores cadastrados
    public List<Clientes> listarClientes() {
        PreparedStatement stmt = null;
        // Declaração do objeto PreparedStatement para executar a consulta
        ResultSet rs = null;
        // Declaração do objeto ResultSet para armazenar os resultados da consulta

        clientes = new ArrayList<>();
        // Cria uma lista para armazenar os carros recuperados do banco de dados
        try {
            String query = "SELECT * FROM clientes_lojacarros";
            stmt = connection.prepareStatement(query);
            // Prepara a consulta SQL para selecionar todos os registros da tabela
            rs = stmt.executeQuery();
            // Executa a consulta e armazena os resultados no ResultSet
            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto Carros com os valores do
                // registro
                Clientes cliente = new Clientes(
                        rs.getString("nome"),
                        rs.getString("Endereco"),
                        rs.getString("numtel"),
                        rs.getString("cpf"),
                        rs.getString("datanasc"));
                clientes.add(cliente); // Add o objeto com todos os dados nele
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
            // Fecha os três
        }
        return clientes; // Retorna a lista para o banco de dados
    }

    // Cadastrar Clientes no banco de dados
    public void cadastrar(String nome, String endereco, String numTel, String cpf, String dataNasc) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para cadastrar na tabela
        String query = "INSERT INTO clientes_lojacarros (nome, endereco, numtel, cpf, datanasc) VALUES (?,?,?,?,?)";

        try {
            // Preparando a consulta para a injeção
            stmt = connection.prepareStatement(query);

            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, numTel);
            stmt.setString(4, cpf);
            stmt.setString(5, dataNasc);

            // Executa a consulta
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados no banco", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
            // Fecha a conexão e o PreparedStatement
        }
    }

    public void atualizar(String nome, String endereco, String numTel, String cpf, String dataNasc) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para atualizar dados pela placa
        String query = "UPDATE clientes_lojacarros SET nome= ?, endereco= ?, numTel = ?, datanasc = ? WHERE cpf = ?"; // CPF
                                                                                                                      // =
                                                                                                                      // PRIMARY
                                                                                                                      // KEY

        try {
            stmt = connection.prepareStatement(query);

            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, numTel);
            stmt.setString(5, cpf);
            stmt.setString(4, dataNasc);
            stmt.executeUpdate();
            System.out.println("Dados atualizados com sucesso");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);

        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    public void apagar(String cpf) {
        PreparedStatement stmt = null;

        String query = "DELETE FROM clientes_lojacarros WHERE cpf= ?";

        try {
            stmt = connection.prepareStatement(query);

            stmt.setString(1, cpf);
            stmt.executeUpdate();
            System.out.println("Dados apagado com sucesso");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        }
        ConnectionFactory.closeConnection(connection, stmt);
    }
}
