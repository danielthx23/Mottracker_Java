import java.sql.*;
import java.util.Properties;

public class TestUsers {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        Properties props = new Properties();
        props.setProperty("user", "RM558263");
        props.setProperty("password", "fiap");
        
        try (Connection conn = DriverManager.getConnection(url, props)) {
            System.out.println("Conectado ao banco de dados!");
            
            // Verificar se a tabela de usuários existe
            String checkTable = "SELECT COUNT(*) FROM mt_usuario_java";
            try (PreparedStatement stmt = conn.prepareStatement(checkTable);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Número de usuários na tabela: " + count);
                }
            }
            
            // Listar todos os usuários
            String listUsers = "SELECT id_usuario, nome_usuario, email_usuario, senha_usuario FROM mt_usuario_java";
            try (PreparedStatement stmt = conn.prepareStatement(listUsers);
                 ResultSet rs = stmt.executeQuery()) {
                System.out.println("\nUsuários encontrados:");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getLong("id_usuario") + 
                                     ", Nome: " + rs.getString("nome_usuario") + 
                                     ", Email: " + rs.getString("email_usuario") + 
                                     ", Senha: " + rs.getString("senha_usuario").substring(0, Math.min(20, rs.getString("senha_usuario").length())) + "...");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
