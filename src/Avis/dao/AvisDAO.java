package Avis.dao;

import Avis.model.Avis;
import Database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvisDAO implements IAvisDAO {
    private Connection con = DBConnection.getConnection();

    @Override
    public int ajouterAvis(Avis avis) {
        String sql = "INSERT INTO avis (user_id, plat_id, note, commentaire, date) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, avis.getUserId());
            ps.setInt(2, avis.getPlatId());
            ps.setInt(3, avis.getNote());
            ps.setString(4, avis.getCommentaire());
            ps.setTimestamp(5, avis.getDate());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Avis> listerAvisParPlat(int platId) {
        List<Avis> avisList = new ArrayList<>();
        String sql = "SELECT * FROM avis WHERE plat_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, platId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                avisList.add(new Avis(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("plat_id"),
                        rs.getInt("note"),
                        rs.getString("commentaire"),
                        rs.getTimestamp("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avisList;
    }

    @Override
    public List<Avis> listerTousLesAvis() {
        List<Avis> avisList = new ArrayList<>();
        String sql = "SELECT * FROM avis";
        try (Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                avisList.add(new Avis(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("plat_id"),
                        rs.getInt("note"),
                        rs.getString("commentaire"),
                        rs.getTimestamp("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avisList;
    }

    @Override
    public boolean supprimerAvis(int avisId) {
        String sql = "DELETE FROM avis WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, avisId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
