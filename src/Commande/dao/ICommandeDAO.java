package Commande.dao;

import Commande.model.Commande;
import java.util.List;

public interface ICommandeDAO {
    int creerCommande(int userId, boolean paiementEnLigne);
    double calculerMontantTotal(int commandeId);
    void ajouterDetailCommande(int commandeId, int platId, int quantite);
    List<Commande> historiqueCommandes(int userId);
}
