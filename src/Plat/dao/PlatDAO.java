package Plat.dao;

import Database.DBConnection;
import Plat.model.Plat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatDAO implements IPlatDAO {
    private Connection con = DBConnection.getConnection();

    @Override
    public List<Plat> findAll() {
        List<Plat> plats = new ArrayList<>();
        String sql = "SELECT * FROM plat";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                plats.add(new Plat(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getString("categorie")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plats;
    }

    @Override
    public List<Plat> findByCategorie(String categorie) {
        List<Plat> plats = new ArrayList<>();
        String sql = "SELECT * FROM plat WHERE LOWER(categorie) = LOWER(?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categorie);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                plats.add(new Plat(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getString("categorie")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plats;
    }

    @Override
    public Plat findById(int id) {
        String sql = "SELECT * FROM plat WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Plat(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getString("categorie")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
