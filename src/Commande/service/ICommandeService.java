package Commande.service;

import Commande.model.CommandePlat;
import java.util.List;

public interface ICommandeService {
    int passerCommande(int userId, boolean paiementEnLigne);
    void voirHistorique(int userId);
    double calculerMontantTotal(int commandeId);
    List<CommandePlat> passerCommandeAvecDetails(int userId, boolean paiementEnLigne);
    double calculerMontantTotalCommandeUtilisateur(int userId);
}
