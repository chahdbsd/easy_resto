package Commande.dao;

import Commande.model.Commande;
import Database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO implements ICommandeDAO {

    @Override
    public int creerCommande(int userId, boolean paiementEnLigne) {
        int commandeId = -1;
        String sql = "INSERT INTO commande (user_id, date_commande, paiement_en_ligne) VALUES (?, NOW(), ?) RETURNING id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setBoolean(2, paiementEnLigne);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                commandeId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandeId;
    }

    @Override
    public void ajouterDetailCommande(int commandeId, int platId, int quantite) {
        String sql = "INSERT INTO commande_plat (commande_id, plat_id, quantite) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, commandeId);
            stmt.setInt(2, platId);
            stmt.setInt(3, quantite);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Commande> historiqueCommandes(int userId) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande WHERE user_id = ? ORDER BY date_commande DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                commandes.add(new Commande(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("date_commande"),
                        rs.getBoolean("paiement_en_ligne")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }

    @Override
    public double calculerMontantTotal(int commandeId) {
        double total = 0;
        String sql = "SELECT SUM(p.prix * cp.quantite) AS total " +
                "FROM commande_plat cp " +
                "JOIN plat p ON cp.plat_id = p.id " +
                "WHERE cp.commande_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, commandeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public double getPrixPlat(int platId) {
        double prix = 0;
        String sql = "SELECT prix FROM plat WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, platId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                prix = rs.getDouble("prix");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prix;
    }
}
