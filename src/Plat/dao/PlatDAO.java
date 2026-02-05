package Plat.dao;

import Plat.model.Plat;
import Database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatDAO implements IPlatDAO {

    @Override
    public List<Plat> findAll() {
        List<Plat> plats = new ArrayList<>();
        String sql = "SELECT id, nom, description, prix, categorie FROM plat";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Plat plat = new Plat(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getString("categorie")
                );
                plats.add(plat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plats;
    }

    @Override
    public Plat findById(int id) {
        String sql = "SELECT id, nom, description, prix, categorie FROM plat WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Plat(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getDouble("prix"),
                            rs.getString("categorie")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Plat> findByCategorie(String categorie) {
        List<Plat> plats = new ArrayList<>();
        String sql = "SELECT id, nom, description, prix, categorie FROM plat WHERE categorie = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categorie);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Plat plat = new Plat(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getDouble("prix"),
                            rs.getString("categorie")
                    );
                    plats.add(plat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plats;
    }

}
