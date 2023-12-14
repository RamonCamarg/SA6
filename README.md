Documentação SA6 - Projeto JAVA Supermercado
Visão Geral
O Sistema de Gerenciamento de Supermercado é uma aplicação Java que permite a gestão eficiente das operações diárias de um supermercado. Ele inclui funcionalidades como controle de estoque, processamento de vendas, gerenciamento de clientes, entre outros.

Pré-requisitos
JDK (Java Development Kit) instalado
PostgreSQL instalado e configurado
IDE Java (por exemplo, Eclipse, IntelliJ) instalada
Maven para gerenciamento de dependências (opcional)
Estrutura do Projeto
O projeto é estruturado da seguinte forma:

src: Pasta APP com um arquivo MAIN.JAVA - para rodar a aplicação.
Pasta CONNECTION com os seguintes arquivos: clientesDAO.java, EstoqueDAO.java, ComprasDAO.java e ConnectionFactory.java.
Pasta CONTROLLER com os seguintes arquivos: clientesControl.java, EstoqueControl.java, ComprasControl.java.
Pasta EXCEPTION com os seguintes arquivos: CodeFormatException.java, CpfValidationException.java, QuantityFormatExeception.java.
Pasta MODEL com os seguintes arquivos: clientes.java, Estoque.java, Compras.java.
Pasta VIEW com os seguintes arquivos: clientesPainel.java, EstoquePainel.java, ComprasPainel.java, JanelaPrincipal.java.

Tecnologias Utilizadas
Java
PostgreSQL
JDBC para comunicação com o banco de dados
Construção e Execução
Execute o script de criação do banco de dados (create_database.sql) no PostgreSQL.
Compile e execute o aplicativo a partir da sua IDE ou utilizando ferramentas como Maven.
Testes
O projeto inclui testes unitários para garantir a integridade das funcionalidades. Execute os testes utilizando sua IDE ou ferramentas de build.

Conclusão
Esta documentação fornece uma visão geral do projeto e orientações para configurar, construir e contribuir.
