package Avis.dao;

import Avis.model.Avis;
import Database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvisDAO implements IAvisDAO {

    @Override
    public List<Avis> findAll() {
        String sql = "SELECT id, user_id, plat_id, note, commentaire, " +
                "       (SELECT column_name FROM information_schema.columns " +
                "        WHERE table_name='avis' AND column_name='date_avis') AS has_date, " +
                "       date_avis " +
                "FROM avis ORDER BY COALESCE(date_avis, '1970-01-01'::timestamp) DESC, id DESC";
        List<Avis> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Avis> findByPlat(int platId) {
        String sql = "SELECT id, user_id, plat_id, note, commentaire, date_avis " +
                "FROM avis WHERE plat_id = ? " +
                "ORDER BY COALESCE(date_avis, '1970-01-01'::timestamp) DESC, id DESC";
        List<Avis> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, platId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(int userId, int platId, int note, String commentaire) {
        String withDate = "INSERT INTO avis (user_id, plat_id, note, commentaire, date_avis) VALUES (?, ?, ?, ?, NOW())";
        String noDate   = "INSERT INTO avis (user_id, plat_id, note, commentaire) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(withDate)) {
            ps.setInt(1, userId);
            ps.setInt(2, platId);
            ps.setInt(3, note);
            ps.setString(4, commentaire);
            ps.executeUpdate();
        } catch (SQLException e1) {
            try (Connection c2 = DBConnection.getConnection();
                 PreparedStatement ps2 = c2.prepareStatement(noDate)) {
                ps2.setInt(1, userId);
                ps2.setInt(2, platId);
                ps2.setInt(3, note);
                ps2.setString(4, commentaire);
                ps2.executeUpdate();
            } catch (SQLException e2) {
                e1.printStackTrace();
                e2.printStackTrace();
                throw new RuntimeException("Insertion avis échouée: " + e1.getMessage(), e1);
            }
        }
    }

    @Override
    public void delete(int avisId) {
        String sql = "DELETE FROM avis WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, avisId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Suppression avis échouée: " + e.getMessage(), e);
        }
    }

    private Avis map(ResultSet rs) throws SQLException {
        Avis a = new Avis();
        a.setId(rs.getInt("id"));
        a.setUserId(rs.getInt("user_id"));
        a.setPlatId(rs.getInt("plat_id"));
        a.setNote(rs.getInt("note"));
        a.setCommentaire(rs.getString("commentaire"));

        try {

            Timestamp ts = null;
            try { ts = rs.getTimestamp("date_avis"); } catch (SQLException ignore) {}
            a.setDateAvis(ts);
        } catch (Exception ignore) {}

        return a;
    }
}
