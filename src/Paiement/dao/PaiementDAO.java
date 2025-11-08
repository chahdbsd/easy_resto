package Paiement.dao;

import Database.DBConnection;
import Paiement.model.Paiement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAO implements IPaiementDAO {
    private Connection con = DBConnection.getConnection();

    @Override
    public void enregistrerPaiement(Paiement paiement) {
        String sql = "INSERT INTO paiement (commande_id, montant, methode, effectue, date_paiement) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, paiement.getCommandeId());
            ps.setDouble(2, paiement.getMontant());
            ps.setString(3, paiement.getMethode());
            ps.setBoolean(4, paiement.isEffectue());
            ps.setTimestamp(5, paiement.getDatePaiement());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Paiement> historiquePaiements(int userId) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = """
            SELECT p.id, p.commande_id, p.montant, p.methode, p.effectue, p.date_paiement
            FROM paiement p
            JOIN commande c ON p.commande_id = c.id
            WHERE c.user_id = ?
            ORDER BY p.date_paiement DESC
        """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                paiements.add(new Paiement(
                        rs.getInt("id"),
                        rs.getInt("commande_id"),
                        rs.getDouble("montant"),
                        rs.getString("methode"),
                        rs.getBoolean("effectue"),
                        rs.getTimestamp("date_paiement")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paiements;
    }
}
