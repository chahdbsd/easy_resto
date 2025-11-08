package Commande.dao;

import Commande.model.Panier;
import Plat.model.Plat;
import Database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierDAO implements IPanierDAO {

    @Override
    public void ajouterAuPanier(int userId, int platId, int quantite) {
        String sql = "INSERT INTO panier (user_id, plat_id, quantite) VALUES (?, ?, ?) " +
                "ON CONFLICT (user_id, plat_id) DO UPDATE SET quantite = panier.quantite + EXCLUDED.quantite";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, platId);
            stmt.setInt(3, quantite);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Panier> voirPanier(int userId) {
        List<Panier> panier = new ArrayList<>();
        String sql = "SELECT p.id AS plat_id, p.nom, p.description, p.prix, p.categorie, pa.quantite " +
                "FROM panier pa JOIN plat p ON pa.plat_id = p.id " +
                "WHERE pa.user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Plat plat = new Plat(
                            rs.getInt("plat_id"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getDouble("prix"),
                            rs.getString("categorie")
                    );
                    panier.add(new Panier(plat, rs.getInt("quantite")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return panier;
    }

    @Override
    public void viderPanier(int userId) {
        String sql = "DELETE FROM panier WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerDuPanier(int userId, int platId) {
        String sql = "DELETE FROM panier WHERE user_id = ? AND plat_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, platId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
