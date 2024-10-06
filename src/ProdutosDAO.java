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
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11?useSSL=false&autoReconnect=true", "root", "admin");
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setInt(2, produto.getValor());
        stmt.setString(3, produto.getStatus());
        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new SQLException("Erro ao cadastrar produto: " + e.getMessage(), e);
    } finally {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // Log e ignorar
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // Log e ignorar
            }
        }
    }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        try {
            conn = connectDB();
            String sql = "SELECT * FROM produtos";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            if (resultset != null) {
                try {
                    resultset.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
        return listagem;
    }

    private Connection connectDB() {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/uc11?user=root&password=admin");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ConectaDAO: " + erro.getMessage());
        }
        return conn;
    }
}



