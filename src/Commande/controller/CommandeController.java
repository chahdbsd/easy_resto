package Commande.controller;

import Commande.service.CommandeService;
import Commande.service.ICommandeService;
import Paiement.view.PaiementView;

import java.util.Scanner;

public class CommandeController {
    private ICommandeService service = new CommandeService();
    private Scanner scanner = new Scanner(System.in);

    /**
     * Permet de passer une commande.
     * Si paiementEnLigne = true, on déclenche la simulation de paiement.
     */
    public void passerCommande(int userId, boolean paiementEnLigne) {
        System.out.println("\n=== PASSER UNE COMMANDE ===");

        // 1️⃣ Création de la commande
        int commandeId = service.passerCommande(userId, paiementEnLigne);

        if (commandeId > 0) {
            System.out.println("✅ Commande créée avec succès (ID: " + commandeId + ")");

            // 2️⃣ Calcul du montant total
            double montantTotal = service.calculerMontantTotal(commandeId);
            System.out.println("Montant total : " + montantTotal + "€");

            // 3️⃣ Paiement (simulation)
            if (paiementEnLigne) {
                System.out.println("➡️ Vous avez choisi le paiement en ligne.");
                new PaiementView().afficherMenuPaiement(commandeId, montantTotal, userId);
            } else {
                System.out.println("🕓 Paiement à la récupération enregistré.");
                System.out.print("Souhaitez-vous finalement payer maintenant ? (o/n) : ");
                String choix = scanner.nextLine();
                if (choix.equalsIgnoreCase("o")) {
                    new PaiementView().afficherMenuPaiement(commandeId, montantTotal, userId);
                }
            }

        } else {
            System.out.println("❌ Échec de la commande.");
        }
    }

    /**
     * Affiche l'historique des commandes d'un utilisateur.
     */
    public void voirHistorique(int userId) {
        service.voirHistorique(userId);
    }
}
