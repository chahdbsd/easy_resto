package Commande.dao;

public interface ICommandeDAO {
    int creerCommande(int userId, boolean paiementEnLigne);
    void ajouterDetailCommande(int commandeId, int platId, int quantite);
    java.util.List<Commande.model.Commande> historiqueCommandes(int userId);
    double calculerMontantTotal(int commandeId);


    double getPrixPlat(int platId);
}
