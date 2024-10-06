/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) throws Exception {
        try {
            // Estabelecendo a conexão com o banco de dados
            conn = connectDB();
            
            // SQL para inserir o produto
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(sql);
            
            // Definindo os parâmetros
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            // Executando a inserção
            prep.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            // Fechando os recursos
            if (prep != null) {
                try {
                    prep.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        return listagem;
    }

    private Connection connectDB() {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uc11?user=root&password=");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ConectaDAO: " + erro.getMessage());
        }
        return conn;
    }
}



