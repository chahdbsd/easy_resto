package Commande.service;

public interface ICommandeService {
    int passerCommande(int userId, boolean paiementEnLigne);
    void voirHistorique(int userId);
    double calculerMontantTotal(int commandeId);
}
