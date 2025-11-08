package Commande.dao;

import Database.DBConnection;
import Plat.model.Plat;
import Commande.model.Panier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierDAO implements IPanierDAO {
    private Connection con = DBConnection.getConnection();

    @Override
    public void ajouterAuPanier(int userId, int platId, int quantite) {
        String sql = "INSERT INTO panier (user_id, plat_id, quantite) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, platId);
            ps.setInt(3, quantite);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void supprimerDuPanier(int userId, int platId) {
        String sql = "DELETE FROM panier WHERE user_id = ? AND plat_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, platId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Panier> voirPanier(int userId) {
        List<Panier> liste = new ArrayList<>();
        String sql = "SELECT p.*, pa.quantite FROM plat p JOIN panier pa ON p.id = pa.plat_id WHERE pa.user_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Plat plat = new Plat(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getString("categorie")
                );
                liste.add(new Panier(plat, rs.getInt("quantite")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    @Override
    public void viderPanier(int userId) {
        String sql = "DELETE FROM panier WHERE user_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
