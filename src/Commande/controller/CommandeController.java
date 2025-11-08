package Commande.controller;

import Commande.service.CommandeService;
import Commande.service.ICommandeService;
import Commande.model.CommandePlat;
import Paiement.view.PaiementView;
import Avis.view.AvisView;
import java.util.List;
import java.util.Scanner;

public class CommandeController {
    private ICommandeService service = new CommandeService();
    private Scanner scanner = new Scanner(System.in);
    private AvisView avisView = new AvisView();

    public List<CommandePlat> passerCommandeAvecDetails(int userId, boolean paiementEnLigne) {
        System.out.println("\n=== PASSER UNE COMMANDE ===");

        List<CommandePlat> platsCommandes = service.passerCommandeAvecDetails(userId, paiementEnLigne);
        if (platsCommandes.isEmpty()) return platsCommandes;

        int commandeId = platsCommandes.get(0).getPlatId(); // info rapide
        double montantTotal = service.calculerMontantTotalCommandeUtilisateur(userId);
        System.out.println("Montant total : " + montantTotal + "€");

        if (paiementEnLigne) {
            new PaiementView().afficherMenuPaiement(commandeId, montantTotal, userId);
        } else {
            System.out.print("Souhaitez-vous payer maintenant ? (o/n) : ");
            String choix = scanner.nextLine();
            if (choix.equalsIgnoreCase("o")) {
                new PaiementView().afficherMenuPaiement(commandeId, montantTotal, userId);
            }
        }

        for (CommandePlat cp : platsCommandes) {
            System.out.println("\nVoulez-vous laisser un avis pour : " + cp.getNom() + " ? (oui/non)");
            String reponse = scanner.nextLine().trim().toLowerCase();
            if (reponse.equals("oui")) {
                avisView.ajouterAvis(userId, cp.getPlatId());
            }
        }

        return platsCommandes;
    }

    public void voirHistorique(int userId) {
        service.voirHistorique(userId);
    }
}
