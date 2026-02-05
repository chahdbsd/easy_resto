package Message.dao;

import Database.DBConnection;
import Message.model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageDAO implements IMessageDAO {


    private static final List<String> CANDIDATE_CONTENT_COLS = Arrays.asList(
            "contenu", "texte", "content", "message_text", "body", "msg", "message"
    );

    @Override
    public void envoyer(int userId, String contenu) {
        try (Connection c = DBConnection.getConnection()) {

            String contentCol = ensureContentColumn(c);


            boolean hasDate = columnExists(c, "message", "date_message");

            String sql = hasDate
                    ? "INSERT INTO message (user_id, " + contentCol + ", date_message) VALUES (?, ?, NOW())"
                    : "INSERT INTO message (user_id, " + contentCol + ") VALUES (?, ?)";

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setString(2, contenu);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Envoi message échoué: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Message> listeParUtilisateur(int userId) {
        List<Message> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {

            List<String> contentCols = getExistingContentColumns(c);
            if (contentCols.isEmpty()) {

                ensureContentColumn(c);
                contentCols = getExistingContentColumns(c);
            }


            String coalesce = "COALESCE(" + String.join(", ", contentCols) + ") AS contenu";

            boolean hasDate = columnExists(c, "message", "date_message");

            String sql = "SELECT id, user_id, " + coalesce +
                    (hasDate ? ", date_message " : ", NULL::timestamp AS date_message ") +
                    "FROM message WHERE user_id = ? " +
                    (hasDate ? "ORDER BY date_message DESC NULLS LAST, id DESC"
                            : "ORDER BY id DESC");

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new Message(
                                rs.getInt("id"),
                                rs.getInt("user_id"),
                                rs.getString("contenu"),
                                rs.getTimestamp("date_message")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void supprimer(int messageId) {
        final String sql = "DELETE FROM message WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, messageId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Suppression message échouée: " + e.getMessage(), e);
        }
    }




    private List<String> getExistingContentColumns(Connection c) throws SQLException {
        List<String> cols = new ArrayList<>();
        for (String name : CANDIDATE_CONTENT_COLS) {
            if (columnExists(c, "message", name)) cols.add(name);
        }
        return cols;
    }


    private boolean columnExists(Connection c, String table, String column) throws SQLException {
        String q = "SELECT 1 FROM information_schema.columns " +
                "WHERE table_schema = 'public' AND table_name = ? AND column_name = ? LIMIT 1";
        try (PreparedStatement ps = c.prepareStatement(q)) {
            ps.setString(1, table);
            ps.setString(2, column);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    private String ensureContentColumn(Connection c) throws SQLException {
        List<String> existing = getExistingContentColumns(c);
        if (existing.isEmpty()) {

            try (Statement st = c.createStatement()) {
                st.execute("ALTER TABLE message ADD COLUMN contenu TEXT");
            }
            return "contenu";
        } else {

            return existing.contains("contenu") ? "contenu" : existing.get(0);
        }
    }
}
