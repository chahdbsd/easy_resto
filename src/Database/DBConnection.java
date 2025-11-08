package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection con;

    public static Connection getConnection() {
        if (con == null) {
            try {
                String url = "jdbc:postgresql://localhost:5432/project_resto";
                String user = "postgres";
                String password = "maryam10"; // adapter selon ton config
                con = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }
}
