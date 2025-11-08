package Commande.dao;

import Database.DBConnection;
import Commande.model.Commande;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO implements ICommandeDAO {
    private Connection con = DBConnection.getConnection();

    @Override
    public int creerCommande(int userId, boolean paiementEnLigne) {
        String sql = "INSERT INTO commande (user_id, statut, paiement_en_ligne) VALUES (?, 'En attente', ?) RETURNING id";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setBoolean(2, paiementEnLigne);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    @Override
    public void ajouterDetailCommande(int commandeId, int platId, int quantite) {
        String sql = "INSERT INTO commande_detail (commande_id, plat_id, quantite) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, commandeId);
            ps.setInt(2, platId);
            ps.setInt(3, quantite);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Commande> historiqueCommandes(int userId) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande WHERE user_id = ? ORDER BY date_commande DESC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                commandes.add(new Commande(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("date_commande"),
                        rs.getString("statut"),
                        rs.getBoolean("paiement_en_ligne")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return commandes;
    }

    @Override
    public double calculerMontantTotal(int commandeId) {
        double total = 0;
        String sql = "SELECT SUM(cd.quantite * p.prix) AS total " +
                "FROM commande_detail cd " +
                "JOIN plat p ON cd.plat_id = p.id " +
                "WHERE cd.commande_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, commandeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

}
