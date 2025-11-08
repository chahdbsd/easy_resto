package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection con;

    public static Connection getConnection() {
        try {
            // Si la connexion n'existe pas ou est fermée, on en crée une nouvelle
            if (con == null || con.isClosed()) {
                String url = "jdbc:postgresql://localhost:5432/project_resto";
                String user = "postgres";
                String password = "maryam10"; // adapter selon ta config
                con = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    // Ne jamais fermer la connexion ici si elle est partagée
    // Les DAO utilisent la même connexion statique
}
