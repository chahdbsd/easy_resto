package Paiement.controller;

import Paiement.service.IPaiementService;
import Paiement.service.PaiementService;

public class PaiementController {
    private IPaiementService service = new PaiementService();

    public void effectuerPaiement(int commandeId, double montant, String methode) {
        service.simulerPaiement(commandeId, montant, methode);
    }

    public void afficherHistorique(int userId) {
        service.afficherHistoriquePaiement(userId);
    }
}
