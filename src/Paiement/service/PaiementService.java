package Paiement.service;

import Paiement.dao.IPaiementDAO;
import Paiement.dao.PaiementDAO;
import Paiement.model.Paiement;

import java.sql.Timestamp;
import java.util.List;

// EVENTS (ton package est "events")
import events.AjoutEvenement;
import events.EvenementDispatcher;

public class PaiementService implements IPaiementService {

    private final IPaiementDAO paiementDAO = new PaiementDAO();


    private final EvenementDispatcher dispatcher;
    public PaiementService(EvenementDispatcher dispatcher) { this.dispatcher = dispatcher; }

    public PaiementService() { this.dispatcher = null; }

    @Override
    public void simulerPaiement(int commandeId, double montant, String methode) {
        boolean success;

        if (methode.equalsIgnoreCase("en ligne")) {
            System.out.println("💳 Traitement du paiement en ligne...");
            try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
            success = true;
            System.out.println("✅ Paiement en ligne réussi !");
        } else {
            success = false;
            System.out.println("🕓 Paiement à la récupération enregistré (non encore payé).");
        }

        Paiement paiement = new Paiement(
                0, commandeId, montant, methode, success,
                new Timestamp(System.currentTimeMillis())
        );

        paiementDAO.enregistrerPaiement(paiement);


        if (success && dispatcher != null) {
            dispatcher.publish(new AjoutEvenement("paiement#cmd:" + commandeId));
        }
    }

    @Override
    public void afficherHistoriquePaiement(int userId) {
        List<Paiement> liste = paiementDAO.historiquePaiements(userId);
        if (liste.isEmpty()) {
            System.out.println("⚠️ Aucun paiement trouvé pour cet utilisateur.");
        } else {
            liste.forEach(System.out::println);
        }
    }
}
