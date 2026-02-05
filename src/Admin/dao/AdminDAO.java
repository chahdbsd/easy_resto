package Admin.dao;

import Database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements IAdminDAO {



    @Override
    public List<String> listUsers() {
        final String sql = "SELECT id, nom, prenom, email, is_admin FROM utilisateur ORDER BY id";
        List<String> out = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(String.format("#%d | %s %s | %s | admin=%s",
                        rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("email"), rs.getBoolean("is_admin")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    @Override
    public int createUser(String nom, String prenom, String email, String password, boolean isAdmin) {

        String sql = "INSERT INTO utilisateur (nom, prenom, email, password, mot_de_passe, is_admin) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, password);
            ps.setBoolean(6, isAdmin);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {

            try (Connection c2 = DBConnection.getConnection();
                 PreparedStatement ps2 = c2.prepareStatement(
                         "INSERT INTO utilisateur (nom, prenom, email, is_admin) VALUES (?, ?, ?, ?) RETURNING id")) {
                ps2.setString(1, nom);
                ps2.setString(2, prenom);
                ps2.setString(3, email);
                ps2.setBoolean(4, isAdmin);
                try (ResultSet rs = ps2.executeQuery()) {
                    if (rs.next()) return rs.getInt(1);
                }
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return -1;
    }

    @Override public void updateUserEmail(int userId, String newEmail) {
        runUpdate("UPDATE utilisateur SET email=? WHERE id=?", ps -> { ps.setString(1,newEmail); ps.setInt(2,userId); });
    }

    @Override public void updateUserPassword(int userId, String newPassword) {
        // tente d'abord password, puis mot_de_passe
        int n = runUpdateCount("UPDATE utilisateur SET password=? WHERE id=?", ps -> { ps.setString(1,newPassword); ps.setInt(2,userId); });
        if (n==0) runUpdate("UPDATE utilisateur SET mot_de_passe=? WHERE id=?", ps -> { ps.setString(1,newPassword); ps.setInt(2,userId); });
    }

    @Override public void setAdmin(int userId, boolean isAdmin) {
        runUpdate("UPDATE utilisateur SET is_admin=? WHERE id=?", ps -> { ps.setBoolean(1,isAdmin); ps.setInt(2,userId); });
    }

    @Override public void deleteUser(int userId) {
        runUpdate("DELETE FROM utilisateur WHERE id=?", ps -> ps.setInt(1,userId));
    }



    @Override
    public List<String> listPlats() {
        List<String> out = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {
            boolean hasDisponible = columnExists(c, "plat", "disponible");
            String sql = hasDisponible
                    ? "SELECT id, nom, description, prix, categorie, disponible FROM plat ORDER BY id"
                    : "SELECT id, nom, description, prix, categorie FROM plat ORDER BY id";
            try (PreparedStatement ps = c.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (hasDisponible) {
                        out.add(String.format("#%d | %s | %.2f€ | cat=%s | dispo=%s\n    %s",
                                rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"),
                                rs.getString("categorie"), rs.getBoolean("disponible"),
                                rs.getString("description")));
                    } else {
                        out.add(String.format("#%d | %s | %.2f€ | cat=%s\n    %s",
                                rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"),
                                rs.getString("categorie"), rs.getString("description")));
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    @Override
    public int createPlat(String nom, String description, double prix, String categorie, boolean disponible) {

        try (Connection c = DBConnection.getConnection()) {
            boolean hasDisponible = columnExists(c, "plat", "disponible");
            String sql = hasDisponible
                    ? "INSERT INTO plat (nom, description, prix, categorie, disponible) VALUES (?,?,?,?,?) RETURNING id"
                    : "INSERT INTO plat (nom, description, prix, categorie) VALUES (?,?,?,?) RETURNING id";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, nom);
                ps.setString(2, description);
                ps.setDouble(3, prix);
                ps.setString(4, categorie);
                if (hasDisponible) ps.setBoolean(5, disponible);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    @Override public void updatePlatPrice(int platId, double prix) {
        runUpdate("UPDATE plat SET prix=? WHERE id=?", ps -> { ps.setDouble(1,prix); ps.setInt(2,platId); });
    }

    @Override public void updatePlatDesc(int platId, String desc) {
        runUpdate("UPDATE plat SET description=? WHERE id=?", ps -> { ps.setString(1,desc); ps.setInt(2,platId); });
    }

    @Override public void setPlatDisponible(int platId, boolean disponible) {
        runUpdate("UPDATE plat SET disponible=? WHERE id=?", ps -> { ps.setBoolean(1,disponible); ps.setInt(2,platId); });
    }

    /* ============ Commandes ============ */

    @Override
    public List<String> listCommandes(String statut) {
        String base = "SELECT id, user_id, statut, date_commande FROM commande";
        String sql = (statut == null || statut.isBlank())
                ? base + " ORDER BY date_commande DESC NULLS LAST, id DESC"
                : base + " WHERE statut = ? ORDER BY date_commande DESC NULLS LAST, id DESC";
        List<String> out = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (statut != null && !statut.isBlank()) ps.setString(1, statut);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(String.format("#%d | user=%d | statut=%s | %s",
                            rs.getInt("id"), rs.getInt("user_id"),
                            rs.getString("statut"), String.valueOf(rs.getTimestamp("date_commande"))));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    @Override
    public void updateCommandeStatut(int commandeId, String statut) {
        runUpdate("UPDATE commande SET statut=? WHERE id=?", ps -> { ps.setString(1,statut); ps.setInt(2,commandeId); });
    }



    @Override
    public List<String> listPaiements(int limit) {
        final String sql = "SELECT id, commande_id, user_id, montant, methode, statut, date_paiement " +
                "FROM paiement ORDER BY date_paiement DESC NULLS LAST, id DESC LIMIT ?";
        List<String> out = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, Math.max(1, limit));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(String.format("#%d | cmd=%d | user=%d | %.2f€ | %s | %s | %s",
                            rs.getInt("id"), rs.getInt("commande_id"), rs.getInt("user_id"),
                            rs.getDouble("montant"), rs.getString("methode"),
                            rs.getString("statut"), String.valueOf(rs.getTimestamp("date_paiement"))));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    @Override
    public List<String> detecterAnomaliesPaiements() {
        final String sql =
                "SELECT p.id AS paiement_id, p.commande_id, p.montant AS montant_paiement, " +
                        "       COALESCE(SUM(cp.quantite * cp.prix_unitaire),0) AS montant_theorique " +
                        "FROM paiement p " +
                        "LEFT JOIN commande_plat cp ON cp.commande_id = p.commande_id " +
                        "GROUP BY p.id, p.commande_id, p.montant " +
                        "HAVING ABS(p.montant - COALESCE(SUM(cp.quantite * cp.prix_unitaire),0)) > 0.009 " +
                        "ORDER BY p.id DESC";
        List<String> out = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(String.format("⚠️ paiement #%d | cmd=%d | payé=%.2f€ ≠ attendu=%.2f€",
                        rs.getInt("paiement_id"), rs.getInt("commande_id"),
                        rs.getDouble("montant_paiement"), rs.getDouble("montant_theorique")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }



    @Override
    public List<String> listMessagesByUser(int userId) {
        List<String> out = new ArrayList<>();
        try (Connection c = DBConnection.getConnection()) {


            boolean hasContenu     = columnExists(c, "message", "contenu");
            boolean hasMessageText = columnExists(c, "message", "message");
            boolean hasDateMessage = columnExists(c, "message", "date_message");
            boolean hasDateEnvoi   = columnExists(c, "message", "date_envoi");


            String contentExpr;
            if (hasContenu && hasMessageText) {
                contentExpr = "COALESCE(contenu, \"message\") AS contenu";
            } else if (hasContenu) {
                contentExpr = "contenu AS contenu";
            } else if (hasMessageText) {
                contentExpr = "\"message\" AS contenu";
            } else {
                contentExpr = "'' AS contenu"; // filet de sécurité
            }


            String dateExpr;
            if (hasDateMessage) {
                dateExpr = "date_message";
            } else if (hasDateEnvoi) {
                dateExpr = "date_envoi AS date_message";
            } else {
                dateExpr = "NULL::timestamp AS date_message";
            }

            String sql = "SELECT id, user_id, " + contentExpr + ", " + dateExpr + " " +
                    "FROM message WHERE user_id = ? " +
                    "ORDER BY date_message DESC NULLS LAST, id DESC";

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        out.add(String.format("#%d | %s | %s",
                                rs.getInt("id"),
                                String.valueOf(rs.getTimestamp("date_message")),
                                rs.getString("contenu")));
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return out;
    }

    @Override
    public void deleteMessage(int messageId) {
        runUpdate("DELETE FROM message WHERE id=?", ps -> ps.setInt(1, messageId));
    }



    @Override
    public boolean isUserAdmin(int userId) {
        final String sql = "SELECT is_admin FROM utilisateur WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getBoolean(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }



    private interface Binder { void bind(PreparedStatement ps) throws SQLException; }

    private void runUpdate(String sql, Binder b) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            b.bind(ps);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private int runUpdateCount(String sql, Binder b) {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            b.bind(ps);
            return ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private boolean columnExists(Connection c, String table, String column) throws SQLException {
        String q = "SELECT 1 FROM information_schema.columns " +
                "WHERE table_schema='public' AND table_name=? AND column_name=? LIMIT 1";
        try (PreparedStatement ps = c.prepareStatement(q)) {
            ps.setString(1, table);
            ps.setString(2, column);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
