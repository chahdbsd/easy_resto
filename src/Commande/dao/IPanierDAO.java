package Commande.dao;

import Commande.model.Panier;
import java.util.List;

public interface IPanierDAO {
    void ajouterAuPanier(int userId, int platId, int quantite);
    void supprimerDuPanier(int userId, int platId);
    List<Panier> voirPanier(int userId);
    void viderPanier(int userId);
}
