package Paiement.service;

import Paiement.model.Paiement;

public interface IPaiementService {
    void simulerPaiement(int commandeId, double montant, String methode);
    void afficherHistoriquePaiement(int userId);
}
